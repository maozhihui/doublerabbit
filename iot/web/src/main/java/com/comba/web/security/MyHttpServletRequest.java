package com.comba.web.security;

import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class MyHttpServletRequest extends HttpServletRequestWrapper{
    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request The request to wrap
     * @throws IllegalArgumentException if the request is null
     */
    public MyHttpServletRequest(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        if (value == null)
            return null;
        return cleanXSS(value);
    }


    @Override
    public String getParameter(String name) {
        String value = super.getParameter(name);
        if (value == null)
            return null;
        return cleanXSS(value);
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if (values != null) {
            int length = values.length;
            String[] escapeValues = new String[length];
            for (int i = 0; i < length; i++) {
                escapeValues[i] = cleanXSS(values[i]);
            }
            return escapeValues;
        }
        return super.getParameterValues(name);
    }

    private static String cleanXSS(String value){
        value = value.replaceAll("eval\\((.*)\\)","");
        value = value.replaceAll("alert\\(.*\\)","");
        value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']","\"\"");
        value = value.replaceAll("script","");
        value = value.replaceAll("document.*","");
        return value;
    }


}
