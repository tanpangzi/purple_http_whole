package com.zimi.zimixing.activity.zmxGpsInstall;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.zimi.zimixing.BaseApplication;
import com.zimi.zimixing.R;
import com.zimi.zimixing.activity.MainActivity;
import com.zimi.zimixing.activity.gpsMonitor.old.amapUtils.GPS3DUtils;
import com.zimi.zimixing.bean.ResponseBean;
import com.zimi.zimixing.bean.gps_install.GPSBean;
import com.zimi.zimixing.biz.InstallBiz;
import com.zimi.zimixing.executor.BaseTask;
import com.zimi.zimixing.executor.RequestExecutor;
import com.zimi.zimixing.utils.ComUtils;
import com.zimi.zimixing.utils.IntentUtil;
import com.zimi.zimixing.utils.LogUtil;
import com.zimi.zimixing.utils.PermissionUtils;
import com.zimi.zimixing.utils.ToastUtil;
import com.zimi.zimixing.utils.dialog.CustomDialog;
import com.zimi.zimixing.utils.dialog.DialogUtil;
import com.zimi.zimixing.widget.TitleView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tanjun on 2018/1/18.
 * gps安装 信号检测
 */

public class GPSInstallationTestActivity extends FragmentActivity
        implements View.OnClickListener, GeocodeSearch.OnGeocodeSearchListener {

    private final long TWO_MINUES = 1000* 2 * 60;//两分钟

    private TitleView title_view;
    private MapView mapView;
    private AMap aMap;
    private MyLocationStyle myLocationStyle;
    private ArrayList<Marker> listMarker = new ArrayList<>();

    private TextView tv_imei; //imeiId
    private TextView tv_receiveDate; //接收时间
    private TextView tv_locationDate; //定位时间
    private TextView tv_location_type;//定位方式 gps wifi gms
    private TextView tv_location;//位置

    private Button btn_refresh_position; //刷新位置
    private Button btn_remove;//我要拆除
    private Button btn_lock_oil_elec; //锁油断电

    private TextView tv_refresh_phone;//刷新手机位置(点击)
    private TextView tv_complete;//安装完成

    private ArrayList<GPSBean.ReturnListBean> listbean;
    //private String isCounterProduct; //1代表是COUNTER老产品 0代表不是COUNTER老产品   1是自动 0是人工
    private String imeiId;
    private String custId;
    private String simId;

    private String receiveDate; //接收时间
    private String locationDate; //定位时间

    private double lon;
    private double lat;

    public double curLon = 0;//当前lon
    public double curLat = 0;//当前lat

    private String location_type; //gps gms wifi
    private int type;//设备类型 0有线 1无线 2odb

    private String wayBillTitle; //气泡 标题
    Marker waybillMarker;

    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_installation_test);
        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);//必须重写
        findViews();
        initGetData();
        widgetListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    private void findViews() {
        title_view = (TitleView) findViewById(R.id.title_view);
        tv_imei = (TextView) findViewById(R.id.tv_imei);
        tv_receiveDate = (TextView) findViewById(R.id.tv_receiveDate);
        tv_locationDate = (TextView) findViewById(R.id.tv_locationDate);
        tv_location_type = (TextView) findViewById(R.id.tv_location_type);
        tv_location = (TextView) findViewById(R.id.tv_location);

        btn_refresh_position = (Button) findViewById(R.id.btn_refresh_position);
        btn_remove = (Button) findViewById(R.id.btn_remove);
        btn_lock_oil_elec = (Button) findViewById(R.id.btn_lock_oil_elec);
        tv_refresh_phone = (TextView) findViewById(R.id.tv_refresh_phone);
        tv_complete = (TextView) findViewById(R.id.tv_complete);

        title_view.setTitle(R.string.gps_signal_check);
        title_view.setLeftBtnImg(R.drawable.arrow_left_black, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endAndJump();
            }
        });

        initMap();
    }

    /**
     * 初始化地图
     */
    private void initMap() {
        myLocationStyle = new MyLocationStyle();
        myLocationStyle.showMyLocation(true);
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER);
        aMap = mapView.getMap();
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setMyLocationEnabled(true);
        aMap.moveCamera(CameraUpdateFactory.zoomTo(18));
    }

    private void initGetData() {
        Bundle bundle = getIntent().getExtras();
        userId = BaseApplication.getInstance().getUserInfoBean().getUserId();
        listbean = (ArrayList<GPSBean.ReturnListBean>) bundle.getSerializable("gpsList");

        imeiId = bundle.getString("imeiId");
        custId = bundle.getString("custId");
        simId = bundle.getString("simId");

        receiveDate = bundle.getString("receiveDate");
        locationDate = bundle.getString("locationDate");

        lon = bundle.getDouble("lon");
        lat = bundle.getDouble("lat");

        setData(0,listbean);// 0表示第一个数据

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    /**
     * 数据设置
     * pos表示在listBean中的位置
     * @param listbean
     */
    private void setData(int pos, ArrayList<GPSBean.ReturnListBean> listbean) {
        lat = Double.parseDouble(listbean.get(pos).getLatitude());
        lon = Double.parseDouble(listbean.get(pos).getLongitude());

        receiveDate = listbean.get(pos).getReceiveDate();
        locationDate = listbean.get(pos).getLocationDate();

        location_type = listbean.get(pos).getGpsOrGms(); //gps gms wifi 直接显示文字
        imeiId = listbean.get(pos).getImeiId();
        type = listbean.get(pos).getType();//设备类型

        wayBillTitle= imeiId.substring(imeiId.length() - 5, imeiId.length()); //imei 后5位

        if (type == 2){
            btn_lock_oil_elec.setVisibility(View.VISIBLE); //显示锁油断电
        }else {
            btn_lock_oil_elec.setVisibility(View.GONE);
        }

        tv_imei.setText(imeiId);
        tv_receiveDate.setText(receiveDate);
        tv_locationDate.setText(locationDate);
        tv_location_type.setText("[" + location_type.toUpperCase() + "]");//gps wifi
        setInfoWindow();
        startLocation(listbean, false);
        //GPS3DUtils.getInstance().getAddressByLatlng2D(this, new LatLng(lat, lon),this);
    }


    @Override
    public void onRegeocodeSearched(RegeocodeResult result, int i) {
        if (result != null) {
            String formatAddress = result.getRegeocodeAddress().getFormatAddress();
            tv_location.setText(formatAddress);
        } else {
            tv_location.setText("经纬度解析有误");
        }
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }

    /**
     * 设置infoWindow
     */
    private void setInfoWindow() {
        aMap.setInfoWindowAdapter(new AMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                View infoView = LayoutInflater.from(GPSInstallationTestActivity.this).inflate(
                        R.layout.custom_info_window, null);
                render(marker, infoView);
                return infoView;
            }

            @Override
            public View getInfoContents(Marker marker) {
                View infoView = LayoutInflater.from(GPSInstallationTestActivity.this).inflate(
                        R.layout.custom_info_window, null);
                render(marker, infoView);
                return infoView;
            }

            public void render(Marker marker, View view){
                String title = marker.getTitle();
                TextView tv_content = ((TextView) view.findViewById(R.id.tv_content));
                if (title != null) {
                    SpannableString titleText = new SpannableString(title);
                    tv_content.setTextSize(15);
                    tv_content.setText(titleText);
                } else {
                    tv_content.setText("");
                }
            }

        });
    }

    /**
     * 开启定位
     * @param returnListBeen
     */
    private void startLocation(final ArrayList<GPSBean.ReturnListBean> returnListBeen,final boolean isCompleted) {
        // 权限数组
        String[] REQUEST_PERMISSIONS = {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};

        PermissionUtils.getInstance(new PermissionUtils.PermissionGrant() {
            @Override
            public void onPermissionGranted(Object permissionName, boolean isSuccess) {
                LogUtil.i(permissionName + "........." + isSuccess);
                switch (String.valueOf(permissionName)){
                    case Manifest.permission.ACCESS_COARSE_LOCATION:
                    case Manifest.permission.ACCESS_FINE_LOCATION:
                    case Manifest.permission.READ_PHONE_STATE:
                    case Manifest.permission.READ_EXTERNAL_STORAGE:
                        GPS3DUtils.getInstance().startLocation(GPSInstallationTestActivity.this, new GPS3DUtils.LocationListener() {
                            @Override
                            public void onCallback(double latitude, double longitude, String address) {
                                curLat = latitude;
                                curLon = longitude;
                                initMap();
                                /**
                                 * 添加marker
                                 */
                                addMarkersToMap(returnListBeen);
                                if (isCompleted){
                                    compareAndComplete(latitude, longitude, returnListBeen);
                                }
                            }

                            @Override
                            public void onErr() {
                                ToastUtil.showToast(GPSInstallationTestActivity.this,"定位失败，请重新定位！");
                            }
                        });
                        break;

                }
            }
        }).requestPermission(GPSInstallationTestActivity.this, REQUEST_PERMISSIONS);
    }

    /**
     * 获取位置并比较
     * @param arrays
     */
    private void compareAndComplete(double phoneLat, double phoneLon, ArrayList<GPSBean.ReturnListBean> arrays) {
        for (int i = 0; i < arrays.size(); i++) {
            double lat2 = Double.parseDouble(arrays.get(i).getLatitude());
            double lon2 = Double.parseDouble(arrays.get(i).getLongitude());

            location_type = arrays.get(i).getGpsOrGms(); // gps gms wifi

            String receiveTime2 = arrays.get(i).getReceiveDate();
            String locationTime2 = arrays.get(i).getLocationDate();

            installComplete(userId, custId);//这是的1用来判断 是否是自动 1是自动 0是人工
        }
    }

    public void addMarkersToMap(ArrayList<GPSBean.ReturnListBean> markerList) {
        try {
            listMarker.clear();
            Drawable drawable = null;
            List<Marker> mapScreenMarkers = aMap.getMapScreenMarkers();
            for (int i = 0; i < mapScreenMarkers.size(); i++) {
                Marker marker = mapScreenMarkers.get(i);
                String title = marker.getTitle();
                if (title != null) {
                    marker.remove();
                }
            }
            mapView.invalidate();

            for (int i = 0; i < markerList.size(); i++) {
                GPSBean.ReturnListBean addCodeBean = markerList.get(i);
                double lat = Double.parseDouble(addCodeBean.getLatitude());
                double lng = Double.parseDouble(addCodeBean.getLongitude());
                locationDate = addCodeBean.getLocationDate();
                receiveDate = addCodeBean.getReceiveDate();

                String strImei = markerList.get(i).getImeiId();
                if ((int) lat == 0 || (int) lng == 0 || TextUtils.isEmpty(locationDate) || TextUtils.isEmpty(receiveDate)) {
                    lat = curLat + 0.0001 * (i + 1);
                    lng = curLon + 0.0001 * (i + 1);
                    drawable = getResources().getDrawable(R.drawable.red_car);
                } else {
                    drawable = getResources().getDrawable(
                            R.drawable.green_car);
                }
                type = markerList.get(i).getType(); //设备类型 有线0  无线1  odb 2

                Bitmap bitmap = ComUtils.convertDrawable2BitmapByCanvas(drawable);
                LatLng latlng = new LatLng(lat, lng);

                GPS3DUtils.getInstance().getAddressByLatlng2D(this, new LatLng(lat, lng),this);

                //LatLng latLng = new LatLng(lat, lon);
                aMap.moveCamera(CameraUpdateFactory.changeLatLng(latlng));

                waybillMarker = aMap.addMarker(new MarkerOptions()
                        .position(latlng)
                        .title("")
                        .icon(BitmapDescriptorFactory.fromBitmap(bitmap))
                        .draggable(true));//添加
                waybillMarker = aMap.addMarker(new MarkerOptions()
                        .position(latlng)
                        .title("")
                        .icon(BitmapDescriptorFactory.fromBitmap(bitmap)));

                strImei = strImei.substring(strImei.length() - 5, strImei.length());
                waybillMarker.setRotateAngle(0);// 设置marker旋转
                waybillMarker.setTitle(strImei);
                waybillMarker.setSnippet(lng + "");
                waybillMarker.setObject(latlng);
                listMarker.add(waybillMarker);
            }
        } catch (Exception e) {
            LogUtil.e(e.toString());
        }

    }

    private void widgetListener() {
        btn_refresh_position.setOnClickListener(this);
        btn_remove.setOnClickListener(this);
        btn_lock_oil_elec.setOnClickListener(this);
        tv_refresh_phone.setOnClickListener(this);
        tv_complete.setOnClickListener(this);

        /**
         * 地图 marker点击事件
         */
        aMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                wayBillTitle = marker.getTitle();
                if (TextUtils.isEmpty(wayBillTitle)){
                    return false;
                }else {
                    getLocationInfo(wayBillTitle,custId,false);
                    ToastUtil.showToast(GPSInstallationTestActivity.this, wayBillTitle);
                }

                return false;
            }
        });
    }

    /**
     * 按钮点击
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_refresh_position: //刷新位置
                getLocationInfo(wayBillTitle, custId, false);
                break;

            case R.id.btn_remove: //拆除
                tearDownDialog();//拆除对话框
                break;

            case R.id.btn_lock_oil_elec: //锁油断电
                String imeiId = tv_imei.getText().toString().trim();
                Bundle bundle = new Bundle();
                bundle.putString("imeiId", imeiId);
                IntentUtil.gotoActivity(GPSInstallationTestActivity.this, LockOilElecActivity.class, bundle);
                break;

            case R.id.tv_refresh_phone: //刷新手机位置
                reLoad();
                break;

            case R.id.tv_complete: //完成
                completeDialog();
                break;
        }
    }

    /**
     * 拆除的弹框
     */
    private void tearDownDialog() {
        DialogUtil.showMessageDg(GPSInstallationTestActivity.this, "提示", "是否拆除", "取消", "确定", new CustomDialog.OnDialogClickListener() {
            @Override
            public void onClick(CustomDialog dialog, int id, Object object) {
                dialog.dismiss();
            }
        }, new CustomDialog.OnDialogClickListener() {
            @Override
            public void onClick(CustomDialog dialog, int id, Object object) {
                String imeiId = tv_imei.getText().toString().trim();
                tearDown(imeiId);//拆除并跳转
            }
        });
    }

    /**
     * 拆除方法
     * @param imeiId
     */
    private void tearDown(final String imeiId){
        RequestExecutor.addTask(new BaseTask(GPSInstallationTestActivity.this, "正在加载中",false) {
            @Override
            public ResponseBean sendRequest() {
                return InstallBiz.tearDown(imeiId);
            }

            @Override
            public void onSuccess(ResponseBean result) {
                ToastUtil.showToast(GPSInstallationTestActivity.this, result.getInfo());
                endAndJump();
            }

            @Override
            public void onFail(ResponseBean result) {
                ToastUtil.showToast(GPSInstallationTestActivity.this, result.getInfo());
            }
        });
    }

    /**
     * 刷新手机位置
     */
    private void reLoad() {
        GPS3DUtils.getInstance().startLocation(this, new GPS3DUtils.LocationListener() {

            @Override
            public void onCallback(double lat, double lon, String add) {
                MyLocationStyle myLocationStyle = new MyLocationStyle();
                myLocationStyle.showMyLocation(true);
                myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);
                aMap.setMyLocationStyle(myLocationStyle);
                aMap.setMyLocationEnabled(true);
            }

            @Override
            public void onErr() {
                ToastUtil.showToast(GPSInstallationTestActivity.this, "定位失败，请重新定位！");
            }
        });
    }

    /**
     * 获取最新定位数据 点击 刷新 完成时都要调用
     * @param subTitle 剪切后的imeiId
     */
    private void getLocationInfo(final String subTitle ,final String custId,final boolean isComplete){
        RequestExecutor.addTask(new BaseTask(GPSInstallationTestActivity.this, "正在加载中",false) {
            @Override
            public ResponseBean sendRequest() {
                return InstallBiz.getLocationInfos(custId);
            }

            @Override
            public void onSuccess(ResponseBean result) {
                listbean.clear();
                GPSBean bean = (GPSBean) result.getObject();
                listbean = bean.getReturnList(); //最新数据
                if (!TextUtils.isEmpty(subTitle)){
                    for (int i = 0; i < listbean.size(); i++) {
                        imeiId = listbean.get(i).getImeiId();
                        wayBillTitle= imeiId.substring(imeiId.length() - 5, imeiId.length()); //imei 后5位
                        if (subTitle.equals(wayBillTitle)){
                            setData(i,listbean);
                        }
                    }
                }else {
                    setData(0,listbean);
                }
                startLocation(listbean, isComplete);
            }

            @Override
            public void onFail(ResponseBean result) {
                ToastUtil.showToast(GPSInstallationTestActivity.this, result.getInfo());
            }
        });
    }

    /**
     * 完成的弹框
     */
    private void completeDialog() {
        DialogUtil.showMessageDg(GPSInstallationTestActivity.this, "提示", "是否完成", "取消", "确定", new CustomDialog.OnDialogClickListener() {
            @Override
            public void onClick(CustomDialog dialog, int id, Object object) {
                dialog.dismiss();
            }
        }, new CustomDialog.OnDialogClickListener() {
            @Override
            public void onClick(CustomDialog dialog, int id, Object object) {
                dialog.dismiss();
                getLocationInfo("", custId,false); //不用发接口
            }
        });
    }

    /**
     * 是否转人工服务
     */
    private void manServiceDialog(String msg) {
        DialogUtil.showMessageDg(GPSInstallationTestActivity.this, "提示：" + msg,
                "您安装的设备出现问题，是否要让安防部协助我们定位问题，请不要让客户离开，配合安防部处理问题！", "取消", "确定", new CustomDialog.OnDialogClickListener() {
                    @Override
                    public void onClick(CustomDialog dialog, int id, Object object) {
                        dialog.dismiss();
                    }
                }, new CustomDialog.OnDialogClickListener() {
                    @Override
                    public void onClick(CustomDialog dialog, int id, Object object) {
                        installComplete(userId, custId);//1代表是COUNTER老产品 0代表不是COUNTER老产品   1是自动 0是人工
                    }
                });
    }

    /**
     * 安装完成
     */
    private void installComplete(final String userId, final String custId){
        RequestExecutor.addTask(new BaseTask(GPSInstallationTestActivity.this, "正在加载中", false) {
            @Override
            public ResponseBean sendRequest() {
                return InstallBiz.installComplete(userId, custId);
            }

            @Override
            public void onSuccess(ResponseBean result) {
                ToastUtil.showToast(GPSInstallationTestActivity.this, "安装完成");
                endAndJump();
            }

            @Override
            public void onFail(ResponseBean result) {
                ToastUtil.showToast(GPSInstallationTestActivity.this, result.getInfo());
            }
        });

    }

    /**
     * 跳转到首页
     */
    private void endAndJump(){
        Intent intent = new Intent(GPSInstallationTestActivity.this, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("type",4);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        endAndJump();
    }

}
