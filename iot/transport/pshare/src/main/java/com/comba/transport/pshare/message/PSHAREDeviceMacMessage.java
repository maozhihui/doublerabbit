package com.comba.transport.pshare.message;

import lombok.Data;

import org.apache.mina.core.buffer.IoBuffer;

import com.comba.transport.pshare.common.ByteUtil;
import com.comba.transport.pshare.common.Constant;

@Data
public class PSHAREDeviceMacMessage  extends PSHAREAbstractMessage{

	private byte[] mac;
	
	public PSHAREDeviceMacMessage(){
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

		return "PSHAREDeviceMacMessage ["
				+ "mac=" + ByteUtil.bytesToHexString(mac) +"]";
	}
}
