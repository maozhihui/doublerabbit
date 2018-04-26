package com.comba.server.dao.service;

import com.comba.server.common.data.Tenant;
import com.comba.server.common.data.web.page.Page;
import com.comba.server.dao.AbstractDaoTest;
import com.comba.server.dao.model.TenantEntity;
import com.comba.server.dao.tenant.TenantService;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TenantServiceImplTest extends AbstractDaoTest{

    @Autowired
    private TenantService tenantService;

    private Tenant tenant = newTenant();

    @Test
    public void findAll() throws Exception {
        List<Tenant> list = tenantService.findAll();
        Assert.assertTrue(list.size() >= 0);
    }

    @Test
    public void pagedQuery() throws Exception {
        Page page = tenantService.pagedQuery(100, 1000, newTenant(), null);
        Assert.assertNotNull(page.getResult());
    }

    @Test
    public void getDataByPage() throws Exception {
        Page page = tenantService.getDataByPage(tenant,100, 1000);
        Assert.assertNotNull(page.getResult());
    }

    @Test
    public void deleteByIds() throws Exception {

    }

    @Test
    public void findByIds() throws Exception {
        List<String> ids = Lists.newArrayList(tenant.getTenantId());
        List<Tenant> tenants = tenantService.findByIds(ids);
        Assert.assertNotNull(tenants);
    }

    @Test
    public void findTenants() throws Exception {
        List<Tenant> tenants = tenantService.findTenants();
        Assert.assertNotNull(tenants);
    }

    @Test
    public void saveTenantAndOrganization() throws Exception {
        tenantService.saveTenantAndOrganization(tenant);
    }

    @Test
    public void findByName() throws Exception {
        TenantEntity entity = tenantService.findByName(tenant.getName());
        Assert.assertEquals(tenant.getName(), entity.getName());
    }

    private Tenant newTenant() {
        Tenant tenant = new Tenant();
        tenant.setName("system admin");
        tenant.setTenantId("ffffffffffffffffffffffffffffffff");
        return tenant;
    }

}