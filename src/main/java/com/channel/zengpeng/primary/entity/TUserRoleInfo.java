package com.channel.zengpeng.primary.entity;

import java.security.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * 用户
 * @author zengpeng
 *
 */
@Entity
@Table(name = "t_user_role")
@Setter
@Getter
public class TUserRoleInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	@Column(name = "user_id")
	private String userId;
	@Column(name = "role_id")
	private String roleId;
}
