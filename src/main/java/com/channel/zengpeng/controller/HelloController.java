package com.channel.zengpeng.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {

	@Value("${person.age}")
	private String personAge;
	
	@RequestMapping("/hello")
	public String hello(HttpServletRequest request,HttpServletResponse response, ModelMap model) {
		model.put("now", new Date());
		System.out.println("----"+personAge);
		return "hello";
	}
	
}
