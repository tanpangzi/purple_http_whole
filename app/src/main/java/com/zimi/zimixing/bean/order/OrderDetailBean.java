package com.zimi.zimixing.bean.order;

import com.zimi.zimixing.bean.BaseBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 订单详情数据
 * <p>
 * <br> Author: 叶青
 * <br> Version: 6.0.0
 * <br> Date: 2018/1/11
 * <br> Copyright: Copyright © 2017 xTeam Technology. All rights reserved.
 */
public class OrderDetailBean extends BaseBean {


    //"imageInfo": [{
    //    "image_name": "CL_B_08",
    //    "group_id": "CL_B",
    //    "url": "app\/common\/captcha\/task\/readImg?fileId=5a5443d75915684ded7f6672",
    //    "fileId": "5a5443d75915684ded7f6672"
    //}, {
    //    "image_name": "CL_B_06",
    //    "group_id": "CL_B",
    //    "url": "app\/common\/captcha\/task\/readImg?fileId=5a5443e15915684ded7f6674",
    //    "fileId": "5a5443e15915684ded7f6674"
    //}]
    //baseInfo: {
    //    "companyPlaceCityCode": "3223",
    //        "companyPlaceDetail": "KKK默默",
    //        "carFrameNo": "556688",
    //        "phone": "1358788996",
    //        "isLockEd": "N",
    //        "companyPhone": "1358788996",
    //        "loanPurposeValue": "房屋装修",
    //        "companyPlaceCityValue": "台中市",
    //        "customerBaseInfoId": 14,
    //        "loanPurposeCode": "3",
    //        "housingTypeCode": "3",
    //        "housingTypeValue": "无按揭购房",
    //        "companyPlaceDistrictCode": "3284",
    //        "mileage": "55699",
    //        "livingPlaceProvinceCode": "465",
    //        "repaymentMethods": "2",
    //        "educationValue": "本科",
    //        "livingPlaceDistrictValue": "和平区",
    //        "companyName": "国贸",
    //        "relationPhone": "路路通",
    //        "livingPlaceDetail": "国贸",
    //        "livingPlaceProvinceValue": "辽宁省",
    //        "carInfoId": 12,
    //        "loanStatus": "0",
    //        "loanInfoId": 6,
    //        "sexValue": "男",
    //        "companyPlaceDistrictValue": "南区",
    //        "productTypesCode": "2",
    //        "sex": "1",
    //        "produceDate": 1489334400000,
    //        "licencePlate": "536800",
    //        "companyPlaceProvinceCode": "3219",
    //        "customerRelationCode": "2",
    //        "customerRelationValue": "子女",
    //        "education": "2",
    //        "relationPerson": "咯哦哦",
    //        "repaymentPeriodhods": "10",
    //        "name": "唐",
    //        "marriageValue": "未婚",
    //        "assess": 3000,
    //        "loanMount": 1365000,
    //        "idCard": "431182654556525588",
    //        "livingPlaceDistrictCode": "467",
    //        "livingPlaceCityCode": "466",
    //        "marriage": "2",
    //        "productTypesValue": "按揭押车",
    //        "versions": "1",
    //        "carModel": "4566",
    //        "idCardValidityDates": 1489334400000,
    //        "livingPlaceCityValue": "沈阳市",
    //        "brand": "×5",
    //        "companyPlaceProvinceValue": "台湾省"
    //}

    private OrderDetailBaseInfoBean baseInfo = new OrderDetailBaseInfoBean();
    private ArrayList<OrderDetailImageInfoBean> imageInfo = new ArrayList<>();

    @Override
    protected void init(JSONObject jSon) throws JSONException {
        if (jSon.optJSONObject("baseInfo") != null) {
            baseInfo.init(jSon.optJSONObject("baseInfo"));
        }
        imageInfo = (ArrayList<OrderDetailImageInfoBean>) BaseBean.toModelList(jSon.optString("imageInfo", ""), OrderDetailImageInfoBean.class);
    }

    public OrderDetailBaseInfoBean getBaseInfo() {
        return baseInfo;
    }

    public void setBaseInfo(OrderDetailBaseInfoBean baseInfo) {
        this.baseInfo = baseInfo;
    }

    public ArrayList<OrderDetailImageInfoBean> getImageInfo() {
        return imageInfo;
    }

    public void setImageInfo(ArrayList<OrderDetailImageInfoBean> imageInfo) {
        this.imageInfo = imageInfo;
    }

}
