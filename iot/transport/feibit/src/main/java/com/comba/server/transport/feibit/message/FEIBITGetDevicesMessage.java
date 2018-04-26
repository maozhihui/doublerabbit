package com.comba.server.transport.feibit.message;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import org.apache.mina.core.buffer.IoBuffer;

import com.comba.server.transport.feibit.common.Constant;


/*
 * 获取当前连接网关的设备 请求消息
 * 
 */
@Slf4j
@Data
public class FEIBITGetDevicesMessage extends FEIBITAbstractMessage {

	public FEIBITGetDevicesMessage() {
		command = Constant.GET_DEVICES_REQ;
	}

	@Override
	public void encodeBody(IoBuffer bt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void decodeBody(byte[] body) {
		// TODO Auto-generated method stub
		log.error("not implement yet,can't run here!");
	}
}
