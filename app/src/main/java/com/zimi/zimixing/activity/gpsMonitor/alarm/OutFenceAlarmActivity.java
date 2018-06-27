package com.zimi.zimixing.activity.gpsMonitor.alarm;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.Marker;
import com.zimi.zimixing.R;
import com.zimi.zimixing.activity.gpsMonitor.old.amapUtils.GPS3DUtils;
import com.zimi.zimixing.utils.AppManagerUtil;
import com.zimi.zimixing.utils.FileUtil;
import com.zimi.zimixing.utils.LogUtil;
import com.zimi.zimixing.utils.PermissionUtils;
import com.zimi.zimixing.utils.ToastUtil;
import com.zimi.zimixing.widget.TitleViewPurple;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 出围栏报警
 * created by tanjun
 */
public class OutFenceAlarmActivity extends FragmentActivity {

    /**
     * 标题栏
     */
    @BindView(R.id.title_view)
    TitleViewPurple titleView;
    /**
     * 地图
     */
    @BindView(R.id.map)
    MapView mapView;
    /**
     * 放大
     */
    @BindView(R.id.view_zoom_out)
    View viewZoomOut;
    /**
     * 缩小
     */
    @BindView(R.id.view_zoom_in)
    View viewZoomIn;
    /**
     * 车位置
     */
    @BindView(R.id.gps_car_position)
    ImageView gpsCarPosition;
    /**
     * 手机当前位置
     */
    @BindView(R.id.gps_mobile_phone_position)
    ImageView gpsMobilePhonePosition;

    @BindView(R.id.tv_userName)
    TextView tvUserName;
    @BindView(R.id.tv_car_num)
    TextView tvCarNum;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_car_state)
    TextView tvCarState;
    @BindView(R.id.tv_car_speed)
    TextView tvCarSpeed;
    @BindView(R.id.tv_alarm_type)
    TextView tvAlarmType;
    @BindView(R.id.tv_fence)
    TextView tvFence;
    @BindView(R.id.tv_car_pos)
    TextView tvCarPos;
    @BindView(R.id.rl_alarm_close)
    LinearLayout rlAlarmClose;
    @BindView(R.id.ll_car_state)
    LinearLayout ll_car_state;
    @BindView(R.id.view_line)
    View view_line;
    @BindView(R.id.iv_arrow)
    ImageView iv_arrow;

    private AMap mAMap;

    /**
     * 当前手机经纬度
     */
    private double phone_lat;
    private double phone_lon;

    /**
     * 汽车的经纬度
     */
    private double car_lat;
    private double car_lon;

    private boolean isClosed = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManagerUtil.getAppManager().addActivity(this);
        setContentView(R.layout.activity_out_fence);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        if (mAMap == null) {
            mAMap = mapView.getMap();
        }
        GPS3DUtils.getInstance().setUiSettings(mAMap);
        initTitle();
        mapView.onCreate(savedInstanceState);
        getPhonePos();

    }

    @OnClick({R.id.map, R.id.view_zoom_out, R.id.view_zoom_in, R.id.gps_car_position,
            R.id.gps_mobile_phone_position, R.id.rl_alarm_close})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.view_zoom_out://放大
                GPS3DUtils.getInstance().setZoomOut(mAMap);
                break;
            case R.id.view_zoom_in://缩小
                GPS3DUtils.getInstance().setZoomIn(mAMap);
                break;
            case R.id.gps_car_position://车位置
                showMarker(phone_lat, phone_lon);
                break;
            case R.id.gps_mobile_phone_position://手机当前位置
                getPhonePos();
                break;

            case R.id.rl_alarm_close:
                if (!isClosed){
                    isClosed = true;
                    iv_arrow.setImageResource(R.drawable.alarm_open);
                    view_line.setVisibility(View.GONE);
                    ll_car_state.setVisibility(View.GONE);
                }else if (isClosed){
                    isClosed = false;
                    view_line.setVisibility(View.VISIBLE);
                    ll_car_state.setVisibility(View.VISIBLE);
                    iv_arrow.setImageResource(R.drawable.alarm_close);

                }

                break;
        }
    }

    /**
     * 获取手机当前位置
     */
    private void getPhonePos() {
        String[] perms = {
                Manifest.permission.ACCESS_FINE_LOCATION
                , Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CALL_PHONE};
        PermissionUtils.getInstance(new PermissionUtils.PermissionGrant() {
            @Override
            public void onPermissionGranted(Object permissionNameOrCode, boolean isSuccess) {

                LogUtil.i(permissionNameOrCode + "........." + isSuccess);
                switch (String.valueOf(permissionNameOrCode)) {
                    case Manifest.permission.ACCESS_FINE_LOCATION:
                        break;
                    case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                        if (isSuccess) {
                            FileUtil.createAllFile();
                        }
                        break;
                    case Manifest.permission.CALL_PHONE:
                        break;
                    case PermissionUtils.REQUEST_MULTIPLE_PERMISSION + "":
                        if (isSuccess) {
                            startLocation();
                            //                                } else {
                            //                                    DialogUtil.showMessageDg(GPSActivity.this, "必需权限", "没有该权限，此应用程序可能无法正常工作。打开应用设置界面以修改应用权限", "", "确定", null, new CustomDialog.OnDialogClickListener() {
                            //                                        @Override
                            //                                        public void onClick(CustomDialog dialog, int id, Object object) {
                            //                                            dialog.dismiss();
                            //                                        }
                            //                                    });
                        }
                        break;
                    default:
                        break;
                }
            }
        }).requestPermission(OutFenceAlarmActivity.this, perms);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 标题栏初始化
     */
    private void initTitle() {
        titleView.setLeftBtnImg();
        titleView.setTitle(R.string.out_fence_detail);
    }

    private void startLocation() {
        GPS3DUtils.getInstance().startLocation(this, new GPS3DUtils.LocationListener() {
            @Override
            public void onCallback(double latitude, double longitude, String address) {
                /** 获取当前手机定位 */
                phone_lat = latitude;
                phone_lon = longitude;

                GPS3DUtils.getInstance().upMarker2D(mAMap, R.drawable.center_point, latitude, longitude);

            }

            @Override
            public void onErr() {
                ToastUtil.showToast(OutFenceAlarmActivity.this, "定位失败，请重新定位！");
            }
        });
    }

    /**
     * 车当前位置
     *
     * @param latitude
     * @param longitude
     */
    private void showMarker(double latitude, double longitude) {
        Marker endMarker = GPS3DUtils.getInstance().upMarker2D(mAMap, R.drawable.green_car, latitude, longitude);
        endMarker.showInfoWindow();
    }

}
