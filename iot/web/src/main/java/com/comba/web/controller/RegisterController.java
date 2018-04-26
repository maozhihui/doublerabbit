package com.comba.web.controller;


import com.comba.server.common.data.User;
import com.comba.server.common.data.user.UserEnum;
import com.comba.server.dao.common.utils.StringUtils;
import com.comba.server.dao.user.UserService;
import com.comba.web.exception.ControllerException;
import com.comba.web.response.ResponseBean;
import org.hibernate.internal.util.StringHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

import static com.comba.server.common.data.web.utils.Constants.VALISATE_CODE;

@Controller
@RequestMapping("/register")
public class RegisterController {


    @Resource
    private UserService userJpaService;

    /**
     * 跳转注册页面
     *
     * @return
     */
    @GetMapping("jumpToPage")
    public String register(){
        return "register";
    }

    /**
     * 用户注册
     *
     * @param user 用户
     * @return
     */
    @PostMapping("newUser")
    @ResponseBody
    public ResponseBean login(User user, HttpSession session) {
        ResponseBean responseBean = new ResponseBean();
        if (user == null || StringUtils.isBlank(user.getAccount())) {
            responseBean.setMessage("账号不能为空！");
            responseBean.setFlag_fail();
            return responseBean;
        }

        if (session.getAttribute(VALISATE_CODE) == null || !session.getAttribute(VALISATE_CODE).toString().equals(user.getMsCaption())){
            responseBean.setMessage("验证码输入错误！");
            responseBean.setFlag_fail();
            return responseBean;
        }

        List<User> users = userJpaService.findByAccount(user.getAccount());
        if (users != null && users.size() > 0) {
            responseBean.setMessage("账号已经存在！");
            responseBean.setFlag_fail();
            return responseBean;
        }

        try{
            user.setType(UserEnum.USER_TYPE_TENANT_ADMIN.getType());
            userJpaService.saveTenantAndUser(user);
            responseBean.setFlag_success();
        } catch (Exception e){
            responseBean.setMessage("数据库保存账号失败,具体信息="+e.getMessage());
            responseBean.setFlag_fail();
        }
        return responseBean;
    }

    @ResponseBody
    @RequestMapping(value = "/validateAccount")
    public String validateAccount(String account) throws Exception {
        if (StringHelper.isEmpty(account))
            throw new ControllerException("参数为空,验证用户账号失败");

        List<User> users = userJpaService.findByAccount(account);
        if (users != null && users.size() > 0) {
            return  "no";
        } else {
            return "yes";
        }
    }

    @ResponseBody
    @RequestMapping(value = "/validatePhone")
    public String validatePhone(String account,String msisdn) throws Exception {
        if (StringHelper.isEmpty(msisdn))
            throw new ControllerException("参数为空,验证用户账号失败");

        String ret = "yes";
        if (StringHelper.isEmpty(account)){
            if (userJpaService.countByMsisdn(msisdn) > 0) {
                ret = "no";
            }
        }else{
            User user = userJpaService.findByAccount(account).get(0);
            if (user != null && !user.getMsisdn().equals(msisdn)
                    && userJpaService.countByMsisdn(msisdn) > 0){
                    ret = "no";
            }
        }

        return ret;
    }


}
