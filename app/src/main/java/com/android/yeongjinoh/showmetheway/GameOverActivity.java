package com.android.yeongjinoh.showmetheway;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by yeongjinoh on 2016-07-21.
 */
public class GameOverActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameover);
        TextView gameoverScore = (TextView) findViewById(R.id.textGameoverScore);
        Intent intent = getIntent();
        int score = intent.getExtras().getInt("score");
        gameoverScore.setText(Integer.toString(score));

        Button buttonGameoverRetry = (Button) findViewById(R.id.btnGameoverRetry);
        Button buttonGameoverExit = (Button) findViewById(R.id.btnGameoverExit);
        buttonGameoverRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent();
                setResult(RESULT_OK, resultIntent);
                finish();

            }
        });

        buttonGameoverExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent();
                setResult(RESULT_CANCELED, resultIntent);
                finish();
            }
        });


    }
}
