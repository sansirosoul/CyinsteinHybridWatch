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
import com.xyy.Gazella.utils.BleUtils;
import com.xyy.Gazella.utils.HexString;
import com.xyy.Gazella.utils.SomeUtills;
import com.xyy.Gazella.view.NumberProgressBar;
import com.xyy.model.SleepData;
import com.ysp.hybridtwatch.R;
import com.ysp.newband.BaseActivity;
import com.ysp.newband.GazelleApplication;
import com.ysp.newband.PreferenceData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
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
    private int updateLength;
    private int Upadatecount;


    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.test);
        ButterKnife.bind(this);
        bleUtils = new BleUtils();
        String address = PreferenceData.getAddressValue(this);
        if (address != null && !address.equals("")) {
            if(!GazelleApplication.isBleConnected){
                connectBLEbyMac(address);
            }else{
                setNotifyCharacteristic();
            }
        }

        bleDevice = GazelleApplication.getRxBleClient(this).getBleDevice(address);
        connectionObservable = getRxObservable(this);


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
    public void isServicesDiscovered(boolean flag) {
        if(flag)setNotifyCharacteristic();
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
            updatebar.setMax(updateLength / 2048);
            updatebar.setProgress(Upadatecount);
            Upadatecount++;
            isReturn = true;
        }
    };
    private StringBuffer stringBuffer = new StringBuffer();
    private boolean isclick;

    Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            OTA();
        }
    });

    List<SleepData> list = new ArrayList<>();
    @Override
    protected void onReadReturn(byte[] bytes) {
        super.onReadReturn(bytes);
        notify.setText(HexString.bytesToHex(bytes));
        if (bleUtils.returnOTAValue(bytes)) {
            //返回蓝牙OTA固件更新指令
            thread.start();
        } else if (bleUtils.returnOTAUpdateValue(bytes) != 0) {
            //返回蓝牙OTA固件更新进度值
            int num = Integer.valueOf(bleUtils.returnOTAUpdateValue(bytes));
            if (updateLength != 0 && num != 0) {
                isReturn = true;
                handler.sendEmptyMessage(101);
            }
        } else if (bleUtils.returnOTAUUpdateOk(bytes) != -1) {
            int updateok = bleUtils.returnOTAUUpdateOk(bytes);
            switch (updateok) {
                case 0:
                    showToatst(BleTest.this, "更新成功");
                    break;
                case 1:
                    showToatst(BleTest.this, "更新失败>>> (CRC错误)");
                    break;
                case 2:
                    showToatst(BleTest.this, "更新失败>>> (数量错误)");
                    break;
                case 3:
                    showToatst(BleTest.this, "更新失败>>> (更新超时)");
                    break;
            }

        } else if (bleUtils.returnSleepData(bytes) != null) {
            //返回7天睡眠数据
            notify.setText(HexString.bytesToHex(bytes));
            List<SleepData> sleepDatas = bleUtils.returnSleepData(bytes);
            list.addAll(sleepDatas);
            for (int i = 0; i < sleepDatas.size(); i++) {
                if(sleepDatas.get(i).isLast){
                    //按时间排序
                    Collections.sort(list, new Comparator<SleepData>() {
                        @Override
                        public int compare(SleepData sleepData, SleepData t1) {
                            if(sleepData.getDate()>t1.getDate()){
                                return 1;
                            }else if(sleepData.getDate()==t1.getDate()){
                                if(sleepData.getHour()>t1.getHour()){
                                    return 1;
                                }else if(sleepData.getHour()==t1.getHour()){
                                    if(sleepData.getMin()>t1.getMin()){
                                        return 1;
                                    }else if(sleepData.getMin()==t1.getMin()){
                                        return 0;
                                    }else{
                                        return -1;
                                    }
                                }else{
                                    return -1;
                                }
                            }else {
                                return -1;
                            }
                        }
                    });

                    for (int j = 0; j < list.size(); j++) {
                        Logger.e(list.get(j).getDate()+"号"+list.get(j).getHour()+"时"+list.get(j).getMin()+"分    状态>>"+list.get(j).getStatus());
                    }
                }
            }



        } else if (bytes[0] == 0x07 & bytes[1] == 0x24) {
            Logger.t(TAG).e(String.valueOf(bytes[3]));
            //返回7天计步数据
         //   List<StepData> data = bleUtils.returnStepData(bytes);
         //   for (int i = 0; i < data.size(); i++) {
//                int count = data.get(i).getCount();
//                int time = data.get(i).getTime();
             //   Logger.t(TAG).e(String.valueOf(data.get(i).getCount()));
//                if (count == 41 && time == 23) {
//                    showToatst(BleTest.this, "完成");
//                }

//                stringBuffer.append("总包数>>>  " + String.valueOf(data.get(i).getSums()) + "  " +
//                            "现在第几个包>>>  " + String.valueOf(data.get(i).getCount()) + "  " +
//                            "日期>>>  " + String.valueOf(data.get(i).getDay()) + "  " +
//                            "步数>>>  " + String.valueOf(data.get(i).getStep()) + "  " +
//                            " 时间 >>>  " + String.valueOf(data.get(i).getTime()) + "\r\n");
//                          day01.setText(stringBuffer.toString());

//                if (count <= 5 && count >= 0) {
//                    stringBuffer.append("总包数>>>  " + String.valueOf(data.get(i).getSums()) + "  " +
//                            "现在第几个包>>>  " + String.valueOf(data.get(i).getCount()) + "  " +
//                            "日期>>>  " + String.valueOf(data.get(i).getDay()) + "  " +
//                            "步数>>>  " + String.valueOf(data.get(i).getStep()) + "  " +
//                            " 时间 >>>  " + String.valueOf(data.get(i).getTime()) + "\r\n");
//                    day01.setText(stringBuffer.toString());
//                    if (count == 5 && time == 23) stringBuffer.setLength(0);
//                }
//                if (count <= 11 && count >= 6) {
//                    stringBuffer.append("总包数>>>  " + String.valueOf(data.get(i).getSums()) + "  " +
//                            "现在第几个包>>>  " + String.valueOf(data.get(i).getCount()) + "  " +
//                            "日期>>>  " + String.valueOf(data.get(i).getDay()) + "  " +
//                            "步数>>>  " + String.valueOf(data.get(i).getStep()) + "  " +
//                            " 时间 >>>  " + String.valueOf(data.get(i).getTime()) + "\r\n");
//                    day02.setText(stringBuffer.toString());
//                    if (count == 11 && time == 23) stringBuffer.setLength(0);
//                }
//                if (count <= 17 && count >= 12) {
//                    stringBuffer.append("总包数>>>  " + String.valueOf(data.get(i).getSums()) + "  " +
//                            "现在第几个包>>>  " + String.valueOf(data.get(i).getCount()) + "  " +
//                            "日期>>>  " + String.valueOf(data.get(i).getDay()) + "  " +
//                            "步数>>>  " + String.valueOf(data.get(i).getStep()) + "  " +
//                            " 时间 >>>  " + String.valueOf(data.get(i).getTime()) + "\r\n");
//                    day03.setText(stringBuffer.toString());
//                    if (count == 17 && time == 23) stringBuffer.setLength(0);
//                }
//                if (count <= 23 && count >= 18) {
//                    stringBuffer.append("总包数>>>  " + String.valueOf(data.get(i).getSums()) + "  " +
//                            "现在第几个包>>>  " + String.valueOf(data.get(i).getCount()) + "  " +
//                            "日期>>>  " + String.valueOf(data.get(i).getDay()) + "  " +
//                            "步数>>>  " + String.valueOf(data.get(i).getStep()) + "  " +
//                            " 时间 >>>  " + String.valueOf(data.get(i).getTime()) + "\r\n");
//                    day04.setText(stringBuffer.toString());
//                    if (count == 23 && time == 23) stringBuffer.setLength(0);
//                }
//                if (count <= 29 && count >= 24) {
//                    stringBuffer.append("总包数>>>  " + String.valueOf(data.get(i).getSums()) + "  " +
//                            "现在第几个包>>>  " + String.valueOf(data.get(i).getCount()) + "  " +
//                            "日期>>>  " + String.valueOf(data.get(i).getDay()) + "  " +
//                            "步数>>>  " + String.valueOf(data.get(i).getStep()) + "  " +
//                            " 时间 >>>  " + String.valueOf(data.get(i).getTime()) + "\r\n");
//                    day05.setText(stringBuffer.toString());
//                    if (count == 29 && time == 23) stringBuffer.setLength(0);
//                }
//                if (count <= 35 && count >= 30) {
//                    stringBuffer.append("总包数>>>  " + String.valueOf(data.get(i).getSums()) + "  " +
//                            "现在第几个包>>>  " + String.valueOf(data.get(i).getCount()) + "  " +
//                            "日期>>>  " + String.valueOf(data.get(i).getDay()) + "  " +
//                            "步数>>>  " + String.valueOf(data.get(i).getStep()) + "  " +
//                            " 时间 >>>  " + String.valueOf(data.get(i).getTime()) + "\r\n");
//                    day06.setText(stringBuffer.toString());
//                    if (count == 35 && time == 23) stringBuffer.setLength(0);
//                }
//                if (count <= 41 && count >= 36) {
//                    stringBuffer.append("总包数>>>  " + String.valueOf(data.get(i).getSums()) + "  " +
//                            "现在第几个包>>>  " + String.valueOf(data.get(i).getCount()) + "  " +
//                            "日期>>>  " + String.valueOf(data.get(i).getDay()) + "  " +
//                            "步数>>>  " + String.valueOf(data.get(i).getStep()) + "  " +
//                            " 时间 >>>  " + String.valueOf(data.get(i).getTime()) + "\r\n");
//                    day07.setText(stringBuffer.toString());
//                    if (count == 41 && time == 23) stringBuffer.setLength(0);
//                }
//            }
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
//                Write(bleUtils.getDeviceSN(), connectionObservable);
                writeCharacteristic(bleUtils.getDeviceSN());
                break;
            case R.id.btn2:
//                Write(bleUtils.sendMessage(1, 0, 0, 0, 1, 0), connectionObservable);
                writeCharacteristic(bleUtils.sendMessage(1, 0, 0, 0, 1, 0));
                break;
            case R.id.btn3:
                Calendar calendar = Calendar.getInstance();
                System.out.println(calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.DAY_OF_MONTH) + "-"
                        + calendar.get(Calendar.HOUR_OF_DAY) + "-" + calendar.get(Calendar.MINUTE) + "-" + calendar.get(Calendar.SECOND));
//                Write(bleUtils.setWatchDateAndTime(1, calendar.get(Calendar.YEAR),
//                        calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH),
//                        calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND)), connectionObservable);
                writeCharacteristic(bleUtils.setWatchDateAndTime(1, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH),
                        calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND)));
                break;
            case R.id.btn4:
//                Write(bleUtils.setWatchAlarm(1, 0, 12, 0, 1, 1, "", 1), connectionObservable);
                writeCharacteristic(bleUtils.setWatchAlarm(1, 0, 12, 0, 1, 1, "", 1));
                break;
            case R.id.btn5:
//                Write(bleUtils.getFWVer(), connectionObservable);
                writeCharacteristic(bleUtils.getFWVer());
                break;
            case R.id.btn6:
//                Write(bleUtils.setDeviceName("CT003"), connectionObservable);
                writeCharacteristic(bleUtils.setDeviceName("CT003"));
                break;
            case R.id.btn7:
//                Write(bleUtils.getDeviceName(), connectionObservable);
                writeCharacteristic(bleUtils.getDeviceName());
                break;
            case R.id.btn8:
//                Write(bleUtils.setSystemType(), connectionObservable);
                writeCharacteristic(bleUtils.setSystemType());
                break;
            case R.id.btn9:
//                Write(bleUtils.getTodayStep(), connectionObservable);
                writeCharacteristic(bleUtils.getTodayStep());
                break;
            case R.id.btn10:
//                Write(bleUtils.getSleepData(6), connectionObservable);
                writeCharacteristic(bleUtils.getSleepData(6));
                break;
            case R.id.btn11:
//                Write(bleUtils.eraseWatchData(), connectionObservable);
                writeCharacteristic(bleUtils.eraseWatchData());
                break;
            case R.id.btn12:
//                Write(bleUtils.getBatteryValue(), connectionObservable);
                writeCharacteristic(bleUtils.getBatteryValue());
                break;
            case R.id.btn13:
//                Write(bleUtils.adjHourHand(direction, Integer.parseInt(step.getText().toString())), connectionObservable);
                writeCharacteristic(bleUtils.adjHourHand(direction, Integer.parseInt(step.getText().toString())));
                break;
            case R.id.btn14:
//                Write(bleUtils.adjMinuteHand(direction, Integer.parseInt(step.getText().toString())), connectionObservable);
                writeCharacteristic(bleUtils.adjMinuteHand(direction, Integer.parseInt(step.getText().toString())));
                break;
            case R.id.btn15:
//                Write(bleUtils.adjSecondHand(direction, Integer.parseInt(step.getText().toString())), connectionObservable);
                writeCharacteristic(bleUtils.adjSecondHand(direction, Integer.parseInt(step.getText().toString())));
                break;
            case R.id.btn16:
//                Write(bleUtils.adjMsgHand(direction, Integer.parseInt(step.getText().toString())), connectionObservable);
                writeCharacteristic(bleUtils.adjMsgHand(direction, Integer.parseInt(step.getText().toString())));
                break;
            case R.id.btn17:
//                Write(bleUtils.adjStepHand(direction, Integer.parseInt(step.getText().toString())), connectionObservable);
                writeCharacteristic(bleUtils.adjStepHand(direction, Integer.parseInt(step.getText().toString())));
                break;
            case R.id.btn18:
//                Write(bleUtils.resetHand(), connectionObservable);
                writeCharacteristic(bleUtils.resetHand());
                break;
            case R.id.btn19:
//                Write(bleUtils.getStepData(6), connectionObservable);
                writeCharacteristic(bleUtils.getStepData(6));
                break;
            case R.id.btn20:
//                Write(bleUtils.setWatchShake(1, 0, 0), connectionObservable);
                writeCharacteristic(bleUtils.setWatchShake(1, 0, 0));
                break;
            case R.id.btn21:
//                Write(bleUtils.getAlarms(), connectionObservable);
                writeCharacteristic(bleUtils.getAlarms());
                break;
            case R.id.btn22:
//                Write(bleUtils.setBleConnect(), connectionObservable);
                writeCharacteristic(bleUtils.setBleConnect());
                break;
            case R.id.btn23:
//                Write(bleUtils.terminateBle(), connectionObservable);
                writeCharacteristic(bleUtils.terminateBle());
                break;
            case R.id.btn24:
                if (path !=null) {
                      handUpdateData();
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
//                                Write(Bytes, connectionObservable);
                                writeCharacteristic(Bytes);
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
//                                Write(Bytes, connectionObservable);
                                writeCharacteristic(Bytes);
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
//                        Write(Bytes, connectionObservable);
                        writeCharacteristic(Bytes);
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

    private int crcLength;
    List<String[]> bigData = new ArrayList<>();
    private void handUpdateData() {
        String[] strs = new SomeUtills().readOTABin(path);
//        String strLength = new SomeUtills().sdcardRead(path).toString();
//        String[] strs = strLength.split(" ");
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
