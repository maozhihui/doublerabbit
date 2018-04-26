package com.comba.server.transport.feibit.message;

import org.apache.mina.core.buffer.IoBuffer;

import com.comba.server.transport.feibit.common.ByteUtil;
import com.comba.server.transport.feibit.common.Constant;
import com.comba.server.transport.feibit.handler.FEIBITClientSessionHandler;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
//查询网关内保存的红外数据 响应

@Slf4j
@Data
public class FEIBITInfraredDataRespMessage extends FEIBITAbstractRespMessage {
	//请求的指令 红外请求
	private byte command;
	private byte checkout1;//0xAA
	private byte checkout2;//0x00
	//后续包长
	private byte len;
	private short shortAddr;
	private byte endpoint;
	private byte reserve;//0x00
	private short irId;
	//名称长度
	private byte nameLen;
	private byte[] name;

	
	public FEIBITInfraredDataRespMessage(){
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
		
		command = buf.get();
		checkout1 = buf.get();
		checkout2 = buf.get();
		len = buf.get();
		shortAddr = ByteUtil.shortConvert_LH(buf.getShort());
		endpoint = buf.get();
		irId = ByteUtil.shortConvert_LH(buf.getShort());
		nameLen = buf.get();
		name = new byte[nameLen];
		buf.get(name);
	}
	
	@Override
	public String toString() {

		return "FEIBITInfraredDataRespMessage 红外数据" 
				+ "["
				+ "command=" + ByteUtil.byteToHexString(command)
				+ ",checkout1=" + ByteUtil.byteToHexString(checkout1)
				+ ",checkout2=" + ByteUtil.byteToHexString(checkout2)
				+ ",len=" + ByteUtil.byteToHexString(len)
				+ ",shortAddr=" + ByteUtil.shortToHexString(shortAddr) 
				+ ",endpoint=" + ByteUtil.byteToHexString(endpoint) 
				+ ",irId=" + irId
				+ ",nameLen=" + ByteUtil.byteToHexString(nameLen) 
				+ ",name=" + new String(name) 
			    + "]";
	}
}
