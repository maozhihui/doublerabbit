package com.comba.server.dao.tenant;

import com.comba.server.common.data.Tenant;
import com.comba.server.dao.AbstractDaoTest;
import com.comba.server.dao.model.TenantEntity;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class TenantDaoTest extends AbstractDaoTest{

    @Autowired
    private TenantDao tenantDao;

    private final static String TEST_NAME = "TenantDaoTest";

    @Before
    public void setUp() throws Exception {
        Tenant tenant = new Tenant();
        tenant.setName(TEST_NAME);
        tenantDao.save(new TenantEntity(tenant));
    }

    @Test
    public void findByName() throws Exception {
        TenantEntity entity = tenantDao.findByName(TEST_NAME);
        assertEquals(TEST_NAME,entity.getName());
    }

}