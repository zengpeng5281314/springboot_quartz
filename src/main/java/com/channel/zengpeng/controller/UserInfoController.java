package com.channel.zengpeng.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import com.channel.zengpeng.service.UserInfoService;

@Controller
public class UserInfoController {


	@Autowired
	UserInfoService userInfoService;
	
	@GetMapping("/userInfo")
	public String userInfo(HttpServletRequest request,HttpServletResponse response, ModelMap model) {
		model.put("userInfo", userInfoService.findAll());
		return "hello";
	}
	
}
