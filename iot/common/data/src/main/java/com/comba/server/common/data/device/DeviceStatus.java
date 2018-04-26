package com.comba.server.common.data.device;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DeviceStatus 设备状态
 *
 * @author maozhihui
 * @create 2017-10-12 12:43
 **/
@Data
public class DeviceStatus {

    private final String did;
    private final String event;
    private final int status;
    private final long ts;

    public DeviceStatus(String did,int status){
        this.did = did;
        this.event = "status";
        this.status = status;
        this.ts = System.currentTimeMillis();
    }
}
