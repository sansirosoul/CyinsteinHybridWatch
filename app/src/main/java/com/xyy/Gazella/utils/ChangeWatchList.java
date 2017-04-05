package com.xyy.Gazella.utils;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.vise.baseble.ViseBluetooth;
import com.vise.baseble.callback.IConnectCallback;
import com.vise.baseble.callback.scan.PeriodScanCallback;
import com.vise.baseble.exception.BleException;
import com.vise.baseble.model.BluetoothLeDevice;
import com.xyy.Gazella.activity.SettingActivity;
import com.xyy.Gazella.adapter.ChangeWatchListAdapter;
import com.ysp.hybridtwatch.R;
import com.ysp.newband.BaseActivity;
import com.ysp.newband.GazelleApplication;
import com.ysp.newband.PreferenceData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/2.
 */

public class ChangeWatchList extends BaseActivity {
    private static String TAG = ChangeWatchList.class.getName();
    private ListView listView;
    private Button cancel;
    private Context context;
    private ChangeWatchListAdapter deviceListAdapter;
    private BluetoothAdapter bluetoothAdapter;
    private List<BluetoothDevice> devices = new ArrayList<>();
    private List<String> deviceTypes = new ArrayList<>();
    private static final int REQUEST_ENABLE_BT = 1;
    private LoadingDialog loadingDialog;
    private PairFailedDialog pairFailedDialog;
    private ProgressBar loading;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.change_watch_list);
        initView();
        initBluetooth();
    }

    @Override
    protected void onReadReturn(byte[] bytes) {
        super.onReadReturn(bytes);
    }

    private void initView() {
        setFinishOnTouchOutside(false);
        context = this;
        listView = (ListView) findViewById(R.id.listview);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ViseBluetooth.getInstance().stopLeScan(periodScanCallback);
                ViseBluetooth.getInstance().disconnect();
                ViseBluetooth.getInstance().close();
                ViseBluetooth.getInstance().clear();
                loadingDialog.show();
                ViseBluetooth.getInstance().connect(devices.get(i), false, new IConnectCallback() {
                    @Override
                    public void onConnectSuccess(BluetoothGatt gatt, int status) {
                        mBluetoothGatt = gatt;
                        loadingDialog.dismiss();
                        GazelleApplication.isBleConnected = true;
                        PreferenceData.setDeviceType(context,deviceTypes.get(i));
                        PreferenceData.setDeviceName(context,devices.get(i).getName());
                        PreferenceData.setAddressValue(context, devices.get(i).getAddress());
                        Intent intent = new Intent(context, SettingActivity.class);
                        startActivity(intent);
                        finish();
                        cleanObservable();
                    }

                    @Override
                    public void onConnectFailure(BleException exception) {
                        loadingDialog.dismiss();
                        if (pairFailedDialog != null)
                            pairFailedDialog.show();
                    }

                    @Override
                    public void onDisconnect() {
                        GazelleApplication.isBleConnected = false;
                    }
                });
            }
        });

        loading = (ProgressBar) findViewById(R.id.loading);

        deviceListAdapter = new ChangeWatchListAdapter(context, devices);
        listView.setAdapter(deviceListAdapter);

        cancel = (Button) findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViseBluetooth.getInstance().stopLeScan(periodScanCallback);
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
        ViseBluetooth.getInstance().stopLeScan(periodScanCallback);
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

    private PeriodScanCallback periodScanCallback = new PeriodScanCallback() {
        @Override
        public void scanTimeout() {

        }

        @Override
        public void onDeviceFound(BluetoothLeDevice bluetoothLeDevice) {
            {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        {
                            byte[] bytes = bluetoothLeDevice.getScanRecord();
                            BluetoothDevice bluetoothDevice = bluetoothLeDevice.getDevice();
                            if (bluetoothLeDevice.getName() != null) {
                                String scanRecord = new String(bytes);
                                if (scanRecord.contains("CT") || scanRecord.contains("EM")) {
                                    if (!devices.contains(bluetoothDevice)) {
                                        byte[] bytes1 = new byte[5];
                                        for (int i = 0; i < bytes1.length; i++) {
                                            bytes1[i] = bytes[i+11];
                                        }
                                        loading.setVisibility(View.GONE);
                                        devices.add(bluetoothDevice);
                                        deviceTypes.add(new String(bytes1));
                                        deviceListAdapter.notifyDataSetChanged();
                                    }
                                }
                            }
                        }
                    }
                });
            }
        }
    };

    private void scanDevices() {
        ViseBluetooth.getInstance().setScanTimeout(-1).startScan(periodScanCallback);
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
}
