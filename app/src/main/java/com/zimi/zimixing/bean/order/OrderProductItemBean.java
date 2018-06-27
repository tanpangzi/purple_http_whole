package com.zimi.zimixing.bean.order;

import com.zimi.zimixing.bean.BaseBean;
import com.zimi.zimixing.utils.JsonUtil;
import com.zimi.zimixing.utils.StringUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 补充信息 产品类型数据
 * <p>
 * <br> Author: 叶青
 * <br> Version: 6.0.0
 * <br> Date: 2018/1/11
 * <br> Copyright: Copyright © 2017 xTeam Technology. All rights reserved.
 */
public class OrderProductItemBean extends BaseBean {

    //"loanTerm": "1,2,3,6",
    //"productId": 172,//产品类型ID
    //"productTypeName": "按揭押车", //产品类型
    //"repaymentModeName": "随心还",//还款方式
    //"repaymentMode": "EASY",//还款方式ID
    //"productType": "mortgageRiding",//贷款产品ID
    //"productName": "按揭押车-先息后本-v1",//贷款产品
    //"fixedInterestRate": 2

    private String productId = "";
    private String productTypeName = "";
    private String repaymentModeName = "";
    private String repaymentMode = "";
    private String productType = "";
    private String productName = "";
    private String fixedInterestRate = "";
    private ArrayList<String> loanTermList = new ArrayList<>();

    @Override
    protected void init(JSONObject jSon) throws JSONException {
        productId = JsonUtil.optString(jSon, "productId", "");
        productTypeName = JsonUtil.optString(jSon, "productTypeName", "");
        repaymentModeName = JsonUtil.optString(jSon, "repaymentModeName", "");
        repaymentMode = JsonUtil.optString(jSon, "repaymentMode", "");
        productType = JsonUtil.optString(jSon, "productType", "");
        productName = JsonUtil.optString(jSon, "productName", "");
        fixedInterestRate = JsonUtil.optString(jSon, "fixedInterestRate", "");
        String loanTerm = JsonUtil.optString(jSon, "loanTerm", "");
        String[] loanTerms = loanTerm.split(",");
        loanTermList.addAll(StringUtil.getList(loanTerms));
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductTypeName() {
        return productTypeName;
    }

    public void setProductTypeName(String productTypeName) {
        this.productTypeName = productTypeName;
    }

    public String getRepaymentModeName() {
        return repaymentModeName;
    }

    public void setRepaymentModeName(String repaymentModeName) {
        this.repaymentModeName = repaymentModeName;
    }

    public String getRepaymentMode() {
        return repaymentMode;
    }

    public void setRepaymentMode(String repaymentMode) {
        this.repaymentMode = repaymentMode;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getFixedInterestRate() {
        return fixedInterestRate;
    }

    public void setFixedInterestRate(String fixedInterestRate) {
        this.fixedInterestRate = fixedInterestRate;
    }

    public ArrayList<String> getLoanTermList() {
        return loanTermList;
    }

    public void setLoanTermList(ArrayList<String> loanTermList) {
        this.loanTermList = loanTermList;
    }
}
