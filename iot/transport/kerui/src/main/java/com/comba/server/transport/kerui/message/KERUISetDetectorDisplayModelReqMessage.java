package com.comba.server.transport.kerui.message;

import org.apache.mina.core.buffer.IoBuffer;

import com.comba.server.transport.kerui.common.ByteUtil;
import com.comba.server.transport.kerui.common.Constant;

/*
 * 
 * 设置一个探测器的显示方式	
 * 
 * 数据占用2字节。第1字节表示探测器地址，第2字节指示显示方式。
 * 0x0表示正常显示车位状态，0x1表示该车位为VIP车位，显示蓝色
 * ，0x2表示测试状态，显示绿色以1Hz速度闪烁，0x3表示预约模式，其它值意义待扩展。
 */
public class KERUISetDetectorDisplayModelReqMessage extends KERUIAbstractMessage {
	private byte detectorAddr;
	private byte displayModel;
	
	public KERUISetDetectorDisplayModelReqMessage() {
		cmdCode = Constant.SET_DETECTOR_DISPLAY_MODEL_REQ;
		dataLen = 2;
		
		detectorAddr = 0x00;
		displayModel = 0x00;
	}

	@Override
	public void encodeData(IoBuffer bt) {
		// TODO Auto-generated method stub
		data = new byte[dataLen];
		data[0] = detectorAddr;
		data[1] = displayModel;
	}

	@Override
	public void decodeData(byte[] body) {
		// TODO Auto-generated method stub
		if(data == null)
			return;
		
		if(data.length >= 2){
			detectorAddr = data[0];
			displayModel = data[1];
		}
	}
	
	@Override
	public String toString() {
		
		return "KERUISetDetectorDisplayModelReqMessage [packetHeader=" + ByteUtil.byteToHexString(getPacketHeader()) + ", version=" + getVersion() 
				+ ",destAddr=" + getDestAddr() + ",srcAddr=" + getSrcAddr() + ",cmdCode=" + cmdCode
				+ ",dataLen=" + dataLen + ",data=" + ByteUtil.bytesToHexString(data) + ",crc16=" + ByteUtil.bytesToHexString(ByteUtil.shortToBytes(getCrc16())) +"]"
				+ ",[detectorAddr="+ByteUtil.byteToHexString(detectorAddr)+"]"
				+ ",[displayModel="+ByteUtil.byteToHexString(displayModel)+"]";
	}
}
