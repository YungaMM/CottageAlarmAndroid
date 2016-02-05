package com.example.cottagealarmandroid.app.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import com.example.cottagealarmandroid.app.R;
import com.example.cottagealarmandroid.app.activity.MainActivity;

public class SmsService extends Service {
    final String LOG_TAG = "myLogs";

    NotificationManager notifManager;
    // Идентификатор уведомления
    private int NOTIFY_ID = 101; //надо сделать изменяемым по каждому получению СМС

    @Override
    public void onCreate() {
        super.onCreate();
        notifManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        NOTIFY_ID += 1;
        sendNotify();
        return super.onStartCommand(intent, flags, startId);
    }

    void sendNotify() {
        // 3) то, что произойдет, если мы нажмем на View из второй части.
        // Тут обычно идет вызов Activity, где мы можем просмотреть полную информацию
        // и обработать событие.
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(MainActivity.FILE_NAME, "somefile");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);

        // 1) то, что видно в статус-баре, когда уведомление только приходит – иконка и текст.
        // Текст потом исчезает и остается только иконка.
        // 2) то, что мы видим, когда открываем статус бар (тянем вниз).
        // Там уже полноценный View с иконкой и двумя текстами,
        // т.е. более подробная информация о событии.
        NotificationCompat.Builder notifBuild = new NotificationCompat.Builder(getBaseContext());
        notifBuild.setContentIntent(pIntent)
                .setSmallIcon(R.drawable.ic_launcher)
                .setTicker("Последнее китайское предупреждение!")// текст в строке состояния
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setContentTitle("Напоминание") // Заголовок уведомления
                .setContentText("Пора покормить кота") // Текст уведомления
                .setNumber(NOTIFY_ID);
        // большая картинка
        //.setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.hungrycat))
        //.setTicker(res.getString(R.string.warning)) // текст в строке состояния
        //.setContentTitle(res.getString(R.string.notifytitle)) // Заголовок уведомления
        //.setContentText(res.getString(R.string.notifytext)) // Текст уведомления

        Notification notify = notifBuild.build();
        //Устанавливаем стандартные звук и вибро
        notify.defaults = Notification.DEFAULT_SOUND |
                Notification.DEFAULT_VIBRATE;
        // ставим флаг, чтобы уведомление пропало после нажатия
        notify.flags |= Notification.FLAG_AUTO_CANCEL;
        // отправляем
        notifManager.notify(NOTIFY_ID, notify);

        Log.d(LOG_TAG, String.valueOf(NOTIFY_ID));

    }

    public IBinder onBind(Intent arg0) {
        return null;
    }
}
