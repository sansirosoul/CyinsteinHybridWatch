package com.ysp.newband;

import android.content.Context;
import android.content.SharedPreferences;

import com.xyy.model.User;

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
    public static final String SAVE_SELECTED_SMALL1_VALUE = "SAVE_SELECTED_SMALL1_VALUE";
    public static final String SAVE_SELECTED_SMALL2_VALUE = "SAVE_SELECTED_SMALL2_VALUE";
    public static final String SAVE_SELECTED_SMALL3_VALUE = "SAVE_SELECTED_SMALL3_VALUE";
    public static final String SAVE_ADDRESS_VALUE = "SAVE_ADDRESS_VALUE";
    public static final String SAVE_DEVICE_SN_VALUE = "SAVE_DEVICE_SN_VALUE";
    public static final String SAVE_DEVICE_FWV_VALUE = "SAVE_DEVICE_FWV_VALUE";


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
        return sharedPreferences.getFloat(SAVE_SELECTED_MUINUTES_VALUE, 0f);
    }


    /** 保存选中 小针 (1) 的值*/
    public static void setSelectedSmall1Value(Context context, int account) {
        SharedPreferences sharedPreferences = context.getApplicationContext()
                .getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(SAVE_SELECTED_SMALL1_VALUE, account);
        editor.commit();
    }

    /** 获取选中 小针 (1) 的值**/
    public static int getSelectedSmall1Value(Context context) {
        SharedPreferences sharedPreferences = context.getApplicationContext()
                .getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(SAVE_SELECTED_SMALL1_VALUE, 0);
    }
    /** 保存选中 小针 (2) 的值*/
    public static void setSelectedSmall2Value(Context context, int account) {
        SharedPreferences sharedPreferences = context.getApplicationContext()
                .getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(SAVE_SELECTED_SMALL2_VALUE, account);
        editor.commit();
    }

    /** 获取选中 小针 (2) 的值**/
    public static int getSelectedSmall2Value(Context context) {
        SharedPreferences sharedPreferences = context.getApplicationContext()
                .getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(SAVE_SELECTED_SMALL2_VALUE, 0);
    }
    /** 保存选中 小针 (3) 的值*/
    public static void setSelectedSmall3Value(Context context, int account) {
        SharedPreferences sharedPreferences = context.getApplicationContext()
                .getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(SAVE_SELECTED_SMALL3_VALUE, account);
        editor.commit();
    }

    /** 获取选中 小针 (3) 的值**/
    public static int getSelectedSmall3Value(Context context) {
        SharedPreferences sharedPreferences = context.getApplicationContext()
                .getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(SAVE_SELECTED_SMALL3_VALUE, 0);
    }

    /** 保存选中  设备的Address 的值*/
    public static void setAddressValue(Context context, String account) {
        SharedPreferences sharedPreferences = context.getApplicationContext()
                .getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SAVE_ADDRESS_VALUE, account);
        editor.commit();
    }

    /** 获取选中 设备的Address 的值**/
    public static String getAddressValue(Context context) {
        SharedPreferences sharedPreferences = context.getApplicationContext()
                .getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SAVE_ADDRESS_VALUE,"");
    }

    //保存个人信息
    public static void setUserInfo(Context context,String name, String birth, int sex, String height, String weight) {
        SharedPreferences sharedPreferences = context.getApplicationContext()
                .getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", name).commit();
        editor.putString("birth", birth).commit();
        editor.putInt("sex", sex).commit();
        editor.putString("height", height).commit();
        editor.putString("weight", weight).commit();
    }

    //获取个人信息
    public static User getUserInfo(Context context) {
        SharedPreferences sharedPreferences = context.getApplicationContext()
                .getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE);
        User user = new User();
        user.setName(sharedPreferences.getString("name", null));
        user.setBirthday(sharedPreferences.getString("birth", null));
        user.setSex(sharedPreferences.getInt("sex", -1));
        user.setHeight(sharedPreferences.getString("height", null));
        user.setWeight(sharedPreferences.getString("weight", null));
        return user;
    }

    //保存手表序列号
    public static void setDeviceSnValue(Context context,String sn){
        SharedPreferences sharedPreferences = context.getApplicationContext()
                .getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SAVE_DEVICE_SN_VALUE,sn).commit();
    }

    //获取手表序列号
    public static String getDeviceSnValue(Context context){
        SharedPreferences sharedPreferences = context.getApplicationContext()
                .getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SAVE_DEVICE_SN_VALUE,null);
    }

    //保存手表固件版本号
    public static void setDeviceFwvValue(Context context,String fwv){
        SharedPreferences sharedPreferences = context.getApplicationContext()
                .getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SAVE_DEVICE_FWV_VALUE,fwv).commit();
    }

    //获取手表件版本号
    public static String getDeviceFwvValue(Context context){
        SharedPreferences sharedPreferences = context.getApplicationContext()
                .getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SAVE_DEVICE_FWV_VALUE,null);
    }
}
