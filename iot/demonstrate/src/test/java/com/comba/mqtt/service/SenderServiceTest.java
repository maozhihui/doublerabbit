package com.comba.mqtt.service;

import static org.junit.Assert.*;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Random;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.JsonParser;

public class SenderServiceTest {

	private static final Random RANDOM = new Random();
	
	@Test
	public void test() {
		System.out.println("path="+this.getClass().getClassLoader().getResource("uploadData.txt").getPath());
		File file = new File(this.getClass().getClassLoader().getResource("uploadData.txt").getPath());
		if (file.exists()) {
			System.out.println("yes=====");
		}else {
			System.out.println("oh no=====");
		}
	}

	@Test
	public void test01(){
		System.out.println(System.currentTimeMillis());
	}
	
	@Test
	public void testDate() throws Exception{
		Date date = new Date(System.currentTimeMillis());
		System.out.println(System.currentTimeMillis());
		System.out.println(date);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date newDate = format.parse(date.toLocaleString());
		System.out.println(newDate);
	}
	
	@Test
	public void randomTest(){
		int i = 0;
		while (i < 30) {
			System.out.println((6+3*RANDOM.nextFloat()));
			i += 1;
		}
	}
	
	@Test
	public void jsonTest(){
		Map<String, Double> testMap = new HashMap<>();
		testMap.put("a", 1.2);
		testMap.put("b", 1.3);
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (Map.Entry<String, Double> entry : testMap.entrySet()) {
			sb.append("{");
		}
		sb.append("]");
		new JsonParser().parse(sb.toString()).toString();
	}
	
	@Test
	public void jsonTestMap(){
		Map<Double,String> testMap = new IdentityHashMap<>();
		testMap.put(1.2, "b");
		testMap.put(1.3, "a");
		testMap.put(1.6, "e");
		testMap.put(1.3, "f");
		System.out.println(new Gson().toJson(testMap));
		
	}
	
	@Test
	public void timeTest(){
		Date date = new Date(1505808000000L);
		System.out.println(date);
	}
	
	@Test
	public void arrayDupTest(){
		int[] array = {10,39,21,39,10};
		int temp = 0;
		for (int i = 0; i < array.length; i++) {
			for(int j = 0,num = 0; j < array.length; j++){
				if ((i == j))
					continue;
				if (array[i] != array[j]) {
					num++;
				}
				if (num == (array.length-1)) {
					temp = array[i];
				}
			}
		}
		System.out.println(temp);
	}
	
	@Test
	public void arrayDupTest1(){
		int[] array = {10,39,21,39,10};
		int temp=0;
		for (int i : array) {
			temp ^= i;
		}
		System.out.println(temp);
	}
	
	@Test
	public void test02(){
		long time = 1506477600000L;
		Date date = new Date(time);
		System.out.println(date);
	}
}
