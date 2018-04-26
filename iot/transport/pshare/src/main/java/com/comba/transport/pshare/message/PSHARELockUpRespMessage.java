package com.comba.transport.pshare.message;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import org.apache.mina.core.buffer.IoBuffer;

import com.comba.transport.pshare.common.ByteUtil;
import com.comba.transport.pshare.common.Constant;

//响应 升锁命令
//CODE-返回码  01-超时02-到位开关 03-遇阻 04-操作冲突

@Slf4j
@Data
public class PSHARELockUpRespMessage extends PSHAREAbstractMessage{
	
	private byte code;

	public PSHARELockUpRespMessage(){

	}

	@Override
	public void encodeMsg(IoBuffer bt) {
		// TODO Auto-generated method stub
		log.error("not suport encode method!");
	}

	@Override
	public void decodeMsg(IoBuffer buf) {
		// TODO Auto-generated method stub
		head = buf.get();
		addr = buf.get();
		len = buf.get();
		cmd = buf.get();
		code = buf.get();
		crc8 = buf.get();
		tail = buf.get();
	}

	@Override
	public String toString() {

		return "PSHARELockUpRespMessage ["
				+ "head=" + ByteUtil.byteToHexString(head) 
				+ ",addr=" + ByteUtil.byteToHexString(addr) 
				+ ",len=" + ByteUtil.byteToHexString(len) 
				+ ",cmd=" + ByteUtil.byteToHexString(cmd) 
				+ ",code=" + ByteUtil.byteToHexString(code) 
				+ ",crc8=" + ByteUtil.byteToHexString(crc8) 
				+ ",tail=" + ByteUtil.byteToHexString(tail) 
				+"]";
	}
}
