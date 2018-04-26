package com.comba.transport.pm25.message;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import org.apache.mina.core.buffer.IoBuffer;

import com.comba.transport.pm25.common.ByteUtil;
import com.comba.transport.pm25.common.Constant;
import com.comba.transport.pm25.common.ModbusCrc16;

@Slf4j
@Data
public class PM25QueryDeviceAddrReqMessage extends PM25AbstractMessage {
	//辅助命令号 0x00
	private byte auxiliaryFunCode;
	//固定值 0x00,0x00,0x1
	private byte[] fixedValue;
	
	public PM25QueryDeviceAddrReqMessage(){
		deviceAddr = Constant.IN_COMMON_USE_QUERY_ADDR;
		funCode = Constant.QUERY_DEVICE_ADDR_REQ;
		auxiliaryFunCode = 0x00;
		
		fixedValue = new byte[3];
		fixedValue[0] = 0x00;
		fixedValue[1] = 0x00;
		fixedValue[2] = 0x01;
	}

	@Override
	public void encodeMsg(IoBuffer buf) {
		// TODO Auto-generated method stub
		IoBuffer tempBuffer = IoBuffer.allocate(2048).setAutoExpand(true);
		tempBuffer.put(deviceAddr);
		tempBuffer.put(funCode);
		tempBuffer.put(auxiliaryFunCode);
		tempBuffer.put(fixedValue);
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

		return "PM25QueryDeviceAddrReqMessage [deviceAddr=" + ByteUtil.byteToHexString(deviceAddr) + ", funCode=" + funCode 
				+ ",auxiliaryFunCode=" + auxiliaryFunCode + ",fixedValue=" + ByteUtil.bytesToHexString(fixedValue) 
				+ ",crc16=" + ByteUtil.bytesToHexString(crc16) +"]";
	}
}
