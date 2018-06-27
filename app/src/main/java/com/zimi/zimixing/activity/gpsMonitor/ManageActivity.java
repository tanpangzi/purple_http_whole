package com.zimi.zimixing.activity.gpsMonitor;

import android.view.View;

import com.zimi.zimixing.R;
import com.zimi.zimixing.activity.BaseActivity;
import com.zimi.zimixing.utils.IntentUtil;
import com.zimi.zimixing.utils.OtherUtils;
import com.zimi.zimixing.widget.TitleView;

/**
 * GPS监控 分组授权管理
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class ManageActivity extends BaseActivity {

    /** 标题栏 */
    private TitleView title_view;
    private View txt_group_manage;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_manage;
    }

    @Override
    protected void findViews() {
        title_view = findViewByIds(R.id.title_view);
        txt_group_manage = findViewByIds(R.id.txt_group_manage);

    }

    @Override
    public void initGetData() {
        //        Bundle bundle = this.getIntent().getExtras();
        //        if (bundle != null) {
        //            storyId = bundle.getString(ConstantKey.INTENT_KEY_ID, "");
        //        }
    }

    @Override
    protected void init() {
        title_view.setTitle("分组授权管理");
    }

    @Override
    protected void widgetListener() {
        title_view.setLeftBtnImg();

        txt_group_manage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (OtherUtils.getInstance().isFastClick(view)) {
                    return;
                }
                IntentUtil.gotoActivity(ManageActivity.this, GroupManageActivity.class, ManageActivity.this.getIntent().getExtras());
            }
        });

    }
}