package com.comba.server.transport.feibit.bean;

import lombok.Data;

import com.comba.server.transport.feibit.common.ByteUtil;
import com.comba.server.transport.feibit.common.Constant;

@Data
public class FEIBITDeviceAttribute {
	
	//设备属性Attrib_ID:2 byte
	private String attributeId;
	
	//Data Type:1 byte
	private byte type;
	
	//Data Value:1 byte or 2 byte
	private String value;
	
	public FEIBITDeviceAttribute(){
	}
	
	public int getValueToInt(){
		int result = -1;
		byte[] bytes = ByteUtil.hexStringToBytes(value);
		
		if(type == Constant.UINT_8 ||
			type == Constant.INT_8 ||
			type == Constant.UINT_16 || 
			type == Constant.INT_16 ||
			type == Constant.UINT_24 || 
			type == Constant.INT_24){
			result = ByteUtil.bytesToInt(bytes);
		}
		return result;
	}
}
