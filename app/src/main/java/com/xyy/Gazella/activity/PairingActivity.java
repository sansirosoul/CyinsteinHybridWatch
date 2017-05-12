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
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.vise.baseble.ViseBluetooth;
import com.vise.baseble.callback.scan.PeriodScanCallback;
import com.vise.baseble.model.BluetoothLeDevice;
import com.xyy.Gazella.adapter.DeviceListAdapter;
import com.xyy.Gazella.services.BluetoothService;
import com.xyy.Gazella.utils.BleUtils;
import com.xyy.Gazella.utils.CheckUpdateDialog2;
import com.xyy.Gazella.utils.LoadingDialog;
import com.xyy.Gazella.utils.PairFailedDialog;
import com.xyy.Gazella.view.AnalogClock2;
import com.xyy.model.ParsedAd;
import com.ysp.hybridtwatch.R;
import com.ysp.newband.BaseActivity;
import com.ysp.newband.GazelleApplication;
import com.ysp.newband.PreferenceData;
import com.ysp.newband.WacthSeries;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/12.
 */

public class PairingActivity extends BaseActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
    @BindView(R.id.clock)
    AnalogClock2 clock;
    private ListView listView;
    private Button skip;
    private DeviceListAdapter deviceListAdapter;
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothLeScanner bluetoothLeScanner;
    private List<BluetoothDevice> devices = new ArrayList<>();
    private List<String> deviceTypes = new ArrayList<>();
    private BluetoothDevice mDevice;
    private String mDeviceType;
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


    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.pairing_activity);
        ButterKnife.bind(this);
        GazelleApplication.mBluetoothService.setActivityHandler2(mActivityHandler);
        initView();
        initBluetooth();
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
            }
        } else {
            devices.clear();
            scanDevices();
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
                    scanDevices();
                } else {
                    // The user disallowed the requested permission.
                    mayRequestLocation();
                }
                break;

        }
    }

    private void scanDevices() {
        devices.clear();
        deviceTypes.clear();
        ViseBluetooth.getInstance().setScanTimeout(-1).startScan(periodScanCallback);
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
                                    ParsedAd parsedAd = BleUtils.parseData(bytes);
                                    byte[] bytes1 = new byte[5];
                                    for (int i =0;i<5;i++){
                                        bytes1[i]=parsedAd.manufacturer[i+6];
                                    }
                                    String type = new String(bytes1);
                                    searchLayout.setVisibility(View.GONE);
                                    pairingLayout.setVisibility(View.VISIBLE);
                                    devices.add(bluetoothDevice);
                                    deviceTypes.add(type);
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
        ViseBluetooth.getInstance().stopLeScan(periodScanCallback);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ViseBluetooth.getInstance().stopLeScan(periodScanCallback);
        GazelleApplication.mBluetoothService.removeActivityHandler2();
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
        clock.setMinuteDrawable(R.drawable.page2_fangdajing2);
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

    private Time mCalendar;
    public int hour;
    public int minute;
    private int second;
    private int myear;
    private int month;
    private int mday;
    private void initTime() {
        if (PreferenceData.getTimeZonesState(this).equals("local")) {
            mCalendar = new Time();
            mCalendar.setToNow();
            hour = mCalendar.hour;
            minute = mCalendar.minute;
            second = mCalendar.second;
            myear = mCalendar.year;
            month = mCalendar.month;
            mday = mCalendar.monthDay;
        } else {
            TimeZone tz = TimeZone.getTimeZone(PreferenceData.getTimeZonesState(this));
            mCalendar = new Time(tz.getID());
            mCalendar.setToNow();
            hour = mCalendar.hour;
            minute = mCalendar.minute;
            second = mCalendar.second;
            myear = mCalendar.year;
            month = mCalendar.month;
            mday = mCalendar.monthDay;
        }
    }

    private Handler mActivityHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case BluetoothService.STATE_CONNECTED:
                    loadingDialog.dismiss();
                    if(pairFailedDialog!=null&&pairFailedDialog.isShowing())pairFailedDialog.dismiss();
                    GazelleApplication.isBleConnected=true;
                    PreferenceData.setAddressValue(PairingActivity.this, mDevice.getAddress());
                    PreferenceData.setDeviceType(PairingActivity.this,mDeviceType);
                    PreferenceData.setDeviceName(PairingActivity.this,mDevice.getName());
                    if(mDeviceType.equals(WacthSeries.CT002)||mDeviceType.equals("CT012")){
                        BleUtils bleUtils = new BleUtils();
                        initTime();
                        writeCharacteristic(bleUtils.setWatchDateAndTime(1, myear, month + 1, mday, hour, minute, second));
                    }
                    Intent intent = new Intent(context, PersonActivity.class);
                    startActivity(intent);
                    PairingActivity.this.finish();
                    overridePendingTransitionEnter(PairingActivity.this);
                    break;
                case BluetoothService.STATE_DISCONNECTED:
                    GazelleApplication.isBleConnected = false;
                    break;
                case BluetoothService.STATE_CONNECT_FAILED:
                    loadingDialog.dismiss();
                    if (pairFailedDialog != null)
                        pairFailedDialog.show();
                    break;
            }
        }
    };

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        mDevice = devices.get(i);
//        if (Build.VERSION.SDK_INT >= 19) mDevice.createBond();
        mDeviceType = deviceTypes.get(i);
        System.out.println(mDeviceType+"------");
        loadingDialog.show();
        GazelleApplication.mBluetoothService.connectByDevice(mDevice);
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1001:
                    clock.setTimeValue(2, count);
                    if (count == 180 && devices.size() == 0) {
                        mHandler.removeCallbacks(runnable);
                        ViseBluetooth.getInstance().stopLeScan(periodScanCallback);
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
                                count = 0;
                                scanDevices();
                                clock.setTimeValue(2, 0);
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
                ViseBluetooth.getInstance().stopLeScan(periodScanCallback);
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
