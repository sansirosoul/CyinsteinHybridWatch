package com.xyy.Gazella.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xyy.Gazella.utils.ChangeWatchDialog;
import com.xyy.Gazella.utils.CleanPhoneData;
import com.xyy.Gazella.utils.CleanWatchData;
import com.xyy.Gazella.utils.RenameWatchDialog;
import com.ysp.newband.BaseActivity;
import com.ysp.smartwatch.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/10/22.
 */

public class SettingActivity extends BaseActivity {


    @BindView(R.id.btnExit)
    Button btnExit;
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
    @BindView(R.id.rl_anti_lost)
    RelativeLayout rlAntiLost;
    @BindView(R.id.rl_ble)
    RelativeLayout rlBle;
    @BindView(R.id.rl_update_bsl)
    RelativeLayout rlUpdateBsl;
    @BindView(R.id.rl_target)
    RelativeLayout rlTarget;
    private Context context;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.setting_activity);
        ButterKnife.bind(this);

        initView();
        context = this;
        TVTitle.setText(R.string.setting);
    }

    private void initView() {

    }

    @OnClick({R.id.btnExit, R.id.rl_user_setting, R.id.rl_update_hardware, R.id.rl_change_watch, R.id.rl_rename_watch, R.id.rl_clock, R.id.rl_clean_phone, R.id.rl_clean_watch, R.id.rl_anti_lost, R.id.rl_ble, R.id.rl_update_bsl, R.id.rl_target})
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
                ChangeWatchDialog changeWatchDialog = new ChangeWatchDialog(context);
                changeWatchDialog.show();
                break;
            case R.id.rl_rename_watch:
                RenameWatchDialog renameWatchDialog = new RenameWatchDialog(context);
                renameWatchDialog.show();
                break;
            case R.id.rl_clock:
                Intent clockIntent = new Intent(context, ClockActivity.class);
                startActivity(clockIntent);
                overridePendingTransitionEnter(SettingActivity.this);
                break;
            case R.id.rl_clean_watch:
                CleanWatchData cleanWatchData = new CleanWatchData(context);
                cleanWatchData.show();
                break;
            case R.id.rl_clean_phone:
                CleanPhoneData cleanPhoneData = new CleanPhoneData(context);
                cleanPhoneData.show();
                break;
            case R.id.rl_target:
                Intent targetIntent = new Intent(context, TargetActivity.class);
                startActivity(targetIntent);
                overridePendingTransitionEnter(SettingActivity.this);
                break;
        }
    }
}
