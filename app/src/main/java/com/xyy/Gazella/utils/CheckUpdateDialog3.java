package com.xyy.Gazella.utils;

import android.app.Activity;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.xyy.Gazella.services.DfuService;
import com.ysp.newband.GazelleApplication;
import com.ysp.smartwatch.R;

import no.nordicsemi.android.dfu.DfuProgressListener;
import no.nordicsemi.android.dfu.DfuServiceInitiator;
import no.nordicsemi.android.dfu.DfuServiceListenerHelper;

import static com.ysp.newband.GazelleApplication.mBluetoothService;

/**
 * Created by Administrator on 2016/10/14.
 */

public class CheckUpdateDialog3 extends Dialog {
    private Context context;
    private BluetoothAdapter mBluetoothAdapter;

    public CheckUpdateDialog3(Context context) {
        super(context, R.style.dialog);
        this.context = context;
    }

    @Override
    protected void onStart() {
        super.onStart();
        DfuServiceListenerHelper.registerProgressListener(context, mDfuProgressListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mBluetoothAdapter.stopLeScan(leScanCallback);
        DfuServiceListenerHelper.unregisterProgressListener(context, mDfuProgressListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.check_update_dialog3);
        setDialogAttributes((Activity) context, this, 0.8f, 0, Gravity.CENTER);

        setCanceledOnTouchOutside(false);

        if (GazelleApplication.deviceAddress != null && GazelleApplication.isBleConnected == true) {
            BluetoothGattCharacteristic characteristic = GazelleApplication.mBluetoothService.getWriteCharacteristic();
            BleUtils utils = new BleUtils();
            utils.startDfu(characteristic);

            final BluetoothManager bluetoothManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
            mBluetoothAdapter = bluetoothManager.getAdapter();
            mBluetoothAdapter.startLeScan(leScanCallback);
        }
    }

    BluetoothAdapter.LeScanCallback leScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(final BluetoothDevice bluetoothDevice, int i, byte[] bytes) {
            if (bluetoothDevice.getName().equals("DfuTarg")) {
                new DfuServiceInitiator(bluetoothDevice.getAddress()).setDisableNotification(true).setZip(R.raw.ct003v02).start(context, DfuService.class);
                mBluetoothAdapter.stopLeScan(this);
            }
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
            GazelleApplication.isDfu = false;
            if (GazelleApplication.deviceAddress != null) {
                GazelleApplication.mBluetoothService.disconnect();
                mBluetoothService.close();
                if (mBluetoothService.initialize()) {
                    mBluetoothService.connect(GazelleApplication.deviceAddress);
                }
            }
            dismiss();
            CheckUpdateDialog4 dialog4 = new CheckUpdateDialog4(context);
            dialog4.show();
        }

        @Override
        public void onDfuAborted(String deviceAddress) {

        }

        @Override
        public void onError(String deviceAddress, int error, int errorType, String message) {
            Toast.makeText(context, "固件升级失败，请重新升级！", Toast.LENGTH_LONG).show();
            dismiss();
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
