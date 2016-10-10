package com.ysp.newband;

public class BluetoothInstruction {
	/**
	 * 设备类型和ID
	 */
	public static String DEVICE = "DEVICE";
	/**
	 * 文件名列表和数量
	 */
	public static String FILEN = "FILEN";//
	/**
	 * 文件内容（步数）
	 */
	public static String FILE = "FILE";// 文件内容（步数）
	/**
	 * 漏传文件或者错误文件内容的处理（步数）
	 */
	public static String FILEM = "FILEM";// 漏传文件或者错误文件内容的处理（步数）
	/**
	 * 睡眠开始结束时间 +文件名
	 */
	public static String SLEEPFILE = "SLEEP";// 睡眠开始结束时间
	/**
	 * 手机发送当前时间;‘TIME’+年月日时分秒（用16进制表示）
	 */
	public static String TIME = "TIME";// 手机发送当前时间;‘TIME’+年月日时分秒（用16进制表示）
	/**
	 * 手机发送当前时间;‘RTIME’+年月日时分秒（用16进制表示）
	 */
	public static String RTIME = "RTIME";// 手机发送当前时间;‘TIME’+年月日时分秒（用16进制表示）
	
	/**
	 * ‘ALARM’+时分（用16进制表示）+WEEK(七天的标志位)打开传E，关闭用D
	 */
	
	public static String ALARM = "ALARM";// ‘ALARM’+时分（用16进制表示）+WEEK(七天的标志位)打开传E，关闭用D
	/**
	 * ‘PHONE’+Number备注:有电话进来;‘PHONE’+E/D 备注:来电是否需要震动
	 */
	public static String PHONE = "PHONE";// ‘PHONE’+Number备注:有电话进来;
	// public static String ‘PHONE’+E/D 备注:来电是否需要震动
	/**
	 * ‘TEST’备注:要求设备进入测试状态
	 */
	public static String TEST = "TEST";// ‘TEST’备注:要求设备进入测试状态
	/**
	 * ‘RESET’备注:要求设备进入初始化
	 */
	public static String RESET = "RESET";// ‘RESET’备注:要求设备进入初始化
	/**
	 * ‘POWER’备注:要求设备进入关闭状态
	 */
	public static String POWER = "POWER";// ‘POWER’备注:要求设备进入关闭状态
}
