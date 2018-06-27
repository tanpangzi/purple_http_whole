package com.zimi.zimixing.activity.gpsMonitor.old;

import android.content.Intent;
import android.widget.TextView;

import com.zimi.zimixing.R;
import com.zimi.zimixing.widget.TitleView;

/**
 * GPS 设备信息
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2018/1/18
 * <br> Copyright: Copyright © 2017 xTeam Technology. All rights reserved.
 */
public class DeviceInfoActivity extends com.zimi.zimixing.activity.BaseActivity {

    private TitleView title_view;
    private TextView name;
    private TextView carNum;
    private TextView type;
    private TextView model;
    private TextView imei;
    private TextView isim;
    private TextView startTime;
    private TextView stopTime;
    private TextView phone;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_device_info;
    }

    @Override
    protected void findViews() {
        title_view = findViewByIds(R.id.title_view);
        name = findViewByIds(R.id.dev_name);
        carNum = findViewByIds(R.id.dev_car_mun);
        type = findViewByIds(R.id.dev_type);
        model = findViewByIds(R.id.dev_model);
        imei = findViewByIds(R.id.dev_imei);
        isim = findViewByIds(R.id.dev_isim);
        startTime = findViewByIds(R.id.dev_start_time);
        stopTime = findViewByIds(R.id.dev_stop_time);
        phone = findViewByIds(R.id.dev_phone);
    }

    @Override
    protected void initGetData() {
        Intent intent = getIntent();
        if (intent != null) {
            name.setText(intent.getStringExtra("name"));
            carNum.setText(intent.getStringExtra("carNum"));
            if (0 == intent.getIntExtra("type", -1)) {
                type.setText("有线");
            } else if (1 == intent.getIntExtra("type", -1)) {
                type.setText("无线");
            }

            model.setText(intent.getStringExtra("model"));
            imei.setText(intent.getStringExtra("imei"));
            isim.setText(intent.getStringExtra("isim"));
            startTime.setText(intent.getStringExtra("startTime"));
            stopTime.setText(intent.getStringExtra("stopTime"));
            phone.setText(intent.getStringExtra("phone"));
        }
    }

    @Override
    protected void init() {
        title_view.setTitle("设备信息");
    }

    @Override
    protected void widgetListener() {
        title_view.setLeftBtnImg();
    }
}
