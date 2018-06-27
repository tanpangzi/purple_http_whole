package com.zimi.zimixing.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018/1/16.
 */

public class RequestNoBean extends BaseBean {

    private String requestNo;

    public String getRequestNo() {
        return requestNo;
    }

    public void setRequestNo(String requestNo) {
        this.requestNo = requestNo;
    }

    @Override
    protected void init(JSONObject jSon) throws JSONException {
        requestNo = jSon.optString("requestNo", "");
    }

}
