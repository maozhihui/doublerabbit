package com.comba.web.security.jwt;

import lombok.Getter;

import java.io.Serializable;

/**
 * JwtAuthenticationResponse
 * 鉴权响应对象
 * @author maozhihui
 * @create 2017-10-20 15:41
 **/
public class JwtAuthenticationResponse implements Serializable {
    private static final long serialVersionUID = 1250166508152483573L;

    @Getter private final String token;

    public JwtAuthenticationResponse(String token) {
        this.token = token;
    }
}
