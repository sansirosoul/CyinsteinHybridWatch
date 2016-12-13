package com.xyy.Gazella.activity;

import android.bluetooth.BluetoothGattCharacteristic;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.polidea.rxandroidble.RxBleConnection;
import com.polidea.rxandroidble.RxBleDevice;
import com.xyy.Gazella.services.BluetoothService;
import com.xyy.Gazella.utils.BleUtils;
import com.xyy.Gazella.utils.HexString;
import com.xyy.Gazella.utils.SomeUtills;
import com.xyy.model.StepData;
import com.ysp.newband.BaseActivity;
import com.ysp.newband.GazelleApplication;
import com.ysp.newband.PreferenceData;
import com.ysp.smartwatch.R;

import java.util.Calendar;

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
    @BindView(R.id.notifytext)
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
    private Observable<RxBleConnection> connectionObservable;
    private RxBleDevice bleDevice;
    private static final String TAG = BleTest.class.getName();
    private int direction = 0;


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
            R.id.btn14, R.id.btn15, R.id.btn16, R.id.btn17, R.id.btn18, R.id.btn19, R.id.btn20, R.id.btn21, R.id.btn22, R.id.btn23, R.id.btn24, R.id.btn25})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                Write(bleUtils.getDeviceSN(), connectionObservable);
                break;
            case R.id.btn2:
                Write(bleUtils.sendMessage(1, 0, 0, 0, 0, 0), connectionObservable);
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
                Write(bleUtils.getSleepData(1), connectionObservable);
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
                Write(bleUtils.getStepData(1), connectionObservable);
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

                int ff = ff = new SomeUtills().getfilelength(BleTest.this, "cyinstein_watch1.txt");
                Logger.t(TAG).e(String.valueOf(ff));
                byte[] fff = bleUtils.startOTA(ff);

                Write(bleUtils.startOTA(ff), connectionObservable);

                break;
            case R.id.btn25:

                String str = new SomeUtills().getFromAssets(BleTest.this, "cyinstein_watch1.txt");
                String[] strings = str.split(",");
                StringBuffer  sb = new StringBuffer();

                byte[] bytes = new byte[20];
                int k = 0;
                for (int i = 0; i < strings.length; i++) {
                    String count = strings[i];
                    if (count.startsWith("0x") || count.startsWith("0X"))
                        count = count.substring(2);
                    if (count.startsWith(" 0X") || count.startsWith(" 0x"))
                        count = count.substring(3);
                    sb.append(count);
                }
                byte[] b = HexString2Bytes(sb.toString());
                for(int i=0; i<b.length;i++){
                    if(k!=19) {
                        bytes[k] = b[i];
                        k++;
                    }else {
                        k=0;
                        Write(bytes, connectionObservable);
                    }
                }
                break;
        }
    }
    /**
     * 将两个ASCII字符合成一个字节； 如："EF"–> 0xEF
     * @param src0  byte
     * @param src1  byte
     * @return  byte
     */
    public static byte uniteBytes(byte src0, byte src1) {
        byte _b0 = Byte.decode("0x" + new String(new byte[] {src0})).byteValue();
        _b0 = (byte) (_b0 << 4);
        byte _b1 = Byte.decode("0x" + new String(new byte[] { src1 })).byteValue();
        byte ret = (byte) (_b0 ^ _b1);
        return ret;
    }

    /**
     * 将指定字符串src，以每两个字符分割转换为16进制形式 如："2B44EFD9" –> byte[]{0x2B, 0×44, 0xEF, 0xD9}
     * @param src String
     * @return byte[]
     */
    public static byte[] HexString2Bytes(String src) {
        if (null == src || 0 == src.length()) {
            return null;
        }
        byte[] ret = new byte[src.length() / 2];
        byte[] tmp = src.getBytes();
        for (int i = 0; i < (tmp.length / 2); i++) {
            ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
        }
        return ret;
    }


    /**
     * 将指定byte数组以16进制的形式打印到控制台
     * @param hint String
     * @param b byte[]
     * @return void
     */
    public static void printHexString(String hint, byte[] b) {
        System.out.print(hint);
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            System.out.print(hex.toUpperCase() + " ");
        }
        System.out.println("");
    }

    /**
     * @param b  byte[]
     * @return  String
     */
    public static String Bytes2HexString(byte[] b) {
        String ret = "";
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            ret += " 0x" + hex.toUpperCase();
        }
        return ret;
    }
}
