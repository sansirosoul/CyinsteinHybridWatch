package com.xyy.Gazella.activity;

import android.app.Activity;
import android.bluetooth.BluetoothGattCharacteristic;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Button;
import android.widget.TextView;

import com.xyy.Gazella.services.BluetoothService;
import com.xyy.Gazella.utils.BleUtils;
import com.ysp.newband.GazelleApplication;
import com.ysp.smartwatch.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.ysp.newband.GazelleApplication.mBluetoothService;

/**
 * Created by Administrator on 2016/11/14.
 */

public class BleTest extends Activity {
    BluetoothGattCharacteristic writeCharacteristic;
    BluetoothGattCharacteristic notifyCharacteristic;
    BleUtils bleUtils;
    @BindView(R.id.btn1)
    Button btn1;
    @BindView(R.id.btn2)
    Button btn2;
    @BindView(R.id.btn3)
    Button btn3;
    @BindView(R.id.btn4)
    Button btn4;
    @BindView(R.id.btn5)
    Button btn5;
    @BindView(R.id.btn6)
    Button btn6;
    @BindView(R.id.btn7)
    Button btn7;
    @BindView(R.id.btn8)
    Button btn8;
    @BindView(R.id.btn9)
    Button btn9;
    @BindView(R.id.tv_sn)
    TextView tvSn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        ButterKnife.bind(this);
        bleUtils = new BleUtils();
        mBluetoothService.setActivityHandler(handler);
        writeCharacteristic = GazelleApplication.mBluetoothService.getWriteCharacteristic();

        notifyCharacteristic = GazelleApplication.mBluetoothService.getNotifyCharacteristic();
        GazelleApplication.mBluetoothService.setCharacteristicNotification(notifyCharacteristic, true);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case BluetoothService.STATE_DISCONNECTED:
                    if (GazelleApplication.deviceAddress != null) {
                        mBluetoothService.close();
                        if (mBluetoothService.initialize()) {
                            mBluetoothService.connect(GazelleApplication.deviceAddress);
                        }
                    }
                    break;
                case BluetoothService.STATE_CONNECTED:
                    GazelleApplication.isBleConnected = false;
                    break;
                case BluetoothService.NOTIFY_SUCCESS:

                    if (msg.obj != null) {

                        byte[] data = (byte[]) msg.obj;
                        if (bleUtils.returnDeviceSN(data) != null) {
                            tvSn.setText(bleUtils.returnDeviceSN(data));
                        } else if (bleUtils.returnBatteryValue(data) != null) {
                            tvSn.setText(bleUtils.returnBatteryValue(data));
                        } else if (bleUtils.returnDeviceName(data) != null) {
                            tvSn.setText(bleUtils.returnDeviceName(data));
                        } else {
                            if (data != null && data.length > 0) {
                                final StringBuilder stringBuilder = new StringBuilder(
                                        data.length);
                                for (byte byteChar : data)
                                    stringBuilder.append(String.format("%02X ", byteChar));

                                tvSn.setText(stringBuilder.toString());
                            }
                        }
                    }
                    break;
            }
        }
    };

//    @OnClick({R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn7, R.id.btn6, R.id.btn8, R.id.btn9})
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.btn1:
//                bleUtils.getDeviceSN(writeCharacteristic);
//                break;
//            case R.id.btn2:
//                bleUtils.sendMessage(writeCharacteristic, 1, 0, 0, 0, 0, 0);
//                break;
//            case R.id.btn3:
//                bleUtils.setWatchDateAndTime(writeCharacteristic, 1, 2016, 11, 24, 8, 0, 0);
//                break;
//            case R.id.btn4:
//
//                break;
//            case R.id.btn5:
//                bleUtils.getFWVer(writeCharacteristic);
//                break;
//            case R.id.btn6:
//                bleUtils.setDeviceName(writeCharacteristic, "aaa");
//                break;
//            case R.id.btn7:
//                bleUtils.getDeviceName(writeCharacteristic);
//                break;
//            case R.id.btn8:
//                bleUtils.setSystemType(writeCharacteristic);
//                break;
//            case R.id.btn9:
//                bleUtils.getTodayStep(writeCharacteristic);
//                break;
//        }
//    }
}
