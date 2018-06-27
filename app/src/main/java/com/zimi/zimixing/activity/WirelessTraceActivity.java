package com.zimi.zimixing.activity;


import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.zimi.zimixing.R;
import com.zimi.zimixing.utils.ToastUtil;
import com.zimi.zimixing.widget.ContainsEmojiEditText;
import com.zimi.zimixing.widget.TitleViewPurple;

/**
 * 无线设备追踪
 * created by tanjun
 */
public class WirelessTraceActivity extends BaseActivity {

    TitleViewPurple title_view;
    /** 回传间隔 */
    ContainsEmojiEditText et_halt;
    /** 追踪时间 */
    ContainsEmojiEditText et_time;

    Button btn_confirm;
    @Override
    protected int getContentViewId() {
        return R.layout.activity_wireless_mode;
    }

    @Override
    protected void findViews() {
        title_view = (TitleViewPurple) findViewById(R.id.title_view);
        et_halt = (ContainsEmojiEditText) findViewById(R.id.et_halt);
        et_time = (ContainsEmojiEditText) findViewById(R.id.et_time);
        btn_confirm = (Button) findViewById(R.id.btn_confirm);
    }

    @Override
    protected void initGetData() {
    }

    @Override
    protected void init() {
        title_view.setLeftBtnImg();
        title_view.setTitle(R.string.wireless_trace);
    }

    @Override
    protected void widgetListener() {
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String backTime = et_halt.getText().toString().trim();
                String traceTime = et_time.getText().toString().trim();

                if (TextUtils.isEmpty(backTime)){
                    ToastUtil.showToast(WirelessTraceActivity.this, getString(R.string.empty_back_time));
                    return;
                }

                if (TextUtils.isEmpty(traceTime)){
                    ToastUtil.showToast(WirelessTraceActivity.this, getString(R.string.empty_trace_time));
                    return;
                }


            }
        });
    }

}
