package com.android.yeongjinoh.showmetheway;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by yeongjinoh on 2016-07-21.
 */
public class AccountSetActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accountset);
        Button buttonSave = (Button) findViewById(R.id.btnSave);
        Button buttonCancel = (Button) findViewById(R.id.btnCancel);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
