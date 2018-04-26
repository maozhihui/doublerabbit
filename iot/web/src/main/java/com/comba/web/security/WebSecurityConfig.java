package com.comba.web.security;

import com.comba.web.spring.UserAuthenticationFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.ConcurrentSessionFilter;
import org.springframework.security.web.session.SimpleRedirectSessionInformationExpiredStrategy;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.annotation.Resource;


/**
 * 继承 WebSecurityConfigurerAdapter ，并重写它的方法来设置一些web安全的细节
 * 
 * @author wengzhonghui
 *
 */
@Slf4j
@Configuration
@EnableWebSecurity//通过 @EnableWebMvcSecurity 注解开启Spring Security的功能
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private MyFilterSecurityInterceptor myFilterSecurityInterceptor;

	@Autowired
    @Qualifier(value = "customUserService")
    private CustomUserService customUserService;

	@Autowired
	private UserAuthenticationFilter userAuthenticationFilter;

	@Resource
    private SessionRegistry sessionRegistry;

	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
		//http.csrf().disable();//Cross-site request forgery跨站请求伪造
        http.csrf().requireCsrfProtectionMatcher(new MyCsrfMatcher());
        http.headers().frameOptions().disable();//坑，允许加载iframe设置
        http.addFilterAt(userAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterAt(new ConcurrentSessionFilter(sessionRegistry,new SimpleRedirectSessionInformationExpiredStrategy("/login")),ConcurrentSessionFilter.class);
        //在正确的位置添加我们自定义的过滤器
  		http.addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class);
		http.authorizeRequests()
				.antMatchers("/css/**").permitAll()
				.antMatchers("/js/**").permitAll()
				.antMatchers("/images/**").permitAll()
				.antMatchers("/plugins/**").permitAll()
				.antMatchers("/login/verifyImg/**").permitAll()
				.antMatchers("/findPassword/**").permitAll()
				.antMatchers("/register/**").permitAll()
				.antMatchers("/api/**").permitAll()
				.antMatchers("/configAttributes/**").permitAll()
				.antMatchers("/auth/validateCode").permitAll()
				.antMatchers("/swagger-ui.html/**").permitAll()
				.antMatchers("/webjars/**").permitAll()
				.antMatchers("/swagger-resources/**").permitAll()
				.antMatchers("/activeSession").permitAll()
				.antMatchers("/v2/api-docs/**").permitAll()
				.antMatchers("/sendCode").permitAll()
				.antMatchers("/resetPassword").permitAll()
				.anyRequest().authenticated() //任何请求,登录后可以访问
				.and()
				.formLogin()
				.loginProcessingUrl("/login")
				.loginPage("/login")
				.failureUrl("/login?error=true")
				.permitAll() //登录页面用户任意访问,一定要记得写，要不出现不了错误界面
				.and()
				.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/loginOut"))
				.logoutSuccessUrl("/login")
				.invalidateHttpSession(true) //是否清除Http session中的内容
				.permitAll() //登录页面用户任意访问
				.and()
				.logout().permitAll(); //注销行为任意访问
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserService); //user Details Service验证
    }

    @Bean
    public SessionRegistry sessionRegistry(){
        return new SessionRegistryImpl();
    }
}
