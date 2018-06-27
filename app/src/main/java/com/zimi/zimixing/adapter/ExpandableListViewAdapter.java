package com.zimi.zimixing.adapter;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zimi.zimixing.R;
import com.zimi.zimixing.bean.CompanyGroupListBean;
import com.zimi.zimixing.bean.CompanyUserListBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpandableListViewAdapter extends BaseExpandableListAdapter {

    private List<CompanyGroupListBean> dataList;
    // 选中的 门店 下面 成员名
    private List<String> checkedChildren = new ArrayList<>();
    private Map<String, Integer> groupCheckedStateMap = new HashMap<>();
    private Context context;

    public ExpandableListViewAdapter(Context context, List<CompanyGroupListBean> dataList) {
        this.context = context;
        this.dataList = dataList;

        int groupCount = getGroupCount();
        for (int groupPosition = 0; groupPosition < groupCount; groupPosition++) {
            try {
                CompanyGroupListBean groupItem = dataList.get(groupPosition);
                if (groupItem == null || groupItem.getUsers() == null || groupItem.getUsers().isEmpty()) {
                    groupCheckedStateMap.put(groupItem.getName(), 3);
                    continue;
                }
                groupCheckedStateMap.put(groupItem.getName(), 3);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public Object getChild(int groupPosition, int childPosition) {
        final CompanyGroupListBean groupItem = dataList.get(groupPosition);
        if (groupItem == null || groupItem.getUsers() == null || groupItem.getUsers().isEmpty()) {
            return null;
        }
        return groupItem.getUsers().get(childPosition);
    }


    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, final ViewGroup parent) {
        CompanyUserListBean childrenItem = (CompanyUserListBean) getChild(groupPosition, childPosition);

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_expandable_list_view, null);
            viewHolder.view_parent = (RelativeLayout) convertView.findViewById(R.id.view_parent);
            viewHolder.txt_name = (TextView) convertView.findViewById(R.id.txt_name);
            viewHolder.img_check = (ImageView) convertView.findViewById(R.id.img_check);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) viewHolder.img_check.getLayoutParams();
        layoutParams.setMargins(50, 0, 0, 0);
        viewHolder.txt_name.setText(childrenItem.getName());
        final String childrenName = childrenItem.getUsername();

        viewHolder.img_check.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkedChildren.contains(childrenName)) {
                    checkedChildren.add(childrenName);
                } else {
                    checkedChildren.remove(childrenName);
                }

                setGroupItemCheckedState(dataList.get(groupPosition));

                ExpandableListViewAdapter.this.notifyDataSetChanged();
            }
        });

        if (checkedChildren.contains(childrenName)) {
            viewHolder.img_check.setImageResource(R.drawable.checked);
        } else {
            viewHolder.img_check.setImageResource(R.drawable.unchecked);
        }

        return convertView;
    }


    @Override
    public int getChildrenCount(int groupPosition) {
        final CompanyGroupListBean groupItem = dataList.get(groupPosition);
        if (groupItem == null || groupItem.getUsers() == null || groupItem.getUsers().isEmpty()) {
            return 0;
        }
        return groupItem.getUsers().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        if (dataList == null) {
            return null;
        }
        return dataList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        if (dataList == null) {
            return 0;
        }
        return dataList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }


    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        //        try {
        CompanyGroupListBean groupItem = dataList.get(groupPosition);

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_expandable_list_view, null);
            viewHolder.txt_name = (TextView) convertView.findViewById(R.id.txt_name);
            viewHolder.img_check = (ImageView) convertView.findViewById(R.id.img_check);
            //				viewHolder.groupCBLayout = (LinearLayout) convertView.findViewById(R.id.cb_layout);
            //				viewHolder.iv_arrow = (ImageView) convertView.findViewById(R.id.iv_arrow);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.img_check.setOnClickListener(new GroupCBLayoutOnClickListener(groupItem));
        viewHolder.txt_name.setText(groupItem.getName());
        int state = 3;
        if (groupCheckedStateMap.containsKey(groupItem.getName())) {
            state = groupCheckedStateMap.get(groupItem.getName());
        }
        if (state == 1) {
            viewHolder.img_check.setImageResource(R.drawable.checked);
        } else {
            viewHolder.img_check.setImageResource(R.drawable.unchecked);
        }
        //            switch (state) {
        //                case 1:
        //                    viewHolder.img_check.setImageResource(R.drawable.checked);
        //                    companyCode = groupItem.getName();
        //                    break;
        //                case 2:
        //                    viewHolder.img_check.setImageResource(R.drawable.unchecked);
        //                    //				viewHolder.img_check.setImageResource(R.drawable.ck_partial_checked);
        //                    companyCode = groupItem.getName();
        //                    break;
        //                case 3:
        //                    viewHolder.img_check.setImageResource(R.drawable.unchecked);
        //                    break;
        //                default:
        //                    break;
        //            }
        //        } catch (Exception e) {
        //            e.printStackTrace();
        //        }

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    private void setGroupItemCheckedState(CompanyGroupListBean groupItem) {
        List<CompanyUserListBean> childrenItems = groupItem.getUsers();
        if (childrenItems == null || childrenItems.isEmpty()) {
            groupCheckedStateMap.put(groupItem.getName(), 3);
            return;
        }

        int checkedCount = 0;
        for (CompanyUserListBean childrenItem : childrenItems) {
            if (checkedChildren.contains(childrenItem.getUsername())) {
                checkedCount++;
            }
        }
        int state = 1;
        if (checkedCount == 0) {
            state = 3;
        } else if (checkedCount == childrenItems.size()) {
            state = 1;
        } else {
            state = 2;
        }

        groupCheckedStateMap.put(groupItem.getName(), state);
    }

    final static class ViewHolder {
        RelativeLayout view_parent;
        TextView txt_name;
        ImageView img_check;
    }

    public class GroupCBLayoutOnClickListener implements OnClickListener {

        private CompanyGroupListBean groupItem;

        public GroupCBLayoutOnClickListener(CompanyGroupListBean groupItem) {
            this.groupItem = groupItem;
        }

        @Override
        public void onClick(View v) {
            List<CompanyUserListBean> childrenItems = groupItem.getUsers();
            if (childrenItems == null || childrenItems.isEmpty()) {
                groupCheckedStateMap.put(groupItem.getName(), 3);
                return;
            }
            int checkedCount = 0;
            for (CompanyUserListBean childrenItem : childrenItems) {
                if (checkedChildren.contains(childrenItem.getUsername())) {
                    checkedCount++;
                }
            }

            boolean checked = false;
            if (checkedCount == childrenItems.size()) {
                checked = false;
                groupCheckedStateMap.put(groupItem.getName(), 3);
            } else {
                checked = true;
                groupCheckedStateMap.put(groupItem.getName(), 1);
            }

            for (CompanyUserListBean childrenItem : childrenItems) {
                String holderKey = childrenItem.getUsername();
                if (checked) {
                    if (!checkedChildren.contains(holderKey)) {
                        checkedChildren.add(holderKey);
                    }
                } else {
                    checkedChildren.remove(holderKey);
                }
            }

            ExpandableListViewAdapter.this.notifyDataSetChanged();
        }
    }

    public List<String> getCheckedRecords() {
        return checkedChildren;
    }

    public List<String> getCheckedChildren() {
        return checkedChildren;
    }
}