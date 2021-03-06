package com.zimi.zimixing.view.dialog;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.zimi.zimixing.R;
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
 * 选择开户行所在地弹出框试图
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2017/1/12
 */
public class SingleWheelView4Dialog extends BaseView {

    /** CustomDialog */
    private CustomDialog mDialog;

    private TextView txt_title;
    private View txt_cancel;
    private View txt_finish;
    private LoopView view_wheel;
    private OnDialogViewCallBack callBack;

    private ArrayList<String> dataList = new ArrayList<>();
    private int position;

    public SingleWheelView4Dialog(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setContentView(context);
    }

    public SingleWheelView4Dialog(Context context, AttributeSet attrs) {
        super(context, attrs);
        setContentView(context);
    }

    public SingleWheelView4Dialog(Context context) {
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
    public SingleWheelView4Dialog(Context context, CustomDialog dialog, OnDialogViewCallBack callBack, ArrayList<String> dataList, int position) {
        super(context);
        this.mDialog = dialog;
        this.callBack = callBack;
        this.position = position;
        this.dataList = dataList;
        setContentView(context);
    }
    //
    //    /**
    //     * 初始化
    //     * <p>
    //     * <br> Version: 1.0.0
    //     * <br> CreateTime: 2016年9月2日,上午11:31:29
    //     * <br> UpdateTime: 2016年9月2日,上午11:31:29
    //     * <br> CreateAuthor: 叶青
    //     * <br> UpdateAuthor: 叶青
    //     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
    //     *
    //     * @param context
    //     *         上下文
    //     */
    //    public void setContentView(Context context) {
    //        this.context = context;
    //        viewParent = LayoutInflater.from(this.context).inflate(getContentViewId(), null);
    //        //        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ScreenUtil.getScreenHightPx(context) / 4 * 3);
    //        //        this.addView(viewParent, params);
    //        this.addView(viewParent);
    //        findViews();
    //        widgetListener();
    //        init();
    //    }

    @Override
    protected int getContentViewId() {
        return R.layout.view_dialog_wheel_single;
    }

    @Override
    protected void findViews() {
        txt_title = findViewByIds(R.id.txt_title);
        txt_cancel = findViewByIds(R.id.txt_cancel);
        txt_finish = findViewByIds(R.id.txt_finish);
        view_wheel = findViewByIds(R.id.view_wheel);
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

                int selectedItem = view_wheel.getSelectedItem();

                Map<String, Object> hashMap = new HashMap<>();
                hashMap.put("position", selectedItem);
                LogUtil.i("----->>>>> " + selectedItem + ":" + dataList.get(selectedItem));
                callBack.onResult(hashMap);
                mDialog.dismiss();
            }
        });
    }

    //    public void updataView(){
    //        init();
    //    }

    @Override
    protected void init() {
        txt_title.setVisibility(GONE);

        //设置是否循环播放
        view_wheel.setNotLoop();
        //滚动监听
        view_wheel.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                LogUtil.i("onItemSelected " + dataList.get(index) + "-->>" + "item " + index);
            }
        });
        //设置原始数据
        view_wheel.setItems(dataList);
        //设置初始位置
        view_wheel.setInitPosition(position);
    }
}
