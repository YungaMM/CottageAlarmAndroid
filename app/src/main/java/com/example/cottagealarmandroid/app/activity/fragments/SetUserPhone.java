package com.example.cottagealarmandroid.app.activity.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.*;
import com.example.cottagealarmandroid.app.R;
import com.example.cottagealarmandroid.app.adapters.MyExpListAdapter;
import com.example.cottagealarmandroid.app.adapters.SetOptionUserPhoneAdapter;
import com.example.cottagealarmandroid.app.controllers.AdvancePreferences;
import com.example.cottagealarmandroid.app.controllers.DevicesAlarm;
import com.example.cottagealarmandroid.app.model.UserPhones;


public class SetUserPhone extends FragmentActivity implements View.OnClickListener {
    private EditText phone;
    private ExpandableListView expListView;

    private DevicesAlarm devicesAlarm;
    private UserPhones userPhone;

    private int countPhone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_user_phone);

        countPhone = getIntent().getIntExtra("phone", 0);

        devicesAlarm = DevicesAlarm.getInstance();
        userPhone = devicesAlarm.basicAlarmProperty.getUserPhone(countPhone);

        phone = (EditText) findViewById(R.id.valueInputPhone);
        phone.setText(userPhone.getPhone());

        final MyExpListAdapter adapter = new MyExpListAdapter(this, userPhone);

        expListView = (ExpandableListView) findViewById(R.id.expandableListView);
        expListView.setAdapter(adapter);
        expListListener(adapter);


    }

    private void expListListener (final MyExpListAdapter adapter){
        //Устанавливаем индикатор группы вправо
        // узнаем размеры экрана из класса Display
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics metricsB = new DisplayMetrics();
        display.getMetrics(metricsB);
        int width = metricsB.widthPixels;
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            expListView.setIndicatorBounds(width - GetPixelFromDips(50), width - GetPixelFromDips(10));
        } else {
            expListView.setIndicatorBoundsRelative(width - GetPixelFromDips(50), width - GetPixelFromDips(10));
        }

        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition,
                                        int childPosition, long id) {
                TextView textOption = (TextView) v.findViewById(android.R.id.text1);
                adapter.setOption(groupPosition, String.valueOf(textOption.getText()));
                userPhone.setOption(groupPosition, String.valueOf(childPosition));
                expListView.collapseGroup(groupPosition);

                return false;
            }
        });

    }

    private int GetPixelFromDips(float pixels) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }

    @Override
    public void onClick(View v) {
        String str = phone.getText().toString();

        devicesAlarm.basicAlarmProperty.setUserPhone(countPhone, str);

        AdvancePreferences.addProperty(userPhone.NAME_PREFS_USER_PHONE, str);
        AdvancePreferences.addProperty(userPhone.NAME_PREFS_OPTIONS, userPhone.getOptionString());

        Toast.makeText(this, "Надо вставить обработку отправления СМС команды, " +
                "а сохранение данных убрать. Телефоны пусть читает после входящей СМСки", Toast.LENGTH_LONG).show();

        finish();
    }
}
