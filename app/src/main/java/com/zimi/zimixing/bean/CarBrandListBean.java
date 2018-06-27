package com.zimi.zimixing.bean;

import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * 首页车易贷上牌城市列表数据
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2017/1/18
 */
public class CarBrandListBean extends BaseBean {

    /** 车辆分组名称 */
    private String groupName = "";
    /** 车辆品牌名称 */
    private String name = "";
    /** ID */
    private String id = "";

    /**
     * GroupName : A
     * Name : 阿尔法·罗密欧
     * MakeId : 92
     */

    /**
     * GroupName : 一汽奥迪
     * Text : 奥迪100
     * Value : 2555
     */

    /**
     * GroupName : 2006 款
     * Text : 4.0L 自动 征程
     * Value : 106831
     */

    @Override
    public void init(JSONObject jSon) throws JSONException {
        //        "id": "15",
        //                "name": "比亚迪",
        //                "group": "B"
        groupName = jSon.optString("GroupName", "");//

        name = jSon.optString("Name", "");//
        if (TextUtils.isEmpty(name)) {
            name = jSon.optString("Text", "");//
        }
        id = jSon.optString("MakeId", "");//
        if (TextUtils.isEmpty(id)) {
            id = jSon.optString("Value", "");//
        }
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getHeadChar() {
        return groupName;
    }

    public void setHeadChar(String headChar) {
        this.groupName = headChar;
    }
}

