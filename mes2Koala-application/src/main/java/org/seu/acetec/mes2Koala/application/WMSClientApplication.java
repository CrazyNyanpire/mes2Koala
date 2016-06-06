package org.seu.acetec.mes2Koala.application;

public interface WMSClientApplication {
	public void orderLots(String linetype,String lotjson);
	public void deleteOrderLot(String linetype,String testId,Long testQTY);  
	public void mfgInWMS(String linetype,String storagetype,String lotjson);
}
