package com.zimi.zimixing.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zimi.zimixing.R;
import com.zimi.zimixing.interf.OnTouchLetterIndex;
import com.zimi.zimixing.utils.ScreenUtil;


/**
 * a-z控件
 * <p>
 * <br> Author: 叶青
 * <br> Version: 6.0.0
 * <br> Date: 2017/9/2
 * <br> Copyright: Copyright © 2017 xTeam Technology. All rights reserved.
 */
public class LetterIndexView extends LinearLayout {

    /**
     * 上下文环境
     */
    private Context context;
    /**
     * 字母控件
     */
    private TextView[] lettersTxt = new TextView[28];
    /**
     * 触碰字母索引接口
     */
    private OnTouchLetterIndex touchLetterIndex;
    /**
     * 设置颜色
     */
    public int setColor = -1;
    /**
     * 设置字体颜色
     */
    public int textColor = -1;

    public LetterIndexView(Context context) {
        super(context);
        this.context = context;
    }

    public LetterIndexView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    /**
     * 设置背景
     *
     * @param color
     *
     * @version 1.0
     * @createTime 2015年7月27日, 下午6:02:23
     * @updateTime 2015年7月27日, 下午6:02:23
     * @createAuthor 叶青
     * @updateAuthor 叶青
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    public void setBackground(int color) {
        this.setColor = color;
        this.setBackgroundColor(getResources().getColor(color));
    }

    /**
     * 初始化控件.
     *
     * @author 叶青
     * @version 1.0, 2013-5-10
     */
    public void init(OnTouchLetterIndex touchLetterIndex) {
        this.touchLetterIndex = touchLetterIndex;
        //		this.setBackgroundColor(getResources().getColor(R.color.white));
        this.setBackgroundColor(getResources().getColor(android.R.color.transparent));

        this.setOrientation(LinearLayout.VERTICAL);
        this.setGravity(Gravity.CENTER);

        for (int i = 0; i < 27; i++) {
            lettersTxt[i] = new TextView(context);
            lettersTxt[i].setGravity(Gravity.CENTER);
            char tab = (char) (i + 64);
            //			if (i == 0)
            //				lettersTxt[i].setText("!");
            //			else
            if (i == 0)
                lettersTxt[i].setText("#");
            else
                lettersTxt[i].setText("" + tab);
            lettersTxt[i].setPadding(ScreenUtil.dip2px( 5), 0, ScreenUtil.dip2px( 5), 0);
            lettersTxt[i].setBackgroundColor(getResources().getColor(android.R.color.transparent));//bg_grey
            lettersTxt[i].setTextSize(11);
            lettersTxt[i].setTextColor(getResources().getColor(R.color.font_black));
            LayoutParams letterParam = new LayoutParams(LayoutParams.WRAP_CONTENT, 0);
            letterParam.weight = 1;
            lettersTxt[i].setLayoutParams(letterParam);
            this.addView(lettersTxt[i]);
        }

        this.setOnTouchListener(new OnTouchListener() {

            private int y;
            private int height;
            private String tab;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        //setTextColor(Color.WHITE);
                        //LetterIndexView.this.setBackgroundColor(getResources().getColor(R.color.bg_default));
                    case MotionEvent.ACTION_MOVE:
                        y = (int) event.getY(); // 获取触发事件点的纵坐标
                        height = LetterIndexView.this.getHeight();
                        int location = (int) (y / (height / 27) + 0.5f);
                        //					if (location == 0) {
                        //						tab = "!";
                        //					} else
                        if (location == 0) {
                            tab = "#";
                        } else if (location > 0 && location <= 26) {
                            tab = String.valueOf((char) (location + 64));
                        }
                        LetterIndexView.this.touchLetterIndex.touchLetterWitch(tab);
                        break;
                    case MotionEvent.ACTION_UP:
                        if (setColor > -1) {
                            LetterIndexView.this.setBackgroundColor(getResources().getColor(setColor));
                        } else {
                            LetterIndexView.this.setBackgroundColor(getResources().getColor(R.color.transparent));
                        }
                        LetterIndexView.this.touchLetterIndex.touchFinish();
                        if (textColor > -1) {
                            setTextColor(getResources().getColor(textColor));
                        } else {
                            setTextColor(R.color.font_gray);
                        }

                        break;
                }
                return true;
            }
        });
    }

    /**
     * 设置字体颜色
     *
     * @param color
     *
     * @author 叶青
     * @version 1.0
     * @date 2013-5-20
     */
    public void setIndexColor(int color) {
        textColor = color;
        setTextColor(getResources().getColor(color));
    }

    /**
     * 设置文字加粗
     */
    public void setBold() {
        for (int i = 0; i < 27; i++) {
            lettersTxt[i].setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        }
    }

    /**
     * 设置字体颜色
     *
     * @param color
     *
     * @author 叶青
     * @version 1.0
     * @date 2013-5-20
     */
    private void setTextColor(int color) {
        for (int i = 0; i < 27; i++) {
            lettersTxt[i].setTextColor(color);
        }
    }

}
