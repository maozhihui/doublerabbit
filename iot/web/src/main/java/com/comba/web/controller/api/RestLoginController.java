package com.comba.web.controller.api;

import com.comba.server.exception.IoTErrorCode;
import com.comba.server.common.msg.rest.ResponseEntity;
import com.comba.web.security.CustomUserService;
import com.comba.web.security.jwt.JwtAuthenticationRequest;
import com.comba.web.security.jwt.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * RestLoginController
 * 负责开放接口的注册与登陆，重新刷新TOKEN操作
 * @author maozhihui
 * @create 2017-10-20 15:41
 **/
@Slf4j
@RestController
public class RestLoginController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private CustomUserService customUserService;

    @PostMapping(value = "${jwt.route.authentication.path}",produces = "application/json")
    public ResponseEntity<?> login(@RequestBody JwtAuthenticationRequest authRequest){
        log.info("rest login auth,username [{}],password [{}]",authRequest.getUsername(),authRequest.getPassword());
        try {
            String token = login(authRequest.getUsername(),authRequest.getPassword());
            StringBuilder data = new StringBuilder("{\"token\":\"");
            data.append(token).append("\"}");
           return new ResponseEntity<>(IoTErrorCode.SUCCESS.getErrorCode(),"success",data.toString());
        }catch (BadCredentialsException e){
            log.error(e.getMessage());
            return new ResponseEntity<>(IoTErrorCode.INVALID_ARGUMENTS.getErrorCode(),e.getMessage(),"");
        }catch (UsernameNotFoundException e1){
            log.error(e1.getMessage());
            return new ResponseEntity<>(IoTErrorCode.ITEM_NOT_FOUND.getErrorCode(),e1.getMessage(),"");
        }
    }

    private String login(String userName,String password) throws AuthenticationException{
        UserDetails userDetails = customUserService.loadUserByUsername(userName);
        if (userDetails != null){
            log.info("loadUserByUsername userName [{}],password [{}]",userDetails.getUsername(),userDetails.getPassword());
            if (userName.equals(userDetails.getUsername())
                    && password.equals(userDetails.getPassword())){
                return jwtTokenUtil.generateToken(userDetails);
            }else {
                throw new BadCredentialsException("username or password invalid.");
            }
        }else {
            throw new UsernameNotFoundException(userName+" is not exists.");
        }
    }

}
