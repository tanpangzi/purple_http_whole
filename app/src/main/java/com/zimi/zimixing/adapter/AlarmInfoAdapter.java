package com.zimi.zimixing.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zimi.zimixing.R;
import com.zimi.zimixing.activity.gpsMonitor.alarm.OutFenceAlarmActivity;
import com.zimi.zimixing.bean.gps_install.IndexTypeBean;
import com.zimi.zimixing.utils.IntentUtil;
import com.zimi.zimixing.utils.OtherUtils;

import java.util.ArrayList;

import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;


/**
 * create by tanjun
 * 报警信息适配器
 */
public class AlarmInfoAdapter extends BaseAdapter{

	private Context mContext;
	private LayoutInflater mInflater;
	private ArrayList<IndexTypeBean> mDatas;

	private Badge badge;

	public AlarmInfoAdapter(Context context, ArrayList<IndexTypeBean> mDatas)	{
		this.mContext = context;
		mInflater = LayoutInflater.from(context);
		this.mDatas = mDatas;
	}

	public int getCount(){
		return mDatas.size();
	}

	public Object getItem(int position)	{
		return mDatas.get(position);
	}

	public long getItemId(int position)	{
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent){
		ViewHolder viewHolder = null;
        badge = new QBadgeView(mContext);
		if (convertView == null){
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(
					R.layout.adapter_horizon_scrollview, parent, false);
			viewHolder.mImg = (ImageView) convertView
					.findViewById(R.id.iv_scroll_view);
			viewHolder.mText = (TextView) convertView
					.findViewById(R.id.tv_scroll_view);

			convertView.setTag(viewHolder);
		} else{
			viewHolder = (ViewHolder) convertView.getTag();
		}

        badge.bindTarget(viewHolder.mImg).setBadgeNumber(5);
		viewHolder.mImg.setImageResource(mDatas.get(position).getResId());
		viewHolder.mText.setText(mDatas.get(position).getResName());

		convertView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (OtherUtils.getInstance().isFastClick(v)) {
					return;
				}
				IntentUtil.gotoActivity(mContext, OutFenceAlarmActivity.class);
			}
		});

		return convertView;
	}

	private class ViewHolder{
		ImageView mImg;
		TextView mText;
	}

}
