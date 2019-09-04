package com.channel.zengpeng.primary.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.channel.zengpeng.primary.dao.PermissionDAO;
import com.channel.zengpeng.primary.dao.RoleInfoDAO;
import com.channel.zengpeng.primary.dao.UserInfoDAO;
import com.channel.zengpeng.primary.entity.TPermission;
import com.channel.zengpeng.primary.entity.TRoleInfo;
import com.channel.zengpeng.primary.entity.TUserInfo;

@Service
public class UserInfoService {

	@Autowired 
	UserInfoDAO userInfoDAO;
	@Autowired 
	PermissionDAO permissionDAO;
	@Autowired 
	RoleInfoDAO roleInfoDAO;
	
	public List<TUserInfo> findAll(){
		return userInfoDAO.findAll();
	}
	
	public TUserInfo findByName(String userName){
		TUserInfo userInfo = userInfoDAO.findByUserName(userName);
		return userInfo;
	}
	
	public TUserInfo findByNameAndPwd(String userName,String passWord){
		List<TUserInfo> list = userInfoDAO.findByUserNameAndPassWord(userName, passWord);
		if(list!=null&&list.size()>0)
			return list.get(0);
		return null;
	}
	
	public boolean selectByNameAndPwd(String userName,String passWord){
		if(userInfoDAO.selectByUserNameAndpassWord(userName, passWord)!=null)
			return true;
		return false;
	}
	
	public List<TPermission> findTPermissionByRoleName(List<String> roleNameList){
		return permissionDAO.findTPermissionByRoleName(roleNameList);
	}
	
	public List<TRoleInfo> findTRoleInfoByByUsername(List<String> username){
		return roleInfoDAO.findTRoleInfoByByUsername(username);
	}
}
