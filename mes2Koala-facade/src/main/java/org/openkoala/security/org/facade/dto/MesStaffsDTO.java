package org.openkoala.security.org.facade.dto;

import java.io.Serializable;

public class MesStaffsDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6361034277909065599L;

	private String id; // 用户ID

	private String name;// 姓名

	private String accounts;// 编号

	private Long deptId;// 部门
	
	private String deptIds;// 部门

	private String deptName;// 部门名称

	private String email;// 电子邮箱
	
	private String mobile; //手机

	private String sex; //性别 0 男 1 女
	
	private String userPriv;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccounts() {
		return accounts;
	}

	public void setAccounts(String accounts) {
		this.accounts = accounts;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getDeptIds() {
		return this.deptIds;
	}

	public void setDeptIds(String deptIds) {
		this.deptIds = deptIds;
	}

	public String getUserPriv() {
		return userPriv;
	}

	public void setUserPriv(String userPriv) {
		this.userPriv = userPriv;
	}
	
	
}