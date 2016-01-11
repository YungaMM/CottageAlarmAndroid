package com.example.cottagealarmandroid.app.model;


import com.example.cottagealarmandroid.app.activity.fragments.SettingListUserPhonesFragment;

import java.util.Set;

public class UserPhones {
    public String NAME_PREFS_USER_PHONE = "";

    private final int count;
    private String phone = "";
    private Set<String> optionSet;
    private String[] option = {"1", "1", "1", "1", "1", "1", "1"};

    public UserPhones() {
        this.count = 0;
        this.phone = "";
    }

    public UserPhones(final int count, final String phone) {
        NAME_PREFS_USER_PHONE = "userPhone" + count;
        this.count = count;
        this.phone = phone;
        for (int i = 0; i <option.length ; i++) {
            optionSet.add(option[i]);
        }
    }

//    public UserPhones(final int count, final String phone, final String[] option) {
//        NAME_PREFS_USER_PHONE = "userPhone" + count;
//        this.count = count;
//        this.phone = phone;
//        this.option = option;
//    }

    public Set<String> getOptionSet(){
        return optionSet;
    }

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

