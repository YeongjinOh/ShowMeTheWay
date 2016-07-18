package com.android.yeongjinoh.showmetheway;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;
import java.util.ArrayList;

/**
 * Created by yeongjinoh on 2016-07-18.
 */
public class BillardTableView extends ImageView {

    private ArrayList<Ball> balls;

    // default setting for billiard ball and table
    private final float radius = 50;
    private final float width = 1224;
    private final float height = 2448;
    private float scaleFactor;
    private final float dt = 0.01F;
    private final float margin = 70.0F;
    private final float surfaceFrictionalRatio = 0.02F;


    Bitmap table;

    public BillardTableView(Context context, AttributeSet attrs) {

        super(context, attrs);

        init();

        // get width and height of the background image
        float w, h;
        table = BitmapFactory.decodeResource(context.getResources(), R.drawable.billiard_table);
        w = table.getWidth();
        h = table.getHeight();

        // get scaleFactor
        if (w*2 < h) {
            scaleFactor = h/(height+2*margin);
            w = h/2;
        } else {
            scaleFactor = w/(width+2*margin);
            h = w*2;
        }

        new UpdateThread().start();
    }

    private void init() {

        // initialize paint
        Paint white = new Paint();
        white.setColor(Color.WHITE);
        Paint yellow = new Paint();
        yellow.setColor(Color.YELLOW);
        Paint red = new Paint();
        red.setColor(Color.RED);

        // initialize balls
        balls = new ArrayList<Ball>();
        Ball whiteBall = new Ball(white,radius,radius,radius,400,500);
        Ball yellowBall = new Ball(yellow,radius,radius,height-2*radius,1000,200);
        Ball redBall = new Ball(red,radius,width-radius,height-radius,-1000,-400);
        balls.add(whiteBall);
        balls.add(yellowBall);
        balls.add(redBall);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //canvas.drawBitmap(table,0,0,null);
        drawBalls(canvas);
    }

    private void drawBalls (Canvas canvas) {
        for (int i=0; i<balls.size(); i++) {
            Ball curBall = balls.get(i);
            canvas.drawCircle((curBall.getX()+margin)*scaleFactor, (curBall.getY()+margin)*scaleFactor, curBall.getR()*scaleFactor, curBall.getPaint());
        }
    }

    public void move() {
        for (int i=0; i<balls.size(); i++) {
            Ball ball = balls.get(i);
            float x, y, Vx, Vy;

            x = ball.getX();
            y = ball.getY();
            Vx = ball.getVx() * (1-surfaceFrictionalRatio);
            Vy = ball.getVy() * (1-surfaceFrictionalRatio);

            x += Vx*dt;
            y += Vy*dt;
            ball.setX(x);
            ball.setY(y);
            if (x < radius || x > width-radius) {
                ball.setVx(-Vx);
            }
            if (y < radius || y > height-radius) {
                ball.setVy(-Vy);
            }
        }
    }

    boolean isStart = true;

    class UpdateThread extends Thread {
        public void run() {
            while(isStart) {
                try {
                    Thread.sleep(10);
                    move();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                postInvalidate();
            }
        }
    }


}
