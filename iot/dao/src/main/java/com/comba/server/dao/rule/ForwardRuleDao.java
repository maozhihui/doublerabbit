package com.comba.server.dao.rule;

import com.comba.server.common.data.rule.ForwardRule;
import com.comba.server.dao.model.ForwardRuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author maozhihui
 * @Description:
 * @create 2018/1/2 13:31
 **/
public interface ForwardRuleDao extends JpaRepository<ForwardRuleEntity,String> {

    List<ForwardRuleEntity> findByTenantId(String tenantId);

    ForwardRuleEntity findByTenantIdAndEvent(String tenantId,String event);
}
