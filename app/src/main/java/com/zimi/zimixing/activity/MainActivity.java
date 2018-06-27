package com.zimi.zimixing.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.RadioGroup;

import com.zimi.zimixing.R;
import com.zimi.zimixing.configs.ConstantKey;
import com.zimi.zimixing.configs.RequestCode;
import com.zimi.zimixing.fragment.IndexFragment;
import com.zimi.zimixing.fragment.MineFragment;
import com.zimi.zimixing.utils.AppManagerUtil;
import com.zimi.zimixing.utils.LogUtil;
import com.zimi.zimixing.utils.ToastUtil;
import com.zimi.zimixing.utils.UpdateVersionUtil;
import com.zimi.zimixing.widget.TitleView;

import java.util.ArrayList;
import java.util.List;


/**
 * 主界面
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class MainActivity extends BaseActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    //    /** 首页 */
    //    private final static int FRAGMENT_INDEX = 0;
    //    /** 展页 */
    //    private final static int FRAGMENT_ORDER = 1;
    //    /** 我的 */
    //    private final static int FRAGMENT_MINE = 2;
    /** 记录退出按下时间 */
    private long exitTime = 0;
    /** 标题栏 */
    private TitleView titleview;

    /** 首页 */
    private IndexFragment indexFragment = null;
    /** 展页 */
    //private OtherFragment otherFragment = null;
    /** 我的 */
    private MineFragment mineFragment = null;

    /** fragment模块集合 */
    private List<Fragment> listFragments = new ArrayList<>();
    /** 功能单选按钮组 */
    private RadioGroup radiogroup;
    /** true则说明未更新完成 */
    private boolean idUpdateAPK = false;
    private UpdateVersionUtil updateVersionUtil;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void findViews() {
        titleview = findViewByIds(R.id.title_view);
        radiogroup = findViewByIds(R.id.radiogroup);
    }

    @Override
    protected void initGetData() {
    }

    @Override
    protected void init() {
        //待申请的权限 耗时
        //        PermissionUtils.getInstance(permissionGrant).requestPermission(this, PermissionUtils.REQUEST_PERMISSIONS);
        //        PermissionUtils.getInstance(permissionGrant).requestPermission(this, PermissionUtils.REQUEST_PERMISSIONS[1]);
        titleview.setTitle(R.string.app_name);
        initFragment();
        //        checkUpdate();
        updateVersionUtil = new UpdateVersionUtil(this);
        updateVersionUtil.checkUpdate();
    }

    @Override
    protected void widgetListener() {

        // 功能菜单选择事件更改
        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.main_radio_btn_index) {
                    switchView(0);

                } /*else if (checkedId == R.id.main_radio_btn_order) {
                    switchView(1);

                } */else if (checkedId == R.id.main_radio_btn_mine) {
                    setTintResource(R.color.title_bg_color);
                    switchView(1);
                    //mineFragment.mainInfo();
                }
            }
            //            }
        });
    }

    /**
     * 初始化子模块
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime:  2016年3月29日,下午1:50:57
     * <br> UpdateTime:  2016年3月29日,下午1:50:57
     * <br> CreateAuthor:  叶青
     * <br> UpdateAuthor:  叶青
     * <br> UpdateInfo:  (此处输入修改内容,若无修改可不写.)
     */
    private void initFragment() {

        //otherFragment = new OtherFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ConstantKey.INTENT_KEY_TYPE, "type");
        //otherFragment.setArguments(bundle);
        indexFragment = new IndexFragment();
        mineFragment = new MineFragment();

        listFragments.add(indexFragment);
        //listFragments.add(otherFragment);
        listFragments.add(mineFragment);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.view_parent, indexFragment);
        //transaction.add(R.id.view_parent, otherFragment);
        transaction.add(R.id.view_parent, mineFragment);
        transaction.commit();

        switchView(0);
    }

    /**
     * 选择界面
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime:  2016年3月29日,下午5:18:33
     * <br> UpdateTime:  2016年3月29日,下午5:18:33
     * <br> CreateAuthor:  叶青
     * <br> UpdateAuthor:  叶青
     * <br> UpdateInfo:  (此处输入修改内容,若无修改可不写.)
     *
     * @param position
     *         fragment的索引值
     */
    public void switchView(int position) {
        try {

            if (listFragments.get(position).isVisible()) {
                return;
            }

            // 获取Fragment的操作对象
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            for (int i = 0; i < listFragments.size(); i++) {
                LogUtil.i(listFragments.get(i).isVisible() + "...");
                //                if (i != position) {
                transaction.hide(listFragments.get(i));
                //                }
            }

            transaction.show(listFragments.get(position));
            // 提交更改
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ***********************************返回键事件处理*********************************
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {
                // 要执行的事件
                // DialogUtil.showExitsDg(MainActivity.this);对话框退出
                // 判断2次点击事件时间
                if ((System.currentTimeMillis() - exitTime) > 2000) {
                    ToastUtil.showToast(MainActivity.this, getString(R.string.tips_exit_time));
                    exitTime = System.currentTimeMillis();
                } else {
                    // 极光推送 BUG1 退出应用 不设置空alias，下次启动app设置alias，极光后台会提示找不到alias
                    // 极光推送 BUG2 在任务管理器 手动干掉APP进程  下次启动app设置alias，极光后台会提示找不到alias;未找到合适的解决方案
                    // JPushUtil.getInstance(this).setEmptyAlias();
                    // 退出程序
                    AppManagerUtil.getAppManager().exitApp();
                    // finish();
                }

            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RequestCode.REQUEST_CODE_INSTALL_APK) {
            idUpdateAPK = true;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (updateVersionUtil.isMust() && idUpdateAPK) {
            AppManagerUtil.getAppManager().exitApp();
        }
    }

}