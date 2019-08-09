package com.channel.zengpeng.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.channel.zengpeng.primary.entity.TUserInfo;
import com.channel.zengpeng.primary.service.UserInfoService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@RestController
public class TestController {

	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private  com.channel.zengpeng.secondary.service.UserInfoService2 suserInfoService;
	
	@RequestMapping("/test1")
	public String hello(HttpServletRequest request,HttpServletResponse response, ModelMap model) {
		model.put("now", new Date());
		List<TUserInfo> list = userInfoService.findAll();
		List<com.channel.zengpeng.secondary.entity.TUserInfo> list2 = suserInfoService.findAll();
		JSONObject json = new JSONObject();
		json.put("list", JSONArray.fromObject(list));
		json.put("list2", JSONArray.fromObject(list2));
		return json.toString();
	}
	
	
}
