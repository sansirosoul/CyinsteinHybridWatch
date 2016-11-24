package com.xyy.Gazella.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xyy.Gazella.services.BluetoothService;
import com.xyy.Gazella.utils.BleUtils;
import com.xyy.Gazella.utils.CheckUpdateDialog1;
import com.ysp.newband.BaseActivity;
import com.ysp.smartwatch.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.xyy.Gazella.activity.HomeActivity.writeCharacteristic;
import static com.ysp.newband.GazelleApplication.mBluetoothService;

/**
 * Created by Administrator on 2016/10/25.
 */

public class UpdateHardware extends BaseActivity {

    @BindView(R.id.btnExit)
    LinearLayout btnExit;
    @BindView(R.id.TVTitle)
    TextView TVTitle;
    @BindView(R.id.watch_ver)
    TextView watchVer;
    @BindView(R.id.watch_sn)
    TextView watchSN;
    @BindView(R.id.app_ver)
    TextView appVer;
    @BindView(R.id.update)
    Button update;
    @BindView(R.id.battery)
    TextView battery;
    private BleUtils bleUtils;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.update_hardware);
        ButterKnife.bind(this);

        TVTitle.setText(R.string.check_update);
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(),0);
            appVer.setText("V"+packageInfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        bleUtils = new BleUtils();
        mBluetoothService.setActivityHandler(mHandler);
        bleUtils.getDeviceSN(writeCharacteristic);
        bleUtils.getFWVer(writeCharacteristic);
        bleUtils.getBatteryValue(writeCharacteristic);
    }

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case BluetoothService.NOTIFY_SUCCESS:
                    if(msg.obj!=null){
                        byte[] data = (byte[]) msg.obj;
                        if(bleUtils.returnDeviceSN(data)!=null){
                            watchSN.setText(bleUtils.returnDeviceSN(data));
                        }else if(bleUtils.returnFWVer(data)!=null){
                            watchVer.setText(bleUtils.returnFWVer(data));
                        }else if(bleUtils.returnBatteryValue(data)!=null){
                            battery.setText(bleUtils.returnBatteryValue(data)+"%");
                        }
                    }
                    break;
            }
        }
    };

    @OnClick({R.id.btnExit, R.id.update})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnExit:
                finish();
                overridePendingTransitionExit(UpdateHardware.this);
                break;
            case R.id.update:
                CheckUpdateDialog1 dialog = new CheckUpdateDialog1(UpdateHardware.this);
                dialog.show();
                break;
        }
    }
}
