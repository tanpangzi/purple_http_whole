package com.zimi.zimixing.activity.zmxGpsInstall;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.zimi.zimixing.R;
import com.zimi.zimixing.activity.BaseActivity;
import com.zimi.zimixing.bean.CardBean;
import com.zimi.zimixing.bean.OrgnizationBean;
import com.zimi.zimixing.bean.ResponseBean;
import com.zimi.zimixing.biz.InstallBiz;
import com.zimi.zimixing.executor.BaseTask;
import com.zimi.zimixing.executor.RequestExecutor;
import com.zimi.zimixing.interf.OnDialogViewCallBack;
import com.zimi.zimixing.utils.IntentUtil;
import com.zimi.zimixing.utils.ScreenUtil;
import com.zimi.zimixing.utils.ToastUtil;
import com.zimi.zimixing.utils.dialog.CustomDialog;
import com.zimi.zimixing.view.dialog.SingleWheelView4Dialog;
import com.zimi.zimixing.widget.AutoBgTextView;
import com.zimi.zimixing.widget.ContainEmojiEditext;
import com.zimi.zimixing.widget.ContainsEmojiEditText;
import com.zimi.zimixing.widget.TitleView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by tanjun on 2018/3/29.
 * 紫米星GPS安装第一个页面
 */

public class ZMXCarInfoActivity extends BaseActivity implements View.OnClickListener {
    /**
     * 标题
     */
    private TitleView titleView;
    /**
     * 客户姓名
     */
    private ContainEmojiEditext et_consumer_name;
    /**
     * 车牌号
     */
    private ContainEmojiEditext et_car_num;
    private LinearLayout ll_container;

    private AutoBgTextView tv_org;

    /**
     * 用来放所属组织的信息
     */
    private ArrayList<OrgnizationBean> orgDatas = new ArrayList<>();
    /**
     * 组织名称
     */
    private ArrayList<String> strOrg = new ArrayList<>();

    private Button btn_next;

    CustomDialog dialogSingle;

    /**
     * 组织id
     */
    private String strOrgId;

    String custId = "";

    @Override
    protected int getContentViewId() {
        return R.layout.activity_zmxcarinfo;
    }

    @Override
    protected void findViews() {
        titleView = (TitleView) findViewById(R.id.title_view);
        /** 所属组织 */
        tv_org = (AutoBgTextView) findViewById(R.id.tv_org);
        btn_next = (Button) findViewById(R.id.btn_next);
        ll_container = (LinearLayout) findViewById(R.id.ll_container);
        et_consumer_name = (ContainEmojiEditext) findViewById(R.id.et_consumer_name);
        et_car_num = (ContainEmojiEditext) findViewById(R.id.et_car_num);
    }

    @Override
    protected void initGetData() {
        getOrgnization();
    }

    @Override
    protected void init() {
        titleView.setLeftBtnImg();
        titleView.setTitle(R.string.car_info);
    }

    @Override
    protected void widgetListener() {
        ll_container.setOnClickListener(this);
        btn_next.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            /** 所属组织 */
            case R.id.ll_container:
                if (strOrg == null || strOrg.size() <= 0){
                    return;
                } else {
                    showSingleWheelView(new OnDialogViewCallBack() {
                        @Override
                        public void onResult(Map<String, Object> map) {
                            int position = (int) map.get("position");
                            tv_org.setText(strOrg.get(position));
                            /** 获取组织id */
                            strOrgId = orgDatas.get(position).getId();

                        }
                    }, strOrg, 0);
                }

                break;

            /** 下一步 */
            case R.id.btn_next:
                /** 客户名称 */
                String name = et_consumer_name.getText().toString().trim();
                /** 车牌号 */
                String carNum = et_car_num.getText().toString().trim();
                /** 组织 */
                String orgId = tv_org.getText().toString().trim();

                if (TextUtils.isEmpty(name)){
                    ToastUtil.showToast(ZMXCarInfoActivity.this,"请输入客户姓名");
                    return;
                }
                if (TextUtils.isEmpty(carNum)){
                    ToastUtil.showToast(ZMXCarInfoActivity.this,"请输入车牌号");
                    return;
                }
                if (TextUtils.isEmpty(orgId)){
                    ToastUtil.showToast(ZMXCarInfoActivity.this,"请选择门店");
                    return;
                }

                createCustInfo(name, carNum, orgId);

                break;
        }
    }

    /**
     * 获取组织信息
     */
    private void getOrgnization() {
        RequestExecutor.addTask(new BaseTask(this, "正在加载中...", false) {
            @Override
            public ResponseBean sendRequest() {
                return InstallBiz.getOrganization();
            }

            @Override
            public void onSuccess(ResponseBean result) {
                orgDatas = (ArrayList<OrgnizationBean>) result.getObject();
                for (int i = 0; i < orgDatas.size(); i++) {
                    strOrg.add(orgDatas.get(i).getName());
                }

            }

            @Override
            public void onFail(ResponseBean result) {

            }
        });
    }

    /**
     * 提交客户信息
     * @param custName
     * @param carNum
     * @param orgId
     */
    private void createCustInfo(final String custName, final String carNum, final String orgId){
        RequestExecutor.addTask(new BaseTask(this, "正在提交中...", false) {
            @Override
            public ResponseBean sendRequest() {
                return InstallBiz.createCustInfo(custName, carNum, orgId);
            }

            @Override
            public void onSuccess(ResponseBean result) {
                String resultStr = (String) result.getObject();
                try {
                    JSONObject json = new JSONObject(resultStr);
                    custId = json.optString("custId", "");

                    Bundle bundle = new Bundle();
                    bundle.putString("custId", custId);

                    IntentUtil.gotoActivity(ZMXCarInfoActivity.this, GPSDeviceInfoActivity.class, bundle);
                    ZMXCarInfoActivity.this.finishActivity();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(ResponseBean result) {
                ToastUtil.showToast(ZMXCarInfoActivity.this, result.getInfo());
            }
        });
    }

    /**
     * 显示组织信息
     * @param callBack
     * @param arrayList
     * @param position
     */
    private void showSingleWheelView(OnDialogViewCallBack callBack, ArrayList<String> arrayList, int position) {
        if (dialogSingle == null) {
            dialogSingle = new CustomDialog(this);
        }

        if (dialogSingle.isShowing()) {
            dialogSingle.dismiss();
        }
        SingleWheelView4Dialog contentView = new SingleWheelView4Dialog(this, dialogSingle, callBack, arrayList, position);
        dialogSingle.setContentView(contentView);
        dialogSingle.show();
        if (dialogSingle.getWindow() != null) {
            WindowManager.LayoutParams lp = dialogSingle.getWindow().getAttributes();
            lp.width = ScreenUtil.getScreenWidthPx(); // 设置宽度
            // v lp.height = ScreenUtil.dip2px(WriteBankInfoActivity.this,150); // 设置宽度
            dialogSingle.getWindow().setGravity(Gravity.BOTTOM);
            dialogSingle.getWindow().setAttributes(lp);
        }
    }


}
