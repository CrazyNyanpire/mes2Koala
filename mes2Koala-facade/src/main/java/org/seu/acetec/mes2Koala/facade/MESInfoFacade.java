package org.seu.acetec.mes2Koala.facade;

import net.sf.json.JSONObject;

public interface MESInfoFacade {
	public String getMesInfo(String node, String testerNo);

	public String getLotInfo(String lot, String category);
	
	public String getTouchdown(String productModel);
}
