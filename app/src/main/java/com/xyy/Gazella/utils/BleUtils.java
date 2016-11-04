package com.xyy.Gazella.utils;

import android.bluetooth.BluetoothGattCharacteristic;

import com.ysp.newband.GazelleApplication;

/**
 * Created by Administrator on 2016/11/3.
 */

public class BleUtils {
    private byte ck_a,ck_b;
    private byte[] vaue = new byte[20];

    public void getDeviceSN(BluetoothGattCharacteristic characteristic){
        vaue[0]=0x48;
        vaue[1]=0x59;

        vaue[2]=0x07;
        vaue[3]=0x00;

        vaue[4]=0x00;
        vaue[5]=0x00;

        for (int i = 2;i<6;i++){
                ck_a= (byte) (ck_a+vaue[i]);
                ck_b=(byte) (ck_b+ck_a);
        }
        vaue[6]=ck_a;
        vaue[7]=ck_b;

        characteristic.setValue(vaue);
        GazelleApplication.mBluetoothService.writeCharateristic(characteristic);
    }

     public void sendMessage(BluetoothGattCharacteristic characteristic,int type){
         vaue[0]=0x48;
         vaue[1]=0x59;

         vaue[2]=0x07;
         vaue[3]=0x01;

         vaue[4]=0x01;
         vaue[5]=0x00;

         vaue[6]=(byte)type;

         for (int i = 2;i<7;i++){
             ck_a= (byte) (ck_a+vaue[i]);
             ck_b=(byte) (ck_b+ck_a);
         }
         vaue[7]=ck_a;
         vaue[8]=ck_b;

         characteristic.setValue(vaue);
         GazelleApplication.mBluetoothService.writeCharateristic(characteristic);
     }

    public void setWatchTime(BluetoothGattCharacteristic characteristic,int mode,int hour,int minute,int second){
        vaue[0]=0x48;
        vaue[1]=0x59;

        vaue[2]=0x07;
        vaue[3]=0x02;

        vaue[4]=0x04;
        vaue[5]=0x00;

        vaue[6]=(byte)mode;
        vaue[7]=(byte)hour;
        vaue[8]=(byte)minute;
        vaue[9]=(byte)second;

        for (int i = 2;i<10;i++){
            ck_a= (byte) (ck_a+vaue[i]);
            ck_b=(byte) (ck_b+ck_a);
        }
        vaue[10]=ck_a;
        vaue[11]=ck_b;

        characteristic.setValue(vaue);
        GazelleApplication.mBluetoothService.writeCharateristic(characteristic);
    }

    public void setWatchDate(BluetoothGattCharacteristic characteristic,int year,int month,int day,int week){
        vaue[0]=0x48;
        vaue[1]=0x59;

        vaue[2]=0x07;
        vaue[3]=0x03;

        vaue[4]=0x04;
        vaue[5]=0x00;

        vaue[6]= (byte) (year-2006);
        vaue[7]=(byte)month;
        vaue[8]=(byte)day;
        vaue[9]=(byte)week;

        for (int i = 2;i<10;i++){
            ck_a= (byte) (ck_a+vaue[i]);
            ck_b=(byte) (ck_b+ck_a);
        }
        vaue[10]=ck_a;
        vaue[11]=ck_b;

        characteristic.setValue(vaue);
        GazelleApplication.mBluetoothService.writeCharateristic(characteristic);
    }

    //number:alarm0~8
    //snoozeTime:0=off, 1=5min, 2=10min, 3=15min, 4=20min, 5=25min, 6=30min
    //mode:0=off, 1=one times, 2=every day,3=a week, 4=Custom.

    public void setWatchAlarm(BluetoothGattCharacteristic characteristic,int number,int snoozeTime,int mode){
        vaue[0]=0x48;
        vaue[1]=0x59;

        vaue[2]=0x07;
        vaue[3]=0x04;

        vaue[4]=0x04;
        vaue[5]=0x00;

        vaue[6]=(byte)number;
        vaue[7]= (byte) snoozeTime;
        vaue[8]=(byte)mode;
    }

    public void getDeviceName(BluetoothGattCharacteristic characteristic){
        vaue[0]=0x48;
        vaue[1]=0x59;

        vaue[2]=0x07;
        vaue[3]=0x08;

        vaue[4]=0x00;
        vaue[5]=0x00;

        for (int i = 2;i<6;i++){
            ck_a= (byte) (ck_a+vaue[i]);
            ck_b=(byte) (ck_b+ck_a);
        }
        vaue[6]=ck_a;
        vaue[7]=ck_b;

        characteristic.setValue(vaue);
        GazelleApplication.mBluetoothService.writeCharateristic(characteristic);
    }

    public void setSystemType(BluetoothGattCharacteristic characteristic){
        vaue[0]=0x48;
        vaue[1]=0x59;

        vaue[2]=0x07;
        vaue[3]=0x09;

        vaue[4]=0x01;
        vaue[5]=0x00;

        vaue[6]=0x01;

        for (int i = 2;i<7;i++){
            ck_a= (byte) (ck_a+vaue[i]);
            ck_b=(byte) (ck_b+ck_a);
        }
        vaue[7]=ck_a;
        vaue[8]=ck_b;

        characteristic.setValue(vaue);
        GazelleApplication.mBluetoothService.writeCharateristic(characteristic);
    }


    //mode=1 on    mode=2  off
    public void setAntiLost(BluetoothGattCharacteristic characteristic,int mode){
        vaue[0]=0x48;
        vaue[1]=0x59;

        vaue[2]=0x07;
        vaue[3]=0x0b;

        vaue[4]=0x01;
        vaue[5]=0x00;

        vaue[6]=(byte)mode;

        for (int i = 2;i<7;i++){
            ck_a= (byte) (ck_a+vaue[i]);
            ck_b=(byte) (ck_b+ck_a);
        }
        vaue[7]=ck_a;
        vaue[8]=ck_b;

        characteristic.setValue(vaue);
        GazelleApplication.mBluetoothService.writeCharateristic(characteristic);
    }
}
