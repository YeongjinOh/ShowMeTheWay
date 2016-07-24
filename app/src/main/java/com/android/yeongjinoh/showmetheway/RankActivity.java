package com.android.yeongjinoh.showmetheway;

import android.app.Activity;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by yeongjinoh on 2016-07-22.
 */
public class RankActivity extends Activity {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private static String TABLE_NAME = "score";
    private static String TABLE_NAME3 = "rank";

    // user information
    private String email;
    private String username;
    private int score;

    // web server
    public static String URL = "http://143.248.179.147/yj/scores/";

    // keys for json
    List<User> userlist = new ArrayList<User>();
    private final String KEY1 = "email";
    private final String KEY2 = "username";
    private final String KEY3 = "score";
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);

        // read sharedPreferences
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        username = pref.getString("username", "");
        email = pref.getString("emailaddress", "");

        // get my best score
        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();
        String SQL = "select score "
                + " from " + TABLE_NAME
                + " order by score desc";
        Cursor c1 = db.rawQuery(SQL, null);
        int numScores = c1.getCount();
        if (numScores < 1) {
            score = 0;
        } else {
            c1.moveToNext();
            score = c1.getInt(0);
        }
        TextView scoreView = (TextView) findViewById(R.id.scoreViewRank);
        scoreView.setText("My score : " + Integer.toString(score));

        // send my score to ranking server
        SendScoreByHttp(score);
        refresh();
        showRank();

        Button buttonBack = (Button) findViewById(R.id.btnRankBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void showRank() {
        UserListAdapter adapter = new UserListAdapter(this, email);
        ListView rankView = (ListView) findViewById(R.id.listViewRank);

        // sort before showing
        Collections.sort(userlist, new Comparator<User>() {
            @Override
            public int compare(User user, User compareUser) {
                return compareUser.getScore() - user.getScore();
            }
        });

        // show all user scores
        for (int i=0; i<userlist.size(); i++) {
            adapter.addItem(userlist.get(i));
        }
        rankView.setAdapter(adapter);
    }

    private void refresh() {
        String str =
                "{'User':"+
                        String.format("[{'%s':'sample1@gmail.com','%s':'Raccoon','%s':1420},", KEY1, KEY2, KEY3) +
                        String.format("{'%s':'sample2@naver.com','%s':'전민영','%s':10},", KEY1, KEY2, KEY3) +
                        String.format("{'%s':'sample3@snu.ac.kr','%s':'Sungmin Oh','%s':4810},", KEY1, KEY2, KEY3) +
                        String.format("{'%s':'sample4@gmail.com','%s':'Hyungmin','%s':0},", KEY1, KEY2, KEY3) +
                        String.format("{'%s':'sample5@hanmail.net','%s':'주희재','%s':1120}]}", KEY1, KEY2, KEY3);

        boolean flagInsert = true;

        try {
            JSONObject jObj = new JSONObject(str);
            JSONArray jUsers = jObj.getJSONArray("User");
            for(int i=0; i < jUsers.length(); i++){
                JSONObject jUser = jUsers.getJSONObject(i);
                User user = new User(jUser.getString(KEY1), jUser.getString(KEY2), jUser.getInt(KEY3));
                userlist.add(user);
                String jEmail = jUser.getString(KEY1);
                if (email.equals(jEmail)) {
                    flagInsert = false;
                }
                /*
                String query = String.format("INSERT INTO %s (email, username, score) VALUES ('%s', '%s', %s);", TABLE_NAME3,
                        user.getString("email"), user.get("username"), user.getInt("score"));
                db.execSQL(query);
                */
            }
        } catch (JSONException e) {
            Log.e("Rank", "JSON exception");
//        } catch (Exception e) {
//            Log.e("Rank", "Exception in insert SQL", e);
        }

        if (flagInsert) {
            User user = new User(email, username, score);
            userlist.add(user);
        }
    }


    private void SendScoreByHttp(int score) {

        DefaultHttpClient client = new DefaultHttpClient();
        try {


            HttpPost post = new HttpPost(URL+"insert/");
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("email",email));
            params.add(new BasicNameValuePair("name",username));
            params.add(new BasicNameValuePair("score",Integer.toString(score)));
            post.setEntity(new UrlEncodedFormEntity(params));

            HttpResponse response = client.execute(post);

        } catch (ClientProtocolException e) {
            Log.e("RankActivity","Client protocol exception");
        } catch (IOException e) {
            Log.e("RankActivity","IO exception");
        }
    }




}
