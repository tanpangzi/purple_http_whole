package com.zimi.zimixing.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;

import com.zimi.zimixing.R;

/**
 * 自动计算列表高度的gridview控件
 * <p>
 * 将gridview用于Scrollview、Listview嵌套使用时候可以使用该控件，避免显示不全和事件冲突
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月31日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class AutoSizeGridViewAlarm extends GridView {

    /** view大小更改监听 */
    private OnSizeChangeListener listener;

    public AutoSizeGridViewAlarm(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public AutoSizeGridViewAlarm(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoSizeGridViewAlarm(Context context) {
        super(context);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {

        if (listener != null) {
            listener.onChange(w, h, oldw, oldh);
        }
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

    /**
     * 设置分割线
     * @param canvas
     */
    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        View localView1 = getChildAt(0);
        int column = getWidth() / localView1.getWidth();//列数
        int childCount = getChildCount();
        Paint localPaint;
        localPaint = new Paint();
        localPaint.setStyle(Paint.Style.STROKE);
        localPaint.setColor(getContext().getResources().getColor(R.color.line_grey_light));//这个就是设置分割线的颜色
        for (int i = 0; i < childCount; i++) {
            View cellView = getChildAt(i);
            if ((i + 1) % column == 0) {//每一行最后一个
                canvas.drawLine(cellView.getLeft(), cellView.getBottom(), cellView.getRight(), cellView.getBottom(), localPaint);
            } else if ((i + 1) > (childCount - (childCount % column))) {//最后一行的item
                canvas.drawLine(cellView.getRight(), cellView.getTop(), cellView.getRight(), cellView.getBottom(), localPaint);
            } else {
                canvas.drawLine(cellView.getRight(), cellView.getTop(), cellView.getRight(), cellView.getBottom(), localPaint);
                canvas.drawLine(cellView.getLeft(), cellView.getBottom(), cellView.getRight(), cellView.getBottom(), localPaint);
            }
        }
    }

    /**
     * 设置size大小更改监听
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-11-28,下午4:31:49
     * <br> UpdateTime: 2016-11-28,下午4:31:49
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     */
    public void setOnSizeChangListener(OnSizeChangeListener onSizeChangeListener) {
        this.listener = onSizeChangeListener;
    }

    /**
     * view 大小更改监听事件
     * <p>
     * <br> Author: 叶青
     * <br> Version: 1.0.0
     * <br> Date: 2016年12月11日
     * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
     */
    public interface OnSizeChangeListener {

        /**
         * View 大小更改监听事件
         * <p>
         * <br> Version: 1.0.0
         * <br> CreateTime: 2016-11-28,下午4:18:33
         * <br> UpdateTime: 2016-11-28,下午4:18:33
         * <br> CreateAuthor: 叶青
         * <br> UpdateAuthor: 叶青
         * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
         *
         * @param w
         *         新的宽度
         * @param h
         *         新的高度
         * @param oldw
         *         旧的宽度
         * @param oldh
         *         新的高度
         */
        public void onChange(int w, int h, int oldw, int oldh);
    }

}