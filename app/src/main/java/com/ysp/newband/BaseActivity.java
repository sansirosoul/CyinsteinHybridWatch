package com.ysp.newband;


import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.WindowManager;

import com.exchange.android.engine.ExchangeProxy;
import com.juts.android.ActivityBase;
import com.xyy.Gazella.exchange.ExangeErrorHandler;
import com.ysp.smartwatch.R;


public class BaseActivity extends ActivityBase {

	
	private static final String TAG=BaseActivity.class.getName();

	public static Context mContext;
		
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		ExchangeProxy.setApplicationDefaultErrorHandle(new ExangeErrorHandler());// 设置报错处理handler
		ExchangeProxy.setProgressModelVisible(false);// 设置弹出框是否显示
		mContext = this;
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

	/** 回退键*/
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			animfinish();
		}
		return super.onKeyDown(keyCode, event);
	}

	public void animfinish() {
		mContext = null;
		finish();
		overridePendingTransitionExit(this);
	}

	/***
	 *    进入页面调用  从左到右进入
	 * @param at
     */
	public static void overridePendingTransitionEnter(Activity at){
		at.overridePendingTransition(R.anim.in_from_right, R.anim.out_righttoleft);
	}

	/***
	 *  退出页面调用 从右到左退出
	 * @param at
     */
	public static void overridePendingTransitionExit(Activity at){
		at.overridePendingTransition(R.anim.in_lefttoright, R.anim.out_to_left);
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
