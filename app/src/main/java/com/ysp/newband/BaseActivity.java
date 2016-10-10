package com.ysp.newband;


import android.bluetooth.BluetoothAdapter;
import android.os.Bundle;
import android.os.Handler;

import com.exchange.android.engine.ExchangeProxy;
import com.juts.android.ActivityBase;
import com.xyy.Gazella.exchange.ExangeErrorHandler;



public class BaseActivity extends ActivityBase {
	
	private static final String TAG=BaseActivity.class.getName();
		
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		ExchangeProxy.setApplicationDefaultErrorHandle(new ExangeErrorHandler());// 设置报错处理handler
		ExchangeProxy.setProgressModelVisible(false);// 设置弹出框是否显示
	}
	
	protected void ConnectionDevice(Handler mHandler) {				
		if (GazelleApplication.CONNECTED == -1) {
			if (GazelleApplication.getInstance().mService != null) {
				GazelleApplication.getInstance().mService.initialize();
				GazelleApplication.getInstance().mService.setActivityHandler(mHandler);
				GazelleApplication.getInstance().mService.registe(GazelleApplication.UUID);
			}
		} else {
			GazelleApplication.getInstance().mService.setActivityHandler(mHandler);
		}	
	}
	@Override
	protected void onStart() {
		super.onStart();
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	@Override
	protected void onPause() {
		super.onPause();
	}
	@Override
	protected void onResume() {
		super.onResume();
	}
	@Override
	protected void onStop() {
		super.onStop();
	}
}
