package com.comba.server.dao.model.device;

import java.io.Serializable;

/**
 * 联合主键类
 */
public class RuleDeviceEntityPK implements Serializable {

    private static final long serialVersionUID = 4673623850611794536L;

    protected String devId;

    protected String ruleId;

    public String getDeviceId() {
        return devId;
    }

    public void setDeviceId(String devId) {
        this.devId = devId;
    }

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public RuleDeviceEntityPK(){
        super();
    }

    public RuleDeviceEntityPK(String devId, String ruleId) {
        this.devId = devId;
        this.ruleId = ruleId;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof RuleDeviceEntityPK) {
            RuleDeviceEntityPK pk = (RuleDeviceEntityPK) o;
            if (pk.getRuleId().equals(this.ruleId) && pk.getDeviceId().equals(devId)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.getRuleId().hashCode();
    }
}
