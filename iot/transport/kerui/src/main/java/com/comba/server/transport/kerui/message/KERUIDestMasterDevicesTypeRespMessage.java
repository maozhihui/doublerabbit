package com.comba.server.transport.kerui.message;

import lombok.Data;

import org.apache.mina.core.buffer.IoBuffer;

import com.comba.server.transport.kerui.common.BitMap;
import com.comba.server.transport.kerui.common.ByteUtil;
import com.comba.server.transport.kerui.common.Constant;

/*
 * 
 * 回复目标主控器下的设备类型	
 * 
 * 数据占用48字节，每8字节为一组，每2bit指示该条线路上对应地址上的设备类型（设备可以是探测器或者屏）。
 * 00表示探测器，01表示屏，10表示无线灯，11保留。
 * 例如：收到前8个字节为0b1000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 
 * 0000 0000 0000 0000 0001，表示地址31处的设备为无线灯，地址1处的设备为屏，其余为探测器。
 * 
 */
@Data
public class KERUIDestMasterDevicesTypeRespMessage extends KERUIAbstractMessage {
	//private byte[] devicesType;
	private BitMap devicesType;
	
	public KERUIDestMasterDevicesTypeRespMessage() {
		cmdCode = Constant.DEST_MASTER_DEVICES_TYPE_RESP;
		dataLen = 48;
		
		byte[] devicesTypeBytes = new byte[dataLen];
		devicesType = new BitMap(devicesTypeBytes);
	}

	@Override
	public void encodeData(IoBuffer bt) {
		// TODO Auto-generated method stub
		
		data = devicesType.getBitMap();
	}

	@Override
	public void decodeData(byte[] body) {
		// TODO Auto-generated method stub
		if(data == null)
			return;
		
		devicesType.setBitMap(data);
	}
	
	public String devicesTypeBit(){
		
		StringBuffer devicesBit = new StringBuffer("");
		
		for(int i = 0; i < devicesType.getBitMap().length*8; ){
			if(i != 0){
				devicesBit.append(",");
			}
			
			int lowBit = devicesType.getBit(i);
			int highBit = devicesType.getBit(i+1);
			//System.out.print(value + "");
			devicesBit.append(lowBit);
			devicesBit.append(highBit);
	
			i = i + 2;
		}
		return devicesBit.toString();
	}
	
	//获取某设备地址的设备类型
	public byte getDeviceTypeByAddr(int deviceAddr){
		
		byte type = 0x00;
		StringBuffer devicesBit = new StringBuffer("");
	
		int lowBit = devicesType.getBit(deviceAddr*2);
		int highBit = devicesType.getBit(deviceAddr*2+1);
		//System.out.print(value + "");
		devicesBit.append(lowBit);
		devicesBit.append(highBit);
		
		return ByteUtil.hexStringTobyte(devicesBit.toString());
	}
	
	@Override
	public String toString() {
		
		return "KERUIDestMasterDevicesTypeRespMessage [packetHeader=" + ByteUtil.byteToHexString(getPacketHeader()) + ", version=" + getVersion() 
				+ ",destAddr=" + getDestAddr() + ",srcAddr=" + getSrcAddr() + ",cmdCode=" + cmdCode
				+ ",dataLen=" + dataLen + ",data=" + ByteUtil.bytesToHexString(data) + ",crc16=" + ByteUtil.bytesToHexString(ByteUtil.shortToBytes(getCrc16())) +"]"
				//+ ", [devicesType=" + ByteUtil.toHexForLog(ByteUtil.bytesToBitBytes(devicesType)) + "]";
				+ ", [devicesType=" + devicesTypeBit() + "]";
	}
}
