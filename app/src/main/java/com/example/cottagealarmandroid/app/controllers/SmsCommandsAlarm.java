package com.example.cottagealarmandroid.app.controllers;

import com.example.cottagealarmandroid.app.model.BasicAlarmProperty;
import com.example.cottagealarmandroid.app.model.Relay;

public class SmsCommandsAlarm {
    private String command;

    //метод возвращает команду Реле для СМС
    public static String setRelay(Relay relay) {
        StringBuilder sb = new StringBuilder();

        sb.append("#R");
        sb.append(relay.getCount() + 1);
        sb.append("=");
        sb.append(relay.isSwitchOnOff() ? "1" : "0");
        if (!relay.getOption().equals("")) {
            sb.append(",");
            sb.append(relay.getOption());
            sb.append(",0");
        }
        return sb.toString();
    }

    public static String setTimeDate(BasicAlarmProperty basicAlarmProperty){
        String time = basicAlarmProperty.getTimeInDevice();
        String date = basicAlarmProperty.getDateInDevice();

        time = time.substring(0,2) + time.substring(3);
        date = date.substring(0,2) + date.substring(3,5) + date.substring(6);
        return "#D=" + time + "," + date;
    }

    public static String setTypeAndDirectionPhone(final int countPhone){
        return "";
    }

    public static String queryRele(final String command){
        return command.equals("R2") ? "?R2" : "?2";
    }

    public static String queryFull(){
        return  "V?Z";
    }

    public static String queryPhone(){
        return "?4";
    }

    public static String queryDeviceStatus(){ return "?5";}
}
