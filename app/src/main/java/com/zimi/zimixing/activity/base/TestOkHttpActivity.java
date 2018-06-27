package com.zimi.zimixing.activity.base;

import android.graphics.Bitmap;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.zimi.zimixing.R;
import com.zimi.zimixing.activity.BaseActivity;
import com.zimi.zimixing.bean.ResponseBean;
import com.zimi.zimixing.biz.SystemBiz;
import com.zimi.zimixing.biz.TestBiz;
import com.zimi.zimixing.configs.ConfigServer;
import com.zimi.zimixing.executor.BaseTask;
import com.zimi.zimixing.executor.RequestExecutor;
import com.zimi.zimixing.utils.LogUtil;
import com.zimi.zimixing.utils.ToastUtil;
import com.zimi.zimixing.utils.http.HttpOkUtil;
import com.zimi.zimixing.utils.http.HttpRequestCallBack;
import com.zimi.zimixing.widget.TitleView;

import java.io.IOException;

/**
 * 主界面
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class TestOkHttpActivity extends BaseActivity {

    /** 记录退出按下时间 */
    private long exitTime = 0;
    /** 标题栏 */
    public TitleView titleview;
    public View item0;
    public View item1;
    public View item2;
    public View item3;
    public View item4;
    public View item5;
    public View item6;
    public ImageView img;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_item10;
    }

    @Override
    protected void findViews() {
        activity = this;
        titleview = (TitleView) findViewById(R.id.title_view);
        item0 = findViewById(R.id.item0);
        item1 = findViewById(R.id.item1);
        item2 = findViewById(R.id.item2);
        item3 = findViewById(R.id.item3);
        item4 = findViewById(R.id.item4);
        item5 = findViewById(R.id.item5);
        item6 = findViewById(R.id.item6);
        img = findViewByIds(R.id.img);
    }

    @Override
    protected void initGetData() {

    }

    @Override
    protected void init() {
        titleview.setTitle("okHttp");
    }


    @Override
    protected void widgetListener() {

        item0.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                RequestExecutor.addTask(new BaseTask() {
                    @Override
                    public ResponseBean sendRequest() {
                        return TestBiz.testGet();
                    }

                    @Override
                    public void onSuccess(ResponseBean result) {

                    }

                    @Override
                    public void onFail(ResponseBean result) {

                    }
                });
            }
        });

        item1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                RequestExecutor.addTask(new BaseTask() {
                    @Override
                    public ResponseBean sendRequest() {
                        return TestBiz.testPost();
                    }

                    @Override
                    public void onSuccess(ResponseBean result) {
                        ToastUtil.showToast(TestOkHttpActivity.this, result.getInfo());
                    }

                    @Override
                    public void onFail(ResponseBean result) {
                        ToastUtil.showToast(TestOkHttpActivity.this, result.getInfo());
                    }
                });
            }
        });

        item2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                RequestExecutor.addTask(new BaseTask() {
                    @Override
                    public ResponseBean sendRequest() {
                        return TestBiz.testDownLoadFile();
                    }

                    @Override
                    public void onSuccess(ResponseBean result) {
                        ToastUtil.showToast(TestOkHttpActivity.this, result.getInfo());
                    }

                    @Override
                    public void onFail(ResponseBean result) {
                        ToastUtil.showToast(TestOkHttpActivity.this, result.getInfo());
                    }
                });
            }
        });

        item3.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                RequestExecutor.addTask(new BaseTask() {
                    @Override
                    public ResponseBean sendRequest() {
                        return TestBiz.testUpLoadFile();
                    }

                    @Override
                    public void onSuccess(ResponseBean result) {

                    }

                    @Override
                    public void onFail(ResponseBean result) {

                    }
                });

                //                RequestExecutor.addTask(new Runnable() {
                //                    @Override
                //                    public void run() {
                //                        try {
                //                            new HttpUtil().uploadFile(string, params, (HashMap<String, File>) Files);
                //                            new HttpUtil().downloadFile("http://fzd-10017606.cos.myqcloud.com/pkg/app-fzdV3_2_0.apk", ConfigFile.PATH_LOG + "app-fzdV3_2_01.apk");
                //                            new HttpUtil().downloadFileWithPro("http://fzd-10017606.cos.myqcloud.com/pkg/app-fzdV3_2_0.apk", ConfigFile.PATH_LOG + "app-fzdV3_2_02.apk", new OnDownLoadCallBack() {
                //                                @Override
                //                                public void ResultProgress(int press) {
                //
                //                                }
                //                            });
                //                        } catch (IOException e) {
                //                            e.printStackTrace();
                //                        }
                //                    }
                //                });
            }
        });

        item4.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                RequestExecutor.addTask(new BaseTask() {
                    @Override
                    public ResponseBean sendRequest() {
                        HttpOkUtil.getInstance().cancel("http://fzd-10017606.cos.myqcloud.com/pkg/app-fzdV3_2_0.apk");
                        Bitmap bitmap = null;
                        try {
                            bitmap = HttpOkUtil.getInstance().loadImage("http://godive.cn/seaguest_server/diveres/headimgs/20170226/13647-201702261047360710.png", R.drawable.img_default_grey);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        LogUtil.i("下载图片成功");
                        if (bitmap != null) {
                            return new ResponseBean(ConfigServer.RESPONSE_STATUS_SUCCESS, "", bitmap);
                        } else {
                            return new ResponseBean(ConfigServer.RESPONSE_STATUS_FAIL, "", "");
                        }
                    }

                    @Override
                    public void onSuccess(ResponseBean result) {
                        Bitmap bitmap = (Bitmap) result.getObject();
                        img.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onFail(ResponseBean result) {

                    }
                });
            }
        });

        item5.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                RequestExecutor.addTask(new BaseTask() {
                    @Override
                    public ResponseBean sendRequest() {
                        //                        new HttpUtil().uploadFile(string, params, (HashMap<String, File>) Files);
                        //                        new HttpUtil().downloadFile("http://fzd-10017606.cos.myqcloud.com/pkg/app-fzdV3_2_0.apk", ConfigFile.PATH_LOG + "app-fzdV3_2_01.apk");
                        //                        new HttpUtil().downloadFileWithPro("http://fzd-10017606.cos.myqcloud.com/pkg/app-fzdV3_2_0.apk", ConfigFile.PATH_LOG + "app-fzdV3_2_02.apk", new OnDownLoadCallBack() {
                        SystemBiz.getIpDetail();
                        SystemBiz.getLatAndLng();
                        return new ResponseBean();
                    }

                    @Override
                    public void onSuccess(ResponseBean result) {

                    }

                    @Override
                    public void onFail(ResponseBean result) {

                    }
                });
            }
        });

        item6.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                TestBiz.testPost(new HttpRequestCallBack() {
                    @Override
                    public void onSuccess(ResponseBean result) {
                        LogUtil.i("...");
                        img.setImageResource(R.drawable.img_default_grey_base);
                        ToastUtil.showToast(TestOkHttpActivity.this,result.getInfo());
                    }

                    @Override
                    public void onFail(ResponseBean result) {
                        LogUtil.i("...");
                        img.setImageResource(R.drawable.img_load_error_base);
                        ToastUtil.showToast(TestOkHttpActivity.this,result.getInfo());
                    }
                });

                TestBiz.testGet(new HttpRequestCallBack() {
                    @Override
                    public void onSuccess(ResponseBean result) {
                        LogUtil.i("...");
                        img.setImageResource(R.drawable.img_default_grey_base);
                        ToastUtil.showToast(TestOkHttpActivity.this,result.getInfo());
                    }

                    @Override
                    public void onFail(ResponseBean result) {
                        LogUtil.i("...");
                        img.setImageResource(R.drawable.img_load_error_base);
                        ToastUtil.showToast(TestOkHttpActivity.this,result.getInfo());
                    }
                });

            }
        });
    }

    public static TestOkHttpActivity activity;

    public static TestOkHttpActivity getActivity() {
        return activity;
    }
}