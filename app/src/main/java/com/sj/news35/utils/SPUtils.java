package com.sj.news35.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by sj on 2015/6/27 0027.
 */
public class SPUtils {

    private static final String SP_NAME = "config";
    private static SharedPreferences sp = null;

    public static void saveBoolean(Context context, String key, boolean value) {
        if (sp == null) {
            sp = context.getSharedPreferences("config", 0);
        }
        sp.edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolean(Context context, String key, boolean defValue) {
        if (sp == null) {
            sp = context.getSharedPreferences("config", 0);
        }
        return sp.getBoolean(key, defValue);
    }

    public static void saveString(Context context, String key, String value) {
        if (sp == null) {
            sp = context.getSharedPreferences("config", 0);
        }
        sp.edit().putString(key, value).commit();
    }

    public static String getString(Context context, String key, String defValue) {
        if (sp == null) {
            sp = context.getSharedPreferences("config", 0);
        }
        return sp.getString(key, defValue);
    }
}
