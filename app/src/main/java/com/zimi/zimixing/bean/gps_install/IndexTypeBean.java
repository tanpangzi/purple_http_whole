package com.zimi.zimixing.bean.gps_install;

import com.zimi.zimixing.bean.BaseBean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 用户信息bean
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class IndexTypeBean extends BaseBean {

    /** 分类名称 */
    private String resName;
    /** 图片资源id */
    private int resId;

    @Override
    protected void init(JSONObject jSon) throws JSONException {
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }
}
