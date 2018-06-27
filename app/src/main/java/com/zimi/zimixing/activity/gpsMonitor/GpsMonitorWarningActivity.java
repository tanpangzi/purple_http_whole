package com.zimi.zimixing.activity.gpsMonitor;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.zimi.zimixing.R;
import com.zimi.zimixing.activity.BaseActivity;
import com.zimi.zimixing.adapter.GpsMonitorStoryAdapter;
import com.zimi.zimixing.bean.GpsMonitorStoryBean;
import com.zimi.zimixing.bean.GpsMonitorStoryTypeBean;
import com.zimi.zimixing.bean.ResponseBean;
import com.zimi.zimixing.biz.GpsMonitorBiz;
import com.zimi.zimixing.configs.ConstantKey;
import com.zimi.zimixing.executor.BaseTask;
import com.zimi.zimixing.executor.RequestExecutor;
import com.zimi.zimixing.utils.OtherUtils;
import com.zimi.zimixing.utils.ToastUtil;
import com.zimi.zimixing.widget.TitleView;
import com.zimi.zimixing.widget.pullview.PullToRefreshListView;

import java.util.ArrayList;


/**
 * GPS监控 门店报警设备列表数据
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class GpsMonitorWarningActivity extends BaseActivity {

    /** 标题栏 */
    private TitleView title_view;
    /** 列表 */
    private PullToRefreshListView lv_base;
    private EditText et_search;
    private View txt_search;
    private GpsMonitorStoryAdapter adapter;
    /** 列表数据 */
    private ArrayList<GpsMonitorStoryBean> orderListBeans = new ArrayList<>();
    /** 当前加载页码 */
    private int pageNum = 1;
    private String storyId = "";

    /** 搜索的内容 */
    private String searchStr = "";

    private View view_search;
    private View view_type;
    //  crossing : 2  越界报警数-->
    private TextView txt_crossing;
    //    displacement : 0  位移报警数-->
    private TextView txt_displacement;
    //  sum : 3  所有报警数-->
    private TextView txt_sum;
    //  outages : 1  断电报警数-->
    private TextView txt_outages;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_gps_monitor_warning;
    }

    @Override
    protected void findViews() {
        title_view = findViewByIds(R.id.title_view);
        lv_base = findViewByIds(R.id.lv_base);
        txt_search = findViewByIds(R.id.txt_search);
        et_search = findViewByIds(R.id.et_search);
        lv_base.setMode(PullToRefreshListView.MODE_BOTHNOT);

        //  crossing : 2  越界报警数-->
        txt_crossing = findViewByIds(R.id.txt_crossing);
        //    displacement : 0  位移报警数-->
        txt_displacement = findViewByIds(R.id.txt_displacement);
        //  sum : 3  所有报警数-->
        txt_sum = findViewByIds(R.id.txt_sum);
        //  outages : 1  断电报警数-->
        txt_outages = findViewByIds(R.id.txt_outages);
        view_type = findViewByIds(R.id.view_type);
        view_search = findViewByIds(R.id.view_search);
    }

    @Override
    public void initGetData() {
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            storyId = bundle.getString(ConstantKey.INTENT_KEY_ID, "");
        }
    }

    @Override
    protected void init() {
        title_view.setTitle("报警列表");

        adapter = new GpsMonitorStoryAdapter(this, orderListBeans);
        adapter.setCallPolice(true);
        lv_base.getRefreshableView().setAdapter(adapter);

        getAlarmList(-1);
    }

    @Override
    protected void widgetListener() {
        title_view.setLeftBtnImg();

        title_view.setRightBtnImg(R.drawable.alarm_icon_filter, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bean == null) {
                    getAlarmScreeningList();
                } else {
                    // 设置数据
                    initType();
                }
            }
        });

        //        lv_base.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener() {
        //            @Override
        //            public void onPullToDownRefresh() {
        //                lv_base.setClickCompleteAndAll();
        //                pageNum = 1;
        //                getAlarmList(-1);
        //            }
        //
        //            @Override
        //            public void onPullToUpRefresh() {
        //                pageNum = pageNum + 1;
        //                getAlarmList(-1);
        //            }
        //        });

        txt_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (OtherUtils.getInstance().isFastClick(view)) {
                    return;
                }
                pageNum = 1;
                searchStr = et_search.getText().toString().trim();
                getAlarmList(-1);
                et_search.setText("");
            }
        });

        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    pageNum = 1;
                    searchStr = et_search.getText().toString().trim();
                    getAlarmList(-1);
                    et_search.setText("");
                    return true;
                }
                return false;
            }
        });


        View.OnClickListener onClickListener = new View.OnClickListener() {
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.txt_sum:// 所有报警数 0
                        getAlarmList(0);
                        break;
                    case R.id.txt_displacement:// 位移报警数 1
                        getAlarmList(1);
                        break;
                    case R.id.txt_outages:// 断电报警数 2
                        getAlarmList(2);
                        break;
                    case R.id.txt_crossing:// 越界报警数 3
                        getAlarmList(3);
                        break;
                    default:// 默认
                        break;
                }
            }
        };
        txt_sum.setOnClickListener(onClickListener);
        txt_displacement.setOnClickListener(onClickListener);
        txt_outages.setOnClickListener(onClickListener);
        txt_crossing.setOnClickListener(onClickListener);
    }

    /**
     * 超级管管理员GPS监控 获取报警设备列表
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-11-25,上午10:38:53
     * <br> UpdateTime: 2016-11-25,上午10:38:53
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param warningType
     *         //-1查所有  0("所有报警"); 1 ("位移报警");  2 ("断点报警"); 3 ("越界报警");
     */
    private void getAlarmList(final int warningType) {
        title_view.setTitle("报警列表");
        view_search.setVisibility(View.VISIBLE);
        lv_base.setVisibility(View.VISIBLE);
        view_type.setVisibility(View.GONE);

        if (pageNum == 1) {
            lv_base.setClickCompleteAndAll();
        }

        RequestExecutor.addTask(new BaseTask(GpsMonitorWarningActivity.this, getString(R.string.process_handle_wait), false) {

            @Override
            public ResponseBean sendRequest() {
                return GpsMonitorBiz.getAlarmList(pageNum + "", searchStr, storyId, warningType);
            }

            @Override
            public void onSuccess(ResponseBean result) {
                ArrayList<GpsMonitorStoryBean> list = (ArrayList<GpsMonitorStoryBean>) result.getObject();
                if (pageNum == 1) {
                    orderListBeans.clear();
                }
                if (list.size() <= 0) {
                    lv_base.onRefreshCompleteAndAll();
                }
                orderListBeans.addAll(list);
                adapter.notifyDataSetChanged();
                lv_base.onRefreshComplete();
            }

            @Override
            public void onFail(ResponseBean result) {
                lv_base.onRefreshComplete();
                ToastUtil.showToast(GpsMonitorWarningActivity.this, result.getInfo());
            }
        });
    }

    private GpsMonitorStoryTypeBean bean;

    /**
     * 超级管管理员GPS监控 筛选报警设备列表(获取报警分类和总数)
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-11-25,上午10:38:53
     * <br> UpdateTime: 2016-11-25,上午10:38:53
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     */
    private void getAlarmScreeningList() {
        RequestExecutor.addTask(new BaseTask(GpsMonitorWarningActivity.this, getString(R.string.process_handle_wait), false) {

            @Override
            public ResponseBean sendRequest() {
                return GpsMonitorBiz.getAlarmScreeningList(storyId);
            }

            @Override
            public void onSuccess(ResponseBean result) {
                bean = (GpsMonitorStoryTypeBean) result.getObject();
                initType();
            }

            @Override
            public void onFail(ResponseBean result) {
                ToastUtil.showToast(GpsMonitorWarningActivity.this, result.getInfo());
            }
        });
    }

    private void initType() {
        if (bean == null) {
            return;
        }

        title_view.setTitle("报警筛选");
        view_search.setVisibility(View.GONE);
        lv_base.setVisibility(View.GONE);
        view_type.setVisibility(View.VISIBLE);
        //  crossing : 2  越界报警数-->
        txt_crossing.setText(bean.getCrossing());
        //    displacement : 0  位移报警数-->
        txt_displacement.setText(bean.getDisplacement());
        //  sum : 3  所有报警数-->
        txt_sum.setText(bean.getSum());
        //  outages : 1  断电报警数-->
        txt_outages.setText(bean.getOutages());
    }

    // ***********************************返回键事件处理*********************************
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {
                // 要执行的事件
                if (view_type.getVisibility() == View.VISIBLE) {
                    title_view.setTitle("报警列表");
                    view_search.setVisibility(View.VISIBLE);
                    lv_base.setVisibility(View.VISIBLE);
                    view_type.setVisibility(View.GONE);
                } else {
                    finishActivity();
                }
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

}