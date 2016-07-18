package com.android.yeongjinoh.showmetheway;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by yeongjinoh on 2016-07-18.
 */
public class BillardTableView extends View{

    private ArrayList<Ball> balls;

    // default setting for billiard ball and table
    private float radius;
    private float width;
    private float height;
    private float scaleFactor;

    public BillardTableView(Context context) {
        super(context);

        // initialize constants
        float r = 31;
        float w = 1224;
        float h = 2*w;

        // initialize paint
        Paint white = new Paint();
        white.setColor(Color.WHITE);
        Paint yellow = new Paint();
        yellow.setColor(Color.YELLOW);
        Paint red = new Paint();
        red.setColor(Color.RED);

        // initialize balls
        balls = new ArrayList<Ball>();
        Ball whiteBall = new Ball(white,r,50,50);
        Ball yellowBall = new Ball(yellow,r,300,200);
        Ball redBall = new Ball(red,r,w-2*r,700);
        balls.add(whiteBall);
        balls.add(yellowBall);
        balls.add(redBall);

        // get width and height of window
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point sizePoint = new Point();
        display.getSize(sizePoint);
        float width = sizePoint.x;
        float height = sizePoint.y;

        // get scaleFactor
        if (width*2 < height) {
            scaleFactor = height/h;
            width = height/2;
        } else {
            scaleFactor = width/w;
            height = width*2;
        }

    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2.0F);
        paint.setColor(Color.BLUE);
        canvas.drawRect(1,1,width,height,paint);
        drawBalls(canvas);
    }

    private void drawBalls (Canvas canvas) {
        for (int i=0; i<balls.size(); i++) {
            Ball curBall = balls.get(i);
            canvas.drawCircle(curBall.getX()*scaleFactor, curBall.getY()*scaleFactor, curBall.getR()*scaleFactor, curBall.getPaint());
        }
    }






}
