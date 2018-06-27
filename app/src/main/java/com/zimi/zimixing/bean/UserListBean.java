package com.zimi.zimixing.bean;


import com.zimi.zimixing.utils.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 门店用户列表
 * <p>
 * <br> Author: 叶青
 * <br> Version: 6.0.0
 * <br> Date: 2018/1/11
 * <br> Copyright: Copyright © 2017 xTeam Technology. All rights reserved.
 */
public class UserListBean extends BaseBean {

    /**
     * userAccount : admin
     * userChnName : 管理员
     */
    private String userAccount="";
    private String userChnName="";

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserChnName() {
        return userChnName;
    }

    public void setUserChnName(String userChnName) {
        this.userChnName = userChnName;
    }

    @Override
    protected void init(JSONObject jSon) throws JSONException {
        userChnName = JsonUtil.optString(jSon, "userChnName", "");
        userAccount = JsonUtil.optString(jSon, "userAccount", "");
    }
}
