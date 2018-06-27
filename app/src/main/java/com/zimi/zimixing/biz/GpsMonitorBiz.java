package com.zimi.zimixing.biz;


import android.text.TextUtils;

import com.zimi.zimixing.BaseApplication;
import com.zimi.zimixing.bean.BaseBean;
import com.zimi.zimixing.bean.CompanyGroupListBean;
import com.zimi.zimixing.bean.GpsListBean;
import com.zimi.zimixing.bean.GpsMonitorHomeBean;
import com.zimi.zimixing.bean.GpsMonitorStoryBean;
import com.zimi.zimixing.bean.GpsMonitorStoryTypeBean;
import com.zimi.zimixing.bean.GpsRecordShowtBean;
import com.zimi.zimixing.bean.ResponseBean;
import com.zimi.zimixing.bean.UserListBean;
import com.zimi.zimixing.configs.ConfigServer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * gps监控相关 网络业务
 * <p>
 * <br> Author: 叶青
 * <br> Version: 6.0.0
 * <br> Date: 2018/1/10
 * <br> Copyright: Copyright © 2017 xTeam Technology. All rights reserved.
 */
public class GpsMonitorBiz {

    /**
     * GPS监控获取门店列表
     * <p>
     * <br> Version: 6.0.0
     * <br> CreateAuthor: 叶青
     * <br> CreateTime: 2018/1/4 16:43
     * <br> UpdateAuthor: 叶青
     * <br> UpdateTime: 2018/1/4 16:43
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @return ResponseBean
     */
    public static ResponseBean getStoreList() {

        HashMap<String, String> params = BaseBiz.getPostHeadMap();
        params.put("token", BaseApplication.getInstance().getToken());
        params.put("name", BaseApplication.getInstance().getUserInfoBean().getUserName());
        params.put(ConfigServer.SERVER_METHOD_KEY, ConfigServer.METHOD_GETSTORELIST);

        ResponseBean responseBean = BaseOkBiz.sendPost(params);
        if (BaseBiz.checkSuccess(responseBean)) {
            BaseBean.setResponseObject(responseBean, GpsMonitorHomeBean.class);
        }
        return responseBean;
    }

    /**
     * GPS监控获取门店下设备列表
     * <p>
     * <br> Version: 6.0.0
     * <br> CreateAuthor: 叶青
     * <br> CreateTime: 2018/1/4 16:43
     * <br> UpdateAuthor: 叶青
     * <br> UpdateTime: 2018/1/4 16:43
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @return ResponseBean
     */
    public static ResponseBean getEquipmentList(String pageIndex, String conditions) {

        HashMap<String, String> params = BaseBiz.getPostHeadMap();
        params.put("token", BaseApplication.getInstance().getToken());
        params.put("username", BaseApplication.getInstance().getUserInfoBean().getUserId());
        params.put("pageIndex", pageIndex);
        if (!TextUtils.isEmpty(conditions)) {
            params.put("conditions", conditions);
        }
        params.put(ConfigServer.SERVER_METHOD_KEY, ConfigServer.METHOD_GETEQUIPMENTLIST);

        ResponseBean responseBean = BaseOkBiz.sendPost(params);
        if (BaseBiz.checkSuccess(responseBean)) {
            BaseBean.setResponseObjectList(responseBean, GpsMonitorStoryBean.class, "returnList");
        }
        return responseBean;
    }

    /**
     * 超级管管理员GPS监控获取门店下设备列表
     * <p>
     * <br> Version: 6.0.0
     * <br> CreateAuthor: 叶青
     * <br> CreateTime: 2018/1/4 16:43
     * <br> UpdateAuthor: 叶青
     * <br> UpdateTime: 2018/1/4 16:43
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @return ResponseBean
     */
    public static ResponseBean getEquipmentListForSuperManager(String pageIndex, String conditions, String orgId) {

        HashMap<String, String> params = BaseBiz.getPostHeadMap();
        params.put("token", BaseApplication.getInstance().getToken());
        //        params.put("username", BaseApplication.getInstance().getUserInfoBean().getUserId());
        params.put("pageIndex", pageIndex);
        if (!TextUtils.isEmpty(conditions)) {
            params.put("conditions", conditions);
        }

        if (!TextUtils.isEmpty(orgId)) {
            params.put("orgId", orgId);
        } else {
            params.put("name", BaseApplication.getInstance().getUserInfoBean().getUserName());
        }
        params.put(ConfigServer.SERVER_METHOD_KEY, ConfigServer.METHOD_GETEQUIPMENTLIST_FORSUPERMANAGER);

        ResponseBean responseBean = BaseOkBiz.sendPost(params);
        if (BaseBiz.checkSuccess(responseBean)) {
            BaseBean.setResponseObjectList(responseBean, GpsMonitorStoryBean.class, "returnList");
        }
        return responseBean;
    }

    /**
     * 超级管管理员GPS监控获取 报警设备列表
     * <p>
     * <br> Version: 6.0.0
     * <br> CreateAuthor: 叶青
     * <br> CreateTime: 2018/1/4 16:43
     * <br> UpdateAuthor: 叶青
     * <br> UpdateTime: 2018/1/4 16:43
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @return ResponseBean
     */
    public static ResponseBean getAlarmList(String pageIndex, String conditions, String orgId, int warningType) {

        HashMap<String, String> params = BaseBiz.getPostHeadMap();
        params.put("token", BaseApplication.getInstance().getToken());
        params.put("accountName", BaseApplication.getInstance().getUserInfoBean().getUserName());
        params.put("pageIndex", pageIndex);
        params.put("orgId", orgId);
        if (!TextUtils.isEmpty(conditions)) {
            params.put("conditions", conditions);
        }
        //-1查所有  0("所有报警"); 1 ("位移报警");  2 ("断点报警"); 3 ("越界报警");
        if (warningType != -1) {//-1查所有
            params.put("warningType", String.valueOf(warningType));
        }
        params.put(ConfigServer.SERVER_METHOD_KEY, ConfigServer.METHOD_GETALARMLIST);

        ResponseBean responseBean = BaseOkBiz.sendPost(params);
        if (BaseBiz.checkSuccess(responseBean)) {
            BaseBean.setResponseObjectList(responseBean, GpsMonitorStoryBean.class, "returnList");
        }
        return responseBean;
    }

    /**
     * 超级管管理员GPS监控 筛选报警设备列表(获取报警分类和总数)
     * <p>
     * <br> Version: 6.0.0
     * <br> CreateAuthor: 叶青
     * <br> CreateTime: 2018/1/4 16:43
     * <br> UpdateAuthor: 叶青
     * <br> UpdateTime: 2018/1/4 16:43
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @return ResponseBean
     */
    public static ResponseBean getAlarmScreeningList(String orgId) {

        HashMap<String, String> params = BaseBiz.getPostHeadMap();
        params.put("token", BaseApplication.getInstance().getToken());
        params.put("accountName", BaseApplication.getInstance().getUserInfoBean().getUserName());
        params.put("orgId", orgId);
        params.put(ConfigServer.SERVER_METHOD_KEY, ConfigServer.METHOD_GETALARMSCREENINGLIST);

        ResponseBean responseBean = BaseOkBiz.sendPost(params);
        if (BaseBiz.checkSuccess(responseBean)) {
            // "data":{"returnList":[{"outages":2,"sum":2,"displacement":0,"crossing":0}]}}
            String resultStr = (String) responseBean.getObject();
            try {
                JSONObject jsonObject = new JSONObject(resultStr);
                JSONArray jsonArray = jsonObject.optJSONArray("returnList");
                if (jsonArray != null && jsonArray.length() > 0) {
                    responseBean.setObject(jsonArray.getJSONObject(0).toString());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            BaseBean.setResponseObject(responseBean, GpsMonitorStoryTypeBean.class);
        }
        return responseBean;
    }

    /**
     * 超级管管理员GPS监控获取 分组授权
     * <p>
     * <br> Version: 6.0.0
     * <br> CreateAuthor: 叶青
     * <br> CreateTime: 2018/1/4 16:43
     * <br> UpdateAuthor: 叶青
     * <br> UpdateTime: 2018/1/4 16:43
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @return ResponseBean
     */
    public static ResponseBean queryCompanyRelUser() {

        HashMap<String, String> params = BaseBiz.getPostHeadMap();
        params.put("token", BaseApplication.getInstance().getToken());
        params.put(ConfigServer.SERVER_METHOD_KEY, ConfigServer.METHOD_QUERYCOMPANYRELUSER);

        ResponseBean responseBean = BaseOkBiz.sendPost(params);
        if (BaseBiz.checkSuccess(responseBean)) {
            BaseBean.setResponseObjectList(responseBean, CompanyGroupListBean.class, "companyRelUserList");
        }
        return responseBean;
    }

    /**
     * 超级管管理员GPS监控获取 分组授权提交
     * <p>
     * <br> Version: 6.0.0
     * <br> CreateAuthor: 叶青
     * <br> CreateTime: 2018/1/4 16:43
     * <br> UpdateAuthor: 叶青
     * <br> UpdateTime: 2018/1/4 16:43
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @return ResponseBean
     */
    public static ResponseBean storeAuthorize(String userName, String time, String orgId) {

        HashMap<String, String> params = BaseBiz.getPostHeadMap();
        params.put("token", BaseApplication.getInstance().getToken());
        params.put("userName", userName);
        params.put("companyCode", orgId + "");
        params.put("authorizedEffectiveLength", time);
        params.put(ConfigServer.SERVER_METHOD_KEY, ConfigServer.METHOD_STOREAUTHORIZE);

        return BaseOkBiz.sendPost(params);
    }

    /**
     * <p>
     * <br> Version: 6.0.0
     * <br> CreateAuthor: 叶青
     * <br> CreateTime: 2018/1/4 16:43
     * <br> UpdateAuthor: 叶青
     * <br> UpdateTime: 2018/1/4 16:43
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @return ResponseBean
     */
    public static ResponseBean getLocationInfo(String imeiId) {

        HashMap<String, String> params = BaseBiz.getPostHeadMap();
        params.put("imeiId", imeiId);
        params.put("token", BaseApplication.getInstance().getToken());
        params.put(ConfigServer.SERVER_METHOD_KEY, ConfigServer.METHOD_GETLOCATIONINFO);
        ResponseBean responseBean = BaseOkBiz.sendPost(params);
        if (BaseBiz.checkSuccess(responseBean)) {
            BaseBean.setResponseObjectList(responseBean, GpsListBean.class, "returnList");
        }
        return responseBean;
    }

    /**
     * <p>
     * <br> Version: 6.0.0
     * <br> CreateAuthor: 叶青
     * <br> CreateTime: 2018/1/4 16:43
     * <br> UpdateAuthor: 叶青
     * <br> UpdateTime: 2018/1/4 16:43
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @return ResponseBean
     */
    public static ResponseBean getTrackPlayback(String imeiId, String first, final String startTime, String endTime, int type, int day) {
        Map<String, String> map = new HashMap<>();
        map.put("imeiId", imeiId);
        map.put("type", type + "");
        map.put("first", first);
        if (!TextUtils.isEmpty(startTime)) {
            map.put("startTime", startTime);
            map.put("endTime", endTime);
        }
        map.put("day", day + "");
        HashMap<String, String> params = BaseBiz.getPostHeadMap();
        //        params.put("token", BaseApplication.getInstance().getToken());
        params.putAll(map);
        params.put(ConfigServer.SERVER_METHOD_KEY, ConfigServer.METHOD_GETTRACKPLAYBACK);
        ResponseBean responseBean = BaseOkBiz.sendPost(params);
        if (BaseBiz.checkSuccess(responseBean)) {
            String content = (String) responseBean.getObject();
            if (content.equals("{}")) {
                responseBean.setObject(new ArrayList<GpsRecordShowtBean>());
                return responseBean;
            }
            BaseBean.setResponseObjectList(responseBean, GpsRecordShowtBean.class, "returnList");
        }
        return responseBean;
    }

    /**
     * 查询所有用户
     * <p>
     * <br> Version: 6.0.0
     * <br> CreateAuthor: 叶青
     * <br> CreateTime: 2018/1/4 16:43
     * <br> UpdateAuthor: 叶青
     * <br> UpdateTime: 2018/1/4 16:43
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @return ResponseBean
     */
    public static ResponseBean queryAllUser() {

        HashMap<String, String> params = BaseBiz.getPostHeadMap();
        params.put("token", BaseApplication.getInstance().getToken());
        params.put(ConfigServer.SERVER_METHOD_KEY, ConfigServer.METHOD_QUERYALLUSER);
        ResponseBean responseBean = BaseOkBiz.sendPost(params);
        if (BaseBiz.checkSuccess(responseBean)) {
            BaseBean.setResponseObjectList(responseBean, UserListBean.class, "userList");
        }
        return responseBean;
    }

    /**
     * 授权用户 提交
     * <p>
     * <br> Version: 6.0.0
     * <br> CreateAuthor: 叶青
     * <br> CreateTime: 2018/1/4 16:43
     * <br> UpdateAuthor: 叶青
     * <br> UpdateTime: 2018/1/4 16:43
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @return ResponseBean
     */
    public static ResponseBean equipmentAuthorize(String userName, String equipmentId) {

        HashMap<String, String> params = BaseBiz.getPostHeadMap();
        params.put("token", BaseApplication.getInstance().getToken());
        params.put("userName", userName);
        params.put("equipmentId", equipmentId);
        params.put(ConfigServer.SERVER_METHOD_KEY, ConfigServer.METHOD_EQUIPMENTAUTHORIZE);

        return BaseOkBiz.sendPost(params);
    }

    /**
     * 更新时间
     * <p>
     * <br> Version: 6.0.0
     * <br> CreateAuthor: 叶青
     * <br> CreateTime: 2018/1/4 16:43
     * <br> UpdateAuthor: 叶青
     * <br> UpdateTime: 2018/1/4 16:43
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @return ResponseBean
     */
    public static ResponseBean updateUploadTime(String imeiId, String type) {

        HashMap<String, String> params = BaseBiz.getPostHeadMap();
        //        params.put("token", BaseApplication.getInstance().getToken());
        params.put("imeiId", imeiId);
        params.put("type", type);
        params.put(ConfigServer.SERVER_METHOD_KEY, ConfigServer.METHOD_UPDATEUPLOADTIME);

        return BaseOkBiz.sendPost(params);
    }
}
