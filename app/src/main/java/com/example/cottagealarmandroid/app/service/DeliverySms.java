package com.example.cottagealarmandroid.app.service;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class DeliverySms extends BroadcastReceiver {

    private final static String MY_TAG = "MyTag";

    @Override
    public void onReceive(Context context, Intent intent) {
        String textSms = intent.getStringExtra(SmsService.SMS_KEY);
        String serviceText = "Ошибка доставки СМС (" + textSms + ")";

        switch(getResultCode()) {
            case Activity.RESULT_OK:
                Log.i(MY_TAG, "SMS delivered");
                Toast.makeText(context, "SMS delivered",
                        Toast.LENGTH_SHORT).show();
                serviceText = "СМС (" + textSms + ") доставлено";
                break;
            case Activity.RESULT_CANCELED:
                Log.i(MY_TAG, "SMS not delivered");
                Toast.makeText(context, "SMS not delivered",
                        Toast.LENGTH_SHORT).show();
                serviceText = "СМС (" + textSms + ") не доставлено";
                break;
        }
        Intent mIntent = new Intent(context, SmsService.class);
        mIntent.putExtra(SmsService.SMS_KEY, serviceText);
        context.startService(mIntent);
    }
}

