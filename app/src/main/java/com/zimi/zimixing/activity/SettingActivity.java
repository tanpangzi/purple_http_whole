package com.zimi.zimixing.activity;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.zimi.zimixing.BaseApplication;
import com.zimi.zimixing.R;
import com.zimi.zimixing.bean.ResponseBean;
import com.zimi.zimixing.bean.UserInfoBean;
import com.zimi.zimixing.bean.UserInfoLoginBean;
import com.zimi.zimixing.biz.UserBiz;
import com.zimi.zimixing.configs.RequestCode;
import com.zimi.zimixing.executor.BaseTask;
import com.zimi.zimixing.executor.RequestExecutor;
import com.zimi.zimixing.utils.AppManagerUtil;
import com.zimi.zimixing.utils.IntentUtil;
import com.zimi.zimixing.utils.OtherUtils;
import com.zimi.zimixing.utils.SystemUtil;
import com.zimi.zimixing.utils.ToastUtil;
import com.zimi.zimixing.utils.UpdateVersionUtil;
import com.zimi.zimixing.utils.dialog.CustomDialog;
import com.zimi.zimixing.utils.dialog.DialogUtil;
import com.zimi.zimixing.widget.TitleView;


/**
 * 设置页面
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class SettingActivity extends BaseActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    /** 标题栏 */
    private TitleView title_view;
    /** 绑定手机 */
    private View txt_bind_phone;
    /** 修改密码 */
    private View txt_edit_password;
    /** 版本号 */
    private TextView txt_version;
    /** 检查更新 */
    private View txt_check_version;
    /** 版本更新工具类 */
    private View btn_login_out;
    /** true则说明未更新完成 */
    private boolean idUpdateAPK = false;
    /** 版本更新工具类 */
    private UpdateVersionUtil updateVersionUtil;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_setting;
    }


    @Override
    protected void findViews() {
        title_view = findViewByIds(R.id.title_view);
        txt_version = findViewByIds(R.id.txt_version);
        txt_bind_phone = findViewByIds(R.id.txt_bind_phone);
        txt_edit_password = findViewByIds(R.id.txt_edit_password);
        txt_check_version = findViewByIds(R.id.txt_check_version);
        btn_login_out = findViewByIds(R.id.btn_login_out);
    }


    @Override
    public void initGetData() {
        //        Bundle bundle = this.getIntent().getExtras();
        //        if (bundle != null) {
        //        }
    }

    @Override
    protected void init() {
        title_view.setTitle("设置");
        updateVersionUtil = new UpdateVersionUtil(this);
        txt_version.setText("V " + SystemUtil.getCurrentVersionName());
    }

    @Override
    protected void widgetListener() {
        title_view.setLeftBtnImg();
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(OtherUtils.getInstance().isFastClick(view)){
                    return;
                }
                switch (view.getId()) {
                    case R.id.txt_bind_phone://
                        ToastUtil.showToast(SettingActivity.this, "功能正在开发中，敬请期待！");
                        break;
                    case R.id.txt_edit_password://
                        IntentUtil.gotoActivity(SettingActivity.this, UpdatePasswordActivity.class);
                        break;
                    case R.id.txt_check_version://
                        updateVersionUtil.checkUpdate();
                        break;
                    case R.id.btn_login_out://
                        DialogUtil.showMessageDg(SettingActivity.this, "提示", "确认要退出当前账号？", getString(R.string.cancel), getString(R.string.sure), null, new CustomDialog.OnDialogClickListener() {
                            @Override
                            public void onClick(CustomDialog dialog, int id, Object object) {
                                dialog.dismiss();
                                loginOut();
                            }
                        });
                        break;
                    default:// 默认
                        break;
                }
            }
        };
        txt_bind_phone.setOnClickListener(listener);
        txt_edit_password.setOnClickListener(listener);
        txt_check_version.setOnClickListener(listener);
        btn_login_out.setOnClickListener(listener);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RequestCode.REQUEST_CODE_INSTALL_APK) {
            idUpdateAPK = true;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    // ***********************************返回键事件处理*********************************
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {
                // 要执行的事件
                finish();
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (updateVersionUtil.isMust() && idUpdateAPK) {
            AppManagerUtil.getAppManager().exitApp();
        }
    }

    private void loginOut() {
        RequestExecutor.addTask(new BaseTask(SettingActivity.this) {
            @Override
            public ResponseBean sendRequest() {
                return UserBiz.loginOut();
            }

            @Override
            public void onSuccess(ResponseBean result) {
                UserInfoLoginBean userInfoBean = BaseApplication.getInstance().getUserInfoBean();
                userInfoBean.setToken("");
                BaseApplication.getInstance().setUserInfoBean(userInfoBean);

                AppManagerUtil.getAppManager().finishAllActivity();
                IntentUtil.gotoActivityToTopAndFinish(SettingActivity.this, LoginActivity.class);
            }

            @Override
            public void onFail(ResponseBean result) {
                ToastUtil.showToast(SettingActivity.this, result.getInfo());
            }
        });
    }

}
