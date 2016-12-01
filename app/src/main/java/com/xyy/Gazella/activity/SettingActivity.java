package com.xyy.Gazella.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.polidea.rxandroidble.RxBleConnection;
import com.polidea.rxandroidble.RxBleDevice;
import com.polidea.rxandroidble.utils.ConnectionSharingAdapter;
import com.xyy.Gazella.utils.BleUtils;
import com.xyy.Gazella.utils.ChangeWatchDialog;
import com.xyy.Gazella.utils.CleanPhoneData;
import com.xyy.Gazella.utils.CleanWatchData;
import com.xyy.Gazella.utils.RenameWatchDialog;
import com.xyy.Gazella.view.SwitchView;
import com.ysp.newband.BaseActivity;
import com.ysp.newband.GazelleApplication;
import com.ysp.newband.PreferenceData;
import com.ysp.smartwatch.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.subjects.PublishSubject;


/**
 * Created by Administrator on 2016/10/22.
 */

public class SettingActivity extends BaseActivity {


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
    private Context context;
    private BleUtils bleUtils;
    public static Observable<RxBleConnection> connectionObservable;
    private RxBleDevice bleDevice;
    private PublishSubject<Void> disconnectTriggerSubject = PublishSubject.create();

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.setting_activity);
        ButterKnife.bind(this);

        context = this;
        initView();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        triggerDisconnect();
    }

    private void initView() {
        TVTitle.setText(R.string.setting);
        vSwitch.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn(SwitchView view) {
                vSwitch.setOpened(true);
                Write(bleUtils.sendMessage(1, 0, 0, 0, 0, 1), connectionObservable);
            }

            @Override
            public void toggleToOff(SwitchView view) {
                vSwitch.setOpened(false);
                Write(bleUtils.sendMessage(1, 0, 0, 0, 0, 0), connectionObservable);
            }
        });


        bleUtils = new BleUtils();
        String address =  PreferenceData.getAddressValue(this);
        if(address!=null&&!address.equals("")){
            bleDevice = GazelleApplication.getRxBleClient(this).getBleDevice(address);
            connectionObservable = bleDevice
                    .establishConnection(this, false)
                    .takeUntil(disconnectTriggerSubject)
                    .compose(new ConnectionSharingAdapter());
            Notify(connectionObservable);
        }

    }


    public void triggerDisconnect() {
        disconnectTriggerSubject.onNext(null);
    }

    @Override
    protected void onReadReturn(byte[] bytes) {
        super.onReadReturn(bytes);
        if(bytes[0]==0x07&&bytes[1]==0x25){
             if(bytes[2]==1)
                 Toast.makeText(context,"手表已震动，请寻找手表！",Toast.LENGTH_SHORT).show();
        }else if(bytes[0]==0x07&&bytes[1]==0x01){
            if(bytes[2]==1)
                Toast.makeText(context,"手表蓝牙已关闭！",Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick({R.id.btnExit, R.id.rl_user_setting, R.id.rl_update_hardware, R.id.rl_change_watch, R.id.rl_rename_watch, R.id.rl_clock, R.id.rl_clean_phone,
            R.id.rl_clean_watch, R.id.rl_search_watch, R.id.rl_close_bluetooth, R.id.rl_update_bsl, R.id.rl_target})
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
                startActivityForResult(changeIntent,1);
                break;
            case R.id.rl_rename_watch:
                Intent nameIntent = new Intent(this,RenameWatchDialog.class);
                startActivity(nameIntent);
                break;
            case R.id.rl_clock:
                Intent clockIntent = new Intent(this, ClockActivity.class);
                startActivity(clockIntent);
                overridePendingTransitionEnter(SettingActivity.this);
                break;
            case R.id.rl_clean_watch:
                Intent watchIntent = new Intent(this,CleanWatchData.class);
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
                Write(bleUtils.setWatchShake(1,1,1),connectionObservable);
                break;
            case R.id.rl_close_bluetooth:
                Write(bleUtils.sendMessage(0,0,0,0,0,0),connectionObservable);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if(data!=null){

                }
                break;
        }
    }
}
