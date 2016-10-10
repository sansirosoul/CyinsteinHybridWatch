package com.xyy.Gazella.activity;
/**连接设备页面**/
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.exchange.android.engine.Uoi;
import com.juts.framework.exp.JException;
import com.xyy.Gazella.exchange.ServicesBase;
import com.ysp.newband.BaseActivity;
import com.ysp.newband.BluetoothInstruction;
import com.ysp.newband.GazelleApplication;
import com.ysp.smartwatch.R;


public class ConnectionDevice extends BaseActivity {

	private static final String TAG=ConnectionDevice.class.getName();
	
	private String DeviceRess;
	private GazelleApplication app;
	
	private Handler mHandler= new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);		
			switch (msg.what) {
			case 0:     // 发送请求返回数据				
				byte[] result = (byte[]) msg.obj;  //  序列号
				String str = "";
				for (int i = 0; i < result.length; i++) {
					char s = (char) result[i];
					String str1 = String.valueOf(s);
					str = str + str1;
					Log.i(TAG, str);
				}if (str.substring(0, 4).equals("TIME")) {
					GazelleApplication.getInstance().mService.sendData("BATTERY");
					Log.i(TAG, "发送时间指令");
				} else if (str.substring(0, 2).equals("SN")) {
					app.getUser().setID(str.substring(2));
					// 设置时间指令""内为年月日时分秒（用16进制表示）
					Calendar calendar = Calendar.getInstance();
					DateFormat format = new SimpleDateFormat("yyMMddHHmmss");
					calendar.get(calendar.WEEK_OF_MONTH);
					String time = format.format(calendar.getTime()) + "0" + (calendar.get(calendar.DAY_OF_WEEK) - 1);
					ArrayList<Integer> list = new ArrayList<Integer>();
					byte[] bt = new byte[7];
					for (int i = 0; i < time.length(); i += 2) {
						int b = Integer.parseInt(time.substring(i, i + 2));
						list.add(b);
					}
					for (int i = 0; i < list.size(); i++) {
						int k = list.get(i);
						bt[i] = (byte) k;
					}
					GazelleApplication.getInstance().mService.sendData(BluetoothInstruction.TIME, bt);
				
					Log.i(TAG, "发送序列号指令");
				} else if (str.substring(0, 4).equals("BATT")) {
					int b = (int) result[7];
					app.getUser().setBattery(b);
					GazelleApplication.getInstance().mService.sendData("FWVERSION");
					Log.i(TAG, "发送版本号指令");
				} else if (str.substring(0, 3).equals("FWV")) {// FWVERSION
					Log.i(TAG, "发送版本号指令成功");
					app.getUser().setFWVERSION(str.substring(9));
					Intent intent = new Intent(ConnectionDevice.this,SetUesrDataActivity.class);
					startActivity(intent);
				}	
				break;
				
			case 1:    // 匹配成功
				try {
					Thread.sleep(1000);
						new MyTask().execute();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				break;
			case -1:// 错误
				break;
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.connection_device);		
	    Intent intent=getIntent();
	    DeviceRess=intent.getStringExtra(BluetoothDevice.EXTRA_DEVICE);
	    app = (GazelleApplication) getApplication();
	    app.getUser().setUUID(DeviceRess);
	    GazelleApplication.UUID=DeviceRess;
	    ConnectionDevice(mHandler);
	}
	
	class MyTask extends AsyncTask<Object, Object, Object> {
		@Override
		protected void onPostExecute(Object result) {
			super.onPostExecute(result);
		}
		@SuppressWarnings("static-access")
		protected Object doInBackground(Object... params) {
			try {
				Thread.sleep(2000);
				GazelleApplication.getInstance().mService.sendData("GETSN");
				Thread.sleep(2000);
				new Thread() {
					@Override
					public void run() {
						try {
							sleep(20000);
						} catch (InterruptedException e) {
							e.printStackTrace();
							Message msg = new Message();
							msg.what = 404;
							mHandler.sendMessage(msg);
						}
						super.run();
					}
				}.start();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return null;
		}
	}
}
