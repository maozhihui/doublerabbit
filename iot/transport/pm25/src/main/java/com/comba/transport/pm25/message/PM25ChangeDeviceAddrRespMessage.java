package com.comba.transport.pm25.message;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import org.apache.mina.core.buffer.IoBuffer;

import com.comba.transport.pm25.common.ByteUtil;
import com.comba.transport.pm25.common.Constant;
@Slf4j
@Data
public class PM25ChangeDeviceAddrRespMessage extends PM25AbstractMessage{
	//数据长度
	private byte dataLen;
	//设备地址
	private byte devAddr;

	
	public PM25ChangeDeviceAddrRespMessage(){
		funCode = Constant.CHANGE_DEVICE_ADDR_RESP;

	}

	@Override
	public void encodeMsg(IoBuffer bt) {
		// TODO Auto-generated method stub
		log.error("not suport encode method!");
	}

	@Override
	public void decodeMsg(IoBuffer buf) {
		// TODO Auto-generated method stub
		dataLen = buf.get();
		devAddr = buf.get();
		
		if(buf.remaining() >= crc16.length){
			buf.get(crc16);
		}
	}

	@Override
	public String toString() {

		return "PM25ChangeDeviceAddrRespMessage [deviceAddr=" + ByteUtil.byteToHexString(deviceAddr) + ", funCode=" + funCode 
				+ ",dataLen=" + dataLen + ",devAddr=" + ByteUtil.byteToHexString(devAddr)
				+ ",crc16=" + ByteUtil.bytesToHexString(crc16) +"]";
	}
}
