package com.zimi.zimixing.activity;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zimi.zimixing.R;
import com.zimi.zimixing.utils.ToastUtil;
import com.zimi.zimixing.widget.ContainsEmojiEditText;
import com.zimi.zimixing.widget.TitleViewPurple;


/**
 * 意见反馈
 * created by tanjun
 */
public class FeedBackActivity extends BaseActivity {

    TitleViewPurple title_view;
    ContainsEmojiEditText et_advice;
    TextView txt_count;
    ContainsEmojiEditText et_contact;
    Button btn_submit;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void findViews() {
        title_view = findViewByIds(R.id.title_view);
        et_advice = findViewByIds(R.id.et_advice);
        txt_count = findViewByIds(R.id.txt_count);
        et_contact = findViewByIds(R.id.et_contact);
        btn_submit = findViewByIds(R.id.btn_submit);
    }

    @Override
    protected void initGetData() {
    }

    @Override
    protected void init() {
        title_view.setLeftBtnImg();
        title_view.setTitle(R.string.feed_back);
    }

    @Override
    protected void widgetListener() {
        et_advice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String str = editable.toString();
                if (!TextUtils.isEmpty(str)) {
                    txt_count.setText(String.valueOf((200 - str.length())) + "/200");
                } else {
                    txt_count.setText("200");
                }
            }
        });

        /**
         * 提交
         */
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strAdvice = et_advice.getText().toString().trim();
                if (TextUtils.isEmpty(strAdvice)){
                    ToastUtil.showToast(FeedBackActivity.this, getString(R.string.empty_advice));
                    return;
                }
            }
        });

    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {
                FeedBackActivity.this.finish();
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }


}
