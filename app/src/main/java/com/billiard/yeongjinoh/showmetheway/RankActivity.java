package com.billiard.yeongjinoh.showmetheway;

import android.app.Activity;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

    // user information
    private String email;
    private String username;
    private int score;

    // web server
    private static String URL = "http://143.248.179.147/yj/scores/insert";
    private String output;

    // keys for json
    List<User> userlist = new ArrayList<User>();
    private final String KEY1 = "email";
    private final String KEY2 = "name";
    private final String KEY3 = "score";

    // adapter to draw user list
    UserListAdapter adapter;
    ListView rankView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);

        // read sharedPreferences
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        username = pref.getString("username", "");
        email = pref.getString("emailaddress", "");

        // initialize adapter and rankView
        adapter = new UserListAdapter(this, email);
        rankView = (ListView) findViewById(R.id.listViewRank);

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
        new Connection().execute();

        Button buttonBack = (Button) findViewById(R.id.btnRankBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void ShowRank() {

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

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                rankView.setAdapter(adapter);
            }
        });
    }

    private void GetUserLists() {
        try {
            JSONObject jObj = new JSONObject(output);
            JSONArray jUsers = jObj.getJSONArray("User");
            for(int i=0; i < jUsers.length(); i++){
                JSONObject jUser = jUsers.getJSONObject(i);
                User user = new User(jUser.getString(KEY1), jUser.getString(KEY2), jUser.getInt(KEY3));
                userlist.add(user);
            }
        } catch (JSONException e) {
            Log.e("Rank", "JSON exception", e);
        } catch (Exception e) {
            Log.e("Rank", "Exception", e);
        }
    }

    private void ConnectionFails () {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(),"인터넷에 연결할 수 없습니다.", Toast.LENGTH_LONG).show();
            }
        });
    }


    private class Connection extends AsyncTask {
        @Override
        protected Object doInBackground(Object... arg0) {
            connect();
            return null;
        }
    }

    private void connect() {
        try {

            DefaultHttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(URL);
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("email",email));
            params.add(new BasicNameValuePair("name",username));
            params.add(new BasicNameValuePair("score",Integer.toString(score)));
            post.setEntity(new UrlEncodedFormEntity(params, "utf-8"));

            HttpResponse response = client.execute(post);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                StringBuilder str = new StringBuilder();
                while((line = reader.readLine()) != null) {
                    str.append(line);
                }
                output = str.toString();
                GetUserLists();
                ShowRank();
            } else {
                ConnectionFails();
            }
        } catch (IOException e) {
            Log.d("HTTPCLIENT", e.getLocalizedMessage());
            ConnectionFails();
        }
    }
}
