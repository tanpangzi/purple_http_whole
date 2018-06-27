package com.zimi.zimixing.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018/1/7.
 */

public class MessageBean extends BaseBean{

        private String customerName;
        private String content;
        private String pullTime;

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getPullTime() {
            return pullTime;
        }

        public void setPullTime(String pullTime) {
            this.pullTime = pullTime;
        }

    @Override
    protected void init(JSONObject jSon) throws JSONException {
        customerName = jSon.optString("customerName","");
        content = jSon.optString("content","");
        pullTime = jSon.optString("pullTime","");
    }

}
