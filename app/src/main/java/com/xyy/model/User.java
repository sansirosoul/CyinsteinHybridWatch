package com.xyy.model;

import java.util.ArrayList;

import android.net.Uri;

public class User {
	private String name;
	private String nickname;
	private String braceletName;
	private int sex;// 0表示男，1表示女；
	private String birthday;
	private String height;
	private String heightUnit;// 身高单位
	private String weight;
	private String weightUnit;// 体重单位
	private int Calorie;
	private String SN;
	private String user_account;
	private String password;
	private String FWVERSION;
	private String user_id;

	private String activityType;// 活动类型
	private String activityintensity;// 活動強度

	private int step;// 步数
	private int Runtime;// 跑步时间

	private String imgPath;
	private Uri imgUri;

	private String ID;// 序号
	private String UUID;
	private int battery;

	private int IDLE_REMIND = 0;// 闲置提醒： 0表示关闭，1表示开启；
	private int INPUT_WEIGHT = 0;// 体重输入提醒：0表示关闭，1表示开启
	private int NAP = 0;// 小睡：0表示关闭，1表示开启；
	private int callReminder;// 来电提醒：0表示关闭，1表示开启

	// 步数文件列表
	ArrayList<byte[]> FILElist;
	// 文件总和列表
	ArrayList<byte[]> FILENList;
	// 睡眠时间列表
	ArrayList<byte[]> SLEEPList;

	public String getActivityintensity() {
		return activityintensity;
	}

	public void setActivityintensity(String activityintensity) {
		this.activityintensity = activityintensity;
	}

	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	public String getUUID() {
		return UUID;
	}

	public void setUUID(String uUID) {
		UUID = uUID;
	}

	public ArrayList<byte[]> getFILElist() {
		return FILElist;
	}

	public int getBattery() {
		return battery;
	}

	public void setBattery(int battery) {
		this.battery = battery;
	}

	public void setFILElist(ArrayList<byte[]> fILElist) {
		FILElist = fILElist;
	}

	public ArrayList<byte[]> getFILENList() {
		return FILENList;
	}

	public void setFILENList(ArrayList<byte[]> fILENList) {
		FILENList = fILENList;
	}

	public ArrayList<byte[]> getSLEEPList() {
		return SLEEPList;
	}

	public void setSLEEPList(ArrayList<byte[]> sLEEPList) {
		SLEEPList = sLEEPList;
	}

	public Uri getImgUri() {
		return imgUri;
	}

	public void setImgUri(Uri imgUri) {
		this.imgUri = imgUri;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String img) {
		this.imgPath = img;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public int getCallReminder() {
		return callReminder;
	}

	public void setCallReminder(int callReminder) {
		this.callReminder = callReminder;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getBraceletName() {
		return braceletName;
	}

	public void setBraceletName(String braceletName) {
		this.braceletName = braceletName;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getHeightUnit() {
		return heightUnit;
	}

	public void setHeightUnit(String heightUnit) {
		this.heightUnit = heightUnit;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getWeightUnit() {
		return weightUnit;
	}

	public void setWeightUnit(String weightUnit) {
		this.weightUnit = weightUnit;
	}

	public int getIDLE_REMIND() {
		return IDLE_REMIND;
	}

	public void setIDLE_REMIND(int iDLE_REMIND) {
		IDLE_REMIND = iDLE_REMIND;
	}

	public int getINPUT_WEIGHT() {
		return INPUT_WEIGHT;
	}

	public void setINPUT_WEIGHT(int iNPUT_WEIGHT) {
		INPUT_WEIGHT = iNPUT_WEIGHT;
	}

	public int getNAP() {
		return NAP;
	}

	public void setNAP(int nAP) {
		NAP = nAP;
	}

	public int getCalorie() {
		return Calorie;
	}

	public void setCalorie(int calorie) {
		Calorie = calorie;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public int getRuntime() {
		return Runtime;
	}

	public void setRuntime(int runtime) {
		Runtime = runtime;
	}

	public String getSN() {
		return SN;
	}

	public void setSN(String sN) {
		SN = sN;
	}

	public String getUser_account() {
		return user_account;
	}

	public void setUser_account(String user_account) {
		this.user_account = user_account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFWVERSION() {
		return FWVERSION;
	}

	public void setFWVERSION(String fWVERSION) {
		FWVERSION = fWVERSION;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

}
