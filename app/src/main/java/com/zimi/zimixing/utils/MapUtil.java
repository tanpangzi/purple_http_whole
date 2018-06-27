package com.zimi.zimixing.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class MapUtil {

    /**
     * 地图应用是否安装
     * @return
     */
    public static boolean isGdMapInstalled(Context context){
        return isAppInstalled(context, "com.autonavi.minimap");
    }

    public static boolean isBaiduMapInstalled(Context context){
        return isAppInstalled(context,"com.baidu.BaiduMap");
    }

    public static boolean isAppInstalled(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        //取得所有的PackageInfo
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        List<String> pName = new ArrayList<>();
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                pName.add(pn);
            }
        }
        //判断包名是否在系统包名列表中
        return pName.contains(packageName);
    }

    //高德
    public static void goToGaode(Context context, String lat, String lon,String address) {

            StringBuffer stringBuffer = new StringBuffer("androidamap://route?sourceApplication=").append("amap");

            stringBuffer.append("&dlat=").append(lat)
                    .append("&dlon=").append(lon)
                    .append("&dname=").append(address)
                    .append("&dev=").append(0)
                    .append("&t=").append(0);

            Intent intent = new Intent("android.intent.action.VIEW", android.net.Uri.parse(stringBuffer.toString()));
            intent.setPackage("com.autonavi.minimap");
            context.startActivity(intent);

    }

    //百度
    public static void goToBaidu(Context context, String lat, String lon, String address){
        if (isBaiduMapInstalled(context)){
            Intent intent = null;
            try {
                intent = Intent.getIntent("intent://map/direction?destination=latlng:" + lat + "," + lon + "|name:" + address + "&mode=driving&src=某某公司#Intent;" + "scheme=bdapp;package=com.baidu.BaiduMap;end");
                context.startActivity(intent);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }else {
            ToastUtil.showToast(context, "您的手机没有安装百度地图！");
        }


    }

}
