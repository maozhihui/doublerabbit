package com.comba.server.transport.kerui.message;

import lombok.Data;

import org.apache.mina.core.buffer.IoBuffer;

import com.comba.server.transport.kerui.common.ByteUtil;
import com.comba.server.transport.kerui.common.Constant;

/*
 * 回复设置探测器参数	
 * 
 * 回复一样的数据说明设置成功，否则失败。
 * 
 */
@Data
public class KERUISetDetectorParameterRespMessage extends KERUIAbstractMessage {
	private byte detectorAddr;
	private byte distance;
	private byte[] reserve;
			
	public KERUISetDetectorParameterRespMessage() {
		cmdCode = Constant.SET_DETECTOR_PARAMETER_RESP;
		dataLen = 9;
		
		detectorAddr = 0x22;
		distance = 0x21;
		reserve = new byte[dataLen-2];
	}

	@Override
	public void encodeData(IoBuffer bt) {
		// TODO Auto-generated method stub
		IoBuffer buf=IoBuffer.allocate(dataLen).setAutoExpand(true);
		buf.put(detectorAddr);
		buf.put(distance);
		buf.put(reserve);
		buf.flip();
		
		data = new byte[dataLen];
		buf.get(data);
		
	}

	@Override
	public void decodeData(byte[] body) {
		// TODO Auto-generated method stub
		if(data == null)
			return;
		
		IoBuffer buf=IoBuffer.allocate(data.length).setAutoExpand(true);
		buf.put(data);
		buf.flip();
		
		if(data.length >= dataLen){
			detectorAddr = buf.get();
			distance = buf.get();
			buf.get(reserve);
		}
	}
	
	@Override
	public String toString() {
		
		return "KERUISetDetectorParameterRespMessage [packetHeader=" + ByteUtil.byteToHexString(getPacketHeader()) + ", version=" + getVersion() 
				+ ",destAddr=" + getDestAddr() + ",srcAddr=" + getSrcAddr() + ",cmdCode=" + cmdCode
				+ ",dataLen=" + dataLen + ",data=" + ByteUtil.bytesToHexString(data) + ",crc16=" + ByteUtil.bytesToHexString(ByteUtil.shortToBytes(getCrc16())) +"]"
				+ "[screenAddr="+ByteUtil.byteToHexString(detectorAddr)
				+ ",masterAddr="+distance
				+ ",binds="+ByteUtil.toHexForLog(reserve)+"]";
	}
}
