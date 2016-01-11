package com.example.cottagealarmandroid.app.activity.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.cottagealarmandroid.app.R;
import com.example.cottagealarmandroid.app.controllers.AdvancePreferences;
import com.example.cottagealarmandroid.app.controllers.DevicesAlarm;
import com.example.cottagealarmandroid.app.model.BasicAlarmProperty;
import com.example.cottagealarmandroid.app.model.UserPhones;


public class SetUserPhone extends FragmentActivity implements View.OnClickListener {
    private EditText phone;
    private DevicesAlarm devicesAlarm;
    private UserPhones userPhone;

    private int countPhone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_user_phone);
        phone = (EditText) findViewById(R.id.valueInputPhone);
        countPhone = getIntent().getIntExtra("phone", 0);

        devicesAlarm = DevicesAlarm.getInstance();
        userPhone = devicesAlarm.basicAlarmProperty.getUserPhone(countPhone);

        phone.setText(userPhone.getPhone());

    }

    @Override
    public void onClick(View v) {
        String str = phone.getText().toString();

        devicesAlarm.basicAlarmProperty.setUserPhone(countPhone, str);
        AdvancePreferences.addProperty(userPhone.NAME_PREFS_USER_PHONE, str);

        Toast.makeText(this, "Надо вставить обработку отправления СМС команды, " +
                "а сохранение данных убрать. Телефоны пусть читает после входящей СМСки", Toast.LENGTH_LONG).show();

        finish();

    }
}
