package com.xyy.Gazella.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

import com.exchange.android.engine.Uoi;
import com.exchange.android.engine.Uoo;
import com.juts.framework.exp.JException;
import com.juts.framework.vo.DataSet;
import com.juts.framework.vo.Row;
import com.xyy.Gazella.exchange.ServicesBase;
import com.ysp.newband.BaseActivity;
import com.ysp.newband.GazelleApplication;
import com.ysp.smartwatch.R;


     /** 启动页面 */
public class StartActivity extends BaseActivity {
	private GazelleApplication app;


	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_start);
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent intent = new Intent(StartActivity.this,LaunchActivity.class);
				startActivity(intent);
				finish();
			}
		},1000);
	}

}
