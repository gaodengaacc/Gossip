package com.gordon.main;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author Gordon
 * @since 2017/4/5
 * do()
 */
public class Account {

    public static Preference preference() {
        return Preference.instance();
    }

    public static class Preference {

        public static Preference mInstance;

        public static Preference instance() {
            if (mInstance == null) {
                mInstance = new Preference();
            }
            return mInstance;
        }
        private boolean isFirstInstall;
        private final String KEY_LOGIN = "login";

        public void clear() {
            setLogin(false);
        }


        public boolean isLogin() {
            return getBoolean(KEY_LOGIN);
        }

        public void setLogin(boolean isFirstInstall) {
            this.isFirstInstall = isFirstInstall;
            saveBoolean(KEY_LOGIN, isFirstInstall);
        }
    }


    private static void saveString(String key, String value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(key, value);
        editor.commit();
    }

    private static String getString(String key) {
        return getSharedPreferences().getString(key, null);
    }

    private static void saveBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    private static boolean getBoolean(String key) {
        return getSharedPreferences().getBoolean(key, false);
    }

    static SharedPreferences getSharedPreferences() {
        return AppApplication.getInstance().getSharedPreferences("Account", Context.MODE_PRIVATE);
    }
}
