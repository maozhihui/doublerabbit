package com.comba.mqtt.dao.service;

import com.comba.mqtt.dao.model.DeviceDataEntity;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class DeviceDataServiceImplTest extends AbstractDaoTest{

	private static final SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final Random RANDOM = new Random();
	
	/**
	 * PM2.5 AQI空气质量指数
	 * 0-50 	优
	 * 51-100 	良
	 * 101-150	轻度污染
	 * 151-200	中度污染
	 * 201-300	重度污染
	 * >300		严重污染
	 * DELETE FROM device_data WHERE dev_id=28;
	 */
	@Test
	public void uploadPmDataTest() {
		String baseTimeStr = "2017-09-12 08:05:00";
		try {
			long baseDate = date_format.parse(baseTimeStr).getTime();
			long currentTime = System.currentTimeMillis();
            while(baseDate < currentTime){
				DeviceDataEntity dataEntity = new DeviceDataEntity();
				dataEntity.setDevId(28);
				dataEntity.setAttrName("pm2.5");
				dataEntity.setAttrValue(String.valueOf(50+RANDOM.nextInt(50)));
				dataEntity.setUpdateTime(new Date(baseDate));
				deviceDataService.save(dataEntity);
				baseDate += 2*60*60*1000L;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}

	/**
	 * PH 值6-9之间
	 * DELETE FROM device_data WHERE dev_id=29;
	 * DELETE FROM device_data WHERE dev_id=30;
	 */
	@Test
	public void uploadPHDataTest(){
		String baseTimeStr = "2017-09-12 09:06:10";
		try {
			long baseDate = date_format.parse(baseTimeStr).getTime();
			long currentTime = System.currentTimeMillis();
			while(baseDate < currentTime){
				DeviceDataEntity dataEntity = new DeviceDataEntity();
				dataEntity.setDevId(30);
				dataEntity.setAttrName("ph");
				dataEntity.setAttrValue(String.valueOf(6+3*RANDOM.nextFloat()));
				dataEntity.setUpdateTime(new Date(baseDate));
				deviceDataService.save(dataEntity);
				baseDate += 3*60*60*1000L;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
}
