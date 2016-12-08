package com.ysp.newband;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.WindowManager;

import com.orhanobut.logger.Logger;
import com.polidea.rxandroidble.RxBleConnection;
import com.polidea.rxandroidble.RxBleDevice;
import com.polidea.rxandroidble.utils.ConnectionSharingAdapter;
import com.xyy.Gazella.utils.CommonDialog;
import com.xyy.Gazella.utils.HexString;

import java.util.UUID;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subjects.PublishSubject;

/**
 * Created by Administrator on 2016/10/22.
 */

public class BaseFragment extends Fragment {

    private static final String TAG = BaseFragment.class.getName();
    public final static String ReadUUID = "6e400003-b5a3-f393-e0a9-e50e24dcca9e";
    public final static String WriteUUID = "6e400002-b5a3-f393-e0a9-e50e24dcca9e";

    private RxBleDevice bleDevice;
    private static Observable<RxBleConnection> connectionObservable;
    private PublishSubject<Void> disconnectTriggerSubject = PublishSubject.create();

    public static Observable<RxBleConnection> getRxObservable(Context context) {
        String address = PreferenceData.getAddressValue(context);
        if (address != null && !address.equals("")) {
            RxBleDevice bleDevicme = GazelleApplication.getRxBleClient(context).getBleDevice(address);
            if(connectionObservable==null) {
                connectionObservable = bleDevicme
                        .establishConnection(context, false)
                        .compose(new ConnectionSharingAdapter());
            }
        }
        return connectionObservable;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(Build.VERSION.SDK_INT >= 19){
            //透明状态栏
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
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

    private CommonDialog dialog;

    protected void Write( byte[] bytes, Observable<RxBleConnection> connectionObservable) {
        if (connectionObservable!=null) {
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
                        if (!dialog.isShowing()) {
                            dialog= new CommonDialog(getActivity());
                            dialog.show();
                            dialog.setTvContext("操作失败,请再操作一次");
                            dialog.setButOk(View.VISIBLE);
                            dialog.onButOKListener(new CommonDialog.onButOKListener() {
                                @Override
                                public void onButOKListener() {
                                    dialog.dismiss();
                                }
                            });
                        }
                        Logger.t(TAG).e("写入数据失败  >>>>>>   " + throwable.toString());
                    }
                });}else {
            if (!dialog.isShowing()) {
                dialog= new CommonDialog(getActivity());
                dialog.show();
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

    protected void Notify(int type,Observable<RxBleConnection> connectionObservable){
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
                onReadReturn(type, bytes);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Logger.t(TAG).e("接收数据失败 >>>>>>  " + throwable.toString());
            }
        });
    }

    protected void onReadReturn(int type, byte[] bytes) {
    }

    protected void onWriteReturn( byte[] bytes) {
    }

    protected void onReadReturnFailed() {
    }
}
