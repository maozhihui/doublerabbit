package com.comba.transport.pm25.message;

import lombok.Data;

import org.apache.mina.core.buffer.IoBuffer;

import com.comba.transport.pm25.common.ByteUtil;
import com.comba.transport.pm25.common.Constant;

@Data
public class PM25DeviceMacMessage  extends PM25AbstractMessage{

	private byte[] mac;
	
	public PM25DeviceMacMessage(){
		mac = new byte[Constant.MAC_LEN];
	}
	@Override
	public void encodeMsg(IoBuffer bt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void decodeMsg(IoBuffer buf) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public String toString() {

		return "PM25DeviceMacMessage ["
				+ ",mac=" + ByteUtil.bytesToHexString(mac) +"]";
	}
}
