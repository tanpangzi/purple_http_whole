package com.zimi.zimixing.biz;


import com.zimi.zimixing.BaseApplication;
import com.zimi.zimixing.bean.AccountTaskBean;
import com.zimi.zimixing.bean.BaseBean;
import com.zimi.zimixing.bean.CarInfoBean;
import com.zimi.zimixing.bean.CarInfoRetBean;
import com.zimi.zimixing.bean.CarMortgageBean;
import com.zimi.zimixing.bean.ContractNoBean;
import com.zimi.zimixing.bean.MessageBean;
import com.zimi.zimixing.bean.ReleaseBean;
import com.zimi.zimixing.bean.RequestNoBean;
import com.zimi.zimixing.bean.ResponseBean;
import com.zimi.zimixing.bean.UpdateBean;
import com.zimi.zimixing.bean.UserInfoBean;
import com.zimi.zimixing.bean.UserInfoLoginBean;
import com.zimi.zimixing.configs.ConfigServer;
import com.zimi.zimixing.configs.Constant;
import com.zimi.zimixing.interf.OnDownLoadCallBack;
import com.zimi.zimixing.utils.SystemUtil;
import com.zimi.zimixing.utils.http.HttpOkUtil;
import com.zimi.zimixing.utils.http.HttpRequestCallBack;

import java.util.HashMap;

/**
 * 用户信息相关 网络业务
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class UserBiz {

    //        ResponseBean responseBean = BaseBiz.sendPost(params);
    //        if (BaseBiz.checkSuccess(responseBean)) {
    //            // BaseBean.setResponseObjectList(responseBean, AdvertisementBean.class);
    //            // BaseBean.setResponseObjectList(responseBean, AdvertisementBean.class, "list");
    //            // BaseBean.setResponseObject(responseBean, BookingBean.class);
    //            // 数据缓存
    //            // PreferencesUtil.putObject("method",responseBean);
    //        }

    /**
     * 登录
     * <p>
     * <br> Version: 6.0.0
     * <br> CreateAuthor: 叶青
     * <br> CreateTime: 2018/1/4 16:43
     * <br> UpdateAuthor: 叶青
     * <br> UpdateTime: 2018/1/4 16:43
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param account
     *         账号
     * @param passWord
     *         密码
     *
     * @return ResponseBean
     */
    public static ResponseBean login(String account, String passWord) {

        HashMap<String, String> params = BaseBiz.getPostHeadMap();

        params.put("username", account);
        params.put("password", passWord);
        params = BaseBiz.paramsEncrypt(params);
        params.put(ConfigServer.SERVER_METHOD_KEY, ConfigServer.METHOD_LOGIN);

        // 自定义 http client 网络请求
        ResponseBean responseBean = BaseOkBiz.sendPost(params);
        // okHttp同步方式 网络请求
        // ResponseBean responseBean = BaseOkBiz.sendPost(params);
        if (BaseBiz.checkSuccess(responseBean)) {
            //responseBean.getObject() 为 json字符串 修改解析方式 可以在从此处修改
            //BaseBean.setResponseObject(responseBean, UserInfoBean.class);
            BaseBean.setResponseObject(responseBean, UserInfoLoginBean.class);
        }
        return responseBean;
    }

    /**
     * 登录
     * <p>
     * <br> Version: 6.0.0
     * <br> CreateAuthor: 叶青
     * <br> CreateTime: 2018/1/4 16:43
     * <br> UpdateAuthor: 叶青
     * <br> UpdateTime: 2018/1/4 16:43
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param account
     *         账号
     * @param passWord
     *         密码
     * @param callBack
     *         HttpRequestCallBack
     */
    public static void login1(String account, String passWord, final HttpRequestCallBack callBack) {
        HashMap<String, String> params = BaseBiz.getPostHeadMap();

        params.put("username", account);
        params.put("password", passWord);
        params = BaseBiz.paramsEncrypt(params);
        params.put(ConfigServer.SERVER_METHOD_KEY, ConfigServer.METHOD_LOGIN);

        // ConfigServer.SERVER_API_URL + ConfigServer.METHOD_LOGIN 可以在HttpOkUtil抽出
        HttpOkUtil.getInstance().sendPost(ConfigServer.SERVER_API_URL + ConfigServer.METHOD_LOGIN, params, new HttpRequestCallBack() {

            @Override
            public void onSuccess(ResponseBean responseBean) {
                //responseBean.getObject() 为 json字符串 修改解析方式 可以在从此处修改
                BaseBean.setResponseObject(responseBean, UserInfoBean.class);
                callBack.onSuccess(responseBean);
            }

            @Override
            public void onFail(ResponseBean result) {
                callBack.onFail(result);
            }
        });
    }

    /**
     * 版本更新
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
    public static ResponseBean checkUpdate() {

        HashMap<String, String> params = BaseBiz.getPostHeadMap();

        params.put("token", BaseApplication.getInstance().getToken());
        params.put("verCode", SystemUtil.getCurrentVersionName());
        params.put("type", "1");
        params.put(ConfigServer.SERVER_METHOD_KEY, ConfigServer.METHOD_VERSION_CHECK);

        // 自定义 http client 网络请求
        ResponseBean responseBean = BaseBiz.sendPost(params);
        // okHttp同步方式 网络请求
        // ResponseBean responseBean = BaseOkBiz.sendPost(params);
        if (BaseBiz.checkSuccess(responseBean)) {
            //responseBean.getObject() 为 json字符串 修改解析方式 可以在从此处修改
            BaseBean.setResponseObject(responseBean, UpdateBean.class);
        }
        return responseBean;
    }

    /**
     * 版本更新
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
    public static ResponseBean downLoadApk(String url, String destFileDir, OnDownLoadCallBack downLoadCallable) {
        return BaseBiz.downLoadFile(url, destFileDir, downLoadCallable);
    }

    /**
     * 退出登录
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
    public static ResponseBean loginOut() {
        HashMap<String, String> params = BaseBiz.getPostHeadMap();
        params.put("token", BaseApplication.getInstance().getToken());
        params.put(ConfigServer.SERVER_METHOD_KEY, ConfigServer.METHOD_LOGOUT);

        // 自定义 http client 网络请求
        return BaseOkBiz.sendPost(params);
    }

    /**
     * 修改密码
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
    public static ResponseBean updatePassword(String oldPassword, String newPassword) {
        HashMap<String, String> params = BaseBiz.getPostHeadMap();
        params.put("token", BaseApplication.getInstance().getToken());
        params.put("oldPassword", oldPassword);
        params.put("newPassword", newPassword);

        params = BaseBiz.paramsEncrypt(params);
        params.put(ConfigServer.SERVER_METHOD_KEY, ConfigServer.METHOD_UPDATE_PASSWORD);

        // 自定义 http client 网络请求
        return BaseBiz.sendPost(params);
    }

    /**
     * 获取任务列表 车辆评估 Gps安装 车辆抵押 用queryType和taskDefKey区分
     * @param queryType
     * @param taskDefKey
     * @param pageIndex
     * @return
     */
    public static ResponseBean queryTaskInfo(int queryType, int taskDefKey, int pageIndex){
        HashMap<String, String> params = BaseBiz.getPostHeadMap();
        params.put("token", BaseApplication.getInstance().getToken());

        if (queryType == Constant.WAIT_DO){ //待办
            params.put("queryType", Constant.agencyTaskTab);
        }else if (queryType == Constant.ALREADY_DONE){
            params.put("queryType", Constant.processedTaskTab);//已办
        }

        if (taskDefKey == Constant.CAR_ASSESS){ // 评估任务
            params.put("taskDefKey", Constant.usertask_assess);
        }else if (taskDefKey == Constant.GPS_INSTALL){ // gps安装
            params.put("taskDefKey", Constant.usertask_gps);
        }else if (taskDefKey == Constant.CAR_MORTGAGE){ // 车辆抵押
            params.put("taskDefKey", Constant.usertask_mortgage);
        }

        params.put("pageIndex", String.valueOf(pageIndex));
        params.put(ConfigServer.SERVER_METHOD_KEY, ConfigServer.QUERYTASKLISTDATA);
        // 自定义 http client 网络请求
        ResponseBean responseBean = BaseOkBiz.sendPost(params);
        // okHttp同步方式 网络请求
        if (BaseBiz.checkSuccess(responseBean)){
            responseBean.setResponseObject(responseBean, AccountTaskBean.class);
        }

        return responseBean;
    }


    /**
     * 点击确定签收
     * CreateAuthor: tanjun
     * @param taskId
     * @return
     */
    public static ResponseBean setCheckResult(String taskId){
        HashMap<String, String> params = BaseBiz.getPostHeadMap();
        params.put("token", BaseApplication.getInstance().getToken());
        params.put("taskId", taskId);
        params.put(ConfigServer.SERVER_METHOD_KEY, ConfigServer.WORKFLOWCLAIM);
        ResponseBean responseBean = BaseBiz.sendPost(params);
        return responseBean;
    }

    /**
     * 合同查询
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
    public static ResponseBean queryContractNo(String carNum, String name) {
        HashMap<String, String> params = BaseBiz.getPostHeadMap();
        params.put("token", BaseApplication.getInstance().getToken());
        params.put("plateNo", carNum);
        params.put("custName", name);

        params.put(ConfigServer.SERVER_METHOD_KEY, ConfigServer.METHOD_QUERYCONTRACTNO);

        //        // 自定义 http client 网络请求
        //        ResponseBean responseBean = BaseBiz.sendPost(params);
        // okHttp同步方式 网络请求
        ResponseBean responseBean = BaseOkBiz.sendPost(params);
        if (BaseBiz.checkSuccess(responseBean)) {
            //responseBean.getObject() 为 json字符串 修改解析方式 可以在从此处修改
            BaseBean.setResponseObject(responseBean, ContractNoBean.class);
        }
        return responseBean;
    }
    /**
     * 合同录入
     * <p>
     * <br> Version: 6.0.0
     * <br> CreateAuthor: 叶青
     * <br> CreateTime: 2018/1/4 16:43
     * <br> UpdateAuthor: 叶青
     * <br> UpdateTime: 2018/1/4 16:43
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     * @param custName       客户名称
     * @param palteNo        车牌号
     * @param serialNumber   流水号
     * @param contractNo     合同编号
     * @param suboCmpanyCode 车管所code
     * @return ResponseBean
     */
    public static ResponseBean SynCustSerialNumber(String custName, String palteNo, String contractNo, String serialNumber, String suboCmpanyCode) {
        HashMap<String, String> params = BaseBiz.getPostHeadMap();
        params.put("token", BaseApplication.getInstance().getToken());
        params.put("custName", custName);
        params.put("palteNo", palteNo);
        params.put("contractNo", contractNo);
        params.put("serialNumber", serialNumber);
        params.put("suboCmpanyCode", suboCmpanyCode);
        params.put(ConfigServer.SERVER_METHOD_KEY, ConfigServer.METHOD_SYNCUSTSERIALNUMBER);

        //        // 自定义 http client 网络请求
        //        ResponseBean responseBean = BaseBiz.sendPost(params);
        // okHttp同步方式 网络请求
        ResponseBean responseBean = BaseOkBiz.sendPost(params);
        return responseBean;
    }
    /**
     * 我的数据
     * <p>
     * <br> Version: 6.0.0
     * <br> CreateAuthor: 叶青
     * <br> CreateTime: 2018/1/4 16:43
     * <br> UpdateAuthor: 叶青
     * <br> UpdateTime: 2018/1/4 16:43
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     */
    /*public static ResponseBean mainInfo() {
        HashMap<String, String> params = BaseBiz.getPostHeadMap();
        params.put("token", BaseApplication.getInstance().getToken());
        params.put(ConfigServer.SERVER_METHOD_KEY, ConfigServer.METHOD_MAININFO);

        //        // 自定义 http client 网络请求
        //        ResponseBean responseBean = BaseBiz.sendPost(params);
        // okHttp同步方式 网络请求
        ResponseBean responseBean = BaseOkBiz.sendPost(params);
        return responseBean;
    }*/

    /**
     * 车辆评估 第一页 获取车辆信息
     * @param businessId
     * @return
     */
    public static ResponseBean queryEvalCarInfo(String businessId){
        HashMap<String, String> params = BaseBiz.getPostHeadMap();
        params.put("token", BaseApplication.getInstance().getToken());
        params.put("businessId", businessId);
        params.put(ConfigServer.SERVER_METHOD_KEY, ConfigServer.QUERYEVALCARINFO);
        ResponseBean responseBean = BaseBiz.sendPost(params);
        // okHttp同步方式 网络请求
        if (BaseBiz.checkSuccess(responseBean)){
            responseBean.setResponseObject(responseBean, CarInfoBean.EvalCarInfoBean.class);
        }
        return responseBean;
    }

    /**
     * 获取RequestNo 防重复提交都可调用
     * @return
     */
    public static ResponseBean getRequestNo(){
        HashMap<String, String> params = BaseBiz.getPostHeadMap();
        params.put("token", BaseApplication.getInstance().getToken());
        params.put(ConfigServer.SERVER_METHOD_KEY, ConfigServer.GENERATEREQUESTNO);
        ResponseBean responseBean = BaseBiz.sendPost(params);
        // okHttp同步方式 网络请求
        if (BaseBiz.checkSuccess(responseBean)){
            responseBean.setResponseObject(responseBean, RequestNoBean.class);
        }
        return responseBean;
    }

    /**
     * 提交车辆评估信息 车辆评估第一页
     * @return
     */
    public static ResponseBean updateEvalCarInfo(String requestNo, String businessId,String plateNo, String registerDate, String travelDistance, String carRecognizationNo, String engineNo){
        HashMap<String, String> params = BaseBiz.getPostHeadMap();
        params.put("token", BaseApplication.getInstance().getToken());
        params.put("requestNo", requestNo);
        params.put("businessId", businessId);
        params.put("plateNo", plateNo);
        params.put("registerDate", registerDate);
        params.put("travelDistance", travelDistance);
        params.put("carRecognizationNo", carRecognizationNo);
        params.put("engineNo", engineNo);
        params.put(ConfigServer.SERVER_METHOD_KEY, ConfigServer.UPDATEEVALCARINFO);
        ResponseBean responseBean = BaseBiz.sendPost(params);
        // okHttp同步方式 网络请求
        if (BaseBiz.checkSuccess(responseBean)){
            responseBean.setResponseObject(responseBean, CarInfoRetBean.class);
        }
        return responseBean;
    }

    /**
     * 车辆评估 第二个界面 提交车辆信息
     * @param requestNo
     * @param businessId
     * @param processInstanceId
     * @param taskId
     * @param carBrand
     * @param surfaceDesc
     * @param assessmentPrice
     * @param isAccidentApplyCar
     * @return
     */
    public static ResponseBean saveCarEvaluation(String requestNo, String businessId, String processInstanceId,
                                                 String taskId, String carBrand,
                                                 String surfaceDesc, String assessmentPrice, String isAccidentApplyCar){
        HashMap<String, String> params = BaseBiz.getPostHeadMap();
        params.put("token", BaseApplication.getInstance().getToken());
        params.put(ConfigServer.SERVER_METHOD_KEY, ConfigServer.SAVECAREVALUATION);
        params.put("requestNo",requestNo);
        params.put("businessId",businessId);
        params.put("taskId",taskId);
        params.put("confirmBrand",carBrand);
        params.put("surfaceDesc",surfaceDesc);
        params.put("assessmentPrice",assessmentPrice);
        params.put("isAccidentApplyCar",isAccidentApplyCar);
        params.put("processInstanceId",processInstanceId);

        // okHttp同步方式 网络请求
        ResponseBean responseBean = BaseBiz.sendPost(params);
        return responseBean;
    }

    /**
     * 车辆抵押 第一个界面 查询车辆信息
     * @param businessId
     * @return
     */
    public static ResponseBean queryMortgageInfo(String businessId){
        HashMap<String, String> params = BaseBiz.getPostHeadMap();
        params.put("token", BaseApplication.getInstance().getToken());
        params.put(ConfigServer.SERVER_METHOD_KEY, ConfigServer.QUERYMORTGAGEINFO);
        params.put("businessId", businessId);
        ResponseBean responseBean = BaseBiz.sendPost(params);
        if (BaseBiz.checkSuccess(responseBean)){
            responseBean.setResponseObject(responseBean, CarMortgageBean.class);
        }
        return responseBean;
    }

    /**
     *  车辆抵押上传 第二界面
     * @param requestNo
     * @param businessId
     * @param custId
     * @param taskId
     * @param processInstanceId
     * @param takeOver
     * @param carNum
     * @return
     */
    public static ResponseBean updateLoanAudiByBussinessId(String requestNo, String businessId, String custId,
                                                           String taskId,String processInstanceId,
                                                            String takeOver, String carNum){
        HashMap<String, String> params = BaseBiz.getPostHeadMap();
        params.put("token", BaseApplication.getInstance().getToken());
        params.put(ConfigServer.SERVER_METHOD_KEY, ConfigServer.UPDATELOANAUDIBYBUSSINESSID);
        params.put("requestNo",requestNo);
        params.put("businessId",businessId);
        params.put("custId",custId);
        params.put("taskId",taskId);
        params.put("processInstanceId",processInstanceId);
        params.put("isTransOwnership",takeOver); //是否过户
        params.put("transOwnershipPlateNo",carNum);
        // okHttp同步方式 网络请求
        ResponseBean responseBean = BaseBiz.sendPost(params);
        return responseBean;

    }

    /**
     * 查询车辆解押列表
     * @param pageIndex 页码 这个页码是从1开始
     * @return
     */
    public static ResponseBean queryRescissionMortgageList(int pageIndex){
        HashMap<String, String> params = BaseBiz.getPostHeadMap();
        params.put("token", BaseApplication.getInstance().getToken());
        params.put(ConfigServer.SERVER_METHOD_KEY, ConfigServer.QUERYRESCISSIONMORTGAGELIST);
        params.put("pageIndex", String.valueOf(pageIndex));
        ResponseBean responseBean = BaseBiz.sendPost(params);
        if (BaseBiz.checkSuccess(responseBean)){
            responseBean.setResponseObject(responseBean, ReleaseBean.class);
        }
        return  responseBean;
    }

    /**
     * 提交解押
     * @param requestNo 防重复码
     * @param businessId businessId
     * @param explain 解押情况说明
     * @return
     */
    public static ResponseBean rescissionMortgageApply(String requestNo,
                                                      String businessId, String explain){
        HashMap<String, String> params = BaseBiz.getPostHeadMap();
        params.put("token", BaseApplication.getInstance().getToken());
        params.put(ConfigServer.SERVER_METHOD_KEY, ConfigServer.RESCISSIONMORTGAGEAPPL);
        params.put("requestNo",requestNo);
        params.put("businessId",businessId);
        params.put("comment",requestNo);
        ResponseBean responseBean = BaseBiz.sendPost(params);
        return responseBean;
    }

}
