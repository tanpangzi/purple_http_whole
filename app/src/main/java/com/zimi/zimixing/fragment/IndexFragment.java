package com.zimi.zimixing.fragment;

import android.widget.LinearLayout;

import com.zimi.zimixing.R;
import com.zimi.zimixing.activity.MainActivity;
import com.zimi.zimixing.adapter.IndexGridAdapter;
import com.zimi.zimixing.bean.IndexTypeBean;
import com.zimi.zimixing.view.BannerView;
import com.zimi.zimixing.widget.AutoSizeGridView;
import com.zimi.zimixing.widget.TitleView;

import java.util.ArrayList;

/**
 * 首页
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class IndexFragment extends BaseFragment {

    /** 标题栏 */
    private TitleView titleview;
    private LinearLayout view_top;
    private AutoSizeGridView grid_view;
    private BannerView bannerView;

    MainActivity mActivity;

    /** 权限列表 */

    public IndexFragment() {
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_index;
    }

    @Override
    protected void findViews() {
        view_top = findViewByIds(R.id.view_top);
        titleview = findViewByIds(R.id.title_view);
        grid_view = findViewByIds(R.id.grid_view);

        bannerView = new BannerView(getActivity());
        view_top.addView(bannerView);

        ArrayList<IndexTypeBean> list = new ArrayList<>();
        IndexTypeBean bean = new IndexTypeBean();

        bean = new IndexTypeBean();
        bean.setResId(R.drawable.icon_gps);
        bean.setResName((R.string.fragment_index_type7));
        list.add(bean);

        bean = new IndexTypeBean();
        bean.setResId(R.drawable.icon_inst);
        bean.setResName((R.string.fragment_index_type9));
        list.add(bean);

        IndexGridAdapter indexGridAdapter = new IndexGridAdapter(getActivity(), list, 2);
        grid_view.setAdapter(indexGridAdapter);
    }

    @Override
    protected void widgetListener() {

    }

    @Override
    protected void init() {
        titleview.setTitle(R.string.activity_main_index);
        mActivity = ((MainActivity) getActivity());
        ArrayList<Integer> beans = new ArrayList<>();
        beans.add(R.drawable.banner_new_one);
        beans.add(R.drawable.banner_new_one);
        bannerView.initViewPageData(beans);

    }

    @Override
    public void initGetData() {

    }

}
