//package com.hxyd.dyt.utils.galleryfinal;
//
//import com.bumptech.glide.Glide;
//
//import cn.finalteam.galleryfinal.PauseOnScrollListener;
//
///**
// * 控制列表view滑动 是否暂停加载图片
// * <p>
// * <br> Author: 叶青
// * <br> Version: 1.0.0
// * <br> Date: 2017/2/18
// * <br> Copyright: Copyright © 2017 xTeam Technology. All rights reserved.
// */
//public class GlidePauseOnScrollListener extends PauseOnScrollListener {
//
//    public GlidePauseOnScrollListener(boolean pauseOnScroll, boolean pauseOnFling) {
//        super(pauseOnScroll, pauseOnFling);
//    }
//
//    @Override
//    public void resume() {
//        Glide.with(getActivity()).resumeRequests();
//    }
//
//    @Override
//    public void pause() {
//        Glide.with(getActivity()).pauseRequests();
//    }
//}