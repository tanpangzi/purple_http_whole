package com.zimi.zimixing.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 合同入库实体
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class ContractNoBean extends BaseBean {

    //    "data": {
    //        "contractNo": "FGDGZ18010870014001",
    //                "companyConfigList": []
    //    }

    /** 合同号 */
    private String contractNo = "";
    /** companyConfigList */
    private ArrayList<CompanyConfigListBean> companyConfigList = new ArrayList<>();

    @Override
    protected void init(JSONObject jSon) throws JSONException {

        contractNo = jSon.optString("contractNo", "");

        companyConfigList = (ArrayList<CompanyConfigListBean>) BaseBean.toModelList(jSon.optString("companyConfigList", ""), CompanyConfigListBean.class);
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public ArrayList<CompanyConfigListBean> getCompanyConfigList() {
        return companyConfigList;
    }

    public void setCompanyConfigList(ArrayList<CompanyConfigListBean> companyConfigList) {
        this.companyConfigList = companyConfigList;
    }
}
