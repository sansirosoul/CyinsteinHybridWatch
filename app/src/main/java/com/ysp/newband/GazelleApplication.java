package com.ysp.newband;

import android.app.Application;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.support.multidex.MultiDex;

import com.bugtags.library.Bugtags;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.polidea.rxandroidble.RxBleClient;
import com.polidea.rxandroidble.internal.RxBleLog;
import com.vise.baseble.ViseBluetooth;
import com.xyy.Gazella.googlebth.BluetoothLeService;
import com.xyy.Gazella.services.BluetoothService;
import com.xyy.Gazella.services.SmsService;
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
	public static ServiceConnection mServiceConnection;
	public static BluetoothLeService mService;
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
	private static int bandType=1;
	public static boolean isBleConnected = false;
	public static boolean isDfu=false;
	public static String deviceName = null;
	public static String deviceAddress = null;
    public static boolean isEnabled=true;
	private RxBleClient rxBleClient;
    public static boolean isNormalDisconnet = false;
	public static boolean isLogoVisible = true;


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
		ShareSDK.initSDK(this);
//        initOkHttp();
		rxBleClient = RxBleClient.create(this);
		RxBleClient.setLogLevel(RxBleLog.DEBUG);

		user = new User();

		instance = this;

		Intent smsIntent = new Intent(this, SmsService.class);
		startService(smsIntent);

		Bugtags.start("5f1b2bd5c0e6fcb208661ab9651ddce0", this, Bugtags.BTGInvocationEventNone );
	}

	private void initOkHttp(){
		OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggerInterceptor("TAG"))
				.connectTimeout(10000L, TimeUnit.MILLISECONDS)
				.readTimeout(10000L, TimeUnit.MILLISECONDS)
				//其他配置
				.build();
		OkHttpUtils.initClient(okHttpClient);
	}

	public  int getWatchType() {
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
		BaseActivity.cleanObservable();
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
