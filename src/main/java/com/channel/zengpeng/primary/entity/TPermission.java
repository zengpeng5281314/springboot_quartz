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
 * 权限
 * @author ZENGPENG
 *
 */
@Entity
@Table(name = "t_permission")
@Setter
@Getter
public class TPermission {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	@Column(name = "permission_id")
	private String permissionId;
	@Column(name = "name")
	private String name;
	@Column(name = "description")
	private String description;
	@Column(name = "url")
	private String url;
	@Column(name = "perms")
	private String perms;
	@Column(name = "parent_id")
	private String parentId;
	@Column(name = "type")
	private int type;
	@Column(name = "order_num")
	private int orderNum;
	@Column(name = "icon")
	private String icon;
	@Column(name = "status")
	private int status;
	@Column(name = "create_time")
	private Timestamp createTime;
	@Column(name = "update_time")
	private Timestamp updateTime;
}
