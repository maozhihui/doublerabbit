package com.comba.server.transport.kerui.message;

import lombok.Data;

import org.apache.mina.core.buffer.IoBuffer;

import com.comba.server.transport.kerui.common.ByteUtil;
import com.comba.server.transport.kerui.common.Constant;

/*
 * 
 * 回复某一个探测器的状态	
 * 
 * 数据占用1字节，0x0表示无车，0x1表示有车，0x2表示错误。
 * 
 */
@Data
public class KERUIDetectorStateRespMessage extends KERUIAbstractMessage {
	private byte hasCar;
	
	public KERUIDetectorStateRespMessage() {
		cmdCode = Constant.DETECTOR_STATE_RESP;
		dataLen = 1;
		hasCar = 0x00;
	}

	@Override
	public void encodeData(IoBuffer bt) {
		// TODO Auto-generated method stub
		
		data = new byte[dataLen];
		data[0] = hasCar;
		
	}

	@Override
	public void decodeData(byte[] body) {
		// TODO Auto-generated method stub
		if(data == null)
			return;
		
		if(data.length >= 1){
			hasCar  = data[0];
		}
	}
	
	@Override
	public String toString() {
		
		return "KERUIDetectorStateRespMessage [packetHeader=" + ByteUtil.byteToHexString(getPacketHeader()) + ", version=" + getVersion() 
				+ ",destAddr=" + getDestAddr() + ",srcAddr=" + getSrcAddr() + ",cmdCode=" + cmdCode
				+ ",dataLen=" + dataLen + ",data=" + ByteUtil.bytesToHexString(data) + ",crc16=" + ByteUtil.bytesToHexString(ByteUtil.shortToBytes(getCrc16())) +"]"
				+ ",[hasCar="+hasCar+"]";
	}
}
