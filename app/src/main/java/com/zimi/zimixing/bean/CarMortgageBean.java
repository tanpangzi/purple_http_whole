package com.zimi.zimixing.bean;


import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018/1/16.
 */

public class CarMortgageBean extends BaseBean {

    private String isCounterProduct;

    private MortgageInfoBean mortgageInfo = new MortgageInfoBean();

    public String getIsCounterProduct() {
        return isCounterProduct;
    }

    public void setIsCounterProduct(String isCounterProduct) {
        this.isCounterProduct = isCounterProduct;
    }

    public MortgageInfoBean getMortgageInfo() {
        return mortgageInfo;
    }

    public void setMortgageInfo(MortgageInfoBean mortgageInfo) {
        this.mortgageInfo = mortgageInfo;
    }

    @Override
    protected void init(JSONObject jSon) throws JSONException {
        isCounterProduct = jSon.optString("isCounterProduct",isCounterProduct);
        if (jSon.optJSONObject("mortgageInfo") != null) {
            mortgageInfo.init(jSon.optJSONObject("mortgageInfo"));
        }
    }

    public static class MortgageInfoBean extends BaseBean{
        /**
         * cust_name : 粤B000003
         * plate_no : 小白
         * assignee : 李白
         * car_brand : 奥迪
         * start_date : 2017-02-18
         */

        private String cust_name; //客户名字
        private String cust_id; //客户id
        private String plate_no; //车牌号
        private String assignee; //执行人
        private String car_brand; //车辆品牌
        private String start_date; //
        private String is_trans_ownership; //是否过户

        @Override
        protected void init(JSONObject jSon) throws JSONException {
            car_brand = jSon.optString("car_brand", "");
            cust_id = jSon.optString("cust_id", "");
            plate_no = jSon.optString("plate_no", "");
            assignee = jSon.optString("assignee", "");
            cust_name = jSon.optString("cust_name", "");
            start_date = jSon.optString("start_date", "");
            is_trans_ownership = jSon.optString("is_trans_ownership", "");
        }

        public String getCust_id() {
            return cust_id;
        }

        public void setCust_id(String cust_id) {
            this.cust_id = cust_id;
        }

        public String getIs_trans_ownership() {
            return is_trans_ownership;
        }

        public void setIs_trans_ownership(String is_trans_ownership) {
            this.is_trans_ownership = is_trans_ownership;
        }

        public String getCust_name() {
            return cust_name;
        }

        public void setCust_name(String cust_name) {
            this.cust_name = cust_name;
        }

        public String getPlate_no() {
            return plate_no;
        }

        public void setPlate_no(String plate_no) {
            this.plate_no = plate_no;
        }

        public String getAssignee() {
            return assignee;
        }

        public void setAssignee(String assignee) {
            this.assignee = assignee;
        }

        public String getCar_brand() {
            return car_brand;
        }

        public void setCar_brand(String car_brand) {
            this.car_brand = car_brand;
        }

        public String getStart_date() {
            return start_date;
        }

        public void setStart_date(String start_date) {
            this.start_date = start_date;
        }



    }

}
