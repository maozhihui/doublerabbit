package com.comba.web.spring;

import com.comba.server.common.data.User;
import com.comba.server.dao.user.UserService;
import com.comba.web.security.LoginSuccessHandler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.comba.server.common.data.web.utils.Constants.USER_INPUT_CODE;
import static com.comba.server.common.data.web.utils.Constants.VALISATE_CODE;

@Service
public class UserAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;

    @Autowired
    private SessionRegistry sessionRegistry;

    @Value("${userImport.initPwd}")
    private String USER_INIT_PWD;//导入用户的初始密码


    private final static String ACCOUNT_LOGIN = "1";//账号密码方式登录
    private final static String PHONE_LOGIN = "2"; //手机短信方式登录

    private final static String IS_INIT_PWD = "1"; //初始密码
    private final static String IS_NOT_INIT_PWD_ = "0"; //非初始密码

    @PostConstruct
    public void init(){
        this.setAuthenticationManager(authenticationManager);
        this.setAuthenticationSuccessHandler(getLoginSuccessHandler());
        this.setAuthenticationFailureHandler(getSimpleUrlAuthenticationFailureHandler());
        this.setSessionAuthenticationStrategy(sessionAuthenticationStrategy());
    }

    /**
     * 自定义登录验证，根据type区分账号密码和短信登录
     * 1）账号密码登录，使用默认的方式
     * 2）短信验证登录，验证请求的参数code和session里面的验证码是否相同，
     * 相同则从数据库加载用户，填写AuthenticationToken的账号密码
     *
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        String username = "";
        String password = "";
        String type = request.getParameter("type");

        if (ACCOUNT_LOGIN.equals(type)){
            request.getSession().setAttribute("loginType",ACCOUNT_LOGIN);//保存当前登录类型，以便错误时跳转到指定页面
            username = this.obtainUsername(request);
             password = this.obtainPassword(request);
             //记录用户输入的密码，如果是123456,则登录成功后需要重置密码
             if (USER_INIT_PWD.equals(password)){
                 request.getSession().setAttribute("pwdType",IS_INIT_PWD);
             }else{
                 request.getSession().setAttribute("pwdType",IS_NOT_INIT_PWD_);
             }

             username = username.trim();
        }
        else if (PHONE_LOGIN.equals(type)){
            request.getSession().setAttribute("loginType",PHONE_LOGIN);//保存当前登录类型，以便错误时跳转到指定页面
            if(validatePhoneCode(request)){
                String msisdn = request.getParameter("msisdn");
                if (StringUtils.isBlank(msisdn)){
                    throw new BadCredentialsException("phoneNum is wrong");
                }
                msisdn = msisdn.trim();
                User user = userService.findByMsisdn(msisdn);
                if (user == null){
                    throw new AuthenticationCredentialsNotFoundException("user doesn't exit");
                }
                username = user.getAccount();
                password = user.getPwd();
                SessionCounter.saveSession(request,username);
            }else{
                throw new BadCredentialsException("code is wrong");
            }
        }
        sessionRegistry.registerNewSession(request.getSession().getId(),username);
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
        this.setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }


    private boolean validatePhoneCode(HttpServletRequest request) {
        boolean isEqual = false;

        if (request.getSession().getAttribute(VALISATE_CODE) != null){
            String code = request.getSession().getAttribute(VALISATE_CODE).toString();
            String requestCode = request.getParameter(USER_INPUT_CODE);

            if (code.equals(requestCode)) {
                isEqual = true;
            }
        }

        return isEqual;
    }

    private LoginSuccessHandler getLoginSuccessHandler(){
        LoginSuccessHandler handler = new LoginSuccessHandler();
        handler.setDefaultTargetUrl("/index");
        handler.setAlwaysUseDefaultTargetUrl(true);
        return handler;
    }

    private SimpleUrlAuthenticationFailureHandler getSimpleUrlAuthenticationFailureHandler(){
        SimpleUrlAuthenticationFailureHandler handler = new SimpleUrlAuthenticationFailureHandler();
        handler.setDefaultFailureUrl("/login?error=true");
        return handler;
    }

    @Bean
    public ConcurrentSessionControlAuthenticationStrategy sessionAuthenticationStrategy(){
        ConcurrentSessionControlAuthenticationStrategy strategy = new ConcurrentSessionControlAuthenticationStrategy(sessionRegistry);
        strategy.setMaximumSessions(1);
        return strategy;
    }

}
