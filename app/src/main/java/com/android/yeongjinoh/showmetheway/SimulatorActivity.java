package com.android.yeongjinoh.showmetheway;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by yeongjinoh on 2016-07-18.
 */
public class SimulatorActivity extends Activity implements ScoreUpdateListener {


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

        // set score update listener
        final BillardTableView billiardTableView = (BillardTableView) findViewById(R.id.billiardTableView);
        billiardTableView.setScoreUpdateListener(this);

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
                billiardTableView.hit();
            }
        });
    }

    @Override
    public void onScoreUpdate(int score) {
        TextView textView = (TextView) findViewById(R.id.scoreText);
        String value = "SCORE : " + Float.toString(score);
        setText(textView, value);
    }

    private void setText(final TextView text,final String value){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                text.setText(value);
            }
        });
    }

}
