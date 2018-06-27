package com.zimi.zimixing.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by tanjun on 2018/3/29.
 * 所属组织
 */

public class OrgBean extends BaseBean {

    private String id;
    private String name;

    @Override
    protected void init(JSONObject jSon) throws JSONException {
        id = jSon.optString("id","");
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
