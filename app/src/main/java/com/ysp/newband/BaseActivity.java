package com.ysp.newband;


import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.WindowManager;

import com.bugtags.library.Bugtags;
import com.exchange.android.engine.ExchangeProxy;
import com.exchange.android.engine.Uoi;
import com.exchange.android.engine.Uoo;
import com.orhanobut.logger.Logger;
import com.polidea.rxandroidble.RxBleConnection;
import com.polidea.rxandroidble.RxBleDevice;
import com.xyy.Gazella.exchange.ExangeErrorHandler;
import com.xyy.Gazella.utils.HexString;
import com.ysp.smartwatch.R;

import java.util.UUID;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;


public class BaseActivity extends FragmentActivity {


    private static final String TAG = BaseActivity.class.getName();

    public static Context mContext;
    public final static String ReadUUID = "6e400003-b5a3-f393-e0a9-e50e24dcca9e";
    public final static String WriteUUID = "6e400002-b5a3-f393-e0a9-e50e24dcca9e";
    private Observable<RxBleConnection> connectionObservable;

    public final int GET_SN = 10001;
    public  boolean  isNotify=false;
    private Subscription connectionSubscription;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        ExchangeProxy.setApplicationDefaultErrorHandle(new ExangeErrorHandler());// 设置报错处理handler
        ExchangeProxy.setProgressModelVisible(false);// 设置弹出框是否显示

        if (Build.VERSION.SDK_INT >= 19) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        mContext = this;
    }
    //连接蓝牙
    protected  void  ConnectionBle(RxBleDevice bleDevice){
        connectionSubscription = bleDevice.establishConnection(this,true)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnUnsubscribe(this::clearSubscription)
                .subscribe(this::onConnectionReceived, this::onConnectionFailure);
    }

    protected void  initBle(RxBleDevice bleDevice){
        if (isConnected(bleDevice)){
            BleStateChangesListener(bleDevice);    //监听蓝牙状态
            //连接
            if(isNotify){
                //可以接收通知
            }else {
                // 注册接收通知
                NotificationObservable();
            }
        }else {
            //断开
            ConnectionBle(bleDevice);
        }
    }

    private  void  NotificationObservable(){
        connectionObservable
                .flatMap(new Func1<RxBleConnection, Observable<Observable<byte[]>>>() {
                    @Override
                    public Observable<Observable<byte[]>> call(RxBleConnection rxBleConnection) {
                        return rxBleConnection.setupNotification(UUID.fromString(ReadUUID));
                    }
                }).doOnNext(new Action1<Observable<byte[]>>() {
            @Override
            public void call(Observable<byte[]> observable) {
                Logger.t(TAG).e("开始接收通知  >>>>>>  ");
                isNotify=true;
            }
        }).flatMap(new Func1<Observable<byte[]>, Observable<byte[]>>() {
            @Override
            public Observable<byte[]> call(Observable<byte[]> notificationObservable) {
                return notificationObservable;
            }
        }).subscribe(new Action1<byte[]>() {
                    @Override
                    public void call(byte[] bytes) {
                        Logger.t(TAG).e("接收数据  >>>>>>  " + HexString.bytesToHex(bytes) + "\n" + ">>>>>>>>" + new String(bytes));
                        onReadReturn(bytes);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.t(TAG).e("接收数据失败 >>>>>>  " + throwable.toString());
                    }
                });
    }
    /**清除连接订阅*/
    private void clearSubscription() {
        connectionSubscription = null;
    }
    /**连接成功*/
    private void onConnectionReceived(RxBleConnection connection) {
        //noinspection ConstantConditions
        Snackbar.make(findViewById(android.R.id.content), "连接成功 ", Snackbar.LENGTH_SHORT).show();
    }
    /**连接失败*/
    private void onConnectionFailure(Throwable throwable) {
        //noinspection ConstantConditions
        Snackbar.make(findViewById(android.R.id.content), "连接失败 : " + throwable, Snackbar.LENGTH_SHORT).show();
    }

    /**写入数据到蓝牙**/
    private void  WriterBleData(String writeString){
        WiterCharacteristic(writeString, connectionObservable).observeOn(AndroidSchedulers.mainThread())
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

    /***监听蓝牙状态变换 */
    protected void BleStateChangesListener(RxBleDevice bleDevice) {
        bleDevice.observeConnectionStateChanges()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<RxBleConnection.RxBleConnectionState>() {
                    @Override
                    public void call(RxBleConnection.RxBleConnectionState rxBleConnectionState) {
                        if (rxBleConnectionState== RxBleConnection.RxBleConnectionState.CONNECTING
                                || rxBleConnectionState== RxBleConnection.RxBleConnectionState.CONNECTED) {
                            //连接
                            Logger.t(TAG).e("蓝牙状态变换>>>>>>>>>  " + rxBleConnectionState.toString());
                            isNotify=true;
                        } else {
                            //断开
                            Logger.t(TAG).e("蓝牙状态变换>>>>>>>>>   " + rxBleConnectionState.toString());
                            isNotify=false;
                        }
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

    private void NotifyCharacteristic() {
         connectionObservable
                .flatMap(new Func1<RxBleConnection, Observable<Observable<byte[]>>>() {
                    @Override
                    public Observable<Observable<byte[]>> call(RxBleConnection rxBleConnection) {
                        return rxBleConnection.setupNotification(UUID.fromString(ReadUUID));
                    }
                }).doOnNext(new Action1<Observable<byte[]>>() {
                    @Override
                    public void call(Observable<byte[]> observable) {
                        Logger.t(TAG).e("开始接收通知  >>>>>>  ");
                         isNotify=true;      //可以接收通知
                    }
                }).flatMap(new Func1<Observable<byte[]>, Observable<byte[]>>() {
                    @Override
                    public Observable<byte[]> call(Observable<byte[]> notificationObservable) {
                        return notificationObservable;
                    }
                });
    }
    /**查看蓝牙连接状态   连接 :true     断开 :false*/
    private boolean isConnected(RxBleDevice bleDevice) {
        if (bleDevice.getConnectionState() == RxBleConnection.RxBleConnectionState.CONNECTING
                || bleDevice.getConnectionState() == RxBleConnection.RxBleConnectionState.CONNECTED) {
            return true;
        } else {
            return false;
        }
    }
    protected void onReadReturn(byte[] bytes) {
    }
    protected void onBleStateChangesListener(String type) {
    }

    protected void onReadReturnFailed() {
    }


    /**
     * 回退键
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            animfinish();
        }
        return super.onKeyDown(keyCode, event);
    }

    public void animfinish() {
        mContext = null;
        finish();
        overridePendingTransitionExit(this);
    }

    /***
     * 进入页面调用  从左到右进入
     *
     * @param at
     */
    public static void overridePendingTransitionEnter(Activity at) {
        at.overridePendingTransition(R.anim.in_from_right, R.anim.out_righttoleft);

    }

    /***
     * 退出页面调用 从右到左退出
     *
     * @param at
     */
    public static void overridePendingTransitionExit(Activity at) {
        at.overridePendingTransition(R.anim.in_lefttoright, R.anim.out_to_left);
    }

    public Handler handler = new Handler() {
        public void handleMessage(Message var1) {
            Uoi var2 = (Uoi) var1.getData().getSerializable("INPUT_DATA");
            Uoo var3 = (Uoo) var1.getData().getSerializable("RETURN_DATA");
            BaseActivity.this.callbackByExchange(var2, var3);
        }
    };

    public BaseActivity() {
    }

    public void callbackByExchange(Uoi var1, Uoo var2) {
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Bugtags.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bugtags.onResume(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        Bugtags.onDispatchTouchEvent(this, event);
        return super.dispatchTouchEvent(event);



    }
}
