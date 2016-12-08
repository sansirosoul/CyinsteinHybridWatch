package com.xyy.Gazella.BroadcastReceiver;

import android.annotation.SuppressLint;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.ysp.newband.GazelleApplication;

@SuppressLint("NewApi")
public class PhoneBroadcastReceiver extends BroadcastReceiver {
	private static final String TAG = "PhoneStatReceiver";

	// private static MyPhoneStateListener phoneListener = new
	// MyPhoneStateListener();

	private static boolean incomingFlag = false;

	private static String incoming_number = null;

	private Context context;

	private String UUID;

	private BluetoothAdapter bluetoothAdapter;
	private boolean isCall = false;

	public PhoneBroadcastReceiver(Context context) {
		super();
		this.context = context;
		SharedPreferences userInfo = context.getSharedPreferences("UUID", 0);
		UUID = userInfo.getString("UUID", "");
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		SharedPreferences userInfo = context.getSharedPreferences("UUID", 0);
		UUID = userInfo.getString("UUID", "");
		context = this.context;
		final BluetoothManager bluetoothManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
		bluetoothAdapter = bluetoothManager.getAdapter();
		// bluetoothAdapter.enable();//打开蓝牙
		// 如果是拨打电话
		if (intent.getAction().equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
			if (!bluetoothAdapter.isEnabled()) {
				System.out.println("-----------------蓝牙状态改变--------------------- 关闭");
				GazelleApplication.mService.disconnect();
				GazelleApplication.mService.close();
			} else {
				System.out.println("-----------------蓝牙状态改变----------------------开启");
				GazelleApplication.mService.disconnect();
				GazelleApplication.mService.close();
				ble();
			}
		} else if (intent.getAction().equals("com.ysp.ble.disconnect")) {
			// ble();
		} else if (BluetoothDevice.ACTION_ACL_CONNECTED.equals(intent.getAction())) {
			Log.e("", "蓝牙连接上了!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		} else if (BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(intent.getAction())) {
			Log.e("", "------------蓝牙断开------------------");
			ble();
		} else if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
			incomingFlag = false;
			String phoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
			Log.i(TAG, "call OUT:" + phoneNumber);
			// Toast.makeText(context, phoneNumber, Toast.LENGTH_SHORT).show();
		} else {
			System.out.println("来电===========================================");
			// 如果是来电
			TelephonyManager tm = (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);
			// tm.listen(new MyPhoneStateListener(),
			// PhoneStateListener.LISTEN_CALL_STATE);
			// if (!GazelleApplication.isCall) {
			// Log.e("", "----------------------------------------------");
			// ble();
			// } else {
			switch (tm.getCallState()) {
			case TelephonyManager.CALL_STATE_RINGING:// 来电
				incomingFlag = true;// 标识当前是来电
				incoming_number = intent.getStringExtra("incoming_number");
				incoming_number.replace("+", "0");
				Log.e(TAG, "RINGING :" + incoming_number);
				// Toast.makeText(context, "isCall" + incoming_number,
				// Toast.LENGTH_SHORT).show();
				isCall = true;
				if (PhoneService.BleConnect == 1) {
//					try {
//						Thread.sleep(5000);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
					Log.e("", "come in phones   来电");
					GazelleApplication.mService.setActivityHandler(mHandler);
//					GazelleApplication.mService.registe(UUID);
					GazelleApplication.getInstance().mService.sendData("PHONE" + incoming_number);
				} else {
					startLeScan();
					// bluetoothAdapter.startLeScan(mLeScanCallback);
					// ble();
				}
				break;
			case TelephonyManager.CALL_STATE_OFFHOOK:// 接
				isCall = false;
				if (incomingFlag) {
					Log.e(TAG, "incoming ACCEPT :" + incoming_number);
					// Toast.makeText(context, "CALL_STATE_OFFHOOK" +
					// incoming_number, Toast.LENGTH_SHORT).show();
					if (PhoneService.BleConnect == 1) {
						Log.e("", "come in phones   接");
						GazelleApplication.mService.setActivityHandler(mHandler);
						GazelleApplication.getInstance().mService.sendData("PHONES");
					}
				}
				break;

			case TelephonyManager.CALL_STATE_IDLE:// 挂起
				isCall = false;
				if (incomingFlag) {
					Log.e(TAG, "incoming IDLE");
					// Toast.makeText(context, "incoming IDLE",
					// Toast.LENGTH_SHORT).show();
					if (PhoneService.BleConnect == 1) {
						Log.e("", "come in phones   挂起");
						GazelleApplication.mService.setActivityHandler(mHandler);
						GazelleApplication.getInstance().mService.sendData("PHONES");
					}
				}
				break;
			// }
			}
		}
	}

	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:// 发送请求返回数据
				byte[] result = (byte[]) msg.obj;
				String str = "";
				for (int i = 0; i < result.length; i++) {
					char s = (char) result[i];
					System.out.println("result-------->" + s);
					String str1 = String.valueOf(s);
					str = str + str1;
				}
				System.out.println("--------------------" + str);
				if (str.substring(0, 2).equals("SN")) {
					Log.e("", "--------------我是分割线~~~------------------");
					Log.e("", "SN:" + str);
				}
				break;
			case 1:
				// GazellaApplication.mService.registe(GazellaApplication.UUID);
				// GazellaApplication.getInstance().mService.sendData("PHONE" +
				// incoming_number);
				// ToastUtils.showTextToast(GazellaApplication.getInstance(),
				// msg.getData().getString("RESULT"));
				Log.e("============", "匹配==========================");
				bluetoothAdapter.stopLeScan(mLeScanCallback);
				GazelleApplication.isCall = true;
				new MyTask().execute();
				break;
			case -1:// 错误
				Log.e("", "--------------蓝牙断开----------------------");
				if (PhoneService.BleConnect != 1) {
					Log.e("=============", "come in error");
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					Log.e("", "33333333333333333333333333333333");
					// GazelleApplication.mService.disconnect();
					// GazelleApplication.mService.close();
					// startLeScan();
					// bluetoothAdapter.startLeScan(mLeScanCallback);
					ble();
				}
				break;
			case 109:
				Log.e("", "222222222222222222222222222");
				bluetoothAdapter.stopLeScan(mLeScanCallback);
				ble();
				break;
			default:
				break;
			}
		}
	};

	private void startLeScan() {
		if (!bluetoothAdapter.isEnabled())
			return;
		// GazelleApplication.getInstance().mService.disconnect();
		// GazelleApplication.getInstance().mService.close();
		// bluetoothAdapter.startLeScan(mLeScanCallback);
		ble();
	}

	class MyPhoneStateListener extends PhoneStateListener {

		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			// TODO Auto-generated method stub
			super.onCallStateChanged(state, incomingNumber);
			switch (state) {
			case TelephonyManager.CALL_STATE_IDLE:
				System.out.println("挂断");
				break;
			case TelephonyManager.CALL_STATE_OFFHOOK:
				System.out.println("接听");
				break;
			case TelephonyManager.CALL_STATE_RINGING:
				System.out.println("响铃:来电号码" + incomingNumber);
				// 输出来电号码



				break;
			}
		}
	}

	class MyTask extends AsyncTask<Object, Object, Object> {
		@SuppressWarnings("static-access")
		@Override
		protected Object doInBackground(Object... params) {
			Log.e("", "连接成功???????????????????????????????????????");
			try {
				// GazellaApplication.getInstance().mService.registe(GazellaApplication.UUID);
				Thread.sleep(2000);
				if (isCall) {
					GazelleApplication.getInstance().mService.sendData("PHONE" + incoming_number);
				} else {

				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return null;
		}
	}

	// 所搜设备返回
	private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
		@Override
		public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
			Log.e("", "device=====" + device.getAddress());
			SharedPreferences userInfo = context.getSharedPreferences("UUID", 0);
			UUID = userInfo.getString("UUID", "");
			if (device.getAddress() != null)
				if (device.getAddress().equals(UUID)) {
					Message msg = new Message();
					msg.what = 109;
					mHandler.sendMessage(msg);
					return;
				}
		}
	};

	private void ble() {
		Log.e("", "come in Broadcast !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		if (!bluetoothAdapter.isEnabled())
			return;
		if (UUID != null) {
			if (PhoneService.BleConnect != 1) {
				GazelleApplication.getInstance().mService.disconnect();
				GazelleApplication.getInstance().mService.close();
				System.out.println("UUID==========" + UUID);
				// GazellaApplication.device =
				BluetoothAdapter.getDefaultAdapter().getRemoteDevice(UUID);
				if (GazelleApplication.getInstance().mService != null) {
					// GazellaApplication.getInstance().mService.connect(GazellaApplication.device);
					GazelleApplication.getInstance().mService.initialize();
					GazelleApplication.getInstance().mService.setActivityHandler(mHandler);
					GazelleApplication.getInstance().mService.registe(UUID);
				}
			} else {
				if (GazelleApplication.getInstance().mService != null) {
					GazelleApplication.getInstance().mService.setActivityHandler(mHandler);
					GazelleApplication.getInstance().mService.registe(UUID);
				}
			}
		}
	}

}
