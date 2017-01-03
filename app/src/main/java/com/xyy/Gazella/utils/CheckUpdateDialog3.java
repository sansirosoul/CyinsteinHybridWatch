package com.xyy.Gazella.utils;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.polidea.rxandroidble.RxBleClient;
import com.polidea.rxandroidble.RxBleConnection;
import com.xyy.Gazella.services.DfuService;
import com.xyy.Gazella.view.NumberProgressBar;
import com.ysp.hybridtwatch.R;
import com.ysp.newband.BaseActivity;
import com.ysp.newband.PreferenceData;

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

    private void scanDevices(){
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        bluetoothAdapter.startLeScan(new BluetoothAdapter.LeScanCallback() {
            @Override
            public void onLeScan(BluetoothDevice bluetoothDevice, int i, byte[] bytes) {
                if (bluetoothDevice.getName().equals("DfuTarg")) {
                    bluetoothAdapter.stopLeScan(this);
                    new DfuServiceInitiator(bluetoothDevice.getAddress()).setDisableNotification(true).setZip(R.raw.ct003v00051b).start(context, DfuService.class);
                }
            }
        });
    }

    @Override
    protected void onWriteReturn(byte[] bytes) {
        super.onWriteReturn(bytes);
             scanDevices();
//        rxBleClient = GazelleApplication.getRxBleClient(this);
//        scanSubscription = rxBleClient.scanBleDevices()
//                .subscribe(
//                        rxBleScanResult -> {
//                            // Process scan result here.
//                            BluetoothDevice bluetoothDevice = rxBleScanResult.getBleDevice().getBluetoothDevice();
//                            if (bluetoothDevice.getName().equals("DfuTarg")) {
//                                scanSubscription.unsubscribe();
//                                new DfuServiceInitiator(bluetoothDevice.getAddress()).setDisableNotification(true).setZip(R.raw.ct003v00048).start(context, DfuService.class);
//                            }
//                        },
//                        throwable -> {
//                            // Handle an error here.
//                            Log.d("OTA========", "Scan error :" + throwable);
//                        }
//                );
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
    }

    private DfuProgressListener mDfuProgressListener = new DfuProgressListener() {
        @Override
        public void onDeviceConnecting(String deviceAddress) {
            Log.d("OTA","----------onDeviceConnecting------------");
        }

        @Override
        public void onDeviceConnected(String deviceAddress) {
            Log.d("OTA","----------onDeviceConnected------------");
        }

        @Override
        public void onDfuProcessStarting(String deviceAddress) {
            Log.d("OTA","----------onDfuProcessStarting------------");
        }

        @Override
        public void onDfuProcessStarted(String deviceAddress) {
            Log.d("OTA","----------onDfuProcessStarted------------");
        }

        @Override
        public void onEnablingDfuMode(String deviceAddress) {
            Log.d("OTA","----------onEnablingDfuMode------------");
        }

        @Override
        public void onProgressChanged(String deviceAddress, int percent, float speed, float avgSpeed, int currentPart, int partsTotal) {
            numberbar.setProgress(percent);
        }

        @Override
        public void onFirmwareValidating(String deviceAddress) {
            Log.d("OTA","----------onFirmwareValidating------------");
        }

        @Override
        public void onDeviceDisconnecting(String deviceAddress) {
            Log.d("OTA","----------onDeviceDisconnecting------------");
        }

        @Override
        public void onDeviceDisconnected(String deviceAddress) {
            Log.d("OTA","----------onDeviceDisconnected------------");
        }

        @Override
        public void onDfuCompleted(String deviceAddress) {
            cleanObservable();
            Intent intent = new Intent(context, CheckUpdateDialog4.class);
            startActivity(intent);
            finish();
        }

        @Override
        public void onDfuAborted(String deviceAddress) {
            cleanObservable();
            showToatst(context,"固件升级失败，请重新升级！");
            finish();
        }

        @Override
        public void onError(String deviceAddress, int error, int errorType, String message) {
            cleanObservable();
            showToatst(context,"固件升级失败，请重新升级！");
            finish();
        }
    };

}
