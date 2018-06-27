package com.zimi.zimixing.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by tanjun on 2018/1/17.
 * 车辆解押
 */

public class ReleaseBean extends BaseBean {

    private int pageIndex;
    private int pageSize;
    private int countTotal;
    private ArrayList<ReturnListBean> returnList;

    @Override
    protected void init(JSONObject jSon) throws JSONException {
        pageIndex = jSon.optInt("pageIndex", 0);
        pageSize = jSon.optInt("pageSize", 0);
        countTotal = jSon.optInt("countTotal", 0);

        returnList = (ArrayList<ReturnListBean>) BaseBean
                .toModelList(jSon.optString("returnList",""),ReturnListBean.class);
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCountTotal() {
        return countTotal;
    }

    public void setCountTotal(int countTotal) {
        this.countTotal = countTotal;
    }

    public ArrayList<ReturnListBean> getReturnList() {
        return returnList;
    }

    public void setReturnList(ArrayList<ReturnListBean> returnList) {
        this.returnList = returnList;
    }

    public static class ReturnListBean extends BaseBean{
        /**
         * plate_no : A12123
         * name : 恒信
         * car_brand : 奥迪Audi Sport奥迪R84.2L 双离合 FSI Quattro
         * cust_name : 恒信
         * business_id : FZD-BIZ-ff6d49f2b2b3ea4fc1372d93
         */

        private String plate_no; //车牌号
        private String name; //执行人
        private String car_brand; //车辆品牌
        private String cust_name; //客户名
        private String business_id;

        @Override
        protected void init(JSONObject jSon) throws JSONException {
            plate_no = jSon.optString("plate_no", "");
            name = jSon.optString("name", "");
            car_brand = jSon.optString("car_brand", "");
            cust_name = jSon.optString("cust_name", "");
            business_id = jSon.optString("business_id", "");
        }

        public String getPlate_no() {
            return plate_no;
        }

        public void setPlate_no(String plate_no) {
            this.plate_no = plate_no;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCar_brand() {
            return car_brand;
        }

        public void setCar_brand(String car_brand) {
            this.car_brand = car_brand;
        }

        public String getCust_name() {
            return cust_name;
        }

        public void setCust_name(String cust_name) {
            this.cust_name = cust_name;
        }

        public String getBusiness_id() {
            return business_id;
        }

        public void setBusiness_id(String business_id) {
            this.business_id = business_id;
        }

    }

}
