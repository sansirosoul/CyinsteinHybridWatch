package com.xyy.Gazella.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.ysp.hybridtwatch.R;
import com.ysp.newband.BaseActivity;


     /** 启动页面 */
public class StartActivity extends BaseActivity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent intent = new Intent(StartActivity.this,LaunchActivity.class);
				startActivity(intent);
				overridePendingTransitionEnter(StartActivity.this);
				finish();
			}
		},1000);
	}
}
