package com.zimi.zimixing.bean.order;


import com.zimi.zimixing.bean.BaseBean;
import com.zimi.zimixing.utils.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *  录单下拉框列表 房屋信息
 * <p>
 * <br> Author: 叶青
 * <br> Version: 6.0.0
 * <br> Date: 2018/1/11
 * <br> Copyright: Copyright © 2017 xTeam Technology. All rights reserved.
 */
public class HousingTypeBean extends BaseBean {

    //"imageInfo": [{
    //    "image_name": "CL_B_08",
    //    "group_id": "CL_B",
    //    "url": "app\/common\/captcha\/task\/readImg?fileId=5a5443d75915684ded7f6672",
    //    "fileId": "5a5443d75915684ded7f6672"
    //}, {
    //    "image_name": "CL_B_06",
    //    "group_id": "CL_B",
    //    "url": "app\/common\/captcha\/task\/readImg?fileId=5a5443e15915684ded7f6674",
    //    "fileId": "5a5443e15915684ded7f6674"
    //}]

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
