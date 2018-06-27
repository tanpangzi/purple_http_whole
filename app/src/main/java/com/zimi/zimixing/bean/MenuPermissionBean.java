package com.zimi.zimixing.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 账户权限列表数据
 * <p>
 * <br> Author: 叶青
 * <br> Version: 6.0.0
 * <br> Date: 2018/1/4
 * <br> Copyright: Copyright © 2017 xTeam Technology. All rights reserved.
 */
public class MenuPermissionBean extends BaseBean {
    /**
     * permissionKey : dytApi:ASSESS
     * permissionName : 车辆评估
     * dimensionKey : MENU
     * userId : 78
     * templateId : 10
     * tenantId : null
     * recStatus : A
     * createdBy : system
     * createdDate : 1505310275000
     * updatedBy : system
     * updatedDate : 1505310275000
     */

    private String permissionKey = "";
    private String permissionName = "";
    private String dimensionKey = "";
    private String userId = "";
    private String templateId = "";
    private String tenantId = "";
    private String recStatus = "";
    private String createdBy = "";
    private long createdDate;
    private String updatedBy = "";
    private long updatedDate;

    @Override
    protected void init(JSONObject jSon) throws JSONException {
        permissionKey = jSon.optString("permissionKey", "");
        permissionName = jSon.optString("permissionName", "");
        dimensionKey = jSon.optString("dimensionKey", "");
        userId = jSon.optString("userId", "");
        templateId = jSon.optString("templateId", "");
        tenantId = jSon.optString("tenantId", "");
        recStatus = jSon.optString("recStatus", "");
        createdBy = jSon.optString("createdBy", "");
        updatedBy = jSon.optString("updatedBy", "");
        createdDate = jSon.optLong("createdDate");
        updatedDate = jSon.optLong("updatedDate");
    }

    public String getPermissionKey() {
        return permissionKey;
    }

    public void setPermissionKey(String permissionKey) {
        this.permissionKey = permissionKey;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getDimensionKey() {
        return dimensionKey;
    }

    public void setDimensionKey(String dimensionKey) {
        this.dimensionKey = dimensionKey;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getRecStatus() {
        return recStatus;
    }

    public void setRecStatus(String recStatus) {
        this.recStatus = recStatus;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public long getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(long updatedDate) {
        this.updatedDate = updatedDate;
    }
}