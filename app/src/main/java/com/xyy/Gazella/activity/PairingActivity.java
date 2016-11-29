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

import com.orhanobut.logger.Logger;
import com.polidea.rxandroidble.RxBleClient;
import com.polidea.rxandroidble.RxBleConnection;
import com.polidea.rxandroidble.RxBleDevice;
import com.polidea.rxandroidble.RxBleScanResult;
import com.polidea.rxandroidble.exceptions.BleScanException;
import com.xyy.Gazella.adapter.DeviceListAdapter;
import com.xyy.Gazella.utils.CheckUpdateDialog2;
import com.xyy.Gazella.utils.LoadingDialog;
import com.xyy.Gazella.utils.PairFailedDialog;
import com.xyy.Gazella.view.AnalogClock;
import com.ysp.newband.BaseActivity;
import com.ysp.newband.GazelleApplication;
import com.ysp.newband.PreferenceData;
import com.ysp.smartwatch.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/10/12.
 */

public class PairingActivity extends BaseActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    private static final String TAG = PairingActivity.class.getName();

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

    private boolean isRun = true;
    private RxBleClient rxBleClient;
    private RxBleDevice bleDevice;
    private Subscription subscription;
    private Subscription connectionSubscription;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.pairing_activity);
        ButterKnife.bind(this);
        rxBleClient = GazelleApplication.getRxBleClient(PairingActivity.this);
        initBluetooth();    // 检查当前手机是否支持ble 蓝牙,如果不支持退出程序
        initView();
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
//                scanDevices();
                devices.clear();
                StatrScanBleDeviceTime();
                ScanBleDevice();
            }
        } else {
//           scanDevices();
            devices.clear();
            StatrScanBleDeviceTime();
            ScanBleDevice();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_FINE_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    devices.clear();
                    StatrScanBleDeviceTime();
                    ScanBleDevice();
                } else {
                    // The user disallowed the requested permission.
                    mayRequestLocation();
                }
                break;
        }
    }
//    @SuppressLint("NewApi")
//    ScanCallback scanCallback = new ScanCallback() {
//        @Override
//        public void onScanResult(int callbackType, ScanResult result) {
//            final BluetoothDevice bluetoothDevice = result.getDevice();
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    if(bluetoothDevice.getName() != null&&(bluetoothDevice.getName().equals("Watch")
//                            || bluetoothDevice.getName().equals("Partner")
//                            || bluetoothDevice.getName().equals("Band")
//                            || bluetoothDevice.getName().equals("Felix") || bluetoothDevice
//                            .getName().equals("Nova"))){
//                        Log.d("=====",bluetoothDevice.getAddress());
//                        if(!devices.contains(bluetoothDevice)){
//                            searchLayout.setVisibility(View.GONE);
//                            pairingLayout.setVisibility(View.VISIBLE);
//                            devices.add(bluetoothDevice);
//                            deviceListAdapter.notifyDataSetChanged();
//                        }
//                    }
//
//                }
//            });
//        }
//    };

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

        StatrScanBleDeviceTime();

        clock.setDialDrawable(R.drawable.page2_biaopan);
        clock.setTimeValue(2, 0);
    }

    private void ScanBleDevice() {
        subscription = rxBleClient
                .scanBleDevices()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnUnsubscribe(new Action0() {
                    @Override
                    public void call() {
                        // 清空缓存,释放内存
                        subscription = null;
                        deviceListAdapter.clearScanResults();
                    }
                })
                .subscribe(new Action1<RxBleScanResult>() {
                    @Override
                    public void call(RxBleScanResult rxBleScanResult) {
                        // has device return
                        searchLayout.setVisibility(View.GONE);
                        pairingLayout.setVisibility(View.VISIBLE);
                        bgLayout.setBackgroundResource(R.drawable.page3_background);
                        deviceListAdapter.addScanResult(rxBleScanResult);
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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        loadingDialog.show();
        if (isScanning()) {
            subscription.unsubscribe();
        }
        device = devices.get(i);
        bleDevice = rxBleClient.getBleDevice(device.getAddress());
        connectionSubscription = bleDevice.establishConnection(PairingActivity.this, true)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnUnsubscribe(this::clearSubscription)
                .subscribe(this::onConnectionReceived, this::onConnectionFailure);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.skip:
                if (bluetoothAdapter != null) {
                    subscription.unsubscribe();
                }
                Intent intent = new Intent(context, PersonActivity.class);
                startActivity(intent);
                PairingActivity.this.finish();
                overridePendingTransitionEnter(PairingActivity.this);
                break;
            default:
                break;
        }
    }

    private void StatrScanBleDeviceTime() {
        Observable.interval(0, 1, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        this.unsubscribe();
                    }

                    @Override
                    public void onNext(Long aLong) {

                        Logger.t(TAG).e(String.valueOf(aLong));

                        clock.setTimeValue(2, aLong);
                        if (aLong == 180 && devices.size() == 0) {
                            isRun = false;
                            myDialog = new CheckUpdateDialog2(PairingActivity.this);
                            myDialog.show();
                            myDialog.setTvContext("搜索超时");
                            myDialog.setCancel("再次连接");
                            myDialog.setConfirm("跳过连接");
                            myDialog.setBtnlListener(new CheckUpdateDialog2.setBtnlListener() {
                                @Override
                                public void onCancelListener() {
                                    myDialog.dismiss();
                                    isRun = true;

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
                    }
                });
    }

 /***取消连接*/
    private void clearSubscription() {
        connectionSubscription = null;
    }

    /****
     * 配对成功
     */
    private void onConnectionReceived(RxBleConnection connection) {
        loadingDialog.dismiss();
        PreferenceData.setAddressValue(PairingActivity.this,device.getAddress());
        Intent intent = new Intent(context, PersonActivity.class);
        startActivity(intent);
        PairingActivity.this.finish();
        overridePendingTransitionEnter(PairingActivity.this);
    }

    /****
     * 配对失败
     */
    private void onConnectionFailure(Throwable throwable) {
        loadingDialog.dismiss();
        pairFailedDialog.show();
    }

    /***是否在搜索*/
    private boolean isScanning() {
        return subscription != null;
    }
}
