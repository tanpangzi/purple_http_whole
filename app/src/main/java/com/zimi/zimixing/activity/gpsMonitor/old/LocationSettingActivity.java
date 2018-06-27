package com.zimi.zimixing.activity.gpsMonitor.old;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;

import com.zimi.zimixing.R;
import com.zimi.zimixing.fragment.gpsMonitor.DeviceLocationAuthorityFragment;
import com.zimi.zimixing.fragment.gpsMonitor.ModifyUploadTimeFragment;
import com.zimi.zimixing.fragment.gpsMonitor.SettingFragment;
import com.zimi.zimixing.widget.TitleView;


public class LocationSettingActivity extends com.zimi.zimixing.activity.BaseActivity {

    private TitleView title_view;
    private FragmentManager fm;
    private SettingFragment stf;
    private ModifyUploadTimeFragment mutf;
    private DeviceLocationAuthorityFragment dlaf;
    private String imeiId;


    private static String CURRENT_TAG = "";
    private static String STF_TAG = "SettingFragment";
    private static String MUTF_TAG = "ModifyUploadTimeFragment";
    private static String DLAF_TAG = "DeviceLocationAuthorityFragment";


    @Override
    protected int getContentViewId() {
        return R.layout.activity_location_setting;
    }

    @Override
    protected void findViews() {
        title_view = findViewByIds(R.id.title_view);
    }

    @Override
    protected void initGetData() {
        imeiId = getIntent().getStringExtra("imeiId");
    }

    @Override
    protected void init() {
        setTitleStr("配置");

        initFragment();
    }

    @Override
    protected void widgetListener() {
        title_view.setLeftBtnImg(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });
    }

    public void setTitleStr(String title) {
        title_view.setTitle(title);
    }

    private void back() {
        setTitleStr("配置");
        if (CURRENT_TAG.equals(STF_TAG)) {
            finishActivity();
        } else if (CURRENT_TAG.equals(MUTF_TAG)) {
            switchContent(mutf, stf, STF_TAG);
        } else if (CURRENT_TAG.equals(DLAF_TAG)) {
            switchContent(dlaf, stf, STF_TAG);
        }
    }

    private void initFragment() {
        int type = getIntent().getIntExtra("type", -1);
        Bundle bundle = new Bundle();
        bundle.putString("imeiId", imeiId);
        bundle.putInt("type", type);

        stf = new SettingFragment();
        stf.setArguments(bundle);

        mutf = new ModifyUploadTimeFragment();
        mutf.setArguments(bundle);

        dlaf = new DeviceLocationAuthorityFragment();
        dlaf.setArguments(bundle);
        changeFragment(true, stf, STF_TAG);
    }

    public void showModifyUploadTimeFragment() {
        setTitleStr("上传修改时间");
        switchContent(stf, mutf, MUTF_TAG);
    }

    public void showDeviceLocationAuthorityFragment() {
        setTitleStr("开启设备定位权限");
        switchContent(stf, dlaf, DLAF_TAG);
    }

    // ***********************************返回键事件处理*********************************
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {
                // 要执行的事件
                back();
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }


    private FragmentManager getMyFragmentManager() {
        if (fm == null) {
            fm = getSupportFragmentManager();
        }
        return fm;
    }

    private void changeFragment(boolean isadd, Fragment f, String tag) {
        if (f != null) {
            FragmentTransaction ft = getMyFragmentManager().beginTransaction();
            CURRENT_TAG = tag;
            if (isadd) {
                ft.add(R.id.location_setting, f, tag);
            } else {
                ft.replace(R.id.location_setting, f, tag);
                ft.addToBackStack(null);
            }
            ft.commit();
        }
    }

    public void switchContent(Fragment from, Fragment to, String tag) {
        if (null != from && null != to) {
            FragmentTransaction transaction = getMyFragmentManager().beginTransaction();
            if (!to.isAdded()) { // 先判断是否被add过
                transaction.hide(from)
                        .add(R.id.location_setting
                                , to, tag).commit(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
            }
            CURRENT_TAG = tag;
        }
    }
}
