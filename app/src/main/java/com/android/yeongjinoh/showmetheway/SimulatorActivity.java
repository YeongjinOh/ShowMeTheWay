package com.android.yeongjinoh.showmetheway;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.BitmapDrawable;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by yeongjinoh on 2016-07-18.
 */
public class SimulatorActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulation);

        // set background image
        Resources resources = getResources();
        BitmapDrawable background = (BitmapDrawable) resources.getDrawable(R.drawable.tile);
        ImageView imageView = (ImageView) findViewById(R.id.imgSimulBackground);
        imageView.setImageDrawable(background);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        final BillardTableView billardTableView = (BillardTableView) findViewById(R.id.billiardTableView);

        // set menu button
        Button buttonSimulMenu = (Button) findViewById(R.id.btnSimulMenu);
        buttonSimulMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // set hit button
        Button buttonSimulHit = (Button) findViewById(R.id.btnSimulHit);
        buttonSimulHit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateScore();
                billardTableView.hit();
            }
        });
    }

    public void updateScore () {
        SharedPreferences scorePrefs = getSharedPreferences("score", MODE_PRIVATE);
        if (scorePrefs != null && scorePrefs.contains("score")) {
            TextView textView = (TextView) findViewById(R.id.scoreText);
            float score = scorePrefs.getFloat("score",-1);
            textView.setText("SCORE : "+Float.toString(score));
        }
    }

}
