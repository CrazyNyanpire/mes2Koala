package org.seu.acetec.mes2Koala.core.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FtTestLot {
	@JsonProperty
	private String ID=""; 
	@JsonProperty
	private String CUS_PPO=""; 
	@JsonProperty
	private String CUS_LOT="";
	@JsonProperty
	private String ACETEC_LOT="";
	@JsonProperty
	private String CUS_CODE=""; 
	@JsonProperty
	private String IN_PARTNUM="";
	@JsonProperty
	private String OUT_PARTNUM="";
	@JsonProperty
	private Long QUANTITY;   
	@JsonProperty
	private String IN_ID="";  
	@JsonProperty
	private List<T_RUNCARDMERGER> MERGER;
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getCUS_PPO() {
		return CUS_PPO;
	}
	public void setCUS_PPO(String cUS_PPO) {
		CUS_PPO = cUS_PPO;
	}
	public String getCUS_LOT() {
		return CUS_LOT;
	}
	public void setCUS_LOT(String cUS_LOT) {
		CUS_LOT = cUS_LOT;
	}
	public String getACETEC_LOT() {
		return ACETEC_LOT;
	}
	public void setACETEC_LOT(String aCETEC_LOT) {
		ACETEC_LOT = aCETEC_LOT;
	}
	public String getCUS_CODE() {
		return CUS_CODE;
	}
	public void setCUS_CODE(String cUS_CODE) {
		CUS_CODE = cUS_CODE;
	}
	public String getIN_PARTNUM() {
		return IN_PARTNUM;
	}
	public void setIN_PARTNUM(String iN_PARTNUM) {
		IN_PARTNUM = iN_PARTNUM;
	}
	public String getOUT_PARTNUM() {
		return OUT_PARTNUM;
	}
	public void setOUT_PARTNUM(String oUT_PARTNUM) {
		OUT_PARTNUM = oUT_PARTNUM;
	}
	public Long getQUANTITY() {
		return QUANTITY;
	}
	public void setQUANTITY(Long qUANTITY) {
		QUANTITY = qUANTITY;
	}
	public String getIN_ID() {
		return IN_ID;
	}
	public void setIN_ID(String iN_ID) {
		IN_ID = iN_ID;
	}
	 
	public List<T_RUNCARDMERGER> getMERGER() {
		return MERGER;
	}
	public void setMERGER(List<T_RUNCARDMERGER> mERGER) {
		MERGER = mERGER;
	} 
	
} 
