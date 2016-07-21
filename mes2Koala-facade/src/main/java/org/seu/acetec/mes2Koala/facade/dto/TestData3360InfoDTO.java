package org.seu.acetec.mes2Koala.facade.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


/**
 * @author HongYu
 * @version 2016/6/6
 */
public class TestData3360InfoDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	//amazon
	private String waferSummaryData;
	
	private String device;

	private String programName;

    private String revision;

    private String testerNumber;

    private String proberCard;

    private String wID;

    private String testStep;

    private String operator;

    private String temperature;
    
    private String startDate;
    
    private String startTime;
    
    private String endDate;
    
    private String endTime;
    
    private String totalTestTime;

    private String grossDie;

    private String goodDie;

    private String badDie;

    private Map<String, Object> dbin;
    
    private int mapSize;

    //lu
    private int deviceName;

    private String waferID;

    private String probeCardNo;

    private String swPassClass2;

    private String testingDate;

    private String computerName;

    private String prober;

    private String osTEST;

    private String vrextIccTest;

    private String adifTest;
    
    private String bdifTest;
    
    private String ioff11V9VTest;
    
    private String ioff6VTest;
    
    private String funcTest;
    
    private String funcrTest;
    
    private String iccTest;
    
    private String ioutTest;
    
    private String idiff20Test;
    
    private String sample;
    
    private String pass;
    
    private String fail;
    
    private String failPercent;
    
    //共用
    private String lotID;
    
    private String yield;
    
    private Map<String, Object> map;

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public String getTesterNumber() {
		return testerNumber;
	}

	public void setTesterNumber(String testerNumber) {
		this.testerNumber = testerNumber;
	}

	public String getProberCard() {
		return proberCard;
	}

	public void setProberCard(String proberCard) {
		this.proberCard = proberCard;
	}

	public String getwID() {
		return wID;
	}

	public void setwID(String wID) {
		this.wID = wID;
	}

	public String getTestStep() {
		return testStep;
	}

	public void setTestStep(String testStep) {
		this.testStep = testStep;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getTotalTestTime() {
		return totalTestTime;
	}

	public void setTotalTestTime(String totalTestTime) {
		this.totalTestTime = totalTestTime;
	}

	public String getGrossDie() {
		return grossDie;
	}

	public void setGrossDie(String grossDie) {
		this.grossDie = grossDie;
	}

	public String getGoodDie() {
		return goodDie;
	}

	public void setGoodDie(String goodDie) {
		this.goodDie = goodDie;
	}

	public String getBadDie() {
		return badDie;
	}

	public void setBadDie(String badDie) {
		this.badDie = badDie;
	}

	public int getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(int deviceName) {
		this.deviceName = deviceName;
	}

	public String getWaferID() {
		return waferID;
	}

	public void setWaferID(String waferID) {
		this.waferID = waferID;
	}

	public String getProbeCardNo() {
		return probeCardNo;
	}

	public void setProbeCardNo(String probeCardNo) {
		this.probeCardNo = probeCardNo;
	}

	public String getSwPassClass2() {
		return swPassClass2;
	}

	public void setSwPassClass2(String swPassClass2) {
		this.swPassClass2 = swPassClass2;
	}

	public String getTestingDate() {
		return testingDate;
	}

	public void setTestingDate(String testingDate) {
		this.testingDate = testingDate;
	}

	public String getComputerName() {
		return computerName;
	}

	public void setComputerName(String computerName) {
		this.computerName = computerName;
	}

	public String getProber() {
		return prober;
	}

	public void setProber(String prober) {
		this.prober = prober;
	}

	public String getOsTEST() {
		return osTEST;
	}

	public void setOsTEST(String osTEST) {
		this.osTEST = osTEST;
	}

	public String getVrextIccTest() {
		return vrextIccTest;
	}

	public void setVrextIccTest(String vrextIccTest) {
		this.vrextIccTest = vrextIccTest;
	}

	public String getAdifTest() {
		return adifTest;
	}

	public void setAdifTest(String adifTest) {
		this.adifTest = adifTest;
	}

	public String getBdifTest() {
		return bdifTest;
	}

	public void setBdifTest(String bdifTest) {
		this.bdifTest = bdifTest;
	}

	public String getIoff11V9VTest() {
		return ioff11V9VTest;
	}

	public void setIoff11V9VTest(String ioff11v9vTest) {
		ioff11V9VTest = ioff11v9vTest;
	}

	public String getIoff6VTest() {
		return ioff6VTest;
	}

	public void setIoff6VTest(String ioff6vTest) {
		ioff6VTest = ioff6vTest;
	}

	public String getFuncTest() {
		return funcTest;
	}

	public void setFuncTest(String funcTest) {
		this.funcTest = funcTest;
	}

	public String getFuncrTest() {
		return funcrTest;
	}

	public void setFuncrTest(String funcrTest) {
		this.funcrTest = funcrTest;
	}

	public String getIccTest() {
		return iccTest;
	}

	public void setIccTest(String iccTest) {
		this.iccTest = iccTest;
	}

	public String getIoutTest() {
		return ioutTest;
	}

	public void setIoutTest(String ioutTest) {
		this.ioutTest = ioutTest;
	}

	public String getIdiff20Test() {
		return idiff20Test;
	}

	public void setIdiff20Test(String idiff20Test) {
		this.idiff20Test = idiff20Test;
	}

	public String getLotID() {
		return lotID;
	}

	public void setLotID(String lotID) {
		this.lotID = lotID;
	}

	public String getYield() {
		return yield;
	}

	public void setYield(String yield) {
		this.yield = yield;
	}

	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getRevision() {
		return revision;
	}

	public void setRevision(String revision) {
		this.revision = revision;
	}

	public String getSample() {
		return sample;
	}

	public void setSample(String sample) {
		this.sample = sample;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getFail() {
		return fail;
	}

	public void setFail(String fail) {
		this.fail = fail;
	}

	public String getFailPercent() {
		return failPercent;
	}

	public void setFailPercent(String failPercent) {
		this.failPercent = failPercent;
	}

	public String getWaferSummaryData() {
		return waferSummaryData;
	}

	public void setWaferSummaryData(String waferSummaryData) {
		this.waferSummaryData = waferSummaryData;
	}

	public Map<String, Object> getDbin() {
		return dbin;
	}

	public void setDbin(Map<String, Object> dbin) {
		this.dbin = dbin;
	}

	public int getMapSize() {
		return mapSize;
	}

	public void setMapSize(int mapSize) {
		this.mapSize = mapSize;
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
}
