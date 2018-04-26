package com.comba.server.transport.kerui.common;

public class Constant {
	//包头	0x7F
	final public static byte PACKET_HEADER = (byte) 0x7F;
	//版本号	起始版本号为0x00
	final public static byte VERSION = (byte) 0x00;
	//PC机地址为0x0，
	final public static byte PC_ADDR = (byte) 0x00;
	//广播地址为0xFF
	final public static byte BROADCAST_ADDR = (byte) 0xFF;
	
	//命令码:
	//空回复
	final public static byte NULL_REPLY = (byte) 0x00;
	//获取主控板数量
	final public static byte MASTER_NUMBER_REQ = (byte) 0x01;
	//回复主控板数量
	final public static byte MASTER_NUMBER_RESP = (byte) 0x81;
	//获取目标主控器下的设备分布
	final public static byte DEST_MASTER_DEVICES_REQ = (byte) 0x02;
	//回复目标主控器下的设备是否存在
	final public static byte DEST_MASTER_DEVICES_RESP = (byte) 0x82;
	//获取目标主控器下的设备类型
	final public static byte DEST_MASTER_DEVICES_TYPE_REQ = (byte) 0x03;
	//回复目标主控器下的设备类型
	final public static byte DEST_MASTER_DEVICES_TYPE_RESP = (byte) 0x83;
	//获取目标主控器所有探测器状态
	final public static byte DEST_MASTER_DETECTORS_STATE_REQ = (byte) 0x04;
	//回复目标主控器所有探测器状态
	final public static byte DEST_MASTER_DETECTORS_STATE_RESP = (byte) 0x84;
	//获取某一个探测器的状态
	final public static byte DETECTOR_STATE_REQ = (byte) 0x05;
	//回复某一个探测器的状态
	final public static byte DETECTOR_STATE_RESP = (byte) 0x85;
	//设置一个探测器的显示方式
	final public static byte SET_DETECTOR_DISPLAY_MODEL_REQ = (byte) 0x06;
	//回应设置一个探测器的显示方式
	final public static byte SET_DETECTOR_DISPLAY_MODEL_RESP = (byte) 0x86;
	//设置时间
	final public static byte SET_TIME_REQ = (byte) 0x07;
	//回应设置时间
	final public static byte SET_TIME_RESP = (byte) 0x87;
	//设置探测板参数
	final public static byte SET_DETECTOR_BOARD_PARAMETER_REQ = (byte) 0x08;
	//回应设置探测板参数
	final public static byte SET_DETECTOR_BOARD_PARAMETER_RESP = (byte) 0x88;
	//设置绑定屏与探测器
	final public static byte BINDING_SCREEN_AND_DETECTOR_REQ = (byte) 0x09;
	//回复绑定
	final public static byte BINDING_SCREEN_AND_DETECTOR_RESP = (byte) 0x89;
	//设置探测器参数
	final public static byte SET_DETECTOR_PARAMETER_REQ = (byte) 0x0A;
	//回应设置探测器参数
	final public static byte SET_DETECTOR_PARAMETER_RESP = (byte) 0x8A;
	//获取CPU ID
	final public static byte CPU_ID_REQ = (byte) 0x0B;
	//响应获取CPU ID
	final public static byte CPU_ID_RESP = (byte) 0x8B;
	
	//设备是否存在 无设备
	final public static byte NOT_DEVICE = (byte) 0x00;
	//设备是否存在 有设备
	final public static byte HAS_DEVICE = (byte) 0x01;
	
	//设备类型 探测器
	final public static byte DETECTOR = (byte) 0x00;
	//设备类型 屏
	final public static byte SCREEN = (byte) 0x01;
	//设备类型 无线灯
	final public static byte WIRELESS_LAMP = (byte) 0x10;
	//设备类型 保留
	final public static byte RESERVE = (byte) 0x11;
	
	//探测器状态 无车
	final public static byte NOT_CAR_STATE = (byte) 0x00;
	//探测器状态 有车
	final public static byte HAS_CAR_STATE = (byte) 0x01;
	//探测器状态 错误
	final public static byte ERROR_STATE = (byte) 0x02;
	
	//探测器显示方式 正常显示车位状态
	final public static byte NORMAL_MODEL = (byte) 0x00;
	//探测器显示方式 VIP状态
	final public static byte VIP_MODEL = (byte) 0x01;
	//探测器显示方式 测试状态
	final public static byte TEST_MODEL = (byte) 0x02;
	//探测器显示方式 预约状态
	final public static byte SUBSCRIBE_MODEL = (byte) 0x03;
	
	//绑定屏与探测器 不绑定
	final public static byte NOT_BIND = (byte) 0x00;
	//绑定屏与探测器 不绑定
	final public static byte HAS_BIND = (byte) 0x01;
	
	//绑定屏与探测器 绑定成功
	final public static byte BIND_SUCCESS = (byte) 0x00;
}
