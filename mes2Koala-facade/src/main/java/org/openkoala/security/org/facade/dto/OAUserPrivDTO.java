package org.openkoala.security.org.facade.dto;

import java.io.Serializable;

public class OAUserPrivDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6361034277909065589L;

	private String userPriv;// 职务编号

	private String privName;// 职务名称

	public String getUserPriv() {
		return userPriv;
	}

	public void setUserPriv(String userPriv) {
		this.userPriv = userPriv;
	}

	public String getPrivName() {
		return privName;
	}

	public void setPrivName(String privName) {
		this.privName = privName;
	}
	
	
}