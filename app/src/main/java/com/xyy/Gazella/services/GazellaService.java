package com.xyy.Gazella.services;

import java.sql.SQLException;

import android.util.Log;

import com.juts.framework.data.DBConn;
import com.juts.framework.data.DataAccess;
import com.juts.framework.engine.Service;
import com.juts.framework.exp.JException;
import com.juts.framework.vo.DataSet;
import com.juts.framework.vo.Row;

@SuppressWarnings("unchecked")
public class GazellaService extends Service {

	public void queryServices(String sMethod) throws JException, SQLException {
		String sServiceName = ivo.sService;
		sSql = "select * from fm_services where service_name='" + sServiceName + "'";
		oResultSet = DataAccess.query(sSql, oConn);
		DataSet ds = new DataSet();
		while (oResultSet.next()) {
			Row row = new Row();
			row.put("service_name", oResultSet.getString("service_name"));
			row.put("class_name", oResultSet.getString("class_name"));
			ds.add(row);
		}
		ovo.set("ds", ds);
	}

	public void addBracelet(String sMethod) throws JException, SQLException {
		String sSn = ivo.getString("sn");
		sSql = "select count(*) from bl_device where number='" + sSn + "'";
		oResultSet = DataAccess.query(sSql, oConn);
		Row bracelet = new Row();
		boolean existBl = false;
		if (oResultSet.next() && oResultSet.getInt(1) > 0) {// 存在手环数据
			existBl = true;
		} else {
			String sDevId = DataAccess.getSequence("BL_DEVICE.DEVICE_ID", oConn);
			System.out.println("sDevId=" + sDevId);
			bracelet.put("DEVICE_ID", sDevId);
		}
		String sUuid = ivo.getString("uuid");
		String sName = ivo.getString("name");
		String sAddr = ivo.getString("addr");
		bracelet.put("number", sSn);
		bracelet.put("name", sName);
		bracelet.put("uuid", sUuid);
		bracelet.put("address", sAddr);
		if (!existBl)
			DataAccess.add("BL_DEVICE", bracelet, oConn);
		else
			DataAccess.edit("BL_DEVICE", "number='" + sSn + "'", bracelet, oConn);
	}

	public void delBracelet(String sMethod) throws JException, SQLException {
		String sDevId = ivo.getString("dev_id");
		String alldelete = ivo.getString("delete");
		String user_id = ivo.getString("user_id");
		if (alldelete == null || alldelete.equals("")) {
			sSql = "delete from BL_DEVICE where device_id=" + sDevId;
			DataAccess.modify(sSql, oConn);
		} else {
			Log.e("", "11111111111111111111111111111111111");
			// 删除原始数据
			sSql = "delete from BL_DATA_ORIGINAL where user_id=" + user_id;
			DataAccess.modify(sSql, oConn);
			// 删除统计数据
			Log.e("", "2222222222222222222222222222222222222222");
			sSql = "delete from BL_DATA_STATISTICS where user_id=" + user_id;
			DataAccess.modify(sSql, oConn);
			// 删除状态数据
			Log.e("", "333333333333333333333333333333333333333");
			sSql = "delete from BL_ACTIVITY_TYPE where user_id=" + user_id;
			DataAccess.modify(sSql, oConn);
		}
	}

	/**
	 * 增加用户
	 */
	public void addUser(String sMethod) throws JException, SQLException {
		System.out.println("come in addUser");
		System.out.println(ivo.oForm);
		String sUser_account = ivo.getString("user_account");
		sSql = "select count(*) from bl_user where user_account='" + sUser_account + "'";
		oResultSet = DataAccess.query(sSql, oConn);
		Row user = new Row();
		boolean existBl = false;
		if (oResultSet.next() && oResultSet.getInt(1) > 0) {// 存在手环数据
			existBl = true;
		} else {
			String sDevId = DataAccess.getSequence("BL_USER.USER_ID", oConn);
			System.out.println("sDevId=" + sDevId);
			// user.put("USER_ID", sDevId);
		}
		String sUser_id = ivo.getString("user_id");
		String sAccount = ivo.getString("user_account");
		String sPass = ivo.getString("password");
		user.put("user_id", sUser_id);
		user.put("user_account", sAccount);
		user.put("password", sPass);
		if (!existBl) {
			DataAccess.add("bl_user", user, oConn);
			DataSet ds = new DataSet();
			Row row = new Row();
			row.put("error", "yes");
			ds.add(row);
			ovo.set("ds", ds);
		} else {
			// DataAccess.edit("bl_user", "user_account='" + sUser_account +
			// "'",
			// user, oConn);
			DataSet ds = new DataSet();
			Row row = new Row();
			row.put("error", "error");
			ds.add(row);
			ovo.set("ds", ds);
		}
	}

	/**
	 * 添加修改头像
	 * 
	 * @param sMethod
	 * @throws JException
	 * @throws SQLException
	 */
	public void addHeadUrl(String sMethod) throws JException, SQLException {
		String sUserId = ivo.getString("user_id");
		sSql = "select count(*) from bl_user where user_id='" + sUserId + "'";
		oResultSet = DataAccess.query(sSql, oConn);
		Row user = new Row();
		boolean existBl = false;
		if (oResultSet.next() && oResultSet.getInt(1) > 0) {// 存在手环数据
			existBl = true;
		} else {
			String sDevId = DataAccess.getSequence("BL_USER.USER_ID", oConn);
			System.out.println("sDevId=" + sDevId);
			user.put("USER_ID", sDevId);
		}
		String HEAD_URL = ivo.getString("HEAD_URL");
		user.put("user_id", sUserId);
		user.put("HEAD_URL", HEAD_URL);
		if (!existBl) {
			DataAccess.add("bl_user", user, oConn);
		} else {
			DataAccess.edit("bl_user", "user_id='" + sUserId + "'", user, oConn);
		}
	}

	/**
	 * 查询用户名
	 */
	public void queryUserAccount(String sMethod) throws JException, SQLException {
		String sUser_accout = ivo.getString("USER_ACCOUNT");
		sSql = "select * from bl_user where user_account='" + sUser_accout + "'";
		oResultSet = DataAccess.query(sSql, oConn);
		System.out.println("sSql:" + sSql);
		DataSet ds = new DataSet();
		while (oResultSet.next()) {
			System.out.println("进入循环");
			Row row = new Row();
			row.put("user_id", oResultSet.getString("user_id"));
			row.put("user_account", oResultSet.getString("user_account"));
			row.put("password", oResultSet.getString("password"));
			ds.add(row);
		}
		ovo.set("ds", ds);
	}

	/**
	 * 增加修改保存密码
	 * 
	 * @param sMethod
	 * @throws JException
	 * @throws SQLException
	 */
	public void addPassWord(String sMethod) throws JException, SQLException {
		String sUser_account = ivo.getString("sn");
		sSql = "select count(*) from bl_user where user_account='" + sUser_account + "'";
		oResultSet = DataAccess.query(sSql, oConn);
		Row user = new Row();
		boolean existBl = false;
		if (oResultSet.next() && oResultSet.getInt(1) > 0) {// 存在手环数据
			existBl = true;
		} else {
			String sDevId = DataAccess.getSequence("BL_USER.USER_ID", oConn);
			System.out.println("sDevId=" + sDevId);
			user.put("USER_ID", sDevId);
		}
		String sAccount = ivo.getString("user_account");
		String sPass = ivo.getString("password");
		user.put("user_account", sAccount);
		user.put("password", sPass);
		if (!existBl)
			DataAccess.add("bl_user", user, oConn);
		else
			DataAccess.edit("bl_user", "user_account='" + sUser_account + "'", user, oConn);
	}

	/**
	 * 查询是否保存密码
	 * 
	 * @param sMethod
	 * @throws JException
	 * @throws SQLException
	 */

	public void queryPassWord(String sMethod) throws JException, SQLException {
		// String sUser_id = "我!111是账号!!!!!!!!";
		String sUser_id = ivo.getString("user_id");
		sSql = "select * from bl_user where user_id='" + sUser_id + "'";
		oResultSet = DataAccess.query(sSql, oConn);
		DataSet ds = new DataSet();
		while (oResultSet.next()) {
			Row row = new Row();
			row.put("user_id", oResultSet.getString("user_id"));
			row.put("user_account", oResultSet.getString("user_account"));
			row.put("password", oResultSet.getString("password"));
			ds.add(row);
		}
		ovo.set("ds", ds);
	}

	/**
	 * 增加修改手环设备信息
	 * 
	 * @param sMethod
	 * @throws JException
	 * @throws SQLException
	 */
	public void addBraceletInformation(String sMethod) throws JException, SQLException {
		String sNumber = ivo.getString("number");
		sSql = "select count(*) from bl_device where number='" + sNumber + "'";
		oResultSet = DataAccess.query(sSql, oConn);
		Row user = new Row();
		boolean existBl = false;
		if (oResultSet.next() && oResultSet.getInt(1) > 0) {// 存在手环数据
			existBl = true;
		} else {
			String sDevId = DataAccess.getSequence("BL_DEVICE.DEVICE_ID", oConn);
			System.out.println("sDevId=" + sDevId);
			user.put("DEVICE_ID", sDevId);
		}
		String number = ivo.getString("number");
		String sName = ivo.getString("name");
		String sUUID = ivo.getString("UUID");
		String sAddress = ivo.getString("address");
		user.put("number", number);
		user.put("name", sName);
		user.put("UUID", sUUID);
		user.put("address", sAddress);
		if (!existBl)
			DataAccess.add("bl_device", user, oConn);
		else
			DataAccess.edit("bl_device", "number='" + sNumber + "'", user, oConn);
	}

	/**
	 * 查询手环设备信息
	 * 
	 * @param sMehod
	 * @throws JException
	 * @throws SQLException
	 */
	public void queryBraceletInformation(String sMehod) throws JException, SQLException {
		String sDevice_id = ivo.getString("number");
		sSql = "select * from bl_device where number='" + sDevice_id + "'";
		oResultSet = DataAccess.query(sSql, oConn);
		DataSet ds = new DataSet();
		while (oResultSet.next()) {
			Row row = new Row();
			row.put("device_id", oResultSet.getString("device_id"));
			row.put("number", oResultSet.getString("number"));
			row.put("name", oResultSet.getString("name"));
			row.put("UUID", oResultSet.getString("UUID"));
			row.put("address", oResultSet.getString("address"));
			ds.add(row);
		}
		ovo.set("ds", ds);
	}

	/**
	 * 增加修改个人信息
	 * 
	 * @param sMethod
	 * @throws JException
	 * @throws SQLException
	 */
	public void addPersonalInformation(String sMethod) throws JException, SQLException {
		String user_id = ivo.getString("user_id");
		sSql = "select count(*) from bl_user where user_id='" + user_id + "'";
		oResultSet = DataAccess.query(sSql, oConn);
		Row user = new Row();
		boolean existBl = false;
		if (oResultSet.next() && oResultSet.getInt(1) > 0) {// 存在手环数据
			existBl = true;
		} else {
			String sDevId = DataAccess.getSequence("BL_USER.USER_ID", oConn);
			System.out.println("sDevId=" + sDevId);
			// user.put("USER_ID", sDevId);
		}
		String user_name = ivo.getString("user_name");
		String nickname = ivo.getString("nickname");
		String head_url = ivo.getString("head_url");
		String sex = ivo.getString("sex");
		String birthday = ivo.getString("birthday");
		String height = ivo.getString("height");
		String weight = ivo.getString("weight");
		String password = ivo.getString("password");
		String signature = ivo.getString("signature");
		String register_date = ivo.getString("register_date");
		String last_login_date = ivo.getString("last_login_date");
		String user_grade = ivo.getString("user_grade");
		String user_grade_value = ivo.getString("user_grade_value");
		String user_account = ivo.getString("user_account");
		String UUID = ivo.getString("UUID");
		String sleeptarget = ivo.getString("sleeptarget");
		String movetarget = ivo.getString("movetarget");
		String remember_password = ivo.getString("remember_password");
		user.put("remember_password", remember_password);
		user.put("user_id", user_id);
		user.put("user_name", user_name);
		user.put("nickname", nickname);
		user.put("head_url", head_url);
		user.put("sex", sex);
		user.put("birthday", birthday);
		user.put("height", height);
		user.put("weight", weight);
		user.put("password", password);
		user.put("signature", signature);
		user.put("register_date", register_date);
		user.put("last_login_date", last_login_date);
		user.put("user_grade", user_grade);
		user.put("user_grade_value", user_grade_value);
		user.put("user_account", user_account);
		user.put("movetarget", movetarget);
		user.put("sleeptarget", sleeptarget);
		user.put("UUID", UUID);
		if (!existBl)
			DataAccess.add("bl_user", user, oConn);
		else
			DataAccess.edit("bl_user", "user_id='" + user_id + "'", user, oConn);
	}

	/**
	 * 查询个人信息
	 * 
	 * @throws JException
	 */
	public void queryPersonalInformation(String sMethod) throws JException, SQLException {
		String sUserID = ivo.getString("user_id");
		sSql = "select * from bl_user where user_id='" + sUserID + "'";
		oStatement = DBConn.createStatement(oConn);
		oResultSet = DataAccess.query(sSql, oStatement);
		DataSet ds = new DataSet();
		while (oResultSet.next()) {
			Row row = new Row();
			row.put("user_id", sUserID);
			row.put("user_name", oResultSet.getString("user_name"));
			row.put("nickname", oResultSet.getString("nickname"));
			row.put("head_url", oResultSet.getString("head_url"));
			row.put("sex", oResultSet.getString("sex"));
			row.put("birthday", oResultSet.getString("birthday"));
			row.put("height", oResultSet.getString("height"));
			row.put("weight", oResultSet.getString("weight"));
			row.put("user_account", oResultSet.getString("user_account"));
			row.put("password", oResultSet.getString("password"));
			row.put("signature", oResultSet.getString("signature"));
			row.put("register_date", oResultSet.getString("register_date"));
			row.put("last_login_date", oResultSet.getString("last_login_date"));
			row.put("user_grade", oResultSet.getString("user_grade"));
			row.put("user_grade_value", oResultSet.getString("user_grade_value"));
			row.put("UUID", oResultSet.getString("UUID"));
			row.put("movetarget", oResultSet.getString("movetarget"));
			row.put("sleeptarget", oResultSet.getString("sleeptarget"));
			row.put("remember_password", oResultSet.getString("remember_password"));
			ds.add(row);
		}
		ovo.set("ds", ds);
		DBConn.close(oStatement);
	}

	/**
	 * 修改用户信息
	 * 
	 * @throws JException
	 * @throws SQLException
	 */
	public void addUserData(String sMethod) throws JException, SQLException {
		String user_id = ivo.getString("user_id");
		sSql = "select count(*) from bl_user where user_id='" + user_id + "'";
		oResultSet = DataAccess.query(sSql, oConn);
		Row user = new Row();
		boolean existBl = false;
		if (oResultSet.next() && oResultSet.getInt(1) > 0) {// 存在手环数据
			existBl = true;
		} else {
			String sDevId = DataAccess.getSequence("BL_USER.USER_ID", oConn);
			System.out.println("sDevId=" + sDevId);
			user.put("USER_ID", sDevId);
		}
		String sex = ivo.getString("sex");
		String birthday = ivo.getString("birthday");
		String height = ivo.getString("height");
		String weight = ivo.getString("weight");
		user.put("user_id", user_id);
		user.put("sex", sex);
		user.put("birthday", birthday);
		user.put("height", height);
		user.put("weight", weight);
		if (!existBl)
			DataAccess.add("bl_user", user, oConn);
		else
			DataAccess.edit("bl_user", "user_id='" + user_id + "'", user, oConn);
	}

	/**
	 * 增加修改原始数据
	 */
	public void addOriginalData(String sMethod) throws JException, SQLException {
		String sSn = ivo.getString("user_id", true, "0");
		String sport_date = ivo.getString("sport_date");
		String pager = ivo.getString("pager");
		sSql = "select count(*) from bl_data_Original where user_id='" + sSn + "' and sport_date='" + sport_date + "' and pager='" + pager + "'";
		oResultSet = DataAccess.query(sSql, oConn);
		Row bracelet = new Row();
		boolean existBl = false;
		if (oResultSet.next() && oResultSet.getInt(1) > 0) {// 存在手环数据
			existBl = true;
		} else {
			String sDevId = DataAccess.getSequence("BL_DATA_ORIGINAL.USER_ID", oConn);
			System.out.println("sDevId=" + sDevId);
			bracelet.put("USER_ID", sDevId);
		}

		String step_num = ivo.getString("step_num");

		bracelet.put("user_id", sSn);
		bracelet.put("pager", pager);
		bracelet.put("sport_date", sport_date);
		bracelet.put("step_num", step_num);
		if (!existBl) {
			int i = DataAccess.add("bl_data_Original", bracelet, oConn);
			DataSet ds = new DataSet();
			Row row = new Row();
			row.put("aaa", i);
			ds.add(row);
			ovo.set("ds", ds);

		} else {
			int i = DataAccess.edit("bl_data_Original", "user_id='" + sSn + "'and sport_date='" + sport_date + "' and pager='" + pager + "'",
					bracelet, oConn);
			DataSet ds = new DataSet();
			Row row = new Row();
			row.put("ddd", i);
			ds.add(row);
			ovo.set("ds", ds);
		}
	}

	/**
	 * 查询原始数据
	 * 
	 * @param sMethod
	 * @throws JException
	 * @throws SQLException
	 */
	public void queryOriginalData(String sMethod) throws JException, SQLException {
		String sDevice_id = ivo.getString("sport_date");
		String sUserID = ivo.getString("user_id");
		String sUpdate = ivo.getString("update");
		if (!sDevice_id.equals("") && sDevice_id != null) {
			Log.e("", "sDevice_id====>>>" + sDevice_id + "   sUserID====>>" + sUserID + "   sUpdate=====>" + sUpdate);
			sSql = "select * from BL_DATA_ORIGINAL where sport_date='" + sDevice_id + "' and user_id = '" + sUserID + "'";
		} else {
			Log.e("111111111111111111111", "sDevice_id====>>>" + sDevice_id + "   sUserID====>>" + sUserID + "   sUpdate=====>" + sUpdate);
			sSql = "select * from BL_DATA_ORIGINAL where user_id = '" + sUserID + "'";
		}
		if (sUpdate.equals("yes")) {
			Log.e("", "111111111111111111111111111111111111111");
			sSql = "select * from BL_DATA_ORIGINAL where user_id = '" + sUserID + "' and sport_date >=" + sDevice_id;
		}
		oResultSet = DataAccess.query(sSql, oConn);
		DataSet ds = new DataSet();
		while (oResultSet.next()) {
			Row row = new Row();
			row.put("user_id", oResultSet.getString("user_id"));
			row.put("pager", oResultSet.getString("pager"));
			row.put("sport_date", oResultSet.getString("sport_date"));
			row.put("step_num", oResultSet.getString("step_num"));
			ds.add(row);
		}
		ovo.set("ds", ds);
	}

	/**
	 * 查询昨天原始数据
	 * 
	 * @param sMethod
	 * @throws JException
	 * @throws SQLException
	 */
	public void queryBeforeOriginalData(String sMethod) throws JException, SQLException {
		String sDevice_id = ivo.getString("sport_date");
		String sUserID = ivo.getString("user_id");
		sSql = "select * from BL_DATA_ORIGINAL where sport_date='" + sDevice_id + "' and user_id = '" + sUserID + "'";
		oResultSet = DataAccess.query(sSql, oConn);
		DataSet ds = new DataSet();
		while (oResultSet.next()) {
			Row row = new Row();
			row.put("user_id", oResultSet.getString("user_id"));
			row.put("pager", oResultSet.getString("pager"));
			row.put("sport_date", oResultSet.getString("sport_date"));
			row.put("step_num", oResultSet.getString("step_num"));
			ds.add(row);
		}
		ovo.set("ds", ds);
	}

	/**
	 * 增加修改统计数据
	 * 
	 * @param sMethod
	 * @throws JException
	 * @throws SQLException
	 */
	public void addDataStatistics(String sMethod) throws JException, SQLException {
		String sSn = ivo.getString("user_id");
		String sport_date = ivo.getString("sport_date");

		String del = ivo.getString("del");
		if (del != null && del.equals("del")) {
			sSql = "delete from BL_DATA_STATISTICS where sport_date= '" + sport_date + "' and user_id='" + sSn + "'";
			DataAccess.modify(sSql, oConn);
		} else {
			sSql = "select count(*) from BL_DATA_STATISTICS where user_id='" + sSn + "'and sport_date='" + sport_date + "'";
			oResultSet = DataAccess.query(sSql, oConn);
			Row row = new Row();
			boolean existBl = false;
			if (oResultSet.next() && oResultSet.getInt(1) > 0) {// 存在手环数据
				existBl = true;
			} else {
				String sDevId = DataAccess.getSequence("BL_DATA_STATISTICS.USER_ID", oConn);
				System.out.println("sDevId=" + sDevId);
				row.put("USER_ID", sSn);
			}
			String grade = ivo.getString("grade");

			String total_step = ivo.getString("total_step");
			String total_date = ivo.getString("total_date");
			String Cal = ivo.getString("Cal");
			String remove_num = ivo.getString("remove_num");
			String weight = ivo.getString("weight");
			String BMI = ivo.getString("BMI");
			String movetarget = ivo.getString("movetarget");
			String sleeptarget = ivo.getString("sleeptarget");
			String height = ivo.getString("height");
			String syn = ivo.getString("synchronous");
			String step_date = ivo.getString("step_date");
			String begin_date = ivo.getString("begin_date");

			row.put("begin_date", begin_date);
			row.put("user_id", sSn);
			row.put("grade", grade);
			row.put("sport_date", sport_date);
			row.put("total_step", total_step);
			row.put("total_date", total_date);
			row.put("Cal", Cal);
			row.put("remove_num", remove_num);
			row.put("weight", weight);
			row.put("BMI", BMI);
			row.put("movetarget", movetarget);
			row.put("sleeptarget", sleeptarget);
			row.put("height", height);
			row.put("synchronous", syn);
			row.put("step_date", step_date);
			if (!existBl)
				DataAccess.add("BL_DATA_STATISTICS", row, oConn);
			else
				DataAccess.edit("BL_DATA_STATISTICS", "user_id='" + sSn + "'and sport_date='" + sport_date + "'", row, oConn);
		}
	}

	/**
	 * 查询手环数据统计
	 * 
	 * @param sMethod
	 * @throws JException
	 * @throws SQLException
	 */
	public void queryDataStatistics(String sMethod) throws JException, SQLException {
		String sDevice_id = ivo.getString("user_id");
		String sport_date = ivo.getString("sport_date");
		sSql = "select * from BL_DATA_STATISTICS where user_id='" + sDevice_id + "' and sport_date ='" + sport_date + "'";
		oResultSet = DataAccess.query(sSql, oConn);
		DataSet ds = new DataSet();
		while (oResultSet.next()) {
			Row row = new Row();
			row.put("user_id", oResultSet.getString("user_id"));
			row.put("grade", oResultSet.getString("grade"));
			row.put("sport_date", oResultSet.getString("sport_date"));
			row.put("total_step", oResultSet.getString("total_step"));
			row.put("total_date", oResultSet.getString("total_date"));
			row.put("step_date", oResultSet.getString("step_date"));
			row.put("Cal", oResultSet.getString("Cal"));
			row.put("remove_num", oResultSet.getString("remove_num"));
			row.put("weight", oResultSet.getString("weight"));
			row.put("BMI", oResultSet.getString("BMI"));
			row.put("begin_date", oResultSet.getString("begin_date"));
			row.put("end_date", oResultSet.getString("end_date"));
			row.put("type", oResultSet.getString("type"));
			row.put("step_num", oResultSet.getString("step_num"));
			row.put("movetarget", oResultSet.getString("movetarget"));
			row.put("sleeptarget", oResultSet.getString("sleeptarget"));
			row.put("synchronous", oResultSet.getString("synchronous"));
			ds.add(row);
		}
		ovo.set("ds", ds);
	}

	/**
	 * 查询所有统计数据
	 * 
	 * @param sMethod
	 * @throws JException
	 * @throws SQLException
	 */
	public void queryAllDataStatistics(String sMethod) throws JException, SQLException {
		String sDevice_id = ivo.getString("user_id");
		String starttime = ivo.getString("starttime");
		String endtime = ivo.getString("endtime");
		if (starttime == null || starttime.equals("")) {
			sSql = "select * from BL_DATA_STATISTICS where user_id='" + sDevice_id + "'";
		} else {
			Log.e("", "starttime==" + starttime + "   endtime==" + endtime);
			sSql = "select * from BL_DATA_STATISTICS where user_id='" + sDevice_id + "' and sport_date >='" + starttime + "' and sport_date <='"
					+ endtime + "'";
		}
		oResultSet = DataAccess.query(sSql, oConn);
		DataSet ds = new DataSet();
		while (oResultSet.next()) {
			Row row = new Row();
			row.put("user_id", oResultSet.getString("user_id"));
			row.put("grade", oResultSet.getString("grade"));
			row.put("sport_date", oResultSet.getString("sport_date"));
			row.put("total_step", oResultSet.getString("total_step"));
			row.put("total_date", oResultSet.getString("total_date"));
			row.put("step_date", oResultSet.getString("step_date"));
			row.put("Cal", oResultSet.getString("Cal"));
			row.put("remove_num", oResultSet.getString("remove_num"));
			row.put("weight", oResultSet.getString("weight"));
			row.put("BMI", oResultSet.getString("BMI"));
			row.put("step_num", oResultSet.getString("step_num"));
			row.put("synchronous", oResultSet.getString("synchronous"));
			ds.add(row);
		}
		ovo.set("ds", ds);
	}

	/**
	 * 增加修改智能闹钟
	 * 
	 * @param sMethod
	 * @throws JException
	 * @throws SQLException
	 */
	public void addSmartClock(String sMethod) throws JException, SQLException {
		System.out.println("come");
		String sSn = ivo.getString("user_id");
		String sNumber = ivo.getString("number");
		sSql = "select * from BL_Smart_Alarm_Clock where user_id='" + sSn + "' and number = '" + sNumber + "'";
		oResultSet = DataAccess.query(sSql, oConn);
		Row bracelet = new Row();
		boolean existBl = false;
		if (oResultSet.next() && oResultSet.getInt(1) > 0) {// 存在手环数据
			existBl = true;
		} else {
			String sDevId = DataAccess.getSequence("BL_Smart_Alarm_Clock.USER_ID", oConn);
			System.out.println("sDevId=" + sDevId);
			bracelet.put("USER_ID", sDevId);
		}
		String state = ivo.getString("state");
		String alarm_clock_time = ivo.getString("alarm_clock_time");
		String smart_sleep = ivo.getString("smart_sleep");
		String Snooze_time = ivo.getString("Snooze_time");
		String Repetition_period = ivo.getString("Repetition_period");
		bracelet.put("user_id", sSn);
		bracelet.put("state", state);
		bracelet.put("alarm_clock_time", alarm_clock_time);
		bracelet.put("smart_sleep", smart_sleep);
		bracelet.put("Snooze_time", Snooze_time);
		bracelet.put("Repetition_period", Repetition_period);
		bracelet.put("number", sNumber);
		if (!existBl)
			DataAccess.add("BL_Smart_Alarm_Clock", bracelet, oConn);
		else
			DataAccess.edit("BL_Smart_Alarm_Clock", "user_id='" + sSn + "' and number = '" + sNumber + "'", bracelet, oConn);
	}

	/**
	 * 删除智能闹钟
	 * 
	 * @param sMethod
	 * @throws JException
	 * @throws SQLException
	 */
	public void deleteSmartClock(String sMethod) throws JException, SQLException {
		String sUser_id = ivo.getString("user_id");
		String number = ivo.getString("number");
		String sSql = "delete from BL_Smart_Alarm_Clock where number=" + number + " and user_id='" + sUser_id + "'";
		DataAccess.modify(sSql, oConn);
	}

	/**
	 * 删除所有智能闹钟
	 * 
	 * @param sMethod
	 * @throws JException
	 * @throws SQLException
	 */
	public void deleteALLSmartClock(String sMethod) throws JException, SQLException {
		String sUser_id = ivo.getString("user_id", true, "用户编号");
		String sSql = "delete from BL_Smart_Alarm_Clock where  user_id='" + sUser_id + "'";
		DataAccess.modify(sSql, oConn);
	}

	/**
	 * 查询智能闹钟
	 * 
	 * @param sMethod
	 * @throws JException
	 * @throws SQLException
	 */
	public void querySmartClock(String sMethod) throws JException, SQLException {
		String sUser_id = ivo.getString("user_id");
		sSql = "select * from BL_Smart_Alarm_Clock where user_id='" + sUser_id + "'";
		oStatement = DBConn.createStatement(oConn);
		oResultSet = DataAccess.query(sSql, oStatement);
		DataSet ds = new DataSet();
		while (oResultSet.next()) {
			Row row = new Row();
			row.put("user_id", oResultSet.getString("user_id"));
			row.put("state", oResultSet.getString("state"));
			row.put("alarm_clock_time", oResultSet.getString("alarm_clock_time"));
			row.put("smart_sleep", oResultSet.getString("smart_sleep"));
			row.put("Snooze_time", oResultSet.getString("Snooze_time"));
			row.put("Repetition_period", oResultSet.getString("Repetition_period"));
			row.put("number", oResultSet.getString("number"));
			ds.add(row);
		}
		ovo.set("ds", ds);
		DBConn.close(oStatement);
	}

	public void queryClock(String sMethod) throws JException, SQLException {
		String sUser_id = ivo.getString("user_id");
		String sNumber = ivo.getString("number");
		sSql = "select * from BL_Smart_Alarm_Clock where user_id='" + sUser_id + "' and number = '" + sNumber + "'";
		oStatement = DBConn.createStatement(oConn);
		oResultSet = DataAccess.query(sSql, oStatement);
		DataSet ds = new DataSet();
		while (oResultSet.next()) {
			Row row = new Row();
			row.put("user_id", oResultSet.getString("user_id"));
			row.put("state", oResultSet.getString("state"));
			row.put("alarm_clock_time", oResultSet.getString("alarm_clock_time"));
			row.put("smart_sleep", oResultSet.getString("smart_sleep"));
			row.put("Snooze_time", oResultSet.getString("Snooze_time"));
			row.put("Repetition_period", oResultSet.getString("Repetition_period"));
			row.put("number", oResultSet.getString("number"));
			ds.add(row);
		}
		ovo.set("ds", ds);
		DBConn.close(oStatement);
	}

	/**
	 * 增加修改闲置提醒
	 * 
	 * @param sMethod
	 * @throws JException
	 * @throws SQLException
	 */
	public void addIdleRemind(String sMethod) throws JException, SQLException {

		String sUser_id = ivo.getString("user_id");
		sSql = "select count(*) from BL_IDLE_REMIND where user_id='" + sUser_id + "'";
		oResultSet = DataAccess.query(sSql, oConn);
		Row bracelet = new Row();
		boolean existBl = false;
		if (oResultSet.next() && oResultSet.getInt(1) > 0) {// 存在手环数据
			existBl = true;
		} else {
			String sDevId = DataAccess.getSequence("BL_IDLE_REMIND.USER_ID", oConn);
			System.out.println("sDevId=" + sDevId);
			bracelet.put("USER_ID", sDevId);
		}
		String state = ivo.getString("state");
		String no_activity_time = ivo.getString("no_activity_time");
		String start_time = ivo.getString("start_time");
		String end_time = ivo.getString("end_time");
		bracelet.put("user_id", sUser_id);
		bracelet.put("state", state);
		bracelet.put("no_activity_time", no_activity_time);
		bracelet.put("start_time", start_time);
		bracelet.put("end_time", end_time);
		if (!existBl)
			DataAccess.add("BL_IDLE_REMIND", bracelet, oConn);
		else
			DataAccess.edit("BL_IDLE_REMIND", "user_id='" + sUser_id + "'", bracelet, oConn);

	}

	/**
	 * 查询闲置提醒
	 * 
	 * @param sMethod
	 * @throws JException
	 * @throws SQLException
	 */
	public void queryIdleRemind(String sMethod) throws JException, SQLException {
		String sUserId = ivo.getString("user_id");
		sSql = "select * from BL_IDLE_REMIND where user_id='" + sUserId + "'";
		oStatement = DBConn.createStatement(oConn);
		oResultSet = DataAccess.query(sSql, oStatement);
		DataSet ds = new DataSet();
		while (oResultSet.next()) {
			Row row = new Row();
			row.put("user_id", sUserId);
			row.put("state", oResultSet.getString("state"));
			row.put("no_activity_time", oResultSet.getString("no_activity_time"));
			row.put("start_time", oResultSet.getString("start_time"));
			row.put("end_time", oResultSet.getString("end_time"));
			ds.add(row);
		}
		ovo.set("ds", ds);
		DBConn.close(oStatement);

	}

	/**
	 * 增加修改小睡
	 * 
	 * @param sMethod
	 * @throws JException
	 * @throws SQLException
	 */
	public void addNapClock(String sMethod) throws JException, SQLException {

		String sUser_id = ivo.getString("user_id");
		sSql = "select count(*) from bl_nap_remind where user_id='" + sUser_id + "'";
		oResultSet = DataAccess.query(sSql, oConn);
		Row bracelet = new Row();
		boolean existBl = false;
		if (oResultSet.next() && oResultSet.getInt(1) > 0) {// 存在手环数据
			existBl = true;
		} else {
			String sDevId = DataAccess.getSequence("bl_nap_remind.USER_ID", oConn);
			System.out.println("sDevId=" + sDevId);
			bracelet.put("USER_ID", sDevId);
		}
		String state = ivo.getString("state");
		String nap_time = ivo.getString("nap_time");
		String long_nap_time = ivo.getString("long_nap_time");
		bracelet.put("user_id", sUser_id);
		bracelet.put("state", state);
		bracelet.put("nap_time", nap_time);
		bracelet.put("long_nap_time", long_nap_time);
		if (!existBl)
			DataAccess.add("bl_nap_remind", bracelet, oConn);
		else
			DataAccess.edit("bl_nap_remind", "user_id='" + sUser_id + "'", bracelet, oConn);

	}

	/**
	 * 查询小睡
	 * 
	 * @param sMethod
	 * @throws JException
	 * @throws SQLException
	 */
	public void queryNapClock(String sMethod) throws JException, SQLException {
		String sUserId = ivo.getString("user_id");
		sSql = "select * from bl_nap_remind where user_id='" + sUserId + "'";
		oResultSet = DataAccess.query(sSql, oConn);
		DataSet ds = new DataSet();
		while (oResultSet.next()) {
			Row row = new Row();
			row.put("user_id", sUserId);
			row.put("state", oResultSet.getString("state"));
			row.put("nap_time", oResultSet.getString("nap_time"));
			row.put("long_nap_time", oResultSet.getString("long_nap_time"));
			ds.add(row);
		}
		ovo.set("ds", ds);
	}

	/**
	 * 增加修改来电提醒
	 * 
	 * @param sMethod
	 * @throws JException
	 * @throws SQLException
	 */
	public void addCallReminder(String sMethod) throws JException, SQLException {

		String sUser_id = ivo.getString("user_id");
		sSql = "select count(*) from bl_Phone_calls_reminding where user_id='" + sUser_id + "'";
		oResultSet = DataAccess.query(sSql, oConn);
		Row bracelet = new Row();
		boolean existBl = false;
		if (oResultSet.next() && oResultSet.getInt(1) > 0) {// 存在手环数据
			existBl = true;
		} else {
			String sDevId = DataAccess.getSequence("bl_Phone_calls_reminding.USER_ID", oConn);
			System.out.println("sDevId=" + sDevId);
			bracelet.put("USER_ID", sDevId);
		}
		String state = ivo.getString("state");
		String star_time = ivo.getString("star_time");
		String end_time = ivo.getString("end_time");
		bracelet.put("user_id", sUser_id);
		bracelet.put("state", state);
		bracelet.put("star_time", star_time);
		bracelet.put("end_time", end_time);
		if (!existBl)
			DataAccess.add("bl_Phone_calls_reminding", bracelet, oConn);
		else
			DataAccess.edit("bl_Phone_calls_reminding", "user_id='" + sUser_id + "'", bracelet, oConn);
	}

	/**
	 * 增加修改来电提醒
	 * 
	 * @param sMethod
	 * @throws JException
	 * @throws SQLException
	 */
	public void queryCallReminder(String sMethod) throws JException, SQLException {
		String sUser_id = ivo.getString("user_id");
		sSql = "select * from bl_Phone_calls_reminding where user_id='" + sUser_id + "'";
		oResultSet = DataAccess.query(sSql, oConn);
		DataSet ds = new DataSet();
		while (oResultSet.next()) {
			Row row = new Row();
			row.put("user_id", oResultSet.getString("user_id"));
			row.put("state", oResultSet.getString("state"));
			row.put("star_time", oResultSet.getString("star_time"));
			row.put("end_time", oResultSet.getString("end_time"));
			ds.add(row);
		}
		ovo.set("ds", ds);
	}

	/**
	 * 增加修改隐私设置
	 * 
	 * @param sMethod
	 * @throws JException
	 * @throws SQLException
	 */
	public void addPrivacy(String sMethod) throws JException, SQLException {

		String sUser_id = ivo.getString("user_id");
		sSql = "select count(*) from bl_privacy where user_id='" + sUser_id + "'";
		oResultSet = DataAccess.query(sSql, oConn);
		Row bracelet = new Row();
		boolean existBl = false;
		if (oResultSet.next() && oResultSet.getInt(1) > 0) {// 存在手环数据
			existBl = true;
		} else {
			String sDevId = DataAccess.getSequence("bl_privacy.USER_ID", oConn);
			System.out.println("sDevId=" + sDevId);
			bracelet.put("USER_ID", sDevId);
		}
		String share_nep = ivo.getString("share_nep");
		String share_step = ivo.getString("share_step");
		String share_records = ivo.getString("share_records");
		bracelet.put("user_id", sUser_id);
		bracelet.put("share_nep", share_nep);
		bracelet.put("share_step", share_step);
		bracelet.put("share_records", share_records);
		if (!existBl)
			DataAccess.add("bl_privacy", bracelet, oConn);
		else
			DataAccess.edit("bl_privacy", "user_id='" + sUser_id + "'", bracelet, oConn);
	}

	/**
	 * 查询隐私设置
	 * 
	 * @param sMethod
	 * @throws JException
	 * @throws SQLException
	 */
	public void queryPrivacy(String sMethod) throws JException, SQLException {
		String sUser_id = ivo.getString("user_id");
		sSql = "select * from bl_privacy where user_id='" + sUser_id + "'";
		oResultSet = DataAccess.query(sSql, oConn);
		DataSet ds = new DataSet();
		while (oResultSet.next()) {
			Row row = new Row();
			row.put("user_id", oResultSet.getString("user_id"));
			row.put("share_nep", oResultSet.getString("share_nep"));
			row.put("share_step", oResultSet.getString("share_step"));
			row.put("share_records", oResultSet.getString("share_records"));
			ds.add(row);
		}
		ovo.set("ds", ds);
	}

	/**
	 * 增加修改体重输入提醒
	 * 
	 * @param sMethod
	 * @throws JException
	 * @throws SQLException
	 */
	public void addInputWeight(String sMethod) throws JException, SQLException {

		String sUser_id = ivo.getString("user_id");
		sSql = "select count(*) from BL_WEIGHT_INVOKE where user_id='" + sUser_id + "'";
		oResultSet = DataAccess.query(sSql, oConn);
		Row bracelet = new Row();
		boolean existBl = false;
		if (oResultSet.next() && oResultSet.getInt(1) > 0) {// 存在手环数据
			existBl = true;
		} else {
			String sDevId = DataAccess.getSequence("BL_WEIGHT_INVOKE.USER_ID", oConn);
			System.out.println("sDevId=" + sDevId);
			bracelet.put("USER_ID", sDevId);
		}
		String state = ivo.getString("state");
		String INPUT_RATE = ivo.getString("INPUT_RATE");
		String input_time = ivo.getString("input_time");
		bracelet.put("user_id", sUser_id);
		bracelet.put("state", state);
		bracelet.put("INPUT_RATE", INPUT_RATE);
		bracelet.put("input_time", input_time);
		if (!existBl)
			DataAccess.add("BL_WEIGHT_INVOKE", bracelet, oConn);
		else
			DataAccess.edit("BL_WEIGHT_INVOKE	", "user_id='" + sUser_id + "'", bracelet, oConn);
	}

	/**
	 * 查询体重输入提醒
	 * 
	 * @param sMethod
	 * @throws JException
	 * @throws SQLException
	 */
	public void queryInputWeight(String sMethod) throws JException, SQLException {
		String sUser_id = ivo.getString("user_id");
		sSql = "select * from BL_WEIGHT_INVOKE where user_id='" + sUser_id + "'";
		oResultSet = DataAccess.query(sSql, oConn);
		DataSet ds = new DataSet();
		while (oResultSet.next()) {
			Row row = new Row();
			row.put("user_id", oResultSet.getString("user_id"));
			row.put("state", oResultSet.getString("state"));
			row.put("INPUT_RATE", oResultSet.getString("INPUT_RATE"));
			row.put("input_time", oResultSet.getString("input_time"));
			ds.add(row);
		}
		ovo.set("ds", ds);
	}

	/**
	 * 查询活动状态
	 * 
	 * @param sMethod
	 * @throws JException
	 * @throws SQLException
	 */
	public void queryActivityType2(String sMethod) throws JException, SQLException {
		String sUser_id = ivo.getString("user_id");
		String sDate = ivo.getString("date");
		String stime = ivo.getString("time");
		sSql = "select * from BL_ACTIVITY_TYPE where user_id='" + sUser_id + "' and date = '" + sDate + "' and time='" + stime + "'";
		oResultSet = DataAccess.query(sSql, oConn);
		DataSet ds = new DataSet();
		while (oResultSet.next()) {
			Row row = new Row();
			row.put("user_id", oResultSet.getString("user_id"));
			row.put("date", oResultSet.getString("date"));
			row.put("state", oResultSet.getString("state"));
			row.put("time", oResultSet.getString("time"));
			row.put("type", oResultSet.getString("type"));
			row.put("intensity", oResultSet.getString("intensity"));
			row.put("alarm", oResultSet.getString("alarm"));
			ds.add(row);
		}
		ovo.set("ds", ds);
	}

	/**
	 * 查询活动状态
	 * 
	 * @param sMethod
	 * @throws JException
	 * @throws SQLException
	 */
	public void queryActivityType(String sMethod) throws JException, SQLException {
		String sUser_id = ivo.getString("user_id");
		String sDate = ivo.getString("date");
		String sUpdata = ivo.getString("updata");
		if (!sDate.equals("") && sDate != null) {
			sSql = "select * from BL_ACTIVITY_TYPE where user_id='" + sUser_id + "' and date = '" + sDate + "'";
		} else {
			Log.e("2222222222", "sUser_id===>>" + sUser_id + "  sDate==>> " + sDate + "  sUpdata===>" + sUpdata);
			sSql = "select * from BL_ACTIVITY_TYPE where user_id='" + sUser_id + "'";
		}
		if (sUpdata.equals("yes")) {
			sSql = "select * from BL_ACTIVITY_TYPE where user_id='" + sUser_id + "' and date >= " + sDate + "";
		}
		oResultSet = DataAccess.query(sSql, oConn);
		DataSet ds = new DataSet();
		while (oResultSet.next()) {
			Row row = new Row();
			row.put("user_id", oResultSet.getString("user_id"));
			row.put("date", oResultSet.getString("date"));
			row.put("state", oResultSet.getString("state"));
			row.put("time", oResultSet.getString("time"));
			row.put("type", oResultSet.getString("type"));
			row.put("intensity", oResultSet.getString("intensity"));
			row.put("alarm", oResultSet.getString("alarm"));
			ds.add(row);
		}
		ovo.set("ds", ds);
	}

	/**
	 * 查询前一天的活动状态
	 * 
	 * @param sMethod
	 * @throws JException
	 * @throws SQLException
	 */
	public void queryBeforeActivityType(String sMethod) throws JException, SQLException {
		String sUser_id = ivo.getString("user_id");
		String sDate = ivo.getString("date");
		sSql = "select * from BL_ACTIVITY_TYPE where user_id='" + sUser_id + "' and date = '" + sDate + "'";
		oResultSet = DataAccess.query(sSql, oConn);
		DataSet ds = new DataSet();
		while (oResultSet.next()) {
			Row row = new Row();
			row.put("user_id", oResultSet.getString("user_id"));
			row.put("date", oResultSet.getString("date"));
			row.put("state", oResultSet.getString("state"));
			row.put("time", oResultSet.getString("time"));
			row.put("type", oResultSet.getString("type"));
			row.put("intensity", oResultSet.getString("intensity"));
			ds.add(row);
		}
		ovo.set("ds", ds);
	}

	/**
	 * 查询所有活动状态
	 * 
	 * @param sMethod
	 * @throws JException
	 * @throws SQLException
	 */
	public void queryAllActivityType(String sMethod) throws JException, SQLException {
		String sUser_id = ivo.getString("user_id");
		sSql = "select * from BL_ACTIVITY_TYPE where user_id='" + sUser_id + "'";
		oResultSet = DataAccess.query(sSql, oConn);
		DataSet ds = new DataSet();
		while (oResultSet.next()) {
			Row row = new Row();
			row.put("user_id", oResultSet.getString("user_id"));
			row.put("date", oResultSet.getString("date"));
			row.put("state", oResultSet.getString("state"));
			row.put("time", oResultSet.getString("time"));
			row.put("type", oResultSet.getString("type"));
			row.put("intensity", oResultSet.getString("intensity"));
			ds.add(row);
		}
		ovo.set("ds", ds);
	}

	/**
	 * 添加活动状态
	 */
	public void addActivityType(String sMethod) throws JException, SQLException {
		String sUser_id = ivo.getString("user_id");
		String time = ivo.getString("time");
		String del = ivo.getString("del");
		String date = ivo.getString("date");
		if (del.equals("del")) {
			sSql = "delete from BL_ACTIVITY_TYPE where user_id= " + sUser_id + " and date='" + date + "'";
			DataAccess.modify(sSql, oConn);
		} else {
			sSql = "select count(*) from BL_ACTIVITY_TYPE where user_id='" + sUser_id + "'and time='" + time + "'";
			oResultSet = DataAccess.query(sSql, oConn);
			Row bracelet = new Row();
			boolean existBl = false;
			if (oResultSet.next() && oResultSet.getInt(1) > 0) {// 存在手环数据
				existBl = true;
			} else {
				String sDevId = DataAccess.getSequence("BL_ACTIVITY_TYPE.USER_ID", oConn);
				System.out.println("sDevId=" + sDevId);
				bracelet.put("USER_ID", sDevId);
			}
			String sDate = ivo.getString("date");
			String state = ivo.getString("state");

			bracelet.put("user_id", sUser_id);
			bracelet.put("state", state);
			bracelet.put("date", sDate);
			bracelet.put("time", time);
			if (!existBl)
				DataAccess.add("BL_ACTIVITY_TYPE", bracelet, oConn);
			else
				DataAccess.edit("BL_ACTIVITY_TYPE	", "user_id='" + sUser_id + "'and time='" + time + "'", bracelet, oConn);
		}
	}

	/**
	 * 添加活动状态
	 */
	public void addActivityType2(String sMethod) throws JException, SQLException {
		String sUser_id = ivo.getString("user_id");
		String sDate = ivo.getString("date");
		String time = ivo.getString("time");
		sSql = "select count(*) from BL_ACTIVITY_TYPE where user_id='" + sUser_id + "'and date='" + sDate + "' and time='" + time + "'";
		oResultSet = DataAccess.query(sSql, oConn);
		Row row = new Row();
		boolean existBl = false;
		if (oResultSet.next() && oResultSet.getInt(1) > 0) {// 存在手环数据
			existBl = true;
		} else {
			String sDevId = DataAccess.getSequence("BL_ACTIVITY_TYPE.USER_ID", oConn);
			System.out.println("sDevId=" + sDevId);
			row.put("USER_ID", sDevId);
		}
		String state = ivo.getString("state");
		String intensity = ivo.getString("intensity");
		String type = ivo.getString("type");
		String alarm = ivo.getString("alarm");

		row.put("user_id", sUser_id);
		row.put("state", state);
		row.put("date", sDate);
		row.put("time", time);
		row.put("intensity", intensity);
		row.put("type", type);
		row.put("alarm", alarm);
		if (!existBl)
			DataAccess.add("BL_ACTIVITY_TYPE", row, oConn);
		else
			DataAccess.edit("BL_ACTIVITY_TYPE", "user_id='" + sUser_id + "'and date='" + sDate + "'and time='" + time + "'", row, oConn);
	}

	/**
	 * 查询记录
	 * 
	 * @param sMethod
	 * @throws JException
	 * @throws SQLException
	 */
	public void queryRecord(String sMethod) throws JException, SQLException {
		String sUser_id = ivo.getString("user_id");
		sSql = "select * from BL_Record where user_id='" + sUser_id + "'";
		oResultSet = DataAccess.query(sSql, oConn);
		DataSet ds = new DataSet();
		while (oResultSet.next()) {
			Row row = new Row();
			row.put("user_id", oResultSet.getString("user_id"));
			row.put("recordName", oResultSet.getString("recordName"));
			row.put("number", oResultSet.getString("number"));
			row.put("time", oResultSet.getString("time"));
			ds.add(row);
		}
		ovo.set("ds", ds);
	}

	/**
	 * 添加记录
	 */
	public void addRecord(String sMethod) throws JException, SQLException {
		String sUser_id = ivo.getString("user_id");
		String recordName = ivo.getString("recordName");
		sSql = "select count(*) from BL_Record where user_id='" + sUser_id + "'and recordName='" + recordName + "'";
		oResultSet = DataAccess.query(sSql, oConn);
		Row row = new Row();
		boolean existBl = false;
		if (oResultSet.next() && oResultSet.getInt(1) > 0) {// 存在手环数据
			existBl = true;
		}
		// } else {
		// String sDevId = DataAccess.getSequence("BL_Record.USER_ID", oConn);
		// System.out.println("sDevId=" + sDevId);
		// // row.put("USER_ID", sDevId);
		// }
		String number = ivo.getString("number");
		String time = ivo.getString("time");

		row.put("user_id", sUser_id);
		row.put("recordName", recordName);
		row.put("number", number);
		row.put("time", time);
		if (!existBl)
			DataAccess.add("BL_Record", row, oConn);
		else
			DataAccess.edit("BL_Record", "user_id='" + sUser_id + "'and recordName='" + recordName + "'", row, oConn);
	}

}
