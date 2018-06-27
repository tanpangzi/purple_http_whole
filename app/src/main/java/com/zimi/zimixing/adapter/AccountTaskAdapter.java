package com.zimi.zimixing.adapter;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zimi.zimixing.R;
import com.zimi.zimixing.activity.zmxGpsInstall.ZMXCarInfoActivity;
import com.zimi.zimixing.bean.AccountTaskBean;
import com.zimi.zimixing.bean.ResponseBean;
import com.zimi.zimixing.biz.UserBiz;
import com.zimi.zimixing.configs.Constant;
import com.zimi.zimixing.executor.BaseTask;
import com.zimi.zimixing.executor.RequestExecutor;
import com.zimi.zimixing.utils.ComUtil;
import com.zimi.zimixing.utils.IntentUtil;
import com.zimi.zimixing.utils.ToastUtil;
import com.zimi.zimixing.utils.dialog.CustomDialog;
import com.zimi.zimixing.utils.dialog.DialogUtil;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/1/11.
 * 车辆评估 Gps安装 车辆抵押 共同数据 适配器
 */

public class AccountTaskAdapter extends SimpleBaseAdapter<AccountTaskBean.ReturnListBean>{

    /** 待办是0 已办是1 */
    int isDone;
    /** 车辆评估3 gps安装4 车辆抵押5 界面区别*/
    int fromType;
    private Context mContext;

    String taskId;
    String is_sign;
    String custName; //客户名
    String carNum;//车牌号
    String carBrand;//车辆品牌
    String surfaceDesc;//车辆表面情况
    String assessment_price;// 评估价
    String is_accident_apply_car;// 是否事故车
    String isCounterProduct;  //新老产品的判断 1是老产品 0是新产品
    String businessId;
    String process_instance_id;

    public AccountTaskAdapter(Context context, int isDone, int fromType,ArrayList<AccountTaskBean.ReturnListBean> datas) {
        super(context, datas);
        this.mContext = context;
        this.isDone = isDone;
        this.fromType = fromType;
    }

    @Override
    public int getItemResource() {
        return R.layout.task_item;
    }

    @Override
    public View getItemView(final int position, View convertView, ViewHolder holder) {

        LinearLayout ll_container = holder.getView(R.id.ll_container);
        TextView tv_name = holder.getView(R.id.tv_name); //客户名
        TextView tv_car_num = holder.getView(R.id.tv_car_num); //车牌号
        TextView tv_model = holder.getView(R.id.tv_model); //车辆类型 比如 奥迪A6L
        Button btn_check = holder.getView(R.id.btn_check); //签收按钮

        final AccountTaskBean.ReturnListBean bean = dataList.get(position);
        is_sign = bean.getIs_not_sign(); //是否已签收 0已签收 1未签收
        businessId = bean.getBusiness_id();
        process_instance_id = bean.getProcess_instance_id();
        taskId = bean.getTask_id(); //taskId
        custName = bean.getCust_name();
        carNum = bean.getPlate_no();
        carBrand = bean.getCar_brand();
        surfaceDesc = bean.getSurface_desc();
        assessment_price = bean.getAssessment_price();
        is_accident_apply_car = bean.getIs_accident_apply_car();
        isCounterProduct = bean.getIsCounterProduct();//新老产品的判断 1是老产品 0是新产品

        //setBundle(position);

        tv_name.setText(custName);
        tv_car_num.setText(carNum);
        tv_model.setText(carBrand);

        // 待办是0 已办是1
        if (isDone == Constant.WAIT_DO){
            btn_check.setVisibility(View.VISIBLE);
        }else if (isDone == Constant.ALREADY_DONE){
            btn_check.setVisibility(View.INVISIBLE);
        }

        // 0已签收 1未签收
        if (is_sign.equals("1")){
            btn_check.setVisibility(View.VISIBLE);
            btn_check.setText(mContext.getString(R.string.not_sign));
        }else if (is_sign.equals("0")){
            btn_check.setVisibility(View.INVISIBLE);
        }


        /**
         * 签收按钮
         */
        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSignDialog(dataList.get(position).getTask_id());
            }
        });

        /**
         * 只有待办且是已签收才能点击
         */
        if (isDone == Constant.WAIT_DO && is_sign.equals("0")){
            ll_container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (fromType == Constant.GPS_INSTALL){ //GPS安装
                        ToastUtil.showToast(mContext, "gps安装");
                        if (ComUtil.getPermissionKey("zmxAPI:GPS")){
                            IntentUtil.gotoActivity(mContext, ZMXCarInfoActivity.class);
                        }else {
                            ToastUtil.showToast(mContext, "没有GPS安装权限！");
                        }
                    }
                }
            });
        }

        return convertView;
    }

    /**
     * 是否签收 对话框
     */
    private void getSignDialog(final String taskId) {
        DialogUtil.showMessageDg(mContext, mContext.getString(R.string.if_check), "", mContext.getString(R.string.cancel), mContext.getString(R.string.sure), null, new CustomDialog.OnDialogClickListener() {
            @Override
            public void onClick(CustomDialog dialog, int id, Object object) {
                dialog.dismiss();
                setCheckResult(taskId);
            }
        });
    }

    /**
     * 签收
     * @param taskId
     */
    private void setCheckResult(final String taskId){
        RequestExecutor.addTask(new BaseTask(mContext, mContext.getString(R.string.process_handle_wait), true) {
            @Override
            public ResponseBean sendRequest() {
                return UserBiz.setCheckResult(taskId);
            }

            @Override
            public void onSuccess(ResponseBean result) {
                ToastUtil.showToast(mContext, mContext.getString(R.string.sign_success));
            }

            @Override
            public void onFail(ResponseBean result) {
                ToastUtil.showToast(mContext, result.getInfo());
            }
        });
    }

}
