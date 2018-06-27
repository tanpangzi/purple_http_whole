package com.zimi.zimixing.fragment;

import android.content.Context;
import android.os.Bundle;

import com.zimi.zimixing.R;
import com.zimi.zimixing.activity.AccountTaskActivity;
import com.zimi.zimixing.adapter.AccountTaskAdapter;
import com.zimi.zimixing.bean.AccountTaskBean;
import com.zimi.zimixing.bean.ResponseBean;
import com.zimi.zimixing.biz.UserBiz;
import com.zimi.zimixing.executor.BaseTask;
import com.zimi.zimixing.executor.RequestExecutor;
import com.zimi.zimixing.utils.ToastUtil;
import com.zimi.zimixing.widget.pullview.PullToRefreshBase;
import com.zimi.zimixing.widget.pullview.PullToRefreshListView;

/**
 * Created by Administrator on 2018/1/11.
 * 车辆评估 Gps安装 车辆抵押 已办 待办 共同的界面
 */

public class AccountTaskFragment extends BaseFragment {

    /** 下拉列表*/
    PullToRefreshListView lv_task;

    /** 用来区分 从车辆估计 GPS安装 车辆抵押*/
    private int mType;

    /**待办是0 已办是1*/
    private int mTabIndex;

    /** 页码 这个接口的页码是从1开始  别的页码可能是从0开始 */
    private int pageIndex = 1;

    private String totalCount;//总数

    public static final String ARG_PAGE = "ARG_PAGE";
    public static final String ARG_TYPE = "ARG_TYPE";

     /** 适配器*/
    private AccountTaskAdapter mAdapter;
    private AccountTaskActivity mActivity;


    /**
     *  type 表示从评估 gsp安装 抵押进来
     *  page 表示从待办0 已办1
     * @return
     */
    public static AccountTaskFragment newInstance(int page, int type){
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_PAGE, page);
        bundle.putInt(ARG_TYPE, type);
        AccountTaskFragment taskFragment = new AccountTaskFragment();
        taskFragment.setArguments(bundle);
        return taskFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (AccountTaskActivity) context;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_task;
    }

    @Override
    protected void findViews() {
        lv_task = findViewByIds(R.id.lv_task);
    }

    @Override
    public void initGetData() {

    }

    @Override
    protected void init() {
        mTabIndex = getArguments().getInt(ARG_PAGE); //待办  已办
        mType = getArguments().getInt(ARG_TYPE); //
        getTaskList(mTabIndex, mType, pageIndex);
    }

    @Override
    protected void widgetListener() {
        lv_task.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener() {
            @Override
            public void onPullToDownRefresh() {
                lv_task.setClickCompleteAndAll();
                pageIndex = 1;
                getTaskList(mTabIndex, mType, 1);
            }

            @Override
            public void onPullToUpRefresh() {
                pageIndex = pageIndex + 1;
                getTaskList(mTabIndex, mType, pageIndex);
            }
        });

    }

    /**
     * 获取任务列表
     * @param isDone 待办是0 已办是1
     * @param fromType
     * @param pageindex
     */
    private void getTaskList(final int isDone, final int fromType, final int pageindex){
        RequestExecutor.addTask(new BaseTask(mActivity, getString(R.string.process_handle_wait), true) {
            @Override
            public ResponseBean sendRequest() {
                return UserBiz.queryTaskInfo(isDone, fromType, pageindex);
            }

            @Override
            public void onSuccess(ResponseBean result) {
                AccountTaskBean bean = (AccountTaskBean) result.getObject();
                totalCount = ((AccountTaskBean) result.getObject()).getCountTotal();
                mAdapter = new AccountTaskAdapter(mActivity, isDone, fromType, bean.getReturnList());
                lv_task.getRefreshableView().setAdapter(mAdapter);
                lv_task.onRefreshComplete();
            }

            @Override
            public void onFail(ResponseBean result) {
                ToastUtil.showToast(mActivity, result.getInfo());
                lv_task.onRefreshComplete();
            }
        });
    }


}
