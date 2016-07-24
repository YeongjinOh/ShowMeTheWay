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
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yeongjinoh on 2016-07-22.
 */
public class RankActivity extends Activity {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private static String TABLE_NAME = "score";
    private String email;
    private String username;
    public static String URL = "http://52.78.84.120:5000/scores/";

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
        c1.moveToNext();
        int score = c1.getInt(0);

        TextView scoreView = (TextView) findViewById(R.id.scoreViewRank);
        scoreView.setText("My score : " + Integer.toString(score) + " " + username + " " + email);

        // send my score to ranking server
        SendScoreByHttp(score);

        Button buttonBack = (Button) findViewById(R.id.btnRankBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void SendScoreByHttp(int score) {

        DefaultHttpClient client = new DefaultHttpClient();
        try {

            HttpPost post = new HttpPost(URL+"insert");
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
