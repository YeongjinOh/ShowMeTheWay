package com.billiard.yeongjinoh.showmetheway;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yeongjinoh on 2016-07-22.
 */
public class ScoreListAdapter extends BaseAdapter {
    private Context context;

    private List<ScoreItem> items;

    public ScoreListAdapter(Context context) {
        this.context = context;
        items = new ArrayList<ScoreItem>();
    }

    @Override
    public int getCount() {
        return items.size();
    }


    public long getItemId(int position) {
        return position;
    }
    public ScoreItem getItem(int position) {
        return items.get(position);
    }
    public void addItem (ScoreItem item) {
        items.add(item);
    }
    public View getView (int position, View convertView, ViewGroup parent) {
        ScoreView scoreView;
        if (convertView == null) {
            scoreView = new ScoreView(context, position, items.get(position));
        } else {
            scoreView = (ScoreView) convertView;
            scoreView.setItems(position, items.get(position));
        }

        return scoreView;
    }
}
