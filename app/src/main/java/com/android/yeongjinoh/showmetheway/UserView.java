package com.android.yeongjinoh.showmetheway;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by yeongjinoh on 2016-07-24.
 */
public class UserView extends LinearLayout {

    TextView rank;
    TextView username;
    TextView score;


    public UserView(Context context, int position, User user) {
        super(context);

        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.user_list, this, true);

        rank = (TextView) findViewById(R.id.userrank_entry);
        score = (TextView) findViewById(R.id.userscore_entry);
        username = (TextView) findViewById(R.id.username_entry);

        setItems(position, user);
    }

    public void setItems(int position, User user) {
        rank.setText(Integer.toString(position+1));
        score.setText(Integer.toString(user.getScore()));
        username.setText(user.getName());
    }
}
