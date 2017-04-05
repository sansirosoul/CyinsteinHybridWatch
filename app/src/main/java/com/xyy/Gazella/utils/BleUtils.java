package com.xyy.Gazella.utils;

import android.content.Context;

import com.orhanobut.logger.Logger;
import com.xyy.model.Clock;
import com.xyy.model.SleepData;
import com.xyy.model.StepData;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * 蓝牙协议操作类
 * Created by Administrator on 2016/11/3.
 */

public class BleUtils {
    private String TAG = BleUtils.class.getName();
    private byte ck_a, ck_b;
    private byte[] value;

    private int[] g_pui32CRC32Table = new int[]{
            0x00000000, 0x1EDC6F41, 0x3DB8DE82, 0x2364B1C3,
            0x7B71BD04, 0x65ADD245, 0x46C96386, 0x58150CC7,
            0xF6E37A08, 0xE83F1549, 0xCB5BA48A, 0xD587CBCB,
            0x8D92C70C, 0x934EA84D, 0xB02A198E, 0xAEF676CF,
            0xF31A9B51, 0xEDC6F410, 0xCEA245D3, 0xD07E2A92,
            0x886B2655, 0x96B74914, 0xB5D3F8D7, 0xAB0F9796,
            0x05F9E159, 0x1B258E18, 0x38413FDB, 0x269D509A,
            0x7E885C5D, 0x6054331C, 0x433082DF, 0x5DECED9E,
            0xF8E959E3, 0xE63536A2, 0xC5518761, 0xDB8DE820,
            0x8398E4E7, 0x9D448BA6, 0xBE203A65, 0xA0FC5524,
            0x0E0A23EB, 0x10D64CAA, 0x33B2FD69, 0x2D6E9228,
            0x757B9EEF, 0x6BA7F1AE, 0x48C3406D, 0x561F2F2C,
            0x0BF3C2B2, 0x152FADF3, 0x364B1C30, 0x28977371,
            0x70827FB6, 0x6E5E10F7, 0x4D3AA134, 0x53E6CE75,
            0xFD10B8BA, 0xE3CCD7FB, 0xC0A86638, 0xDE740979,
            0x866105BE, 0x98BD6AFF, 0xBBD9DB3C, 0xA505B47D,
            0xEF0EDC87, 0xF1D2B3C6, 0xD2B60205, 0xCC6A6D44,
            0x947F6183, 0x8AA30EC2, 0xA9C7BF01, 0xB71BD040,
            0x19EDA68F, 0x0731C9CE, 0x2455780D, 0x3A89174C,
            0x629C1B8B, 0x7C4074CA, 0x5F24C509, 0x41F8AA48,
            0x1C1447D6, 0x02C82897, 0x21AC9954, 0x3F70F615,
            0x6765FAD2, 0x79B99593, 0x5ADD2450, 0x44014B11,
            0xEAF73DDE, 0xF42B529F, 0xD74FE35C, 0xC9938C1D,
            0x918680DA, 0x8F5AEF9B, 0xAC3E5E58, 0xB2E23119,
            0x17E78564, 0x093BEA25, 0x2A5F5BE6, 0x348334A7,
            0x6C963860, 0x724A5721, 0x512EE6E2, 0x4FF289A3,
            0xE104FF6C, 0xFFD8902D, 0xDCBC21EE, 0xC2604EAF,
            0x9A754268, 0x84A92D29, 0xA7CD9CEA, 0xB911F3AB,
            0xE4FD1E35, 0xFA217174, 0xD945C0B7, 0xC799AFF6,
            0x9F8CA331, 0x8150CC70, 0xA2347DB3, 0xBCE812F2,
            0x121E643D, 0x0CC20B7C, 0x2FA6BABF, 0x317AD5FE,
            0x696FD939, 0x77B3B678, 0x54D707BB, 0x4A0B68FA,
            0xC0C1D64F, 0xDE1DB90E, 0xFD7908CD, 0xE3A5678C,
            0xBBB06B4B, 0xA56C040A, 0x8608B5C9, 0x98D4DA88,
            0x3622AC47, 0x28FEC306, 0x0B9A72C5, 0x15461D84,
            0x4D531143, 0x538F7E02, 0x70EBCFC1, 0x6E37A080,
            0x33DB4D1E, 0x2D07225F, 0x0E63939C, 0x10BFFCDD,
            0x48AAF01A, 0x56769F5B, 0x75122E98, 0x6BCE41D9,
            0xC5383716, 0xDBE45857, 0xF880E994, 0xE65C86D5,
            0xBE498A12, 0xA095E553, 0x83F15490, 0x9D2D3BD1,
            0x38288FAC, 0x26F4E0ED, 0x0590512E, 0x1B4C3E6F,
            0x435932A8, 0x5D855DE9, 0x7EE1EC2A, 0x603D836B,
            0xCECBF5A4, 0xD0179AE5, 0xF3732B26, 0xEDAF4467,
            0xB5BA48A0, 0xAB6627E1, 0x88029622, 0x96DEF963,
            0xCB3214FD, 0xD5EE7BBC, 0xF68ACA7F, 0xE856A53E,
            0xB043A9F9, 0xAE9FC6B8, 0x8DFB777B, 0x9327183A,
            0x3DD16EF5, 0x230D01B4, 0x0069B077, 0x1EB5DF36,
            0x46A0D3F1, 0x587CBCB0, 0x7B180D73, 0x65C46232,
            0x2FCF0AC8, 0x31136589, 0x1277D44A, 0x0CABBB0B,
            0x54BEB7CC, 0x4A62D88D, 0x6906694E, 0x77DA060F,
            0xD92C70C0, 0xC7F01F81, 0xE494AE42, 0xFA48C103,
            0xA25DCDC4, 0xBC81A285, 0x9FE51346, 0x81397C07,
            0xDCD59199, 0xC209FED8, 0xE16D4F1B, 0xFFB1205A,
            0xA7A42C9D, 0xB97843DC, 0x9A1CF21F, 0x84C09D5E,
            0x2A36EB91, 0x34EA84D0, 0x178E3513, 0x09525A52,
            0x51475695, 0x4F9B39D4, 0x6CFF8817, 0x7223E756,
            0xD726532B, 0xC9FA3C6A, 0xEA9E8DA9, 0xF442E2E8,
            0xAC57EE2F, 0xB28B816E, 0x91EF30AD, 0x8F335FEC,
            0x21C52923, 0x3F194662, 0x1C7DF7A1, 0x02A198E0,
            0x5AB49427, 0x4468FB66, 0x670C4AA5, 0x79D025E4,
            0x243CC87A, 0x3AE0A73B, 0x198416F8, 0x075879B9,
            0x5F4D757E, 0x41911A3F, 0x62F5ABFC, 0x7C29C4BD,
            0xD2DFB272, 0xCC03DD33, 0xEF676CF0, 0xF1BB03B1,
            0xA9AE0F76, 0xB7726037, 0x9416D1F4, 0x8ACABEB5
    };


    //获取手表序列号
    public byte[] getDeviceSN() {
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

        return value;
    }

    //返回手表序列号
    public String returnDeviceSN(byte[] bytes) {
        String deviceSN = null;
        if (bytes == null || bytes.length == 0) return null;
        if (bytes.length != 20) {
            sbyte = concat(sbyte, bytes);
            if (sbyte.length == 20) {
                byte[] bytes1 = new byte[16];
                for (int i = 0; i < bytes1.length; i++) {
                    bytes1[i] = sbyte[2 + i];
                }
                try {
                    deviceSN = new String(bytes1, "ascii");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                sbyte = new byte[]{};
            }
        } else {
            if (bytes[0] == 0x07 && bytes[1] == 0x00) {
                byte[] bytes1 = new byte[16];
                for (int i = 0; i < bytes1.length; i++) {
                    bytes1[i] = bytes[2 + i];
                }
                try {
                    deviceSN = new String(bytes1, "ascii");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
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
    public byte[] sendMessage(int flight, int phone, int sms, int mail, int msg, int shake) {
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

        return value;
    }

    //设置手表日期和时间
   /* mode	无定义	01
            year	年份	从2000开始
            month月份	1-12
            day	日期	1-31
            hour	时钟数值	0-23
            minute	分钟数值	0-59
            second	秒钟数值	0-59*/
    public byte[] setWatchDateAndTime(int mode, int year, int month, int day, int hour, int minute, int second) {
        value = new byte[14];
        ck_a = 0;
        ck_b = 0;

        value[0] = 0x48;
        value[1] = 0x59;

        value[2] = 0x07;
        value[3] = 0x02;

        value[4] = 0x07;

        value[5] = (byte) mode;
        value[6] = (byte) (year - 2000);
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

        return value;
    }


    //设置手表闹铃
    /*mode    设置/清除模式	0x01=设置模式 0x00=清除闹铃
       id	闹铃号	0-7（对应闹钟号1-8 一共8组）
       hour   闹铃时钟	0-23
       minute  闹铃分钟	0-59
        snoozeTime	贪睡时间	0x00=off，0x01=5min，0x02=10min，0x03=15min，0x04=20min，0x05=25min，0x06=30min
        ringmode	响铃模式	0x00=off，0x01=一次，0x02=每天，0x03=周一-周五，0x04=周六、周日,  0x05=自定义模式
        custom	自定义模式	Bit7=Not Care，Bit6=Sunday, Bit5=Saturday, Bit4=Friday, Bit3=Thursday, Bit2=Wednesday, Bit1=Tuesday, Bit0=Monday\
        byteStr   二进制字符串
        isOpen     是否开启闹铃   0=不开启，1=开启闹铃
        */
    public byte[] setWatchAlarm(int mode, int id, int hour, int minute, int snoozeTime, int ringMode, String byteStr, int isOpen) {
        value = new byte[15];
        ck_a = 0;
        ck_b = 0;

        value[0] = 0x48;
        value[1] = 0x59;

        value[2] = 0x07;
        value[3] = 0x04;

        value[4] = 0x08;

        value[5] = (byte) mode;
        value[6] = (byte) id;
        value[7] = (byte) hour;
        value[8] = (byte) minute;
        value[9] = (byte) snoozeTime;
        value[10] = (byte) ringMode;
        value[11] = decodeBinaryString(byteStr);
        value[12] = (byte) isOpen;

        for (int i = 2; i < 13; i++) {
            ck_a = (byte) (ck_a + value[i]);
            ck_b = (byte) (ck_b + ck_a);
        }
        value[13] = ck_a;
        value[14] = ck_b;

        return value;
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
    public byte[] getFWVer() {
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

        return value;
    }

    //返回固件版本
    public String returnFWVer(byte[] bytes) {
        String FWVer = null;
        if (bytes == null || bytes.length == 0) return null;
        if (bytes.length != 9) {
            sbyte = concat(sbyte, bytes);
            if (sbyte.length == 9) {
                byte[] bytes1 = new byte[5];
                for (int i = 0; i < bytes1.length; i++) {
                    bytes1[i] = sbyte[2 + i];
                }
                FWVer = new String(sbyte);
                sbyte = new byte[]{};
            }
        } else {
            if (bytes[0] == 0x07 && bytes[1] == 0x05) {
                byte[] bytes1 = new byte[5];
                for (int i = 0; i < bytes1.length; i++) {
                    bytes1[i] = bytes[2 + i];
                }
                FWVer = new String(bytes1);
            }
        }
        return FWVer;
    }

    //修改设备名称
    public byte[] setDeviceName(String deviceName) {
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

        return value;
    }

    //获取设备名称
    public byte[] getDeviceName() {
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

        return value;
    }

    //返回设备名称
    public String returnDeviceName(byte[] bytes) {
        if (bytes == null || bytes.length == 0) return null;
        String deviceName = null;
        if (bytes[0] == 0x07 && bytes[1] == 0x08) {
            int length = bytes.length;
            byte[] bytes1 = new byte[length - 4];
            for (int i = 0; i < bytes1.length; i++) {
                bytes1[i] = bytes[2 + i];
                deviceName = new String(bytes1);
            }
        }

        return deviceName;
    }

    //设置系统类型
    public byte[] setSystemType() {
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

        return value;
    }

    //获取设备型号名称
    public byte[] getDeviceType() {
        value = new byte[7];
        ck_a = 0;
        ck_b = 0;

        value[0] = 0x48;
        value[1] = 0x59;

        value[2] = 0x07;
        value[3] = 0x0A;

        value[4] = 0x00;

        for (int i = 2; i < 5; i++) {
            ck_a = (byte) (ck_a + value[i]);
            ck_b = (byte) (ck_b + ck_a);
        }
        value[5] = ck_a;
        value[6] = ck_b;

        return value;
    }

    //返回设备型号名称
    public String returnDeviceType(byte[] bytes) {
        if (bytes == null || bytes.length == 0) return null;
        String deviceType = null;
        if (bytes[0] == 0x07 && bytes[1] == 0x0A) {
            byte[] bytes1 = new byte[6];
            for (int i = 0; i < bytes1.length; i++) {
                bytes1[i] = bytes[2 + i];
                deviceType = new String(bytes1);
            }
        }
        return deviceType;
    }

    //获取当天总计步值
    public byte[] getTodayStep() {
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

        return value;
    }

    //返回当天总计步值
    public StepData returnTodayStep(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        StepData data = null;
        if (bytes.length != 15) {
            sbyte = concat(sbyte, bytes);
            if (sbyte.length == 15) {
                data = new StepData();
                data.setYear((sbyte[2] & 0xFF) + 2000);
                data.setMonth(sbyte[3] & 0xFF);
                data.setDay(sbyte[4] & 0xFF);
                int step = (sbyte[8] & 0xFF) + ((sbyte[7] & 0xFF) << 8) + ((sbyte[6] & 0xFF) << 16) + ((sbyte[5] & 0xFF) << 24);
                int seconds = (sbyte[12] & 0xFF) + ((sbyte[11] & 0xFF) << 8) + ((sbyte[10] & 0xFF) << 16) + ((sbyte[9] & 0xFF) << 24);
                data.setStep(step);
                data.setSeconds(seconds);
                sbyte = new byte[]{};
            }
        } else {
            if (bytes[0] == 0x07 && bytes[1] == 0x0C) {
                data = new StepData();
                data.setYear((bytes[2] & 0xFF) + 2000);
                data.setMonth(bytes[3] & 0xFF);
                data.setDay(bytes[4] & 0xFF);
                int step = (bytes[8] & 0xFF) + ((bytes[7] & 0xFF) << 8) + ((bytes[6] & 0xFF) << 16) + ((bytes[5] & 0xFF) << 24);
                int seconds = (bytes[12] & 0xFF) + ((bytes[11] & 0xFF) << 8) + ((bytes[10] & 0xFF) << 16) + ((bytes[9] & 0xFF) << 24);
                data.setStep(step);
                data.setSeconds(seconds);
            }
        }
        return data;
    }

    //获取睡眠数据
    /*
    num:
    0x00表示当天的睡眠数据，0x01表示前一天，依次类推，0x06表示之前6天的睡眠数据，由于手表最多只能保存7天的睡眠数据，因此取值范围0x00-0x06；*/
    public byte[] getSleepData(int num) {
        value = new byte[8];
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

        return value;
    }

    //返回睡眠数据
    public ArrayList<SleepData> returnSleepData(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        ArrayList<SleepData> list = new ArrayList<>();
        if (bytes.length != 20) {
            sbyte = concat(sbyte, bytes);
            if (sbyte.length == 20) {
                int index = (sbyte.length - 4) / 4;
                for (int i = 1; i < index + 1; i++) {
                    SleepData data = new SleepData();
                    if (i == index && sbyte[2] == (sbyte[3] + 1)) {
                        data.setLast(true);
                    }
                    data.setDate(sbyte[4 * i] & 0xFF);
                    data.setHour(sbyte[4 * i + 1] & 0xFF);
                    data.setMin(sbyte[4 * i + 2] & 0xFF);
                    data.setStatus(sbyte[4 * i + 3] & 0xFF);
                    data.setSums(sbyte[2]);
                    data.setCount(sbyte[3]);
                    list.add(data);
                }
                sbyte = new byte[]{};
            }
        } else {
            if (bytes[0] == 0x07 && bytes[1] == 0x0D) {
                if (bytes[2] != 0) {
                    int index = (bytes.length - 4) / 4;
                    for (int i = 1; i < index + 1; i++) {
                        SleepData data = new SleepData();
                        if (i == index && bytes[2] == (bytes[3] + 1)) {
                            data.setLast(true);
                        }
                        data.setDate(bytes[4 * i] & 0xFF);
                        data.setHour(bytes[4 * i + 1] & 0xFF);
                        data.setMin(bytes[4 * i + 2] & 0xFF);
                        data.setStatus(bytes[4 * i + 3] & 0xFF);
                        data.setSums(bytes[2]);
                        data.setCount(bytes[3]);
                        list.add(data);
                    }
                }
            }
        }
        return list;
    }

    //清除手表数据
    public byte[] eraseWatchData() {
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

        return value;
    }

    //获取生产序号
    public byte[] getDevicePN() {
        value = new byte[7];
        ck_a = 0;
        ck_b = 0;

        value[0] = 0x48;
        value[1] = 0x59;

        value[2] = 0x07;
        value[3] = 0x11;

        value[4] = 0x00;

        for (int i = 2; i < 5; i++) {
            ck_a = (byte) (ck_a + value[i]);
            ck_b = (byte) (ck_b + ck_a);
        }
        value[5] = ck_a;
        value[6] = ck_b;

        return value;
    }

    //返回生产序号
    public String returnDevicePN(byte[] bytes) {
        if (bytes == null || bytes.length == 0) return null;
        String deviceSN = null;
        if (bytes[0] == 0x07 && bytes[1] == 0x11) {
            byte[] bytes1 = new byte[16];
            for (int i = 0; i < bytes1.length; i++) {
                bytes1[i] = bytes[2 + i];
                deviceSN = new String(bytes1);
            }
        }
        return deviceSN;
    }

    //获取手表电量
    public byte[] getBatteryValue() {
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

        return value;
    }

    //返回手表电量
    public String returnBatteryValue(byte[] bytes) {
        if (bytes == null || bytes.length == 0) return null;
        String batteryValue = null;
        if (bytes[0] == 0x07 && bytes[1] == 0x13) {
            batteryValue = String.valueOf(bytes[2] & 0xFF);
        }
        return batteryValue;
    }

    //校准时针
    /*	direction 方向	0x01=正向，0x02=逆向
    stepCount	步进值	Value=1-180步*/
    public byte[] adjHourHand(int direction, int stepCount) {
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

        return value;
    }

    //校准分针
    public byte[] adjMinuteHand(int direction, int stepCount) {
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

        return value;
    }

    //校准秒针
    public byte[] adjSecondHand(int direction, int stepCount) {
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

        return value;
    }

    //校准信息提示针
    public byte[] adjMsgHand(int direction, int stepCount) {
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

        return value;
    }

    //校准计步针
    public byte[] adjStepHand(int direction, int stepCount) {
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

        return value;
    }

    //指针重置模式
    public byte[] resetHand() {
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

        return value;
    }

    //获取7天计步数据
    public byte[] getStepData(int num) {
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

        return value;
    }

    public static byte[] concat(byte[] first, byte[] second) {
        byte[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

    byte[] sbyte = new byte[]{};

    //返回7天计步数据
    public ArrayList<StepData> returnStepData(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        ArrayList<StepData> list = new ArrayList<>();
        if (bytes.length != 20) {
            sbyte = concat(sbyte, bytes);
            if (sbyte.length == 20) {
                for (int i = 1; i < 5; i++) {
                    StepData data = new StepData();
                    data.setDay(sbyte[4 * i] & 0xFF);
                    data.setTime(sbyte[4 * i + 1] & 0xFF);
                    data.setStep((sbyte[4 * i + 3] & 0xFF) + ((sbyte[4 * i + 2] & 0xFF) << 8));
                    data.setSums(sbyte[2]);
                    data.setCount(sbyte[3]);
                    list.add(data);
                }
                sbyte = new byte[]{};
            }
        } else {
            if (bytes[0] == 0x07 & bytes[1] == 0x24) {
                if (bytes[2] != 0) {
                    for (int i = 1; i < 5; i++) {
                        StepData data = new StepData();
                        data.setDay(bytes[4 * i] & 0xFF);
                        data.setTime(bytes[4 * i + 1] & 0xFF);
                        data.setStep((bytes[4 * i + 3] & 0xFF) + ((bytes[4 * i + 2] & 0xFF) << 8));
                        data.setSums(bytes[2]);
                        data.setCount(bytes[3]);
                        list.add(data);
                    }
                }
            }
        }
        return list;
    }


    //手表震动
   /* mode	震动类型	0x00=关震动，大于0x00开震动
    interval	震动间隔时间	0-255秒
    time	震动次数	0-255次*/
    public byte[] setWatchShake(int mode, int interval, int time) {
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

        return value;
    }

    //获取闹铃信息
    public byte[] getAlarms() {
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

        return value;
    }

    //返回闹钟信息
    public Clock returnAlarms(Context context, byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        Clock clock = null;
        if (bytes.length != 12) {
            sbyte = concat(sbyte, bytes);
            if (sbyte.length == 12) {
                clock = new Clock();
                clock.setId((sbyte[3] & 0xFF));

                String hour, minute;
                if ((sbyte[4] & 0xFF) < 10) {
                    hour = "0" + (sbyte[4] & 0xFF);
                } else {
                    hour = (sbyte[4] & 0xFF) + "";
                }

                if ((sbyte[5] & 0xFF) < 10) {
                    minute = "0" + (sbyte[5] & 0xFF);
                } else {
                    minute = (sbyte[5] & 0xFF) + "";
                }
                clock.setTime(hour + ":" + minute);

                clock.setSnoozeTime(Clock.transformSnoozeTime2(context, (sbyte[6] & 0xFF)));

                if ((bytes[7] & 0xFF) == 5) {
                    String str = byte2bits(sbyte[8]);
                    clock.setRate(Clock.transformCustom(context, str));
                    clock.setCustom(str);
                } else {
                    clock.setRate(Clock.transformRat2(context, (sbyte[7] & 0xFF)));
                }

                clock.setIsOpen((sbyte[9] & 0xFF));
                sbyte = new byte[]{};
            }
        } else {
            if (bytes[0] == 0x07 && bytes[1] == 0x26) {
                clock = new Clock();
                if (bytes[2] != 0) {
                    clock.setId((bytes[3] & 0xFF));

                    String hour, minute;
                    if ((bytes[4] & 0xFF) < 10) {
                        hour = "0" + (bytes[4] & 0xFF);
                    } else {
                        hour = (bytes[4] & 0xFF) + "";
                    }

                    if ((bytes[5] & 0xFF) < 10) {
                        minute = "0" + (bytes[5] & 0xFF);
                    } else {
                        minute = (bytes[5] & 0xFF) + "";
                    }
                    clock.setTime(hour + ":" + minute);

                    clock.setSnoozeTime(Clock.transformSnoozeTime2(context, (bytes[6] & 0xFF)));

                    if ((bytes[7] & 0xFF) == 5) {
                        String str = byte2bits(bytes[8]);
                        clock.setRate(Clock.transformCustom(context, str));
                        clock.setCustom(str);
                    } else {
                        clock.setRate(Clock.transformRat2(context, (bytes[7] & 0xFF)));
                    }

                    clock.setIsOpen((bytes[9] & 0xFF));
                }
            }
        }
        return clock;
    }

    //将字节转换二进制字符串
    public static String byte2bits(byte b) {
        int z = b;
        z |= 256;
        String str = Integer.toBinaryString(z);
        int len = str.length();
        return str.substring(len - 8, len);
    }

    //发送蓝牙连接状态
    public byte[] setBleConnect() {
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

        return value;
    }

    // 断开蓝牙连接
    public byte[] terminateBle() {
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

        return value;
    }

    //返回手表断开蓝牙连接是否成功
    public String returnterminateBle(byte[] bytes) {
        String deviceSN = null;
        if (bytes[0] == 0x07 && (bytes[1] & 0xff) == 0xEE) {
            if (bytes[2] == 1)
                return deviceSN = "1";
            else
                return deviceSN = null;
        }
        return deviceSN;
    }


    //蓝牙OTA固件更新
    public byte[] startDfu() {
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

        return value;
    }

    //蓝牙OTA固件更新
    public byte[] startOTA(int length, int crclength) {

        value = new byte[16];
        ck_a = 0;
        ck_b = 0;
        value[0] = 0x48;
        value[1] = 0x59;
        value[2] = 0x07;
        value[3] = (byte) 0xDF;
        value[4] = 0x09;
        value[5] = 0x00;

        value[6] = (byte) (length >>> 24);// 最高位,无符号右移。
        value[7] = (byte) ((length >> 16) & 0xff);// 次高位
        value[8] = (byte) ((length >> 8) & 0xff);// 次低位
        value[9] = (byte) (length & 0xff);// 最低位

        value[10] = (byte) (crclength >>> 24);// 最高位,无符号右移。
        value[11] = (byte) ((crclength >> 16) & 0xff);// 次高位
        value[12] = (byte) ((crclength >> 8) & 0xff);// 次低位
        value[13] = (byte) (crclength & 0xff);// 最低位

        for (int i = 2; i < 14; i++) {
            ck_a = (byte) (ck_a + value[i]);
            ck_b = (byte) (ck_b + ck_a);
        }
        value[14] = ck_a;
        value[15] = ck_b;
        return value;
    }

    //蓝牙OTA固件更新 校验码
    public int OTACrc(byte[] pvData) {

        int ui32CRCIndex, i;
        int ui32TempCRC = 0;
        for (i = 0; i < pvData.length; i++) {
            ui32CRCIndex = (pvData[i] ^ (ui32TempCRC >> 24)) & 0xff;
            ui32TempCRC = (ui32TempCRC << 8) ^ g_pui32CRC32Table[ui32CRCIndex];
        }
        return ui32TempCRC;
    }

    /**
     * bytes[2] == 0x00  不能升级
     * bytes[4]  ==ox00 不能升级
     * bytes[4]  ==ox01 可以升级
     */

    //返回蓝牙OTA固件更新指令
    public boolean returnOTAValue(byte[] bytes) {
        if (bytes == null || bytes.length == 0) return false;
        if (bytes[0] == 0x07 && (bytes[1] & 0xff) == 0xDF) {
            if (bytes[2] == 0x02) {
                if (bytes[4] == 0x01)
                    return true;
                else
                    return false;
            } else {
                return false;
            }
        }
        return false;
    }

    //返回蓝牙OTA固件更新进度值
    public int returnOTAUpdateValue(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return 0;
        }
        int BytesNum = 0;
//        if(bytes.length!=10){
//            sbyte=concat(sbyte,bytes);
//            if(sbyte.length==10){
//                System.out.println(HexString.bytesToHex(sbyte)+"/////");
//                if (sbyte[0] == 0x07 && (sbyte[1] & 0xff) == 0xDF) {
//                    if (sbyte[2] == 0x05) {
//                        if (sbyte[3] == 0x02) {
//                            BytesNum = (sbyte[4] & 0xff) * 16777216
//                                    + (sbyte[5] & 0xff) * 65536
//                                    + (sbyte[6] & 0xff) * 256
//                                    + (sbyte[7] & 0xff);
//                        }
//                    }
//                }
//                sbyte=new byte[]{};
//            }
//        }else {
            if (bytes[0] == 0x07 && (bytes[1] & 0xff) == 0xDF) {
                if (bytes[2] == 0x05) {
                    if (bytes[3] == 0x02) {
                        BytesNum = (bytes[4] & 0xff) * 16777216
                                + (bytes[5] & 0xff) * 65536
                                + (bytes[6] & 0xff) * 256
                                + (bytes[7] & 0xff);
                    }
                }
            }
//        }
        return BytesNum;
    }

    /**
     * 返回蓝牙OTA固件更新是否成功
     *
     * @param bytes
     * @return bytes[4]== 0x00 成功  bytes[4]== 0x01 CRC错 bytes[4]== 0x02 数量错 bytes[4]== 0x03  更新时间超时
     */
    public int returnOTAUUpdateOk(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return -1;
        }
        int BytesNum = -1;
        if (bytes.length !=11) {
            sbyte = concat(sbyte, bytes);
            if (sbyte.length == 11) {
                if (sbyte[0] == 0x07 && (sbyte[1] & 0xff) == 0xDF) {
                    if (sbyte[2] == 0x06) {
                        if (sbyte[3] == 0x10) {
                            if (sbyte[4] == 0x00)
                                return 0;
                            if (sbyte[4] == 0x01)
                                return 1;
                            if (sbyte[4] == 0x02)
                                return 2;
                            if (sbyte[4] == 0x03)
                                return 3;
                        }
                    }
                }
                sbyte = new byte[]{};
            }
        } else {
            if (bytes[0] == 0x07 && (bytes[1] & 0xff) == 0xDF) {
                if (bytes[2] == 0x06) {
                    if (bytes[3] == 0x10) {
                        if (bytes[4] == 0x00)
                            return 0;
                        if (bytes[4] == 0x01)
                            return 1;
                        if (bytes[4] == 0x02)
                            return 2;
                        if (bytes[4] == 0x03)
                            return 3;
                    }
                }
            }
        }
        return BytesNum;
    }


    /**
     * 将两个ASCII字符合成一个字节； 如："EF"–> 0xEF
     *
     * @param src0 byte
     * @param src1 byte
     * @return byte
     */
    public byte uniteBytes(byte src0, byte src1) {
        byte _b0 = Byte.decode("0x" + new String(new byte[]{src0})).byteValue();
        _b0 = (byte) (_b0 << 4);
        byte _b1 = Byte.decode("0x" + new String(new byte[]{src1})).byteValue();
        byte ret = (byte) (_b0 ^ _b1);
        return ret;
    }

    /**
     * 将指定字符串src，以每两个字符分割转换为16进制形式 如："2B44EFD9" –> byte[]{0x2B, 0×44, 0xEF, 0xD9}
     *
     * @param src String
     * @return byte[]
     */
    public byte[] HexString2Bytes(String src) {
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
     *
     * @param b byte[]
     * @return void
     */
    public void printHexString(byte[] b) {
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            Logger.t("TAG").e(hex.toUpperCase() + " ");
        }
        System.out.println("");
    }

    /**
     * @param b byte[]
     * @return String
     */
    public String Bytes2HexString(byte[] b) {
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

    //设置计步目标值
    public byte[] setStepTarget(int target) {
        value = new byte[11];
        ck_a = 0;
        ck_b = 0;

        value[0] = 0x48;
        value[1] = 0x59;

        value[2] = 0x07;
        value[3] = 0x28;

        value[4] = 0x04;

        value[5] = (byte) (target >>> 24);// 最高位,无符号右移。
        value[6] = (byte) ((target >> 16) & 0xff);// 次高位
        value[7] = (byte) ((target >> 8) & 0xff);// 次低位
        value[8] = (byte) (target & 0xff);// 最低位

        for (int i = 2; i < 9; i++) {
            ck_a = (byte) (ck_a + value[i]);
            ck_b = (byte) (ck_b + ck_a);
        }
        value[9] = ck_a;
        value[10] = ck_b;

        return value;
    }

    //获取计步目标值
    public byte[] getStepTarget() {
        value = new byte[7];
        ck_a = 0;
        ck_b = 0;

        value[0] = 0x48;
        value[1] = 0x59;

        value[2] = 0x07;
        value[3] = 0x29;

        value[4] = 0x00;

        for (int i = 2; i < 5; i++) {
            ck_a = (byte) (ck_a + value[i]);
            ck_b = (byte) (ck_b + ck_a);
        }

        value[5] = ck_a;
        value[6] = ck_b;

        return value;
    }

    //返回计步目标值
    public int returnStepTarget(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return -1;
        }
        int target = -1;
        if (bytes[0] == 0x07 && (bytes[1] & 0xff) == 0x29) {
            target = (bytes[2] & 0xff) * 16777216
                    + (bytes[3] & 0xff) * 65536
                    + (bytes[4] & 0xff) * 256
                    + (bytes[5] & 0xff);
        }
        return target;
    }
}
