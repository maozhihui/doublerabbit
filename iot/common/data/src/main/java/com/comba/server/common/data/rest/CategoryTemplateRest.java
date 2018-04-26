package com.comba.server.common.data.rest;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author maozhihui
 * @Description:类型参数模板实体类
 * @create 2017/11/27 10:03
 **/
@Data
public class CategoryTemplateRest {
    // 模板ID
    private String id;
    // 类别编码
    private String typeCode;
    // 模板描述
    private String description;
    // 模板参数列表
    private List<TemplateParam> params;

    public CategoryTemplateRest(String id,String typeCode,String description){
        this.id = id;
        this.typeCode = typeCode;
        this.description = description;
        params = new ArrayList<>();
    }

    @Data
    @AllArgsConstructor
    public static class TemplateParam{
        // 参数ID
        private String id;
        // 参数名称
        private String name;
        // 参数标识
        private String identity;
        // 参数值类型
        private String valueType;
        // 参数描述
        private String description;
        //参数类型
        private Integer type;
    }
}
