package com.example.cottagealarmandroid.app.controllers;


import android.content.Context;
import android.content.SharedPreferences;
import com.example.cottagealarmandroid.app.model.Relay;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class AdvancePreferences {
    public static final String APP_FILE_PREFS = "alarmsettings";

    private static SharedPreferences settings = null;
    private static SharedPreferences.Editor editor = null;
    private static Context context = null;

    public static void init(Context context) {
        AdvancePreferences.context = context;
    }

    private static void init() {
        settings = context.getSharedPreferences(APP_FILE_PREFS, Context.MODE_PRIVATE);
        editor = settings.edit();
    }

    //Временный метод для очистки неправильно записанных данных
    public static void clearAllProperty(){
        if (settings == null) {
            init();
        }
        editor.clear();
        editor.commit();
    }

    public static void addRelay(final Relay relay){
        if (settings == null) {
            init();
        }
        addProperty(relay.getNAME_PREFS_RELAY(),relay.getName());
        addProperty(relay.getNAME_PREFS_MODE_CONTROL(),relay.getModeControl());
        addProperty(relay.getNAME_PREFS_SMS(),relay.getSmsCommand());
    }

    public static void addProperty(String name, String value) {
        if (settings == null) {
            init();
        }
        editor.putString(name, value);
        editor.commit();
    }

    public static void addSetProperty(final String name, final String[] value) {
        if (settings == null) {
            init();
        }
        Set<String> result = new TreeSet<>();
        for (int i = 0; i <value.length ; i++) {
            result.add(value[i]);
        }

        editor.putStringSet(name, result);
        editor.commit();
    }

    public static void addIntProperty(String name, int value) {
        if (settings == null) {
            init();
        }
        editor.putInt(name, value);
        editor.commit();
    }

    public static void addBooleanProperty(String name, boolean value) {
        if (settings == null) {
            init();
        }
        editor.putBoolean(name, value);
        editor.commit();
    }

    public static Relay getRelay (final int count){
        if (settings == null) {
            init();
        }
        Relay relay = new Relay(count);
        relay.setName(getProperty(relay.getNAME_PREFS_RELAY()));
        relay.setModeControl(getProperty(relay.getNAME_PREFS_MODE_CONTROL()));
        relay.setSmsCommand(getProperty(relay.getNAME_PREFS_SMS()));
        return relay;
    }

    public static String getProperty(String name) {
        if (settings == null) {
            init();
        }
        return settings.getString(name, "");
    }

    public static Set<String> getSetProperty(final String name){
        if (settings == null) {
            init();
        }
        return settings.getStringSet(name, new HashSet<String>());
    }

    public static boolean getBooleanProperty(String name) {
        if (settings == null) {
            init();
        }
        return settings.getBoolean(name, false);
    }

    public static int getIntProperty(String name) {
        if (settings == null) {
            init();
        }
        return settings.getInt(name, 0);
    }

    public static Map<String, ?> getAllProperty() {
        if (settings == null) {
            init();
        }
        return settings.getAll();
    }
}
