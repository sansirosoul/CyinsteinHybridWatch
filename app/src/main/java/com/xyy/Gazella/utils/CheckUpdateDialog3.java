package com.xyy.Gazella.utils;

import android.app.Activity;
import android.app.Dialog;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;

import com.polidea.rxandroidble.RxBleClient;
import com.polidea.rxandroidble.RxBleConnection;
import com.polidea.rxandroidble.RxBleDevice;
import com.polidea.rxandroidble.utils.ConnectionSharingAdapter;
import com.xyy.Gazella.services.DfuService;
import com.xyy.Gazella.view.NumberProgressBar;
import com.ysp.newband.BaseActivity;
import com.ysp.newband.GazelleApplication;
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
    private Observable<RxBleConnection> connectionObservable;
    private RxBleDevice bleDevice;
    private BleUtils bleUtils;
    private RxBleClient rxBleClient;
    private Subscription scanSubscription;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1001:
                    if(numberbar.getProgress()==100) {
                        numberbar.setProgress(0);

                    }else {
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
    protected void onWriteReturn(int type, byte[] bytes) {
        super.onWriteReturn(type, bytes);
        if(type==GET_SN){
            rxBleClient = GazelleApplication.getRxBleClient(this);
            scanSubscription = rxBleClient.scanBleDevices()
                    .subscribe(
                            rxBleScanResult -> {
                                // Process scan result here.
                                BluetoothDevice bluetoothDevice = rxBleScanResult.getBleDevice().getBluetoothDevice();
                                if (bluetoothDevice.getName().equals("DfuTarg")) {
                                    System.out.println("OTA is starting..."+bluetoothDevice.getAddress());
                                    new DfuServiceInitiator(bluetoothDevice.getAddress()).setDisableNotification(true).setZip(R.raw.ct003v00042).start(context, DfuService.class);
                                    scanSubscription.unsubscribe();
                                }
                            },
                            throwable -> {
                                // Handle an error here.
                                Log.d("==========","Scan error :"+throwable);
                            }
                    );
        }
    }

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.check_update_dialog3);
        context=this;
        bleUtils = new BleUtils();

        numberbar=(NumberProgressBar)findViewById(R.id.numberbar);
//        mHandler.post(runnable);

        bleDevice = GazelleApplication.getRxBleClient(this).getBleDevice(GazelleApplication.deviceAddress);
        connectionObservable = bleDevice
                .establishConnection(context, false)
                .compose(new ConnectionSharingAdapter());

        Write(GET_SN,bleUtils.startDfu(),connectionObservable);
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
            System.out.println("onDeviceConnecting");
        }

        @Override
        public void onDeviceConnected(String deviceAddress) {
            System.out.println("onDeviceConnected");
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
            finish();
            CheckUpdateDialog4 dialog4 = new CheckUpdateDialog4(context);
            dialog4.show();
        }

        @Override
        public void onDfuAborted(String deviceAddress) {
            GazelleApplication.isDfu = false;
            System.out.println("onDfuAborted");
        }

        @Override
        public void onError(String deviceAddress, int error, int errorType, String message) {
            GazelleApplication.isDfu = false;
            Toast.makeText(context, "固件升级失败，请重新升级！", Toast.LENGTH_LONG).show();
            finish();
        }
    };

    public void setDialogAttributes(Activity context, final Dialog dialog,
                                    float widthP, float heightP, int gravity) {

        Display d = context.getWindowManager().getDefaultDisplay();
        WindowManager.LayoutParams p = dialog.getWindow().getAttributes();

        Point mPoint = new Point();
        d.getSize(mPoint);
        if (heightP != 0)
            p.height = (int) (mPoint.y * heightP);
        if (widthP != 0)
            p.width = (int) (mPoint.x * widthP);
        dialog.getWindow().setAttributes(p);
        dialog.getWindow().setGravity(gravity);
    }
}
