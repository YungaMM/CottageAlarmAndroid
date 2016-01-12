package com.example.cottagealarmandroid.app.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import com.example.cottagealarmandroid.app.R;
import com.example.cottagealarmandroid.app.model.UserPhones;

import java.util.ArrayList;

public class MyExpListAdapter extends BaseExpandableListAdapter {
    private final String[] mChildStr;

    private ArrayList<ArrayList<String>> mGroups;
    private String[] mGroupsStr;
    private String[] mOptionStr;
    private Context mContext;

    private UserPhones userPhone;

    public MyExpListAdapter(final Context context, final UserPhones userPhone) {
        mContext = context;
        this.userPhone = userPhone;

        mGroupsStr = mContext.getResources().getStringArray(R.array.nameUserPhoneOptions);
        mChildStr = mContext.getResources().getStringArray(R.array.userPhoneOptions);
        mOptionStr = new String[mGroupsStr.length];
        mGroups = setmGroups();
    }


    public void setOption(final int count, final String option){
        this.mOptionStr[count] = option;
    }

    public String[] getmOptionStr() {
        return mOptionStr;
    }

    public void setmOptionStr(String[] mOptionStr) {
        this.mOptionStr = mOptionStr;
    }

    @Override
    public int getGroupCount() {
        return mGroups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mGroups.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mGroups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mGroups.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
                             ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.option_user_phone, null);
        }

        if (isExpanded) {
            //Изменяем что-нибудь, если текущая Group раскрыта
        } else {
            //Изменяем что-нибудь, если текущая Group скрыта
        }

        TextView textGroup = (TextView) convertView.findViewById(R.id.textGroupName);
        textGroup.setText(mGroupsStr[groupPosition]);

        TextView textChild = (TextView) convertView.findViewById(R.id.textChildName);
        textChild.setText(mOptionStr[groupPosition]);

        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(android.R.layout.simple_list_item_1, null);
        }

        final TextView textChild = (TextView) convertView.findViewById(android.R.id.text1);
        textChild.setText(mGroups.get(groupPosition).get(childPosition));

//        textChild.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mOptionStr[groupPosition] = String.valueOf(textChild.getText());
//                userPhone.setOption(groupPosition,String.valueOf(childPosition));
//            }
//        });
//
//        this.onGroupCollapsed(groupPosition);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private ArrayList<ArrayList<String>> setmGroups() {
        ArrayList<ArrayList<String>> groups = new ArrayList<>();
        ArrayList<String> child = new ArrayList<>();

        for (int i = 0; i < mChildStr.length; i++) {
            child.add(mChildStr[i]);
        }

        for (int i = 0; i < mGroupsStr.length; i++) {
            groups.add(child);
            int option = Integer.valueOf(userPhone.getOptionOnCount(i));
            mOptionStr[i] = mChildStr[option];
        }

        return groups;
    }
}
