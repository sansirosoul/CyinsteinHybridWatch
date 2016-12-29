package com.ysp.newband;


import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.bugtags.library.Bugtags;
import com.exchange.android.engine.ExchangeProxy;
import com.exchange.android.engine.Uoi;
import com.exchange.android.engine.Uoo;
import com.orhanobut.logger.Logger;
import com.polidea.rxandroidble.RxBleConnection;
import com.polidea.rxandroidble.RxBleDevice;
import com.polidea.rxandroidble.utils.ConnectionSharingAdapter;
import com.xyy.Gazella.exchange.ExangeErrorHandler;
import com.xyy.Gazella.utils.CommonDialog;
import com.xyy.Gazella.utils.HexString;
import com.ysp.hybridtwatch.R;

import java.util.UUID;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subjects.PublishSubject;


public class BaseActivity extends FragmentActivity {


    private static final String TAG = BaseActivity.class.getName();

    public static Context mContext;
    public final static String ReadUUID = "6e400003-b5a3-f393-e0a9-e50e24dcca9e";
    public final static String WriteUUID = "6e400002-b5a3-f393-e0a9-e50e24dcca9e";
    private static Observable<RxBleConnection> connectionObservable;
    private PublishSubject<Void> disconnectTriggerSubject = PublishSubject.create();
    private RxBleDevice bleDevicme;


    public static Observable<RxBleConnection> getRxObservable(Context context) {

        String address = PreferenceData.getAddressValue(context);
        if (address != null && !address.equals("")) {
            RxBleDevice bleDevicme = GazelleApplication.getRxBleClient(context).getBleDevice(address);
            if (connectionObservable == null) {
                connectionObservable = bleDevicme
                        .establishConnection(context, false)
                        .compose(new ConnectionSharingAdapter());
            }
        }
        return connectionObservable;
    }

    public static void cleanObservable() {
        connectionObservable = null;
    }

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
        String address = PreferenceData.getAddressValue(this);
        if (address != null && !address.equals(""))
            bleDevicme = GazelleApplication.getRxBleClient(this).getBleDevice(address);
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
        if (connectionObservable != null) {
            WiterCharacteristic(HexString.bytesToHex(bytes), connectionObservable).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<byte[]>() {
                        @Override
                        public void call(byte[] bytes) {
                            Logger.t(TAG).e("写入数据  >>>>>>  " + HexString.bytesToHex(bytes));
                            onWriteReturn(bytes);
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            Logger.t(TAG).e("写入数据失败  >>>>>>   " + throwable.toString());
                        }
                    });
        } else {
            dialog = new CommonDialog(this);
            dialog.show();
            if (dialog.isShowing()) {
                dialog.setTvContext("请检查手表蓝牙是否开启");
                dialog.setButOk(View.VISIBLE);
                dialog.onButOKListener(new CommonDialog.onButOKListener() {
                    @Override
                    public void onButOKListener() {
                        dialog.dismiss();
                    }
                });
            }
        }
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 101:
                    String throwable = (String) msg.obj;
                    BluetoothAdapter blueadapter = BluetoothAdapter.getDefaultAdapter();
                    if (!blueadapter.isEnabled()) {
                        if (dialog.isShowing()) {
                            dialog.setTvContext("是否开启手机蓝牙");
                            dialog.setButOk(View.VISIBLE);
                            dialog.onButOKListener(new CommonDialog.onButOKListener() {
                                @Override
                                public void onButOKListener() {
                                    startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), 10010);
                                }
                            });
                        }
                    } else {
                        if (throwable.contains("status=133") || throwable.contains("status=129")) {
                            dialog.setTvContext("蓝牙连接失败是否继续连接");
                            dialog.setButOk(View.VISIBLE);
                            dialog.onButOKListener(new CommonDialog.onButOKListener() {
                                @Override
                                public void onButOKListener() {
                                    dialog.dismiss();
                                    Notify(connectionObservable);
                                }
                            });
                        } else {
                            dialog.setTvContext("请检查手表蓝牙是否开启");
                            dialog.setButOk(View.VISIBLE);
                            dialog.onButOKListener(new CommonDialog.onButOKListener() {
                                @Override
                                public void onButOKListener() {
                                    dialog.dismiss();
                                }
                            });
                        }
                    }
                    break;
            }
        }
    };

    private CommonDialog dialog;

    protected void Notify(Observable<RxBleConnection> connectionObservable) {
        dialog = new CommonDialog(this);
        dialog.show();
        if (connectionObservable != null) {
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
                    if (dialog.isShowing())
                        dialog.dismiss();
                    onNotifyReturn(0, null);
                }
            }).flatMap(new Func1<Observable<byte[]>, Observable<byte[]>>() {
                @Override
                public Observable<byte[]> call(Observable<byte[]> notificationObservable) {
                    return notificationObservable;
                }
            }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<byte[]>() {
                @Override
                public void call(byte[] bytes) {
                    Logger.t(TAG).e("接收数据  >>>>>>  " + HexString.bytesToHex(bytes) + "\n" + ">>>>>>>>" + new String(bytes));
                    onReadReturn(bytes);
                }
            }, new Action1<Throwable>() {
                @Override
                public void call(Throwable throwable) {
                    Logger.t(TAG).e("接收数据失败 >>>>>>  " + throwable.toString());
                    onNotifyReturn(1, throwable.toString());
                    HandleThrowableException(throwable.toString());
                }
            });
        } else {
            if (!dialog.isShowing()) dialog.show();
            dialog.setTvContext("没有连接到手表设备");
            dialog.setButOk(View.VISIBLE);
            dialog.onButOKListener(new CommonDialog.onButOKListener() {
                @Override
                public void onButOKListener() {
                    dialog.dismiss();
                }
            });
        }
    }


    protected void HandleThrowableException(String throwable) {
        BluetoothAdapter blueadapter = BluetoothAdapter.getDefaultAdapter();
        if (!blueadapter.isEnabled()) {
            if (dialog == null) dialog = new CommonDialog(this);
            if (dialog.isShowing()) {
                dialog.setTvContext("是否开启手机蓝牙");
                dialog.setButOk(View.VISIBLE);
                dialog.onButOKListener(new CommonDialog.onButOKListener() {
                    @Override
                    public void onButOKListener() {
                        startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), 10010);
                    }
                });
            }
        }
        if (throwable.contains("status=133") || throwable.contains("status=129")) {
            if (dialog == null) dialog = new CommonDialog(this);
            if (!dialog.isShowing()) dialog.show();
            dialog.setTvContext("蓝牙连接失败是否继续连接");
            dialog.setButOk(View.VISIBLE);
            dialog.onButOKListener(new CommonDialog.onButOKListener() {
                @Override
                public void onButOKListener() {
                    dialog.dismiss();
                    Notify(connectionObservable);
                }
            });
        } else {
            if (dialog == null) dialog = new CommonDialog(this);
            if (!dialog.isShowing()) dialog.show();
            dialog.setTvContext("请检查手表蓝牙是否开启");
            dialog.setButOk(View.VISIBLE);
            dialog.onButOKListener(new CommonDialog.onButOKListener() {
                @Override
                public void onButOKListener() {
                    dialog.dismiss();
                }
            });
        }
    }

    private void DeviceConnectionStateChanges() {
        String address = PreferenceData.getAddressValue(this);
        if (address != null && !address.equals("")) {
            RxBleDevice bleDevicme = GazelleApplication.getRxBleClient(this).getBleDevice(address);
            bleDevicme.observeConnectionStateChanges()
                    .subscribe(new Action1<RxBleConnection.RxBleConnectionState>() {
                        @Override
                        public void call(RxBleConnection.RxBleConnectionState rxBleConnectionState) {
                            if (bleDevicme.getConnectionState() == RxBleConnection.RxBleConnectionState.CONNECTING
                                    || bleDevicme.getConnectionState() == RxBleConnection.RxBleConnectionState.CONNECTED) {
                                Logger.t(TAG).e("连接 >>>>>>  " + rxBleConnectionState.toString());
                            } else {
                                Logger.t(TAG).e("断开 >>>>>>  " + rxBleConnectionState.toString());
                            }
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                        }
                    });
        }
    }


    protected boolean getConnectionState() {
        if (bleDevicme.getConnectionState() == RxBleConnection.RxBleConnectionState.CONNECTING
                || bleDevicme.getConnectionState() == RxBleConnection.RxBleConnectionState.CONNECTED)
            return true;
        else
            return false;
    }


    protected void onReadReturn(byte[] bytes) {
    }

    protected void onWriteReturn(byte[] bytes) {
    }

    protected void onNotifyReturn(int type, String str) {
    }

    protected void onReadReturnFailed() {
    }

    protected void onConnectionStateChanges() {
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 10010) {
            if (resultCode == Activity.RESULT_OK) {
                if (dialog.isShowing())
                    dialog.dismiss();
                onNotifyReturn(2, null);//  再次发送监听蓝牙
            }
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
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
        triggerDisconnect();
    }


    public void triggerDisconnect() {
        disconnectTriggerSubject.onNext(null);
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

    private Toast result;
    private TextView textView;

    protected void showToatst(Context context, String tvStr) {

        if (result == null) {
            result = new Toast(context);
            LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflate.inflate(R.layout.toast, null);
            textView = (TextView) v.findViewById(R.id.tv_context);
            textView.setText(tvStr);
            result.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            result.setView(v);
        } else
            textView.setText(tvStr);
        result.show();
    }
}
