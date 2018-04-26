package com.comba.transport.daikin.message;

import org.apache.mina.core.buffer.IoBuffer;




import com.comba.transport.daikin.common.ByteUtil;
import com.comba.transport.daikin.common.Constant;

import lombok.Data;

/* 
 * 响应查询，读取输入寄存器中的值 
*/

@Data
public class ReadInputRegisterRespMessage extends ModbusAbstractMessage{
	//数据大小
	private byte dataLen;
	//数据内容
	private short[] data;

	public ReadInputRegisterRespMessage(){
		code = Constant.READ_INPUT_REGISTER;
	}

	@Override
	public byte[] encodeMsg() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void decodeMsg(byte[] message) {
		// TODO Auto-generated method stub
		IoBuffer buf = IoBuffer.allocate(message.length).setAutoExpand(true);
		buf.put(message);
		buf.flip();
		
		slaveId = buf.get();
		code = buf.get();
		dataLen = buf.get();
		data = new short[dataLen/2];
		
		for(int i = 0;i < dataLen/2;i++){
			data[i] = buf.getShort();
		}
		if(buf.remaining() >= 2)
			crc16 = buf.getShort();
	}
	
	@Override
	public String toString() {
		
		StringBuffer dataBuf = new StringBuffer("");
		for(int i = 0;i < dataLen/2;i++){
			//buf.getShort(data[i]);
			dataBuf.append(",data["+i+"]=")
			.append(ByteUtil.shortToHexString(data[i]));
			
		}
		
		return "ReadInputRegisterRespMessage ["
				+ "slaveId = " + slaveId
				+ ",code =" + code
				+ ",dataLen =" + dataLen
				+ ",data =" + dataBuf.toString()
				+"]";
	}
}
