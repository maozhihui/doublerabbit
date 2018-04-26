package com.comba.alarm.rule;

import com.alibaba.fastjson.JSON;
import com.comba.alarm.mq.ConnectionProducer;
import com.comba.alarm.mq.MqMessage;
import com.comba.alarm.util.Constant;
import com.comba.server.common.data.Device;
import com.comba.server.common.data.device.ActiveAlarm;
import com.comba.server.common.data.device.AlarmRule;
import com.comba.server.common.data.device.HistoryAlarm;
import com.comba.server.dao.device.DeviceService;
import com.comba.server.dao.device.alarm.ActiveAlarmService;
import com.comba.server.dao.device.alarm.AlarmRuleService;
import com.comba.server.dao.device.alarm.HistoryAlarmService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Slf4j
@Service("AnalyseAlarmRuleService")
public class AnalyseAlarmRuleServiceImpl implements AnalyseAlarmRuleService {

    @Autowired
    private AlarmRuleService alarmRuleService;

    @Autowired
    private ActiveAlarmService activeAlarmService;

    @Autowired
    private HistoryAlarmService historyAlarmService;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private ConnectionProducer sender;

    @Value("${activemq.topic.alarmExtension}")
    private String alarmExtension;

    private final static String EVENT = "alarm";


    /**
     * 分析设备上报数据，校验是否符合规则
     *
     * @param tenantId 租户id
     * @param did 设备id
     * @param attributes 属性名称列表
     */
    @Override
    @Async
    public void analyse(String tenantId, String did, Map<String ,String> attributes){

        //遍历属性列表
        for (Map.Entry<String,String> entry :attributes.entrySet()){
            //校验规则
            analyseDeviceData(tenantId,did,entry.getKey(),entry.getValue());
        }
    }

    /**
     * 分析设备上报数据，校验是否符合规则
     *
     * @param tenantId 租户id
     * @param did 设备id
     * @param attribute 属性名称
     * @param value 属性值
     */
    @Override
    public void analyseDeviceData(String tenantId, String did, String attribute, String value) {

        Device device = deviceService.getOne(did);
        if (device == null){
            return;
        }
        //校验数据,如果不符合条件则无操作
        AlarmRule rule = analyzeRule(device.getProductId(),attribute,value);
        if (rule == null){
            return;
        }

        //数据库入库 告警通知和告警恢复
        if (Constant.NOTIFY_RULE.equals(rule.getType())) {
            //保存活动告警，如已存在，则更新告警时间
            try {
                activeAlarmService.saveActiveAlarm(new ActiveAlarm(tenantId, did, rule,device.getProductId()));
            } catch (Exception e) {
                log.error("保存活动告警失败 ，did{} message{}",did,e.getMessage());
            }
        } else if (Constant.RECOVER_RULE.equals(rule.getType())) {
            ActiveAlarm alarm = activeAlarmService.isExitActiveAlarm(did,rule.getAlarmId());
            if (alarm != null){
                //存在告警信息，删除该条告警
                activeAlarmService.deleteActiveAlarm(did,rule.getAlarmId());
                historyAlarmService.save(new HistoryAlarm(alarm));
            }else{
                return;
            }
        } else {
            log.error("规则类型不存在");
            return;
        }

        //告警信息mq发送
        MqMessage message = new MqMessage(did,rule.getAlarmName(),rule.getType(),rule.getAlarmContent(),rule.getAlarmLevel(),new Date(),EVENT);
        String topicName = tenantId+alarmExtension;
        sender.send(topicName, JSON.toJSONString(message));

    }

    /**
     * 设备上报告警信息
     *
     * @param hardIdentity       设备
     * @param notifyType   告警类型
     * @param alarmLevel   告警等级
     * @param alarmId      告警id
     * @param alarmContent 告警内容
     * @param startTime    告警时间
     */
    @Override
    @Async
    public void receiveAlarm(String hardIdentity,Integer notifyType,Integer alarmLevel,String alarmId,String alarmContent,Date startTime) {
        //生成活动告警
        Optional<Device> device = deviceService.findDeviceByHardIdentity(hardIdentity);
        if (!device.isPresent()){
            log.error("该设备不存在，hardIdentity {} ",hardIdentity);
            return;
        }

        String devId = device.get().getDevId();
        if (Constant.NOTIFY_RULE.equals(notifyType)) {
            //保存活动告警，如已存在，则更新告警时间
            activeAlarmService.saveActiveAlarm(new ActiveAlarm(devId, device.get().getProductId(), alarmId, alarmContent, startTime));
        } else if (Constant.RECOVER_RULE.equals(notifyType)) {
            ActiveAlarm alarm = activeAlarmService.isExitActiveAlarm(devId, alarmId);
            if (alarm == null) {
                log.error("该设备活动告警不存在，hardIdentity {} ",hardIdentity);
                return;
            }
            //存在告警信息，删除该条告警,保存为历史告警
            activeAlarmService.deleteActiveAlarm(devId, alarmId);
            historyAlarmService.save(new HistoryAlarm(alarm));
        }

        //告警信息mq发送
        MqMessage message = new MqMessage(devId, alarmId, notifyType, alarmContent, alarmLevel, new Date(), EVENT);
        String topicName = device.get().getTenantId() + alarmExtension;
        sender.send(topicName, JSON.toJSONString(message));
    }


    private AlarmRule analyzeRule(String productId,String attribute, String value) {

        //查询出该租户的所有规则
        List<AlarmRule> list = alarmRuleService.findByProductId(productId);

        //校验是否符合规则条件
        for (AlarmRule rule :list){
            //校验属性名称和属性值
            if (attribute.equals(rule.getAttribute()) && operatorValue(rule.getRuleCondition(),value,rule.getValue())){
                return rule;
            }
        }

        return null;
    }


    private boolean operatorValue(String operator,String value,String ruleValue) {
        boolean result = false;

        if(operator.equals(">") && Double.parseDouble(value) > Double.parseDouble(ruleValue)){
            result =  true;
        }
        else if(operator.equals(">=") && Double.parseDouble(value) >= Double.parseDouble(ruleValue)){
            result =  true;
        }
        else if(operator.equals("<") && Double.parseDouble(value) < Double.parseDouble(ruleValue)){
            result =  true;
        }
        else if(operator.equals("<=") && Double.parseDouble(value)  <= Double.parseDouble(ruleValue)){
            result =  true;
        }
        else if(operator.equals("==") && Double.parseDouble(value)  == Double.parseDouble(ruleValue)){
            result =  true;
        }

        return result;
    }


}
