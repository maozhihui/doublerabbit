package com.comba.transport.daikin.message;

import lombok.Data;

import org.apache.mina.core.buffer.IoBuffer;

import com.comba.transport.daikin.common.ByteUtil;
import com.comba.transport.daikin.common.Constant;


/*
 * 
 * 查询信息列举了寄存器的起始地址和一个值。寄存器寻址起始地址为0：寄存器
40001的地址是0x0000。
例如：请求在从机地址3中写入一个值1000至寄存器40150中。

 */
@Data
public class PresetSingleRegisterMessage extends ModbusAbstractMessage{
	//地址
	private short offset;
	//数据
	private short data;
	
	public PresetSingleRegisterMessage(){
		code = Constant.PRESET_SINGLE_REGISTER;
	}
	
	@Override
	public byte[] encodeMsg() {
		// TODO Auto-generated method stub
		IoBuffer buf = IoBuffer.allocate(2048).setAutoExpand(true);
		
		buf.put(slaveId);
		buf.put(code);
		buf.putShort(offset);
		buf.putShort(data);
		buf.flip();
		
    	byte[] bytes = new byte[buf.remaining()];
    	buf.get(bytes);
    	
		return bytes;
	}

	@Override
	public void decodeMsg(byte[] message) {
		// TODO Auto-generated method stub
		
		IoBuffer buf = IoBuffer.allocate(message.length).setAutoExpand(true);
		buf.put(message);
		buf.flip();
		
		slaveId = buf.get();
		code = buf.get();
		offset = buf.getShort();
		data = buf.getShort();

		if(buf.remaining() >= 2)
			crc16 = buf.getShort();
	}
	
	@Override
	public String toString() {

		return "PresetSingleRegisterMessage ["
				+ "slaveId = " + slaveId
				+ ",code =" + code
				+ ",offset =" + offset 
				+ ",data =" + ByteUtil.shortToHexString(data)
				+"]";
	}
}
