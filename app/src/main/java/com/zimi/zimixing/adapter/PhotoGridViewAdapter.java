package com.zimi.zimixing.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zimi.zimixing.R;
import com.zimi.zimixing.bean.order.OrderDetailImageInfoBean;
import com.zimi.zimixing.configs.ConfigServer;
import com.zimi.zimixing.utils.OtherUtils;
import com.zimi.zimixing.utils.ScreenUtil;
import com.zimi.zimixing.utils.picasso.PicassoUtil;

import java.util.ArrayList;

public class PhotoGridViewAdapter extends SimpleBaseAdapter<OrderDetailImageInfoBean> {


    private String type;

    public PhotoGridViewAdapter(Context context, ArrayList<OrderDetailImageInfoBean> dataList, String type) {
        super(context, dataList);
        this.type = type;
    }

    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount();
    }

    @Override
    public int getItemResource() {
        return R.layout.item_select_pic;
    }

    @Override
    public View getItemView(final int position, View convertView, ViewHolder holder) {
        ImageView iv_select_pic = holder.getView(R.id.iv_select_pic);
        TextView tv_select_txt = holder.getView(R.id.tv_select_txt);

        int width = (int) ((ScreenUtil.getScreenWidthPx() - ScreenUtil.dip2px(80)) / 3f);
        iv_select_pic.getLayoutParams().width = width;
        iv_select_pic.getLayoutParams().height = width;

        final OrderDetailImageInfoBean bean = dataList.get(position);

        if (TextUtils.isEmpty(bean.getDec())) {
            tv_select_txt.setVisibility(View.INVISIBLE);
        } else {
            tv_select_txt.setVisibility(View.VISIBLE);
            tv_select_txt.setText(bean.getDec());
        }

        if (TextUtils.isEmpty(bean.getUrl())) {
            PicassoUtil.loadImage(context, R.drawable.icon_select_pic, iv_select_pic, R.drawable.icon_select_pic, R.drawable.icon_select_pic, null);
            //iv_select_pic.setImageResource(R.drawable.icon_select_pic);
        } else {
            String path = bean.getUrl();
            if (!path.startsWith("/storage/emulated") && !path.startsWith("http")) {
                path = ConfigServer.SERVER_API_URL + path.replaceFirst("app/", "");
            }
            PicassoUtil.loadImage(context, path, iv_select_pic);
        }

        iv_select_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (OtherUtils.getInstance().isFastClick(view)) {
                    return;
                }
                for (int i = 0; i < dataList.size(); i++) {
                    dataList.get(i).setCheck(false);
                }
                dataList.get(position).setCheck(true);
                //SPUtils.putString("order", position + "");
                /*if (TextUtils.isEmpty(bean.getUrl())) {
                    // TODO 选择图片
                    if (context instanceof AddOrderThreeActivity) {
                        ((AddOrderThreeActivity) context).showChoseDialog(type);
                    }
                } else {
                    if (context instanceof AddOrderThreeActivity) {
                        ((AddOrderThreeActivity) context).gotoPreviewPhoto(type, position);
                    } else if (context instanceof AddOrderSampleActivity) {
                        ((AddOrderSampleActivity) context).gotoPreviewPhoto(type, position);
                    }
                }*/
            }
        });


        //        // 贷款列表点击
        //        view_parent.setOnClickListener(new View.OnClickListener() {
        //            @Override
        //            public void onClick(View view) {
        //                if (OtherUtils.getInstance().isFastClick()) {
        //                    return;
        //                }
        //            }
        //        });
        return convertView;
    }


}
