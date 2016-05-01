package com.example.cottagealarmandroid.app.controllers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;
import android.widget.Toast;
import com.example.cottagealarmandroid.app.model.DevicesAlarm;
import com.example.cottagealarmandroid.app.service.SmsService;


public class SmsReceiver extends BroadcastReceiver {
    private static final String ACTION = "android.provider.Telephony.SMS_RECEIVED";

    @Override
    public void onReceive(Context context, Intent intent) {//---получить входящее SMS сообщение---

        Toast.makeText(context,"СМС перехват",Toast.LENGTH_SHORT).show();

        if (intent != null && intent.getAction() != null &&
                ACTION.compareToIgnoreCase(intent.getAction()) == 0) {
            Object[] pduArray = (Object[]) intent.getExtras().get("pdus");
            SmsMessage[] messages = new SmsMessage[pduArray.length];
            for (int i = 0; i < pduArray.length; i++) {
                messages[i] = SmsMessage.createFromPdu((byte[]) pduArray[i]);
            }
            checkAddress(messages, context);
        }
    }

    private void checkAddress(final SmsMessage[] messages, final Context context){
        String sms_from = messages[0].getDisplayOriginatingAddress();
        String alarmPhone = DevicesAlarm.getInstance().getBasicAlarmProperty().getAlarmPhone();
        if (sms_from.equalsIgnoreCase(alarmPhone)) {
            StringBuilder bodyText = new StringBuilder();
            for (int i = 0; i < messages.length; i++) {
                bodyText.append(messages[i].getMessageBody());
            }

            String body = bodyText.toString();

            Toast.makeText(context,"Пришло сообщение от: " + sms_from + " - " +
                    body,Toast.LENGTH_LONG).show();

            Intent mIntent = new Intent(context, SmsService.class);
            mIntent.putExtra(SmsService.SMS_KEY, body);
            context.startService(mIntent);

            // надо подумать отдавать станд. обработчику наше полученное сообщение или нет
            //abortBroadcast();//прерывает обработку СМС сообщения другими программами
        }
    }
}
