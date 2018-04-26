package com.comba.web.security;

import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;


public class MyCsrfMatcher implements RequestMatcher {


    private Pattern allowMethods = Pattern.compile("^(GET|HEAD|TRACE|OPTIONS)$");

    private Pattern unProtectMethods = Pattern.compile("^(/login|/api/auth|/api/v1/.*|/user/import|/device/importExcel|/appVersion/import)$");

    private RegexRequestMatcher unProtectMatcher = new RegexRequestMatcher(unProtectMethods.toString(),null);

    /**
     * Decides whether the rule implemented by the strategy matches the supplied request.
     *
     * @param request the request to check for a match
     * @return true if the request matches, false otherwise
     */
    @Override
    public boolean matches(HttpServletRequest request) {
        if (allowMethods.matcher(request.getMethod()).matches()){
            return false;
        }

        return !unProtectMatcher.matches(request);
    }
}
