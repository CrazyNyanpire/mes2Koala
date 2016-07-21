package org.seu.acetec.mes2Koala.facade.sbl;

public interface SBLClient {

	public String getErrorInfo(String lotNo, String stationName);

	public String insertCPSBL(String sbl);
	
	public String cpInfoForJson(String lotNo, String stationName, String toId);
}
