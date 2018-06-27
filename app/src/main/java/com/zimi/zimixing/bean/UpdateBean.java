package com.zimi.zimixing.bean;

import com.zimi.zimixing.configs.Constant;
import com.zimi.zimixing.utils.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 版本更新属性类
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class UpdateBean extends BaseBean {

    /** 新版版本号 */
    private String versionCode = "";
    /** 服务器地址 */
    private String serverUrl = "";
    /** 下载地址 */
    private String url = "";
    /** 文件大小 byte */
    private String fileSize = "-1";
    /** 版本说明 */
    private String versionInfo = "";
    /** 录入时间 */
    private String createTime = "";
    /** 强制更新 (0为不强制，1为强制)' */
    private boolean isMustUpdate = false;

    //			"verName": "V1.8.1",//版本名称
    //			"verCode": "10801",//版本号 verCode=0 可忽略
    //			"force": "0",是否强制更新 0=否
    //			"url": "http://www.baidu.com/ ",//app下载地址
    //			"info": "AAAAA"//版本更新描述

    /**
     * verName : V1.0.0
     * verCode : 1000
     * force : 1
     * url : http://dyt.html?download=1
     * info : 版本介绍
     */

    @Override
    protected void init(JSONObject jSon) throws JSONException {
        setVersionCode(JsonUtil.optString(jSon, "verCode", ""));
        setMustUpdate(JsonUtil.optString(jSon, "force", "").equals(Constant.TRUE));
        setUrl(JsonUtil.optString(jSon, "url", ""));

        setVersionInfo(JsonUtil.optString(jSon, "info", ""));
        setCreateTime(JsonUtil.optString(jSon, "dateline", ""));
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVersionInfo() {
        return versionInfo;
    }

    public void setVersionInfo(String versionInfo) {
        this.versionInfo = versionInfo;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    //    public String getServerUrl() {
    //        return serverUrl;
    //    }
    //
    //    public void setServerUrl(String serverUrl) {
    //        this.serverUrl = serverUrl;
    //    }

    public String getFile_Size() {
        return fileSize;
    }

    public void setFileSize(String file_Size) {
        this.fileSize = file_Size;
    }

    public boolean isMustUpdate() {
        return isMustUpdate;
    }

    public void setMustUpdate(boolean mustUpdate) {
        isMustUpdate = mustUpdate;
    }
}