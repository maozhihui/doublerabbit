package com.comba.server.dao.user;

import com.comba.server.common.data.user.UserEnum;
import com.comba.server.dao.AbstractDaoTest;
import com.comba.server.dao.model.UserEntity;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.comba.server.common.data.User.STATE_FLAG_ACTIVE;


public class UserJpaDaoTest extends AbstractDaoTest{

    @Autowired
    private UserDao userDao;

    private final static String ADMIN_ACCOUNT = "admin";
    private final static String ADMIN_USER_ID = "cf553381782511e7319067db7fdd09cd";
    private final static String ADMIN_TENANT_ID = "ffffffffffffffffffffffffffffffff";


    @Test
    public void findByAccount() throws Exception {
        List<UserEntity> user = userDao.findByAccount(ADMIN_ACCOUNT);
        Assert.assertEquals(ADMIN_ACCOUNT,user.get(0).getAccount());
    }

    @Test
    public void findByUserId() throws Exception {
        List<UserEntity> users = userDao.findByUserId(ADMIN_USER_ID);
        Assert.assertEquals(ADMIN_USER_ID,users.get(0).getUserId());
    }

    @Test
    public void countByStateFlag() throws Exception {
        Long ret = userDao.countByStateFlag(STATE_FLAG_ACTIVE);
        Assert.assertTrue(ret >= 0);
    }

    @Test
    public void countByMsisdn() throws Exception {
        int ret = userDao.countByMsisdn("");
        Assert.assertEquals(0,ret);
    }

    @Test
    public void countByTenantIdAndType() throws Exception {
        int ret = userDao.countByTenantIdAndType(ADMIN_TENANT_ID, UserEnum.USER_TYPE_SUPER_ADMIN.getType());
        Assert.assertEquals(1,ret);
    }

    @Test
    @Transactional
    @Rollback()
    public void deleteByTenantId() throws Exception {
        int ret = userDao.deleteByTenantId(ADMIN_TENANT_ID);
        Assert.assertEquals(1,ret);
    }

    @Test
    public void findByMsisdn() throws Exception {
    	UserEntity user = userDao.findByMsisdn("");
        Assert.assertEquals(null,user);
    }

    @Test
    public void findByTenantId() throws Exception {
    	UserEntity user = userDao.findByTenantId(ADMIN_TENANT_ID).get(0);
        Assert.assertEquals(ADMIN_TENANT_ID,user.getTenantId());
    }

}