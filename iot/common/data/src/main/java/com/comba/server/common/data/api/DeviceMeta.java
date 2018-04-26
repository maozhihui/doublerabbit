package com.comba.server.common.data.api;

import lombok.Data;

/**
 * 开放api的页面属性实体
 */
@Data
public class DeviceMeta {
    /**
     * 记录总数
     */
    private long total;
    /**
     * 当次查询最大返回值
     */
    private int limit;
    /**
     * 下一页，如果没有则返回null
     */
    private String next;

}