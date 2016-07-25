package com.billiard.yeongjinoh.showmetheway;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;


import java.util.ArrayList;

/**
 * Created by yeongjinoh on 2016-07-18.
 */
public class BillardTableView extends ImageView implements View.OnTouchListener {

    private ArrayList<Ball> balls;

    // default setting for billiard ball and table
    private final float radius = 50;
    private final float width = 1224;
    private final float height = 2448;
    private float scaleFactor;
    private final float margin = 60.0F;

    // constants for physical system
    private final float dt = 0.01F;
    private final float maximumPower = 4000.0F;
    private final float surfaceFrictionalRatio = 0.15F;
    private final float cushionConflictChangeRatio = 0.9F;
    private final float ballConflictChangeRatio = 0.85F;

    // the other global variables
    private double angle = Math.PI/4;
    private int score;
    public boolean isStart = false;
    private Bitmap table;
    private final int DEFAULT_LIFE = 3;

    // flags to calculate score;
    private boolean hitRed1, hitRed2, hitYellow;
    private int life;
    private int stage;
    private UpdateListener UpdateListener;

    public BillardTableView(Context context, AttributeSet attrs) {

        super(context, attrs);
        setOnTouchListener(this);
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
        AddBall(white);
        AddBall(red);
        AddBall(red);
        AddBall(yellow);

        score = 0;
        life = DEFAULT_LIFE;
        stage = 1;

        new UpdateThread().start();
    }

    public void setUpdateListener(UpdateListener listener) {
        UpdateListener = listener;
    }

    public void reset() {

        // initialize paint
        Paint white = new Paint();
        white.setColor(Color.WHITE);
        Paint yellow = new Paint();
        yellow.setColor(Color.YELLOW);
        Paint red = new Paint();
        red.setColor(Color.RED);

        // reset balls
        balls.clear();
        AddBall(white);
        AddBall(red);
        AddBall(red);
        AddBall(yellow);

        score = 0;
        life = DEFAULT_LIFE;
        stage = 1;
        UpdateListener.onLifeUpdate(life,score);
        UpdateListener.onScoreUpdate(score);
    }

    public void hit(float power) {
        if (!isStart) {

            // reset flags
            hitRed1 = false;
            hitRed2 = false;
            hitYellow = false;

            Ball white = balls.get(0);
            white.setVx(-maximumPower*power*(float)Math.cos(angle));
            white.setVy(-maximumPower*power*(float)Math.sin(angle));
            isStart = true;
        }
    }

    private void drawCue (Canvas canvas) {
        // get white ball information
        Ball white = balls.get(0);
        float x = white.getX(), y = white.getY();

        // draw cue
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setARGB(200,188,143,143);
        paint.setStrokeWidth(20*scaleFactor);
        paint.setAntiAlias(true);
        canvas.drawLine(convertScale((float)(x+Math.cos(angle)*radius*1.2F)),convertScale((float)(y+Math.sin(angle)*radius*1.2F)),
                        convertScale((float)(x+Math.cos(angle)*radius*17.2F)),convertScale((float)(y+Math.sin(angle)*radius*17.2F)),paint);

        // draw ahead line
        paint.setARGB(100,255,255,255);
        paint.setStrokeWidth(5*scaleFactor);
        float xStep = (float)Math.cos(angle)*radius, yStep = (float)Math.sin(angle)*radius;
        for (int i=1; i<50; i++) {

            // draw until conflict with other ball
            if (i>3 && !checkNoConflictWithAnyBall(x-xStep*i,y-yStep*i) ) {
                break;
            }
            canvas.drawCircle(convertScale(x-xStep*i),convertScale(y-yStep*i), radius/5.0F*scaleFactor, paint);
        }

    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBalls(canvas);
        if (!isStart) {
            drawCue(canvas);
        }
    }

    private void drawBalls (Canvas canvas) {
        for (int i=0; i<balls.size(); i++) {
            Ball curBall = balls.get(i);
            canvas.drawCircle(convertScale(curBall.getX()), convertScale(curBall.getY()), curBall.getR()*scaleFactor, curBall.getPaint());
        }
    }

    private void AddBall (Paint paint) {
        float randomX, randomY;
        randomX = radius + (float)Math.random()*(width-2*radius);
        randomY = radius + (float)Math.random()*(height-2*radius);
        while (!checkNoConflictWithAnyBall(randomX, randomY)) {
            randomX = radius + (float)Math.random()*(width-radius);
            randomY = radius + (float)Math.random()*(height-radius);
        }
        // now, given random values doesn't make conflict with any other ball.
        paint.setAntiAlias(true);
        balls.add(new Ball(paint, radius, randomX, randomY));
    }

    public void move() {

        for (int i=0; i<balls.size(); i++) {
            Ball ball = balls.get(i);
            float x, y, Vx, Vy, Vnorm;

            x = ball.getX();
            y = ball.getY();
            Vx = ball.getVx();
            Vy = ball.getVy();

            // update velocity
            Vnorm = norm(Vx, Vy);
            if (Vnorm < 10.0F) {
                Vx = 0;
                Vy = 0;
            }  else if (Vnorm < 30.0F) {
                Vx = Vx*0.98F;
                Vy = Vy*0.98F;
            }  else if (Vnorm < 500.0F) {
                Vx = Vx * (1 - surfaceFrictionalRatio*dt) * (Vnorm*2/500.0F  + 98.0F)/100.0F;
                Vy = Vy * (1 - surfaceFrictionalRatio*dt) * (Vnorm*2/500.0F  + 98.0F)/100.0F;
            }  else {
                Vx = Vx * (1 - surfaceFrictionalRatio*dt);
                Vy = Vy * (1 - surfaceFrictionalRatio*dt);
            }

            // update position
            x += Vx*dt;
            y += Vy*dt;

            // assign updated values
            ball.setX(x);
            ball.setY(y);
            ball.setVx(Vx);
            ball.setVy(Vy);

            // check conflict with cushion
            if ((x < radius && Vx < 0)|| (x > width-radius && Vx > 0)) {
                ball.setVx(-Vx*cushionConflictChangeRatio);
            }
            if ((y < radius && Vy < 0) || (y > height-radius && Vy > 0)) {
                ball.setVy(-Vy*cushionConflictChangeRatio);
            }
        }

        // check conflict between two balls
        for (int i=1; i<balls.size(); i++) {
            for (int j=0; j<i; j++) {
                if (checkConflict(i, j)) {

                    // if conflict, change direction
                    double theta, thetaI, thetaJ;
                    Ball ballI = balls.get(i), ballJ = balls.get(j);
                    float xI = ballI.getX(), yI = ballI.getY(), xJ = ballJ.getX(), yJ = ballJ.getY();
                    float vxI = ballI.getVx(), vyI = ballI.getVy(), vxJ = ballJ.getVx(), vyJ = ballJ.getVy();

                    if (j == 0) {

                        // update flags
                        if (i == 1) {
                            hitRed1 = true;
                        } else if (i == 2) {
                            hitRed2 = true;
                        } else {
                            hitYellow = true;
                        }
                    }

                    // get angles using arctan
                    theta = Math.atan2(yJ-yI, xJ-xI);
                    thetaI = Math.atan2(vyI, vxI);
                    thetaJ = Math.atan2(vyJ, vxJ);

                    // set new velocity
                    ballI.setVx((float)(norm(vxJ,vyJ)*Math.cos(thetaJ-theta)*Math.cos(theta)
                            - norm(vxI,vyI)*Math.sin(thetaI-theta)*Math.sin(theta))*ballConflictChangeRatio);
                    ballI.setVy((float)(norm(vxJ,vyJ)*Math.cos(thetaJ-theta)*Math.sin(theta)
                            + norm(vxI,vyI)*Math.sin(thetaI-theta)*Math.cos(theta))*ballConflictChangeRatio);
                    ballJ.setVx((float)(norm(vxI,vyI)*Math.cos(thetaI-theta)*Math.cos(theta)
                            - norm(vxJ,vyJ)*Math.sin(thetaJ-theta)*Math.sin(theta))*ballConflictChangeRatio);
                    ballJ.setVy((float)(norm(vxI,vyI)*Math.cos(thetaI-theta)*Math.sin(theta)
                            + norm(vxJ,vyJ)*Math.sin(thetaJ-theta)*Math.cos(theta))*ballConflictChangeRatio);

                }
            }
        }

    }

    // check if the given position with default radius doesn't have conflict with any other balls
    private boolean checkNoConflictWithAnyBall (float x, float y) {
        for (int i=0; i<balls.size(); i++) {
            Ball ball = balls.get(i);
            if (norm(x-ball.getX(), y-ball.getY()) < radius + ball.getR()) {
                return false;
            }
        }
        return true;
    }

    // check conflict between two balls
    private boolean checkConflict(int i, int j) {
        Ball iBall, jBall;
        iBall = balls.get(i);
        jBall = balls.get(j);
        float xi = iBall.getX(), yi = iBall.getY(), xj = jBall.getX(), yj = jBall.getY();
        xi += iBall.getVx()*dt;
        yi += iBall.getVy()*dt;
        xj += jBall.getVx()*dt;
        yj += jBall.getVy()*dt;

        return (norm(xi-xj, yi-yj) < iBall.getR() + jBall.getR());
    }

    // calculate l2-norm
    private float norm (float a, float b) {
        return (float)Math.sqrt((double)(a*a + b*b));
    }

    // check if all balls stop
    public boolean checkAllStop() {
        for (int i=0; i<balls.size(); i++) {
            if (norm (balls.get(i).getVx(), balls.get(i).getVy()) > 1.0F) {
                return false;
            }
        }
        return true;
    }

    // this method is called at the last time.
    private void updateScore() {
        if (hitYellow || !(hitRed1 || hitRed2)) {
            life--;
            UpdateListener.onLifeUpdate(life,score);
        } else if (hitRed1 && hitRed2) {
            score += (10 * (int)Math.pow(2,stage-1));
        }
        UpdateListener.onScoreUpdate(score);
        stage++;

        if (life == 0) {
            reset();
        }
    }

    float convertScale (float billiard) {
        return (billiard+margin)*scaleFactor;
    }

    // use touch event for cue angle adjusting
    private float prevAngle, curAngle;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Ball white = balls.get(0);
        float x = convertScale(white.getX()), y = convertScale(white.getY());

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                prevAngle = (float)Math.atan2(event.getY()-y, event.getX()-x);
                break;

            case MotionEvent.ACTION_MOVE:

                curAngle = (float)Math.atan2(event.getY()-y,event.getX()-x);
                angle += curAngle-prevAngle;
                prevAngle = curAngle;
                postInvalidate();


                break;

        }

        return true;
    }

    // update thread to make balls move
    class UpdateThread extends Thread {
        public void run() {
            int cnt = 0;

            while(!isStart){};
            while(isStart) {
                try {
                    Thread.sleep((long)(1000*dt));
                    move();

                    if (checkAllStop() || cnt*dt > 50) {
                        cnt = 0;
                        Paint yellow = new Paint();
                        yellow.setColor(Color.YELLOW);
                        AddBall(yellow);
                        isStart = false;
                        updateScore();
                        postInvalidate();
                        while(!isStart){}
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                postInvalidate();
                cnt++;
            }
        }
    }


}
