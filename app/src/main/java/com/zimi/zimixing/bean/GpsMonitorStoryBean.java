package com.zimi.zimixing.bean;


import com.zimi.zimixing.utils.MathUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 门店监控设备列表数据
 * <p>
 * <br> Author: 叶青
 * <br> Version: 6.0.0
 * <br> Date: 2018/1/11
 * <br> Copyright: Copyright © 2017 xTeam Technology. All rights reserved.
 */
public class GpsMonitorStoryBean extends BaseBean {

    // status : 设备报警
    private String status="";
    // speed : 0
    private String speed="";
    // accState : 1  0关 1开
    private String accState="";
    // orgName : 广东
    private String orgName="";
    // type : 1  设备类型 0-有线，1-无线
    private String type="";
    // warning_name : 越界报警
    private String warning_name="";
    // location_time : 2017-08-27 14:17:57
    private String location_time="";
    // driveStatus : 静止
    private String driveStatus="";
    // name : 测试14530108763
    private String name="";
    // carNumber : 车牌0876
    private String carNumber="";
    // imeiId : 14530108763
    private String imeiId="";
    // warning_type : 5
    private String warning_type="";
    // beOnline : 3
    private String beOnline="";
    // online_status : 0
    private String online_status="";
    private String color="";


    @Override
    protected void init(JSONObject jSon) throws JSONException {
//        private String status;
        status = jSon.optString("status", "");
//        private double speed;
        speed = MathUtils.round4String(jSon.optString("speed", ""),2);
        //        private int accState;
        accState = jSon.optString("accState", "");
        //        private String orgName;
        orgName = jSon.optString("orgName", "");
        //        private int type;
        type = jSon.optString("type", "");
        //        private String warning_name;
        warning_name = jSon.optString("warning_name", "");
        //        private String location_time;
        location_time = jSon.optString("location_time", "");
        //        private String driveStatus;
        driveStatus = jSon.optString("driveStatus", "");
        //        private String name;
        name = jSon.optString("name", "");
        //        private String carNumber;
        carNumber = jSon.optString("carNumber", "");
        //        private String imeiId;
        imeiId = jSon.optString("imeiId", "");
        //        private int warning_type;
        warning_type = jSon.optString("warning_type", "");
        //        private int beOnline;
        beOnline = jSon.optString("beOnline", "");
        //        private int online_status;
        online_status = jSon.optString("online_status", "");
        //        private String color;
        color = jSon.optString("color", "");
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getAccState() {
        return accState;
    }

    public void setAccState(String accState) {
        this.accState = accState;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWarning_name() {
        return warning_name;
    }

    public void setWarning_name(String warning_name) {
        this.warning_name = warning_name;
    }

    public String getLocation_time() {
        return location_time;
    }

    public void setLocation_time(String location_time) {
        this.location_time = location_time;
    }

    public String getDriveStatus() {
        return driveStatus;
    }

    public void setDriveStatus(String driveStatus) {
        this.driveStatus = driveStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getImeiId() {
        return imeiId;
    }

    public void setImeiId(String imeiId) {
        this.imeiId = imeiId;
    }

    public String getWarning_type() {
        return warning_type;
    }

    public void setWarning_type(String warning_type) {
        this.warning_type = warning_type;
    }

    public String getBeOnline() {
        return beOnline;
    }

    public void setBeOnline(String beOnline) {
        this.beOnline = beOnline;
    }

    public String getOnline_status() {
        return online_status;
    }

    public void setOnline_status(String online_status) {
        this.online_status = online_status;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
