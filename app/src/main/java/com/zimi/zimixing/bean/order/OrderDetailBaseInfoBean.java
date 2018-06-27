package com.zimi.zimixing.bean.order;


import com.zimi.zimixing.bean.BaseBean;
import com.zimi.zimixing.utils.JsonUtil;
import com.zimi.zimixing.utils.MathUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 叮当详情基本信息
 * <p>
 * <br> Author: 叶青
 * <br> Version: 6.0.0
 * <br> Date: 2018/1/11
 * <br> Copyright: Copyright © 2017 xTeam Technology. All rights reserved.
 */
public class OrderDetailBaseInfoBean extends BaseBean {
    /**
     * sexValue : 男
     * loanPurposeValue : 资金周转
     * education : 1
     * idCard : 370883199009120754
     * companyName : 中南海
     * spousePhone :
     * relationship_3Value :
     * businessId : DYTAPI-BIZ-201712272006298696579
     * cityId : 201
     * carEngineNo : 269
     * productName : 全款押车-先息后本-huang
     * productBigTypeName : 线下车贷产品
     * prodcutTypeName : 全款押车
     * livingPlaceCityCode : 1
     * companyPlaceDistrictCode : 2
     * companyPhone : 010165666666
     * spouseIdcard :
     * brand : 奥迪 奥迪A4L 2.0T 双离合 40TFSI 进取型
     * repaymentMethods : EASY
     * mileage : 2
     * housingTypeCode : 1
     * relationship_1Value : 父母
     * companyPlaceProvinceValue : 北京市
     * licencePlate : 粤B1235666
     * rename_3 :
     * marriageValue : 未婚
     * rename_1 : 咯莫
     * rename_2 : 默默哦
     * phone : 13612345688
     * companyPlaceProvinceCode : 1
     * livingPlaceDetail : 北京胡同
     * name : 张开元
     * audiValuationPrice : 16.85
     * carFrameNo : 12665566555666665
     * repaymentMethodsName : 随心还
     * productBigType : GUARANTY
     * companyPlaceDistrictValue : 东城区
     * carColor : 黑色
     * livingPlaceProvinceCode : 1
     * cityName : 北京
     * surfaceConditionDescription : 新
     * marriage : 2
     * housingTypeValue : 商业按揭房
     * custId : CUST2017122720061110300345859
     * livingPlaceCityValue : 北京市
     * rephone_1 : 13666666666
     * educationValue : 硕士或以上
     * rephone_3 :
     * productType : fullRiding
     * productNo : 149
     * registerDate : 2017-12-27
     * rephone_2 : 13666865666
     * companyPlaceCityValue : 北京市
     * loanPurposeCode : 1
     * valuationPrice : 21.06
     * sex : 1
     * companyPlaceDetail : 被即墨
     * livingPlaceProvinceValue : 北京市
     * loanMount : 20000
     * companyPlaceCityCode : 1
     * relationship_3 :
     * livingPlaceDistrictCode : 2
     * relationship_2 : 1
     * relationship_1 : 1
     * repaymentPeriodhods : 1
     * livingPlaceDistrictValue : 东城区
     * spouseName :
     * business_id : DYTAPI-BIZ-201712272006298696579
     * relationship_2Value : 父母
     * carModel : 266
     */

    private String sexValue = "";
    private String loanPurposeValue = "";
    private String education = "";
    private String idCard = "";
    private String companyName = "";
    private String spousePhone = "";
    private String relationship_3Value = "";
    private String businessId = "";
    private String cityId = "";
    private String carEngineNo = "";
    private String productName = "";
    private String productBigTypeName = "";
    private String prodcutTypeName = "";
    private String livingPlaceCityCode = "";
    private String companyPlaceDistrictCode = "";
    private String companyPhone = "";
    private String spouseIdcard = "";
    private String brand = "";
    private String repaymentMethods = "";
    private String mileage = "";
    private String housingTypeCode = "";
    private String relationship_1Value = "";
    private String companyPlaceProvinceValue = "";
    private String licencePlate = "";
    private String rename_3 = "";
    private String marriageValue = "";
    private String rename_1 = "";
    private String rename_2 = "";
    private String phone = "";
    private String companyPlaceProvinceCode = "";
    private String livingPlaceDetail = "";
    private String name = "";
    private String audiValuationPrice = "";
    private String carFrameNo = "";
    private String repaymentMethodsName = "";
    private String productBigType = "";
    private String companyPlaceDistrictValue = "";
    private String carColor = "";
    private String livingPlaceProvinceCode = "";
    private String cityName = "";
    private String surfaceConditionDescription = "";
    private String marriage = "";
    private String housingTypeValue = "";
    private String custId = "";
    private String livingPlaceCityValue = "";
    private String rephone_1 = "";
    private String educationValue = "";
    private String rephone_3 = "";
    private String productType = "";
    private String productNo = "";
    private String certificateDate = "";
    private String rephone_2 = "";
    private String companyPlaceCityValue = "";
    private String loanPurposeCode = "";
    private String valuationPrice = "";
    private String sex = "";
    private String companyPlaceDetail = "";
    private String livingPlaceProvinceValue = "";
    private String loanMount = "";
    private String companyPlaceCityCode = "";
    private String relationship_3 = "";
    private String livingPlaceDistrictCode = "";
    private String relationship_2 = "";
    private String relationship_1 = "";
    private String repaymentPeriodhods = "";
    private String livingPlaceDistrictValue = "";
    private String spouseName = "";
    private String business_id = "";
    private String relationship_2Value = "";
    private String carModel = "";
    private String registerDate = "";//":"注册日期" 上牌时间
    private String TrimId = "";//":"评估用 车型id"

    @Override
    protected void init(JSONObject jSon) throws JSONException {
        //* sexValue : 男
        sexValue = JsonUtil.optString(jSon, "sexValue", "");
        //* loanPurposeValue : 资金周转
        loanPurposeValue = JsonUtil.optString(jSon, "loanPurposeValue", "");
        //* education : 1
        education = JsonUtil.optString(jSon, "education", "");
        //* idCard : 370883199009120754
        idCard = JsonUtil.optString(jSon, "idCard", "");
        //* companyName : 中南海
        companyName = JsonUtil.optString(jSon, "companyName", "");
        //* spousePhone :
        spousePhone = JsonUtil.optString(jSon, "spousePhone", "");
        //* relationship_3Value :
        relationship_3Value = JsonUtil.optString(jSon, "relationship_3Value", "");
        //* businessId : DYTAPI-BIZ-201712272006298696579
        businessId = JsonUtil.optString(jSon, "businessId", "");
        //* cityId : 201
        cityId = JsonUtil.optString(jSon, "cityId", "");
        //* carEngineNo : 269
        carEngineNo = JsonUtil.optString(jSon, "carEngineNo", "");
        //* productName : 全款押车-先息后本-huang
        productName = JsonUtil.optString(jSon, "productName", "");
        //* productBigTypeName : 线下车贷产品
        productBigTypeName = JsonUtil.optString(jSon, "productBigTypeName", "");
        //* prodcutTypeName : 全款押车
        prodcutTypeName = JsonUtil.optString(jSon, "prodcutTypeName", "");
        //* livingPlaceCityCode : 1
        livingPlaceCityCode = JsonUtil.optString(jSon, "livingPlaceCityCode", "");


        //* companyPlaceDistrictCode : 2
        companyPlaceDistrictCode = JsonUtil.optString(jSon, "companyPlaceDistrictCode", "");
        //* companyPhone : 010165666666
        companyPhone = JsonUtil.optString(jSon, "companyPhone", "");
        //* spouseIdcard :
        spouseIdcard = JsonUtil.optString(jSon, "spouseIdcard", "");
        //* brand : 奥迪 奥迪A4L 2.0T 双离合 40TFSI 进取型
        brand = JsonUtil.optString(jSon, "brand", "");
        //* repaymentMethods : EASY
        repaymentMethods = JsonUtil.optString(jSon, "repaymentMethods", "");
        //* mileage : 2
        mileage = MathUtils.round4String(JsonUtil.optString(jSon, "mileage", ""), 2);
        if (Double.parseDouble(mileage) <= 0) {
            mileage = "";
        }
        //* housingTypeCode : 1
        housingTypeCode = JsonUtil.optString(jSon, "housingTypeCode", "");
        //* relationship_1Value : 父母
        relationship_1Value = JsonUtil.optString(jSon, "relationship_1Value", "");
        //* companyPlaceProvinceValue : 北京市
        companyPlaceProvinceValue = JsonUtil.optString(jSon, "companyPlaceProvinceValue", "");
        //* licencePlate : 粤B1235666
        licencePlate = JsonUtil.optString(jSon, "licencePlate", "");
        //* rename_3 :
        rename_3 = JsonUtil.optString(jSon, "rename_3", "");
        //* marriageValue : 未婚
        marriageValue = JsonUtil.optString(jSon, "marriageValue", "");
        //* rename_1 : 咯莫
        rename_1 = JsonUtil.optString(jSon, "rename_1", "");
        //* rename_2 : 默默哦
        rename_2 = JsonUtil.optString(jSon, "rename_2", "");
        //* phone : 13612345688
        phone = JsonUtil.optString(jSon, "phone", "");
        //* companyPlaceProvinceCode : 1
        companyPlaceProvinceCode = JsonUtil.optString(jSon, "companyPlaceProvinceCode", "");
        //* livingPlaceDetail : 北京胡同
        livingPlaceDetail = JsonUtil.optString(jSon, "livingPlaceDetail", "");
        //* name : 张开元
        name = JsonUtil.optString(jSon, "name", "");
        //* audiValuationPrice : 16.85
        audiValuationPrice = JsonUtil.optString(jSon, "audiValuationPrice", "");
        //* carFrameNo : 12665566555666665
        carFrameNo = JsonUtil.optString(jSon, "carFrameNo", "");
        //* repaymentMethodsName : 随心还
        repaymentMethodsName = JsonUtil.optString(jSon, "repaymentMethodsName", "");
        //* productBigType : GUARANTY
        productBigType = JsonUtil.optString(jSon, "productBigType", "");
        //* companyPlaceDistrictValue : 东城区
        companyPlaceDistrictValue = JsonUtil.optString(jSon, "companyPlaceDistrictValue", "");
        //* carColor : 黑色
        carColor = JsonUtil.optString(jSon, "carColor", "");
        //* livingPlaceProvinceCode : 1
        livingPlaceProvinceCode = JsonUtil.optString(jSon, "livingPlaceProvinceCode", "");
        //* cityName : 北京
        cityName = JsonUtil.optString(jSon, "cityName", "");
        //* surfaceConditionDescription : 新
        surfaceConditionDescription = JsonUtil.optString(jSon, "surfaceConditionDescription", "");
        //* marriage : 2
        marriage =JsonUtil.optString(jSon, "marriage", "");
        //* housingTypeValue : 商业按揭房
        housingTypeValue = JsonUtil.optString(jSon, "housingTypeValue", "");
        //* custId : CUST2017122720061110300345859
        custId = JsonUtil.optString(jSon, "custId", "");
        //* livingPlaceCityValue : 北京市
        livingPlaceCityValue = JsonUtil.optString(jSon, "livingPlaceCityValue", "");
        //* rephone_1 : 13666666666
        rephone_1 = JsonUtil.optString(jSon, "rephone_1", "");
        //* educationValue : 硕士或以上
        educationValue = JsonUtil.optString(jSon, "educationValue", "");
        //* rephone_3 :
        rephone_3 = JsonUtil.optString(jSon, "rephone_3", "");
        //* productType : fullRiding
        productType = JsonUtil.optString(jSon, "productType", "");
        //* productNo : 149
        productNo = JsonUtil.optString(jSon, "productNo", "");
        //* registerDate : 2017-12-27
        registerDate = JsonUtil.optString(jSon, "registerDate", "");
        //* rephone_2 : 13666865666
        rephone_2 = JsonUtil.optString(jSon, "rephone_2", "");
        //* companyPlaceCityValue : 北京市
        companyPlaceCityValue = JsonUtil.optString(jSon, "companyPlaceCityValue", "");
        //* loanPurposeCode : 1
        loanPurposeCode = JsonUtil.optString(jSon, "loanPurposeCode", "");
        //* valuationPrice : 21.06
        valuationPrice = JsonUtil.optString(jSon, "valuationPrice", "");
        //* sex : 1
        sex = JsonUtil.optString(jSon, "sex", "");
        //* companyPlaceDetail : 被即墨
        companyPlaceDetail = JsonUtil.optString(jSon, "companyPlaceDetail", "");
        //* livingPlaceProvinceValue : 北京市
        livingPlaceProvinceValue = JsonUtil.optString(jSon, "livingPlaceProvinceValue", "");
        //* loanMount : 20000
        loanMount = MathUtils.round4String(JsonUtil.optString(jSon, "loanMount", ""), 2);
        //* companyPlaceCityCode : 1
        companyPlaceCityCode = JsonUtil.optString(jSon, "companyPlaceCityCode", "");
        //* relationship_3 :
        relationship_3 = JsonUtil.optString(jSon, "relationship_3", "");
        //* livingPlaceDistrictCode : 2
        livingPlaceDistrictCode = JsonUtil.optString(jSon, "livingPlaceDistrictCode", "");
        //* relationship_2 : 1
        relationship_2 = JsonUtil.optString(jSon, "relationship_2", "");
        //* relationship_1 : 1
        relationship_1 = JsonUtil.optString(jSon, "relationship_1", "");
        //* repaymentPeriodhods : 1
        repaymentPeriodhods = JsonUtil.optString(jSon, "repaymentPeriodhods", "");
        //* livingPlaceDistrictValue : 东城区
        livingPlaceDistrictValue = JsonUtil.optString(jSon, "livingPlaceDistrictValue", "");
        //* spouseName :
        spouseName = JsonUtil.optString(jSon, "spouseName", "");
        //* business_id : DYTAPI-BIZ-201712272006298696579
        business_id = JsonUtil.optString(jSon, "business_id", "");
        //* relationship_2Value : 父母
        relationship_2Value = JsonUtil.optString(jSon, "relationship_2Value", "");
        //* carModel : 266
        carModel = JsonUtil.optString(jSon, "carModel", "");
        TrimId = JsonUtil.optString(jSon, "TrimId", "");
    }

    public String getSexValue() {
        return sexValue;
    }

    public void setSexValue(String sexValue) {
        this.sexValue = sexValue;
    }

    public String getLoanPurposeValue() {
        return loanPurposeValue;
    }

    public void setLoanPurposeValue(String loanPurposeValue) {
        this.loanPurposeValue = loanPurposeValue;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getSpousePhone() {
        return spousePhone;
    }

    public void setSpousePhone(String spousePhone) {
        this.spousePhone = spousePhone;
    }

    public String getRelationship_3Value() {
        return relationship_3Value;
    }

    public void setRelationship_3Value(String relationship_3Value) {
        this.relationship_3Value = relationship_3Value;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCarEngineNo() {
        return carEngineNo;
    }

    public void setCarEngineNo(String carEngineNo) {
        this.carEngineNo = carEngineNo;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductBigTypeName() {
        return productBigTypeName;
    }

    public void setProductBigTypeName(String productBigTypeName) {
        this.productBigTypeName = productBigTypeName;
    }

    public String getProdcutTypeName() {
        return prodcutTypeName;
    }

    public void setProdcutTypeName(String prodcutTypeName) {
        this.prodcutTypeName = prodcutTypeName;
    }

    public String getLivingPlaceCityCode() {
        return livingPlaceCityCode;
    }

    public void setLivingPlaceCityCode(String livingPlaceCityCode) {
        this.livingPlaceCityCode = livingPlaceCityCode;
    }

    public String getCompanyPlaceDistrictCode() {
        return companyPlaceDistrictCode;
    }

    public void setCompanyPlaceDistrictCode(String companyPlaceDistrictCode) {
        this.companyPlaceDistrictCode = companyPlaceDistrictCode;
    }

    public String getCompanyPhone() {
        return companyPhone;
    }

    public void setCompanyPhone(String companyPhone) {
        this.companyPhone = companyPhone;
    }

    public String getSpouseIdcard() {
        return spouseIdcard;
    }

    public void setSpouseIdcard(String spouseIdcard) {
        this.spouseIdcard = spouseIdcard;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getRepaymentMethods() {
        return repaymentMethods;
    }

    public void setRepaymentMethods(String repaymentMethods) {
        this.repaymentMethods = repaymentMethods;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public String getHousingTypeCode() {
        return housingTypeCode;
    }

    public void setHousingTypeCode(String housingTypeCode) {
        this.housingTypeCode = housingTypeCode;
    }

    public String getRelationship_1Value() {
        return relationship_1Value;
    }

    public void setRelationship_1Value(String relationship_1Value) {
        this.relationship_1Value = relationship_1Value;
    }

    public String getCompanyPlaceProvinceValue() {
        return companyPlaceProvinceValue;
    }

    public void setCompanyPlaceProvinceValue(String companyPlaceProvinceValue) {
        this.companyPlaceProvinceValue = companyPlaceProvinceValue;
    }

    public String getLicencePlate() {
        return licencePlate;
    }

    public void setLicencePlate(String licencePlate) {
        this.licencePlate = licencePlate;
    }

    public String getRename_3() {
        return rename_3;
    }

    public void setRename_3(String rename_3) {
        this.rename_3 = rename_3;
    }

    public String getMarriageValue() {
        return marriageValue;
    }

    public void setMarriageValue(String marriageValue) {
        this.marriageValue = marriageValue;
    }

    public String getRename_1() {
        return rename_1;
    }

    public void setRename_1(String rename_1) {
        this.rename_1 = rename_1;
    }

    public String getRename_2() {
        return rename_2;
    }

    public void setRename_2(String rename_2) {
        this.rename_2 = rename_2;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCompanyPlaceProvinceCode() {
        return companyPlaceProvinceCode;
    }

    public void setCompanyPlaceProvinceCode(String companyPlaceProvinceCode) {
        this.companyPlaceProvinceCode = companyPlaceProvinceCode;
    }

    public String getLivingPlaceDetail() {
        return livingPlaceDetail;
    }

    public void setLivingPlaceDetail(String livingPlaceDetail) {
        this.livingPlaceDetail = livingPlaceDetail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAudiValuationPrice() {
        return audiValuationPrice;
    }

    public void setAudiValuationPrice(String audiValuationPrice) {
        this.audiValuationPrice = audiValuationPrice;
    }

    public String getCarFrameNo() {
        return carFrameNo;
    }

    public void setCarFrameNo(String carFrameNo) {
        this.carFrameNo = carFrameNo;
    }

    public String getRepaymentMethodsName() {
        return repaymentMethodsName;
    }

    public void setRepaymentMethodsName(String repaymentMethodsName) {
        this.repaymentMethodsName = repaymentMethodsName;
    }

    public String getProductBigType() {
        return productBigType;
    }

    public void setProductBigType(String productBigType) {
        this.productBigType = productBigType;
    }

    public String getCompanyPlaceDistrictValue() {
        return companyPlaceDistrictValue;
    }

    public void setCompanyPlaceDistrictValue(String companyPlaceDistrictValue) {
        this.companyPlaceDistrictValue = companyPlaceDistrictValue;
    }

    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    public String getLivingPlaceProvinceCode() {
        return livingPlaceProvinceCode;
    }

    public void setLivingPlaceProvinceCode(String livingPlaceProvinceCode) {
        this.livingPlaceProvinceCode = livingPlaceProvinceCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getSurfaceConditionDescription() {
        return surfaceConditionDescription;
    }

    public void setSurfaceConditionDescription(String surfaceConditionDescription) {
        this.surfaceConditionDescription = surfaceConditionDescription;
    }

    public String getMarriage() {
        return marriage;
    }

    public void setMarriage(String marriage) {
        this.marriage = marriage;
    }

    public String getHousingTypeValue() {
        return housingTypeValue;
    }

    public void setHousingTypeValue(String housingTypeValue) {
        this.housingTypeValue = housingTypeValue;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getLivingPlaceCityValue() {
        return livingPlaceCityValue;
    }

    public void setLivingPlaceCityValue(String livingPlaceCityValue) {
        this.livingPlaceCityValue = livingPlaceCityValue;
    }

    public String getRephone_1() {
        return rephone_1;
    }

    public void setRephone_1(String rephone_1) {
        this.rephone_1 = rephone_1;
    }

    public String getEducationValue() {
        return educationValue;
    }

    public void setEducationValue(String educationValue) {
        this.educationValue = educationValue;
    }

    public String getRephone_3() {
        return rephone_3;
    }

    public void setRephone_3(String rephone_3) {
        this.rephone_3 = rephone_3;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    //    public String getCertificateDate() {
    //        return certificateDate;
    //    }
    //
    //    public void setCertificateDate(String certificateDate) {
    //        this.certificateDate = certificateDate;
    //    }

    public String getRephone_2() {
        return rephone_2;
    }

    public void setRephone_2(String rephone_2) {
        this.rephone_2 = rephone_2;
    }

    public String getCompanyPlaceCityValue() {
        return companyPlaceCityValue;
    }

    public void setCompanyPlaceCityValue(String companyPlaceCityValue) {
        this.companyPlaceCityValue = companyPlaceCityValue;
    }

    public String getLoanPurposeCode() {
        return loanPurposeCode;
    }

    public void setLoanPurposeCode(String loanPurposeCode) {
        this.loanPurposeCode = loanPurposeCode;
    }

    public String getValuationPrice() {
        return valuationPrice;
    }

    public void setValuationPrice(String valuationPrice) {
        this.valuationPrice = valuationPrice;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCompanyPlaceDetail() {
        return companyPlaceDetail;
    }

    public void setCompanyPlaceDetail(String companyPlaceDetail) {
        this.companyPlaceDetail = companyPlaceDetail;
    }

    public String getLivingPlaceProvinceValue() {
        return livingPlaceProvinceValue;
    }

    public void setLivingPlaceProvinceValue(String livingPlaceProvinceValue) {
        this.livingPlaceProvinceValue = livingPlaceProvinceValue;
    }

    public String getLoanMount() {
        return loanMount;
    }

    public void setLoanMount(String loanMount) {
        this.loanMount = loanMount;
    }

    public String getCompanyPlaceCityCode() {
        return companyPlaceCityCode;
    }

    public void setCompanyPlaceCityCode(String companyPlaceCityCode) {
        this.companyPlaceCityCode = companyPlaceCityCode;
    }

    public String getRelationship_3() {
        return relationship_3;
    }

    public void setRelationship_3(String relationship_3) {
        this.relationship_3 = relationship_3;
    }

    public String getLivingPlaceDistrictCode() {
        return livingPlaceDistrictCode;
    }

    public void setLivingPlaceDistrictCode(String livingPlaceDistrictCode) {
        this.livingPlaceDistrictCode = livingPlaceDistrictCode;
    }

    public String getRelationship_2() {
        return relationship_2;
    }

    public void setRelationship_2(String relationship_2) {
        this.relationship_2 = relationship_2;
    }

    public String getRelationship_1() {
        return relationship_1;
    }

    public void setRelationship_1(String relationship_1) {
        this.relationship_1 = relationship_1;
    }

    public String getRepaymentPeriodhods() {
        return repaymentPeriodhods;
    }

    public void setRepaymentPeriodhods(String repaymentPeriodhods) {
        this.repaymentPeriodhods = repaymentPeriodhods;
    }

    public String getLivingPlaceDistrictValue() {
        return livingPlaceDistrictValue;
    }

    public void setLivingPlaceDistrictValue(String livingPlaceDistrictValue) {
        this.livingPlaceDistrictValue = livingPlaceDistrictValue;
    }

    public String getSpouseName() {
        return spouseName;
    }

    public void setSpouseName(String spouseName) {
        this.spouseName = spouseName;
    }

    public String getBusiness_id() {
        return business_id;
    }

    public void setBusiness_id(String business_id) {
        this.business_id = business_id;
    }

    public String getRelationship_2Value() {
        return relationship_2Value;
    }

    public void setRelationship_2Value(String relationship_2Value) {
        this.relationship_2Value = relationship_2Value;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getTrimId() {
        return TrimId;
    }

    public void setTrimId(String trimId) {
        TrimId = trimId;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    /*private String companyPlaceCityCode = "";
    private String companyPlaceDetail = "";
    private String carFrameNo = "";
    private String phone = "";
    private String isLockEd = "";
    private String companyPhone = "";
    private String loanPurposeValue = "";
    private String companyPlaceCityValue = "";
    private String repaymentMethodsValue = "";
    private String customerBaseInfoId = "";
    private String loanPurposeCode = "";
    private String housingTypeCode = "";
    private String housingTypeValue = "";
    private String companyPlaceDistrictCode = "";
    private String mileage = "";
    private String livingPlaceProvinceCode = "";
    private String repaymentMethods = "";
    private String educationValue = "";
    private String livingPlaceDistrictValue = "";
    private String companyName = "";
    private String livingPlaceDetail = "";
    private String livingPlaceProvinceValue = "";
    private String carInfoId = "";
    private String loanStatus = "";
    private String loanInfoId = "";
    private String sexValue = "";
    private String companyPlaceDistrictValue = "";
    private String productTypesCode = "";
    private String sex = "";
    private String produceDate = "";
    private String licencePlate = "";
    private String companyPlaceProvinceCode = "";
    private String customerRelationCode = "";
    private String education = "";
    private String repaymentPeriodhods = "";
    private String name = "";
    private String marriageValue = "";
    private String assess = "";
    private String loanMount = "";
    private String idCard = "";
    private String livingPlaceDistrictCode = "";
    private String livingPlaceCityCode = "";
    private String marriage = "";
    private String productTypesValue = "";
    private String versions = "";
    private String carModel = "";
    private String idCardValidityDates = "";
    private String livingPlaceCityValue = "";
    private String brand = "";
    private String companyPlaceProvinceValue = "";
    private String evaluationPrice = "";
    private String surfaceConditionDescription = "";

    private String homePlaceProvinceCode = "";
    private String homePlaceProvinceValue = "";//："户籍地址省份value",
    private String homePlaceCityCode = "";//":"户籍地址市code",
    private String homePlaceCityValue = "";//":"户籍地址市value",
    private String homePlaceDistrictCode = "";//":"户籍地址区code",
    private String homePlaceDistrictValue = "";//":"户籍地址value",
    private String homePlaceDetail = "";//":"户籍详细地址",
    private String rename_1 = "";//":"客户联系人1姓名",
    private String rephone_1 = "";//":"客户联系人1电话",
    private String relationship_1 = "";//":"客户联系人1关系",
    private String relationship_1Value = "";
    private String rename_2 = "";//":"客户联系人2姓名",
    private String rephone_2 = "";//":"客户联系人2电话",
    private String relationship_2 = "";//":"客户联系人2关系",
    private String relationship_2Value = "";
    private String rename_3 = "";//":"客户联系人3姓名",
    private String rephone_3 = "";//":"客户联系人3电话",
    private String relationship_3 = "";//":"客户联系人3关系",
    private String relationship_3Value = "";
    private String carEngineNo = "";//":"发动机号",
    private String carColor = "";//":"车身颜色",
    private String carFuel = "";//":"燃料",
    private String certificateDate = "";//":"发证日期",
    private String registerDate = "";//":"注册日期"
    private String backReason = "";
    private String spouseName = "";
    private String spouseIdcard = "";
    private String spousePhone = "";
    private String audiAmount = "";
    private String audiTerm = "";
    private String audiProduct = "";
    private String cityId = "";
    private String trimId = "";
    private String cityName = "";
    private String valuationPriceReall = "";
    private String valuationPrice= "";
    private String audiValuationPrice = "";

    public String getValuationPrice() {
        return valuationPrice;
    }

    public void setValuationPrice(String valuationPrice) {
        this.valuationPrice = valuationPrice;
    }

    public String getAudiValuationPrice() {
        return audiValuationPrice;
    }

    public void setAudiValuationPrice(String audiValuationPrice) {
        this.audiValuationPrice = audiValuationPrice;
    }

    public String getValuationPriceReall() {
        return valuationPriceReall;
    }

    public void setValuationPriceReall(String valuationPriceReall) {
        this.valuationPriceReall = valuationPriceReall;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getTrimId() {
        return trimId;
    }

    public void setTrimId(String trimId) {
        this.trimId = trimId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getAudiAmount() {
        return audiAmount;
    }

    public void setAudiAmount(String audiAmount) {
        this.audiAmount = audiAmount;
    }

    public String getAudiTerm() {
        return audiTerm;
    }

    public void setAudiTerm(String audiTerm) {
        this.audiTerm = audiTerm;
    }

    public String getAudiProduct() {
        return audiProduct;
    }

    public void setAudiProduct(String audiProduct) {
        this.audiProduct = audiProduct;
    }

    public String getSpouseName() {
        return spouseName;
    }

    public void setSpouseName(String spouseName) {
        this.spouseName = spouseName;
    }

    public String getSpouseIdcard() {
        return spouseIdcard;
    }

    public void setSpouseIdcard(String spouseIdcard) {
        this.spouseIdcard = spouseIdcard;
    }

    public String getSpousePhone() {
        return spousePhone;
    }

    public void setSpousePhone(String spousePhone) {
        this.spousePhone = spousePhone;
    }

    public String getBackReason() {
        return backReason;
    }

    public void setBackReason(String backReason) {
        this.backReason = backReason;
    }

    public String getEvaluationPrice() {
        return evaluationPrice;
    }

    public void setEvaluationPrice(String evaluationPrice) {
        this.evaluationPrice = evaluationPrice;
    }

    public String getSurfaceConditionDescription() {
        return surfaceConditionDescription;
    }

    public void setSurfaceConditionDescription(String surfaceConditionDescription) {
        this.surfaceConditionDescription = surfaceConditionDescription;
    }

    public String getCompanyPlaceCityCode() {
        return companyPlaceCityCode;
    }

    public void setCompanyPlaceCityCode(String companyPlaceCityCode) {
        this.companyPlaceCityCode = companyPlaceCityCode;
    }

    public String getCompanyPlaceDetail() {
        return companyPlaceDetail;
    }

    public void setCompanyPlaceDetail(String companyPlaceDetail) {
        this.companyPlaceDetail = companyPlaceDetail;
    }

    public String getCarFrameNo() {
        return carFrameNo;
    }

    public void setCarFrameNo(String carFrameNo) {
        this.carFrameNo = carFrameNo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIsLockEd() {
        return isLockEd;
    }

    public void setIsLockEd(String isLockEd) {
        this.isLockEd = isLockEd;
    }

    public String getCompanyPhone() {
        return companyPhone;
    }

    public void setCompanyPhone(String companyPhone) {
        this.companyPhone = companyPhone;
    }

    public String getLoanPurposeValue() {
        return loanPurposeValue;
    }

    public void setLoanPurposeValue(String loanPurposeValue) {
        this.loanPurposeValue = loanPurposeValue;
    }

    public String getCompanyPlaceCityValue() {
        return companyPlaceCityValue;
    }

    public void setCompanyPlaceCityValue(String companyPlaceCityValue) {
        this.companyPlaceCityValue = companyPlaceCityValue;
    }

    public String getRepaymentMethodsValue() {
        return repaymentMethodsValue;
    }

    public void setRepaymentMethodsValue(String repaymentMethodsValue) {
        this.repaymentMethodsValue = repaymentMethodsValue;
    }

    public String getCustomerBaseInfoId() {
        return customerBaseInfoId;
    }

    public void setCustomerBaseInfoId(String customerBaseInfoId) {
        this.customerBaseInfoId = customerBaseInfoId;
    }

    public String getLoanPurposeCode() {
        return loanPurposeCode;
    }

    public void setLoanPurposeCode(String loanPurposeCode) {
        this.loanPurposeCode = loanPurposeCode;
    }

    public String getHousingTypeCode() {
        return housingTypeCode;
    }

    public void setHousingTypeCode(String housingTypeCode) {
        this.housingTypeCode = housingTypeCode;
    }

    public String getHousingTypeValue() {
        return housingTypeValue;
    }

    public void setHousingTypeValue(String housingTypeValue) {
        this.housingTypeValue = housingTypeValue;
    }

    public String getCompanyPlaceDistrictCode() {
        return companyPlaceDistrictCode;
    }

    public void setCompanyPlaceDistrictCode(String companyPlaceDistrictCode) {
        this.companyPlaceDistrictCode = companyPlaceDistrictCode;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public String getLivingPlaceProvinceCode() {
        return livingPlaceProvinceCode;
    }

    public void setLivingPlaceProvinceCode(String livingPlaceProvinceCode) {
        this.livingPlaceProvinceCode = livingPlaceProvinceCode;
    }

    public String getRepaymentMethods() {
        return repaymentMethods;
    }

    public void setRepaymentMethods(String repaymentMethods) {
        this.repaymentMethods = repaymentMethods;
    }

    public String getEducationValue() {
        return educationValue;
    }

    public void setEducationValue(String educationValue) {
        this.educationValue = educationValue;
    }

    public String getLivingPlaceDistrictValue() {
        return livingPlaceDistrictValue;
    }

    public void setLivingPlaceDistrictValue(String livingPlaceDistrictValue) {
        this.livingPlaceDistrictValue = livingPlaceDistrictValue;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getLivingPlaceDetail() {
        return livingPlaceDetail;
    }

    public void setLivingPlaceDetail(String livingPlaceDetail) {
        this.livingPlaceDetail = livingPlaceDetail;
    }

    public String getLivingPlaceProvinceValue() {
        return livingPlaceProvinceValue;
    }

    public void setLivingPlaceProvinceValue(String livingPlaceProvinceValue) {
        this.livingPlaceProvinceValue = livingPlaceProvinceValue;
    }

    public String getCarInfoId() {
        return carInfoId;
    }

    public void setCarInfoId(String carInfoId) {
        this.carInfoId = carInfoId;
    }

    public String getLoanStatus() {
        return loanStatus;
    }

    public void setLoanStatus(String loanStatus) {
        this.loanStatus = loanStatus;
    }

    public String getLoanInfoId() {
        return loanInfoId;
    }

    public void setLoanInfoId(String loanInfoId) {
        this.loanInfoId = loanInfoId;
    }

    public String getSexValue() {
        return sexValue;
    }

    public void setSexValue(String sexValue) {
        this.sexValue = sexValue;
    }

    public String getCompanyPlaceDistrictValue() {
        return companyPlaceDistrictValue;
    }

    public void setCompanyPlaceDistrictValue(String companyPlaceDistrictValue) {
        this.companyPlaceDistrictValue = companyPlaceDistrictValue;
    }

    public String getProductTypesCode() {
        return productTypesCode;
    }

    public void setProductTypesCode(String productTypesCode) {
        this.productTypesCode = productTypesCode;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getProduceDate() {
        return produceDate;
    }

    public void setProduceDate(String produceDate) {
        this.produceDate = produceDate;
    }

    public String getLicencePlate() {
        return licencePlate;
    }

    public void setLicencePlate(String licencePlate) {
        this.licencePlate = licencePlate;
    }

    public String getCompanyPlaceProvinceCode() {
        return companyPlaceProvinceCode;
    }

    public void setCompanyPlaceProvinceCode(String companyPlaceProvinceCode) {
        this.companyPlaceProvinceCode = companyPlaceProvinceCode;
    }

    public String getCustomerRelationCode() {
        return customerRelationCode;
    }

    public void setCustomerRelationCode(String customerRelationCode) {
        this.customerRelationCode = customerRelationCode;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getRepaymentPeriodhods() {
        return repaymentPeriodhods;
    }

    public void setRepaymentPeriodhods(String repaymentPeriodhods) {
        this.repaymentPeriodhods = repaymentPeriodhods;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMarriageValue() {
        return marriageValue;
    }

    public void setMarriageValue(String marriageValue) {
        this.marriageValue = marriageValue;
    }

    public String getAssess() {
        return assess;
    }

    public void setAssess(String assess) {
        this.assess = assess;
    }

    public String getLoanMount() {
        return loanMount;
    }

    public void setLoanMount(String loanMount) {
        this.loanMount = loanMount;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getLivingPlaceDistrictCode() {
        return livingPlaceDistrictCode;
    }

    public void setLivingPlaceDistrictCode(String livingPlaceDistrictCode) {
        this.livingPlaceDistrictCode = livingPlaceDistrictCode;
    }

    public String getLivingPlaceCityCode() {
        return livingPlaceCityCode;
    }

    public void setLivingPlaceCityCode(String livingPlaceCityCode) {
        this.livingPlaceCityCode = livingPlaceCityCode;
    }

    public String getMarriage() {
        return marriage;
    }

    public void setMarriage(String marriage) {
        this.marriage = marriage;
    }

    public String getProductTypesValue() {
        return productTypesValue;
    }

    public void setProductTypesValue(String productTypesValue) {
        this.productTypesValue = productTypesValue;
    }

    public String getVersions() {
        return versions;
    }

    public void setVersions(String versions) {
        this.versions = versions;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getIdCardValidityDates() {
        return idCardValidityDates;
    }

    public void setIdCardValidityDates(String idCardValidityDates) {
        this.idCardValidityDates = idCardValidityDates;
    }

    public String getLivingPlaceCityValue() {
        return livingPlaceCityValue;
    }

    public void setLivingPlaceCityValue(String livingPlaceCityValue) {
        this.livingPlaceCityValue = livingPlaceCityValue;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCompanyPlaceProvinceValue() {
        return companyPlaceProvinceValue;
    }

    public void setCompanyPlaceProvinceValue(String companyPlaceProvinceValue) {
        this.companyPlaceProvinceValue = companyPlaceProvinceValue;
    }

    public String getHomePlaceProvinceCode() {
        return homePlaceProvinceCode;
    }

    public void setHomePlaceProvinceCode(String homePlaceProvinceCode) {
        this.homePlaceProvinceCode = homePlaceProvinceCode;
    }

    public String getHomePlaceProvinceValue() {
        return homePlaceProvinceValue;
    }

    public void setHomePlaceProvinceValue(String homePlaceProvinceValue) {
        this.homePlaceProvinceValue = homePlaceProvinceValue;
    }

    public String getHomePlaceCityCode() {
        return homePlaceCityCode;
    }

    public void setHomePlaceCityCode(String homePlaceCityCode) {
        this.homePlaceCityCode = homePlaceCityCode;
    }

    public String getHomePlaceCityValue() {
        return homePlaceCityValue;
    }

    public void setHomePlaceCityValue(String homePlaceCityValue) {
        this.homePlaceCityValue = homePlaceCityValue;
    }

    public String getHomePlaceDistrictCode() {
        return homePlaceDistrictCode;
    }

    public void setHomePlaceDistrictCode(String homePlaceDistrictCode) {
        this.homePlaceDistrictCode = homePlaceDistrictCode;
    }

    public String getHomePlaceDistrictValue() {
        return homePlaceDistrictValue;
    }

    public void setHomePlaceDistrictValue(String homePlaceDistrictValue) {
        this.homePlaceDistrictValue = homePlaceDistrictValue;
    }

    public String getHomePlaceDetail() {
        return homePlaceDetail;
    }

    public void setHomePlaceDetail(String homePlaceDetail) {
        this.homePlaceDetail = homePlaceDetail;
    }

    public String getRename_1() {
        return rename_1;
    }

    public void setRename_1(String rename_1) {
        this.rename_1 = rename_1;
    }

    public String getRephone_1() {
        return rephone_1;
    }

    public void setRephone_1(String rephone_1) {
        this.rephone_1 = rephone_1;
    }

    public String getRelationship_1() {
        return relationship_1;
    }

    public void setRelationship_1(String relationship_1) {
        this.relationship_1 = relationship_1;
    }

    public String getRename_2() {
        return rename_2;
    }

    public void setRename_2(String rename_2) {
        this.rename_2 = rename_2;
    }

    public String getRephone_2() {
        return rephone_2;
    }

    public void setRephone_2(String rephone_02) {
        this.rephone_2 = rephone_2;
    }

    public String getRelationship_2() {
        return relationship_2;
    }

    public void setRelationship_2(String relationship_02) {
        this.relationship_2 = relationship_02;
    }

    public String getRename_3() {
        return rename_3;
    }

    public void setRename_3(String rename_3) {
        this.rename_3 = rename_3;
    }

    public String getRephone_3() {
        return rephone_3;
    }

    public void setRephone_3(String rephone_3) {
        this.rephone_3 = rephone_3;
    }

    public String getRelationship_3() {
        return relationship_3;
    }

    public void setRelationship_3(String relationship_3) {
        this.relationship_3 = relationship_3;
    }

    public String getCarEngineNo() {
        return carEngineNo;
    }

    public void setCarEngineNo(String carEngineNo) {
        this.carEngineNo = carEngineNo;
    }

    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    public String getCarFuel() {
        return carFuel;
    }

    public void setCarFuel(String carFuel) {
        this.carFuel = carFuel;
    }

    public String getCertificateDate() {
        return certificateDate;
    }

    public void setCertificateDate(String certificateDate) {
        this.certificateDate = certificateDate;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public String getRelationship_1Value() {
        return relationship_1Value;
    }

    public void setRelationship_1Value(String relationship_1Value) {
        this.relationship_1Value = relationship_1Value;
    }

    public String getRelationship_02Value() {
        return relationship_2Value;
    }

    public void setRelationship_2Value(String relationship_2Value) {
        this.relationship_2Value = relationship_2Value;
    }

    public String getRelationship_3Value() {
        return relationship_3Value;
    }

    public void setRelationship_3Value(String relationship_3Value) {
        this.relationship_3Value = relationship_3Value;
    }*/
}
