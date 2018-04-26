package com.comba.mantun.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author maozhihui
 * @date 2017年12月15日 下午1:41:23
 */
@Slf4j
public class MD5Util {

	private static char[] hc = "0123456789abcdef".toCharArray();
	
	public static String MD5(String param) {
		MessageDigest md;
		String res = "";
		try {
			md = MessageDigest.getInstance("MD5");
			md.reset();
			md.update(param.getBytes("utf-8"));
			byte[] d = md.digest();
			StringBuilder r = new StringBuilder(d.length * 2);
			for (byte b : d) {
				r.append(hc[(b >> 4) & 0xF]);
				r.append(hc[(b & 0xF)]);
			}
			res = r.toString();
		} catch (NoSuchAlgorithmException e) {
			log.error("param [{}] md5 failed,cause [{}]",param,e.getMessage());
		} catch (UnsupportedEncodingException e) {
			log.error("param [{}] convert to utf8 format failed,cause [{}]",param,e.getMessage());
		}
		return res;
	}
}
