package com.android.yeongjinoh.showmetheway;

import android.content.Intent;
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
        //setContentView(new BillardTableView(this));

        setContentView(R.layout.activity_main);

        // initailize main buttons
        Button buttonMainStart = (Button) findViewById(R.id.btnMainStart);
        Button buttonMainEnd = (Button) findViewById(R.id.btnMainEnd);


        // set on click listener on the buttons
        buttonMainStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SimulatorActivity.class);
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
