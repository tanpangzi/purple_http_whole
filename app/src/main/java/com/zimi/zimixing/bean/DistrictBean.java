package com.zimi.zimixing.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 地区信息bean
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class DistrictBean extends BaseBean {

    /**
     * id : 36
     * name : 石家庄市
     * city : [{"id":"37","name":"长安区"},{"id":"38","name":"桥东区"},{"id":"39","name":"桥西区"},{"id":"40","name":"新华区"},{"id":"41","name":"井陉矿区"},{"id":"42","name":"裕华区"},{"id":"43","name":"井陉县"},{"id":"44","name":"正定县"},{"id":"45","name":"栾城县"},{"id":"46","name":"行唐县"},{"id":"47","name":"灵寿县"},{"id":"48","name":"高邑县"},{"id":"49","name":"深泽县"},{"id":"50","name":"赞皇县"},{"id":"51","name":"无极县"},{"id":"52","name":"平山县"},{"id":"53","name":"元氏县"},{"id":"54","name":"赵县"},{"id":"55","name":"辛集市"},{"id":"56","name":"藁城市"},{"id":"57","name":"晋州市"},{"id":"58","name":"新乐市"},{"id":"59","name":"鹿泉市"}]
     */
    /** 省份信息 */
    private String name = "";
    /** 省份ID */
    private String id = "";

    @Override
    protected void init(JSONObject jSon) throws JSONException {

        name = jSon.optString("name", "");
        id = jSon.optString("id", "");
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

}
