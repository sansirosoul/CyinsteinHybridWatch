package com.xyy.Gazella.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.xyy.Gazella.adapter.DeviceListAdapter;
import com.ysp.newband.BaseActivity;
import com.ysp.newband.GazelleApplication;
import com.ysp.smartwatch.R;

/**搜索设备列表页面**/
public class DeviceListActivity extends BaseActivity implements OnItemClickListener {

	private static final String TAG = DeviceListActivity.class.getName();
	private static final int REQUEST_ENABLE_BT = 51;

	private ListView deviceListView;
	private GazelleApplication app;
	private BluetoothAdapter bluetoothAdapter;

	private List<BluetoothDevice> deviceList;
	private DeviceListAdapter adapter;
	private String mac;
	private BluetoothDevice BlueDevice;
	private boolean Found = false;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1001:
				if (deviceList.size() > 0) {
					for (BluetoothDevice listDev : deviceList) {
					   String ss=	listDev.getAddress();
					   Log.i(TAG, "SSS==="+ss);
						if (listDev.getAddress().equals(mac)) {
							continue;
						} else {
							Found = true;
							break;
						}
					}
					if (Found) {
						deviceList.add(BlueDevice);
						adapter.notifyDataSetChanged();
						Found=false;
					}
				} else {
					deviceList.add(BlueDevice);
					adapter.notifyDataSetChanged();
				}
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.devicelist);
		app = GazelleApplication.getInstance();
		deviceListView = (ListView) findViewById(R.id.devices_list);
		deviceList = new ArrayList<BluetoothDevice>();
		adapter = new DeviceListAdapter(DeviceListActivity.this, deviceList);
		deviceListView.setAdapter(adapter);
		 deviceListView.setOnItemClickListener(this);
		// 检查当前手机是否支持ble 蓝牙,如果不支持退出程序
		if (!getPackageManager().hasSystemFeature(
				PackageManager.FEATURE_BLUETOOTH_LE)) {
			Toast.makeText(this, "不支持蓝牙", Toast.LENGTH_SHORT).show();
			finish();
		}
		ScanDeviceList();
	}

	private void ScanDeviceList() {
		bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		// 检查设备上是否支持蓝牙
		// 初始化 Bluetooth adapter, 通过蓝牙管理器得到一个参考蓝牙适配器(API必须在以上android4.3或以上和版本)
		final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
		bluetoothAdapter = bluetoothManager.getAdapter();

		// 检查设备上是否支持蓝牙
		if (bluetoothAdapter == null) {
			Toast.makeText(this, "不支持蓝牙", Toast.LENGTH_SHORT).show();
			finish();
		}
		if (!bluetoothAdapter.isEnabled()) { // /蓝牙是否连接
			Intent enableIntent = new Intent(
					BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
		} else {
			bluetoothAdapter.startLeScan(mLeScanCallback);
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case REQUEST_ENABLE_BT:
			if (resultCode == Activity.RESULT_OK) {
				bluetoothAdapter.startLeScan(mLeScanCallback);
			} else {
			}
			break;
		}
}

	// Device scan callback.
	private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {

		@Override
		public void onLeScan(final BluetoothDevice device, final int rssi,
				byte[] scanRecord) {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					if (device != null) {
						if (device.getName() != null
								&& (device.getName().equals("Watch")
										|| device.getName().equals("Partner")
										|| device.getName().equals("Band")
										|| device.getName().equals("Felix") || device
										.getName().equals("Nova"))) {
							Log.e(TAG,
									"----------------搜索到设备!!!-----------------");
							Log.e(TAG, "device.name== " + device.getName()
									+ "  mac== " + device.getAddress()
									+ "  rssi== " + String.valueOf(rssi));
							mac = device.getAddress();
							Log.i(TAG, "MAC" + mac);
							BlueDevice = device;
							handler.sendEmptyMessage(1001);
						}
					}
				}
			});
		}
	};

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent intent  = new Intent(DeviceListActivity.this,ConnectionDevice.class);
		intent.putExtra(BluetoothDevice.EXTRA_DEVICE, deviceList.get(arg2).getAddress());
		startActivity(intent);		
		bluetoothAdapter.stopLeScan(mLeScanCallback);
	}
}
