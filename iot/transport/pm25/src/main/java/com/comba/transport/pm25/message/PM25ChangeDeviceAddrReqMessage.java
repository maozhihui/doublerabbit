package com.comba.transport.pm25.message;

import org.apache.mina.core.buffer.IoBuffer;

import com.comba.transport.pm25.common.Constant;

public class PM25ChangeDeviceAddrReqMessage extends PM25AbstractMessage{
	//辅助命令号 0x0B
	private byte auxiliaryFunCode;
	//固定值 0x00,0x00
	private short fixedValue;
	//更改的地址
	private byte changeAddr;
	
	public PM25ChangeDeviceAddrReqMessage(){
		funCode = Constant.CHANGE_DEVICE_ADDR_REQ;
		auxiliaryFunCode = 0x0B;
		fixedValue = 0;
	}

	@Override
	public void encodeMsg(IoBuffer bt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void decodeMsg(IoBuffer buf) {
		// TODO Auto-generated method stub
		
	}


}
