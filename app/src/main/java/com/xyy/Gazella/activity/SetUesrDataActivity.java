package com.xyy.Gazella.activity;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.exchange.android.engine.Uoi;
import com.exchange.android.engine.Uoo;
import com.juts.framework.exp.JException;
import com.juts.utility.StringUtil;
import com.xyy.Gazella.exchange.ServicesBase;
import com.xyy.Gazella.fileupload.FormFile;
import com.xyy.Gazella.fileupload.HttpFilePost;
import com.ysp.newband.BaseActivity;
import com.ysp.newband.GazelleApplication;
import com.ysp.smartwatch.R;

public class SetUesrDataActivity extends BaseActivity  {

	private static final String TAG=SetUesrDataActivity.class.getName();
	private GazelleApplication app;
	private String date;
	private int[] activityTIME = new int[] { 15, 30, 45, 60, 75, 90, 105, 120, 135, 150, 165, 180 };
	
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// mProgressDialog.dismiss();
			switch (msg.what) {
			case 0:// 发送请求返回数据
				byte[] result = (byte[]) msg.obj;
				String str = "";
				for (int i = 0; i < result.length; i++) {
					char s = (char) result[i];
					// System.out.println("result-------->" + s);
					String str1 = String.valueOf(s);
					str = str + str1;
					str.substring(0, 1);
				}
				if (str.substring(0, 5).equals("ALARM")) {

					String no_activity_time = "45";
					String start_time = "AM 9:30";
					String end_time = "PM 5:00";

					String activityTime = no_activity_time;
					byte noactivity = 45;

					String starttime = start_time;
					String t = starttime.substring(2);
					String[] tt = StringUtil.toArray(t.trim(), ":");
					byte start_time_hour = 0;
					byte start_time_min = 0;

					if (starttime.substring(0, 2).equals("PM")) {
						start_time_hour = (byte) (Integer.parseInt(tt[0]) + 12);
					} else {
						start_time_hour = (byte) Integer.parseInt(tt[0]);
					}
					start_time_min = (byte) Integer.parseInt(tt[1]);
					byte end_time_hour = 0;
					byte end_time_min = 0;
					String endtime = end_time;
					String te = endtime.substring(2);
					String[] tte = StringUtil.toArray(te.trim(), ":");
					if (endtime.subSequence(0, 2).equals("PM")) {
						end_time_hour = (byte) (Integer.parseInt(tte[0]) + 12);
					} else {
						end_time_hour = (byte) Integer.parseInt(tte[0]);
					}
					end_time_min = (byte) Integer.parseInt(tte[1]);
					String E = "E";
					byte[] e = E.getBytes();
					String D = "D";
					byte[] d = D.getBytes();
					GazelleApplication.getInstance().mService.sendData("IDLEALERT", new byte[] {
							start_time_hour, start_time_min, end_time_hour, end_time_min, noactivity, e[0] });
				} else if (str.substring(0, 3).equals("IDL")) {
					byte xiaoshuichangdu = 28;
					byte longxiaoshuichangdu = 45;
					String E = "E";
					byte[] e = E.getBytes();
					String D = "D";
					byte[] d = D.getBytes();
					GazelleApplication.getInstance().mService.sendData("POWERNAP", new byte[] {
							xiaoshuichangdu, longxiaoshuichangdu, e[0] });
				} else if (str.subSequence(0, 3).equals("POW")) {
					String D = "D"; 
					byte[] d = D.getBytes();
					GazelleApplication.getInstance().mService.sendData("PHONE",
							new byte[] { d[0], 0, 0, 0, 0 });
				}
				break;
			case 1:// 匹配成功
				sendAlarm();
				break;
			case -1:// 错误
				break;
			case 2:
				break;
			case 3:

				break;
			case 4:
				break;
			}
			super.handleMessage(msg);
		}
	};

	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.set_user_data_activity);
		Calendar calendar = Calendar.getInstance();
		DateFormat format = new SimpleDateFormat("yyMMdd");
		date = format.format(calendar.getTime());
		addUserData();		
		ConnectionDevice(mHandler);
		sendAlarm();
	}
	/**发送警报*/
	private void sendAlarm() {
		String e = "E";
		byte[] E = e.getBytes();
		GazelleApplication.getInstance().mService.sendData("ALARM", new byte[] { 0, 8, 0, 0, 10, (byte) 0,
				(byte) 0, E[0] });
	}
	/**添加手环用户信息*/
	private void addBracelet() {
		Uoi uoi = new Uoi("addBracelet");
		try {
			uoi.set("number", app.getUser().getID());
			uoi.set("name", "");
			uoi.set("uuid", GazelleApplication.UUID);
			uoi.set("address", "");
			ServicesBase.connectService(SetUesrDataActivity.this, uoi, true);
		} catch (JException e) {
			e.printStackTrace();
		}
	}
	/**添加手表用户信息*/
	private void addUserData()  {
		Uoi uoi = new Uoi("addPersonalInformation");
		// GazellaApplication.USER_ID = 1;
		//app.getUser().setUser_id(GazelleApplication.USER_ID + "");
		try {
			uoi.set("user_id", GazelleApplication.USER_ID);
			uoi.set("user_name", "admin");
			uoi.set("nickname","admin");
			uoi.set("head_url", "");
			uoi.set("sex", 0);
			uoi.set("birthday", "1985-01-01");
			uoi.set("height", "170 厘米");
			uoi.set("weight", "60 KG");
			uoi.set("signature","CT00116041800020");
			uoi.set("register_date", "V2.0");
			uoi.set("last_login_date", "160930");
			uoi.set("user_grade", "1");
			uoi.set("user_grade_value", "0");
			uoi.set("user_account", "admin");
			uoi.set("password","admin");
			uoi.set("UUID", GazelleApplication.UUID);
			ServicesBase.connectService(SetUesrDataActivity.this, uoi, true);
		} catch (JException e) {
			e.printStackTrace();
		}		
	}

	@Override
	public void callbackByExchange(Uoi uoi, Uoo uoo) {
		try {
			if (uoi.sService.equals("registBangleUser")) {
				String user_id = uoo.getString("USER_ID");
				GazelleApplication.USER_ID = Integer.parseInt(user_id);
				//User_info user = new User_info();
			//	user.setBirthday(app.getUser().getBirthday());
				String height = app.getUser().getHeight();
				int hei = 0;
				if (height.lastIndexOf("\"") != -1) {
					int h = Integer.parseInt(height.substring(0, height.lastIndexOf("'"))) * 12;
					int hh = Integer.parseInt(height.substring(height.lastIndexOf("'") + 1,
							height.lastIndexOf("\"")));
					int small = h + hh;
					hei = (int) (small * 2.54);
				} else {
					String s = height.substring(0, height.length() - 2).trim();
					hei = Integer.parseInt(s);
				}
			//	user.setHeight(hei + "");

				String WEIGHT = app.getUser().getWeight();
				double weight = 0;
				if (WEIGHT.lastIndexOf("lbs") != -1) {
					String s = WEIGHT.replace("lbs", "").trim();
					double w = Double.parseDouble(s);
					weight = w * 0.4535;
				} else {
					String s = StringUtil.toArray(WEIGHT, " ")[0];
					// StringUtil.toArray(WEIGHT, " ")[0];
					weight = Double.parseDouble(s);
				}
//         	user.setWeight((int) weight + "");
//				user.setNikename(app.getUser().getNickname());
//				user.setPlusName(app.getUser().getBraceletName());
//				user.setPlusSN(app.getUser().getID());
//				user.setSEX(app.getUser().getSex() + "");
//				user.setName(app.getUser().getName());
//				user.setUUID(GazelleApplication.UUID);
//				user.setFWS(app.getUser().getFWVERSION());
//				sengImage(NamedActivity.imageViewuri);
//				NetService.uploadBangleData(SetUesrDataActivity.this, GazelleApplication.USER_ID + "", user,
//						null, null);
			} else if (uoi.sService.equals("uploadBangleDataSyn")) {
				addUserData();
			} else if (uoi.sService.equals("addPersonalInformation")) {
				Log.e(TAG, "--------------come in addUser------------------");
				System.out.println(uoo.oForm);
				if (uoo.getString("USER_ID") == null || uoo.getString("USER_ID").equals("")) {
					addUserData();
				} else {
					Intent i = new Intent(SetUesrDataActivity.this,HomeActivity.class);
					startActivity(i);
				}
			}
		} catch (JException e) {
			e.printStackTrace();
		}
	}
}
