package com.zimi.zimixing.biz;

import com.zimi.zimixing.bean.BaseBean;
import com.zimi.zimixing.bean.ResponseBean;
import com.zimi.zimixing.bean.UpdateBean;
import com.zimi.zimixing.configs.ConfigFile;
import com.zimi.zimixing.configs.ConfigServer;
import com.zimi.zimixing.utils.DateUtil;
import com.zimi.zimixing.utils.PreferencesUtil;
import com.zimi.zimixing.utils.code.MD5Coder;
import com.zimi.zimixing.utils.http.HttpOkUtil;
import com.zimi.zimixing.utils.http.HttpRequestCallBack;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 系统业务处理
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class TestBiz {

    /**
     * 测试get请求
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime:  2016年12月24日,上午10:33:58
     * <br> UpdateTime:  2016年12月24日,上午10:33:58
     * <br> CreateAuthor:  叶青
     * <br> UpdateAuthor:  叶青
     * <br> UpdateInfo:  (此处输入修改内容,若无修改可不写.)
     */
    public static ResponseBean testGet() {
        String string = "http://api.map.baidu.com/geocoder/v2/";
        // String string = "http://api.map.baidu.com/geocoder/v2/?address=%s&output=json&ak=CidLuM0yn9FVWbw5GqLqpNg5rFlZDloO";
        Map<String, String> params = new HashMap<>();
        params.put("address", "湖北省宜昌市西陵一路亚洲广场B座2507号");
        params.put("output", "json");
        params.put("ak", "CidLuM0yn9FVWbw5GqLqpNg5rFlZDloO");

        ResponseBean result = BaseOkBiz.sendGet(string, params);
        //        if (BaseBiz.checkSuccess(result)) {
        //            // BaseBean.setResponseObjectList(result, AdvertisementBean.class);
        //            // BaseBean.setResponseObjectList(result, AdvertisementBean.class, "list");
        //            BaseBean.setResponseObject(result, UpdateBean.class);
        //            // 数据缓存
        //            PreferencesUtil.putObject(params.get(ConfigServer.SERVER_METHOD_KEY), result);
        //        }
        return result;
    }

    /**
     * 测试post请求
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime:  2016年12月24日,上午10:33:58
     * <br> UpdateTime:  2016年12月24日,上午10:33:58
     * <br> CreateAuthor:  叶青
     * <br> UpdateAuthor:  叶青
     * <br> UpdateInfo:  (此处输入修改内容,若无修改可不写.)
     */
    public static ResponseBean testPost() {
        final HashMap<String, String> params = new HashMap<>();
        params.put(ConfigServer.SERVER_METHOD_KEY, "version/check");
        String postTime = DateUtil.getDate("yyyyMMddHHmmss");
        String sign = MD5Coder.md5Encrypt(postTime + "wFaj81aXawkj8XNOF3GFCUn2q903zN8F").toLowerCase();
        params.put("posttime",postTime);
        params.put("sign", sign);
        params.put("type", "1");
        params.put("verCode", "1");
        //params.put("channel", "57fc786667e58e66ad0017df");

        ResponseBean result = BaseOkBiz.sendPost(ConfigServer.SERVER_API_URL, params);
        if (BaseBiz.checkSuccess(result)) {
            // BaseBean.setResponseObjectList(result, AdvertisementBean.class);
            // BaseBean.setResponseObjectList(result, AdvertisementBean.class, "list");
            BaseBean.setResponseObject(result, UpdateBean.class);
            // 数据缓存
            PreferencesUtil.put(params.get(ConfigServer.SERVER_METHOD_KEY), result);
        }
        return result;
    }

    /**
     * 测试post请求
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime:  2016年12月24日,上午10:33:58
     * <br> UpdateTime:  2016年12月24日,上午10:33:58
     * <br> CreateAuthor:  叶青
     * <br> UpdateAuthor:  叶青
     * <br> UpdateInfo:  (此处输入修改内容,若无修改可不写.)
     */
    public static ResponseBean testUpLoadFile() {
        //        final String string = "http://61.130.9.88:8081/router/rest/";
        //        String method = "fileService.upload";
        //        final HashMap<String, String> params = new HashMap<>();
        //        params.put("key", "1.0");
        //        params.put("method", method);
        //        params.put("app_key", "10");
        //        params.put("v", "1.0");
        //
        final Map<String, File> Files = new HashMap<>();
        Files.put("image", new File(ConfigFile.PATH_IMAGES + "456.jpg"));

        final HashMap<String, String> params = new HashMap<>();
        params.put(ConfigServer.SERVER_METHOD_KEY, "auth/uploadPicture");
        String postTime = DateUtil.getDate("yyyyMMddHHmmss");
        String sign = MD5Coder.md5Encrypt(postTime + "wFaj81aXawkj8XNOF3GFCUn2q903zN8F").toLowerCase();
        params.put("posttime",postTime);
        params.put("sign", sign);
        params.put("token", "需要替換");
        ResponseBean result = BaseOkBiz.upLoadFile(ConfigServer.SERVER_API_URL, params, Files);
        //        if (BaseBiz.checkSuccess(result)) {
        //            // BaseBean.setResponseObjectList(result, AdvertisementBean.class);
        //            // BaseBean.setResponseObjectList(result, AdvertisementBean.class, "list");
        //            BaseBean.setResponseObject(result, UpdateBean.class);
        //            // 数据缓存
        //            PreferencesUtil.putObject(params.get(ConfigServer.SERVER_METHOD_KEY), result);
        //        }
        return result;
    }

    /**
     * 测试post请求
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime:  2016年12月24日,上午10:33:58
     * <br> UpdateTime:  2016年12月24日,上午10:33:58
     * <br> CreateAuthor:  叶青
     * <br> UpdateAuthor:  叶青
     * <br> UpdateInfo:  (此处输入修改内容,若无修改可不写.)
     */
    public static ResponseBean testDownLoadFile() {
        ResponseBean result = BaseOkBiz.downLoadFile("http://fzd-10017606.cos.myqcloud.com/pkg/app-fzdV3_2_0.apk", ConfigFile.PATH_LOG);
        return result;
    }

    /**
     * 测试post请求
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime:  2016年12月24日,上午10:33:58
     * <br> UpdateTime:  2016年12月24日,上午10:33:58
     * <br> CreateAuthor:  叶青
     * <br> UpdateAuthor:  叶青
     * <br> UpdateInfo:  (此处输入修改内容,若无修改可不写.)
     */
    public static ResponseBean testPost1() {
        final HashMap<String, String> params = new HashMap<>();
        params.put(ConfigServer.SERVER_METHOD_KEY, "version/check");
        String postTime = DateUtil.getDate("yyyyMMddHHmmss");
        //40位的SHA签名
        String sign = MD5Coder.md5Encrypt(postTime + "wFaj81aXawkj8XNOF3GFCUn2q903zN8F").toLowerCase();
        params.put("posttime",postTime);
        params.put("sign", sign);
        params.put("type", "1");
        params.put("verCode", "1");
        //params.put("channel", "57fc786667e58e66ad0017df");

        ResponseBean result = BaseBiz.sendPost(params);
        if (BaseBiz.checkSuccess(result)) {
            BaseBean.setResponseObject(result, UpdateBean.class);
        }
        return result;
    }

        /* TODO *************************************************** 异步 **************************************************/

        /**
         * 测试get请求
         * <p>
         * <br> Version: 1.0.0
         * <br> CreateTime:  2016年12月24日,上午10:33:58
         * <br> UpdateTime:  2016年12月24日,上午10:33:58
         * <br> CreateAuthor:  叶青
         * <br> UpdateAuthor:  叶青
         * <br> UpdateInfo:  (此处输入修改内容,若无修改可不写.)
         */
        public static void testGet(final HttpRequestCallBack callBack) {
            String string = "http://api.map.baidu.com/geocoder/v2/";
            // String string = "http://api.map.baidu.com/geocoder/v2/?address=%s&output=json&ak=CidLuM0yn9FVWbw5GqLqpNg5rFlZDloO";
            Map<String, String> map = new HashMap<>();
            map.put("address", "湖北省宜昌市西陵一路亚洲广场B座2507号");
            map.put("output", "json");
            map.put("ak", "CidLuM0yn9FVWbw5GqLqpNg5rFlZDloO");
            HttpOkUtil.getInstance().sendGet(string, map, new HttpRequestCallBack() {

                @Override
                public void onSuccess(ResponseBean resultObj) {
//                    String resultStr = (String) resultObj;
                    callBack.onSuccess(resultObj);
                }

                @Override
                public void onFail(ResponseBean result) {
                    callBack.onFail(result);
                }
            });
        }

        /**
         * 测试post请求
         * <p>
         * <br> Version: 1.0.0
         * <br> CreateTime:  2016年12月24日,上午10:33:58
         * <br> UpdateTime:  2016年12月24日,上午10:33:58
         * <br> CreateAuthor:  叶青
         * <br> UpdateAuthor:  叶青
         * <br> UpdateInfo:  (此处输入修改内容,若无修改可不写.)
         */
        public static void testPost(final HttpRequestCallBack callBack) {
            final HashMap<String, String> params = new HashMap<>();
            params.put(ConfigServer.SERVER_METHOD_KEY, "version/check");
            String postTime = DateUtil.getDate("yyyyMMddHHmmss");
            String sign = MD5Coder.md5Encrypt(postTime + "wFaj81aXawkj8XNOF3GFCUn2q903zN8F").toLowerCase();
            params.put("posttime",postTime);
            params.put("sign", sign);
            params.put("type", "1");
            params.put("verCode", "1");

            //        ResponseBean result = (ResponseBean) PreferencesUtil.getObject(params.get(ConfigServer.SERVER_METHOD_KEY));
            //        if (result != null) {
            //            callBack.onSuccess(result);
            //        }
            HttpOkUtil.getInstance().sendPost(ConfigServer.SERVER_API_URL+"version/check", params, new HttpRequestCallBack() {

                @Override
                public void onSuccess(ResponseBean result) {
//                    String resultStr = (String) resultObj;
//                    ResponseBean result = parseData(resultStr);
                    if (BaseBiz.checkSuccess(result)) {
                        // BaseBean.setResponseObjectList(result, AdvertisementBean.class);
                        // BaseBean.setResponseObjectList(result, AdvertisementBean.class, "list");
                        BaseBean.setResponseObject(result, UpdateBean.class);
                        // 数据缓存
                        PreferencesUtil.putObject(params.get(ConfigServer.SERVER_METHOD_KEY), result);
                    }
                    callBack.onSuccess(result);
                }

                @Override
                public void onFail(ResponseBean result) {
                    callBack.onFail(result);
                }
            });
        }
    //
    //    /**
    //     * 解析json
    //     * <p>
    //     * <br> Version: 4.0.0
    //     * <br> CreateAuthor: 叶青
    //     * <br> CreateTime: 2017/4/26 15:22
    //     * <br> UpdateAuthor: 叶青
    //     * <br> UpdateTime: 2017/4/26 15:22
    //     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
    //     *
    //     * @param result
    //     *         json字符串
    //     *
    //     * @return ResponseBean
    //     */
    //    private static ResponseBean parseData(String result) {
    //        ResponseBean responseBean = new ResponseBean();
    //        HashMap<String, String> map = new HashMap<>();
    //        try {
    //            map.putAll(JsonUtil.jsonObjectStr2Map(result));
    //        } catch (JSONException e) {
    //            e.printStackTrace();
    //            map = new HashMap<>();
    //            map.put("status", BaseApplication.getInstance().getString(R.string.exception_net_work_json_code));
    //            map.put("info", BaseApplication.getInstance().getString(R.string.exception_net_work_json_message));
    //        }
    //
    //        responseBean.setStatus(map.get("status"));
    //        responseBean.setInfo(map.get("info"));
    //        responseBean.setObject(map.get("data"));
    //        return responseBean;
    //    }

}