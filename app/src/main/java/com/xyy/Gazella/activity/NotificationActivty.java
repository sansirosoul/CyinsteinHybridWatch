package com.xyy.Gazella.activity;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.xyy.Gazella.services.NotificationService;
import com.xyy.Gazella.view.SwitchView;
import com.ysp.hybridtwatch.R;
import com.ysp.newband.BaseActivity;
import com.ysp.newband.PreferenceData;
import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionDenied;
import com.zhy.m.permission.PermissionGrant;

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
    private static final String ENABLED_NOTIFICATION_LISTENERS = "enabled_notification_listeners";

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
        if (!isNotificationAccessEnabled()) {
            //通知监听授权
            Intent intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
            startActivity(intent);
        }
        toggleNotificationListenerService(this);
        getPermission();
        PreferenceData.setNotificationShakeState(this,1);
    }

    public void toggleNotificationListenerService(Context context) {
        PackageManager pm = context.getPackageManager();
        pm.setComponentEnabledSetting(
                new ComponentName(context, NotificationService.class),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

        pm.setComponentEnabledSetting(
                new ComponentName(context, NotificationService.class),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
    }

    //判断通知权限是否开启
    private boolean isNotificationAccessEnabled() {
        String pkgName = getPackageName();
        final String flat = Settings.Secure.getString(getContentResolver(),
                ENABLED_NOTIFICATION_LISTENERS);
        if (!TextUtils.isEmpty(flat)) {
            final String[] names = flat.split(":");
            for (int i = 0; i < names.length; i++) {
                final ComponentName cn = ComponentName.unflattenFromString(names[i]);
                if (cn != null) {
                    if (TextUtils.equals(pkgName, cn.getPackageName())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    //sdk6.0以上获取权限
    private void getPermission(){
        MPermissions.requestPermissions(NotificationActivty.this, 100, Manifest.permission.RECEIVE_SMS);
        MPermissions.requestPermissions(NotificationActivty.this, 100, Manifest.permission.READ_PHONE_STATE);
        MPermissions.requestPermissions(NotificationActivty.this, 100, Manifest.permission.MODIFY_PHONE_STATE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        MPermissions.onRequestPermissionsResult(this,requestCode,permissions,grantResults);
    }

    @PermissionGrant(100)
    public void requestSdcardSuccess()
    {
    }

    @PermissionDenied(100)
    public void requestSdcardFailed()
    {
    }

    private void initView() {
        TVTitle.setText(R.string.msg_notify);

        int state = PreferenceData.getNotificationState(NotificationActivty.this);
        if (state == 1) {
            all.setOpened(true);

            tel.setEnabled(true);
            if (PreferenceData.getNotificationPhoneState(NotificationActivty.this) == 1) {
                tel.setChecked(true);
            } else {
                tel.setChecked(false);
            }

            message.setEnabled(true);
            if (PreferenceData.getNotificationMessageState(NotificationActivty.this) == 1) {
                message.setChecked(true);
            } else {
                message.setChecked(false);
            }

            email.setEnabled(true);
            if (PreferenceData.getNotificationMailState(NotificationActivty.this) == 1) {
                email.setChecked(true);
            } else {
                email.setChecked(false);
            }

            skype.setEnabled(true);
            if (PreferenceData.getNotificationSkypeState(NotificationActivty.this) == 1) {
                skype.setChecked(true);
            } else {
                skype.setChecked(false);
            }

            line.setEnabled(true);
            if (PreferenceData.getNotificationLineState(NotificationActivty.this) == 1) {
                line.setChecked(true);
            } else {
                line.setChecked(false);
            }

            qq.setEnabled(true);
            if (PreferenceData.getNotificationQQState(NotificationActivty.this) == 1) {
                qq.setChecked(true);
            } else {
                qq.setChecked(false);
            }

            facebook.setEnabled(true);
            if (PreferenceData.getNotificationFacebookState(NotificationActivty.this) == 1) {
                facebook.setChecked(true);
            } else {
                facebook.setChecked(false);
            }

            twitter.setEnabled(true);
            if (PreferenceData.getNotificationTwitterState(NotificationActivty.this) == 1) {
                twitter.setChecked(true);
            } else {
                twitter.setChecked(false);
            }

            wechat.setEnabled(true);
            if (PreferenceData.getNotificationWechatState(NotificationActivty.this) == 1) {
                wechat.setChecked(true);
            } else {
                wechat.setChecked(false);
            }
        } else {
            all.setOpened(false);

            tel.setEnabled(false);
            message.setEnabled(false);
            email.setEnabled(false);
            skype.setEnabled(false);
            line.setEnabled(false);
            qq.setEnabled(false);
            facebook.setEnabled(false);
            twitter.setEnabled(false);
            wechat.setEnabled(false);
        }

        all.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn(SwitchView view) {
                all.setOpened(true);
                PreferenceData.setNotificationState(NotificationActivty.this, 1);

                tel.setEnabled(true);
                message.setEnabled(true);
                email.setEnabled(true);
                skype.setEnabled(true);
                line.setEnabled(true);
                qq.setEnabled(true);
                facebook.setEnabled(true);
                twitter.setEnabled(true);
                wechat.setEnabled(true);
            }

            @Override
            public void toggleToOff(SwitchView view) {
                all.setOpened(false);
                PreferenceData.setNotificationState(NotificationActivty.this, 0);
                PreferenceData.setNotificationPhoneState(NotificationActivty.this, 0);
                PreferenceData.setSaveNotificationMessageState(NotificationActivty.this, 0);
                PreferenceData.setSaveNotificationMailState(NotificationActivty.this, 0);
                PreferenceData.setSaveNotificationTwitterState(NotificationActivty.this, 0);
                PreferenceData.setSaveNotificationLineState(NotificationActivty.this, 0);
                PreferenceData.setSaveNotificationQQState(NotificationActivty.this, 0);
                PreferenceData.setSaveNotificationFacebookState(NotificationActivty.this, 0);
                PreferenceData.setSaveNotificationSkypeState(NotificationActivty.this, 0);
                PreferenceData.setSaveNotificationWechatState(NotificationActivty.this, 0);

                tel.setChecked(false);
                tel.setEnabled(false);
                message.setEnabled(false);
                message.setChecked(false);
                email.setEnabled(false);
                email.setChecked(false);
                skype.setEnabled(false);
                skype.setChecked(false);
                line.setEnabled(false);
                line.setChecked(false);
                qq.setEnabled(false);
                qq.setChecked(false);
                facebook.setEnabled(false);
                facebook.setChecked(false);
                twitter.setEnabled(false);
                twitter.setChecked(false);
                wechat.setEnabled(false);
                wechat.setChecked(false);
            }
        });

        tel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    PreferenceData.setNotificationPhoneState(NotificationActivty.this, 1);
                } else {
                    PreferenceData.setNotificationPhoneState(NotificationActivty.this, 0);
                }
            }
        });

        message.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    PreferenceData.setSaveNotificationMessageState(NotificationActivty.this, 1);
                } else {
                    PreferenceData.setSaveNotificationMessageState(NotificationActivty.this, 0);
                }
            }
        });

        email.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    PreferenceData.setSaveNotificationMailState(NotificationActivty.this, 1);
                } else {
                    PreferenceData.setSaveNotificationMailState(NotificationActivty.this, 0);
                }
            }
        });

        twitter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    PreferenceData.setSaveNotificationTwitterState(NotificationActivty.this, 1);
                } else {
                    PreferenceData.setSaveNotificationTwitterState(NotificationActivty.this, 0);
                }
            }
        });

        line.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    PreferenceData.setSaveNotificationLineState(NotificationActivty.this, 1);
                } else {
                    PreferenceData.setSaveNotificationLineState(NotificationActivty.this, 0);
                }
            }
        });

        qq.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    PreferenceData.setSaveNotificationQQState(NotificationActivty.this, 1);
                } else {
                    PreferenceData.setSaveNotificationQQState(NotificationActivty.this, 0);
                }
            }
        });

        facebook.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    PreferenceData.setSaveNotificationFacebookState(NotificationActivty.this, 1);
                } else {
                    PreferenceData.setSaveNotificationFacebookState(NotificationActivty.this, 0);
                }
            }
        });

        skype.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    PreferenceData.setSaveNotificationSkypeState(NotificationActivty.this, 1);
                } else {
                    PreferenceData.setSaveNotificationSkypeState(NotificationActivty.this, 0);
                }
            }
        });

        wechat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    PreferenceData.setSaveNotificationWechatState(NotificationActivty.this, 1);
                } else {
                    PreferenceData.setSaveNotificationWechatState(NotificationActivty.this, 0);
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
