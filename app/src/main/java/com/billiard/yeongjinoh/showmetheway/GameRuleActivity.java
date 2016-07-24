package com.billiard.yeongjinoh.showmetheway;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;



/**
 * Created by yeongjinoh on 2016-07-22.
 */
public class GameRuleActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamerule);

        Button buttonRuleBack = (Button) findViewById(R.id.btnRuleBack);
        buttonRuleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
