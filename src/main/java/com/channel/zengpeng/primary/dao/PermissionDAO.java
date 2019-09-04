package com.channel.zengpeng.primary.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.channel.zengpeng.primary.entity.TPermission;


public interface PermissionDAO extends JpaRepository<TPermission,Integer>{

	@Query("select s from TPermission s inner join TRolePermissionInfo a on a.permissionId = s.permissionId inner join TRoleInfo b on b.roleId = a.roleId where b.name in (?1)")
    List<TPermission> findTPermissionByRoleName(List<String> roleNameList);
	
}
