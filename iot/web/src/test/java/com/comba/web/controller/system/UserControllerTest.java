package com.comba.web.controller.system;

import com.comba.server.common.data.User;
import com.comba.server.common.data.web.page.Page;
import com.comba.server.dao.tenant.TenantService;
import com.comba.server.dao.user.UserService;
import com.comba.web.common.export.ExportUtils;
import com.comba.web.response.ResponseBean;
import com.comba.web.security.SpringSecurityUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.comba.server.common.data.user.UserEnum.USER_TYPE_SUPER_ADMIN;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({SpringSecurityUtils.class, ExportUtils.class})
public class UserControllerTest {

    @Mock
    private UserService userJpaService;

    @Mock
    private TenantService tenantService;

    @InjectMocks
    private UserController userController;

    @Mock
    private HttpServletRequest request;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        PowerMockito.mockStatic(SpringSecurityUtils.class);
        PowerMockito.mockStatic(ExportUtils.class);
    }

    @Test
    public void asignRole() throws Exception {
        String ret = userController.asignRole("1",new BindingAwareModelMap());
        Assert.assertEquals("system/user/asign_role",ret);
    }

    @Test
    public void viewInfo() throws Exception {
        String ret = userController.viewInfo(new Page());
        Assert.assertEquals("system/user/viewMyInfo",ret);
    }

    @Test
    public void list() throws Exception {
        String ret = userController.list(new Page());
        Assert.assertEquals("system/user/user_list",ret);
    }

    @Test
    public void user_list_tenant() throws Exception {
        String ret = userController.user_list_tenant(new Page());
        Assert.assertEquals("system/user/user_list_tenant",ret);
    }

    @Test
    public void getDataByPageOfTenant() throws Exception {
        Page page = new Page();
        User user = new User();
        String tenantId = "1";
        user.setTenantId(tenantId);
        page.setPageNo(1);
        page.setPageSize(100);

        when(SpringSecurityUtils.getCurrentUserTenantId()).thenReturn(tenantId);
        when(userJpaService.pagedQuery(1,100,user,null)).thenReturn(page);
        Page ret = userController.getDataByPageOfTenant(user,page);
        Assert.assertNull(ret);
    }

    @Test
    public void exportDataOfTenant() throws Exception {

    }

    @Test
    public void getDataByPage() throws Exception {
        Page page = new Page();
        User user = new User();
        String tenantId = "1";
        user.setTenantId(tenantId);
        page.setPageNo(1);
        page.setPageSize(100);

        when(userJpaService.pagedQuery(1,100,user,null)).thenReturn(page);
        Page ret = userController.getDataByPage(user,page);
        Assert.assertNull(ret);
    }

    @Test
    public void exportData() throws Exception {
    }

    @Test
    public void listByOrga() throws Exception {
        String ret = userController.listByOrga(new Page());
        Assert.assertEquals("system/user/user_listByOrga",ret);
    }

    @Test
    public void datasByPageOfOrga() throws Exception {
        Page page = new Page();
        User user = new User();
        String tenantId = "1";
        user.setTenantId(tenantId);
        page.setPageNo(1);
        page.setPageSize(100);
        when(userJpaService.pagedQuery(1,100,user,null)).thenReturn(page);
        Page ret = userController.datasByPageOfOrga(user,page,1,1);
        Assert.assertNull(ret);
    }

    @Test
    public void exportDataOfOrga() throws Exception {
    }

    @Test
    public void userAdd() throws Exception {
        String userId = "1";

        when(userJpaService.findByUserId(userId)).thenReturn(new User());
        String ret = userController.userAdd(userId,new BindingAwareModelMap(),1);

        Assert.assertEquals("system/user/user_add",ret);
    }

    @Test
    public void userEdit() throws Exception {
        String userId = "1";

        when(userJpaService.findByUserId(userId)).thenReturn(new User());
        String ret = userController.userEdit(userId,new BindingAwareModelMap(),1);

        Assert.assertEquals("system/user/user_edit",ret);
    }

    @Test
    public void userCheck() throws Exception {
        String userId = "1";

        when(userJpaService.findByUserId(userId)).thenReturn(new User());
        String ret = userController.userCheck(userId,new BindingAwareModelMap());

        Assert.assertEquals("system/user/user_view",ret);
    }

    @Test
    public void addUserFail() throws Exception {
        User user = newUser();

        when(SpringSecurityUtils.isSupperAdmin()).thenReturn(true);
        when(tenantService.countByName(user.getUserName())).thenReturn(1);


        ResponseBean ret =  userController.addUser(user,request);
        Assert.assertEquals(ResponseBean.FAIL,ret.getFlag());
        Assert.assertEquals("添加用户失败，该用户已存在",ret.getMessage());
    }

    @Test
    public void addUserSuccess() throws Exception {

        User user = newUser();

        when(SpringSecurityUtils.isSupperAdmin()).thenReturn(true);
        when(tenantService.countByName(user.getUserName())).thenReturn(0);


        ResponseBean ret =  userController.addUser(user,request);
        Assert.assertEquals(ResponseBean.SUCCESS,ret.getFlag());

    }

    @Test
    public void updateUser() throws Exception {
        User user = newUser();

        when(userJpaService.getOne(user.getUserId())).thenReturn(user);

        ResponseBean ret =  userController.updateUser(user,request);
        Assert.assertEquals(ResponseBean.SUCCESS,ret.getFlag());
    }

    @Test
    public void updateUserFail() throws Exception {
        User user = newUser();

        when(userJpaService.getOne(user.getUserId())).thenReturn(null);

        ResponseBean ret =  userController.updateUser(user,request);
        Assert.assertEquals(ResponseBean.FAIL,ret.getFlag());
    }

    @Test
    public void deleteUserFail() throws Exception {
        String[] ids = "1".split(",");
        List<String> idList = Lists.newArrayList(ids);
        List<User> users = Lists.newArrayList();
        User user = newUser();
        user.setType(USER_TYPE_SUPER_ADMIN.getType());
        users.add(user);

        when(userJpaService.findByIds(idList)).thenReturn(users);

        ResponseBean responseBean = userController.deleteUser(ids,request);
        Assert.assertEquals(ResponseBean.FAIL,responseBean.getFlag());
        Assert.assertEquals("系统管理员不能删除",responseBean.getMessage());
    }

    @Test
    public void deleteUser() throws Exception {
        String[] ids = "1".split(",");
        List<String> idList = Lists.newArrayList(ids);
        List<User> users = Lists.newArrayList();
        User user = newUser();
        users.add(user);

        when(userJpaService.findByIds(idList)).thenReturn(users);

        ResponseBean responseBean = userController.deleteUser(ids,request);
        Assert.assertEquals(ResponseBean.SUCCESS,responseBean.getFlag());
    }

    @Test
    public void updPassword() throws Exception {
        String randomPsw = "123456";
        String userId = "1";

        when(userJpaService.findByUserId(userId)).thenReturn(newUser());
        String ret = userController.updPassword(userId,request,randomPsw);
        Assert.assertEquals(randomPsw,ret);
    }

    @Test
    public void setPassword() throws Exception {
        String userId = "1";
        String password = "111111";
        User user = newUser();


        when(userJpaService.findByUserId(userId)).thenReturn(user);
        ResponseBean responseBean = userController.setPassword(userId,password,user.getPwd(),request);

        Assert.assertEquals(ResponseBean.SUCCESS,responseBean.getFlag());
    }


    @Test
    public void viewMyInfo() throws Exception {
    }

    @Test
    public void userSetPassword() throws Exception {
        String userId = "1";

        when(userJpaService.findByUserId(userId)).thenReturn(newUser());
        String ret = userController.userSetPassword(userId,new BindingAwareModelMap());
        Assert.assertEquals("system/user/user_setPassword",ret);
    }

    @Test
    public void getCurrentUser() throws Exception {
        String userId = "1";
        User user = newUser();
        when(SpringSecurityUtils.getCurrentUserId()).thenReturn(userId);
        when(userJpaService.findByUserId(userId)).thenReturn(user);

        User ret = userController.getCurrentUser();
        Assert.assertEquals(user.getUserId(),ret.getUserId());
    }

    @Test
    public void insert() throws Exception {
    }

    @Test
    public void importExcel() throws Exception {
        MultipartFile file = new MockMultipartFile("test","content:123".getBytes());
        List<HSSFRow> hssfRowList = Lists.newArrayList();

        when(ExportUtils.readExcel(file)).thenReturn(hssfRowList);
        when(userJpaService.saveUserList(hssfRowList)).thenReturn(1);

        ResponseBean responseBean = userController.importExcel(request,file);
        Assert.assertEquals(ResponseBean.SUCCESS,responseBean.getFlag());
    }

    @Test
    public void importExcelFail() throws Exception {
        MultipartFile file = new MockMultipartFile("test","content:123".getBytes());

        when(ExportUtils.readExcel(file)).thenThrow(IOException.class);

        ResponseBean responseBean = userController.importExcel(request,file);
        Assert.assertEquals(ResponseBean.FAIL,responseBean.getFlag());
        Assert.assertEquals("用户导入失败,读取excel文件失败",responseBean.getMessage());
    }

    @Test
    public void importExcelFail2() throws Exception {
        MultipartFile file = new MockMultipartFile("test","content:123".getBytes());
        List<HSSFRow> hssfRowList = Lists.newArrayList();

        when(ExportUtils.readExcel(file)).thenReturn(hssfRowList);
        when(userJpaService.saveUserList(hssfRowList)).thenThrow(new Exception("用户导入失败，保存记录失败"));

        ResponseBean responseBean = userController.importExcel(request,file);
        Assert.assertEquals(ResponseBean.FAIL,responseBean.getFlag());
        Assert.assertEquals("用户导入失败，保存记录失败",responseBean.getMessage());
    }

    @Test
    public void exportUserModelExcel() throws Exception {
        HttpSession session = new MockHttpSession();
        Map<String,Object> parameter = new HashMap<>();

        userController.exportUserModelExcel(session,parameter);
    }

    private User newUser(){
        User user = new User();
        user.setAccount("admin");
        user.setUserName("admin");
        user.setPwd("123456");
        user.setUserId("1");
        return user;
    }
}