package com.zimi.zimixing.bean.order;


import com.zimi.zimixing.bean.BaseBean;
import com.zimi.zimixing.utils.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 录单下拉框列表 学历信息
 * <p>
 * <br> Author: 叶青
 * <br> Version: 6.0.0
 * <br> Date: 2018/1/11
 * <br> Copyright: Copyright © 2017 xTeam Technology. All rights reserved.
 */
public class EducationBean extends BaseBean {

    //"housing_Type": [{
    //"value": "商业按揭房",
    //"code": "1"
    //}, {
    //"value": "无按揭购房",
    //"code": "2"
    //}, {
    //"value": "公积金按揭购房",
    //"code": "3"
    //}, {
    //"value": "自建房",
    //"code": "4"
    //}, {
    //"value": "租用",
    //"code": "5"
    //}, {
    //"value": "亲属住房",
    //"code": "6"
    //}, {
    //"value": "单位住房",
    //"code": "7"
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
