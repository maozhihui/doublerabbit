package com.comba.web.spring;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 此类用于统计系统的在线人数和判断是否在线
 *
 */
@WebListener
public class SessionCounter implements HttpSessionListener {

    private static ConcurrentMap<String, Object> sessionIdMap = new ConcurrentHashMap<>();

    public static void saveSession(HttpServletRequest request,String account){
        String sessionId = request.getSession().getId();
        sessionIdMap.put(sessionId,account);
    }

    public static boolean getSession(String account){
        return sessionIdMap.containsValue(account);
    }

    public static int getActiveSession(){
        return sessionIdMap.size();
    }

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        if (!sessionIdMap.isEmpty()){
            sessionIdMap.remove(httpSessionEvent.getSession().getId());
        }
    }
}
