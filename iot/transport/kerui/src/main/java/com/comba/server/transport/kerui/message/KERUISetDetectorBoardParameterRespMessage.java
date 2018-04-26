package com.comba.server.transport.kerui.message;

import org.apache.mina.core.buffer.IoBuffer;

import com.comba.server.transport.kerui.common.ByteUtil;
import com.comba.server.transport.kerui.common.Constant;

/*
 * 回复设置探测板参数	
 * 
 * 回复一样的数据，表示已经设置成功。
 * 
 */
public class KERUISetDetectorBoardParameterRespMessage extends KERUIAbstractMessage {
	private byte[] parameter;
	
	public KERUISetDetectorBoardParameterRespMessage() {
		cmdCode = Constant.SET_DETECTOR_BOARD_PARAMETER_RESP;
		dataLen = 8;
		
		parameter = new byte[dataLen];
	}

	@Override
	public void encodeData(IoBuffer bt) {
		// TODO Auto-generated method stub
		data = parameter;
	}

	@Override
	public void decodeData(byte[] body) {
		// TODO Auto-generated method stub
		if(data == null)
			return;
		
		parameter = data;
	}
	
	@Override
	public String toString() {
		
		return "KERUISetDetectorBoardParameterRespMessage [packetHeader=" + ByteUtil.byteToHexString(getPacketHeader()) + ", version=" + getVersion() 
				+ ",destAddr=" + getDestAddr() + ",srcAddr=" + getSrcAddr() + ",cmdCode=" + cmdCode
				+ ",dataLen=" + dataLen + ",data=" + ByteUtil.bytesToHexString(data) + ",crc16=" + ByteUtil.bytesToHexString(ByteUtil.shortToBytes(getCrc16())) +"]"
				+ "[parameter=" + ByteUtil.bytesToHexString(parameter) + "]";
	}
}
