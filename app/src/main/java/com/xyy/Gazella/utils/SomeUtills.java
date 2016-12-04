package com.xyy.Gazella.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Environment;
import android.view.View;
import android.view.WindowManager;

import com.orhanobut.logger.Logger;
import com.partner.entity.Partner;
import com.polidea.rxandroidble.RxBleConnection;
import com.xyy.Gazella.activity.SleepActivity;
import com.xyy.Gazella.activity.StepActivity;
import com.xyy.Gazella.dbmanager.CommonUtils;
import com.ysp.smartwatch.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

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

    public void setCompress(Activity activity, int layout) {

        View rootView = activity.findViewById(layout);

        WindowManager wm = activity.getWindowManager();
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();

        Bitmap newb = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(newb);

        rootView.draw(canvas);

        File file = new File(Environment.getExternalStorageDirectory() + "/" + "share.png");

        FileOutputStream f = null;
        try {
            f = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        boolean b = newb.compress(Bitmap.CompressFormat.PNG, 100, f);
        if (b) {
            //截图成功
            //    Logger.t(TAG).i(String.valueOf(activity) + "====截图成功\n" + file.getPath());
            showShare(activity);
        }
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
        oks.setImagePath(Environment.getExternalStorageDirectory() + "/" + "userImage.png");
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

    public final static String ReadUUID = "6e400003-b5a3-f393-e0a9-e50e24dcca9e";
    public final static String WriteUUID = "6e400002-b5a3-f393-e0a9-e50e24dcca9e";

    public String[] WriteCharacteristic(String writeString, Observable<RxBleConnection> connectionObservable) {
        final String[] returStr = {null};


        connectionObservable
                .flatMap(new Func1<RxBleConnection, Observable<byte[]>>() {
                    @Override
                    public Observable<byte[]> call(RxBleConnection rxBleConnection) {
                        return rxBleConnection.writeCharacteristic(UUID.fromString(WriteUUID), HexString.hexToBytes(writeString));
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<byte[]>() {
                    @Override
                    public void call(byte[] bytes) {
                        Logger.t(TAG).e("写入数据>>>>>>  " + HexString.bytesToHex(bytes));
                        ReadCharacteristic(connectionObservable).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<byte[]>() {
                            @Override
                            public void call(byte[] bytes) {
                                Logger.t(TAG).e("返回数据>>>>>>  " + HexString.bytesToHex(bytes));
                                returStr[0] =HexString.bytesToHex(bytes);
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                Logger.t(TAG).e("返回数据失败>>>>>>  " + throwable.toString());
                            }
                        });
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.t(TAG).e("写入数据失败>>>>>>  " + throwable.toString());
                    }
                });
        return  returStr;
    }

    public Observable<byte[]> ReadCharacteristic(Observable<RxBleConnection> connectionObservable) {
        return connectionObservable.flatMap(new Func1<RxBleConnection, Observable<byte[]>>() {
            @Override
            public Observable<byte[]> call(RxBleConnection rxBleConnection) {
                return rxBleConnection.readCharacteristic(UUID.fromString(ReadUUID));
            }
        });
    }
    public Observable<byte[]> Write2Characteristic(String writeString,Observable<RxBleConnection> connectionObservable) {
        return connectionObservable.flatMap(new Func1<RxBleConnection, Observable<byte[]>>() {
            @Override
            public Observable<byte[]> call(RxBleConnection rxBleConnection) {
                return rxBleConnection.writeCharacteristic(UUID.fromString(WriteUUID), HexString.hexToBytes(writeString));
            }
        });
    }




}
