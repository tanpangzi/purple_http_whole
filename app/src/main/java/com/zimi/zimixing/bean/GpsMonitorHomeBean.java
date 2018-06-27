package com.zimi.zimixing.bean;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * GPS监控首页数据
 * <p>
 * <br> Author: 叶青
 * <br> Version: 6.0.0
 * <br> Date: 2018/1/11
 * <br> Copyright: Copyright © 2017 xTeam Technology. All rights reserved.
 */
public class GpsMonitorHomeBean extends BaseBean {


    //* invalid : 0 当前门店下失效设备总数
    private String invalid = "";
    //* sum : 3  当前门店下设备总数
    private String sum = "";
    //* offline : 0  当前门店下离线设备总数
    private String offline = "";
    //* online : 3 当前门店下在线设备总数
    private String online = "";

    private ArrayList<GpsMonitorHomeListBean> storyList = new ArrayList<>();

    @Override
    protected void init(JSONObject jSon) throws JSONException {
        invalid = jSon.optString("invalid", "0");
        sum = jSon.optString("sum", "0");
        offline = jSon.optString("offline", "0");
        online = jSon.optString("online", "0");

        storyList = (ArrayList<GpsMonitorHomeListBean>) BaseBean.toModelList(jSon.optString("returnList",""),GpsMonitorHomeListBean.class);
    }


    public String getInvalid() {
        return invalid;
    }

    public void setInvalid(String invalid) {
        this.invalid = invalid;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }


    public String getOffline() {
        return offline;
    }

    public void setOffline(String offline) {
        this.offline = offline;
    }

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }

    public ArrayList<GpsMonitorHomeListBean> getStoryList() {
        return storyList;
    }

    public void setStoryList(ArrayList<GpsMonitorHomeListBean> storyList) {
        this.storyList = storyList;
    }
}
