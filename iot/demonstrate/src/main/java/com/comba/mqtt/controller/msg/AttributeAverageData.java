package com.comba.mqtt.controller.msg;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 属性按时间统计对象
 * @author maozhihui
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttributeAverageData {
	private String attrName;
	private Map<Long, String> averageStat;
}
