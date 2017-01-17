package com.xyy.Gazella.activity;

/**
 * 主页
 */

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.polidea.rxandroidble.RxBleConnection;
import com.xyy.Gazella.utils.BleUtils;
import com.ysp.hybridtwatch.R;
import com.ysp.newband.BaseActivity;
import com.ysp.newband.PreferenceData;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;

public class HomeActivity extends BaseActivity {

    private static String TAG = HomeActivity.class.getName();

    @BindView(R.id.ll_time)
    LinearLayout llTime;
    @BindView(R.id.ll_notice)
    LinearLayout llNotice;
    @BindView(R.id.ll_healthy)
    LinearLayout llHealthy;
    @BindView(R.id.ll_settings)
    LinearLayout llSettings;
    @BindView(R.id.ll_introduce)
    LinearLayout llIntroduce;
    @BindView(R.id.ll_other)
    LinearLayout llOther;
    private long mExitTime = 0;
    public static HomeActivity install;
    BleUtils bleUtils;
    public Observable<RxBleConnection> connectionObservable;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);
        bleUtils = new BleUtils();
        String address = PreferenceData.getAddressValue(this);
        if (address != null && !address.equals("")) {
            connectionObservable = getRxObservable(this);
            Notify(connectionObservable);
        }
        getTelephony();
        install = this;
    }

    @Override
    protected void onReadReturn(byte[] bytes) {
    }

    @Override
    protected void onConnectionStateChanges() {
        super.onConnectionStateChanges();


    }

    @Override
    protected void onNotifyReturn(int type,String str) {
        super.onNotifyReturn(type,str);
        switch (type) {
            case 0:
                Write(bleUtils.setSystemType(),connectionObservable);
                break;
            case 1:
                Message.obtain(handler,101,str).sendToTarget();
                break;
            case 2:
                Notify(connectionObservable);
                break;
        }
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 101:
                    String str = (String) msg.obj;
                    HandleThrowableException(str);
                    break;
            }
        }
    };

    @OnClick({R.id.ll_time, R.id.ll_notice, R.id.ll_healthy, R.id.ll_settings, R.id.ll_introduce,R.id.ll_other})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_time:
                Intent timeIntent = new Intent(HomeActivity.this, TimeSynchronization.class);
                startActivity(timeIntent);
                overridePendingTransitionEnter(HomeActivity.this);
                break;
            case R.id.ll_notice:
                Intent noticeIntent = new Intent(HomeActivity.this, NotificationActivty.class);
                startActivity(noticeIntent);
                overridePendingTransitionEnter(HomeActivity.this);
                break;
            case R.id.ll_healthy:
                Intent healthIntent = new Intent(HomeActivity.this, HealthyActivity.class);
                startActivity(healthIntent);
                overridePendingTransitionEnter(HomeActivity.this);
                break;
            case R.id.ll_settings:
                Intent settingsIntent = new Intent(HomeActivity.this, SettingActivity.class);
                startActivity(settingsIntent);
                overridePendingTransitionEnter(HomeActivity.this);
                break;
            case R.id.ll_introduce:
                Intent helpIntent = new Intent(HomeActivity.this, HelpActivity.class);
                startActivity(helpIntent);
                overridePendingTransitionEnter(HomeActivity.this);
                break;
            case R.id.ll_other:
                Intent otherIntent = new Intent(HomeActivity.this, BleTest.class);
                startActivity(otherIntent);
                overridePendingTransitionEnter(HomeActivity.this);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private Object iTelephony;
     // 初始电话实例
    public void getTelephony() {
        TelephonyManager telMgr = (TelephonyManager)getSystemService(Service.TELEPHONY_SERVICE);
        Class<TelephonyManager> c = TelephonyManager.class;
        Method getITelephonyMethod = null;
        try {
            getITelephonyMethod = c.getDeclaredMethod("getITelephony",(Class[]) null);
            getITelephonyMethod.setAccessible(true);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        try {
            iTelephony = getITelephonyMethod.invoke(telMgr,(Object[])null);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    //挂电话
    public void endCall(){
        try {
            Method endCallmethod = iTelephony.getClass().getDeclaredMethod("endCall");
            endCallmethod.invoke(iTelephony);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }
}
