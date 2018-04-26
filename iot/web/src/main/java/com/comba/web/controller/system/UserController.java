package com.comba.web.controller.system;

import com.comba.server.common.data.User;
import com.comba.server.common.data.user.UserEnum;
import com.comba.server.common.data.web.page.Page;
import com.comba.server.common.data.web.utils.Constants;
import com.comba.server.dao.common.utils.StringUtils;
import com.comba.server.dao.tenant.TenantService;
import com.comba.server.dao.user.UserService;
import com.comba.web.common.annotation.SystemControllerLog;
import com.comba.web.common.export.ExportUtils;
import com.comba.web.exception.ControllerException;
import com.comba.web.response.ResponseBean;
import com.comba.web.security.SpringSecurityUtils;
import com.comba.web.spring.SessionCounter;
import com.comba.web.utils.CommonProperties;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.hibernate.internal.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.comba.server.common.data.user.UserEnum.*;

/**
 * 用户管理
 *
 * @author wengzhonghui
 */
@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userJpaService;

    @Autowired
    private TenantService tenantService;

    @RequestMapping("/asign_role")
    public String asignRole(String userId, Model model) throws Exception {
        model.addAttribute("userId", userId);
        return "system/user/asign_role";
    }

    @RequestMapping("/to_view_info")
    public String viewInfo(@ModelAttribute Page page) throws Exception {
        return "system/user/viewMyInfo";
    }

    @RequestMapping("/user-list")
    public String list(@ModelAttribute Page page) throws Exception {
        return "system/user/user_list";
    }

    @RequestMapping("/user-list-tenant")
    public String user_list_tenant(@ModelAttribute Page page) throws Exception {
        return "system/user/user_list_tenant";
    }

    @ResponseBody
    @RequestMapping("/datasByPageOfTenant")
    public Page getDataByPageOfTenant(User user, Page page) throws Exception {
        page = page == null ? new Page() : page;
        Page returnPage = null;
        List<String> orderBysList = page.getOrderBys();
        user.setTenantId(SpringSecurityUtils.getCurrentUserTenantId());
        returnPage = userJpaService.pagedQuery(page.getPageNo(), page.getPageSize(), user, orderBysList);
        return returnPage;
    }

    @ResponseBody
    @RequestMapping("/datasByExportOfTenant")
    public ResponseBean ExportDataOfTenant(User user, Page page, HttpSession session
            , @RequestParam Map<String, Object> parameterMap, String exportFlag
    ) throws Exception {
        page = page == null ? new Page() : page;
        Page returnPage = null;
        List<String> orderBysList = page.getOrderBys();
        page.setPageSize(CommonProperties.maxAllowExportNum);
        user.setTenantId(SpringSecurityUtils.getCurrentUserTenantId());
        returnPage = userJpaService.pagedQuery(page.getPageNo(), page.getPageSize(), user, orderBysList);
        return ExportUtils.exportDatasByExcel(returnPage, exportFlag, session, parameterMap);
    }


    @ResponseBody
    @RequestMapping("/datasByPage")
    public Page getDataByPage(User user, Page page) throws Exception {
        page = page == null ? new Page() : page;
        List<String> orderBysList = page.getOrderBys();
        return userJpaService.pagedQuery(page.getPageNo(), page.getPageSize(), user, orderBysList);
    }

    @ResponseBody
    @RequestMapping("/datasByExport")
    public ResponseBean ExportData(HttpServletRequest request,User user, Page page, HttpSession session
            , @RequestParam Map<String, Object> parameterMap, String exportFlag
    ) throws Exception {
        page = page == null ? new Page() : page;
        Page returnPage = null;
        List<String> orderBysList = page.getOrderBys();
        page.setPageSize(CommonProperties.maxAllowExportNum);
        returnPage = userJpaService.pagedQuery(page.getPageNo(), page.getPageSize(), user, orderBysList);
        request.setAttribute(Constants.AOP_LOG_DESCRIPTION, "导出用户列表成功");
        return ExportUtils.exportDatasByExcel(returnPage, exportFlag, session, parameterMap);
    }


    /**
     * 进入按机构查看用户界面
     *
     * @param page
     * @return
     * @throws Exception
     */
    @RequestMapping("/user_listByOrga")
    public String listByOrga(@ModelAttribute Page page) throws Exception {
        return "system/user/user_listByOrga";
    }

    /**
     * 通过机构获取分页数据
     *
     * @param user
     * @param page
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/datasByPageOfOrga")
    public Page datasByPageOfOrga(User user, Page page, Integer organId, Integer isQueryChild) throws Exception {
        List<String> orderBysList = page.getOrderBys();
        page = page == null ? new Page() : page;
        return userJpaService.pagedQuery(page.getPageNo(), page.getPageSize(), user, orderBysList);
    }

    @ResponseBody
    @RequestMapping("/datasByPageOfOrgaExport")
    @SystemControllerLog(description = "导出用户列表")
    public ResponseBean ExportDataOfOrga(User user, Page page, Integer organId, Integer isQueryChild
            , String exportFlag, HttpSession session, @RequestParam Map<String, Object> parameterMap
    ) throws Exception {
        page.setPageSize(CommonProperties.maxAllowExportNum);
        Page dataPage = datasByPageOfOrga(user, page, organId, isQueryChild);
        return ExportUtils.exportDatasByExcel(dataPage, exportFlag, session, parameterMap);
    }

    @RequestMapping("/to_user_add")
    public String userAdd(String userId, Model model, Integer selectOrganId) throws Exception {
        User user = null;
        if (StringHelper.isNotEmpty(userId))
            user = userJpaService.findByUserId(userId);
        model.addAttribute("user", user);

        model.addAttribute("isSuperAdmin", SpringSecurityUtils.isSupperAdmin());
        model.addAttribute("selectOrganId", selectOrganId);
        return "system/user/user_add";
    }

    @RequestMapping("/to_user_edit")
    public String userEdit(String userId, Model model, Integer selectOrganId) throws Exception {
        User user = null;
        if (StringHelper.isNotEmpty(userId))
            user = userJpaService.findByUserId(userId);
        model.addAttribute("user", user);
        model.addAttribute("isSuperAdmin", SpringSecurityUtils.isSupperAdmin());
        model.addAttribute("selectOrganId", selectOrganId);
        return "system/user/user_edit";
    }

    @RequestMapping("/to_user_view")
    public String userCheck(String userId, Model model) throws Exception {
        User user = userJpaService.findByUserId(userId);

        model.addAttribute("isSuperAdmin", SpringSecurityUtils.isSupperAdmin());
        model.addAttribute("user", user);
        return "system/user/user_view";
    }


    @RequestMapping(value = "addUser", method = RequestMethod.POST)
    @ResponseBody
    @SystemControllerLog(description = "添加用户")
    public ResponseBean addUser(User user, HttpServletRequest request) throws Exception {
        ResponseBean responseBean = new ResponseBean(ResponseBean.FAIL);
        if (StringHelper.isEmpty(user.getAccount())
                || StringHelper.isEmpty(user.getUserName())
                || StringHelper.isEmpty(user.getPwd())) {
            request.setAttribute(Constants.AOP_LOG_DESCRIPTION, "添加用户失败【" + user.getUserName() + "】");
            responseBean.setMessage("参数为空");
            return responseBean;
        }

        //根据当前用户类型设置添加用户的类型，如果是超级管理员
        //则添加的是租户管理员，如果当前是租户管理员，则添加的是普通用户
        if (SpringSecurityUtils.isSupperAdmin()) {
            //如果已存在租户，则不能继续添加
            if (tenantService.countByName(user.getAccount()) > 0) {
                responseBean.setMessage("添加用户失败，该用户已存在");
                request.setAttribute(Constants.AOP_LOG_DESCRIPTION, "添加用户失败【" + user.getUserName() + "】");
                return responseBean;
            }
            user.setType(USER_TYPE_TENANT_ADMIN.getType());
        } else {
            user.setType(USER_TYPE_NORMAL.getType());
        }

        userJpaService.saveTenantAndUser(user);
        request.setAttribute(Constants.AOP_LOG_DESCRIPTION, "添加用户【" + user.getUserName() + "】");
        responseBean.setFlag_success();
        return responseBean;

    }

    @RequestMapping(value = "updateUser", method = RequestMethod.POST)
    @ResponseBody
    @SystemControllerLog(description = "更新用户")
    public ResponseBean updateUser(User user, HttpServletRequest request) throws Exception {
        ResponseBean responseBean = new ResponseBean(ResponseBean.FAIL);
        if (user.getUserId() == null
                || StringHelper.isEmpty(user.getAccount())) {
            request.setAttribute(Constants.AOP_LOG_DESCRIPTION, "更新用户失败，参数为空【" + user.getUserName() + "】");
            responseBean.setMessage("参数为空");
            return responseBean;
        }
        User oldUser = userJpaService.getOne(user.getUserId());
        if (oldUser == null) {
            responseBean.setMessage("用户不存在");
            return responseBean;
        }

        user.setPwd(oldUser.getPwd());
        user.setStateFlag(user.getStateFlag());
        userJpaService.save(user);
        responseBean.setFlag_success();
        request.setAttribute(Constants.AOP_LOG_DESCRIPTION, "更新用户【" + user.getUserName() + "】");
        return responseBean;
    }

    @RequestMapping(value = "deleteUser", method = RequestMethod.POST)
    @SystemControllerLog(description = "删除用户")
    @ResponseBody
    public ResponseBean deleteUser(@RequestParam(value = "ids[]") String[] ids
            , HttpServletRequest request) throws Exception {
        ResponseBean responseBean = new ResponseBean(ResponseBean.FAIL);
        if (ids == null || ids.length < 1) {
            return responseBean;
        }
        List<String> userIdList = Lists.newArrayList(ids);

        StringBuilder logDesc = new StringBuilder();
        List<User> userList = userJpaService.findByIds(userIdList);

        for (User user : userList) {if (userList != null && userList.size() > 0) {
            if (user != null && user.getUserName() != null) {
                if(USER_TYPE_SUPER_ADMIN.getType().equals(user.getType())){
                    request.setAttribute(Constants.AOP_LOG_DESCRIPTION, "删除用户【" + user.getUserName() + "】 失败");
                    responseBean.setMessage("系统管理员不能删除");
                    return responseBean;
                }

                if (SessionCounter.getSession(user.getAccount())){
                    request.setAttribute(Constants.AOP_LOG_DESCRIPTION, "删除用户【" + user.getUserName() + "】 失败");
                    responseBean.setMessage("用户在线，不能删除");
                    return responseBean;
                }

                if (logDesc.length() > 0) logDesc.append(",");
                logDesc.append(user.getUserName());
            }
        }
        request.setAttribute(Constants.AOP_LOG_DESCRIPTION, "删除用户【" + logDesc.toString() + "】");
        }
        userJpaService.deleteByIds(ids);
        responseBean.setFlag_success();
        return responseBean;
    }

    @RequestMapping(value = "updPassword", method = RequestMethod.POST)
    @SystemControllerLog(description = "重置用户密码")
    @ResponseBody
    public String updPassword(String userId, HttpServletRequest request, String randomPsw) throws Exception {
        if (userId == null) {
            throw new ControllerException("参数为空，重置密码失败");
        }
        userJpaService.resetPassword(userId, randomPsw);
        User user = userJpaService.findByUserId(userId);
        if (user != null && user.getUserName() != null) {
            request.setAttribute(Constants.AOP_LOG_DESCRIPTION, "重置用户密码【" + user.getUserName() + "】");
        }
        return randomPsw;
    }

    @RequestMapping(value = "setPassword", method = RequestMethod.POST)
    @SystemControllerLog(description = "修改用户密码")
    @ResponseBody
    public ResponseBean setPassword(String userId, String password, String oldPassword
            , HttpServletRequest request) throws Exception {

        ResponseBean responseBean = new ResponseBean(ResponseBean.FAIL);
        if (userId == null) {
            throw new ControllerException("参数为空，修改密码失败");
        }
        User user = userJpaService.findByUserId(userId);

        if (user == null){
            responseBean.setMessage("用户不存在");
            return responseBean;
        }

        if (!oldPassword.equals(user.getPwd().toString())) {
            responseBean.setMessage("初始密码错误！");
            return responseBean;
        }

        userJpaService.resetPassword(userId, password);
        request.setAttribute(Constants.AOP_LOG_DESCRIPTION, "修改用户密码【" + user.getUserName() + "】");
        responseBean.setFlag_success();
        return responseBean;
    }


    @RequestMapping("/viewMyInfo")
    public String viewMyInfo(@RequestParam Map<String, Object> parameterMap, Model model) throws Exception {
        String userId = StringUtils.getString(parameterMap.get("userId"));
        User user = null;
        if (StringHelper.isNotEmpty(userId)) {
            user = userJpaService.findByUserId(userId);
        }

        model.addAttribute("user", user);

        return "system/user/viewMyInfo";
    }

    @RequestMapping("/user_setPassword")
    public String userSetPassword(String userId, Model model) throws Exception {
        User user = null;
        if (StringHelper.isNotEmpty(userId)) {
            user = userJpaService.findByUserId(userId);
        }
        model.addAttribute("user", user);

        return "system/user/user_setPassword";
    }


    /**
     * 获取当前登录用户的信息
     *
     * @return
     */
    @GetMapping("/currentUser")
    @ResponseBody
    public User getCurrentUser() {
        String userId = SpringSecurityUtils.getCurrentUserId();
        if (StringHelper.isNotEmpty(userId)) {
            User user = userJpaService.findByUserId(userId);
            return user;
        }
        return null;
    }

    @RequestMapping("/insert")
    public String insert(@ModelAttribute Page page, Model model) throws Exception {
        return "system/user/user_insert";
    }


    /**
     * user文件导入
     *
     * @param userFile
     * @return
     */
    @PostMapping("/import")
    @ResponseBody
    @SystemControllerLog(description = "用户导入")
    public ResponseBean importExcel(HttpServletRequest request,@RequestParam MultipartFile userFile) {
        ResponseBean response = new ResponseBean(ResponseBean.FAIL);

        List<HSSFRow> deviceRowList;
        try {
            deviceRowList = ExportUtils.readExcel(userFile);
        } catch (IOException e) {
            response.setMessage("用户导入失败,读取excel文件失败");
            request.setAttribute(Constants.AOP_LOG_DESCRIPTION, "用户导入失败,读取excel文件失败");
            return response;
        }
        int count;

        try {
            count = userJpaService.saveUserList(deviceRowList);
        } catch (Exception e) {
            log.error(e.getMessage());
            request.setAttribute(Constants.AOP_LOG_DESCRIPTION, "用户导入失败，保存记录失败");
            response.setMessage(e.getMessage());
            return response;
        }

        request.setAttribute(Constants.AOP_LOG_DESCRIPTION, "用户导入成功导入的个数为【" +count+ "】");
        response.setFlag_success();
        return response;
    }


    /**
     * 导出用户模板文件
     *
     * @param parameterMap 列名列表
     * @return
     */
    @PostMapping("exportUserModelExcel")
    @ResponseBody
    public ResponseBean exportUserModelExcel(HttpSession session,@RequestParam Map<String, Object> parameterMap){
        //设置一行模板数据
        List<List<?>> dataList = getDataList();
        List<String> sheetName = Lists.newArrayList("用户导入模板");

        return ExportUtils.export(dataList,parameterMap,sheetName,session);
    }

    private List<List<?>> getDataList(){
        List<List<?>> dataList = Lists.newArrayList();
        List<User> users = Lists.newArrayList();
        User user = new User();
        user.setAccount("comba");
        user.setTenantName("京信通信有限公司");
        user.setUserName("物联网管理员");
        user.setMsisdn("13711745713");
        user.setEmail("comba@comba.com");

        users.add(user);
        dataList.add(users);
        return dataList;
    }
}
