package com.comba.transport.daikin.message;

import java.io.Serializable;

import lombok.Data;

import org.apache.mina.core.buffer.IoBuffer;

@Data
public abstract class ModbusAbstractMessage implements Serializable {
	//从机地址
	protected byte slaveId;
	//功能码
	protected byte code;
	//crc16
	protected short crc16;
	
	public ModbusAbstractMessage() {

	}
	public abstract byte[] encodeMsg();
	
	public abstract void decodeMsg(byte[] msg);
}
