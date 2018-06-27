package com.zimi.zimixing.fragment.gpsMonitor;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.zimi.zimixing.R;
import com.zimi.zimixing.activity.gpsMonitor.old.LocationSettingActivity;
import com.zimi.zimixing.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 设备定位权限配置 设置
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2018/1/18
 * <br> Copyright: Copyright © 2017 xTeam Technology. All rights reserved. 
 */
public class SettingFragment extends BaseFragment {

    @BindView(R.id.location_setting_device)
    RelativeLayout locationSettingDevice;

    private LocationSettingActivity mActivity;

    @BindView(R.id.location_setting_updata_time)
    LinearLayout ll;

    private int type;

    @Override
    protected int getContentViewId() {
        return R.layout.setting_fragment;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this, viewParent);
    }

    @Override
    public void initGetData() {
        type = getArguments().getInt("type", -1);
    }

    @Override
    protected void init() {
        mActivity = (LocationSettingActivity) getActivity();
    }

    @Override
    protected void widgetListener() {

    }

    @OnClick({R.id.location_setting_updata_time, R.id.location_setting_device})
    public void onClick(View v) {
        if (v.getId() == R.id.location_setting_updata_time) {
            mActivity.showModifyUploadTimeFragment();
        } else if (v.getId() == R.id.location_setting_device) {
            mActivity.showDeviceLocationAuthorityFragment();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //         * 0-有线，1-无线，2-OBD
        if (1 == type) {
            ll.setVisibility(View.VISIBLE);
        } else {
            ll.setVisibility(View.GONE);
        }
    }
}
