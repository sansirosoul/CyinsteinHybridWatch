package com.xyy.Gazella.utils;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.polidea.rxandroidble.RxBleClient;
import com.xyy.Gazella.adapter.ChangeWatchListAdapter;
import com.xyy.Gazella.services.BluetoothService;
import com.ysp.hybridtwatch.R;
import com.ysp.newband.BaseActivity;
import com.ysp.newband.GazelleApplication;
import com.ysp.newband.PreferenceData;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;

/**
 * Created by Administrator on 2016/11/2.
 */

public class ChangeWatchList extends BaseActivity {
    private ListView listView;
    private Button cancel;
    private Context context;
    private ChangeWatchListAdapter deviceListAdapter;
    private BluetoothAdapter bluetoothAdapter;
    private List<BluetoothDevice> devices = new ArrayList<>();
    private static final int REQUEST_ENABLE_BT = 1;
    private LoadingDialog loadingDialog;
    private PairFailedDialog pairFailedDialog;
    private BluetoothDevice device;
    private RxBleClient rxBleClient;
    private Subscription scanSubscription;
    private ProgressBar loading;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.change_watch_list);
        rxBleClient = GazelleApplication.getRxBleClient(this);
        initView();
        initBluetooth();
    }

    private void initView() {
        setFinishOnTouchOutside(false);
        context = this;
        listView = (ListView) findViewById(R.id.listview);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                loadingDialog.show();
                scanSubscription.unsubscribe();
//                GazelleApplication.mBluetoothService.close();
//                if (GazelleApplication.mBluetoothService.initialize()) {
//                    device = devices.get(i);
//                    GazelleApplication.mBluetoothService.connect(devices.get(i).getAddress());
//                    GazelleApplication.mBluetoothService.setActivityHandler(mHandler);
//                }
//                if (bluetoothAdapter != null) {
//                    bluetoothAdapter.stopLeScan(leScanCallback);
//                }

                PreferenceData.setAddressValue(context, devices.get(i).getAddress());
                cleanObservable();
                finish();
            }
        });

        loading = (ProgressBar) findViewById(R.id.loading);

        deviceListAdapter = new ChangeWatchListAdapter(context, devices);
        listView.setAdapter(deviceListAdapter);

        cancel = (Button) findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (bluetoothAdapter != null) {
//                    bluetoothAdapter.stopLeScan(leScanCallback);
//                }
                scanSubscription.unsubscribe();
                finish();
            }
        });

        loadingDialog = new LoadingDialog(context);
        pairFailedDialog = new PairFailedDialog(context);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
        mayRequestLocation();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        if (bluetoothAdapter != null) {
////            if(bluetoothLeScanner!=null){
////                bluetoothLeScanner.stopScan(scanCallback);
////            }
//            bluetoothAdapter.stopLeScan(leScanCallback);
//    }
        scanSubscription.unsubscribe();
    }

    private void initBluetooth() {
        // 检查当前手机是否支持ble 蓝牙,如果不支持退出程序
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, "不支持蓝牙", Toast.LENGTH_SHORT).show();
            finish();
        }

        // 初始化 Bluetooth adapter, 通过蓝牙管理器得到一个参考蓝牙适配器(API必须在以上android4.3或以上和版本)
        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter = bluetoothManager.getAdapter();

        // 检查设备上是否支持蓝牙
        if (bluetoothAdapter == null) {
            Toast.makeText(this, "不支持蓝牙", Toast.LENGTH_SHORT).show();
            // finish();
            return;
        }
    }

    BluetoothAdapter.LeScanCallback leScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(final BluetoothDevice bluetoothDevice, int i, byte[] bytes) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (bluetoothDevice.getName() != null ) {
                        if (!devices.contains(bluetoothDevice)) {
                            devices.add(bluetoothDevice);
                            deviceListAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }
    };

    //sdk6.0以上获取蓝牙权限
    private static final int REQUEST_FINE_LOCATION = 0;

    private void mayRequestLocation() {
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION);
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                Log.d("===========", "没有蓝牙权限");
                //请求蓝牙权限
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_FINE_LOCATION);
                return;
            } else {
                Log.d("===========", "有蓝牙权限");
                devices.clear();
                scanDevices();
//                bluetoothAdapter.startLeScan(leScanCallback);
            }
        } else {
            devices.clear();
            scanDevices();
//            bluetoothAdapter.startLeScan(leScanCallback);
        }
    }

    private void scanDevices() {
        scanSubscription = rxBleClient.scanBleDevices()
                .subscribe(
                        rxBleScanResult -> {
                            // Process scan result here.
                            BluetoothDevice bluetoothDevice = rxBleScanResult.getBleDevice().getBluetoothDevice();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (bluetoothDevice.getName() != null) {
                                        if (!devices.contains(bluetoothDevice)) {
                                            loading.setVisibility(View.GONE);
                                            devices.add(bluetoothDevice);
                                            deviceListAdapter.notifyDataSetChanged();
                                        }
                                    }
                                }
                            });
                        },
                        throwable -> {
                            // Handle an error here.
                            Log.d("==========", "Scan error :" + throwable);
                        }
                );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_FINE_LOCATION:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // The requested permission is granted.

                    scanDevices();
                    devices.clear();
//                    bluetoothAdapter.startLeScan(leScanCallback);
                } else {
                    // The user disallowed the requested permission.

                }
                break;

        }
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case BluetoothService.STATE_CONNECTED:
                    loadingDialog.dismiss();
                    GazelleApplication.deviceAddress = device.getAddress();
                    GazelleApplication.deviceName = device.getName();
                    PreferenceData.setAddressValue(context, device.getAddress());
                    finish();
                    break;
                case BluetoothService.STATE_DISCONNECTED:
                    pairFailedDialog.show();
                    break;
                default:
                    break;
            }
        }
    };

}
