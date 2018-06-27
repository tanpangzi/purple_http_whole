package com.zimi.zimixing.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 用户信息bean
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class UserInfoBean extends BaseBean {

    //    "data": {
    //                "loginAccount": "13500000000",
    //                "accountName": "",
    //                "alias": "77ae4e2a5d20bc28b5118b652c9e2780",
    //                 userId : 1093
    //                 userName : 吴宪加
    //         * roleName : 客户经理
    //                "menuPermission": [
    //        {
    //            "permissionKey": "dytApi:RISK:VISIT",
    //                "permissionName": "风控家访",
    //                "dimensionKey": "MENU",
    //                "userId": 1270,
    //                "templateId": 12,
    //                "tenantId": null,
    //                "recStatus": "A",
    //                "createdBy": "system",
    //                "createdDate": 1508699695000,
    //                "updatedBy": "system",
    //                "updatedDate": 1508699695000
    //        },
    //        {
    //            "permissionKey": "dytApi:MANAGER",
    //                "permissionName": "门店经理",
    //                "dimensionKey": "MENU",
    //                "userId": 1270,
    //                "templateId": 12,
    //                "tenantId": null,
    //                "recStatus": "A",
    //                "createdBy": "system",
    //                "createdDate": 1511028892000,
    //                "updatedBy": "system",
    //                "updatedDate": 1511028892000
    //        }]
    //    }

    /** 账号 */
    private String loginAccount = "";
    /** 密码 */
    private String password = "";
    /** accountName */
    private String accountName = "";
    /** 极光托送alias */
    private String alias = "";
    /** 吴宪加 */
    private String userName = "";
    /** userId */
    private String userId = "";
    /** token */
    private String token = "";
    /** 客户经理 */
    private String roleName = "";
    /** 权限列表 */
    private ArrayList<MenuPermissionBean> menuPermission = new ArrayList<>();


    @Override
    protected void init(JSONObject jSon) throws JSONException {

        loginAccount = jSon.optString("loginAccount", "");
        accountName = jSon.optString("accountName", "");
        userName = jSon.optString("userName", "");
        alias = jSon.optString("alias", "");
        userId = jSon.optString("userId", "");
        token = jSon.optString("token", "");
        roleName = jSon.optString("roleName", "");

        menuPermission = (ArrayList<MenuPermissionBean>) BaseBean.toModelList(jSon.optString("menuPermission", ""), MenuPermissionBean.class);
    }

    public String getLoginAccount() {
        return loginAccount;
    }

    public void setLoginAccount(String loginAccount) {
        this.loginAccount = loginAccount;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ArrayList<MenuPermissionBean> getMenuPermission() {
        return menuPermission;
    }

    public void setMenuPermission(ArrayList<MenuPermissionBean> menuPermission) {
        this.menuPermission = menuPermission;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
