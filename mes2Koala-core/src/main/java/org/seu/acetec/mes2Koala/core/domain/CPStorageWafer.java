package org.seu.acetec.mes2Koala.core.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CPStorageWafer {
	@JsonProperty String ID;
	@JsonProperty String LOT_ID;
	@JsonProperty String WAFER_CODE;
	@JsonProperty String WAFER_INDEX;
	@JsonProperty private int IS_TRUE;
	@JsonProperty private int GROSS_DIE;
	@JsonProperty private int GOOD_DIE;
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getLOT_ID() {
		return LOT_ID;
	}
	public void setLOT_ID(String lOT_ID) {
		LOT_ID = lOT_ID;
	}
	public String getWAFER_CODE() {
		return WAFER_CODE;
	}
	public void setWAFER_CODE(String wAFER_CODE) {
		WAFER_CODE = wAFER_CODE;
	}
	public String getWAFER_INDEX() {
		return WAFER_INDEX;
	}
	public void setWAFER_INDEX(String wAFER_INDEX) {
		WAFER_INDEX = wAFER_INDEX;
	}
	public int getIS_TRUE() {
		return IS_TRUE;
	}
	public void setIS_TRUE(int iS_TRUE) {
		IS_TRUE = iS_TRUE;
	}
	public int getGROSS_DIE() {
		return GROSS_DIE;
	}
	public void setGROSS_DIE(int gROSS_DIE) {
		GROSS_DIE = gROSS_DIE;
	}
	public int getGOOD_DIE() {
		return GOOD_DIE;
	}
	public void setGOOD_DIE(int gOOD_DIE) {
		GOOD_DIE = gOOD_DIE;
	}
	
}
