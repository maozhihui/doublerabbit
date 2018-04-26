package com.comba.mqtt.dao.service;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

import com.comba.mqtt.controller.msg.DeviceData;
import com.comba.mqtt.controller.msg.GraphData;
import com.comba.mqtt.controller.msg.StatusInfo;
import com.comba.mqtt.controller.msg.TopStatData;
import com.google.gson.Gson;

public class LatestDataServiceImplTest extends AbstractDaoTest{

	private static final SimpleDateFormat DATA_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Test
	public void test() {
		List<GraphData> res = latestDataService.getTopStat("temperture", 5);
		/*for (Map.Entry<String, Double> entry : res.entrySet()) {
			System.out.println("key="+entry.getKey()+",value="+entry.getValue());
		}
		List<Map.Entry<String, Double>> list = new ArrayList<Map.Entry<String, Double>>(res.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {  
		    @Override  
		    public int compare(Entry<String, Double> o1, Entry<String, Double> o2) {  
		        // return 0;  // 降序  
		        return o2.getValue().compareTo(o1.getValue()); // 降序  
		        //return o1.getValue().compareTo(o2.getValue()); // 升序  
		    }  
		});
		for (Map.Entry<String, Double> mapping : list) {  
		    System.out.println(mapping.getKey() + ":" + mapping.getValue());  
		} */
		System.out.println(new Gson().toJson(res));
	}
	
	@Test
	public void testTopStat(){
		String attrName = "temperture";
		int limit = 5;
		Date startTime = new Date(1505750400000L);
		Date endTime = new Date(System.currentTimeMillis());
		TopStatData data = latestDataService.getTopStat(attrName, startTime, endTime, limit);
		System.out.println("=============");
	}
	
	@Test
	public void testRealData(){
		int devId = 20;
		DeviceData data = latestDataService.queryByDevId(devId);
		System.out.println(new Gson().toJson(data));
	}
	
	@Test
	public void testGetStatusInfoByEn(){
		List<StatusInfo> statusInfos = latestDataService.findAllByEn();
		for (StatusInfo statusInfo : statusInfos) {
			System.out.println(statusInfo.getDevName()+","+statusInfo.getAlarmLevel()+","+statusInfo.getAlarmCause());
		}
	}

	@Test
	public void testGetStatusInfoByCn(){
		List<StatusInfo> statusInfos = latestDataService.findAll();
		for (StatusInfo statusInfo : statusInfos) {
			System.out.println(statusInfo.getDevName()+","+statusInfo.getAlarmLevel()+","+statusInfo.getAlarmCause());
		}
	}
	
	@Test
	public void testGetTempertureTopDataByEn() throws Exception{
		String timeStr = "2017-10-25 00:00:00";
		Date startTime = DATA_FORMAT.parse(timeStr);
		TopStatData topStatData = latestDataService.getTopStatByEn("temperture", startTime, new Date(System.currentTimeMillis()), 5);
		for (GraphData graphData:topStatData.getCurrentValue()) {
			System.out.println("当前值 key="+graphData.getKey()+",value="+graphData.getValue());
		}
		for (GraphData graphData:topStatData.getAvgValue()) {
			System.out.println("平均值 key="+graphData.getKey()+",value="+graphData.getValue());
		}
	}
	
	@Test
	public void testGetTempertureTopDataByCn() throws Exception{
		String timeStr = "2017-10-25 00:00:00";
		Date startTime = DATA_FORMAT.parse(timeStr);
		TopStatData topStatData = latestDataService.getTopStat("temperture", startTime, new Date(System.currentTimeMillis()), 5);
		for (GraphData graphData:topStatData.getCurrentValue()) {
			System.out.println("当前值 key="+graphData.getKey()+",value="+graphData.getValue());
		}
		for (GraphData graphData:topStatData.getAvgValue()) {
			System.out.println("平均值 key="+graphData.getKey()+",value="+graphData.getValue());
		}
	}
}
