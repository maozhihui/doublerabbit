package com.comba.server.dao.user;

import com.comba.server.common.data.User;
import com.comba.server.common.data.user.UserEnum;
import com.comba.server.dao.AbstractDaoTest;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest extends AbstractDaoTest{

    @Autowired
    private UserService userService;

    private User user = newUser();

    @Test
    public void findByAccount() throws Exception {
        List<User> users = userService.findByAccount(user.getAccount());
        Assert.assertEquals(user.getAccount(),users.get(0).getAccount());
    }

    @Test
    public void findAll() throws Exception {
        List<User> list = userService.findAll();
        Assert.assertTrue(!list.isEmpty());
    }

    @Test
    public void findByIds() throws Exception {

        List<User> list = userService.findByIds(Lists.newArrayList(user.getUserId()));
        Assert.assertTrue(!list.isEmpty());
    }

    @Test
    public void deleteByIds() throws Exception {
    }

    @Test
    public void getStringRandom() throws Exception {
    }

    @Test
    public void resetPassword() throws Exception {
        userService.resetPassword(user.getUserId(),"123456");
    }

    @Test
    public void findByUserId() throws Exception {
        User userDB = userService.findByUserId(user.getUserId());
        Assert.assertEquals(user.getUserId(),userDB.getUserId());
    }

    @Test
    public void pagedQuery() throws Exception {
        List<String> orderBy = Lists.newArrayList("updateTime");
        userService.pagedQuery(10,100,user,orderBy);
    }

    @Test
    public void selectUserNames() throws Exception {
//        List<Map<String, Object>> list = userService.selectUserNames(user.getUserId());
//        Assert.assertNotNull(list);
    }

    @Test
    public void countByStateFlag() throws Exception {
        Long count = userService.countByStateFlag(User.STATE_FLAG_ACTIVE);
        Assert.assertTrue(count >= 0);
    }

    @Test
    public void saveTenantAndUser() throws Exception {
        userService.saveTenantAndUser(user);
    }

    @Test
    public void countByMsisdn() throws Exception {
        int count = userService.countByMsisdn(user.getMsisdn());
        Assert.assertTrue(count >= 0);
    }

    @Test
    public void countByTenantIdAndType() throws Exception {
        int count = userService.countByTenantIdAndType(user.getTenantId(), UserEnum.USER_TYPE_SUPER_ADMIN.getType());
        Assert.assertEquals(1,count);
    }

    @Test
    public void saveUserList() throws Exception {
    }

    @Test
    public void deleteByTenantId() throws Exception {
    }

    @Test
    public void saveUserAndProduct() throws Exception {
        userService.saveUserAndProduct(user);
    }

    @Test
    public void findByMsisdn() throws Exception {
        User userDB = userService.findByMsisdn(user.getMsisdn());
    }

    @Test
    public void findByTenantId() throws Exception {
        List<User> users = userService.findByTenantId(user.getTenantId());
        Assert.assertTrue(users.size() >= 1);
    }

    private User newUser(){
        User user = new User();
        user.setUserId("cf553381782511e7319067db7fdd09cd");
        user.setAccount("admin");
        user.setTenantId("ffffffffffffffffffffffffffffffff");
        user.setMsisdn("");
        return user;
    }

}