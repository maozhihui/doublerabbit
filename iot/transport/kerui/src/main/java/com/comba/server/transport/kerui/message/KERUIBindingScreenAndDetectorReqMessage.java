package com.comba.server.transport.kerui.message;

import lombok.Data;

import org.apache.mina.core.buffer.IoBuffer;

import com.comba.server.transport.kerui.common.BitMap;
import com.comba.server.transport.kerui.common.ByteUtil;
import com.comba.server.transport.kerui.common.Constant;

/*
 * 
 * 设置绑定屏与探测器	
 * 
 * 数据占用26字节，第1字节表示要绑定的屏的地址；第2字节表示要绑定的探测器所在的主控的地址；
 * 后24字节每一bit表示是否绑定，1表示绑定，0表示不绑定。如果多次绑定同一个主控下的探测器，
 * 以最后一次设置为最终结果。
 */
@Data
public class KERUIBindingScreenAndDetectorReqMessage extends KERUIAbstractMessage {
	private byte screenAddr;
	private byte masterAddr;
	//private byte[] binds;
	private BitMap binds;
	
	public KERUIBindingScreenAndDetectorReqMessage() {
		cmdCode = Constant.BINDING_SCREEN_AND_DETECTOR_REQ;
		dataLen = 26;
		
		screenAddr = 0x11;
		masterAddr = 0x12;
		
		byte[] bindsBytes = new byte[dataLen-2];
		binds = new BitMap(bindsBytes);
	}

	@Override
	public void encodeData(IoBuffer bt) {
		// TODO Auto-generated method stub
		
		IoBuffer buf=IoBuffer.allocate(dataLen).setAutoExpand(true);
		buf.put(screenAddr);
		buf.put(masterAddr);
		buf.put(binds.getBitMap());
		buf.flip();
		
		data = new byte[dataLen];
		buf.get(data);
		
	}

	@Override
	public void decodeData(byte[] body) {
		// TODO Auto-generated method stub
		if(data == null)
			return;
		
		IoBuffer buf=IoBuffer.allocate(data.length).setAutoExpand(true);
		buf.put(data);
		buf.flip();
		
		if(data.length >= dataLen){
			screenAddr = buf.get();
			masterAddr = buf.get();
			
			byte[] bindsBytes = new byte[dataLen - 2];
			buf.get(bindsBytes);
			
			binds.setBitMap(bindsBytes);
		}
	}
	
	public String bindsBit(){
		
		StringBuffer devicesBit = new StringBuffer("");
		
		for(int i = 0; i < binds.getBitMap().length*8; i++){
			if(i != 0){
				devicesBit.append(",");
			}
			
			int value = binds.getBit(i);
			//System.out.print(value + "");
			devicesBit.append(value);
			//devicesBit.append(",");
		}
		return devicesBit.toString();
	}
	
	@Override
	public String toString() {
		
		return "KERUIBindingScreenAndDetectorReqMessage [packetHeader=" + ByteUtil.byteToHexString(getPacketHeader()) + ", version=" + getVersion() 
				+ ",destAddr=" + getDestAddr() + ",srcAddr=" + getSrcAddr() + ",cmdCode=" + cmdCode
				+ ",dataLen=" + dataLen + ",data=" + ByteUtil.bytesToHexString(data) + ",crc16=" + ByteUtil.bytesToHexString(ByteUtil.shortToBytes(getCrc16())) +"]"
				+ "[screenAddr="+ByteUtil.byteToHexString(screenAddr)
				+ ",masterAddr="+ByteUtil.byteToHexString(masterAddr)
				//+ ",binds="+ByteUtil.toHexForLog(binds)+"]";
				+ ",binds="+bindsBit()+"]";
	}
}
