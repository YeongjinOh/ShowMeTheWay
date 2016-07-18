package com.android.yeongjinoh.showmetheway;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by yeongjinoh on 2016-07-18.
 */
public class SimulatorActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulation);

        final BillardTableView billardTableView = (BillardTableView) findViewById(R.id.billiardTableView);

        Button buttonSimulMenu = (Button) findViewById(R.id.btnSimulMenu);
        buttonSimulMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button buttonSimulHit = (Button) findViewById(R.id.btnSimulHit);
        buttonSimulHit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                billardTableView.hit();
            }
        });
    }
}
