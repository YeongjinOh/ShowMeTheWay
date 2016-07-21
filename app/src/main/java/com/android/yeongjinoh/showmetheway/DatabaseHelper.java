package com.android.yeongjinoh.showmetheway;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by yeongjinoh on 2016-07-21.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TAG = "ScoreDataBase";
    private static String DATABASE_NAME = "scoreDB";
    private static String TABLE_NAME = "score";
    private static int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        println("creating table [" + TABLE_NAME + "].");

        try {
            String DROP_SQL = "drop table if exists " + TABLE_NAME;
            db.execSQL(DROP_SQL);
        } catch(Exception ex) {
            Log.e(TAG, "Exception in DROP_SQL", ex);
        }

        String CREATE_SQL = "create table " + TABLE_NAME + "("
                + " _id integer PRIMARY KEY autoincrement, "
                + " date text, "
                + " score integer)";

        try {
            db.execSQL(CREATE_SQL);
        } catch(Exception ex) {
            Log.e(TAG, "Exception in CREATE_SQL", ex);
        }

        println("inserting records.");

        try {
            db.execSQL( "insert into " + TABLE_NAME + "(date, score) values ('16-07-21', 200);" );
            db.execSQL( "insert into " + TABLE_NAME + "(date, score) values ('16-07-10', 150);" );
            db.execSQL( "insert into " + TABLE_NAME + "(date, score) values ('16-07-17', 20);" );
            db.execSQL( "insert into " + TABLE_NAME + "(date, score) values ('16-07-21', 200);" );
            db.execSQL( "insert into " + TABLE_NAME + "(date, score) values ('16-07-10', 150);" );
            db.execSQL( "insert into " + TABLE_NAME + "(date, score) values ('16-07-17', 20);" );
            db.execSQL( "insert into " + TABLE_NAME + "(date, score) values ('16-07-21', 200);" );
            db.execSQL( "insert into " + TABLE_NAME + "(date, score) values ('16-07-10', 150);" );
            db.execSQL( "insert into " + TABLE_NAME + "(date, score) values ('16-07-17', 20);" );
            db.execSQL( "insert into " + TABLE_NAME + "(date, score) values ('16-07-21', 200);" );
            db.execSQL( "insert into " + TABLE_NAME + "(date, score) values ('16-07-10', 150);" );
            db.execSQL( "insert into " + TABLE_NAME + "(date, score) values ('16-07-17', 20);" );
            db.execSQL( "insert into " + TABLE_NAME + "(date, score) values ('16-07-21', 200);" );
            db.execSQL( "insert into " + TABLE_NAME + "(date, score) values ('16-07-10', 150);" );
            db.execSQL( "insert into " + TABLE_NAME + "(date, score) values ('16-07-17', 20);" );
            db.execSQL( "insert into " + TABLE_NAME + "(date, score) values ('16-07-21', 200);" );
            db.execSQL( "insert into " + TABLE_NAME + "(date, score) values ('16-07-10', 150);" );
            db.execSQL( "insert into " + TABLE_NAME + "(date, score) values ('16-07-17', 20);" );
            db.execSQL( "insert into " + TABLE_NAME + "(date, score) values ('16-707-21', 200);" );
            db.execSQL( "insert into " + TABLE_NAME + "(date, score) values ('16-07-10', 150);" );
            db.execSQL( "insert into " + TABLE_NAME + "(date, score) values ('16-07-17', 20);" );
            db.execSQL( "insert into " + TABLE_NAME + "(date, score) values ('16-07-21', 200);" );
            db.execSQL( "insert into " + TABLE_NAME + "(date, score) values ('16-07-10', 150);" );
            db.execSQL( "insert into " + TABLE_NAME + "(date, score) values ('16-07-17', 20);" );
            db.execSQL( "insert into " + TABLE_NAME + "(date, score) values ('16-07-21', 2070);" );
            db.execSQL( "insert into " + TABLE_NAME + "(date, score) values ('16-07-10', 170);" );
            db.execSQL( "insert into " + TABLE_NAME + "(date, score) values ('16-07-17', 20);" );
            db.execSQL( "insert into " + TABLE_NAME + "(date, score) values ('16-07-21', 200);" );
            db.execSQL( "insert into " + TABLE_NAME + "(date, score) values ('16-07-10', 1570);" );
            db.execSQL( "insert into " + TABLE_NAME + "(date, score) values ('16-07-17', 20);" );
            db.execSQL( "insert into " + TABLE_NAME + "(date, score) values ('16-07-21', 200);" );
            db.execSQL( "insert into " + TABLE_NAME + "(date, score) values ('16-07-10', 1570);" );
            db.execSQL( "insert into " + TABLE_NAME + "(date, score) values ('16-07-17', 270);" );
            db.execSQL( "insert into " + TABLE_NAME + "(date, score) values ('16-07-721', 200);" );
            db.execSQL( "insert into " + TABLE_NAME + "(date, score) values ('16-077-10', 1750);" );
            db.execSQL( "insert into " + TABLE_NAME + "(date, score) values ('16-07-17', 20);" );
        } catch(Exception ex) {
            Log.e(TAG, "Exception in insert SQL", ex);
        }

    }

    public void onOpen(SQLiteDatabase db) {
        println("opened database [" + DATABASE_NAME + "].");

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "Upgrading database from version " + oldVersion +
                " to " + newVersion + ".");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void println(String msg) {
        Log.d(TAG, msg);
    }
}
