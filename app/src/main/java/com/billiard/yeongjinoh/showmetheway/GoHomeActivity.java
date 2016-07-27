package com.billiard.yeongjinoh.showmetheway;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


/**
 * Created by yeongjinoh on 2016-07-21.
 */
public class GoHomeActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gohome);

        Button buttonGameoverNo = (Button) findViewById(R.id.btnGohomeNo);
        Button buttonGameoverYes = (Button) findViewById(R.id.btnGohomeYes);

        buttonGameoverYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent();
                setResult(RESULT_CANCELED, resultIntent);
                finish();
            }
        });


        buttonGameoverNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent();
                setResult(RESULT_OK, resultIntent);
                finish();

            }
        });



    }
}
