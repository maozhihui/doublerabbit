package com.comba.transport.pshare.message;

import java.io.Serializable;

import org.apache.mina.core.buffer.IoBuffer;

import com.comba.transport.pshare.common.ByteUtil;

import lombok.Data;

@Data
public abstract class PSHAREAbstractMessage implements Serializable {

	//包头
	protected byte head;
	//地址
	protected byte addr;
	//长度
	protected byte len;
	//功能码
	protected byte cmd;
	//CRC校验 2bytes
	protected byte crc8;
	//包尾
	protected byte tail;
	
	public PSHAREAbstractMessage() {

	}
	
	public abstract void encodeMsg(IoBuffer bt);
	
	public abstract void decodeMsg(IoBuffer buf);
}
