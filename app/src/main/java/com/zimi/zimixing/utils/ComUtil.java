package com.zimi.zimixing.utils;

import android.text.TextUtils;
import android.util.Log;

import com.zimi.zimixing.BaseApplication;
import com.zimi.zimixing.bean.MenuPermissionBean;

import java.util.ArrayList;

/**
 * Created by tanjun on 2018/1/4.
 * 一些不好归类的方法放于此
 */

public class ComUtil {
    /**
     * 权限判断
     * @param key
     * @return
     */
    public static boolean getPermissionKey(String key){
        boolean isPermission = false;
        String menuPermissiond = BaseApplication.getInstance().getUserInfoBean().getPermissionKey();
        String[] permissions = menuPermissiond.split(",");

        if (null != menuPermissiond && permissions.length > 0 && !TextUtils.isEmpty(key)) {
            Log.e("tanjun","permission:" + menuPermissiond.toString());
            for (int i = 0; i < permissions.length; i++) {
                if (key.equals(permissions[i])) {
                    isPermission = true;
                    break;
                }
            }
        }
        return isPermission;
    }

}
