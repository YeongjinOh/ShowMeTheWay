package com.billiard.yeongjinoh.showmetheway;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by yeongjinoh on 2016-07-25.
 */
public class PowerGaugeBarView extends ImageView {

    private Paint paint;
    private float height;
    private float width;
    private float power;
    int color;

    public PowerGaugeBarView (Context context, AttributeSet attrs) {
        super(context, attrs);
        power = 0.0F;
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        height = getHeight();
        width = getWidth();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(color);
        canvas.drawRect(0.0F, height*(1.0F-power), width, height, paint);
    }

    public void updatePower(float power) {
        this.power = power;
        color = Color.rgb(255,(int)(255*(1-power)),0);
        postInvalidate();
    }
}
