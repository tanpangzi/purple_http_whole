package com.zimi.zimixing.activity.zmxGpsInstall;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import com.zimi.zimixing.BaseApplication;
import com.zimi.zimixing.R;
import com.zimi.zimixing.activity.BaseActivity;
import com.zimi.zimixing.activity.ImageBrowseActivity;
import com.zimi.zimixing.adapter.PhotoGridAdapter;
import com.zimi.zimixing.bean.ImgBean;
import com.zimi.zimixing.bean.ResponseBean;
import com.zimi.zimixing.bean.gps_install.GPSBean;
import com.zimi.zimixing.bean.gps_install.NewBindingBean;
import com.zimi.zimixing.biz.BaseBiz;
import com.zimi.zimixing.biz.InstallBiz;
import com.zimi.zimixing.biz.UserBiz;
import com.zimi.zimixing.configs.ConfigFile;
import com.zimi.zimixing.configs.ConfigServer;
import com.zimi.zimixing.configs.Constant;
import com.zimi.zimixing.configs.ConstantKey;
import com.zimi.zimixing.configs.RequestCode;
import com.zimi.zimixing.executor.BaseTask;
import com.zimi.zimixing.executor.RequestExecutor;
import com.zimi.zimixing.utils.FileUtil;
import com.zimi.zimixing.utils.IntentUtil;
import com.zimi.zimixing.utils.LogUtil;
import com.zimi.zimixing.utils.PermissionUtils;
import com.zimi.zimixing.utils.ToastUtil;
import com.zimi.zimixing.utils.dialog.CustomDialog;
import com.zimi.zimixing.utils.dialog.DialogUtil;
import com.zimi.zimixing.utils.imageutils.GetPathUtil;
import com.zimi.zimixing.widget.AutoSizeGridView;
import com.zimi.zimixing.widget.ContainEmojiEditext;
import com.zimi.zimixing.widget.TitleView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by tanjun on 2018/1/18.
 * gps安装 装车位置
 */

public class GPSLoadingActivity extends BaseActivity implements View.OnClickListener {

    private TitleView title_view; //标题
    private ContainEmojiEditext et_install_pos; //装车位置
    private Button btn_continue_install; //继续安装
    private Button btn_check_signal; //信号检测

    private String imeiId, custId;
    private String simId;

    private String userName;

    private String posDesc;//安装位置 描述

    private AutoSizeGridView grid_view_pic;
    private PhotoGridAdapter adapterCarInfo; // 适配器
    private ArrayList<ImgBean.ImgListBean> datasCarInfo; //list



    private final int MAX_SIZE = 1;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_gps_loading;
    }

    @Override
    protected void findViews() {
        title_view = (TitleView) findViewById(R.id.title_view);
        grid_view_pic = (AutoSizeGridView) findViewById(R.id.grid_view_pic);
        et_install_pos = (ContainEmojiEditext) findViewById(R.id.et_install_pos); //装车位置 文字
        btn_continue_install = (Button) findViewById(R.id.btn_continue_install);
        btn_check_signal = (Button) findViewById(R.id.btn_check_signal);
    }

    @Override
    protected void initGetData() {
        title_view.setTitle(R.string.install_position);
        title_view.setLeftBtnImg();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            imeiId = bundle.getString("imeiId");
            custId = bundle.getString("custId");
            simId = bundle.getString("simId");
        }
        userName = BaseApplication.getInstance().getUserInfoBean().getUserName();
        queryAllImgByParams(Constant.positionImages, imeiId);

    }

    @Override
    protected void init() {

    }

    @Override
    protected void widgetListener() {
        btn_continue_install.setOnClickListener(this);
        btn_check_signal.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_continue_install: //继续安装 继续安装传0
                if (check()){
                    newBindingForContinue(userName, custId, imeiId, simId, posDesc, "0"); //继续安装传0
                }
                break;

            case R.id.btn_check_signal: //信号检测
                if (check()){
                    newBindingForContinueForCheckSignal(userName, custId, imeiId, simId, posDesc, "1"); //信号检测传1
                }

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK){
            if (requestCode == RequestCode.REQUEST_CODE_PIC){
                if (data != null) {
                    Uri uri = data.getData();
                    String picPath;//相册路径
                    if (uri.toString().startsWith("file:///storage/emulated")){
                        picPath = uri.toString().replace("file:///storage/emulated", "/storage/emulated");
                    }else {
                        //判断手机系统版本号
                        if (Build.VERSION.SDK_INT >= 19) {
                            //4.4及以上系统使用这个方法处理图片
                            picPath = GetPathUtil.getPath(GPSLoadingActivity.this, uri);
                        } else {
                            //4.4以下系统使用这个方法处理图片
                            picPath = null;
                            //通过Uri和selection来获取真实的图片路径
                            Cursor cursor = GPSLoadingActivity.this.getContentResolver().query(uri, null, null, null, null);
                            if (cursor != null) {
                                if (cursor.moveToFirst()) {
                                    picPath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                                }
                                cursor.close();
                            }
                        }
                    }
                    if (!TextUtils.isEmpty(picPath)){
                        // 解析本地图片，获得图片尺寸
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        // 不生成Bitmap 只是获取bitmap的宽高，相对省内存
                        options.inJustDecodeBounds = true;
                        BitmapFactory.decodeFile(picPath, options);
                        if (options.outWidth == -1 || options.outHeight == -1){
                            ToastUtil.showToast(GPSLoadingActivity.this, "图片已损坏，请重新选择");
                            return;
                        }
                        uploadPicture(picPath);
                    }
                }
            }
            else if (requestCode == RequestCode.REQUEST_CODE_PHOTO){ //相机
                uploadPicture(tempPath);
            }else if (requestCode == RequestCode.REQUEST_CODE_PREVIEW){ //预览
                queryAllImgByParams(Constant.positionImages, imeiId);
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 点击预览
     */
    public void gotoPreviewPhoto(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt(ConstantKey.INTENT_KEY_POSITION, position);
        bundle.putString("groupId", Constant.GROUPID_POSITIONIMAGES);
        bundle.putString("imeiId", imeiId);
        bundle.putInt("imgType", Constant.positionImages); //类型
        ArrayList<ImgBean.ImgListBean> datas = datasCarInfo;
        ArrayList<String> arrayList;


        arrayList = new ArrayList<>();
        if (datas != null) {
            for (int i = 0; i < datas.size(); i++) {
                String path = datas.get(i).getUrl();
                if (!TextUtils.isEmpty(path) && !path.startsWith("/storage/emulated")) {
                    path = ConfigServer.SERVER_API_URL + path.replaceFirst("app/", "");
                }
                arrayList.add(path);
            }
        }
        bundle.putStringArrayList(ConstantKey.INTENT_KEY_DATAS, arrayList);
        IntentUtil.gotoActivityForResult(GPSLoadingActivity.this,
                ImageBrowseActivity.class, bundle, RequestCode.REQUEST_CODE_PREVIEW);

    }

    private boolean isCamera = false;
    private boolean isStorage = false;
    /**
     * 拍照 保存图片的本地缓存路径
     */
    private String tempPath = "";

    /**
     * 选择相册 相机对话框
     */
    public void showChoseDialog() {
        DialogUtil.showIosDialog(GPSLoadingActivity.this, null, getResources().getStringArray(R.array.image_operation), Gravity.BOTTOM, null, new CustomDialog.OnDialogClickListener() {

            @Override
            public void onClick(CustomDialog dialog, int id, Object object) {
                dialog.dismiss();
                switch (id) {
                    case 1:// 相册
                        checkPhoto();
                        break;
                    case 0:// 相机
                        tackPic();
                        break;
                }
            }
        });
    }

    /**
     * 相册
     */
    public void checkPhoto() {
        PermissionUtils.getInstance(new PermissionUtils.PermissionGrant() {
            @Override
            public void onPermissionGranted(Object permissionNameOrCode, boolean isSuccess) {
                LogUtil.i(permissionNameOrCode + "........." + isSuccess);
                if (String.valueOf(permissionNameOrCode).equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    if (isSuccess) {
                        FileUtil.createAllFile();
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_PICK);//Pick an item fromthe data
                        intent.setType("image/*");
                        startActivityForResult(intent, RequestCode.REQUEST_CODE_PIC);
                    } else {
                        DialogUtil.showMessageDg(GPSLoadingActivity.this, "提示", "存储权限权限被拒绝", "", "确定", null, new CustomDialog.OnDialogClickListener() {
                            @Override
                            public void onClick(CustomDialog dialog, int id, Object object) {
                                dialog.dismiss();
                            }
                        }, Gravity.CENTER);
                    }
                }
            }
        }).requestPermission(GPSLoadingActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

    }

    /**
     * 拍照
     * @param
     */
    public void tackPic() {
        // 权限数组
        String[] REQUEST_PERMISSIONS = {
                Manifest.permission.CAMERA,                         // android.permission-group.CAMERA
                Manifest.permission.WRITE_EXTERNAL_STORAGE          // android.permission-group.STORAGE
        };
        PermissionUtils.getInstance(new PermissionUtils.PermissionGrant() {
            @Override
            public void onPermissionGranted(Object permissionNameOrCode, boolean isSuccess) {
                LogUtil.i(permissionNameOrCode + "........." + isSuccess);
                switch (String.valueOf(permissionNameOrCode)) {
                    case Manifest.permission.CAMERA:
                        isCamera = isSuccess;
                        break;
                    case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                        isStorage = isSuccess;
                        if (isSuccess) {
                            FileUtil.createAllFile();
                        }
                        break;
                    case PermissionUtils.REQUEST_MULTIPLE_PERMISSION + "":
                        if (isSuccess) {
                            Intent intent = new Intent();
                            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                            intent.addCategory(Intent.CATEGORY_DEFAULT);              // 根据文件地址创建文件

                            tempPath = ConfigFile.PATH_IMAGES + "/dk_" + System.currentTimeMillis() + ".jpg";

                            tempPath = ConfigFile.PATH_IMAGES + "/dk_" + System.currentTimeMillis() + ".jpg";
                            Uri uri;
                            if (Build.VERSION.SDK_INT >= 24) {
                                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                uri = FileProvider.getUriForFile(GPSLoadingActivity.this, "com.hxyd.dyt.fileprovider", new File(tempPath));
                                //								uri = FileProvider.getUriForFile(RecordedOrderActvityThree.this, BuildConfig.APPLICATION_ID + ".fileProvider", new File(tempPath));
                            } else {
                                uri = Uri.fromFile(new File(tempPath));
                            }
                            //							Uri uri = Uri.fromFile(new File(tempPath));
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                            startActivityForResult(intent, RequestCode.REQUEST_CODE_PHOTO);

                        } else {
                            String tips = "";
                            if (!isCamera) {
                                tips = tips + "相机";
                            }

                            if (!isStorage) {
                                tips = tips + "、存储";
                            }

                            if (!TextUtils.isEmpty(tips)) {
                                if (tips.startsWith("、")) {
                                    tips = tips.replaceFirst("、", "");
                                }
                                tips = "亲，您还没有授权" + tips + "权限";
                                DialogUtil.showMessageDg(GPSLoadingActivity.this, "提示", tips + "权限被拒绝", "", "确定", null, new CustomDialog.OnDialogClickListener() {
                                    @Override
                                    public void onClick(CustomDialog dialog, int id, Object object) {
                                        dialog.dismiss();
                                    }
                                }, Gravity.CENTER);
                            }
                        }
                }
            }
        }).requestPermission(GPSLoadingActivity.this, REQUEST_PERMISSIONS);
    }

    /**
     * 上传图片
     * @param path
     */
    String fileName = "";
    private void uploadPicture(final String pressPath){
        ArrayList<ImgBean.ImgListBean> datas = null;
        datas = datasCarInfo;
        if (datas != null) {
//            for (int i = 0; i < datas.size(); i++) {
            int positionItem=adapterCarInfo.getPositionItem();
            if (positionItem < 9){
                fileName = Constant.GROUPID_POSITIONIMAGES + "_0" + (positionItem + 1);
            }else{
                fileName = Constant.GROUPID_POSITIONIMAGES + "_" + (positionItem + 1);
            }
//            }
        }
        if (TextUtils.isEmpty(fileName)){
            return;
        }
        RequestExecutor.addTask(new BaseTask(GPSLoadingActivity.this, "上传中请稍候", false) {
            @Override
            public ResponseBean sendRequest() {
                return InstallBiz.upLoadImg(Constant.positionImages, fileName, pressPath, imeiId);
            }

            @Override
            public void onSuccess(ResponseBean result) {
                String resultStr = (String) result.getObject();
                String fileId = "";
                try {
                    JSONObject json = new JSONObject(resultStr);
                    fileId = json.optString("fileId", "");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                ToastUtil.showToast(GPSLoadingActivity.this, result.getInfo());
                setData("app/common/captcha/task/readImg?fileId="+fileId,fileName);
            }

            @Override
            public void onFail(ResponseBean result) {
                ToastUtil.showToast(GPSLoadingActivity.this, result.getInfo());
            }
        });

    }

    /**
     * 拍照 选择图片后 刷新数据源
     */
    private void setData(String path, String fileName){
        setPhotoData(datasCarInfo, path, fileName, Constant.GROUPID_POSITIONIMAGES);
        adapterCarInfo.notifyDataSetChanged();
    }

    private void setPhotoData(ArrayList<ImgBean.ImgListBean> datas, String path, String fileName, String groupId){
        boolean isLast = false;
        int positionItem=adapterCarInfo.getPositionItem();
//        for (int i = 0; i < datas.size(); i++) {
        datas.get(positionItem).setUrl(path);
        datas.get(positionItem).setGroup_id(groupId);
        datas.get(positionItem).setImage_name(fileName);
        if (positionItem == datas.size() -1){
            isLast = true;
        }
//        }

        if (isLast && datas.size() != MAX_SIZE && !TextUtils.isEmpty(path)){
            ImgBean.ImgListBean bean = new ImgBean.ImgListBean();
            datas.add(bean);
        }
    }


    /**
     * 检测是否能继续
     * @return
     */
    private boolean check(){
        posDesc = et_install_pos.getText().toString().trim();
        if (TextUtils.isEmpty(posDesc)){
            ToastUtil.showToast(GPSLoadingActivity.this, getString(R.string.empty_install_pos));
            return false;
        }

        for (int i = 0; i < datasCarInfo.size(); i++) {
            if (TextUtils.isEmpty(datasCarInfo.get(0).getUrl())){ //用第一张图作检验就可以
                ToastUtil.showToast(GPSLoadingActivity.this, "安装位置图片不能为空");
                return false;
            }
        }

        return true;
    }

    /**
     * 根据 imgType 和businessId查询图片
     * @param imgType
     * @param imeiId
     */
    private void queryAllImgByParams(final int imgType, final String imeiId){
        HashMap<String, String> params = BaseBiz.getPostHeadMap();
        params.put("token", BaseApplication.getInstance().getToken());
        params.put("imeiId", imeiId);
        params.put("groupId", "positionImages");
        RequestExecutor.addTask(new BaseTask(GPSLoadingActivity.this, getString(R.string.process_handle_wait), false) {
            @Override
            public ResponseBean sendRequest() {
                return InstallBiz.queryAllImgByParams(imgType, imeiId);
            }

            @Override
            public void onSuccess(ResponseBean result) {
                ImgBean bean = (ImgBean) result.getObject();
                initData(bean);

            }

            @Override
            public void onFail(ResponseBean result) {
                ToastUtil.showToast(GPSLoadingActivity.this, result.getInfo());
            }
        });
    }

    /**
     * 从服务器返回数据显示
     */
    private void initData(ImgBean imgBean){
        datasCarInfo = new ArrayList<>();
        ImgBean.ImgListBean bean = new ImgBean.ImgListBean();
        datasCarInfo.add(bean);

        adapterCarInfo = new PhotoGridAdapter(GPSLoadingActivity.this, datasCarInfo);
        grid_view_pic.setAdapter(adapterCarInfo);
        if (imgBean != null){
            Map<Integer, ImgBean.ImgListBean> map = new HashMap<>();
            int maxItem = 0; //图片最大下标值
            ArrayList<ImgBean.ImgListBean> imgList = imgBean.getImgList();
            for (int i = 0; i < imgList.size(); i++) {
                ImgBean.ImgListBean dataBean = imgList.get(i);
                String imgName = dataBean.getImage_name();
                int curPos = Integer
                        .parseInt(imgName.substring(imgName.length() - 2, imgName.length()));//图片的位置
                if (curPos > maxItem){
                    maxItem = curPos;
                }
                map.put(curPos, dataBean);
            }
            addImg(maxItem, MAX_SIZE, map, datasCarInfo);
            adapterCarInfo.notifyDataSetChanged();
        }
    }

    /**
     * 添加图片
     * @param maxItem 最大下标
     * @param maxSize 最大数量
     * @param map
     * @param datas
     */
    private void addImg(int maxItem, int maxSize,
                        Map<Integer, ImgBean.ImgListBean> map, ArrayList<ImgBean.ImgListBean> datas){
        int add = maxItem - datas.size();
        for (int i = 0; i < add; i++) {
            ImgBean.ImgListBean bean = new ImgBean.ImgListBean();
            datas.add(bean);
        }
        Set<Map.Entry<Integer, ImgBean.ImgListBean>> set = map.entrySet();
        for (Map.Entry<Integer, ImgBean.ImgListBean> entry : set) {
            int position = entry.getKey();
            ImgBean.ImgListBean imageInfo = entry.getValue();
            if (position <= maxSize) {
                datas.get(position - 1).setUrl(imageInfo.getUrl());
                datas.get(position - 1).setImage_name(imageInfo.getImage_name());
            }
        }

        if (maxItem < maxSize && datas.size() != 0){
            if (!TextUtils.isEmpty(datas.get(datas.size() - 1).getUrl())) {
                ImgBean.ImgListBean imageInfoBean = new ImgBean.ImgListBean();
                datas.add(imageInfoBean);
            }
        }
    }

    /** 绑定 跳转继续安装*/
    private void newBindingForContinue(final String userName,final String custId,
                            final String imeiId, final String simId,
                            final String posDesc, final String isGetLocation){
        RequestExecutor.addTask(new BaseTask(GPSLoadingActivity.this, getString(R.string.process_handle_wait), true) {
            @Override
            public ResponseBean sendRequest() {
                return InstallBiz.newBinding(userName, custId, imeiId, simId, posDesc, isGetLocation);
            }

            @Override
            public void onSuccess(ResponseBean result) {
                ToastUtil.showToast(GPSLoadingActivity.this, result.getInfo());
                continueInstall(custId, simId);
            }

            @Override
            public void onFail(ResponseBean result) {
                ToastUtil.showToast(GPSLoadingActivity.this, result.getInfo());
            }
        });
    }

    /** 绑定 跳转信号检测*/
    private void newBindingForContinueForCheckSignal(final String userName, final String custId,
                                       final String imeiId, final String simId,
                                       final String posDesc, final String isGetLocation){
        RequestExecutor.addTask(new BaseTask(GPSLoadingActivity.this, getString(R.string.process_handle_wait), true) {
            @Override
            public ResponseBean sendRequest() {
                return InstallBiz.newBinding(userName, custId, imeiId, simId, posDesc, isGetLocation);
            }

            @Override
            public void onSuccess(ResponseBean result) {
                NewBindingBean bean = (NewBindingBean) result.getObject();
                //NewBindingBean datas = (NewBindingBean) bean.getReturnList();
                for (int i = 0; i < bean.getReturnList().size(); i++) {
                    newBindData(bean.getReturnList().get(i));
                }
            }

            @Override
            public void onFail(ResponseBean result) {
                ToastUtil.showToast(GPSLoadingActivity.this, result.getInfo());
            }
        });
    }

    /**
     * 绑定 安装
     * @param returnListBean
     */
    private void newBindData(NewBindingBean.ReturnListBean returnListBean){
        if (returnListBean != null){
            String receiveDate = returnListBean.getReceiveDate();
            String locationDate = returnListBean.getLocationDate();
            double lon = returnListBean.getLongitude();
            double lat = returnListBean.getLatitude();

            jumpSignalCheck(receiveDate, locationDate, lon, lat);
        }
    }

    /**
     * 继续安装 跳转 设备信息界面
     */
    private void continueInstall(String custId,
                                 String simId){
        Bundle bundle = new Bundle();
        //bundle.putString("businessId",businessId);
        bundle.putString("custId",custId);
        //bundle.putString("processInstanceId",processInstanceId);
        bundle.putString("simId",simId);
        bundle.putString("imeiId",imeiId);
        IntentUtil.gotoActivity(GPSLoadingActivity.this, GPSDeviceInfoActivity.class,bundle);
        finishActivity();
    }

    /**
     * 跳转信号检测
     */
    private void jumpSignalCheck(final String receiveDate, final String locationDate, final double lon, final double lat){
        RequestExecutor.addTask(new BaseTask() {
            @Override
            public ResponseBean sendRequest() {
                return InstallBiz.getLocationInfos(custId);
            }

            @Override
            public void onSuccess(ResponseBean result) {
                Bundle bundle = new Bundle();
                GPSBean bean = (GPSBean) result.getObject();
                String isCounterProduct = bean.getIsCounterProduct(); //新老产品 1老产品 0是新产品
                ArrayList<GPSBean.ReturnListBean> listbean = bean.getReturnList();

                bundle.putSerializable("gpsList", listbean);
                //bundle.putString("isCounterProduct",isCounterProduct);
                //bundle.putString("businessId", businessId);
                bundle.putString("imeiId", imeiId);
                bundle.putString("custId", custId);
                bundle.putString("simId",simId);

                bundle.putString("receiveDate",receiveDate);
                bundle.putString("locationDate",locationDate);
                bundle.putDouble("lon",lon);
                bundle.putDouble("lat",lat);
                IntentUtil.gotoActivity(GPSLoadingActivity.this, GPSInstallationTestActivity.class, bundle);
                finishActivity();
            }

            @Override
            public void onFail(ResponseBean result) {

            }
        });

    }


}
