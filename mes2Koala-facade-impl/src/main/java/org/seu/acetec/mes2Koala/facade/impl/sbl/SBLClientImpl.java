package org.seu.acetec.mes2Koala.facade.impl.sbl;

import org.apache.commons.httpclient.NameValuePair;
import org.seu.acetec.mes2Koala.facade.sbl.SBLClient;
import org.seu.acetec.mes2Koala.infra.httpclient.HttpClientUtil;
import org.seu.acetec.mes2Koala.infra.httpclient.Node;

public class SBLClientImpl implements SBLClient {

	private String address = "http://192.168.1.58:8081/CPSBLService.asmx/";

	private HttpClientUtil httpClientUtil = new HttpClientUtil();

	private Node node = new Node();

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String getErrorInfo(String lotNo, String stationName) {
		NameValuePair nvplot = new NameValuePair("LotNo", lotNo);
		NameValuePair nvpstation = new NameValuePair("StationName", stationName);
		byte[] res = httpClientUtil.getPostByte(address + "GetErrorInfo",
				new NameValuePair[] { nvplot, nvpstation });
		return node.getNodeText(res, "string");
	}

	@Override
	public String insertCPSBL(String sbl) {
		NameValuePair nvpsbl = new NameValuePair("strJson", sbl);
		byte[] res = httpClientUtil.getPostByte(address + "InsertCPSBL",
				new NameValuePair[] { nvpsbl });
		String result = node.getNodeText(res, "string");
		if(!"true".equalsIgnoreCase(result)){
			throw new RuntimeException("同步SBL异常！");
		}
		return result;
	}
	
	@Override
	public String cpInfoForJson(String lotNo, String stationName, String toId) {
		NameValuePair nvplot = new NameValuePair("LotNo", lotNo);
		NameValuePair nvpstation = new NameValuePair("StationName", stationName);
		NameValuePair nvpToId = new NameValuePair("ToId", toId);
		byte[] res = httpClientUtil.getPostByte(address + "CPInfoForJson",
				new NameValuePair[] { nvplot, nvpstation, nvpToId });
		return node.getNodeText(res, "string");
	}
}
