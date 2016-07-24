package com.billiard.yeongjinoh.showmetheway;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



/**
 * Created by yeongjinoh on 2016-07-21.
 */
public class AccountSetActivity extends Activity {

    public SharedPreferences pref;
    public String username;
    public String emailaddress;
    public final String USER_NAME = "username";
    public final String EMAIL_ADDRESS = "emailaddress";
    public EditText textUsername;
    public EditText textEmailaddress;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accountset);

        // show stored strings in EditText
        pref = getSharedPreferences("pref", MODE_PRIVATE);
        username = pref.getString(USER_NAME,"");
        emailaddress = pref.getString(EMAIL_ADDRESS,"");
        textUsername = (EditText) findViewById(R.id.usernameEntry);
        textEmailaddress = (EditText) findViewById(R.id.emailaddressEntry);
        textUsername.setText(username);
        textEmailaddress.setText(emailaddress);

        Button buttonSave = (Button) findViewById(R.id.btnSave);
        Button buttonCancel = (Button) findViewById(R.id.btnCancel);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // get user inputs from EditText objects
                username = textUsername.getText().toString();
                emailaddress = textEmailaddress.getText().toString();

                // save them into SharedPreferences
                SharedPreferences.Editor editor = pref.edit();
                editor.putString(USER_NAME, username);
                editor.putString(EMAIL_ADDRESS,emailaddress);
                editor.commit();
                if (username.equals("") || emailaddress.equals("")) {
                    Toast.makeText(getApplicationContext(),"이름과 이메일 주소를 모두 입력해주세요.",Toast.LENGTH_LONG).show();
                } else {
                    finish();
                    Intent intent = new Intent(getApplicationContext(), RankActivity.class);
                    startActivity(intent);
                }
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
