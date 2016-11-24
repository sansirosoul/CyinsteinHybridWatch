package com.xyy.Gazella.activity;

import android.Manifest;
import android.bluetooth.BluetoothDevice;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.polidea.rxandroidble.RxBleClient;
import com.polidea.rxandroidble.RxBleScanResult;
import com.xyy.Gazella.adapter.DeviceListAdapter;
import com.ysp.newband.GazelleApplication;
import com.ysp.smartwatch.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class RxAndroidTestActivity extends AppCompatActivity {

    private  static  String TAG = RxAndroidTestActivity.class.getName();

    private static final int REQUEST_FINE_LOCATION = 0;
    @BindView(R.id.scan_toggle_btn)
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
        adapter = new DeviceListAdapter(RxAndroidTestActivity.this, deviceList);
        scanResults.setAdapter(adapter);
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


    @OnClick({R.id.scan_toggle_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.scan_toggle_btn:
                if(isScanning()){
                    subscription.unsubscribe();
                }else {
                    subscription=rxBleClient.scanBleDevices()
                            .observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<RxBleScanResult>() {
                                @Override
                                public void call(RxBleScanResult rxBleScanResult) {

//                                    Logger.t(TAG).i(  "BleDevice==="+rxBleScanResult.getBleDevice().toString()+"\n"+
//                                                              "ScanRecord==="+rxBleScanResult.getScanRecord()+"\n"+
//                                                              "RSSi==="+rxBleScanResult.getRssi()+"\n"+"" +
//                                                              "BluetoohDevice==="+rxBleScanResult.getBleDevice().getBluetoothDevice()+"\n"+
//                                                              "ConnectionState==="+rxBleScanResult.getBleDevice().getConnectionState()+"\n");

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
