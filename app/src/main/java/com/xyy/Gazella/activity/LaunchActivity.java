package com.xyy.Gazella.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.polidea.rxandroidble.RxBleClient;
import com.ysp.hybridtwatch.R;
import com.ysp.newband.BaseActivity;
import com.ysp.newband.GazelleApplication;
import com.ysp.newband.PreferenceData;

import rx.Subscription;

/**
 * Created by Administrator on 2016/10/14.
 */

public class LaunchActivity extends BaseActivity {

    private  static String  TAG= LaunchActivity.class.getName();
    private Button start;
    private byte ck_a, ck_b;

    private RxBleClient rxBleClient;
    private Subscription scanSubscription;

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
    }
}
