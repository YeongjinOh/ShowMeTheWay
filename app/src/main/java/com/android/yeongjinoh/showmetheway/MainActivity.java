package com.android.yeongjinoh.showmetheway;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    CallbackManager callbackManager;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private static String TABLE_NAME2 = "time";
    private boolean isOpen;

    // user access information to store into database
    SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
    private String login;
    private String lastgame;
    private String logout;

    // request code for simulator activity
    public static final int REQUEST_CODE_SIMUL = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        // open database to save login time
        isOpen = openDatabase();

        // get login time
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        login = timeFormat.format(date);
        lastgame = null;

        // initialize facebook
        FacebookSdk.sdkInitialize(getApplicationContext());

        // set content view
        setContentView(R.layout.activity_main);

        // initailize main buttons
        Button buttonMainStart = (Button) findViewById(R.id.btnMainStart);
        Button buttonMainRank = (Button) findViewById(R.id.btnMainRank);
        Button buttonMainAccount = (Button) findViewById(R.id.btnAboutGame);
        Button buttonMainEnd = (Button) findViewById(R.id.btnMainEnd);


        // set on click listener on the buttons
        buttonMainStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), SimulatorActivity.class);
            startActivityForResult(intent, REQUEST_CODE_SIMUL);

            }
        });

        buttonMainRank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ScoreHistoryActivity.class);
                startActivity(intent);
            }
        });

        buttonMainAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GameRuleActivity.class);
                startActivity(intent);
            }
        });

        buttonMainEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // setting for facebook
        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) findViewById(R.id.btnFacebookLogin);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "로그인 취소", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(), "로그인 에러", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // get logout time
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        logout = timeFormat.format(date);

        // store user access times into database
        insertTime(db, login, lastgame, logout);

        dbHelper.close();
    }

    public void insertTime(SQLiteDatabase db, String login, String lastgame, String logout) {
        if (isOpen) {
            dbHelper.println("inserting records.");
            try {
                String query = String.format("INSERT INTO %s (login, lastgame, logout) VALUES ('%s', '%s', '%s');", TABLE_NAME2, login, lastgame, logout);
                db.execSQL(query);
            } catch (Exception ex) {
                Log.e("SimulatorActivity", "Exception in insert SQL", ex);
                ex.printStackTrace();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // for simulator activity
        if (requestCode == REQUEST_CODE_SIMUL) {
            if (resultCode == RESULT_OK) {
                lastgame = data.getStringExtra("lastgame");
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private boolean openDatabase() {
        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();
        return true;
    }
}
