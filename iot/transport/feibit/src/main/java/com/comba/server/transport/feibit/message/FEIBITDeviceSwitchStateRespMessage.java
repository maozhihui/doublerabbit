package com.comba.server.transport.feibit.message;

import org.apache.mina.core.buffer.IoBuffer;

import com.comba.server.transport.feibit.common.ByteUtil;
import com.comba.server.transport.feibit.common.Constant;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

//获取指定设备的开关状态 响应

@Slf4j
@Data
public class FEIBITDeviceSwitchStateRespMessage extends FEIBITAbstractRespMessage {

	//后续长度
	//private byte len;
	//地址
	private short shortAddr;
	//endpoint
	private byte endpoint;
	//开关状态
	private byte switchState;
	
	public FEIBITDeviceSwitchStateRespMessage(){
		deviceStateResp = Constant.DEVICE_SWITCH_STATE_RESP;
		
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
		shortAddr = ByteUtil.shortConvert_LH(buf.getShort());
		endpoint = buf.get();
		switchState = buf.get();
	}
	
	@Override
	public String toString() {

		return "FEIBITDeviceSwitchStateRespMessage 开关状态"
				+ "["
				//+ "len=" + len
				+ "shortAddr=" + ByteUtil.shortToHexString(shortAddr)
				+ ",endpoint=" + ByteUtil.byteToHexString(endpoint) 
				+ ",switchState=" + switchState 
			    + "]";
	}
}
