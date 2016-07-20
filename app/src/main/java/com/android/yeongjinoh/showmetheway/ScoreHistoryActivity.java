package com.android.yeongjinoh.showmetheway;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by yeongjinoh on 2016-07-20.
 */
public class ScoreHistoryActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scorehistory);

        Button buttonScoreMenu = (Button) findViewById(R.id.btnScoreMenu);
        buttonScoreMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
