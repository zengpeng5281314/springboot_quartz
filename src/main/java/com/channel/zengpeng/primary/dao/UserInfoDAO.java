package com.channel.zengpeng.primary.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.channel.zengpeng.primary.entity.TUserInfo;


public interface UserInfoDAO extends JpaRepository<TUserInfo,Integer>{

	TUserInfo findByUserName(String userName);
	
	List<TUserInfo> findByUserNameAndPassWord(String userName,String passWord);
	

	 @Modifying
	 @Query("update TUserInfo set passWord = ?1 where id= ?2")
	 int updatePassWordById(String passWord, long id);
	 
	 @Transactional(timeout = 10)
	 @Query("from TUserInfo where userName = ?1 and passWord= ?2")
	 TUserInfo selectByUserNameAndpassWord(String userName, String passWord);
}
