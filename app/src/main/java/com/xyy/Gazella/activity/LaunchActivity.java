package com.xyy.Gazella.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.polidea.rxandroidble.RxBleClient;
import com.ysp.hybridtwatch.R;
import com.ysp.newband.BaseActivity;
import com.ysp.newband.GazelleApplication;
import com.ysp.newband.PreferenceData;

/**
 * Created by Administrator on 2016/10/14.
 */

public class LaunchActivity extends BaseActivity {
    private  static String  TAG= LaunchActivity.class.getName();
    private Button start;
    private byte ck_a, ck_b;
    private static final String ENABLED_NOTIFICATION_LISTENERS = "enabled_notification_listeners";
    private RxBleClient rxBleClient;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.launch_activity);
        rxBleClient = GazelleApplication.getRxBleClient(this);

        start = (Button) findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                String address = PreferenceData.getAddressValue(LaunchActivity.this);
                if (address == null || address.equals(""))
                    intent = new Intent(LaunchActivity.this, PairingActivity.class);
                else
                    intent = new Intent(LaunchActivity.this, HomeActivity.class);
                startActivity(intent);
                overridePendingTransitionEnter(LaunchActivity.this);
                finish();
            }
        });

        if (!isNotificationAccessEnabled()) {
            //通知监听授权
            Intent intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
            startActivity(intent);
        }

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
}
