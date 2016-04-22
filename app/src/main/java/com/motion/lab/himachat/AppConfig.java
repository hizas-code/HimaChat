package com.motion.lab.himachat;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by maaakbar on 4/10/16.
 */
public class AppConfig {
    static String pref = "Neuropedia";
    static String loginKey = "login";
    static String userKey = "user";
    static String passKey = "pass";

    static SharedPreferences getPreference(Context context){
        return context.getSharedPreferences(pref, Context.MODE_PRIVATE);
    }

    static boolean isLogin(Context context){
        return getPreference(context).getBoolean(loginKey, false);
    }

    static void saveLogin(Context context, boolean state){
        SharedPreferences.Editor editor = getPreference(context).edit();
        editor.putBoolean(loginKey, state);
        editor.apply();
    }

    static void saveAccount(Context context, String username, String password){
        SharedPreferences.Editor editor = getPreference(context).edit();
        editor.putString(userKey, username);
        editor.putString(passKey, password);
        editor.apply();
    }

    static String getLoggedUser(Context context){
        return getPreference(context).getString(userKey, "");
    }
}
