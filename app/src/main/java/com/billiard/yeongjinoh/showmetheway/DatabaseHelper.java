package com.billiard.yeongjinoh.showmetheway;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yeongjinoh on 2016-07-21.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TAG = "ScoreDataBase";
    private static String DATABASE_NAME = "scoreDB";
    private static String TABLE_NAME = "score";
    private static String TABLE_NAME2 = "time";
    private static String TABLE_NAME3 = "rank";
    private static int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        println("creating or opening table [" + TABLE_NAME + "].");

        String CREATE_SQL = "create table " + TABLE_NAME + "("
                + " _id integer PRIMARY KEY autoincrement, "
                + " score integer, "
                + " date text, "
                + " time text)";
        try {
            db.execSQL(CREATE_SQL);
        } catch(Exception ex) {
            Log.e(TAG, "Exception in CREATE_SQL", ex);
        }

        println("creating or opening table [" + TABLE_NAME2 + "].");

        String CREATE_SQL2 = "create table " + TABLE_NAME2 + "("
                + " _id integer PRIMARY KEY autoincrement, "
                + " login text, "
                + " lastgame text, "
                + " logout text)";
        try {
            db.execSQL(CREATE_SQL2);
        } catch(Exception ex) {
            Log.e(TAG, "Exception in CREATE_SQL2", ex);
        }


        println("creating or opening table [" + TABLE_NAME3 + "].");

        String CREATE_SQL3 = "create table " + TABLE_NAME3 + "("
                + " email text PRIMARY KEY, "
                + " name text, "
                + " score integer)";
        try {
            db.execSQL(CREATE_SQL3);
            setSampleUsers(db);
        } catch(Exception ex) {
            Log.e(TAG, "Exception in CREATE_SQL3", ex);
        }
    }
    public void onOpen(SQLiteDatabase db) {
        println("opened database [" + DATABASE_NAME + "].");

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "Upgrading database from version " + oldVersion +
                " to " + newVersion + ".");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
        onCreate(db);
    }

    public void setSampleUsers(SQLiteDatabase db) {
        String[] namelist = {"Raccoon","Sangmin Oh","주희진", "전민준", "강건욱", "Gungee kim", "이슬기", "김경률", "Minsu", "YJ",
            "오영신", "김도경", "이영근", "최대성", " 박종원", "이하늘", "Hyungmin", "김찬", "김준호", "Bruce lee",
                "Gyungho", "Choi Junghun", "이은석", "도민호", "마송원"};
        int[] scorelist = {4820, 3420, 2480, 1020, 4520, 0, 20, 10, 10, 130, 110, 520, 780, 1200, 70,
                            500, 100, 30, 40, 30, 20, 60, 480, 1990, 2580};
        for (int i=0; i<25; i++) {
            String query = String.format("INSERT INTO %s (email, name, score) VALUES ('sample%s@gmail.com', '%s', %s);", TABLE_NAME3,
                    Integer.toString(i), namelist[i], Integer.toString(scorelist[i]));
            db.execSQL(query);
        }
    }

    public void println(String msg) {
        Log.d(TAG, msg);
    }
}