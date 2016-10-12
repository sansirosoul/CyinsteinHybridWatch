package com.xyy.Gazella.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
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
	private Button start_btn;
	private GazelleApplication app;
	private String username;
	private String password;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		app = (GazelleApplication) getApplication();
		SharedPreferences userInfo = getSharedPreferences("user_info", 0);
		if (userInfo != null) {
			username = userInfo.getString("name", "");
			password = userInfo.getString("pass", "");
			String isSave = userInfo.getString("isSave", "");
			if (isSave.equals("1")) {
				try {
					queryUserAccount(username);
				} catch (JException e) {
					e.printStackTrace();
				}
			}
		}
		setContentView(R.layout.activity_start);
		start_btn = (Button) findViewById(R.id.start_btn);
		start_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				app.getUser().setUser_account("admin");
				app.getUser().setPassword("admin");	
				startActivity(new Intent(StartActivity.this, DeviceListActivity.class));
				StartActivity.this.finish();
			}
		});
	}

	private void queryUserAccount(String user_account) throws JException {
		Uoi uoi = new Uoi("queryUserAccount");
		uoi.set("USER_ACCOUNT", user_account);
		ServicesBase.connectService(StartActivity.this, uoi, false);
	}

	@Override
	public void callbackByExchange(Uoi uoi, Uoo uoo) {
		try {
			if (uoo != null && uoo.iCode > 0) {
				if (uoi.sService.equals("queryUserAccount")) {
					System.out.println("come in password");
					DataSet ds = uoo.getDataSet("ds");
					String pass = null;
					for (int i = 0; i < ds.size(); i++) {
						System.out.println("come to password");
						Row row = (Row) ds.get(0);
						GazelleApplication.USER_ID = Integer.parseInt(row.getString("user_id"));
						Log.e("", "user_id:" + GazelleApplication.USER_ID);
						System.out.println(row.getString("user_account"));
						username = row.getString("user_account");
						System.out.println(row.getString("password"));
						pass = row.getString("password");
					}
					if (pass != null && pass.equals(password)) {

					}
				}
			}
		} catch (JException e) {
			e.printStackTrace();
		}
	}
}
