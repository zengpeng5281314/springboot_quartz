package com.channel.zengpeng.usercontroller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.channel.zengpeng.primary.entity.TRoleInfo;
import com.channel.zengpeng.primary.service.UserInfoService;

import net.sf.json.JSONObject;


@RestController
@RequestMapping("/admin")
public class AdminInfoResController {

	private static final Logger logger = LoggerFactory.getLogger(AdminInfoResController.class);
	
	@Autowired
	UserInfoService userInfoService;

	@PostMapping("/doAddRole")
	public String doAddRole(@RequestBody TRoleInfo roleInfo, HttpServletRequest request, HttpServletResponse response,
			ModelMap model) {
		JSONObject json = new JSONObject();
		if (StringUtils.isNotBlank(roleInfo.getName())) {
			roleInfo = userInfoService.saveTRoleInfo(roleInfo);
			json.put("success", true);
			json.put("msg", "成功");
		} else {
			json.put("success", false);
			json.put("msg", "必填参数为不能为空！");
		}
		return json.toString();
	}

}