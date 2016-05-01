package com.example.cottagealarmandroid.app.controllers;

import android.content.Context;
import android.telephony.SmsManager;
import android.widget.Toast;
import com.example.cottagealarmandroid.app.model.*;

public class ProcessingSMS {
    private String textSms;

    private BasicAlarmProperty basicSett;
    private Temperature temperature;
    private Battery battery;
    private Energy energy;
    private Relay[] relay;

    private final static DevicesAlarm devAlarm = DevicesAlarm.getInstance();

    public ProcessingSMS() {
        basicSett = devAlarm.getBasicAlarmProperty();
        relay = devAlarm.getRelays();
    }

    public static void sendSms(final Context context, final String textSms) {
        String phone = devAlarm.getBasicAlarmProperty().getAlarmPhone();

        if (!phone.equals("")) {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phone, null, textSms, null, null);
        } else {
            Toast.makeText(context, "Укажите номер телефона устройства", Toast.LENGTH_SHORT).show();
        }
    }

    //Обрабатывет полученное СМС и устанвливает соответствущие параметры сигнализации
    public void checkSMS(final String textSms) {
        this.textSms = textSms;

        if (textSms.contains("ERITEA")) {
            processEritea();
        } else if (textSms.contains("ПИТ.")) {
            processAc();
        } else if (textSms.contains("РЕЛЕ")) {
            processRelay();
        } else if (textSms.contains("1.")) {
            processPhoneList();
        }
    }

    private void processPhoneList() {
        for (int i = 0; i < BasicAlarmProperty.COUNT_USER_PHONES; i++) {
            String nameUserPhone = basicSett.getUserPhone(i).getNAME_PREFS_USER_PHONE();
            String phone = loadCommand((" " + (i + 1) + "."), (" " + (i + 2) + "."));
            AdvancePreferences.addProperty(nameUserPhone, phone);
            basicSett.setUserPhone(i, phone);
        }
    }

    private void processRelay() {
        int ii = textSms.contains("РЕЛЕ 1") ? 3 : 6;
        String smsCommand;

        for (int i = ii - 3; i < ii; i++) {
            String start = "РЕЛЕ " + (i + 1) + "=";
            String end = " РЕЛЕ " + (i + 2);

            if (loadCommand(start, end).contains("ВЫКЛ")) {
                smsCommand = SmsCommandsAlarm.createSmsRelayOff(relay[i]);
                relay[i].setSmsCommand(smsCommand);
                relay[i].setModeControl("0");
            } else { //ВКЛ
                String lc = loadCommand(("РЕЛЕ " + (i + 1) + "=ВКЛ "), (" РЕЛЕ " + (i + 2)));//lc="00-00"
                int index = lc.indexOf("-");
                String textMin = lc.substring(0, index-1);
                String textSec = lc.substring(index+1, index+3);

                smsCommand = SmsCommandsAlarm.createSmsRelay(relay[i],textMin,textSec);
                relay[i].setSmsCommand(smsCommand);
                relay[i].setModeControl("1");
            }

            AdvancePreferences.addRelay(relay[i]);
        }
    }

    private void processAc() {
        if (textSms.contains("НОРМА")) {
            energy.setEnergy(true);
            if (textSms.indexOf("Т=") > 0) {
                temperature.setTemperature(Integer.valueOf(loadCommand("Т=", "С"))); //"T=" и "С" может быть русской
                // или латинской, в Андроид надо проверить
            }
        } else if (textSms.contains("ВЫКЛ")) {
            energy.setEnergy(false);
            battery.setBattery(Double.valueOf(loadCommand("АКБ=", " В")));
        }
    }

    private void processEritea() {
        basicSett.setTimeInDevice(loadCommand("TIME", "-"));
        basicSett.setDateInDevice(loadCommand("-", "T1"));

        temperature.setTemperature(Integer.valueOf(loadCommand("T1", "T2")));

        String s = textSms.contains("OKAKB=") ? "OKAKB=" : "FALLAKB=";
        battery.setBattery(Double.valueOf(loadCommand(s, "TRM")));
        energy.setEnergy(s.equals("OKAKB="));
    }

    private String loadCommand(final String startCommand, final String endCommand) {
        int beginIndex = textSms.indexOf(startCommand) + startCommand.length();
        int endIndex = textSms.indexOf(endCommand) > 0 ? textSms.indexOf(endCommand) : textSms.indexOf("\n");

        return endIndex - beginIndex > 0 ? textSms.substring(beginIndex, endIndex) : "";
    }

}
