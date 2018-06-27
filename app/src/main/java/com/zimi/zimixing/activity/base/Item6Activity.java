package com.zimi.zimixing.activity.base;

import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.zimi.zimixing.R;
import com.zimi.zimixing.activity.BaseActivity;
import com.zimi.zimixing.utils.EmptyCheckUtil;
import com.zimi.zimixing.utils.KeyboardUtil;
import com.zimi.zimixing.utils.LogUtil;
import com.zimi.zimixing.widget.CustomKeyBoardLayout;
import com.zimi.zimixing.widget.TitleView;

/**
 * 键盘高度
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2017 xTeam Technology. All rights reserved.
 */
public class Item6Activity extends BaseActivity {

    private TextView textView;
    private CustomKeyBoardLayout keyboardLayout;
    private static final int KEYBOARD_SHOW = 0X10;
    private static final int KEYBOARD_HIDE = 0X20;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_item6;
    }

    @Override
    protected void findViews() {
        keyboardLayout = (CustomKeyBoardLayout) findViewById(R.id.view1);
        textView = (TextView) findViewById(R.id.textView);
        TitleView title_view = findViewByIds(R.id.title_view);
        final EditText edit_search = findViewByIds(R.id.editText1);
        title_view.setTitle("键盘高度");
        title_view.setLeftBtnImg();

        edit_search.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        edit_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    KeyboardUtil.hideKeyBord(edit_search);
                    if (!EmptyCheckUtil.isEmpty(edit_search.getText().toString())) {
                        //						if (currentFragment == FRAGMENT_INDEX) {
                        //							switchView(FRAGMENT_TYPE);
                        //						} else {
                        //							searchResultFragment.initListView();
                        //						}
                    }
                    return true;
                }

                return false;
            }
        });

        keyboardLayout.setOnSizeChangedListener(new CustomKeyBoardLayout.onSizeChangedListener() {

            @Override
            public void onChanged(boolean showKeyboard) {
                // TODO Auto-generated method stub
                if (showKeyboard) {
                    mHandler.sendMessage(mHandler.obtainMessage(KEYBOARD_SHOW));
                } else {
                    mHandler.sendMessage(mHandler.obtainMessage(KEYBOARD_HIDE));
                }
            }
        });
    }

    @Override
    protected void init() {
    }

    @Override
    protected void initGetData() {

    }

    @Override
    protected void widgetListener() {

    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case KEYBOARD_HIDE:
                    LogUtil.i("show keyboard false");
                    break;

                case KEYBOARD_SHOW:
                    LogUtil.i("show keyboard true");
                    break;

                default:
                    break;
            }
        }
    };

}