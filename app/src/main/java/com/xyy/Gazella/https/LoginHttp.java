package com.xyy.Gazella.https;


import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xyy.model.User;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/3/22.
 */

public class LoginHttp {
     public static final String url = "www.cyinstein.com";

     private void request(String name,String password){
          OkHttpUtils.post().url(url)
                  .addParams("name",name).addParams("password",password)
                  .build().execute(new UserCallback());
     }



     public class UserCallback extends Callback<User>{

          @Override
          public User parseNetworkResponse(Response response, int id) throws Exception {
               String string = response.body().string();
               User user = new Gson().fromJson(string,new TypeToken<List<User>>(){}.getType());
               return user;
          }

          @Override
          public void onError(Call call, Exception e, int id) {
               Log.e("",e.toString());
          }

          @Override
          public void onResponse(User response, int id) {

          }
     }
}
