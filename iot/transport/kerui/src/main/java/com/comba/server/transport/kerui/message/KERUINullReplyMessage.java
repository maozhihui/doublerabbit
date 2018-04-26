package com.comba.server.transport.kerui.message;

import org.apache.mina.core.buffer.IoBuffer;

import com.comba.server.transport.kerui.common.ByteUtil;
import com.comba.server.transport.kerui.common.Constant;
/*
 * 空回复 		表示正在准备数据，请等待
 * 
 */
public class KERUINullReplyMessage extends KERUIAbstractMessage {
	
	public KERUINullReplyMessage() {
		cmdCode = Constant.NULL_REPLY;
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
		
		return "KERUINullReplyMessage [packetHeader=" + ByteUtil.byteToHexString(getPacketHeader()) + ", version=" + getVersion() 
				+ ",destAddr=" + getDestAddr() + ",srcAddr=" + getSrcAddr() + ",cmdCode=" + cmdCode
				+ ",dataLen=" + dataLen + ",data=" + ByteUtil.bytesToHexString(data) + ",crc16=" + ByteUtil.bytesToHexString(ByteUtil.shortToBytes(getCrc16())) +"]";
				
	}
}
