package com.channel.zengpeng.usercontroller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.channel.zengpeng.primary.entity.JobEntity;
import com.channel.zengpeng.primary.entity.TRoleInfo;
import com.channel.zengpeng.primary.service.DynamicJobService;
import com.channel.zengpeng.primary.service.QuartzJobManager;
import com.channel.zengpeng.primary.service.UserInfoService;

@Controller
@RequestMapping("/admin")
public class AdminInfoController {

	private static final Logger logger = LoggerFactory.getLogger(AdminInfoController.class);
	
	@Autowired
	private UserInfoService userSerInfoService;

	@GetMapping("/rolelist")
	public String joblist(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		List<TRoleInfo> roleInfolist = userSerInfoService.findAllTRoleInfo();
		model.put("roleInfolist", roleInfolist);
		return "/admin/rolelist";
	}

//	@RequiresRoles(value = {"admin"}, logical = Logical.OR)
//    @RequiresPermissions(value = {"user:list"}, logical = Logical.OR)
	@GetMapping("/addrole")
	public String addjob(@RequestParam(defaultValue="",name="roleId") String roleId, HttpServletRequest request, HttpServletResponse response,
			ModelMap model) {
		int type=0;
		TRoleInfo roleInfo = new TRoleInfo();
		if(!roleId.equals("")){
			roleInfo = userSerInfoService.findTRoleInfoByRoleId(roleId);
			type=1;
		}
		model.put("roleInfo", roleInfo);
		model.put("type", type);
		return "/admin/addrole";
	}


}