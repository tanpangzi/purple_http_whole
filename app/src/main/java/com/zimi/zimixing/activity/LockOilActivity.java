package com.zimi.zimixing.activity;


import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import com.zimi.zimixing.BaseApplication;
import com.zimi.zimixing.R;
import com.zimi.zimixing.bean.ResponseBean;
import com.zimi.zimixing.biz.InstallBiz;
import com.zimi.zimixing.executor.BaseTask;
import com.zimi.zimixing.executor.RequestExecutor;
import com.zimi.zimixing.utils.ToastUtil;
import com.zimi.zimixing.widget.TitleViewPurple;

/**
 * 锁油断电模式
 * created by tanjun
 */
public class LockOilActivity extends BaseActivity implements View.OnClickListener {

    TitleViewPurple title_view; //标题
    RadioButton rb_lock; //锁油断电
    RadioButton rb_restore; //恢复

    Button btn_confirm;//确认按钮

    RelativeLayout rl_lock; //锁油断电 0
    RelativeLayout rl_restore; //恢复 1

    /** 指令 */
    private String instruction = "-1";
    /** imei */
    private String imei;

    private String userId;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_lock_oil;
    }

    @Override
    protected void findViews() {
        title_view = findViewByIds(R.id.title_view);
        rl_lock = findViewByIds(R.id.rl_lock);
        rl_restore = findViewByIds(R.id.rl_restore);
        rb_lock = findViewByIds(R.id.rb_lock);
        rb_restore = findViewByIds(R.id.rb_restore);
        btn_confirm = findViewByIds(R.id.btn_confirm);
    }

    @Override
    protected void initGetData() {
        userId = BaseApplication.getInstance().getUserInfoBean().getUserId();
    }

    @Override
    protected void init() {
        title_view.setLeftBtnImg();
        title_view.setTitle(R.string.lock_oil_elect);
    }

    @Override
    protected void widgetListener() {
        rl_lock.setOnClickListener(this);
        rl_restore.setOnClickListener(this);

        rb_restore.setOnClickListener(this);
        rb_lock.setOnClickListener(this);

        btn_confirm.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_lock:
                rb_restore.setChecked(false);
                if (rb_lock.isChecked()){
                    rb_lock.setChecked(false);
                }else {
                    rb_lock.setChecked(true);
                }
                break;

            case R.id.rl_restore:
                rb_lock.setChecked(false);
                if (rb_restore.isChecked()){
                    rb_restore.setChecked(false);
                }else {
                    rb_restore.setChecked(true);
                }

                break;

            case R.id.btn_confirm:
                if (!rb_lock.isChecked() && !rb_restore.isChecked()){
                    ToastUtil.showToast(LockOilActivity.this, getString(R.string.empty_operation));
                    return;
                }

                if (rb_lock.isChecked()){
                    instruction = "0";
                }else if (rb_restore.isChecked()){
                    instruction = "1";
                }

                lockOil(imei, instruction, userId);

                break;

            case R.id.rb_lock:
                rb_restore.setChecked(false);
                if (rb_lock.isChecked()){
                    rb_lock.setChecked(false);
                }else {
                    rb_lock.setChecked(true);
                }
                break;

            case R.id.rb_restore:
                rb_lock.setChecked(false);
                if (rb_restore.isChecked()){
                    rb_restore.setChecked(false);
                }else {
                    rb_restore.setChecked(true);
                }
                break;
        }
    }

    /** 指令 */
    private void lockOil(final String imei, final String instruction, final String userId){
        RequestExecutor.addTask(new BaseTask(LockOilActivity.this, "正在加载中", false) {
            @Override
            public ResponseBean sendRequest() {
                return InstallBiz.lockOilInstruction(imei, instruction, userId);
            }

            @Override
            public void onSuccess(ResponseBean result) {
                ToastUtil.showToast(LockOilActivity.this, result.getInfo());
            }

            @Override
            public void onFail(ResponseBean result) {
                ToastUtil.showToast(LockOilActivity.this, result.getInfo());
            }
        });
    }

}
