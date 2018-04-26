package com.comba.mqtt.service;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.junit.Test;

import com.comba.mqtt.message.MqttMessageConverter;

public class PushCallbackTest {

	@Test
	public void test() {
		String timeStr = "2017-09-27T02:14:55.459807Z";
		long time = MqttMessageConverter.parseTimeData(timeStr);
		System.out.println(new Date(time));
	}
	
	@Test
	public void test02(){
		//String timeStr = "2017-09-27T02:14:55.459807Z";
		String timeStr = "2017-09-27T02:14:55Z";
		DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT;
		LocalDate localDate = LocalDate.parse(timeStr, formatter);
		System.out.println(localDate);
	}

	@Test
	public void test03(){
		String timeStr = "2017-09-27T02:14:55Z";
		timeStr = timeStr.replace("Z", "");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
		try {
			Date date = format.parse(timeStr);
			System.out.println(date);
		} catch (ParseException e) {
		}
	}
}
