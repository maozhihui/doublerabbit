package com.comba.server.dao.device;

import com.comba.server.common.data.device.Category;
import com.comba.server.common.data.web.utils.UUIDUtils;
import com.comba.server.dao.AbstractDaoTest;
import com.comba.server.dao.model.device.CategoryEntity;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CategoryServiceTest extends AbstractDaoTest{

    @Autowired
    private CategoryService categoryService;

    private Category category = newCategory();

    @Test
    public void findAll() throws Exception {
        List<Category> categories = categoryService.findAll();
        Assert.assertTrue(categories.size() >= 1);
    }

    @Test
    public void pagedQuery() throws Exception {
        categoryService.pagedQuery(1,100,category,null);
    }

    @Test
    public void deleteByIds() throws Exception {
    }

    @Test
    public void findByIds() throws Exception {
        List<Category> categories = categoryService.findByIds(Lists.newArrayList(category.getCategoryId()));
        Assert.assertTrue(categories.size() >= 1);
    }

    @Test
    public void findByCode() throws Exception {
    }

    @Test
    public void findByCategory() throws Exception {
        List<Category> list = categoryService.findByCategory(category);
        Assert.assertTrue(list.size() >= 1);
    }

    @Test
    public void countByName() throws Exception {
        int count = categoryService.countByName(category.getName());
        Assert.assertTrue(count >= 1);
    }

    @Test
    public void findByName(){
        String name = "其它";
        Category entity = categoryService.findByNameAndLevelFlag(name,4);
        Assert.assertNull(entity);
    }

    private Category newCategory(){
        Category category = new Category();
        category.setCategoryId("1");
        category.setParentId("8a8aeb955ce30d4c015ce3167a260001");
        category.setName("其它");
        category.setLevelFlag(2);
        return category;
    }

    @Test
    public void testGetOne(){
        String categoryId = "8a8aeb8e-5dc6-82db-015d-c686895d0004";
        category = categoryService.getOne(UUIDUtils.toUUID(UUID.fromString(categoryId)));
        System.out.println("categoryName="+category.getName());
    }
}