package com.xyy.Gazella.services;

import android.os.Handler;
import android.os.Message;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

import com.orhanobut.logger.Logger;
import com.polidea.rxandroidble.RxBleConnection;
import com.xyy.Gazella.utils.BleUtils;
import com.xyy.Gazella.utils.HexString;
import com.ysp.newband.BaseActivity;
import com.ysp.newband.PreferenceData;

import java.util.UUID;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Administrator on 2017/1/3.
 */

//通知监听服务类
public class NotificationService extends NotificationListenerService {
    private static final String TAG = "NotificationService";
    public final static String WriteUUID = "6e400002-b5a3-f393-e0a9-e50e24dcca9e";
    private Observable<RxBleConnection> connectionObservable;

    //当系统收到新的通知后出发回调
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        Logger.e(sbn.getPackageName());
        String pname = sbn.getPackageName();
        Message.obtain(handler,101,pname).sendToTarget();
    }

    //当系统通知被删掉后出发回调
    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {

    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 101:
                    String pname = (String) msg.obj;
                    if(pname.equals("com.tencent.mobileqq")){
                        //qq通知
                        int state = PreferenceData.getNotificationQQState(NotificationService.this);
                        if(state==1){
                            String address = PreferenceData.getAddressValue(NotificationService.this);
                            if (address != null && !address.equals("")) {
                                BleUtils bleUtils = new BleUtils();
                                connectionObservable = BaseActivity.getRxObservable(NotificationService.this);
                                int shake = PreferenceData.getNotificationShakeState(NotificationService.this);
                                Write(bleUtils.sendMessage(1, 0, 0, 0, state, shake), connectionObservable);
                            }
                        }
                    }else if(pname.equals("com.tencent.mm")){
                        //微信通知
                        int state = PreferenceData.getNotificationWechatState(NotificationService.this);
                        if(state==1){
                            String address = PreferenceData.getAddressValue(NotificationService.this);
                            if (address != null && !address.equals("")) {
                                BleUtils bleUtils = new BleUtils();
                                connectionObservable = BaseActivity.getRxObservable(NotificationService.this);
                                int shake = PreferenceData.getNotificationShakeState(NotificationService.this);
                                Write(bleUtils.sendMessage(1, 0, 0, 0, state, shake), connectionObservable);
                            }
                        }
                    }else if(pname.equals("com.facebook.katana")){
                        //facebook通知
                        int state = PreferenceData.getNotificationFacebookState(NotificationService.this);
                        if(state==1){
                            String address = PreferenceData.getAddressValue(NotificationService.this);
                            if (address != null && !address.equals("")) {
                                BleUtils bleUtils = new BleUtils();
                                connectionObservable = BaseActivity.getRxObservable(NotificationService.this);
                                int shake = PreferenceData.getNotificationShakeState(NotificationService.this);
                                Write(bleUtils.sendMessage(1, 0, 0, 0, state, shake), connectionObservable);
                            }
                        }
                    }else if(pname.equals("com.twitter.android")){
                        //twitter通知
                        int state = PreferenceData.getNotificationTwitterState(NotificationService.this);
                        if(state==1){
                            String address = PreferenceData.getAddressValue(NotificationService.this);
                            if (address != null && !address.equals("")) {
                                BleUtils bleUtils = new BleUtils();
                                connectionObservable = BaseActivity.getRxObservable(NotificationService.this);
                                int shake = PreferenceData.getNotificationShakeState(NotificationService.this);
                                Write(bleUtils.sendMessage(1, 0, 0, 0, state, shake), connectionObservable);
                            }
                        }
                    }else if(pname.equals("com.skype.rover")){
                        //skype通知
                        int state = PreferenceData.getNotificationSkypeState(NotificationService.this);
                        if(state==1){
                            String address = PreferenceData.getAddressValue(NotificationService.this);
                            if (address != null && !address.equals("")) {
                                BleUtils bleUtils = new BleUtils();
                                connectionObservable = BaseActivity.getRxObservable(NotificationService.this);
                                int shake = PreferenceData.getNotificationShakeState(NotificationService.this);
                                Write(bleUtils.sendMessage(1, 0, 0, 0, state, shake), connectionObservable);
                            }
                        }
                    }else if(pname.equals("jp.naver.line.android")){
                        //line通知
                        int state = PreferenceData.getNotificationLineState(NotificationService.this);
                        if(state==1){
                            String address = PreferenceData.getAddressValue(NotificationService.this);
                            if (address != null && !address.equals("")) {
                                BleUtils bleUtils = new BleUtils();
                                connectionObservable = BaseActivity.getRxObservable(NotificationService.this);
                                int shake = PreferenceData.getNotificationShakeState(NotificationService.this);
                                Write(bleUtils.sendMessage(1, 0, 0, 0, state, shake), connectionObservable);
                            }
                        }
                    }else if(pname.equals("com.android.email")){
                        //邮件通知
                        int state = PreferenceData.getNotificationMailState(NotificationService.this);
                        if(state==1){
                            String address = PreferenceData.getAddressValue(NotificationService.this);
                            if (address != null && !address.equals("")) {
                                BleUtils bleUtils = new BleUtils();
                                connectionObservable = BaseActivity.getRxObservable(NotificationService.this);
                                int shake = PreferenceData.getNotificationShakeState(NotificationService.this);
                                Write(bleUtils.sendMessage(1, 0, 0, state, 0, shake), connectionObservable);
                            }
                        }
                    }
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
