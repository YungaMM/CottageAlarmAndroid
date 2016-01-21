package com.example.cottagealarmandroid.app.controllers;


import com.example.cottagealarmandroid.app.model.*;
import java.io.FileNotFoundException;

public class ProcessingSMS {
    private String textSms;

    private BasicAlarmProperty basicSett;
    private Temperature temperature;
    private Battery battery;
    private Energy energy;
    private Relay[] relay;

    public ProcessingSMS(final DevicesAlarm deviceAlarm) {
        basicSett = deviceAlarm.getBasicAlarmProperty();
        temperature = deviceAlarm.getTemperature();
        battery = deviceAlarm.getBattery();
//        energy = deviceAlarm.getEnergy();
        relay = deviceAlarm.getRelays();
        textSms = "";
    }

    //Обрабатывет полученное СМС и устанвливает соответствущие параметры сигнализации
    public void checkSMS(final String textSms) throws FileNotFoundException {
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
        for (int i = 0; i < basicSett.getUserPhones().length; i++) {
            basicSett.setUserPhone(i, loadCommand(" " + (i + 1) + ".", (" " + (i + 2) + ".")));
        }
    }

    private void processRelay() {
        int ii = textSms.contains("РЕЛЕ 1") ? 3 : 6;
        String lc;

        for (int i = ii - 3; i < ii; i++) {
            lc = loadCommand(("РЕЛЕ " + (i + 1) + "="), (" РЕЛЕ " + (i + 2)));
            if (lc.contains("ВЫКЛ")) {
//                relay[i].setSwitchOnOff(false);
                relay[i].setSmsCommand("");
            } else { //ВКЛ
                lc = loadCommand(("РЕЛЕ " + (i + 1) + "=ВКЛ "), (" РЕЛЕ " + (i + 2)));
                relay[i].setSmsCommand(lc);
//                relay[i].setSwitchOnOff(true);
            }
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
        energy.setEnergy(s.equals("OKAKB=") ? true : false);
    }

    private String loadCommand(final String startCommand, final String endCommand) {
        int beginIndex = textSms.indexOf(startCommand) + startCommand.length();
        int endIndex = textSms.indexOf(endCommand) > 0 ? textSms.indexOf(endCommand) : textSms.indexOf("\n");

        return endIndex - beginIndex > 0 ? textSms.substring(beginIndex, endIndex) : "";
    }

}
