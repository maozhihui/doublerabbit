package com.comba.web.security.jwt;

import com.comba.web.security.SpringSecurityUserAuth;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JwtTokenUtil
 * JWT工具类
 * @author maozhihui
 * @create 2017-10-20 15:39
 **/
@Slf4j
@Component
public class JwtTokenUtil implements Serializable {

    private static final long serialVersionUID = -3301605591108950415L;

    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "created";
    private static final String CLAIM_KEY_ID = "id";

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String getUsernameFromToken(String token) {
        String username;
        try {
            final Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    public String getIdFromToken(String token) {
        String id = "";
        try {
            final Claims claims = getClaimsFromToken(token);
            id = claims.get(CLAIM_KEY_ID,String.class);
        } catch (Exception e) {
            log.error("get tenantId from token [{}] failed,cause [{}]",token,e.getMessage());
        }
        return id;
    }

    public Date getCreatedDateFromToken(String token) {
        Date created;
        try {
            final Claims claims = getClaimsFromToken(token);
            created = new Date((Long) claims.get(CLAIM_KEY_CREATED));
        } catch (Exception e) {
            created = null;
        }
        return created;
    }

    public Date getExpirationDateFromToken(String token) throws ExpiredJwtException {
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(token);
            if (claims != null)
                expiration = claims.getExpiration();
            else
                expiration = null;
        } catch (ExpiredJwtException exp){
            throw exp;
        }
        return expiration;
    }

    private Claims getClaimsFromToken(String token) throws ExpiredJwtException {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        }catch (ExpiredJwtException exp){
            throw exp;
        }catch (Exception e) {
            log.error("parse claims from token [{}] failed,exception cause [{}]",token,e.getMessage());
            claims = null;
        }
        return claims;
    }

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    private Boolean isTokenExpired(String token) {
        boolean res = false;
        try {
            final Date expiration = getExpirationDateFromToken(token);
            if (expiration == null){
                return true;
            }
            res = expiration.before(new Date());
        }catch (ExpiredJwtException e){
            res = true;
            log.error("token [{}] expired.",token);
        }
        return res;
    }

    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED, new Date());
        if (userDetails instanceof SpringSecurityUserAuth){
            claims.put(CLAIM_KEY_ID,((SpringSecurityUserAuth)userDetails).getTenantId());
        }
        return generateToken(claims);
    }

    String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
        final Date created = getCreatedDateFromToken(token);
        return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
                && !isTokenExpired(token);
    }

    public String refreshToken(String token) {
        String refreshedToken;
        try {
            final Claims claims = getClaimsFromToken(token);
            claims.put(CLAIM_KEY_CREATED, new Date());
            refreshedToken = generateToken(claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    /*public Boolean validateToken(String token, UserDetails userDetails) {
        JwtUser user = (JwtUser) userDetails;
        final String username = getUsernameFromToken(token);
        final Date created = getCreatedDateFromToken(token);
        //final Date expiration = getExpirationDateFromToken(token);
        return (
                username.equals(user.getUsername())
                        && !isTokenExpired(token)
                        //&& !isCreatedBeforeLastPasswordReset(created, user.getLastPasswordResetDate())
                        );
    }*/

    public boolean validateToken(String token){
        boolean res = false;
        if (StringUtils.isBlank(token)){
            log.error("token [{}] format error.",token);
            return res;
        }
        return (!isTokenExpired(removeHeader(token)));
    }

    /**
     * 原始token带有"Bearer "头，操作前需要先移除此头部信息
     * @param token
     * @return
     */
    public String removeHeader(String token){
        if (token.startsWith(tokenHead))
            return token.substring(tokenHead.length());
        else
            return token;
    }
}

