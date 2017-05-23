package com.xyy.Gazella.services;

import android.annotation.SuppressLint;
import android.app.Service;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;

import com.orhanobut.logger.Logger;
import com.vise.baseble.ViseBluetooth;
import com.vise.baseble.callback.IBleCallback;
import com.vise.baseble.callback.IConnectCallback;
import com.vise.baseble.exception.BleException;
import com.ysp.newband.GazelleApplication;
import com.ysp.newband.PreferenceData;

import java.util.List;

@SuppressLint("NewApi")
public class BluetoothService extends Service {
    public final static String TAG = BluetoothService.class.getSimpleName();
    public final static String serviceUUID = "6e400001-b5a3-f393-e0a9-e50e24dcca9e";
    public final static String writeUUID = "6e400002-b5a3-f393-e0a9-e50e24dcca9e";
    public final static String notifyUUID = "6e400003-b5a3-f393-e0a9-e50e24dcca9e";
    private Handler mActivityHandler = null;
    private Handler mActivityHandler2 = null;
    public BluetoothGatt mBluetoothGatt;

    public static final int STATE_DISCONNECTED = 2000;
    public static final int STATE_CONNECTED = 2001;
    public static final int STATE_CONNECT_FAILED = 2002;
    public static final int READ_SUCCESS = 2003;
    public static final int WRITE_SUCCESS = 2004;
    public static final int NOTIFY_SUCCESS = 2005;

    public void setActivityHandler(Handler mHandler) {
        mActivityHandler = mHandler;
    }

    public void setActivityHandler2(Handler mHandler) {
        mActivityHandler2 = mHandler;
    }

    public void removeActivityHandler(){
        mActivityHandler=null;
    }

    public void removeActivityHandler2(){
        mActivityHandler2=null;
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


    public void connectByAddress(String address) {
        ViseBluetooth.getInstance().setScanTimeout(-1).connectByMac(address, false, new IConnectCallback() {
            @Override
            public void onConnectSuccess(BluetoothGatt gatt, int status) {
                GazelleApplication.isBleConnected=true;
                GazelleApplication.isNormalDisconnet=false;
                mBluetoothGatt = gatt;
                if(mActivityHandler!=null)
                mActivityHandler.sendEmptyMessage(STATE_CONNECTED);
            }

            @Override
            public void onConnectFailure(BleException exception) {
                Logger.e(exception.getDescription());
                GazelleApplication.isBleConnected=false;
                if(exception.getDescription().equals("Timeout Exception Occurred! ")){
                    if(mActivityHandler!=null){
                        mActivityHandler.sendEmptyMessage(STATE_CONNECT_FAILED);
                    }
                    return;
                }
                if(mActivityHandler!=null){
                    mActivityHandler.sendEmptyMessage(STATE_CONNECT_FAILED);
                }
                if(!GazelleApplication.isNormalDisconnet){
                    String mac = PreferenceData.getAddressValue(getApplicationContext());
                    connectByAddress(mac);
                }
            }

            @Override
            public void onDisconnect() {
                GazelleApplication.isBleConnected=false;
                if(mActivityHandler!=null)
                mActivityHandler.sendEmptyMessage(STATE_DISCONNECTED);
                if(!GazelleApplication.isNormalDisconnet){
                    String mac = PreferenceData.getAddressValue(getApplicationContext());
                    connectByAddress(mac);
                }
            }
        });
    }

    public void connectByDevice(BluetoothDevice device){
        ViseBluetooth.getInstance().connect(device, false, new IConnectCallback() {
            @Override
            public void onConnectSuccess(BluetoothGatt gatt, int status) {
                GazelleApplication.isBleConnected=true;
                GazelleApplication.isNormalDisconnet=false;
                mBluetoothGatt = gatt;
                if(mActivityHandler2!=null)
                    mActivityHandler2.sendEmptyMessage(STATE_CONNECTED);
            }

            @Override
            public void onConnectFailure(BleException exception) {
                Logger.e(exception.getDescription());
                GazelleApplication.isBleConnected=false;
                if(exception.getDescription().equals("Timeout Exception Occurred! ")){
                    if(mActivityHandler!=null){
                        mActivityHandler.sendEmptyMessage(STATE_CONNECT_FAILED);
                    }
                    return;
                }
                if(mActivityHandler2!=null)
                    mActivityHandler2.sendEmptyMessage(STATE_CONNECT_FAILED);
                if(!GazelleApplication.isNormalDisconnet){
                    String mac = PreferenceData.getAddressValue(getApplicationContext());
                    connectByAddress(mac);
                }
            }

            @Override
            public void onDisconnect() {
                GazelleApplication.isBleConnected=false;
                if(mActivityHandler2!=null)
                    mActivityHandler2.sendEmptyMessage(STATE_DISCONNECTED);
                if(!GazelleApplication.isNormalDisconnet){
                    String mac = PreferenceData.getAddressValue(getApplicationContext());
                    connectByAddress(mac);
                }
            }
        });
    }

    public BluetoothGattCharacteristic getWriteCharacteristic() {
        BluetoothGattCharacteristic gattCharacteristic = null;
        if (mBluetoothGatt == null)
            return null;
        List<BluetoothGattService> services = mBluetoothGatt.getServices();
        for (int i = 0; i < services.size(); i++) {
            BluetoothGattService service = services.get(i);
            if (service.getUuid().toString().equals(serviceUUID)) {
                List<BluetoothGattCharacteristic> characteristics = service.getCharacteristics();
                for (int j = 0; j < characteristics.size(); j++) {
                    BluetoothGattCharacteristic characteristic = characteristics.get(j);
                    if (characteristic.getUuid().toString().equals(writeUUID)) {
                        gattCharacteristic = characteristic;
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
        for (int i = 0; i < services.size(); i++) {
            BluetoothGattService service = services.get(i);
            if (service.getUuid().toString().equals(serviceUUID)) {
                List<BluetoothGattCharacteristic> characteristics = service.getCharacteristics();
                for (int j = 0; j < characteristics.size(); j++) {
                    BluetoothGattCharacteristic characteristic = characteristics.get(j);
                    if (characteristic.getUuid().toString().equals(notifyUUID)) {
                        gattCharacteristic = characteristic;
                    }
                }
            }
        }
        return gattCharacteristic;
    }


    public void disconnect() {
        ViseBluetooth.getInstance().disconnect();
    }

    public void close() {
        ViseBluetooth.getInstance().close();
        ViseBluetooth.getInstance().clear();
    }

    public void writeCharacteristic(byte[] bytes) {
        ViseBluetooth.getInstance().writeCharacteristic(getWriteCharacteristic(), bytes, new IBleCallback<BluetoothGattCharacteristic>() {
            @Override
            public void onSuccess(BluetoothGattCharacteristic bluetoothGattCharacteristic, int type) {
                if(mActivityHandler!=null)
                mActivityHandler.obtainMessage(WRITE_SUCCESS, bluetoothGattCharacteristic.getValue()).sendToTarget();
            }

            @Override
            public void onFailure(BleException exception) {
                Logger.e(exception.getDescription());
            }
        });
    }

    public void setNotifyCharacteristic() {
        ViseBluetooth.getInstance().enableCharacteristicNotification(getNotifyCharacteristic(), new IBleCallback<BluetoothGattCharacteristic>() {
            @Override
            public void onSuccess(BluetoothGattCharacteristic bluetoothGattCharacteristic, int type) {
                if(mActivityHandler!=null)
                mActivityHandler.obtainMessage(NOTIFY_SUCCESS, bluetoothGattCharacteristic.getValue()).sendToTarget();
            }

            @Override
            public void onFailure(BleException exception) {
                Logger.e(exception.getDescription());
            }
        }, false);
    }


}
