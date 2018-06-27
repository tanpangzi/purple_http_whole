package com.zimi.zimixing.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.amap.api.maps.model.LatLng;
import com.zimi.zimixing.R;
import com.zimi.zimixing.configs.Constant;
import com.zimi.zimixing.utils.code.HexBytesUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by tanjun on 2017/10/26.
 * 一些不好归类的方法放于此
 */

public class ComUtils {
    /**
     * 检测GPS是否打开
     *
     * @return
     */
    public static boolean checkGPSIsOpen(Context context) {
        boolean isOpen;
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        isOpen = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return isOpen;
    }

    public static void openGpsSetting(final Context context) {
        //没有打开则弹出对话框
        new AlertDialog.Builder(context)
                .setTitle(R.string.notifyTitle)
                .setMessage(R.string.gpsNotifyMsg)
                // 拒绝, 退出应用
                .setNegativeButton(R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //finish();
                                Toast.makeText(context.getApplicationContext(), "请开启GPS功能", Toast.LENGTH_SHORT).show();
                            }
                        })

                .setPositiveButton(R.string.setting,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //跳转GPS设置界面
                                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                context.startActivity(intent);
                            }
                        })
                .setCancelable(false)
                .show();
    }


    //String转毫秒
    public static long dateToMs(String strDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    //String转Date
    public static Date strToDate(String strDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 点击空白隐藏键盘
     *
     * @param view
     */
    public static void closeKeyBorad(Context mContext, View view) {
        InputMethodManager imm = (InputMethodManager)
                mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static Bitmap convertDrawable2BitmapByCanvas(Drawable drawable) {
        Bitmap bitmap = Bitmap
                .createBitmap(
                        drawable.getIntrinsicWidth(),
                        drawable.getIntrinsicHeight(),
                        drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                                : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static void startActivity(Context context, Class clazz) {
        startActivity(context, clazz, null);
    }

    public static void startActivity(Context context, Class clazz, Bundle bundle) {
        Intent intent = new Intent(context, clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }

    /**
     * 获取斜线后面的文件名
     *
     * @param path
     */
    public static String getFileName(String path) {
        int index = path.lastIndexOf("/");
        return path.substring(index + 1);
    }

    /**
     * 获取当前本地apk的版本
     *
     * @param mContext
     * @return
     */
    public static int getVersionCode(Context mContext) {
        int versionCode = 0;
        try {
            //获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionCode = mContext.getPackageManager().
                    getPackageInfo(mContext.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    private static final String JPUSH_MESSAGE = "jpush_message";

    public static void setMessage(boolean is) {
        PreferencesUtil.put(JPUSH_MESSAGE, is);
    }

    public static boolean getMessage() {
        return (boolean) PreferencesUtil.get(JPUSH_MESSAGE, false);
    }

    /**
     * 根据地址 获取网络时间（如 百度 淘宝）
     * @param webUrl 网络地址
     * @param format 时间格式
     * @return
     */
    public static String getWebsiteDatetime(String webUrl, String format){
        try {
            URL url = new URL(webUrl);// 取得资源对象
            URLConnection uc = url.openConnection();// 生成连接对象
            uc.connect();// 发出连接
            long ld = uc.getDate();// 读取网站日期时间
            Date date = new Date(ld);// 转换为标准时间对象
            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINA);// 输出北京时间
            return sdf.format(date);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取sign
     * @return
     */
    public static String getSign(String mills){
        try {
            String key_present = "aFaj81aXawkj8XNOF3GFCUn2q903LN8U";
            StringBuilder sb = new StringBuilder();
            sb.append(key_present);
            sb.append(mills);
            sb.append(key_present);
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] bytes = md.digest(sb.toString().getBytes("UTF-8"));
            String sign = HexBytesUtils.bytes2HexString(bytes);
            return sign;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static byte[] getSHA1Digest(String data) throws IOException {
        byte[] bytes = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            bytes = md.digest(data.getBytes("UTF-8"));
        } catch (GeneralSecurityException gse) {
            throw new IOException(gse.getMessage());
        }
        return bytes;
    }

    public static String signNew(String secret,String time ) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(secret);
            sb.append(time);
            sb.append(secret);
            byte[] sha1Digest = getSHA1Digest(sb.toString());
            return byte2hex(sha1Digest);
        } catch (IOException e) {

            return null;
        }
    }

    /**
     * 二进制转十六进制字符串
     *
     * @param bytes
     * @return
     */
    private static String byte2hex(byte[] bytes) {
        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex.toUpperCase());
        }
        return sign.toString();
    }

     //网络图片转drawable
    private Drawable loadImageFromNetwork(String imageUrl)
    {
        Drawable drawable = null;
        try {
            // 可以在这里通过文件名来判断，是否本地有此图片
            drawable = Drawable.createFromStream(
                    new URL(imageUrl).openStream(), "image.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (drawable == null) {
            LogUtil.d("null drawable");
        } else {
            LogUtil.d("not null drawable");
        }
        return drawable ;
    }

    /**
     * sp转化成px
     */
    public static int sp2px(Context context, float spValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (spValue * scale + 0.5f);
    }

    /**
     * dip转化成px
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return  系统版本号
     */
    public static int getSystemVersion() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 是否是5.0以上系统
     * @return
     */
    public static boolean isBiggerOS(){
        return Build.VERSION.SDK_INT >=21;
    }

    /**
     * 高德地图定位经纬度转百度经纬度
     * @param gd_lon
     * @param gd_lat
     * @return
     */
    public static double[] gaoDeToBaidu(double gd_lon, double gd_lat) {
        double[] bd_lat_lon = new double[2];
        double PI = 3.14159265358979324 * 3000.0 / 180.0;
        double x = gd_lon, y = gd_lat;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * PI);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * PI);
        bd_lat_lon[0] = z * Math.cos(theta) + 0.0065;
        bd_lat_lon[1] = z * Math.sin(theta) + 0.006;
        return bd_lat_lon;
    }



}
