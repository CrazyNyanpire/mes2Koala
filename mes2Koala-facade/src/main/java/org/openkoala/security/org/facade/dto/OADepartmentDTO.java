package org.openkoala.security.org.facade.dto;

import java.io.Serializable;

public class OADepartmentDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6361034277909065599L;

	private Long id; // 平台或设备id

	private String deptName;// 状态

	private String deptNo;// 分类
	
	private Long deptParentId;
	
	private String depParentNo;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDeptNo() {
		return deptNo;
	}

	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}

	public Long getDeptParentId() {
		return deptParentId;
	}

	public void setDeptParentId(Long deptParentId) {
		this.deptParentId = deptParentId;
	}

	public String getDepParentNo() {
		return depParentNo;
	}

	public void setDepParentNo(String depParentNo) {
		this.depParentNo = depParentNo;
	}

	
}