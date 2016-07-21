package com.android.yeongjinoh.showmetheway;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

                // get user inputs from EditText objects
                EditText textUsername = (EditText) findViewById(R.id.usernameEntry);
                EditText textEmailaddress = (EditText) findViewById(R.id.emailaddressEntry);
                String username = textUsername.getText().toString();
                String emailaddress = textEmailaddress.getText().toString();

                // save them into SharedPreferences
                SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("username", username);
                editor.putString("emailaddress",emailaddress);
                editor.commit();
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
