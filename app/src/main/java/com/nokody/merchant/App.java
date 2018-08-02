package com.nokody.merchant;

import android.app.Application;
import android.content.Context;

import com.nokody.merchant.utils.Utilities;

public class App extends Application {

    public static String appLang;

    public static App get(Context context) {
        return (App) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appLang = Utilities.getLangFromPref(this);
    }
}
