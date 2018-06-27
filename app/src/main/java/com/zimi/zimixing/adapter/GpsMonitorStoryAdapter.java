package com.zimi.zimixing.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zimi.zimixing.R;
import com.zimi.zimixing.activity.gpsMonitor.old.GPSActivity;
import com.zimi.zimixing.bean.GpsListBean;
import com.zimi.zimixing.bean.GpsMonitorStoryBean;
import com.zimi.zimixing.bean.ResponseBean;
import com.zimi.zimixing.biz.GpsMonitorBiz;
import com.zimi.zimixing.executor.BaseTask;
import com.zimi.zimixing.executor.RequestExecutor;
import com.zimi.zimixing.utils.IntentUtil;
import com.zimi.zimixing.utils.ToastUtil;

import java.util.ArrayList;

/**
 * 门店监控设备列表数据 适配器
 * <p>
 * <br> Author: 叶青
 * <br> Version: 4.0.0
 * <br> Date: 2018/1/17
 * <br> Copyright: Copyright © 2017 xTeam Technology. All rights reserved.
 */
public class GpsMonitorStoryAdapter extends SimpleBaseAdapter<GpsMonitorStoryBean> {

    private Context mContext;

    public GpsMonitorStoryAdapter(Context context, ArrayList<GpsMonitorStoryBean> datas) {
        super(context, datas);
        mContext = context;
    }

    // 是否报警
    private boolean isCallPolice = false;

    public void setCallPolice(boolean b) {
        this.isCallPolice = b;
    }

    @Override
    public int getItemResource() {
        return R.layout.item_gps_monitor_story;
    }

    @Override
    public View getItemView(final int position, final View convertView, ViewHolder holder) {

        TextView container_name = holder.getView(R.id.container_name);
        TextView container_plate = holder.getView(R.id.container_plate);
        ImageView containe_image = holder.getView(R.id.containe_image);
        //      TextView container_munber = holder.getView(R.id.container_munber);
        TextView container_type = holder.getView(R.id.container_type);
        TextView container_parameter = holder.getView(R.id.container_parameter);
        TextView container_date = holder.getView(R.id.container_date);
        TextView container_state = holder.getView(R.id.container_state);
        final GpsMonitorStoryBean bean = dataList.get(position);

        container_name.setText(bean.getName());
        container_plate.setText(bean.getCarNumber());
        if (bean.getType().equals("1")) {//无线
            containe_image.setImageResource(R.drawable.alarm_info_icon_wireless);
        } else if (bean.getType().equals("0")){ //有线
            containe_image.setImageResource(R.drawable.alarm_icon_wired);
        } else if (bean.getType().equals("2")){//ODB
            containe_image.setImageResource(R.drawable.odb_icon);
        }

        //            container_munber.setText();
        container_type.setText(bean.getDriveStatus());

        String orgName = bean.getOrgName();
        String acc = "ACC";
        String accState = bean.getAccState();
        if (accState.equals("0")) {
            acc += " 关";
        } else if (accState.equals("1")) {
            acc += " 开";
        }
        String speed = bean.getSpeed() + "KM/H";
        container_type.setTextColor(Double.parseDouble(bean.getSpeed()) > 0 ? Color.GREEN : Color.BLUE);
        container_parameter.setText(orgName + "|" + acc + " " + speed);


        String time = bean.getLocation_time();
        String status = bean.getStatus();
        container_date.setText(time + " " + status);


        container_state.setText(bean.getWarning_name());
        container_state.setTextColor(getColor(bean.getColor()));

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocationInfo(bean);

                /*Bundle bundle = new Bundle();
                bundle.putString("imeiId", bean.getImeiId());
                bundle.putString("orgName", bean.getOrgName());
                bundle.putBoolean("isCallPolice", isCallPolice);
                bundle.putString("type", bean.getType());
                IntentUtil.gotoActivity(context, GPSActivity.class, bundle);*/
            }
        });
        return convertView;
    }

    /**
     * add by tanjun
     * 有数据就可以跳转 没有则不跳转
     * @param bean
     */
    private void getLocationInfo( final GpsMonitorStoryBean bean) {
        RequestExecutor.addTask(new BaseTask(mContext) {
            @Override
            public ResponseBean sendRequest() {
                return GpsMonitorBiz.getLocationInfo(bean.getImeiId());
            }

            @Override
            public void onSuccess(ResponseBean result) {
                ArrayList<GpsListBean> arrayList = (ArrayList<GpsListBean>) result.getObject();

                if (arrayList != null && arrayList.size() > 0) {
                    double lat = arrayList.get(0).getLatitude();
                    double lon = arrayList.get(0).getLongitude();
                    GpsListBean gpsBean = arrayList.get(0);
                    String get_imeiId = gpsBean.getImeiId();
                    int type = gpsBean.getType();
                    if (lat > 0 && lon > 0) {
                        //showMarker(lat, lon);
                        Bundle bundle = new Bundle();
                        bundle.putString("imeiId", bean.getImeiId());
                        bundle.putString("orgName", bean.getOrgName());
                        bundle.putBoolean("isCallPolice", isCallPolice);
                        bundle.putString("type", bean.getType());
                        IntentUtil.gotoActivity(context, GPSActivity.class, bundle);

                    } else {
                        ToastUtil.showToast(mContext, "获取不到数据！");
                    }
                } else {
                    ToastUtil.showToast(mContext, "定位数据为空！");
                }
            }

            @Override
            public void onFail(ResponseBean result) {
                ToastUtil.showToast(mContext, result.getInfo());
            }
        });
    }

    private int getColor(String color) {
        int c = -1;
        if ("0".equals(color)) {
            c = Color.GRAY;
        } else if ("1".equals(color)) {
            c = Color.RED;
        } else if ("2".equals(color)) {
            c = Color.GREEN;
        } else if ("3".equals(color)) {
            c = Color.YELLOW;
        } else if ("4".equals(color)) {
            c = Color.BLUE;
        }
        return c;
    }
}
