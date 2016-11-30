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

import com.polidea.rxandroidble.RxBleConnection;
import com.polidea.rxandroidble.RxBleDevice;
import com.polidea.rxandroidble.utils.ConnectionSharingAdapter;
import com.xyy.Gazella.services.BluetoothService;
import com.xyy.Gazella.utils.BleUtils;
import com.xyy.Gazella.utils.HexString;
import com.xyy.model.StepData;
import com.ysp.newband.BaseActivity;
import com.ysp.newband.GazelleApplication;
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

        bleDevice = GazelleApplication.getRxBleClient(this).getBleDevice(GazelleApplication.deviceAddress);
        connectionObservable = bleDevice
                .establishConnection(this, false)
                .compose(new ConnectionSharingAdapter());

        Notify(GET_SN,connectionObservable);

//        writeCharacteristic=GazelleApplication.mBluetoothService.getWriteCharacteristic();
//        notifyCharacteristic=GazelleApplication.mBluetoothService.getNotifyCharacteristic();
//        if(notifyCharacteristic!=null)GazelleApplication.mBluetoothService.setCharacteristicNotification(notifyCharacteristic,true);

        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i==forward.getId()){
                    direction=1;
                }else if(i==back.getId()){
                    direction=2;
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
    protected void onReadReturn(int type, byte[] bytes) {
        super.onReadReturn(type, bytes);
        if (type == GET_SN) {
            if(bleUtils.returnTodayStep(bytes)!=null){
                StepData  data = bleUtils.returnTodayStep(bytes);
                notify.setText(data.getYear()+"-"+data.getMonth()+"-"+data.getDay()+"步数"+data.getStep());
            }else if(bleUtils.returnDeviceSN(bytes)!=null){
                notify.setText(bleUtils.returnDeviceSN(bytes));
            }else if(bleUtils.returnFWVer(bytes)!=null){
                notify.setText(bleUtils.returnFWVer(bytes));
            }else if(bleUtils.returnBatteryValue(bytes)!=null){
                notify.setText(bleUtils.returnBatteryValue(bytes)+"%");
            }else if(bleUtils.returnDeviceName(bytes)!=null){
                notify.setText(bleUtils.returnDeviceName(bytes));
            }
            else{
                notify.setText(HexString.bytesToHex(bytes));
            }
        }
    }

    @Override
    protected void onWriteReturn(int type, byte[] bytes) {
        super.onWriteReturn(type, bytes);


        write.setText(HexString.bytesToHex(bytes));
        notify.setText("");
    }

    @OnClick({R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn7, R.id.btn6, R.id.btn8, R.id.btn9, R.id.btn10, R.id.btn11, R.id.btn12, R.id.btn13,
            R.id.btn14, R.id.btn15, R.id.btn16, R.id.btn17, R.id.btn18, R.id.btn19, R.id.btn20, R.id.btn21, R.id.btn22, R.id.btn23})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                Write(GET_SN, bleUtils.getDeviceSN(), connectionObservable);
                break;
            case R.id.btn2:
                Write(GET_SN, bleUtils.sendMessage(1, 0, 0, 0, 0, 0), connectionObservable);
                break;
            case R.id.btn3:
                Calendar calendar = Calendar.getInstance();
                System.out.println(calendar.get(Calendar.YEAR)+"-"+calendar.get(Calendar.MONTH)+"-"+calendar.get(Calendar.DAY_OF_MONTH)+"-"
                        +calendar.get(Calendar.HOUR_OF_DAY)+"-"+calendar.get(Calendar.MINUTE)+"-"+calendar.get(Calendar.SECOND));
                Write(GET_SN, bleUtils.setWatchDateAndTime(1, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.DAY_OF_MONTH),
                        calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND)), connectionObservable);
                break;
            case R.id.btn4:
                Write(GET_SN, bleUtils.setWatchAlarm(0, 0, 12, 0, 1, 1, ""), connectionObservable);
                break;
            case R.id.btn5:
                Write(GET_SN, bleUtils.getFWVer(), connectionObservable);
                break;
            case R.id.btn6:
                Write(GET_SN, bleUtils.setDeviceName("CT003"), connectionObservable);
                break;
            case R.id.btn7:
                Write(GET_SN, bleUtils.getDeviceName(), connectionObservable);
                break;
            case R.id.btn8:
                Write(GET_SN, bleUtils.setSystemType(), connectionObservable);
                break;
            case R.id.btn9:
                Write(GET_SN, bleUtils.getTodayStep(), connectionObservable);
                break;
            case R.id.btn10:
                Write(GET_SN, bleUtils.getSleepData(1), connectionObservable);
                break;
            case R.id.btn11:
                Write(GET_SN, bleUtils.eraseWatchData(), connectionObservable);
                break;
            case R.id.btn12:
                Write(GET_SN, bleUtils.getBatteryValue(), connectionObservable);
                break;
            case R.id.btn13:
                Write(GET_SN, bleUtils.adjHourHand(direction, Integer.parseInt(step.getText().toString())), connectionObservable);
                break;
            case R.id.btn14:
                Write(GET_SN, bleUtils.adjMinuteHand(direction, Integer.parseInt(step.getText().toString())), connectionObservable);
                break;
            case R.id.btn15:
                Write(GET_SN, bleUtils.adjSecondHand(direction, Integer.parseInt(step.getText().toString())), connectionObservable);
                break;
            case R.id.btn16:
                Write(GET_SN, bleUtils.adjMsgHand(direction, Integer.parseInt(step.getText().toString())), connectionObservable);
                break;
            case R.id.btn17:
                Write(GET_SN, bleUtils.adjStepHand(direction, Integer.parseInt(step.getText().toString())), connectionObservable);
                break;
            case R.id.btn18:
                Write(GET_SN, bleUtils.resetHand(), connectionObservable);
                break;
            case R.id.btn19:
                Write(GET_SN, bleUtils.getStepData(1), connectionObservable);
                break;
            case R.id.btn20:
                Write(GET_SN, bleUtils.setWatchShake(1, 0, 0), connectionObservable);
                break;
            case R.id.btn21:
                Write(GET_SN, bleUtils.getAlarms(), connectionObservable);
                break;
            case R.id.btn22:
                Write(GET_SN, bleUtils.setBleConnect(), connectionObservable);
                break;
            case R.id.btn23:
                Write(GET_SN, bleUtils.terminateBle(), connectionObservable);
                break;
        }
    }

}
