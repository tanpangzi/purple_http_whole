package com.zimi.zimixing.fragment.gpsMonitor;

import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.zimi.zimixing.R;
import com.zimi.zimixing.activity.gpsMonitor.old.DeviceLocationAdapter;
import com.zimi.zimixing.activity.gpsMonitor.old.LocationSettingActivity;
import com.zimi.zimixing.bean.ResponseBean;
import com.zimi.zimixing.bean.UserListBean;
import com.zimi.zimixing.biz.GpsMonitorBiz;
import com.zimi.zimixing.executor.BaseTask;
import com.zimi.zimixing.executor.RequestExecutor;
import com.zimi.zimixing.fragment.BaseFragment;
import com.zimi.zimixing.utils.ToastUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 选择授权人页面
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2018/1/18
 * <br> Copyright: Copyright © 2017 xTeam Technology. All rights reserved.
 */
public class DeviceLocationAuthorityFragment extends BaseFragment {

    @BindView(R.id.lv_base)
    ListView lv_base;
    @BindView(R.id.device_l_a_ll)
    LinearLayout ll;
    @BindView(R.id.device_l_a_name)
    TextView name;

    private LocationSettingActivity mActivity;
    private DeviceLocationAdapter mAdapter;
    private String mEquipmentId;
    private String imeiId;

    @Override
    protected int getContentViewId() {
        return R.layout.device_location_authority;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this, viewParent);
    }

    @Override
    public void initGetData() {
        imeiId = getArguments().getString("imeiId");
    }

    @Override
    protected void init() {
        mActivity = (LocationSettingActivity) getActivity();
    }

    @Override
    protected void widgetListener() {

    }

    @OnClick({R.id.device_l_a_rl, R.id.device_l_a_bt})
    public void onClick(View v) {
        if (v.getId() == R.id.device_l_a_rl) {
            //            p.getqueryAllUser();
            queryAllUser();
        } else if (v.getId() == R.id.device_l_a_bt) {
            if (TextUtils.isEmpty(mEquipmentId) || TextUtils.isEmpty(imeiId)) {
                ToastUtil.showToast(getContext(),"请选择被`授权人");
            } else {
                equipmentAuthorize(mEquipmentId, imeiId);
            }
        }
    }

    private void equipmentAuthorize(final String userName, final String equipmentId) {
        RequestExecutor.addTask(new BaseTask(getActivity()) {
            @Override
            public ResponseBean sendRequest() {
                return GpsMonitorBiz.equipmentAuthorize(userName, equipmentId);
            }

            @Override
            public void onSuccess(ResponseBean result) {
                mActivity.finish();
                ToastUtil.showToast(getContext(),"授权成功！");
            }

            @Override
            public void onFail(ResponseBean result) {
                ToastUtil.showToast(getActivity(), result.getInfo());
            }
        });
    }

    private void queryAllUser() {
        RequestExecutor.addTask(new BaseTask(getActivity()) {
            @Override
            public ResponseBean sendRequest() {
                return GpsMonitorBiz.queryAllUser();
            }

            @Override
            public void onSuccess(ResponseBean result) {
                ArrayList<UserListBean> arrayList = (ArrayList<UserListBean>) result.getObject();

                if (arrayList != null && arrayList.size() > 0) {
                    ll.setVisibility(View.GONE);
                    lv_base.setVisibility(View.VISIBLE);
                    mActivity.setTitleStr("人员选择");
                    mAdapter = new DeviceLocationAdapter(getContext(), arrayList,
                            new DeviceLocationAdapter.DeviceLocationCallback() {
                                @Override
                                public void onCallback(String userChnName, String equipmentId) {
                                    mActivity.setTitleStr("开启设备定位权限");
                                    ll.setVisibility(View.VISIBLE);
                                    lv_base.setVisibility(View.GONE);
                                    mEquipmentId = equipmentId;
                                    name.setText(userChnName);

                                }
                            });
                    lv_base.setAdapter(mAdapter);
                } else {
                    ToastUtil.showToast(getActivity(),"沒有数据！");
                }
            }

            @Override
            public void onFail(ResponseBean result) {
                ToastUtil.showToast(getActivity(), result.getInfo());
            }
        });
    }
}
