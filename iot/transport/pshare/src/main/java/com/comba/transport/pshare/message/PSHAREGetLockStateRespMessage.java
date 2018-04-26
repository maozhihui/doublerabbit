package com.comba.transport.pshare.message;

import org.apache.mina.core.buffer.IoBuffer;

import com.comba.transport.pshare.common.ByteUtil;
import com.comba.transport.pshare.common.Constant;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/*
 *  注：
 *  LOCK--00升锁状态，01降锁状态， 02中间状态，03上升遇阻后恢复，88运动状态
 *  CSB—车辆传感器状态，01为有车，00为无车02为变化中 03为未知
 *  BAT—电池电量，03满格，02中间，01低电 00没电 
 *  BEEP—蜂鸣器设置，01为打开，00为关闭  
 *  ONLINE—在线状态，01为在线，00为离线
 * 
 */
@Slf4j
@Data
public class PSHAREGetLockStateRespMessage extends PSHAREAbstractMessage {

	private byte lock;
	private byte csb;
	private byte bat;
	private byte beep;
	private byte online;//网关返回才有
	
	public PSHAREGetLockStateRespMessage() {
		head = Constant.PACK_HEAD;
		cmd = Constant.GET_LOCK_STATE;
		tail = Constant.PACK_TAIL;
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
		lock = buf.get();
		csb = buf.get();
		bat = buf.get();
		beep = buf.get();
		if(len == 6)
			online = buf.get();
		crc8 = buf.get();
		tail = buf.get();

	}
	
	@Override
	public String toString() {

		return "PSHAREGetLockStateRespMessage ["
				+ "head=" + ByteUtil.byteToHexString(head) 
				+ ",addr=" + ByteUtil.byteToHexString(addr) 
				+ ",len=" + ByteUtil.byteToHexString(len) 
				+ ",cmd=" + ByteUtil.byteToHexString(cmd) 
				+ ",lock=" + ByteUtil.byteToHexString(lock) 
				+ ",csb=" + ByteUtil.byteToHexString(csb) 
				+ ",bat=" + ByteUtil.byteToHexString(bat) 
				+ ",beep=" + ByteUtil.byteToHexString(beep) 
				+ ",crc8=" + ByteUtil.byteToHexString(crc8) 
				+ ",tail=" + ByteUtil.byteToHexString(tail) 
				+"]";
	}
}
