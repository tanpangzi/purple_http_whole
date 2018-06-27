package com.zimi.zimixing.utils;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;

import com.zimi.zimixing.BuildConfig;
import com.zimi.zimixing.R;
import com.zimi.zimixing.activity.BaseActivity;
import com.zimi.zimixing.activity.MainActivity;
import com.zimi.zimixing.bean.ResponseBean;
import com.zimi.zimixing.bean.UpdateBean;
import com.zimi.zimixing.biz.UserBiz;
import com.zimi.zimixing.configs.ConfigFile;
import com.zimi.zimixing.configs.RequestCode;
import com.zimi.zimixing.executor.BaseTask;
import com.zimi.zimixing.executor.DownProgressDialogUtil;
import com.zimi.zimixing.executor.RequestExecutor;
import com.zimi.zimixing.interf.OnDownLoadCallBack;
import com.zimi.zimixing.service.AMapLocationService;
import com.zimi.zimixing.utils.dialog.CustomDialog;
import com.zimi.zimixing.utils.dialog.DialogUtil;

import java.io.File;

/**
 * 检测更新 工具类
 * <p>
 * <br> Author: 叶青
 * <br> Version: 6.0.0
 * <br> Date: 2018/1/5
 * <br> Copyright: Copyright © 2017 xTeam Technology. All rights reserved.
 */
public class UpdateVersionUtil {

    private BaseActivity activity;

    public UpdateVersionUtil(BaseActivity activity) {
        this.activity = activity;
    }

    /**
     * 检测更新
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-11-25,上午10:38:53
     * <br> UpdateTime: 2016-11-25,上午10:38:53
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     */
    public void checkUpdate() {
        RequestExecutor.addTask(new BaseTask(activity, activity.getString(R.string.process_handle_wait), false) {

            @Override
            public ResponseBean sendRequest() {
                return UserBiz.checkUpdate();
            }

            @Override
            public void onSuccess(ResponseBean result) {
                bean = (UpdateBean) result.getObject();

                // 比较当前版本跟服务器版本 version——name的大小
                if (!TextUtils.isEmpty(bean.getVersionCode())) {
                    // if (Integer.parseInt(bean.getVersionCode()) > 1) {
                    if (Integer.parseInt(bean.getVersionCode()) > SystemUtil.getCurrentVersionCode()) {
                        DialogUtil.showMessageDg(activity, "贷业通已经更新版本", bean.getVersionInfo(), "取消", "确定", new CustomDialog.OnDialogClickListener() {
                            @Override
                            public void onClick(CustomDialog dialog, int id, Object object) {
                                if (bean.isMustUpdate()) {
                                    AppManagerUtil.getAppManager().exitApp();
                                } else {
                                    dialog.dismiss();
                                }
                            }
                        }, new CustomDialog.OnDialogClickListener() {
                            @Override
                            public void onClick(CustomDialog dialog, int id, Object object) {
                                // TODO 下载
                                dialog.dismiss();
                                PermissionUtils.getInstance(new PermissionUtils.PermissionGrant() {
                                    @Override
                                    public void onPermissionGranted(Object permissionName, boolean isSuccess) {
                                        if (isSuccess) {
                                            LogUtil.i(permissionName + "........." + true);
                                        } else {
                                            LogUtil.e(permissionName + "........." + false);
                                        }
                                        switch (String.valueOf(permissionName)) {
                                            case Manifest.permission.RECORD_AUDIO:
                                                break;
                                            case Manifest.permission.READ_CONTACTS:
                                                break;
                                            case Manifest.permission.CALL_PHONE:
                                                break;
                                            case Manifest.permission.CAMERA:
                                                break;
                                            case Manifest.permission.ACCESS_FINE_LOCATION:
                                                if (isSuccess) {
                                                    activity.startService(new Intent().setClass(activity, AMapLocationService.class));
                                                }
                                                break;
                                            case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                                                if (isSuccess) {
                                                    FileUtil.createAllFile();
                                                    downLoadApk(bean.getUrl());
                                                } else {
                                                    ToastUtil.showToast(activity, "您已拒绝了文件存储权限");
                                                    if (bean.isMustUpdate()) {
                                                        AppManagerUtil.getAppManager().exitApp();
                                                    }
                                                }
                                                break;
                                            case Manifest.permission.READ_SMS:
                                                break;
                                            case PermissionUtils.REQUEST_MULTIPLE_PERMISSION:
                                                break;
                                            default:
                                                break;
                                        }
                                    }
                                }).requestPermission(activity, PermissionUtils.REQUEST_PERMISSIONS[5]);
                            }
                        });
                    } else {
                        if (!(activity instanceof MainActivity)) {
                            ToastUtil.showToast(activity, "当前已是最新版本");
                        }
                    }
                }
            }

            @Override
            public void onFail(ResponseBean result) {
                ToastUtil.showToast(activity, result.getInfo());
            }
        });
    }

    /** 检测更新信息 */
    private UpdateBean bean;
    /** 更新进度 */
    private DownProgressDialogUtil downProgressDialogUtil;

    public boolean isMust() {
        return bean != null && bean.isMustUpdate();
    }

    /**
     * apk下载
     * <p>
     * <br> Version: 6.0.0
     * <br> CreateAuthor: 叶青
     * <br> CreateTime: 2018/1/5 18:13
     * <br> UpdateAuthor: 叶青
     * <br> UpdateTime: 2018/1/5 18:13
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param url
     *         下载路径
     */
    public void downLoadApk(final String url) {
        if (downProgressDialogUtil == null) {
            downProgressDialogUtil = new DownProgressDialogUtil(activity);
        }
        downProgressDialogUtil.showDialog("提示", "正在下载中，请稍后...", !bean.isMustUpdate());

        RequestExecutor.addTask(new BaseTask() {

            @Override
            public ResponseBean sendRequest() {
                return UserBiz.downLoadApk(url, ConfigFile.PATH_DOWNLOAD, new OnDownLoadCallBack() {
                    @Override
                    public void ResultProgress(final int press, int fileSize, int downLoadSize) {
                        if (downProgressDialogUtil != null) {
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    downProgressDialogUtil.setProgress(press);
                                }
                            });
                        }
                    }
                });
            }

            @Override
            public void onSuccess(ResponseBean result) {
                String apkLocal = StringUtil.getLocalCachePath(url, ConfigFile.PATH_DOWNLOAD);
                File file = (new File(apkLocal));
                Intent intent = new Intent();
                intent.setAction(android.content.Intent.ACTION_VIEW);
                //判读版本是否在7.0以上
                if (Build.VERSION.SDK_INT >= 24) {
                    //provider authorities
                    Uri apkUri = FileProvider.getUriForFile(activity, BuildConfig.APPLICATION_ID + ".fileprovider", file);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //Granting Temporary Permissions to a URI
                    intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
                } else {
                    Uri apkUri = Uri.fromFile(file);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
                }
                activity.startActivityForResult(intent, RequestCode.REQUEST_CODE_INSTALL_APK);
                downProgressDialogUtil.dismissDialog();
                ToastUtil.showToast(activity, result.getInfo());
            }

            @Override
            public void onFail(ResponseBean result) {
                downProgressDialogUtil.dismissDialog();
                ToastUtil.showToast(activity, result.getInfo());
            }
        });
    }
}
