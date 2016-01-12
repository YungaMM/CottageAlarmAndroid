package com.example.cottagealarmandroid.app.activity.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.*;
import com.example.cottagealarmandroid.app.R;
import com.example.cottagealarmandroid.app.controllers.DevicesAlarm;
import com.example.cottagealarmandroid.app.model.UserPhones;

import java.util.*;

public class SettingListUserPhones extends FragmentActivity
        implements AdapterView.OnItemClickListener {

    private ListView listPhones;

    private ListAdapter listAdapter;
    private ArrayList<HashMap<String, String>> phoneList;
    private UserPhones[] usPhones;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_user_phones);

    }

    private ArrayList<HashMap<String, String>> setMapPhones(final UserPhones[] userPhones) {
        ArrayList<HashMap<String, String>> result = new ArrayList<>();
        HashMap<String, String> map;
        for (int i = 0; i < userPhones.length; i++) {
            map = new HashMap<>();
            map.put("count", String.valueOf(userPhones[i].getCount() + 1) + ".");
            map.put("phone", userPhones[i].getPhone());
            result.add(map);
        }
        return result;
    }

    @Override
    protected void onResume() {
        super.onResume();
        usPhones = DevicesAlarm.getInstance().getBasicAlarmProperty().getUserPhones();
        phoneList = setMapPhones(usPhones);

        listPhones = (ListView) findViewById(R.id.listUserPhones);
        listAdapter = new SimpleAdapter(this, phoneList, R.layout.item_list_user_phones,
                new String[]{"count", "phone"},
                new int[]{R.id.showCountPhone, R.id.showPhone});
        listPhones.setAdapter(listAdapter);

        listPhones.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, SetUserPhone.class);

        intent.putExtra("phone",usPhones[position].getCount());
        startActivity(intent);

        Toast.makeText(this, "Click phone" + position, Toast.LENGTH_SHORT).show();
    }
}
