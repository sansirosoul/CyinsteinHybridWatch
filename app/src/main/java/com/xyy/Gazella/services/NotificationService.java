package com.xyy.Gazella.services;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.os.Handler;
import android.os.Message;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

import com.orhanobut.logger.Logger;
import com.polidea.rxandroidble.RxBleConnection;
import com.vise.baseble.ViseBluetooth;
import com.vise.baseble.callback.IBleCallback;
import com.vise.baseble.exception.BleException;
import com.xyy.Gazella.utils.BleUtils;
import com.xyy.Gazella.utils.HexString;
import com.ysp.newband.BaseActivity;
import com.ysp.newband.PreferenceData;

import java.util.List;
import java.util.UUID;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

import static com.xyy.Gazella.services.BluetoothService.notifyUUID;
import static com.xyy.Gazella.services.BluetoothService.writeUUID;

/**
 * Created by Administrator on 2017/1/3.
 */

//通知监听服务类
public class NotificationService extends NotificationListenerService {
    private static final String TAG = "NotificationService";
    public final static String serviceUUID = "6e400001-b5a3-f393-e0a9-e50e24dcca9e";
    public final static String ReadUUID = "6e400003-b5a3-f393-e0a9-e50e24dcca9e";
    public final static String WriteUUID = "6e400002-b5a3-f393-e0a9-e50e24dcca9e";
    private BluetoothGatt mBluetoothGatt;
    private boolean isGet = false;

    //当系统收到新的通知后出发回调
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        System.out.println(sbn.getPackageName());
        String pname = sbn.getPackageName();
        if (!isGet){
            Message.obtain(handler,101,pname).sendToTarget();
            isGet=true;
            new Handler(getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    isGet=false;
                }
            },5000);
        }
    }

    //当系统通知被删掉后出发回调
    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {

    }

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

    public void setNotifyCharacteristic() {
        mBluetoothGatt=BaseActivity.mBluetoothGatt;
        ViseBluetooth.getInstance().enableCharacteristicNotification(getNotifyCharacteristic(), new IBleCallback<BluetoothGattCharacteristic>() {
            @Override
            public void onSuccess(BluetoothGattCharacteristic bluetoothGattCharacteristic, int type) {

            }

            @Override
            public void onFailure(BleException exception) {
                Logger.e(exception.getDescription());
            }
        }, false);
    }

    public BluetoothGattCharacteristic getNotifyCharacteristic() {
        BluetoothGattCharacteristic gattCharacteristic = null;
        if (mBluetoothGatt == null)
            return null;
        List<BluetoothGattService> services = mBluetoothGatt.getServices();
        for (int i = 0; i < services.size(); i++) {
            BluetoothGattService service = services.get(i);
            if (service.getUuid().toString().equals(serviceUUID)) {
                List<BluetoothGattCharacteristic> characteristics = service.getCharacteristics();
                for (int j = 0; j < characteristics.size(); j++) {
                    BluetoothGattCharacteristic characteristic = characteristics.get(j);
                    if (characteristic.getUuid().toString().equals(notifyUUID)) {
                        gattCharacteristic = characteristic;
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

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 101:
                    mBluetoothGatt=BaseActivity.mBluetoothGatt;
                    String pname = (String) msg.obj;
                    if(pname.equals("com.tencent.mobileqq")||pname.equals("com.tencent.mm")||pname.equals("com.facebook.katana")||pname.equals("com.twitter.android")
                            ||pname.equals("com.skype.rover")||pname.equals("jp.naver.line.android")){
                        int state = PreferenceData.getNotificationTwitterState(NotificationService.this);
                        if(state==1){
                            String address = PreferenceData.getAddressValue(NotificationService.this);
                            if (address != null && !address.equals("")) {
                                BleUtils bleUtils = new BleUtils();
                                int shake = PreferenceData.getNotificationShakeState(NotificationService.this);
                                writeCharacteristic(bleUtils.sendMessage(1, 0, 0, 0, state, shake));
                            }
                        }
                    } else if(pname.equals("com.android.email")){
                        //邮件通知
                        int state = PreferenceData.getNotificationMailState(NotificationService.this);
                        if(state==1){
                            String address = PreferenceData.getAddressValue(NotificationService.this);
                            if (address != null && !address.equals("")) {
                                BleUtils bleUtils = new BleUtils();
                                int shake = PreferenceData.getNotificationShakeState(NotificationService.this);
                                writeCharacteristic(bleUtils.sendMessage(1, 0, 0, state, 0, shake));
                            }
                        }
                    } else if(pname.equals("com.android.mms")){
                        //短信
                        int state = PreferenceData.getNotificationMessageState(NotificationService.this);
                        if(state==1){
                            String address = PreferenceData.getAddressValue(NotificationService.this);
                            if (address != null && !address.equals("")) {
                                BleUtils bleUtils = new BleUtils();
                                int shake = PreferenceData.getNotificationShakeState(NotificationService.this);
                                writeCharacteristic(bleUtils.sendMessage(1, 0, state, 0, 0, shake));
                            }
                        }
                    }



//                    if(pname.equals("com.tencent.mobileqq")){
//                        //qq通知
//                        int state = PreferenceData.getNotificationQQState(NotificationService.this);
//                        if(state==1){
//                            String address = PreferenceData.getAddressValue(NotificationService.this);
//                            if (address != null && !address.equals("")) {
//                                BleUtils bleUtils = new BleUtils();
////                                connectionObservable = BaseActivity.getRxObservable(NotificationService.this);
//                                int shake = PreferenceData.getNotificationShakeState(NotificationService.this);
////                                Write(bleUtils.sendMessage(1, 0, 0, 0, state, shake), connectionObservable);
//                                writeCharacteristic(bleUtils.sendMessage(1, 0, 0, 0, state, shake));
//                            }
//                        }
//                    }else if(pname.equals("com.tencent.mm")){
//                        //微信通知
//                        int state = PreferenceData.getNotificationWechatState(NotificationService.this);
//                        if(state==1){
//                            String address = PreferenceData.getAddressValue(NotificationService.this);
//                            if (address != null && !address.equals("")) {
//                                BleUtils bleUtils = new BleUtils();
////                                connectionObservable = BaseActivity.getRxObservable(NotificationService.this);
//                                int shake = PreferenceData.getNotificationShakeState(NotificationService.this);
////                                Write(bleUtils.sendMessage(1, 0, 0, 0, state, shake), connectionObservable);
//                                writeCharacteristic(bleUtils.sendMessage(1, 0, 0, 0, state, shake));
//                            }
//                        }
//                    }else if(pname.equals("com.facebook.katana")){
//                        //facebook通知
//                        int state = PreferenceData.getNotificationFacebookState(NotificationService.this);
//                        if(state==1){
//                            String address = PreferenceData.getAddressValue(NotificationService.this);
//                            if (address != null && !address.equals("")) {
//                                BleUtils bleUtils = new BleUtils();
////                                connectionObservable = BaseActivity.getRxObservable(NotificationService.this);
//                                int shake = PreferenceData.getNotificationShakeState(NotificationService.this);
////                                Write(bleUtils.sendMessage(1, 0, 0, 0, state, shake), connectionObservable);
//                                writeCharacteristic(bleUtils.sendMessage(1, 0, 0, 0, state, shake));
//                            }
//                        }
//                    }else if(pname.equals("com.twitter.android")){
//                        //twitter通知
//                        int state = PreferenceData.getNotificationTwitterState(NotificationService.this);
//                        if(state==1){
//                            String address = PreferenceData.getAddressValue(NotificationService.this);
//                            if (address != null && !address.equals("")) {
//                                BleUtils bleUtils = new BleUtils();
////                                connectionObservable = BaseActivity.getRxObservable(NotificationService.this);
//                                int shake = PreferenceData.getNotificationShakeState(NotificationService.this);
////                                Write(bleUtils.sendMessage(1, 0, 0, 0, state, shake), connectionObservable);
//                                writeCharacteristic(bleUtils.sendMessage(1, 0, 0, 0, state, shake));
//                            }
//                        }
//                    }else if(pname.equals("com.skype.rover")){
//                        //skype通知
//                        int state = PreferenceData.getNotificationSkypeState(NotificationService.this);
//                        if(state==1){
//                            String address = PreferenceData.getAddressValue(NotificationService.this);
//                            if (address != null && !address.equals("")) {
//                                BleUtils bleUtils = new BleUtils();
////                                connectionObservable = BaseActivity.getRxObservable(NotificationService.this);
//                                int shake = PreferenceData.getNotificationShakeState(NotificationService.this);
////                                Write(bleUtils.sendMessage(1, 0, 0, 0, state, shake), connectionObservable);
//                                writeCharacteristic(bleUtils.sendMessage(1, 0, 0, 0, state, shake));
//                            }
//                        }
//                    }else if(pname.equals("jp.naver.line.android")){
//                        //line通知
//                        int state = PreferenceData.getNotificationLineState(NotificationService.this);
//                        if(state==1){
//                            String address = PreferenceData.getAddressValue(NotificationService.this);
//                            if (address != null && !address.equals("")) {
//                                BleUtils bleUtils = new BleUtils();
////                                connectionObservable = BaseActivity.getRxObservable(NotificationService.this);
//                                int shake = PreferenceData.getNotificationShakeState(NotificationService.this);
////                                Write(bleUtils.sendMessage(1, 0, 0, 0, state, shake), connectionObservable);
//                                writeCharacteristic(bleUtils.sendMessage(1, 0, 0, 0, state, shake));
//                            }
//                        }
//                    }else if(pname.equals("com.android.email")){
//                        //邮件通知
//                        int state = PreferenceData.getNotificationMailState(NotificationService.this);
//                        if(state==1){
//                            String address = PreferenceData.getAddressValue(NotificationService.this);
//                            if (address != null && !address.equals("")) {
//                                BleUtils bleUtils = new BleUtils();
////                                connectionObservable = BaseActivity.getRxObservable(NotificationService.this);
//                                int shake = PreferenceData.getNotificationShakeState(NotificationService.this);
////                                Write(bleUtils.sendMessage(1, 0, 0, state, 0, shake), connectionObservable);
//                                writeCharacteristic(bleUtils.sendMessage(1, 0, 0, state, 0, shake));
//                            }
//                        }
//                    }else if(pname.equals("com.android.mms")){
//                        int state = PreferenceData.getNotificationMessageState(NotificationService.this);
//                        if(state==1){
//                            String address = PreferenceData.getAddressValue(NotificationService.this);
//                            if (address != null && !address.equals("")) {
//                                BleUtils bleUtils = new BleUtils();
//                                int shake = PreferenceData.getNotificationShakeState(NotificationService.this);
//                                writeCharacteristic(bleUtils.sendMessage(1, 0, state, 0, 0, shake));
//                            }
//                        }
//                    }
                    break;
            }
        }
    };


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
    }

}
