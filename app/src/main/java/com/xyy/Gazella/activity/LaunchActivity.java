package com.xyy.Gazella.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.ysp.newband.BaseActivity;
import com.ysp.smartwatch.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/10/14.
 */

public class LaunchActivity extends BaseActivity {
    @BindView(R.id.start)
    Button start;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.launch_activity);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.start)
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start:
                Intent intent = new Intent(LaunchActivity.this, PairingActivity.class);
                startActivity(intent);
                overridePendingTransitionEnter(LaunchActivity.this);
                break;
        }
    }
}
