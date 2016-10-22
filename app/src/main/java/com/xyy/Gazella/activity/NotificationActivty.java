package com.xyy.Gazella.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.ysp.newband.BaseActivity;
import com.ysp.smartwatch.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/10/20.
 */

public class NotificationActivty extends BaseActivity {

    @BindView(R.id.btnExit)
    Button btnExit;
    @BindView(R.id.TVTitle)
    TextView TVTitle;
    @BindView(R.id.all)
    ToggleButton all;
    @BindView(R.id.tel)
    ToggleButton tel;
    @BindView(R.id.email)
    ToggleButton email;
    @BindView(R.id.twitter)
    ToggleButton twitter;
    @BindView(R.id.line)
    ToggleButton line;
    @BindView(R.id.qq)
    ToggleButton qq;
    @BindView(R.id.facebook)
    ToggleButton facebook;
    @BindView(R.id.message)
    ToggleButton message;
    @BindView(R.id.skype)
    ToggleButton skype;
    @BindView(R.id.wechat)
    ToggleButton wechat;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.notification_activity);
        ButterKnife.bind(this);

        if(Build.VERSION.SDK_INT >= 19){
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        }
        initView();
    }

    private void initView() {
        TVTitle.setText(R.string.msg_notify);
        if(all.isChecked()){
            tel.setEnabled(true);
        }else{
            tel.setEnabled(false);
            email.setEnabled(false);
            twitter.setEnabled(false);
            line.setEnabled(false);
            qq.setEnabled(false);
            facebook.setEnabled(false);
            message.setEnabled(false);
            skype.setEnabled(false);
            wechat.setEnabled(false);
        }

        all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    tel.setEnabled(true);
                }else {
                    tel.setEnabled(false);
                    email.setEnabled(false);
                    twitter.setEnabled(false);
                    line.setEnabled(false);
                    qq.setEnabled(false);
                    facebook.setEnabled(false);
                    message.setEnabled(false);
                    skype.setEnabled(false);
                    wechat.setEnabled(false);

                    tel.setChecked(false);
                    email.setChecked(false);
                    twitter.setChecked(false);
                    line.setChecked(false);
                    qq.setChecked(false);
                    facebook.setChecked(false);
                    message.setChecked(false);
                    skype.setChecked(false);
                    wechat.setChecked(false);
                }
            }
        });
    }

    @OnClick(R.id.btnExit)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnExit:
                finish();
                overridePendingTransitionExit(this);
                break;
        }
    }
}
