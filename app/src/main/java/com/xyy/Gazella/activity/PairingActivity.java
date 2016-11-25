package com.xyy.Gazella.activity;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.polidea.rxandroidble.RxBleClient;
import com.xyy.Gazella.adapter.DeviceListAdapter;
import com.xyy.Gazella.services.BluetoothService;
import com.xyy.Gazella.utils.CheckUpdateDialog2;
import com.xyy.Gazella.utils.LoadingDialog;
import com.xyy.Gazella.utils.PairFailedDialog;
import com.xyy.Gazella.view.AnalogClock;
import com.ysp.newband.BaseActivity;
import com.ysp.newband.GazelleApplication;
import com.ysp.smartwatch.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscription;

/**
 * Created by Administrator on 2016/10/12.
 */

public class PairingActivity extends BaseActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
    @BindView(R.id.clock)
    AnalogClock clock;
    private ListView listView;
    private Button skip;
    private DeviceListAdapter deviceListAdapter;
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothLeScanner bluetoothLeScanner;
    private List<BluetoothDevice> devices = new ArrayList<>();
    private static final int REQUEST_ENABLE_BT = 1;
    private RelativeLayout searchLayout, bgLayout;
    private LinearLayout pairingLayout;
    private Context context;
    private LoadingDialog loadingDialog;
    private PairFailedDialog pairFailedDialog;
    private BluetoothDevice device;
    private int count;
    private CheckUpdateDialog2 myDialog;
    private boolean isRun=true;
    private RxBleClient rxBleClient;
    private Subscription scanSubscription,connectionSubscription;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.pairing_activity);
        ButterKnife.bind(this);

        initView();
        initBluetooth();
        rxBleClient = GazelleApplication.getRxBleClient(this);
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
                            searchLayout.setVisibility(View.GONE);
                            pairingLayout.setVisibility(View.VISIBLE);
                            bgLayout.setBackgroundResource(R.drawable.page3_background);
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
//                scanDevices();
                bluetoothAdapter.startLeScan(leScanCallback);
            }
        } else {
            devices.clear();
//           scanDevices();
            bluetoothAdapter.startLeScan(leScanCallback);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_FINE_LOCATION:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // The requested permission is granted.

                    devices.clear();
//                   scanDevices();
                    bluetoothAdapter.startLeScan(leScanCallback);
                } else {
                    // The user disallowed the requested permission.
                    mayRequestLocation();
                }
                break;

        }
    }

    private void scanDevices(){
        scanSubscription = rxBleClient.scanBleDevices()
                .subscribe(
                        rxBleScanResult -> {
                            // Process scan result here.
                            BluetoothDevice bluetoothDevice = rxBleScanResult.getBleDevice().getBluetoothDevice();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (bluetoothDevice.getName() != null ) {
                                        if (!devices.contains(bluetoothDevice)) {
                                            searchLayout.setVisibility(View.GONE);
                                            pairingLayout.setVisibility(View.VISIBLE);
                                            bgLayout.setBackgroundResource(R.drawable.page3_background);
                                            devices.add(bluetoothDevice);
                                            deviceListAdapter.notifyDataSetChanged();
                                        }
                                    }
                                }
                            });
                        },
                        throwable -> {
                            // Handle an error here.
                        }
                );
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 为了确保设备上蓝牙能使用, 如果当前蓝牙设备没启用,弹出对话框向用户要求授予权限来启用
        if (!bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

        searchLayout.setVisibility(View.VISIBLE);
        pairingLayout.setVisibility(View.GONE);
        bgLayout.setBackgroundResource(R.drawable.page2_bg);

        mayRequestLocation();

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (bluetoothAdapter != null) {
//            if(bluetoothLeScanner!=null){
//                bluetoothLeScanner.stopScan(scanCallback);
//            }
            bluetoothAdapter.stopLeScan(leScanCallback);
        }
//        scanSubscription.unsubscribe();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ENABLE_BT && resultCode == Activity.RESULT_CANCELED) {

            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initView() {
        context = this;
        listView = (ListView) findViewById(R.id.listview);
        skip = (Button) findViewById(R.id.skip);

        deviceListAdapter = new DeviceListAdapter(this, devices);
        listView.setAdapter(deviceListAdapter);

        listView.setOnItemClickListener(this);
        skip.setOnClickListener(this);

        searchLayout = (RelativeLayout) findViewById(R.id.search_layout);
        pairingLayout = (LinearLayout) findViewById(R.id.pairing_layout);
        bgLayout = (RelativeLayout) findViewById(R.id.bg_layout);

        loadingDialog = new LoadingDialog(context);
        pairFailedDialog = new PairFailedDialog(context);

        clock.setDialDrawable(R.drawable.page2_biaopan);
        clock.setTimeValue(2, 0);
        mHandler.post(runnable);

    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (isRun) {
                count++;
                mHandler.sendEmptyMessage(1001);
                mHandler.postDelayed(this, 50);
            }
        }
    };

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        loadingDialog.show();
        GazelleApplication.mBluetoothService.disconnect();
        GazelleApplication.mBluetoothService.close();
        if (GazelleApplication.mBluetoothService.initialize()) {
            device = devices.get(i);
            GazelleApplication.mBluetoothService.connect(devices.get(i).getAddress());
            GazelleApplication.mBluetoothService.setActivityHandler(mHandler);
        }
        if (bluetoothAdapter != null) {
//            if(bluetoothLeScanner!=null){
//                bluetoothLeScanner.stopScan(scanCallback);
//            }
            bluetoothAdapter.stopLeScan(leScanCallback);
        }

//        scanSubscription.unsubscribe();
//        device = devices.get(i);
//        RxBleDevice rxBleDevice = rxBleClient.getBleDevice(device.getAddress());
//        connectionSubscription = rxBleDevice.establishConnection(context, false) // <-- autoConnect flag
//                .subscribe(
//                        rxBleConnection -> {
//                            // All GATT operations are done through the rxBleConnection.
//                            loadingDialog.dismiss();
//                            connectionSubscription.unsubscribe();
//                            GazelleApplication.deviceAddress=device.getAddress();
//                            Intent intent = new Intent(context, BleTest.class);
//                            startActivity(intent);
//                            PairingActivity.this.finish();
//                            overridePendingTransitionEnter(PairingActivity.this);
//                        },
//                        throwable -> {
//                            // Handle an error here.
//                        }
//                );
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case BluetoothService.STATE_CONNECTED:
                    loadingDialog.dismiss();
                    GazelleApplication.isBleConnected=true;
                    GazelleApplication.deviceAddress=device.getAddress();
                    GazelleApplication.deviceName=device.getName();
                    Intent intent = new Intent(context, BleTest.class);
                    startActivity(intent);
                    PairingActivity.this.finish();
                    overridePendingTransitionEnter(PairingActivity.this);
                    break;
                case BluetoothService.STATE_DISCONNECTED:
                    if(GazelleApplication.deviceAddress==null){
                        loadingDialog.dismiss();
                        pairFailedDialog.show();
                    }
                    break;
                case 1001:
                    clock.setTimeValue(2, count);
                    if (count == 180 && devices.size() == 0) {
                        bluetoothAdapter.stopLeScan(leScanCallback);
                        isRun=false;
                        myDialog = new CheckUpdateDialog2(PairingActivity.this);
                        myDialog.show();
                        myDialog.setTvContext("搜索超时");
                        myDialog.setCancel("再次连接");
                        myDialog.setConfirm("跳过连接");
                        myDialog.setBtnlListener(new CheckUpdateDialog2.setBtnlListener() {
                            @Override
                            public void onCancelListener() {
                                myDialog.dismiss();
                                isRun=true;
                                count=0;
                                bluetoothAdapter.startLeScan(leScanCallback);
//                                scanDevices();
                                mHandler.post(runnable);
                            }
                            @Override
                            public void onConfirm() {
                                Intent intent = new Intent(context, PersonActivity.class);
                                startActivity(intent);
                                PairingActivity.this.finish();
                                overridePendingTransitionEnter(PairingActivity.this);
                                myDialog.dismiss();
                            }
                        });
                    }
                    break;
            }
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.skip:
                if (bluetoothAdapter != null) {
                    bluetoothAdapter.stopLeScan(leScanCallback);
                }
//                scanSubscription.unsubscribe();
                Intent intent = new Intent(context, PersonActivity.class);
                startActivity(intent);
                PairingActivity.this.finish();
                overridePendingTransitionEnter(PairingActivity.this);
                break;
            default:
                break;
        }
    }
}
