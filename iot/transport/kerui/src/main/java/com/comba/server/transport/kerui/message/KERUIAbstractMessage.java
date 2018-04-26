package com.comba.server.transport.kerui.message;

import org.apache.mina.core.buffer.IoBuffer;

import com.comba.server.transport.kerui.common.ByteUtil;
import com.comba.server.transport.kerui.common.Constant;
import com.comba.server.transport.kerui.common.ModbusCrc16;

import lombok.Data;

@Data
public class KERUIAbstractMessage {
	//包头		0x7F
	private byte packetHeader;
	//版本号		起始版本号为0x00
	private byte version;
	//目标地址	指示接收方地址，PC机地址为0x0，广播地址为0xFF
	private byte destAddr;
	//源地址		指示发送方地址
	private byte srcAddr;
	//命令码		指示要执行的命令
	protected byte cmdCode;
	//数据长度	指示后面紧跟着的数据长度，不包含CRC。2 byte
	protected short dataLen;
	//数据	传输数据，大端模式。
	protected byte[] data;
	//CRC校验	16位CRC检验，采用与MODBUS协议一样的CRC校验方法
	private short crc16;
	
	public KERUIAbstractMessage() {
		packetHeader = Constant.PACKET_HEADER;
	}
	public void encodeMessage(IoBuffer buf) {
		
		IoBuffer buffer = IoBuffer.allocate(2048).setAutoExpand(true);
		
		//不算帧头
		//buffer.put(packetHeader);
		buffer.put(version);
		buffer.put(destAddr);
		buffer.put(srcAddr);
		buffer.put(cmdCode);
		
		if(data != null)
			dataLen = (short) data.length;
		buffer.putShort(dataLen);
		
		if(dataLen > 0)
			buffer.put(data);
		buffer.flip();
		
		byte[] bufferBytes = new byte[buffer.limit() - buffer.position()];
		buffer.get(bufferBytes);

		//======组成码流
		byte[] crcBytes = new byte[2]; 
		ByteUtil.shortToByte_LH((short) ModbusCrc16.getCrc16(bufferBytes), crcBytes, 0);
		buf.put(packetHeader);
		buf.put(bufferBytes);
		buf.put(crcBytes);
	}

	public boolean decodeMessage(IoBuffer buf) {
		
		packetHeader = buf.get();	
		if(packetHeader != Constant.PACKET_HEADER)
			return false;
		version = buf.get();	
		destAddr = buf.get();
		srcAddr = buf.get();
		cmdCode = buf.get();
		
		dataLen = buf.getShort();
		
		//转义字符7F处理
		if(dataLen > 0 ) {
			data = new byte[dataLen];
			//buf.get(data);
			
			data[0] = buf.get();
			for(int i = 1; i < dataLen;){
				data[i] = buf.get();
				if(data[i] == data[i-1] && 
					data[i] == Constant.PACKET_HEADER){
					data[i] = buf.get();//如：7F 7F 7F 7F,第2位是转义，则下一个一定不是，所以放到第2位，再比较2,3位
				}
				i++;
			}
		}

		crc16 = buf.getShort();
		return true;
	}
	
	public void encodeData(IoBuffer bt) {
	}
	
	public void decodeData(byte[] body) {
	}
	
	public void initMessage(KERUIAbstractMessage absMsg) {
		packetHeader = absMsg.packetHeader;	
		version = absMsg.version;	
		destAddr = absMsg.destAddr;
		srcAddr = absMsg.srcAddr;
		cmdCode = absMsg.cmdCode;
		dataLen = absMsg.dataLen;
		data = absMsg.data;
		crc16 = absMsg.crc16;
	}
	
	@Override
	public String toString() {
		
		return "KERUIAbstractMessage [packetHeader=" + ByteUtil.byteToHexString(packetHeader) + ", version=" + version 
				+ ",destAddr=" + destAddr + ",srcAddr=" + srcAddr + ",cmdCode=" + cmdCode
				+ ",dataLen=" + dataLen + ",data=" + ByteUtil.bytesToHexString(data) + ",crc16=" + ByteUtil.bytesToHexString(ByteUtil.shortToBytes(crc16)) +"]";
	}
}
