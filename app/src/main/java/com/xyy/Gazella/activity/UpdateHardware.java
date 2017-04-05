package com.xyy.Gazella.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.polidea.rxandroidble.RxBleConnection;
import com.vise.baseble.ViseBluetooth;
import com.xyy.Gazella.utils.BleUtils;
import com.xyy.Gazella.utils.CheckUpdateDialog1;
import com.ysp.hybridtwatch.R;
import com.ysp.newband.BaseActivity;
import com.ysp.newband.GazelleApplication;
import com.ysp.newband.PreferenceData;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;


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
    public Observable<RxBleConnection> connectionObservable;
    private int battery_num = 0;
    private Context context;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.update_hardware);
        ButterKnife.bind(this);
        context = this;
        TVTitle.setText(R.string.check_update);
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            appVer.setText("V" + packageInfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        ViseBluetooth.getInstance().setOnNotifyListener(onNotifyListener);
        String address = PreferenceData.getAddressValue(this);
        if (address != null && !address.equals("")) {
            bleUtils = new BleUtils();
            if (GazelleApplication.isBleConnected) {
                setNotifyCharacteristic();
            } else {
                connectBLEbyMac(address);
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ViseBluetooth.getInstance().setOnNotifyListener(onNotifyListener);
        String address = PreferenceData.getAddressValue(this);
        if (address != null && !address.equals("")) {
            bleUtils = new BleUtils();
//            if (GazelleApplication.isBleConnected) {
//                setNotifyCharacteristic();
//            } else {
                connectBLEbyMac(address);
//            }
        }
    }

    private ViseBluetooth.OnNotifyListener onNotifyListener = new ViseBluetooth.OnNotifyListener() {
        @Override
        public void onNotify(boolean flag) {
            if(flag){
                writeCharacteristic(bleUtils.getDeviceSN());
            }
        }
    };

    @Override
    public void onConnectionState(int state) {
        if (state == 1) {
            setNotifyCharacteristic();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ViseBluetooth.getInstance().removeOnNotifyListener();
    }

    @Override
    protected void onReadReturn(byte[] bytes) {
        super.onReadReturn(bytes);
        if (bytes[0] == 0x07 && bytes[1] == 0x00) {
            writeCharacteristic(bleUtils.getFWVer());
        } else if (bytes[0] == 0x07 && bytes[1] == 0x05) {
            writeCharacteristic(bleUtils.getBatteryValue());
        }
        if (bleUtils.returnDeviceSN(bytes) != null) {
            watchSN.setText(bleUtils.returnDeviceSN(bytes));
            PreferenceData.setDeviceSnValue(this, bleUtils.returnDeviceSN(bytes));
        } else if (bleUtils.returnFWVer(bytes) != null) {
            watchVer.setText(bleUtils.returnFWVer(bytes));
            PreferenceData.setDeviceFwvValue(this, bleUtils.returnFWVer(bytes));
        } else if (bleUtils.returnBatteryValue(bytes) != null) {
            battery_num = Integer.parseInt(bleUtils.returnBatteryValue(bytes));
            battery.setText(bleUtils.returnBatteryValue(bytes) + "%");
        } else if (bleUtils.returnDeviceType(bytes) != null) {
            PreferenceData.setDeviceType(this, bleUtils.returnDeviceType(bytes));
        }
    }

    @OnClick({R.id.btnExit, R.id.update})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnExit:
                finish();
                overridePendingTransitionExit(UpdateHardware.this);
                break;
            case R.id.update:
                //  if(battery_num>=50){
                ViseBluetooth.getInstance().removeOnNotifyListener();
                CheckUpdateDialog1 dialog = new CheckUpdateDialog1(UpdateHardware.this);
                dialog.show();
//                }else{
//                    showToatst(UpdateHardware.this,"手表电量不足50%，无法升级！");
//                }
                break;
        }
    }
}
