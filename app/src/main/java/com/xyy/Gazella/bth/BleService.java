package com.xyy.Gazella.bth;

import android.annotation.SuppressLint;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothProfile;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.samsung.android.sdk.bt.gatt.BluetoothGatt;
import com.samsung.android.sdk.bt.gatt.BluetoothGattAdapter;
import com.samsung.android.sdk.bt.gatt.BluetoothGattCallback;
import com.samsung.android.sdk.bt.gatt.BluetoothGattCharacteristic;
import com.samsung.android.sdk.bt.gatt.BluetoothGattDescriptor;
import com.samsung.android.sdk.bt.gatt.BluetoothGattService;

import java.util.UUID;

@SuppressLint("NewApi")
public class BleService extends Service {
	
	private static final String TAG=BleService.class.getName();
	
	private int count = 0;
	private BluetoothAdapter mBtAdapter = null;
	public BluetoothGatt mBluetoothGatt = null;
	private BluetoothGattCharacteristic mCharac = null;
	private Handler mActivityHandler = null;
	public static final UUID HRP_SERVICE = UUID
			.fromString("0000FFB0-0000-1000-8000-00805f9b34fb");
	public static final UUID HeartRate_ControlPoint = UUID
			.fromString("0000FFB2-0000-1000-8000-00805f9b34fb");
	public static final UUID CCC = UUID
			.fromString("00002902-0000-1000-8000-00805f9b34fb");
	public static final UUID MY_SERVICE = UUID
			.fromString("00001800-0000-1000-8000-00805f9b34fb");
	public static final UUID MY_CHARAC = UUID
			.fromString("00002a00-0000-1000-8000-00805f9b34fb");
	private boolean isRegister = false;
	private BluetoothDevice device;

	public static final int HRP_CONNECT_MSG = 1;
	public static final int HRP_DISCONNECT_MSG = 2;
	public static final int HRP_READY_MSG = 3;
	public static final int HRP_VALUE_MSG = 4;
	public static final int GATT_DEVICE_FOUND_MSG = 5;
	public static final String EXTRA_RSSI = "RSSI";
	public static final String EXTRA_SOURCE = "SOURCE";

	public void setActivityHandler(Handler mHandler) {
		mActivityHandler = mHandler;
	}

	public class LocalBinder extends Binder {
		public BleService getService() {
			return BleService.this;
		}
	}

	private final IBinder binder = new LocalBinder();

	public void connect(BluetoothDevice device, boolean autoconnect) {
		if (mBluetoothGatt != null) {
			mBluetoothGatt.connect(device, autoconnect);
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		Log.d(TAG, "onBind() called");
		return binder;
	}

	@Override
	public void onCreate() {
		Log.d(TAG, "onCreate() called");
		if (mBtAdapter == null) {
			mBtAdapter = BluetoothAdapter.getDefaultAdapter();
			if (mBtAdapter == null)
				return;
		}

		if (mBluetoothGatt == null) {
			BluetoothGattAdapter.getProfileProxy(this, mProfileServiceListener,
					BluetoothGattAdapter.GATT);
			System.out.println(mBluetoothGatt);
		}
	}

	@Override
	public void onDestroy() {
		Log.d(TAG, "onDestroy() called");
		if (mBtAdapter != null && mBluetoothGatt != null) {
			BluetoothGattAdapter.closeProfileProxy(BluetoothGattAdapter.GATT,
					mBluetoothGatt);
		}
		super.onDestroy();
	}

	private BluetoothProfile.ServiceListener mProfileServiceListener = new BluetoothProfile.ServiceListener() {
		@Override
		public void onServiceConnected(int profile, BluetoothProfile proxy) {
			System.out.println(profile);
			if (profile == BluetoothGattAdapter.GATT) {
				mBluetoothGatt = (BluetoothGatt) proxy;
				mBluetoothGatt.registerApp(mGattCallbacks);
			}
		}

		@Override
		public void onServiceDisconnected(int profile) {
			if (profile == BluetoothGattAdapter.GATT) {
				if (mBluetoothGatt != null)
					mBluetoothGatt.unregisterApp();
//				Log.e("onServiceDisconnected==mBluetoothGatt", mBluetoothGatt
//						+ "");
				mBluetoothGatt = null;
			}
		}
	};
	public static final int ADV_DATA_FLAG = 0x01;
	public static final int LIMITED_AND_GENERAL_DISC_MASK = 0x03;

	/*
	 * Broadcast mode checker API
	 */
	public boolean checkIfBroadcastMode(byte[] scanRecord) {
		int offset = 0;
		while (offset < (scanRecord.length - 2)) {
			int len = scanRecord[offset++];
			if (len == 0)
				break; // Length == 0 , we ignore rest of the packet
			// Check the rest of the packet if get len = 0

			int type = scanRecord[offset++];
			switch (type) {
			case ADV_DATA_FLAG:

				if (len >= 2) {
					// The usual scenario(2) and More that 2 octets scenario.
					// Since this data will be in Little endian format, we
					// are interested in first 2 bits of first byte
					byte flag = scanRecord[offset++];
					/*
					 * 00000011(0x03) - LE Limited Discoverable Mode and LE
					 * General Discoverable Mode
					 */
					if ((flag & LIMITED_AND_GENERAL_DISC_MASK) > 0)
						return false;
					else
						return true;
				} else if (len == 1) {
					continue;// ignore that packet and continue with the rest
				}
			default:
				offset += (len - 1);
				break;
			}
		}
		return false;
	}

	public void setDeviceListHandler(Handler mHandler) {
		Log.d(TAG, "Device List Handler set");
		mDeviceListHandler = mHandler;
	}

	private Handler mDeviceListHandler = null;
	public static final int DEVICE_SOURCE_SCAN = 0;
	// 三星蓝牙CallBack方法
	private BluetoothGattCallback mGattCallbacks = new BluetoothGattCallback() {
		@Override
		public void onScanResult(BluetoothDevice device, int rssi,
				byte[] scanRecord) {
			Log.d(TAG, "onScanResult() - device=" + device + ", rssi=" + rssi);
			if (!checkIfBroadcastMode(scanRecord)) {
				Bundle mBundle = new Bundle();
				Message msg = Message.obtain(mDeviceListHandler,
						GATT_DEVICE_FOUND_MSG);
				mBundle.putParcelable(BluetoothDevice.EXTRA_DEVICE, device);
				mBundle.putInt(EXTRA_RSSI, rssi);
				mBundle.putInt(EXTRA_SOURCE, DEVICE_SOURCE_SCAN);
				msg.setData(mBundle);
				msg.sendToTarget();
			} else
				Log.i(TAG, "device =" + device
						+ " is in Brodacast mode, hence not displaying");
		}

		@Override
		public void onConnectionStateChange(BluetoothDevice device, int status,
				int newState) {
			Message msg = new Message();
			Bundle mBundle = new Bundle();
			System.out.println(mBluetoothGatt);
			if (newState == BluetoothProfile.STATE_CONNECTED
					&& mBluetoothGatt != null) {
				msg = Message.obtain(mActivityHandler, 1);
				mBundle.putString("RESULT", "匹配成功！");
				mBluetoothGatt.discoverServices(device);
			}
			if (newState == BluetoothProfile.STATE_DISCONNECTED
					&& mBluetoothGatt != null) {
				msg = Message.obtain(mActivityHandler, -1);
				mBundle.putString("ERROR", "匹配失败，请重试！");
			}
			msg.setData(mBundle);
			msg.sendToTarget();
		}

		@Override
		public void onServicesDiscovered(BluetoothDevice device, int status) {
			// Message msg = Message.obtain(mActivityHandler, HRP_READY_MSG);
			// msg.sendToTarget();
			// DummyReadForSecLevelCheck(device);
		}

		@Override
		public void onCharacteristicChanged(
				BluetoothGattCharacteristic characteristic) {
			System.out.println("返回数据====228");
			byte[] buffer = characteristic.getValue();
			count += buffer.length;
			String str = "";
			for (int i = 0; i < buffer.length; i++) {
				char result = (char) buffer[i];
				Log.d(TAG, buffer[i] + "===" + result + "=====/234/");
				str = str + result;
			}
			Bundle mBundle = new Bundle();
			Message msg = Message.obtain(mActivityHandler, 0);
			mBundle.putByteArray("mData", characteristic.getValue());
			msg.setData(mBundle);
			msg.sendToTarget();
			// Log.d("接收到的文件名/ID", str + "==============/242/");
			// Log.d(TAG, "收到数据总长" + count + "=========/243/");
		}

		@Override
		public void onCharacteristicRead(BluetoothGattCharacteristic charac,
				int status) {
			// 接受数据成功
			Bundle mBundle = new Bundle();
			Message msg = Message.obtain(mActivityHandler, 0);
			mBundle.putByteArray("mData", charac.getValue());
			msg.setData(mBundle);
			msg.sendToTarget();
			Log.i(TAG, "状态：" + status + "===============159/");
		}

		@Override
		public void onDescriptorRead(BluetoothGattDescriptor descriptor,
				int status) {
			// BluetoothGattCharacteristic mHRMcharac = descriptor
			// .getCharacteristic();
			// enableNotification(true, mHRMcharac);
		}

		@Override
		public void onCharacteristicWrite(BluetoothGattCharacteristic charac,
				int status) {
			// 发送数据成功
		}

	};

	// 注册信息
	public void registe(BluetoothDevice device) {
		Log.i(TAG, "279/reg Notifacation");
		BluetoothGattService mSendService = mBluetoothGatt.getService(device,
				HRP_SERVICE);
		Log.e("mSendService", mSendService + "");
		if (mSendService == null) {
			Log.e(TAG, "284/HRP service not found!");
			// isRegister = false;
			return;
		} else {
			isRegister = true;
		}
		Log.v("isRegister", isRegister + "======");
		mCharac = mSendService.getCharacteristic(HeartRate_ControlPoint);
		Log.e("注册mCharac",
				mSendService.getCharacteristic(HeartRate_ControlPoint) + "");
		if (mCharac == null) {
			Log.e(TAG, "reg: mCharac = null");
			return;
		}
		enableNotification(true, mCharac);
	}

	public boolean enableNotification(boolean enable,
			BluetoothGattCharacteristic characteristic) {
		Log.e("211/enableNotification", "enableNotification");
		if (mBluetoothGatt == null)
			return false;
		if (!mBluetoothGatt.setCharacteristicNotification(characteristic,
				enable))
			return false;

		BluetoothGattDescriptor clientConfig = characteristic
				.getDescriptor(CCC);
		if (clientConfig == null)
			return false;

		if (enable) {
			Log.i(TAG, "enable notification");
			clientConfig.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
		} else {
			Log.i(TAG, "disable notification");
			clientConfig
					.setValue(BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE);
		}
		return mBluetoothGatt.writeDescriptor(clientConfig);
	}

	// 发送数据
	/**
	 * String 转化为char[]
	 * 
	 * @param commandStr
	 * @return
	 */
	public boolean sendData(String commandStr) {
		Log.e("发送数据", "===========sendData========");
		if (!isRegister) {
			Bundle mBundle = new Bundle();
			Message msg = Message.obtain(mActivityHandler, -1);
			mBundle.putString("ERROR", "请求失败，请重试！");
			msg.setData(mBundle);
			msg.sendToTarget();
			return false;
		}
		int commandLength = commandStr.length();
		char[] commandChar = commandStr.toCharArray();
		byte[] value = new byte[20];
		for (int i = 0; i < value.length; i++) {
			if (i < commandLength)
				value[i] = (byte) commandChar[i];
			else
				value[i] = 0;
		}
		// Log.e("mCharac", mCharac + "");
		// Log.e("commandStr", commandStr + "");
		System.out.println(mCharac + "=====" + value);
		if (mCharac != null) {
			mCharac.setValue(value);
			Log.e(TAG, "isRegister====>>>>>" + isRegister);
			return mBluetoothGatt.writeCharacteristic(mCharac);
		} else {
			Message msg = new Message();
			msg.what = -1;
			mActivityHandler.sendMessage(msg);
			return false;
		}
	}

	public boolean sendData(String commandStr, byte[] num) {
		Log.e("发送数据", "===========sendData========");
		if (!isRegister) {
			Bundle mBundle = new Bundle();
			Message msg = Message.obtain(mActivityHandler, -1);
			mBundle.putString("ERROR", "请求失败，请重试！");
			msg.setData(mBundle);
			msg.sendToTarget();
			// mActivityHandler.sendMessage(msg);
			return false;
		}
		int totalLength = commandStr.length() + num.length;
		int commandLength = commandStr.length();
		char[] commandChar = commandStr.toCharArray();
		byte[] value = new byte[20];
		int j = 0;
		for (int i = 0; i < value.length; i++) {
			if (i < commandLength) {
				value[i] = (byte) commandChar[i];
			} else if (i < totalLength) {
				value[i] = num[j];
				j++;
			} else {
				value[i] = 0;
			}
		}
		if (mCharac != null) {
			mCharac.setValue(value);
			Log.e(TAG, "isRegister====>>>>>" + isRegister);
			return mBluetoothGatt.writeCharacteristic(mCharac);
		} else {
			Message msg = new Message();
			msg.what = -1;
			mActivityHandler.sendMessage(msg);
			return false;
		}
	}

	/**
	 * 直接发送String
	 * 
	 * @param commandStr
	 * @param tye
	 * @return
	 */
	public boolean sendData(String commandStr, int tye) {
		if (!isRegister) {
			Bundle mBundle = new Bundle();
			Message msg = Message.obtain(mActivityHandler, -1);
			mBundle.putString("ERROR", "请求失败，请重试！");
			msg.setData(mBundle);
			msg.sendToTarget();
			return false;
		}
		if (mCharac != null) {
			mCharac.setValue(commandStr);
			Log.e("isRegister", "isRegister====>>>>>" + isRegister);
			return mBluetoothGatt.writeCharacteristic(mCharac);
		} else {
			Message msg = new Message();
			msg.what = -1;
			mActivityHandler.sendMessage(msg);
			return false;
		}
	}

	// 连接蓝牙
	public void connect(BluetoothDevice device) {
		this.device = device;
		if (mBluetoothGatt != null) {
			// System.out.println(mBluetoothGatt + "===" + device);
			mBluetoothGatt.connect(device, false);
		}
	}

	// 断开
	public void disconnect() {
		Log.e(TAG, "come in disconnect");
		if (mBluetoothGatt != null && device != null) {
			Log.e(TAG, "断开蓝牙==================");
			mBluetoothGatt.cancelConnection(device);
		}
	}

	// 移除Bond
	public void removeBond() {
		if (mBluetoothGatt != null && device != null) {
			Log.e(TAG, "移除Bond==================");
			mBluetoothGatt.removeBond(device);
		}
	}

	public boolean isBLEDevice(BluetoothDevice device) {
		return mBluetoothGatt.isBLEDevice(device);
	}

	// 搜索
	public void scan(boolean start) {
		if (mBluetoothGatt == null)
			return;
		if (start) {
			mBluetoothGatt.startScan();
		} else {
			mBluetoothGatt.stopScan();
		}
	}
}