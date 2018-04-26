package com.comba.transport.pm25.message;

import org.apache.mina.core.buffer.IoBuffer;

import com.comba.transport.pm25.common.ByteUtil;
import com.comba.transport.pm25.common.Constant;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class PM25ReadDataRespMessage extends PM25AbstractMessage {
	//数据长度
	private byte dataLen;
	//气体浓度 ug/m3 (2 bytes)
	private short gasConcentration;
	//电压值(2 bytes)
	private short voltageValue;
	//量程系数
	private short rangeCoefficient;
	//量程电压下限
	private short rangeVoltageMinimum;
	//量程电压上限
	private short rangeVoltageMaximum;
	//量程范围下限
	private short rangeMinimum;
	//量程范围上限
	private short rangeMaximum;
	//显示值校准
	private short displayValueCalibration;
	//小数点数
	private short decimalPointNumber;
	//单位类型
	private short  unitType;
	
	public PM25ReadDataRespMessage() {
		funCode = Constant.READ_DATA_RESP;
	}

	@Override
	public void encodeMsg(IoBuffer bt) {
		// TODO Auto-generated method stub
		log.error("not suport encode method!");
		//bt.put((byte) 0x00);
	}

/*	@Override
	public void decode(byte[] body) {
		// TODO Auto-generated method stub
		IoBuffer buf=IoBuffer.allocate(body.length).setAutoExpand(true);
		buf.put(body);
		buf.flip();
		
		dataLen = buf.get();
		
		if(dataLen == 0x14){
			gasConcentration = buf.getShort();
			voltageValue = buf.getShort();
			rangeCoefficient = buf.getShort();
			rangeVoltageMinimum = buf.getShort();
			rangeVoltageMaximum = buf.getShort();
			rangeMinimum = buf.getShort();
			rangeMaximum = buf.getShort();
			displayValueCalibration = buf.getShort();
			decimalPointNumber = buf.getShort();
			unitType = buf.getShort();
		}
	}*/
	
	@Override
	public void decodeMsg(IoBuffer buf) {
		// TODO Auto-generated method stub
		dataLen = buf.get();
		
		if(dataLen == 0x14){
			gasConcentration = buf.getShort();
			voltageValue = buf.getShort();
			rangeCoefficient = buf.getShort();
			rangeVoltageMinimum = buf.getShort();
			rangeVoltageMaximum = buf.getShort();
			rangeMinimum = buf.getShort();
			rangeMaximum = buf.getShort();
			displayValueCalibration = buf.getShort();
			decimalPointNumber = buf.getShort();
			unitType = buf.getShort();
		}
		
		if(buf.remaining() >= crc16.length){
			buf.get(crc16);
		}
	}
	
	@Override
	public String toString() {

		return "PM25ReadDataRespMessage [deviceAddr=" + ByteUtil.byteToHexString(deviceAddr) 
				+ ", funCode=" + funCode 
				+ ",dataLen=" + dataLen 
				+ ",gasConcentration=" + gasConcentration
				+ ",voltageValue=" + voltageValue
				+ ",rangeCoefficient=" + rangeCoefficient
				+ ",rangeVoltageMinimum=" + rangeVoltageMinimum
				+ ",rangeVoltageMaximum=" + rangeVoltageMaximum
				+ ",rangeMinimum=" + rangeMinimum
				+ ",rangeMaximum=" + rangeMaximum
				+ ",displayValueCalibration=" + displayValueCalibration
				+ ",decimalPointNumber=" + decimalPointNumber
				+ ",unitType=" + unitType
				+ ",crc16=" + ByteUtil.bytesToHexString(crc16) +"]";
	}
}
