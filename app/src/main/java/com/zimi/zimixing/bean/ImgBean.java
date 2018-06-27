package com.zimi.zimixing.bean;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by tanjun on 2018/1/19.
 * 评估 gps安装 车辆抵押 车辆解押 图片
 */

public class ImgBean extends BaseBean{

    private ArrayList<ImgListBean> imgList = new ArrayList<>();

    public ArrayList<ImgListBean> getImgList() {
        return imgList;
    }

    public void setImgList(ArrayList<ImgListBean> imgList) {
        this.imgList = imgList;
    }

    @Override
    protected void init(JSONObject jSon) throws JSONException {
        imgList = (ArrayList<ImgListBean>) BaseBean.toModelList(jSon.optString("imgList", ""), ImgListBean.class);
    }

    public static class ImgListBean extends BaseBean{
        /**
         * image_name : positionImages_01
         * group_id : positionImages
         * url : app/common/captcha/task/readImg?fileId=59ba2d9c841e351c625fe6db
         */

        private String image_name;
        private String group_id;
        private String url;

        /**
         * 图片描述
         */
        private String imgDesc = "";

        @Override
        protected void init(JSONObject jSon) throws JSONException {
            image_name = jSon.optString("image_name", "");
            group_id = jSon.optString("group_id", "");
            url = jSon.optString("url", "");
        }

        public String getImgDesc() {
            return imgDesc;
        }

        public void setImgDesc(String imgDesc) {
            this.imgDesc = imgDesc;
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
           /* if (!Constant.BASE_URL.substring(Constant.BASE_URL.length()-1).equals("/")&&
                    !TextUtils.isEmpty(url) &&url.length()>2&& !url.substring(0, 1).equals("/")) {
                url = "/" + url;
            }*/
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

    }
}
