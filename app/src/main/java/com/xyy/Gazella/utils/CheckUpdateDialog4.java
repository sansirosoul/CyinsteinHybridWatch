package com.xyy.Gazella.utils;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.xyy.Gazella.activity.UpdateHardware;
import com.ysp.hybridtwatch.R;
import com.ysp.newband.BaseActivity;

/**
 * Created by Administrator on 2016/10/14.
 */

public class CheckUpdateDialog4 extends BaseActivity {

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.check_update_dialog4);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(CheckUpdateDialog4.this, UpdateHardware.class);
                startActivity(intent);
                finish();
            }
        },1500);
    }

}
