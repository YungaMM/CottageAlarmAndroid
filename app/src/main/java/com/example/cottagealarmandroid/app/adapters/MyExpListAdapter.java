package com.example.cottagealarmandroid.app.adapters;


import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.*;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
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

    public MyExpListAdapter(final Context context, final String[] itemGroup,
                            final String[] itemChild, final String[] existChildOption) {
        mContext = context;

        mGroupsStr = itemGroup;
        mChildStr = itemChild;
        mOptionStr = new String[mGroupsStr.length];
        mGroups = setmGroups(existChildOption);
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

    public void setIndicatorGroupRight(final ExpandableListView expListView, final Activity activity) {
        //Устанавливаем индикатор группы вправо
        // узнаем размеры экрана из класса Display
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics metricsB = new DisplayMetrics();
        display.getMetrics(metricsB);
        int width = metricsB.widthPixels;
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            expListView.setIndicatorBounds(width - GetPixelFromDips(50), width - GetPixelFromDips(10));
        } else {
            expListView.setIndicatorBoundsRelative(width - GetPixelFromDips(50), width - GetPixelFromDips(10));
        }
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
            convertView = inflater.inflate(R.layout.option_user_phone_fragment, null);
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

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private int GetPixelFromDips(float pixels) {
        // Get the screen's density scale
        final float scale = mContext.getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }

    private ArrayList<ArrayList<String>> setmGroups(final String[] existChildOption) {
        ArrayList<ArrayList<String>> groups = new ArrayList<>();
        ArrayList<String> child = new ArrayList<>();

        for (String aMChildStr : mChildStr) {
            child.add(aMChildStr);
        }

        for (int i = 0; i < mGroupsStr.length; i++) {
            groups.add(child);
            int option = Integer.valueOf(existChildOption[i]);
            mOptionStr[i] = mChildStr[option];
        }

        return groups;
    }
}
