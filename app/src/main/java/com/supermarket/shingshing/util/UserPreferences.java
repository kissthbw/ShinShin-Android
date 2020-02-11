package com.supermarket.shingshing.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.supermarket.shingshing.models.LoginModel;

public class UserPreferences {
    private static final String KEY_TIPO = "loginTipo";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_AYUDA = "ayuda";

    public UserPreferences() {}

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences("APP_PREF", Context.MODE_PRIVATE);
    }

    public static void setLoginUser(Context context, LoginModel model) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putInt(KEY_TIPO, model.getTipo());
        editor.apply();
    }

    public static int getLoginUser(Context context) {
        return getSharedPreferences(context).getInt(KEY_TIPO, 0);
    }

    public static void setEmailUser(Context context, String email) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(KEY_EMAIL, email);
        editor.apply();
    }

    public static String getEmailUser(Context context) {
        return getSharedPreferences(context).getString(KEY_EMAIL, null);
    }

    public static void setEmailAyuda(Context context, String email) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(KEY_AYUDA, email);
        editor.apply();
    }

    public static String getEmailAyuda(Context context) {
        return getSharedPreferences(context).getString(KEY_AYUDA, null);
    }
}
