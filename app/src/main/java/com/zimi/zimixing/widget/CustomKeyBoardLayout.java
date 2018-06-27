package com.zimi.zimixing.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

import com.zimi.zimixing.utils.LogUtil;

/**
 * 自定义 ScrollView  实现输入框 显示隐藏 监听
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2017/10/19
 * <br> Copyright: Copyright © 2017 xTeam Technology. All rights reserved.
 */
public class CustomKeyBoardLayout extends ScrollView {

    private onSizeChangedListener mChangedListener;

    public CustomKeyBoardLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public CustomKeyBoardLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomKeyBoardLayout(Context context) {
        super(context);
    }

    //    @Override
    //    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    //        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    //         LogUtil.i("onMeasure-----------");
    //    }
    //
    //    @Override
    //    protected void onLayout(boolean changed, int l, int t, int r, int b) {
    //        super.onLayout(changed, l, t, r, b);
    //         LogUtil.i("onLayout-------------------");
    //    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        LogUtil.i("onSizeChanged-------------------");
        LogUtil.i("w----" + w + "\n" + "h-----" + h + "\n" + "oldW-----" + oldw + "\noldh----" + oldh);
        if (null != mChangedListener && 0 != oldw && 0 != oldh) {
            // TODO 如果输入框支持换行，则不能直接用h < oldh。因为换行也会改变scrollview的高度。
            boolean mShowKeyboard = h < oldh;
            mChangedListener.onChanged(mShowKeyboard);
            LogUtil.i("mShowKeyboard----- " + mShowKeyboard);
        }
    }

    public void setOnSizeChangedListener(onSizeChangedListener listener) {
        mChangedListener = listener;
    }

    public interface onSizeChangedListener {

        void onChanged(boolean showKeyboard);
    }

}