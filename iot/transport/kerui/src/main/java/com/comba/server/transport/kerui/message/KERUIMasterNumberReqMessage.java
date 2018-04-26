package com.comba.server.transport.kerui.message;

import lombok.Data;

import org.apache.mina.core.buffer.IoBuffer;

import com.comba.server.transport.kerui.common.ByteUtil;
import com.comba.server.transport.kerui.common.Constant;

/*
 * 
 * 获取主控板数量	数据占用1字节，无意义。
 * 
 */
@Data
public class KERUIMasterNumberReqMessage extends KERUIAbstractMessage {
	
	//1 byte,保留，暂无意义，初始化为0x00
	private byte reserve;

	public KERUIMasterNumberReqMessage() {
		cmdCode = Constant.MASTER_NUMBER_REQ;
		dataLen = 1;
		reserve = 0x00;
	}

	@Override
	public void encodeData(IoBuffer bt) {
		// TODO Auto-generated method stub
		data = new byte[dataLen];
		data[0] = reserve;
	}

	@Override
	public void decodeData(byte[] body) {
		// TODO Auto-generated method stub
/*		if(body == null)
			return;
		
		IoBuffer buf=IoBuffer.allocate(body.length).setAutoExpand(true);
		buf.put(body);
		buf.flip();
		
		reserve = buf.get();*/
		if(data == null)
			return;
		
		if(data.length >= 1){
			reserve = data[0];
		}
		
	}
	@Override
	public String toString() {
		
		return "KERUIMasterNumberReqMessage [packetHeader=" + ByteUtil.byteToHexString(getPacketHeader()) + ", version=" + getVersion() 
				+ ",destAddr=" + getDestAddr() + ",srcAddr=" + getSrcAddr() + ",cmdCode=" + cmdCode
				+ ",dataLen=" + dataLen + ",data=" + ByteUtil.bytesToHexString(data) + ",crc16=" + ByteUtil.bytesToHexString(ByteUtil.shortToBytes(getCrc16())) +"]"
				+ ",[reserve=" + reserve + "]";
	}
}
