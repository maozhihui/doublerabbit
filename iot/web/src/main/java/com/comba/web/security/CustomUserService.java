package com.comba.web.security;

import com.comba.server.dao.common.BaseDao;
import com.comba.server.dao.model.UserEntity;
import com.comba.server.dao.user.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

;

/**
 * 初始化
 *
 * @version 
 * @author wengzhonghui  2017年6月14日 下午4:28:24
 * 
 */
@Service
public class CustomUserService implements UserDetailsService { //自定义UserDetailsService 接口

	@Resource
	BaseDao baseDao;
    @Autowired
    UserDao UserJpaDao;

    public UserDetails loadUserByUsername(String username) {
    	UserEntity user = null;
    	 List<UserEntity> userList = UserJpaDao.findByAccount(username);
        if(userList!=null && userList.size()>0){
        	user = userList.get(0);
        }
        if (user != null) {
        	List<GrantedAuthority> grantedAuthorities = new ArrayList <>();
            SpringSecurityUserAuth userAuth = new SpringSecurityUserAuth(user.getAccount(), user.getPwd(), grantedAuthorities);
            userAuth.setUserId(user.getUserId());
            Map<String,Integer> priCodeMap = getPriCodeMap(user.getUserId());
            userAuth.setPriCodeMap(priCodeMap);// TODO  需要加载拥有的权限编码
            userAuth.setUserType(user.getType());
            userAuth.setTenantId(user.getTenantId());
            return userAuth;
        } else {
            throw new UsernameNotFoundException("admin: " + username + " do not exist!");
        }
    }
    
	/**
	 * TODO 待处理，需要从数据库中加载用户拥有的权限 
	 * 
	 * @author wengzhonghui 2017年6月14日
	 * @param userId
	 * @return
	 */
	public Map<String,Integer> getPriCodeMap(String userId){
		Map<String,Integer> map = new HashMap<String,Integer>();
		return map;
	}

}
