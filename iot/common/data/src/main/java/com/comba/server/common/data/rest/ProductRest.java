package com.comba.server.common.data.rest;

import lombok.Data;

/**
 * Product 传输实体类
 *
 * @author maozhihui
 * @create 2017-11-22 16:22
 **/
@Data
public class ProductRest {

    // 产品ID
    private String id;
    // 产品名称
    private String name;
    // 类别编码
    private String typeCode;
    // 产品类型
    private String mode;
    // 接入类型
    private String accessType;
    // 产品描述
    private String description;
    // 产品创建时间
    private String createTime;

}
