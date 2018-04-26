package com.comba.web.controller.device;

import com.comba.server.common.data.device.Category;
import com.comba.server.common.data.web.page.Page;
import com.comba.server.dao.device.CategoryService;
import com.comba.server.dao.device.ProductService;
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

import static org.junit.Assert.*;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SpringSecurityUtils.class)
public class CategoryControllerTest {

    @InjectMocks
    private CategoryController categoryController;

    @Mock
    private CategoryService categoryService;

    @Mock
    private ProductService productService;

    @Mock
    private DeviceTemplateService deviceTemplateService;

    private HttpServletRequest request = new MockHttpServletRequest();

    private Category category = newCategory();

    @Test
    public void list() throws Exception {
    }

    @Test
    public void edit() throws Exception {
    }

    @Test
    public void getAuditDataByPage() throws Exception {
        Page page = new Page();
        page.setPageNo(1);
        page.setPageSize(100);

        when(categoryService.pagedQuery(1,page.getPageSize(),category,null)).thenReturn(page);

        Page ret = categoryController.getAuditDataByPage(category,page);
        Assert.assertNull(ret);
    }

    @Test
    public void check() throws Exception {

        when(categoryService.getOne(category.getCategoryId())).thenReturn(category);

        String ret = categoryController.check(category.getCategoryId(),new BindingAwareModelMap());
        assertEquals("device/category/category_view",ret);
    }

    @Test
    public void addCategory() throws Exception {
        when(categoryService.countByName(category.getName())).thenReturn(0);

        ResponseBean responseBean = categoryController.addCategory(category,request);
        assertEquals(ResponseBean.SUCCESS,responseBean.getFlag());
        Mockito.verify(categoryService).save(category);
    }

    @Test
    public void updateCategory() throws Exception {
        when(categoryService.getOne(category.getParentId())).thenReturn(null);

        ResponseBean responseBean = categoryController.updateCategory(category,request);
        assertEquals(ResponseBean.SUCCESS,responseBean.getFlag());
        Mockito.verify(categoryService).save(category);
    }

    @Test
    public void deleteOrg() throws Exception {
        when(categoryService.getOne(category.getCategoryId())).thenReturn(category);
        when(productService.countByCategoryId(category.getCategoryId())).thenReturn(0);
        when(deviceTemplateService.countByCategoryId(category.getCategoryId())).thenReturn(0);

        ResponseBean responseBean = categoryController.deleteOrg(category.getCategoryId(),request);
        assertEquals(ResponseBean.SUCCESS,responseBean.getFlag());
        Mockito.verify(categoryService).delete(category.getCategoryId());
    }

    @Test
    public void validateCode() throws Exception {

        when(categoryService.findByCode(category.getCode())).thenReturn(Lists.newArrayList(category));

        int ret = categoryController.validateCode(category.getCode(),category.getCategoryId());
        Assert.assertEquals(1,ret);
    }

    @Test
    public void getTreeData() throws Exception {
    }

    @Test
    public void getTreeGridData() throws Exception {
    }

    @Test
    public void getAuditDataByPageExport() throws Exception {
    }

    @Test
    public void expandNoses() throws Exception {
    }

    @Test
    public void validateCategoryName() throws Exception {
        when(categoryService.getOne(category.getCategoryId())).thenReturn(category);

        ResponseBean responseBean = categoryController.validateCategoryName(category.getCategoryId(),category.getName());
        assertEquals(ResponseBean.SUCCESS,responseBean.getFlag());
    }

    @Test
    public void validateCategoryName2() throws Exception {
        when(categoryService.countByName(category.getName())).thenReturn(0);

        ResponseBean responseBean = categoryController.validateCategoryName("",category.getName());
        assertEquals(ResponseBean.SUCCESS,responseBean.getFlag());
    }

    private Category newCategory(){
        Category category = new Category();
        category.setName("test");
        category.setCategoryId("1");
        category.setParentId("2");
        category.setLevelFlag(1);
        category.setCode("1");
        return category;
    }

}