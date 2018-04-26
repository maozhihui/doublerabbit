package com.comba.server.common.data.api;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

/**
 *  开放接口设备属性
 */
@Data
@AllArgsConstructor
public class DeviceAttributes {
    /**
     * 上报时间
     */
    private Long ts;
    /**
     * 上报属性值
     */
    private Map<String,Object> attribute;

}
