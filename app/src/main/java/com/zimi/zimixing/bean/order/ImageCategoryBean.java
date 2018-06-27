package com.zimi.zimixing.bean.order;


import com.zimi.zimixing.bean.BaseBean;
import com.zimi.zimixing.utils.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 录单下拉框列表 图像类型信息
 * <p>
 * <br> Author: 叶青
 * <br> Version: 6.0.0
 * <br> Date: 2018/1/11
 * <br> Copyright: Copyright © 2017 xTeam Technology. All rights reserved.
 */
public class ImageCategoryBean extends BaseBean {

    //"image_category": [{
    //"value": "身份证",
    //"code": "39_1"
    //}, {
    //"value": "登记证",
    //"code": "30_2"
    //}, {
    //"value": "驾驶证",
    //"code": "30_3"
    //}, {
    //"value": "行驶证",
    //"code": "30_4"
    //}, {
    //"value": "车辆图片",
    //"code": "31_5"
    //}, {
    //"value": "其他资料",
    //"code": "28_6"
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
