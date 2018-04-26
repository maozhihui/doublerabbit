package com.comba.server.transport.kerui.message;

import lombok.Data;

import org.apache.mina.core.buffer.IoBuffer;

import com.comba.server.transport.kerui.common.ByteUtil;
import com.comba.server.transport.kerui.common.Constant;

/*
 * 回复主控板数量	数据占用N字节，每个字节表示存在的主控板的地址。
 * 
 */
@Data
public class KERUIMasterNumberRespMessage extends KERUIAbstractMessage {

	private short mastersNum;//相当于dataLen
	private byte[] mastersAddr;//相当于data
	
	public KERUIMasterNumberRespMessage() {
		cmdCode = Constant.MASTER_NUMBER_RESP;
	}

	@Override
	public void encodeData(IoBuffer bt) {
		// TODO Auto-generated method stub
		
		dataLen = mastersNum;
		//data = new byte[dataLen];
		data = mastersAddr;
	}

	@Override
	public void decodeData(byte[] body) {
		// TODO Auto-generated method stub
		if(data == null)
			return;
		
		mastersNum = dataLen;
		mastersAddr = data;
	}
	
	@Override
	public String toString() {
		
		return "KERUIMasterNumberRespMessage [packetHeader=" + ByteUtil.byteToHexString(getPacketHeader()) + ", version=" + getVersion() 
				+ ",destAddr=" + getDestAddr() + ",srcAddr=" + getSrcAddr() + ",cmdCode=" + cmdCode
				+ ",dataLen=" + dataLen + ",data=" + ByteUtil.bytesToHexString(data) + ",crc16=" + ByteUtil.bytesToHexString(ByteUtil.shortToBytes(getCrc16())) +"]"
				+ ",[masterNum=" + mastersNum + ", mastersAddr=" + ByteUtil.toHexForLog(mastersAddr) + "]";
	}
}
