package com.comba.server.common.data.device;

import lombok.Data;

/**
 * 遥测数据导出实体
 *
 */
@Data
public class TelemetryDTO {

    /**
     * 上报数据量
     */
    private String deviceNum;
    /**
     * 上报日期
     */
    private String uploadTime;
}
