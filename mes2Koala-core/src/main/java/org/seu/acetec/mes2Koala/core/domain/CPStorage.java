package org.seu.acetec.mes2Koala.core.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * 用于CP入库，WMS
 * @author Eva
 *@version 2016-4-19
 */
public class CPStorage {

	@JsonProperty String  ID=""; 
	@JsonProperty String  CUS_PPO="";
	@JsonProperty String  CUS_LOT="";
	@JsonProperty String  CUS_CODE=""; 
	@JsonProperty String  IN_PARTNUM="";
	@JsonProperty int  QUANTITY; 
	@JsonProperty String  MASK_NAME="";  
	@JsonProperty String ACETEC_LOT="";
	@JsonProperty String OUT_PARTNUM=""; 
	@JsonProperty String NUMBER="";  
	@JsonProperty String TEST_ID="";
	@JsonProperty List<CPStorageWafer> WAFERS; 
	
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
	public int getQUANTITY() {
		return QUANTITY;
	}
	public void setQUANTITY(int qUANTITY) {
		QUANTITY = qUANTITY;
	}
	 
	public String getMASK_NAME() {
		return MASK_NAME;
	}
	public void setMASK_NAME(String mASK_NAME) {
		MASK_NAME = mASK_NAME;
	}
	public String getACETEC_LOT() {
		return ACETEC_LOT;
	}
	public void setACETEC_LOT(String aCETEC_LOT) {
		ACETEC_LOT = aCETEC_LOT;
	}
	public String getOUT_PARTNUM() {
		return OUT_PARTNUM;
	}
	public void setOUT_PARTNUM(String oUT_PARTNUM) {
		OUT_PARTNUM = oUT_PARTNUM;
	}
	public String getNUMBER() {
		return NUMBER;
	}
	public void setNUMBER(String nUMBER) {
		NUMBER = nUMBER;
	} 
	public String getTEST_ID() {
		return TEST_ID;
	}
	public void setTEST_ID(String tEST_ID) {
		TEST_ID = tEST_ID;
	}
	public List<CPStorageWafer> getWAFERS() {
		return WAFERS;
	}
	public void setWAFERS(List<CPStorageWafer> wAFERS) {
		WAFERS = wAFERS;
	}  
}
