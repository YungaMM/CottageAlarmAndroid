package com.example.cottagealarmandroid.app.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;
import com.example.cottagealarmandroid.app.R;
import com.example.cottagealarmandroid.app.activity.MainActivity;
import com.example.cottagealarmandroid.app.controllers.AdvancePreferences;
import com.example.cottagealarmandroid.app.model.DevicesAlarm;
import com.example.cottagealarmandroid.app.controllers.ProcessingSMS;

public class SmsService extends Service {
    public static final String SMS_KEY = "sms_body";
    final String LOG_TAG = "myLogs";

    NotificationManager notifyManager;
    // Идентификатор уведомления
    private int NOTIFY_ID = 0; //надо сделать изменяемым по каждому получению СМС

    @Override
    public void onCreate() {
        super.onCreate();
        notifyManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        NOTIFY_ID += 1;
        String smsBody = intent.getExtras().getString(SMS_KEY);
        AdvancePreferences.init(this);

        readSms(smsBody);
        showNotify(smsBody);

        String bsp = DevicesAlarm.getInstance().getBasicAlarmProperty().getUserPhoneNumber(0);
        Toast.makeText(getBaseContext(), bsp, Toast.LENGTH_SHORT).show();

        stopSelf();
        return super.onStartCommand(intent, flags, startId);
    }

    private void readSms(String smsBody) {
        ProcessingSMS prSms = new ProcessingSMS();
        prSms.checkSMS(smsBody);
    }

    void showNotify(final String smsBody) {
        Intent intent = new Intent(this, MainActivity.class);

        intent.putExtra(MainActivity.FILE_NAME, smsBody);

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder notifyBuild = new NotificationCompat.Builder(getBaseContext());
        notifyBuild.setContentIntent(pIntent)
                .setSmallIcon(R.drawable.ic_launcher)
                .setTicker("Последнее китайское предупреждение!")// текст в строке состояния
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setContentTitle("Напоминание") // Заголовок уведомления
                .setContentText(smsBody) // Текст уведомления
                .setNumber(NOTIFY_ID)
                .setDefaults(Notification.DEFAULT_SOUND |
                        Notification.DEFAULT_VIBRATE) //Устанавливаем стандартные звук и вибро
                .setAutoCancel(true); // ставим флаг, чтобы уведомление пропало после нажатия
        Notification notify = notifyBuild.build();
        notifyManager.notify(NOTIFY_ID, notify); // отправляем
        //Log.d(LOG_TAG, String.valueOf(NOTIFY_ID));
    }

    public IBinder onBind(Intent arg0) {
        return null;
    }
}
