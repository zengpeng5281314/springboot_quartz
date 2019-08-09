package com.channel.zengpeng.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.channel.zengpeng.primary.service.UserInfoService;

import net.sf.json.JSONObject;

@Controller
public class IndexController {

	@Autowired
	UserInfoService userInfoService;

	@RequestMapping("/index")
	public String login(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		return "index";
	}

	@PostMapping("/doindex")
	@ResponseBody
	public String dologin(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		JSONObject json = new JSONObject();
		if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)) {
			if (username.equals("admin") && password.equals("123456")) {
				json.put("success", true);
				json.put("msg", "成功");
			} else {
				json.put("success", false);
				json.put("msg", "用户名密码不正确！");
			}
		} else {
			json.put("success", false);
			json.put("msg", "用户名密码不能为空！");
		}
		return json.toString();
	}
}
