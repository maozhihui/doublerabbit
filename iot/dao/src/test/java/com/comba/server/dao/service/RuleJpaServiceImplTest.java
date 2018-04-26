package com.comba.server.dao.service;

import com.comba.server.common.data.device.Rule;
import com.comba.server.common.data.id.TenantId;
import com.comba.server.common.data.web.page.Page;
import com.comba.server.dao.AbstractDaoTest;
import com.comba.server.dao.model.device.RuleEntity;
import com.comba.server.dao.rule.RuleJpaDao;
import com.comba.server.dao.rule.RuleJpaService;
import com.google.common.collect.Lists;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class RuleJpaServiceImplTest extends AbstractDaoTest{

    @Autowired
    private RuleJpaService ruleJpaService;

    @Autowired
    private RuleJpaDao ruleDeviceDao;

    private Rule rule = newRule();

    @Before
    public void setUp() throws Exception {
        RuleEntity entity = new RuleEntity(rule);
        ruleDeviceDao.save(entity);
        rule.setRuleId(entity.getRuleId());
    }

    @After
    public void tearDown() throws Exception {
        ruleJpaService.delete(rule.getRuleId());
    }

    @Test
    public void findAll() throws Exception {
        List<Rule> list = ruleJpaService.findAll();
        assertTrue(list.size() >= 1);
    }

    @Test
    public void pagedQuery() throws Exception {
        List<String> orderBy = Lists.newArrayList("updateTime");
        Page page = ruleJpaService.pagedQuery(1,100,rule,orderBy);
        assertTrue(page.getResultSize() >= 0);
    }

    @Test
    public void getDataByPage() throws Exception {
        Page page = ruleJpaService.getDataByPage(rule,1,100);
        assertTrue(page.getResultSize() >= 0);
    }

    @Test
    public void deleteByIds() throws Exception {
    }

    @Test
    public void findByIds() throws Exception {
        List<String> ids = Lists.newArrayList(rule.getRuleId());
        List<Rule> list = ruleJpaService.findByIds(ids);
        assertTrue(list.size() >= 1);
    }

    @Test
    public void findSystemRules() throws Exception {
        List<Rule> list = ruleJpaService.findSystemRules();
        assertTrue(list.size() >= 0);
    }

    @Test
    public void findTenantRules() throws Exception {
        List<Rule> list = ruleJpaService.findTenantRules(TenantId.fromString2(rule.getTenantId()));
        assertTrue(list.size() >= 0);
    }

    @Test
    public void findRuleById() throws Exception {
    }

    @Test
    public void findByTenantId() throws Exception {
        List<Rule> list = ruleJpaService.findByTenantId(rule.getTenantId());
        assertTrue(list.size() >= 1);
    }

    @Test
    public void countByTenantId() throws Exception {
        Long count = ruleJpaService.countByTenantId(rule.getTenantId());
        assertTrue(count >= 1);
    }

    @Test
    public void deleteByTenantId() throws Exception {
    }

    @Test
    public void countByTenantIdAndProductId() throws Exception {
        Long count = ruleJpaService.countByTenantIdAndProductId(rule.getTenantId(),rule.getProductId());
        assertTrue(count >= 1);
    }

    @Test
    public void countByName() throws Exception {
        int count = ruleJpaService.countByName(rule.getName());
        assertTrue(count >= 1);
    }

    private Rule newRule(){
        Rule rule = new Rule();
        rule.setProductId("1");
        rule.setTenantId("ffffffffffffffffffffffffffffffff");
        rule.setAction("action");
        rule.setFilters("filters");
        rule.setName("test");
        rule.setProcessor("process");
        return rule;
    }

}