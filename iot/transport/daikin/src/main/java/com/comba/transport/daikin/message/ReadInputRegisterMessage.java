package com.comba.transport.daikin.message;

import org.apache.mina.core.buffer.IoBuffer;



import com.comba.transport.daikin.common.Constant;

import lombok.Data;

/*查询信息列举了寄存器的起始地址及寄存器的数量。寄存器寻址起始地址为0：寄
存器30001的地址是0x0000。此功能在一次查询中最多可读取4台VRV室内机信息。
例如：请求读取从机地址5中4个寄存器的值，从寄存器30030开始
*/

@Data
public class ReadInputRegisterMessage extends ModbusAbstractMessage{
	//偏移地址
	private short offset;
	//寄存器数量
	private short registerNum;

	public ReadInputRegisterMessage(){
		code = Constant.READ_INPUT_REGISTER;
	}


	@Override
	public byte[] encodeMsg() {
		// TODO Auto-generated method stub
		IoBuffer buf = IoBuffer.allocate(2048).setAutoExpand(true);
		
		buf.put(slaveId);
		buf.put(code);
		buf.putShort(offset);
		buf.putShort(registerNum);		
		buf.flip();
		
    	byte[] bytes = new byte[buf.remaining()];
    	buf.get(bytes);
    	
		return bytes;
	}


	@Override
	public void decodeMsg(byte[] message) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public String toString() {

		return "ReadInputRegisterMessage ["
				+ "slaveId = " + slaveId
				+ ",code =" + code
				+ ",offset =" + offset 
				+ ",registerNum =" + registerNum
				+"]";
	}
}
