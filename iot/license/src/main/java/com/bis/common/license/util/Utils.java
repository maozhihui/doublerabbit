package com.bis.common.license.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.SystemUtils;

@Slf4j
public abstract class Utils {
	private static volatile String serial = null;
	
	private Utils() {
	}
	
	public static String getLinuxSerialNumber() throws IOException {
		InputStream input = null;
		try {
			Process process = Runtime.getRuntime().exec("dmidecode -t system");
			input = process.getInputStream();
			
			List<String> lines = IOUtils.readLines(input);
			for(String line : lines) {
				if(line.contains("Serial Number")) {
					serial = line.split(":")[1].trim();
					break;
				}
			}
		} catch(IOException e) {
			log.error("Get system serial number failure!");
			throw new IOException("Get system serial number failure!", e);
		} finally {
			IOUtils.closeQuietly(input);
		}
		return serial;
	}
	
	@SuppressWarnings("resource")
	public static String getWindowsSerialNumber() throws IOException {
		// TODO Auto-generated method stub
		Process process = Runtime.getRuntime().exec("wmic bios get SerialNumber");
		process.getOutputStream().close();
		Scanner sc = new Scanner(process.getInputStream());
		sc.next();
		serial = sc.next();
		return serial;
	}
	
	public static String getSerialNumber() throws IOException{
		if(SystemUtils.IS_OS_LINUX) {
			getLinuxSerialNumber();
		} else if(SystemUtils.IS_OS_WINDOWS) {
			getWindowsSerialNumber();
		}
		return serial;
	}
}
