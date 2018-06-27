package com.zimi.zimixing.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zimi.zimixing.R;
import com.zimi.zimixing.activity.gpsMonitor.GpsMonitorStoryActivity;
import com.zimi.zimixing.bean.GpsMonitorHomeListBean;
import com.zimi.zimixing.configs.ConstantKey;
import com.zimi.zimixing.utils.IntentUtil;

import java.util.ArrayList;

public class GpsMonitorHomeAdapter extends SimpleBaseAdapter<GpsMonitorHomeListBean> {

    public GpsMonitorHomeAdapter(Context context, ArrayList<GpsMonitorHomeListBean> datas) {
        super(context, datas);
    }

    @Override
    public int getItemResource() {
        return R.layout.item_gps_monitor_home;
    }

    @Override
    public View getItemView(final int position, final View convertView, ViewHolder holder) {
        TextView txt_store_name = holder.getView(R.id.txt_store_name);
        TextView txt_total = holder.getView(R.id.txt_total);
        TextView txt_on_line = holder.getView(R.id.txt_on_line);
        TextView txt_off_line = holder.getView(R.id.txt_off_line);
        TextView txt_invalid = holder.getView(R.id.txt_invalid);

        final GpsMonitorHomeListBean bean = dataList.get(position);
        txt_store_name.setText(bean.getOrgName());
        txt_total.setText("总数（" + bean.getSum() + "）");
        txt_on_line.setText("在线 | " + bean.getOnline());
        txt_off_line.setText("离线 | " + bean.getOffline());
        txt_invalid.setText("失效 | " + bean.getInvalid());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(ConstantKey.INTENT_KEY_STRING, bean.getOrgName());
                bundle.putString(ConstantKey.INTENT_KEY_ID, bean.getOrgId());
                //bundle.putBoolean(ConstantKey.INTENT_KEY_BOOLEAN, bean.getIsEditType().equals("0"));
                IntentUtil.gotoActivity(context, GpsMonitorStoryActivity.class, bundle);
            }
        });
        return convertView;
    }
}
