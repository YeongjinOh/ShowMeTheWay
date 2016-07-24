package com.android.yeongjinoh.showmetheway;

/**
 * Created by yeongjinoh on 2016-07-22.
 */
public class User {

    String email;
    String name;
    int score;

    public User (String email, String name, int score) {
        this.email = email;
        this.name = name;
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
