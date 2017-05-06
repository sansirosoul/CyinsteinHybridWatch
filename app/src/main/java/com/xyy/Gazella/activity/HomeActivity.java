package com.xyy.Gazella.activity;

/**
 * 主页
 */

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.format.Time;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.vise.baseble.ViseBluetooth;
import com.xyy.Gazella.utils.BleUtils;
import com.ysp.hybridtwatch.R;
import com.ysp.newband.BaseActivity;
import com.ysp.newband.GazelleApplication;
import com.ysp.newband.PreferenceData;
import com.ysp.newband.WacthSeries;
import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionDenied;
import com.zhy.m.permission.PermissionGrant;

import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    @BindView(R.id.logo)
    ImageView logo;
    @BindView(R.id.ll_health)
    LinearLayout llHealth;
    @BindView(R.id.ll_setting)
    LinearLayout llSetting;
    @BindView(R.id.ll_help)
    LinearLayout llHelp;
    @BindView(R.id.layout2)
    LinearLayout layout2;
    @BindView(R.id.layout1)
    LinearLayout layout1;
    private long mExitTime = 0;
    public static HomeActivity install;
    BleUtils bleUtils;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        ViseBluetooth.getInstance().setOnNotifyListener(onNotifyListener);
        if (!GazelleApplication.isLogoVisible) {
            logo.setVisibility(View.INVISIBLE);
        }
//        logo.setImageDrawable(getResources().getDrawable(R.drawable.index_julius));
        String deviceType = PreferenceData.getDeviceType(this);
        if(deviceType.equals(WacthSeries.CT002)||deviceType.equals("CT012")){
            layout1.setVisibility(View.GONE);
            layout2.setVisibility(View.VISIBLE);
        }else {
            layout1.setVisibility(View.VISIBLE);
            layout2.setVisibility(View.GONE);
        }

        bleUtils = new BleUtils();
        String address = PreferenceData.getAddressValue(this);
        if (address != null && !address.equals("")) {
            if (!GazelleApplication.isBleConnected) {
                connectBLEbyMac(address);
            } else {
                setNotifyCharacteristic();
            }
        }
        MPermissions.requestPermissions(this, 1000, Manifest.permission.CALL_PHONE);
        install = this;
    }

    private Time mCalendar;
    public int hour;
    public int minute;
    private int second;
    private int myear;
    private int month;
    private int mday;
    private void initTime() {
        if (PreferenceData.getTimeZonesState(this).equals("local")) {
            mCalendar = new Time();
            mCalendar.setToNow();
            hour = mCalendar.hour;
            minute = mCalendar.minute;
            second = mCalendar.second;
            myear = mCalendar.year;
            month = mCalendar.month;
            mday = mCalendar.monthDay;
        } else {
            TimeZone tz = TimeZone.getTimeZone(PreferenceData.getTimeZonesState(this));
            mCalendar = new Time(tz.getID());
            mCalendar.setToNow();
            hour = mCalendar.hour;
            minute = mCalendar.minute;
            second = mCalendar.second;
            myear = mCalendar.year;
            month = mCalendar.month;
            mday = mCalendar.monthDay;
        }
    }

    private ViseBluetooth.OnNotifyListener onNotifyListener = new ViseBluetooth.OnNotifyListener() {
        @Override
        public void onNotify(boolean flag) {
            if (flag) {
                writeCharacteristic(bleUtils.setSystemType());
            }
        }
    };

    @Override
    public void isServicesDiscovered(boolean flag) {
        if (flag) {
            setNotifyCharacteristic();
        }

    }

    @Override
    protected void onReadReturn(byte[] bytes) {
         if(bytes[0] == 0x07 && (bytes[1] & 0xff) == 0x09&&bytes[2]==0x01){
            String mDeviceType = PreferenceData.getDeviceType(this);
            System.out.println(mDeviceType+">>>>>");
            if(mDeviceType.equals(WacthSeries.CT002)||mDeviceType.equals("CT012")){
                BleUtils bleUtils = new BleUtils();
                initTime();
                writeCharacteristic(bleUtils.setWatchDateAndTime(1, myear, month + 1, mday, hour, minute, second));
            }
        }
    }

    @OnClick({R.id.ll_time, R.id.ll_notice, R.id.ll_healthy, R.id.ll_settings, R.id.ll_introduce, R.id.ll_other,R.id.ll_health,R.id.ll_setting,R.id.ll_help})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_time:
                ViseBluetooth.getInstance().removeOnNotifyListener();
                Intent timeIntent = new Intent(HomeActivity.this, TimeSynchronization.class);
                startActivityForResult(timeIntent,0);
                overridePendingTransitionEnter(HomeActivity.this);
                break;
            case R.id.ll_notice:
                ViseBluetooth.getInstance().removeOnNotifyListener();
                Intent noticeIntent = new Intent(HomeActivity.this, NotificationActivty.class);
                startActivityForResult(noticeIntent,0);
                overridePendingTransitionEnter(HomeActivity.this);
                break;
            case R.id.ll_healthy:
                ViseBluetooth.getInstance().removeOnNotifyListener();
                Intent healthIntent = new Intent(HomeActivity.this, HealthyActivity.class);
                startActivityForResult(healthIntent,0);
                overridePendingTransitionEnter(HomeActivity.this);
                break;
            case R.id.ll_settings:
                ViseBluetooth.getInstance().removeOnNotifyListener();
                Intent settingsIntent = new Intent(HomeActivity.this, SettingActivity.class);
                startActivityForResult(settingsIntent,0);
                overridePendingTransitionEnter(HomeActivity.this);
                break;
            case R.id.ll_introduce:
                ViseBluetooth.getInstance().removeOnNotifyListener();
                Intent helpIntent = new Intent(HomeActivity.this, HelpActivity.class);
                startActivityForResult(helpIntent,0);
                overridePendingTransitionEnter(HomeActivity.this);
                break;
            case R.id.ll_other:
                ViseBluetooth.getInstance().removeOnNotifyListener();
                Intent otherIntent = new Intent(HomeActivity.this, BleTest.class);
                startActivityForResult(otherIntent,0);
                overridePendingTransitionEnter(HomeActivity.this);
                break;


            case R.id.ll_health:
                ViseBluetooth.getInstance().removeOnNotifyListener();
                Intent healthIntent2 = new Intent(HomeActivity.this, HealthyActivity.class);
                startActivityForResult(healthIntent2,0);
                overridePendingTransitionEnter(HomeActivity.this);
                break;
            case R.id.ll_setting:
                ViseBluetooth.getInstance().removeOnNotifyListener();
                Intent settingsIntent2 = new Intent(HomeActivity.this, SettingActivity.class);
                startActivityForResult(settingsIntent2,0);
                overridePendingTransitionEnter(HomeActivity.this);
                break;
            case R.id.ll_help:
                ViseBluetooth.getInstance().removeOnNotifyListener();
                Intent helpIntent2 = new Intent(HomeActivity.this, HelpActivity.class);
                startActivityForResult(helpIntent2,0);
                overridePendingTransitionEnter(HomeActivity.this);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String deviceType = PreferenceData.getDeviceType(this);
        if(deviceType.equals(WacthSeries.CT002)||deviceType.equals("CT012")){
            layout1.setVisibility(View.GONE);
            layout2.setVisibility(View.VISIBLE);
        }else {
            layout1.setVisibility(View.VISIBLE);
            layout2.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(this, getResources().getString(R.string.press_again_exit), Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        MPermissions.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @PermissionGrant(1000)
    public void requestSdcardSuccess() {
    }

    @PermissionDenied(1000)
    public void requestSdcardFailed() {
    }
}
