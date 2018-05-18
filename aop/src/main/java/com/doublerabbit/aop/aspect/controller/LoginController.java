package com.doublerabbit.aop.aspect.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.doublerabbit.aop.annotation.SystemLog;

@RestController
public class LoginController {

	@SystemLog(description = "gobal login")
	@GetMapping(value = "/login")
	public String login(String username,String pwd) throws Exception{
		System.out.println("enter login function====");
		if (username.equals("123")) {
			throw new Exception("not such user.");
		}
		return "success";
	}
	
}
