package com.zimi.zimixing.activity;

import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;

import com.zimi.zimixing.BaseApplication;
import com.zimi.zimixing.R;
import com.zimi.zimixing.activity.gpsMonitor.GPSNaviFirstActivity;
import com.zimi.zimixing.service.AMapLocationService;
import com.zimi.zimixing.utils.IntentUtil;

/**
 * 引导页
 * <p>
 * <br> Author: 叶青
 * <br> Version: 6.0.0
 * <br> Date: 2018/1/5
 * <br> Copyright: Copyright © 2017 xTeam Technology. All rights reserved.
 */
public class SplashActivity extends BaseActivity {

    @Override
    protected int getContentViewId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void findViews() {

    }

    @Override
    protected void initGetData() {

    }

    @Override
    protected void init() {
        startService(new Intent().setClass(this, AMapLocationService.class));
        handler.postDelayed(runnable, 1500);
    }

    @Override
    protected void widgetListener() {

    }

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (!TextUtils.isEmpty(BaseApplication.getInstance().getToken())) {
               IntentUtil.gotoActivityAndFinish(SplashActivity.this, MainActivity.class); //主界面
               //IntentUtil.gotoActivityAndFinish(SplashActivity.this, GPSNaviFirstActivity.class); //导航界面
            } else {
                IntentUtil.gotoActivityAndFinish(SplashActivity.this, LoginActivity.class);
            }
        }
    };

    @Override
    protected void onDestroy() {
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        handler = null;
        super.onDestroy();
    }
}
