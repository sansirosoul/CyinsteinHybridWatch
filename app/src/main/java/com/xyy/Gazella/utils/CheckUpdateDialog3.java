package com.xyy.Gazella.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.vise.baseble.ViseBluetooth;
import com.vise.baseble.callback.scan.PeriodScanCallback;
import com.vise.baseble.model.BluetoothLeDevice;
import com.xyy.Gazella.activity.UpdateHardware;
import com.xyy.Gazella.services.DfuService;
import com.xyy.Gazella.view.NumberProgressBar;
import com.ysp.hybridtwatch.R;
import com.ysp.newband.BaseActivity;
import com.ysp.newband.GazelleApplication;
import com.ysp.newband.PreferenceData;
import com.ysp.newband.WacthSeries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import no.nordicsemi.android.dfu.DfuProgressListener;
import no.nordicsemi.android.dfu.DfuServiceInitiator;
import no.nordicsemi.android.dfu.DfuServiceListenerHelper;

/**
 * Created by Administrator on 2016/10/14.
 */

public class CheckUpdateDialog3 extends BaseActivity {
    private Context context;
    private NumberProgressBar numberbar;
    private BleUtils bleUtils;

    @Override
    protected void onResume() {
        super.onResume();
        DfuServiceListenerHelper.registerProgressListener(context, mDfuProgressListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        DfuServiceListenerHelper.unregisterProgressListener(context, mDfuProgressListener);
    }

    private void scanDevices() {
        ViseBluetooth.getInstance().setScanTimeout(-1).startScan(new PeriodScanCallback() {
            @Override
            public void scanTimeout() {

            }

            @Override
            public void onDeviceFound(BluetoothLeDevice bluetoothLeDevice) {
                 if(bluetoothLeDevice.getName()!=null){
                     if (bluetoothLeDevice.getName().equals("DfuTarg")) {
                         ViseBluetooth.getInstance().stopLeScan(this);
                         new DfuServiceInitiator(bluetoothLeDevice.getAddress()).setDisableNotification(true).setZip(R.raw.ct001v39).start(context, DfuService.class);
                     }
                 }
            }
        });
    }

    @Override
    protected void onWriteReturn(byte[] bytes) {
        super.onWriteReturn(bytes);
        if(HexString.bytesToHex(bytes).equals("485907DF00E6D3")){
            GazelleApplication.isNormalDisconnet=true;
            scanDevices();
        }
    }

    Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            OTA();
        }
    });

    Thread dateThread = new Thread(new Runnable() {
        @Override
        public void run() {
            handUpdateData();
        }
    });

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==101){
                numberbar.setMax(updateLength / 2048);
                numberbar.setProgress(Upadatecount);
                Upadatecount++;
            }
        }
    };

    @Override
    protected void onReadReturn(byte[] bytes) {
        if (bleUtils.returnOTAValue(bytes)) {
            //返回蓝牙OTA固件更新指令
            thread.start();
        } else if (bleUtils.returnOTAUpdateValue(bytes) != 0) {
            //返回蓝牙OTA固件更新进度值
            int num = bleUtils.returnOTAUpdateValue(bytes);
            if (updateLength != 0 && num != 0) {
                isReturn = true;
                Message.obtain(handler,101,num).sendToTarget();
            }

        } else if (bleUtils.returnOTAUUpdateOk(bytes) != -1) {
            int updateok = bleUtils.returnOTAUUpdateOk(bytes);
            switch (updateok) {
                case 0:
                    Logger.e("更新成功");
                    Intent intent = new Intent(context, CheckUpdateDialog4.class);
                    startActivity(intent);
                    finish();
                    break;
                case 1:
                    Toast.makeText(context, getResources().getString(R.string.update_failed_again), Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                case 2:
                    Toast.makeText(context, getResources().getString(R.string.update_failed_again), Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                case 3:
                    Toast.makeText(context, getResources().getString(R.string.update_failed_again), Toast.LENGTH_SHORT).show();
                    finish();
                    break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.check_update_dialog3);
        //禁止自动锁屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setFinishOnTouchOutside(false);
        context = this;
        String address = PreferenceData.getAddressValue(context);
        if (address != null && !address.equals("")) {
            bleUtils = new BleUtils();
            if (GazelleApplication.isBleConnected) {
                String type = PreferenceData.getDeviceType(this);
                System.out.println(type+">>>>>>>>>>>");
                if (type.equals(WacthSeries.CT003)||type.equals(WacthSeries.CT002)||type.equals("CT012")||type.equals("CT-002")) {
                    writeCharacteristic(bleUtils.startDfu());
                } else if(type.equals(WacthSeries.EM001)||type.equals(WacthSeries.EM002)||type.equals(WacthSeries.EM003)) {
                    setNotifyCharacteristic();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dateThread.start();
                        }
                    }, 300);
                }
            }
        }
        numberbar = (NumberProgressBar) findViewById(R.id.numberbar);
    }

    private DfuProgressListener mDfuProgressListener = new DfuProgressListener() {
        @Override
        public void onDeviceConnecting(String deviceAddress) {
            Log.d("OTA", "----------onDeviceConnecting------------");
        }

        @Override
        public void onDeviceConnected(String deviceAddress) {
            Log.d("OTA", "----------onDeviceConnected------------");
        }

        @Override
        public void onDfuProcessStarting(String deviceAddress) {
            Log.d("OTA", "----------onDfuProcessStarting------------");
        }

        @Override
        public void onDfuProcessStarted(String deviceAddress) {
            Log.d("OTA", "----------onDfuProcessStarted------------");
        }

        @Override
        public void onEnablingDfuMode(String deviceAddress) {
            Log.d("OTA", "----------onEnablingDfuMode------------");
        }

        @Override
        public void onProgressChanged(String deviceAddress, int percent, float speed, float avgSpeed, int currentPart, int partsTotal) {
            numberbar.setProgress(percent);
        }

        @Override
        public void onFirmwareValidating(String deviceAddress) {
            Log.d("OTA", "----------onFirmwareValidating------------");
        }

        @Override
        public void onDeviceDisconnecting(String deviceAddress) {
            Log.d("OTA", "----------onDeviceDisconnecting------------");
        }

        @Override
        public void onDeviceDisconnected(String deviceAddress) {
            Log.d("OTA", "----------onDeviceDisconnected------------");
        }

        @Override
        public void onDfuCompleted(String deviceAddress) {
            GazelleApplication.isNormalDisconnet = true;
            Intent intent = new Intent(context, CheckUpdateDialog4.class);
            startActivity(intent);
            finish();
        }

        @Override
        public void onDfuAborted(String deviceAddress) {
            Toast.makeText(context, getResources().getString(R.string.update_failed_again), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(CheckUpdateDialog3.this, UpdateHardware.class);
            startActivity(intent);
            finish();
        }

        @Override
        public void onError(String deviceAddress, int error, int errorType, String message) {
            Toast.makeText(context, getResources().getString(R.string.update_failed_again), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(CheckUpdateDialog3.this, UpdateHardware.class);
            startActivity(intent);
            finish();
        }
    };

    private int updateLength;
    private int Upadatecount;
    private int crcLength;
    List<String[]> bigData = new ArrayList<>();

    private void handUpdateData() {
        String fw = PreferenceData.getDeviceFwvValue(this);
        String t = fw.substring(fw.indexOf(".")+1);
        String type = PreferenceData.getDeviceType(this);
        String[] strs;
        if(type.equals(WacthSeries.EM003)){
            if(t.equals("1B")){
                strs = new SomeUtills().readOTABin(this,"em003_1b_58.bin");
            }else if(t.equals("2A")){
                strs = new SomeUtills().readOTABin(this,"em003_2a_58.bin");
            }else{
                strs = new SomeUtills().readOTABin(this,"em003_3a_58.bin");
            }
        }else {
            strs = new SomeUtills().readOTABin(this,"em003_3a_58.bin");
        }
        int size = strs.length / 2048 + 1;
        for (int i = 0; i < size; i++) {
            String[] ss;
            if (i == size - 1) {
                ss = new String[strs.length % 2048];
            } else {
                ss = new String[2048];
            }
            bigData.add(ss);
        }

        StringBuffer WriteLength = new StringBuffer();
        for (int i = 0; i < strs.length; i++) {
            String str = strs[i];
            WriteLength.append(str);
            for (int j = 0; j < size; j++) {
                if (i / 2048 == j) {
                    bigData.get(j)[i % 2048] = str;
                }
            }
        }
        byte[] Bytes = bleUtils.HexString2Bytes(WriteLength.toString());
        updateLength=Bytes.length;
        crcLength = bleUtils.OTACrc(Bytes);
        prepareOTA();
    }

    //EM系列发送升级命令
    private void prepareOTA() {
        writeCharacteristic(bleUtils.startOTA(updateLength, crcLength));
    }

    boolean isReturn = false;

    private void OTA() {
        try {
            for (int i = 0; i < bigData.size(); i++) {
                String[] strs = bigData.get(i);
                int k = 0;
                int FaCount = 0;
                boolean isTrue = true;
                StringBuffer sb = new StringBuffer();
                StringBuffer newsb = new StringBuffer();
                for (int j = 0; j < strs.length; j++) {
                    String count = strs[j];
                    if (isTrue) {
                        if (k != 20) {
                            sb.append(count);
                            k++;
                        } else {
                            byte[] Bytes = bleUtils.HexString2Bytes(sb.toString());
                            Thread.sleep(20);
                            writeCharacteristic(Bytes);
                            FaCount += Bytes.length;
                            sb.setLength(0);
                            sb.append(count);
                            k = 0;
                            isTrue = false;
                        }
                    } else {
                        if (k != 19) {
                            sb.append(count);
                            k++;
                        } else {
                            byte[] Bytes = bleUtils.HexString2Bytes(sb.toString());
                            Thread.sleep(30);
                            writeCharacteristic(Bytes);
                            FaCount += Bytes.length;
                            sb.setLength(0);
                            sb.append(count);
                            k = 0;
                        }
                    }
                }
                if (FaCount != strs.length) {
                    newsb.setLength(0);
                    StringBuffer WriteLengthb = new StringBuffer();
                    String[] newData = Arrays.copyOfRange(strs, FaCount, strs.length);

                    for (int m = 0; m < newData.length; m++) {
                        String CountLength = newData[m];
                        WriteLengthb.append(CountLength);
                    }

                    byte[] Byteslens = bleUtils.HexString2Bytes(WriteLengthb.toString());
                    for (int n = 0; n < Byteslens.length; n++) {
                        String count = newData[n];
                        newsb.append(count);
                    }

                    byte[] Bytes = bleUtils.HexString2Bytes(newsb.toString());
                    Thread.sleep(10);
                    writeCharacteristic(Bytes);
                    isReturn = false;
                    while (!isReturn) {
                        try {
                            Thread.sleep(3);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
