package com.xyy.Gazella.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.polidea.rxandroidble.RxBleConnection;
import com.vise.baseble.ViseBluetooth;
import com.xyy.Gazella.utils.BleUtils;
import com.xyy.Gazella.utils.ChangeWatchDialog;
import com.xyy.Gazella.utils.CheckUpdateDialog2;
import com.xyy.Gazella.utils.CleanPhoneData;
import com.xyy.Gazella.utils.CleanWatchData;
import com.xyy.Gazella.utils.HexString;
import com.xyy.Gazella.utils.RenameWatchDialog;
import com.xyy.Gazella.utils.SomeUtills;
import com.xyy.Gazella.view.SwitchView;
import com.xyy.model.TimeZonesData;
import com.ysp.hybridtwatch.R;
import com.ysp.newband.BaseActivity;
import com.ysp.newband.GazelleApplication;
import com.ysp.newband.PreferenceData;
import com.ysp.newband.WacthSeries;

import java.util.ArrayList;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;


/**
 * Created by Administrator on 2016/10/22.
 */

public class SettingActivity extends BaseActivity {

    private static String TAG = SettingActivity.class.getName();

    @BindView(R.id.btnExit)
    LinearLayout btnExit;
    @BindView(R.id.TVTitle)
    TextView TVTitle;
    @BindView(R.id.rl_user_setting)
    RelativeLayout rlUserSetting;
    @BindView(R.id.rl_update_hardware)
    RelativeLayout rlUpdateHardware;
    @BindView(R.id.rl_change_watch)
    RelativeLayout rlChangeWatch;
    @BindView(R.id.rl_rename_watch)
    RelativeLayout rlRenameWatch;
    @BindView(R.id.rl_clock)
    RelativeLayout rlClock;
    @BindView(R.id.rl_clean_watch)
    RelativeLayout rlCleanWatch;
    @BindView(R.id.rl_clean_phone)
    RelativeLayout rlCleanPhone;
    @BindView(R.id.rl_target)
    RelativeLayout rlTarget;
    @BindView(R.id.rl_search_watch)
    RelativeLayout rlSearchWatch;
    @BindView(R.id.rl_close_bluetooth)
    RelativeLayout rlCloseBluetooth;
    @BindView(R.id.v_switch)
    SwitchView vSwitch;
    @BindView(R.id.rl_timezone)
    RelativeLayout rlTimezone;
    @BindView(R.id.timezone)
    TextView timezone;
    @BindView(R.id.rl_update_bsl)
    RelativeLayout rlUpdateBsl;
    private Context context;
    private BleUtils bleUtils;
    public Observable<RxBleConnection> connectionObservable;
    private ArrayList<TimeZonesData> dateList = new ArrayList<TimeZonesData>();
    private String strZonesTime;
    private String ZonesName;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.setting_activity);
        ButterKnife.bind(this);
        context = this;
        initView();
        initBle();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setActivityHandler();
        ViseBluetooth.getInstance().setOnNotifyListener(onNotifyListener);
        initBle();
    }

    private ViseBluetooth.OnNotifyListener onNotifyListener = new ViseBluetooth.OnNotifyListener() {
        @Override
        public void onNotify(boolean flag) {
            if (flag) {
                writeCharacteristic(bleUtils.setSystemType());
            }
        }
    };

    private void initBle() {
        bleUtils = new BleUtils();
        String address = PreferenceData.getAddressValue(context);
        if (address != null && !address.equals("")) {
            if (!GazelleApplication.isBleConnected) {
                connectBLEbyMac(address);
            } else {
                setNotifyCharacteristic();
            }
        }
    }


    @Override
    public void onConnectionState(int state) {
        if (state == 1) {
            setNotifyCharacteristic();
        }
    }


    private void initView() {
        TVTitle.setText(R.string.setting);
        if(PreferenceData.getDeviceType(this)!=null){
            if(!PreferenceData.getDeviceType(this).equals(WacthSeries.CT003)){
                rlUpdateBsl.setVisibility(View.VISIBLE);
            }
        }
        int state = PreferenceData.getNotificationShakeState(context);
        if (state == 1) {
            vSwitch.setOpened(true);
        } else {
            vSwitch.setOpened(false);
        }
        vSwitch.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn(SwitchView view) {
                vSwitch.setOpened(true);
                PreferenceData.setNotificationShakeState(context, 1);
            }

            @Override
            public void toggleToOff(SwitchView view) {
                vSwitch.setOpened(false);
                PreferenceData.setNotificationShakeState(context, 0);
            }
        });
        if (PreferenceData.getTimeZonesState(this).equals("local")) {
            initTime();
        } else {
            setTimezone();
        }
    }

    private void initTime() {
        Time time = new Time();
        time.setToNow();
        int minute = time.minute;
        int hour = time.hour;
        String strHour;
        String strMinute;
        if (hour < 10)
            strHour = String.format("%2d", hour).replace(" ", "0");
        else
            strHour = String.valueOf(hour);
        if (minute < 10)
            strMinute = String.format("%2d", minute).replace(" ", "0");
        else
            strMinute = String.valueOf(minute);

        timezone.setText(strHour + " : " + strMinute + getResources().getString(R.string.local_timezone));
    }

    @Override
    protected void onReadReturn(byte[] bytes) {
        if (HexString.bytesToHex(bytes).equals("0725012D60")) {
            showToatst(context, getResources().getString(R.string.watch_shake_search));
        } else if (bleUtils.returnDeviceType(bytes) != null) {
            PreferenceData.setDeviceType(this, bleUtils.returnDeviceType(bytes));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ViseBluetooth.getInstance().removeOnNotifyListener();
    }

    @OnClick({R.id.btnExit, R.id.rl_user_setting, R.id.rl_update_hardware, R.id.rl_change_watch, R.id.rl_rename_watch, R.id.rl_clock, R.id.rl_clean_phone,
            R.id.rl_clean_watch, R.id.rl_search_watch, R.id.rl_close_bluetooth, R.id.rl_update_bsl, R.id.rl_target, R.id.rl_timezone})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnExit:
                finish();
                overridePendingTransitionExit(SettingActivity.this);
                break;
            case R.id.rl_user_setting:
                Intent userIntent = new Intent(context, UserSetting.class);
                startActivity(userIntent);
                overridePendingTransitionEnter(SettingActivity.this);
                break;
            case R.id.rl_update_hardware:
                if (GazelleApplication.isBleConnected) {
                    Intent updateIntent = new Intent(context, UpdateHardware.class);
                    startActivity(updateIntent);
                    overridePendingTransitionEnter(SettingActivity.this);
                } else {
                    showToatst(SettingActivity.this, getResources().getString(R.string.not_connect_device));
                }
                break;
            case R.id.rl_change_watch:
                Intent changeIntent = new Intent(this, ChangeWatchDialog.class);
                startActivity(changeIntent);
                break;
            case R.id.rl_rename_watch:
                if (GazelleApplication.isBleConnected) {
                    Intent nameIntent = new Intent(this, RenameWatchDialog.class);
                    startActivity(nameIntent);
                } else {
                    showToatst(SettingActivity.this, getResources().getString(R.string.not_connect_device));
                }

                break;
            case R.id.rl_clock:
                if (GazelleApplication.isBleConnected) {
                    Intent clockIntent = new Intent(this, ClockActivity.class);
                    startActivity(clockIntent);
                    overridePendingTransitionEnter(SettingActivity.this);
                } else {
                    showToatst(SettingActivity.this, getResources().getString(R.string.not_connect_device));
                }
                break;
            case R.id.rl_clean_watch:
                if (GazelleApplication.isBleConnected) {
                    Intent watchIntent = new Intent(this, CleanWatchData.class);
                    startActivity(watchIntent);
                } else {
                    showToatst(SettingActivity.this, getResources().getString(R.string.not_connect_device));
                }

                break;
            case R.id.rl_clean_phone:
                CleanPhoneData cleanPhoneData = new CleanPhoneData(SettingActivity.this);
                cleanPhoneData.show();
                break;
            case R.id.rl_target:
                Intent targetIntent = new Intent(context, TargetActivity.class);
                startActivity(targetIntent);
                overridePendingTransitionEnter(SettingActivity.this);
                break;
            case R.id.rl_search_watch:
                if (GazelleApplication.isBleConnected) {
                    writeCharacteristic(bleUtils.setWatchShake(1, 2, 3));
                } else {
                    showToatst(SettingActivity.this, getResources().getString(R.string.not_connect_device));
                }
                break;
            case R.id.rl_close_bluetooth:
                if (GazelleApplication.isBleConnected) {
                    CheckUpdateDialog2 myDialog = new CheckUpdateDialog2(SettingActivity.this);
                    myDialog.show();
                    myDialog.setTvContext(getResources().getString(R.string.is_close_bluetooth));
                    myDialog.setCancel(getResources().getString(R.string.no));
                    myDialog.setConfirm(getResources().getString(R.string.yes));
                    myDialog.setBtnlListener(new CheckUpdateDialog2.setBtnlListener() {
                        @Override
                        public void onCancelListener() {
                            myDialog.dismiss();
                        }

                        @Override
                        public void onConfirm() {
                            myDialog.dismiss();
                            if (GazelleApplication.isBleConnected) {
                                writeCharacteristic(bleUtils.sendMessage(0, 0, 0, 0, 0, 0));
                            }
                            GazelleApplication.isEnabled = true;
                        }
                    });
                } else {
                    showToatst(SettingActivity.this, getResources().getString(R.string.not_connect_device));
                }

                break;
            case R.id.rl_timezone:
                Intent TimeZonesIntent = new Intent(context, TimeZonesActivity.class);
                startActivityForResult(TimeZonesIntent, 10010);
                overridePendingTransitionEnter(SettingActivity.this);
                break;
        }
    }


    private Time mCalendar;
    private int hour;
    private int minute;
    private int second;
    private int myear;
    private int month;
    private int mday;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            if (requestCode == 10010) {
                if (resultCode == Activity.RESULT_OK) {
                    if (PreferenceData.getTimeZonesState(this).equals("local")) {
                        initTime();
                        mCalendar = new Time();
                        mCalendar.setToNow();
                        hour = mCalendar.hour;
                        minute = mCalendar.minute;
                        second = mCalendar.second;
                        myear = mCalendar.year;
                        month = mCalendar.month;
                        mday = mCalendar.monthDay;
                        if (GazelleApplication.isBleConnected) {
                            writeCharacteristic(bleUtils.setWatchDateAndTime(1, myear, month + 1, mday, hour, minute, second));
                        }
                    } else {
                        setTimezone();
                        TimeZone tz = TimeZone.getTimeZone(PreferenceData.getTimeZonesState(this));
                        mCalendar = new Time(tz.getID());
                        mCalendar.setToNow();
                        hour = mCalendar.hour;
                        minute = mCalendar.minute;
                        second = mCalendar.second;
                        myear = mCalendar.year;
                        month = mCalendar.month;
                        mday = mCalendar.monthDay;
                        if (GazelleApplication.isBleConnected) {
                            writeCharacteristic(bleUtils.setWatchDateAndTime(1, myear, month + 1, mday, hour, minute, second));
                        }
                    }
                }
                return;
            }
        }
    }

    private void setTimezone() {
        dateList = new SomeUtills().getTimeZones(SettingActivity.this);
        for (int i = 0; i < dateList.size(); i++) {
            if (PreferenceData.getTimeZonesState(SettingActivity.this).equals(dateList.get(i).getGtm())) {
                ZonesName = dateList.get(i).getName();
            }
        }
        strZonesTime = new SomeUtills().getZonesTime(PreferenceData.getTimeZonesState(SettingActivity.this));
        timezone.setText(strZonesTime + ZonesName);
    }
}
