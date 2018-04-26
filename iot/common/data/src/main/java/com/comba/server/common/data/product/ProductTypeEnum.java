package com.comba.server.common.data.product;

public enum ProductTypeEnum {

    COMMON_PRODUCT(1,"通用产品"),
    USER_PRODUCT(2,"用户自定义产品");

    private Integer type;
    private String name;

    public Integer getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    ProductTypeEnum(Integer type, String name){
        this.type = type;
        this.name = name;
    }
}
