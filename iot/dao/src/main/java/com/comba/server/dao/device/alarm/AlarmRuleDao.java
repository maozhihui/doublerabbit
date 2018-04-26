package com.comba.server.dao.device.alarm;

import com.comba.server.common.data.device.AlarmRule;
import com.comba.server.dao.model.device.AlarmRuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


/**
 * 告警规则 数据库实现类
 *
 * @作者 sujinxian
 * @创建时间 2018-02-28 15:55:17
 */
public interface AlarmRuleDao extends JpaRepository<AlarmRuleEntity, String>{

    /**
     * 查询该租户下面的所有规则
     *
     * @param tenantId
     * @return
     */
    List<AlarmRuleEntity> findByProductId(String tenantId);

}
