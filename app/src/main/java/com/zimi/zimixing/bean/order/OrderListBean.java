package com.zimi.zimixing.bean.order;


import com.zimi.zimixing.bean.BaseBean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 订单列表
 * <p>
 * <br> Author: 叶青
 * <br> Version: 6.0.0
 * <br> Date: 2018/1/11
 * <br> Copyright: Copyright © 2017 xTeam Technology. All rights reserved.
 */
public class OrderListBean extends BaseBean {

    //"processStatus": "门店复核",
    //"licencePlate": "忘Ajxj11",
    //"businessId": "DYTAPI-BIZ-20180109121451954",
    //"custId": "CUST201801091215283821456542",
    //"isEditType": 5,
    //"customerName": "苹果",
    //"carModel": "Jdkdj"
    private String processStatus = "";
    private String licencePlate = "";
    private String customerName = "";
    private String carModel = "";
    private String businessId = "";
    private String custId = "";
    private String isEditType = "";

    @Override
    protected void init(JSONObject jSon) throws JSONException {
        processStatus = jSon.optString("processStatus", "");
        licencePlate = jSon.optString("licencePlate", "");
        businessId = jSon.optString("businessId", "");
        custId = jSon.optString("custId", "");
        isEditType = jSon.optString("isEditType", "");
        customerName = jSon.optString("customerName", "");
        carModel = jSon.optString("carModel", "");
    }

    public String getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(String processStatus) {
        this.processStatus = processStatus;
    }

    public String getLicencePlate() {
        return licencePlate;
    }

    public void setLicencePlate(String licencePlate) {
        this.licencePlate = licencePlate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getIsEditType() {
        return isEditType;
    }

    public void setIsEditType(String isEditType) {
        this.isEditType = isEditType;
    }


}
