/*
 *  Copyright (c) 2015 The CCP project authors. All Rights Reserved.
 *
 *  Use of this source code is governed by a Beijing Speedtong Information Technology Co.,Ltd license
 *  that can be found in the LICENSE file in the root of the web site.
 *
 *   http://www.yuntongxun.com
 *
 *  An additional intellectual property rights grant can be found
 *  in the file PATENTS.  All contributing project authors may
 *  be found in the AUTHORS file in the root of the source tree.
 */package com.zimi.zimixing.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zimi.zimixing.R;
import com.zimi.zimixing.utils.DensityUtil;

/**
 * 每一个CCPTabView代表一个导航按钮，配合{@link CCPLauncherUITabView}使用
 * Created by Jorstin on 2015/3/18.
 */
public class CCPTabView extends ViewGroup {

    /**
     * The tab index.
     */
    private int index;

    /**
     * The tab padding.
     */
    private int padding;

    /**
     *
     */
    public int total;

    /**
     * The tab name.
     */
    public TextView mTabDescription;


    /**
     * The tab image.
     */
    public ImageView mTabImage;

    /**
     * @param context
     */
    public CCPTabView(Context context) {
        super(context);
        total = 4;
        padding = 0;

        init();
    }

    public CCPTabView(Context context, int index) {
        this(context);
        this.index = index;
        notifyChange();
    }

    /**
     * @param context
     * @param attrs
     */
    public CCPTabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        total = 4;
        padding = 0;

        init();
    }

    /**
     * @param context
     * @param attrs
     * @param defStyle
     */
    public CCPTabView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        total = 4;
        padding = 0;

        init();
    }

    /**
     *
     */
    private void init() {

        int dip2px = DensityUtil.dp2px(getContext(), 2);

        padding = getResources().getDimensionPixelSize(R.dimen.SmallestPadding);
        TextView description = new TextView(getContext());
        description.setSingleLine();
        description.setEllipsize(TextUtils.TruncateAt.END);
        description.setTextColor(getResources().getColor(R.color.font_green));
        description.setTextSize(TypedValue.COMPLEX_UNIT_PX , getResources().getDimensionPixelSize(R.dimen.font_size_normal) + dip2px);
        description.setText("tab_view_name");
        description.setTypeface(null, Typeface.NORMAL);
        addView(mTabDescription = description);

        ImageView descriptionImage = new ImageView(getContext());
        descriptionImage.setImageResource(R.drawable.search);
        descriptionImage.setVisibility(View.INVISIBLE);
        addView(mTabImage = descriptionImage);

        //setBackgroundResource(R.drawable.);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int widthD_value = r - l;
        int heightD_value = b - t;
        int left = (widthD_value - mTabDescription.getMeasuredWidth()) / 2 ;
        int right = left + mTabDescription.getMeasuredWidth();

        int top = (heightD_value - mTabDescription.getMeasuredHeight() ) / 2 ;
        int bottom = top + mTabDescription.getMeasuredHeight();
        mTabDescription.layout(left, top, right , bottom);

        int imageLeft = left + padding;
        int imageRight = imageLeft + mTabImage.getMeasuredWidth();
        int imageTop = (heightD_value - mTabImage.getMeasuredHeight() ) / 2 ;
        int imageBottom = imageTop + mTabImage.getMeasuredHeight();
        mTabImage.layout(imageLeft, imageTop, imageRight, imageBottom);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft() - getPaddingRight();
        int height = MeasureSpec.getSize(heightMeasureSpec) - getPaddingLeft() - getPaddingRight();
        int makeHeightMeasureSpec = 0;
        if(MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.AT_MOST) {
            MeasureSpec.makeMeasureSpec(height, MeasureSpec.AT_MOST);
        } else {
            makeHeightMeasureSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
        }

        mTabDescription.measure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.AT_MOST), makeHeightMeasureSpec);
        mTabImage.measure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.AT_MOST), makeHeightMeasureSpec);
        setMeasuredDimension(width, height);
    }



    /**
     *
     */
    public final void notifyChange() {

    }

    /**
     *
     * @param visibility
     */
    public final void setTabImageVisibility(boolean visibility) {

        mTabImage.setVisibility(visibility? View.VISIBLE : View.INVISIBLE);
    }

    /**
     * set tab name description.
     * @param resid
     */
    public final void setText(int resid) {
        mTabDescription.setText(resid);
    }

    /**
     * set tab name description.
     * @param text
     */
    public final void setText(String text) {
        mTabDescription.setText(text);
    }

    /**
     * set tab name color description.
     * @param color
     */
    public final void setTextColor(int color) {
        mTabDescription.setTextColor(color);
    }

    public final void setTextColor(ColorStateList colors) {
        mTabDescription.setTextColor(colors);
    }

}

