package com.android.yeongjinoh.showmetheway;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yeongjinoh on 2016-07-24.
 */
public class UserListAdapter extends BaseAdapter {
    private Context context;

    private List<User> users;

    public UserListAdapter(Context context) {
        this.context = context;
        users = new ArrayList<User>();
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
        if (convertView == null) {
            userView = new UserView(context, position, users.get(position));
        } else {
            userView = (UserView) convertView;
            userView.setItems(position, users.get(position));
        }

        return userView;
    }
}
