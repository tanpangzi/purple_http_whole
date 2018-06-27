package com.zimi.zimixing.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.zimi.zimixing.BaseApplication;
import com.zimi.zimixing.R;
import com.zimi.zimixing.bean.ResponseBean;
import com.zimi.zimixing.bean.UserInfoBean;
import com.zimi.zimixing.bean.UserInfoLoginBean;
import com.zimi.zimixing.biz.UserBiz;
import com.zimi.zimixing.executor.BaseTask;
import com.zimi.zimixing.executor.RequestExecutor;
import com.zimi.zimixing.utils.KeyboardUtil;
import com.zimi.zimixing.utils.OtherUtils;
import com.zimi.zimixing.utils.ToastUtil;
import com.zimi.zimixing.widget.DYTEditText;
import com.zimi.zimixing.widget.TitleView;


/**
 * 修改密码
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class UpdatePasswordActivity extends BaseActivity {

    /** 标题栏 */
    private TitleView title_view;
    /** 旧密码 */
    private DYTEditText et_password;
    /** 新密码 */
    private DYTEditText et_password_new;
    /** 确认密码 */
    private DYTEditText et_password_new_re;
    /** 登陆按钮 */
    private Button btn_submit;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_update_password;
    }

    @Override
    protected void findViews() {
        title_view = findViewByIds(R.id.title_view);
        et_password = findViewByIds(R.id.et_password);
        et_password_new = findViewByIds(R.id.et_password_new);
        et_password_new_re = findViewByIds(R.id.et_password_new_re);
        btn_submit = findViewByIds(R.id.btn_submit);
    }


    @Override
    public void initGetData() {
        //        Bundle bundle = this.getIntent().getExtras();
        //        if (bundle != null) {
        //        }
    }

    @Override
    protected void init() {
        title_view.setTitle("修改密码");
    }

    @Override
    protected void widgetListener() {
        title_view.setLeftBtnImg();
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkData() && !OtherUtils.getInstance().isFastClick(v)) {
                    KeyboardUtil.hideKeyBord(viewParent);
                    updatePassword();
                }
            }
        });
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
        String password = et_password.getText().toString();
        if (TextUtils.isEmpty(password)) {
            ToastUtil.showToast(this, "原密码不能为空");
            return false;
        }

        String password_new = et_password_new.getText().toString();
        if (TextUtils.isEmpty(password_new)) {
            ToastUtil.showToast(this, "新密码不能为空");
            return false;
        } else if (password_new.length() < 8) {
            ToastUtil.showToast(this, "新密码长度不得小于8位数");
            return false;
        }

        String password_new_re = et_password_new_re.getText().toString();
        if (TextUtils.isEmpty(password_new_re)) {
            ToastUtil.showToast(this, "重复密码不能为空");
            return false;
        } else if (password_new_re.length() < 8) {
            ToastUtil.showToast(this, "新密码长度不得小于8位数");
            return false;

        }

        if (!password_new.equals(password_new_re)) {
            ToastUtil.showToast(this, "两次输入新密码不一致");
            return false;
        }
        return true;
    }

    /**
     * 修改密码
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-11-25,上午10:38:53
     * <br> UpdateTime: 2016-11-25,上午10:38:53
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     */
    private void updatePassword() {
        RequestExecutor.addTask(new BaseTask(UpdatePasswordActivity.this, getString(R.string.process_handle_wait), false) {

            @Override
            public ResponseBean sendRequest() {
                return UserBiz.updatePassword(et_password.getText().toString(), et_password_new.getText().toString());
            }

            @Override
            public void onSuccess(ResponseBean result) {
                UserInfoLoginBean bean = BaseApplication.getInstance().getUserInfoBean();
                //bean.setPassword(et_password_new.getText().toString());
                BaseApplication.getInstance().setUserInfoBean(bean);
                ToastUtil.showToast(UpdatePasswordActivity.this, result.getInfo());
                finishActivity();
            }

            @Override
            public void onFail(ResponseBean result) {
                ToastUtil.showToast(UpdatePasswordActivity.this, result.getInfo());
            }
        });
    }

}