package com.comba.server.dao.ruleDevice;

import com.comba.server.dao.model.device.RuleDeviceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 规则和设置的映射表
 */
public interface RuleDeviceDao extends JpaRepository<RuleDeviceEntity, String> {

}
