package com.comba.server.dao.rule;

import com.comba.server.common.data.rule.ForwardRule;

import java.util.List;
import java.util.Optional;

/**
 * @author maozhihui
 * @Description:
 * @create 2018/1/2 13:33
 **/
public interface ForwardRuleService {

    List<ForwardRule> findAll();

    Optional<ForwardRule> findById(String id);

    List<ForwardRule> findByTenantId(String tenantId);

    Optional<ForwardRule> findByTenantIdAndEvent(String tenantId,String event);

    ForwardRule save(ForwardRule forwardRule);

    void deleteById(String id);
}
