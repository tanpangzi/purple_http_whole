package com.zimi.zimixing.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 合同入库实体 CompanyConfigListBean
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class CompanyConfigListBean extends BaseBean {

    //            "subCompanyCode": "0",
    //            "subCompanyName": "通用",
    //            "contractList": []
    /** 合同模版code？ */
    private String subCompanyCode = "";
    /** 合同末班名字？ */
    private String subCompanyName = "";
    /** 合同列表列表 */
    private ArrayList<ContractListBean> contractList = new ArrayList<>();


    @Override
    protected void init(JSONObject jSon) throws JSONException {

        subCompanyCode = jSon.optString("subCompanyCode", "");
        subCompanyName = jSon.optString("subCompanyName", "");

        contractList = (ArrayList<ContractListBean>) BaseBean.toModelList(jSon.optString("contractList", ""), ContractListBean.class);
    }

    public String getSubCompanyCode() {
        return subCompanyCode;
    }

    public void setSubCompanyCode(String subCompanyCode) {
        this.subCompanyCode = subCompanyCode;
    }

    public String getSubCompanyName() {
        return subCompanyName;
    }

    public void setSubCompanyName(String subCompanyName) {
        this.subCompanyName = subCompanyName;
    }

    public ArrayList<ContractListBean> getContractList() {
        return contractList;
    }

    public void setContractList(ArrayList<ContractListBean> contractList) {
        this.contractList = contractList;
    }
}
