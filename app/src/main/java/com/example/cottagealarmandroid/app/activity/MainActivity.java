package com.example.cottagealarmandroid.app.activity;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import com.example.cottagealarmandroid.app.R;
import com.example.cottagealarmandroid.app.activity.fragments.*;
import com.example.cottagealarmandroid.app.activity.fragments.dialogs.EnterPhoneAlarmFragment;
import com.example.cottagealarmandroid.app.adapters.TabsAdapter;
import com.example.cottagealarmandroid.app.controllers.AdvancePreferences;
import com.example.cottagealarmandroid.app.model.DevicesAlarm;
import com.example.cottagealarmandroid.app.model.Relay;
import com.example.cottagealarmandroid.app.service.DeliverySms;
import com.example.cottagealarmandroid.app.service.SendSms;
import com.example.cottagealarmandroid.app.service.SmsService;

public class MainActivity extends AppCompatActivity {
    //переменная для чтения ЛОГа
    final String LOG_TAG = "myLogs"; //this.getClass().getSimpleName();
    public final static String FILE_NAME = "filename";

    public final static String SENT = "SENT_SMS_ACTION";
    public final static String DELIVERED = "DELIVERED_SMS_ACTION";
    private SendSms sendSms;
    private DeliverySms deliverySms;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createTabs(savedInstanceState);

        AdvancePreferences.init(this);
        //clearAllProperty();//временный метод для удаления неправильных установок

        //DevicesAlarm надо будет переделать, чтобы в него подставлялись
        //объекты с уже считанными настройками. А то получается что есть такой класс
        //который сам по себе что-то подключил и как-то настроил
        DevicesAlarm devAlarm = DevicesAlarm.getInstance(); //Инициализируем устройство и устанавливаем его настройки

        devAlarm.setRelays(installRelays(devAlarm.COUNT_RELAY));

        //Блок регистрации слушателей СМС отправки-получения клиентом
        sendSms = new SendSms();
        deliverySms = new DeliverySms();
        registerReceiver(sendSms, new IntentFilter(SENT));
        registerReceiver(deliverySms, new IntentFilter(DELIVERED));

//        Intent mIntent = new Intent(this, SmsService.class);
//        mIntent.putExtra(SmsService.SMS_KEY, "Запуск службы");
//        this.startService(mIntent);
    }

    @Override
    protected void onStop() {
        //когда приложение переходит в ожидание или же закрывается
        // то снимаем с регистрации приёмники
        unregisterReceiver(sendSms);
        unregisterReceiver(deliverySms);
        super.onStop();
    }

    private void clearAllProperty() {
        AdvancePreferences.clearAllProperty();
        String name;
        for (int i = 0; i < 6; i++) {
            name = "РЕЛЕ" + i;
            AdvancePreferences.addProperty(name, name);
            name = "Sms" + i;
            AdvancePreferences.addProperty(name, "1");
            name = "РежимУправленияРЕЛЕ" + String.valueOf(i);
            AdvancePreferences.addProperty(name, "1");
        }

    }

    private Relay[] installRelays(final int count) {
        Relay[] relay = new Relay[count];
        for (int i = 0; i < relay.length; i++) {
            relay[i] = AdvancePreferences.getRelay(i);
        }
        return relay;
    }


    public void clickReloadSettings(View v) {
//        Toast.makeText(this, "Нажали обновить данные", Toast.LENGTH_SHORT).show();
    }

//    public void clickSMS(View v) {
//        //Блок обработки СМСсервиса
//        //String body = "01.01.13 12:04 1.+70000000 2.+7936589458 3.+7945362158 4.+7912356485";
//        EditText et = (EditText) findViewById(R.id.editTextMain);
//        String txt = et.getText().toString();
//        String body = "01.01.13 12:04 1.+" + txt + " 2.+7936589458 3.+7945362158 4.+7912356485";
//
//        Intent mIntent = new Intent(this, SmsService.class);
//        mIntent.putExtra(SmsService.SMS_KEY, body);
//        startService(mIntent); //ЗАпускаем сервис обработки СМС сообщений
//    }

    public void clickBtnStopService(View v) {
        stopService(new Intent(this, SmsService.class));
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
                startActivity(new Intent(this, SetDateTime.class));
                return true;
            case R.id.item_set_limit_temperature:
                Toast.makeText(this, "Limit temperature", Toast.LENGTH_SHORT).show();
                return true;
        }

        return super.onOptionsItemSelected(item);
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
}
