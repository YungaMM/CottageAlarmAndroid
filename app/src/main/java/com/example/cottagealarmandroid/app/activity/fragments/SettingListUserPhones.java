package com.example.cottagealarmandroid.app.activity.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.*;
import com.example.cottagealarmandroid.app.R;
import com.example.cottagealarmandroid.app.model.DevicesAlarm;
import com.example.cottagealarmandroid.app.model.UserPhones;

import java.util.*;

public class SettingListUserPhones extends FragmentActivity
        implements AdapterView.OnItemClickListener, View.OnClickListener {

    private UserPhones[] usPhones;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_user_phones);

    }

    private ArrayList<HashMap<String, String>> setMapPhones(final UserPhones[] userPhones) {
        ArrayList<HashMap<String, String>> result = new ArrayList<>();
        HashMap<String, String> map;
        for (UserPhones userPhone : userPhones) {
            map = new HashMap<>();
            map.put("count", String.valueOf(userPhone.getCount() + 1) + ".");
            map.put("phone", userPhone.getPhone());
            result.add(map);
        }
        return result;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //AdvancePreferences.init(this);
        DevicesAlarm.reloadInstance();
        usPhones = DevicesAlarm.getInstance().getBasicAlarmProperty().getUserPhones();
        ArrayList<HashMap<String, String>> phoneList = setMapPhones(usPhones);

        ListView listPhones = (ListView) findViewById(R.id.listUserPhones);
        ListAdapter listAdapter = new SimpleAdapter(this, phoneList, R.layout.item_list_user_phones_fragment,
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
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
