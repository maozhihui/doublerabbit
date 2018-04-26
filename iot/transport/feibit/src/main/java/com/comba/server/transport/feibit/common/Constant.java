package com.comba.server.transport.feibit.common;

public class Constant {
	//控制标志
	final public static byte CONTROL_FLAG = (byte) 0xFE;
	
	//获取当前连接的所有设备
	final public static byte GET_DEVICES_REQ = (byte) 0x81;
	//获取当前连接的所有设备 响应
	final public static byte GET_DEVICES_RESP = (byte) 0x01;
	//设置设备开关状态
	final public static byte SET_DEVICE_SWITCH_STATE_REQ = (byte) 0x82;
	//设备状态响应:删除设备，更改名称，设置开关，设置亮度，设置颜色，绑定设备，取消绑定，调用场景，设置报告时间，设置色温，查询红外，
	final public static byte DEVICE_STATE_RESP = (byte) 0x29;
	//获取设备开关状态
	final public static byte DEVICE_SWITCH_STATE_REQ = (byte) 0x85;
	//获取设备开关状态 响应
	final public static byte DEVICE_SWITCH_STATE_RESP = (byte) 0x07;
	//节点主动上报：该信息由节点主动上报
	final public static byte NODE_ACTIVE_REPORTING = (byte) 0x70;
	//获取网关信息：(无参数)
	final public static byte GET_GATEWAY_INFO_REQ = (byte) 0x9D;
	//获取网关信息 响应
	final public static byte GET_GATEWAY_INFO_RESP = (byte) 0x15;
	//指令标识(DEVICE_STATE_RESP的子标识)
	final public static byte COMMAND_FLAG = (byte) 0x04;
	//0xA7 红外转发及学习操作(红外查询05，红外发送06)(DEVICE_STATE_RESP的子标识)
	final public static byte INFRARED_RETRANSMISSION_AND_LEARN = (byte) 0xA7;
	//查询网关内保存的红外数据
	final public static byte INFRARED_DATA_READ = (byte) 0x05;
	//发送网关内保存的红外数据
	final public static byte INFRARED_DATA_SEND = (byte) 0x06;
	//0x9E 设置报告间隔时间
	final public static byte SET_REPORT_INTERVAL_TIME = (byte) 0x9E;
	
	//传感器类型
	//门磁
	final public static short DOOR_LOCK_SENSOR = (short) 0x0015;
	//人体红外
	final public static short HUMAN_INFRARED_SENSOR = (short) 0x000d;
	//烟雾
	final public static short SMOKE_SENSOR = (short) 0x0028;
	//气体
	final public static short GAS_SENSOR = (short) 0x002b;
	//一氧化碳
	final public static short CARBON_MONOXIDE_SENSOR = (short) 0x8001;
	//震动
	final public static short SHOCK_SENSOR = (short) 0x002d;
	//水浸
	final public static short WATER_IMMERSION_SENSOR = (short) 0x002a;
	//安防遥控
	final public static short SECURITY_REMOTE_CONTROL_SENSOR = (short) 0x0115;
	//紧急按钮
	final public static short SOS_BUTTON_SENSOR = (short) 0x002c;
	//报警器
	final public static short ALARM_SENSOR = (short) 0x0225;
	
	//传感器
	final public static short SENSOR = (short) 0x0402;
	//温湿度
	final public static short THERMO_HYGROMETER = (short) 0x0302;
	//窗帘
	final public static short CURTAIN = (short) 0x0202;
	//红外控制面板
	final public static short IR_CONTROL = (short) 0x0161;
	//光照illumination
	final public static short ILLUMINATION = (short) 0x0106;
	//电源开关
	final public static short POWER_SWITCH = (short) 0x0002;
	//情景开关
	final public static short SCENE_SWITCH = (short) 0x0004;
	//未知
	final public static short UNKNOWN = (short) 0x0000;
	
	//当 ProfileID 为 0xc05e 时，表明这是一个 ZLL Device
	//当 ProfileID 为 0x0104 时，表明这是一个 HA 设备
	final public static short ZLL_DEVICE = (short) 0xc05e;
	final public static short HA_DEVICE = (short) 0x0104;
	//数据类型
	final public static byte UINT_8 = (byte)0x20;	//1 bit
	final public static byte UINT_16 = (byte)0x21;	//2 bit
	final public static byte UINT_24 = (byte)0x22;	//3 bit
	final public static byte UINT_32 = (byte)0x23;	//4 bit
	final public static byte INT_8 = (byte)0x28;	//1 bit
	final public static byte INT_16 = (byte)0x29;	//2 bit
	final public static byte INT_24 = (byte)0x2a;	//3 bit
	final public static byte INT_32 = (byte)0x2b;	//4 bit
	//define connecting status
	final public static int STATUS_CONNECT_FAILURE = -1;
	final public static int STATUS_NOT_CONNECTED = 0;
	final public static int STATUS_CONNECTING = 1;
	final public static int STATUS_CONNECTED = 2;
	final public static int STATUS_CLOSED = 3;
	
}
