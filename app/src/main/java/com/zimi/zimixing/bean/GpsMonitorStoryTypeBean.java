package com.zimi.zimixing.bean;


import org.json.JSONException;
import org.json.JSONObject;

/**
 * 筛选报警设备列表(获取报警分类和总数)
 * <p>
 * <br> Author: 叶青
 * <br> Version: 6.0.0
 * <br> Date: 2018/1/11
 * <br> Copyright: Copyright © 2017 xTeam Technology. All rights reserved.
 */
public class GpsMonitorStoryTypeBean extends BaseBean {

    //            /**
    //             * crossing : 2  越界报警数
    //             * displacement : 0  位移报警数
    //             * sum : 3  所有报警数
    //             * outages : 1  断电报警数
    //             */
    // crossing : 2  越界报警数
    private String crossing = "";
    //  displacement : 0  位移报警数
    private String displacement = "";
    // sum : 3  所有报警数
    private String sum = "";
    // * outages : 1  断电报警数
    private String outages = "";

    @Override
    protected void init(JSONObject jSon) throws JSONException {
        crossing = jSon.optString("crossing", "0");
        displacement = jSon.optString("displacement", "0");
        sum = jSon.optString("sum", "0");
        outages = jSon.optString("outages", "0");
    }

    public String getCrossing() {
        return crossing;
    }

    public void setCrossing(String crossing) {
        this.crossing = crossing;
    }

    public String getDisplacement() {
        return displacement;
    }

    public void setDisplacement(String displacement) {
        this.displacement = displacement;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public String getOutages() {
        return outages;
    }

    public void setOutages(String outages) {
        this.outages = outages;
    }
}
