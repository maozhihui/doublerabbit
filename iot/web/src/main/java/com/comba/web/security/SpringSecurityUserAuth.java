package com.comba.web.security;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.comba.server.common.data.user.UserEnum.*;

@Data
public class SpringSecurityUserAuth extends User {
	
	private static final long serialVersionUID = 389641263373558225L;
	

	public SpringSecurityUserAuth(String username, String password,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}

    private Integer userType;//用户类型
    private Map<String,Integer> priCodeMap;
    private String userId; 
    private String tenantId;//租户ID
    private List<Map<String,Object>> roles;//拥有的角色组合

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isAccountNonExpired() {
        return true;
    }

	/**
	 * 是否超级管理员
	 * 
	 * @author wengzhonghui 2017年6月14日
	 * @return
	 */
	public boolean isSupperAdmin() {
		return USER_TYPE_SUPER_ADMIN.getType().equals(userType);
	}

	/**
	 * 是否为超级管理员添加的租户管理员
	 * 
	 * @author wengzhonghui 2017年6月14日
	 * @return
	 */
	public boolean isTenantAdmin() {
		return USER_TYPE_TENANT_ADMIN.getType().equals(userType);
	}

	/**
	 * 是否普通用户
	 * 
	 * @author wengzhonghui 2017年6月14日
	 * @return
	 */
	public boolean isCommonUser() {
		return USER_TYPE_NORMAL.getType().equals(userType);
	}


    @Override
    public boolean equals(Object o) {
        return this.toString().equals(o.toString());
    }

    @Override
    public int hashCode() {
        return this.getUsername().hashCode();
    }

    @Override
    public String toString() {
        return this.getUsername();
    }
}
