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

    private TabHost mTabHost;
    private ViewPager mViewPager;
    private TabsAdapter mTabsAdapter;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createTabs(savedInstanceState);

        AdvancePreferences.init(this);
        DevicesAlarm.getInstance(); //Инициализируем устройство и устанавливаем его настройки


    }

    private void createTabs(final Bundle savedInstanceState) {
        mTabHost = (TabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup();

        mViewPager = (ViewPager) findViewById(R.id.pager);

        mTabsAdapter = new TabsAdapter(this, mTabHost, mViewPager);

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
                startActivity(new Intent(this, SettingListUserPhonesFragment.class));
                Toast.makeText(this, "Phone user", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item_set_date_alarm:
                //Intent intent = new Intent(this, SetDateTimeFragment.class);
                startActivity(new Intent(this, SetDateTimeFragment.class));
                return true;
            case R.id.item_set_limit_temperature:
                Toast.makeText(this, "Limit temperature", Toast.LENGTH_SHORT).show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
