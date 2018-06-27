package com.zimi.zimixing.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.zimi.zimixing.BaseApplication;
import com.zimi.zimixing.R;
import com.zimi.zimixing.activity.gpsMonitor.GPSNaviFirstActivity;
import com.zimi.zimixing.bean.ResponseBean;
import com.zimi.zimixing.bean.UserInfoBean;
import com.zimi.zimixing.bean.UserInfoLoginBean;
import com.zimi.zimixing.biz.UserBiz;
import com.zimi.zimixing.configs.RequestCode;
import com.zimi.zimixing.executor.BaseTask;
import com.zimi.zimixing.executor.RequestExecutor;
import com.zimi.zimixing.utils.IntentUtil;
import com.zimi.zimixing.utils.KeyboardUtil;
import com.zimi.zimixing.utils.OtherUtils;
import com.zimi.zimixing.utils.ToastUtil;
import com.zimi.zimixing.utils.http.HttpRequestCallBack;
import com.zimi.zimixing.widget.DYTEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 登录界面
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class LoginActivity extends BaseActivity {

     /** 用户名*/
    @BindView(R.id.et_login_username)
    DYTEditText etLoginUsername;

    /** 密码*/
    @BindView(R.id.et_login_password)
    DYTEditText etLoginPassword;

     /** 登陆按钮*/
    @BindView(R.id.btn_login_submit)
    Button btnLoginSubmit;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_login;
    }


    @OnClick(R.id.btn_login_submit)
    public void onViewClicked(View v) {
        if (checkData() && !OtherUtils.getInstance().isFastClick(v)) {
            KeyboardUtil.hideKeyBord(viewParent);
            login();
            // login1();
        }
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
    }

    @Override
    public void initGetData() {


    }

    @Override
    protected void onResume() {
        UserInfoLoginBean userInfoBean = BaseApplication.getInstance().getUserInfoBean();
        if (userInfoBean != null) {
            if (!TextUtils.isEmpty(userInfoBean.getUserName())) {
                etLoginUsername.setText(userInfoBean.getUserName());
            }

        }
        super.onResume();
    }

    @Override
    protected void init() {
        setButtonBg(etLoginUsername);
        setButtonBg(etLoginPassword);
    }

    @Override
    protected void widgetListener() {

    }

    /**
     * 校验数据
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016/5/25 20:52
     * <br> UpdateTime: 2016/5/25 20:52
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     */
    private boolean checkData() {
        String account = etLoginUsername.getText().toString().trim();
        //        账号
        if (TextUtils.isEmpty(account)) {
            ToastUtil.showToast(this, getString(R.string.account_empty_error));
            return false;
        }

        //        密码
        if (TextUtils.isEmpty(etLoginPassword.getText().toString())) {
            ToastUtil.showToast(this, getString(R.string.password_empty_error));
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 注册界面
        if (requestCode == RequestCode.REQUEST_CODE_REGISTER && resultCode == RESULT_OK) {
            //            && data != null
            finish();
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

    /**
     * 检测更新
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-11-25,上午10:38:53
     * <br> UpdateTime: 2016-11-25,上午10:38:53
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     */
    private void login() {
        RequestExecutor.addTask(new BaseTask(LoginActivity.this, getString(R.string.process_handle_wait), false) {

            @Override
            public ResponseBean sendRequest() {
                return UserBiz.login(etLoginUsername.getText().toString().trim(), etLoginPassword.getText().toString().trim());
            }

            @Override
            public void onSuccess(ResponseBean result) {
                //UserInfoBean bean = (UserInfoBean) result.getObject();
                UserInfoLoginBean bean = (UserInfoLoginBean) result.getObject();
                //bean.setUserAccount(edit_account.getText().toString());
                //bean.setPassword(etLoginPassword.getText().toString());
                BaseApplication.getInstance().setUserInfoBean(bean);
                // 实例化极光推送
                //JPushUtil.getInstance(LoginActivity.this).setAlias("123456789amos");
                ToastUtil.showToast(LoginActivity.this, result.getInfo());
                IntentUtil.gotoActivityAndFinish(LoginActivity.this, MainActivity.class);
                //IntentUtil.gotoActivityAndFinish(LoginActivity.this, GPSNaviFirstActivity.class);
            }

            @Override
            public void onFail(ResponseBean result) {
                ToastUtil.showToast(LoginActivity.this, result.getInfo());
            }
        });
    }

    /**
     * created by tanjun
     * 根据输入框的状态设置点击按钮背景
     * @param editText
     */
    private void setButtonBg(EditText editText){
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                btnLoginSubmit.setBackgroundResource(R.drawable.button_backgroud_gray);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0){
                    btnLoginSubmit.setBackgroundResource(R.drawable.button_backgroud_purple);
                }else if (s.length() == 0){
                    btnLoginSubmit.setBackgroundResource(R.drawable.button_backgroud_gray);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


}
