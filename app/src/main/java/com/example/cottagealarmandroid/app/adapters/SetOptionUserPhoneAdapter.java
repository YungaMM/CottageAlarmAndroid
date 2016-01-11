package com.example.cottagealarmandroid.app.adapters;


import android.content.Context;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import com.example.cottagealarmandroid.app.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SetOptionUserPhoneAdapter {
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

        adapter = new SimpleExpandableListAdapter(
                context,
                groupData,
                android.R.layout.simple_expandable_list_item_1,
                groupFrom,
                groupTo,
                childData,
                android.R.layout.simple_list_item_1,
                childFrom,
                childTo);

        return adapter;
    }

    String getGroupText(int groupPos) {
        return ((Map<String,String>)(adapter.getGroup(groupPos))).get(GROUP_NAME);
    }

    String getChildText(int groupPos, int childPos) {
        return ((Map<String,String>)(adapter.getChild(groupPos, childPos))).get(CHILD_NAME);
    }

    String getGroupChildText(int groupPos, int childPos) {
        return getGroupText(groupPos) + " " +  getChildText(groupPos, childPos);
    }


}
