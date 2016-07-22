package com.android.yeongjinoh.showmetheway;

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

    private List<ScoreItem> items = new ArrayList<ScoreItem>();

    public ScoreListAdapter(Context context) {
        this.context = context;
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

    public View getView (int position, View convertView, ViewGroup parent) {
        ScoreView scoreView;
        if (convertView == null) {
            scoreView = new ScoreView(context,items.get(position));
        } else {
            scoreView = (ScoreView) convertView;
            scoreView.setItems(items.get(position));
        }

        return scoreView;
    }
}
