package com.xyy.Gazella.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.daimajia.androidanimations.library.YoYo;
import com.ysp.hybridtwatch.R;
import com.ysp.newband.BaseActivity;

import butterknife.ButterKnife;

import static com.daimajia.androidanimations.library.Techniques.ZoomIn;


/**
 * 启动页面
 */
public class StartActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ButterKnife.bind(this);
        YoYo.with(ZoomIn).duration(700).playOn(findViewById(R.id.text));
        YoYo.with(ZoomIn).duration(700).playOn(findViewById(R.id.logo));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(StartActivity.this, LaunchActivity.class);
                startActivity(intent);
                overridePendingTransitionEnter(StartActivity.this);
                finish();
            }
        }, 1000);
    }
}
