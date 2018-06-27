package com.zimi.zimixing.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by win7 on 2017/9/4.
 */

public class PlayView extends View {

    private Paint mPaint;
    private Path mPath;

    private int w;
    private int h;

    private boolean isPlay = true;

    public PlayView(Context context) {
        this(context, null);
    }

    public PlayView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public PlayView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(10);
        mPath = new Path();
    }

    public void isPlay(boolean b) {
        isPlay = b;
        invalidate();
    }

    public boolean getPlay(){
        return isPlay;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        w = getMeasuredWidth();
        h = getMeasuredHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        if (isPlay) {

            mPath.moveTo(w / 2 - 5, h / 2 - 15);
            mPath.lineTo(w / 2 + 20, h / 2);
            mPath.lineTo(w / 2 - 5, h / 2 + 15);
            mPath.close();
            canvas.drawPath(mPath, mPaint);
        } else {
            canvas.drawLine(w / 2 - 5, h / 2 - 25, w / 2 - 5, h / 2 + 25, mPaint);
            canvas.drawLine(w / 2 + 20, h / 2 - 25, w / 2 + 20, h / 2 + 25, mPaint);

        }
        this.setLayerType(View.LAYER_TYPE_NONE, null);
    }
}
