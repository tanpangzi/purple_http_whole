package com.zimi.zimixing.fragment;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.zimi.zimixing.BaseApplication;
import com.zimi.zimixing.R;
import com.zimi.zimixing.activity.FeedBackActivity;
import com.zimi.zimixing.activity.LockOilActivity;
import com.zimi.zimixing.activity.LoginActivity;
import com.zimi.zimixing.activity.WirelessTraceActivity;
import com.zimi.zimixing.activity.gpsMonitor.GPSNaviFirstActivity;
import com.zimi.zimixing.bean.ResponseBean;
import com.zimi.zimixing.bean.UserInfoLoginBean;
import com.zimi.zimixing.biz.UserBiz;
import com.zimi.zimixing.configs.ConfigFile;
import com.zimi.zimixing.executor.BaseTask;
import com.zimi.zimixing.executor.RequestExecutor;
import com.zimi.zimixing.utils.AppManagerUtil;
import com.zimi.zimixing.utils.FileUtil;
import com.zimi.zimixing.utils.IntentUtil;
import com.zimi.zimixing.utils.ToastUtil;
import com.zimi.zimixing.utils.dialog.CustomDialog;
import com.zimi.zimixing.utils.dialog.DialogUtil;
import com.zimi.zimixing.widget.AutoBgTextView;

import java.io.File;

import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

/**
 * created by tanjun
 * 左侧滑界面
 */
public class LeftNaviFragment extends BaseFragment implements View.OnClickListener {

    LinearLayout ll_photo;
    /** 用户头像 */
    ImageView navAvator;
    /** 用户名 */
    TextView navUsername;
    /** 无线追踪 */
    AutoBgTextView tvWirelessTrace;
    /** 意见反馈 */   /***/
    AutoBgTextView tvFeedback;
    /** 清除缓存 */
    AutoBgTextView tvClearCache;
    /** 缓存大小 */
    TextView tv_cache_count;
    /** 锁油断电 */
    AutoBgTextView tvLockOil;
    /** 用于开启报警信息 */
    ToggleButton btnToggle;
    /** 退出登录 */
    AutoBgTextView tvLogout;

    GPSNaviFirstActivity mActivity;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_navi_left;
    }

    @Override
    protected void findViews() {
        ll_photo = findViewByIds(R.id.ll_photo);
        navAvator = findViewByIds(R.id.nav_avator);
        navUsername = findViewByIds(R.id.nav_username);
        tvWirelessTrace = findViewByIds(R.id.tv_wireless_trace);
        tvFeedback = findViewByIds(R.id.tv_feedback);
        tvClearCache = findViewByIds(R.id.tv_clear_cache);
        tv_cache_count = findViewByIds(R.id.tv_cache_count);
        tvLockOil = findViewByIds(R.id.tv_lock_oil);
        btnToggle = findViewByIds(R.id.btn_toggle);
        tvLogout = findViewByIds(R.id.tv_logout);
    }

    @Override
    public void initGetData() {
        mActivity = (GPSNaviFirstActivity) getActivity();
        navUsername.setText(BaseApplication.getInstance().getUserInfoBean().getUserName());
    }

    @Override
    protected void init() {
    }

    @Override
    protected void widgetListener() {
        ll_photo.setOnClickListener(this);
        tvWirelessTrace.setOnClickListener(this);
        tvFeedback.setOnClickListener(this);
        tvClearCache.setOnClickListener(this);
        tvLockOil.setOnClickListener(this);
        btnToggle.setOnClickListener(this);
        tvLogout.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        getFileSize();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_photo:
                break;
            case R.id.tv_wireless_trace://无线追踪模式
                IntentUtil.gotoActivity(mActivity, WirelessTraceActivity.class);
                break;
            case R.id.tv_feedback:
                IntentUtil.gotoActivity(mActivity, FeedBackActivity.class);
                break;
            case R.id.tv_clear_cache:
                DialogUtil.showMessageDg(mActivity, "提示", "确定要清除缓存？", getString(R.string.cancel), getString(R.string.sure), null, new CustomDialog.OnDialogClickListener() {
                    @Override
                    public void onClick(CustomDialog dialog, int id, Object object) {
                        dialog.dismiss();
                        FileUtil.deleteFolderFile(ConfigFile.PATH_BASE,true);
                        getFileSize();
                        ToastUtil.showToast(mActivity, getString(R.string.clear_cache_success));
                    }
                });

                break;
            case R.id.tv_lock_oil:
                IntentUtil.gotoActivity(mActivity, LockOilActivity.class);
                break;
            case R.id.btn_toggle:
                setToggleButton();
                break;
            case R.id.tv_logout:
                DialogUtil.showMessageDg(mActivity, "提示", "确定要退出当前账号？", getString(R.string.cancel), getString(R.string.sure), null, new CustomDialog.OnDialogClickListener() {
                    @Override
                    public void onClick(CustomDialog dialog, int id, Object object) {
                        dialog.dismiss();
                        loginOut();
                    }
                });
                break;
        }
    }

    /**
     * toggle的控制
     */
    String result;
    private void setToggleButton(){
        btnToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    result = "打开";
                }else {
                    result = "关闭";
                }
                Toast.makeText(mActivity, result, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 退出登录
     */
    private void loginOut() {
        UserInfoLoginBean userInfoBean = BaseApplication.getInstance().getUserInfoBean();
        userInfoBean.setToken("");
        BaseApplication.getInstance().setUserInfoBean(userInfoBean);

        AppManagerUtil.getAppManager().finishAllActivity();
        IntentUtil.gotoActivityToTopAndFinish(mActivity, LoginActivity.class);
        /*RequestExecutor.addTask(new BaseTask(mActivity) {
            @Override
            public ResponseBean sendRequest() {
                return UserBiz.loginOut();
            }

            @Override
            public void onSuccess(ResponseBean result) {
                ToastUtil.showToast(mActivity, result.getInfo());
                UserInfoLoginBean userInfoBean = BaseApplication.getInstance().getUserInfoBean();
                userInfoBean.setToken("");
                BaseApplication.getInstance().setUserInfoBean(userInfoBean);

                AppManagerUtil.getAppManager().finishAllActivity();
                IntentUtil.gotoActivityToTopAndFinish(mActivity, LoginActivity.class);
            }

            @Override
            public void onFail(ResponseBean result) {
                ToastUtil.showToast(mActivity, result.getInfo());
            }
        });*/
    }

    /**
     * 获取缓存大小
     */
    private void getFileSize(){
        File file = new File(ConfigFile.PATH_BASE);
        if (file!=null){
            long size = FileUtil.getFolderSize(file);
            double dSize = Double.parseDouble(size+"");
            tv_cache_count.setText( FileUtil.getFormatSize(dSize));
        }else {
            tv_cache_count.setText( "0M");
        }

    }


}
