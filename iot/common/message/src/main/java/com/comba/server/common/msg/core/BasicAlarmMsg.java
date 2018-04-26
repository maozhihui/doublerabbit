package com.comba.server.common.msg.core;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * BasicAlarmMsg 告警信息
 *
 * @author maozhihui
 * @create 2017-10-12 19:58
 **/
@Data
@AllArgsConstructor
public class BasicAlarmMsg implements Serializable {

    private String devId;
    private String type;
    private int status;
}
