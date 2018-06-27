package com.zimi.zimixing.bean.order;


import com.zimi.zimixing.bean.BaseBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 订单列表
 * <p>
 * <br> Author: 叶青
 * <br> Version: 6.0.0
 * <br> Date: 2018/1/11
 * <br> Copyright: Copyright © 2017 xTeam Technology. All rights reserved.
 */
public class OrderBaseBean extends BaseBean {

    /**
     * code : 0
     * msg : 操作成功
     * data : {"sex":[{"value":"男","code":"1"},{"value":"女","code":"2"}],"loan_purpose":[{"value":"资金周转","code":"1"},{"value":"个人消费","code":"2"},{"value":"房屋装修","code":"3"},{"value":"培训教育","code":"5"},{"value":"其他","code":"6"}],"loan_status":[{"value":"草稿","code":"1"},{"value":"申请中","code":"2"},{"value":"审核中","code":"3"},{"value":"放款成功","code":"4"}],"image_category":[{"value":"身份证","code":"39_1"},{"value":"登记证","code":"30_2"},{"value":"驾驶证","code":"30_3"},{"value":"行驶证","code":"30_4"},{"value":"车辆图片","code":"31_5"},{"value":"其他资料","code":"28_6"}],"repayment_methods":[{"value":"先息后本","code":"1"},{"value":"等本等息","code":"2"}],"housing_Type":[{"value":"商业按揭房","code":"1"},{"value":"无按揭购房","code":"2"},{"value":"公积金按揭购房","code":"3"},{"value":"自建房","code":"4"},{"value":"租用","code":"5"},{"value":"亲属住房","code":"6"},{"value":"单位住房","code":"7"}],"marriage":[{"value":"已婚","code":"1"},{"value":"未婚","code":"2"},{"value":"离异","code":"3"},{"value":"丧偶","code":"4"}],"education":[{"value":"硕士或以上","code":"1"},{"value":"本科","code":"2"},{"value":"大专","code":"3"},{"value":"高中","code":"4"},{"value":"初中","code":"5"},{"value":"小学","code":"6"},{"value":"其他","code":"7"}],"product_types":[{"value":"全款押车","code":"1"},{"value":"全款不押车","code":"2"},{"value":"按揭押车","code":"3"}]}
     */
    //"value": "男", "code": "1"
    private ArrayList<SexBean> sexList = new ArrayList<>();
    // "value": "资金周转",
    private ArrayList<LoanPurposeBean> loanPurposeList = new ArrayList<>();
    // 	"value": "草稿",
    private ArrayList<LoanStatusBean> loanStatusist = new ArrayList<>();
    // "value": "身份证",		"code": "39_1"
    private ArrayList<ImageCategoryBean> imageCategoryList = new ArrayList<>();
    //	"value": "先息后本",
    private ArrayList<RepaymentMethodsBean> repaymentMethodsList = new ArrayList<>();
    //	"value": "商业按揭房",
    private ArrayList<HousingTypeBean> housingTypeList = new ArrayList<>();
    //	"value": "已婚",
    private ArrayList<MarriageBean> marriageList = new ArrayList<>();
    //	"value": "硕士或以上",
    private ArrayList<EducationBean> educationList = new ArrayList<>();
    //	"value": "全款押车",
    private ArrayList<ProductTypesBean> productTypesList = new ArrayList<>();
    //		"code": "1", "value": "父母"
    private ArrayList<CustomerRelationBean> customerRelationList = new ArrayList<>();

    @Override
    protected void init(JSONObject jSon) throws JSONException {
        sexList = (ArrayList<SexBean>) BaseBean.toModelList(jSon.optString("sex", ""), SexBean.class);
        loanPurposeList = (ArrayList<LoanPurposeBean>) BaseBean.toModelList(jSon.optString("loan_purpose", ""), LoanPurposeBean.class);
        loanStatusist = (ArrayList<LoanStatusBean>) BaseBean.toModelList(jSon.optString("loan_status", ""), LoanStatusBean.class);
        imageCategoryList = (ArrayList<ImageCategoryBean>) BaseBean.toModelList(jSon.optString("image_category", ""), ImageCategoryBean.class);
        repaymentMethodsList = (ArrayList<RepaymentMethodsBean>) BaseBean.toModelList(jSon.optString("repayment_methods", ""), RepaymentMethodsBean.class);

        housingTypeList = (ArrayList<HousingTypeBean>) BaseBean.toModelList(jSon.optString("housing_Type", ""), HousingTypeBean.class);
        marriageList = (ArrayList<MarriageBean>) BaseBean.toModelList(jSon.optString("marriage", ""), MarriageBean.class);
        educationList = (ArrayList<EducationBean>) BaseBean.toModelList(jSon.optString("education", ""), EducationBean.class);
        productTypesList = (ArrayList<ProductTypesBean>) BaseBean.toModelList(jSon.optString("product_types", ""), ProductTypesBean.class);
        customerRelationList = (ArrayList<CustomerRelationBean>) BaseBean.toModelList(jSon.optString("customer_relation", ""), CustomerRelationBean.class);
    }

    public ArrayList<SexBean> getSexList() {
        return sexList;
    }

    public void setSexList(ArrayList<SexBean> sexList) {
        this.sexList = sexList;
    }

    public ArrayList<LoanPurposeBean> getLoanPurposeList() {
        return loanPurposeList;
    }

    public void setLoanPurposeList(ArrayList<LoanPurposeBean> loanPurposeList) {
        this.loanPurposeList = loanPurposeList;
    }

    public ArrayList<LoanStatusBean> getLoanStatusist() {
        return loanStatusist;
    }

    public void setLoanStatusist(ArrayList<LoanStatusBean> loanStatusist) {
        this.loanStatusist = loanStatusist;
    }

    public ArrayList<ImageCategoryBean> getImageCategoryList() {
        return imageCategoryList;
    }

    public void setImageCategoryList(ArrayList<ImageCategoryBean> imageCategoryList) {
        this.imageCategoryList = imageCategoryList;
    }

    public ArrayList<RepaymentMethodsBean> getRepaymentMethodsList() {
        return repaymentMethodsList;
    }

    public void setRepaymentMethodsList(ArrayList<RepaymentMethodsBean> repaymentMethodsList) {
        this.repaymentMethodsList = repaymentMethodsList;
    }

    public ArrayList<HousingTypeBean> getHousingTypeList() {
        return housingTypeList;
    }

    public void setHousingTypeList(ArrayList<HousingTypeBean> housingTypeList) {
        this.housingTypeList = housingTypeList;
    }

    public ArrayList<MarriageBean> getMarriageList() {
        return marriageList;
    }

    public void setMarriageList(ArrayList<MarriageBean> marriageList) {
        this.marriageList = marriageList;
    }

    public ArrayList<EducationBean> getEducationList() {
        return educationList;
    }

    public void setEducationList(ArrayList<EducationBean> educationList) {
        this.educationList = educationList;
    }

    public ArrayList<ProductTypesBean> getProductTypesList() {
        return productTypesList;
    }

    public void setProductTypesList(ArrayList<ProductTypesBean> productTypesList) {
        this.productTypesList = productTypesList;
    }

    public ArrayList<CustomerRelationBean> getCustomerRelationList() {
        return customerRelationList;
    }

    public void setCustomerRelationList(ArrayList<CustomerRelationBean> customerRelationList) {
        this.customerRelationList = customerRelationList;
    }
}
