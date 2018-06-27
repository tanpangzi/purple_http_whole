package com.zimi.zimixing.activity.gpsMonitor;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.zimi.zimixing.R;
import com.zimi.zimixing.activity.BaseActivity;
import com.zimi.zimixing.adapter.ExpandableListViewAdapter;
import com.zimi.zimixing.bean.CompanyGroupListBean;
import com.zimi.zimixing.bean.ResponseBean;
import com.zimi.zimixing.biz.GpsMonitorBiz;
import com.zimi.zimixing.configs.ConstantKey;
import com.zimi.zimixing.executor.BaseTask;
import com.zimi.zimixing.executor.RequestExecutor;
import com.zimi.zimixing.interf.OnDialogViewCallBack;
import com.zimi.zimixing.utils.IntentUtil;
import com.zimi.zimixing.utils.LogUtil;
import com.zimi.zimixing.utils.OtherUtils;
import com.zimi.zimixing.utils.ScreenUtil;
import com.zimi.zimixing.utils.ToastUtil;
import com.zimi.zimixing.utils.dialog.CustomDialog;
import com.zimi.zimixing.view.dialog.SingleWheelView4Dialog;
import com.zimi.zimixing.widget.TitleView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * GPS监控 分组授权管理
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class GroupManageActivity extends BaseActivity {

    /** 标题栏 */
    private TitleView title_view;
    private ExpandableListView expandableListView;
    private ExpandableListViewAdapter adapter;
    private TextView tv_sel_time;
    /** 列表数据 */
    private ArrayList<CompanyGroupListBean> arrayList = new ArrayList<>();
    private String storyId = "";
    // 授权时长
    private String time = "";//默认值

    @Override
    protected int getContentViewId() {
        return R.layout.activity_manage_group;
    }

    @Override
    protected void findViews() {
        title_view = findViewByIds(R.id.title_view);
        expandableListView = findViewByIds(R.id.expandable_list);
        tv_sel_time = findViewByIds(R.id.tv_sel_time);
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
        title_view.setTitle("分组授权管理");

        adapter = new ExpandableListViewAdapter(this, arrayList);
        expandableListView.setAdapter(adapter);

        queryCompanyRelUser();
    }

    @Override
    protected void widgetListener() {
        title_view.setLeftBtnImg();
        title_view.setRightBtnTxt("提交", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (OtherUtils.getInstance().isFastClick(view)) {
                    return;
                }

                String timeLen = tv_sel_time.getText().toString().trim();
                if (TextUtils.isEmpty(timeLen)) {
                    ToastUtil.showToast(GroupManageActivity.this, "请选择授权时间");
                    return;
                }

                StringBuilder checkName = new StringBuilder();
                List<String> checkedChildren = adapter.getCheckedChildren();
                if (checkedChildren != null && !checkedChildren.isEmpty()) {
                    for (String string : checkedChildren) {
                        if (!TextUtils.isEmpty(string)) {
                            checkName.append(",");
                            checkName.append(string);
                        }
                    }
                }

                String content = checkName.toString().replaceFirst(",", "");
                if (TextUtils.isEmpty(checkName.toString())) {
                    ToastUtil.showToast(GroupManageActivity.this, "请选择要授权的员工");
                } else {
                    LogUtil.i(content);
                    submit(content);
                }
            }
        });
        tv_sel_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (OtherUtils.getInstance().isFastClick(view)) {
                    return;
                }
                final ArrayList<String> dataList = new ArrayList<>();
                //                timeItem.add(new CardBean(24*2 + "", "两天"));
                //                timeItem.add(new CardBean(24*3 + "", "三天"));
                //                timeItem.add(new CardBean(24*7 + "", "一周"));
                //                timeItem.add(new CardBean(24*30 + "", "一月"));
                int selectItem = 0;
                dataList.add("两天");
                dataList.add("三天");
                dataList.add("一周");
                dataList.add("一月");
                CustomDialog dialog = new CustomDialog(GroupManageActivity.this);
                SingleWheelView4Dialog contentView = new SingleWheelView4Dialog(GroupManageActivity.this, dialog, new OnDialogViewCallBack() {
                    @Override
                    public void onResult(Map<String, Object> map) {
                        int position = (int) map.get("position");
                        tv_sel_time.setText(dataList.get(position));
                        int date = 0;
                        switch (position) {
                            case 0://
                                date = 2;
                                break;
                            case 1://
                                date = 3;
                                break;
                            case 2://
                                date = 7;
                                break;
                            case 3://
                                date = 30;
                                break;
                            default:// 默认
                                break;
                        }
                        time = String.valueOf(24 * date);
                    }
                }, dataList, selectItem);
                dialog.setContentView(contentView);

                dialog.show();
                if (dialog.getWindow() != null) {
                    WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
                    lp.width = ScreenUtil.getScreenWidthPx(); // 设置宽度
                    // v lp.height = ScreenUtil.dip2px(WriteBankInfoActivity.this,150); // 设置宽度
                    dialog.getWindow().setGravity(Gravity.BOTTOM);
                    dialog.getWindow().setAttributes(lp);
                }
            }
        });

    }

    /**
     * 超级管管理员GPS监控获取 分组授权
     * <p>
     * <br> Version: 6.0.0
     * <br> CreateAuthor: 叶青
     * <br> CreateTime: 2018/1/4 16:43
     * <br> UpdateAuthor: 叶青
     * <br> UpdateTime: 2018/1/4 16:43
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     */
    private void queryCompanyRelUser() {

        RequestExecutor.addTask(new BaseTask(GroupManageActivity.this, getString(R.string.process_handle_wait), false) {

            @Override
            public ResponseBean sendRequest() {
                return GpsMonitorBiz.queryCompanyRelUser();
            }

            @Override
            public void onSuccess(ResponseBean result) {
                ArrayList<CompanyGroupListBean> list = (ArrayList<CompanyGroupListBean>) result.getObject();
                arrayList.clear();
                arrayList.addAll(list);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFail(ResponseBean result) {
                ToastUtil.showToast(GroupManageActivity.this, result.getInfo());
            }
        });
    }

    /**
     * 提交
     * <p>
     * <br> Version: 6.0.0
     * <br> CreateAuthor: 叶青
     * <br> CreateTime: 2018/1/4 16:43
     * <br> UpdateAuthor: 叶青
     * <br> UpdateTime: 2018/1/4 16:43
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     */
    private void submit(final String content) {

        RequestExecutor.addTask(new BaseTask(GroupManageActivity.this, getString(R.string.process_handle_wait), false) {

            @Override
            public ResponseBean sendRequest() {
                return GpsMonitorBiz.storeAuthorize(content, time, storyId);
            }

            @Override
            public void onSuccess(ResponseBean result) {
                ToastUtil.showToast(GroupManageActivity.this, "授权成功");
                IntentUtil.gotoActivityToTop(GroupManageActivity.this, GpsMonitorHomeActivity.class);
            }

            @Override
            public void onFail(ResponseBean result) {
                ToastUtil.showToast(GroupManageActivity.this, "分组授权失败");
            }
        });
    }
}