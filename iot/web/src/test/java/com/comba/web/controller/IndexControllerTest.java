package com.comba.web.controller;

import com.comba.server.common.data.User;
import com.comba.server.dao.device.DeviceService;
import com.comba.server.dao.rule.RuleJpaService;
import com.comba.server.dao.user.UserService;
import com.comba.web.security.SpringSecurityUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.validation.support.BindingAwareModelMap;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SpringSecurityUtils.class)
public class IndexControllerTest {

    @InjectMocks
    private IndexController indexController;

    @Mock
    private RuleJpaService ruleService;
    @Mock
    private UserService userJpaService;
    @Mock
    private DeviceService deviceService;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        PowerMockito.mockStatic(SpringSecurityUtils.class);
    }

    @Test
    public void index() throws Exception {
        when(SpringSecurityUtils.isSupperAdmin()).thenReturn(true);
        String ret = indexController.index(new BindingAwareModelMap());
        Assert.assertEquals("redirect:index_admin",ret);
    }

    @Test
    public void indexForTenant() throws Exception {
        when(SpringSecurityUtils.isTenantAdmin()).thenReturn(true);
        String ret = indexController.index(new BindingAwareModelMap());
        Assert.assertEquals("redirect:index_common",ret);
    }

    @Test
    public void indexForNormal() throws Exception {
        when(SpringSecurityUtils.isCommonUser()).thenReturn(true);
        String ret = indexController.index(new BindingAwareModelMap());
        Assert.assertEquals("redirect:index_normal",ret);
    }

    @Test
    public void index_common() throws Exception {
        when(SpringSecurityUtils.isTenantAdmin()).thenReturn(true);
        String ret = indexController.index_common(new BindingAwareModelMap());
        Assert.assertEquals("index_common",ret);
    }

    @Test
    public void index_normal() throws Exception {
        when(SpringSecurityUtils.isCommonUser()).thenReturn(true);
        String ret = indexController.index_normal(new BindingAwareModelMap());
        Assert.assertEquals("index_normal",ret);
    }

    @Test
    public void index_admin() throws Exception {
        when(SpringSecurityUtils.isSupperAdmin()).thenReturn(true);
        String ret = indexController.index_admin(new BindingAwareModelMap());
        Assert.assertEquals("index_admin",ret);
    }

    @Test
    public void mainContent_admin() throws Exception {
        String ret = indexController.mainContent_admin();
        Assert.assertEquals("admin/mainContent",ret);
    }

    @Test
    public void homeStatis_admin() throws Exception {
        Map<String,Object> devices = new HashMap<>();
        Long userNum = Long.valueOf("1");
        Long ruleNum = Long.valueOf("1");

        when(deviceService.deviceStatisByIsGateWay()).thenReturn(devices);
        when(userJpaService.countByStateFlag(User.STATE_FLAG_ACTIVE)).thenReturn(userNum);
        when(ruleService.countByTenantId(SpringSecurityUtils.getCurrentUserTenantId())).thenReturn(ruleNum);

        Map<String,Object> ret = indexController.homeStatis_admin();
        Assert.assertEquals(userNum,ret.get("userNum"));
        Assert.assertEquals(ruleNum,ret.get("ruleNum"));

    }

    @Test
    public void unauthor() throws Exception {
    }

    @Test
    public void reports() throws Exception {
    }

    @Test
    public void getActiveSession() throws Exception {
    }

}