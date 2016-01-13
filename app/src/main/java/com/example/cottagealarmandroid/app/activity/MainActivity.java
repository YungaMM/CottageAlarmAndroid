package com.example.cottagealarmandroid.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TabHost;
import android.widget.Toast;
import com.example.cottagealarmandroid.app.R;
import com.example.cottagealarmandroid.app.activity.fragments.*;
import com.example.cottagealarmandroid.app.adapters.TabsAdapter;
import com.example.cottagealarmandroid.app.controllers.AdvancePreferences;
import com.example.cottagealarmandroid.app.controllers.DevicesAlarm;

public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createTabs(savedInstanceState);

        AdvancePreferences.init(this);
       // AdvancePreferences.clearAllProperty(); //временный метод для удаления неправильных установок
        DevicesAlarm.getInstance(); //Инициализируем устройство и устанавливаем его настройки


    }

    private void createTabs(final Bundle savedInstanceState) {
        TabHost mTabHost = (TabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup();

        ViewPager mViewPager = (ViewPager) findViewById(R.id.pager);

        TabsAdapter mTabsAdapter = new TabsAdapter(this, mTabHost, mViewPager);

        mTabsAdapter.addTab(mTabHost.newTabSpec("Основной экран")
                        .setIndicator(getString(R.string.stateAlarm))
                , BasicViewFragment.class, null);
        mTabsAdapter.addTab(mTabHost.newTabSpec("Экран реле")
                        .setIndicator(getString(R.string.managementRelay))
                , RelayFragment.class, null);

        if (savedInstanceState != null) {
            mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab"));
        }
    }

    public void clickReloadSettings(View v) {
        Toast.makeText(this, "Нажали обновить данные", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.item_phone_alarm:
                new EnterPhoneAlarmFragment().show(getSupportFragmentManager(), "PhoneAlarm");
                return true;
            case R.id.item_phone_user:
                startActivity(new Intent(this, SettingListUserPhones.class));
                return true;
            case R.id.item_set_date_alarm:
                startActivity(new Intent(this, SetDateTimeFragment.class));
                return true;
            case R.id.item_set_limit_temperature:
                Toast.makeText(this, "Limit temperature", Toast.LENGTH_SHORT).show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
