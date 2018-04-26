package com.comba.server.transport.feibit.message;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import org.apache.mina.core.buffer.IoBuffer;

import com.comba.server.transport.feibit.common.ByteUtil;
import com.comba.server.transport.feibit.common.Constant;
import com.comba.server.transport.feibit.handler.FEIBITClientSessionHandler;

//查询网关内保存的红外数据

@Slf4j
@Data
public class FEIBITInfraredDataReqMessage extends FEIBITAbstractMessage {

	private byte parameterLen;
	private short shortAddr;//保存红外时，有可能为0
	private byte endpoint;//保存红外时，有可能为0
	//红外标识：查询
	private byte infrared;
	
	public FEIBITInfraredDataReqMessage(){
		command = Constant.INFRARED_RETRANSMISSION_AND_LEARN;
		infrared = Constant.INFRARED_DATA_READ;
	}
	@Override
	public void encodeBody(IoBuffer buf) {
		// TODO Auto-generated method stub
		parameterLen = 4;
		buf.put(parameterLen);
		buf.putShort(ByteUtil.shortConvert_LH(shortAddr));
		buf.put(endpoint);
		buf.put(infrared);
	}

	@Override
	public void decodeBody(byte[] body) {
		// TODO Auto-generated method stub
		log.error("not implement yet,can't run here!");
	}
	
	@Override
	public String toString() {

		return "FEIBITInfraredDataReqMessage " 
				+ "["
				+ "parameterLen=" + parameterLen
				+ ",shortAddr=" + ByteUtil.shortToHexString(shortAddr) 
				+ ",endpoint=" + ByteUtil.byteToHexString(endpoint) 
				+ ",infrared=" + ByteUtil.byteToHexString(infrared) 
			    + "]";
	}
}
