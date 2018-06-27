package com.zimi.zimixing.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by tanjun on 2018/1/16.
 *
 */

public class CarInfoRetBean extends BaseBean {
    private String isCounterProduct;

    public String getIsCounterProduct() {
        return isCounterProduct;
    }

    public void setIsCounterProduct(String isCounterProduct) {
        this.isCounterProduct = isCounterProduct;
    }

    @Override
    protected void init(JSONObject jSon) throws JSONException {
        isCounterProduct = jSon.optString("isCounterProduct", "");
    }

}
