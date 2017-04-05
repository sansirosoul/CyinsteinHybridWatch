package com.xyy.Gazella.services;

import android.annotation.SuppressLint;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.orhanobut.logger.Logger;
import com.vise.baseble.ViseBluetooth;
import com.vise.baseble.callback.IConnectCallback;
import com.vise.baseble.exception.BleException;

import java.util.List;

@SuppressLint("NewApi")
public class BluetoothService extends Service {
    public final static String TAG = BluetoothService.class.getSimpleName();
    public final static String serviceUUID = "6e40ffb0-b5a3-f393-e0a9-e50e24dcca9e";
    public final static String writeUUID = "6e400002-b5a3-f393-e0a9-e50e24dcca9e";
    public final static String notifyUUID = "6e400003-b5a3-f393-e0a9-e50e24dcca9e";

    private BluetoothAdapter mBluetoothAdapter;
    public BluetoothGatt mBluetoothGatt;
    private String mBluetoothDeviceAddress;

    private Handler mActivityHandler = null;

    public static final int STATE_DISCONNECTED = 2000;
    public static final int STATE_CONNECTED = 2001;
    public static final int SERVICES_DISCOVERED = 2002;
    public static final int READ_SUCCESS = 2003;
    public static final int WRITE_SUCCESS = 2004;
    public static final int NOTIFY_SUCCESS = 2005;

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

    public void connect(String address) {
        ViseBluetooth.getInstance().connectByMac(address, false, new IConnectCallback() {
            @Override
            public void onConnectSuccess(BluetoothGatt gatt, int status) {
                mBluetoothGatt=gatt;
                mActivityHandler.sendEmptyMessage(STATE_CONNECTED);
            }

            @Override
            public void onConnectFailure(BleException exception) {
                Logger.e(exception.getDescription());
            }

            @Override
            public void onDisconnect() {
                mActivityHandler.sendEmptyMessage(STATE_DISCONNECTED);
            }
        });
    }

    public void disconnect() {
        ViseBluetooth.getInstance().disconnect();
    }

    public void close() {
        ViseBluetooth.getInstance().close();
        ViseBluetooth.getInstance().clear();
    }

    public int getConnectState(){
        return ViseBluetooth.getInstance().getState().getCode();
    }

    public void writeCharateristic(BluetoothGattCharacteristic characteristic) {
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
        List<BluetoothGattDescriptor> descriptors = characteristic.getDescriptors();
        for (int i = 0; i < descriptors.size(); i++) {
            BluetoothGattDescriptor descriptor = descriptors.get(i);
            if (descriptor != null) {
                descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                mBluetoothGatt.writeDescriptor(descriptor);
            }
        }
    }

    public List<BluetoothGattService> getSupportedGattServices() {
        if (mBluetoothGatt == null)
            return null;

        return mBluetoothGatt.getServices();
    }

    public BluetoothGattCharacteristic getWriteCharacteristic() {
        BluetoothGattCharacteristic gattCharacteristic = null;
        if (mBluetoothGatt == null)
            return null;
        List<BluetoothGattService> services = mBluetoothGatt.getServices();
        for (int i = 0; i<services.size();i++){
            BluetoothGattService service = services.get(i);
            if(service.getUuid().toString().equals(serviceUUID)){
                List<BluetoothGattCharacteristic> characteristics = service.getCharacteristics();
                for(int j = 0; j<characteristics.size();j++){
                    BluetoothGattCharacteristic characteristic = characteristics.get(j);
                    if(characteristic.getUuid().toString().equals(writeUUID)){
                        gattCharacteristic=characteristic;
                    }
                }
            }
        }
        return gattCharacteristic;
    }

    public BluetoothGattCharacteristic getNotifyCharacteristic() {
        BluetoothGattCharacteristic gattCharacteristic = null;
        if (mBluetoothGatt == null)
            return null;
        List<BluetoothGattService> services = mBluetoothGatt.getServices();
        for (int i = 0; i<services.size();i++){
            BluetoothGattService service = services.get(i);
            if(service.getUuid().toString().equals(serviceUUID)){
                List<BluetoothGattCharacteristic> characteristics = service.getCharacteristics();
                for(int j = 0; j<characteristics.size();j++){
                    BluetoothGattCharacteristic characteristic = characteristics.get(j);
                    if(characteristic.getUuid().toString().equals(notifyUUID)){
                        gattCharacteristic=characteristic;
                    }
                }
            }
        }
        return gattCharacteristic;
    }
}
