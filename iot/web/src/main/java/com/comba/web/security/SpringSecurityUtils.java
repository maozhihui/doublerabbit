package com.comba.web.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;





public class SpringSecurityUtils {
    private static Logger logger = LoggerFactory
            .getLogger(SpringSecurityUtils.class);

    protected SpringSecurityUtils() {
    }

    @SuppressWarnings("unchecked")
    public static <T extends UserDetails> T getCurrentUser() {
        Authentication authentication = getAuthentication();

        if (authentication == null) {
            return null;
        }

        Object principal = authentication.getPrincipal();

        if (!(principal instanceof UserDetails)) {
            return null;
        }

        return (T) principal;
    }

    public static String getCurrentUserTenantId() {
    	SpringSecurityUserAuth springSecurityUserAuth = getCurrentUser();
    	if (springSecurityUserAuth == null)
    	    return null;
        return springSecurityUserAuth.getTenantId();
    }
    
    public static String getCurrentUserDisplayName() {
    	SpringSecurityUserAuth springSecurityUserAuth = getCurrentUser();
        return springSecurityUserAuth.getUsername();
    }
    /**
     * 取得当前用户的登录名, 如果当前用户未登录则返回空字符串.
     */
    public static String getCurrentUsername() {
        Authentication authentication = getAuthentication();

        if ((authentication == null) || (authentication.getPrincipal() == null)) {
            return "";
        }
        return authentication.getName();
    }

    /**
     * 取得当前用户的id，如果当前用户与未登录，则返回null.
     */
    public static String getCurrentUserId() {
        SpringSecurityUserAuth springSecurityUserAuth = getCurrentUser();

        if (springSecurityUserAuth == null) {
            return "";
        }

        return springSecurityUserAuth.getUserId();
    }
    
    
    /**
     * 判断当前用户是否超级管理员
     */
    public static boolean isSupperAdmin() {
        SpringSecurityUserAuth springSecurityUserAuth = getCurrentUser();

        if (springSecurityUserAuth == null) {
            return false;
        }

        return springSecurityUserAuth.isSupperAdmin();
    }

    /**
     * 判断当前用户是否租户管理员
     */
    public static boolean isTenantAdmin(){
        SpringSecurityUserAuth auth = getCurrentUser();
        if (auth == null){
            return false;
        }
        return auth.isTenantAdmin();
    }


    /**
     * 判断当前用户是否普通用户
     */
    public static boolean isCommonUser(){
        SpringSecurityUserAuth auth = getCurrentUser();
        if (auth == null){
            return false;
        }
        return auth.isCommonUser();
    }

    /**
     * 通过权限编码判断当前用户是否拥有该权限
     */
    public static boolean hasPriCode(String code) {
        SpringSecurityUserAuth springSecurityUserAuth = getCurrentUser();
        if(code==null || code.length()==0) return true;
        code = code.trim();
        if (springSecurityUserAuth == null) {
            return false;
        }
        Map<String,Integer> priCodeMap = springSecurityUserAuth.getPriCodeMap();
        if(priCodeMap==null) return false;
        return priCodeMap.containsKey(code);
    }
    
    /**
     * 取得当前用户登录IP, 如果当前用户未登录则返回空字符串.
     */
    public static String getCurrentUserIp() {
        Authentication authentication = getAuthentication();

        if (authentication == null) {
            return "";
        }

        Object details = authentication.getDetails();

        if (!(details instanceof WebAuthenticationDetails)) {
            return "";
        }

        WebAuthenticationDetails webDetails = (WebAuthenticationDetails) details;

        return webDetails.getRemoteAddress();
    }

    


    /**
     * 取得Authentication, 如当前SecurityContext为空时返回null.
     */
    public static Authentication getAuthentication() {
        SecurityContext context = SecurityContextHolder.getContext();

        return context.getAuthentication();
    }

    // ~ ======================================================================
    public static boolean hasPermission(String... permissions) {
        if (permissions == null) {
            logger.warn("permission is null");

            return false;
        }

        Collection<String> attributes = getAuthorities();

        for (String permission : permissions) {
            if (attributes.contains(permission)) {
                logger.debug("has : {}", permission);

                return true;
            }
        }

        return false;
    }

    public static boolean hasAllPermissions(String... permissions) {
        if (permissions == null) {
            logger.warn("permissions is null");

            return false;
        }

        Collection<String> attributes = getAuthorities();

        for (String permission : permissions) {
            if (!attributes.contains(permission)) {
                logger.debug("lack : {}", permission);

                return false;
            }
        }

        return true;
    }

    public static boolean lackPermission(String... permissions) {
        if (permissions == null) {
            logger.warn("permissions is null");

            return true;
        }

        Collection<String> attributes = getAuthorities();

        for (String permission : permissions) {
            if (!attributes.contains(permission)) {
                logger.debug("lack : {}", permission);

                return true;
            }
        }

        return false;
    }

    public static boolean lackAllPermissions(String... permissions) {
        if (permissions == null) {
            logger.warn("permissions is null");

            return true;
        }

        Collection<String> attributes = getAuthorities();

        for (String permission : permissions) {
            if (attributes.contains(permission)) {
                logger.debug("has : {}", permission);

                return false;
            }
        }

        return true;
    }

    public static List<String> getAuthorities() {
        Authentication authentication = getAuthentication();

        if (authentication == null) {
            return Collections.EMPTY_LIST;
        }

        Collection<? extends GrantedAuthority> grantedAuthorityList = authentication
                .getAuthorities();

        List<String> authorities = new ArrayList<String>();

        for (GrantedAuthority grantedAuthority : grantedAuthorityList) {
            authorities.add(grantedAuthority.getAuthority());
        }

        return authorities;
    }

    // ~ ======================================================================
    /**
     * 判断用户是否拥有角色, 如果用户拥有参数中的任意一个角色则返回true.
     */
    public static boolean hasRole(String... roles) {
        if (roles == null) {
            logger.warn("roles is null");

            return false;
        }

        Collection<Map<String,Object>> attributes = getRoles();

        for (String role : roles) {
            if (attributes.contains(role)) {
                logger.debug("has : {}", role);

                return true;
            }
        }

        return false;
    }

    public static boolean hasAllRoles(String... roles) {
        if (roles == null) {
            logger.warn("roles is null");

            return false;
        }

        Collection<Map<String,Object>> attributes = getRoles();

        for (String role : roles) {
            if (!attributes.contains(role)) {
                logger.debug("lack : {}", role);

                return false;
            }
        }

        return true;
    }

    public static boolean lackRole(String... roles) {
        if (roles == null) {
            logger.warn("roles is null");

            return true;
        }

        Collection<Map<String,Object>> attributes = getRoles();

        for (String role : roles) {
            if (!attributes.contains(role)) {
                logger.debug("lack : {}", role);

                return true;
            }
        }

        return false;
    }

    public static boolean lackAllRoles(String... roles) {
        if (roles == null) {
            logger.warn("roles is null");

            return true;
        }

        Collection<Map<String,Object>> attributes = getRoles();

        for (String role : roles) {
            if (attributes.contains(role)) {
                logger.debug("has : {}", role);

                return false;
            }
        }

        return true;
    }

    public static Collection<Map<String,Object>> getRoles() {
        Authentication authentication = getAuthentication();

        if (authentication == null) {
            return Collections.EMPTY_LIST;
        }

        Object principal = authentication.getPrincipal();

        if (!(principal instanceof SpringSecurityUserAuth)) {
            logger.debug("principal[{}] is not SpringSecurityUserAuth",
                    principal);

            return Collections.EMPTY_LIST;
        }

        SpringSecurityUserAuth springSecurityUserAuth = (SpringSecurityUserAuth) principal;

        List<Map<String,Object>> roles = springSecurityUserAuth.getRoles();

        return roles;
    }

    // ~ ==================================================

    /**
     * 取得当前用户的id，如果当前用户与未登录，则返回null.
     */
    public static SpringSecurityUserAuth getCurrentUser(
            SecurityContext securityContext) {

        Authentication authentication = securityContext.getAuthentication();

        if (authentication == null) {
            logger.debug("authentication is null");

            return null;
        }

        Object principal = authentication.getPrincipal();

        if (principal == null) {
            logger.info("principal is null");

            return null;
        }

        if (!(principal instanceof SpringSecurityUserAuth)) {
            logger.info("principal {} is not SpringSecurityUserAuth", principal);

            return null;
        }

        SpringSecurityUserAuth springSecurityUserAuth = (SpringSecurityUserAuth) principal;

        return springSecurityUserAuth;
    }
}
