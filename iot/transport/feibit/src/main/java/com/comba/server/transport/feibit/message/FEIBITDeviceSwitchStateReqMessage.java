package com.comba.server.transport.feibit.message;

import org.apache.mina.core.buffer.IoBuffer;

import com.comba.server.transport.feibit.common.ByteUtil;
import com.comba.server.transport.feibit.common.Constant;
import com.comba.server.transport.feibit.handler.FEIBITClientSessionHandler;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

//0x85 获取指定设备的开关状态
@Slf4j
@Data
public class FEIBITDeviceSwitchStateReqMessage extends FEIBITAbstractMessage {

	//参数长度
	private byte parameterLen;
	//地址模式 0x02
	private byte addrMode;
	//短地址:2 byte
	private final int shortAddrLen = 2;
	private short shortAddr;
	//保留
	private final int reserve1Len = 6;
	private byte[] reserve1;
	
	private byte endpoint;
	//保留
	private final int reserve2Len = 2;
	private byte[] reserve2;
	
	public FEIBITDeviceSwitchStateReqMessage(){
		command = Constant.DEVICE_SWITCH_STATE_REQ;
		addrMode = 0x02;
		reserve1 = new byte[reserve1Len];
		reserve2 = new byte[reserve2Len];
	}

	@Override
	public void encodeBody(IoBuffer buf) {
		// TODO Auto-generated method stub
		parameterLen =  1 + shortAddrLen + reserve1Len + 1 + reserve2Len;
		
		buf.put(parameterLen);
		buf.put(addrMode);
		buf.putShort(ByteUtil.shortConvert_LH(shortAddr));
		buf.put(reserve1);
		buf.put(endpoint);
		buf.put(reserve2);
	}

	@Override
	public void decodeBody(byte[] body) {
		// TODO Auto-generated method stub
		log.error("not implement yet,can't run here!");
	}
	
	@Override
	public String toString() {

		return "FEIBITDeviceSwitchStateReqMessage "
				+ "["
				+ "parameterLen=" + parameterLen
				+ ",addrMode=" + addrMode
				+ ",shortAddr=" + ByteUtil.shortToHexString(shortAddr) 
				+ ",reserve1=" + ByteUtil.bytesToHexString(reserve1)
				+ ",endpoint=" + ByteUtil.byteToHexString(endpoint) 
				+ ",reserve2=" + ByteUtil.bytesToHexString(reserve2)
			    + "]";
	}
}
