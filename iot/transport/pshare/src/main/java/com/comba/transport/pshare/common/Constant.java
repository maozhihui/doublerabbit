package com.comba.transport.pshare.common;

public class Constant {
	
	//命令的包头
	final public static byte PACK_HEAD = (byte) 0x55;
	//响应成功的包头
	final public static byte PACK_HEAD_SUCCESS = (byte) 0x5A;
	//响应失败的包头
	final public static byte PACK_HEAD_ERROR = (byte) 0x5B;
	//命令的包尾
	final public static byte PACK_TAIL = (byte) 0xAA;
	
	//查询锁状态
	final public static byte GET_LOCK_STATE = (byte) 0x21;
	//降锁
	final public static byte LOCK_DOWN = (byte) 0x25;
	//升锁
	final public static byte LOCK_UP = (byte) 0x26;

	//MAC长度
	final public static int MAC_LEN = 6;
}
