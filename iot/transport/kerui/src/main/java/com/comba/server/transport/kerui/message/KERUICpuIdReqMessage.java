package com.comba.server.transport.kerui.message;

import org.apache.mina.core.buffer.IoBuffer;

import com.comba.server.transport.kerui.common.ByteUtil;
import com.comba.server.transport.kerui.common.Constant;

/*
 * 
 * 获取CPU ID	
 * 
 * 数据占用0字节
 * 
 */
public class KERUICpuIdReqMessage extends KERUIAbstractMessage {

	public KERUICpuIdReqMessage() {
		cmdCode = Constant.CPU_ID_REQ;
		dataLen = 0;
	}

	@Override
	public void encodeData(IoBuffer bt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void decodeData(byte[] body) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public String toString() {
		
		return "KERUICpuIdReqMessage [packetHeader=" + ByteUtil.byteToHexString(getPacketHeader()) + ", version=" + getVersion() 
				+ ",destAddr=" + getDestAddr() + ",srcAddr=" + getSrcAddr() + ",cmdCode=" + cmdCode
				+ ",dataLen=" + dataLen + ",data=" + ByteUtil.bytesToHexString(data) + ",crc16=" + ByteUtil.bytesToHexString(ByteUtil.shortToBytes(getCrc16())) +"]";
	}
}
