package com.xyy.Gazella.utils;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.polidea.rxandroidble.RxBleClient;
import com.polidea.rxandroidble.RxBleConnection;
import com.xyy.Gazella.services.DfuService;
import com.xyy.Gazella.view.NumberProgressBar;
import com.ysp.newband.BaseActivity;
import com.ysp.newband.GazelleApplication;
import com.ysp.newband.PreferenceData;
import com.ysp.smartwatch.R;

import no.nordicsemi.android.dfu.DfuProgressListener;
import no.nordicsemi.android.dfu.DfuServiceInitiator;
import no.nordicsemi.android.dfu.DfuServiceListenerHelper;
import rx.Observable;
import rx.Subscription;

/**
 * Created by Administrator on 2016/10/14.
 */

public class CheckUpdateDialog3 extends BaseActivity {
    private Context context;
    private NumberProgressBar numberbar;
    private BleUtils bleUtils;
    public Observable<RxBleConnection> connectionObservable;
    private RxBleClient rxBleClient;
    private Subscription scanSubscription;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1001:
                    if (numberbar.getProgress() == 100) {
                        numberbar.setProgress(0);

                    } else {
                        numberbar.incrementProgressBy(1);
                    }
                    break;
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        DfuServiceListenerHelper.registerProgressListener(context, mDfuProgressListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        DfuServiceListenerHelper.unregisterProgressListener(context, mDfuProgressListener);
    }

    @Override
    protected void onWriteReturn(byte[] bytes) {
        super.onWriteReturn(bytes);

        rxBleClient = GazelleApplication.getRxBleClient(this);
        scanSubscription = rxBleClient.scanBleDevices()
                .subscribe(
                        rxBleScanResult -> {
                            // Process scan result here.
                            BluetoothDevice bluetoothDevice = rxBleScanResult.getBleDevice().getBluetoothDevice();
                            if (bluetoothDevice.getName().equals("DfuTarg")) {
                                new DfuServiceInitiator(bluetoothDevice.getAddress()).setDisableNotification(true).setZip(R.raw.ct003v00047).start(context, DfuService.class);
                                scanSubscription.unsubscribe();
                            }
                        },
                        throwable -> {
                            // Handle an error here.
                            Log.d("==========", "Scan error :" + throwable);
                        }
                );

    }

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.check_update_dialog3);
        setFinishOnTouchOutside(false);
        context = this;
        String address = PreferenceData.getAddressValue(context);
        if (address != null && !address.equals("")){
            bleUtils = new BleUtils();
            connectionObservable=getRxObservable(this);
            Write(bleUtils.startDfu(), connectionObservable);
        }
        numberbar = (NumberProgressBar) findViewById(R.id.numberbar);
//        mHandler.post(runnable);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            mHandler.sendEmptyMessage(1001);
            mHandler.postDelayed(this, 100);
        }
    };

    private DfuProgressListener mDfuProgressListener = new DfuProgressListener() {
        @Override
        public void onDeviceConnecting(String deviceAddress) {

        }

        @Override
        public void onDeviceConnected(String deviceAddress) {

        }

        @Override
        public void onDfuProcessStarting(String deviceAddress) {

        }

        @Override
        public void onDfuProcessStarted(String deviceAddress) {

        }

        @Override
        public void onEnablingDfuMode(String deviceAddress) {

        }

        @Override
        public void onProgressChanged(String deviceAddress, int percent, float speed, float avgSpeed, int currentPart, int partsTotal) {
            numberbar.setProgress(percent);
        }

        @Override
        public void onFirmwareValidating(String deviceAddress) {

        }

        @Override
        public void onDeviceDisconnecting(String deviceAddress) {

        }

        @Override
        public void onDeviceDisconnected(String deviceAddress) {

        }

        @Override
        public void onDfuCompleted(String deviceAddress) {
            Intent intent = new Intent(context, CheckUpdateDialog4.class);
            startActivity(intent);
            finish();
        }

        @Override
        public void onDfuAborted(String deviceAddress) {
            Toast.makeText(context, "固件升级失败，请重新升级！", Toast.LENGTH_LONG).show();
            finish();
        }

        @Override
        public void onError(String deviceAddress, int error, int errorType, String message) {
            Toast.makeText(context, "固件升级失败，请重新升级！", Toast.LENGTH_LONG).show();
            finish();
        }
    };

}
