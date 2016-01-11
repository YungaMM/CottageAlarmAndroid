package com.example.cottagealarmandroid.app.model;


public class UserPhones {
    public String NAME_PREFS_USER_PHONE = "";
    public String NAME_PREFS_OPTIONS = "";

    private final int count;
    private String phone = "";
    private String[] option = {"1", "1", "1", "1", "1", "1", "1"};

    public UserPhones() {
        this.count = 0;
        this.phone = "";
    }

    public UserPhones(final int count, final String phone) {
        NAME_PREFS_USER_PHONE = "userPhone" + count;
        NAME_PREFS_OPTIONS = "set" + NAME_PREFS_USER_PHONE;
        this.count = count;
        this.phone = phone;
    }

//    public UserPhones(final int count, final String phone, final String[] option) {
//        NAME_PREFS_USER_PHONE = "userPhone" + count;
//        this.count = count;
//        this.phone = phone;
//        this.option = option;
//    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(final String phone) {
        this.phone = phone;
    }

    public String[] getOption() {
        return option;
    }

    public void setOption(String[] option) {
        this.option = option;
    }

    public int getCount() {
        return count;
    }
}

