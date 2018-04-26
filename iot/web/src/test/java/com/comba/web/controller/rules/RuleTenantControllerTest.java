package com.comba.web.controller.rules;

import com.comba.server.actors.service.ActorService;
import com.comba.server.common.data.device.Rule;
import com.comba.server.common.data.web.page.Page;
import com.comba.server.dao.rule.RuleJpaService;
import com.comba.web.response.ResponseBean;
import com.comba.web.security.SpringSecurityUtils;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.validation.support.BindingAwareModelMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SpringSecurityUtils.class)
public class RuleTenantControllerTest {

    @InjectMocks
    private RuleTenantController ruleTenantController;

    @Mock
    private RuleJpaService ruleService;

    @Mock
    private ActorService actorService;

    private HttpServletRequest request = new MockHttpServletRequest();

    private HttpSession session = new MockHttpSession();

    private Rule rule = newRule();

    @Test
    public void list() throws Exception {
    }



    @Test
    public void datasByExport() throws Exception {
    }

    @Test
    public void ruleAdd() throws Exception {
        when(ruleService.getOne(rule.getRuleId())).thenReturn(rule);

        String ret = ruleTenantController.ruleAdd(rule.getRuleId(),new BindingAwareModelMap(),session);
        Assert.assertEquals("rules/ruleTenant/rule_edit",ret);
    }

    @Test
    public void ruleCheck() throws Exception {
        when(ruleService.getOne(rule.getRuleId())).thenReturn(rule);

        String ret = ruleTenantController.ruleCheck(rule.getRuleId(),new BindingAwareModelMap());
        Assert.assertEquals("rules/ruleTenant/rule_view",ret);
    }

    @Test
    public void addRule() throws Exception {
    }

    @Test
    public void updateRule() throws Exception {
    }

    @Test
    public void deleteGrule() throws Exception {
        String[] ids = "1".split(",");
        List<String> idList = Lists.newArrayList(ids);

        List<Rule> list = Lists.newArrayList();
        list.add(newRule());

        when(ruleService.findByIds(idList)).thenReturn(list);

        ResponseBean responseBean = ruleTenantController.deleteGrule(ids,request);
        assertEquals(ResponseBean.SUCCESS,responseBean.getFlag());
        Mockito.verify(ruleService).deleteByIds(ids);
    }

    @Test
    public void validateRuleName() throws Exception {
        when(ruleService.getOne(rule.getRuleId())).thenReturn(rule);

        ruleTenantController.validateRuleName(rule.getRuleId(),rule.getName());
    }

    @Test
    public void validateRuleName2() throws Exception {
        when(ruleService.countByName(rule.getName())).thenReturn(0);

        ruleTenantController.validateRuleName("",rule.getName());
    }

    @Test
    public void getAuditDataByPage() throws Exception {
        Page page = new Page();
        page.setPageNo(1);
        page.setPageSize(100);

        when(ruleService.pagedQuery(page.getPageNo(),100,rule,null)).thenReturn(page);

        Page ret = ruleTenantController.getAuditDataByPage(rule,page);
        Assert.assertNull(ret);

    }

    private Rule newRule(){
        Rule rule = new Rule();
        rule.setRuleId("1");
        rule.setName("test");
        return rule;
    }

}