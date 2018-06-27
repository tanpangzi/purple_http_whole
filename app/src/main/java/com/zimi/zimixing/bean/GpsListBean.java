package com.zimi.zimixing.bean;


import com.zimi.zimixing.utils.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 设备位置信息
 * <p>
 * <br> Author: 叶青
 * <br> Version: 6.0.0
 * <br> Date: 2018/1/11
 * <br> Copyright: Copyright © 2017 xTeam Technology. All rights reserved.
 */
public class GpsListBean extends BaseBean {

    /**
     * gpsOrGms : 0
     * serviceDate : 2017-04-25 17:40:03  服务结束时间
     * status : 离线
     * accState : 0  acc状态1：开   0:关仅有线设备有ACC状态
     * simId : 14530107833_1  sim 卡
     * onlineStatus : 1  设备在线状态：0-在线，1-离线
     * type : 1  设备类型 0-有线，1-无线
     * gsmSignalIntensity : null  无线设备：GSM信号强度
     * phoneNum : 13614215872  客户电话
     * installDate : 2017-04-25 17:40:03  服务开始时间-
     * name : 测试14530107833  客户名称
     * imeiId : 14530107833   设备ID
     * longitude : 116.15162759288133  经度
     * versionType : HX-110  设备型号
     * latitude : 24.97769623544669  纬度
     * locationState : 0  是否定位  0:未定位  1:定位
     * batteryCapacity : null  无线设备：电池容量
     * gpsSignalIntensity : null
     * mileageSum : 0  设备记录的车辆总里程
     * numberPlates : 车牌0783  车牌
     * speed
     */

    private String gpsOrGms = "";
    private String serviceDate = "";
    private String status = "";
    private String accState = "";
    private String simId = "";
    private int onlineStatus = -10;
    private int type = -10;
    private String gsmSignalIntensity = "";
    private String phoneNum = "";
    private String installDate = "";
    private String name = "";
    private String imeiId = "";
    private double longitude = 0.0;
    private String versionType = "";
    private double latitude = 0.0;
    private int locationState = -10;
    private String batteryCapacity = "";
    private int gpsSignalIntensity = -10;
    private int mileageSum = -10;
    //        private String numberPlates = "";
    private String speed = "";
    private String direction = "";
    private String receiveDate = "";
    private String stateLength = "";
    private String locationDate = "";
    private String color = "";
    private String carNumber = "";

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getLocationDate() {
        return locationDate;
    }

    public void setLocationDate(String locationDate) {
        this.locationDate = locationDate;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(String receiveDate) {
        this.receiveDate = receiveDate;
    }

    public String getStateLength() {
        return stateLength;
    }

    public void setStateLength(String stateLength) {
        this.stateLength = stateLength;
    }

    public String getGpsOrGms() {
        return gpsOrGms;
    }

    public void setGpsOrGms(String gpsOrGms) {
        this.gpsOrGms = gpsOrGms;
    }

    public String getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(String serviceDate) {
        this.serviceDate = serviceDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAccState() {
        return accState;
    }

    public void setAccState(String accState) {
        this.accState = accState;
    }

    public String getSimId() {
        return simId;
    }

    public void setSimId(String simId) {
        this.simId = simId;
    }

    public int getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(int onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getGsmSignalIntensity() {
        return gsmSignalIntensity;
    }

    public void setGsmSignalIntensity(String gsmSignalIntensity) {
        this.gsmSignalIntensity = gsmSignalIntensity;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getInstallDate() {
        return installDate;
    }

    public void setInstallDate(String installDate) {
        this.installDate = installDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImeiId() {
        return imeiId;
    }

    public void setImeiId(String imeiId) {
        this.imeiId = imeiId;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getVersionType() {
        return versionType;
    }

    public void setVersionType(String versionType) {
        this.versionType = versionType;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public int getLocationState() {
        return locationState;
    }

    public void setLocationState(int locationState) {
        this.locationState = locationState;
    }

    public String getBatteryCapacity() {
        return batteryCapacity;
    }

    public void setBatteryCapacity(String batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }

    public int getGpsSignalIntensity() {
        return gpsSignalIntensity;
    }

    public void setGpsSignalIntensity(int gpsSignalIntensity) {
        this.gpsSignalIntensity = gpsSignalIntensity;
    }

    public int getMileageSum() {
        return mileageSum;
    }

    public void setMileageSum(int mileageSum) {
        this.mileageSum = mileageSum;
    }

    //        public String getNumberPlates() {
    //            return numberPlates;
    //        }
    //
    //        public void setNumberPlates(String numberPlates) {
    //            this.numberPlates = numberPlates;
    //        }


    @Override
    protected void init(JSONObject jSon) throws JSONException {
        gpsOrGms = JsonUtil.optString(jSon, "gpsOrGms", "");
        serviceDate = JsonUtil.optString(jSon, "serviceDate", "");
        status = JsonUtil.optString(jSon, "status", "");
        accState = JsonUtil.optString(jSon, "accState", "");
        simId = JsonUtil.optString(jSon, "simId", "");
        onlineStatus = jSon.optInt("onlineStatus", -10);
        type = jSon.optInt("type", -10);
        gsmSignalIntensity = JsonUtil.optString(jSon, "gsmSignalIntensity", "");
        phoneNum = JsonUtil.optString(jSon, "phoneNum", "");
        installDate = JsonUtil.optString(jSon, "installDate", "");
        name = JsonUtil.optString(jSon, "name", "");
        imeiId = JsonUtil.optString(jSon, "imeiId", "");
        longitude = jSon.optDouble("longitude", 0.0);
        versionType = JsonUtil.optString(jSon, "versionType", "");
        latitude = jSon.optDouble("latitude", 0.0);
        locationState = jSon.optInt("locationState", -10);
        batteryCapacity = JsonUtil.optString(jSon, "batteryCapacity", "");
        gpsSignalIntensity = jSon.optInt("gpsSignalIntensity", -10);
        mileageSum = jSon.optInt("mileageSum", -10);
        speed = JsonUtil.optString(jSon, "speed", "");
        direction = JsonUtil.optString(jSon, "direction", "");
        receiveDate = JsonUtil.optString(jSon, "receiveDate", "");
        stateLength = JsonUtil.optString(jSon, "stateLength", "");
        locationDate = JsonUtil.optString(jSon, "locationDate", "");
        color = JsonUtil.optString(jSon, "color", "");
        carNumber = JsonUtil.optString(jSon, "carNumber", "");
    }
}
