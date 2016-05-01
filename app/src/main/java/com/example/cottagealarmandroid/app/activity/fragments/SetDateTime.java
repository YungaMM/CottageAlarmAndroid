package com.example.cottagealarmandroid.app.activity.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;
import com.example.cottagealarmandroid.app.R;
import com.example.cottagealarmandroid.app.controllers.AdvancePreferences;
import com.example.cottagealarmandroid.app.model.DevicesAlarm;
import com.example.cottagealarmandroid.app.model.BasicAlarmProperty;


public class SetDateTime extends FragmentActivity implements View.OnClickListener {

    private TimePicker tp;
    private DatePicker dp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_date_time);
        tp = (TimePicker) this.findViewById(R.id.timePicker);
        tp.setIs24HourView(true);

        dp = (DatePicker) this.findViewById(R.id.datePicker);
        dp.setCalendarViewShown(false);

    }

    @Override
    public void onClick(View v) {
        String str = String.format("%02d:%02d"
                , tp.getCurrentHour()
                , tp.getCurrentMinute());

        DevicesAlarm.getInstance().getBasicAlarmProperty().setTimeInDevice(str);
        AdvancePreferences.addProperty(BasicAlarmProperty.NAME_PREFS_TIME_IN_DEVICE, str);

        str = String.format("%02d.%02d.%s"
                , dp.getDayOfMonth()
                , dp.getMonth() + 1
                , String.valueOf(dp.getYear()).substring(2));
        DevicesAlarm.getInstance().getBasicAlarmProperty().setDateInDevice(str);
        AdvancePreferences.addProperty(BasicAlarmProperty.NAME_PREFS_DATE_IN_DEVICE, str);

        Toast.makeText(this, "Надо вставить обработку отправления СМС команды, " +
                "а сохранение данных убрать. Дату пусть читает после входящей СМСки", Toast.LENGTH_LONG).show();

        finish();
    }

}
