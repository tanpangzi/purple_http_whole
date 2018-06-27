package com.zimi.zimixing.activity.gpsMonitor.old;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.zimi.zimixing.R;
import com.zimi.zimixing.adapter.SimpleBaseAdapter;
import com.zimi.zimixing.bean.UserListBean;

import java.util.ArrayList;

public class DeviceLocationAdapter extends SimpleBaseAdapter<UserListBean> {

    private DeviceLocationCallback callback;

    public DeviceLocationAdapter(Context context, ArrayList<UserListBean> dataList, DeviceLocationCallback callback) {
        super(context, dataList);
        this.callback = callback;
    }

    @Override
    public int getItemResource() {
        return R.layout.devicelocation_item;
    }

    @Override
    public View getItemView(final int position, View convertView, ViewHolder holder) {
        TextView tv = holder.getView(R.id.device_location_item_tv);
        tv.setText(dataList.get(position).getUserChnName());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callback != null) {
                    callback.onCallback(dataList.get(position).getUserChnName(),
                            dataList.get(position).getUserAccount());
                }
            }
        });
        return convertView;
    }

    public interface DeviceLocationCallback {
        void onCallback(String userChnName, String equipmentId);
    }
}
