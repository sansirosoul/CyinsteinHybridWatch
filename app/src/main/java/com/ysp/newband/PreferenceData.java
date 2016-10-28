package com.ysp.newband;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.Serializable;

/**
 * Created by dell on 2016/5/4.
 */
public class PreferenceData implements Serializable{
    private static final long serialVersionUID = 1019621703138147697L;

    /** Preference Config **/
    public static final String SHARED_PREFERENCE = "PartnerConfig";




    public static final String SAVE_SELECTED_HOUR_VALUE = "SAVE_SELECTED_HOUR_VALUE";
    public static final String SAVE_SELECTED_MUINUTES_VALUE = "SAVE_SELECTED_MUINUTES_VALUE";




    public static void clearAllSharePreferences(Context context) {
        clearSharePreferences(context, SHARED_PREFERENCE);
    }

    public static void clearSharePreferences(Context context, String spName) {
        SharedPreferences sharedPreferences = context.getApplicationContext()
                .getSharedPreferences(spName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit().clear();
        editor.commit();
    }

    /** 保存选中时针的值*/
    public static void setSelectedHourValue(Context context, float account) {
        SharedPreferences sharedPreferences = context.getApplicationContext()
                .getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(SAVE_SELECTED_HOUR_VALUE, account);
        editor.commit();
    }

    /** 获取选中 时针的值**/
    public static float getSelectedHourValue(Context context) {
        SharedPreferences sharedPreferences = context.getApplicationContext()
                .getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE);
        return sharedPreferences.getFloat(SAVE_SELECTED_HOUR_VALUE, 1.8f);
    }
    /** 保存选中 分针的值*/
    public static void setSelectedMuinutesValue(Context context, float account) {
        SharedPreferences sharedPreferences = context.getApplicationContext()
                .getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(SAVE_SELECTED_MUINUTES_VALUE, account);
        editor.commit();
    }

    /** 获取选中 分针的值**/
    public static float getSelectedMuinutesValue(Context context) {
        SharedPreferences sharedPreferences = context.getApplicationContext()
                .getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE);
        return sharedPreferences.getFloat(SAVE_SELECTED_MUINUTES_VALUE, 1.8f);
    }
}
