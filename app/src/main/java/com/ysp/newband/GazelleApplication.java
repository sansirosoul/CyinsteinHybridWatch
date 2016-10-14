package com.ysp.newband;

import java.util.ArrayList;

import android.app.Application;
import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import com.xyy.Gazella.BroadcastReceiver.PhoneBroadcastReceiver;
import com.xyy.Gazella.googlebth.BluetoothLeService;
import com.xyy.Gazella.services.BluetoothService;
import com.xyy.model.User;

public class GazelleApplication extends Application {
	
	
	private static GazelleApplication instance;
	public static int SCREEN_WIDTH = 800;
	public static int SCREEN_HEIGHT = 480;
	public static BluetoothLeService mService;
	public static BluetoothService mBluetoothService;
	public static ServiceConnection mServiceConnection;
	public static BluetoothDevice device;
	public static int USER_ID = 1;
	private User user;
	public static int CONNECTED = -1;
	public static String UUID;
	// 来电监听广播
	public static PhoneBroadcastReceiver phoneBroadcastReceiver;
	public static IntentFilter intentFoilter;
	public static boolean isPhoneCall;
	public static boolean isCall;
	private static int bandType=1;

	public static GazelleApplication getInstance() {
		return instance;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		phoneRegisterReceiver();

//		CrashHandler crashHandler = CrashHandler.getInstance();
//		crashHandler.init(getApplicationContext());

		user = new User();
	//	alarmClockList = new ArrayList<AlarmClock>();
		instance = this;
		mServiceConnection = new ServiceConnection() {
			public void onServiceConnected(ComponentName className, IBinder rawBinder) {
				System.out.println("come in");
				try {
//					mService = ((BluetoothLeService.LocalBinder) rawBinder).getService();
					mBluetoothService=((BluetoothService.LocalBinder) rawBinder).getService();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			public void onServiceDisconnected(ComponentName classname) {
				System.out.println("come on");
//				mService = null;
				mBluetoothService=null;
			}
		};
		Intent bindIntent = new Intent(this, BluetoothService.class);
		startService(bindIntent);
		bindService(bindIntent, mServiceConnection, Context.BIND_AUTO_CREATE);

//		Intent bindIntent = new Intent(this, BluetoothLeService.class);
//		startService(bindIntent);
//		bindService(bindIntent, mServiceConnection, Context.BIND_AUTO_CREATE);
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

	/**
	 * 注册来电监听广播
	 */
	private void phoneRegisterReceiver() {
		intentFoilter = new IntentFilter();
		intentFoilter.addAction(TelephonyManager.ACTION_PHONE_STATE_CHANGED);
		phoneBroadcastReceiver = new PhoneBroadcastReceiver(this);
	}

	@Override
	public void onTerminate() {
		unbindService(mServiceConnection);
//		stopService(new Intent(this, BluetoothLeService.class));
		stopService(new Intent(this, BluetoothService.class));
		super.onTerminate();
	}

	public static void RegisterReceiver(Context context) {
		context.registerReceiver(phoneBroadcastReceiver, intentFoilter);
	}

	public static void UnRegisterReceiver(Context context) {
		context.unregisterReceiver(phoneBroadcastReceiver);
	}	
}
