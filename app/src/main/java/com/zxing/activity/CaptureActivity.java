package com.zxing.activity;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.zimi.zimixing.R;
import com.zimi.zimixing.activity.BaseActivity;
import com.zimi.zimixing.configs.ConstantKey;
import com.zimi.zimixing.utils.imageutils.GetPathUtil;
import com.zimi.zimixing.utils.imageutils.ImageCompressUtil;
import com.zimi.zimixing.widget.TitleView;
import com.zxing.camera.CameraManager;
import com.zxing.decoding.CaptureActivityHandler;
import com.zxing.decoding.InactivityTimer;
import com.zxing.decoding.RGBLuminanceSource;
import com.zxing.encoding.EncodingHandler;
import com.zxing.view.ViewfinderView;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;

import static com.zimi.zimixing.configs.RequestCode.REQUEST_CODE_CHOSE_PHOTO;

/**
 * 扫码界面
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class CaptureActivity extends BaseActivity implements Callback {

    /** 选择本地二维码图---返回返回码 */
    private static final int SELECT_PICTURE_CODE = 1;
    /** 振动持续时间 */
    private static final long VIBRATE_DURATION = 200L;
    /** 音量 */
    private static final float BEEP_VOLUME = 0.10f;
    /** Handler */
    private CaptureActivityHandler handler;
    /** 预览图像 */
    private SurfaceView surfaceView;
    /** 扫码预览view */
    private ViewfinderView viewfinderView;
    private Vector<BarcodeFormat> decodeFormats;
    private InactivityTimer inactivityTimer;
    /** 媒体播放�? */
    private MediaPlayer mediaPlayer;
    /** 标题 */
    private TitleView viewTitle;
    //	/** 扫描本地二维码图片进度条  */
    // private ProgressDialog mProgress;
    private boolean hasSurface;
    /** 扫描完成是否播放声音 */
    private boolean playBeep;
    /** 振动 */
    private boolean vibrate;
    private String characterSet;
    /** 本地二维码图片路�? */
    private String photoPath = "";
    /** 扫描二维码后 返回的二维码bitmap */
    private Bitmap scanBitmap;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_scan_camera;
    }

    @Override
    protected void findViews() {
        viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
        viewTitle = (TitleView) findViewById(R.id.view_title);
        surfaceView = (SurfaceView) findViewById(R.id.preview_view);
    }

    @Override
    protected void init() {
        CameraManager.init(getApplication());
        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);
        viewTitle.setTitle(R.string.activity_scan_code);

        try {
            // 生成Bitmap
            Bitmap qrCodeBitmap = EncodingHandler.createQRCode("123456", 354);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initGetData() {
    }

    @Override
    protected void widgetListener() {
        viewTitle.setLeftBtnImg();

        //        viewTitle.setRightBtnTxt("相册", new OnClickListener() {
        //            @Override
        //            public void onClick(View v) {
        //                IntentUtil.chosePhoto(CaptureActivity.this);
        //                //                Intent getImage = new Intent(Intent.ACTION_GET_CONTENT);
        //                //                getImage.addCategory(Intent.CATEGORY_OPENABLE);
        //                //                getImage.setType("image/*");
        //                //                startActivityForResult(getImage, SELECT_PICTURE_CODE);
        //            }
        //        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("123456", System.currentTimeMillis() + "....onCreate....");
        super.onCreate(savedInstanceState);
        Log.i("123456", System.currentTimeMillis() + "....onCreateonCreate....");
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void onResume() {
        super.onResume();
        SurfaceHolder surfaceHolder = surfaceView.getHolder();

        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }

        decodeFormats = null;
        characterSet = null;

        playBeep = true;
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }

        initBeepSound();
        vibrate = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    /**
     * Handler(扫描结果处理)
     *
     * @param result
     *         返回数据
     * @param barcode
     *         图片资源
     *         <p>
     *         <br> Version:  1.0
     *         <br> CreateTime: 2016-4-9,上午10:08:37
     *         <br> UpdateTime: 2016-4-9,上午10:08:37
     *         <br> CreateAuthor: 叶青
     *         <br> UpdateAuthor: 叶青
     *         <br> UpdateInfo:(此处输入修改内容,若无修改可不�?.)
     */
    public void handleDecode(Result result, Bitmap barcode) {
        if (barcode != null) {
            //取景框绘制bitmap
            viewfinderView.drawResultBitmap(barcode);
            //Toast.makeText(CaptureActivity.this, "barcode!=null", Toast.LENGTH_SHORT).show();
        }
        if (result != null) {
            inactivityTimer.onActivity();
            playBeepSoundAndVibrate();
            // 扫码结果
            String resultString = result.getText();
            if (TextUtils.isEmpty(resultString)) {
                Toast.makeText(CaptureActivity.this, "扫码失败", Toast.LENGTH_SHORT).show();
            } else {
                //                TODO 拿到结果 做处理
                // Toast.makeText(CaptureActivity.this, "扫码成功", Toast.LENGTH_SHORT).show();
                Bundle bundle = getIntent().getExtras();
                bundle.putString(ConstantKey.INTENT_KEY_STRING, resultString);
                Intent intent = new Intent();
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finishActivity();
            }
        } else {
            Toast.makeText(CaptureActivity.this, "扫码失败", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 初始化相�?
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016年9月25日,下午5:54:10
     * <br> UpdateTime: 2016年9月25日,下午5:54:10
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param surfaceHolder
     */
    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);
        } catch (Exception ioe) {
            return;
        }
        if (handler == null) {
            handler = new CaptureActivityHandler(this, decodeFormats, characterSet);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_CHOSE_PHOTO:// 图库选择图片后返�?

                    if (data != null) {
                        Uri uri = data.getData();
                        if (uri.toString().indexOf("file://") != -1) {// 从非图库相册选择图片（文件浏览器�?
                            photoPath = uri.toString().replaceAll("file://", "");
                        } else {// 从图库相册�?�择图片,
                            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {// 系统版本4.4以上
                                photoPath = GetPathUtil.getPath(this, uri);
                            } else {// 系统版本4.4以下
                                photoPath = GetPathUtil.getDataColumn(this, uri, null, null);
                            }
                        }
                    }

                    // mProgress = new ProgressDialog(CaptureActivity.this);
                    // mProgress.setMessage("正在扫描...");
                    // mProgress.setCancelable(true);
                    // mProgress.show();

                    Result result = scanningImage(photoPath);
                    handleDecode(result, scanBitmap);

                    break;

            }
        }
    }

    /**
     * 扫描本地二维码图片的方法
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016年9月25日,下午5:54:20
     * <br> UpdateTime: 2016年9月25日,下午5:54:20
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param path
     *         本地图片路径
     */
    public Result scanningImage(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        Hashtable<DecodeHintType, String> hints = new Hashtable<DecodeHintType, String>();
        hints.put(DecodeHintType.CHARACTER_SET, "UTF8"); // 设置二维码内容的编码

        //        BitmapFactory.Options options = new BitmapFactory.Options();
        //        options.inJustDecodeBounds = true; // 先获取原大小
        //        scanBitmap = BitmapFactory.decodeFile(path, options);
        //        options.inJustDecodeBounds = false; // 获取新的大小
        //        int sampleSize = (int) (options.outHeight / (float) 200);
        //        if (sampleSize <= 0)
        //            sampleSize = 1;
        //        options.inSampleSize = sampleSize;
        //        scanBitmap = BitmapFactory.decodeFile(path, options);
        scanBitmap = ImageCompressUtil.getBitmap(path);
        //        try {
        //            scanBitmap = Picasso.with(CaptureActivity.this).load(path).get();
        //        } catch (IOException e) {
        //            LogUtil.e(e);
        //            e.printStackTrace();
        //        }
        RGBLuminanceSource source = new RGBLuminanceSource(scanBitmap);
        BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source));
        QRCodeReader reader = new QRCodeReader();
        try {
            return reader.decode(bitmap1, hints);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        surfaceView.setLayoutParams(new RelativeLayout.LayoutParams(width, height));
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;

    }

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public void drawViewfinder() {
        // 从相册选取图片，取景框没有bitmap，原因在这
        //        viewfinderView.drawViewfinder();

    }

    /**
     * 初始化媒体播放器
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016年9月25日,下午5:52:29
     * <br> UpdateTime: 2016年9月25日,下午5:52:29
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     */
    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            // The volume on STREAM_SYSTEM is not adjustable, and users found it
            // too loud,
            // so we now play on the music stream.
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(R.raw.beep);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e) {
                mediaPlayer = null;
            }
        }
    }

    /**
     * 扫描完成是播放声音加震动
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-4-9,上午10:06:56
     * <br> UpdateTime: 2016-4-9,上午10:06:56
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不�?.)
     */
    private void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    /**
     * 多媒体播放监�?
     */
    private OnCompletionListener beepListener = new OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };
}