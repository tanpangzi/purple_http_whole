package com.zimi.zimixing.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zimi.zimixing.R;


/**
 * 加载空白页实现
 */
public class EmptyView extends RelativeLayout {

    private LinearLayout loading_layout;
    private LinearLayout failure_layout;
    //private ImageView failure_iv;
    private TextView failure_tv; //数据为空或者加载失败时控件
    private TextView loading_view;
    private View mBindView; //绑定view

    private ResetListener resetListener;

    private OnClickListener listener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (resetListener != null) {
                loading_layout.setVisibility(VISIBLE);
                resetListener.onRestListener();
            }
        }
    };

    public EmptyView(Context context) {
        this(context, null);
    }

    public EmptyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmptyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.emptyview, this);
        loading_layout = (LinearLayout) findViewById(R.id.loading_layout);
        failure_layout = (LinearLayout) findViewById(R.id.failure_layout);
        //failure_iv = (ImageView) findViewById(R.id.failure_iv);
        loading_view = (TextView) findViewById(R.id.loading_view);
        failure_tv = (TextView) findViewById(R.id.failure_tv);

        failure_layout.setOnClickListener(listener);
    }

    public void setResetListener(ResetListener resetListener) {
        this.resetListener = resetListener;
    }

    public void setInitLoadintLayoutIsDisplay(boolean isDisplay){
        loading_layout.setVisibility(isDisplay?View.VISIBLE:View.GONE);
    }

    /**
     * 绑定View
     *
     * @param view
     */
    public void bindView(View view) {
        mBindView = view;
    }

    /**
     * 成功
     */
    public void success() {
        setVisibility(GONE);
        if (mBindView != null) {
            mBindView.setVisibility(VISIBLE);
        }
    }

    public void loading(){
        /*setVisibility(GONE);
        mBindView.setVisibility(VISIBLE);*/
        /*if (mBindView != null) {
            mBindView.setVisibility(VISIBLE);
        }*/
        loading_layout.setVisibility(VISIBLE);
        failure_layout.setVisibility(GONE);
        loading_view.setText("正在加载中...");

    }
    /**
     * 无数据
     */
    public void empty() {
        if (mBindView != null) {
            mBindView.setVisibility(GONE);
        }
        setVisibility(VISIBLE);
        loading_layout.setVisibility(GONE);
        failure_layout.setVisibility(VISIBLE);
        //failure_iv.setImageResource(R.drawable.nodata);
        failure_tv.setText("没有更多数据了...");
    }

    /**
     * 失败
     */
    public void failure() {
        if (mBindView != null) {
            mBindView.setVisibility(GONE);
        }
        setVisibility(VISIBLE);
        loading_layout.setVisibility(GONE);
        failure_layout.setVisibility(VISIBLE);
        //failure_iv.setImageResource(R.drawable.load_failed);
        failure_tv.setText("加载失败...");
    }


    public interface ResetListener {
        void onRestListener();
    }

}
