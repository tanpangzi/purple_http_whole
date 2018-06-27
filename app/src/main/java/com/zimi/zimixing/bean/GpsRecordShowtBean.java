package com.zimi.zimixing.bean;


import com.zimi.zimixing.utils.JsonUtil;
import com.zimi.zimixing.utils.MathUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 轨迹回放数据
 * <p>
 * <br> Author: 叶青
 * <br> Version: 6.0.0
 * <br> Date: 2018/1/11
 * <br> Copyright: Copyright © 2017 xTeam Technology. All rights reserved.
 */
public class GpsRecordShowtBean extends BaseBean {

    /**
     * receiveDate : 1491543450000 定位时间
     * speed : 37 速度
     * altitude : 0
     * direction : 81 方向
     * imei : 14530098897
     * state : 未定位  (有线为：定位，未定位  ；无线为 gps定位，基站定位)
     * accState : ACC状态:关 仅有线设备有acc状态
     * longitude : 114.1314468384
     * latitude : 22.5487003326
     * createDate : 1491543463000 接收时间
     */

    private String receiveDate="";
    private String speed="";
    private int altitude;
    private String direction;
    private String imei="";
    private String state="";
    private String accState="";
    private double longitude ;
    private double latitude ;
    private String createDate="";

    @Override
    protected void init(JSONObject jSon) throws JSONException {
        receiveDate = JsonUtil.optString(jSon, "receiveDate", "");
        imei = JsonUtil.optString(jSon, "imei", "");
        state = JsonUtil.optString(jSon, "state", "");
        accState = JsonUtil.optString(jSon, "accState", "");
        createDate = JsonUtil.optString(jSon, "createDate", "");
        longitude = jSon.optDouble("longitude", 0.0);
        latitude = jSon.optDouble("latitude", 0.0);
        altitude = jSon.optInt("altitude", -10);
        direction = jSon.optString("direction", "");
        speed = MathUtils.round4String(jSon.optString("speed", ""),2);
    }

    public String getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(String receiveDate) {
        this.receiveDate = receiveDate;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public int getAltitude() {
        return altitude;
    }

    public void setAltitude(int altitude) {
        this.altitude = altitude;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAccState() {
        return accState;
    }

    public void setAccState(String accState) {
        this.accState = accState;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
