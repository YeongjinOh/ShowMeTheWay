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

/**
 * Created by yeongjinoh on 2016-07-20.
 */
public class ScoreHistoryActivity extends Activity {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private static String TABLE_NAME = "score";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scorehistory);

        ListView scoreListView = (ListView) findViewById(R.id.listViewScore);

        boolean isOpen = openDatabase();
        if (isOpen) {
            Cursor cursor = executeRawQueryParam();
            startManagingCursor(cursor);

            String[] columns = new String[] {"_id", "score", "date", "time"};
            int[] to = new int[] { R.id.rank_entry, R.id.score_entry, R.id.date_entry, R.id.time_entry};
            SimpleCursorAdapter mAdapter = new SimpleCursorAdapter(this, R.layout.score_list_item, cursor, columns, to);

            scoreListView.setAdapter(mAdapter);
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

    private Cursor executeRawQueryParam() {
        dbHelper.println("\nexecuteRawQueryParam called.\n");

        String SQL = "select _id, score, date, time "
                + " from " + TABLE_NAME
                + " order by score desc";
        Cursor c1 = db.rawQuery(SQL, null);

        return c1;
    }


}
