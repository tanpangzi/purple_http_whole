package com.zimi.zimixing.activity.zmxGpsInstall;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.zimi.zimixing.BaseApplication;
import com.zimi.zimixing.R;
import com.zimi.zimixing.activity.BaseActivity;
import com.zimi.zimixing.bean.CardBean;
import com.zimi.zimixing.bean.ResponseBean;
import com.zimi.zimixing.biz.InstallBiz;
import com.zimi.zimixing.executor.BaseTask;
import com.zimi.zimixing.executor.RequestExecutor;
import com.zimi.zimixing.interf.OnDialogViewCallBack;
import com.zimi.zimixing.utils.ScreenUtil;
import com.zimi.zimixing.utils.ToastUtil;
import com.zimi.zimixing.utils.dialog.CustomDialog;
import com.zimi.zimixing.view.dialog.AccidentWheelView4Dialog;
import com.zimi.zimixing.widget.TitleView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/1/26.
 * 锁油断电
 */

public class LockOilElecActivity extends BaseActivity implements View.OnClickListener {

    private List<CardBean> lockItem = new ArrayList<>(); //事故车选项

    private TitleView title_view;
    private RelativeLayout rl_lock;
    private TextView tv_lock_status;
    private Button btn_confirm;

    String imeiId;
    String uId;
    String strLock;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_lockoil;
    }

    @Override
    protected void findViews() {
        title_view = (TitleView) findViewById(R.id.title_view);
        rl_lock = (RelativeLayout) findViewById(R.id.rl_lock);
        tv_lock_status = (TextView) findViewById(R.id.tv_lock_status);
        btn_confirm = (Button) findViewById(R.id.btn_confirm);
    }

    @Override
    protected void initGetData() {
        Bundle bundle = getIntent().getExtras();
        imeiId = bundle.getString("imeiId");
        uId = BaseApplication.getInstance().getUserInfoBean().getUserId();//userId
    }

    @Override
    protected void init() {
        title_view.setTitle(R.string.title_lock_oil_elect);
        title_view.setLeftBtnImg();
        lockItem.add(new CardBean("0", "锁油/断电"));
        lockItem.add(new CardBean("1", "恢复"));
    }

    @Override
    protected void widgetListener() {
        rl_lock.setOnClickListener(this);
        btn_confirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rl_lock:
                showWheelAccidentCar();
                break;
            case R.id.btn_confirm:
                if (TextUtils.isEmpty(tv_lock_status.getText().toString().trim())){
                    ToastUtil.showToast(this, "请选择操作方式");
                    return;
                }else {
                    lockOil(imeiId, strLock, uId);
                }

                break;
        }
    }

    /**
     * 锁油断电
     * @param imeiId
     * @param strLock
     * @param uId
     */
    private void lockOil(final String imeiId, final String strLock, final String uId){
        RequestExecutor.addTask(new BaseTask(LockOilElecActivity.this, "正在加载中",false) {
            @Override
            public ResponseBean sendRequest() {
                return InstallBiz.lockOilInstruction(imeiId, strLock, uId);
            }

            @Override
            public void onSuccess(ResponseBean result) {
                ToastUtil.showToast(LockOilElecActivity.this, result.getInfo());
            }

            @Override
            public void onFail(ResponseBean result) {
                ToastUtil.showToast(LockOilElecActivity.this, result.getInfo());
            }
        });
    }

    /**
     * 是否事故车弹出框
     */
    CustomDialog dialog;
    AccidentWheelView4Dialog accidentWheelView4Dialog;
    private void showWheelAccidentCar(){
        if (lockItem.size() <= 0){
            return;
        }

        ArrayList<String> dataList = new ArrayList<>();
        int selItem = 0;
        for (int i = 0; i < lockItem.size(); i++) {
            dataList.add(lockItem.get(i).getName());
            if (lockItem.get(i).getId().equals(lockItem)){
                selItem = i;

            }
        }

        dialog = new CustomDialog(LockOilElecActivity.this);
        /** 自定义dialog */
        accidentWheelView4Dialog =
                new AccidentWheelView4Dialog(LockOilElecActivity.this, dialog, new OnDialogViewCallBack() {
                    @Override
                    public void onResult(Map<String, Object> map) {
                        int position = (int) map.get("position");
                        tv_lock_status.setText(lockItem.get(position).getName());
                        strLock = String.valueOf(position + 1);
                    }
                }, dataList, selItem);
        dialog.setContentView(accidentWheelView4Dialog);
        dialog.show();
        if (dialog.getWindow() != null){
            WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
            lp.width = ScreenUtil.getScreenWidthPx(); // 设置宽度
            // v lp.height = ScreenUtil.dip2px(WriteBankInfoActivity.this,150); // 设置宽度
            dialog.getWindow().setGravity(Gravity.BOTTOM);
            dialog.getWindow().setAttributes(lp);
        }
    }


}
