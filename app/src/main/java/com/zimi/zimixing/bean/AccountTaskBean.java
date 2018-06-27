package com.zimi.zimixing.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by tanjun on 2017/1/11.
 *
 * 车辆评估 Gps安装 车辆抵押 共同数据 根据传入的同参数获取不同数据 参数 已办 待办 不同进入方式
 */

public class AccountTaskBean extends BaseBean{

    /**
     * countTotal : 3
     * returnList : [{"cust_name":"小白","plate_no":"粤B000000","business_id":1,"car_brand":"品牌"},{"cust_name":"小白2","plate_no":"粤B000002","business_id":2,"car_brand":"品牌2"},{"cust_name":"小白3","plate_no":"粤B000003","business_id":3,"car_brand":"品牌3"}]
     */


    private String countTotal;
    private ArrayList<ReturnListBean> returnList;

    public String getCountTotal() {
        return countTotal;
    }

    public void setCountTotal(String countTotal) {
        this.countTotal = countTotal;
    }

    public ArrayList<ReturnListBean> getReturnList() {
        return returnList;
    }

    public void setReturnList(ArrayList<ReturnListBean> returnList) {
        this.returnList = returnList;
    }

    @Override
    protected void init(JSONObject jSon) throws JSONException {
        countTotal = jSon.optString("countTotal","");
        returnList = (ArrayList<ReturnListBean>) BaseBean
                .toModelList(jSon.optString("returnList",""),ReturnListBean.class);
    }

    public static class ReturnListBean extends BaseBean{
        /**
         * cust_name : 小白
         * plate_no : 粤B000000
         * business_id : 1
         * car_brand : 品牌
         */
        private String plate_no;
        private String is_not_sign; //是否已签收 0已签收 1未签收
        private String process_instance_id;
        private String car_brand;
        private String task_id;
        private String cust_name;
        private String created_date;
        private String cust_id;
        private String business_id;

        private String surface_desc;
        private String assessment_price;
        private String is_accident_apply_car;
        private String isCounterProduct;  //新老产品的判断 1是老产品 0是新产品

        @Override
        protected void init(JSONObject jSon) throws JSONException {
            plate_no = jSon.optString("plate_no", "");
            is_not_sign = jSon.optString("is_not_sign","");
            process_instance_id = jSon.optString("process_instance_id", "");
            car_brand = jSon.optString("car_brand", "");
            task_id = jSon.optString("task_id","");
            cust_name = jSon.optString("cust_name","");
            created_date = jSon.optString("created_date", created_date);
            cust_id = jSon.optString("cust_id", "");
            business_id = jSon.optString("business_id", "");

            surface_desc = jSon.optString("surface_desc", "");
            assessment_price = jSon.optString("assessment_price", "");
            is_accident_apply_car = jSon.optString("is_accident_apply_car", "");
            isCounterProduct = jSon.optString("isCounterProduct","");
        }

        public String getCreated_date() {
            return created_date;
        }

        public void setCreated_date(String created_date) {
            this.created_date = created_date;
        }

        public String getIsCounterProduct() {
            return isCounterProduct;
        }

        public void setIsCounterProduct(String isCounterProduct) {
            this.isCounterProduct = isCounterProduct;
        }

        public String getSurface_desc() {
            return surface_desc;
        }

        public void setSurface_desc(String surface_desc) {
            this.surface_desc = surface_desc;
        }

        public String getAssessment_price() {
            return assessment_price;
        }

        public void setAssessment_price(String assessment_price) {
            this.assessment_price = assessment_price;
        }

        public String getIs_accident_apply_car() {
            return is_accident_apply_car;
        }

        public void setIs_accident_apply_car(String is_accident_apply_car) {
            this.is_accident_apply_car = is_accident_apply_car;
        }

        public String getCust_id() {
            return cust_id;
        }

        public void setCust_id(String cust_id) {
            this.cust_id = cust_id;
        }

        public String getProcess_instance_id() {
            return process_instance_id;
        }

        public void setProcess_instance_id(String process_instance_id) {
            this.process_instance_id = process_instance_id;
        }

        public String getTask_id() {
            return task_id;
        }

        public void setTask_id(String task_id) {
            this.task_id = task_id;
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

        public String getBusiness_id() {
            return business_id;
        }

        public void setBusiness_id(String business_id) {
            this.business_id = business_id;
        }

        public String getCar_brand() {
            return car_brand;
        }

        public void setCar_brand(String car_brand) {
            this.car_brand = car_brand;
        }

        public String getIs_not_sign() {
            return is_not_sign;
        }

        public void setIs_not_sign(String is_not_sign) {
            this.is_not_sign = is_not_sign;
        }

    }


}
