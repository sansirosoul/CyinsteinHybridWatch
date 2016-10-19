package com.xyy.Gazella.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.xyy.model.User;


/**
 * Created by Administrator on 2016/10/18.
 */

public class SharedPreferencesUtils {
    Context context;
    SharedPreferences sp;

    public SharedPreferencesUtils(Context context){
        this.context=context;
        sp = context.getSharedPreferences("userinfo",Context.MODE_PRIVATE);
    }

    public void setUserInfo(String name,String birth,int sex,String height,String weight){
        sp.edit().putString("name",name).commit();
        sp.edit().putString("birth",birth).commit();
        sp.edit().putInt("sex",sex).commit();
        sp.edit().putString("height",height).commit();
        sp.edit().putString("weight",weight).commit();
    }

    public User getUserInfo(){
             User user = new User();
             user.setName(sp.getString("name",null));
             user.setBirthday(sp.getString("birth",null));
             user.setSex(sp.getInt("sex",-1));
             user.setHeight(sp.getString("height",null));
             user.setWeight(sp.getString("weight",null));
        return user;
    }
}
