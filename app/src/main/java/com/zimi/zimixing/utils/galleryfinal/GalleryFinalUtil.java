//package com.hxyd.dyt.utils.galleryfinal;
//
//import android.content.Context;
//import android.view.Gravity;
//import android.widget.Toast;
//
//import com.hxyd.dyt.R;
//import com.hxyd.dyt.configs.ConfigFile;
//import com.hxyd.dyt.utils.dialog.CustomDialog;
//import com.hxyd.dyt.utils.dialog.DialogUtil;
//
//import java.io.File;
//import java.util.ArrayList;
//
//import cn.finalteam.galleryfinal.CoreConfig;
//import cn.finalteam.galleryfinal.FunctionConfig;
//import cn.finalteam.galleryfinal.GalleryFinal;
//import cn.finalteam.galleryfinal.PauseOnScrollListener;
//import cn.finalteam.galleryfinal.ThemeConfig;
//import cn.finalteam.galleryfinal.model.PhotoInfo;
//
///**
// * 选择图片工具类
// * <br> Author: 叶青
// * <br> Version: 1.0.0
// * <br> Date: 2016年12月11日
// * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
// */
//public class GalleryFinalUtil {
//
//    private static final int REQUEST_CODE_CAMERA = 1000;
//    private static final int REQUEST_CODE_GALLERY = 1001;
//    private static final int REQUEST_CODE_CROP = 1002;
//    private static final int REQUEST_CODE_EDIT = 1003;
//
//    /**
//     * <p>
//     * <br> Version: 1.0.0
//     * <br> CreateTime: 2016/5/24 17:11
//     * <br> UpdateTime: 2016/5/24 17:11
//     * <br> CreateAuthor: 叶青
//     * <br> UpdateAuthor: 叶青
//     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
//     *
//     * @param context
//     *         上下文
//     * @param mPhotoList
//     *         已选择的图片列表
//     * @param isSingleSelect
//     *         是否单图选择
//     * @param maxPhotoSize
//     *         最大的图片选择张数
//     * @param isEditPhoto
//     *         是否可以编辑图片
//     * @param isRotate
//     *         是否可以旋转图片
//     * @param isRotateReplaceSource
//     *         是否旋转覆盖原图片
//     * @param isCrop
//     *         是否可以裁剪图片
//     * @param isCropReplaceSource
//     *         是否裁剪原图片
//     * @param cropPhotoWidth
//     *         裁剪图片的宽度
//     * @param CropPhotoHeight
//     *         裁剪图片的高度
//     * @param isCropSquare
//     *         是否裁剪为正方形
//     * @param isShowCamera
//     *         是否显示相机按钮
//     * @param isPreview
//     *         是否显示预览按钮
//     * @param isOpenForceCrop
//     *         是否强制裁剪--单图才可以强制裁剪
//     * @param isOpenForceCropEdit
//     *         是否强制裁剪编辑--单图才可以强制裁剪
//     * @param mOnHanlderResultCallback
//     *         回调方法
//     */
//    public static void selectPhoto(Context context, ArrayList<PhotoInfo> mPhotoList, boolean isSingleSelect, int maxPhotoSize, boolean isEditPhoto,
//                                   boolean isRotate, boolean isRotateReplaceSource, boolean isCrop, boolean isCropReplaceSource
//            , int cropPhotoWidth, int CropPhotoHeight, boolean isCropSquare, boolean isShowCamera, boolean isPreview
//            , boolean isOpenForceCrop, boolean isOpenForceCropEdit, final GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback) {
//
//        {
//
//            //公共配置都可以在application中配置，这里只是为了代码演示而写在此处
//            ThemeConfig themeConfig = null;
//
//            //            配置主题颜色
//            //            if (mRbThemeDefault.isChecked()) {
//            themeConfig = ThemeConfig.DEFAULT;
//            //            } else if (mRbThemeDark.isChecked()) {
//            //                themeConfig = ThemeConfig.DARK;
//            //            } else if (mRbThemeCyan.isChecked()) {
//            //                themeConfig = ThemeConfig.CYAN;
//            //            } else if (mRbThemeOrange.isChecked()) {
//            //                themeConfig = ThemeConfig.ORANGE;
//            //            } else if (mRbThemeGreen.isChecked()) {
//            //                themeConfig = ThemeConfig.GREEN;
//            //            } else if (mRbThemeTeal.isChecked()) {
//            //                themeConfig = ThemeConfig.TEAL;
//            //            } else if (mRbThemeCustom.isChecked()) {
//            //                ThemeConfig theme = new ThemeConfig.Builder()
//            //                        .setTitleBarBgColor(Color.rgb(0xFF, 0x57, 0x22))
//            //                        .setTitleBarTextColor(Color.BLACK)
//            //                        .setTitleBarIconColor(Color.BLACK)
//            //                        .setFabNornalColor(Color.RED)
//            //                        .setFabPressedColor(Color.BLUE)
//            //                        .setCheckNornalColor(Color.WHITE)
//            //                        .setCheckSelectedColor(Color.BLACK)
//            //                        .setIconBack(R.mipmap.ic_action_previous_item)
//            //                        .setIconRotate(R.mipmap.ic_action_repeat)
//            //                        .setIconCrop(R.mipmap.ic_action_crop)
//            //                        .setIconCamera(R.mipmap.ic_action_camera)
//            //                        .build();
//            //                themeConfig = theme;
//            //            }
//
//            FunctionConfig.Builder functionConfigBuilder = new FunctionConfig.Builder();
//            cn.finalteam.galleryfinal.ImageLoader imageLoader;
//            PauseOnScrollListener pauseOnScrollListener = null;
//
//            //            配置图片加载方式
//            //            if (mRbUil.isChecked()) {
//            //                imageLoader = new UILImageLoader();
//            //                pauseOnScrollListener = new UILPauseOnScrollListener(false, true);
//            //            } else if (mRbXutils.isChecked()) {
//            //                imageLoader = new XUtils2ImageLoader(MainActivity.this);
//            //            } else if (mRbXutils3.isChecked()) {
//            //                imageLoader = new XUtilsImageLoader();
//            //            } else if (mRbGlide.isChecked()) {
//            imageLoader = new GlideImageLoader();
//            pauseOnScrollListener = new GlidePauseOnScrollListener(false, true);
//            //            } else if (mRbFresco.isChecked()) {
//            //                imageLoader = new FrescoImageLoader(MainActivity.this);
//            //            } else {
//            //                imageLoader = new PicassoImageLoader();
//            //                pauseOnScrollListener = new PicassoPauseOnScrollListener(false, true);
//            //            }
//
//            final boolean mutiSelect = !isSingleSelect;
//            //配置是否多选
//            functionConfigBuilder.setMutiSelect(mutiSelect);
//            //ture多选 false单选，
//            if (!isSingleSelect) {
//                //            设置选择图片最大张数
//                if (maxPhotoSize <= 0) {
//                    Toast.makeText(context, "请输入MaxSize", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                functionConfigBuilder.setMutiSelectMaxSize(maxPhotoSize);
//            }
//
//            //开启编辑功能
//            functionConfigBuilder.setEnableEdit(isEditPhoto);
//            //开启旋转功能
//            functionConfigBuilder.setEnableRotate(isRotate);
//            //配置选择图片时是否替换原始图片，默认不替换
//            functionConfigBuilder.setRotateReplaceSource(isRotateReplaceSource);
//
//            //可编辑
//            if (isCrop) {
//                //开启裁剪功能
//                functionConfigBuilder.setEnableCrop(true);
//                if (cropPhotoWidth > 0) {
//                    //                    配置裁剪的宽度高度
//                    functionConfigBuilder.setCropWidth(cropPhotoWidth);
//                }
//
//                if (CropPhotoHeight > 0) {
//                    //                    配置裁剪的宽度高度
//                    functionConfigBuilder.setCropHeight(CropPhotoHeight);
//                }
//
//                if (isCropSquare) {
//                    //裁剪正方形
//                    functionConfigBuilder.setCropSquare(true);
//                }
//                //配置裁剪图片时是否替换原始图片，默认不替换
//                functionConfigBuilder.setCropReplaceSource(isCropReplaceSource);
//
//
//                //强制裁剪,只针对单张图片 有效
//                if (isOpenForceCrop && isSingleSelect) {
//                    //启动强制裁剪功能,一进入编辑页面就开启图片裁剪，不需要用户手动点击裁剪，此功能只针对单选操作
//                    functionConfigBuilder.setForceCrop(true);
//                    if (isOpenForceCropEdit) {
//                        //在开启强制裁剪功能时是否可以对图片进行编辑（也就是是否显示旋转图标和拍照图标）
//                        functionConfigBuilder.setForceCropEdit(true);
//                    }
//                }
//            }
//
//            //开启相机功能 === 是否显示相机按钮
//            functionConfigBuilder.setEnableCamera(isShowCamera);
//
//            //是否开启预览功能 === 是否显示预览按钮
//            functionConfigBuilder.setEnablePreview(isPreview);
//
//            //添加已选列表,只是在列表中默认被选中不会过滤图片
//            functionConfigBuilder.setSelected(mPhotoList);//添加过滤集合
//
//            final FunctionConfig functionConfig = functionConfigBuilder.build();
//
//            //构建CoreConfig所需ImageLoader和ThemeConfig
//            CoreConfig coreConfig = new CoreConfig.Builder(context, imageLoader, themeConfig)
//                    //            .setDebug //debug开关
//                    //配置全局GalleryFinal功能
//                    .setFunctionConfig(functionConfig)
//                    //设置imageloader滑动加载图片优化OnScrollListener,根据选择的ImageLoader来选择PauseOnScrollListener
//                    .setPauseOnScrollListener(pauseOnScrollListener)
//                    //配置编辑（裁剪和旋转）功能产生的cache文件保存目录，不做配置的话默认保存在/sdcard/GalleryFinal/edittemp/
//                    .setEditPhotoCacheFolder(new File(ConfigFile.PATH_IMAGE_EDITTEMP))
//                    //设置拍照保存目录，默认是/sdcard/DICM/GalleryFinal/
//                    .setTakePhotoFolder(new File(ConfigFile.PATH_CAMERA))
//                    //是否开启动画 默认true关闭动画
//                    .setNoAnimcation(true)
//                    .build();
//            GalleryFinal.init(coreConfig);
//
//            DialogUtil.showIosDialog(context, null, context.getResources().getStringArray(R.array.image_operation), Gravity.BOTTOM, null, new CustomDialog.OnDialogClickListener() {
//
//                @Override
//                public void onClick(CustomDialog dialog, int id, Object object) {
//                    dialog.dismiss();
//                    switch (id) {
//                        case 1:
//                            if (mutiSelect) {
//                                GalleryFinal.openGalleryMuti(REQUEST_CODE_GALLERY, functionConfig, mOnHanlderResultCallback);
//                            } else {
//                                GalleryFinal.openGallerySingle(REQUEST_CODE_GALLERY, functionConfig, mOnHanlderResultCallback);
//                            }
//                            break;
//                        case 0:
//                            GalleryFinal.openCamera(REQUEST_CODE_CAMERA, functionConfig, mOnHanlderResultCallback);
//                            break;
//                    }
//
//                }
//            });
//            //                                case 2:
//            //                                    if (new File(path).exists()) {
//            //                                        GalleryFinal.openCrop(REQUEST_CODE_CROP, functionConfig, path, mOnHanlderResultCallback);
//            //                                    } else {
//            //                                        Toast.makeText(MainActivity.this, "图片不存在", Toast.LENGTH_SHORT).show();
//            //                                    }
//            //                                    break;
//            //                                case 3:
//            //                                    if (new File(path).exists()) {
//            //                                        GalleryFinal.openEdit(REQUEST_CODE_EDIT, functionConfig, path, mOnHanlderResultCallback);
//            //                                    } else {
//            //                                        Toast.makeText(MainActivity.this, "图片不存在", Toast.LENGTH_SHORT).show();
//            //                                    }
//            //                                    break;
//        }
//
//    }
//
//}