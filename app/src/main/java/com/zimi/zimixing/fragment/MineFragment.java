package com.zimi.zimixing.fragment;

import android.view.View;
import android.widget.TextView;

import com.zimi.zimixing.BaseApplication;
import com.zimi.zimixing.R;
import com.zimi.zimixing.activity.SettingActivity;
import com.zimi.zimixing.utils.IntentUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 我的(最后一页)
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class MineFragment extends BaseFragment {

    /** 名字 */
    @BindView(R.id.activity_main_tv_username)
    TextView activityMainTvUsername;
    /** 头衔 */
    @BindView(R.id.activity_main_tv_usertitle)
    TextView activityMainTvUsertitle;

    /** 设置 */
    @BindView(R.id.tv_setting)
    TextView tvSetting;

    public MineFragment() {
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this, viewParent);
    }

    @Override
    protected void widgetListener() {

    }

    @Override
    protected void init() {
        activityMainTvUsername.setText(BaseApplication.getInstance().getUserInfoBean().getUserName());
    }

    @Override
    public void initGetData() {

    }

    @OnClick({R.id.tv_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_setting:
                IntentUtil.gotoActivity(getActivity(), SettingActivity.class);
                break;
        }
    }

    /**
     * 获取我的数据
     * <p>
     * <p>
     * <br> Version: 3.0.0
     * <br> CreateAuthor: 叶青
     * <br> CreateTime: 2018/1/10 10:36
     * <br> UpdateAuthor: 叶青
     * <br> UpdateTime: 2018/1/10 10:36
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     */
    /*public void mainInfo() {
        RequestExecutor.addTask(new BaseTask() {
            //        RequestExecutor.addTask(new BaseTask(getActivity(), getString(R.string.process_handle_wait), false) {

            @Override
            public ResponseBean sendRequest() {
                return UserBiz.mainInfo();
            }

            @Override
            public void onSuccess(ResponseBean result) {
                String str = (String) result.getObject();
                try {
                    JSONObject jsonObject = new JSONObject(str);
                    String countTotal = jsonObject.optString("countTotal", "0");
                    txtAlreadyApply.setText("已申请" + countTotal + "单");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(ResponseBean result) {
                ToastUtil.showToast(getActivity(), result.getInfo());
            }
        });
    }*/

}
