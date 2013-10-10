package com.alorma.megsjc.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by alorma on 19/05/13.
 */
public class AppUtils {

    public static boolean checkFirstTime(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

        return prefs.getBoolean(context.getPackageName()+ "-FIRST", true);
    }

    public static void saveFirstTime(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(context.getPackageName()+ "-FIRST", false);
        editor.commit();
    }
    public static void startNewActivityInNewTask(Activity context,Class c){
        Intent intent = new Intent(context,c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        context.finish();
    }
}
