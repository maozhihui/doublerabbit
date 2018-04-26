package com.comba.mqtt.message.comba;

import java.util.Date;
import java.util.Optional;

import com.comba.mqtt.dao.model.LatestDataEntity;
import com.comba.mqtt.dao.service.LatestDataService;
import com.comba.mqtt.data.KvEntry;
import com.comba.mqtt.message.DataProcessor;
import com.comba.mqtt.service.MqttServiceContext;

public class RuleProcessor implements DataProcessor {

	private static final String ALARM_LEVEL = "正常";
	private static final String ALARM_CAUSE = "状态正常";
	private static final String ALARM_LEVEL_EN = "Normal";
	private static final String ALARM_CAUSE_EN = "Normal condition";
	private static final String ALARM_LEVEL_WARN = "告警";
	private static final String ALARM_CAUSE_WARN = "状态异常";
	private static final String ALARM_LEVEL_WARN_EN = "Warn";
	private static final String ALARM_CAUSE_WARN_EN = "Exception condition";
	
	@Override
	public void onProcess(int deviceId, KvEntry kvEntry, MqttServiceContext context) {
		// TODO Auto-generated method stub
		/*LatestDataService latestDataService = context.getLatestDataService();
		Optional<LatestDataEntity> latestEntity = latestDataService.findByDevIdAndAttrName(deviceId, kvEntry.getKey());
		LatestDataEntity latestDataEntity = null;
		if (latestEntity.isPresent()) {
			latestDataEntity = latestEntity.get();
		}else {
			latestDataEntity = new LatestDataEntity();
		}
		latestDataEntity.setDevId(deviceId);
		latestDataEntity.setAttrName(kvEntry.getKey());
		latestDataEntity.setAttrValue(kvEntry.getValueAsString());
		latestDataEntity.setUpdateTime(new Date(entry.getKey()));
		if (config.getModelClass().contains("winext")) {
			judgeAlarm(latestDataEntity, kvEntry);
		}else {
			latestDataEntity.setAlarmLevel("正常");
			latestDataEntity.setAlarmLevelEn("Normal");
			latestDataEntity.setAlarmCause("状态正常");
			latestDataEntity.setAlarmCauseEn("Normal condition");;
		}
		latestDataService.save(latestDataEntity);*/
	}

}
