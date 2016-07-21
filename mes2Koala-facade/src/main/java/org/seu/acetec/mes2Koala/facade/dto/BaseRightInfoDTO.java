package org.seu.acetec.mes2Koala.facade.dto;

import java.io.Serializable;

public class BaseRightInfoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3840888991524557274L;

	private String username;

	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
