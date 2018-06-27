package com.zimi.zimixing.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 合同入库 合同流水号实体
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class ContractSerialBean extends BaseBean {

    /** 合同流水号 */
    private String contractSerial="";

    @Override
    protected void init(JSONObject jSon) throws JSONException {
        //        token = jSon.optString("token", "");
    }

    public String getContractSerial() {
        return contractSerial;
    }

    public void setContractSerial(String contractSerial) {
        this.contractSerial = contractSerial;
    }
}
