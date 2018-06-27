package com.zimi.zimixing.bean;

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
    private int resName;
    /** 图片资源id */
    private int resId;

    @Override
    protected void init(JSONObject jSon) throws JSONException {
        //        token = jSon.optString("token", "");
    }

    public int getResName() {
        return resName;
    }

    public void setResName(int resName) {
        this.resName = resName;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }
}
