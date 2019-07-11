package com.channel.zengpeng.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.channel.zengpeng.entity.TUserInfo;

public interface UserInfoDAO extends JpaRepository<TUserInfo,Integer>{

	
}
