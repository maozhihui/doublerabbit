package com.comba.server.common.data.device;

import com.comba.server.common.data.SearchTextBased;
import com.comba.server.common.data.id.RuleDeviceId;

/**
 * 规则设备映射实体
 */
public class RuleDevice extends SearchTextBased<RuleDeviceId> {

    private String devId;

    private String ruleId;

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

    public RuleDevice(String devId,String ruleId){
        this.devId = devId;
        this.ruleId = ruleId;
    }

    public RuleDevice(){
        super();
    }

    @Override
    public String getSearchText() {
        return null;
    }
}
