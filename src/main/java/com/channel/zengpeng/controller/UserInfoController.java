package com.channel.zengpeng.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserInfoController {

	@GetMapping("/quickDemo")
	public String quickDemo() {
		return "this is quick demo for Spring Boot!";
	}
	
}
