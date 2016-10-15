package com.xyy.Gazella.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.xyy.Gazella.adapter.DeviceListAdapter;
import com.xyy.Gazella.utils.LoadingDialog;
import com.ysp.newband.BaseActivity;
import com.ysp.newband.GazelleApplication;
import com.ysp.smartwatch.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/12.
 */

public class PairingActivity extends BaseActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
    private ListView listView;
    private Button skip;
    private DeviceListAdapter deviceListAdapter;
    private BluetoothAdapter bluetoothAdapter;
    private List<BluetoothDevice> devices = new ArrayList<BluetoothDevice>();
    private static final int REQUEST_ENABLE_BT = 1;
    private RelativeLayout searchLayout;
    private  LinearLayout pairingLayout;
    private Context context;
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.pairing_activity);

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
            finish();
            return;
        }
    }

    BluetoothAdapter.LeScanCallback leScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(final BluetoothDevice bluetoothDevice, int i, byte[] bytes) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(bluetoothDevice.getName() != null&&(bluetoothDevice.getName().equals("Watch")
                            || bluetoothDevice.getName().equals("Partner")
                            || bluetoothDevice.getName().equals("Band")
                            || bluetoothDevice.getName().equals("Felix") || bluetoothDevice
                            .getName().equals("Nova"))){
                        if(!devices.contains(bluetoothDevice)){
                            searchLayout.setVisibility(View.GONE);
                            pairingLayout.setVisibility(View.VISIBLE);
                            devices.add(bluetoothDevice);
                            deviceListAdapter.notifyDataSetChanged();
                        }
                    }

                }
            });
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

        devices.clear();
        bluetoothAdapter.startLeScan(leScanCallback);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(bluetoothAdapter!=null){
            bluetoothAdapter.stopLeScan(leScanCallback);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ENABLE_BT && resultCode == Activity.RESULT_CANCELED) {

            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initView() {
        context=this;
        listView = (ListView) findViewById(R.id.listview);
        skip = (Button) findViewById(R.id.skip);

        deviceListAdapter = new DeviceListAdapter(this, devices);
        listView.setAdapter(deviceListAdapter);

        listView.setOnItemClickListener(this);
        skip.setOnClickListener(this);

        searchLayout= (RelativeLayout) findViewById(R.id.search_layout);
        pairingLayout=(LinearLayout) findViewById(R.id.pairing_layout);


        loadingDialog=new LoadingDialog(context);


    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        loadingDialog.show();
//        if(GazelleApplication.mBluetoothService.initialize()){
//            GazelleApplication.mBluetoothService.connect(devices.get(i).getAddress());
//            GazelleApplication.mBluetoothService.setActivityHandler(mHandler);
//        }
//        bluetoothAdapter.stopLeScan(leScanCallback);
    }

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
//                case BluetoothService.STATE_CONNECTED:
//                    pairingDialog.dismiss();
//                    Intent intent = new Intent(context,PersonActivity.class);
//                    startActivity(intent);
//                    break;
//                case BluetoothService.STATE_DISCONNECTED:

//                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.skip:
                bluetoothAdapter.stopLeScan(leScanCallback);
                Intent intent = new Intent(context,PersonActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
