package com.comba.server.common.msg.core;

import com.comba.server.common.msg.session.FromDeviceRequestMsg;
import com.comba.server.common.msg.session.MsgType;

import java.util.Date;

public class BasicAlarmUploadRequest extends BasicRequest implements FromDeviceRequestMsg {

    private String deviceId;

    private Integer notifyType;

    private Integer alarmLevel;

    private String alarmId;

    private String alarmContent;

    private Date startTime;

    public BasicAlarmUploadRequest(String deviceId, Integer notifyType, Integer alarmLevel, String alarmId, String alarmContent, Date startTime) {
        super(DEFAULT_REQUEST_ID);
        this.deviceId = deviceId;
        this.notifyType = notifyType;
        this.alarmLevel = alarmLevel;
        this.alarmId = alarmId;
        this.alarmContent = alarmContent;
        this.startTime = startTime;
    }

    public Integer getNotifyType() {
        return notifyType;
    }

    public Integer getAlarmLevel() {
        return alarmLevel;
    }

    public String getAlarmId() {
        return alarmId;
    }

    public String getAlarmContent() {
        return alarmContent;
    }

    public Date getStartTime() {
        return startTime;
    }

    public String getDeviceId() {
        return deviceId;
    }

    @Override
    public MsgType getMsgType() {
        return MsgType.ALARM_MESSAGE;
    }
}
