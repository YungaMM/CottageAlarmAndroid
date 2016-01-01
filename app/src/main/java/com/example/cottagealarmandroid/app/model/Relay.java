package com.example.cottagealarmandroid.app.model;

public class Relay {

    public static String NAME_PREFS_RELAY = "";

    private static final String COMMAND_OFF = "#R%d=0";
    private static final String COMMAND_ON = "#R%d=1%s";

    private static String command;

    private final int count;
    private boolean switchOnOff;
    private String option;

    public Relay(final int count, final boolean switchOnOff, final String option) {
        NAME_PREFS_RELAY = "relay" + count;
        this.count = count;
        this.switchOnOff = switchOnOff;
        this.option = option;
    }

    //методы textRelayCommand созданы для файла setting.txt
    public static String textRelayCommand(final Relay relay) {
        command = relay.isSwitchOnOff() ? COMMAND_ON : COMMAND_OFF;
        return String.format(command, relay.getCount() + 1, relay.getOption());
    }

    public String textRelayCommand() {
        command = this.switchOnOff ? COMMAND_ON : COMMAND_OFF;
        return String.format(command, this.count + 1, this.option);
    }

    //********************get/set*******************************************************

    public void setSwitchOnOff(final boolean switchOnOff) {
        this.switchOnOff = switchOnOff;
    }

    public void setSwitchOnOff(final boolean switchOnOff, final String option) {
        this.switchOnOff = switchOnOff;
        this.option = option;
    }

    public boolean isSwitchOnOff() {
        return switchOnOff;
    }

    public int getCount() {
        return count;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String optionRelayOn) {
        this.option = optionRelayOn;
    }
}
