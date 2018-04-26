package com.comba.server.dao.device;

import com.comba.server.common.data.device.TelemetryAttributes;
import com.comba.server.common.data.web.page.Page;
import com.comba.server.dao.AbstractDaoTest;
import com.comba.server.dao.model.device.TelemetryAttributesEntity;
import org.apache.commons.lang3.time.DateUtils;
import org.assertj.core.util.Lists;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TelemetryAttributesServiceTest extends AbstractDaoTest{

    @Autowired
    private TelemetryAttributesService telemetryAttributesService;

    @Autowired
    private TelemetryAttributesDao telemetryAttributesDao;

    private TelemetryAttributes telemetryAttributes = newTelemetryAttributes();

    @Before
    public void setUp() throws Exception {
        TelemetryAttributesEntity entity = new TelemetryAttributesEntity(telemetryAttributes);
        telemetryAttributesDao.save(entity);
        telemetryAttributes.setTelemetryAttributeId(entity.getTelemetryAttributeId());
    }

    @After
    public void tearDown() throws Exception {
        telemetryAttributesService.delete(telemetryAttributes.getTelemetryAttributeId());
    }

    @Test
    public void findAll() throws Exception {
        List<TelemetryAttributes> telemetryAttributes = telemetryAttributesService.findAll();
        assertTrue(telemetryAttributes.size() >= 0);
    }

    @Test
    public void pagedQuery() throws Exception {
        List<String> orderBy = Lists.newArrayList("updateTime");
        Page page = telemetryAttributesService.pagedQuery(100,1000,telemetryAttributes,orderBy);
        Assert.assertTrue(page.getResultSize() >= 0);
    }

    @Test
    public void getDataByPage() throws Exception {
        Page page = telemetryAttributesService.getDataByPage(telemetryAttributes,100,1000);
        Assert.assertTrue(page.getResultSize() >= 0);
    }

    @Test
    public void deleteByIds() throws Exception {
    }

    @Test
    public void findByIds() throws Exception {
        List<String> ids = Lists.newArrayList(telemetryAttributes.getTelemetryAttributeId());
        List<TelemetryAttributes> list = telemetryAttributesService.findByIds(ids);
        assertTrue(list.size() >= 1);
    }

    @Test
    public void countByProductId() throws Exception {
        Date end = new Date();
        Date start = DateUtils.addDays(end,-1);
        Long count = telemetryAttributesService.countByProductId("1",start,end);
        assertTrue(count >= 0);
    }

    @Test
    public void attributeStatisByDayOfProduct() throws Exception {
        Date end = new Date();
        Date start = DateUtils.addDays(end,-1);
        List<Object[]> list = telemetryAttributesService.attributeStatisByDayOfProduct(start,end,"1");
        assertNotNull(list);
    }

    @Test
    public void attributeStaticsByDevIdAndAttributeName() throws Exception {
        LocalDate end = LocalDate.now();
        LocalDate start = end.plusDays(-1);

        List<?> list = telemetryAttributesService.attributeStaticsByDevIdAndAttributeName(start,end,telemetryAttributes.getDevId(),telemetryAttributes.getAttributeName());
        assertNotNull(list);
    }

    @Test
    public void save() throws Exception {
        telemetryAttributesService.save(telemetryAttributes);

    }

    @Test
    public void getListByDevId() throws Exception {
        List<TelemetryAttributesEntity> list = telemetryAttributesService.getListByDevId(telemetryAttributes.getDevId());
        assertTrue(list.size() >= 1);
    }

    @Test
    public void countByAttributeNameAndDevId() throws Exception {
        int count = telemetryAttributesService.countByAttributeNameAndDevId(telemetryAttributes.getAttributeName(),telemetryAttributes.getDevId());
        assertTrue(count >= 1);
    }

    private TelemetryAttributes newTelemetryAttributes(){
        TelemetryAttributes telemetryAttributes = new TelemetryAttributes();
        telemetryAttributes.setAttributeName("test");
        telemetryAttributes.setDevId("8a8aeb8e5e4f8fa8015e512b60890041");
        telemetryAttributes.setValueType("String");
        return telemetryAttributes;
    }

}