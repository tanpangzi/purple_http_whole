package com.zimi.zimixing.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by tanjun on 2018/1/15.
 * 车辆评估 第一页 获取车辆信息
 */

public class CarInfoBean extends BaseBean{

/*    private JSONObject object;

    public JSONObject getEvalCarInfoBean() {
        return object;
    }

    public void setEvalCarInfoBean(JSONObject object) {
        this.object = object;
    }*/

    @Override
    protected void init(JSONObject jSon) throws JSONException {
        //object =  jSon.optJSONObject("evalCarInfo");
    }

    public static class EvalCarInfoBean extends BaseBean{
        /** 引擎号 */
        private String engine_no; //引擎
        /** 车牌号 */
        private String plate_no;
        /** 车架号 */
        private String car_recognization_no;
        /** 系统评估价 */
        private String c2b_prices;
        /** 注册时间 */
        private String register_date;
        /** 行驶公里数 */
        private double travel_distance;
        /** 车辆表面 */
        private String surface_desc;
        private String business_id;

        @Override
        protected void init(JSONObject jSon) throws JSONException {
            JSONObject obj = jSon.optJSONObject("evalCarInfo");
            plate_no = obj.optString("plate_no","");
            register_date = obj.optString("register_date","");
            travel_distance = obj.optDouble("travel_distance", 0.0);
            c2b_prices = obj.optString("c2b_prices", "");
            engine_no = obj.optString("engine_no", "");
            car_recognization_no = obj.optString("car_recognization_no","");
            surface_desc = obj.optString("surface_desc", "");
            business_id = obj.optString("business_id","");
        }

        public String getSurface_desc() {
            return surface_desc;
        }

        public void setSurface_desc(String surface_desc) {
            this.surface_desc = surface_desc;
        }

        public String getBusiness_id() {
            return business_id;
        }

        public void setBusiness_id(String business_id) {
            this.business_id = business_id;
        }

        public String getPlate_no() {
            return plate_no;
        }

        public void setPlate_no(String plate_no) {
            this.plate_no = plate_no;
        }

        public String getRegister_date() {
            return register_date;
        }

        public void setRegister_date(String register_date) {
            this.register_date = register_date;
        }

        public double getTravel_distance() {
            return travel_distance;
        }

        public void setTravel_distance(double travel_distance) {
            this.travel_distance = travel_distance;
        }

        public String getC2b_prices() {
            return c2b_prices;
        }

        public void setC2b_prices(String c2b_prices) {
            this.c2b_prices = c2b_prices;
        }

        public String getEngine_no() {
            return engine_no;
        }

        public void setEngine_no(String engine_no) {
            this.engine_no = engine_no;
        }

        public String getCar_recognization_no() {
            return car_recognization_no;
        }

        public void setCar_recognization_no(String car_recognization_no) {
            this.car_recognization_no = car_recognization_no;
        }
    }

}
