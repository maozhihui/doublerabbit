package com.comba.server.transport.kerui.message;

import lombok.Data;

import org.apache.mina.core.buffer.IoBuffer;

import com.comba.server.transport.kerui.common.ByteUtil;
import com.comba.server.transport.kerui.common.Constant;

/*
 * 
 * 获取某一个探测器的状态	
 * 
 * 数据长度为2字节，第一字节表示主控地址，第二字节表示探测器地址。
 * 
 */
@Data
public class KERUIDetectorStateReqMessage extends KERUIAbstractMessage {
	//private byte masterAddr;
	private byte detectorAddr;
	
	public KERUIDetectorStateReqMessage() {
		cmdCode = Constant.DETECTOR_STATE_REQ;
		dataLen = 1;
		//masterAddr = 0x00;
		detectorAddr = 0x00;
	}

	@Override
	public void encodeData(IoBuffer bt) {
		// TODO Auto-generated method stub
		data = new byte[dataLen];
		//data[0] = masterAddr;
		data[0] = detectorAddr;
	}

	@Override
	public void decodeData(byte[] body) {
		// TODO Auto-generated method stub
		if(data == null)
			return;
		
		if(data.length >= 1){
			//masterAddr = data[0];
			detectorAddr = data[0];
		}
	}
	
	@Override
	public String toString() {
		
		return "KERUIDetectorStateReqMessage [packetHeader=" + ByteUtil.byteToHexString(getPacketHeader()) + ", version=" + getVersion() 
				+ ",destAddr=" + getDestAddr() + ",srcAddr=" + getSrcAddr() + ",cmdCode=" + cmdCode
				+ ",dataLen=" + dataLen + ",data=" + ByteUtil.bytesToHexString(data) + ",crc16=" + ByteUtil.bytesToHexString(ByteUtil.shortToBytes(getCrc16())) +"]"
				//+ ",[masterAddr="+ByteUtil.byteToHexString(masterAddr)+"]"
				+ ",[detectorAddr="+ByteUtil.byteToHexString(detectorAddr)+"]";
	}
}
