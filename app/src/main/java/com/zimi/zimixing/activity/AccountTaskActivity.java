package com.zimi.zimixing.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.zimi.zimixing.R;
import com.zimi.zimixing.configs.Constant;
import com.zimi.zimixing.fragment.AccountTaskFragment;
import com.zimi.zimixing.fragment.BaseFragment;
import com.zimi.zimixing.widget.CCPLauncherUITabView;
import com.zimi.zimixing.widget.TitleView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tanjun on 2018/1/11.
 * 车辆评估 GPS安装 车辆抵押 共同进入界面
 */

public class AccountTaskActivity extends BaseActivity {

    private AccountTitleAdapter mAdapter;

    /** 标题栏 */
    private TitleView title_view;

    /** viewpager */
    private ViewPager mViewPager;

    /** 用来区分从哪进来 */
    private int queryType;

    /** tab栏 */
    private CCPLauncherUITabView mLauncherUITabView;
    private Map<Integer, BaseFragment> fragments = new HashMap<Integer, BaseFragment>();

    @Override
    protected int getContentViewId() {
        return R.layout.activity_task_tabs;
    }

    @Override
    protected void findViews() {
        title_view = (TitleView) findViewById(R.id.title_view_task);
        mViewPager = (ViewPager) findViewById(R.id.account_task_viewpager);
        mLauncherUITabView = (CCPLauncherUITabView) findViewById(R.id.laucher_tab_top);
    }

    @Override
    protected void initGetData() {

    }

    @Override
    protected void init() {
        title_view.setLeftBtnImg(R.drawable.back);
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            queryType = bundle.getInt("type");
        }
        mViewPager.setCurrentItem(0);
        if (queryType == Constant.CAR_ASSESS){ //车辆评估
            mAdapter = new AccountTitleAdapter(getSupportFragmentManager(), this, Constant.CAR_ASSESS);
            title_view.setTitle(R.string.car_assessment);
        }else if(queryType == Constant.GPS_INSTALL){ //GPS安装
            mAdapter = new AccountTitleAdapter(getSupportFragmentManager(), this, Constant.GPS_INSTALL);
            title_view.setTitle(R.string.gps_install);
        }else if (queryType == Constant.CAR_MORTGAGE){ //车辆抵押
            mAdapter = new AccountTitleAdapter(getSupportFragmentManager(), this, Constant.CAR_MORTGAGE);
            title_view.setTitle(R.string.car_mortgage);
        }


        mViewPager.setOnPageChangeListener(mAdapter);
        mViewPager.setAdapter(mAdapter);

    }

    @Override
    protected void widgetListener() {
        mLauncherUITabView.setOnUITabViewClickListener(new TabClickListener());
    }

    /**
     * Tab点击事件
     */
    class TabClickListener implements CCPLauncherUITabView.OnUITabViewClickListener {

        @Override
        public void onTabClick(int tabIndex) {
            mViewPager.setCurrentItem(tabIndex);
        }
    }

    /** ViewPager适配器   **/
    private class AccountTitleAdapter extends FragmentStatePagerAdapter implements ViewPager.OnPageChangeListener {
        Context mContext;
        int type;

        public AccountTitleAdapter(FragmentManager fm, Context mContext, int queryType) {
            super(fm);
            this.mContext = mContext;
            this.type = queryType;
        }

        @Override
        public Fragment getItem(int position) {
            return getTabFragment(position, type);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (mLauncherUITabView != null) {//移动条形的
                mLauncherUITabView.doTranslateImageMatrix(position, positionOffset);
            }
        }

        @Override
        public void onPageSelected(int position) {
            mLauncherUITabView.doChangeTabViewDisplay(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }

    }

    public Fragment getTabFragment(int position, int queryType){
        BaseFragment fragment = fragments.get(position);
        if(fragment != null) return fragment;
        switch (position) {
            case 0: //待办
                fragment = AccountTaskFragment.newInstance(position, queryType);
                break;
            case 1: //已办
                fragment = AccountTaskFragment.newInstance(position, queryType);
                break;
            default:
                break;
        }
        if(fragment!=null){
            fragments.put(position, fragment);
        }
        return fragment;
    }

}
