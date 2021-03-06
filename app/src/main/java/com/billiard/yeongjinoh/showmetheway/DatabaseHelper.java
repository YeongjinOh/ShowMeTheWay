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

    public void println(String msg) {
        Log.d(TAG, msg);
    }
}
