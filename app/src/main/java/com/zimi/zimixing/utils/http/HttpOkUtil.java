package com.zimi.zimixing.utils.http;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;

import com.zimi.zimixing.BaseApplication;
import com.zimi.zimixing.R;
import com.zimi.zimixing.bean.ResponseBean;
import com.zimi.zimixing.biz.BaseBiz;
import com.zimi.zimixing.biz.BaseOkBiz;
import com.zimi.zimixing.configs.BroadcastFilters;
import com.zimi.zimixing.configs.ConfigServer;
import com.zimi.zimixing.utils.LogUtil;
import com.zimi.zimixing.utils.StringUtil;
import com.zimi.zimixing.utils.SystemUtil;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

/**
 * okHttp3 实现网络工具类  get、post请求 文件上传 文件下载 图片加载
 * okHttp只会对get请求进行缓存,post请求是不会进行缓存
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2017/4/13
 * <br> Copyright: Copyright © 2017 xTeam Technology. All rights reserved.
 */
public class HttpOkUtil {

    //    RequestBody：普通的请求参数
    //    FormBody.Builder：以表单的方式传递键值对的请求参数
    //    MultipartBody.Builder：以表单的方式上传文件的请求参数
    //Call
    //enqueue(Callback callback)：异步请求
    //execute()：同步请求

    private OkHttpClient okhttpclient;
    private static HttpOkUtil httpOkUtil;
    private Handler okHttpHandler;//全局处理子线程和M主线程通信

    public static HttpOkUtil getInstance() {
        if (httpOkUtil == null) {
            httpOkUtil = new HttpOkUtil();
        }
        return httpOkUtil;
    }

    private OkHttpClient getClient() {
        if (okhttpclient == null) {
            okhttpclient = new OkHttpClient.Builder()
                    .readTimeout(ConfigServer.SERVER_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)   //设置读取超时时间
                    .writeTimeout(ConfigServer.SERVER_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)  //设置写的超时时间
                    .connectTimeout(ConfigServer.SERVER_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)//设置连接超时时间
                    .retryOnConnectionFailure(false)                                           //设置不进行连接失败重试
                    .build();

            okHttpHandler = new Handler(BaseApplication.getInstance().getMainLooper());
        }
        return okhttpclient;
    }

    /**
     * 像指定地址发送get请求提交数据.
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateAuthor: 叶青
     * <br> CreateTime: 2017/4/17 13:57
     * <br> UpdateAuthor: 叶青
     * <br> UpdateTime: 2017/4/17 13:57
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param url
     *         数据提交路径.
     * @param map
     *         发送请求参数,key为属性名,value为属性值.
     */
    public String sendGet(String url, Map<String, String> map) throws IOException {
        // 添加请求参数
        Uri.Builder builderUri = Uri.parse(url).buildUpon();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            builderUri.appendQueryParameter(entry.getKey(), entry.getValue());
        }
        Uri uri = builderUri.build();
        url = uri.toString();

        // 创建request
        //Request request = new Request.Builder().tag(url).url(url).build();
        Request request = addHeaders().tag(url).url(url).build();
        Call call = getClient().newCall(request);           //请求加入队列
        Response response = call.execute();                 //同步的
        LogUtil.i(response.toString());
        if (response.isSuccessful()) {
            //获得返回体
            ResponseBody responseBody = response.body();
            return responseBody.string();
        }

        return "";
    }

    /**
     * 像指定地址发送post请求提交数据.
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateAuthor: 叶青
     * <br> CreateTime: 2017/4/17 13:57
     * <br> UpdateAuthor: 叶青
     * <br> UpdateTime: 2017/4/17 13:57
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param url
     *         数据提交路径.
     * @param map
     *         发送请求参数,key为属性名,value为属性值.
     */
    public String sendPost(final String url, Map<String, String> map) throws IOException {
        // 如果是3.0之前版本的，构建表单数据 FormEncodingBuilder; 3.0之后版本FormBody.Builder
        FormBody.Builder formBody = new FormBody.Builder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            formBody.add(entry.getKey(), entry.getValue());
        }
        FormBody body = formBody.build();//post请求参数为表单

        //        StringBuilder tempParams = new StringBuilder();
        //        for (Map.Entry<String, String> entry : map.entrySet()) {
        //            // tempParams.append("&").append(String.format("%s=%s", entry.getKey(), URLEncoder.encode(entry.getValue(), "utf-8")));
        //            tempParams.append("&").append(String.format("%s=%s", entry.getKey(),entry.getValue()));
        //        }
        //        String json = tempParams.toString().replaceFirst("&", "");
        //        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);//post请求，json为参数

        //Request request = new Request.Builder().headers(setHeaders(new HashMap<String, String>())).tag("").url(url).post(body).build();
        Request request = addHeaders().tag(url).url(url).post(body).build();
        Call call = getClient().newCall(request);                    //请求加入队列
        Response response = call.execute();                          //同步的 需要新开线程
        LogUtil.i(response.toString());
        if (response.isSuccessful()) {
            //获得返回体
            ResponseBody responseBody = response.body();
            return responseBody.string();
        }
        return "";
    }

    /**
     * 文件上传
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateAuthor: 叶青
     * <br> CreateTime: 2017/4/17 13:58
     * <br> UpdateAuthor: 叶青
     * <br> UpdateTime: 2017/4/17 13:58
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param url
     *         服务器地址
     * @param params
     *         附带参数集合
     * @param files
     *         文件集合，支持多文件上传
     */
    public String uploadFile(final String url, Map<String, String> params, Map<String, File> files) throws IOException {
        // form的分割线,自己定义
        String boundary = "xx--------------------------------------------------------------xx";
        MultipartBody.Builder builderBody = new MultipartBody.Builder(boundary);
        // 如果数据包含文件：setType(MultipartBody.FORM);
        builderBody.setType(MultipartBody.FORM);
        for (Map.Entry<String, String> entry : params.entrySet()) {
            builderBody.addFormDataPart(entry.getKey(), entry.getValue());
        }

        for (Map.Entry<String, File> entry : files.entrySet()) {
            final File file = entry.getValue();
            //            RequestBody fileBody = RequestBody.create(MediaType.parse(guessMimeType(file.getPath())), file);
            //            builderBody.addFormDataPart(entry.getKey(), file.getName(), fileBody);
            builderBody.addFormDataPart(entry.getKey(), file.getName(), new RequestBody() {
                @Override
                public MediaType contentType() {
                    return MediaType.parse(guessMimeType(file.getPath()));
                }

                @Override
                public long contentLength() {
                    return file.length();
                }

                @Override
                public void writeTo(BufferedSink sink) throws IOException {
                    try {
                        Source source = Okio.source(file);
                        Buffer buf = new Buffer();

                        Long contentLength = contentLength();
                        Long remaining = contentLength();
                        for (long readCount; (readCount = source.read(buf, 2048)) != -1; ) {// 2048字节 回调一次
                            sink.write(buf, readCount);
                            // 剩余进度
                            remaining = remaining - readCount;
                            // 当前上传进度
                            String progress = (contentLength - remaining) * 100 / contentLength + "%";
                            LogUtil.i(file.getName() + "上传进度..." + contentLength + "..." + remaining + "..." + progress);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        MultipartBody body = builderBody.build();

        // Request request = new Request.Builder().headers(setHeaders(new HashMap<String, String>())).tag("").url(url).post(body).build();
        Request request = addHeaders().tag("").url(url).post(body).build();
        Call call = getClient().newCall(request);                    //请求加入队列
        Response response = call.execute();                          //同步的 需要新开线程
        LogUtil.i(response.toString());
        if (response.isSuccessful()) {
            //获得返回体
            ResponseBody responseBody = response.body();
            return responseBody.string();
        }
        return "";
    }

    /**
     * 下载文件存储至指定路径.
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateAuthor: 叶青
     * <br> CreateTime: 2017/4/17 13:59
     * <br> UpdateAuthor: 叶青
     * <br> UpdateTime: 2017/4/17 13:59
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param url
     *         下载路径.
     * @param destFileDir
     *         本地文件存储的文件夹
     */
    public String downLoadFile(final String url, final String destFileDir) throws IOException {
        //Request request = new Request.Builder().headers(setHeaders(new HashMap<String, String>())).tag(url).url(url).build();
        Request request = addHeaders().tag(url).url(url).build();
        Call call = getClient().newCall(request);                    //请求加入队列
        Response response = call.execute();                          //同步的 需要新开线程
        LogUtil.i(response.toString());
        if (response.isSuccessful()) {
            // 获得返回体
            ResponseBody body = response.body();
            long total = body.contentLength();

            InputStream is = null;
            FileOutputStream fos = null;
            try {
                byte[] buf = new byte[2048];
                int len;
                long sum = 0;

                is = body.byteStream();
                File file = new File(destFileDir, StringUtil.getFileName(url));
                fos = new FileOutputStream(file);

                while ((len = is.read(buf)) != -1) {
                    fos.write(buf, 0, len);
                    sum += len;
                    // 当前下载进度
                    int progress = (int) (sum * 1.0f / total * 100);
                    LogUtil.i("下传进度..." + file.getName() + "..." + progress + "%");
                }

                fos.flush();
                LogUtil.i("下载文件成功");
                return "success";
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (is != null) is.close();
                    if (fos != null) fos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return "fail";
    }

    /**
     * 下载图片
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateAuthor: 叶青
     * <br> CreateTime: 2017/4/17 13:59
     * <br> UpdateAuthor: 叶青
     * <br> UpdateTime: 2017/4/17 13:59
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param url
     *         下载路径.
     * @param errorResId
     *         下载出错显示的图片资源文件ID
     */
    public Bitmap loadImage(final String url, final int errorResId) throws IOException {
        // Request request = new Request.Builder().headers(setHeaders(new HashMap<String, String>())).tag(url).url(url).build();
        Request request = addHeaders().tag(url).url(url).build();
        Call call = getClient().newCall(request);                     //请求加入队列
        Response response = call.execute();                           //同步的 需要新开线程
        LogUtil.i(response.toString());
        if (response.isSuccessful()) {
            // 获得返回体
            ResponseBody body = response.body();
            //response的body是图片的byte字节
            byte[] bytes = body.bytes();
            //把byte字节组装成图片
            Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            LogUtil.i("下载图片成功");
            return bmp;
        }
        return BitmapFactory.decodeResource(BaseApplication.getInstance().getResources(), errorResId);
    }

    /**
     * 根据文件路径判断MediaType
     */
    private String guessMimeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(path);
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }

    /**
     * 生成请求头Headers
     *
     * @return Headers
     */
    private Headers getHeaders() {
        Map<String, String> headersParams = new HashMap<>();
        headersParams.put("token", BaseApplication.getInstance().getToken());
        headersParams.put("platform", "Android");
        headersParams.put("osVersion", SystemUtil.getSystemVersion());
        headersParams.put("model", SystemUtil.getSystemModel());
        headersParams.put("brand", SystemUtil.getDeviceBrand());
        headersParams.put("appVersion", SystemUtil.getCurrentVersionName() + "");
        headersParams.put("tenantId", "0000100001");
        headersParams.put("channel", "dyt");

        Headers.Builder headersBuilder = new Headers.Builder();
        for (Map.Entry<String, String> entry : headersParams.entrySet()) {
            headersBuilder.add(entry.getKey(), entry.getValue());
        }
        return headersBuilder.build();
    }

    /**
     * 统一为请求添加头信息
     */
    private Request.Builder addHeaders() {
        return new Request.Builder().headers(getHeaders());
        //        Request.Builder requestBuilder = new Request.Builder();
        //        Map<String, String> headersParams = new HashMap<>();
        //        headersParams.put("platform", "Android");
        //        headersParams.put("osVersion", SystemUtil.getSystemVersion());
        //        headersParams.put("model", SystemUtil.getSystemModel());
        //        headersParams.put("brand", SystemUtil.getDeviceBrand());
        //        headersParams.put("appVersion", SystemUtil.getCurrentVersionCode() + "");
        //        for (Map.Entry<String, String> entry : headersParams.entrySet()) {
        //            requestBuilder.addHeader(entry.getKey(), entry.getValue());
        //        }
        //        return requestBuilder;
    }

    /* TODO *************************************************** 异步 **************************************************/

    /**
     * 像指定地址发送get请求提交数据.
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateAuthor: 叶青
     * <br> CreateTime: 2017/4/17 13:57
     * <br> UpdateAuthor: 叶青
     * <br> UpdateTime: 2017/4/17 13:57
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param url
     *         数据提交路径.
     * @param map
     *         发送请求参数,key为属性名,value为属性值.
     */
    public void sendGet(String url, Map<String, String> map, final HttpRequestCallBack callBack) {
        // 添加请求参数
        Uri.Builder builderUri = Uri.parse(url).buildUpon();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            builderUri.appendQueryParameter(entry.getKey(), entry.getValue());
        }
        Uri uri = builderUri.build();
        final String urlStr = uri.toString();

        // 创建request
        Request request = addHeaders().tag(urlStr).url(urlStr).build();
        //        Request.Builder builder = new Request.Builder();
        //        builder.tag("");                          // 设置tag 可用于取消线程
        //        builder.url(uri.toString());
        //        // builder.method("GET", null);           //可以省略，默认是GET请求
        //        Request request = builder.build();
        Call call = getClient().newCall(request);           //请求加入队列
        call.enqueue(                                       //异步的
                new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        // LogUtil.i(urlStr + ".." + e.getMessage());
                        failCallBack(e, callBack);
                        // call.cancel();//取消线程
                    }

                    @Override
                    public void onResponse(Call call, Response response) {
                        LogUtil.i(response.toString());
                        // 请求成功
                        successCallBack(response.body(), callBack);
                        // call.cancel();//取消线程
                    }
                });
    }

    /**
     * 像指定地址发送post请求提交数据.
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateAuthor: 叶青
     * <br> CreateTime: 2017/4/17 13:57
     * <br> UpdateAuthor: 叶青
     * <br> UpdateTime: 2017/4/17 13:57
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param url
     *         数据提交路径.
     * @param map
     *         发送请求参数,key为属性名,value为属性值.
     */
    public void sendPost(final String url, Map<String, String> map, final HttpRequestCallBack callBack) {
        // 如果是3.0之前版本的，构建表单数据 FormEncodingBuilder; 3.0之后版本FormBody.Builder
        FormBody.Builder formBody = new FormBody.Builder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            formBody.add(entry.getKey(), entry.getValue());
        }
        FormBody body = formBody.build();

        //        RequestBody body = RequestBody.create(JSON, json); // JSON为MediaType.parse(“application/json; charset=utf-8”)//post请求，json为参数
        Request request = addHeaders().tag(url).url(url).post(body).build();
        //        Request.Builder builder = new Request.Builder();
        //        builder.tag(url);                                    // 设置tag 可用于取消线程
        //        builder.url(url);                                   // 这里的URL路径为全路径，传递时注意
        //        builder.post(body);
        //        Request request = builder.build();
        Call call = getClient().newCall(request);           //请求加入队列
        call.enqueue(                                       //异步的
                new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        // LogUtil.i(url + ".." + e.getMessage());
                        failCallBack(e, callBack);
                        // call.cancel();//取消线程
                    }

                    @Override
                    public void onResponse(Call call, Response response) {
                        LogUtil.i(response.toString());
                        // 请求成功
                        successCallBack(response.body(), callBack);
                        // call.cancel();//取消线程
                    }
                });
    }
    //
    //    /**
    //     * 文件上传
    //     * <p>
    //     * <br> Version: 1.0.0
    //     * <br> CreateAuthor: 叶青
    //     * <br> CreateTime: 2017/4/17 13:58
    //     * <br> UpdateAuthor: 叶青
    //     * <br> UpdateTime: 2017/4/17 13:58
    //     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
    //     *
    //     * @param url
    //     *         服务器地址
    //     * @param params
    //     *         附带参数集合
    //     * @param files
    //     *         文件集合，支持多文件上传
    //     */
    //    public void uploadFile(final String url, Map<String, String> params, Map<String, File> files) {
    //        //        /* form的分割线,自己定义 */
    //        //        String boundary = "xx--------------------------------------------------------------xx";
    //        //        MultipartBody.Builder builderBody = new MultipartBody.Builder(boundary);
    //        MultipartBody.Builder builderBody = new MultipartBody.Builder();
    //        builderBody.setType(MultipartBody.FORM);            //如果数据包含文件：setType(MultipartBody.FORM);
    //        for (Map.Entry<String, String> entry : params.entrySet()) {
    //            builderBody.addFormDataPart(entry.getKey(), entry.getValue());
    //        }
    //
    //        for (Map.Entry<String, File> entry : files.entrySet()) {
    //            final File file = entry.getValue();
    //            //            RequestBody fileBody = RequestBody.create(MediaType.parse(guessMimeType(file.getPath())), file);
    //            //            builderBody.addFormDataPart(entry.getKey(), file.getName(), fileBody);
    //            builderBody.addFormDataPart(entry.getKey(), file.getName(), new RequestBody() {
    //                @Override
    //                public MediaType contentType() {
    //                    return MediaType.parse(guessMimeType(file.getPath()));
    //                }
    //
    //                @Override
    //                public long contentLength() {
    //                    return file.length();
    //                }
    //
    //                @Override
    //                public void writeTo(BufferedSink sink) throws IOException {
    //                    try {
    //                        Source source = Okio.source(file);
    //                        //sink.writeAll(source);
    //                        Buffer buf = new Buffer();
    //
    //                        Long contentLength = contentLength();
    //                        Long remaining = contentLength();
    //                        for (long readCount; (readCount = source.read(buf, 2048)) != -1; ) {
    //                            sink.write(buf, readCount);
    //                            // 剩余进度
    //                            remaining = remaining - readCount;
    //                            // 当前上传进度
    //                            String progress = (contentLength - remaining) * 100 / contentLength + "%";
    //                            LogUtil.i("上传进度..." + contentLength + "..." + remaining + "..." + progress);
    //                        }
    //                    } catch (Exception e) {
    //                        e.printStackTrace();
    //                    }
    //                }
    //            });
    //        }
    //        MultipartBody body = builderBody.build();
    //
    //        Request request = addHeaders().tag(url).url(url).post(body).build();
    //        //        Request.Builder builder = new Request.Builder();
    //        //        builder.tag(url);                                    // 设置tag 可用于取消线程
    //        //        builder.url(url);                                   //这里的URL路径为全路径，传递时注意
    //        //        builder.post(body);
    //        //        Request request = builder.build();
    //        Call call = getClient().newCall(request);           //请求加入队列
    //        call.enqueue(new Callback() {
    //            @Override
    //            public void onFailure(Call call, IOException e) {
    //                e.printStackTrace();
    //                LogUtil.i(url + ".." + e.getMessage());
    //                // call.cancel();//取消线程
    //                onFail(e, null);
    //            }
    //
    //            @Override
    //            public void onResponse(Call call, Response response) throws IOException {
    //     LogUtil.i(response.toString());
    //                // 请求成功
    //                //获得返回体
    //                ResponseBody body = response.body();
    //                String content = body.string();
    //                LogUtil.json(url, content);
    //
    //                body.close();
    //                // call.cancel();//取消线程
    //            }
    //        });
    //    }
    //
    //    /**
    //     * 下载文件存储至指定路径.
    //     * <p>
    //     * <br> Version: 1.0.0
    //     * <br> CreateAuthor: 叶青
    //     * <br> CreateTime: 2017/4/17 13:59
    //     * <br> UpdateAuthor: 叶青
    //     * <br> UpdateTime: 2017/4/17 13:59
    //     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
    //     *
    //     * @param url
    //     *         下载路径.
    //     * @param destFileDir
    //     *         本地文件存储的文件夹
    //     */
    //    public void downLoadFile(final String url, final String destFileDir) {
    //        Request request = addHeaders().tag(url).url(url).build();
    //        //        Request.Builder builder = new Request.Builder();
    //        //        builder.tag(url);                                   // 设置tag 可用于取消线程
    //        //        builder.url(url);                                   //这里的URL路径为全路径，传递时注意
    //        //        Request request = builder.build();
    //        Call call = getClient().newCall(request);
    //        call.enqueue(new Callback() {
    //            @Override
    //            public void onFailure(Call call, IOException e) {
    //                e.printStackTrace();
    //                LogUtil.i(url + ".." + e.getMessage());
    //                // call.cancel();//取消线程
    //                onFail(e, null);
    //            }
    //
    //            @Override
    //            public void onResponse(Call call, Response response) throws IOException {
    //     LogUtil.i(response.toString());
    //                ResponseBody body = response.body();
    //                //                String content = body.string();
    //                //                LogUtil.json(url, content);
    //                long total = body.contentLength();
    //
    //                InputStream is = null;
    //                FileOutputStream fos = null;
    //                try {
    //                    byte[] buf = new byte[2048];
    //                    int len;
    //                    long sum = 0;
    //
    //                    is = body.byteStream();
    //                    File file = new File(destFileDir, StringUtil.getFileName(url));
    //                    fos = new FileOutputStream(file);
    //
    //                    while ((len = is.read(buf)) != -1) {
    //                        fos.write(buf, 0, len);
    //                        sum += len;
    //                        // 当前下载进度
    //                        int progress = (int) (sum * 1.0f / total * 100);
    //                        LogUtil.i("上传进度..." + file.getName() + "..." + progress + "%");
    //                    }
    //
    //                    fos.flush();
    //                    LogUtil.i("下载文件成功");
    //                    // sendSuccessStringCallback(file.getAbsolutePath(), callback);
    //                } catch (Exception e) {
    //                    // sendFailedStringCallback(response.request(), e, callback);
    //                    e.printStackTrace();
    //                } finally {
    //                    try {
    //                        if (is != null) is.close();
    //                        if (fos != null) fos.close();
    //                    } catch (Exception e) {
    //                        e.printStackTrace();
    //                    }
    //                }
    //                body.close();
    //                // call.cancel();//取消线程
    //            }
    //        });
    //    }
    //
    //    /**
    //     * 下载图片
    //     * <p>
    //     * <br> Version: 1.0.0
    //     * <br> CreateAuthor: 叶青
    //     * <br> CreateTime: 2017/4/17 13:59
    //     * <br> UpdateAuthor: 叶青
    //     * <br> UpdateTime: 2017/4/17 13:59
    //     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
    //     *
    //     * @param image
    //     *         ImageView.
    //     * @param url
    //     *         下载路径.
    //     * @param errorResId
    //     *         下载出错显示的图片资源文件ID
    //     */
    //    public void loadImage(final ImageView image, final String url, final int errorResId) {
    //        Request request = addHeaders().tag(url).url(url).build();
    //        //        Request.Builder builder = new Request.Builder();
    //        //        builder.tag(url);                                    // 设置tag 可用于取消线程
    //        //        builder.url(url);                                   //这里的URL路径为全路径，传递时注意
    //        //        Request request = builder.build();
    //        Call call = getClient().newCall(request);           //请求加入队列
    //        // Response execute = call.execute();               //同步的
    //        call.enqueue(                                       //异步的
    //                new Callback() {
    //                    @Override
    //                    public void onFailure(Call call, IOException e) {
    //                        e.printStackTrace();
    //                        LogUtil.i(url + ".." + e.getMessage());
    //                        // call.cancel();//取消线程
    //                        onFail(e, null);
    //                    }
    //
    //                    @Override
    //                    public void onResponse(Call call, Response response) throws IOException {
    //     LogUtil.i(response.toString());
    //                        // 请求成功
    //                        ResponseBody body = response.body();
    //                        //                        String content = body.string();
    //                        //                        LogUtil.json(url, content);
    //                        //我写的这个例子是请求一个图片
    //                        //response的body是图片的byte字节
    //                        byte[] bytes = body.bytes();
    //                        //把byte字节组装成图片
    //                        final Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    //                        LogUtil.i("下载图片成功");
    //                        //回调是运行在非ui主线程，
    //                        //数据请求成功后，在主线程中更新
    //                        TestOkHttpActivity.getActivity().runOnUiThread(new Runnable() {
    //                            @Override
    //                            public void run() {
    //                                // 网络图片请求成功，更新到主线程的ImageView
    //                                if (bmp != null) {
    //                                    image.setImageBitmap(bmp);
    //                                } else {
    //                                    image.setImageResource(errorResId);
    //                                }
    //                            }
    //                        });
    //
    //                        body.close();
    //                        // call.cancel();//取消线程
    //                    }
    //                });
    //    }
    //

    /**
     * 取消线程
     */
    public void cancel(String tag) {
        for (Call call : getClient().dispatcher().runningCalls()) {
            if (call.request().tag().equals(tag)) {
                call.cancel();
                break;
            }
        }

        for (Call call : getClient().dispatcher().queuedCalls()) {
            if (call.request().tag().equals(tag)) {
                call.cancel();
                break;
            }
        }
    }

    /* TODO *************************************************** 异步数据处理 **************************************************/


    //    public interface RequestCallBack<T> {
    //        /**
    //         * 响应成功
    //         */
    //        void onSuccess(T result);
    //
    //        /**
    //         * 响应失败
    //         */
    //        void onFail(String errorMsg);
    //    }
    //    private <T> void successCallBack(final T result, final ReqCallBack<T> callBack) {
    //        okHttpHandler.post(new Runnable() {
    //            @Override
    //            public void run() {
    //                if (callBack != null) {
    //                    callBack.onReqSuccess(result);
    //                }
    //            }
    //        });
    //    }

    /**
     * 统一处理成功信息
     *
     * @param body
     *         ResponseBody
     * @param callBack
     *         HttpRequestCallBack
     */
    private void successCallBack(final ResponseBody body, final HttpRequestCallBack callBack) {
        okHttpHandler.post(new Runnable() {
            @Override
            public void run() {
                ResponseBean responseBean = new ResponseBean();
                Context context = BaseApplication.getInstance().getApplicationContext();
                try {
                    String result = body.string();
                    LogUtil.json("getSuccessMsg", result);
                    JSONObject jsonObject = new JSONObject(result);
                    responseBean.setStatus(jsonObject.optString(ConfigServer.RESULT_JSON_STATUS, context.getString(R.string.exception_local_other_code)));
                    responseBean.setInfo(jsonObject.optString(ConfigServer.RESULT_JSON_INFO, context.getString(R.string.exception_local_other_message)));
                    responseBean.setObject(jsonObject.optString(ConfigServer.RESULT_JSON_DATA, ""));
                } catch (Exception e) {
                    failCallBack(e, callBack);
                    return;
                }

                body.close();

                if (callBack != null) {
                    if (BaseBiz.checkSuccess(responseBean)) {
                        callBack.onSuccess(responseBean);
                    } else {
                        if (responseBean.getStatus().equals(ConfigServer.RESPONSE_STATUS_TOKEN_ERROR)) {
                            Intent intent = new Intent();
                            intent.setAction(BroadcastFilters.ACTION_TONKEN_EXPIRED);
                            context.sendBroadcast(intent);
                        } else {
                            callBack.onFail(responseBean);
                        }
                    }
                }
            }
        });
    }

    /**
     * 统一处理失败信息
     *
     * @param e
     *         Throwable
     * @param callBack
     *         HttpRequestCallBack
     */
    private void failCallBack(final Throwable e, final HttpRequestCallBack callBack) {
        LogUtil.e(e);
        okHttpHandler.post(new Runnable() {
            @Override
            public void run() {
                ResponseBean responseBean = BaseOkBiz.getFailMsg(e);
                if (callBack != null) {
                    callBack.onFail(responseBean);
                }
            }
        });
    }
}