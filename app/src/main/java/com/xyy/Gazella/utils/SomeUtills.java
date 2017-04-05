package com.xyy.Gazella.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.text.format.Time;
import android.view.View;
import android.view.WindowManager;

import com.orhanobut.logger.Logger;
import com.partner.entity.Partner;
import com.xyy.Gazella.activity.SleepActivity;
import com.xyy.Gazella.activity.StepActivity;
import com.xyy.Gazella.dbmanager.CommonUtils;
import com.xyy.model.SleepData;
import com.xyy.model.TimeZonesData;
import com.ysp.hybridtwatch.R;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import cn.sharesdk.onekeyshare.OnekeyShare;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

import static android.content.ContentValues.TAG;


/**
 * Created by Administrator on 2016/10/24.
 */

public class SomeUtills {

    private Calendar CalendarInstance = Calendar.getInstance();
    private SimpleDateFormat sdf;
    private CommonUtils mCommonUtils;


    /***
     * 获取 一周天数
     *
     * @param calendar
     * @return
     */

    public HashMap<String, String> getWeekdate(Date calendar) {
        HashMap<String, String> weekMap = new HashMap<>();
        sdf = new SimpleDateFormat("yyyy.MM.dd");
//        Calendar c = Calendar.getInstance();
        CalendarInstance.setTime(calendar);
        // 今天是一周中的第几天
        int dayOfWeek = CalendarInstance.get(Calendar.DAY_OF_WEEK);
//        if (CalendarInstance.getFirstDayOfWeek() == Calendar.SUNDAY)
//            CalendarInstance.add(Calendar.DAY_OF_MONTH, 1);

        // 计算一周开始的日期
        CalendarInstance.add(Calendar.DAY_OF_MONTH, -dayOfWeek);
        for (int i = 1; i <= 7; i++) {
            CalendarInstance.add(Calendar.DAY_OF_MONTH, 1);
            System.out.println(sdf.format(CalendarInstance.getTime()));
            if (i == 1) weekMap.put("1", sdf.format(CalendarInstance.getTime()));
            if (i == 2) weekMap.put("2", sdf.format(CalendarInstance.getTime()));
            if (i == 3) weekMap.put("3", sdf.format(CalendarInstance.getTime()));
            if (i == 4) weekMap.put("4", sdf.format(CalendarInstance.getTime()));
            if (i == 5) weekMap.put("5", sdf.format(CalendarInstance.getTime()));
            if (i == 6) weekMap.put("6", sdf.format(CalendarInstance.getTime()));
            if (i == 7) weekMap.put("7", sdf.format(CalendarInstance.getTime()));
        }
        return weekMap;
    }

    public HashMap<String, String> getMonthdate(Date calendar) {
        HashMap<String, String> weekMap = new HashMap<>();
        sdf = new SimpleDateFormat("yyyy.MM.dd");
//        Calendar c = Calendar.getInstance();
        CalendarInstance.setTime(calendar);
        // 今天是一周中的第几天
        int dayOfWeek = CalendarInstance.get(Calendar.DAY_OF_MONTH);
        int MaxDay = CalendarInstance.getActualMaximum(Calendar.DAY_OF_MONTH);
        // 计算一周开始的日期
        CalendarInstance.add(Calendar.DAY_OF_MONTH, -dayOfWeek);
        for (int i = 1; i <= MaxDay; i++) {
            CalendarInstance.add(Calendar.DAY_OF_MONTH, 1);
            weekMap.put(String.valueOf(i), sdf.format(CalendarInstance.getTime()));
        }
        return weekMap;
    }

    /***
     * 获取 下周天数
     *
     * @param calendar
     * @param amount   0是上周   1是下周
     * @return
     */

    public HashMap<String, String> getAmountWeekdate(Date calendar, int amount) {
        HashMap<String, String> weekMap = new HashMap<>();
        sdf = new SimpleDateFormat("yyyy.MM.dd");
        CalendarInstance.setTime(calendar);
        // 今天是一周中的第几天
        int dayOfWeek = CalendarInstance.get(Calendar.DAY_OF_WEEK);
        if (amount == 0) {
            if (CalendarInstance.getFirstDayOfWeek() == Calendar.SUNDAY) {
                CalendarInstance.add(Calendar.DAY_OF_MONTH, -7);
            }
        } else {
            if (CalendarInstance.getFirstDayOfWeek() == Calendar.SUNDAY) {
                CalendarInstance.add(Calendar.DAY_OF_MONTH, 7);
            }
        }
        // 计算一周开始的日期
        CalendarInstance.add(Calendar.DAY_OF_MONTH, -dayOfWeek);
        for (int i = 1; i <= 7; i++) {
            CalendarInstance.add(Calendar.DAY_OF_MONTH, 1);
            System.out.println(sdf.format(CalendarInstance.getTime()));
            if (i == 1) weekMap.put("1", sdf.format(CalendarInstance.getTime()));
            if (i == 2) weekMap.put("2", sdf.format(CalendarInstance.getTime()));
            if (i == 3) weekMap.put("3", sdf.format(CalendarInstance.getTime()));
            if (i == 4) weekMap.put("4", sdf.format(CalendarInstance.getTime()));
            if (i == 5) weekMap.put("5", sdf.format(CalendarInstance.getTime()));
            if (i == 6) weekMap.put("6", sdf.format(CalendarInstance.getTime()));
            if (i == 7) weekMap.put("7", sdf.format(CalendarInstance.getTime()));
        }
        return weekMap;
    }

    public HashMap<String, String> getAmountMonthdate(Date calendar, int amount) {
        HashMap<String, String> weekMap = new HashMap<>();
        sdf = new SimpleDateFormat("yyyy.MM.dd");
        CalendarInstance.setTime(calendar);
        CalendarInstance.add(Calendar.MONTH, +1);
        int dayOfWeek = CalendarInstance.get(Calendar.DAY_OF_MONTH);
        for (int i = 1; i <= 7; i++) {
            CalendarInstance.add(Calendar.MONTH, +1);
            System.out.println(sdf.format(CalendarInstance.getTime()));
            if (i == 1) weekMap.put("1", sdf.format(CalendarInstance.getTime()));
            if (i == 2) weekMap.put("2", sdf.format(CalendarInstance.getTime()));
            if (i == 3) weekMap.put("3", sdf.format(CalendarInstance.getTime()));
            if (i == 4) weekMap.put("4", sdf.format(CalendarInstance.getTime()));
            if (i == 5) weekMap.put("5", sdf.format(CalendarInstance.getTime()));
            if (i == 6) weekMap.put("6", sdf.format(CalendarInstance.getTime()));
            if (i == 7) weekMap.put("7", sdf.format(CalendarInstance.getTime()));
        }
        return weekMap;
    }

    /***
     * 获取日期
     *
     * @param type 0 :  年月日   1： 年月
     * @return
     */

    public String getDate(Date calendar, int type) {
        String Date = "";
        switch (type) {
            case 0:
                sdf = new SimpleDateFormat("yyyy.MM.dd");
                Date = sdf.format(calendar.getTime());
                break;
            case 1:
                sdf = new SimpleDateFormat("yyyy.MM");
                Date = sdf.format(calendar.getTime());
                break;
        }
        return Date;
    }

    /****
     * 获取  上一天上个月，下一天下个月 日期
     *
     * @param type   0是天数，  1 是月数
     * @param amount 0 是上一天 上个月。1是下一天 下个月
     * @return
     */

    public String getAmountDate(Date calendar, int type, int amount) {
        String Date = "";
        switch (type) {
            case 0:
                sdf = new SimpleDateFormat("yyyy.MM.dd");
                CalendarInstance.setTime(calendar);
                if (amount == 0) {
                    CalendarInstance.add(Calendar.DAY_OF_MONTH, -1);
                    Date = sdf.format(CalendarInstance.getTime());
                } else {
                    CalendarInstance.add(Calendar.DAY_OF_MONTH, +1);
                    Date = sdf.format(CalendarInstance.getTime());
                }
                break;
            case 1:

                sdf = new SimpleDateFormat("yyyy.MM");
                CalendarInstance.setTime(calendar);
                if (amount == 0) {
                    CalendarInstance.add(Calendar.MONTH, -1);
                    Date = sdf.format(CalendarInstance.getTime());
                } else {
                    CalendarInstance.add(Calendar.MONTH, +1);
                    Date = sdf.format(CalendarInstance.getTime());
                }
                break;
        }
        return Date;
    }

    public void setCalendarViewGone(int type) {
        if (type == 0) {
            if (SleepActivity.sleepActivity.widget.getVisibility() == View.VISIBLE)
                SleepActivity.sleepActivity.setLlDateVisible(1);
        } else {
            if (StepActivity.stepActivity.widget.getVisibility() == View.VISIBLE)
                StepActivity.stepActivity.setLlDateVisible(1);
        }
    }

    private File file;

    public void setShare(Activity activity, int layout) {
        Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                View rootView = activity.findViewById(layout);
                WindowManager wm = activity.getWindowManager();
                int width = wm.getDefaultDisplay().getWidth();
                int height = wm.getDefaultDisplay().getHeight();
                Bitmap newb = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(newb);
                rootView.draw(canvas);

                if (!TextUtils.isEmpty(Environment.getExternalStorageDirectory() + "/" + "share.png")) {
                    Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    file = new File(Environment.getExternalStorageDirectory() + "/" + "share.png");
                    Uri uri = Uri.fromFile(file);
                    intent.setData(uri);
                    activity.sendBroadcast(intent);
                    file.delete();
                }

                file = new File(Environment.getExternalStorageDirectory() + "/" + "share.png");
                FileOutputStream f = null;
                try {
                    f = new FileOutputStream(file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                boolean b = newb.compress(Bitmap.CompressFormat.PNG, 100, f);
                if (b) {
                    subscriber.onNext(true);
                }
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                showShare(activity);
            }
        }, new Action1<Throwable>() {
            @Override

            public void call(Throwable throwable) {

            }
        });
    }


    public Observable<Boolean> onSharesdk(Activity activity, int layout) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                if (!subscriber.isUnsubscribed()) {
                    View rootView = activity.findViewById(layout);
                    WindowManager wm = activity.getWindowManager();
                    int width = wm.getDefaultDisplay().getWidth();
                    int height = wm.getDefaultDisplay().getHeight();
                    Bitmap newb = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                    Canvas canvas = new Canvas(newb);
                    rootView.draw(canvas);

                    file = new File(Environment.getExternalStorageDirectory() + "/" + "share.png");
                    FileOutputStream f = null;
                    try {
                        f = new FileOutputStream(file);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    boolean b = newb.compress(Bitmap.CompressFormat.PNG, 100, f);
                    if (b) {
                        subscriber.onNext(true);
                    }
                }
                subscriber.onCompleted();
            }
        });
    }


    public void showShare(final Activity activity) {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
//        oks.disableSSOWhenAuthorize();
//        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
//        oks.setTitle("标题");
//        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
//         oks.setTitleUrl("http://www.cyinstein.com");
//        // text是分享文本，所有平台都需要这个字段
//        oks.setText("分享文本");
//        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//        oks.setImagePath(Environment.getExternalStorageDirectory() + "/" + "userImage.png");//确保SDcard下面存在此张图片
//        // url仅在微信（包括好友和朋友圈）中使用
//        oks.setUrl("http://www.cyinstein.com");
//        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
//        oks.setComment("评论文本");
//        // site是分享此内容的网站名称，仅在QQ空间使用
//        oks.setSite(activity.getString(R.string.app_name));
//        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
//        oks.setSiteUrl("http://www.cyinstein.com");
//        oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");

//         oks.addHiddenPlatform(QZone.NAME);  //隐藏分享

        oks.setTitleUrl("http://www.cyinstein.com");
        // text是分享文本，所有平台都需要这个字段
        oks.setText(activity.getString(R.string.app_name));
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath(Environment.getExternalStorageDirectory() + "/" + "share.png");
        // url仅在微信（包括好友和朋友圈）中使用
        //oks.setUrl("http://www.ibabylabs.com");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        // oks.setComment(this.getString(R.string.share));
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(activity.getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://www.cyinstein.com");
        // 启动分享GUI
        oks.show(activity);
    }

    /***
     * 插入数据到数据库
     *
     * @param context
     */
    public void setPartnerData(Context context) {
        mCommonUtils = new CommonUtils(context);
        Partner partner = new Partner();
        partner.setType("3");
        partner.setDate("2016.11.11");
        partner.setTime("10");
        partner.setSleep("8");
        partner.setLightsleep("1");
        partner.setSleeping("2");
        partner.setAwake("3");
        mCommonUtils.insertPartner(partner);
    }

    /***
     * 查询数据库全部数据
     *
     * @param context
     */
    public void getPartnerData(Context context) {
        mCommonUtils = new CommonUtils(context);
        List<Partner> partner = mCommonUtils.listAll();
        for (int i = 0; i < partner.size(); i++) {
            Partner o = partner.get(i);
            Logger.t(TAG).i("数据总数== " + String.valueOf(partner.size()) + "\n"
                    + "第几条数据== " + String.valueOf(i) + "\n"
                    + "Awake== " + o.getAwake() + "\n"
                    + "Sleep== " + o.getSleep() + "\n"
                    + "Data== " + o.getDate() + "\n"
                    + "Type== " + o.getType() + "\n"
                    + "LightSleep== " + o.getLightsleep() + "\n"
                    + "Time== " + o.getTime() + "\n"
                    + "Id== " + o.getId() + "\n"
                    + "Sleeping== " + o.getSleeping());
        }
    }

    /***
     * 查询特定条件数据
     *
     * @param context
     */
    public void getPartnerTypeData(Context context) {
        mCommonUtils = new CommonUtils(context);
        List<Partner> list = mCommonUtils.queryByBuilder("3", "2016.11.11");
        for (Partner o : list) {
            Logger.t(TAG).i("数据总数== " + String.valueOf(list.size()) + "\n"
                    + "第几条数据== " + String.valueOf(o) + "\n"
                    + "A" +
                    "wake== " + o.getAwake() + "\n"
                    + "Sleep== " + o.getSleep() + "\n"
                    + "Data== " + o.getDate() + "\n"
                    + "Type== " + o.getType() + "\n"
                    + "LightSleep== " + o.getLightsleep() + "\n"
                    + "Time== " + o.getTime() + "\n"
                    + "Id== " + o.getId() + "\n"
                    + "Sleeping== " + o.getSleeping());
        }
    }

    public int getfilelength(Context context, String path) {
        InputStream ff;
        int fff = 0;
        try {
            ff = context.getResources().getAssets().open(path);
            fff = ff.available();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fff;
    }

    public String[] readOTABin(Context context,String fileName){
        String[] strings =null;
        try {
            int index = 0;
            DataInputStream dis = new DataInputStream(context.getResources().getAssets().open(fileName));
            int length = dis.available();
            strings = new String[length];
            byte[] b = new byte[1];
            while (dis.read(b)!=-1){
                strings[index]=HexString.bytesToHex(b);
                index++;
            }
            dis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strings;
    }

    public String[] readOTABin(String path){
        String[] strings =null;
        try {
            int index = 0;
            File file = new File(path);
            DataInputStream dis = new DataInputStream(new FileInputStream(file));
            int length = dis.available();
            strings = new String[length];
            byte[] b = new byte[1];
            while (dis.read(b)!=-1){
               strings[index]=HexString.bytesToHex(b);
                index++;
            }
            dis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strings;
    }

    public String getFromAssets(Context context, String fileName) {
        String result = "";
        try {
            InputStreamReader inputReader = new InputStreamReader(context.getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            String Result = "";
            while ((line = bufReader.readLine()) != null)
                Result += line;
            return Result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void getFromAssetsf(Context context, String fileName) {
        InputStream in = null;
        try {
            in = context.getAssets().open(fileName);
            FileOutputStream fos = new FileOutputStream(new File(Environment.getExternalStorageDirectory() + "/" + fileName));
            byte[] buffer = new byte[1024];
            int count = 0;
            while (true) {
                count++;
                int len = in.read(buffer);
                if (len == -1) {
                    break;
                }
                fos.write(buffer, 0, len);
            }
            in.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //
    public byte[] setFromAssets(Context context, String fileName) {
        //getFromAssets(context, fileName);
        File file = new File(Environment.getExternalStorageDirectory() + "/" + fileName);
        StringBuffer stringBuffer = new StringBuffer();
        byte[] buffer = new byte[1024];
        try {
            FileInputStream fileR = new FileInputStream(file);
            BufferedReader reads = new BufferedReader(new InputStreamReader(fileR));

            while (true) {
                int len = fileR.read(buffer);
                if (len == -1) {
                    break;
                }
                return buffer;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer;
    }

    // 读取sdcard文件
    public StringBuffer sdcardRead(String path) {
        StringBuffer sb = new StringBuffer();
        try {
            File filev = new File(path);
            BufferedReader br = new BufferedReader(new FileReader(filev));
            String readline = "";
            while ((readline = br.readLine()) != null) {
                sb.append(readline);
            }
            br.close();
            System.out.println("读取成功：" + sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb;
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public double changeDouble(Double dou) {
        dou = dou / 1000.0;
        DecimalFormat nf = new DecimalFormat("0.0");
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        nf.setDecimalFormatSymbols(symbols);
        dou = Double.parseDouble(nf.format(dou));
        return dou;
    }

    public ArrayList<TimeZonesData> getTimeZones(Context context) {
        TimeZonesData data;
        ArrayList<TimeZonesData> dateList = new ArrayList<TimeZonesData>();

        Resources res = context.getResources();
        XmlResourceParser xrp = res.getXml(R.xml.timezones);
        try {
            while (xrp.getEventType() != XmlResourceParser.END_DOCUMENT) {
                if (xrp.getEventType() == XmlResourceParser.START_TAG) {
                    String name = xrp.getName();
                    if (name.equals("timezone")) {
                        if (xrp.getAttributeValue(0).equals("Pacific/Majuro")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Pacific_Majuro));
                            dateList.add(data);
                        }

                        if (xrp.getAttributeValue(0).equals("Pacific/Midway")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Pacific_Midway));
                            dateList.add(data);
                        }

                        if (xrp.getAttributeValue(0).equals("Pacific/Honolulu")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Pacific_Honolulu));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("America/Anchorage")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.America_Anchorage));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("America/Los_Angeles")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.America_Los_Angeles));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("America/Phoenix")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.America_Phoenix));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("America/Chicago")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.America_Chicago));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("America/New_York")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.America_New_York));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Canada/Atlantic")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Canada_Atlantic));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("America/Santiago")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.America_Santiago));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("America/Sao_Paulo")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.America_Sao_Paulo));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Atlantic/Cape_Verde")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Atlantic_Cape_Verde));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Europe/London")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Europe_London));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Europe/Brussels")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Europe_Brussels));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Europe/Athens")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Europe_Athens));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Asia/Baghdad")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Asia_Baghdad));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Asia/Dubai")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Asia_Dubai));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Asia/Karachi")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Asia_Karachi));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Asia/Almaty")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Asia_Almaty));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Asia/Bangkok")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Asia_Bangkok));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Asia/Shanghai")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Asia_Shanghai));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Asia/Tokyo")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Asia_Tokyo));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Australia/Brisbane")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Australia_Brisbane));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Australia/Sydney")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Australia_Sydney));
                            dateList.add(data);
                        }
                    }
                }
                xrp.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateList;
    }

    public String getZonesTime(String id) {
        TimeZone tz = TimeZone.getTimeZone(id);
        Time time = new Time(tz.getID());
        time.setToNow();
        int minute = time.minute;
        int hour = time.hour;
        String strHour;
        String strMinute;
        if (hour < 10)
            strHour = String.format("%2d", hour).replace(" ", "0");
        else
            strHour = String.valueOf(hour);
        if (minute < 10)
            strMinute = String.format("%2d", minute).replace(" ", "0");
        else
            strMinute = String.valueOf(minute);
        return strHour + " : " + strMinute;
    }

    /**
     * 图片的缩放方法
     *
     * @param orgBitmap ：源图片资源
     * @param newWidth  ：缩放后宽度
     * @param newHeight ：缩放后高度
     * @return
     */
    public static Bitmap getZoomImage(Bitmap orgBitmap, double newWidth, double newHeight) {
        if (null == orgBitmap) {
            return null;
        }
        if (orgBitmap.isRecycled()) {
            return null;
        }
        if (newWidth <= 0 || newHeight <= 0) {
            return null;
        }

        // 获取图片的宽和高
        float width = orgBitmap.getWidth();
        float height = orgBitmap.getHeight();
        // 创建操作图片的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(orgBitmap, 0, 0, (int) width, (int) height, matrix, true);
        return bitmap;
    }

    public static List<SleepData> sort(List<SleepData> list) {
        Collections.sort(list, new Comparator<SleepData>() {
            @Override
            public int compare(SleepData sleepData, SleepData t1) {
                if (sleepData.getDate() < t1.getDate()) {
                    return -1;
                } else if (sleepData.getDate() == t1.getDate()) {
                    if (sleepData.getHour() < t1.getHour()) {
                        return -1;
                    } else if (sleepData.getHour() == t1.getHour()) {
                        if (sleepData.getMin() < t1.getMin()) {
                            return -1;
                        } else if (sleepData.getMin() == t1.getMin()) {
                            return 0;
                        } else {
                            return 1;
                        }
                    } else {
                        return 1;
                    }
                } else {
                    return 1;
                }
            }
        });
        return list;
    }
}

