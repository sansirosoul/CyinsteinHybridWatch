package com.xyy.Gazella.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.xyy.Gazella.view.SwitchView;
import com.ysp.newband.BaseActivity;
import com.ysp.newband.PreferenceData;
import com.ysp.smartwatch.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/10/20.
 */

public class NotificationActivty extends BaseActivity {

    @BindView(R.id.btnExit)
    LinearLayout btnExit;
    @BindView(R.id.TVTitle)
    TextView TVTitle;
    @BindView(R.id.all)
    SwitchView all;
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

        if (Build.VERSION.SDK_INT >= 19) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        }
        initView();
    }


    private void initView() {
        TVTitle.setText(R.string.msg_notify);
        email.setEnabled(false);
        twitter.setEnabled(false);
        line.setEnabled(false);
        qq.setEnabled(false);
        facebook.setEnabled(false);
        message.setEnabled(false);
        skype.setEnabled(false);
        wechat.setEnabled(false);

        int state = PreferenceData.getNotificationState(NotificationActivty.this);
        if(state==1){
            all.setOpened(true);

            tel.setEnabled(true);
            if(PreferenceData.getNotificationPhoneState(NotificationActivty.this)==1){
                tel.setChecked(true);
            }else{
                tel.setChecked(false);
            }
        }else{
            all.setOpened(false);

            tel.setEnabled(false);
        }

        all.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn(SwitchView view) {
                all.setOpened(true);
                PreferenceData.setNotificationState(NotificationActivty.this,1);

                tel.setEnabled(true);
            }

            @Override
            public void toggleToOff(SwitchView view) {
                all.setOpened(false);
                PreferenceData.setNotificationState(NotificationActivty.this,0);
                PreferenceData.setNotificationPhoneState(NotificationActivty.this,0);

                tel.setChecked(false);
                tel.setEnabled(false);
            }
        });

        tel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    PreferenceData.setNotificationPhoneState(NotificationActivty.this,1);

                } else {
                    PreferenceData.setNotificationPhoneState(NotificationActivty.this,0);

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
