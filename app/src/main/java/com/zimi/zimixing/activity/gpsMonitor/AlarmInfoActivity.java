package com.zimi.zimixing.activity.gpsMonitor;


import com.zimi.zimixing.R;
import com.zimi.zimixing.activity.BaseActivity;
import com.zimi.zimixing.adapter.AlarmInfoAdapter;
import com.zimi.zimixing.bean.gps_install.IndexTypeBean;
import com.zimi.zimixing.widget.AutoSizeGridViewAlarm;
import com.zimi.zimixing.widget.TitleViewPurple;

import java.util.ArrayList;


/**
 * created by tanjun
 * 报警信息
 */
public class AlarmInfoActivity extends BaseActivity {

    /** 标题 */
    TitleViewPurple title_view;
    /**数据内容*/
    AutoSizeGridViewAlarm gridView;

    /** 适配器的图标和文字列表 */
    private ArrayList<Integer> imgList = new ArrayList<>();
    private ArrayList<String> titleList = new ArrayList<>();

    ArrayList<IndexTypeBean> list = new ArrayList<>();

    IndexTypeBean bean;

    AlarmInfoAdapter mAdapter;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_alarm_info;
    }

    @Override
    protected void findViews() {
        title_view = findViewByIds(R.id.title_view);
        gridView = findViewByIds(R.id.grid_view);
    }

    @Override
    protected void initGetData() {
        initFuncList();
        setAdapterData();
    }

    @Override
    protected void init() {
        title_view.setLeftBtnImg();
        title_view.setTitle(R.string.call_police_info);
        mAdapter = new AlarmInfoAdapter(this, list);
        gridView.setAdapter(mAdapter);
    }

    @Override
    protected void widgetListener() {

    }

    /**
     * 对数据进行初始化 不要随意改变位置
     */
    private void initFuncList(){
        titleList.add("低电压报警");
        titleList.add("断电报警");
        titleList.add("位移报警");
        titleList.add("进行政区域报警");
        titleList.add("进行政区域报警");
        titleList.add("二押点停留报警");
        titleList.add("二押点久留报警");
        titleList.add("进二押点报警");
        titleList.add("出围栏报警");
        titleList.add("进围栏报警");

        imgList.add(R.drawable.low_elec);
        imgList.add(R.drawable.lose_elec);
        imgList.add(R.drawable.pos_move);
        imgList.add(R.drawable.in_alarm);
        imgList.add(R.drawable.out_alarm);
        imgList.add(R.drawable.stay);
        imgList.add(R.drawable.long_stay);
        imgList.add(R.drawable.two_point);
        imgList.add(R.drawable.out_fence_alarm);
        imgList.add(R.drawable.in_fence_alarm);
    }

    private void setAdapterData(){
        for (int i = 0; i < titleList.size(); i++) {
            bean = new IndexTypeBean();
            bean.setResName(titleList.get(i));
            bean.setResId(imgList.get(i));
            list.add(bean);
        }
    }

}
