package com.comba.server.transport.feibit.message;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import org.apache.mina.core.buffer.IoBuffer;

import com.comba.server.transport.feibit.common.ByteUtil;
import com.comba.server.transport.feibit.common.Constant;
import com.comba.server.transport.feibit.handler.FEIBITClientSessionHandler;

//发送网关内保存的红外数据

@Slf4j
@Data
public class FEIBITInfraredDataSendMessage extends FEIBITAbstractMessage {

	private byte parameterLen;
	private short shortAddr;
	private byte endpoint;
	//红外标识：发送
	private byte infrared;
	private short irId;
	//保留
	private final int reserveLen = 2;
	private byte[] reserve;
	
	public FEIBITInfraredDataSendMessage(){
		command = Constant.INFRARED_RETRANSMISSION_AND_LEARN;
		infrared = Constant.INFRARED_DATA_SEND;
		reserve = new byte[reserveLen];
	}
	@Override
	public void encodeBody(IoBuffer buf) {
		// TODO Auto-generated method stub
		parameterLen = 8;
		buf.put(parameterLen);
		buf.putShort(ByteUtil.shortConvert_LH(shortAddr));
		buf.put(endpoint);
		buf.put(infrared);
		buf.putShort(ByteUtil.shortConvert_LH(irId));
		buf.put(reserve);
	}

	@Override
	public void decodeBody(byte[] body) {
		// TODO Auto-generated method stub
		log.error("not implement yet,can't run here!");
	}
	
	@Override
	public String toString() {

		return "FEIBITInfraredDataSendMessage "
				+ "["
				+ "parameterLen=" + parameterLen
				+ ",shortAddr=" + ByteUtil.shortToHexString(shortAddr) 
				+ ",endpoint=" + ByteUtil.byteToHexString(endpoint) 
				+ ",infrared=" + ByteUtil.byteToHexString(infrared) 
				+ ",irId=" + ByteUtil.shortToHexString(irId) 
				+ ",reserve=" + ByteUtil.bytesToHexString(reserve) 
			    + "]";
	}
}
