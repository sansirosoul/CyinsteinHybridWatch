package com.xyy.Gazella.activity;

import android.bluetooth.BluetoothGattCharacteristic;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;
import com.orhanobut.logger.Logger;
import com.polidea.rxandroidble.RxBleConnection;
import com.polidea.rxandroidble.RxBleDevice;
import com.xyy.Gazella.services.BluetoothService;
import com.xyy.Gazella.utils.BleUtils;
import com.xyy.Gazella.utils.HexString;
import com.xyy.Gazella.utils.SomeUtills;
import com.xyy.Gazella.view.NumberProgressBar;
import com.xyy.model.SleepData;
import com.xyy.model.StepData;
import com.ysp.hybridtwatch.R;
import com.ysp.newband.BaseActivity;
import com.ysp.newband.GazelleApplication;
import com.ysp.newband.PreferenceData;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;

/**
 * Created by Administrator on 2016/11/14.
 */

public class BleTest extends BaseActivity {
    BluetoothGattCharacteristic writeCharacteristic;
    BluetoothGattCharacteristic notifyCharacteristic;
    BleUtils bleUtils;
    @BindView(R.id.btn1)
    Button btn1;
    @BindView(R.id.btn2)
    Button btn2;
    @BindView(R.id.btn3)
    Button btn3;
    @BindView(R.id.btn4)
    Button btn4;
    @BindView(R.id.btn5)
    Button btn5;
    @BindView(R.id.btn6)
    Button btn6;
    @BindView(R.id.btn7)
    Button btn7;
    @BindView(R.id.btn8)
    Button btn8;
    @BindView(R.id.btn9)
    Button btn9;
    @BindView(R.id.writetext)
    TextView write;
    @BindView(R.id.btn10)
    Button btn10;
    @BindView(R.id.btn11)
    Button btn11;
    @BindView(R.id.btn12)
    Button btn12;
    @BindView(R.id.btn13)
    Button btn13;
    @BindView(R.id.btn14)
    Button btn14;
    @BindView(R.id.btn15)
    Button btn15;
    @BindView(R.id.btn16)
    Button btn16;
    @BindView(R.id.btn17)
    Button btn17;
    @BindView(R.id.btn18)
    Button btn18;
    @BindView(R.id.btn19)
    Button btn19;
    @BindView(R.id.btn20)
    Button btn20;
    @BindView(R.id.btn21)
    Button btn21;
    @BindView(R.id.btn22)
    Button btn22;
    @BindView(R.id.btn23)
    Button btn23;
    @BindView(R.id.notify)
    TextView notify;
    @BindView(R.id.forward)
    RadioButton forward;
    @BindView(R.id.back)
    RadioButton back;
    @BindView(R.id.radiogroup)
    RadioGroup radiogroup;
    @BindView(R.id.step)
    EditText step;
    @BindView(R.id.btn24)
    Button btn24;
    @BindView(R.id.btn25)
    Button btn25;
    @BindView(R.id.btn26)
    Button btn26;

    @BindView(R.id.btn27)
    Button btn27;
    @BindView(R.id.btn28)
    Button btn28;
    @BindView(R.id.daynum)
    EditText daynum;
    @BindView(R.id.day01)
    TextView day01;
    @BindView(R.id.day02)
    TextView day02;
    @BindView(R.id.day03)
    TextView day03;
    @BindView(R.id.day04)
    TextView day04;
    @BindView(R.id.day05)
    TextView day05;
    @BindView(R.id.day06)
    TextView day06;
    @BindView(R.id.day07)
    TextView day07;
    @BindView(R.id.updatebar)
    NumberProgressBar updatebar;
    private Observable<RxBleConnection> connectionObservable;
    private RxBleDevice bleDevice;
    private static final String TAG = BleTest.class.getName();
    private int direction = 0;
    private   int updateLength;
    private int Upadatecount;


    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.test);
        ButterKnife.bind(this);
        bleUtils = new BleUtils();
        String address = PreferenceData.getAddressValue(this);
        bleDevice = GazelleApplication.getRxBleClient(this).getBleDevice(address);
        connectionObservable = getRxObservable(this);

        Notify(connectionObservable);
//        writeCharacteristic=GazelleApplication.mBluetoothService.getWriteCharacteristic();
//        notifyCharacteristic=GazelleApplication.mBluetoothService.getNotifyCharacteristic();
//        if(notifyCharacteristic!=null)GazelleApplication.mBluetoothService.setCharacteristicNotification(notifyCharacteristic,true);

        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == forward.getId()) {
                    direction = 1;
                } else if (i == back.getId()) {
                    direction = 2;
                }
            }
        });
    }

    @Override
    protected void onNotifyReturn(int type, String str) {
        super.onNotifyReturn(type, str);
        if (type == 2) {
            Notify(connectionObservable);
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case BluetoothService.WRITE_SUCCESS:
                    if (msg.obj != null) {
                        byte[] bytes = (byte[]) msg.obj;
                        write.setText(HexString.bytesToHex(bytes));
                        notify.setText("");
                    }
                    break;
                case BluetoothService.NOTIFY_SUCCESS:
                    if (msg.obj != null) {
                        byte[] bytes = (byte[]) msg.obj;
                        notify.setText(HexString.bytesToHex(bytes));
                    }
                    break;
                case BluetoothService.SERVICES_DISCOVERED:
                    writeCharacteristic = GazelleApplication.mBluetoothService.getWriteCharacteristic();
                    notifyCharacteristic = GazelleApplication.mBluetoothService.getNotifyCharacteristic();
                    if (notifyCharacteristic != null)
                        GazelleApplication.mBluetoothService.setCharacteristicNotification(notifyCharacteristic, true);
                    break;
            }
        }
    };
    private StringBuffer stringBuffer = new StringBuffer();
    private boolean isclick;

    @Override
    protected void onReadReturn(byte[] bytes) {
        super.onReadReturn(bytes);
        if (bleUtils.returnTodayStep(bytes) != null) {
            StepData data = bleUtils.returnTodayStep(bytes);
            notify.setText(data.getYear() + "-" + data.getMonth() + "-" + data.getDay() + "步数" + data.getStep());
        } else if (bleUtils.returnDeviceSN(bytes) != null) {
            notify.setText(bleUtils.returnDeviceSN(bytes));
        } else if (bleUtils.returnFWVer(bytes) != null) {
            notify.setText(bleUtils.returnFWVer(bytes));
        } else if (bleUtils.returnBatteryValue(bytes) != null) {
            notify.setText(bleUtils.returnBatteryValue(bytes) + "%");
        } else if (bleUtils.returnOTAValue(bytes)) {
            //返回蓝牙OTA固件更新指令
            OTA();
        } else if (bleUtils.returnOTAUpdateValue(bytes) != 0) {
            //返回蓝牙OTA固件更新进度值
            int num= Integer.valueOf( bleUtils.returnOTAUpdateValue(bytes));
            if(updateLength!=0&&num!=0){
//              NumberFormat numberFormat = NumberFormat.getInstance();
//             numberFormat.setMaximumFractionDigits(0);
//            String result = numberFormat.format((float) updateLength / (float) num * 100);
                updatebar.setMax(updateLength/2048);
                updatebar.setProgress(Upadatecount);
                Upadatecount++;
            }
        } else if(bleUtils.returnOTAUUpdateOk(bytes)!=-1){
           int updateok= bleUtils.returnOTAUUpdateOk(bytes);
            switch (updateok){
                case 0:
                    showToatst(BleTest.this,"更新成功");
                    break;
                case 1:
                    showToatst(BleTest.this,"更新失败>>> (CRC错误)");
                    break;
                case 2:
                    showToatst(BleTest.this,"更新失败>>> (数量错误)");
                    break;
                case 3:
                    showToatst(BleTest.this,"更新失败>>> (更新超时)");
                    break;
            }

        } else if(bleUtils.returnSleepData(bytes) != null){

            List<SleepData> sleepdata = bleUtils.returnSleepData(bytes);

            for (int i = 0; i < sleepdata.size(); i++) {
                int count = sleepdata.get(i).getCount();
                int time = sleepdata.get(i).getTime();
                if (count <= 5 && count >= 0) {
                    stringBuffer.append("总包数>>>  " + String.valueOf(sleepdata.get(i).getSums()) + "  " +
                            "现在第几个包>>>  " + String.valueOf(sleepdata.get(i).getCount()) + "  " +
                            "日期>>>  " + String.valueOf(sleepdata.get(i).getDate()) + "  " +
                            "睡眠状态>>>  " + String.valueOf(sleepdata.get(i).getStatus()) + "  " +
                            "睡眠质量>>>  " + String.valueOf(sleepdata.get(i).getQuality()) + "  " +
                            " 时间 >>>  " + String.valueOf(sleepdata.get(i).getTime()) + "\r\n");
                    day01.setText(stringBuffer.toString());
                    if (count == 5 && time == 23) stringBuffer.setLength(0);
                }
                if (count <= 11 && count >= 6) {
                    stringBuffer.append("总包数>>>  " + String.valueOf(sleepdata.get(i).getSums()) + "  " +
                            "现在第几个包>>>  " + String.valueOf(sleepdata.get(i).getCount()) + "  " +
                            "日期>>>  " + String.valueOf(sleepdata.get(i).getDate()) + "  " +
                            "睡眠状态>>>  " + String.valueOf(sleepdata.get(i).getStatus()) + "  " +
                            "睡眠质量>>>  " + String.valueOf(sleepdata.get(i).getQuality()) + "  " +
                            " 时间 >>>  " + String.valueOf(sleepdata.get(i).getTime()) + "\r\n");
                    day02.setText(stringBuffer.toString());
                    if (count == 11 && time == 23) stringBuffer.setLength(0);
                }
                if (count <= 17 && count >= 12) {
                    stringBuffer.append("总包数>>>  " + String.valueOf(sleepdata.get(i).getSums()) + "  " +
                            "现在第几个包>>>  " + String.valueOf(sleepdata.get(i).getCount()) + "  " +
                            "日期>>>  " + String.valueOf(sleepdata.get(i).getDate()) + "  " +
                            "睡眠状态>>>  " + String.valueOf(sleepdata.get(i).getStatus()) + "  " +
                            "睡眠质量>>>  " + String.valueOf(sleepdata.get(i).getQuality()) + "  " +
                            " 时间 >>>  " + String.valueOf(sleepdata.get(i).getTime()) + "\r\n");
                    day03.setText(stringBuffer.toString());
                    if (count == 17 && time == 23) stringBuffer.setLength(0);
                }
                if (count <= 23 && count >= 18) {
                    stringBuffer.append("总包数>>>  " + String.valueOf(sleepdata.get(i).getSums()) + "  " +
                            "现在第几个包>>>  " + String.valueOf(sleepdata.get(i).getCount()) + "  " +
                            "日期>>>  " + String.valueOf(sleepdata.get(i).getDate()) + "  " +
                            "睡眠状态>>>  " + String.valueOf(sleepdata.get(i).getStatus()) + "  " +
                            "睡眠质量>>>  " + String.valueOf(sleepdata.get(i).getQuality()) + "  " +
                            " 时间 >>>  " + String.valueOf(sleepdata.get(i).getTime()) + "\r\n");
                    day04.setText(stringBuffer.toString());
                    if (count == 23 && time == 23) stringBuffer.setLength(0);
                }
                if (count <= 29 && count >= 24) {
                    stringBuffer.append("总包数>>>  " + String.valueOf(sleepdata.get(i).getSums()) + "  " +
                            "现在第几个包>>>  " + String.valueOf(sleepdata.get(i).getCount()) + "  " +
                            "日期>>>  " + String.valueOf(sleepdata.get(i).getDate()) + "  " +
                            "睡眠状态>>>  " + String.valueOf(sleepdata.get(i).getStatus()) + "  " +
                            "睡眠质量>>>  " + String.valueOf(sleepdata.get(i).getQuality()) + "  " +
                            " 时间 >>>  " + String.valueOf(sleepdata.get(i).getTime()) + "\r\n");
                    day05.setText(stringBuffer.toString());
                    if (count == 29 && time == 23) stringBuffer.setLength(0);
                }
                if (count <= 35 && count >= 30) {
                    stringBuffer.append("总包数>>>  " + String.valueOf(sleepdata.get(i).getSums()) + "  " +
                            "现在第几个包>>>  " + String.valueOf(sleepdata.get(i).getCount()) + "  " +
                            "日期>>>  " + String.valueOf(sleepdata.get(i).getDate()) + "  " +
                            "睡眠状态>>>  " + String.valueOf(sleepdata.get(i).getStatus()) + "  " +
                            "睡眠质量>>>  " + String.valueOf(sleepdata.get(i).getQuality()) + "  " +
                            " 时间 >>>  " + String.valueOf(sleepdata.get(i).getTime()) + "\r\n");
                    day06.setText(stringBuffer.toString());
                    if (count == 35 && time == 23) stringBuffer.setLength(0);
                }
                if (count <= 41 && count >= 36) {
                    stringBuffer.append("总包数>>>  " + String.valueOf(sleepdata.get(i).getSums()) + "  " +
                            "现在第几个包>>>  " + String.valueOf(sleepdata.get(i).getCount()) + "  " +
                            "日期>>>  " + String.valueOf(sleepdata.get(i).getDate()) + "  " +
                            "睡眠状态>>>  " + String.valueOf(sleepdata.get(i).getStatus()) + "  " +
                            "睡眠质量>>>  " + String.valueOf(sleepdata.get(i).getQuality()) + "  " +
                            " 时间 >>>  " + String.valueOf(sleepdata.get(i).getTime()) + "\r\n");
                    day07.setText(stringBuffer.toString());
                    if (count == 41 && time == 23) stringBuffer.setLength(0);
                }
            }
        } else if (bleUtils.returnStepData(bytes) != null) {
            //返回7天计步数据
            List<StepData> data = bleUtils.returnStepData(bytes);
            for (int i = 0; i < data.size(); i++) {
                int count = data.get(i).getCount();
                int time = data.get(i).getTime();
                if (count <= 5 && count >= 0) {
                    stringBuffer.append("总包数>>>  " + String.valueOf(data.get(i).getSums()) + "  " +
                            "现在第几个包>>>  " + String.valueOf(data.get(i).getCount()) + "  " +
                            "日期>>>  " + String.valueOf(data.get(i).getDay()) + "  " +
                            "步数>>>  " + String.valueOf(data.get(i).getStep()) + "  " +
                            " 时间 >>>  " + String.valueOf(data.get(i).getTime()) + "\r\n");
                    day01.setText(stringBuffer.toString());
                    if (count == 5 && time == 23) stringBuffer.setLength(0);
                }
                if (count <= 11 && count >= 6) {
                    stringBuffer.append("总包数>>>  " + String.valueOf(data.get(i).getSums()) + "  " +
                            "现在第几个包>>>  " + String.valueOf(data.get(i).getCount()) + "  " +
                            "日期>>>  " + String.valueOf(data.get(i).getDay()) + "  " +
                            "步数>>>  " + String.valueOf(data.get(i).getStep()) + "  " +
                            " 时间 >>>  " + String.valueOf(data.get(i).getTime()) + "\r\n");
                    day02.setText(stringBuffer.toString());
                    if (count == 11 && time == 23) stringBuffer.setLength(0);
                }
                if (count <= 17 && count >= 12) {
                    stringBuffer.append("总包数>>>  " + String.valueOf(data.get(i).getSums()) + "  " +
                            "现在第几个包>>>  " + String.valueOf(data.get(i).getCount()) + "  " +
                            "日期>>>  " + String.valueOf(data.get(i).getDay()) + "  " +
                            "步数>>>  " + String.valueOf(data.get(i).getStep()) + "  " +
                            " 时间 >>>  " + String.valueOf(data.get(i).getTime()) + "\r\n");
                    day03.setText(stringBuffer.toString());
                    if (count == 17 && time == 23) stringBuffer.setLength(0);
                }
                if (count <= 23 && count >= 18) {
                    stringBuffer.append("总包数>>>  " + String.valueOf(data.get(i).getSums()) + "  " +
                            "现在第几个包>>>  " + String.valueOf(data.get(i).getCount()) + "  " +
                            "日期>>>  " + String.valueOf(data.get(i).getDay()) + "  " +
                            "步数>>>  " + String.valueOf(data.get(i).getStep()) + "  " +
                            " 时间 >>>  " + String.valueOf(data.get(i).getTime()) + "\r\n");
                    day04.setText(stringBuffer.toString());
                    if (count == 23 && time == 23) stringBuffer.setLength(0);
                }
                if (count <= 29 && count >= 24) {
                    stringBuffer.append("总包数>>>  " + String.valueOf(data.get(i).getSums()) + "  " +
                            "现在第几个包>>>  " + String.valueOf(data.get(i).getCount()) + "  " +
                            "日期>>>  " + String.valueOf(data.get(i).getDay()) + "  " +
                            "步数>>>  " + String.valueOf(data.get(i).getStep()) + "  " +
                            " 时间 >>>  " + String.valueOf(data.get(i).getTime()) + "\r\n");
                    day05.setText(stringBuffer.toString());
                    if (count == 29 && time == 23) stringBuffer.setLength(0);
                }
                if (count <= 35 && count >= 30) {
                    stringBuffer.append("总包数>>>  " + String.valueOf(data.get(i).getSums()) + "  " +
                            "现在第几个包>>>  " + String.valueOf(data.get(i).getCount()) + "  " +
                            "日期>>>  " + String.valueOf(data.get(i).getDay()) + "  " +
                            "步数>>>  " + String.valueOf(data.get(i).getStep()) + "  " +
                            " 时间 >>>  " + String.valueOf(data.get(i).getTime()) + "\r\n");
                    day06.setText(stringBuffer.toString());
                    if (count == 35 && time == 23) stringBuffer.setLength(0);
                }
                if (count <= 41 && count >= 36) {
                    stringBuffer.append("总包数>>>  " + String.valueOf(data.get(i).getSums()) + "  " +
                            "现在第几个包>>>  " + String.valueOf(data.get(i).getCount()) + "  " +
                            "日期>>>  " + String.valueOf(data.get(i).getDay()) + "  " +
                            "步数>>>  " + String.valueOf(data.get(i).getStep()) + "  " +
                            " 时间 >>>  " + String.valueOf(data.get(i).getTime()) + "\r\n");
                    day07.setText(stringBuffer.toString());
                    if (count == 41 && time == 23) stringBuffer.setLength(0);
                }
            }
        } else {
            notify.setText(HexString.bytesToHex(bytes));
        }
    }

    @Override
    protected void onWriteReturn(byte[] bytes) {
        super.onWriteReturn(bytes);


        write.setText(HexString.bytesToHex(bytes));
        notify.setText("");
    }

    @OnClick({R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn7, R.id.btn6, R.id.btn8, R.id.btn9, R.id.btn10, R.id.btn11, R.id.btn12, R.id.btn13,
            R.id.btn14, R.id.btn15, R.id.btn16, R.id.btn17, R.id.btn18, R.id.btn19, R.id.btn20, R.id.btn21, R.id.btn22, R.id.btn23, R.id.btn24, R.id.btn25, R.id.btn26})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                Write(bleUtils.getDeviceSN(), connectionObservable);
                break;
            case R.id.btn2:
                Write(bleUtils.sendMessage(1, 1, 0, 0, 0, 0), connectionObservable);
                break;
            case R.id.btn3:
                Calendar calendar = Calendar.getInstance();
                System.out.println(calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.DAY_OF_MONTH) + "-"
                        + calendar.get(Calendar.HOUR_OF_DAY) + "-" + calendar.get(Calendar.MINUTE) + "-" + calendar.get(Calendar.SECOND));
                Write(bleUtils.setWatchDateAndTime(1, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH),
                        calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND)), connectionObservable);
                break;
            case R.id.btn4:
                Write(bleUtils.setWatchAlarm(1, 0, 12, 0, 1, 1, "", 1), connectionObservable);
                break;
            case R.id.btn5:
                Write(bleUtils.getFWVer(), connectionObservable);
                break;
            case R.id.btn6:
                Write(bleUtils.setDeviceName("CT003"), connectionObservable);
                break;
            case R.id.btn7:
                Write(bleUtils.getDeviceName(), connectionObservable);
                break;
            case R.id.btn8:
                Write(bleUtils.setSystemType(), connectionObservable);
                break;
            case R.id.btn9:
                Write(bleUtils.getTodayStep(), connectionObservable);
                break;
            case R.id.btn10:
                Write(bleUtils.getSleepData(6), connectionObservable);
                break;
            case R.id.btn11:
                Write(bleUtils.eraseWatchData(), connectionObservable);
                break;
            case R.id.btn12:
                Write(bleUtils.getBatteryValue(), connectionObservable);
                break;
            case R.id.btn13:
                Write(bleUtils.adjHourHand(direction, Integer.parseInt(step.getText().toString())), connectionObservable);
                break;
            case R.id.btn14:
                Write(bleUtils.adjMinuteHand(direction, Integer.parseInt(step.getText().toString())), connectionObservable);
                break;
            case R.id.btn15:
                Write(bleUtils.adjSecondHand(direction, Integer.parseInt(step.getText().toString())), connectionObservable);
                break;
            case R.id.btn16:
                Write(bleUtils.adjMsgHand(direction, Integer.parseInt(step.getText().toString())), connectionObservable);
                break;
            case R.id.btn17:
                Write(bleUtils.adjStepHand(direction, Integer.parseInt(step.getText().toString())), connectionObservable);
                break;
            case R.id.btn18:
                Write(bleUtils.resetHand(), connectionObservable);
                break;
            case R.id.btn19:
                Write(bleUtils.getStepData(6), connectionObservable);

                break;
            case R.id.btn20:
                Write(bleUtils.setWatchShake(1, 0, 0), connectionObservable);
                break;
            case R.id.btn21:
                Write(bleUtils.getAlarms(), connectionObservable);
                break;
            case R.id.btn22:
                Write(bleUtils.setBleConnect(), connectionObservable);
                break;
            case R.id.btn23:
                Write(bleUtils.terminateBle(), connectionObservable);
                break;
            case R.id.btn24:

//                String strLength = new SomeUtills().getFromAssets(BleTest.this, "cyinstein_watchbin.txt");
                if (path != null && !path.equals("")) {
                    String strLength = new SomeUtills().sdcardRead(path).toString();
                    String[] stringstrLength = strLength.split(" ");
                    StringBuffer WriteLength = new StringBuffer();
                    for (int i = 0; i < stringstrLength.length; i++) {
                        String CountLength = stringstrLength[i];
                        WriteLength.append(CountLength);
                    }
                    byte[] Bytes = bleUtils.HexString2Bytes(WriteLength.toString());
                    int crcLength = bleUtils.OTACrc(Bytes);
                     updateLength=Bytes.length;
                    Write(bleUtils.startOTA(Bytes.length, crcLength), connectionObservable);
                }

                break;
            case R.id.btn25:

//                String str = new SomeUtills().getFromAssets(BleTest.this, "cyinstein_watchbin.txt");
                if (path != null && !path.equals("")) {

                    String str = new SomeUtills().sdcardRead(path).toString();
                    String[] strings = str.split(" ");
                    StringBuffer sb = new StringBuffer();
                    StringBuffer newsb = new StringBuffer();
                    StringBuffer Fasb = new StringBuffer();
                    StringBuffer Fastr = new StringBuffer();
                    StringBuffer WriteLength = new StringBuffer();

                    for (int i = 0; i < strings.length; i++) {
                        String CountLength = strings[i];
                        WriteLength.append(CountLength);
                    }

                    byte[] Byteslen = bleUtils.HexString2Bytes(WriteLength.toString());
                    int length = Byteslen.length;
                    int k = 0;
                    int FaCount = 0;
                    boolean isTrue = true;
                    for (int i = 0; i < strings.length; i++) {
                        String count = strings[i];
                        if (count.startsWith("0x") || count.startsWith("0X"))
                            count = count.substring(2);
                        if (count.startsWith(" 0X") || count.startsWith(" 0x"))
                            count = count.substring(3);
                        if (isTrue) {
                            if (k != 20) {
                                sb.append(count);
                                k++;
                            } else {
                                byte[] Bytes = bleUtils.HexString2Bytes(sb.toString());
                                Write(Bytes, connectionObservable);
                                FaCount += Bytes.length;
                                Fasb.append(sb.toString());
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
                                Write(Bytes, connectionObservable);
                                FaCount += Bytes.length;
                                Fasb.append(sb.toString());
                                sb.setLength(0);
                                sb.append(count);
                                k = 0;
                            }
                        }
                    }
                    if (FaCount != length) {
                        StringBuffer WriteLengthb = new StringBuffer();
                        String[] newData = Arrays.copyOfRange(strings, FaCount, length);

                        for (int i = 0; i < newData.length; i++) {
                            String CountLength = newData[i];
                            WriteLengthb.append(CountLength);
                        }

                        byte[] Byteslens = bleUtils.HexString2Bytes(WriteLengthb.toString());

                        for (int n = 0; n < Byteslens.length; n++) {
                            String count = newData[n];
                            if (count.startsWith("0x") || count.startsWith("0X"))
                                count = count.substring(2);
                            if (count.startsWith(" 0X") || count.startsWith(" 0x"))
                                count = count.substring(3);
                            newsb.append(count);
                        }

                        byte[] Bytes = bleUtils.HexString2Bytes(newsb.toString());
                        Write(Bytes, connectionObservable);
                        FaCount += Bytes.length;
                        Fasb.append(sb.toString());

                    }
                    Logger.t(TAG).e("String总数 >>>>>>>>>   " + String.valueOf(strings.length) + "\n" + "发送总数>>>>    " + String.valueOf(FaCount));
                }
                break;
            case R.id.btn26:

                new MaterialFilePicker()
                        .withActivity(this)
                        .withRequestCode(1)
                        .start();

                break;
        }
    }

    private String path;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            path = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
            if (path != null) {
                btn26.setText("已选择文件 >> " + path);
                btn26.setTextColor(getResources().getColor(R.color.red));
            }
        }
    }

    private void setTimeText(int CountTime, TextView mview, int tempTime) {
        switch (CountTime) {
            case 0:
                mview.setText(tempTime);
                break;
        }
    }

    private void OTA() {
        String str = new SomeUtills().getFromAssets(BleTest.this, "cyinstein_watchbin.txt");
        String[] strings = str.split(" ");
        StringBuffer sb = new StringBuffer();
        StringBuffer newsb = new StringBuffer();
        StringBuffer Fasb = new StringBuffer();
        StringBuffer Fastr = new StringBuffer();
        StringBuffer WriteLength = new StringBuffer();

        for (int i = 0; i < strings.length; i++) {
            String CountLength = strings[i];
            WriteLength.append(CountLength);
        }

        byte[] Byteslen = bleUtils.HexString2Bytes(WriteLength.toString());
        int length = Byteslen.length;
        int k = 0;
        int FaCount = 0;
        boolean isTrue = true;
        for (int i = 0; i < strings.length; i++) {
            String count = strings[i];
            if (count.startsWith("0x") || count.startsWith("0X"))
                count = count.substring(2);
            if (count.startsWith(" 0X") || count.startsWith(" 0x"))
                count = count.substring(3);
            if (isTrue) {
                if (k != 20) {
                    sb.append(count);
                    k++;
                } else {
                    byte[] Bytes = bleUtils.HexString2Bytes(sb.toString());
                    Write(Bytes, connectionObservable);
                    FaCount += Bytes.length;
                    Fasb.append(sb.toString());
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
                    Write(Bytes, connectionObservable);
                    FaCount += Bytes.length;
                    Fasb.append(sb.toString());
                    sb.setLength(0);
                    sb.append(count);
                    k = 0;
                }
            }
        }
        if (FaCount != length) {
            StringBuffer WriteLengthb = new StringBuffer();
            String[] newData = Arrays.copyOfRange(strings, FaCount, length);

            for (int i = 0; i < newData.length; i++) {
                String CountLength = newData[i];
                WriteLengthb.append(CountLength);
            }

            byte[] Byteslens = bleUtils.HexString2Bytes(WriteLengthb.toString());

            for (int n = 0; n < Byteslens.length; n++) {
                String count = newData[n];
                if (count.startsWith("0x") || count.startsWith("0X"))
                    count = count.substring(2);
                if (count.startsWith(" 0X") || count.startsWith(" 0x"))
                    count = count.substring(3);
                newsb.append(count);
            }

            byte[] Bytes = bleUtils.HexString2Bytes(newsb.toString());
            Write(Bytes, connectionObservable);
            FaCount += Bytes.length;
            Fasb.append(sb.toString());

        }
        Logger.t(TAG).e("String总数 >>>>>>>>>   " + String.valueOf(strings.length) + "\n" + "发送总数>>>>    " + String.valueOf(FaCount));
    }
}
