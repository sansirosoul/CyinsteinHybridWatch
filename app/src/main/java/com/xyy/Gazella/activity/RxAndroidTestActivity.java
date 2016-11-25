package com.xyy.Gazella.activity;

import android.Manifest;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.polidea.rxandroidble.RxBleClient;
import com.polidea.rxandroidble.RxBleScanResult;
import com.polidea.rxandroidble.exceptions.BleScanException;
import com.xyy.Gazella.adapter.DeviceListAdapter;
import com.ysp.newband.GazelleApplication;
import com.ysp.smartwatch.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;

import static com.ysp.smartwatch.R.id.scan_toggle_btn;

public class RxAndroidTestActivity extends AppCompatActivity {

    private  static  String TAG = RxAndroidTestActivity.class.getName();

    private static final int REQUEST_FINE_LOCATION = 0;
    @BindView(scan_toggle_btn)
    Button scanToggleBtn;
    @BindView(R.id.scan_results)
    ListView scanResults;
    private RxBleClient rxBleClient;
    private Subscription subscription;
    private DeviceListAdapter adapter;
    private List<BluetoothDevice> deviceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_android_test);
        ButterKnife.bind(this);
        rxBleClient = GazelleApplication.getRxBleClient(RxAndroidTestActivity.this);
        MayRequestLocation();
        deviceList = new ArrayList<BluetoothDevice>();
        adapter = new DeviceListAdapter(RxAndroidTestActivity.this, deviceList);
//        scanResults.setAdapter(adapter);


        Observable<DeviceListAdapter> observable = Observable.create(new Observable.OnSubscribe<DeviceListAdapter>() {
            @Override
            public void call(Subscriber<? super DeviceListAdapter> subscriber) {

                subscriber.onNext(adapter);
                subscriber.onCompleted();
            }
        });
        Subscriber<DeviceListAdapter> subscriber = new Subscriber<DeviceListAdapter>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(DeviceListAdapter scanResultsAdapter) {
                scanResults.setAdapter(adapter);
                scanResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        final Intent intent = new Intent(RxAndroidTestActivity.this, RxAndroidTestActivityDeviceActivity.class);
                        intent.putExtra("EXTRA_NAME",deviceList.get(i).getName());
                        intent.putExtra("EXTRA_MAC_ADDRESS", deviceList.get(i).getAddress());
                        startActivity(intent);
                        if (isScanning()) {
                            subscription.unsubscribe();
                        }
                    }
                });
            }
        };
        observable.subscribe(subscriber);
    }

    /**
     * android 6.0必须动态申请权限
     */
    private void MayRequestLocation() {
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(RxAndroidTestActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION);
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                //判断是否需要 向用户解释，为什么要申请该权限
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    // Toast.makeText(context,R.string.ble_need, 1).show();
                }
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_FINE_LOCATION);
            }
        }
    }


    @OnClick({scan_toggle_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case scan_toggle_btn:
                if(isScanning()){
                    subscription.unsubscribe();
                }else {
                    subscription = rxBleClient
                            .scanBleDevices()
                            .observeOn(AndroidSchedulers.mainThread())
                            .doOnUnsubscribe(new Action0() {
                                @Override
                                public void call() {
                                    // 清空缓存,释放内存
                                    subscription = null;
                                    adapter.clearScanResults();
                                }
                            })
                            .subscribe(new Action1<RxBleScanResult>() {
                                @Override
                                public void call(RxBleScanResult rxBleScanResult) {
                                    // has device return
                                    adapter.addScanResult(rxBleScanResult);
                                }
                            }, new Action1<Throwable>() {
                                @Override
                                public void call(Throwable throwable) {
                                    // handle error
                                    if (throwable instanceof BleScanException) {

                                    }
                                }
                            });
                }
                break;
        }
    }

    private boolean isScanning() {
        return subscription != null;
    }
}
