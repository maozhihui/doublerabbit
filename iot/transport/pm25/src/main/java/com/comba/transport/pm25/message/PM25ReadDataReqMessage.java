package com.comba.transport.pm25.message;

import org.apache.mina.core.buffer.IoBuffer;

import com.comba.transport.pm25.common.ByteUtil;
import com.comba.transport.pm25.common.Constant;
import com.comba.transport.pm25.common.ModbusCrc16;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class PM25ReadDataReqMessage extends PM25AbstractMessage {
	//起始地址
	private short startAddr;
	//数据长度
	private short dataLen;
	
	public PM25ReadDataReqMessage() {
		funCode = Constant.READ_DATA_REQ;
		startAddr = 0;
		dataLen = 0x0A;
		//crc16 = new byte[2];
	}

	@Override
	public void encodeMsg(IoBuffer buf) {
		// TODO Auto-generated method stub
		
		IoBuffer tempBuffer = IoBuffer.allocate(2048).setAutoExpand(true);
		tempBuffer.put(deviceAddr);
		tempBuffer.put(funCode);
		tempBuffer.putShort(startAddr);
		tempBuffer.putShort(dataLen);
		tempBuffer.flip();
		
		byte[] tempBytes = new byte[tempBuffer.limit() - tempBuffer.position()];
		tempBuffer.get(tempBytes);

		//组成码流,大端模式
		ByteUtil.shortToByte_LH((short) ModbusCrc16.getCrc16(tempBytes), crc16, 0);
		buf.put(tempBytes);
		buf.put(crc16);
	}
	
	@Override
	public void decodeMsg(IoBuffer buf) {
		// TODO Auto-generated method stub
		log.error("not suport decode method!");
	}
	
	@Override
	public String toString() {

		return "PM25ReadDataReqMessage [deviceAddr=" + ByteUtil.byteToHexString(deviceAddr) + ", funCode=" + funCode 
				+ ",startAddr=" + startAddr + ",dataLen=" + dataLen 
				+ ",crc16=" + ByteUtil.bytesToHexString(crc16) +"]";
	}
}
