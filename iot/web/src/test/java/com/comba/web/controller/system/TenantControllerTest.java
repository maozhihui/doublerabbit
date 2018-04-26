package com.comba.web.controller.system;

import com.comba.server.common.data.Tenant;
import com.comba.server.common.data.User;
import com.comba.server.common.data.web.page.Page;
import com.comba.server.dao.tenant.TenantService;
import com.comba.web.response.ResponseBean;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.validation.support.BindingAwareModelMap;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
public class TenantControllerTest {

    @InjectMocks
    private TenantController tenantController;

    @Mock
    private TenantService tenantJpaService;

    @Mock
    private HttpServletRequest request;

    @Test
    public void list() throws Exception {
    }

    @Test
    public void getAuditDataByPage() throws Exception {
        Page page = new Page();
        Tenant tenant = new Tenant();

        page.setPageNo(1);
        page.setPageSize(100);

        when(tenantJpaService.pagedQuery(1,100,tenant,null)).thenReturn(page);
        Page ret = tenantController.getAuditDataByPage(tenant,page);
        Assert.assertNull(ret);
    }

    @Test
    public void datasByExport() throws Exception {
    }

    @Test
    public void toTentEdit() throws Exception {
        String tenantId= "1";

        String ret = tenantController.toTentEdit(tenantId,new BindingAwareModelMap());
        Assert.assertEquals("system/tenant/tenant_edit",ret);
    }

    @Test
    public void userCheck() throws Exception {
    }

    @Test
    public void addTenant() throws Exception {
        Tenant tenant = newTenant();

        ResponseBean responseBean = tenantController.addTenant(tenant,request);
        Assert.assertEquals(ResponseBean.SUCCESS,responseBean.getFlag());
    }

    @Test
    public void updateTenant() throws Exception {
        Tenant tenant = newTenant();

        ResponseBean responseBean = tenantController.updateTenant(tenant,request);
        Assert.assertEquals(ResponseBean.SUCCESS,responseBean.getFlag());
    }

    @Test
    public void deleteTenant() throws Exception {
        String[] ids = "1".split(",");
        List<String> idList = Lists.newArrayList(ids);

        List<Tenant> list = Lists.newArrayList();
        list.add(newTenant());

        when(tenantJpaService.findByIds(idList)).thenReturn(list);
        ResponseBean responseBean = tenantController.deleteTenant(ids,request);
        Assert.assertEquals(ResponseBean.SUCCESS,responseBean.getFlag());
    }

    @Test
    public void validateTenantName() throws Exception {
        Tenant tenant = newTenant();

        when(tenantJpaService.getOne(tenant.getTenantId())).thenReturn(tenant);
        when(tenantJpaService.findByName(tenant.getName())).thenReturn(null);

        ResponseBean responseBean = tenantController.validateTenantName(tenant.getName(),tenant.getTenantId());
        Assert.assertEquals(ResponseBean.SUCCESS,responseBean.getFlag());
    }

    @Test
    public void validateTenantName2() throws Exception {
        Tenant tenant = newTenant();

        when(tenantJpaService.findByName(tenant.getName())).thenReturn(null);

        ResponseBean responseBean = tenantController.validateTenantName(tenant.getName(),null);
        Assert.assertEquals(ResponseBean.SUCCESS,responseBean.getFlag());
    }


    private Tenant newTenant(){
        Tenant tenant = new Tenant();

        tenant.setTenantId("1");
        tenant.setName("test");
        tenant.setAdditionalInfo("info");
        return tenant;
    }

}