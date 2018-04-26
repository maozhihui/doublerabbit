package com.comba.server.transport.feibit.message;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import org.apache.mina.core.buffer.IoBuffer;

import com.comba.server.transport.feibit.common.ByteUtil;
import com.comba.server.transport.feibit.common.Constant;
import com.comba.server.transport.feibit.handler.FEIBITClientSessionHandler;

//设置设备的响应消息

@Slf4j
@Data
public class FEIBITSettingDeviceRespMessage extends FEIBITAbstractRespMessage{

	//后续长度
	//private byte len;
	//指令
	private byte flag;
	//发送指令
	private byte command;
	
	public FEIBITSettingDeviceRespMessage() {
		deviceStateResp = Constant.DEVICE_STATE_RESP;
	}
	
	@Override
	public void encodeBody(IoBuffer bt) {
		// TODO Auto-generated method stub
		log.error("not implement yet,can't run here!");
	}

	@Override
	public void decodeBody(byte[] body) {
		// TODO Auto-generated method stub
		IoBuffer buf=IoBuffer.allocate(body.length).setAutoExpand(true);
		buf.put(body);
		buf.flip();
		
		//len = buf.get();
		flag = buf.get();
		command = buf.get();
	}
	@Override
	public String toString() {

		return "FEIBITSettingDeviceRespMessage "
				+ "["
				//+ "len=" + len
				+ "flag=" + ByteUtil.byteToHexString(flag)
				+ ",command=" + ByteUtil.byteToHexString(command) 
			    + "]";
	}
}
