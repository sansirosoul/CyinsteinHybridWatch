package com.xyy.Gazella.services;

import android.annotation.SuppressLint;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import java.util.List;
import java.util.UUID;

@SuppressLint("NewApi")
public class BluetoothService extends Service {
	public final static String TAG = BluetoothService.class.getSimpleName();

	private BluetoothManager mBluetoothManager;
	private BluetoothAdapter mBluetoothAdapter;
	public BluetoothGatt mBluetoothGatt;
	private String mBluetoothDeviceAddress;

	private Handler mActivityHandler = null;

	public static final int STATE_DISCONNECTED = 0;
	public static final int STATE_CONNECTED = 1;
	public static final int SERVICES_DISCOVERED = 2;
	public static final int READ_SUCCESS = 3;
	public static final int WRITE_SUCCESS = 4;
	public static final int NOTIFY_SUCCESS = 5;

	public void setActivityHandler(Handler mHandler) {
		mActivityHandler = mHandler;
	}

	private final IBinder mBinder = new LocalBinder();



	public class LocalBinder extends Binder {
		public BluetoothService getService() {
			return BluetoothService.this;
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return mBinder;
	}

	@Override
	public boolean onUnbind(Intent intent) {
		// TODO Auto-generated method stub
		close();
		return super.onUnbind(intent);
	}

	public void close() {
		if (mBluetoothGatt == null) {
			return;
		}
		mBluetoothGatt.close();
		mBluetoothGatt = null;
	}

	public boolean initialize() {
		if (mBluetoothManager == null) {
			mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
			if (mBluetoothManager == null) {
				Log.e(TAG, "Unable to initialize BluetoothManager.");
				return false;
			}
		}

		mBluetoothAdapter = mBluetoothManager.getAdapter();
		if (mBluetoothAdapter == null) {
			Log.e(TAG, "Unable to obtain a BluetoothAdapter.");
			return false;
		}

		return true;
	}

	public boolean connect(final String address) {
		if (mBluetoothAdapter == null || address == null) {
			Log.w(TAG,
					"BluetoothAdapter not initialized or unspecified address.");
			return false;
		}

		// Previously connected device. Try to reconnect.
		if (mBluetoothDeviceAddress != null
				&& address.equals(mBluetoothDeviceAddress)
				&& mBluetoothGatt != null) {
			Log.d(TAG,
					"Trying to use an existing mBluetoothGatt for connection.");
			if (mBluetoothGatt.connect()) {
				return true;
			} else {
				return false;
			}
		}

		final BluetoothDevice device = mBluetoothAdapter
				.getRemoteDevice(address);
		if (device == null) {
			Log.w(TAG, "Device not found.  Unable to connect.");
			return false;
		}

		mBluetoothGatt = device.connectGatt(this, false, mGattCallback);
		Log.d(TAG, "Trying to create a new connection.");
		mBluetoothDeviceAddress = address;
		return true;
	}
	
	public void disconnect() {
		if (mBluetoothAdapter == null || mBluetoothGatt == null) {
			Log.w(TAG, "BluetoothAdapter not initialized");
			return;
		}
		mBluetoothGatt.disconnect();
	}

	BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
		public void onConnectionStateChange(BluetoothGatt gatt, int status,
				int newState) {
			System.out.println("=======status:" + status);
			if (newState == BluetoothProfile.STATE_CONNECTED) {
				mActivityHandler.obtainMessage(STATE_CONNECTED).sendToTarget();
				Log.i(TAG, "Connected to GATT server.");
				// Attempts to discover services after successful connection.
				Log.i(TAG, "Attempting to start service discovery:"
						+ mBluetoothGatt.discoverServices());
			} else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
				mActivityHandler.obtainMessage(STATE_DISCONNECTED).sendToTarget();
				Log.i(TAG, "Disconnected from GATT server.");
			}
		};

		@Override
		public void onServicesDiscovered(BluetoothGatt gatt, int status) {
			if (status == BluetoothGatt.GATT_SUCCESS) {
				mActivityHandler.obtainMessage(SERVICES_DISCOVERED).sendToTarget();
			} else {
				Log.w(TAG, "onServicesDiscovered received: " + status);
			}
		}

		public void onCharacteristicChanged(BluetoothGatt gatt,
				BluetoothGattCharacteristic characteristic) {
			 Bundle bundle = new Bundle();
             Message msg = Message.obtain(mActivityHandler, NOTIFY_SUCCESS);
             msg.setData(bundle);
             msg.sendToTarget();

		};

		public void onCharacteristicRead(BluetoothGatt gatt,
				BluetoothGattCharacteristic characteristic, int status) {
              Bundle bundle = new Bundle();
              Message msg = Message.obtain(mActivityHandler, READ_SUCCESS);
              msg.setData(bundle);
              msg.sendToTarget();
		};

		public void onCharacteristicWrite(BluetoothGatt gatt,
				BluetoothGattCharacteristic characteristic, int status) {
			 Bundle bundle = new Bundle();
             Message msg = Message.obtain(mActivityHandler, WRITE_SUCCESS);
             msg.setData(bundle);
             msg.sendToTarget();
		};
		
		@Override
		public void onDescriptorWrite(BluetoothGatt gatt,
				BluetoothGattDescriptor descriptor, int status) {

			System.out.println("onDescriptorWriteonDescriptorWrite = " + status
					+ ", descriptor =" + descriptor.getUuid().toString());
		}
		
		@Override
		public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
			System.out.println("rssi = " + rssi);
		}
	};
	
	public void readCharacteristic(BluetoothGattCharacteristic characteristic){
		if (mBluetoothAdapter == null || mBluetoothGatt == null) {
			Log.w(TAG, "BluetoothAdapter not initialized");
			return;
		}
		mBluetoothGatt.readCharacteristic(characteristic);
	}
	
	public void writeCharateristic(BluetoothGattCharacteristic characteristic){
		if (mBluetoothAdapter == null || mBluetoothGatt == null) {
			Log.w(TAG, "BluetoothAdapter not initialized");
			return;
		}
		mBluetoothGatt.writeCharacteristic(characteristic);
	}
	
	public void setCharacteristicNotification(
			BluetoothGattCharacteristic characteristic, boolean enabled) {
		if (mBluetoothAdapter == null || mBluetoothGatt == null) {
			Log.w(TAG, "BluetoothAdapter not initialized");
			return;
		}
		mBluetoothGatt.setCharacteristicNotification(characteristic, enabled);
	}
	
	public List<BluetoothGattService> getSupportedGattServices() {
		if (mBluetoothGatt == null)
			return null;

		return mBluetoothGatt.getServices();
	}

	public BluetoothGattCharacteristic getWriteCharacteristic(){
		if (mBluetoothGatt == null)
			return null;
		BluetoothGattService service = mBluetoothGatt.getService(UUID.fromString("6e400001-b5a3-f393-e0a9-e50e24dcca9e"));
		BluetoothGattCharacteristic characteristic = service.getCharacteristic(UUID.fromString("6e400002-b5a3-f393-e0a9-e50e24dcca9e"));
		return characteristic;
	}

	public BluetoothGattCharacteristic getNotifyCharacteristic(){
		if (mBluetoothGatt == null)
			return null;
		BluetoothGattService service = mBluetoothGatt.getService(UUID.fromString("6e400001-b5a3-f393-e0a9-e50e24dcca9e"));
		BluetoothGattCharacteristic characteristic = service.getCharacteristic(UUID.fromString("6e400003-b5a3-f393-e0a9-e50e24dcca9e"));
		return characteristic;
	}
}
