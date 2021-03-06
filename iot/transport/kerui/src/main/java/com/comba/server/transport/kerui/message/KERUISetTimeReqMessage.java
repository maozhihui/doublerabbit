package com.comba.server.transport.kerui.message;

import lombok.Data;

import org.apache.mina.core.buffer.IoBuffer;

import com.comba.server.transport.kerui.common.Constant;
import com.comba.server.transport.kerui.common.ByteUtil;
/*
 * 
 * 设置时间	
 * 
 * 数据占用7字节，前两个字节表示年，后面的字节分别表示月、日、时、分、秒。每整点时刻同步一次时间。
 */
@Data
public class KERUISetTimeReqMessage extends KERUIAbstractMessage {
	private short year;
	private byte month;
	private byte day;
	private byte hour;
	private byte minute;
	private byte second;
	
	public KERUISetTimeReqMessage() {
		cmdCode = Constant.SET_TIME_REQ;
		dataLen = 7;
		
		year = 0;
		month = 0;
		day = 0;
		hour = 0;
		minute = 0;
		second = 0;
	}

	@Override
	public void encodeData(IoBuffer bt) {
		// TODO Auto-generated method stub
		byte[] yearBytes = new byte[2];
		ByteUtil.shortToByte_LH(year, yearBytes, 0);
		
		data = new byte[dataLen];
		data[0] = yearBytes[0];
		data[1] = yearBytes[1];
		data[2] = month;
		data[3] = day;
		data[4] = hour;
		data[5] = minute;
		data[6] = second;
	}

	@Override
	public void decodeData(byte[] body) {
		// TODO Auto-generated method stub
		
		if(data == null)
			return;
		
		if(data.length >= dataLen){
			
			byte[] yearBytes = new byte[2];
			yearBytes[0] = data[1];
			yearBytes[1] = data[0];
			year = ByteUtil.bytesToShort(yearBytes);
			month = data[2];
			day = data[3];
			hour = data[4];
			minute = data[5];
			second = data[6];
		}
	}
	
	@Override
	public String toString() {
		
		return "KERUISetTimeReqMessage [packetHeader=" + ByteUtil.byteToHexString(getPacketHeader()) + ", version=" + getVersion() 
				+ ",destAddr=" + getDestAddr() + ",srcAddr=" + getSrcAddr() + ",cmdCode=" + cmdCode
				+ ",dataLen=" + dataLen + ",data=" + ByteUtil.bytesToHexString(data) + ",crc16=" + ByteUtil.bytesToHexString(ByteUtil.shortToBytes(getCrc16())) +"]"
				+ "[time:"+year+"-"+month+"-"+day+"-"+hour+"-"+minute+"-"+second+"]";
	}
	
}
