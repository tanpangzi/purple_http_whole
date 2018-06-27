package com.zimi.zimixing.utils.http;

import android.net.Uri;

import com.zimi.zimixing.BaseApplication;
import com.zimi.zimixing.configs.ConfigServer;
import com.zimi.zimixing.executor.Cancel;
import com.zimi.zimixing.interf.OnDownLoadCallBack;
import com.zimi.zimixing.utils.LogUtil;
import com.zimi.zimixing.utils.StringUtil;
import com.zimi.zimixing.utils.SystemUtil;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * 网络操作类.
 * <p>
 * 用于网络的POST 、 GET 、 download、upload等操作
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年3月30日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class HttpUtil {

    /** 加解密统一使用的编码方式 */
    private final static String ENCODING = "UTF-8";
    /** 读取输入流 */
    private InputStreamReader inputStreamReader;
    /** BufferedReader */
    private BufferedReader bufferedReader;
    /** http链接 */
    private HttpURLConnection conn;
    /** 输出流 */
    private DataOutputStream output;
    /** 已下载的大小 */
    private int downLen;

    /**
     * 像指定地址发送post请求提交数据.
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016年12月31日,上午2:06:53
     * <br> UpdateTime: 2016年12月31日,上午2:06:53
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param path
     *         数据提交路径.
     * @param timeout
     *         超时时间(毫秒).
     * @param attribute
     *         发送请求参数,key为属性名,value为属性值.
     *
     * @return 服务器的响应信息, 当发生错误时返回响应码.
     *
     * @throws IOException
     *         网络连接错误时抛出IOException.
     * @throws TimeoutException
     *         网络连接超时时抛出TimeoutException.
     */
    public String sendPost(String path, int timeout, Map<String, String> attribute) throws IOException, TimeoutException, Cancel.CancelException {
        try {
            URL url = new URL(path);
            conn = (HttpURLConnection) url.openConnection();
            conn.setUseCaches(false); // 设置是否启用缓存,post请求不能使用缓存.
            conn.setDoOutput(true); // 设置输出,post请求必须设置.
            conn.setDoInput(true); // 设置输入,post请求必须设置.
            // 设置通用的请求属性 // 请求头数据
            //            conn.setRequestProperty("Content-Type", "text/html;charset=utf-8");//有时候你需要给 connection 指定 Content-type
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("charset", ENCODING);
            Map<String, String> headersParams = getHeaders();
            for (Map.Entry<String, String> entry : headersParams.entrySet()) {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }
            conn.setConnectTimeout(timeout);
            conn.setReadTimeout(timeout);
            conn.setRequestMethod("POST");
            conn.connect(); // 打开网络链接.
            Cancel.checkInterrupted();
            output = new DataOutputStream(conn.getOutputStream());

            output.writeBytes(getParams(attribute)); // 将请求参数写入网络链接.
            output.flush();
            Cancel.checkInterrupted();
            return readResponse();
        } catch (SocketTimeoutException e) {
            throw new TimeoutException(e.getMessage());
        }
    }

    /**
     * 像指定地址发送get请求.
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016年12月31日,上午3:04:16
     * <br> UpdateTime: 2016年12月31日,上午3:04:16
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param path
     *         数据提交路径.
     * @param timeout
     *         超时时间,单位为毫秒.
     * @param map
     *         参数
     *
     * @return 服务器的响应信息, 当发生错误时返回响应码.
     *
     * @throws IOException
     *         网络连接错误时抛出IOException.
     * @throws TimeoutException
     *         网络连接超时时抛出TimeoutException.
     */
    public String sendGet(String path, int timeout, Map<String, String> map) throws IOException, TimeoutException, Cancel.CancelException {
        try {
            Uri.Builder builderUri = Uri.parse(path).buildUpon();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                builderUri.appendQueryParameter(entry.getKey(), entry.getValue());
            }
            Uri uri = builderUri.build();
            LogUtil.i(uri.toString());
            URL url = new URL(uri.toString());
            conn = (HttpURLConnection) url.openConnection();
            conn.setUseCaches(false); // 设置是否启用缓存,post请求不能使用缓存.
            // 设置通用的请求属性 // 请求头数据
            //            conn.setRequestProperty("Content-Type", "text/html;charset=utf-8");//有时候你需要给 connection 指定 Content-type
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("charset", ENCODING);
            Map<String, String> headersParams = getHeaders();
            for (Map.Entry<String, String> entry : headersParams.entrySet()) {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }
            conn.setConnectTimeout(timeout);
            conn.setReadTimeout(timeout);
            conn.setRequestMethod("GET");

            conn.connect(); // 打开网络链接.
            Cancel.checkInterrupted();
            return readResponse();
        } catch (SocketTimeoutException e) {
            throw new TimeoutException(e.getMessage());
        }
    }

    /**
     * 读取服务器响应信息.
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016年12月31日,上午3:04:49
     * <br> UpdateTime: 2016年12月31日,上午3:04:49
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @return 服务器的响应信息, 当发生错误时返回响应码.
     *
     * @throws IOException
     *         读取信息发生错误时抛出IOException.
     */
    private String readResponse() throws IOException, Cancel.CancelException {
        // 返回结果
        String result;
        int responseCode = conn.getResponseCode();
        LogUtil.i(conn.getURL() + "\n" + "conn.getResponseCode()---->>>>" + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) { // 若响应码以2开头则读取响应头总的返回信息
            //返回打开连接读取的输入流
            inputStreamReader = new InputStreamReader(conn.getInputStream());
            bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            StringBuilder stringBuffer = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                Cancel.checkInterrupted();
                stringBuffer.append(line);
            }
            //result = EncodingUtils.getString(stringBuffer.toString().getBytes(), ENCODING);
            result = new String(stringBuffer.toString().getBytes(), ENCODING);
        } else { // 若响应码不以2开头则返回错误信息.
            result = "error";
        }
        closeConnection();
        return result;
    }

    /**
     * 将发送请求的参数构造为指定格式.
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016年12月31日,上午3:05:28
     * <br> UpdateTime: 2016年12月31日,上午3:05:28
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param attribute
     *         发送请求的参数,key为属性名,value为属性值.
     *
     * @return 指定格式的请求参数.
     */
    private String getParams(Map<String, String> attribute) {
        if (attribute.size() <= 0) {
            return "";
        }
        StringBuilder params = new StringBuilder();
        // 取出所有参数进行构造
        for (Map.Entry<String, String> entry : attribute.entrySet()) {
            try {
                //                String param = "";
                //                if (RegexUtil.isContainsChinese(entry.getValue())) {
                //                    param = entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), ENCODING) + "&";
                //                } else {
                //                    param = entry.getKey() + "=" + entry.getValue() + "&";
                //                }
                String param = entry.getKey() + "=" + entry.getValue() + "&";
                // String param = entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), ENCODING) + "&";
                params.append(param);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // 返回构造结果
        return params.toString().substring(0, params.toString().length() - 1);
    }


    /**
     * 生成请求头Headers
     *
     * @return Headers
     */
    private Map<String, String> getHeaders() {
        Map<String, String> headersParams = new HashMap<>();
        headersParams.put("token", BaseApplication.getInstance().getToken());
        headersParams.put("platform", "Android");
        headersParams.put("osVersion", SystemUtil.getSystemVersion());
        headersParams.put("model", SystemUtil.getSystemModel());
        headersParams.put("brand", SystemUtil.getDeviceBrand());
        headersParams.put("appVersion", SystemUtil.getCurrentVersionName() + "");
        //headersParams.put("appVersion", SystemUtil.getCurrentVersionCode() + "");
        headersParams.put("tenantId", "0000100001");
        headersParams.put("channel", "dyt");

        return headersParams;
    }

    /**
     * 关闭链接与所有从链接中获得的流.
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016年12月31日,上午3:06:27
     * <br> UpdateTime: 2016年12月31日,上午3:06:27
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @throws IOException
     *         关闭发生错误时抛出IOException.
     */
    private void closeConnection() throws IOException {
        if (inputStreamReader != null) {
            inputStreamReader.close();
        }
        if (bufferedReader != null) {
            bufferedReader.close();
        }
        if (output != null) {
            output.close();
        }
        if (conn != null) {
            conn.disconnect();
        }
    }

    // **************************************文件上传下载

    /**
     * 文件上传
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016年12月31日,上午3:11:17
     * <br> UpdateTime: 2016年12月31日,上午3:11:17
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param path
     *         服务器地址
     * @param params
     *         附带参数集合
     * @param files
     *         文件集合，支持多文件上传
     */
    public String uploadFile(String path, Map<String, String> params, Map<String, File> files) throws IOException, Cancel.CancelException {
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(path);
        MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
        if (params.size() > 0) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                StringBody stringBody = new StringBody(entry.getValue(), Charset.forName(HTTP.UTF_8));
                multipartEntity.addPart(entry.getKey(), stringBody);
            }
        }
        if (files.size() > 0) {
            for (Map.Entry<String, File> entry : files.entrySet()) {
                FileBody fileBody = new FileBody(entry.getValue());
                multipartEntity.addPart(entry.getKey(), fileBody);
            }
        }
        // 请求头数据
        Map<String, String> headersParams = getHeaders();
        for (Map.Entry<String, String> entry : headersParams.entrySet()) {
            httpPost.addHeader(entry.getKey(), entry.getValue());
        }
        httpPost.setEntity(multipartEntity);

        String result;
        HttpResponse response = httpClient.execute(httpPost);
        Cancel.checkInterrupted();
        int statueCode = response.getStatusLine().getStatusCode();
        LogUtil.i(path + "\n" + "conn.getResponseCode()---->>>>" + statueCode);
        if (statueCode == HttpURLConnection.HTTP_OK) {
            result = EntityUtils.toString(response.getEntity(), ENCODING);
            //result = new String(response.getEntity(), ENCODING);
            LogUtil.json(path, result);
        } else {
            result = "error";
        }

        try {
            multipartEntity.consumeContent();
            httpPost.abort();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Cancel.checkInterrupted();
        return result;

    }

    /**
     * 下载文件,下载文件存储至指定路径.
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016年12月31日,上午3:06:58
     * <br> UpdateTime: 2016年12月31日,上午3:06:58
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param netPath
     *         下载路径.
     * @param localParentPath
     *         本地文件存储目录路径.
     *
     * @return 下载成功返回true, 若下载失败则返回false.
     */
    public boolean downloadFile(String netPath, String localParentPath) throws IOException, Cancel.CancelException {
        return downloadFileWithPro(netPath, localParentPath, null);
    }

    /**
     * 下载文件 并返回当前下载的进度
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime:: 2016-11-3,下午2:08:57
     * <br> UpdateTime: 2016-11-3,下午2:08:57
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: : (此处输入修改内容, 若无修改可不写.)
     *
     * @param netPath
     *         网络文件路径
     * @param localParentPath
     *         本地文件存储目录路径
     * @param downLoadCallable
     *         监听线程回调接口
     *
     * @return false：下载文件失败；true:下载文件成功
     */
    public boolean downloadFileWithPro(String netPath, String localParentPath, OnDownLoadCallBack downLoadCallable) throws IOException, Cancel.CancelException {

        String localPath = StringUtil.getLocalCachePath(netPath, localParentPath);
        File localFile = new File(localPath);

        // 文件已经存在，无需下载
        if (localFile.exists()) {
            return true;
        }

        // 临时缓存文件
        // /storage/emulated/0/Diver/3E53AE683F8E8C84221DB763B30FE907/video/558d07830881c.mp4.temp
        String tempPath = localPath + ".temp";
        File tempFile = new File(tempPath);

        URL url = new URL(netPath);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(ConfigServer.SERVER_CONNECT_TIMEOUT * 2);//设置客户端连接超时间隔，如果超过指定时间  服务端还没有响应 不等了
        //            conn.setRequestMethod("GET");
        //            conn.setDoInput(true);
        //            conn.connect();
        // 获取文件大小
        float apkSize = (float) conn.getContentLength();
        int code = conn.getResponseCode();
        LogUtil.i(netPath + "\n" + "conn.getResponseCode()---->>>>" + code);
        if (code == HttpURLConnection.HTTP_OK) {
            Cancel.checkInterrupted();
            InputStream input = conn.getInputStream();
            tempFile.createNewFile(); // 创建文件
            OutputStream output = new FileOutputStream(tempFile);
            byte buffer[] = new byte[1024];
            int read = 0;
            while ((read = input.read(buffer)) != -1) { // 读取信息循环写入文件
                Cancel.checkInterrupted();
                output.write(buffer, 0, read);
                if (downLoadCallable != null) {
                    synchronized (BaseApplication.getInstance().getApplicationContext()) {
                        downLen += read;
                        LogUtil.i("下载进度" + downLen + "===" + apkSize);
                        downLoadCallable.ResultProgress((int) (downLen / apkSize * 100), (int) apkSize, downLen);
                    }
                }
            }
            output.flush();
            output.close();
            input.close();
            // 文件下载完成，更名为正式缓存文件
            tempFile.renameTo(localFile);
            Cancel.checkInterrupted();
            return true;
        }
        Cancel.checkInterrupted();
        return false;
    }
}