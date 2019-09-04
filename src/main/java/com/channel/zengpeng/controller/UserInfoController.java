package com.channel.zengpeng.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.channel.zengpeng.primary.entity.TUserInfo;
import com.channel.zengpeng.primary.service.UserInfoService;

import net.sf.json.JSONObject;

@Controller
public class UserInfoController {

	@Autowired
	UserInfoService userInfoService;

	@RequestMapping("/login")
	public String login(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		return "login";
	}

	@PostMapping("/dologin")
	@ResponseBody
	public String dologin(HttpServletRequest request, HttpServletResponse response,@RequestBody Map map, ModelMap model) {
		
		String username =  map.get("username").toString();
		String password =  map.get("password").toString();
		JSONObject json = new JSONObject();
		String msg = "成功！";
		boolean success = true;
		if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)) {
			Subject subject = SecurityUtils.getSubject();
			UsernamePasswordToken token = new UsernamePasswordToken(username, password);

			if (!subject.isAuthenticated()) {
				try {
					subject.login(token);
				} catch (UnknownAccountException uae) {
					msg = "未知账户";
					success = false;
				} catch (IncorrectCredentialsException ice) {
					msg = "密码不正确";
					success = false;
				} catch (LockedAccountException lae) {
					msg = "账户已锁定";
					success = false;
				} catch (ExcessiveAttemptsException eae) {
					msg = "用户名或密码错误次数过多";
					success = false;
				} catch (AuthenticationException ae) {
					msg = "用户名或密码不正确！";
					success = false;
				}
				
			}
		} else {
			msg = "用户名密码不能为空~";
			success = false;
		}

		json.put("success", success);
		json.put("msg", msg);
		return json.toString();
	}

	
	
	@RequestMapping("/notRole")
	public String notRole(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		return "notRole";
	}
	
	@RequestMapping("/logout")
	public String logout(TUserInfo loginUser) {
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		return "已注销";
	}
}
