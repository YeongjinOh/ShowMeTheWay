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
public class SpinControlView extends ImageView implements View.OnTouchListener {

    private Paint paint;
    private float height;
    private float width;
    private float radius;
    private float x;

    public SpinControlView(Context context, AttributeSet attrs) {

        super(context, attrs);
        setOnTouchListener(this);
        init();

    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        height = 400.0F;
        width = 400.0F;
        x = width/2;
        radius = 10.0F;
    }

    // get size of image
    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        height = getHeight();
        width = getWidth();
        x = width/2;
        radius = width/20;
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(x,height/2,radius,paint);
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float prevX = x;
        float curX;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                prevX = event.getX();
                postInvalidate();
                break;

            case MotionEvent.ACTION_MOVE:
                curX = event.getX();
                x += curX - prevX;
                x = Math.min(x, width-4*radius);
                x = Math.max(x, 4*radius);
                prevX = curX;
                postInvalidate();

                break;
        }
        return true;
    }
}
