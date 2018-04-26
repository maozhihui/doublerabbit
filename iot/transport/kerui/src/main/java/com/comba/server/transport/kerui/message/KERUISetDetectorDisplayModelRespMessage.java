package com.comba.server.transport.kerui.message;

import org.apache.mina.core.buffer.IoBuffer;

import com.comba.server.transport.kerui.common.ByteUtil;
import com.comba.server.transport.kerui.common.Constant;

/*
 * 
 * 回应设置	
 * 
 * 数据长度为0。此命令表示设置已经成功执行。
 * 
 */
public class KERUISetDetectorDisplayModelRespMessage extends KERUIAbstractMessage {
	
	public KERUISetDetectorDisplayModelRespMessage() {
		cmdCode = Constant.SET_DETECTOR_DISPLAY_MODEL_RESP;
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
		
		return "KERUISetDetectorDisplayModelRespMessage [packetHeader=" + ByteUtil.byteToHexString(getPacketHeader()) + ", version=" + getVersion() 
				+ ",destAddr=" + getDestAddr() + ",srcAddr=" + getSrcAddr() + ",cmdCode=" + cmdCode
				+ ",dataLen=" + dataLen + ",data=" + ByteUtil.bytesToHexString(data) + ",crc16=" + ByteUtil.bytesToHexString(ByteUtil.shortToBytes(getCrc16())) +"]";
	}
}
