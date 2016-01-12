package com.example.cottagealarmandroid.app.adapters;


import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.cottagealarmandroid.app.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SetOptionUserPhoneAdapter extends BaseExpandableListAdapter {
    final String GROUP_NAME = "groupName";
    final String CHILD_NAME = "childName";

    private ExpandableListView expListView;
    private SimpleExpandableListAdapter adapter;
    private Context context;

    // коллекция для групп
    private ArrayList<Map<String, String>> groupData;

    // коллекция для элементов одной группы
    private ArrayList<Map<String, String>> childDataItem;

    // общая коллекция для коллекций элементов
    private ArrayList<ArrayList<Map<String, String>>> childData;
    // в итоге получится childData = ArrayList<childDataItem>

    // список аттрибутов группы или элемента
    private Map<String, String> m;

    public SetOptionUserPhoneAdapter(Context context) {
        this.context = context;
    }

    public SimpleExpandableListAdapter getAdapter(){
        // заполняем коллекцию групп из массива с названиями групп
        groupData = new ArrayList<Map<String, String>>();
        String[] groups = context.getResources().getStringArray(R.array.nameUserPhoneOptions);

        for (String group : groups) {
            // заполняем список аттрибутов для каждой группы
            m = new HashMap<String, String>();
            m.put(GROUP_NAME, group); // название группы
            groupData.add(m);
        }

        // список аттрибутов групп для чтения
        String groupFrom[] = new String[] {GROUP_NAME};
        // список ID view-элементов, в которые будет помещены аттрибуты групп
        int groupTo[] = new int[] {android.R.id.text1};

        // создаем коллекцию для коллекций элементов
        childData = new ArrayList<ArrayList<Map<String, String>>>();
        String[] child = context.getResources().getStringArray(R.array.userPhoneOptions);

        for (int i = 0; i < groups.length; i++) {
            // создаем коллекцию элементов для i группы
            childDataItem = new ArrayList<Map<String, String>>();
            // заполняем список аттрибутов для каждого элемента
            for (String options : child) {
                m = new HashMap<String, String>();
                m.put(CHILD_NAME, options); // название child_a
                childDataItem.add(m);
            }
            // добавляем в коллекцию коллекций
            childData.add(childDataItem);
        }

        // список аттрибутов элементов для чтения
        String childFrom[] = new String[] {CHILD_NAME};
        // список ID view-элементов, в которые будет помещены аттрибуты элементов
        int childTo[] = new int[] {android.R.id.text1};

       // Layout optUserPhone = R.layout.option_user_phone;

        adapter = new SimpleExpandableListAdapter(
                context,
                groupData,
                R.layout.option_user_phone,
                groupFrom,
                groupTo,
                childData,
                android.R.layout.simple_list_item_1,
                childFrom,
                childTo);

        return adapter;
    }

    public String getGroupText(int groupPos) {
        return ((Map<String,String>)(adapter.getGroup(groupPos))).get(GROUP_NAME);
    }

    public String getChildText(int groupPos, int childPos) {
        return ((Map<String,String>)(adapter.getChild(groupPos, childPos))).get(CHILD_NAME);
    }

    public String getGroupChildText(int groupPos, int childPos) {
        return getGroupText(groupPos) + " " +  getChildText(groupPos, childPos);
    }


    @Override
    public int getGroupCount() {
        return 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.option_user_phone, null);
        }

        if (isExpanded){
            //Изменяем что-нибудь, если текущая Group раскрыта
        }
        else{
            //Изменяем что-нибудь, если текущая Group скрыта
        }

        TextView textGroupName = (TextView) convertView.findViewById(R.id.textGroupName);
        TextView textChildName = (TextView) convertView.findViewById(R.id.textChildName);

        textGroupName.setText(getGroupText(groupPosition));
        textChildName.setText("Ghbdtnhvh");
//        textGroupName.setText("Group " + Integer.toString(groupPosition));

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {
       return null;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

}
