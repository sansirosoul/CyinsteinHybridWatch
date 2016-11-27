package com.xyy.Gazella.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.polidea.rxandroidble.RxBleConnection;
import com.polidea.rxandroidble.RxBleDevice;
import com.polidea.rxandroidble.RxBleDeviceServices;
import com.polidea.rxandroidble.utils.ConnectionSharingAdapter;
import com.xyy.Gazella.utils.HexString;
import com.ysp.newband.BaseActivity;
import com.ysp.newband.GazelleApplication;
import com.ysp.smartwatch.R;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;


public class RxAndroidTestActivityDeviceActivity extends BaseActivity {

    private static String TAG = RxAndroidTestActivityDeviceActivity.class.getName();
    @BindView(R.id.tv_connect)
    TextView tvConnect;
    @BindView(R.id.tv_in)
    EditText tvIn;
    @BindView(R.id.butt)
    Button butt;
    private String extra_name;
    private String extra_mac_address;


    private Subscription connectionSubscription;
    private RxAndroidAdapterTestActivity adapter;
    private List<RxBleDeviceServices> deviceList;

    private Observable<RxBleConnection> connectionObservable;
    private RxBleDevice bleDevice;

    public final static String ReadUUID = "6e400003-b5a3-f393-e0a9-e50e24dcca9e";
    public final static String WriteUUID = "6e400002-b5a3-f393-e0a9-e50e24dcca9e";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_android_test_device);
        ButterKnife.bind(this);

        extra_name = getIntent().getStringExtra("EXTRA_NAME");
        extra_mac_address = getIntent().getStringExtra("EXTRA_MAC_ADDRESS");

        bleDevice = GazelleApplication.getRxBleClient(this).getBleDevice(extra_mac_address);
        deviceList = new ArrayList<RxBleDeviceServices>();

        bleDevice = GazelleApplication.getRxBleClient(this).getBleDevice(extra_mac_address);
        connectionObservable = bleDevice
                .establishConnection(this, false)
                .doOnUnsubscribe(this::clearSubscription)
                .compose(new ConnectionSharingAdapter());
    }
    private void clearSubscription() {
        connectionSubscription = null;
    }

    @Override
    protected void onReadReturn(int type, byte[] bytes) {
        if(type==GET_SN){

        }
    }

    @OnClick({R.id.tv_in, R.id.butt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_in:
                break;
            case R.id.butt:

                Write(GET_SN,tvIn.getText().toString(),connectionObservable);




//                SomeUtills utils= new SomeUtills();
//                utils.Write2Characteristic(tvIn.getText().toString(),connectionObservable).subscribe(new Action1<byte[]>() {
//                    @Override
//                    public void call(byte[] bytes) {
//                        utils.ReadCharacteristic(connectionObservable).subscribe(new Action1<byte[]>() {
//                            @Override
//                            public void call(byte[] bytes) {
//
//                            }
//                        }, new Action1<Throwable>() {
//                            @Override
//                            public void call(Throwable throwable) {
//
//                            }
//                        });
//                    }
//                }, new Action1<Throwable>() {
//                    @Override
//                    public void call(Throwable throwable) {
//
//                    }
//                });

                connectionObservable.flatMap(rxBleConnection -> rxBleConnection.readCharacteristic(UUID.fromString(ReadUUID))
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnNext(bytes -> {
                            // 返回数据
                            Logger.t(TAG).e("返回数据>>>>>>>>  "+new String(bytes));
                            tvConnect.setText(new String(bytes));
                        }).doOnError(throwable -> {
                            // 返回数据 失败
                            Logger.t(TAG).e("返回数据 失败>>>>>>>>  "+throwable.toString());
                        }).observeOn(AndroidSchedulers.mainThread())
                        .flatMap(bytes -> rxBleConnection.writeCharacteristic(UUID.fromString(WriteUUID), getInputBytes())))
                        .doOnError(throwable -> {
                            // 写入数据 失败
                            Logger.t(TAG).e("写入数据 失败>>>>>>>>  "+throwable.toString());
                        }).subscribe(writeBytes -> {
                            // 写入数据
                            Logger.t(TAG).e("写入数据>>>>>>>>  "+HexString.bytesToHex(writeBytes));
                        });

                break;
        }
    }
    private byte[] getInputBytes() {
        return HexString.hexToBytes(tvIn.getText().toString());
    }
}
