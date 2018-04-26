package com.comba.license;

import java.io.StringReader;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.LineIterator;

public class ContentDecoder {
	private static final String CONTENT_LINE_PREFIX = "content";
	
	/**
	 * 获取license的content字段内容,并对content内容解析Hex解码
	 * @param 
	 * @throws DecoderException 
	 */
	public String getContent(final byte[] decryptedData) throws Exception{
		LineIterator iterator = new LineIterator(new StringReader(new String(decryptedData)));
		while(iterator.hasNext()) {
			String line = iterator.nextLine();
			if(line.startsWith(CONTENT_LINE_PREFIX)) {
				String content = line.split("=")[1];
				byte[] bytes = Hex.decodeHex(content.toCharArray());
				return new String(bytes);
			}
		}
		throw new LicenseException(LicenseStatus.INVALID_LICENSE,"License content does not exists!");
	}
}
