package com.zimi.zimixing.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 合同类型bean
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class ContractListBean extends BaseBean {

    //"contractType":"75",
    //"contractName":"抵押合同-佛山"

    /** 75 */
    private String contractType = "";
    /** 抵押合同-佛山 */
    private String contractName = "";
    /** 合同编号 */
    private String contractNum = "";

    @Override
    protected void init(JSONObject jSon) throws JSONException {

        contractType = jSon.optString("contractType", "");
        contractName = jSon.optString("contractName", "");
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getContractNum() {
        return contractNum;
    }

    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
    }
}
