package com.comba.web.controller.device;

import com.comba.server.common.data.device.Category;
import com.comba.server.common.data.device.Product;
import com.comba.server.common.data.web.page.Page;
import com.comba.server.dao.device.CategoryService;
import com.comba.server.dao.device.DeviceService;
import com.comba.server.dao.device.HistoryDataService;
import com.comba.server.dao.device.ProductService;
import com.comba.server.dao.rule.RuleJpaService;
import com.comba.web.response.ResponseBean;
import com.comba.web.security.SpringSecurityUtils;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.validation.support.BindingAwareModelMap;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SpringSecurityUtils.class)
public class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;
    @Mock
    private DeviceService deviceService;
    @Mock
    private RuleJpaService ruleService;
    @Mock
    private HistoryDataService historyDataService;
    @Mock
    private CategoryService categoryService;

    private HttpServletRequest request = new MockHttpServletRequest();

    private Product product = newProduct();


    @Before
    public void setUp() throws Exception {
        initMocks(this);
        PowerMockito.mockStatic(SpringSecurityUtils.class);
    }

    @Test
    public void index() throws Exception {
        when(productService.getOne(product.getProductId())).thenReturn(product);

        String ret = productController.index(new BindingAwareModelMap(),product.getProductId(),new MockHttpSession());
        Assert.assertEquals("product/product_index",ret);
    }

    @Test
    public void index2() throws Exception {
        List<Product> list = Lists.newArrayList();
        list.add(product);

        when(SpringSecurityUtils.getCurrentUserTenantId()).thenReturn(product.getTenantId());
        when(productService.getOne(product.getProductId())).thenReturn(product);
        when(productService.findByTenantId(product.getTenantId())).thenReturn(list);

        String ret = productController.index(new BindingAwareModelMap(),"",new MockHttpSession());
        Assert.assertEquals("product/product_index",ret);
    }

    @Test
    public void product_statis() throws Exception {
        Map<String,Object> deviceStatis = new HashMap<>();
        Long dataNum = Long.valueOf(1);
        Long ruleNum = Long.valueOf(1);

        when(deviceService.deviceStatisByIsGateWayOfProduct(product.getProductId())).thenReturn(deviceStatis);
        when(historyDataService.countByProductId(product.getProductId())).thenReturn(dataNum);
        when(SpringSecurityUtils.getCurrentUserTenantId()).thenReturn(product.getTenantId());
        when(ruleService.countByTenantIdAndProductId(product.getTenantId(),product.getProductId())).thenReturn(ruleNum);

        Map<String,Object> ret = productController.product_statis(product.getProductId());
        assertEquals(dataNum,ret.get("dataNum"));
        assertEquals(ruleNum,ret.get("ruleNum"));
    }

    @Test
    public void edit() throws Exception {

        when(productService.getOne(product.getProductId())).thenReturn(product);
        when(categoryService.getOne(product.getCategoryId())).thenReturn(new Category());


        String ret = productController.edit(product.getProductId(),new BindingAwareModelMap());
        assertEquals("product/product_edit",ret);
    }

    @Test
    public void list() throws Exception {
    }

    @Test
    public void userCheck() throws Exception {
    }

    @Test
    public void getAuditDataByPage() throws Exception {
        Page page = new Page();
        page.setPageNo(1);
        page.setPageSize(100);

        when(productService.pagedQuery(page.getPageNo(),page.getPageSize(),product,null))
                .thenReturn(page);
        Page ret = productController.getAuditDataByPage(product,new Page());
        Assert.assertNull(ret);
    }

    @Test
    public void datasByExport() throws Exception {
    }

    @Test
    public void addProduct() throws Exception {

        when(productService.findByNameAndTenantId(product.getName(),product.getTenantId())).thenReturn(null);
        when(SpringSecurityUtils.getCurrentUserTenantId()).thenReturn(product.getTenantId());

        ResponseBean responseBean = productController.addProduct(product,request);
        assertEquals(ResponseBean.SUCCESS,responseBean.getFlag());
        Mockito.verify(productService).save(product);
    }

    @Test
    public void addProductFail() throws Exception {

        when(productService.findByNameAndTenantId(product.getName(),product.getTenantId())).thenReturn(new Product());
        when(SpringSecurityUtils.getCurrentUserTenantId()).thenReturn(product.getTenantId());
        ResponseBean responseBean = productController.addProduct(product,request);
        assertEquals(ResponseBean.FAIL,responseBean.getFlag());
    }

    @Test
    public void updateProduct() throws Exception {
        when(productService.getOne(product.getProductId())).thenReturn(product);
        when(SpringSecurityUtils.getCurrentUserTenantId()).thenReturn(product.getTenantId());

        ResponseBean responseBean = productController.updateProduct(product,request);
        assertEquals(ResponseBean.SUCCESS,responseBean.getFlag());
        Mockito.verify(productService).save(product);
    }

    @Test
    public void updateProduct2() throws Exception {
        Product productNew = newProduct();
        productNew.setName("new TestName");

        when(productService.getOne(product.getProductId())).thenReturn(product);
        when(SpringSecurityUtils.getCurrentUserTenantId()).thenReturn(product.getTenantId());
        when(productService.findByNameAndTenantId(product.getName(),product.getTenantId())).thenReturn(new Product());

        ResponseBean responseBean = productController.updateProduct(productNew,request);
        assertEquals(ResponseBean.SUCCESS,responseBean.getFlag());
        Mockito.verify(productService).save(productNew);
    }

    @Test
    public void deleteGuser() throws Exception {
        String[] ids = "1".split(",");
        List<String> idList = Lists.newArrayList(ids);

        List<Product> list = Lists.newArrayList();
        list.add(newProduct());

        when(productService.findByIds(idList)).thenReturn(list);

        ResponseBean responseBean = productController.deleteGuser(ids,request);
        assertEquals(ResponseBean.SUCCESS,responseBean.getFlag());
        Mockito.verify(productService).deleteByIds(idList);
    }

    private Product newProduct(){
        Product product = new Product();
        product.setProductId("1");
        product.setCategoryId("2");
        product.setTenantId("3");
        product.setType(1);
        product.setName("test");
        return product;
    }

}