package com.channel.zengpeng.config;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.channel.zengpeng.primary.entity.TPermission;
import com.channel.zengpeng.primary.entity.TRoleInfo;
import com.channel.zengpeng.primary.entity.TUserInfo;
import com.channel.zengpeng.primary.service.UserInfoService;

public class CustomRealm extends AuthorizingRealm {

	@Autowired
	private UserInfoService userInfoService;

	/**
	 * 获取授权信息
	 *
	 * @param principalCollection
	 * @return
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		System.out.println("-------授权--------");
		String username = (String) SecurityUtils.getSubject().getPrincipal();
		System.out.println("-------" + username + "--------");
		// TUserInfo userInfo = userInfoService.findByName(username);
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		// 设置权限
		Set<String> tPermissionSet = new HashSet<>();
		// 设置角色
		Set<String> rolesSet = new HashSet<>();

		List<String> userNameList = new ArrayList<>();
		userNameList.add(username);
		// 获取角色
		List<TRoleInfo> listTRoleInfo = userInfoService.findTRoleInfoByUsername(userNameList);
		List<String> roleNameList = new ArrayList<>();// 角色名称
		for (TRoleInfo tRoleInfo : listTRoleInfo) {
			if (!rolesSet.contains(tRoleInfo.getName()))
				rolesSet.add(tRoleInfo.getName());
			roleNameList.add(tRoleInfo.getName());
		}
		List<TPermission> listTPermission = userInfoService.findTPermissionByRoleName(roleNameList);
		for (TPermission tPermission : listTPermission) {
			if (!tPermissionSet.contains(tPermission.getName()))
				tPermissionSet.add(tPermission.getName());
		}
		// stringSet.add("user:list");
		info.setStringPermissions(tPermissionSet);
		info.setRoles(rolesSet);
		return info;
	}

	/**
	 * 这里可以注入userService,为了方便演示，我就写死了帐号了密码 private UserService userService;
	 * <p>
	 * 获取即将需要认证的信息
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
			throws AuthenticationException {
		System.out.println("-------身份认证方法--------");
		String userName = (String) authenticationToken.getPrincipal();
		if (userName == null)
			throw new AccountException("用户名不正确");
		String userPwd = new String((char[]) authenticationToken.getCredentials());

		TUserInfo userInfo = userInfoService.findByName(userName);
		if (userInfo == null) {
			throw new AccountException("用户名不正确");
		} else if (!userInfo.getPassWord().equals(userPwd)) {
			throw new AccountException("密码不正确");
		}
		SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(userName, userPwd, getName());

		return simpleAuthenticationInfo;
	}
}
