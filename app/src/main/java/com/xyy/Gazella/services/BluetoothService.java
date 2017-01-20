//package com.xyy.Gazella.services;
//
//import android.annotation.SuppressLint;
//import android.app.Service;
//import android.bluetooth.BluetoothAdapter;
//import android.bluetooth.BluetoothDevice;
//import android.bluetooth.BluetoothGatt;
//import android.bluetooth.BluetoothGattCallback;
//import android.bluetooth.BluetoothGattCharacteristic;
//import android.bluetooth.BluetoothGattDescriptor;
//import android.bluetooth.BluetoothGattService;
//import android.bluetooth.BluetoothManager;
//import android.bluetooth.BluetoothProfile;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Binder;
//import android.os.Handler;
//import android.os.IBinder;
//import android.os.Message;
//import android.util.Log;
//
//import java.io.UnsupportedEncodingException;
//import java.util.List;
//
//@SuppressLint("NewApi")
//public class BluetoothService extends Service {
//    public final static String TAG = BluetoothService.class.getSimpleName();
//    public final static String serviceUUID = "6e40ffb0-b5a3-f393-e0a9-e50e24dcca9e";
//    public final static String writeUUID = "6e400002-b5a3-f393-e0a9-e50e24dcca9e";
//    public final static String notifyUUID = "6e400003-b5a3-f393-e0a9-e50e24dcca9e";
//
//    private BluetoothManager mBluetoothManager;
//    private BluetoothAdapter mBluetoothAdapter;
//    public BluetoothGatt mBluetoothGatt;
//    private String mBluetoothDeviceAddress;
//
//    private Handler mActivityHandler = null;
//
//    public static final int STATE_DISCONNECTED = 2000;
//    public static final int STATE_CONNECTED = 2001;
//    public static final int SERVICES_DISCOVERED = 2002;
//    public static final int READ_SUCCESS = 2003;
//    public static final int WRITE_SUCCESS = 2004;
//    public static final int NOTIFY_SUCCESS = 2005;
//
//    public void setActivityHandler(Handler mHandler) {
//        mActivityHandler = mHandler;
//    }
//
//    private final IBinder mBinder = new LocalBinder();
//
//
//    public class LocalBinder extends Binder {
//        public BluetoothService getService() {
//            return BluetoothService.this;
//        }
//    }
//
//    @Override
//    public IBinder onBind(Intent intent) {
//        // TODO Auto-generated method stub
//        return mBinder;
//    }
//
//    @Override
//    public boolean onUnbind(Intent intent) {
//        // TODO Auto-generated method stub
//        close();
//        return super.onUnbind(intent);
//    }
//
//    public void close() {
//        if (mBluetoothGatt == null) {
//            return;
//        }
//        mBluetoothGatt.close();
//        mBluetoothGatt = null;
//    }
//
//    public boolean initialize() {
//        if (mBluetoothManager == null) {
//            mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
//            if (mBluetoothManager == null) {
//                Log.e(TAG, "Unable to initialize BluetoothManager.");
//                return false;
//            }
//        }
//
//        mBluetoothAdapter = mBluetoothManager.getAdapter();
//        if (mBluetoothAdapter == null) {
//            Log.e(TAG, "Unable to obtain a BluetoothAdapter.");
//            return false;
//        }
//
//        return true;
//    }
//
//    public boolean connect(final String address) {
//        if (mBluetoothAdapter == null || address == null) {
//            Log.w(TAG,
//                    "BluetoothAdapter not initialized or unspecified address.");
//            return false;
//        }
//
//        // Previously connected device. Try to reconnect.
////        if (mBluetoothDeviceAddress != null
////                && address.equals(mBluetoothDeviceAddress)
////                && mBluetoothGatt != null) {
////            Log.d(TAG,
////                    "Trying to use an existing mBluetoothGatt for connection.");
////            if (mBluetoothGatt.connect()) {
////                return true;
////            } else {
////                return false;
////            }
////        }
//
//        final BluetoothDevice device = mBluetoothAdapter
//                .getRemoteDevice(address);
//        if (device == null) {
//            Log.w(TAG, "Device not found.  Unable to connect.");
//            return false;
//        }
//
//        mBluetoothGatt = device.connectGatt(this, false, mGattCallback);
//        Log.d(TAG, "Trying to create a new connection.");
//        mBluetoothDeviceAddress = address;
//        return true;
//    }
//
//    public void disconnect() {
//        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
//            Log.w(TAG, "BluetoothAdapter not initialized");
//            return;
//        }
//        mBluetoothGatt.disconnect();
//    }
//
//    BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
//        public void onConnectionStateChange(BluetoothGatt gatt, int status,
//                                            int newState) {
//            System.out.println("=======status:" + status);
//            if (status==133){
//                connect(mBluetoothDeviceAddress);
//            }
//            if (newState == BluetoothProfile.STATE_CONNECTED) {
//                mActivityHandler.obtainMessage(STATE_CONNECTED).sendToTarget();
//                Log.i(TAG, "Connected to GATT server.");
//                // Attempts to discover services after successful connection.
//                Log.i(TAG, "Attempting to start service discovery:"
//                        + mBluetoothGatt.discoverServices());
//            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
//                mActivityHandler.obtainMessage(STATE_DISCONNECTED).sendToTarget();
//                Log.i(TAG, "Disconnected from GATT server.");
//            }
//        }
//
//        ;
//
//        @Override
//        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
//            if (status == BluetoothGatt.GATT_SUCCESS) {
//                Log.w(TAG, "onServicesDiscovered successed " );
//
//                Message msg = Message.obtain(mActivityHandler, SERVICES_DISCOVERED);
//                msg.sendToTarget();
//            } else {
//                Log.w(TAG, "onServicesDiscovered received: " + status);
//            }
//        }
//
//        public void onCharacteristicChanged(BluetoothGatt gatt,
//                                            BluetoothGattCharacteristic characteristic) {
//            final byte[] data = characteristic.getValue();
//            if (data != null && data.length > 0) {
//                final StringBuilder stringBuilder = new StringBuilder(
//                        data.length);
//                for (byte byteChar : data)
//                    stringBuilder.append(String.format("%02X ", byteChar));
//
//                try {
//                    System.out.println("notify" + new String(data,"ascii") + "\n"
//                            + stringBuilder.toString());
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
//
//                Message msg = Message.obtain(mActivityHandler, NOTIFY_SUCCESS, characteristic.getValue());
//                msg.sendToTarget();
//            }        }
//
//        ;
//
//        public void onCharacteristicRead(BluetoothGatt gatt,
//                                         BluetoothGattCharacteristic characteristic, int status) {
//            Message msg = Message.obtain(mActivityHandler, READ_SUCCESS);
//            msg.sendToTarget();
//        }
//
//        ;
//
//        public void onCharacteristicWrite(BluetoothGatt gatt,
//                                          BluetoothGattCharacteristic characteristic, int status) {
//            final byte[] data = characteristic.getValue();
//            if (data != null && data.length > 0) {
//                final StringBuilder stringBuilder = new StringBuilder(
//                        data.length);
//                for (byte byteChar : data)
//                    stringBuilder.append(String.format("%02X ", byteChar));
//
//                System.out.println("write" + stringBuilder.toString());
//            }
//            Message msg = Message.obtain(mActivityHandler, WRITE_SUCCESS,characteristic.getValue());
//            msg.sendToTarget();
//        }
//
//        ;
//
//        @Override
//        public void onDescriptorWrite(BluetoothGatt gatt,
//                                      BluetoothGattDescriptor descriptor, int status) {
//
//            System.out.println("onDescriptorWriteonDescriptorWrite = " + status
//                    + ", descriptor =" + descriptor.getUuid().toString());
//        }
//
//        @Override
//        public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
//            System.out.println("rssi = " + rssi);
//        }
//    };
//
//    public void readCharacteristic(BluetoothGattCharacteristic characteristic) {
//        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
//            Log.w(TAG, "BluetoothAdapter not initialized");
//            return;
//        }
//        mBluetoothGatt.readCharacteristic(characteristic);
//    }
//
//    public void writeCharateristic(BluetoothGattCharacteristic characteristic) {
//        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
//            Log.w(TAG, "BluetoothAdapter not initialized");
//            return;
//        }
//        mBluetoothGatt.writeCharacteristic(characteristic);
//    }
//
//    public void setCharacteristicNotification(
//            BluetoothGattCharacteristic characteristic, boolean enabled) {
//        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
//            Log.w(TAG, "BluetoothAdapter not initialized");
//            return;
//        }
//        mBluetoothGatt.setCharacteristicNotification(characteristic, enabled);
//        List<BluetoothGattDescriptor> descriptors = characteristic.getDescriptors();
//        for (int i = 0; i < descriptors.size(); i++) {
//            BluetoothGattDescriptor descriptor = descriptors.get(i);
//            if (descriptor != null) {
//                descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
//                mBluetoothGatt.writeDescriptor(descriptor);
//            }
//        }
//    }
//
//    public List<BluetoothGattService> getSupportedGattServices() {
//        if (mBluetoothGatt == null)
//            return null;
//
//        return mBluetoothGatt.getServices();
//    }
//
//    public BluetoothGattCharacteristic getWriteCharacteristic() {
//        BluetoothGattCharacteristic gattCharacteristic = null;
//        if (mBluetoothGatt == null)
//            return null;
//        List<BluetoothGattService> services = mBluetoothGatt.getServices();
//        for (int i = 0; i<services.size();i++){
//            BluetoothGattService service = services.get(i);
//            if(service.getUuid().toString().equals(serviceUUID)){
//                List<BluetoothGattCharacteristic> characteristics = service.getCharacteristics();
//                for(int j = 0; j<characteristics.size();j++){
//                    BluetoothGattCharacteristic characteristic = characteristics.get(j);
//                    if(characteristic.getUuid().toString().equals(writeUUID)){
//                        gattCharacteristic=characteristic;
//                    }
//                }
//            }
//        }
//        return gattCharacteristic;
//    }
//
//    public BluetoothGattCharacteristic getNotifyCharacteristic() {
//        BluetoothGattCharacteristic gattCharacteristic = null;
//        if (mBluetoothGatt == null)
//            return null;
//        List<BluetoothGattService> services = mBluetoothGatt.getServices();
//        for (int i = 0; i<services.size();i++){
//            BluetoothGattService service = services.get(i);
//            if(service.getUuid().toString().equals(serviceUUID)){
//                List<BluetoothGattCharacteristic> characteristics = service.getCharacteristics();
//                for(int j = 0; j<characteristics.size();j++){
//                    BluetoothGattCharacteristic characteristic = characteristics.get(j);
//                    if(characteristic.getUuid().toString().equals(notifyUUID)){
//                        gattCharacteristic=characteristic;
//                    }
//                }
//            }
//        }
//        return gattCharacteristic;
//    }
//}
