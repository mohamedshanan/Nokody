package com.nokody.merchant.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.nokody.merchant.App;
import com.nokody.merchant.base.BaseActivity;

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
}
