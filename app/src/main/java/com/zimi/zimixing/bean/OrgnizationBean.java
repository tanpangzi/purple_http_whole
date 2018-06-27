package com.zimi.zimixing.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 查询所属组织
 * Created by tanjun on 2018/5/7.
 */

public class OrgnizationBean extends BaseBean {
    /** 组织id */
    private String id;
    /** 组织名 */
    private String name;

    @Override
    protected void init(JSONObject jSon) throws JSONException {
        id = jSon.optString("id", "");
        name = jSon.optString("name", "");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
