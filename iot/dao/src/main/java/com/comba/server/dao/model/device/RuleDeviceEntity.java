package com.comba.server.dao.model.device;

import com.comba.server.common.data.device.RuleDevice;
import com.comba.server.dao.model.ToData;

import javax.persistence.*;

/**
 * 规则设备映射表
 */
@Entity
@Table(name="ruleDevice")
@IdClass(RuleDeviceEntityPK.class)
public class RuleDeviceEntity implements ToData<RuleDevice> {

    @Id
    protected String devId;

    @Id
    protected String ruleId;



    public RuleDeviceEntity(){
        super();
    }

    public RuleDeviceEntity(RuleDevice ruleDevice){
        this.devId = ruleDevice.getDeviceId();
        this.ruleId = ruleDevice.getRuleId();
    }

    /**
     * This method convert domain model object to data transfer object.
     *
     * @return the dto object
     */
    @Override
    public RuleDevice toData() {
        return new RuleDevice(devId,ruleId);
    }
}
