package com.xyy.Gazella.activity;

/**
 * 主页
 */

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.polidea.rxandroidble.RxBleConnection;
import com.xyy.Gazella.utils.BleUtils;
import com.ysp.hybridtwatch.R;
import com.ysp.newband.BaseActivity;
import com.ysp.newband.PreferenceData;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;

public class HomeActivity extends BaseActivity {

    private static String TAG = HomeActivity.class.getName();

    @BindView(R.id.ll_time)
    LinearLayout llTime;
    @BindView(R.id.ll_notice)
    LinearLayout llNotice;
    @BindView(R.id.ll_healthy)
    LinearLayout llHealthy;
    @BindView(R.id.ll_settings)
    LinearLayout llSettings;
    @BindView(R.id.ll_introduce)
    LinearLayout llIntroduce;
    @BindView(R.id.ll_other)
    LinearLayout llOther;
    private long mExitTime = 0;
    public static HomeActivity install;
    public Observable<RxBleConnection> connectionObservable;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);
        BleUtils bleUtils = new BleUtils();
        String address = PreferenceData.getAddressValue(this);
        if (address != null && !address.equals("")) {
            connectionObservable = getRxObservable(this);
            Notify(getRxObservable(this));
            Write(bleUtils.setSystemType(),connectionObservable);
        }
        install = this;
    }

    @Override
    protected void onReadReturn(byte[] bytes) {
        Logger.t(TAG).e(String.valueOf(bytes));
        onHomeReadReturn(bytes);
        super.onReadReturn(bytes);
    }

    public void onHomeReadReturn(byte[] bytes) {

    }

    @Override
    protected void onConnectionStateChanges() {
        super.onConnectionStateChanges();


    }

    @Override
    protected void onNotifyReturn(int type,String str) {
        super.onNotifyReturn(type,str);
        switch (type) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                Notify(connectionObservable);
                break;
        }
    }

    @OnClick({R.id.ll_time, R.id.ll_notice, R.id.ll_healthy, R.id.ll_settings, R.id.ll_introduce,R.id.ll_other})
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
            case R.id.ll_healthy:
                Intent healthIntent = new Intent(HomeActivity.this, HealthyActivity.class);
                startActivity(healthIntent);
                overridePendingTransitionEnter(HomeActivity.this);
                break;
            case R.id.ll_settings:
                Intent settingsIntent = new Intent(HomeActivity.this, SettingActivity.class);
                startActivity(settingsIntent);
                overridePendingTransitionEnter(HomeActivity.this);
                break;
            case R.id.ll_introduce:
                Intent helpIntent = new Intent(HomeActivity.this, HelpActivity.class);
                startActivity(helpIntent);
                overridePendingTransitionEnter(HomeActivity.this);
                break;
            case R.id.ll_other:
                Intent otherIntent = new Intent(HomeActivity.this, BleTest.class);
                startActivity(otherIntent);
                overridePendingTransitionEnter(HomeActivity.this);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
