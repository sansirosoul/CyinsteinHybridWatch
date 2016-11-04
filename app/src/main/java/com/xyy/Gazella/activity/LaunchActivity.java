package com.xyy.Gazella.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ysp.newband.BaseActivity;
import com.ysp.smartwatch.R;

/**
 * Created by Administrator on 2016/10/14.
 */

public class LaunchActivity extends BaseActivity {
    private Button start;
    private byte ck_a,ck_b;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.launch_activity);

        start= (Button) findViewById(R.id.start);
                start.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(LaunchActivity.this,PairingActivity.class);
                        startActivity(intent);
                        overridePendingTransitionEnter(LaunchActivity.this);
                        finish();
                    }
                });
    }
}
