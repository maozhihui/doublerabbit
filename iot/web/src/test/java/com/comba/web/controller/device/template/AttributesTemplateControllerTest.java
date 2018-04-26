package com.comba.web.controller.device.template;

import com.comba.server.common.data.device.AttributesTemplate;
import com.comba.server.common.data.web.page.Page;
import com.comba.server.dao.device.template.AttributesTemplateService;
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

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SpringSecurityUtils.class)
public class AttributesTemplateControllerTest {

    @InjectMocks
    private AttributesTemplateController attributesTemplateController;

    @Mock
    private AttributesTemplateService attributesTemplateService;

    private AttributesTemplate attributesTemplate = newAttributesTemplate();

    private HttpServletRequest request = new MockHttpServletRequest();

    @Test
    public void list() throws Exception {
    }

    @Test
    public void getAuditDataByPage() throws Exception {
        Page page = new Page();
        page.setPageNo(1);
        page.setPageSize(100);

        when(attributesTemplateService.pagedQuery(page.getPageNo(),page.getPageSize(),attributesTemplate,null)).thenReturn(page);

        Page ret = attributesTemplateController.getAuditDataByPage(attributesTemplate,page);
        Assert.assertNotNull(ret);
    }

    @Test
    public void datasByExport() throws Exception {
    }

    @Test
    public void get_attributesTemplateData() throws Exception {
        when(attributesTemplateService.getOne(attributesTemplate.getAttributesTemplateId())).thenReturn(attributesTemplate);
        AttributesTemplate ret = attributesTemplateController.get_attributesTemplateData(attributesTemplate.getAttributesTemplateId());
        assertEquals(attributesTemplate.getAttributesTemplateId(),ret.getAttributesTemplateId());
    }

    @Test
    public void to_attributesTemplate_edit() throws Exception {
        when(attributesTemplateService.getOne(attributesTemplate.getAttributesTemplateId())).thenReturn(attributesTemplate);
        String ret = attributesTemplateController.to_attributesTemplate_edit(attributesTemplate.getAttributesTemplateId(),attributesTemplate.getIsTelemetry().toString(),new BindingAwareModelMap());
        assertEquals("device/template/attributesTemplate/attributesTemplate_edit",ret);
    }

    @Test
    public void attributesTemplate_view() throws Exception {
        when(attributesTemplateService.getOne(attributesTemplate.getAttributesTemplateId())).thenReturn(attributesTemplate);
        String ret = attributesTemplateController.attributesTemplate_view(attributesTemplate.getAttributesTemplateId(),new BindingAwareModelMap());
        assertEquals("device/template/attributesTemplate/attributesTemplate_view",ret);
    }

    @Test
    public void addAttributesTemplate() throws Exception {
        ResponseBean responseBean = attributesTemplateController.addAttributesTemplate(attributesTemplate,request);
        assertEquals(ResponseBean.SUCCESS,responseBean.getFlag());
        Mockito.verify(attributesTemplateService).save(attributesTemplate);
    }

    @Test
    public void updateAttributesTemplate() throws Exception {
        ResponseBean responseBean = attributesTemplateController.updateAttributesTemplate(attributesTemplate,request);
        assertEquals(ResponseBean.SUCCESS,responseBean.getFlag());
        Mockito.verify(attributesTemplateService).save(attributesTemplate);
    }

    @Test
    public void deleteGuser() throws Exception {
        String[] ids = "1".split(",");
        List<String> idList = Lists.newArrayList(ids);

        List<AttributesTemplate> list = Lists.newArrayList();
        list.add(newAttributesTemplate());

        when(attributesTemplateService.findByIds(idList)).thenReturn(list);

        ResponseBean responseBean = attributesTemplateController.deleteGuser(ids,request);
        assertEquals(ResponseBean.SUCCESS,responseBean.getFlag());
        Mockito.verify(attributesTemplateService).deleteByIds(ids);
    }

    @Test
    public void validateAttributeTemplateName() throws Exception {
        when(attributesTemplateService.getOne(attributesTemplate.getAttributesTemplateId())).thenReturn(attributesTemplate);

        ResponseBean responseBean = attributesTemplateController.validateAttributeTemplateName(attributesTemplate.getAttributesTemplateId()
        ,attributesTemplate.getDeviceTemplateId(),attributesTemplate.getName(),attributesTemplate.getIsTelemetry());
        assertEquals(ResponseBean.SUCCESS,responseBean.getFlag());
    }

    @Test
    public void validateAttributeTemplateName2() throws Exception {
        when(attributesTemplateService.countByNameAndDeviceTemplateIdAndIsTelemetry(attributesTemplate.getDeviceTemplateId(),attributesTemplate.getName(),attributesTemplate.getIsTelemetry())).thenReturn(0);

        ResponseBean responseBean = attributesTemplateController.validateAttributeTemplateName(""
                ,attributesTemplate.getDeviceTemplateId(),attributesTemplate.getName(),attributesTemplate.getIsTelemetry());
        assertEquals(ResponseBean.SUCCESS,responseBean.getFlag());
    }

    private AttributesTemplate newAttributesTemplate(){
        AttributesTemplate attributesTemplate = new AttributesTemplate();
        attributesTemplate.setAttributesTemplateId("1");
        attributesTemplate.setName("test");
        attributesTemplate.setIsTelemetry(1);
        attributesTemplate.setReadOnly(1);
        attributesTemplate.setDeviceTemplateId("1");
        attributesTemplate.setValueType("String");
        attributesTemplate.setUpdateTime(new Date());
        return attributesTemplate;
    }

}