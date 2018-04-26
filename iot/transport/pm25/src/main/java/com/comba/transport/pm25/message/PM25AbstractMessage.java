package com.comba.transport.pm25.message;

import java.io.Serializable;

import org.apache.mina.core.buffer.IoBuffer;

import com.comba.transport.pm25.common.ByteUtil;

import lombok.Data;

@Data
public abstract class PM25AbstractMessage implements Serializable {

	//设备地址
	protected byte deviceAddr;
	//功能码
	protected byte funCode;
	//CRC校验 2bytes
	protected byte[] crc16;
	
	public PM25AbstractMessage() {
		crc16 = new byte[2];
	}
	
	public abstract void encodeMsg(IoBuffer bt);
	
	public abstract void decodeMsg(IoBuffer buf);
}
