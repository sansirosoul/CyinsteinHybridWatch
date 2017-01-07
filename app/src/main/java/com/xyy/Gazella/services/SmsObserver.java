package com.xyy.Gazella.services;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;

import com.orhanobut.logger.Logger;

/**
 * Created by Administrator on 2017/1/5.
 */

//数据观察者，监听短信数据库
public class SmsObserver extends ContentObserver {
    public static final Uri MMSSMS_ALL_MESSAGE_URI = Uri.parse("content://sms/inbox");
    public static final String SORT_FIELD_STRING = "_id asc";  // 排序
    public static final String DB_FIELD_ID = "_id";
    public static final String DB_FIELD_ADDRESS = "address";
    public static final String DB_FIELD_PERSON = "person";
    public static final String DB_FIELD_BODY = "body";
    public static final String DB_FIELD_DATE = "date";
    public static final String DB_FIELD_TYPE = "type";
    public static final String DB_FIELD_THREAD_ID = "thread_id";
    public static final String[] ALL_DB_FIELD_NAME = {
            DB_FIELD_ID, DB_FIELD_ADDRESS, DB_FIELD_PERSON, DB_FIELD_BODY,
            DB_FIELD_DATE, DB_FIELD_TYPE, DB_FIELD_THREAD_ID };
    private Handler handler;
    private ContentResolver mResolver;
    public static int mMessageCount = -1;

    public SmsObserver(ContentResolver resolver,Handler handler) {
        super(handler);
        this.handler=handler;
        this.mResolver=resolver;
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
        Logger.e("sms is coming...");
        handler.sendEmptyMessage(101);
    }
}
