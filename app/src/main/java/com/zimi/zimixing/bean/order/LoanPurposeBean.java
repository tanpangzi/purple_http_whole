package com.zimi.zimixing.bean.order;


import com.zimi.zimixing.bean.BaseBean;
import com.zimi.zimixing.utils.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 借款类型信息
 * <p>
 * <br> Author: 叶青
 * <br> Version: 6.0.0
 * <br> Date: 2018/1/11
 * <br> Copyright: Copyright © 2017 xTeam Technology. All rights reserved.
 */
public class LoanPurposeBean extends BaseBean {

    //"loan_purpose": [{
    //"value": "资金周转",
    //        "code": "1"
    //}, {
    //"value": "个人消费",
    //        "code": "2"
    //}, {
    //"value": "房屋装修",
    //        "code": "3"
    //}, {
    //"value": "培训教育",
    //        "code": "5"
    //}, {
    //"value": "其他",
    //        "code": "6"
    //}],
    private String value = "";
    private String code = "";

    @Override
    protected void init(JSONObject jSon) throws JSONException {
        value = JsonUtil.optString(jSon, "value", "");
        code = JsonUtil.optString(jSon, "code", "");
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
