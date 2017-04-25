package com.ysp.newband;

import android.app.Application;
import android.app.Service;
import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.multidex.MultiDex;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.bugtags.library.Bugtags;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.polidea.rxandroidble.RxBleClient;
import com.polidea.rxandroidble.internal.RxBleLog;
import com.vise.baseble.ViseBluetooth;
import com.xyy.Gazella.services.BluetoothService;
import com.xyy.Gazella.utils.BleUtils;
import com.xyy.model.User;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import java.util.concurrent.TimeUnit;

import cn.sharesdk.framework.ShareSDK;
import okhttp3.OkHttpClient;

public class GazelleApplication extends Application {


    private static GazelleApplication instance;
    public static int SCREEN_WIDTH = 800;
    public static int SCREEN_HEIGHT = 480;
    public static BluetoothService mBluetoothService;
    public static BluetoothDevice device;
    public static int USER_ID = 1;
    private User user;
    public static int CONNECTED = -1;
    // 来电监听广播
    public static String UUID;
    public static IntentFilter intentFoilter;
    public static boolean isPhoneCall;
    public static boolean isCall;
    private static int bandType = 1;
    public static boolean isBleConnected = false;
    public static boolean isDfu = false;
    public static String deviceName = null;
    public static String deviceAddress = null;
    public static boolean isEnabled = true;
    private RxBleClient rxBleClient;
    public static boolean isNormalDisconnet = false;
    public static boolean isLogoVisible = true;
    private BleUtils bleUtils;
    private TelephonyManager tm;

    public static GazelleApplication getInstance() {
        return instance;
    }

    public static RxBleClient getRxBleClient(Context context) {
        GazelleApplication application = (GazelleApplication) context.getApplicationContext();
        return application.rxBleClient;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        initLogger();
        ViseBluetooth.getInstance().init(this);
        bleUtils = new BleUtils();
        ShareSDK.initSDK(this);
//        initOkHttp();
        rxBleClient = RxBleClient.create(this);
        RxBleClient.setLogLevel(RxBleLog.DEBUG);

        user = new User();

        instance = this;
        tm = (TelephonyManager) getSystemService(Service.TELEPHONY_SERVICE);
        tm.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);

//		Intent smsIntent = new Intent(this, SmsService.class);
//		startService(smsIntent);

        Intent intent = new Intent(this, BluetoothService.class);
        bindService(intent, mServiceConnection, BIND_AUTO_CREATE);

        Bugtags.start("5f1b2bd5c0e6fcb208661ab9651ddce0", this, Bugtags.BTGInvocationEventNone);
    }

    ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Logger.e("service is connected");
            mBluetoothService = ((BluetoothService.LocalBinder) iBinder).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Logger.e("service is disconnected");
            mBluetoothService = null;
        }
    };

    //来电状态监听
    PhoneStateListener listener = new PhoneStateListener() {

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            // TODO Auto-generated method stub
            //state 当前状态 incomingNumber,貌似没有去电的API
            super.onCallStateChanged(state, incomingNumber);
            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE://挂断
                    String address1 = PreferenceData.getAddressValue(getApplicationContext());
                    if (address1 != null && !address1.equals("")) {
                        if (GazelleApplication.isBleConnected) {
                            mBluetoothService.writeCharacteristic(bleUtils.sendMessage(1, 2, 0, 0, 0, 0));
                        }
                    }
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK://接听
                    String address2 = PreferenceData.getAddressValue(getApplicationContext());
                    if (address2 != null && !address2.equals("")) {
                        if (GazelleApplication.isBleConnected) {
                            mBluetoothService.writeCharacteristic(bleUtils.sendMessage(1, 2, 0, 0, 0, 0));
                        }
                    }
                    break;
                case TelephonyManager.CALL_STATE_RINGING://来电
                    System.out.println("响铃:来电号码" + incomingNumber); //输出来电号码
                    String address = PreferenceData.getAddressValue(getApplicationContext());
                    if (address != null && !address.equals("")) {
                        if (GazelleApplication.isBleConnected) {
                            int pstate = PreferenceData.getNotificationPhoneState(getApplicationContext());
                            int shake = PreferenceData.getNotificationShakeState(getApplicationContext());
                            if (pstate == 1) {
                                mBluetoothService.writeCharacteristic(bleUtils.sendMessage(1, pstate, 0, 0, 0, shake));
                            }
                        }

                    }
                    break;
            }
        }
    };

    private void initOkHttp() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }

    public int getWatchType() {
        return bandType;
    }

    public void setWatchType(int bandType) {
        GazelleApplication.bandType = bandType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void onTerminate() {
        ViseBluetooth.getInstance().clear();
        super.onTerminate();
    }

    private void initLogger() {
        Logger.init("hybridwatch").methodCount(2).methodOffset(0).logLevel(LogLevel.FULL).hideThreadInfo();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
