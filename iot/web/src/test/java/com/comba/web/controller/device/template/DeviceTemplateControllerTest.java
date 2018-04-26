package com.comba.web.controller.device.template;

import com.comba.server.common.data.device.DeviceTemplate;
import com.comba.server.common.data.web.page.Page;
import com.comba.server.dao.device.DeviceService;
import com.comba.server.dao.device.template.DeviceTemplateService;
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
import org.springframework.validation.support.BindingAwareModelMap;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SpringSecurityUtils.class)
public class DeviceTemplateControllerTest {

    @InjectMocks
    private DeviceTemplateController deviceTemplateController;

    @Mock
    private DeviceTemplateService deviceTemplateService;

    @Mock
    private DeviceService deviceService;

    private DeviceTemplate deviceTemplate = newDeviceTemplate();

    private HttpServletRequest request = new MockHttpServletRequest();

    @Test
    public void list() throws Exception {
    }

    @Test
    public void getAuditDataByPage() throws Exception {
        Page page = new Page();
        page.setPageNo(1);
        page.setPageSize(100);

        when(deviceTemplateService.pagedQuery(page.getPageNo(),page.getPageSize(),deviceTemplate,null)).thenReturn(page);

        Page ret = deviceTemplateController.getAuditDataByPage(deviceTemplate,page);
        Assert.assertNull(ret);
    }

    @Test
    public void datasByExport() throws Exception {
    }

    @Test
    public void toDeviceTemplateEditIndex() throws Exception {
        when(deviceTemplateService.getOne(deviceTemplate.getDeviceTemplateId())).thenReturn(deviceTemplate);

        String ret = deviceTemplateController.toDeviceTemplateEditIndex(deviceTemplate.getDeviceTemplateId(),new BindingAwareModelMap());
        assertEquals("device/template/deviceTemplate/deviceTemplateEditIndex",ret);
    }

    @Test
    public void toDeviceTemplateViewIndex() throws Exception {
        when(deviceTemplateService.getOne(deviceTemplate.getDeviceTemplateId())).thenReturn(deviceTemplate);

        String ret = deviceTemplateController.toDeviceTemplateEditIndex(deviceTemplate.getDeviceTemplateId(),new BindingAwareModelMap());
        assertEquals("device/template/deviceTemplate/deviceTemplateEditIndex",ret);
    }

    @Test
    public void userAdd() throws Exception {
        when(deviceTemplateService.getOne(deviceTemplate.getDeviceTemplateId())).thenReturn(deviceTemplate);

        String ret = deviceTemplateController.userAdd(deviceTemplate.getDeviceTemplateId(),new BindingAwareModelMap());
        assertEquals("device/template/deviceTemplate/deviceTemplate_edit",ret);
    }

    @Test
    public void userCheck() throws Exception {
        when(deviceTemplateService.getOne(deviceTemplate.getDeviceTemplateId())).thenReturn(deviceTemplate);

        String ret = deviceTemplateController.userCheck(deviceTemplate.getDeviceTemplateId(),new BindingAwareModelMap());
        assertEquals("device/template/deviceTemplate/deviceTemplate_view",ret);
    }

    @Test
    public void addDeviceTemplate() throws Exception {
        ResponseBean responseBean = deviceTemplateController.addDeviceTemplate(deviceTemplate,request);
        assertEquals(ResponseBean.SUCCESS,responseBean.getFlag());
        Mockito.verify(deviceTemplateService).save(deviceTemplate);
    }

    @Test
    public void updateDeviceTemplate() throws Exception {
        ResponseBean responseBean = deviceTemplateController.updateDeviceTemplate(deviceTemplate,request);
        assertEquals(ResponseBean.SUCCESS,responseBean.getFlag());
        Mockito.verify(deviceTemplateService).save(deviceTemplate);
    }

    @Test
    public void deleteDeviceTemplate() throws Exception {
        String[] ids = "1".split(",");
        List<String> idList = Lists.newArrayList(ids);

        List<DeviceTemplate> list = Lists.newArrayList();
        list.add(newDeviceTemplate());

        when(deviceTemplateService.findByIds(idList)).thenReturn(list);
        when(deviceService.countByDeviceTemplateId(idList)).thenReturn(0);

        ResponseBean responseBean = deviceTemplateController.deleteDeviceTemplate(ids,request);
        assertEquals(ResponseBean.SUCCESS,responseBean.getFlag());
        Mockito.verify(deviceTemplateService).deleteByIds(ids);

    }

    @Test
    public void insert() throws Exception {
    }

    @Test
    public void validateDeviceTemplateName() throws Exception {
        when(deviceTemplateService.getOne(deviceTemplate.getDeviceTemplateId())).thenReturn(deviceTemplate);

        ResponseBean responseBean = deviceTemplateController.validateDeviceTemplateName(deviceTemplate.getDeviceTemplateId(),deviceTemplate.getName());
        assertEquals(ResponseBean.SUCCESS,responseBean.getFlag());
    }

    @Test
    public void validateDeviceTemplateName2() throws Exception {
        when(deviceTemplateService.countByName(deviceTemplate.getName())).thenReturn(0);

        ResponseBean responseBean = deviceTemplateController.validateDeviceTemplateName("",deviceTemplate.getName());
        assertEquals(ResponseBean.SUCCESS,responseBean.getFlag());
    }

    private DeviceTemplate newDeviceTemplate(){
        DeviceTemplate deviceTemplate = new DeviceTemplate();
        deviceTemplate.setDeviceTemplateId("1");
        deviceTemplate.setCategoryId("1");
        deviceTemplate.setName("test");
        return deviceTemplate;
    }

}