package org.seu.acetec.mes2Koala.core.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class T_RUNCARDMERGER {
	@JsonProperty
	private String ID="";
	@JsonProperty
	private String IN_ID="";
	@JsonProperty
	private String TEST_ID="";
	@JsonIgnore
	public String getID() {
		return ID;
	}
	@JsonIgnore
	public void setID(String iD) {
		ID = iD;
	}
	@JsonIgnore
	public String getIN_ID() {
		return IN_ID;
	}
	@JsonIgnore
	public void setIN_ID(String iN_ID) {
		IN_ID = iN_ID;
	}
	@JsonIgnore
	public String getTEST_ID() {
		return TEST_ID;
	}
	@JsonIgnore
	public void setTEST_ID(String tEST_ID) {
		TEST_ID = tEST_ID;
	}
	
}
