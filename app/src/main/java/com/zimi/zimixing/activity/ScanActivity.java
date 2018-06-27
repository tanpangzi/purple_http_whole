package com.zimi.zimixing.activity;

import android.content.Intent;
import android.os.Vibrator;

import com.zimi.zimixing.R;
import com.zimi.zimixing.configs.RequestCode;
import com.zimi.zimixing.utils.ToastUtil;
import com.zimi.zimixing.widget.TitleView;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zbar.ZBarView;

/**
 * Created by tanjun on 2018/4/9.
 * 扫描
 */

public class ScanActivity extends BaseActivity implements QRCodeView.Delegate{

    private QRCodeView mQRCodeView;

    private TitleView title_view;


    @Override
    protected int getContentViewId() {
        return R.layout.activity_scan;
    }

    @Override
    protected void findViews() {
        title_view = (TitleView) findViewById(R.id.title_view);
        mQRCodeView = (ZBarView) findViewById(R.id.zbarview);
        //mLamp = (CheckBox) findViewById(R.id.scan_lamp);
        mQRCodeView.setDelegate(this);

    }

    @Override
    protected void initGetData() {

    }

    @Override
    protected void init() {
        title_view.setLeftBtnImg();
        title_view.setTitle("扫描");

    }

    @Override
    protected void widgetListener() {

    }

    @Override
    protected void onResume() {
        super.onResume();

        mQRCodeView.startSpot();
        mQRCodeView.startCamera();
        mQRCodeView.startSpotAndShowRect();
        mQRCodeView.changeToScanQRCodeStyle();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mQRCodeView.stopSpot();
        mQRCodeView.stopCamera();
        mQRCodeView.stopSpotAndHiddenRect();
    }

    @Override
    protected void onStop() {

        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mQRCodeView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        vibrate();
        Intent intent = new Intent();
        intent.putExtra("IMEI", result);
        setResult(RequestCode.REQUEST_CODE_IMEI, intent);
        finish();
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        ToastUtil.showToast(ScanActivity.this, "打开相机出错");
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }
}
