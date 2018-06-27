package com.zimi.zimixing.fragment;


import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zimi.zimixing.R;
import com.zimi.zimixing.activity.gpsMonitor.old.RecordShowActivity;
import com.zimi.zimixing.interf.OnDialogViewCallBack;
import com.zimi.zimixing.utils.ComUtils;
import com.zimi.zimixing.utils.DateUtil;
import com.zimi.zimixing.utils.LogUtil;
import com.zimi.zimixing.utils.ScreenUtil;
import com.zimi.zimixing.utils.ToastUtil;
import com.zimi.zimixing.utils.dialog.CustomDialog;
import com.zimi.zimixing.view.dialog.TimeWheelView4Dialog;
import com.zimi.zimixing.view.dialog.TimeWheelView4DialogSecond;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * created by tanjun
 * 轨迹回放里右侧滑
 */
public class RightNaviFragment extends BaseFragment {

    /**
     * 开始时间
     */
    @BindView(R.id.et_start_time)
    TextView etStartTime;
    /**
     * 结束时间
     */
    @BindView(R.id.et_end_time)
    TextView etEndTime;
    /**
     * 取消按钮
     */
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    /**
     * 确定按钮
     */
    @BindView(R.id.btn_confirm)
    Button btnConfirm;

    Unbinder unbinder;

    private static int CUSTOM_TAG = 0;
    private final static int SATR_TIME = 1;
    private final static int STOP_TIME = 2;

    private RecordShowActivity mActivity;

    CustomDialog dialogTime;
    TimeWheelView4DialogSecond contentViewTme;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_navi_right;
    }

    @Override
    protected void findViews() {

    }

    @Override
    public void initGetData() {
        mActivity = (RecordShowActivity) getActivity();
    }

    @Override
    protected void init() {
        initWheelViewTime();

    }

    @Override
    protected void widgetListener() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.et_start_time, R.id.et_end_time, R.id.btn_cancel, R.id.btn_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.et_start_time: //选择开始时间
                ComUtils.closeKeyBorad(mActivity, view);
                CUSTOM_TAG = SATR_TIME;
                showWheelViewTime();
                break;
            case R.id.et_end_time://选择结束时间
                ComUtils.closeKeyBorad(mActivity, view);
                CUSTOM_TAG = STOP_TIME;
                showWheelViewTime();
                break;
            case R.id.btn_cancel: //关闭侧滑
                mActivity.drawer.closeDrawer(GravityCompat.END);

                break;
            case R.id.btn_confirm:
                String starTime = etStartTime.getText().toString().trim();
                String endTime = etEndTime.getText().toString().trim();
                if (TextUtils.isEmpty(starTime)) {
                    ToastUtil.showToast(mActivity, mActivity.getString(R.string.start_time_empty));
                    return;
                }

                if (TextUtils.isEmpty(endTime)) {
                    ToastUtil.showToast(mActivity, mActivity.getString(R.string.end_time_empty));
                    return;
                }

                if (DateUtil.getMillsTime(endTime) > DateUtil.getMillsTime(endTime)) {
                    ToastUtil.showToast(mActivity, mActivity.getString(R.string.start_big_end));
                    return;
                }

                mActivity.drawer.closeDrawer(GravityCompat.END);
                /** 回放 */
                mActivity.playBack(starTime, endTime);

                break;
        }
    }

    /**
     * 合同模版选择器
     */
    private void initWheelViewTime() {
        if (dialogTime == null) {
            dialogTime = new CustomDialog(mActivity);
            contentViewTme = new TimeWheelView4DialogSecond(mActivity, dialogTime, new OnDialogViewCallBack() {
                @Override
                public void onResult(Map<String, Object> map) {
                    String date = (String) map.get("date");
                    LogUtil.i("----->>>>> " + date);
                    if (CUSTOM_TAG == SATR_TIME) {
                        etStartTime.setText((date));
                    } else if (CUSTOM_TAG == STOP_TIME) {
                        etEndTime.setText((date));
                    }
                }
            }, "");
            dialogTime.setContentView(contentViewTme);
        }
    }

    private void showWheelViewTime() {
        if (dialogTime == null) {
            return;
        }
        if (!dialogTime.isShowing()) {
            dialogTime.show();
            if (dialogTime.getWindow() != null) {
                WindowManager.LayoutParams lp = dialogTime.getWindow().getAttributes();
                lp.width = ScreenUtil.getScreenWidthPx(); // 设置宽度
                // v lp.height = ScreenUtil.dip2px(WriteBankInfoActivity.this,150); // 设置宽度
                dialogTime.getWindow().setGravity(Gravity.BOTTOM);
                dialogTime.getWindow().setAttributes(lp);
            }
        }
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return format.format(date);
    }

}
