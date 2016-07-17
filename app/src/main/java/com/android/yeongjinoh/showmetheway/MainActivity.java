package com.android.yeongjinoh.showmetheway;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        // initailize main buttons
        Button buttonMainStart = (Button) findViewById(R.id.btnMainStart);
        Button buttonMainEnd = (Button) findViewById(R.id.btnMainEnd);

        // set on click on the end button
        buttonMainEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
