package com.comba.transport.pm25.common;

public class Constant {
	
	//通用查询地址
	final public static byte IN_COMMON_USE_QUERY_ADDR = (byte) 0xFA;
	
	//MAC长度
	final public static int MAC_LEN = 6;
	//功能码
	//读取数据
	final public static byte READ_DATA_REQ = (byte) 0x03;
	//读取数据 回应（与读取数据是一样的）
	final public static byte READ_DATA_RESP = (byte) 0x03;
	//更改设备地址
	final public static byte CHANGE_DEVICE_ADDR_REQ = (byte) 0x06;
	//更改设备地址 回应
	final public static byte CHANGE_DEVICE_ADDR_RESP = (byte) 0x25;
	//查询设备地址
	final public static byte QUERY_DEVICE_ADDR_REQ = (byte) 0x03;
	//查询设备地址
	final public static byte QUERY_DEVICE_ADDR_RESP = (byte) 0x03;

}
