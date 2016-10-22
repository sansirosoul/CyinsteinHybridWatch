package com.xyy.Gazella.activity;

/**
 * 主页
 */

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.ysp.newband.BaseActivity;
import com.ysp.smartwatch.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends BaseActivity {

    @BindView(R.id.ll_time)
    LinearLayout llTime;
    @BindView(R.id.ll_notice)
    LinearLayout llNotice;
    @BindView(R.id.ll_healthy)
    LinearLayout llHealthy;


    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);

    }

    @OnClick({R.id.ll_time, R.id.ll_notice,R.id.ll_healthy})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_time:
                Intent timeIntent = new Intent(HomeActivity.this, TimeSynchronization.class);
                startActivity(timeIntent);
                overridePendingTransitionEnter(HomeActivity.this);
                break;
            case R.id.ll_notice:
                Intent noticeIntent = new Intent(HomeActivity.this, NotificationActivty.class);
                startActivity(noticeIntent);
                overridePendingTransitionEnter(HomeActivity.this);
                break;
            case  R.id.ll_healthy:
                Intent healthIntent = new Intent(HomeActivity.this, HealthyActivity.class);
                startActivity(healthIntent);
                overridePendingTransitionEnter(HomeActivity.this);
                break;






        }
    }
}
