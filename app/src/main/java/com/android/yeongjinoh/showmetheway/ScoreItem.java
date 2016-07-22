package com.android.yeongjinoh.showmetheway;

/**
 * Created by yeongjinoh on 2016-07-22.
 */
public class ScoreItem {

    private int rank;
    private int score;
    private String date;
    private String time;

    public ScoreItem(int rank, int score, String date, String time) {
        this.rank = rank;
        this.score = score;
        this.date = date;
        this.time = time;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getRank() {
        return rank;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }
}
