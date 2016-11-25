package com.xyy.Gazella.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.polidea.rxandroidble.RxBleConnection;
import com.polidea.rxandroidble.RxBleDevice;
import com.polidea.rxandroidble.RxBleDeviceServices;
import com.ysp.newband.GazelleApplication;
import com.ysp.smartwatch.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;


public class RxAndroidTestActivityDeviceActivity extends AppCompatActivity {

    private static String TAG = RxAndroidTestActivityDeviceActivity.class.getName();
    @BindView(R.id.tv_connect)
    TextView tvConnect;
    @BindView(R.id.list_item)
    ListView listItem;
    private String extra_name;
    private String extra_mac_address;

    private RxBleDevice bleDevice;

    private Subscription connectionSubscription;
    private RxAndroidAdapterTestActivity adapter;
    private List<RxBleDeviceServices> deviceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_android_test_device);
        ButterKnife.bind(this);

        extra_name = getIntent().getStringExtra("EXTRA_NAME");
        extra_mac_address = getIntent().getStringExtra("EXTRA_MAC_ADDRESS");

        bleDevice = GazelleApplication.getRxBleClient(this).getBleDevice(extra_mac_address);
        deviceList = new ArrayList<RxBleDeviceServices>();
        adapter = new RxAndroidAdapterTestActivity(this, deviceList);
        listItem.setAdapter(adapter);
        connect();
    }

    private void connect() {
        bleDevice.establishConnection(this, false)
                .flatMap(new Func1<RxBleConnection, Observable<RxBleDeviceServices>>() {
                    @Override
                    public Observable<RxBleDeviceServices> call(RxBleConnection rxBleConnection) {
                        return rxBleConnection.discoverServices();
                    }
                })
                .first() // Disconnect automatically after discovery
//                .compose(bindUntilEvent(PAUSE))
                .observeOn(AndroidSchedulers.mainThread())
                .doOnUnsubscribe(new Action0() {
                    @Override
                    public void call() {
//                        updateUI();
                    }
                })
                .subscribe(new Action1<RxBleDeviceServices>() {
                    @Override
                    public void call(RxBleDeviceServices rxBleDeviceServices) {
                        tvConnect.setText("Connection OK");
                        deviceList.add(rxBleDeviceServices);
                        adapter.notifyDataSetChanged();

                        Logger.t(TAG).e(String.valueOf(rxBleDeviceServices));
//                        adapter.swapScanResult(rxBleDeviceServices);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        onConnectionFailure(throwable);
                    }
                });


    }


    private void clearSubscription() {
        connectionSubscription = null;
    }

    private void onConnectionReceived(RxBleConnection connection) {
        //noinspection ConstantConditions
        tvConnect.setText("Connection OK");
//        Snackbar.make(findViewById(android.R.id.content), "Connection received", Snackbar.LENGTH_SHORT).show();
    }

    private void onConnectionFailure(Throwable throwable) {
        //noinspection ConstantConditions
        tvConnect.setText("Connection error: " + throwable);
//        Snackbar.make(findViewById(android.R.id.content), "Connection error: " + throwable, Snackbar.LENGTH_SHORT).show();
    }
}
