package com.xyy.Gazella.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;

import com.orhanobut.logger.Logger;
import com.partner.entity.Partner;
import com.xyy.Gazella.activity.SleepActivity;
import com.xyy.Gazella.activity.StepActivity;
import com.xyy.Gazella.dbmanager.CommonUtils;
import com.ysp.smartwatch.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
        if (CalendarInstance.getFirstDayOfWeek() == Calendar.SUNDAY)
            CalendarInstance.add(Calendar.DAY_OF_MONTH, 1);

        // 计算一周开始的日期
        CalendarInstance.add(Calendar.DAY_OF_MONTH, -dayOfWeek);
        for (int i = 1; i <= 7; i++) {
            CalendarInstance.add(Calendar.DAY_OF_MONTH, 1);
            System.out.println(sdf.format(CalendarInstance.getTime()));
            if (i == 1) weekMap.put("1", sdf.format(CalendarInstance.getTime()));
            if (i == 7) weekMap.put("7", sdf.format(CalendarInstance.getTime()));
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

    public long getfilelength(Context context, String path) {
        InputStream ff;
        int fff = 0;
        try {
            ff = context.getResources().getAssets().open(path);
            fff = ff.available();
            File file = new File(Environment.getExternalStorageDirectory() + "/" + path);
            file.length();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  file.length();
    }

    public String getFromAssets(Context context,String fileName) {
        String result = "";
        try {
            InputStreamReader inputReader = new InputStreamReader(context.getResources().getAssets().open(fileName) );
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line="";
            String Result="";
            while((line = bufReader.readLine()) != null)
                Result += line;
            return Result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

//    public void getFromAssets(Context context, String fileName) {
//        InputStream in = null;
//        try {
//            in = context.getAssets().open(fileName);
//            FileOutputStream fos = new FileOutputStream(new File(Environment.getExternalStorageDirectory() + "/" +fileName));
//            byte[] buffer = new byte[1024];
//            int count = 0;
//            while (true) {
//                count++;
//                int len = in.read(buffer);
//                if (len == -1) {
//                    break;
//                }
//                fos.write(buffer, 0, len);
//            }
//            in.close();
//            fos.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public  byte[]  setFromAssets(Context context, String fileName) {
//        getFromAssets(context, fileName);
//        File file = new File(Environment.getExternalStorageDirectory() + "/" + fileName);
//        byte[] buffer = new byte[20];
//        try {
//            FileInputStream fileR = new FileInputStream(file);
//            BufferedReader reads = new BufferedReader(new InputStreamReader(fileR));
//
//            while (true) {
//                int len = fileR.read(buffer);
//
//                if (len == -1) {
//                    break;
//                }
//                return  buffer;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return  buffer;
//    }
}

