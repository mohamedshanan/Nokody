package com.nokody.merchant.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;

import com.nokody.merchant.App;

public class Utilities {

    public static void changeFontPath() {

    }

    public static String getLangFromPref(App app) {
        return "en";
    }

    public static void loadLanguage(Context context) {

    }

    public static boolean getNetworkState(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            return false;
        }
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return (activeNetwork != null
                && (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI || activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE));
    }

    public static void saveStringToPref(String key, String value, Context context) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        defaultSharedPreferences.edit().putString(key, value).apply();
    }

    public static String getStringFromPref(Context context, String key) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return defaultSharedPreferences.getString(key, "");
    }

    public static void saveIntToPref(String key, Integer value, Context context) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        defaultSharedPreferences.edit().putInt(key, value).apply();
    }

    public static Integer getIntFromPref(Context context, String key) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return defaultSharedPreferences.getInt(key, -1);
    }
}
