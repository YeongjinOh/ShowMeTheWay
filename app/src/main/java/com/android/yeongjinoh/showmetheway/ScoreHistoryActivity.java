package com.android.yeongjinoh.showmetheway;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yeongjinoh on 2016-07-20.
 */
public class ScoreHistoryActivity extends Activity {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private static String TABLE_NAME = "score";
    private List<ScoreItem> scoreItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scorehistory);

        // initialize
        ListView scoreListView = (ListView) findViewById(R.id.listViewScore);
        scoreItems = new ArrayList<ScoreItem>();

        // read database
        boolean isOpen = openDatabase();
        if (isOpen) {
            executeRawQueryParam();
            ScoreListAdapter adapter = new ScoreListAdapter(this);
            for (int i=0; i<scoreItems.size(); i++) {
                adapter.addItem(scoreItems.get(i));
            }
            scoreListView.setAdapter(adapter);
        }

        // menu button
        Button buttonScoreMenu = (Button) findViewById(R.id.btnScoreMenu);
        buttonScoreMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // rank button
        Button buttonScoreRank = (Button) findViewById(R.id.btnScoreRank);
        buttonScoreRank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
                String username = pref.getString("username", "");
                String emailaddress = pref.getString("emailaddress", "");
                if (username.equals("") || emailaddress.equals("")) {
                    Intent intent = new Intent(getApplicationContext(), AccountSetActivity.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(),"사용자 정보를 입력해주세요.",Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(getApplicationContext(), RankActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.close();
    }

    private boolean openDatabase() {
        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();
        return true;
    }

    // this method read all records and store them into list of ScoreItem
    private void executeRawQueryParam() {
        dbHelper.println("\nexecuteRawQueryParam called.\n");

        String SQL = "select score, date, time "
                + " from " + TABLE_NAME
                + " order by score desc";
        Cursor c1 = db.rawQuery(SQL, null);

        // read all datas and store into the list
        for (int i=0; i < c1.getCount(); i++) {
            c1.moveToNext();
            int score = c1.getInt(0);
            String date = c1.getString(1);
            String time = c1.getString(2);
            scoreItems.add(new ScoreItem(score, date, time));
        }

        // close cursor
        c1.close();
    }


}
