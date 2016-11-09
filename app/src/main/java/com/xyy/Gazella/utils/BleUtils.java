package com.xyy.Gazella.utils;

import android.bluetooth.BluetoothGattCharacteristic;

import com.ysp.newband.GazelleApplication;

/**
 * Created by Administrator on 2016/11/3.
 */

public class BleUtils {
    private byte ck_a,ck_b;
    private byte[] value = new byte[20];

    //获取手表序列号
    public void getDeviceSN(BluetoothGattCharacteristic characteristic){
        value[0]=0x48;
        value[1]=0x59;

        value[2]=0x07;
        value[3]=0x00;

        value[4]=0x00;
        value[5]=0x00;

        for (int i = 2;i<6;i++){
                ck_a= (byte) (ck_a+value[i]);
                ck_b=(byte) (ck_b+ck_a);
        }
        value[6]=ck_a;
        value[7]=ck_b;

        characteristic.setValue(value);
        GazelleApplication.mBluetoothService.writeCharateristic(characteristic);
    }

    //打开通知
     public void sendMessage(BluetoothGattCharacteristic characteristic,int type){
         value[0]=0x48;
         value[1]=0x59;

         value[2]=0x07;
         value[3]=0x01;

         value[4]=0x01;
         value[5]=0x00;

         value[6]=(byte)type;

         for (int i = 2;i<7;i++){
             ck_a= (byte) (ck_a+value[i]);
             ck_b=(byte) (ck_b+ck_a);
         }
         value[7]=ck_a;
         value[8]=ck_b;

         characteristic.setValue(value);
         GazelleApplication.mBluetoothService.writeCharateristic(characteristic);
     }

    public void setWatchTime(BluetoothGattCharacteristic characteristic,int mode,int hour,int minute,int second){
        value[0]=0x48;
        value[1]=0x59;

        value[2]=0x07;
        value[3]=0x02;

        value[4]=0x04;
        value[5]=0x00;

        value[6]=(byte)mode;
        value[7]=(byte)hour;
        value[8]=(byte)minute;
        value[9]=(byte)second;

        for (int i = 2;i<10;i++){
            ck_a= (byte) (ck_a+value[i]);
            ck_b=(byte) (ck_b+ck_a);
        }
        value[10]=ck_a;
        value[11]=ck_b;

        characteristic.setValue(value);
        GazelleApplication.mBluetoothService.writeCharateristic(characteristic);
    }

    public void setWatchDate(BluetoothGattCharacteristic characteristic,int year,int month,int day,int week){
        value[0]=0x48;
        value[1]=0x59;

        value[2]=0x07;
        value[3]=0x03;

        value[4]=0x04;
        value[5]=0x00;

        value[6]= (byte) (year-2006);
        value[7]=(byte)month;
        value[8]=(byte)day;
        value[9]=(byte)week;

        for (int i = 2;i<10;i++){
            ck_a= (byte) (ck_a+value[i]);
            ck_b=(byte) (ck_b+ck_a);
        }
        value[10]=ck_a;
        value[11]=ck_b;

        characteristic.setValue(value);
        GazelleApplication.mBluetoothService.writeCharateristic(characteristic);
    }

    //number:alarm0~8
    //snoozeTime:0=off, 1=5min, 2=10min, 3=15min, 4=20min, 5=25min, 6=30min
    //mode:0=off, 1=one times, 2=every day,3=a week, 4=Custom.

    public void setWatchAlarm(BluetoothGattCharacteristic characteristic,int number,int snoozeTime,int mode){
        value[0]=0x48;
        value[1]=0x59;

        value[2]=0x07;
        value[3]=0x04;

        value[4]=0x04;
        value[5]=0x00;

        value[6]=(byte)number;
        value[7]= (byte) snoozeTime;
        value[8]=(byte)mode;
    }

    //获取固件版本
    public void getFWVer(BluetoothGattCharacteristic characteristic){
        value[0]=0x48;
        value[1]=0x59;

        value[2]=0x07;
        value[3]=0x05;

        value[4]=0x00;
        value[5]=0x00;

        for (int i = 2;i<6;i++){
            ck_a= (byte) (ck_a+value[i]);
            ck_b=(byte) (ck_b+ck_a);
        }
        value[6]=ck_a;
        value[7]=ck_b;

        characteristic.setValue(value);
        GazelleApplication.mBluetoothService.writeCharateristic(characteristic);
    }

    //修改设备名称
    public void setDeviceName(BluetoothGattCharacteristic characteristic){
        value[0]=0x48;
        value[1]=0x59;

        value[2]=0x07;
        value[3]=0x07;

        value[4]=0x10;
        value[5]=0x00;

    }

    //获取设备名称
    public void getDeviceName(BluetoothGattCharacteristic characteristic){
        value[0]=0x48;
        value[1]=0x59;

        value[2]=0x07;
        value[3]=0x08;

        value[4]=0x00;
        value[5]=0x00;

        for (int i = 2;i<6;i++){
            ck_a= (byte) (ck_a+value[i]);
            ck_b=(byte) (ck_b+ck_a);
        }
        value[6]=ck_a;
        value[7]=ck_b;

        characteristic.setValue(value);
        GazelleApplication.mBluetoothService.writeCharateristic(characteristic);
    }

    public void setSystemType(BluetoothGattCharacteristic characteristic){
        value[0]=0x48;
        value[1]=0x59;

        value[2]=0x07;
        value[3]=0x09;

        value[4]=0x01;
        value[5]=0x00;

        value[6]=0x01;

        for (int i = 2;i<7;i++){
            ck_a= (byte) (ck_a+value[i]);
            ck_b=(byte) (ck_b+ck_a);
        }
        value[7]=ck_a;
        value[8]=ck_b;

        characteristic.setValue(value);
        GazelleApplication.mBluetoothService.writeCharateristic(characteristic);
    }

    //防丢失功能   mode=1 开    mode=2  关
    public void setAntiLost(BluetoothGattCharacteristic characteristic,int mode){
        value[0]=0x48;
        value[1]=0x59;

        value[2]=0x07;
        value[3]=0x0b;

        value[4]=0x01;
        value[5]=0x00;

        value[6]=(byte)mode;

        for (int i = 2;i<7;i++){
            ck_a= (byte) (ck_a+value[i]);
            ck_b=(byte) (ck_b+ck_a);
        }
        value[7]=ck_a;
        value[8]=ck_b;

        characteristic.setValue(value);
        GazelleApplication.mBluetoothService.writeCharateristic(characteristic);
    }

    //清除手表数据
    public void eraseWatchData(BluetoothGattCharacteristic characteristic){
        value[0]=0x48;
        value[1]=0x59;

        value[2]=0x07;
        value[3]=0x0e;

        value[4]=0x00;
        value[5]=0x00;

        for (int i = 2;i<6;i++){
            ck_a= (byte) (ck_a+value[i]);
            ck_b=(byte) (ck_b+ck_a);
        }
        value[6]=ck_a;
        value[7]=ck_b;

        characteristic.setValue(value);
        GazelleApplication.mBluetoothService.writeCharateristic(characteristic);
    }

    //获取手表电量
    public void getBatteryValue(BluetoothGattCharacteristic characteristic){
        value[0]=0x48;
        value[1]=0x59;

        value[2]=0x07;
        value[3]=0x13;

        value[4]=0x00;
        value[5]=0x00;

        for (int i = 2;i<6;i++){
            ck_a= (byte) (ck_a+value[i]);
            ck_b=(byte) (ck_b+ck_a);
        }
        value[6]=ck_a;
        value[7]=ck_b;

        characteristic.setValue(value);
        GazelleApplication.mBluetoothService.writeCharateristic(characteristic);
    }

    //校准时针   direction：1  向前   2  向后  stepCount：0~180
    public void adjHourHand(BluetoothGattCharacteristic characteristic,int direction,int stepCount){
        value[0]=0x48;
        value[1]=0x59;

        value[2]=0x07;
        value[3]=0x14;

        value[4]=0x02;
        value[5]=0x00;

        value[6]= (byte) direction;
        value[7]=(byte) stepCount;

        for (int i = 2;i<8;i++){
            ck_a= (byte) (ck_a+value[i]);
            ck_b=(byte) (ck_b+ck_a);
        }
        value[8]=ck_a;
        value[9]=ck_b;

        characteristic.setValue(value);
        GazelleApplication.mBluetoothService.writeCharateristic(characteristic);
    }

    //校准分针
    public void adjMinuteHand(BluetoothGattCharacteristic characteristic,int direction,int stepCount){
        value[0]=0x48;
        value[1]=0x59;

        value[2]=0x07;
        value[3]=0x15;

        value[4]=0x02;
        value[5]=0x00;

        value[6]= (byte) direction;
        value[7]=(byte) stepCount;

        for (int i = 2;i<8;i++){
            ck_a= (byte) (ck_a+value[i]);
            ck_b=(byte) (ck_b+ck_a);
        }
        value[8]=ck_a;
        value[9]=ck_b;

        characteristic.setValue(value);
        GazelleApplication.mBluetoothService.writeCharateristic(characteristic);
    }

    //校准秒针
    public void adjSecondHand(BluetoothGattCharacteristic characteristic,int direction,int stepCount){
        value[0]=0x48;
        value[1]=0x59;

        value[2]=0x07;
        value[3]=0x16;

        value[4]=0x02;
        value[5]=0x00;

        value[6]= (byte) direction;
        value[7]=(byte) stepCount;

        for (int i = 2;i<8;i++){
            ck_a= (byte) (ck_a+value[i]);
            ck_b=(byte) (ck_b+ck_a);
        }
        value[8]=ck_a;
        value[9]=ck_b;

        characteristic.setValue(value);
        GazelleApplication.mBluetoothService.writeCharateristic(characteristic);
    }

    // 关闭手表蓝牙
    public void terminateBle(BluetoothGattCharacteristic characteristic){
        value[0]=0x48;
        value[1]=0x59;

        value[2]=0x07;
        value[3]= (byte) 0xee;

        value[4]=0x00;
        value[5]=0x00;

        for (int i = 2;i<6;i++){
            ck_a= (byte) (ck_a+value[i]);
            ck_b=(byte) (ck_b+ck_a);
        }
        value[6]=ck_a;
        value[7]=ck_b;

        characteristic.setValue(value);
        GazelleApplication.mBluetoothService.writeCharateristic(characteristic);
    }

}
