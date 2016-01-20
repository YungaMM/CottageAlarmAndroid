package com.example.cottagealarmandroid.app.adapters;


import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.*;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import com.example.cottagealarmandroid.app.R;

import java.util.ArrayList;
import java.util.Collections;

public class MyExpListAdapter extends BaseExpandableListAdapter {
    private ArrayList<ArrayList<String>> mGroups;
    private String[] itemGroup;
    private String[] itemChild;
    private String[] existChild;
    private Context mContext;

    public MyExpListAdapter(final Context context, final String[] itemGroup,
                            final String[] itemChild, final String[] existChild) {
        mContext = context;

        this.itemGroup = itemGroup;
        this.itemChild = itemChild;
        this.existChild = new String[this.itemGroup.length];
        mGroups = setmGroups(existChild);
    }


    public void setOption(final int count, final String option){
        this.existChild[count] = option;
    }

    public String[] getExistChild() {
        return existChild;
    }

    public void setExistChild(String[] existChild) {
        this.existChild = existChild;
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
            convertView = inflater.inflate(R.layout.my_exp_list_fragment, null);
        }

        if (isExpanded) {
            //Изменяем что-нибудь, если текущая Group раскрыта
        } else {
            //Изменяем что-нибудь, если текущая Group скрыта
        }

        TextView textGroup = (TextView) convertView.findViewById(R.id.textGroupName);
        textGroup.setText(itemGroup[groupPosition]);

        TextView textChild = (TextView) convertView.findViewById(R.id.textChildName);
        textChild.setText(existChild[groupPosition]);

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

        Collections.addAll(child, itemChild);

        for (int i = 0; i < itemGroup.length; i++) {
            groups.add(child);
            int option = Integer.valueOf(existChildOption[i]);
            existChild[i] = itemChild[option];
        }

        return groups;
    }
}
