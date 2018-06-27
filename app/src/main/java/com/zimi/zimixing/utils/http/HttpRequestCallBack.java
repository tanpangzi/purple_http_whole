package com.zimi.zimixing.utils.http;

import com.zimi.zimixing.bean.ResponseBean;

/**
 * 网络请求结果回调
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2017/4/26
 * <br> Copyright: Copyright © 2017 xTeam Technology. All rights reserved.
 */
public abstract class HttpRequestCallBack {
    /**
     * 请求成功回调
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016年4月19日,下午12:52:26
     * <br> UpdateTime: 2016年4月19日,下午12:52:26
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param result
     *         请求返回数据
     */
    public abstract void onSuccess(ResponseBean result);

    /**
     * 请求失败回调
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016年4月19日,下午12:52:26
     * <br> UpdateTime: 2016年4月19日,下午12:52:26
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param result
     *         请求返回数据
     */
    public abstract void onFail(ResponseBean result);
}
