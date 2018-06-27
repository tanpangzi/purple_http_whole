package com.zimi.zimixing.activity.gpsMonitor;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.zimi.zimixing.R;
import com.zimi.zimixing.activity.BaseActivity;
import com.zimi.zimixing.adapter.GpsMonitorStoryAdapter;
import com.zimi.zimixing.bean.GpsMonitorStoryBean;
import com.zimi.zimixing.bean.ResponseBean;
import com.zimi.zimixing.biz.GpsMonitorBiz;
import com.zimi.zimixing.configs.ConstantKey;
import com.zimi.zimixing.executor.BaseTask;
import com.zimi.zimixing.executor.RequestExecutor;
import com.zimi.zimixing.utils.IntentUtil;
import com.zimi.zimixing.utils.OtherUtils;
import com.zimi.zimixing.utils.ToastUtil;
import com.zimi.zimixing.widget.TitleView;
import com.zimi.zimixing.widget.pullview.PullToRefreshBase;
import com.zimi.zimixing.widget.pullview.PullToRefreshListView;

import java.util.ArrayList;


/**
 * GPS监控 门店设备列表数据
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class GpsMonitorStoryActivity extends BaseActivity {

    /** 标题栏 */
    private TitleView title_view;
    /** 列表 */
    private PullToRefreshListView lv_base;
    private EditText et_search;
    private View txt_search;
    private View btn_see;
    private GpsMonitorStoryAdapter adapter;
    /** 列表数据 */
    private ArrayList<GpsMonitorStoryBean> orderListBeans = new ArrayList<>();
    /** 当前加载页码 */
    private int pageNum = 1;
    private String storyId = "";
    private String storyName = "";

    /** true 超级管理员 */
    private boolean isAdmin = true;
    /** true 一进页面就加载数据 */
    private boolean isShowData = true;
    /** 搜索的内容 */
    private String searchStr = "";

    @Override
    protected int getContentViewId() {
        return R.layout.activity_gps_monitor_story;
    }

    @Override
    protected void findViews() {
        title_view = findViewByIds(R.id.title_view);
        lv_base = findViewByIds(R.id.lv_base);
        txt_search = findViewByIds(R.id.txt_search);
        et_search = findViewByIds(R.id.et_search);
        btn_see = findViewByIds(R.id.btn_see);

    }

    @Override
    public void initGetData() {
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            storyId = bundle.getString(ConstantKey.INTENT_KEY_ID, "");
            storyName = bundle.getString(ConstantKey.INTENT_KEY_STRING, "");
            isAdmin = bundle.getBoolean(ConstantKey.INTENT_KEY_BOOLEAN, true);
            isShowData = bundle.getBoolean(ConstantKey.INTENT_KEY_BOOLEAN1, true);
        }
    }

    @Override
    protected void init() {
        if (TextUtils.isEmpty(storyName)) {
            title_view.setTitle("车辆列表");
        } else {
            title_view.setTitle(storyName + "风控平台");
        }

        adapter = new GpsMonitorStoryAdapter(this, orderListBeans);
        lv_base.getRefreshableView().setAdapter(adapter);

        if (isShowData) {
            btn_see.setVisibility(View.VISIBLE);
            getEquipmentList();
        } else {
            btn_see.setVisibility(View.GONE);
        }
    }

    @Override
    protected void widgetListener() {
        title_view.setLeftBtnImg();

        if (isAdmin) {
            title_view.setRightBtnImg(R.drawable.location_query_icon_setting, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString(ConstantKey.INTENT_KEY_ID, storyId);
                    IntentUtil.gotoActivity(GpsMonitorStoryActivity.this, ManageActivity.class, bundle);
                }
            });
        }

        lv_base.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener() {
            @Override
            public void onPullToDownRefresh() {
                lv_base.setClickCompleteAndAll();
                pageNum = 1;
                getEquipmentList();
            }

            @Override
            public void onPullToUpRefresh() {
                pageNum = pageNum + 1;
                getEquipmentList();
            }
        });

        btn_see.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (OtherUtils.getInstance().isFastClick(view)) {
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString(ConstantKey.INTENT_KEY_ID, storyId);
                IntentUtil.gotoActivity(GpsMonitorStoryActivity.this, GpsMonitorWarningActivity.class, bundle);

            }
        });
        /** 按钮搜索 */
        txt_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (OtherUtils.getInstance().isFastClick(view)) {
                    return;
                }
                pageNum = 1;
                searchStr = et_search.getText().toString().trim();
                getEquipmentList();
                et_search.setText("");
            }
        });

        /** 回车搜索 */
        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //                    if (mSign == CONTAINER_SIGN || mSign == SEARCH_SIGN) {
                    //                        showFootView(0);
                    //                        getEquipmentList(true, true, orgId, mSearchView.getText().toString().trim(), 1);
                    //                    } else if (mSign == CALL_POLICE_SIGN) {
                    //                        showFootView(0);
                    //                        p.getAlarmList(true, true, orgId, mSearchView.getText().toString().trim(), -1, 1);
                    //                    }
                    pageNum = 1;
                    searchStr = et_search.getText().toString().trim();
                    getEquipmentList();
                    et_search.setText("");
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * 超级管管理员GPS监控获取门店下设备列表
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-11-25,上午10:38:53
     * <br> UpdateTime: 2016-11-25,上午10:38:53
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     */
    private void getEquipmentList() {
        if (pageNum == 1) {
            lv_base.setClickCompleteAndAll();
        }

        RequestExecutor.addTask(new BaseTask(GpsMonitorStoryActivity.this, getString(R.string.process_handle_wait), false) {

            @Override
            public ResponseBean sendRequest() {
                if (isAdmin) {
                    return GpsMonitorBiz.getEquipmentListForSuperManager(pageNum + "", searchStr, storyId);
                } else {
                    return GpsMonitorBiz.getEquipmentList(pageNum + "", searchStr);
                }
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
                ToastUtil.showToast(GpsMonitorStoryActivity.this, result.getInfo());
            }
        });
    }
}