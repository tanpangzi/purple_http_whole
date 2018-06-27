package com.zimi.zimixing.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.zimi.zimixing.R;
import com.zimi.zimixing.utils.DensityUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 主界面Tab容器
 * Created by Jorstin on 2015/3/18.
 */
public class CCPLauncherUITabView extends RelativeLayout implements View.OnClickListener {

    /**
     * Follow the label moved slowly
     */
    private Bitmap mIndicatorBitmap;

    /**
     *
     */
    private Matrix mMatrix = new Matrix();

    /**
     * Slide unit
     */
    private int mTabViewBaseWidth;

    /**
     * The current label location, is the need to move the index
     */
    private int mCurrentSlideIndex = -1;

    /**
     *
     */
    private ImageView mSlideImage;

    /**
     * tab view click.
     */
    private long mClickTime = 0L;

    /**
     * Listener used to dispatch click events.
     */
    private OnUITabViewClickListener mListener;

    /**
     * data source
     */
    private CharSequence[] array;

    /**
     * data source
     */
    private Map<Integer, TabViewHolder> map = new HashMap<Integer, TabViewHolder>();

    /**
     * defultCount
     */
    private int defultCount = 2;

    private int width;

    /**
     * @param context
     */
    public CCPLauncherUITabView(Context context) {
        super(context);

        init();
    }

    /**
     * @param context
     * @param attrs
     */
    public CCPLauncherUITabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.UITabViewData);//获取配置属性
        //TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.UITabViewData, defStyle, 0);
        array = typedArray.getTextArray(R.styleable.UITabViewData_tabNames);
        typedArray.recycle();
        init();
    }

    /**
     * @param context
     * @param attrs
     * @param defStyle
     */
    public CCPLauncherUITabView(Context context, AttributeSet attrs,
                                int defStyle) {
        super(context, attrs, defStyle);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.UITabViewData, defStyle, 0);
        array = typedArray.getTextArray(R.styleable.UITabViewData_tabNames);
        typedArray.recycle();
        init();
    }
    /**
     * 初始化三个Tab 视图
     */
    private void init() {
        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.HORIZONTAL);

        addView(layout, new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));

        defultCount = 2;
        if(array != null){
            defultCount = array.length;
            for (int i = 0; i < defultCount; i++) {
                TabViewHolder tabViewContacts = createTabView(i);
                tabViewContacts.tabView.setText(array[i].toString());
                LinearLayout.LayoutParams contactsLayoutParams = new LinearLayout.LayoutParams(
                        0, getResources().getDimensionPixelSize(R.dimen.DefaultTabbarHeight));
                contactsLayoutParams.weight = 1.0F;
                layout.addView(tabViewContacts.tabView, contactsLayoutParams);
                map.put(i, tabViewContacts);
            }
            array = null;
        }else {
            // TabView contacts
            TabViewHolder tabViewContacts = createTabView(0);
            tabViewContacts.tabView.setText(R.string.wait_do);
            LinearLayout.LayoutParams contactsLayoutParams = new LinearLayout.LayoutParams(
                    0, getResources().getDimensionPixelSize(R.dimen.DefaultTabbarHeight));
            contactsLayoutParams.weight = 1.0F;
            layout.addView(tabViewContacts.tabView, contactsLayoutParams);
            map.put(0, tabViewContacts);

            // TabView communication
            TabViewHolder tabViewSecond = createTabView(1);
            tabViewSecond.tabView.setText(R.string.already_done);
            LinearLayout.LayoutParams secondLayoutParams = new LinearLayout.LayoutParams(
                    0, getResources().getDimensionPixelSize(R.dimen.DefaultTabbarHeight));
            secondLayoutParams.weight = 1.0F;
            layout.addView(tabViewSecond.tabView, secondLayoutParams);
            map.put(1, tabViewSecond);
        }

        ImageView imageView = new ImageView(getContext());
        imageView.setImageMatrix(mMatrix);
        imageView.setScaleType(ImageView.ScaleType.MATRIX);
        LayoutParams imageViewLayoutParams = new LayoutParams(-1, DensityUtil.dp2px(getContext(), defultCount));
        imageViewLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        addView(mSlideImage = imageView, imageViewLayoutParams);
    }

    /**
     * Register a callback to be invoked when this UITabView is clicked.
     * @param l The callback that will run
     */
    public void setOnUITabViewClickListener(OnUITabViewClickListener l) {
        mListener = l;
    }

    public void setLeftText(String content){
        if(map != null && map.get(0) != null)
            map.get(0).tabView.setText(content);
    }

    public void setMiddleText(String content){
        if(map != null && map.get(1) != null)
            map.get(1).tabView.setText(content);
    }

    public void setRightText(String content){
        if(map != null && map.get(2) != null)
            map.get(2).tabView.setText(content);
    }

    /**
     *
     * @param index
     */
    public final void setTabViewText(int index , int resid) {
        if(map != null && map.get(index) != null){
            map.get(index).tabView.setText(resid);
        }
    }

    /**
     * create new TabView.
     * @param index
     * @return
     */
    public TabViewHolder createTabView(int index) {
        TabViewHolder tabViewHolder = new TabViewHolder();
        tabViewHolder.tabView = new CCPTabView(getContext(), index);
        tabViewHolder.tabView.setTag(Integer.valueOf(index));
        tabViewHolder.tabView.setOnClickListener(this);
        return tabViewHolder;
    }


    public final void resetTabViewDesc() {
        if(map == null) {
            return;
        }
        for (TabViewHolder v : map.values()) {
            v.tabView.notifyChange();
        }
    }

    @Override
    public void onClick(View v) {
        int intValue = (Integer)v.getTag();
        if((mCurrentSlideIndex == intValue) || (System.currentTimeMillis() - mClickTime <= 300L)) {
            return;
        }
        doChangeTabViewDisplay(intValue);
        if(mListener != null) {
            mListener.onTabClick(intValue);
            doTranslateImageMatrix(intValue, 0);
            mClickTime = System.currentTimeMillis();
        }
    }

    /**
     *
     */
    public final void doSentEvents() {
        if(map == null) {
            return;
        }
        for (TabViewHolder v : map.values()) {
            v.tabView.notifyChange();
        }
    }

    /**
     * 移动
     * @param start 开始位置
     * @param distance 移动距离
     */
    public final void doTranslateImageMatrix(int start, float distance) {
        mMatrix.setTranslate(mTabViewBaseWidth * (start + distance) , 0.0F);
        mSlideImage.setImageMatrix(mMatrix);
    }

    /**
     * Change the selected TabView color
     * @param index
     */
    public final void doChangeTabViewDisplay(int index) {
        if(map == null) {
            return;
        }
        for (Map.Entry<Integer, TabViewHolder> entry : map.entrySet()) {
            if(entry.getKey() == index){
                entry.getValue().tabView.setTextColor(getResources().getColorStateList(R.color.main_head_area_background));
            }else{
                entry.getValue().tabView.setTextColor(getResources().getColorStateList(R.color.black));
            }
        }
        mCurrentSlideIndex = index;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        width = mTabViewBaseWidth = ((r - l) / defultCount);//条形的宽度

        if(mIndicatorBitmap == null || mIndicatorBitmap.getWidth() != mTabViewBaseWidth) {

            int from = -1;
            if(mIndicatorBitmap != null) {
                from = mIndicatorBitmap.getWidth();
            }

            mIndicatorBitmap = Bitmap.createBitmap(width, DensityUtil.dp2px(getContext(), 4), Bitmap.Config.ARGB_4444);

            Canvas canvas = new Canvas(mIndicatorBitmap);//条形
            canvas.drawColor(getResources().getColor(R.color.main_head_area_background));
            doTranslateImageMatrix(mCurrentSlideIndex, 0.0F);
            mSlideImage.setImageBitmap(mIndicatorBitmap);
            if(mCurrentSlideIndex == -1) {
                doChangeTabViewDisplay(0);
            }
        }

    }

    /**
     * @author Jorstin Chan
     * @date 2014-4-26
     * @version 1.0
     */
    public class TabViewHolder {

        CCPTabView tabView;
    }

    /**
     * Interface definition for a callback to be invoked when a UITabView is clicked.
     */
    public abstract interface OnUITabViewClickListener {

        /**
         * Called when a UITabView has been clicked.
         * @param tabIndex index of UITabView
         */
        public abstract void onTabClick(int tabIndex);
    }

}
