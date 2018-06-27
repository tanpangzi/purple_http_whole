package com.zimi.zimixing.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.zimi.zimixing.BaseApplication;
import com.zimi.zimixing.R;
import com.zimi.zimixing.activity.AccountTaskActivity;
import com.zimi.zimixing.activity.zmxGpsInstall.ZMXCarInfoActivity;
import com.zimi.zimixing.activity.gpsMonitor.GpsMonitorHomeActivity;
import com.zimi.zimixing.activity.gpsMonitor.GpsMonitorStoryActivity;

import com.zimi.zimixing.bean.IndexTypeBean;
import com.zimi.zimixing.configs.Constant;
import com.zimi.zimixing.configs.ConstantKey;
import com.zimi.zimixing.utils.ComUtil;
import com.zimi.zimixing.utils.IntentUtil;
import com.zimi.zimixing.utils.OtherUtils;
import com.zimi.zimixing.utils.ScreenUtil;
import com.zimi.zimixing.utils.ToastUtil;

import java.util.ArrayList;

/**
 * 首页适配器
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class IndexGridAdapter extends SimpleBaseAdapter<IndexTypeBean> {

    private int rows = 4;

    public IndexGridAdapter(Context context, ArrayList<IndexTypeBean> data) {
        super(context, data);
    }

    public IndexGridAdapter(Context context, ArrayList<IndexTypeBean> data, int rows) {
        super(context, data);
        this.rows = rows;
    }

    @Override
    public int getItemResource() {
        return R.layout.item_gird_index;
    }

    @Override
    public View getItemView(final int position, View convertView, ViewHolder holder) {
        TextView txt_content = holder.getView(R.id.txt_content);
        RelativeLayout view_parent = holder.getView(R.id.view_parent);

        // 控制ImageView的宽高
        LayoutParams layoutParams = (LayoutParams) view_parent.getLayoutParams();
        layoutParams.width = ScreenUtil.getScreenWidthPx() / rows;
        layoutParams.height = ScreenUtil.getScreenWidthPx() / 5;

        final IndexTypeBean bean = dataList.get(position);
        if (bean.getResName() > 0) {
            txt_content.setVisibility(View.VISIBLE);
            txt_content.setText(bean.getResName());
            txt_content.setCompoundDrawablesWithIntrinsicBounds(null, context.getResources().getDrawable(bean.getResId()), null, null);
            view_parent.setBackgroundResource(R.drawable.selector_white_black_5);
            view_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (OtherUtils.getInstance().isFastClick(v)) {
                        return;
                    }
                    Bundle bundle = new Bundle();
                    switch (bean.getResName()) {

                        case R.string.fragment_index_type5:
                            /*if (isContainsPermission("dytApi:ENTERING")) {
                                IntentUtil.gotoActivity(context, AddOrderActivity.class);
                            } else {
                                ToastUtil.showToast(context, "没有录单权限！");
                            }*/
                            break;

                        case R.string.fragment_index_type7:
                            if (isContainsPermission("zmxAPI:MONITORS")) {
                                //   TODO     permissionKey = "dytApi:GPS:SUPER:ADMIN" permissionName = "GPS超级管理员"
                                if (isContainsPermission("zmxAPI:WARDEN")) {
                                    IntentUtil.gotoActivity(context, GpsMonitorHomeActivity.class);
                                } else {
                                    bundle = new Bundle();
                                    bundle.putBoolean(ConstantKey.INTENT_KEY_BOOLEAN, false);
                                    IntentUtil.gotoActivity(context, GpsMonitorStoryActivity.class, bundle);
                                }
                            } else {
                                ToastUtil.showToast(context, "没有GPS监控权限！");
                            }
                            break;
                        case R.string.fragment_index_type8:
                            if (ComUtil.getPermissionKey("dytApi:ASSESS")) {
                                bundle.putInt("type", Constant.CAR_ASSESS);
                                IntentUtil.gotoActivity(context, AccountTaskActivity.class, bundle);
                            } else {
                                ToastUtil.showToast(context, "没有车辆评估权限！");
                                return;
                            }
                            break;
                        case R.string.fragment_index_type9:
                            if (isContainsPermission("zmxAPI:INSTALL")) {
                                bundle.putInt("type", Constant.GPS_INSTALL);
                                //IntentUtil.gotoActivity(context, AccountTaskActivity.class, bundle);
                                IntentUtil.gotoActivity(context, ZMXCarInfoActivity.class, bundle);
                            } else {
                                ToastUtil.showToast(context, "没有GPS安装权限！");
                                return;
                            }
                            break;
                        case R.string.fragment_index_type10:
                            if (ComUtil.getPermissionKey("dytApi:GUARANTY")) {
                                bundle.putInt("type", Constant.CAR_MORTGAGE);
                                IntentUtil.gotoActivity(context, AccountTaskActivity.class, bundle);
                            } else {
                                ToastUtil.showToast(context, "没有GPS安装权限！");
                                return;
                            }
                            break;

                    }
                }
            });
        } else {
            txt_content.setVisibility(View.GONE);
            view_parent.setBackgroundColor(context.getResources().getColor(R.color.white));
            view_parent.setOnClickListener(null);
        }

        return convertView;
    }

    /** 当前用户 所有的权限 key列表 */
    private ArrayList<String> Permissions;

    private boolean isContainsPermission(String permission) {
            String permissionKey = BaseApplication.getInstance().getUserInfoBean().getPermissionKey();
            //permissionKey = permissionKey.substring(1, permissionKey.length() - 1);
            String[] permissions = permissionKey.split(",");
            Permissions = new ArrayList<>();
            if (permissions.length>0){
                for (int i = 0; i < permissions.length; i++) {
                    Permissions.add(permissions[i].substring(1, permissions[i].length() - 1));
                }
            }


        return Permissions.contains(permission);
    }

}