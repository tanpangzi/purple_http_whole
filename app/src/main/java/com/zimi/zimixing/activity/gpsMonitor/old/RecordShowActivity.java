package com.zimi.zimixing.activity.gpsMonitor.old;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.zimi.zimixing.R;
import com.zimi.zimixing.activity.gpsMonitor.old.amapUtils.GPS3DUtils;
import com.zimi.zimixing.activity.gpsMonitor.old.amapUtils.TraceRePlay;
import com.zimi.zimixing.bean.GpsRecordShowtBean;
import com.zimi.zimixing.bean.ResponseBean;
import com.zimi.zimixing.biz.GpsMonitorBiz;
import com.zimi.zimixing.executor.BaseTask;
import com.zimi.zimixing.executor.RequestExecutor;
import com.zimi.zimixing.interf.OnDialogViewCallBack;
import com.zimi.zimixing.utils.AppManagerUtil;
import com.zimi.zimixing.utils.FileUtil;
import com.zimi.zimixing.utils.LogUtil;
import com.zimi.zimixing.utils.PermissionUtils;
import com.zimi.zimixing.utils.ScreenUtil;
import com.zimi.zimixing.utils.ToastUtil;
import com.zimi.zimixing.utils.dialog.CustomDialog;
import com.zimi.zimixing.view.dialog.TimeWheelView4Dialog;
import com.zimi.zimixing.widget.PlayView;
import com.zimi.zimixing.widget.TitleViewPurple;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecordShowActivity extends FragmentActivity implements
        AMap.InfoWindowAdapter, GeocodeSearch.OnGeocodeSearchListener {

    private final static int AMAP_LOADED = 2;

    private int fastForward = 2000;
    private AMap mAMap;
    private Marker mOriginStartMarker, mOriginEndMarker, mOriginRoleMarker;
    private Polyline mOriginPolyline;

    private List<LatLng> mOriginLatLngList = new ArrayList<>();

    private ExecutorService mThreadPool;
    private TraceRePlay mRePlay;
    private String name;
    private String numberPlates;
    private String color;

    private String direction = "0";

    @BindView(R.id.title_view)
    public TitleViewPurple title_view;

    @BindView(R.id.map)
    MapView mMapView;

    @BindView(R.id.seekbar)
    SeekBar seekbar;

    @BindView(R.id.gps_alarm_map)
    ImageView alarmMap;

    @BindView(R.id.record_ll_custom)
    LinearLayout record_ll_custom;

    @BindView(R.id.custom_satr_time)
    TextView custom_satr_time;

    @BindView(R.id.custom_stop_time)
    TextView custom_stop_time;

    @BindView(R.id.record_fast_forward)
    TextView record_fast_forward;

    @BindView(R.id.playview)
    PlayView playview;

    @BindView(R.id.search_ll)
    LinearLayout search_ll;

    /** 播放 */
    @BindView(R.id.iv_play)
    ImageView iv_play;

    /**
     * 右侧滑
     */
    @BindView(R.id.drawer_layout)
    public DrawerLayout drawer;

    /**
     * 当前位置
     */
    @BindView(R.id.gps_mobile_phone_position)
    ImageView gps_mobile_phone_position;

    @BindView(R.id.gps_car_position)
    ImageView gps_car_position;

    private String imeiId;
    private int type;
    private PopupWindow mPop;

    private TextView locationTV, speedTV;

    private static int CUSTOM_TAG = 0;
    private final static int SATR_TIME = 1;
    private final static int STOP_TIME = 2;
    private int mFastForward = 1;
    private boolean isPlay = true;
    private double nLenStart = 0;
    private boolean isZoom = false;

    private double lastLat;
    private double lastLon;

    private double carLat;
    private double carLon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 添加Activity到堆栈
        AppManagerUtil.getAppManager().addActivity(this);
        setContentView(R.layout.activity_record_main);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        initAllMembersView(savedInstanceState);
    }

    protected void initAllMembersView(Bundle savedInstanceState) {

//        setIL(R.drawable.back);
//        isShowSpoy(false);
//        isShowTR(false);
//        isShowIR(false);
        title_view.setTitle("轨迹回放");
        title_view.setLeftBtnImg();
        imeiId = getIntent().getStringExtra("imeiId");
        type = getIntent().getIntExtra("type", -1);
        name = getIntent().getStringExtra("name");
        color = getIntent().getStringExtra("color");
        numberPlates = getIntent().getStringExtra("numberPlates");
        lastLat = getIntent().getExtras().getDouble("lat");
        lastLon = getIntent().getExtras().getDouble("lon");
        //        p = new RecordShowP(this);

        //        mMapView = GPS3DUtils.getInstance().getMapView(this, 22.547634, 114.072214);
        mMapView.onCreate(savedInstanceState);// 此方法必须重写
        //        fl.addView(mMapView, 0, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        int threadPoolSize = Runtime.getRuntime().availableProcessors() * 2 + 2;
        mThreadPool = Executors.newFixedThreadPool(threadPoolSize);
        initMap();

        showMarker(carLat, carLon);
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, final int progress, final boolean fromUser) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Process.setThreadPriority(-1);
                        if (fromUser && mOriginRoleMarker != null) {
                            mOriginRoleMarker.setPositionNotUpdate(mOriginLatLngList.get(progress > 0 ? progress - 1 : 0));
                            mAMap.moveCamera(CameraUpdateFactory.changeLatLng(mOriginLatLngList.get(progress > 0 ? progress - 1 : 0)));
                        }
                    }
                });
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Process.setThreadPriority(-20);
                        if (mRePlay != null) {
                            mRePlay.stopTrace();
                        }

                    }
                });

            }

            @Override
            public void onStopTrackingTouch(final SeekBar seekBar) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Process.setThreadPriority(-20);
                        if (mRePlay != null) {
                            mRePlay.setI(seekBar.getProgress());
                            mRePlay.satrTrace();
                            mThreadPool.execute(mRePlay);
                        }

                    }
                });

            }
        });

        //initCustomTimePicker();
        //initWheelViewTime();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        //        int x = (int) event.getX();
        //        int y = (int) event.getY();
        //
        //        int top = search_ll.getTop() + getViewTop();
        //
        //        boolean b = x <= seekbar.getRight() && x >= seekbar.getLeft() && y <= seekbar.getBottom() + top + seekbar.getTop() && y >= seekbar.getTop() + top;
        //
        //        if (event.getAction() == MotionEvent.ACTION_DOWN && b) {
        //
        //        }
        //        System.out.print("===" + b);


        int nCnt = event.getPointerCount();

        int n = event.getAction();
        if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_POINTER_DOWN && 2 == nCnt) {

            for (int i = 0; i < nCnt; i++) {
                float x = event.getX(i);
                float y = event.getY(i);

                Point pt = new Point((int) x, (int) y);

            }

            int xlen = Math.abs((int) event.getX(0) - (int) event.getX(1));
            int ylen = Math.abs((int) event.getY(0) - (int) event.getY(1));

            nLenStart = Math.sqrt((double) xlen * xlen + (double) ylen * ylen);


        } else if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_POINTER_UP && 2 == nCnt) {

            for (int i = 0; i < nCnt; i++) {
                float x = event.getX(i);
                float y = event.getY(i);

                Point pt = new Point((int) x, (int) y);

            }

            int xlen = Math.abs((int) event.getX(0) - (int) event.getX(1));
            int ylen = Math.abs((int) event.getY(0) - (int) event.getY(1));

            double nLenEnd = Math.sqrt((double) xlen * xlen + (double) ylen * ylen);

            if (nLenEnd > nLenStart)//通过两个手指开始距离和结束距离，来判断放大缩小
            {
                isZoom = true;
            } else {
                isZoom = true;
            }
        }

        return super.dispatchTouchEvent(event);
    }


    private void initMap() {
        if (mAMap == null) {
            mAMap = mMapView.getMap();
            //            mAMap.setOnMapLoadedListener(this);
            mAMap.setInfoWindowAdapter(this);
            GPS3DUtils.getInstance().setUiSettings(mAMap);
        }
    }

    @OnClick({R.id.record_fast_forward, R.id.gps_alarm_map, R.id.gps_bg_right_1, R.id.gps_bg_right_2, R.id.custom
            , R.id.custom_satr_rl, R.id.custom_stop_rl, R.id.custom_confirm, R.id.custom_cancel,
            R.id.last_day, R.id.last_time, R.id.last_three_days, R.id.playview, R.id.gps_mobile_phone_position, R.id.gps_car_position})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.gps_car_position: //车当前位置
                showMarker(carLat, carLon);
                break;

            case R.id.gps_mobile_phone_position: //当前位置
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
                                    startLocation();
                                }
                                break;
                            default:
                                break;
                        }
                    }
                }).requestPermission(RecordShowActivity.this, perms);

                break;

            case R.id.record_fast_forward:
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Process.setThreadPriority(-20);
                        if (mRePlay != null) {
                            if (fastForward <= 2000 && fastForward > 1000) {
                                fastForward = fastForward - 500;
                                mFastForward++;
                            } else {
                                fastForward = 2000;
                                mFastForward = 1;
                            }
                            mRePlay.setIntervalMillisecond(fastForward);
                            record_fast_forward.setText("X " + mFastForward);
                        }
                    }
                });

                break;
            case R.id.gps_alarm_map:
                showSeletMapType();
                break;
            case R.id.gps_bg_right_1:
                isZoom = true;
                GPS3DUtils.getInstance().setZoomIn(mAMap);
                break;
            case R.id.gps_bg_right_2:
                isZoom = true;
                GPS3DUtils.getInstance().setZoomOut(mAMap);
                break;
            case R.id.custom:
                /*record_ll_custom.setVisibility(View.VISIBLE);
                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(0, ((LinearLayout) findViewById(R.id.record_ll)).getMeasuredHeight(), 0, 0);
                record_ll_custom.setLayoutParams(layoutParams);
                Date nowTime = new Date(System.currentTimeMillis());
                custom_satr_time.setText(getTime(nowTime));
                custom_stop_time.setText(getTime(nowTime));*/
                showRight();
                break;
            case R.id.custom_satr_rl:
                CUSTOM_TAG = SATR_TIME;
                //                if (pvCustomTime != null) {
                //                    pvCustomTime.show();
                //                }
                showWheelViewTime();
                break;
            case R.id.custom_stop_rl:
                CUSTOM_TAG = STOP_TIME;
                //                if (pvCustomTime != null) {
                //                    pvCustomTime.show();
                //                }
                showWheelViewTime();
                break;
            case R.id.custom_confirm:
                String satrTime = custom_satr_time.getText().toString();
                String stopTime = custom_stop_time.getText().toString();
                if (TextUtils.isEmpty(satrTime)) {
                    ToastUtil.showToast(RecordShowActivity.this, "开始时间不能为空");
                    break;
                }
                if (TextUtils.isEmpty(stopTime)) {
                    ToastUtil.showToast(RecordShowActivity.this, "结束时间不能为空");
                    break;
                }
                playBack(satrTime, stopTime);
                break;
            case R.id.custom_cancel:
                record_ll_custom.setVisibility(View.GONE);
                break;
            case R.id.last_time: //最近一次
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Process.setThreadPriority(-20);
                        if (mRePlay != null) {
                            mRePlay.stopTrace();
                        }
                    }
                });
                getTrackPlayback(imeiId, "Y", null, null, type, -1);
                break;
            case R.id.last_day:
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Process.setThreadPriority(-20);
                        if (mRePlay != null) {
                            mRePlay.stopTrace();
                        }
                    }
                });
                long time = System.currentTimeMillis();
                getTrackPlayback(imeiId, "N", getTime(time, 1), getTime(new Date(time)), type, 1);
                break;
            case R.id.last_three_days:
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Process.setThreadPriority(-20);
                        if (mRePlay != null) {
                            mRePlay.stopTrace();
                        }
                    }
                });
                long time2 = System.currentTimeMillis();
                getTrackPlayback(imeiId, "N", getTime(time2, 3), getTime(new Date(time2)), type, 3);
                break;

            case R.id.playview: //播放
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Process.setThreadPriority(-20);
                        if (mRePlay != null) {
                            if (isPlay) {
                                isPlay = false;
                                mRePlay.stopTrace();
                            } else {
                                isPlay = true;
                                mRePlay.setI(seekbar.getProgress() == seekbar.getMax() ? 0 : seekbar.getProgress());
                                mRePlay.satrTrace();
                                mThreadPool.execute(mRePlay);
                            }
                            playview.isPlay(isPlay);
                        }
                    }
                });

                break;

            case R.id.iv_play: //播放
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Process.setThreadPriority(-20);
                        if (mRePlay != null) {
                            if (isPlay) {
                                iv_play.setImageResource(R.drawable.play);
                                isPlay = false;
                                mRePlay.stopTrace();
                            } else {
                                iv_play.setImageResource(R.drawable.pause);
                                isPlay = true;
                                mRePlay.setI(seekbar.getProgress() == seekbar.getMax() ? 0 : seekbar.getProgress());
                                mRePlay.satrTrace();
                                mThreadPool.execute(mRePlay);
                            }
                            playview.isPlay(isPlay);
                        }
                    }
                });
                break;

            default:
                break;
        }
    }

    /**
     * 回放
     *
     * @param satrTime
     * @param stopTime
     */
    public void playBack(String satrTime, String stopTime) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Process.setThreadPriority(-20);
                if (mRePlay != null) {
                    mRePlay.stopTrace();
                }
            }
        });
        record_ll_custom.setVisibility(View.GONE);
        getTrackPlayback(imeiId, "N", satrTime, stopTime, type, -1);
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
            GPS3DUtils.getInstance().setMapType(mAMap, type);
            if (mPop != null) {
                mPop.dismiss();
            }
        }
    };

    //    @Override AMap.OnMapLoadedListener,
    //    public void onMapLoaded() {
    //        Message msg = handler.obtainMessage();
    //        msg.what = AMAP_LOADED;
    //        handler.sendMessage(msg);
    //    }

    @SuppressLint("HandlerLeak")
    public Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case AMAP_LOADED:
                    setupRecord();
                    break;
                default:
                    break;
            }
        }

    };


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mThreadPool != null) {
            mThreadPool.shutdownNow();
        }
        GPS3DUtils.getInstance().onDestroy();
    }

    private void startMove() {
        if (mRePlay != null) {
            mRePlay.stopTrace();
            SystemClock.sleep(200);
        }
        mRePlay = rePlayTrace(mOriginLatLngList, mOriginRoleMarker);
    }

    /**
     * 轨迹回放方法
     */
    private TraceRePlay rePlayTrace(List<LatLng> list, final Marker updateMarker) {
        TraceRePlay rePlay = new TraceRePlay(list, fastForward,
                new TraceRePlay.TraceRePlayListener() {

                    @Override
                    public void onTraceUpdating(int progress, LatLng latLng) {
                        if (mOriginRoleMarker != null && seekbar != null) {
                            seekbar.setProgress(progress);
                            //                            updateMarker.setPosition(latLng); // 更新小人实现轨迹回放
                            //                            mAMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));


                            mOriginRoleMarker.remove();
                            mAMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(
                                    latLng, isZoom ? mAMap.getCameraPosition().zoom : 19f, 30, 30)));
                            mOriginRoleMarker = mAMap.addMarker(new MarkerOptions().position(
                                    latLng).icon(
                                    BitmapDescriptorFactory.fromBitmap(BitmapFactory
                                            .decodeResource(getResources(), getCarColor(color)))));
                            mOriginRoleMarker.showInfoWindow();
                            mOriginRoleMarker.setRotateAngle(Float.parseFloat(String.valueOf(360 - Integer.parseInt(direction)))); //设置车的角度

                            //                            if(locationTV!=null) {
                            //                                GPS3DUtils.getInstance().getAddressByLatlng(RecordShowActivity.this, latLng, RecordShowActivity.this);
                            //                                locationTV.setText(gpsRecordShowtBeans.get(progress).getCreateDate() + "[定位]");
                            //                                speedTV.setText("速度:" + gpsRecordShowtBeans.get(progress).getSpeed() + "KM/H");
                            //                            }
                        }
                    }

                    @Override
                    public void onTraceUpdateFinish() {

                    }
                });
        mThreadPool.execute(rePlay);
        return rePlay;
    }


    /**
     * 轨迹数据初始化
     */
    private void setupRecord() {
        // 轨迹纠偏初始化
        //        LBSTraceClient mTraceClient = new LBSTraceClient(
        //                getApplicationContext());

        //        LatLng startLatLng = new LatLng(startLoc.getLatitude(),
        //                startLoc.getLongitude());
        //        LatLng endLatLng = new LatLng(endLoc.getLatitude(),
        //                endLoc.getLongitude());
        seekbar.setMax(mOriginLatLngList.size());
        addOriginTrace(mOriginLatLngList.get(0), mOriginLatLngList.get(mOriginLatLngList.size() - 1), mOriginLatLngList);

        //            List<TraceLocation> mGraspTraceLocationList = Util
        //                    .parseTraceLocationList(recordList);
        // 调用轨迹纠偏，将mGraspTraceLocationList进行轨迹纠偏处理
        //            mTraceClient.queryProcessedTrace(1, mGraspTraceLocationList,
        //                    LBSTraceClient.TYPE_AMAP, this);

        startMove();

    }


    /**
     * 地图上添加原始轨迹线路及起终点、轨迹动画小人
     *
     * @param startPoint
     * @param endPoint
     * @param originList
     */
    private void addOriginTrace(LatLng startPoint, LatLng endPoint,
                                List<LatLng> originList) {

        mAMap.clear();
        if (mOriginPolyline != null) {
            mOriginPolyline.remove();
        }
        if (mOriginStartMarker != null) {
            mOriginStartMarker.remove();
        }
        if (mOriginEndMarker != null) {
            mOriginEndMarker.remove();
        }
        if (mOriginRoleMarker != null) {
            mOriginRoleMarker.remove();
        }

        mOriginPolyline = mAMap.addPolyline(new PolylineOptions().color(
                Color.BLUE).addAll(originList));
        mOriginStartMarker = mAMap.addMarker(new MarkerOptions().position(
                startPoint).icon(BitmapDescriptorFactory.fromResource(R.drawable.start)));
        mOriginEndMarker = mAMap.addMarker(new MarkerOptions().position(
                endPoint).icon(BitmapDescriptorFactory.fromResource(R.drawable.end)));

        try {
            //            mAMap.moveCamera(CameraUpdateFactory.newLatLngBounds(getBounds(),
            //                    50));
            mAMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(
                    startPoint, 19f, 30, 30)));

        } catch (Exception e) {
            e.printStackTrace();
        }
        mOriginRoleMarker = mAMap.addMarker(new MarkerOptions().position(
                startPoint).icon(
                BitmapDescriptorFactory.fromBitmap(BitmapFactory
                        .decodeResource(getResources(), getCarColor(color)))));
        mOriginRoleMarker.showInfoWindow();
    }

    private TextView snippetUi;

    @Override
    public View getInfoWindow(Marker marker) {
        View infoContent = getLayoutInflater().inflate(
                R.layout.recor_show, null);

        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();

        ((TextView) infoContent.findViewById(R.id.map_info_name)).setText(name);
        ((TextView) infoContent.findViewById(R.id.map_info_vehicle_num)).setText(numberPlates);

        ImageView imageView = ((ImageView) infoContent.findViewById(R.id.map_info_icon));
        if (type == 0) {
            imageView.setImageResource(R.drawable.vehicle_icon_wired);
        } else {
            imageView.setImageResource(R.drawable.alarm_info_icon_wireless);
        }

        locationTV = ((TextView) infoContent.findViewById(R.id.map_info_location));
        locationTV.setText(gpsRecordShowtBeans.get(seekbar.getProgress()).getCreateDate() + "[定位]");
        speedTV = ((TextView) infoContent.findViewById(R.id.map_info_speed));
        speedTV.setText("速度:" + gpsRecordShowtBeans.get(seekbar.getProgress()).getSpeed() + "KM/H");

        direction = gpsRecordShowtBeans.get(seekbar.getProgress()).getDirection();//当前车的角度

        double lat = (gpsRecordShowtBeans.get(seekbar.getProgress()).getLatitude());
        double lon = (gpsRecordShowtBeans.get(seekbar.getProgress()).getLongitude());
        carLat = lat;
        carLon = lon;
        GPS3DUtils.getInstance().getAddressByLatlng(this, new LatLng(lat, lon), this);
        snippetUi = ((TextView) infoContent.findViewById(R.id.map_info_address));
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width / 2 + 150, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(20, 0, 0, 0);
        snippetUi.setLayoutParams(layoutParams);


        return infoContent;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }


    private String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return format.format(date);
    }

    private String getTime(long time, int data) {
        return getTime(new Date(time - (60 * 60 * 1000 * 24 * data)));

    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult result, int i) {
        if (result != null) {
            String formatAddress = result.getRegeocodeAddress().getFormatAddress();
            snippetUi.setText("地址:" + formatAddress);
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

    private void showMarker(double latitude, double longtitude) {
        Marker marker = GPS3DUtils.getInstance()
                .upMarker(mAMap, getCarColor(color), latitude, longtitude);
        marker.showInfoWindow();
    }

    ArrayList<GpsRecordShowtBean> gpsRecordShowtBeans = new ArrayList<>();

    public void getTrackPlayback(final String imeiId, final String first, final String startTime, final String endTime, final int type, final int day) {
        RequestExecutor.addTask(new BaseTask(RecordShowActivity.this) {
            @Override
            public ResponseBean sendRequest() {
                return GpsMonitorBiz.getTrackPlayback(imeiId, first, startTime, endTime, type, day);
            }

            @Override
            public void onSuccess(ResponseBean result) {
                ArrayList<GpsRecordShowtBean> arrayList = (ArrayList<GpsRecordShowtBean>) result.getObject();

                if (arrayList != null && arrayList.size() > 0) {
                    gpsRecordShowtBeans.clear();
                    gpsRecordShowtBeans.addAll(arrayList);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            Process.setThreadPriority(-20);
                            mOriginLatLngList.clear();
                            for (int i = 0; i < gpsRecordShowtBeans.size(); i++) {
                                GpsRecordShowtBean bean = gpsRecordShowtBeans.get(i);
                                double lat = (bean.getLatitude());
                                double lon = (bean.getLongitude());
                                //direction = bean.getDirection();
                                LatLng latLng = new LatLng(lat, lon);
                                mOriginLatLngList.add(latLng);
                            }

                            seekbar.setProgress(0);
                            if (!playview.getPlay()) {
                                playview.isPlay(true);
                            }
                            fastForward = 2000;

                            Message msg = handler.obtainMessage();
                            msg.what = AMAP_LOADED;
                            handler.sendMessage(msg);

                        }
                    }).start();
                } else {
                    ToastUtil.showToast(RecordShowActivity.this, "没有回放数据！");
                }
            }

            @Override
            public void onFail(ResponseBean result) {
                ToastUtil.showToast(RecordShowActivity.this, result.getInfo());
            }
        });
    }

    CustomDialog dialogTime;
    TimeWheelView4Dialog contentViewTme;

    /**
     * 合同模版选择器
     */
    private void initWheelViewTime() {
        if (dialogTime == null) {
            dialogTime = new CustomDialog(RecordShowActivity.this);
            contentViewTme = new TimeWheelView4Dialog(RecordShowActivity.this, dialogTime, new OnDialogViewCallBack() {
                @Override
                public void onResult(Map<String, Object> map) {
                    String date = (String) map.get("date");
                    LogUtil.i("----->>>>> " + date);
                    if (CUSTOM_TAG == SATR_TIME) {
                        custom_satr_time.setText((date));
                    } else if (CUSTOM_TAG == STOP_TIME) {
                        custom_stop_time.setText((date));
                    }
                }
            }, "");
            dialogTime.setContentView(contentViewTme);
        }
    }

    private void showWheelViewTime() {
        if (dialogTime == null) {
            return;
        }
        if (!dialogTime.isShowing()) {
            dialogTime.show();
            if (dialogTime.getWindow() != null) {
                WindowManager.LayoutParams lp = dialogTime.getWindow().getAttributes();
                lp.width = ScreenUtil.getScreenWidthPx(); // 设置宽度
                // v lp.height = ScreenUtil.dip2px(WriteBankInfoActivity.this,150); // 设置宽度
                dialogTime.getWindow().setGravity(Gravity.BOTTOM);
                dialogTime.getWindow().setAttributes(lp);
            }
        }
    }

    /**
     * 控制侧滑
     */
    private void showRight() {
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            drawer.openDrawer(GravityCompat.END);
        }
    }

    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else if (!drawer.isDrawerOpen(GravityCompat.END)) {
            finish();
        }
    }

    /***
     * 用来控制是否定位到当前位置
     */
    private void startLocation() {
        //        String[] perms = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION
        //                , Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE};
        //        if (EasyPermissions.hasPermissions(this, perms)) {
        // 已经有了许可，做这件事
        GPS3DUtils.getInstance().startLocation(this, new GPS3DUtils.LocationListener() {
            @Override
            public void onCallback(double latitude, double longitude, String address) {
                GPS3DUtils.getInstance().upMarker(mAMap, R.drawable.cur_pos, latitude, longitude);
            }

            @Override
            public void onErr() {
                ToastUtil.showToast(RecordShowActivity.this, "定位失败，请重新定位！");
            }
        });
        //        } else {
        //            // 没有权限，现在就请求它们
        //            EasyPermissions.requestPermissions(this, "请打开位置定位功能！",
        //                    RC_CAMERA_AND_LOCATION, perms);
        //        }
    }


}
