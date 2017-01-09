package com.xyy.Gazella.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.Canvas;
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
import com.xyy.model.TimeZonesData;
import com.ysp.hybridtwatch.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import cn.sharesdk.framework.ShareSDK;
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
        int MaxDay=CalendarInstance.getActualMaximum(Calendar.DAY_OF_MONTH);
        // 计算一周开始的日期
        CalendarInstance.add(Calendar.DAY_OF_MONTH, -dayOfWeek);
        for (int i = 1; i <= MaxDay; i++) {
            CalendarInstance.add(Calendar.DAY_OF_MONTH, 1);
            weekMap.put(String.valueOf(i),sdf.format(CalendarInstance.getTime()));
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
        ShareSDK.initSDK(activity);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
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
            FileOutputStream fos = new FileOutputStream(new File(Environment.getExternalStorageDirectory() + "/" +fileName));
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
    public  byte[]  setFromAssets(Context context, String fileName) {
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
                return  buffer;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  buffer;
    }

    // 读取sdcard文件
    public StringBuffer sdcardRead(String path){
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
        return  sb;
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
        dou =dou/1000;
        NumberFormat nf = new DecimalFormat("0.0 ");
        dou = Double.parseDouble(nf.format(dou));
        return dou;
    }

    public  ArrayList<TimeZonesData>  getTimeZones(Context context) {
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
                        if (xrp.getAttributeValue(0).equals("America/Tijuana")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.America_Tijuana));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("America/Phoenix")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.America_Phoenix));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("America/Chihuahua")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.America_Chihuahua));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("America/Denver")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.America_Denver));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("America/Costa_Rica")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.America_Costa_Rica));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("America/Chicago")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.America_Chicago));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("America/Mexico_City")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.America_Mexico_City));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("America/Regina")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.America_Regina));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("America/Bogota")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.America_Bogota));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("America/New_York")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.America_New_York));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("America/Caracas")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.America_Caracas));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("America/Barbados")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.America_Barbados));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("America/Manaus")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.America_Manaus));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("America/Santiago")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.America_Santiago));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("America/St_Johns")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.America_St_Johns));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("America/Sao_Paulo")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.America_Sao_Paulo));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("America/Argentina/Buenos_Aires")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.America_Argentina_Buenos_Aires));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("America/Godthab")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.America_Godthab));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("America/Montevideo")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.America_Montevideo));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Atlantic/South_Georgia")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Atlantic_South_Georgia));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Atlantic/Azores")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Atlantic_Azores));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Atlantic/Cape_Verde")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Atlantic_Cape_Verde));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Africa/Casablanca")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Africa_Casablanca));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Europe/London")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Europe_London));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Europe/Amsterdam")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Europe_Amsterdam));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Europe/Belgrade")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Europe_Belgrade));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Europe/Brussels")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Europe_Brussels));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Europe/Sarajevo")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Europe_Sarajevo));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Africa/Windhoek")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Africa_Windhoek));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Africa/Brazzaville")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Africa_Brazzaville));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Asia/Amman")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Asia_Amman));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Asia/Beirut")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Asia_Beirut));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Africa/Cairo")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Africa_Cairo));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Europe/Helsinki")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Europe_Helsinki));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Asia/Jerusalem")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Asia_Jerusalem));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Europe/Minsk")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Europe_Minsk));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Africa/Harare")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Africa_Harare));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Asia/Baghdad")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Asia_Baghdad));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Europe/Moscow")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Europe_Moscow));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Asia/Kuwait")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Asia_Kuwait));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Africa/Nairobi")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Africa_Nairobi));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Asia/Tehran")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Asia_Tehran));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Asia/Baku")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Asia_Baku));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Asia/Tbilisi")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Asia_Tbilisi));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Asia/Yerevan")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Asia_Yerevan));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Asia/Dubai")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Asia_Dubai));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Asia/Kabul")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Asia_Kabul));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Asia/Karachi")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Asia_Karachi));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Asia/Oral")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Asia_Oral));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Asia/Yekaterinburg")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Asia_Yekaterinburg));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Asia/Calcutta")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Asia_Calcutta));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Asia/Colombo")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Asia_Colombo));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Asia/Katmandu")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Asia_Katmandu));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Asia/Almaty")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Asia_Almaty));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Asia/Rangoon")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Asia_Rangoon));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Asia/Krasnoyarsk")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Asia_Krasnoyarsk));
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
                        if (xrp.getAttributeValue(0).equals("Asia/Hong_Kong")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Asia_Hong_Kong));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Asia/Irkutsk")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Asia_Irkutsk));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Asia/Kuala_Lumpur")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Asia_Kuala_Lumpur));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Australia/Perth")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Australia_Perth));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Asia/Taipei")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Asia_Taipei));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Asia/Seoul")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Asia_Seoul));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Asia/Tokyo")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Asia_Tokyo));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Asia/Yakutsk")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Asia_Yakutsk));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Australia/Adelaide")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Australia_Adelaide));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Australia/Darwin")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Australia_Darwin));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Australia/Brisbane")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Australia_Brisbane));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Australia/Hobart")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Australia_Hobart));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Australia/Sydney")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Australia_Sydney));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Asia/Vladivostok")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Asia_Vladivostok));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Pacific/Guam")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Pacific_Guam));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Asia/Magadan")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Asia_Magadan));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Pacific/Auckland")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Pacific_Auckland));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Pacific/Fiji")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Pacific_Fiji));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Pacific/Tongatapu")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Pacific_Tongatapu));
                            dateList.add(data);
                        }
                    }
                }
                xrp.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  dateList;
    }

    public String getZonesTime(String id) {
        TimeZone tz = TimeZone.getTimeZone(id);
        Time time = new Time(tz.getID());
        time.setToNow();
        int minute = time.minute;
        int hour = time.hour;
        String  strHour;
        String strMinute;
        if (hour < 10)
            strHour= String.format("%2d", hour).replace(" ", "0");
        else
            strHour= String.valueOf(hour);
        if (minute < 10)
            strMinute= String.format("%2d", minute).replace(" ", "0");
        else
            strMinute= String.valueOf(minute);
        return strHour + " :" + strMinute;
    }
}

