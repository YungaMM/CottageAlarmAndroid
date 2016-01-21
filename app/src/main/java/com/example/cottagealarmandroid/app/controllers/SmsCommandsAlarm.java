package com.example.cottagealarmandroid.app.controllers;

import com.example.cottagealarmandroid.app.model.BasicAlarmProperty;
import com.example.cottagealarmandroid.app.model.Relay;

public class SmsCommandsAlarm {
    private String command;

//    //метод возвращает команду Реле для СМС
//    //надо переделать чтобы метод брал номер реле и соединял с опциями этого реле
//    public static String createRelay(Relay relay) {
//        StringBuilder sb = new StringBuilder();
//
//        sb.append("#R");
//        sb.append(relay.getCount() + 1);
//        sb.append("=");
////        sb.append(relay.isSwitchOnOff() ? "1" : "0");
//        if (!relay.getSmsCommand().equals("")) {
//            sb.append(",");
//            sb.append(relay.getSmsCommand());
//            sb.append(",0");
//        }
//        return sb.toString();
//    }

    public static String createSmsRelayOff(final Relay relay){
        StringBuilder sb = new StringBuilder();
        sb.append("#R");
        sb.append(relay.getCount() + 1);
        sb.append("=0");
        return sb.toString();
    }

    public static String createSmsRelay(final Relay relay, final String minOn, final String secOn) {
        StringBuilder sb = new StringBuilder();

        sb.append("#R");
        sb.append(relay.getCount() + 1);
        sb.append("=1,");
        sb.append(minOn);
        sb.append("-");
        sb.append(secOn);
        sb.append(",0");

        return String.valueOf(sb);
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
