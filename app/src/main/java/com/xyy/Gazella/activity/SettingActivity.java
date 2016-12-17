package com.xyy.Gazella.activity;

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
import com.xyy.Gazella.view.SwitchView;
import com.ysp.newband.BaseActivity;
import com.ysp.newband.PreferenceData;
import com.ysp.smartwatch.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;


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
    public Observable<RxBleConnection> connectionObservable;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.setting_activity);
        ButterKnife.bind(this);
        context = this;
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initBle();
    }


    private void initBle() {
        String address = PreferenceData.getAddressValue(context);
        if (address != null && !address.equals("")) {
            bleUtils = new BleUtils();
            connectionObservable = getRxObservable(this);
            Notify(connectionObservable);
        }
    }

    @Override
    protected void onNotifyReturn(int type) {
        super.onNotifyReturn(type);

    }


    private void initView() {
        TVTitle.setText(R.string.setting);
        int state = PreferenceData.getNotificationShakeState(context);
        if(state==1){
            vSwitch.setOpened(true);
        }else{
            vSwitch.setOpened(false);
        }
        vSwitch.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn(SwitchView view) {
                vSwitch.setOpened(true);
                PreferenceData.setNotificationShakeState(context,1);
            }

            @Override
            public void toggleToOff(SwitchView view) {
                vSwitch.setOpened(false);
                PreferenceData.setNotificationShakeState(context,0);
            }
        });
    }

    @Override
    protected void onReadReturn(byte[] bytes) {
        super.onReadReturn(bytes);
        System.out.println("-----------");
//        if(HexString.bytesToHex(bytes).equals("0725012D60")){
//                 Toast.makeText(context,"手表已震动，请寻找手表！",Toast.LENGTH_SHORT).show();
//        }else if(HexString.bytesToHex(bytes).equals("0701010918")){
//                Toast.makeText(context,"手表蓝牙已关闭！",Toast.LENGTH_SHORT).show();
//        }
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
                    Write(bleUtils.setWatchShake(1, 2, 2), connectionObservable);
                    showToatst(context, "手表已震动，请寻找手表！");
                }
                break;
            case R.id.rl_close_bluetooth:
                CheckUpdateDialog2 myDialog = new CheckUpdateDialog2(context);
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
                        connectionObservable = null;
                    }
                });
                break;
        }
    }

}
