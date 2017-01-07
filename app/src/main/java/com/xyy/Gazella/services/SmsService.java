package com.xyy.Gazella.services;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Process;

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

import static com.xyy.Gazella.activity.HealthyActivity.connectionObservable;

/**
 * Created by Administrator on 2017/1/5.
 */

//短信监听服务
public class SmsService extends Service {
    public static final String TAG = "SmsService";
    public final static String WriteUUID = "6e400002-b5a3-f393-e0a9-e50e24dcca9e";
    private Uri SMS_INBOX = Uri.parse("content://sms/");
    private ContentObserver mObserver;

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.e("SmsService is starting...");
        addSMSObserver();
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 101:
                    int state = PreferenceData.getNotificationMessageState(SmsService.this);
                    if (state == 1) {
                        String address = PreferenceData.getAddressValue(SmsService.this);
                        if (address != null && !address.equals("")) {
                            BleUtils bleUtils = new BleUtils();
                            connectionObservable = BaseActivity.getRxObservable(SmsService.this);
                            int shake = PreferenceData.getNotificationShakeState(SmsService.this);
                            Write(bleUtils.sendMessage(1, 0, state, 0, 0, shake), connectionObservable);
                        }
                    }
                    break;
            }
        }
    };

    public void addSMSObserver() {
        ContentResolver resolver = getContentResolver();
        mObserver = new SmsObserver(resolver,mHandler);
        resolver.registerContentObserver(SMS_INBOX, true, mObserver);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getContentResolver().unregisterContentObserver(mObserver);
        Process.killProcess(Process.myPid());
        System.exit(0);
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
    }
}
