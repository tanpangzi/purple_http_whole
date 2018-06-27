package com.zimi.zimixing.fragment.gpsMonitor;

import android.app.Dialog;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.zimi.zimixing.R;
import com.zimi.zimixing.activity.gpsMonitor.old.LocationSettingActivity;
import com.zimi.zimixing.bean.ResponseBean;
import com.zimi.zimixing.biz.GpsMonitorBiz;
import com.zimi.zimixing.executor.BaseTask;
import com.zimi.zimixing.executor.RequestExecutor;
import com.zimi.zimixing.fragment.BaseFragment;
import com.zimi.zimixing.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 更新授权时间
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2018/1/18
 * <br> Copyright: Copyright © 2017 xTeam Technology. All rights reserved. 
 */
public class ModifyUploadTimeFragment extends BaseFragment {

    private Dialog mDialog;
    private int type;
    private String imeiId;
    private int state = -1;

    private int Semih = 0;
    private int oneHours = 1;
    private int towHours = 2;

    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.image)
    ImageView iv;
    private LocationSettingActivity mActivity;


    @Override
    protected int getContentViewId() {
        return R.layout.modify_upload_time_fragment;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this, viewParent);
    }

    @Override
    public void initGetData() {
        type = getArguments().getInt("type", -1);
        imeiId = getArguments().getString("imeiId");
    }

    @Override
    protected void init() {
        mActivity = (LocationSettingActivity) getActivity();
    }

    @Override
    protected void widgetListener() {

    }

    @OnClick({R.id.modify_upload_rl, R.id.modify_upload_bt})
    public void onClick(View v) {
        if (v.getId() == R.id.modify_upload_rl) {
            if (type == 1) {
                showDialog();
            } else {
                ToastUtil.showToast(getContext(),"只有无线设备才可使用");
            }
        } else if (v.getId() == R.id.modify_upload_bt) {
            String timeLenth = time.getText().toString().trim();
            if (TextUtils.isEmpty(timeLenth)) {
                ToastUtil.showToast(getContext(),"请选择时间");
            } else {
                updateUploadTime(imeiId, state);
            }
        }
    }

    private void showDialog() {
        if (mDialog == null) {
            mDialog = new Dialog(getContext(), R.style.dialog_bottom_full);//.create();

            Window window = mDialog.getWindow();
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.popupAnimation);
            View view = View.inflate(getContext(), R.layout.bottom_dailog, null);

            TextView tv1 = (TextView) view.findViewById(R.id.bottom_tv1);
            TextView tv2 = (TextView) view.findViewById(R.id.bottom_tv2);
            TextView tv3 = (TextView) view.findViewById(R.id.bottom_tv3);

            tv1.setText("半小时");
            tv2.setText("1小时");
            tv3.setText("2小时");
            tv3.setTextColor(getContext().getResources().getColor(R.color.font_content));
            view.findViewById(R.id.iv_userinfo_takepic).setBackgroundColor(Color.WHITE);
            view.findViewById(R.id.iv_userinfo_takepic).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    state = Semih;
                    iv.setVisibility(View.GONE);
                    time.setText("半小时");
                    mDialog.dismiss();
                }
            });
            view.findViewById(R.id.iv_userinfo_choosepic).setBackgroundColor(Color.WHITE);
            view.findViewById(R.id.iv_userinfo_choosepic).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    state = oneHours;
                    time.setText("1小时");
                    iv.setVisibility(View.GONE);
                    mDialog.dismiss();
                }
            });
            view.findViewById(R.id.iv_userinfo_cancle).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    state = towHours;
                    time.setText("2小时");
                    iv.setVisibility(View.GONE);
                    mDialog.dismiss();
                }
            });
            window.setContentView(view);
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);//设置横向全屏
            mDialog.setCanceledOnTouchOutside(true);
            mDialog.setCancelable(true);
        }
        mDialog.show();
    }


    private void updateUploadTime(final String imeiId, final int type) {
        RequestExecutor.addTask(new BaseTask(getActivity()) {
            @Override
            public ResponseBean sendRequest() {
                return GpsMonitorBiz.updateUploadTime(imeiId, type + "");
            }

            @Override
            public void onSuccess(ResponseBean result) {
                mActivity.finish();
                //                ToastUtil.showToast(getContext(),"授权成功！");
            }

            @Override
            public void onFail(ResponseBean result) {
                ToastUtil.showToast(getActivity(), result.getInfo());
            }
        });
    }

}
