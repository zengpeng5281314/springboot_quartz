package com.channel.zengpeng.primary.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.channel.zengpeng.primary.entity.TRoleInfo;


public interface RoleInfoDAO extends JpaRepository<TRoleInfo,Integer>{

	@Query("select s from TRoleInfo s inner join TUserRoleInfo a on a.roleId = s.roleId inner join TUserInfo b on b.userId = a.userId where b.userName = ?1")
    List<TRoleInfo> findTRoleInfoByUsername(List<String> username);
	
	TRoleInfo findTRoleInfoByRoleId(String roleId);
}
