package com.xyy.Gazella.activity;

/**
 * 主页
 */

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.polidea.rxandroidble.RxBleConnection;
import com.vise.baseble.ViseBluetooth;
import com.xyy.Gazella.utils.BleUtils;
import com.ysp.hybridtwatch.R;
import com.ysp.newband.BaseActivity;
import com.ysp.newband.GazelleApplication;
import com.ysp.newband.PreferenceData;
import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionDenied;
import com.zhy.m.permission.PermissionGrant;

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
    @BindView(R.id.logo)
    ImageView logo;
    private long mExitTime = 0;
    public static HomeActivity install;
    BleUtils bleUtils;
    public Observable<RxBleConnection> connectionObservable;
    private TelephonyManager tm;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        ViseBluetooth.getInstance().setOnNotifyListener(onNotifyListener);
        if(!GazelleApplication.isLogoVisible){
            logo.setVisibility(View.INVISIBLE);
        }
//        logo.setImageDrawable(getResources().getDrawable(R.drawable.index_julius));
        bleUtils = new BleUtils();
        String address = PreferenceData.getAddressValue(this);
        if (address != null && !address.equals("")) {
            if (!GazelleApplication.isBleConnected) {
                connectBLEbyMac(address);
            } else {
                setNotifyCharacteristic();
            }
        }
        getTelephony();
        install = this;
        if(tm==null) {
            tm = (TelephonyManager) getSystemService(Service.TELEPHONY_SERVICE);
            tm.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
        }
    }

    //来电状态监听
    PhoneStateListener listener = new PhoneStateListener() {

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            // TODO Auto-generated method stub
            //state 当前状态 incomingNumber,貌似没有去电的API
            super.onCallStateChanged(state, incomingNumber);
            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE://挂断
                    String address1 = PreferenceData.getAddressValue(mContext);
                    if (address1 != null && !address1.equals("")) {
                        if (GazelleApplication.isBleConnected) {
                            writeCharacteristic(bleUtils.sendMessage(1, 2, 0, 0, 0, 0));
                        }
                    }
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK://接听
                    String address2 = PreferenceData.getAddressValue(mContext);
                    if (address2 != null && !address2.equals("")) {
                        if (GazelleApplication.isBleConnected) {
                            writeCharacteristic(bleUtils.sendMessage(1, 2, 0, 0, 0, 0));
                        }
                    }
                    break;
                case TelephonyManager.CALL_STATE_RINGING://来电
                    System.out.println("响铃:来电号码" + incomingNumber); //输出来电号码
                    String address = PreferenceData.getAddressValue(mContext);
                    if (address != null && !address.equals("")) {
                        if (GazelleApplication.isBleConnected) {
                            int pstate = PreferenceData.getNotificationPhoneState(mContext);
                            int shake = PreferenceData.getNotificationShakeState(mContext);
                            if (pstate == 1) {
                                writeCharacteristic(bleUtils.sendMessage(1, pstate, 0, 0, 0, shake));
                            }
                        }

                    }
                    break;
            }
        }
    };

    private ViseBluetooth.OnNotifyListener onNotifyListener = new ViseBluetooth.OnNotifyListener() {
        @Override
        public void onNotify(boolean flag) {
            if(flag){
                writeCharacteristic(bleUtils.setSystemType());
            }
        }
    };

    @Override
    public void isServicesDiscovered(boolean flag) {
        if (flag) {
            setNotifyCharacteristic();
        }

    }

    @Override
    protected void onReadReturn(byte[] bytes) {
        if(bytes[0]==0x07&&(bytes[1] & 0xff)==0x81){
            endCall();
        }
    }

    @OnClick({R.id.ll_time, R.id.ll_notice, R.id.ll_healthy, R.id.ll_settings, R.id.ll_introduce, R.id.ll_other})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_time:
                ViseBluetooth.getInstance().removeOnNotifyListener();
                Intent timeIntent = new Intent(HomeActivity.this, TimeSynchronization.class);
                startActivity(timeIntent);
                overridePendingTransitionEnter(HomeActivity.this);
                break;
            case R.id.ll_notice:
                ViseBluetooth.getInstance().removeOnNotifyListener();
                Intent noticeIntent = new Intent(HomeActivity.this, NotificationActivty.class);
                startActivity(noticeIntent);
                overridePendingTransitionEnter(HomeActivity.this);
                break;
            case R.id.ll_healthy:
                ViseBluetooth.getInstance().removeOnNotifyListener();
                Intent healthIntent = new Intent(HomeActivity.this, HealthyActivity.class);
                startActivity(healthIntent);
                overridePendingTransitionEnter(HomeActivity.this);
                break;
            case R.id.ll_settings:
                ViseBluetooth.getInstance().removeOnNotifyListener();
                Intent settingsIntent = new Intent(HomeActivity.this, SettingActivity.class);
                startActivity(settingsIntent);
                overridePendingTransitionEnter(HomeActivity.this);
                break;
            case R.id.ll_introduce:
                ViseBluetooth.getInstance().removeOnNotifyListener();
                Intent helpIntent = new Intent(HomeActivity.this, HelpActivity.class);
                startActivity(helpIntent);
                overridePendingTransitionEnter(HomeActivity.this);
                break;
            case R.id.ll_other:
                ViseBluetooth.getInstance().removeOnNotifyListener();
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
                Toast.makeText(this, getResources().getString(R.string.press_again_exit), Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        MPermissions.onRequestPermissionsResult(this,requestCode,permissions,grantResults);
    }

    @PermissionGrant(1000)
    public void requestSdcardSuccess()
    {
    }

    @PermissionDenied(1000)
    public void requestSdcardFailed()
    {
    }

    private Object iTelephony;

    // 初始电话实例
    public void getTelephony() {
        MPermissions.requestPermissions(this, 1000, Manifest.permission.CALL_PHONE);
        TelephonyManager telMgr = (TelephonyManager) getSystemService(Service.TELEPHONY_SERVICE);
        Class<TelephonyManager> c = TelephonyManager.class;
        Method getITelephonyMethod = null;
        try {
            getITelephonyMethod = c.getDeclaredMethod("getITelephony", (Class[]) null);
            getITelephonyMethod.setAccessible(true);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        try {
            iTelephony = getITelephonyMethod.invoke(telMgr, (Object[]) null);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    //挂电话
    public void endCall() {
        System.out.println("挂电话>>>>>>>>>>>>>>>>>>>>>>>>");
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
