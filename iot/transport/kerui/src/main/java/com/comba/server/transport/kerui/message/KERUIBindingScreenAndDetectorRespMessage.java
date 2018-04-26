package com.comba.server.transport.kerui.message;

import lombok.Data;

import org.apache.mina.core.buffer.IoBuffer;

import com.comba.server.transport.kerui.common.ByteUtil;
import com.comba.server.transport.kerui.common.Constant;

/*
 * 
 * 回复绑定	数据占用1字节，0表示无错误，非0表示有错误。
 * 
 */
@Data
public class KERUIBindingScreenAndDetectorRespMessage extends KERUIAbstractMessage {
	private byte bindState; 
	
	public KERUIBindingScreenAndDetectorRespMessage() {
		cmdCode = Constant.BINDING_SCREEN_AND_DETECTOR_RESP;
		dataLen = 1;
		
		bindState = 0x01;//有错误
	}

	@Override
	public void encodeData(IoBuffer bt) {
		// TODO Auto-generated method stub
		
		data = new byte[dataLen];
		data[0] = bindState;
	}

	@Override
	public void decodeData(byte[] body) {
		// TODO Auto-generated method stub
		if(data == null)
			return;
		
		if(data.length >= dataLen){
			bindState = data[0];
		}
	}
	
	@Override
	public String toString() {
		
		return "KERUIBindingScreenAndDetectorRespMessage [packetHeader=" + ByteUtil.byteToHexString(getPacketHeader()) + ", version=" + getVersion() 
				+ ",destAddr=" + getDestAddr() + ",srcAddr=" + getSrcAddr() + ",cmdCode=" + cmdCode
				+ ",dataLen=" + dataLen + ",data=" + ByteUtil.bytesToHexString(data) + ",crc16=" + ByteUtil.bytesToHexString(ByteUtil.shortToBytes(getCrc16())) +"]"
				+ "[bindState="+ByteUtil.byteToHexString(bindState)+"]";
	}
}
