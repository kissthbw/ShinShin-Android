package com.supermarket.shingshing.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.supermarket.shingshing.models.LoginModel;

public class UserPreferences {
    private static final String KEY_TIPO = "loginTipo";

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
}
