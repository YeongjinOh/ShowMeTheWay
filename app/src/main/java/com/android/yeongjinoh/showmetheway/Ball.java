package com.android.yeongjinoh.showmetheway;

/**
 * Created by yeongjinoh on 2016-07-18.
 */
public class Ball {

    private int index;

    // radius
    private double r;

    // position
    private double x;
    private double y;

    // velocity
    private double vx;
    private double vy;


    // constructors
    public Ball (int index) {
        this.index = index;
    }
    public Ball (int index, double r) {
        this.index = index;
        this.r = r;
    }
    public Ball (int index, double r, double x, double y) {
        this.index = index;
        this.r = r;
        this.x = x;
        this.y = y;
    }

    // get and set methos for all variables
    public void setIndex(int index) {
        this.index = index;
    }
    public int getIndex() {
        return index;
    }
    public void setR(double r) {
        this.r = r;
    }
    public double getR() {
        return r;
    }
    public void setX(double x) {
        this.x = x;
    }
    public double getX() {
        return x;
    }
    public void setY(double y) {
        this.y = y;
    }
    public double getY() {
        return y;
    }
    public void setVx(double vx) {
        this.vx = vx;
    }
    public double getVx() {
        return vx;
    }
    public void setVy(double vy) {
        this.vy = vy;
    }
    public double getVy() {
        return vy;
    }
}
