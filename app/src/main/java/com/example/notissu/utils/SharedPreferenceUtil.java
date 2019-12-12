package com.example.notissu.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceUtil {
    public static final String PREF_NAME = "notissu";

    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    public static void init(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    public static void setStringValue(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }
    public static String getString(String key) {
        return sharedPreferences.getString(key, "");
    }

    public static void setIntValue(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

    public static int getInt(String key) { return sharedPreferences.getInt(key, -1); }

    public static void clearValue() {
        editor.clear();
    }
}
