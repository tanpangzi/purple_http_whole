package com.zimi.zimixing.activity.gpsMonitor.old;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.enums.NaviType;
import com.amap.api.navi.model.NaviLatLng;
import com.zimi.zimixing.R;
import com.zimi.zimixing.activity.gpsMonitor.old.amapUtils.GPS3DUtils;
import com.zimi.zimixing.utils.FileUtil;
import com.zimi.zimixing.utils.LogUtil;
import com.zimi.zimixing.utils.PermissionUtils;
import com.zimi.zimixing.utils.ToastUtil;
import com.zimi.zimixing.utils.dialog.CustomDialog;
import com.zimi.zimixing.utils.dialog.DialogUtil;

public class GPSNaviActivity extends BaseActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpsnavi);

        double lat = getIntent().getDoubleExtra("STARTLATLNG_LATITUDE", 0);
        double lon = getIntent().getDoubleExtra("STARTLATLNG_LONGITUDE", 0);
        if (lat == 0 && lon == 0) {
            ToastUtil.showToast(GPSNaviActivity.this, "终点位置获取失败，请重试！");
            finish();
        }
        showProgressDialog("正在规划中...");
        mEndLatlng = new NaviLatLng(lat, lon);
        eList.add(mEndLatlng);
        mAMapNaviView = (AMapNaviView) findViewById(R.id.navi_view);
        mAMapNaviView.onCreate(savedInstanceState);
        mAMapNavi.addAMapNaviListener(this);
        //设置模拟导航的行车速度
        mAMapNavi.setEmulatorNaviSpeed(75);
        mAMapNaviView.setAMapNaviViewListener(this);

    }

    @Override
    public void onInitNaviSuccess() {
        super.onInitNaviSuccess();

        //        startLocation();
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
                        } else {
                            DialogUtil.showMessageDg(GPSNaviActivity.this, "必需权限", "没有该权限，此应用程序可能无法正常工作。打开应用设置界面以修改应用权限", "", "确定", null, new CustomDialog.OnDialogClickListener() {
                                @Override
                                public void onClick(CustomDialog dialog, int id, Object object) {
                                    dismiss();
                                    dialog.dismiss();
                                    finish();
                                }
                            });
                        }
                        break;
                    default:
                        break;
                }
            }
        }).requestPermission(GPSNaviActivity.this, perms);
    }


    //    public static final int RC_CAMERA_AND_LOCATION = 1001;
    //    @AfterPermissionGranted(RC_CAMERA_AND_LOCATION)
    private void startLocation() {
        //        String[] perms = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION
        //                ,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.READ_PHONE_STATE};
        //        if (EasyPermissions.hasPermissions(this, perms)) {
        // 已经有了许可，做这件事
        GPS3DUtils.getInstance().startLocation(this, new GPS3DUtils.LocationListener() {
            @Override
            public void onCallback(double latitude, double longitude, String address) {
                dismiss();
                mStartLatlng = new NaviLatLng(latitude, longitude);
                sList.add(mStartLatlng);
                /**
                 * 方法: int strategy=mAMapNavi.strategyConvert(congestion, avoidhightspeed, cost, hightspeed, multipleroute); 参数:
                 *
                 * @congestion 躲避拥堵
                 * @avoidhightspeed 不走高速
                 * @cost 避免收费
                 * @hightspeed 高速优先
                 * @multipleroute 多路径
                 *
                 *  说明: 以上参数都是boolean类型，其中multipleroute参数表示是否多条路线，如果为true则此策略会算出多条路线。
                 *  注意: 不走高速与高速优先不能同时为true 高速优先与避免收费不能同时为true
                 */
                int strategy = 0;
                try {
                    //再次强调，最后一个参数为true时代表多路径，否则代表单路径
                    strategy = mAMapNavi.strategyConvert(true, false, false, false, false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mAMapNavi.setCarNumber("京", "DFZ588");
                mAMapNavi.calculateDriveRoute(sList, eList, mWayPointList, strategy);
            }

            @Override
            public void onErr() {
                dismiss();
                ToastUtil.showToast(GPSNaviActivity.this, "定位失败，请重新定位！");
                finish();
            }
        });
        //        } else {
        //            // 没有权限，现在就请求它们
        //            EasyPermissions.requestPermissions(this, "请打开位置定位功能！",
        //                    RC_CAMERA_AND_LOCATION, perms);
        //        }
    }

//    @Override
//    public void onPermissionsGranted(int requestCode, List<String> perms) {
//        //已授予某些权限。
//    }
//
//    @Override
//    public void onPermissionsDenied(int requestCode, List<String> perms) {
//
//        //某些权限已被拒绝。用户拒绝后
//        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
//            new AppSettingsDialog.Builder(this)
//                    .setRationale("没有该权限，此应用程序可能无法正常工作。打开应用设置界面以修改应用权限")
//                    .setTitle("必需权限")
//                    .build()
//                    .show();
//        }
//    }
//
    //    @Override
    //    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    //        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    //        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    //    }


    @Override
    public void onCalculateRouteSuccess(int[] ids) {
        super.onCalculateRouteSuccess(ids);
        mAMapNavi.startNavi(NaviType.GPS);
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

}
