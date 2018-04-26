package com.comba.transport.pm25.message;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import org.apache.mina.core.buffer.IoBuffer;

import com.comba.transport.pm25.common.ByteUtil;
import com.comba.transport.pm25.common.Constant;
@Slf4j
@Data
public class PM25QueryDeviceAddrRespMessage extends PM25AbstractMessage {

	//数据长度
	private byte dataLen;
	//随机字节 2个
	private byte[] randomBytes;
	
	public PM25QueryDeviceAddrRespMessage(){
		funCode = Constant.QUERY_DEVICE_ADDR_REQ;
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
		
		randomBytes = new byte[dataLen];
		buf.get(randomBytes);
		
		if(buf.remaining() >= crc16.length){
			buf.get(crc16);
		}
	}
	@Override
	public String toString() {

		return "PM25QueryDeviceAddrRespMessage [deviceAddr=" + ByteUtil.byteToHexString(deviceAddr) + ", funCode=" + funCode 
				+ ",dataLen=" + dataLen + ",randomBytes=" + ByteUtil.bytesToHexString(randomBytes)
				+ ",crc16=" + ByteUtil.bytesToHexString(crc16) +"]";
	}
}
