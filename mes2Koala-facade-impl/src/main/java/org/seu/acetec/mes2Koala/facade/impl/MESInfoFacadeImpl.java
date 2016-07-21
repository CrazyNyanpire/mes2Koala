package org.seu.acetec.mes2Koala.facade.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.seu.acetec.mes2Koala.application.CPInfoApplication;
import org.seu.acetec.mes2Koala.application.CPLotApplication;
import org.seu.acetec.mes2Koala.application.FTLotApplication;
import org.seu.acetec.mes2Koala.application.FTProcessApplication;
import org.seu.acetec.mes2Koala.application.TestProgramTemplateApplication;
import org.seu.acetec.mes2Koala.core.domain.CPInfo;
import org.seu.acetec.mes2Koala.core.domain.CPLot;
import org.seu.acetec.mes2Koala.core.domain.FTLot;
import org.seu.acetec.mes2Koala.core.domain.FTNode;
import org.seu.acetec.mes2Koala.core.domain.TestProgramTemplate;
import org.seu.acetec.mes2Koala.facade.MESInfoFacade;
import org.seu.acetec.mes2Koala.infra.EmsFetcher;

@Named
public class MESInfoFacadeImpl implements MESInfoFacade {

	private String platformUrl = "/Platform/findPlatformByPlatformNo.koala?platformNo=#testerNo#";

	private String lastPlatformOptionLog = "/Platform/getLastPlatformOptionLog/#equipmentId#.koala";

	@Inject
	private EmsFetcher emsFetcher;

	@Inject
	private CPLotApplication cplotAppliction;

	@Inject
	private CPInfoApplication cpInfoApplication;

	@Inject
	private FTLotApplication ftlotAppliction;

	@Inject
	private FTProcessApplication ftProcessApplication;

	@Inject
	private TestProgramTemplateApplication testProgramTemplateApplication;

	@Override
	public String getMesInfo(String node, String testerNo) {
		String platfrom = emsFetcher.fetch(emsFetcher.getEmsAddress()
				+ this.platformUrl.replace("#testerNo#", testerNo));
		JSONObject platformJson = JSONObject.fromObject(platfrom);
		JSONObject result = new JSONObject();
		if (platformJson.get("data") == null) {
			// result.put("msg", "获取机台信息失败！");
			return "ERROR0";
		}
		platformJson = (JSONObject) platformJson.get("data");
		String platfromOptionLog = emsFetcher.fetch(emsFetcher.getEmsAddress()
				+ this.lastPlatformOptionLog.replace("#equipmentId#",
						platformJson.getString("id")));

		JSONObject platfromOptionLogJson = JSONObject
				.fromObject(platfromOptionLog);
		platfromOptionLogJson = platfromOptionLogJson.getJSONObject("data");
		JSONArray equipmentChildren = platformJson
				.getJSONArray("equipmentChildren");
		JSONObject pOrH = equipmentChildren.getJSONObject(0);// 组装的Prober或handler
		result.put("CurrentStatus", platformJson.get("status"));
		result.put("CurrentStatusCode", platformJson.get("statusCode"));
		result.put("ProductID", "SR3592"); // TODO
		result.put("CustomerID", "1602-SPR-SH");// TODO
		result.put("CurrentStation", "Shipping");// TODO
		result.put("ProgramName", "SR3592_CH_93K_FT_R410.tp");// TODO
		result.put("CustomerLotID", "PA5Y59.00F");// TODO
		result.put("AssemblyLotID", "NA");// TODO
		result.put("WaferVersion", "130923CH");// TODO
		result.put("FABID", "TSMC");// TODO
		result.put("ACETECLotNo", platfromOptionLogJson.get("nowLot"));
		result.put("HandlerID", "");
		result.put("ProberID", "");
		if ("CP".equals(platformJson.getString("platformCategory"))) {
			result.put("ProberID", pOrH.get("equipmentNo"));
		} else if ("FT".equals(platformJson.getString("platformCategory"))) {
			result.put("HandlerID", pOrH.get("equipmentNo"));
		}
		result.put("Operator", platfromOptionLogJson.get("optUser"));
		result.put("IncomingNumber", "14340");// TODO
		result.put("GrossDie", null);// TODO
		result.put("TestFlow",
				"Incoming,IQC,GU Test,FT,EQC,Baking,Lead Scan,FQC,Packing,OQC,Shipping,");// todo
		result.put("ShippingTypeID", "SR3592");// TODO
		result.put("PartsNo", platfromOptionLogJson.get("pcNo"));
		result.put("PlatformType", platformJson.getString("platformCategory"));
		result.put("Flow", node);
		result.put("Version", "7.2.3.2");// TODO
		result.put("Temperature", "25");// TODO
		result.put("Edition", "R200");// TODO
		result.put("ProductName", "SR3592");// TODO
		result.put("LotType", "MP");// TODO
		result.put("DevicePartNo", "130923_00");// TODO
		if ("CP".equals(platformJson.getString("platformCategory"))) {
			return this.getCPJsonInfo(result);
		} else if ("FT".equals(platformJson.getString("platformCategory"))) {
			return this.getFTJsonInfo(result);
		} else {
			return result.toString();
		}
	}

	private String getCPJsonInfo(JSONObject result) {
		String lotNo = result.getString("ACETECLotNo");
		CPLot cpLot = cplotAppliction.findOne(
				"select o from CPLot o where o.internalLotNumber = ?", lotNo);
		List<Object> params = new ArrayList<Object>();
		params.add(cpLot.getCustomerCPLot().getCpInfo().getId());
		params.add(result.get("Flow"));
		TestProgramTemplate testProgramTemplate = testProgramTemplateApplication
				.findOne(
						"select o from TestProgramTemplate o where o.internalProduct.id = ? and site=? order by o.lastModifyTimestamp desc",
						params);
		if (testProgramTemplate == null) {
			return "There is no test program for the current flow!";
		}
		result.put("ProductID", cpLot.getCustomerCPLot().getCpInfo()
				.getInternalProductNumber());
		result.put("CustomerID", cpLot.getCustomerCPLot().getCpInfo()
				.getCustomerDirect().getCode());
		result.put("CurrentStation", cpLot.getCpProcess().getNowNode()
				.getName());
		result.put("ProgramName", testProgramTemplate.getName());
		result.put("CustomerLotID", cpLot.getCustomerCPLot()
				.getCustomerLotNumber());
		result.put("AssemblyLotID", "NA");
		result.put("WaferVersion", "NA");
		result.put("FABID", "NA");
		result.put("IncomingNumber", cpLot.getCustomerCPLot()
				.getIncomingQuantity());
		result.put("GrossDie", cpLot.getCustomerCPLot().getCpInfo()
				.getGrossDie());
		result.put("TestFlow", cpLot.getCpProcess().getContent());
		result.put("ShippingTypeID", cpLot.getCustomerCPLot()
				.getShipmentNumber());
		result.put("Version", testProgramTemplate.getTestSoftwareVersion());
		result.put("Temperature", testProgramTemplate.getTemperature());
		result.put("Edition", testProgramTemplate.getProductVersion());
		result.put("ProductName", testProgramTemplate.getProductName());
		result.put("LotType", "MP");// todo
		result.put("DevicePartNo", testProgramTemplate.getRevision());
		return result.toString();
	}

	private String getFTJsonInfo(JSONObject result) {
		String lotNo = result.getString("ACETECLotNo");
		FTLot ftLot = ftlotAppliction.findOne(
				"select o from FTLot o where o.internalLotNumber = ?", lotNo);
		List<Object> params = new ArrayList<Object>();
		params.add(ftLot.getCustomerFTLot().getFtInfo().getId());
		FTNode nowNode = ftProcessApplication.findToStartNode(ftLot
				.getFtProcess().getFtNodes());
		params.add(nowNode.getName());
		TestProgramTemplate testProgramTemplate = testProgramTemplateApplication
				.findOne(
						"select o from TestProgramTemplate o where o.internalProduct.id = ? and site=? order by o.lastModifyTimestamp desc",
						params);
		if (testProgramTemplate == null) {
			return "There is no test program for the current flow!";
		}
		result.put("ProductID", ftLot.getCustomerFTLot().getFtInfo()
				.getInternalProductNumber());
		result.put("CustomerID", ftLot.getCustomerFTLot().getFtInfo()
				.getCustomerDirect().getCode());
		result.put("CurrentStation", nowNode.getName());
		result.put("ProgramName", testProgramTemplate.getName());
		result.put("CustomerLotID", ftLot.getCustomerFTLot()
				.getCustomerLotNumber());
		result.put("AssemblyLotID", ftLot.getCustomerFTLot().getPackageNumber());
		result.put("WaferVersion", "NA");// TODO
		result.put("FABID", "NA");// TODO
		result.put("IncomingNumber", ftLot.getCustomerFTLot()
				.getIncomingQuantity());
		result.put("GrossDie", "NA");
		result.put("TestFlow", ftLot.getFtProcess().getContent());
		result.put("ShippingTypeID", ftLot.getCustomerFTLot()
				.getShipmentNumber());
		result.put("Version", testProgramTemplate.getTestSoftwareVersion());
		result.put("Temperature", testProgramTemplate.getTemperature());
		result.put("Edition", testProgramTemplate.getProductVersion());
		result.put("ProductName", testProgramTemplate.getProductName());
		result.put("LotType", "MP");// TODO
		result.put("DevicePartNo", testProgramTemplate.getRevision());
		return result.toString();
	}

	@Override
	public String getLotInfo(String lot, String category) {
		if ("CP".equalsIgnoreCase(category)) {
			return this.getCPLotInfo(lot);
		} else if ("FT".equalsIgnoreCase(category)) {
			return this.getFTLotInfo(lot);
		} else {
			JSONObject result = new JSONObject();
			result.put("productModel", "NA");
			result.put("grossDie", "NA");
			result.put("nowStation", "NA");
			result.put("team", "NA");// TODO
			result.put("touchdown", "0");
			result.put("standardWorkHours", "1");// TODO
			result.put("customerCode", "NA");
			result.put("programName", "");// TODO
			result.put("error", "category Error");// TODO
			return result.toString();
		}
	}

	private String getCPLotInfo(String lotNo) {
		CPLot cpLot = cplotAppliction.findOne(
				"select o from CPLot o where o.internalLotNumber = ?", lotNo);
		if (cpLot == null) {
			JSONObject result = new JSONObject();
			result.put("productModel", "NA");
			result.put("grossDie", "NA");
			result.put("nowStation", "NA");
			result.put("team", "NA");// TODO
			result.put("touchdown", "0");
			result.put("standardWorkHours", "1");// TODO
			result.put("customerCode", "NA");
			result.put("programName", "");// TODO
			return result.toString();
		}
		JSONObject result = new JSONObject();
		result.put("productModel", cpLot.getCustomerCPLot().getCpInfo()
				.getInternalProductNumber());
		result.put("grossDie", cpLot.getCustomerCPLot().getCpInfo()
				.getGrossDie());
		if(cpLot.getCpProcess().getNowNode()==null)
		{
			result.put("nowStation", "NA");
		}
		else{
			result.put("nowStation", cpLot.getCpProcess().getNowNode().getName());
		}
		result.put("team", "NA");// TODO
		result.put("touchdown", cpLot.getCustomerCPLot().getCpInfo()
				.getTouchQty());
		result.put("standardWorkHours", "1");// TODO
		result.put("customerCode", cpLot.getCustomerCPLot().getCustomerNumber());
		result.put("programName", "");// TODO
		return result.toString();
	}

	private String getFTLotInfo(String lotNo) {
		FTLot ftLot = ftlotAppliction.findOne(
				"select o from FTLot o where o.internalLotNumber = ?", lotNo);
		if (ftLot == null) {
			JSONObject result = new JSONObject();
			result.put("productModel", "NA");
			result.put("grossDie", "NA");
			result.put("nowStation", "NA");
			result.put("team", "NA");// TODO
			result.put("touchdown", "0");
			result.put("standardWorkHours", "1");// TODO
			result.put("customerCode", "NA");
			result.put("programName", "");// TODO
			return result.toString();
		}
		JSONObject result = new JSONObject();
		result.put("productModel", ftLot.getCustomerFTLot().getFtInfo()
				.getInternalProductNumber());
		result.put("grossDie", "NA");// TODO
		FTNode nowNode = ftProcessApplication.findToStartNode(ftLot
				.getFtProcess().getFtNodes());
		result.put("nowStation", nowNode.getName());
		result.put("team", "NA");// TODO
		result.put("touchdown", "NA");// TODO
		result.put("standardWorkHours", "1");// TODO
		result.put("customerCode", ftLot.getCustomerFTLot().getCustomerNumber());
		result.put("programName", "");// TODO
		return result.toString();
	}

	@Override
	public String getTouchdown(String productModel) {
		CPInfo cpInfo = cpInfoApplication.findOne(
				"select o from CPInfo o where o.internalProductNumber = ?",
				productModel);
		if (cpInfo != null) {
			return String.valueOf(cpInfo.getTouchQty());
		}
		return "";
	}
}
