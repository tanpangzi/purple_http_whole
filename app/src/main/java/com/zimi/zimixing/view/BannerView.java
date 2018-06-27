package com.zimi.zimixing.view;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.zimi.zimixing.R;
import com.zimi.zimixing.activity.BaseActivity;
import com.zimi.zimixing.adapter.ViewPagerAdapter;
import com.zimi.zimixing.utils.LogUtil;
import com.zimi.zimixing.utils.ScreenUtil;
import com.zimi.zimixing.widget.CustomViewPager4ScrollView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * 广告轮播
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class BannerView extends BaseView {

    /** 延时时长 */
    public static final int DELAYED_TIME = 3000;
    /** 自动切换试图 */
    private CustomViewPager4ScrollView mViewPager;
    /** 当前的索引 */
    private int currentItem = 0;
    /** 自动切换圆点试图 */
    private LinearLayout viewDot;
    public Handler handler;
    /** 换页切换任务 */
    public Runnable runnable;

    public BannerView(Context context) {
        super(context);
        setContentView(context);
    }

    public BannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setContentView(context);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setContentView(context);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.view_banner;
    }

    @Override
    protected void findViews() {

        mViewPager = findViewByIds(R.id.viewpager);
        viewDot = findViewByIds(R.id.view_dot);

        RelativeLayout page_header = findViewByIds(R.id.page_header);
        // 设置 header的高度
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) page_header.getLayoutParams();
        layoutParams.height = ScreenUtil.getScreenWidthPx() * 5/ 12;
        page_header.setLayoutParams(layoutParams);
    }

    @Override
    protected void widgetListener() {

    }

    @Override
    protected void init() {
        handler = new Handler();

        runnable = new Runnable() {

            @Override
            public void run() {
                boolean isD;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    isD = ((BaseActivity) context).isDestroyed();
                } else {
                    isD = ((BaseActivity) context).isFinishing();
                }

                if (!isD) {
                    if (viewDot.getChildCount() > 0) {
                        if (currentItem < viewDot.getChildCount() - 1) {
                            currentItem = currentItem + 1;
                            mViewPager.setCurrentItem(currentItem, false);
                        } else if (currentItem == viewDot.getChildCount() - 1) {
                            currentItem = 0;
                            mViewPager.setCurrentItem(currentItem, false);
                        }
                        //LogUtil.i(currentItem + "......currentItem");
                    }
                    handler.postDelayed(runnable, DELAYED_TIME);
                } else {
                    handler.removeCallbacksAndMessages(null);
                }
            }
        };

        //        ArrayList<String> beans = new ArrayList<>();
        //        String string =
        //                "http://godive.cn/seaguest_server/diveres/imgs/20170228/201702281706302456-12256.png"
        //                        + ",http://godive.cn/seaguest_server/diveres/headimgs/20161211/12529-20161211220020206.jpg"
        //                        + ",http://godive.cn/seaguest_server/diveres/imgs/20170228/13459-20170228143153528-1.png"
        //                        + ",http://godive.cn/seaguest_server/diveres/imgs/20170228/13459-20170228143153488-0.png"
        //                        //                        + ",http://godive.cn/seaguest_server/diveres/imgs/20170228/13459-20170228143153746-7.png"
        //                        //                        + ",http://godive.cn/seaguest_server/diveres/imgs/20170225/13639-20170225193158526-5.png"
        //                        //                        + ",http://godive.cn/seaguest_server/diveres/headimgs/20170226/13647-201702261047360710.png"
        //                        + ",http://godive.cn/seaguest_server/diveres/headimgs/20170225/13639-201702251932151860.png"
        //                        + ",http://godive.cn/seaguest_server/diveres/imgs/20170225/13639-20170225193158439-3.png";
        //
        //        String[] strs = string.split(",");
        //        Collections.addAll(beans, strs);
        //        initViewPageData(beans);
    }

    /**
     * 描述： 实现思路：0、1、2、3、4、5、6、0
     */
    public void initViewPageData(ArrayList<Integer> beans) {
        if (beans == null) {
            return;
        }
        currentItem = 0;
        ArrayList<View> views = new ArrayList<>();
        try {
            if (!beans.isEmpty()) {
                viewDot.removeAllViews();
                for (int i = 0; i < beans.size(); i++) {
                    ImageView img = new ImageView(context);

                    Picasso.with(context).load(beans.get(i)).error(R.drawable.img_load_error_base)
                            .placeholder(R.drawable.img_default_grey_base)// 占位图 默认图片 一般可以设置成一个加载中的进度GIF图
                            .fit()// 单独调用fit 图片会拉伸 it & centerCrop (不会拉伸): fit 它会自动测量我们的View的大小，然后内部调用reszie方法把图片裁剪到View的大小，这就帮我们做了计算size和调用resize 这2步
                            .centerCrop()// 比例类型 充满ImageView 的边界，居中裁剪
                            .into(img);
                    img.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            LogUtil.i("广告轮播图点击事件");
                        }
                    });
                    views.add(img);

                    View view = new View(context);
                    view.setBackgroundResource(R.drawable.selector_point_white_blue);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(20, 20);
                    lp.leftMargin = ScreenUtil.dip2px(4);
                    view.setLayoutParams(lp);
                    view.setEnabled(false);
                    viewDot.addView(view);
                }

                if (beans.size() > 1) {
                    ImageView img = new ImageView(context);
                    Picasso.with(context).load(beans.get(0)).error(R.drawable.img_load_error_base)
                            .placeholder(R.drawable.img_default_grey_base)// 占位图 默认图片 一般可以设置成一个加载中的进度GIF图
                            .fit()// 单独调用fit 图片会拉伸 it & centerCrop (不会拉伸): fit 它会自动测量我们的View的大小，然后内部调用reszie方法把图片裁剪到View的大小，这就帮我们做了计算size和调用resize 这2步
                            .centerCrop()// 比例类型 充满ImageView 的边界，居中裁剪
                            .into(img);
                    img.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            LogUtil.i("广告轮播图点击事件");
                        }
                    });
                    views.add(img);
                }


                ViewPagerAdapter adapter = new ViewPagerAdapter(views);
                mViewPager.setAdapter(adapter);
                // 设置一个监听器，当ViewPager中的页面改变时调用
                mViewPager.setOnPageChangeListener(new MyPageChangeListener());
                mViewPager.setCurrentItem(0);

                mViewPager.setItemSize(views.size());
                // 一张不显示
                if (beans.size() == 1) {
                    viewDot.setVisibility(GONE);
                } else {// 圆点形式
                    viewDot.setVisibility(VISIBLE);
                    if (0 < viewDot.getChildCount()) {
                        viewDot.getChildAt(0).setEnabled(true);
                    }
                }
                handler.removeCallbacksAndMessages(null);
                handler.postDelayed(runnable, DELAYED_TIME);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 当ViewPager中页面的状态发生改变时调用
     */
    private class MyPageChangeListener implements ViewPager.OnPageChangeListener {

        public void onPageSelected(int position) {
            if (viewDot.getChildCount() > 0) {
                for (int i = 0; i < viewDot.getChildCount(); i++) {
                    viewDot.getChildAt(i).setEnabled(false);
                }

                if (position == viewDot.getChildCount()) {// 只有手动滑动 才会出现这个情况
                    currentItem = 0;
                    mViewPager.setCurrentItem(currentItem, false);
                    viewDot.getChildAt(currentItem).setEnabled(true);
                } else {
                    currentItem = position;
                    viewDot.getChildAt(position).setEnabled(true);
                }
            }
        }

        /**
         * @param arg0
         *         :当前页面，及你点击滑动的页面
         * @param arg1
         *         arg1:当前页面偏移的百分比
         * @param arg2
         *         :当前页面偏移的像素位置
         */
        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            //            if (arg2 != 0) {
            //                handler.removeCallbacks(runnable);
            //                mViewPager.setLock(true);
            //            } else {
            //                handler.postDelayed(runnable, POSTDELAYED_TIME);
            //                mViewPager.setLock(false);
            //            }
            //            LogUtil.i(arg0 + "......" + arg1 + "......." + arg2);
        }

        /**
         * @param arg0
         *         有三种状态（0，1，2）。arg0
         *         ==1的时辰默示正在滑动，arg0==2的时辰默示滑动完毕了，arg0==0的时辰默示什么都没做。
         */
        @Override
        public void onPageScrollStateChanged(int arg0) {
            //LogUtil.e(arg0 + "........");
            if (arg0 == 1) {
                handler.removeCallbacksAndMessages(null);
                mViewPager.setLock(true);
            } else if (arg0 == 2) {
                handler.removeCallbacksAndMessages(null);
                handler.postDelayed(runnable, DELAYED_TIME);
                mViewPager.setLock(false);
            }
        }
    }
}
