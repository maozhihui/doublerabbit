package com.comba.transport.pshare.message;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import org.apache.mina.core.buffer.IoBuffer;

import com.comba.transport.pshare.common.ByteUtil;
import com.comba.transport.pshare.common.CRC8;
import com.comba.transport.pshare.common.Constant;

//升锁
@Slf4j
@Data
public class PSHARELockUpReqMessage extends PSHAREAbstractMessage{
	
	public PSHARELockUpReqMessage(){
		head = Constant.PACK_HEAD;
		len = 0x01;
		cmd = Constant.LOCK_UP;
		tail = Constant.PACK_TAIL;
	}

	@Override
	public void encodeMsg(IoBuffer buf) {
		// TODO Auto-generated method stub
		IoBuffer tempBuffer = IoBuffer.allocate(2048).setAutoExpand(true);
		tempBuffer.put(addr);
		tempBuffer.put(len);
		tempBuffer.put(cmd);
		tempBuffer.flip();
		
		byte[] tempBytes = new byte[tempBuffer.limit() - tempBuffer.position()];
		tempBuffer.get(tempBytes);

		crc8 = CRC8.calcCrc8(tempBytes);
		
		buf.put(head);
		buf.put(tempBytes);
		buf.put(crc8);
		buf.put(tail);
	}

	@Override
	public void decodeMsg(IoBuffer buf) {
		// TODO Auto-generated method stub
		log.error("not suport decode method!");
	}

	@Override
	public String toString() {

		return "PSHARELockUpReqMessage ["
				+ "head=" + ByteUtil.byteToHexString(head) 
				+ ",addr=" + ByteUtil.byteToHexString(addr) 
				+ ",len=" + ByteUtil.byteToHexString(len) 
				+ ",cmd=" + ByteUtil.byteToHexString(cmd) 
				+ ",crc8=" + ByteUtil.byteToHexString(crc8) 
				+ ",tail=" + ByteUtil.byteToHexString(tail) 
				+"]";
	}
}
