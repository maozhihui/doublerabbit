package com.comba.server.dao.device;

import com.comba.server.common.data.device.Product;
import com.comba.server.common.data.product.ProductTypeEnum;
import com.comba.server.common.data.web.page.Page;
import com.comba.server.dao.AbstractDaoTest;
import com.comba.server.dao.model.device.CategoryEntity;
import com.comba.server.dao.model.device.ProductEntity;
import com.google.common.collect.Lists;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductServiceTest extends AbstractDaoTest{

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductDao productDao;

    private Product product = newProduct();

    @Before
    public void setUp() throws Exception {
        ProductEntity productEntity = new ProductEntity(product);
        productDao.save(productEntity);
        product.setProductId(productEntity.getId());
    }

    @After
    public void tearDown() throws Exception {
        productService.deleteByTenantId(product.getTenantId());
    }

    @Test
    public void pagedQuery() throws Exception {
        List<String> orderBy = Lists.newArrayList("updateTime");
        Page page = productService.pagedQuery(1,100,product,orderBy);
        Assert.assertTrue(page.getResultSize() >= 0);
    }

    @Test
    public void getDataByPage() throws Exception {
        Page page = productService.getDataByPage(product,100,1000);
        Assert.assertTrue(page.getResultSize() >= 0);
    }

    @Test
    public void deleteByIds() throws Exception {
    }

    @Test
    public void findByIds() throws Exception {
        List<String> productIdList = Lists.newArrayList(product.getProductId());
        List<Product> productDBs = productService.findByIds(productIdList);
        assertEquals(product.getProductId(),productDBs.get(0).getProductId());
    }

    @Test
    public void findByTenantId() throws Exception {
        List<Product> productDBs = productService.findByTenantId(product.getTenantId());
        assertTrue(productDBs.size() >= 1);
    }

    @Test
    public void countByCategoryId() throws Exception {
        int count = productService.countByCategoryId(product.getCategoryId());
        assertTrue(count >= 1);
    }

    @Test
    public void deleteByTenantId() throws Exception {
    }

    @Test
    public void saveCommonProduct() throws Exception {
        productService.saveCommonProduct(product.getTenantId());
        int count = productService.countByCategoryId(product.getCategoryId());
        assertTrue(count >= 2);
    }

    @Test
    public void findAllByTenantIdAndType() throws Exception {
        List<Product> products = productService.findAllByTenantIdAndType(product.getTenantId(),product.getType());
        assertTrue(products.size() >= 1);
    }

    @Test
    public void findByNameAndTenantId() throws Exception {
        Product productDB = productService.findByNameAndTenantId(product.getName(),product.getTenantId());
        assertEquals(product.getName(),productDB.getName());
    }

    @Test
    public void findByTenantIdAndCategoryId(){
        String tenantId = "8a8aeb885ea83ecd015ea83f99c30002";
        String categoryId = "3";
        Product product = productService.findByTenantIdAndCategoryId(tenantId,categoryId);
        assertNull(product);
    }

    private Product newProduct(){
        String tenantId = "ffffffffffffffffffffffffffffffff";
        Product product = new Product();
        product.setCategoryId("3");
        product.setType(ProductTypeEnum.COMMON_PRODUCT.getType());
        product.setName(ProductTypeEnum.COMMON_PRODUCT.getName());
        product.setTenantId(tenantId);
        product.setCreateTime(new Date());
        product.setAccessProtocol("HTTP");
        return product;
    }

}