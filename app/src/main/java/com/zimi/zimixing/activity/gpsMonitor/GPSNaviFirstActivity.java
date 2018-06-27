package com.zimi.zimixing.activity.gpsMonitor;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;

import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.zimi.zimixing.R;
import com.zimi.zimixing.activity.MainActivity;
import com.zimi.zimixing.activity.gpsMonitor.old.GPSNaviActivity;
import com.zimi.zimixing.activity.gpsMonitor.old.RecordShowActivity;
import com.zimi.zimixing.activity.gpsMonitor.old.amapUtils.GPS3DUtils;
import com.zimi.zimixing.bean.GpsListBean;
import com.zimi.zimixing.bean.ResponseBean;
import com.zimi.zimixing.biz.GpsMonitorBiz;
import com.zimi.zimixing.executor.BaseTask;
import com.zimi.zimixing.executor.RequestExecutor;
import com.zimi.zimixing.utils.AppManagerUtil;
import com.zimi.zimixing.utils.FileUtil;
import com.zimi.zimixing.utils.IntentUtil;
import com.zimi.zimixing.utils.LogUtil;
import com.zimi.zimixing.utils.MapUtil;
import com.zimi.zimixing.utils.PermissionUtils;
import com.zimi.zimixing.utils.ToastUtil;
import com.zimi.zimixing.utils.dialog.CustomDialog;
import com.zimi.zimixing.utils.dialog.DialogUtil;
import com.zimi.zimixing.widget.TitleViewPurple;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * created by tanjun
 * 从登录直接进来的
 * 进入导航界面
 */
public class GPSNaviFirstActivity extends FragmentActivity implements AMap.InfoWindowAdapter, GeocodeSearch.OnGeocodeSearchListener, ActivityCompat.OnRequestPermissionsResultCallback {

    /** 标题栏 */
    @BindView(R.id.title_view)
    public TitleViewPurple title_view;

    /** 地图 */
    @BindView(R.id.map)
    public MapView mMapView2D;

    /** 侧滑 */
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    private AMap aMap;

    /** 交通变化 */
    @BindView(R.id.gps_traffic_conditions)
    CheckBox trafficConditions;

    /** 报警信息 */
    @BindView(R.id.gps_alarm_map)
    ImageView alarmMap;

    /**
     * 计算两点的距离
     */
    @BindView(R.id.gps_distance)
    View gps_distance;

    @BindView(R.id.gps_ll)
    LinearLayout ll;

    @BindView(R.id.gps_refresh)
    TextView gps_refresh;

    /**
     * 记录退出按下时间
     */
    private long exitTime = 0;

    private TextView snippetUi;
    private TextView snippetUiCall;

    private View infoContent;
    private PopupWindow mPop;
    private GpsListBean bean;
    //private String imeiId = "863666010920993"; //用这个账号假数据
    private String imeiId = "14530708116"; //用这个账号假数据
    private boolean isCallPolice;
    private int type;

    String formatAddress;
    Marker phoneMarker = null; //手机标记点
    Marker carMarker = null; //汽车标记点
    Marker endMarker = null; //结束标记点
    Marker startMarker = null; //开始标记点

    /**
     * 汽车的经纬度
     */
    private double car_lat;
    private double car_lon;

    /**
     * 当前手机经纬度
     */
    private double phone_lat;
    private double phone_lon;

    /**
     * 经纬度列表
     */
    ArrayList<LatLng> latLngs = new ArrayList<LatLng>();

    private String direction = "0"; //后台传过来车的方向

    private Timer timer = new Timer();
    private TimerTask task;
    private int countDown = 29;

    /**
     * 用来判断布局
     */
    boolean isVisi = false;

    /**
     * 画线 默认未画线
     */
    boolean isDrawed = false;
    /**
     * 是否已展开 默认打开
     */
    boolean isOpen = true;
    Polyline line;

    RelativeLayout ll_distance;
    LinearLayout ll_info;
    RelativeLayout rl_open_close;
    LinearLayout ll_location_info;
    /**
     * 距离
     */
    String result;

    double dis; //两点之间的距离

    /** 左上角定时刷新 */
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                if (null != gps_refresh) {
                    gps_refresh.setText(countDown + "s");
                    if (countDown == 0) {
                        countDown = 30;
                        //p.getLocationInfo(imeiId);
                        getLocationInfo();
                    } else {
                        countDown--;
                    }
                }
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 添加Activity到堆栈
        AppManagerUtil.getAppManager().addActivity(this);
        setContentView(R.layout.activity_navi_main);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        initAllMembersView(savedInstanceState);
    }

    protected void initAllMembersView(Bundle savedInstanceState) {
        title_view.setTitle("紫米星");
        //title_view.setLeftBtnImg();
        title_view.setLeftBtnImg(R.drawable.head_left, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(GPSNaviFirstActivity.this, LocationSettingActivity.class);
                intent.putExtra("imeiId", imeiId);
                intent.putExtra("type", getIntent().getIntExtra("type", -1));
                startActivity(intent);*/
                showLeft();
            }
        });

        if (isCallPolice) {
            ll.setVisibility(View.GONE);
        }
        startLocation(false, false);
        getLocationInfo();

        mMapView2D.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mMapView2D.getMap();
        }
        aMap.setInfoWindowAdapter(this);
        GPS3DUtils.getInstance().setUiSettings(aMap);
        //GPS3DUtils.getInstance().addMarkersToMap(aMap, R.drawable.location_query_icon_car_driving, 22.547634, 114.072214);
        //showMarker();

        //fl.addView(mMapView2D, 0, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));

        trafficConditions.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                GPS3DUtils.getInstance().setTraffic(aMap, isChecked);
            }
        });

    }

    @Override
    protected void onDestroy() {
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView2D.onDestroy();
        GPS3DUtils.getInstance().onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView2D.onResume();
        task = new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = 1;
                message.arg1 = countDown;
                mHandler.sendMessage(message);
            }
        };
        if (timer == null) {
            timer = new Timer();
        }
        timer.schedule(task, 1000, 1000);
    }


    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView2D.onPause();
        timer.cancel();
        timer = null;
        task = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView2D.onSaveInstanceState(outState);
    }

    @OnClick({R.id.gps_alarm_map, R.id.gps_traffic_conditions, R.id.gps_vehicle_position,
            R.id.gps_mobile_phone_position, R.id.gps_bg_right_1, R.id.gps_distance,
            R.id.gps_bg_right_2, R.id.iv_gps_guide, R.id.gps_play_back, R.id.gps_call_police})
    public void onClivk(View v) {
        switch (v.getId()) {

            case R.id.gps_call_police: //报警信息
                IntentUtil.gotoActivity(GPSNaviFirstActivity.this, AlarmInfoActivity.class);
                break;

            case R.id.gps_distance: //计算直线距离
                LatLng phoneLatLon = new LatLng(phone_lat, phone_lon);
                LatLng carLatLon = new LatLng(car_lat, car_lon);

                float distance = AMapUtils.calculateLineDistance(phoneLatLon, carLatLon);

                if ((int) distance > 1000) {
                    dis = distance / 1000.0;
                    result = String.format("%.2f", dis) + "KM";
                } else {
                    dis = distance;
                    result = String.format("%.2f", dis) + "M";
                }

                /**
                 * 第一次进来 点击则画线 第二次删除线
                 */
                if (!isDrawed) {
                    isDrawed = true;
                    isVisi = true;
                    PolylineOptions polt = new PolylineOptions();
                    polt.add(phoneLatLon);
                    polt.add(carLatLon);
                    polt.width(10).geodesic(true).color(Color.GREEN);

                    showMarker(car_lat, car_lon, true);

                    line = aMap.addPolyline(polt);
                    aMap.moveCamera(CameraUpdateFactory.zoomTo(10));

                    /*ll_distance.setVisibility(View.VISIBLE);
                    ll_info.setVisibility(View.GONE);*/

                    if (ll_distance != null && ll_info != null){
                        ll_distance.setVisibility(View.VISIBLE);
                        ll_info.setVisibility(View.GONE);
                    }

                } else if (isDrawed) {
                    endMarker.remove();
                    startMarker.remove();
                    isDrawed = false;
                    isVisi = false;
                    line.remove();
                    if (carMarker != null){
                        showMarker(car_lat, car_lon, false);
                    }


                    if (ll_distance != null && ll_info != null){
                        ll_distance.setVisibility(View.GONE);
                        ll_info.setVisibility(View.VISIBLE);
                    }

                }

                break;

            case R.id.gps_alarm_map:
                showSeletMapType();
                break;

            case R.id.gps_traffic_conditions:
                break;
            case R.id.gps_mobile_phone_position: //手机当前位置

                //                startLocation();
                // 权限数组
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
                                    startLocation(true, false);
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
                }).requestPermission(GPSNaviFirstActivity.this, perms);
                break;
            case R.id.gps_vehicle_position://车当前位置
                if (carMarker != null){
                    showMarker(car_lat, car_lon, false);
                }

                break;
            case R.id.gps_bg_right_1://缩小
                GPS3DUtils.getInstance().setZoomIn(aMap);
                break;
            case R.id.gps_bg_right_2://放大
                GPS3DUtils.getInstance().setZoomOut(aMap);
                break;
            case R.id.gps_play_back: //回放
                Intent intent2 = new Intent(this, RecordShowActivity.class);
                if (bean != null){
                    intent2.putExtra("imeiId", imeiId);
                    intent2.putExtra("type", type);
                    intent2.putExtra("name", bean.getName());
                    intent2.putExtra("color", bean.getColor());
                    intent2.putExtra("numberPlates", bean.getCarNumber());
                    intent2.putExtra("car_lat", car_lat);
                    intent2.putExtra("car_lon", car_lon);
                    startActivity(intent2);
                }

                break;
            case R.id.iv_gps_guide: //导航
                /*Intent intent = new Intent(this, GPSNaviActivity.class);
                intent.putExtra("STARTLATLNG_LATITUDE", car_lat);
                intent.putExtra("STARTLATLNG_LONGITUDE", car_lon);
                startActivity(intent);*/
                showChoseDialog();
                break;

        }
    }

    /***
     * 用来控制是否定位到当前位置
     * @param isStart
     */
    private void startLocation(final boolean isStart, final boolean isStartpos) {
        GPS3DUtils.getInstance().startLocation(this, new GPS3DUtils.LocationListener() {
            @Override
            public void onCallback(double latitude, double longitude, String address) {
                /** 获取当前手机定位 */
                phone_lat = latitude;
                phone_lon = longitude;
                if (phoneMarker != null && phoneMarker.isVisible()){
                    phoneMarker.remove();
                }
                phoneMarker = GPS3DUtils.getInstance().upMarker(aMap, R.drawable.cur_pos, latitude, longitude);

            }

            @Override
            public void onErr() {
                ToastUtil.showToast(GPSNaviFirstActivity.this, "定位失败，请重新定位！");
            }
        });
    }

    private void showMarker(double latitude, double longitude, boolean isEndPos) {
        if (!isEndPos) {
            carMarker = GPS3DUtils.getInstance().upMarker(aMap, getCarColor(bean.getColor()), latitude, longitude);
            carMarker.setRotateAngle(Float.parseFloat(String.valueOf(360 - Integer.parseInt(direction)))); //设置车的角度
            carMarker.showInfoWindow();
        } else if (isEndPos) {
            startMarker = aMap.addMarker(new MarkerOptions().position(new LatLng(phone_lat, phone_lon))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.start_pos)));

            endMarker = aMap.addMarker(new MarkerOptions().position(new LatLng(car_lat, car_lon))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.end_pos)));

            endMarker.showInfoWindow();

        }
    }

    private void showSeletMapType() {
        if (mPop == null) {
            View contentView = LayoutInflater.from(this).inflate(R.layout.map_type, null, false);
            contentView.findViewById(R.id.map_type_1).setOnClickListener(mapTypeListener);
            contentView.findViewById(R.id.map_type_2).setOnClickListener(mapTypeListener);
            contentView.findViewById(R.id.map_type_3).setOnClickListener(mapTypeListener);
            contentView.findViewById(R.id.map_type_4).setOnClickListener(mapTypeListener);
            mPop = new PopupWindow(contentView, 100, ViewGroup.LayoutParams.WRAP_CONTENT, true);
            mPop.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mPop.setOutsideTouchable(true);
            mPop.setTouchable(true);
        }
        if (mPop.isShowing()) {
            mPop.dismiss();
        }
        // 第一个参数是PopupWindow的锚点，第二和第三个参数分别是PopupWindow相对锚点的x、y偏移
        mPop.showAsDropDown(alarmMap, -alarmMap.getMeasuredWidth() - 50, -alarmMap.getMeasuredHeight() - 50);
    }

    private View.OnClickListener mapTypeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int type = -1;
            switch (v.getId()) {
                case R.id.map_type_1:
                    type = 1;
                    break;
                case R.id.map_type_2:
                    type = 2;
                    break;
                case R.id.map_type_3:
                    type = 3;
                    break;
                case R.id.map_type_4:
                    type = 4;
                    break;
                default:
                    break;
            }
            GPS3DUtils.getInstance().setMapType(aMap, type);
            if (mPop != null) {
                mPop.dismiss();
            }
        }
    };

    @Override
    public View getInfoWindow(Marker marker) {
        if (!isCallPolice) {
            infoContent = getLayoutInflater().inflate(R.layout.map_infowindow_purple, null);
            WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
            int width = wm.getDefaultDisplay().getWidth();
            snippetUi = ((TextView) infoContent.findViewById(R.id.map_info_address));
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width / 2 + 150, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(20, 0, 0, 0);
            snippetUi.setLayoutParams(layoutParams);

            /*infoContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(GPSNaviFirstActivity.this, DeviceInfoActivity.class);
                    intent.putExtra("name", bean.getName());
                    intent.putExtra("carNum", bean.getCarNumber());
                    intent.putExtra("type", bean.getType());
                    intent.putExtra("model", bean.getVersionType());
                    intent.putExtra("imei", bean.getImeiId());
                    intent.putExtra("isim", bean.getSimId());
                    intent.putExtra("startTime", bean.getInstallDate());
                    intent.putExtra("stopTime", bean.getServiceDate());
                    intent.putExtra("phone", bean.getPhoneNum());
                    GPSNaviFirstActivity.this.startActivity(intent);
                }
            });*/



            /** 用来控制打开 收起 */
            rl_open_close = (RelativeLayout) infoContent.findViewById(R.id.rl_open_close);
            /** 隐藏距离按钮 */
            ImageView ib = (ImageView) infoContent.findViewById(R.id.btn_close);
            final ImageView iv_arrow = (ImageView) infoContent.findViewById(R.id.iv_arrow);
            /** 显示距离 */
            TextView tv_distance = (TextView) infoContent.findViewById(R.id.tv_distance);
            ll_distance = (RelativeLayout) infoContent.findViewById(R.id.ll_distance);
            ll_info = (LinearLayout) infoContent.findViewById(R.id.ll_info);
            ll_location_info = (LinearLayout) infoContent.findViewById(R.id.ll_location_info);

            if (isVisi){
                ll_distance.setVisibility(View.VISIBLE);
                ll_info.setVisibility(View.GONE);
            }else {
                ll_distance.setVisibility(View.GONE);
                ll_info.setVisibility(View.VISIBLE);
            }

            if (!TextUtils.isEmpty(result)) {
                tv_distance.setText(result);
            }

            /** 收起 展开点击 */
            rl_open_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isOpen) {
                        isOpen = false;
                        iv_arrow.setImageResource(R.drawable.arrow_open);
                        ll_location_info.setVisibility(View.GONE);
                    } else if (!isOpen) {
                        isOpen = true;
                        ll_location_info.setVisibility(View.VISIBLE);
                        iv_arrow.setImageResource(R.drawable.arrow_close);
                    }
                }
            });

            /**
             * 点击关闭距离按钮
             */
            ib.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isDrawed = false;
                    endMarker.remove();
                    startMarker.remove();
                    ll_distance.setVisibility(View.GONE);
                    ll_info.setVisibility(View.VISIBLE);
                    line.remove();
                }
            });

            if (bean != null) {
                GPS3DUtils.getInstance().getAddressByLatlng(this, new LatLng(car_lat, car_lon), this);

                ((TextView) infoContent.findViewById(R.id.map_info_name)).setText(bean.getName());
                ((TextView) infoContent.findViewById(R.id.map_info_vehicle_num)).setText(bean.getCarNumber());
                //                ((TextView) infoContent.findViewById(R.id.map_info_city)).setText(orgName);
                ImageView imageView = ((ImageView) infoContent.findViewById(R.id.map_info_icon));
                if (type == 0) {
                    imageView.setImageResource(R.drawable.vehicle_icon_wired);
                } else {
                    imageView.setImageResource(R.drawable.alarm_info_icon_wireless);
                }
                ((TextView) infoContent.findViewById(R.id.map_info_state)).setText("【" + bean.getStatus() + "】");
                if (getColor(bean.getColor()) != -1) {
                    ((TextView) infoContent.findViewById(R.id.map_info_state)).setTextColor(getColor(bean.getColor()));
                }

                ((TextView) infoContent.findViewById(R.id.map_info_location)).setText("定位时间：" + bean.getLocationDate());
                ((TextView) infoContent.findViewById(R.id.map_info_receive)).setText("接收时间：" + bean.getReceiveDate());
                ((TextView) infoContent.findViewById(R.id.map_info_speed)).setText("速度：" + bean.getSpeed() + "KM/H");
                String x = "行驶";
                if (!bean.getAccState().equals("1")) {
                    x = "静止";
                }
                ((TextView) infoContent.findViewById(R.id.map_info_travel_time)).setText(x + bean.getStateLength());
                ((TextView) infoContent.findViewById(R.id.map_info_location_type)).setText("定位：GPS");
                //((TextView) infoContent.findViewById(R.id.map_info_acc)).setText("ACC：" + (bean.getAccState().equals("1") ? "开" : "关"));

            }

        } else {
            infoContent = getLayoutInflater().inflate(
                    R.layout.map_infowindow_call, null);
            WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
            int width = wm.getDefaultDisplay().getWidth();
            snippetUiCall = ((TextView) infoContent.findViewById(R.id.map_info_call_address));
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width / 2 + 150, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(20, 0, 0, 0);
            snippetUiCall.setLayoutParams(layoutParams);

            if (bean != null) {

                GPS3DUtils.getInstance().getAddressByLatlng(this, new LatLng(car_lat, car_lon), this);

                ((TextView) infoContent.findViewById(R.id.map_info_call_name)).setText(bean.getName());
                ((TextView) infoContent.findViewById(R.id.map_info_call_vehicle_num)).setText(bean.getCarNumber());
                ImageView imageView = (ImageView) infoContent.findViewById(R.id.map_info_call_icon);
                if (type == 0) { //0是有线
                    imageView.setImageResource(R.drawable.vehicle_icon_wired);
                } else if (type == 1) { //1是无线
                    imageView.setImageResource(R.drawable.alarm_info_icon_wireless);
                } else if (type == 2) { //2是odb
                    imageView.setImageResource(R.drawable.odb_icon);
                }
                ((TextView) infoContent.findViewById(R.id.map_info_call_state_t)).setText(bean.getStatus());
                ((TextView) infoContent.findViewById(R.id.map_info_call_state)).setText("【" + bean.getStatus() + "】");
                String str = bean.getLocationDate() + " " + bean.getSpeed() + "KM/H";
                ((TextView) infoContent.findViewById(R.id.map_info_call_location)).setText(str);
            }
        }
        return infoContent;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult result, int rCode) {

        if (result != null) {
            formatAddress = result.getRegeocodeAddress().getFormatAddress();
            if (!isCallPolice) {
                snippetUi.setText("地址:" + formatAddress);
            } else {
                snippetUiCall.setText("地址:" + formatAddress);
            }
        }
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }

    private int getCarColor(String color) {
        int c = R.drawable.green_car;
        if ("0".equals(color)) {
            c = R.drawable.gray_car;
        } else if ("1".equals(color)) {
            c = R.drawable.red_car;
        } else if ("2".equals(color)) {
            c = R.drawable.green_car;
        } else if ("3".equals(color)) {
            c = R.drawable.yellow_car;
        } else if ("4".equals(color)) {
            c = R.drawable.blue_car;
        }
        return c;
    }

    @TargetApi(Build.VERSION_CODES.M)
    private int getColor(String color) {
        int c = -1;
        if ("0".equals(color)) {
            c = Color.GRAY;
        } else if ("1".equals(color)) {
            c = Color.RED;
        } else if ("2".equals(color)) {
            c = Color.GREEN;
        } else if ("3".equals(color)) {
            //c = Color.YELLOW;
            c = getColor(R.color.yellow);
        } else if ("4".equals(color)) {
            c = Color.BLUE;
        }
        return c;
    }

    private void getLocationInfo() {
        RequestExecutor.addTask(new BaseTask(GPSNaviFirstActivity.this) {
            @Override
            public ResponseBean sendRequest() {
                return GpsMonitorBiz.getLocationInfo(imeiId);
            }

            @Override
            public void onSuccess(ResponseBean result) {
                if (startMarker != null){
                    startMarker.remove();
                }

                if (endMarker != null){
                    endMarker.remove();
                }

                isVisi = false;

                ArrayList<GpsListBean> arrayList = (ArrayList<GpsListBean>) result.getObject();

                if (arrayList != null && arrayList.size() > 0) {
                    car_lat = arrayList.get(0).getLatitude();
                    car_lon = arrayList.get(0).getLongitude();
                    bean = arrayList.get(0);
                    direction = bean.getDirection();
                    imeiId = bean.getImeiId();
                    type = bean.getType();
                    if (car_lat > 0 && car_lon > 0) {
                        showMarker(car_lat, car_lon, false);
                    } else {
                        ToastUtil.showToast(GPSNaviFirstActivity.this, "获取不到数据！");
                        //finish();
                    }
                } else {
                    ToastUtil.showToast(GPSNaviFirstActivity.this, "定位数据为空！");
                }
            }

            @Override
            public void onFail(ResponseBean result) {
                ToastUtil.showToast(GPSNaviFirstActivity.this, result.getInfo());
            }
        });
    }


    /**
     * 回调获取授权结果，判断是否授权
     * 0 =PackageManager.PERMISSION_GRANTED 权限开启成功
     * -1=PackageManager.PERMISSION_DENIED  权限开启失败
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (permissions.length <= 0 || grantResults.length <= 0) {
            return;
        }
        PermissionUtils.getInstance().requestPermissionsResult(permissions, grantResults, requestCode);
    }

    /**
     * 控制侧滑
     */
    private void showLeft() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            drawer.openDrawer(GravityCompat.START);
        }
    }

    /**
     * 选择百度 高德 对话框
     */
    public void showChoseDialog() {
        DialogUtil.showIosDialogPurple(this, null, getResources().getStringArray(R.array.map_selection), Gravity.BOTTOM, null, new CustomDialog.OnDialogClickListener() {

            @Override
            public void onClick(CustomDialog dialog, int id, Object object) {
                dialog.dismiss();
                switch (id) {
                    case 1:// 高德
                        if (MapUtil.isGdMapInstalled(GPSNaviFirstActivity.this)) {
                            MapUtil.goToGaode(GPSNaviFirstActivity.this, car_lat + "", car_lon + "", formatAddress);
                        } else {
                            ToastUtil.showToast(GPSNaviFirstActivity.this, "您的手机没有安装高德地图！");
                        }

                        /*Intent intent = new Intent(GPSNaviFirstActivity.this, GPSNaviActivity.class);
                        intent.putExtra("STARTLATLNG_LATITUDE", car_lat);
                        intent.putExtra("STARTLATLNG_LONGITUDE", car_lon);
                        startActivity(intent);*/

                        break;
                    case 0:// 百度
                        MapUtil.goToBaidu(GPSNaviFirstActivity.this, car_lat + "", car_lon + "", formatAddress);
                        break;
                }
            }
        });
    }

    // ***********************************返回键事件处理*********************************
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else if ((System.currentTimeMillis() - exitTime) > 2000) {
                    ToastUtil.showToast(GPSNaviFirstActivity.this, getString(R.string.tips_exit_time));
                    exitTime = System.currentTimeMillis();
                } else {
                    AppManagerUtil.getAppManager().exitApp();
                }

            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }


}
