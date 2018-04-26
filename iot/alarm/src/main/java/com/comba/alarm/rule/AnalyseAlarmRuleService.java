package com.comba.alarm.rule;

import java.util.Date;
import java.util.Map;

public interface AnalyseAlarmRuleService {

    /**
     * 分析设备上报数据，校验是否符合规则
     *
     * @param tenantId 租户id
     * @param did 设备id
     * @param attribute 属性名称
     * @param value 属性值
     */
    void analyseDeviceData(String tenantId, String did,String attribute,String value);

    /**
     * 分析设备上报数据，校验是否符合规则
     *
     * @param tenantId 租户id
     * @param did 设备id
     * @param attributes 属性名称
     */
    void analyse(String tenantId, String did, Map<String ,String> attributes);

    /**
     * 设备上报告警信息
     *
     * @param deviceId       设备
     * @param notifyType   告警类型
     * @param alarmLevel   告警等级
     * @param alarmId      告警id
     * @param alarmContent 告警内容
     * @param startTime    告警时间
     */
    void receiveAlarm(String deviceId, Integer notifyType, Integer alarmLevel, String alarmId, String alarmContent, Date startTime);

    }
