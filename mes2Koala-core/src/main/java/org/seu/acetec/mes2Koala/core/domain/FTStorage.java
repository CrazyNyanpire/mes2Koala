package org.seu.acetec.mes2Koala.core.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 用于FT入库，WMS
 * @author Eva  
 * @version 2016-4-21
 */
public class FTStorage {

	@JsonProperty
	private String ID=""; 
	@JsonProperty
	private String ACETEC_LOT="";
	@JsonProperty
	private String NUMBER="";
	@JsonProperty
	private String REELCODE="";
	@JsonProperty
	private int QUANTITY;
	@JsonProperty
	private String QUALITY="";    
	@JsonProperty
	private String IS_MERGE=""; 
	@JsonProperty
	private String PARENT_ID=""; 
	@JsonProperty
	private List<FTStorage> CHILDES; 
	@JsonProperty
	private String TEST_ID="";  
	@JsonProperty 
	String SPECIAL_CONTROL="";
	@JsonProperty
	private String CUS_PPO="";
	@JsonProperty
	private String CUS_LOT="";
	@JsonProperty
	private String DATECODE=""; 
	
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	
	public String getACETEC_LOT() {
		return ACETEC_LOT;
	}
	public void setACETEC_LOT(String aCETEC_LOT) {
		ACETEC_LOT = aCETEC_LOT;
	}
		public String getNUMBER() {
		return NUMBER;
	}
	public void setNUMBER(String nUMBER) {
		NUMBER = nUMBER;
	}
	public String getREELCODE() {
		return REELCODE;
	}
	public void setREELCODE(String rEELCODE) {
		REELCODE = rEELCODE;
	}
	public int getQUANTITY() {
		return QUANTITY;
	}
	public void setQUANTITY(int qUANTITY) {
		QUANTITY = qUANTITY;
	}
	public String getQUALITY() {
		return QUALITY;
	}
	public void setQUALITY(String qUALITY) {
		QUALITY = qUALITY;
	}
	public String getIS_MERGE() {
		return IS_MERGE;
	}
	public void setIS_MERGE(String iS_MERGE) {
		IS_MERGE = iS_MERGE;
	}
	public String getPARENT_ID() {
		return PARENT_ID;
	}
	public void setPARENT_ID(String pARENT_ID) {
		PARENT_ID = pARENT_ID;
	}
	public List<FTStorage> getCHILDES() {
		return CHILDES;
	}
	public void setCHILDES(List<FTStorage> cHILDES) {
		CHILDES = cHILDES;
	}
	public String getTEST_ID() {
		return TEST_ID;
	}
	public void setTEST_ID(String tEST_ID) {
		TEST_ID = tEST_ID;
	}
	public String getSPECIAL_CONTROL() {
		return SPECIAL_CONTROL;
	}
	public void setSPECIAL_CONTROL(String sPECIAL_CONTROL) {
		SPECIAL_CONTROL = sPECIAL_CONTROL;
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
	public String getDATECODE() {
		return DATECODE;
	}
	public void setDATECODE(String dATECODE) {
		DATECODE = dATECODE;
	} 
	
}
