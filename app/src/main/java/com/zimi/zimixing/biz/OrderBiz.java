package com.zimi.zimixing.biz;


import com.zimi.zimixing.BaseApplication;
import com.zimi.zimixing.bean.BaseBean;
import com.zimi.zimixing.bean.ProvinceBean;
import com.zimi.zimixing.bean.ResponseBean;
import com.zimi.zimixing.bean.order.OrderBaseBean;
import com.zimi.zimixing.bean.order.OrderDetailBean;
import com.zimi.zimixing.bean.order.OrderListBean;
import com.zimi.zimixing.bean.order.OrderProductBigBean;
import com.zimi.zimixing.configs.ConfigServer;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 录单相关 网络业务
 * <p>
 * <br> Author: 叶青
 * <br> Version: 6.0.0
 * <br> Date: 2018/1/10
 * <br> Copyright: Copyright © 2017 xTeam Technology. All rights reserved.
 */
public class OrderBiz {


    //        ResponseBean responseBean = BaseBiz.sendPost(params);
    //        if (BaseBiz.checkSuccess(responseBean)) {
    //            // BaseBean.setResponseObjectList(responseBean, AdvertisementBean.class);
    //            // BaseBean.setResponseObjectList(responseBean, AdvertisementBean.class, "list");
    //            // BaseBean.setResponseObject(responseBean, BookingBean.class);
    //            // 数据缓存
    //            // PreferencesUtil.putObject("method",responseBean);
    //        }

    /**
     * 录单首页
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
    public static ResponseBean getSelectData() {
        HashMap<String, String> params = BaseBiz.getPostHeadMap();

        //        params.put("username", account);
        //        params.put("password", passWord);
        params.put(ConfigServer.SERVER_METHOD_KEY, ConfigServer.METHOD_GETSELECTDATA);

        //        // 自定义 http client 网络请求
        //        ResponseBean responseBean = BaseBiz.sendPost(params);
        //        okHttp同步方式 网络请求
        ResponseBean responseBean = BaseOkBiz.sendPost(params);
        if (BaseBiz.checkSuccess(responseBean)) {
            //responseBean.getObject() 为 json字符串 修改解析方式 可以在从此处修改
            BaseBean.setResponseObject(responseBean, OrderBaseBean.class);
        }
        return responseBean;
    }

    /**
     * 城市列表
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
    public static ResponseBean getAreaList() {

        HashMap<String, String> params = BaseBiz.getPostHeadMap();
        //        params.put("username", account);
        //        params.put("password", passWord);
        params.put(ConfigServer.SERVER_METHOD_KEY, ConfigServer.METHOD_GETAREALIST);

        // 自定义 http client 网络请求
        ResponseBean responseBean = BaseBiz.sendPost(params);
        // okHttp同步方式 网络请求
        // ResponseBean responseBean = BaseOkBiz.sendPost(params);
        if (BaseBiz.checkSuccess(responseBean)) {
            //responseBean.getObject() 为 json字符串 修改解析方式 可以在从此处修改
            JSONObject json = new JSONObject();
            try {
                json.put("dataList", responseBean.getObject());
                responseBean.setObject(json.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            BaseBean.setResponseObjectList(responseBean, ProvinceBean.class, "dataList");
        }
        return responseBean;
    }

    /**
     * 金真估的 车型数据 和 城市数据
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
    public static ResponseBean getJingZhenGuData(String operate, Map<String, String> hashMap) {

        HashMap<String, String> params = BaseBiz.getPostHeadMap();
        params.put("Operate", operate);
        params.put("token", BaseApplication.getInstance().getToken());
        params.putAll(hashMap);
        params.put(ConfigServer.SERVER_METHOD_KEY, ConfigServer.METHOD_GETJINGZHENGUDATA);

        // 自定义 http client 网络请求
        //        ResponseBean responseBean = BaseBiz.sendPost(params);
        // okHttp同步方式 网络请求
        ResponseBean responseBean = BaseOkBiz.sendPost(params);
        //        if (BaseBiz.checkSuccess(responseBean)) {
        //            ArrayList<JzgBean> arrayList = new ArrayList<>();
        //            String result = (String) responseBean.getObject();
        //            try {
        //                JSONArray jsonArray = new JSONArray(result);
        //                switch (operate) {
        //                    case Constant.OPERATE_MAKE:// 获取品牌
        //                        break;
        //                    case Constant.OPERATE_MODEL:// 根据品牌ID获取车系信息
        //                        break;
        //                    case Constant.OPERATE_STYLE:// 根据车系Id获取车型信息
        //                        break;
        //                    case Constant.OPERATE_PROVINCE:// 获取省份信息
        //                    case Constant.OPERATE_CITY:// 根据省份ID获取城市信息
        //                        for (int i = 0; i < jsonArray.length(); i++) {
        //                            String content = jsonArray.getString(i);
        //                            String newStr[] = content.split(":");
        //                            JzgBean jzgBean = new JzgBean();
        //                            jzgBean.setName(newStr[0]);
        //                            jzgBean.setId(newStr[1]);
        //                            arrayList.add(jzgBean);
        //                        }
        //                        break;
        //                    default:// 默认
        //                        break;
        //                }
        //            } catch (JSONException e) {
        //                e.printStackTrace();
        //            }
        //            responseBean.setObject(arrayList);
        //            //            //responseBean.getObject() 为 json字符串 修改解析方式 可以在从此处修改
        //            //            JSONObject json = new JSONObject();
        //            //            try {
        //            //                json.put("dataList", responseBean.getObject());
        //            //                responseBean.setObject(json.toString());
        //            //            } catch (Exception e) {
        //            //                e.printStackTrace();
        //            //            }
        //            //            BaseBean.setResponseObjectList(responseBean, ProvinceBean.class, "dataList");
        //        }
        return responseBean;
    }

    /**
     * 金真估的提交评估
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
    public static ResponseBean getJingZhenGuObjectData(Map<String, String> map) {

        HashMap<String, String> params = BaseBiz.getPostHeadMap();
        params.put("token", BaseApplication.getInstance().getToken());
        params.putAll(map);
        params.put(ConfigServer.SERVER_METHOD_KEY, ConfigServer.METHOD_GETJINGZHENGUOBJECTDATAA);

        // 自定义 http client 网络请求
        // okHttp同步方式 网络请求
        // ResponseBean responseBean = BaseOkBiz.sendPost(params);
        return BaseOkBiz.sendPost(params);
    }

    /**
     * 我的订单
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
    public static ResponseBean queryLoanList(String pageIndex, String queryParameter) {

        HashMap<String, String> params = BaseBiz.getPostHeadMap();
        params.put("token", BaseApplication.getInstance().getToken());
        params.put("pageIndex", pageIndex);// 页码
        params.put("queryParameter", queryParameter);// 搜索 关键字
        params.put(ConfigServer.SERVER_METHOD_KEY, ConfigServer.METHOD_QUERYLOANLIST);

        ResponseBean responseBean = BaseOkBiz.sendPost(params);
        if (BaseBiz.checkSuccess(responseBean)) {
            BaseBean.setResponseObjectList(responseBean, OrderListBean.class, "loanList");
        }
        return responseBean;
    }

    /**
     * 订单详情
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
    public static ResponseBean businessdetail(String businessId, String custId) {

        HashMap<String, String> params = BaseBiz.getPostHeadMap();
        params.put("token", BaseApplication.getInstance().getToken());
        params.put("businessId", businessId);
        params.put("custId", custId);
        params.put(ConfigServer.SERVER_METHOD_KEY, ConfigServer.METHOD_BUSINESSDETAIL);

        ResponseBean responseBean = BaseOkBiz.sendPost(params);
        if (BaseBiz.checkSuccess(responseBean)) {
            BaseBean.setResponseObject(responseBean, OrderDetailBean.class);
        }
        return responseBean;
    }

    /**
     * 录单提交
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
    public static ResponseBean recordLoan(Map<String, String> map, String requestNo) {

        HashMap<String, String> params = BaseBiz.getPostHeadMap();
        params.put("token", BaseApplication.getInstance().getToken());
        params.put("requestNo", requestNo);
        params.putAll(map);
        params.put(ConfigServer.SERVER_METHOD_KEY, ConfigServer.METHOD_RECORDLOAN);

        return BaseOkBiz.sendPost(params);
    }

    /**
     * 录单 保存客户信息提交
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
    public static ResponseBean saveCustInfo(Map<String, String> map, String requestNo) {

        HashMap<String, String> params = BaseBiz.getPostHeadMap();
        params.put("token", BaseApplication.getInstance().getToken());
        params.put("requestNo", requestNo);
        params.putAll(map);
        params.put(ConfigServer.SERVER_METHOD_KEY, ConfigServer.METHOD_SAVECUSTINFO);

        return BaseOkBiz.sendPost(params);
    }

    /**
     * 获取RequestNo
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
    public static ResponseBean getRequestNo() {

        HashMap<String, String> params = BaseBiz.getPostHeadMap();
        params.put("token", BaseApplication.getInstance().getToken());
        params.put(ConfigServer.SERVER_METHOD_KEY, ConfigServer.METHOD_GENERATEREQUESTNO);

        return BaseOkBiz.sendPost(params);
    }

    /**
     * 查询产品大类信息
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
    public static ResponseBean queryApvProductInf() {

        HashMap<String, String> params = BaseBiz.getPostHeadMap();
        params.put("token", BaseApplication.getInstance().getToken());
        params.put(ConfigServer.SERVER_METHOD_KEY, ConfigServer.METHOD_QUERYAPVPRODUCTINF);

        ResponseBean responseBean = BaseOkBiz.sendPost(params);
        if (BaseBiz.checkSuccess(responseBean)) {
            BaseBean.setResponseObject(responseBean, OrderProductBigBean.class);
        }
        return responseBean;
    }

    /**
     * 录单提交 保存补充信息
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
    public static ResponseBean saveSupplyInformation(Map<String, String> map, String requestNo) {

        HashMap<String, String> params = BaseBiz.getPostHeadMap();
        params.put("token", BaseApplication.getInstance().getToken());
        params.put("requestNo", requestNo);
        params.putAll(map);
        params.put(ConfigServer.SERVER_METHOD_KEY, ConfigServer.METHOD_SAVESUPPLYINFORMATION);

        return BaseOkBiz.sendPost(params);
    }

    /**
     * 录单提交 图片上传
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
    public static ResponseBean uploadImg(String businessId, String custId, Map<String, File> mapFile) {

        HashMap<String, String> params = BaseBiz.getPostHeadMap();
        params.put("token", BaseApplication.getInstance().getToken());
        params.put("custId", custId);
        params.put("businessId", businessId);
        //        return BaseBiz.upLoadFile(ConfigServer.SERVER_API_URL + ConfigServer.METHOD_IMGUPLOAD, params, mapFile);
        return BaseOkBiz.upLoadFile(ConfigServer.SERVER_API_URL.replace("https","http") + ConfigServer.METHOD_IMGUPLOAD, params, mapFile);
    }

    /**
     * 录单提交 删除图片
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
    public static ResponseBean deleteImg(String custId, String fileName) {

        HashMap<String, String> params = BaseBiz.getPostHeadMap();
        params.put("token", BaseApplication.getInstance().getToken());
        params.put("custId", custId);
        params.put("name", fileName);
        params.put(ConfigServer.SERVER_METHOD_KEY, ConfigServer.METHOD_NEWDELETEIMG);

        return BaseOkBiz.sendPost(params);
    }

    /**
     * 录单提交
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
    public static ResponseBean workflowStart(Map<String, String> map) {

        HashMap<String, String> params = BaseBiz.getPostHeadMap();
        params.put("token", BaseApplication.getInstance().getToken());
        params.putAll(map);
        params.put(ConfigServer.SERVER_METHOD_KEY, ConfigServer.METHOD_WORKFLOWSTART);

        return BaseOkBiz.sendPost(params);
    }


    /**
     * 录单提交 样例图获取
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
    public static ResponseBean readImageSample() {

        HashMap<String, String> params = BaseBiz.getPostHeadMap();
        params.put("token", BaseApplication.getInstance().getToken());
        params.put(ConfigServer.SERVER_METHOD_KEY, ConfigServer.METHOD_READIMAGESAMPLE);

        return BaseOkBiz.sendPost(params);
    }
}
