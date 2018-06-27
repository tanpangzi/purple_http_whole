package com.zimi.zimixing.activity.gpsMonitor;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.zimi.zimixing.R;
import com.zimi.zimixing.activity.BaseActivity;
import com.zimi.zimixing.adapter.GpsMonitorHomeAdapter;
import com.zimi.zimixing.bean.GpsMonitorHomeBean;
import com.zimi.zimixing.bean.GpsMonitorHomeListBean;
import com.zimi.zimixing.bean.ResponseBean;
import com.zimi.zimixing.biz.GpsMonitorBiz;
import com.zimi.zimixing.configs.ConstantKey;
import com.zimi.zimixing.executor.BaseTask;
import com.zimi.zimixing.executor.RequestExecutor;
import com.zimi.zimixing.utils.IntentUtil;
import com.zimi.zimixing.utils.ToastUtil;
import com.zimi.zimixing.widget.TitleView;

import java.util.ArrayList;


/**
 * GPS监控首页
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class GpsMonitorHomeActivity extends BaseActivity {

    /** 标题栏 */
    private TitleView title_view;
    /** 列表 */
    private ListView lv_base;
    private TextView txt_total;
    private TextView txt_on_line;
    private TextView txt_off_line;
    private TextView txt_invalid;
    private GpsMonitorHomeAdapter adapter;
    /** 列表数据 */
    private ArrayList<GpsMonitorHomeListBean> orderListBeans = new ArrayList<>();

    @Override
    protected int getContentViewId() {
        return R.layout.activity_gps_monitor_home;
    }

    @Override
    protected void findViews() {
        title_view = findViewByIds(R.id.title_view);
        lv_base = findViewByIds(R.id.lv_base);
        txt_total = findViewByIds(R.id.txt_total);
        txt_on_line = findViewByIds(R.id.txt_on_line);
        txt_off_line = findViewByIds(R.id.txt_off_line);
        txt_invalid = findViewByIds(R.id.txt_invalid);
    }


    @Override
    public void initGetData() {
    }


    @Override
    protected void init() {
        title_view.setTitle("紫米星监控平台");

        adapter = new GpsMonitorHomeAdapter(this, orderListBeans);
        lv_base.setAdapter(adapter);

        getStoreList();
    }

    @Override
    protected void widgetListener() {
        title_view.setLeftBtnImg();

        title_view.setRightBtnImg(R.drawable.search_big, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putBoolean(ConstantKey.INTENT_KEY_BOOLEAN1, false);
                IntentUtil.gotoActivity(GpsMonitorHomeActivity.this, GpsMonitorStoryActivity.class, bundle);
            }
        });
    }

    /**
     * 门店列表
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-11-25,上午10:38:53
     * <br> UpdateTime: 2016-11-25,上午10:38:53
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     */
    private void getStoreList() {

        RequestExecutor.addTask(new BaseTask(GpsMonitorHomeActivity.this, getString(R.string.process_handle_wait), false) {

            @Override
            public ResponseBean sendRequest() {
                return GpsMonitorBiz.getStoreList();
            }

            @Override
            public void onSuccess(ResponseBean result) {
                GpsMonitorHomeBean bean = (GpsMonitorHomeBean) result.getObject();
                txt_total.setText(bean.getSum());
                txt_on_line.setText(bean.getOnline());
                txt_off_line.setText(bean.getOffline());
                txt_invalid.setText(bean.getInvalid());
                ArrayList<GpsMonitorHomeListBean> list = bean.getStoryList();
                orderListBeans.clear();
                orderListBeans.addAll(list);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFail(ResponseBean result) {
                ToastUtil.showToast(GpsMonitorHomeActivity.this, result.getInfo());
            }
        });
    }
}