package com.xyy.Gazella.activity;

import android.app.Activity;
import android.bluetooth.BluetoothGattCharacteristic;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.polidea.rxandroidble.RxBleConnection;
import com.polidea.rxandroidble.RxBleDevice;
import com.xyy.Gazella.services.BluetoothService;
import com.xyy.Gazella.utils.BleUtils;
import com.ysp.newband.GazelleApplication;
import com.ysp.smartwatch.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;

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
    @BindView(R.id.write)
    TextView write;
    @BindView(R.id.notify)
    TextView notify;
    @BindView(R.id.btn10)
    Button btn10;
    @BindView(R.id.btn11)
    Button btn11;
    @BindView(R.id.btn12)
    Button btn12;
    @BindView(R.id.btn13)
    Button btn13;
    @BindView(R.id.btn14)
    Button btn14;
    @BindView(R.id.btn15)
    Button btn15;
    @BindView(R.id.btn16)
    Button btn16;
    @BindView(R.id.btn17)
    Button btn17;
    @BindView(R.id.btn18)
    Button btn18;
    @BindView(R.id.btn19)
    Button btn19;
    @BindView(R.id.btn20)
    Button btn20;
    @BindView(R.id.btn21)
    Button btn21;
    @BindView(R.id.btn22)
    Button btn22;
    @BindView(R.id.btn23)
    Button btn23;
    private RxBleDevice rxBleDevice;
    private Observable<RxBleConnection> connectionObservable;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        ButterKnife.bind(this);
        context = this;
        bleUtils = new BleUtils();
        mBluetoothService.setActivityHandler(handler);
        writeCharacteristic = GazelleApplication.mBluetoothService.getWriteCharacteristic();

        notifyCharacteristic = GazelleApplication.mBluetoothService.getNotifyCharacteristic();
        if (notifyCharacteristic != null)
            GazelleApplication.mBluetoothService.setCharacteristicNotification(notifyCharacteristic, true);

//        rxBleDevice = GazelleApplication.getRxBleClient(context).getBleDevice(GazelleApplication.deviceAddress);
//        connectionObservable = rxBleDevice.establishConnection(context, false);
//        connectionObservable.subscribe(rxBleConnection -> {
//            Log.d(getClass().getSimpleName(), "Hey, connection has been established!");
//        },throwable -> {
//            System.out.println(throwable);
//        });

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
                            notify.setText(bleUtils.returnDeviceSN(data));
                        } else if (bleUtils.returnBatteryValue(data) != null) {
                            notify.setText(bleUtils.returnBatteryValue(data));
                        } else if (bleUtils.returnDeviceName(data) != null) {
                            notify.setText(bleUtils.returnDeviceName(data));
                        } else {
                            if (data != null && data.length > 0) {
                                final StringBuilder stringBuilder = new StringBuilder(
                                        data.length);
                                for (byte byteChar : data)
                                    stringBuilder.append(String.format("%02X ", byteChar));

                                notify.setText(stringBuilder.toString());
                            }
                        }
                    }
                    break;
                case BluetoothService.WRITE_SUCCESS:
                    notify.setText("");
                    if (msg.obj != null) {
                        byte[] data = (byte[]) msg.obj;
                        if (data != null && data.length > 0) {
                            final StringBuilder stringBuilder = new StringBuilder(
                                    data.length);
                            for (byte byteChar : data)
                                stringBuilder.append(String.format("%02X ", byteChar));

                            write.setText(stringBuilder.toString());
                        }
                    }
                    break;
                case BluetoothService.SERVICES_DISCOVERED:
                    Log.i("TAG", "======================");
                    writeCharacteristic = GazelleApplication.mBluetoothService.getWriteCharacteristic();

                    notifyCharacteristic = GazelleApplication.mBluetoothService.getNotifyCharacteristic();
                    if (notifyCharacteristic != null)
                        GazelleApplication.mBluetoothService.setCharacteristicNotification(notifyCharacteristic, true);
                    break;
            }
        }
    };


    @OnClick({R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn7, R.id.btn6, R.id.btn8, R.id.btn9, R.id.btn10, R.id.btn11, R.id.btn12, R.id.btn13,
            R.id.btn14, R.id.btn15, R.id.btn16, R.id.btn17, R.id.btn18, R.id.btn19, R.id.btn20, R.id.btn21, R.id.btn22, R.id.btn23})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                bleUtils.getDeviceSN(writeCharacteristic);
//                connectionObservable
//                        .flatMap(rxBleConnection -> rxBleConnection.writeCharacteristic(UUID.fromString(BluetoothService.writeUUID), bleUtils.getDeviceSN2()))
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(
//                                characteristicValue -> {
//                                    // Characteristic value confirmed.
//                                    System.out.println("Write success");
//                                    handler.obtainMessage(BluetoothService.WRITE_SUCCESS, characteristicValue).sendToTarget();
//                                },
//                                throwable -> {
//                                    // Handle an error here.
//                                    System.out.println("write" + throwable);
//                                }
//                        );
                break;
            case R.id.btn2:
                bleUtils.sendMessage(writeCharacteristic, 1, 0, 0, 0, 0, 0);
//                connectionObservable
//                        .flatMap(rxBleConnection -> rxBleConnection.setupNotification(UUID.fromString(BluetoothService.notifyUUID)))
//                        .doOnNext(notificationObservable -> {
//                            // Notification has been set up
//                            System.out.println("Notification has been set up");
//                        })
//                        .flatMap(notificationObservable -> notificationObservable) // <-- Notification has been set up, now observe value changes.
//                        .subscribe(
//                                bytes -> {
//                                    System.out.println("noyify===========");
//                                    // Given characteristic has been changes, here is the value.
//                                    final byte[] data = bytes;
//                                    if (data != null && data.length > 0) {
//                                        final StringBuilder stringBuilder = new StringBuilder(
//                                                data.length);
//                                        for (byte byteChar : data)
//                                            stringBuilder.append(String.format("%02X ", byteChar));
//
//                                        notify.setText(stringBuilder.toString());
//
//                                    }
//                                },
//                                throwable -> {
//                                    // Handle an error here.
//                                    System.out.println("notify==========" + throwable);
//                                }
//                        );
                break;
            case R.id.btn3:
                bleUtils.setWatchDateAndTime(writeCharacteristic, 1, 2016, 11, 22, 8, 0, 0);
                break;
            case R.id.btn4:
                bleUtils.setWatchAlarm(writeCharacteristic, 1, 0, 12, 0, 1, 1, "");
                break;
            case R.id.btn5:
                bleUtils.getFWVer(writeCharacteristic);
                break;
            case R.id.btn6:
                bleUtils.setDeviceName(writeCharacteristic, "aaa");
                break;
            case R.id.btn7:
                bleUtils.getDeviceName(writeCharacteristic);
                break;
            case R.id.btn8:
                bleUtils.setSystemType(writeCharacteristic);
                break;
            case R.id.btn9:
                bleUtils.getTodayStep(writeCharacteristic);
                break;
            case R.id.btn10:
                bleUtils.getSleepData(writeCharacteristic, 0);
                break;
            case R.id.btn11:
                bleUtils.eraseWatchData(writeCharacteristic);
                break;
            case R.id.btn12:
                bleUtils.getBatteryValue(writeCharacteristic);
                break;
            case R.id.btn13:
                bleUtils.adjHourHand(writeCharacteristic, 1, 1);
                break;
            case R.id.btn14:
                bleUtils.adjMinuteHand(writeCharacteristic, 1, 1);
                break;
            case R.id.btn15:
                bleUtils.adjSecondHand(writeCharacteristic, 1, 1);
                break;
            case R.id.btn16:
                bleUtils.adjMsgHand(writeCharacteristic, 1, 1);
                break;
            case R.id.btn17:
                bleUtils.adjStepHand(writeCharacteristic, 1, 1);
                break;
            case R.id.btn18:
                bleUtils.resetHand(writeCharacteristic);
                break;
            case R.id.btn19:
                bleUtils.getStepData(writeCharacteristic, 1);
                break;
            case R.id.btn20:
                bleUtils.setWatchShake(writeCharacteristic, 1, 0, 0);
                break;
            case R.id.btn21:
                bleUtils.getAlarms(writeCharacteristic);
                break;
            case R.id.btn22:
                bleUtils.setBleConnect(writeCharacteristic);
                break;
            case R.id.btn23:
                bleUtils.terminateBle(writeCharacteristic);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GazelleApplication.mBluetoothService.close();
    }
}
