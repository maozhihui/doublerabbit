package com.comba.server.extensions.core.filter;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 设备属性过滤器配置
 * @author maozhihui
 * @Description:
 * @create 2018/3/6 20:15
 **/
@Data
@AllArgsConstructor
public class AttributeFilterConfiguration {
    private String name;
    private String did;
    private String expression;
}
