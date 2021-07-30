package com.deshbangla.shrimpandfish.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.deshbangla.shrimpandfish.model.User;
import com.google.gson.Gson;

import static android.content.Context.MODE_PRIVATE;

public class SharedPreferenceData {

    private static final String MY_PREFS_NAME = "MyPreference";

    public static void setUserPreferenceData(Context context, User user){
        SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        editor.putString("UserData", json);
        editor.apply();
    }

    public static User getUserPreferenceData(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        Gson gson = new Gson();
        String json = prefs.getString("UserData", "");
        return gson.fromJson(json, User.class);
    }

    public static void logOut(Context context){
        SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();
    }

}
