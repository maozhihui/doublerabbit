package com.comba.server.common.data.user;

public enum  UserEnum {

    USER_TYPE_SUPER_ADMIN(1,"super管理员"),
    USER_TYPE_TENANT_ADMIN(2,"租户管理员"),
    USER_TYPE_NORMAL(3,"普通用户");

    private Integer type;
    private String info;

    public Integer getType() {
        return type;
    }

    public String getInfo() {
        return info;
    }

    UserEnum(Integer type, String info){
        this.type = type;
        this.info = info;
    }
}
