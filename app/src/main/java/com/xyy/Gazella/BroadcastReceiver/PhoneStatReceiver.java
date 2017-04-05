package com.xyy.Gazella.BroadcastReceiver;

import android.app.Service;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.orhanobut.logger.Logger;
import com.polidea.rxandroidble.RxBleConnection;
import com.vise.baseble.ViseBluetooth;
import com.vise.baseble.callback.IBleCallback;
import com.vise.baseble.exception.BleException;
import com.xyy.Gazella.utils.HexString;
import com.ysp.newband.BaseActivity;
import com.ysp.newband.PreferenceData;

import java.util.List;
import java.util.UUID;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

import static com.xyy.Gazella.services.BluetoothService.writeUUID;

/**
 * Created by Administrator on 2016/10/29.
 */

public class PhoneStatReceiver extends BroadcastReceiver {
    private static final String TAG = "PhoneStatReceiver";
    public final static String serviceUUID = "6e400001-b5a3-f393-e0a9-e50e24dcca9e";
    public final static String ReadUUID = "6e400003-b5a3-f393-e0a9-e50e24dcca9e";
    public final static String WriteUUID = "6e400002-b5a3-f393-e0a9-e50e24dcca9e";
    private Context mContext;
    private BluetoothGatt mBluetoothGatt;
    private TelephonyManager tm;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.mContext = context;
        mBluetoothGatt=BaseActivity.mBluetoothGatt;
        if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
            //如果是去电（拨出）

        } else {
            //查了下android文档，貌似没有专门用于接收来电的action,所以，非去电即来电
            tm = (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);
            tm.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
            //设置一个监听器
        }
    }

    PhoneStateListener listener = new PhoneStateListener() {

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            // TODO Auto-generated method stub
            //state 当前状态 incomingNumber,貌似没有去电的API
            super.onCallStateChanged(state, incomingNumber);
            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE:
//                    System.out.println("挂断");
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
//                    System.out.println("接听");
                    break;
                case TelephonyManager.CALL_STATE_RINGING:
                    System.out.println("响铃:来电号码" + incomingNumber);
                    //输出来电号码
                    String address = PreferenceData.getAddressValue(mContext);
                    if (address != null && !address.equals("")) {
//                        BleUtils bleUtils = new BleUtils();
//                        if (GazelleApplication.isBleConnected) {
//                            int pstate = PreferenceData.getNotificationPhoneState(mContext);
//                            int shake = PreferenceData.getNotificationShakeState(mContext);
//                            if (pstate == 1) {
//                                writeCharacteristic(bleUtils.sendMessage(1, pstate, 0, 0, 0, shake));
//                            }
//                        }

                    }
                    tm=null;
                    break;
            }
        }
    };

    public BluetoothGattCharacteristic getWriteCharacteristic() {
        BluetoothGattCharacteristic gattCharacteristic = null;
        if (mBluetoothGatt == null)
            return null;
        List<BluetoothGattService> services = mBluetoothGatt.getServices();
        for (int i = 0; i<services.size();i++){
            BluetoothGattService service = services.get(i);
            if(service.getUuid().toString().equals(serviceUUID)){
                List<BluetoothGattCharacteristic> characteristics = service.getCharacteristics();
                for(int j = 0; j<characteristics.size();j++){
                    BluetoothGattCharacteristic characteristic = characteristics.get(j);
                    if(characteristic.getUuid().toString().equals(writeUUID)){
                        gattCharacteristic=characteristic;
                    }
                }
            }
        }
        return gattCharacteristic;
    }

    public void writeCharacteristic(byte[] bytes){
        ViseBluetooth.getInstance().writeCharacteristic(getWriteCharacteristic(), bytes, new IBleCallback<BluetoothGattCharacteristic>() {
            @Override
            public void onSuccess(BluetoothGattCharacteristic bluetoothGattCharacteristic, int type) {
            }

            @Override
            public void onFailure(BleException exception) {
                Logger.e(exception.getDescription());
            }
        });
    }

    private Observable<byte[]> WiterCharacteristic(String writeString, Observable<RxBleConnection> connectionObservable) {
        return connectionObservable
                .flatMap(new Func1<RxBleConnection, Observable<byte[]>>() {
                    @Override
                    public Observable<byte[]> call(RxBleConnection rxBleConnection) {
                        return rxBleConnection.writeCharacteristic(UUID.fromString(WriteUUID), HexString.hexToBytes(writeString));
                    }
                });

    }

    protected void Write(byte[] bytes, Observable<RxBleConnection> connectionObservable) {
        WiterCharacteristic(HexString.bytesToHex(bytes), connectionObservable).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<byte[]>() {
                    @Override
                    public void call(byte[] bytes) {
                        Logger.t(TAG).e("写入数据  >>>>>>  " + HexString.bytesToHex(bytes));
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.t(TAG).e("写入数据失败  >>>>>>   " + throwable.toString());
                    }
                });


//        connectionObservable
//                .flatMap(new Func1<RxBleConnection, Observable<Observable<byte[]>>>() {
//                    @Override
//                    public Observable<Observable<byte[]>> call(RxBleConnection rxBleConnection) {
//                        return rxBleConnection.setupNotification(UUID.fromString(ReadUUID));
//                    }
//                }).doOnNext(new Action1<Observable<byte[]>>() {
//            @Override
//            public void call(Observable<byte[]> observable) {
//                Logger.t(TAG).e("开始接收通知  >>>>>>  ");
//
//                WiterCharacteristic(HexString.bytesToHex(bytes), connectionObservable).observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(new Action1<byte[]>() {
//                            @Override
//                            public void call(byte[] bytes) {
//                                Logger.t(TAG).e("写入数据  >>>>>>  " + HexString.bytesToHex(bytes));
//                                onWriteReturn(type,bytes);
//                            }
//                        }, new Action1<Throwable>() {
//                            @Override
//                            public void call(Throwable throwable) {
//                                Logger.t(TAG).e("写入数据失败  >>>>>>   " + throwable.toString());
//                            }
//                        });
//            }
//        }).flatMap(new Func1<Observable<byte[]>, Observable<byte[]>>() {
//            @Override
//            public Observable<byte[]> call(Observable<byte[]> notificationObservable) {
//                return notificationObservable;
//            }
//        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<byte[]>() {
//            @Override
//            public void call(byte[] bytes) {
//                Logger.t(TAG).e("接收数据  >>>>>>  " + HexString.bytesToHex(bytes) + "\n" + ">>>>>>>>" + new String(bytes));
//                onReadReturn(type, bytes);
//            }
//        }, new Action1<Throwable>() {
//            @Override
//            public void call(Throwable throwable) {
//                Logger.t(TAG).e("接收数据失败 >>>>>>  " + throwable.toString());
//            }
//        });
    }
}
