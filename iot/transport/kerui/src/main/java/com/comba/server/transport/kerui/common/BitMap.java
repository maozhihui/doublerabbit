package com.comba.server.transport.kerui.common;

import lombok.Data;

@Data
public class BitMap {
	final public static int shift = 3;
	final public static byte mask = 0x07;
	
	byte[] bitMap;
	
	public BitMap(byte[] bitMap){
		this.bitMap = bitMap;
	}
	
	public void setBit(int i){
		bitMap[i >> shift] |= 1 << (i & mask);
	}
	
	public int getBit(int i){
		int value = bitMap[i >> shift] & (1 << (i & mask));
		if(value == 0 )
			return value;
		else
			return 1;
	}
	
	public int  clearBitMap(int i){
		return bitMap[i >> shift] & ~(1 << (i & mask));
	}
}
