package com.zimi.zimixing;

import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.multidex.MultiDex;

import com.zimi.zimixing.bean.LocationBean;
import com.zimi.zimixing.bean.ProvinceBean;
import com.zimi.zimixing.bean.UserInfoBean;
import com.zimi.zimixing.bean.UserInfoLoginBean;
import com.zimi.zimixing.bean.order.OrderBaseBean;
import com.zimi.zimixing.configs.ConstantKey;
import com.zimi.zimixing.utils.AppCrashUtil;
import com.zimi.zimixing.utils.FileUtil;
import com.zimi.zimixing.utils.LogUtil;
import com.zimi.zimixing.utils.NotificationUtil;
import com.zimi.zimixing.utils.PreferencesUtil;

import java.util.ArrayList;


/**
 * 全局公用Application类
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月24日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class BaseApplication extends Application {

    /** 全局TApplication  获取全局上下文，可用于文本、图片、sp数据的资源加载，不可用于视图级别的创建和展示 */
    protected static BaseApplication application;
    /** 定位信息bean */
    public LocationBean locationBean;
    /** 当前app处于start状态的activity数量 >0 则说明当前app在栈顶 */
    private int startCount;
    /** 用户信息bean */
    //private UserInfoBean userInfoBean;
    private UserInfoLoginBean userInfoBean;

    //**********************************************录单 编辑订单 数据缓存
    /** 录单 下拉框基础数据 */
    private OrderBaseBean orderBaseBean=null;
    /** 录单：现居地 省市区数据 */
    private ArrayList<ProvinceBean> provinceBeanArrayList = new ArrayList<>();

    public UserInfoLoginBean getUserInfoBean() {
        if (userInfoBean == null) {
            userInfoBean = (UserInfoLoginBean) PreferencesUtil.get(ConstantKey.SP_KEY_FILE_USER_INFO, null);
        }
        return userInfoBean;
    }

    public void setUserInfoBean(UserInfoLoginBean userInfoBean) {
        this.userInfoBean = userInfoBean;
        saveUserInfoBean();
    }

    public String getToken() {
        if (getUserInfoBean() != null) {
            return userInfoBean.getToken();
        }
        return "";
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static void saveUserInfoBean() {
        PreferencesUtil.put(ConstantKey.SP_KEY_FILE_USER_INFO, BaseApplication.getInstance().userInfoBean);
    }

    /**
     * 获取全局TApplication
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateAuthor: 叶青
     * <br> CreateTime: 2017/3/7 17:51
     * <br> UpdateAuthor: 叶青
     * <br> UpdateTime: 2017/3/7 17:51
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @return BaseApplication
     */
    public static BaseApplication getInstance() {
        if (application == null) {
            synchronized (BaseApplication.class) {
                if (application == null) {
                    application = new BaseApplication();
                }
            }
        }
        return application;
    }

    /**
     * 整个应用程序的初始入口函数
     * <p>
     * 本方法内一般用来初始化程序的全局数据，或者做应用的长数据保存取回操作
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016年12月24日, 下午2:12:17
     * <br> UpdateTime: 2016年12月24日, 下午2:12:17
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容, 若无修改可不写.)
     */
    @Override
    public void onCreate() {
        application = this;
        NotificationUtil.cancelAll();
        //applicationId要和包路径相同，才能正常重启APP WelcomeActivity
        initData("SplashActivity");
        super.onCreate();
    }

    /**
     * 初始化数据
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-12-29,下午10:47:29
     * <br> UpdateTime: 2016-12-29,下午10:47:29
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容, 若无修改可不写.)
     *
     * @param activityName
     *         程序出现异常后的跳转界面的名字 ;activityName为null时 直接退出app。
     */
    private void initData(String activityName) {
        locationBean = new LocationBean();

        // 错误日志捕捉工具WelcomeActivity
        Thread.setDefaultUncaughtExceptionHandler(AppCrashUtil.getAppExceptionHandler(activityName));
        // 本地文件构建
        FileUtil.createAllFile();
        //// 实例化GalleryFinal
        //initGalleryFinal(this);
        //// 实例化极光推送
        //JPushUtil.getInstance(this).setAlias("123456789amos21");
        //// 实例化LeakCanary
        //LeakCanary.install(this);

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                //// 添加Activity到堆栈
                //AppManagerUtil.getAppManager().addActivity(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {
                startCount = startCount + 1;
            }

            @Override
            public void onActivityResumed(Activity activity) {
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityStopped(Activity activity) {
                startCount = startCount - 1;
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                //// 移除堆栈Activity
                //AppManagerUtil.getAppManager().removeActivity(activity);
            }
        });
        registerReceiver();
    }

    public int getStartCount() {
        return startCount;
    }

    /**
     * 广播注册 监听Home键 监听锁屏
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateAuthor: 叶青
     * <br> CreateTime: 2017/4/5 14:32
     * <br> UpdateAuthor: 叶青
     * <br> UpdateTime: 2017/4/5 14:32
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     */
    private void registerReceiver() {
        IntentFilter filter = new IntentFilter();
        // filter.addAction(Intent.ACTION_SCREEN_ON);        // 监听开屏
        filter.addAction(Intent.ACTION_SCREEN_OFF);          // 监听锁屏
        filter.addAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);// 监听Home键
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF) || intent.getAction().equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
                    //                    if (getStartCount() > 0) {//APP在栈顶
                    LogUtil.i(System.currentTimeMillis() + "...." + intent.getAction() + "..." + getStartCount());
                    //                    }
                }
            }
        }, filter);
    }

    //    public OrderDetailBean getOrderDetailBean() {
    //        return orderDetailBean;
    //    }
    //
    //    public void setOrderDetailBean(OrderDetailBean orderDetailBean) {
    //        this.orderDetailBean = orderDetailBean;
    //    }

    public ArrayList<ProvinceBean> getProvinceBeanArrayList() {
        return provinceBeanArrayList;
    }

    public void setProvinceBeanArrayList(ArrayList<ProvinceBean> provinceBeanArrayList) {
        this.provinceBeanArrayList.addAll(provinceBeanArrayList);
    }

    public OrderBaseBean getOrderBaseBean() {
        return orderBaseBean;
    }

    public void setOrderBaseBean(OrderBaseBean orderBaseBean) {
        this.orderBaseBean = orderBaseBean;
    }

    //    @Override
    //    protected void attachBaseContext(Context base) {
    //        // 解决Error:warning: Ignoring InnerClasses attribute for an anonymous inner class
    //        // http://blog.csdn.net/lvshuchangyin/article/details/51803154
    //        super.attachBaseContext(base);
    //        MultiDex.install(this);
    //    }
    //
    //    private void initGalleryFinal(Context mContext) {
    //        //设置主题
    //        ThemeConfig theme = ThemeConfig.DEFAULT;
    //        //配置功能
    //        FunctionConfig functionConfig = new FunctionConfig.Builder()
    //                .setEnableCamera(true)
    //                .setEnableEdit(true)
    //                .setEnableCrop(true)
    //                .setEnableRotate(true)
    //                .setCropSquare(true)
    //                .setEnablePreview(true)
    //                .build();
    //        CoreConfig coreConfig = new CoreConfig.Builder(mContext, new GlideImageLoader(), theme)
    //                .setFunctionConfig(functionConfig)
    //                .setPauseOnScrollListener(new GlidePauseOnScrollListener(false, true))
    //                .build();
    //        GalleryFinal.init(coreConfig);
    //    }
}