package com.zimi.zimixing.bean;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * GPS监控 分组授权  公司人员分组名单
 * <p>
 * <br> Author: 叶青
 * <br> Version: 6.0.0
 * <br> Date: 2018/1/11
 * <br> Copyright: Copyright © 2017 xTeam Technology. All rights reserved.
 */
public class CompanyUserListBean extends BaseBean {

    //"username": "15999526775",
    private String username = "";
    //"name": "测试",
    private String name = "";
    //"permissionNames": "风控家访",
    private String permissionNames = "";
    //"user_id": 1352,
    private String user_id = "";
    //"permissionKeys": "dytApi:RISK:VISIT"
    private String permissionKeys = "";

    private ArrayList<GpsMonitorHomeListBean> storyList = new ArrayList<>();

    @Override
    protected void init(JSONObject jSon) throws JSONException {
        username = jSon.optString("username", "");
        name = jSon.optString("name", "");
        permissionNames = jSon.optString("permissionNames", "");
        permissionKeys = jSon.optString("permissionKeys", "");
        user_id = jSon.optString("user_id", "");

        storyList = (ArrayList<GpsMonitorHomeListBean>) BaseBean.toModelList(jSon.optString("returnList", ""), GpsMonitorHomeListBean.class);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPermissionNames() {
        return permissionNames;
    }

    public void setPermissionNames(String permissionNames) {
        this.permissionNames = permissionNames;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPermissionKeys() {
        return permissionKeys;
    }

    public void setPermissionKeys(String permissionKeys) {
        this.permissionKeys = permissionKeys;
    }

    public ArrayList<GpsMonitorHomeListBean> getStoryList() {
        return storyList;
    }

    public void setStoryList(ArrayList<GpsMonitorHomeListBean> storyList) {
        this.storyList = storyList;
    }
}
