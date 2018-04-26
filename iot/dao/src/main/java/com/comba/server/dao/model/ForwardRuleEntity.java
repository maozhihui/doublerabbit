package com.comba.server.dao.model;

import com.comba.server.common.data.rule.ForwardRule;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * @author maozhihui
 * @Description:
 * @create 2018/1/2 13:19
 **/
@Data
@Entity
@Table(name="forward_rule")
public class ForwardRuleEntity implements ToData<ForwardRule>{

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name="ID")
    private String id;

    @Column(name="TENANT_ID")
    private String tenantId;

    @Column(name="NAME")
    private String name;

    @Column(name="EVENT")
    private String event;

    @Column(name="TYPE")
    private String type;

    @Column(name="DST")
    private String dst;

    @Column(name="CREATE_TIME")
    private Date createTime;

    @Column(name="UPDATE_TIME")
    private Date updateTime;

    @Override
    public ForwardRule toData() {
        ForwardRule forwardRule = new ForwardRule();
        forwardRule.setId(id);
        forwardRule.setTenantId(tenantId);
        forwardRule.setName(name);
        forwardRule.setEvent(event);
        forwardRule.setType(type);
        forwardRule.setDst(dst);
        forwardRule.setCreateTime(createTime);
        forwardRule.setUpdateTime(updateTime);
        return forwardRule;
    }

    public ForwardRuleEntity(){}

    public ForwardRuleEntity(ForwardRule forwardRule){
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
