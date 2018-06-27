package com.zimi.zimixing.fragment;

import android.os.Bundle;

import com.zimi.zimixing.R;
import com.zimi.zimixing.adapter.TestListAdapter;
import com.zimi.zimixing.bean.ResponseBean;
import com.zimi.zimixing.biz.TestBiz;
import com.zimi.zimixing.configs.ConstantKey;
import com.zimi.zimixing.executor.BaseTask;
import com.zimi.zimixing.executor.RequestExecutor;
import com.zimi.zimixing.utils.LogUtil;
import com.zimi.zimixing.widget.TitleView;
import com.zimi.zimixing.widget.pullview.PullToRefreshBase;
import com.zimi.zimixing.widget.pullview.PullToRefreshListView;

import java.util.ArrayList;
import java.util.Collections;

/**
 * 其他
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class OtherFragment extends BaseFragment {

    /** 标题栏 */
    private TitleView titleview;
    private PullToRefreshListView lv_base;

    public OtherFragment() {
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_other;
    }

    @Override
    protected void findViews() {
        lv_base = findViewByIds(R.id.lv_base);
        titleview = findViewByIds(R.id.title_view);
    }

    @Override
    protected void widgetListener() {
        //        titleview.setRightBtnImg(R.drawable.icon_message_black, new View.OnClickListener() {
        //
        //            @Override
        //            public void onClick(View v) {
        //                IntentUtil.gotoActivity(getActivity(), MainActivityDemo.class);
        //            }
        //        });
        //        titleview.setLeftBtnImg(R.drawable.icon_message_black, new View.OnClickListener() {
        //
        //            @Override
        //            public void onClick(View v) {
        //            }
        //        });

        lv_base.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener() {
            @Override
            public void onPullToDownRefresh() {
                lv_base.setClickCompleteAndAll();
                checkUpdate();
            }

            @Override
            public void onPullToUpRefresh() {
                checkUpdate();
            }
        });
    }

    @Override
    protected void init() {
        titleview.setTitle(R.string.activity_main_show);
        initListView();
    }

    @Override
    public void initGetData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            String type = getArguments().getString(ConstantKey.INTENT_KEY_TYPE, "");
            LogUtil.i(this.getClass().getName() + " Bundle String content == " + type);
        }
    }

    private void initListView() {

        String string = "http://godive.cn/seaguest_server/diveres/imgs/20161221/12673-20161221200427966-0.png"
                + ",http://godive.cn/seaguest_server/diveres/headimgs/20170119/13094-201701191718091070.png"
                + ",http://godive.cn/seaguest_server/diveres/headimgs/20161228/12763-20161228103510014.jpg"
                + ",http://godive.cn/seaguest_server/diveres/headimgs/20170117/13057-201701171321582230.png"
                + ",http://godive.cn/seaguest_server/diveres/headimgs/20161221/12673-20161221171337284.jpg"
                + ",http://godive.cn/seaguest_server/diveres/imgs/20170228/201702281758484730-12529.png";

        String[] strs = string.split(",");
        ArrayList<String> arrayList = new ArrayList<>();
        Collections.addAll(arrayList, strs);

        TestListAdapter adapter = new TestListAdapter(getActivity(), arrayList);
        lv_base.getRefreshableView().setAdapter(adapter);
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
    private void checkUpdate() {
        RequestExecutor.addTask(new BaseTask() {

            @Override
            public ResponseBean sendRequest() {
                return TestBiz.testPost1();
            }

            @Override
            public void onSuccess(ResponseBean result) {
                lv_base.onRefreshComplete();
            }

            @Override
            public void onFail(ResponseBean result) {
                lv_base.onRefreshComplete();
            }
        });
    }
}
