package com.channel.zengpeng.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.channel.zengpeng.dao.UserInfoDAO;
import com.channel.zengpeng.entity.TUserInfo;

@Service
public class UserInfoService {

	@Autowired 
	UserInfoDAO userInfoDAO;
	
	
	public List<TUserInfo> findAll(){
		return userInfoDAO.findAll();
	}
	
}
