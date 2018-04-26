package com.comba.server.common.data.rest;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * CategoryRest 提供REST服务实体类
 *
 * @author maozhihui
 * @create 2017-11-27 09:22
 **/
@Data
@AllArgsConstructor
public class CategoryRest {

    private String id;
    private String parentId;
    private String code;
    private String name;
}
