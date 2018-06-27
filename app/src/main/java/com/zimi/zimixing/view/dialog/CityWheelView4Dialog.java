package com.zimi.zimixing.view.dialog;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.zimi.zimixing.R;
import com.zimi.zimixing.bean.ProvinceBean;
import com.zimi.zimixing.interf.OnDialogViewCallBack;
import com.zimi.zimixing.utils.LogUtil;
import com.zimi.zimixing.utils.dialog.CustomDialog;
import com.zimi.zimixing.view.BaseView;
import com.zimi.zimixing.widget.loopview.LoopView;
import com.zimi.zimixing.widget.loopview.OnItemSelectedListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * 选择所在地弹出框试图
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2017/1/12
 */
public class CityWheelView4Dialog extends BaseView {

    /** CustomDialog */
    private CustomDialog mDialog;

    private TextView txt_title;
    private View txt_cancel;
    private View txt_finish;
    private LoopView view_province;
    private LoopView view_city;
    private LoopView view_district;
    private OnDialogViewCallBack callBack;

    private ArrayList<ProvinceBean> provinceBeanArrayList = new ArrayList<>();

    //    public CityWheelView4Dialog(Context context, AttributeSet attrs, int defStyle) {
    //        super(context, attrs, defStyle);
    //        setContentView(context);
    //    }
    //
    //    public CityWheelView4Dialog(Context context, AttributeSet attrs) {
    //        super(context, attrs);
    //        setContentView(context);
    //    }
    //
    //    public CityWheelView4Dialog(Context context) {
    //        super(context);
    //        setContentView(context);
    //    }

    /**
     * 构造方法
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateAuthor: 叶青
     * <br> CreateTime: 2017/1/14 13:05
     * <br> UpdateAuthor: 叶青
     * <br> UpdateTime: 2017/1/14 13:05
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param context
     *         上下文
     * @param dialog
     *         CustomDialog
     */
    public CityWheelView4Dialog(Context context, CustomDialog dialog, OnDialogViewCallBack callBack, ArrayList<ProvinceBean> provinceBeanArrayList) {
        super(context);
        this.mDialog = dialog;
        this.callBack = callBack;
        this.provinceBeanArrayList = provinceBeanArrayList;
        setContentView(context);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.view_dialog_wheel_city;
    }

    @Override
    protected void findViews() {
        txt_title = findViewByIds(R.id.txt_title);
        txt_cancel = findViewByIds(R.id.txt_cancel);
        txt_finish = findViewByIds(R.id.txt_finish);
        view_province = findViewByIds(R.id.view_province);
        view_city = findViewByIds(R.id.view_city);
        view_district = findViewByIds(R.id.view_district);

    }

    @Override
    protected void widgetListener() {
        txt_cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
            }
        });

        txt_finish.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                int province = view_province.getSelectedItem();
                int city = view_city.getSelectedItem();
                int district = view_district.getSelectedItem();
                Map<String, Object> hashMap = new HashMap<>();
                hashMap.put("province", province);
                hashMap.put("city", city);
                hashMap.put("district", district);
                LogUtil.i("----->>>>> " + province + ":" + city + ":" + district);
                callBack.onResult(hashMap);
                mDialog.dismiss();
            }
        });
    }

    ArrayList<String> provinceNameList = new ArrayList<>();
    ArrayList<String> cityNameList = new ArrayList<>();
    ArrayList<String> districtNameList = new ArrayList<>();

    @Override
    protected void init() {
        txt_title.setVisibility(GONE);

        if (provinceBeanArrayList == null || provinceBeanArrayList.size() <= 0) {
            return;
        }

        for (int i = 0; i < provinceBeanArrayList.size(); i++) {
            provinceNameList.add(provinceBeanArrayList.get(i).getName());
        }

        //设置是否循环播放
        view_province.setNotLoop();
        //滚动监听
        view_province.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                LogUtil.i("onItemSelected " + provinceNameList.get(index) + "-->>" + "item " + index);
                initCity();
            }
        });
        //设置原始数据
        view_province.setItems(provinceNameList);
        //设置初始位置
        view_province.setInitPosition(0);

        initCity();
        initDistrict();
    }

    private void initCity() {
        cityNameList.clear();
        int province = view_province.getSelectedItem();
        for (int i = 0; i < provinceBeanArrayList.get(province).getCityList().size(); i++) {
            cityNameList.add(provinceBeanArrayList.get(province).getCityList().get(i).getName());
        }

        if (cityNameList.size() <= 0) {
            view_city.setItems(null);
            return;
        }
        //设置是否循环播放
        view_city.setNotLoop();
        //滚动监听
        view_city.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                if (index >= cityNameList.size()) {
                    view_city.setCurrentPosition(0);
                }
                LogUtil.i("onItemSelected " + "-->>" + "item " + index);
                initDistrict();
            }
        });
        //设置原始数据
        view_city.setItems(cityNameList);
        //设置初始位置
        view_city.setInitPosition(0);
        //        //设置当前位置
        //        if (view_city.getSelectedItem() >= cityNameList.size()) {
        //            //设置当前位置
        //            view_city.setCurrentPosition(cityNameList.size() - 1);
        //        }
        view_city.setCurrentPosition(0);
        initDistrict();
    }

    private void initDistrict() {
        districtNameList.clear();
        int province = view_province.getSelectedItem();
        int city = view_city.getSelectedItem();
        if (city >= provinceBeanArrayList.get(province).getCityList().size()) {
            city = 0;
        }
        for (int i = 0; i < provinceBeanArrayList.get(province).getCityList().get(city).getDistrictList().size(); i++) {
            districtNameList.add(provinceBeanArrayList.get(province).getCityList().get(city).getDistrictList().get(i).getName());
        }
        if (districtNameList.size() <= 0) {
            //设置原始数据
            view_district.setItems(null);
            return;
        }
        //设置是否循环播放
        view_district.setNotLoop();
        //滚动监听
        view_district.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                if (index >= districtNameList.size()) {
                    view_district.setCurrentPosition(0);
                }
                LogUtil.i("onItemSelected " + districtNameList.get(index) + "-->>" + "item " + index);
            }
        });
        //设置原始数据
        view_district.setItems(districtNameList);
        //设置初始位置
        view_district.setInitPosition(0);
        //        //设置当前位置
        //        if (view_district.getSelectedItem() >= districtNameList.size()) {
        //            //设置当前位置
        //            view_district.setCurrentPosition(districtNameList.size() - 1);
        //        }
        view_district.setCurrentPosition(0);
    }
}
