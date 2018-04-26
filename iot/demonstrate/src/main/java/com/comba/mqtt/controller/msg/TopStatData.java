package com.comba.mqtt.controller.msg;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopStatData {

	private String attrName;
	private List<GraphData> currentValue;
	private List<GraphData> avgValue;
}
