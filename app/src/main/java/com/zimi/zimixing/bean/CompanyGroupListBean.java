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
public class CompanyGroupListBean extends BaseBean {

    //"users": [{
    //"username": "15999526775",
    //"name": "测试",
    //"permissionNames": "风控家访",
    //"user_id": 1352,
    //"permissionKeys": "dytApi:RISK:VISIT"
    //}],
    //"name": "广州门店"

    //"name": "广州门店"
    private String name = "";

    private ArrayList<CompanyUserListBean> users = new ArrayList<>();

    @Override
    protected void init(JSONObject jSon) throws JSONException {
        name = jSon.optString("name", "");
        users = (ArrayList<CompanyUserListBean>) BaseBean.toModelList(jSon.optString("users", ""), CompanyUserListBean.class);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<CompanyUserListBean> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<CompanyUserListBean> users) {
        this.users = users;
    }
}
