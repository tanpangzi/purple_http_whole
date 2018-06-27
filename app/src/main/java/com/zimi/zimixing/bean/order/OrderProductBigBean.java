package com.zimi.zimixing.bean.order;

import com.zimi.zimixing.bean.BaseBean;
import com.zimi.zimixing.utils.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 补充信息 产品大类
 * <p>
 * <br> Author: 叶青
 * <br> Version: 6.0.0
 * <br> Date: 2018/1/11
 * <br> Copyright: Copyright © 2017 xTeam Technology. All rights reserved.
 */
public class OrderProductBigBean extends BaseBean {

    //{
    //"productBigType": "GUARANTY",
    //"productList": [{
    //"loanTerm": "1,2,3,6",
    //"productId": 157,
    //"productTypeName": "全款不押车",
    //"repaymentModeName": "随心还",
    //"repaymentMode": "EASY",
    //"productType": "fullNoRiding",
    //"productName": "全款不押车-先息后本-v1",
    //"fixedInterestRate": 2.5
    //}, {
    //"loanTerm": "1,2,3,6",
    //"productId": 172,
    //"productTypeName": "按揭押车",
    //"repaymentModeName": "随心还",
    //"repaymentMode": "EASY",
    //"productType": "mortgageRiding",
    //"productName": "按揭押车-先息后本-v1",
    //"fixedInterestRate": 2
    //}],
    //"productBigTypeName": "线下车贷产品"
    //}
    private String productBigType = "";
    private String productBigTypeName = "";
    private ArrayList<OrderProductItemBean> productList = new ArrayList<>();

    @Override
    protected void init(JSONObject jSon) throws JSONException {
        productBigType = JsonUtil.optString(jSon, "productBigType", "");
        productBigTypeName = JsonUtil.optString(jSon, "productBigTypeName", "");
        productList = (ArrayList<OrderProductItemBean>) BaseBean.toModelList(jSon.optString("productList", ""), OrderProductItemBean.class);
    }

    public String getProductBigType() {
        return productBigType;
    }

    public void setProductBigType(String productBigType) {
        this.productBigType = productBigType;
    }

    public String getProductBigTypeName() {
        return productBigTypeName;
    }

    public void setProductBigTypeName(String productBigTypeName) {
        this.productBigTypeName = productBigTypeName;
    }

    public ArrayList<OrderProductItemBean> getProductList() {
        return productList;
    }

    public void setProductList(ArrayList<OrderProductItemBean> productList) {
        this.productList = productList;
    }
}
