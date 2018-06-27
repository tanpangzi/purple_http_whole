package com.zimi.zimixing.bean;


import org.json.JSONException;
import org.json.JSONObject;

/**
 * 订单列表
 * <p>
 * <br> Author: 叶青
 * <br> Version: 6.0.0
 * <br> Date: 2018/1/11
 * <br> Copyright: Copyright © 2017 xTeam Technology. All rights reserved.
 */
public class GpsMonitorHomeListBean extends BaseBean {

    //*orgId : 2 门店id
    private String orgId = "";
    //* invalid : 0 当前门店下失效设备总数
    private String invalid = "";
    //* sum : 3  当前门店下设备总数
    private String sum = "";
    //* orgName : 上海 门店名称
    private String orgName = "";
    //* offline : 0  当前门店下离线设备总数
    private String offline = "";
    //* online : 3 当前门店下在线设备总数
    private String online = "";

    @Override
    protected void init(JSONObject jSon) throws JSONException {
        orgId = jSon.optString("orgId", "");
        invalid = jSon.optString("invalid", "0");
        sum = jSon.optString("sum", "0");
        orgName = jSon.optString("orgName", "");
        offline = jSon.optString("offline", "0");
        online = jSon.optString("online", "0");
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
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

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
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
}
