package com.xyy.Gazella.BroadcastReceiver;

import android.annotation.SuppressLint;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.ysp.newband.GazelleApplication;

public class PhoneService extends Service {

	private PhoneBroadcastReceiver broadcast;
	public IntentFilter intentFoilter;
	private String UUID;

	public static int BleConnect;

	private BluetoothAdapter bluetoothAdapter;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressLint({ "InlinedApi", "NewApi" })
	@Override
	public void onCreate() {
		super.onCreate();
		SharedPreferences userInfo = getSharedPreferences("UUID", 0);
		UUID = userInfo.getString("UUID", "");
		intentFoilter = new IntentFilter();
		intentFoilter.addAction(TelephonyManager.ACTION_PHONE_STATE_CHANGED);
		intentFoilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
		intentFoilter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
		intentFoilter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
		intentFoilter.addAction("com.ysp.ble.disconnect");
		broadcast = new PhoneBroadcastReceiver(this);
		registerReceiver(broadcast, intentFoilter);
		BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(BLUETOOTH_SERVICE);
		bluetoothAdapter = bluetoothManager.getAdapter();
		ble();
	}

	@SuppressLint("NewApi")
	@Override
	@Deprecated
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.e("---------", "---------------服务内注销来电广播-----------------");
		unregisterReceiver(broadcast);
	}

	private void ble() {
		Log.e("", "come in phone service !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		if (!bluetoothAdapter.isEnabled())
			return;
		if (BleConnect != 1) {
			Log.e("", "111111111111111111111");
			System.out.println("UUID==========devic" + UUID);
			// GazellaApplication.device =
			// BluetoothAdapter.getDefaultAdapter().getRemoteDevice(GazellaApplication.UUID);
			if (GazelleApplication.getInstance().mService != null) {
				System.out.println("==================================");
				GazelleApplication.getInstance().mService.initialize();
				GazelleApplication.getInstance().mService.setActivityHandler(mHandler);
				GazelleApplication.getInstance().mService.registe(UUID);
			}
		} else {
			Log.e("", "22222222222222222");
			if (GazelleApplication.getInstance().mService != null) {
				GazelleApplication.getInstance().mService.setActivityHandler(mHandler);
				GazelleApplication.getInstance().mService.registe(UUID);
			}
		}
	}

	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				GazelleApplication.isCall = true;
				Log.e("", "----------来电连接成功!!!-------------");
				break;
			case -1:
				Log.e("", "------------来电连接失败!!!1-----------------");
				Intent i = new Intent("com.ysp.ble.disconnect");
				sendBroadcast(i);
				break;
			default:
				break;
			}
		};
	};
}
