package com.xyy.Gazella.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.polidea.rxandroidble.RxBleConnection;
import com.polidea.rxandroidble.RxBleDevice;
import com.xyy.Gazella.utils.BleUtils;
import com.xyy.Gazella.utils.CheckUpdateDialog1;
import com.ysp.newband.BaseActivity;
import com.ysp.smartwatch.R;

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
    private Observable<RxBleConnection> connectionObservable;
    private RxBleDevice bleDevice;

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
//        bleDevice = GazelleApplication.getRxBleClient(this).getBleDevice(GazelleApplication.deviceAddress);
//        connectionObservable = bleDevice
//                .establishConnection(this, false)
//                .compose(new ConnectionSharingAdapter());
//
//        Write(GET_SN,bleUtils.getDeviceSN(),connectionObservable);
//        Write(GET_SN,bleUtils.getFWVer(),connectionObservable);
//        Write(GET_SN,bleUtils.getBatteryValue(),connectionObservable);
    }

    @Override
    protected void onReadReturn(int type, byte[] bytes) {
        super.onReadReturn(type, bytes);
        if(bleUtils.returnDeviceSN(bytes)!=null){
            watchSN.setText(bleUtils.returnDeviceSN(bytes));
        }else if(bleUtils.returnFWVer(bytes)!=null){
            watchVer.setText(bleUtils.returnFWVer(bytes));
        }else if(bleUtils.returnBatteryValue(bytes)!=null){
            battery.setText(bleUtils.returnBatteryValue(bytes)+"%");
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
                CheckUpdateDialog1 dialog = new CheckUpdateDialog1(UpdateHardware.this);
                dialog.show();
                break;
        }
    }
}
