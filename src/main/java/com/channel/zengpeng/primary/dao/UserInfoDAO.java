package com.channel.zengpeng.primary.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.channel.zengpeng.primary.entity.TUserInfo;


public interface UserInfoDAO extends JpaRepository<TUserInfo,Integer>{

	
}
