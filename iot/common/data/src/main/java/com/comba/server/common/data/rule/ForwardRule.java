package com.comba.server.common.data.rule;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author maozhihui
 * @Description:
 * @create 2018/1/2 13:20
 **/
@Data
public class ForwardRule implements Serializable{

    private static final long serialVersionUID = 0L;

    private String id;
    private String tenantId;
    private String name;
    private String event;
    private String type;
    private String dst;
    private Date createTime;
    private Date updateTime;

    public ForwardRule(){}

    public ForwardRule(ForwardRule forwardRule){
        this.id = forwardRule.getId();
        this.tenantId = forwardRule.getTenantId();
        this.name = forwardRule.getName();
        this.event = forwardRule.getEvent();
        this.type = forwardRule.getType();
        this.dst = forwardRule.getDst();
        this.createTime = forwardRule.getCreateTime();
        this.updateTime = forwardRule.getUpdateTime();
    }
}
