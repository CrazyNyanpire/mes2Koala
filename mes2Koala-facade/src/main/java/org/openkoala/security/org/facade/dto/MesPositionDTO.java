package org.openkoala.security.org.facade.dto;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * 职务DTO
 */
public class MesPositionDTO implements Serializable {

	private static final long serialVersionUID = -6538960709126681092L;

	private Long id;

	private String postCode;

	private String postName;

	private String postDept;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}

	public String getPostDept() {
		return postDept;
	}

	public void setPostDept(String postDept) {
		this.postDept = postDept;
	}

	@Override
	public String toString() {
		return "PositionDTO [id=" + id + ", name=" + postName + "]";
	}

}
