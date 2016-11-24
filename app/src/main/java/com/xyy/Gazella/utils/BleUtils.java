package com.xyy.Gazella.utils;

import android.bluetooth.BluetoothGattCharacteristic;
import android.util.Log;

import com.xyy.model.Clock;
import com.xyy.model.SleepData;
import com.xyy.model.StepData;
import com.ysp.newband.GazelleApplication;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * 蓝牙协议操作类
 * Created by Administrator on 2016/11/3.
 */

public class BleUtils {
    private String TAG = BleUtils.class.getName();
    private byte ck_a, ck_b;
    private byte[] value;


    //获取手表序列号
    public void getDeviceSN(BluetoothGattCharacteristic characteristic) {
        if(characteristic==null){
            Log.i(TAG,"BluetoothGattCharacteristic is null");
            return;
        }
        value = new byte[7];
        ck_a = 0;
        ck_b = 0;

        value[0] = 0x48;
        value[1] = 0x59;

        value[2] = 0x07;
        value[3] = 0x00;

        value[4] = 0x00;

        for (int i = 2; i < 5; i++) {
            ck_a = (byte) (ck_a + value[i]);
            ck_b = (byte) (ck_b + ck_a);
        }
        value[5] = ck_a;
        value[6] = ck_b;

        characteristic.setValue(value);
        GazelleApplication.mBluetoothService.writeCharateristic(characteristic);
    }

    //返回手表序列号
    public String returnDeviceSN(byte[] bytes) {
        String deviceSN = null;
        if (bytes[0] == 0x07 && bytes[1] == 0x00) {
            byte[] bytes1 = new byte[16];
            for (int i = 0; i < bytes1.length; i++) {
                bytes1[i] = bytes[2 + i];
            }
                deviceSN = new String(bytes1);
        }
        return deviceSN;
    }


    //消息提示
   /* fight	飞行模式	0x00=开  0x01=关
    phome	来电提醒	0x00=关  0x01=开
    sms	短信提醒	0x00=关  0x01=开
    mail	邮件提醒	0x00=关  0x01=开
    msg	消息提示提醒	0x00=关  0x01=开
    shake	是否开启震动	0x00=关  0x01=开*/
    public void sendMessage(BluetoothGattCharacteristic characteristic, int flight, int phone, int sms, int mail, int msg, int shake) {
        if(characteristic==null){
            Log.i(TAG,"BluetoothGattCharacteristic is null");
            return;
        }
        value = new byte[13];
        ck_a = 0;
        ck_b = 0;

        value[0] = 0x48;
        value[1] = 0x59;

        value[2] = 0x07;
        value[3] = 0x01;

        value[4] = 0x06;

        value[5] = (byte) flight;
        value[6] = (byte) phone;
        value[7] = (byte) sms;
        value[8] = (byte) mail;
        value[9] = (byte) msg;
        value[10] = (byte) shake;

        for (int i = 2; i < 11; i++) {
            ck_a = (byte) (ck_a + value[i]);
            ck_b = (byte) (ck_b + ck_a);
        }
        value[11] = ck_a;
        value[12] = ck_b;

        characteristic.setValue(value);
        GazelleApplication.mBluetoothService.writeCharateristic(characteristic);
    }

    //设置手表日期和时间
   /* mode	无定义	01
            year	年份	从2000开始
            month月份	1-12
            day	日期	1-31
            hour	时钟数值	0-23
            minute	分钟数值	0-59
            second	秒钟数值	0-59*/
    public void setWatchDateAndTime(BluetoothGattCharacteristic characteristic, int mode, int year, int month, int day, int hour, int minute, int second) {
        if(characteristic==null){
            Log.i(TAG,"BluetoothGattCharacteristic is null");
            return;
        }
        value = new byte[14];
        ck_a = 0;
        ck_b = 0;

        value[0] = 0x48;
        value[1] = 0x59;

        value[2] = 0x07;
        value[3] = 0x02;

        value[4] = 0x07;

        value[5] = (byte) mode;
        value[6] = (byte) (year - 2016);
        value[7] = (byte) month;
        value[8] = (byte) day;
        value[9] = (byte) hour;
        value[10] = (byte) minute;
        value[11] = (byte) second;

        for (int i = 2; i < 12; i++) {
            ck_a = (byte) (ck_a + value[i]);
            ck_b = (byte) (ck_b + ck_a);
        }
        value[12] = ck_a;
        value[13] = ck_b;

        characteristic.setValue(value);
        GazelleApplication.mBluetoothService.writeCharateristic(characteristic);
    }


    //设置手表闹铃
    /*mode    设置/清除模式	0x01=设置模式 0x00=清除闹铃
       id	闹铃号	0-7（对应闹钟号1-8 一共8组）
       hour   闹铃时钟	0-23
       minute  闹铃分钟	0-59
        snoozeTime	贪睡时间	0x00=off，0x01=5min，0x02=10min，0x03=15min，0x04=20min，0x05=25min，0x06=30min
        ringmode	响铃模式	0x00=off，0x01=一次，0x02=每天，0x03=周一-周五，0x04=自定义模式
        custom	自定义模式	Bit7=Not Care，Bit6=Sunday, Bit5=Saturday, Bit4=Friday, Bit3=Thursday, Bit2=Wednesday, Bit1=Tuesday, Bit0=Monday\
        byteStr   二进制字符串*/
    public void setWatchAlarm(BluetoothGattCharacteristic characteristic, int mode, int id, int hour, int minute, int snoozeTime, int ringMode, String byteStr) {
        if(characteristic==null){
            Log.i(TAG,"BluetoothGattCharacteristic is null");
            return;
        }
        value = new byte[14];
        ck_a = 0;
        ck_b = 0;

        value[0] = 0x48;
        value[1] = 0x59;

        value[2] = 0x07;
        value[3] = 0x04;

        value[4] = 0x07;

        value[5] = (byte) mode;
        value[6] = (byte) id;
        value[7] = (byte) hour;
        value[8] = (byte) minute;
        value[9] = (byte) snoozeTime;
        value[10] = (byte) ringMode;
        value[11] = decodeBinaryString(byteStr);

        for (int i = 2; i < 12; i++) {
            ck_a = (byte) (ck_a + value[i]);
            ck_b = (byte) (ck_b + ck_a);
        }
        value[12] = ck_a;
        value[13] = ck_b;

        characteristic.setValue(value);
        GazelleApplication.mBluetoothService.writeCharateristic(characteristic);
    }

    /**
     * 二进制字符串转byte
     */
    public static byte decodeBinaryString(String byteStr) {
        int re, len;
        if (null == byteStr) {
            return 0;
        }
        len = byteStr.length();
        if (len != 4 && len != 8) {
            return 0;
        }
        if (len == 8) {// 8 bit处理
            if (byteStr.charAt(0) == '0') {// 正数
                re = Integer.parseInt(byteStr, 2);
            } else {// 负数
                re = Integer.parseInt(byteStr, 2) - 256;
            }
        } else {// 4 bit处理
            re = Integer.parseInt(byteStr, 2);
        }
        return (byte) re;
    }

    //获取固件版本
    public void getFWVer(BluetoothGattCharacteristic characteristic) {
        if(characteristic==null){
            Log.i(TAG,"BluetoothGattCharacteristic is null");
            return;
        }
        value = new byte[7];
        ck_a = 0;
        ck_b = 0;

        value[0] = 0x48;
        value[1] = 0x59;

        value[2] = 0x07;
        value[3] = 0x05;


        value[4] = 0x00;

        for (int i = 2; i < 5; i++) {
            ck_a = (byte) (ck_a + value[i]);
            ck_b = (byte) (ck_b + ck_a);
        }
        value[5] = ck_a;
        value[6] = ck_b;

        characteristic.setValue(value);
        GazelleApplication.mBluetoothService.writeCharateristic(characteristic);
    }

    //返回固件版本
    public String returnFWVer(byte[] bytes) {
        String FWVer = null;
        if (bytes[0] == 0x07 && bytes[1] == 0x05) {
            byte[] bytes1 = new byte[5];
            for (int i = 0; i < bytes1.length; i++) {
                bytes1[i] = bytes[2 + i];
                FWVer = new String(bytes1);
            }
        }
        return FWVer;
    }

    //修改设备名称
    public void setDeviceName(BluetoothGattCharacteristic characteristic, String deviceName) {
        if(characteristic==null){
            Log.i(TAG,"BluetoothGattCharacteristic is null");
            return;
        }
        value = new byte[20];
        ck_a = 0;
        ck_b = 0;

        value[0] = 0x48;
        value[1] = 0x59;

        value[2] = 0x07;
        value[3] = 0x07;

        value[4] = 0x0D;

        byte[] bytes;
        try {
            bytes = deviceName.getBytes("ascii");
            for (int i = 0; i < bytes.length; i++) {
                value[5 + i] = bytes[i];
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        for (int i = 2; i < 18; i++) {
            ck_a = (byte) (ck_a + value[i]);
            ck_b = (byte) (ck_b + ck_a);
        }
        value[18] = ck_a;
        value[19] = ck_b;

        characteristic.setValue(value);
        GazelleApplication.mBluetoothService.writeCharateristic(characteristic);
    }

    //获取设备名称
    public void getDeviceName(BluetoothGattCharacteristic characteristic) {
        if(characteristic==null){
            Log.i(TAG,"BluetoothGattCharacteristic is null");
            return;
        }
        value = new byte[7];
        ck_a = 0;
        ck_b = 0;

        value[0] = 0x48;
        value[1] = 0x59;

        value[2] = 0x07;
        value[3] = 0x08;

        value[4] = 0x00;

        for (int i = 2; i < 5; i++) {
            ck_a = (byte) (ck_a + value[i]);
            ck_b = (byte) (ck_b + ck_a);
        }
        value[5] = ck_a;
        value[6] = ck_b;

        characteristic.setValue(value);
        GazelleApplication.mBluetoothService.writeCharateristic(characteristic);
    }

    //返回设备名称
    public String returnDeviceName(byte[] bytes) {
        String deviceName = null;
        if (bytes[0] == 0x07 && bytes[1] == 0x08) {
            byte[] bytes1 = new byte[13];
            for (int i = 0; i < bytes1.length; i++) {
                bytes1[i] = bytes[2 + i];
                deviceName = new String(bytes1);
            }
        }

        return deviceName;
    }

    //设置系统类型
    public void setSystemType(BluetoothGattCharacteristic characteristic) {
        if(characteristic==null){
            Log.i(TAG,"BluetoothGattCharacteristic is null");
            return;
        }
        value = new byte[8];
        ck_a = 0;
        ck_b = 0;

        value[0] = 0x48;
        value[1] = 0x59;

        value[2] = 0x07;
        value[3] = 0x09;

        value[4] = 0x01;
        value[5] = 0x00;

        for (int i = 2; i < 6; i++) {
            ck_a = (byte) (ck_a + value[i]);
            ck_b = (byte) (ck_b + ck_a);
        }
        value[6] = ck_a;
        value[7] = ck_b;

        characteristic.setValue(value);
        GazelleApplication.mBluetoothService.writeCharateristic(characteristic);
    }

    //获取当天总计步值
    public void getTodayStep(BluetoothGattCharacteristic characteristic) {
        if(characteristic==null){
            Log.i(TAG,"BluetoothGattCharacteristic is null");
            return;
        }
        value = new byte[7];
        ck_a = 0;
        ck_b = 0;

        value[0] = 0x48;
        value[1] = 0x59;

        value[2] = 0x07;
        value[3] = 0x0C;

        value[4] = 0x00;

        for (int i = 2; i < 5; i++) {
            ck_a = (byte) (ck_a + value[i]);
            ck_b = (byte) (ck_b + ck_a);
        }
        value[5] = ck_a;
        value[6] = ck_b;

        characteristic.setValue(value);
        GazelleApplication.mBluetoothService.writeCharateristic(characteristic);
    }

    //返回当天总计步值
    public StepData returnTodayStep(byte[] bytes) {
        StepData data = new StepData();
        if (bytes[0] == 0x07 && bytes[1] == 0x0C) {
            data.setYear(bytes[2] & 0xFF + 2000);
            data.setMonth(bytes[3] & 0xFF);
            data.setDay(bytes[4] & 0xFF);
            int step = bytes[8] & 0xFF + (bytes[7] & 0xFF) << 8 + (bytes[6] & 0xFF) << 16 + (bytes[5] & 0xFF) << 24;
            data.setStep(step);
        }
        return data;
    }

    //获取睡眠数据
    /*
    num:
    0x00表示当天的睡眠数据，0x01表示前一天，依次类推，0x06表示之前6天的睡眠数据，由于手表最多只能保存7天的睡眠数据，因此取值范围0x00-0x06；*/
    public void getSleepData(BluetoothGattCharacteristic characteristic, int num) {
        value = new byte[8];
        if(characteristic==null){
            Log.i(TAG,"BluetoothGattCharacteristic is null");
            return;
        }
        ck_a = 0;
        ck_b = 0;

        value[0] = 0x48;
        value[1] = 0x59;

        value[2] = 0x07;
        value[3] = 0x0D;

        value[4] = 0x01;
        value[5] = (byte) num;

        for (int i = 2; i < 6; i++) {
            ck_a = (byte) (ck_a + value[i]);
            ck_b = (byte) (ck_b + ck_a);
        }
        value[6] = ck_a;
        value[7] = ck_b;

        characteristic.setValue(value);
        GazelleApplication.mBluetoothService.writeCharateristic(characteristic);
    }

    //返回睡眠数据
    public ArrayList<SleepData> returnSleepData(byte[] bytes) {
        ArrayList<SleepData> list = new ArrayList<>();
        if (bytes[0] == 0x07 && bytes[1] == 0x0D) {
            for (int i = 1; i < 6; i++) {
                SleepData data = new SleepData();
                data.setDate(bytes[3 * i + 1] & 0xFF);
                data.setTime(bytes[3 * i + 2] & 0xFF);
                data.setStatus(bytes[3 * i + 3] & 0xFF);
                list.add(data);
            }
        }
        return list;
    }

    //清除手表数据
    public void eraseWatchData(BluetoothGattCharacteristic characteristic) {
        if(characteristic==null){
            Log.i(TAG,"BluetoothGattCharacteristic is null");
            return;
        }
        value = new byte[7];
        ck_a = 0;
        ck_b = 0;

        value[0] = 0x48;
        value[1] = 0x59;

        value[2] = 0x07;
        value[3] = 0x0E;

        value[4] = 0x00;

        for (int i = 2; i < 5; i++) {
            ck_a = (byte) (ck_a + value[i]);
            ck_b = (byte) (ck_b + ck_a);
        }
        value[5] = ck_a;
        value[6] = ck_b;

        characteristic.setValue(value);
        GazelleApplication.mBluetoothService.writeCharateristic(characteristic);
    }

    //获取手表电量
    public void getBatteryValue(BluetoothGattCharacteristic characteristic) {
        if(characteristic==null){
            Log.i(TAG,"BluetoothGattCharacteristic is null");
            return;
        }
        value = new byte[7];
        ck_a = 0;
        ck_b = 0;

        value[0] = 0x48;
        value[1] = 0x59;

        value[2] = 0x07;
        value[3] = 0x13;

        value[4] = 0x00;

        for (int i = 2; i < 5; i++) {
            ck_a = (byte) (ck_a + value[i]);
            ck_b = (byte) (ck_b + ck_a);
        }
        value[5] = ck_a;
        value[6] = ck_b;

        characteristic.setValue(value);
        GazelleApplication.mBluetoothService.writeCharateristic(characteristic);
    }

    //返回手表电量
    public String returnBatteryValue(byte[] bytes) {
        String batteryValue = null;
        if (bytes[0] == 0x07 && bytes[1] == 0x13) {
            batteryValue = String.valueOf(bytes[2] & 0xFF);
        }
        return batteryValue;
    }

    //校准时针
    /*	direction 方向	0x01=正向，0x02=逆向
    stepCount	步进值	Value=1-180步*/
    public void adjHourHand(BluetoothGattCharacteristic characteristic, int direction, int stepCount) {
        if(characteristic==null){
            Log.i(TAG,"BluetoothGattCharacteristic is null");
            return;
        }
        value = new byte[9];
        ck_a = 0;
        ck_b = 0;

        value[0] = 0x48;
        value[1] = 0x59;

        value[2] = 0x07;
        value[3] = 0x14;

        value[4] = 0x02;

        value[5] = (byte) direction;
        value[6] = (byte) stepCount;

        for (int i = 2; i < 7; i++) {
            ck_a = (byte) (ck_a + value[i]);
            ck_b = (byte) (ck_b + ck_a);
        }
        value[7] = ck_a;
        value[8] = ck_b;

        characteristic.setValue(value);
        GazelleApplication.mBluetoothService.writeCharateristic(characteristic);
    }

    //校准分针
    public void adjMinuteHand(BluetoothGattCharacteristic characteristic, int direction, int stepCount) {
        if(characteristic==null){
            Log.i(TAG,"BluetoothGattCharacteristic is null");
            return;
        }
        value = new byte[9];
        ck_a = 0;
        ck_b = 0;

        value[0] = 0x48;
        value[1] = 0x59;

        value[2] = 0x07;
        value[3] = 0x15;

        value[4] = 0x02;

        value[5] = (byte) direction;
        value[6] = (byte) stepCount;

        for (int i = 2; i < 7; i++) {
            ck_a = (byte) (ck_a + value[i]);
            ck_b = (byte) (ck_b + ck_a);
        }
        value[7] = ck_a;
        value[8] = ck_b;

        characteristic.setValue(value);
        GazelleApplication.mBluetoothService.writeCharateristic(characteristic);
    }

    //校准秒针
    public void adjSecondHand(BluetoothGattCharacteristic characteristic, int direction, int stepCount) {
        if(characteristic==null){
            Log.i(TAG,"BluetoothGattCharacteristic is null");
            return;
        }
        value = new byte[9];
        ck_a = 0;
        ck_b = 0;

        value[0] = 0x48;
        value[1] = 0x59;

        value[2] = 0x07;
        value[3] = 0x16;

        value[4] = 0x02;

        value[5] = (byte) direction;
        value[6] = (byte) stepCount;

        for (int i = 2; i < 7; i++) {
            ck_a = (byte) (ck_a + value[i]);
            ck_b = (byte) (ck_b + ck_a);
        }
        value[7] = ck_a;
        value[8] = ck_b;

        characteristic.setValue(value);
        GazelleApplication.mBluetoothService.writeCharateristic(characteristic);
    }

    //校准信息提示针
    public void adjMsgHand(BluetoothGattCharacteristic characteristic, int direction, int stepCount) {
        if(characteristic==null){
            Log.i(TAG,"BluetoothGattCharacteristic is null");
            return;
        }
        value = new byte[9];
        ck_a = 0;
        ck_b = 0;

        value[0] = 0x48;
        value[1] = 0x59;

        value[2] = 0x07;
        value[3] = 0x17;

        value[4] = 0x02;

        value[5] = (byte) direction;
        value[6] = (byte) stepCount;

        for (int i = 2; i < 7; i++) {
            ck_a = (byte) (ck_a + value[i]);
            ck_b = (byte) (ck_b + ck_a);
        }
        value[7] = ck_a;
        value[8] = ck_b;

        characteristic.setValue(value);
        GazelleApplication.mBluetoothService.writeCharateristic(characteristic);
    }

    //校准计步针
    public void adjStepHand(BluetoothGattCharacteristic characteristic, int direction, int stepCount) {
        if(characteristic==null){
            Log.i(TAG,"BluetoothGattCharacteristic is null");
            return;
        }
        value = new byte[9];
        ck_a = 0;
        ck_b = 0;

        value[0] = 0x48;
        value[1] = 0x59;

        value[2] = 0x07;
        value[3] = 0x18;

        value[4] = 0x02;

        value[5] = (byte) direction;
        value[6] = (byte) stepCount;

        for (int i = 2; i < 7; i++) {
            ck_a = (byte) (ck_a + value[i]);
            ck_b = (byte) (ck_b + ck_a);
        }
        value[7] = ck_a;
        value[8] = ck_b;

        characteristic.setValue(value);
        GazelleApplication.mBluetoothService.writeCharateristic(characteristic);
    }

    //指针重置模式
    public void resetHand(BluetoothGattCharacteristic characteristic) {
        if(characteristic==null){
            Log.i(TAG,"BluetoothGattCharacteristic is null");
            return;
        }
        value = new byte[7];
        ck_a = 0;
        ck_b = 0;

        value[0] = 0x48;
        value[1] = 0x59;

        value[2] = 0x07;
        value[3] = 0x19;

        value[4] = 0x00;

        for (int i = 2; i < 5; i++) {
            ck_a = (byte) (ck_a + value[i]);
            ck_b = (byte) (ck_b + ck_a);
        }
        value[5] = ck_a;
        value[6] = ck_b;

        characteristic.setValue(value);
        GazelleApplication.mBluetoothService.writeCharateristic(characteristic);
    }

    //获取24小时计步数据
    public void getStepData(BluetoothGattCharacteristic characteristic, int num) {
        if(characteristic==null){
            Log.i(TAG,"BluetoothGattCharacteristic is null");
            return;
        }
        value = new byte[8];
        ck_a = 0;
        ck_b = 0;

        value[0] = 0x48;
        value[1] = 0x59;

        value[2] = 0x07;
        value[3] = 0x24;

        value[4] = 0x01;
        value[5] = (byte) num;

        for (int i = 2; i < 6; i++) {
            ck_a = (byte) (ck_a + value[i]);
            ck_b = (byte) (ck_b + ck_a);
        }
        value[6] = ck_a;
        value[7] = ck_b;

        characteristic.setValue(value);
        GazelleApplication.mBluetoothService.writeCharateristic(characteristic);
    }

    //返回24小时计步数据
    public ArrayList<StepData> returnStepData(byte[] bytes) {
        ArrayList<StepData> list = new ArrayList<>();
        if (bytes[0] == 0x07 & bytes[1] == 0x24) {
            for (int i = 1; i < 5; i++) {
                StepData data = new StepData();
                data.setDay(bytes[4 * i] & 0xFF);
                data.setTime(bytes[4 * i + 1] & 0xFF);
                data.setStep(bytes[4 * i + 2] & 0xFF + (bytes[4 * i + 3] & 0xFF) << 8);
                list.add(data);
            }
        }

        return list;
    }


    //手表震动
   /* mode	震动类型	0x00=关震动，大于0x00开震动
    interval	震动间隔时间	0-255秒
    time	震动次数	0-255次*/
    public void setWatchShake(BluetoothGattCharacteristic characteristic, int mode, int interval, int time) {
        if(characteristic==null){
            Log.i(TAG,"BluetoothGattCharacteristic is null");
            return;
        }
        value = new byte[10];
        ck_a = 0;
        ck_b = 0;

        value[0] = 0x48;
        value[1] = 0x59;

        value[2] = 0x07;
        value[3] = 0x25;

        value[4] = 0x03;
        value[5] = (byte) mode;
        value[6] = (byte) interval;
        value[7] = (byte) time;

        for (int i = 2; i < 8; i++) {
            ck_a = (byte) (ck_a + value[i]);
            ck_b = (byte) (ck_b + ck_a);
        }
        value[8] = ck_a;
        value[9] = ck_b;

        characteristic.setValue(value);
        GazelleApplication.mBluetoothService.writeCharateristic(characteristic);
    }

    //获取闹铃信息
    public void getAlarms(BluetoothGattCharacteristic characteristic) {
        if(characteristic==null){
            Log.i(TAG,"BluetoothGattCharacteristic is null");
            return;
        }
        value = new byte[7];
        ck_a = 0;
        ck_b = 0;

        value[0] = 0x48;
        value[1] = 0x59;

        value[2] = 0x07;
        value[3] = 0x26;

        value[4] = 0x00;

        for (int i = 2; i < 5; i++) {
            ck_a = (byte) (ck_a + value[i]);
            ck_b = (byte) (ck_b + ck_a);
        }
        value[5] = ck_a;
        value[6] = ck_b;

        characteristic.setValue(value);
        GazelleApplication.mBluetoothService.writeCharateristic(characteristic);
    }

    //返回闹钟信息
    public Clock returnAlarms(byte[] bytes) {
        Clock clock = new Clock();
        if (bytes[0] == 0x07 & bytes[1] == 0x26) {
            clock.setId(bytes[3]);
            String hour = null;
            String minute = null;
            if(bytes[4]<10){
                hour="0"+bytes[4];
            }
            if(bytes[5]<10){
                minute="0"+bytes[5];
            }
            clock.setTime(hour+":"+minute);
            if(bytes[6]==1){
                clock.setRingtime("5分钟");
            }else if(bytes[6]==2){
                clock.setRingtime("10分钟");
            }else if(bytes[6]==3){
                clock.setRingtime("15分钟");
            }else if(bytes[6]==4){
                clock.setRingtime("20分钟");
            }else if(bytes[6]==5){
                clock.setRingtime("25分钟");
            }else if(bytes[6]==6){
                clock.setRingtime("30分钟");
            }

            if(bytes[7]==0){
                clock.setIsOpen(0);
            }else {
                clock.setIsOpen(1);
                if(bytes[7]==1){
                    clock.setRingtime("只响一次");
                }else if(bytes[7]==2){
                    clock.setRingtime("每天");
                }else if(bytes[7]==3){
                    clock.setRingtime("周一到周五");
                }else if(bytes[7]==4){

                }
            }
        }
        return clock;
    }

    //发送蓝牙连接状态
    public void setBleConnect(BluetoothGattCharacteristic characteristic) {
        if(characteristic==null){
            Log.i(TAG,"BluetoothGattCharacteristic is null");
            return;
        }
        value = new byte[7];
        ck_a = 0;
        ck_b = 0;

        value[0] = 0x48;
        value[1] = 0x59;

        value[2] = 0x07;
        value[3] = (byte) 0xE1;

        value[4] = 0x00;

        for (int i = 2; i < 5; i++) {
            ck_a = (byte) (ck_a + value[i]);
            ck_b = (byte) (ck_b + ck_a);
        }
        value[5] = ck_a;
        value[6] = ck_b;

        characteristic.setValue(value);
        GazelleApplication.mBluetoothService.writeCharateristic(characteristic);
    }

    // 断开蓝牙连接
    public void terminateBle(BluetoothGattCharacteristic characteristic) {
        if(characteristic==null){
            Log.i(TAG,"BluetoothGattCharacteristic is null");
            return;
        }
        value = new byte[7];
        ck_a = 0;
        ck_b = 0;

        value[0] = 0x48;
        value[1] = 0x59;

        value[2] = 0x07;
        value[3] = (byte) 0xee;

        value[4] = 0x00;

        for (int i = 2; i < 5; i++) {
            ck_a = (byte) (ck_a + value[i]);
            ck_b = (byte) (ck_b + ck_a);
        }
        value[5] = ck_a;
        value[6] = ck_b;

        characteristic.setValue(value);
        GazelleApplication.mBluetoothService.writeCharateristic(characteristic);
    }


    //蓝牙OTA固件更新
    public void startDfu(BluetoothGattCharacteristic characteristic) {
        if(characteristic==null){
            Log.i(TAG,"BluetoothGattCharacteristic is null");
            return;
        }
        value = new byte[7];
        ck_a = 0;
        ck_b = 0;

        value[0] = 0x48;
        value[1] = 0x59;

        value[2] = 0x07;
        value[3] = (byte) 0xDF;

        value[4] = 0x00;

        for (int i = 2; i < 5; i++) {
            ck_a = (byte) (ck_a + value[i]);
            ck_b = (byte) (ck_b + ck_a);
        }
        value[5] = ck_a;
        value[6] = ck_b;

        characteristic.setValue(value);
        GazelleApplication.mBluetoothService.writeCharateristic(characteristic);
    }
}
