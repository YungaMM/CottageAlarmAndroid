package com.example.cottagealarmandroid.app.controllers;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;
import com.example.cottagealarmandroid.app.service.SmsService;

public class SendSmsReceiver extends BroadcastReceiver{
    private final static String MY_TAG = "MyTag";

    @Override
    public void onReceive(Context context, Intent intent) {
        String textSMS;
        textSMS = intent.getStringExtra(ProcessingSMS.SENT);
//        String textSMS = intent.getStringExtra(SmsService.SMS_KEY);
        String serviceText = "Ошибка отправки СМС (" + textSMS + ")";

        switch(getResultCode()) {
            case Activity.RESULT_OK:
                Log.i(MY_TAG, "SMS send");
                Toast.makeText(context, "SMS sent",
                        Toast.LENGTH_SHORT).show();
                serviceText = "СМС (" + textSMS + ") отправлено";
                break;
            case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                Log.i(MY_TAG, "unknown problems");
                Toast.makeText(context, "Generic failure",
                        Toast.LENGTH_SHORT).show();
                serviceText = "СМС (" + textSMS + ") ошибка -> Generic failure";
                break;
            case SmsManager.RESULT_ERROR_RADIO_OFF:
                Log.i(MY_TAG, "modul is down");
                Toast.makeText(context, "Radio off",
                        Toast.LENGTH_SHORT).show();
                serviceText = "СМС (" + textSMS + ")ошибка -> Radio off";
                break;
            case SmsManager.RESULT_ERROR_NULL_PDU:
                Log.i(MY_TAG, "PDU error");
                Toast.makeText(context, "Null PDU",
                        Toast.LENGTH_SHORT).show();
                serviceText = "СМС (" + textSMS + ")ошибка -> Null PDU";
                break;
            case SmsManager.RESULT_ERROR_NO_SERVICE:
                Toast.makeText(context, "No service",
                        Toast.LENGTH_SHORT).show();
                serviceText = "СМС (" + textSMS + ")ошибка -> No service";
                break;
        }
        Intent mIntent = new Intent(context, SmsService.class);
        mIntent.putExtra(SmsService.SMS_KEY, serviceText);
        context.startService(mIntent);
    }
}
