package com.comba.server.transport.kerui.message;

import java.io.UnsupportedEncodingException;

import lombok.Data;

import org.apache.mina.core.buffer.IoBuffer;

import com.comba.server.transport.kerui.common.ByteUtil;
import com.comba.server.transport.kerui.common.Constant;

/*
 * 
 * 回复获取CPU ID	
 * 
 * 数据占用12字节，内容为CPU ID
 * 
 */
@Data
public class KERUICpuIdRespMessage extends KERUIAbstractMessage {
	private byte[] cpuId;
	
	public KERUICpuIdRespMessage() {
		cmdCode = Constant.CPU_ID_RESP;
		dataLen = 12;
		
		cpuId = new byte[dataLen];
	}

	@Override
	public void encodeData(IoBuffer bt) {
		// TODO Auto-generated method stub
		data = cpuId;
	}

	@Override
	public void decodeData(byte[] body) {
		// TODO Auto-generated method stub
		if(data == null)
			return;
		
		cpuId = data;
	}
	
	@Override
	public String toString() {
		
		try {
			return "KERUICpuIdRespMessage [packetHeader=" + ByteUtil.byteToHexString(getPacketHeader()) + ", version=" + getVersion() 
					+ ",destAddr=" + getDestAddr() + ",srcAddr=" + getSrcAddr() + ",cmdCode=" + cmdCode
					+ ",dataLen=" + dataLen + ",data=" + ByteUtil.bytesToHexString(data) + ",crc16=" + ByteUtil.bytesToHexString(ByteUtil.shortToBytes(getCrc16())) +"]"
					+ ", [cpuId=" + ByteUtil.bytesToHexString(cpuId) + "]"
					+ ", [cpuId=" + new String(cpuId,"utf-8") + "]";
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
}
