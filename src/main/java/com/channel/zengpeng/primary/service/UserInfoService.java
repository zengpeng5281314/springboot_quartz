package com.channel.zengpeng.primary.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.channel.zengpeng.primary.dao.UserInfoDAO;
import com.channel.zengpeng.primary.entity.TUserInfo;

@Service
public class UserInfoService {

	@Autowired 
	UserInfoDAO userInfoDAO;
	
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
	
}
