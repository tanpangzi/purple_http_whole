package com.zimi.zimixing.bean.order;


import com.zimi.zimixing.bean.BaseBean;
import com.zimi.zimixing.utils.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 叮当详情 图像信息
 * <p>
 * <br> Author: 叶青
 * <br> Version: 6.0.0
 * <br> Date: 2018/1/11
 * <br> Copyright: Copyright © 2017 xTeam Technology. All rights reserved.
 */
public class OrderDetailImageInfoBean extends BaseBean {

    //"imageInfo": [{
    //    "image_name": "CL_B_08",
    //    "group_id": "CL_B",
    //    "url": "app\/common\/captcha\/task\/readImg?fileId=5a5443d75915684ded7f6672",
    //    "fileId": "5a5443d75915684ded7f6672"
    //}, {
    //    "image_name": "CL_B_06",
    //    "group_id": "CL_B",
    //    "url": "app\/common\/captcha\/task\/readImg?fileId=5a5443e15915684ded7f6674",
    //    "fileId": "5a5443e15915684ded7f6674"
    //}]

    private String image_name = "";
    //     ("KH_I"))        //身份证
    //     ("DJ|CL_A"))     //登记
    //     ("JS|CL_A"))     //驾驶
    //     ("XS|CL_A"))     //行驶
    //     ("KH_H"))        //其他
    //     ("CL_B"))        //车辆图片
    private String group_id = "";
    private String url = "";
    private String fileId = "";

    /** 当前选择的item下表 */
    private boolean isCheck = false;
    /** 描述 */
    private String dec = "";

    @Override
    protected void init(JSONObject jSon) throws JSONException {
        image_name = JsonUtil.optString(jSon, "image_name", "");
        group_id = JsonUtil.optString(jSon, "group_id", "");
        url = JsonUtil.optString(jSon, "url", "");
        fileId = JsonUtil.optString(jSon, "fileId", "");
    }

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getDec() {
        return dec;
    }

    public void setDec(String dec) {
        this.dec = dec;
    }


    /*private String imageId = "";
    private String loanId = "";
    private String name = "";
    private String category = "";
    private String thumbnailUrl = "";
    private String originalUrl = "";
    private String isValid = "";
    private String createdBy = "";
    private String updatedBy = "";

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getOriginalUrl() {
        if (!TextUtils.isEmpty(originalUrl) && originalUrl.length()>2&& !originalUrl.substring(0, 1).equals("/")) {
            originalUrl = "/" + originalUrl;
        }
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }*/
}
