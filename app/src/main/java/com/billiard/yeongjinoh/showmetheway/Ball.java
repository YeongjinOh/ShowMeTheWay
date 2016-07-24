package com.billiard.yeongjinoh.showmetheway;

import android.graphics.Paint;

/**
 * Created by yeongjinoh on 2016-07-18.
 */
public class Ball {

    // color paint
    private Paint paint;

    // radius
    private float r;

    // position
    private float x;
    private float y;

    // velocity
    private float vx;
    private float vy;


    // constructors
    public Ball (Paint paint, float r, float x, float y) {
        this.paint = paint;
        this.r = r;
        this.x = x;
        this.y = y;
        vx = 0;
        vy = 0;
    }
    public Ball (Paint paint, float r, float x, float y, float vx, float vy) {
        this.paint = paint;
        this.r = r;
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
    }

    // get and set methos for all variables
    public void setPaint(Paint paint) {
        this.paint = paint;
    }
    public Paint getPaint() {
        return paint;
    }
    public void setR(float r) {
        this.r = r;
    }
    public float getR() {
        return r;
    }
    public void setX(float x) {
        this.x = x;
    }
    public float getX() {
        return x;
    }
    public void setY(float y) {
        this.y = y;
    }
    public float getY() {
        return y;
    }
    public void setVx(float vx) {
        this.vx = vx;
    }
    public float getVx() {
        return vx;
    }
    public void setVy(float vy) {
        this.vy = vy;
    }
    public float getVy() {
        return vy;
    }
}