package com.comba.server.dao.rule;

import com.comba.server.common.data.rule.ForwardRule;
import com.comba.server.dao.model.ForwardRuleEntity;
import com.comba.server.dao.util.DaoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author maozhihui
 * @Description:
 * @create 2018/1/2 13:34
 **/
@Service
public class ForwardRuleServiceImpl implements ForwardRuleService {

    @Autowired
    private ForwardRuleDao dao;

    @Override
    public List<ForwardRule> findAll() {
        return DaoUtil.convertDataList(dao.findAll());
    }

    @Override
    public ForwardRule save(ForwardRule forwardRule) {
        ForwardRuleEntity entity = dao.save(new ForwardRuleEntity(forwardRule));
        return entity.toData();
    }

    @Override
    public void deleteById(String id) {
        dao.delete(id);
    }

    @Override
    public Optional<ForwardRule> findById(String id){
        ForwardRuleEntity entity = dao.findOne(id);
        if (entity == null){
            return Optional.ofNullable(null);
        }else {
            return Optional.of(entity.toData());
        }
    }

    @Override
    public List<ForwardRule> findByTenantId(String tenantId){
        List<ForwardRuleEntity> entities = dao.findByTenantId(tenantId);
        return DaoUtil.convertDataList(entities);
    }

    @Override
    public Optional<ForwardRule> findByTenantIdAndEvent(String tenantId, String event) {
        ForwardRuleEntity entity = dao.findByTenantIdAndEvent(tenantId,event);
        if (entity == null){
            return Optional.ofNullable(null);
        }else {
            return Optional.of(entity.toData());
        }
    }
}
