package com.android.yeongjinoh.showmetheway;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.Toast;

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
    private final float surfaceFrictionalRatio = 0.1F;

    private boolean isStart = true;

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
        Ball whiteBall = new Ball(white,radius,radius,5*radius,2000,-1500);
        Ball yellowBall = new Ball(yellow,radius,2*radius,height-2*radius,0.0000000001F,0);
        Ball redBall = new Ball(red,radius,width-radius,5*radius,0,0.0000000001F);
        Ball yellowBall2 = new Ball(yellow,radius, width-2*radius, height-2*radius, 0.0000000001F, 0);
        Ball redBall2 = new Ball(red,radius,width/2,5*radius,0,0.0000000001F);
        Ball yellowBall3 = new Ball(yellow,radius, width/2, height-2*radius, 0.0000000001F, 0);
        balls.add(whiteBall);
        balls.add(yellowBall);
        balls.add(redBall);
        balls.add(yellowBall2);
        balls.add(redBall2);
        balls.add(yellowBall3);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
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
            float x, y, Vx, Vy, Vnorm;

            x = ball.getX();
            y = ball.getY();

            Vx = ball.getVx();
            Vy = ball.getVy();
            Vnorm = norm(Vx, Vy);
            Vx = Vx  * (1-surfaceFrictionalRatio) * (Vnorm - 10) / Vnorm ;
            Vy = Vy  * (1-surfaceFrictionalRatio) * (Vnorm - 10) / Vnorm ;
            x += Vx*dt;
            y += Vy*dt;
            ball.setX(x);
            ball.setY(y);

            // check conflict with cushion
            if ((x < radius && Vx < 0)|| (x > width-radius && Vx > 0)) {
                ball.setVx(-Vx);
            }
            if ((y < radius && Vy < 0) || (y > height-radius && Vy > 0)) {
                ball.setVy(-Vy);
            }
        }

        for (int i=1; i<balls.size(); i++) {
            for (int j=0; j<i; j++) {
                if (checkConflict(i, j)) {

                    // if conflict, change direction
                    double theta, thetaI, thetaJ;
                    Ball ballI = balls.get(i), ballJ = balls.get(j);
                    float xI = ballI.getX(), yI = ballI.getY(), xJ = ballJ.getX(), yJ = ballJ.getY();
                    float vxI = ballI.getVx(), vyI = ballI.getVy(), vxJ = ballJ.getVx(), vyJ = ballJ.getVy();

                    // get angles using arctan
                    theta = Math.atan2(yJ-yI, xJ-xI);
                    thetaI = Math.atan2(vyI, vxI);
                    thetaJ = Math.atan2(vyJ, vxJ);

                    // set new velocity
                    ballI.setVx((float)((norm(vxJ,vyJ)*Math.cos(thetaJ-theta)*Math.cos(theta)
                            - norm(vxI,vyI)*Math.sin(thetaI-theta)*Math.sin(theta))*(1+surfaceFrictionalRatio)));
                    ballI.setVy((float)((norm(vxJ,vyJ)*Math.cos(thetaJ-theta)*Math.sin(theta)
                            + norm(vxI,vyI)*Math.sin(thetaI-theta)*Math.cos(theta))*(1+surfaceFrictionalRatio)));
                    ballJ.setVx((float)((norm(vxI,vyI)*Math.cos(thetaI-theta)*Math.cos(theta)
                            - norm(vxJ,vyJ)*Math.sin(thetaJ-theta)*Math.sin(theta))*(1+surfaceFrictionalRatio)));
                    ballJ.setVy((float)((norm(vxI,vyI)*Math.cos(thetaI-theta)*Math.sin(theta)
                            + norm(vxJ,vyJ)*Math.sin(thetaJ-theta)*Math.cos(theta))*(1+surfaceFrictionalRatio)));

                }
            }
        }

    }

    private boolean checkConflict(int i, int j) {
        Ball iBall, jBall;
        iBall = balls.get(i);
        jBall = balls.get(j);
        return (norm(iBall.getX()-jBall.getX(), iBall.getY()-jBall.getY()) < 2*radius);
    }


    private float norm (float a, float b) {
        return (float)Math.sqrt((double)(a*a + b*b));
    }

    private boolean checkAllStop() {
        for (int i=0; i<balls.size(); i++) {
            if (norm (balls.get(i).getVx(), balls.get(i).getVy()) > 1) {
                return false;
            }
        }
        return true;
    }

    class UpdateThread extends Thread {
        public void run() {
            int cnt = 0;
            while(isStart && cnt < 500000) {
                try {
                    Thread.sleep((long)(1000*dt));
                    move();

                    if (cnt%10000 == 0 && checkAllStop()) {
                        break;
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
