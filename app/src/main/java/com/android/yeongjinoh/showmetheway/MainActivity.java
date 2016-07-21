package com.android.yeongjinoh.showmetheway;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        // set content view
        setContentView(R.layout.activity_main);

        // initailize main buttons
        Button buttonMainStart = (Button) findViewById(R.id.btnMainStart);
        Button buttonMainRank = (Button) findViewById(R.id.btnMainRank);
        Button buttonMainAccount = (Button) findViewById(R.id.btnMainAccount);
        Button buttonMainEnd = (Button) findViewById(R.id.btnMainEnd);


        // set on click listener on the buttons
        buttonMainStart.setOnClickListener(new View.OnClickListener() {
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
                    Intent intent = new Intent(getApplicationContext(), SimulatorActivity.class);
                    startActivity(intent);
                }
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
                Intent intent = new Intent(getApplicationContext(), AccountSetActivity.class);
                startActivity(intent);
            }
        });

        buttonMainEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



    }
}
