package com.comba.server.transport.kerui.message;

import lombok.Data;

import org.apache.mina.core.buffer.IoBuffer;

import com.comba.server.transport.kerui.common.BitMap;
import com.comba.server.transport.kerui.common.ByteUtil;
import com.comba.server.transport.kerui.common.Constant;

/*
 * 
 * 回复目标主控器下的设备是否存在	
 * 
 * 数据占用24字节，每4字节为一组，每一bit指示该条线路上对应地址是否有设备（设备可以是探测器或者屏）。
 * 1表示有设备，0表示无设备。例如：收到前4个字节为0b0001 0000 0000 0000 0000 0000 0000 1110，
 * 表示地址1，2，3 ，28有设备，其余地址无设备。
 * 
 */
@Data
public class KERUIDestMasterDevicesRespMessage extends KERUIAbstractMessage {
	private BitMap[] group;

	public KERUIDestMasterDevicesRespMessage() {
		cmdCode = Constant.DEST_MASTER_DEVICES_RESP;
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
	
	public String devicesBit(){
		
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
	
	@Override
	public String toString() {
		
		return "KERUIDestMasterDevicesRespMessage [packetHeader=" + ByteUtil.byteToHexString(getPacketHeader()) + ", version=" + getVersion() 
				+ ",destAddr=" + getDestAddr() + ",srcAddr=" + getSrcAddr() + ",cmdCode=" + cmdCode
				+ ",dataLen=" + dataLen + ",data=" + ByteUtil.bytesToHexString(data) + ",crc16=" + ByteUtil.bytesToHexString(ByteUtil.shortToBytes(getCrc16())) +"]"
				//+ ", [devices=" + ByteUtil.toHexForLog(ByteUtil.bytesToBitBytes(data)) + "]";
				+ ", [devices=" + devicesBit() + "]";
	}
}
