package com.xyy.Gazella.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.polidea.rxandroidble.RxBleConnection;
import com.xyy.Gazella.utils.BleUtils;
import com.xyy.Gazella.utils.ChangeWatchDialog;
import com.xyy.Gazella.utils.CheckUpdateDialog2;
import com.xyy.Gazella.utils.CleanPhoneData;
import com.xyy.Gazella.utils.CleanWatchData;
import com.xyy.Gazella.utils.RenameWatchDialog;
import com.xyy.Gazella.utils.SomeUtills;
import com.xyy.Gazella.view.SwitchView;
import com.xyy.model.TimeZonesData;
import com.ysp.hybridtwatch.R;
import com.ysp.newband.BaseActivity;
import com.ysp.newband.PreferenceData;

import java.util.ArrayList;

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

//    @Override
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//        initBle();
//    }

    @Override
    protected void onResume() {
        super.onResume();
        //  initBle();
    }

    private void initBle() {
        String address = PreferenceData.getAddressValue(context);
        if (address != null && !address.equals("")) {
            bleUtils = new BleUtils();
            connectionObservable = getRxObservable(this);
            Notify(connectionObservable);
            Write(bleUtils.setSystemType(), connectionObservable);
        }
    }

    @Override
    protected void onNotifyReturn(int type, String str) {
        super.onNotifyReturn(type, str);
        switch (type) {
            case 0:
                break;
            case 1:
                HandleThrowableException(str);
                break;
            case 2:
                Notify(connectionObservable);
                break;
        }
    }

    private void initView() {
        TVTitle.setText(R.string.setting);
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
        setTimezone();
    }


    @Override
    protected void onReadReturn(byte[] bytes) {
        super.onReadReturn(bytes);

        if (bleUtils.returnterminateBle(bytes) != null && bleUtils.returnterminateBle(bytes).equals("1")) {
            showToatst(SettingActivity.this, "蓝牙已断开");

        }


//        if(HexString.bytesToHex(bytes).equals("0725012D60")){
//                 Toast.makeText(context,"手表已震动，请寻找手表！",Toast.LENGTH_SHORT).show();
//        }else if(HexString.bytesToHex(bytes).equals("0701010918")){
//                Toast.makeText(context,"手表蓝牙已关闭！",Toast.LENGTH_SHORT).show();
//        }
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
                Intent updateIntent = new Intent(context, UpdateHardware.class);
                startActivity(updateIntent);
                overridePendingTransitionEnter(SettingActivity.this);
                break;
            case R.id.rl_change_watch:
                Intent changeIntent = new Intent(this, ChangeWatchDialog.class);
                startActivity(changeIntent);
                break;
            case R.id.rl_rename_watch:
                Intent nameIntent = new Intent(this, RenameWatchDialog.class);
                startActivity(nameIntent);
                break;
            case R.id.rl_clock:
                Intent clockIntent = new Intent(this, ClockActivity.class);
                startActivity(clockIntent);
                overridePendingTransitionEnter(SettingActivity.this);
                break;
            case R.id.rl_clean_watch:
                Intent watchIntent = new Intent(this, CleanWatchData.class);
                startActivity(watchIntent);
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
                if (connectionObservable != null) {
                    Write(bleUtils.setWatchShake(1, 2, 3), connectionObservable);
                    showToatst(context, "手表已震动，请寻找手表！");
                }
                break;
            case R.id.rl_close_bluetooth:
                CheckUpdateDialog2 myDialog = new CheckUpdateDialog2(SettingActivity.this);
                myDialog.show();
                myDialog.setTvContext("是否确定关闭蓝牙？");
                myDialog.setCancel("否");
                myDialog.setConfirm("是");
                myDialog.setBtnlListener(new CheckUpdateDialog2.setBtnlListener() {
                    @Override
                    public void onCancelListener() {
                        myDialog.dismiss();
                    }

                    @Override
                    public void onConfirm() {
                        myDialog.dismiss();
                        if (connectionObservable != null)
                            Write(bleUtils.sendMessage(0, 0, 0, 0, 0, 0), connectionObservable);
                     //   connectionObservable = null;
                    }
                });
                break;
            case R.id.rl_timezone:
                Intent TimeZonesIntent = new Intent(context, TimeZonesActivity.class);
                startActivityForResult(TimeZonesIntent, 10010);
                overridePendingTransitionEnter(SettingActivity.this);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10010) {
            if (resultCode == Activity.RESULT_OK) {
                setTimezone();
            }
            return;
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
