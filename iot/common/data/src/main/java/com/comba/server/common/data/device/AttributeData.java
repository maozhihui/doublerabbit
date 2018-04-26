package com.comba.server.common.data.device;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * AttributeData
 *
 * @author maozhihui
 * @create 2017-10-13 10:34
 **/
@Data
@AllArgsConstructor
public class AttributeData {
    private String name;
    private String currentValue;
    private long updateTime;
}
