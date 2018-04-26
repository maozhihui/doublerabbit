package com.comba.server.transport.kerui.message;

import lombok.Data;

import org.apache.mina.core.buffer.IoBuffer;

import com.comba.server.transport.kerui.common.BitMap;
import com.comba.server.transport.kerui.common.ByteUtil;
import com.comba.server.transport.kerui.common.Constant;

/*
 * 
 * 回复目标主控器所有探测器状态	
 * 
 * 命令参数无意义。数据占用24字节，每4个字节共32位为一组，对应一路探测器，
 * 每1位代表该探测器的状态，0表示无车，1表示有车。
 * 例如：0b0000 0000 0000 0000 0000 0000 0000 0010 
 * 表示该路上只有编码为1的探测器有车，其余无车
 * 
 */
@Data
public class KERUIDestMasterDetectorsStateRespMessage extends KERUIAbstractMessage {
	private BitMap[] group;
	
	public KERUIDestMasterDetectorsStateRespMessage() {
		cmdCode = Constant.DEST_MASTER_DETECTORS_STATE_RESP;
		dataLen = 24;
		
		byte[] devicesBytes = new byte[4];
		group = new BitMap[6];
		
		for(int i = 0; i < group.length; i++){
			group[i] = new BitMap(devicesBytes);
		}
	}

	@Override
	public void encodeData(IoBuffer bt) {
		// TODO Auto-generated method stub	
	}

	@Override
	public void decodeData(byte[] body) {
		// TODO Auto-generated method stub
		
		if(data == null)
			return;
		
		for(int i = 0; i < group.length; i++){
			byte[] bytes = new byte[4];
			bytes[0] = data[i*4+3];
			bytes[1] = data[i*4+2];
			bytes[2] = data[i*4+1];
			bytes[3] = data[i*4+0];
			group[i].setBitMap(bytes);
		}
	}
	
	public String detectorsStateBit(){
		
		StringBuffer devicesBit = new StringBuffer("");
		for(int i = 0; i < group.length; i++){
			for(int j = 0; j < group[i].getBitMap().length*8; j++){
				if(j != 0){
					devicesBit.append(" ");
				}
				
				int value = group[i].getBit(j);
				devicesBit.append(value);
			}
			devicesBit.append("\n");
		}
		return devicesBit.toString();
	}
	
	
	public byte getDetectorStateByAddr(byte addr){
		int index = addr/32;
		int state = group[index].getBit(addr%32);
		return ByteUtil.intToByte(state);
	}
	
	@Override
	public String toString() {
		
		return "KERUIDestMasterDetectorsStateRespMessage [packetHeader=" + ByteUtil.byteToHexString(getPacketHeader()) + ", version=" + getVersion() 
				+ ",destAddr=" + getDestAddr() + ",srcAddr=" + getSrcAddr() + ",cmdCode=" + cmdCode
				+ ",dataLen=" + dataLen + ",data=" + ByteUtil.bytesToHexString(data) + ",crc16=" + ByteUtil.bytesToHexString(ByteUtil.shortToBytes(getCrc16())) +"]"
				+ ", [DetectorsState=" + detectorsStateBit() + "]";
	}
}
