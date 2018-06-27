package com.zimi.zimixing.biz;

import android.content.Context;
import android.text.TextUtils;

import com.zimi.zimixing.BaseApplication;
import com.zimi.zimixing.R;
import com.zimi.zimixing.bean.ResponseBean;
import com.zimi.zimixing.configs.ConfigServer;
import com.zimi.zimixing.executor.Cancel;
import com.zimi.zimixing.utils.LogUtil;
import com.zimi.zimixing.utils.NetWorkUtil;
import com.zimi.zimixing.utils.http.HttpOkUtil;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * 系统业务逻辑
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class BaseOkBiz {

    /**
     * 为map添加数据，如果value值为空则不传递次参数
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-11-8,下午2:10:30
     * <br> UpdateTime: 2016-11-8,下午2:10:30
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param map
     *         键值对对象
     * @param key
     *         键
     * @param value
     *         值
     */
    public static void mapPutValue(HashMap<String, String> map, String key, String value) {
        if (!TextUtils.isEmpty(value)) {
            map.put(key, value);
        }
    }

    /**
     * 发送get请求
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016年5月20日,下午4:57:16
     * <br> UpdateTime: 2016年5月20日,下午4:57:16
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param attribute
     *         参数
     *
     * @return 请求后返回的接口数据
     */
    public static ResponseBean sendPost(HashMap<String, String> attribute) {
        String url = ConfigServer.SERVER_API_URL;
        String method = attribute.get(ConfigServer.SERVER_METHOD_KEY);
        attribute.remove(ConfigServer.SERVER_METHOD_KEY);
        if (!TextUtils.isEmpty(method)) {
            url = url + method;
        }
        return sendPost(url, attribute);
    }

    /**
     * 发送post请求
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016年12月31日,上午5:01:35
     * <br> UpdateTime: 2016年12月31日,上午5:01:35
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param url
     *         接口地址
     * @param attribute
     *         接口参数
     *
     * @return 请求后返回的解析数据
     */
    public static ResponseBean sendPost(String url, Map<String, String> attribute) {
        ResponseBean responseBean = new ResponseBean();
        Context context = BaseApplication.getInstance().getApplicationContext();
        try {
            String result = HttpOkUtil.getInstance().sendPost(url, attribute);
            LogUtil.json(url, result);
            JSONObject jsonObject = new JSONObject(result);
            responseBean.setStatus(jsonObject.optString(ConfigServer.RESULT_JSON_STATUS, context.getString(R.string.exception_local_other_code)));
            responseBean.setInfo(jsonObject.optString(ConfigServer.RESULT_JSON_INFO, context.getString(R.string.exception_local_other_message)));
            responseBean.setObject(jsonObject.optString(ConfigServer.RESULT_JSON_DATA, ""));
        } catch (Exception e) {
            responseBean = getFailMsg(e);
        }
        return responseBean;
    }


    /**
     * 发送get请求
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016年5月20日,下午4:57:16
     * <br> UpdateTime: 2016年5月20日,下午4:57:16
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param attribute
     *         参数
     *
     * @return 请求后返回的接口数据
     */
    public static ResponseBean sendGet(Map<String, String> attribute) {
        return sendGet(ConfigServer.SERVER_API_URL, attribute);
    }

    /**
     * 发送get请求
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016年5月20日,下午4:57:16
     * <br> UpdateTime: 2016年5月20日,下午4:57:16
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param url
     *         服务器地址
     * @param attribute
     *         参数
     *
     * @return 请求后返回的接口数据
     */
    public static ResponseBean sendGet(String url, Map<String, String> attribute) {
        ResponseBean responseBean = new ResponseBean();
        Context context = BaseApplication.getInstance().getApplicationContext();
        try {
            // String result = new HttpUtil().sendGet(url, timeout, attribute);
            String result = HttpOkUtil.getInstance().sendGet(url, attribute);
            LogUtil.json(url, result);
            JSONObject jsonObject = new JSONObject(result);
            responseBean.setStatus(jsonObject.optString(ConfigServer.RESULT_JSON_STATUS, context.getString(R.string.exception_local_other_code)));
            responseBean.setInfo(jsonObject.optString(ConfigServer.RESULT_JSON_INFO, context.getString(R.string.exception_local_other_message)));
            responseBean.setObject(jsonObject.optString(ConfigServer.RESULT_JSON_DATA, ""));
        } catch (Exception e) {
            responseBean = getFailMsg(e);
        }
        return responseBean;
    }

    /**
     * 文件上传
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016年5月20日,下午4:57:16
     * <br> UpdateTime: 2016年5月20日,下午4:57:16
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param url
     *         服务器地址
     * @param params
     *         附带参数集合
     * @param files
     *         文件集合，支持多文件上传
     *
     * @return 请求后返回的接口数据
     */
    public static ResponseBean upLoadFile(String url, Map<String, String> params, Map<String, File> files) {
        ResponseBean responseBean = new ResponseBean();
        Context context = BaseApplication.getInstance().getApplicationContext();
        try {
            // String result = new HttpUtil().uploadFile(url, params, files);
            String result = HttpOkUtil.getInstance().uploadFile(url, params, files);
            LogUtil.json(url, result);
            JSONObject jsonObject = new JSONObject(result);
            responseBean.setStatus(jsonObject.optString(ConfigServer.RESULT_JSON_STATUS, context.getString(R.string.exception_local_other_code)));
            responseBean.setInfo(jsonObject.optString(ConfigServer.RESULT_JSON_INFO, context.getString(R.string.exception_local_other_message)));
            responseBean.setObject(jsonObject.optString(ConfigServer.RESULT_JSON_DATA, ""));
        } catch (Exception e) {
            responseBean = getFailMsg(e);
        }
        return responseBean;
    }

    /**
     * 文件下载
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016年5月20日,下午4:57:16
     * <br> UpdateTime: 2016年5月20日,下午4:57:16
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param url
     *         下载路径.
     * @param destFileDir
     *         本地文件存储的文件夹
     *
     * @return 请求后返回的接口数据
     */
    public static ResponseBean downLoadFile(String url, String destFileDir) {
        ResponseBean responseBean = new ResponseBean();
        Context context = BaseApplication.getInstance().getApplicationContext();
        try {
            String result = HttpOkUtil.getInstance().downLoadFile(url, destFileDir);
            LogUtil.i(result);
            if (result.equals("success")) {
                responseBean.setStatus(ConfigServer.RESPONSE_STATUS_SUCCESS);
                responseBean.setInfo(context.getString(R.string.service_update_hint_download_finish));
            } else {
                responseBean.setStatus(ConfigServer.RESPONSE_STATUS_FAIL);
                responseBean.setInfo(context.getString(R.string.service_update_hint_download_error));
            }
        } catch (Exception e) {
            responseBean = getFailMsg(e);
        }

        return responseBean;
    }

    /**
     * 网络处理返回数据
     * <p>
     * <br> Version: 4.0.0
     * <br> CreateAuthor: 叶青
     * <br> CreateTime: 2017/4/26 15:07
     * <br> UpdateAuthor: 叶青
     * <br> UpdateTime: 2017/4/26 15:07
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param e
     *         Throwable
     */
    public static ResponseBean getFailMsg(Throwable e) {
        e.printStackTrace();
        Context context = BaseApplication.getInstance().getApplicationContext();
        if (!NetWorkUtil.isNetworkAvailable()) {
            return new ResponseBean(context.getString(R.string.exception_net_work_io_code), context.getString(R.string.exception_net_work_io_message), "");
        } else if (e instanceof SocketTimeoutException || e instanceof TimeoutException || e instanceof ConnectException) {
            return new ResponseBean(context.getString(R.string.exception_net_work_time_out_code), context.getString(R.string.exception_net_work_time_out_message), "");
        } else if (e instanceof Cancel.CancelException) {
            return new ResponseBean(context.getString(R.string.exception_cancel_code), context.getString(R.string.exception_cancel_message), "");
        } else if (e instanceof IOException) {
            return new ResponseBean(context.getString(R.string.exception_local_other_code), context.getString(R.string.exception_local_other_message), "");
        } else {
            return new ResponseBean(context.getString(R.string.exception_local_other_code), context.getString(R.string.exception_local_other_message), "");
        }
    }
}