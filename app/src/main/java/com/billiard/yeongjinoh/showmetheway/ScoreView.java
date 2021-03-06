package com.billiard.yeongjinoh.showmetheway;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * Created by yeongjinoh on 2016-07-22.
 */
public class ScoreView extends LinearLayout {

    TextView rank;
    TextView score;
    TextView date;
    TextView time;


    public ScoreView(Context context, int position, ScoreItem scoreItem) {
        super(context);

        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.score_list_item, this, true);

        rank = (TextView) findViewById(R.id.rank_entry);
        score = (TextView) findViewById(R.id.score_entry);
        date = (TextView) findViewById(R.id.date_entry);
        time = (TextView) findViewById(R.id.time_entry);

        setItems(position, scoreItem);
    }

    public void setItems(int position, ScoreItem scoreItem) {
        rank.setText(Integer.toString(position+1));
        score.setText(Integer.toString(scoreItem.getScore()));
        date.setText(scoreItem.getDate());
        time.setText(scoreItem.getTime());
    }
}
