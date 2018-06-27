package com.zimi.zimixing.view.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.zimi.zimixing.R;
import com.zimi.zimixing.interf.OnDialogViewCallBack;
import com.zimi.zimixing.utils.DateUtil;
import com.zimi.zimixing.utils.LogUtil;
import com.zimi.zimixing.utils.dialog.CustomDialog;
import com.zimi.zimixing.view.BaseView;
import com.zimi.zimixing.widget.loopview.LoopView;
import com.zimi.zimixing.widget.loopview.OnItemSelectedListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


/**
 * 选择所在地弹出框试图
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2017/1/12
 */
public class TimeWheelView4Dialog extends BaseView {

    /** CustomDialog */
    private CustomDialog mDialog;

    private TextView txt_title;
    private View txt_cancel;
    private View txt_finish;
    private LoopView view_year;
    private LoopView view_month;
    private LoopView view_day;
    private String minYear = "";
    private OnDialogViewCallBack callBack;

    public TimeWheelView4Dialog(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setContentView(context);
    }

    public TimeWheelView4Dialog(Context context, AttributeSet attrs) {
        super(context, attrs);
        setContentView(context);
    }

    public TimeWheelView4Dialog(Context context) {
        super(context);
        setContentView(context);
    }

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
    public TimeWheelView4Dialog(Context context, CustomDialog dialog, OnDialogViewCallBack callBack, String minYear) {
        super(context);
        this.mDialog = dialog;
        this.callBack = callBack;
        this.minYear = minYear;
        setContentView(context);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.view_dialog_wheel_time;
    }

    @Override
    protected void findViews() {
        txt_title = findViewByIds(R.id.txt_title);
        txt_cancel = findViewByIds(R.id.txt_cancel);
        txt_finish = findViewByIds(R.id.txt_finish);
        view_year = findViewByIds(R.id.view_province);
        view_month = findViewByIds(R.id.view_city);
        view_day = findViewByIds(R.id.view_district);
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
                int province = view_year.getSelectedItem();
                int city = view_month.getSelectedItem();
                int district = view_day.getSelectedItem();

                Map<String, Object> hashMap = new HashMap<>();
                /*String date = arrayYear.get(province).replace("年", "") +
                        "-" + arrayMonth.get(city).replace("月", "") +
                        "-" + arrayDay.get(district).replace("日", "");*/

                StringBuilder builder = new StringBuilder();
                builder.append( arrayYear.get(province).replace("年", ""))
                        .append("-")
                        .append(arrayMonth.get(city).replace("月", ""))
                        .append("-")
                        .append(arrayDay.get(district).replace("日", ""));

                String date = builder.toString();

                LogUtil.i("----->>>>> " + date);
                if (DateUtil.compareDate4Today(date)) {
                    date = DateUtil.getDate(DateUtil.DATE_FORMAT_YMD);
                }
                hashMap.put("date", date);
                LogUtil.i("----->>>>> " + date);
                callBack.onResult(hashMap);
                mDialog.dismiss();
            }
        });
    }

    private ArrayList<String> arrayYear = new ArrayList<>();
    private ArrayList<String> arrayMonth = new ArrayList<>();
    private ArrayList<String> arrayDay = new ArrayList<>();

    @Override
    protected void init() {
        txt_title.setVisibility(GONE);

        int minYearNew = 1970;
        if (!TextUtils.isEmpty(minYear)) {
            minYearNew = Integer.parseInt(minYear);
        }

        Calendar calendar = Calendar.getInstance();
        final int yearCur = calendar.get(Calendar.YEAR);
        arrayYear.add(minYearNew + "年");
        for (int i = 1; i <= yearCur - minYearNew; i++) {
            arrayYear.add((minYearNew + i) + "年");
        }

        //设置是否循环播放
        view_year.setNotLoop();
        //滚动监听
        view_year.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                LogUtil.i("onItemSelected " + arrayYear.get(index) + "-->>" + "item " + index);
                initMonth();
            }
        });
        //设置原始数据
        view_year.setItems(arrayYear);
        //设置初始位置
        view_year.setInitPosition(arrayYear.size() - 1);


        calendar = Calendar.getInstance();
        int monthCur = calendar.get(Calendar.MONTH) + 1;
        for (int i = 1; i <= monthCur; i++) {
            if (i < 10) {
                arrayMonth.add("0" + i + "月");
            } else {
                arrayMonth.add(i + "月");
            }
        }

        //设置是否循环播放
        view_month.setNotLoop();
        //滚动监听
        view_month.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                LogUtil.i("item " + index);
                initDay();
            }
        });
        //设置原始数据
        view_month.setItems(arrayMonth);
        //设置初始位置
        view_month.setInitPosition(arrayMonth.size() - 1);


        calendar = Calendar.getInstance();
        int dayCur = calendar.get(Calendar.DAY_OF_MONTH);
        for (int i = 1; i <= dayCur; i++) {
            if (i < 10) {
                arrayDay.add("0" + i + "日");
            } else {
                arrayDay.add(i + "日");
            }
        }

        //设置是否循环播放
        view_day.setNotLoop();
        //滚动监听
        view_day.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                LogUtil.i("onItemSelected " + "-->>" + "item " + index);
            }
        });
        //设置原始数据
        view_day.setItems(arrayDay);
        //设置初始位置
        view_day.setInitPosition(arrayDay.size() - 1);
    }

    private void initMonth() {
        int year = view_year.getSelectedItem();
        arrayMonth.clear();
        if (year == arrayYear.size() - 1) {
            Calendar calendar = Calendar.getInstance();
            int monthCur = calendar.get(Calendar.MONTH) + 1;
            for (int i = 1; i <= monthCur; i++) {
                if (i < 10) {
                    arrayMonth.add("0" + i + "月");
                } else {
                    arrayMonth.add(i + "月");
                }
            }
        } else {
            for (int i = 1; i < 13; i++) {
                if (i < 10) {
                    arrayMonth.add("0" + i + "月");
                } else {
                    arrayMonth.add(i + "月");
                }
            }
        }

        //设置是否循环播放
        view_month.setNotLoop();
        //滚动监听
        view_month.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                LogUtil.i("item " + index);
                initDay();
            }
        });
        //设置原始数据
        view_month.setItems(arrayMonth);
        //设置初始位置
        //        view_month.setInitPosition(0);
        //        view_month.setItemsVisibleCount(7);
        //设置当前位置
        if (view_month.getSelectedItem() >= arrayMonth.size()) {
            //设置当前位置
            view_month.setCurrentPosition(arrayMonth.size() - 1);
        }
        //        view_month.setCurrentPosition(0);
        initDay();
    }

    private void initDay() {
        int year = view_year.getSelectedItem();
        int month = view_month.getSelectedItem();
        arrayDay.clear();
        Calendar calendar = Calendar.getInstance();
        if (year == arrayYear.size() - 1 && month >= arrayMonth.size() - 1) {
            int dayCur = calendar.get(Calendar.DAY_OF_MONTH);
            for (int i = 1; i <= dayCur; i++) {
                if (i < 10) {
                    arrayDay.add("0" + i + "日");
                } else {
                    arrayDay.add(i + "日");
                }
            }
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                String date = "";
                if (year >= arrayYear.size()) {
                    date = arrayYear.get(arrayYear.size() - 1).replace("年", "");
                } else {
                    date = arrayYear.get(year).replace("年", "");
                }

                if (month >= arrayMonth.size()) {
                    date = date + "-" + arrayMonth.get(arrayMonth.size() - 1).replace("月", "");
                } else {
                    date = date + "-" + arrayMonth.get(month).replace("月", "");
                }

                date = date + "-1";
                calendar.setTime(sdf.parse(date));
            } catch (Exception e) {
                e.printStackTrace();
            }

            int day = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            for (int i = 1; i <= day; i++) {
                if (i < 10) {
                    arrayDay.add("0" + i + "日");
                } else {
                    arrayDay.add(i + "日");
                }
            }
        }

        //设置是否循环播放
        view_day.setNotLoop();
        //滚动监听
        view_day.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                LogUtil.i("onItemSelected " + "-->>" + "item " + index);
            }
        });
        //设置原始数据
        view_day.setItems(arrayDay);
        //设置初始位置
        //        view_day.setInitPosition(0);
        if (view_day.getSelectedItem() >= arrayDay.size()) {
            //设置当前位置
            view_day.setCurrentPosition(arrayDay.size() - 1);
        }
        //        view_day.setCurrentPosition(0);
    }
}
