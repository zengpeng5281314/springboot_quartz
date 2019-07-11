package com.channel.zengpeng.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserInfoRestController {

	@Value("${person.age}")
	private String personAge;
	
	@GetMapping("/quickDemo")
	public String quickDemo() {
		System.out.println("----"+personAge);
		return "this is quick demo for Spring Boot!"+personAge;
	}
	
}
