package com.comba.server.transport.feibit.common;

/**
*
* Copyright 2006 Jason Zou.
*
*  Licensed under the Apache License, Version 2.0 (the "License");
*  you may not use this file except in compliance with the License.
*  You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software
*  distributed under the License is distributed on an "AS IS" BASIS,
*  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*  See the License for the specific language governing permissions and
*  limitations under the License.
*/

import org.apache.commons.codec.binary.Hex;
import org.apache.mina.core.buffer.IoBuffer;

/**
* @author Jason
* 
*         TODO To change the template for this generated type comment go to
*         Window - Preferences - Java - Code Style - Code Templates
*/
public class ByteUtil {
	// get 4 byte from byte[] and convert it to integer.
	public static int bytesToInt(byte[] bt, int idx) {
		int result = bt[idx] * 256 * 256 * 256 + bt[idx + 1] * 256 * 256
				+ bt[idx + 2] * 256 + bt[idx+3];
		return result;
	}
	
	public static int bytesToInt(byte[] bytes) {
		int result = 0;
		
		if(bytes.length == 1){
			int a = (bytes[0] & 0xFF);
			result = a ;
		}
		else if(bytes.length == 2){
			int a = (bytes[0] & 0xFF) << 8;
			int b = (bytes[1] & 0xFF);
			result = a | b;
		}
		else if(bytes.length == 3){
			int a = (bytes[0] & 0xFF) << 16;
			int b = (bytes[1] & 0xFF) << 8;
			int c = (bytes[2] & 0xFF);
			result = a | b | c;
		}
		else if(bytes.length == 4){
			int a = (bytes[0] & 0xFF) << 24;
			int b = (bytes[1] & 0xFF) << 16;
			int c = (bytes[2] & 0xFF) << 8;
			int d = (bytes[3] & 0xFF);
			result = a | b | c | d;
		}

		return result;
	}

	/**
	 * Create an array with 0 filled.
	 * 
	 * @param length
	 *            the length of the array returned.
	 * @return
	 */
	public static byte[] intArray(int length) {
		byte[] bt = new byte[length];
		for (int i = 0; i < length; i++) {
			bt[i] = 0;
		}
		return bt;
	}

	/**
	 * The ByteBuffer's initial position shou
	 * 
	 * @param bb
	 * @return
	 */
	public static String toHexForLog(IoBuffer bb) {

		int originPos = bb.position();
		bb.position(0);

		byte[] arr = new byte[bb.limit()];
		bb.get(arr);
		String str = toHexForLog(arr);

		bb.position(originPos);
		return str;

	}

	public static String toHexForLog(byte[] bt) {
		StringBuffer sb = new StringBuffer();

		String lSep = System.getProperty("line.separator");
		char[] hexChar = Hex.encodeHex(bt);
		for (int i = 0; i < (hexChar.length / 2); i++) {
			sb.append(hexChar[2 * i]);
			sb.append(hexChar[2 * i + 1]);
			sb.append(' ');

			if ((i + 1) % 16 == 0) {
				sb.append(lSep);
			}
		}
		return sb.toString().toUpperCase();
	}

	/**
	 * get a string's byte code. If the length is not enough, fill 0.
	 * 
	 * @param str
	 * @param len
	 * @return
	 */
	public static byte[] getByteFillSuffix(String str, int len) {
		byte[] arr = new byte[len];
		byte[] bt = str.getBytes();
		int realLen = bt.length > len ? len : bt.length;
		for (int i = 0; i < realLen; i++) {
			arr[i] = bt[i];
		}
		for (int j = bt.length; j < arr.length; j++) {
			arr[j] = 0;
		}
		return arr;
	}

	/**
	 * get a string's byte code. If the length is not enough, fill 0 at the
	 * beginning.
	 * 
	 * @param str
	 * @param len
	 * @return
	 */
	public static byte[] getByteFillPrefix(String str, int len) {
		byte[] arr = new byte[len];
		byte[] bt = str.getBytes();
		int fillLen = len - str.length();

		if (fillLen > 0) {
			for (int j = 0; j < fillLen; j++) {
				arr[j] = 0;
			}
		} else {
			fillLen = 0;
		}

		for (int i = 0; i < str.length(); i++) {
			arr[fillLen + i] = bt[i];
		}

		return arr;
	}

	/**
	 * Get Variable Length String's byte, end with 0x00;
	 * 
	 * @param str
	 * @return
	 */
	public static byte[] getCOctetBytes(String str) {
		byte[] bSrc = str.getBytes();
		byte[] bts = new byte[bSrc.length + 1];
		System.arraycopy(bSrc, 0, bts, 0, bSrc.length);
		bts[bSrc.length] = 0;
		return bts;
	}

	public static String decOctetString(byte[] bt) {
		int b = 0;
		int e = 0;

		// find the begin non 0 position;
		for (int i = 0; i < bt.length; i++) {
			if (bt[i] != 0) {
				b = i;
				break;
			}
		}

		// find the end non 0 position;
		for (int i = bt.length - 1; i > 0; i--) {
			if (bt[i] != 0) {
				e = i;
				break;
			}
		}

		return new String(bt, b, e - b + 1);

	}

	public static byte hexStringTobyte(String str) {
		if (str.startsWith("0x")) {
			str = str.substring(2, str.length());
		}

		return (byte) Integer.parseInt(str, 16);
	}

	public static String byteToString(byte[] bs) {
		if (bs == null)
			return null;
		String strTemp = null;
		StringBuffer sb = new StringBuffer();
		for (byte each : bs) {
			strTemp = String.format("0x%02X,", each);
			sb.append(strTemp);
		}
		return sb.toString();
	}
	
	// 字节序转换 低位-高位
	public static void shortToByte_LH(short shortVal, byte[] b, int offset) {  
		b[0 + offset] = (byte) (shortVal & 0xff);  
		b[1 + offset] = (byte) (shortVal >> 8 & 0xff);  
	}
	
	public static short byteToShort_HL(byte[] b, int offset)
	{
		short result;
		result = (short)((((b[offset + 1]) << 8) & 0xff00 | b[offset] & 0x00ff));
		return result;
	}
	
	public static void intToByte_LH(int intVal, byte[] b, int offset) {  
		b[0 + offset] = (byte) (intVal & 0xff);  
		b[1 + offset] = (byte) (intVal >> 8 & 0xff);  
		b[2 + offset] = (byte) (intVal >> 16 & 0xff);  
		b[3 + offset] = (byte) (intVal >> 24 & 0xff);  
	}   
	
	public static int byteToInt_HL(byte[] b, int offset)
	{
		int result;
		result = (((b[3 + offset] & 0x00ff) << 24) & 0xff000000)
				| (((b[2 + offset] & 0x00ff) << 16) & 0x00ff0000)
				| (((b[1 + offset] & 0x00ff) << 8) & 0x0000ff00)
				| ((b[0 + offset] & 0x00ff));
		return result;
	}
	
	public static byte[] intToByte(int i) {  
		byte[] bt = new byte[4];
		bt[0] = (byte) (i & 0xff);  
		bt[1] = (byte) (i >> 8 & 0xff);  
		bt[2] = (byte) (i >> 16 & 0xff);  
		bt[3] = (byte) (i >> 24 & 0xff);  
		return bt;
	}  
/*	public static int bytesToInt(byte[] bytes){
		int num = bytes[0] & 0xFF;
		num |= ((bytes[1] << 8) & 0xFF00);
		num |= ((bytes[2] << 16) & 0xFF0000);
		num |= ((bytes[3] << 24) & 0xFF000000);
		return num;
	}*/
	   public static String byteToHexString(byte src){   
	       StringBuilder stringBuilder = new StringBuilder("");   

           int v = src & 0xFF;   
           String hv = Integer.toHexString(v);   
           if (hv.length() < 2) {   
               stringBuilder.append(0);   
           }   
           stringBuilder.append(hv);   
        
	       return stringBuilder.toString();   
	   }
	   

	/*
    * Convert byte[] to hex string.这里我们可以将byte转换成int，然后利用Integer.toHexString(int)来转换成16进制字符串。   
    * @param src byte[] data   
    * @return hex string   
    */      
   public static String bytesToHexString(byte[] src){   
       StringBuilder stringBuilder = new StringBuilder("");   
       if (src == null || src.length <= 0) {   
           return null;   
       }   
       for (int i = 0; i < src.length; i++) {   
           int v = src[i] & 0xFF;   
           String hv = Integer.toHexString(v);   
           if (hv.length() < 2) {   
               stringBuilder.append(0);   
           }   
           stringBuilder.append(hv);   
       }   
       return stringBuilder.toString();   
   }   
   /**  
    * Convert hex string to byte[]  
    * @param hexString the hex string  
    * @return byte[]  
    */  
   public static byte[] hexStringToBytes(String hexString) {   
       if (hexString == null || hexString.equals("")) {   
           return null;   
       }   
       hexString = hexString.toUpperCase();   
       int length = hexString.length() / 2;   
       char[] hexChars = hexString.toCharArray();   
       byte[] d = new byte[length];   
       for (int i = 0; i < length; i++) {   
           int pos = i * 2;   
           d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));   
       }   
       return d;   
   }   
   /**  
    * Convert char to byte  
    * @param c char  
    * @return byte  
    */  
    private static byte charToByte(char c) {   
       return (byte) "0123456789ABCDEF".indexOf(c);   
   }  

    //将指定byte数组以16进制的形式打印到控制台   
    public static void printHexString( byte[] b) {     
       for (int i = 0; i < b.length; i++) {    
         String hex = Integer.toHexString(b[i] & 0xFF);    
         if (hex.length() == 1) {    
           hex = '0' + hex;    
         }    
         System.out.print(hex.toUpperCase() );    
       }    
      
    }  
    
    //正常(大端模式)，高位在前，低位在后
    public static byte[] shortToBytes(short n){
    	byte[] b = new byte[2];
    	b[1] = (byte)(n & 0xFF);
    	b[0] = (byte)((n >> 8) & 0xFF);
    	return b;   	
    }

    public static short bytesToShort(byte[] b){
    	return (short)(b[1] & 0xFF | (b[0] & 0xFF)<< 8);
    }
    
    //低位高位调换位置
    public static short shortConvert_LH(short n){
    	byte[] b = shortToBytes(n);
    	return (short)(b[0] & 0xFF | (b[1] & 0xFF)<< 8);  	
    }
    
    //short转16进制字符串
    public static String shortToHexString(short n){
    	byte[] bytes = shortToBytes(n);
    	return bytesToHexString(bytes);
    }
    
    public static int byteToInt(byte b){
    	return (int)((b & 0xFF));
    }
}
