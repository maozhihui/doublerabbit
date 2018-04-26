package com.comba.web.controller.device;

import com.comba.server.common.data.device.TelemetryAttributes;
import com.comba.server.common.data.web.page.Page;
import com.comba.server.dao.device.TelemetryAttributesService;
import com.comba.web.response.ResponseBean;
import com.comba.web.security.SpringSecurityUtils;
import org.apache.commons.lang3.time.DateUtils;
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
import org.springframework.validation.support.BindingAwareModelMap;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SpringSecurityUtils.class)
public class TelemetryAttributesControllerTest {

    @InjectMocks
    private TelemetryAttributesController telemetryAttributesController;

    @Mock
    private TelemetryAttributesService telemetryAttributesService;

    private TelemetryAttributes telemetryAttributes = newTelemetryAttributes();

    private HttpServletRequest request = new MockHttpServletRequest();

    @Test
    public void list() throws Exception {
    }

    @Test
    public void param() throws Exception {
    }



    @Test
    public void datasByExport() throws Exception {
    }

    @Test
    public void userAdd() throws Exception {

        when(telemetryAttributesService.getOne(telemetryAttributes.getTelemetryAttributeId())).thenReturn(telemetryAttributes);

        String  ret = telemetryAttributesController.userAdd(telemetryAttributes.getTelemetryAttributeId(),new BindingAwareModelMap());
        assertEquals("device/telemetryAttributes/telemetryAttributes_edit",ret);
    }

    @Test
    public void userCheck() throws Exception {
        when(telemetryAttributesService.getOne(telemetryAttributes.getTelemetryAttributeId())).thenReturn(telemetryAttributes);

        String  ret = telemetryAttributesController.userCheck(telemetryAttributes.getTelemetryAttributeId(),new BindingAwareModelMap());
        assertEquals("device/telemetryAttributes/telemetryAttributes_view",ret);
    }



    @Test
    public void get_telemetryAttributesData() throws Exception {
        when(telemetryAttributesService.getOne(telemetryAttributes.getTelemetryAttributeId())).thenReturn(telemetryAttributes);

        TelemetryAttributes ret = telemetryAttributesController.get_telemetryAttributesData(telemetryAttributes.getTelemetryAttributeId());
        assertEquals(telemetryAttributes.getAttributeName(),ret.getAttributeName());
    }

    @Test
    public void deviceStatisByDay() throws Exception {
        Date end = new Date();
        Date start = DateUtils.addDays(end,-1);

        telemetryAttributesController.deviceStatisByDay(start,end,telemetryAttributes.getDevId(),telemetryAttributes.getAttributeName());
    }

    @Test
    public void deviceStatisByDay1() throws Exception {
        Date end = new Date();
        Date start = DateUtils.addDays(end,-1);

        telemetryAttributesController.deviceStatisByDay(start,end,telemetryAttributes.getDevId());
    }


    @Test
    public void exportTelemetryAttributesByDay() throws Exception {
    }

    private TelemetryAttributes newTelemetryAttributes(){
        TelemetryAttributes telemetryAttributes = new TelemetryAttributes();
        telemetryAttributes.setAttributeName("test");
        telemetryAttributes.setDevId("8a8aeb8e5e4f8fa8015e512b60890041");
        telemetryAttributes.setValueType("String");
        telemetryAttributes.setTelemetryAttributeId("1");
        return telemetryAttributes;
    }

}