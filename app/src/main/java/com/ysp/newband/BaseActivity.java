package com.ysp.newband;


import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.WindowManager;

import com.exchange.android.engine.ExchangeProxy;
import com.exchange.android.engine.Uoi;
import com.exchange.android.engine.Uoo;
import com.xyy.Gazella.exchange.ExangeErrorHandler;
import com.ysp.smartwatch.R;


public class BaseActivity extends FragmentActivity {

	
	private static final String TAG=BaseActivity.class.getName();

	public static Context mContext;
		
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		ExchangeProxy.setApplicationDefaultErrorHandle(new ExangeErrorHandler());// 设置报错处理handler
		ExchangeProxy.setProgressModelVisible(false);// 设置弹出框是否显示

		if(Build.VERSION.SDK_INT >= 19){
			//透明状态栏
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			//透明导航栏
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		}
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

	public Handler handler = new Handler() {
		public void handleMessage(Message var1) {
			Uoi var2 = (Uoi)var1.getData().getSerializable("INPUT_DATA");
			Uoo var3 = (Uoo)var1.getData().getSerializable("RETURN_DATA");
			BaseActivity.this.callbackByExchange(var2, var3);
		}
	};

	public BaseActivity() {
	}

	public void callbackByExchange(Uoi var1, Uoo var2) {
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
