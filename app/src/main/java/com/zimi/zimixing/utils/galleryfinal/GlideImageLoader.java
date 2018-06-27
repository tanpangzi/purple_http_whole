///*
// * Copyright (C) 2014 pengjianbo(pengjianbosoft@gmail.com), Inc.
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *     http://www.apache.org/licenses/LICENSE-2.0
// *
// *  Unless required by applicable law or agreed to in writing, software
// *  distributed under the License is distributed on an "AS IS" BASIS,
// *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// *  See the License for the specific language governing permissions and
// *  limitations under the License.
// */
//
//package com.hxyd.dyt.utils.galleryfinal;
//
//import android.app.Activity;
//import android.graphics.drawable.Drawable;
//
//import com.hxyd.dyt.R;
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.Priority;
//import com.bumptech.glide.request.RequestOptions;
//
//import cn.finalteam.galleryfinal.widget.GFImageView;
//
///**
// * <p>
// * <br> Author: 叶青
// * <br> Version: 1.0.0
// * <br> Date: 2017/2/18
// * <br> Copyright: Copyright © 2017 xTeam Technology. All rights reserved.
// */
//public class GlideImageLoader implements cn.finalteam.galleryfinal.ImageLoader {
//
//
//    @Override
//    public void displayImage(Activity activity, String path, final GFImageView imageView, Drawable defaultDrawable, int width, int height) {
//        //        Glide.with(activity)
//        //                //.load("file://" + path)
//        //                .load(path)
//        //                .placeholder(defaultDrawable)
//        //                .error(defaultDrawable)
//        //                .override(width, height)
//        //                .diskCacheStrategy(DiskCacheStrategy.NONE) //不缓存到SD卡
//        //                .skipMemoryCache(true)
//        //                //.centerCrop()
//        //                .into(new ImageViewTarget<GlideDrawable>(imageView) {
//        //                    @Override
//        //                    protected void setResource(GlideDrawable resource) {
//        //                        imageView.setImageDrawable(resource);
//        //                    }
//        //
//        //                    @Override
//        //                    public void setRequest(Request request) {
//        //                        imageView.setTag(R.id.img_tag_id, request);
//        //                    }
//        //
//        //                    @Override
//        //                    public Request getRequest() {
//        //                        return (Request) imageView.getTag(R.id.img_tag_id);
//        //                    }
//        //                });
//        RequestOptions options = new RequestOptions()
//                .centerCrop()//
//                .placeholder(defaultDrawable)//
//                .error(R.drawable.img_load_error_grey)//
//                .fallback(R.drawable.img_load_error_grey)
//                //                .crossFade()// 渐变切换
//                //                .thumbnail(0.1f)//先显示缩略图，再显示原图
//                .dontAnimate()// 不使用Glide的默认动画
//                // .override(200, 100)//指定图片大小 .override(Target.SIZE_ORIGINAL); 加载一张图片的原始尺寸
//                //                .skipMemoryCache(true)//禁止内存缓存
//                //                .diskCacheStrategy(DiskCacheStrategy.NONE)//禁止磁盘缓存
//                .priority(Priority.HIGH)//指定资源的优先加载顺序
//                //                .apply(bitmapTransform(new CropTransformation(CropTransformation.CropType.CIRCLE)))//
//                ;//
//
//        Glide.with(activity).load(path).apply(options).into(imageView);
//    }
//
//    @Override
//    public void clearMemoryCache() {
//    }
//}