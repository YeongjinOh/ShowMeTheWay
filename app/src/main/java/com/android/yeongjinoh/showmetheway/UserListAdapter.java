package com.android.yeongjinoh.showmetheway;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by yeongjinoh on 2016-07-24.
 */
public class UserListAdapter extends BaseAdapter {
    private Context context;
    private String key;
    private List<User> users;

    public UserListAdapter(Context context, String key) {
        this.context = context;
        users = new ArrayList<User>();
        this.key = key;
    }

    @Override
    public int getCount() {
        return users.size();
    }


    public long getItemId(int position) {
        return position;
    }
    public User getItem(int position) {
        return users.get(position);
    }
    public void addItem (User user) {
        users.add(user);
    }
    public View getView (int position, View convertView, ViewGroup parent) {
        UserView userView;
        User user = users.get(position);
        if (convertView == null) {
            userView = new UserView(context, position, user);
        } else {
            userView = (UserView) convertView;
            userView.setItems(position, user);
        }
        if (key.equals(user.getEmail())) {
            userView.setBackgroundColor(Color.argb(187, 187, 187, 221));
        }
        return userView;
    }
}
