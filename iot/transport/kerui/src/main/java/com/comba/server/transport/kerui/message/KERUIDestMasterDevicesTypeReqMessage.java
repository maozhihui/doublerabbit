package com.comba.server.transport.kerui.message;

import org.apache.mina.core.buffer.IoBuffer;

import com.comba.server.transport.kerui.common.ByteUtil;
import com.comba.server.transport.kerui.common.Constant;

/*
 * 
 * 获取目标主控器下的设备类型	数据长度为0
 * 
 */
public class KERUIDestMasterDevicesTypeReqMessage extends KERUIAbstractMessage {
	
	public KERUIDestMasterDevicesTypeReqMessage() {
		cmdCode = Constant.DEST_MASTER_DEVICES_TYPE_REQ;
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
		
		return "KERUIDestMasterDevicesTypeReqMessage [packetHeader=" + ByteUtil.byteToHexString(getPacketHeader()) + ", version=" + getVersion() 
				+ ",destAddr=" + getDestAddr() + ",srcAddr=" + getSrcAddr() + ",cmdCode=" + cmdCode
				+ ",dataLen=" + dataLen + ",data=" + ByteUtil.bytesToHexString(data) + ",crc16=" + ByteUtil.bytesToHexString(ByteUtil.shortToBytes(getCrc16())) +"]";
	}
}
