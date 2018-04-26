package com.comba.server.common.data.rest;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author maozhihui
 * @Description:
 * @create 2017/11/27 18:36
 **/
@Data
@AllArgsConstructor
public class DeviceRest {

    // 设备ID
    private String id;
    // 设备名称
    private String name;
    // 设备硬件标识
    private String hardIdentity;
    // 设备类型编码
    private String typeCode;
    // 设备模板ID
    private String templateId;
    // 设备SN
    private String sn;
    // 是否为网关
    private boolean isGateway;
    // 位置信息
    private String position;
    // 设备描述
    private String description;
}
