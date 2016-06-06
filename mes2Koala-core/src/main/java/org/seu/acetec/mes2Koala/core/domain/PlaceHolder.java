package org.seu.acetec.mes2Koala.core.domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "E_Place_Holder")
@Access(AccessType.PROPERTY)

public class PlaceHolder extends MES2AbstractEntity {

	//private FTLot ftLot;
	
	//#IQC
	private String Worker;
	private String IQCMaterialWeight;
	private String ActualWeight;
	private String DifferenceValue;
	//#Baking
	private String OvenNumber;
	private String BakingTemp;
	private String BakingTime;
	private String CheckInTime;
	private String CheckOutTime;
	private String EnterBakingOperator;
	private String OutBakingOperator;
	//#FT+EQC
	private String EQCLowYieldReport;
	private String TesterType;
	private String NormalTestProgram;
	private String ProgramVersion;
	//#LAT
	private String RejectReport;
	private String LATOprator;
	//#FVI
	private String FVILowYieldReport;
	private String FVIOperator;
	//#Packing
	private String PackingRejectReport;
	//#FQC
	private String FQCRejectReport;
	private String FQCOperator;
	//#OQC
	private String OQCRejectReport;
	private String OQCOperator;
	
	//#ADD
	private String customerCode;
	private String productName;
	private String productVersion;
	private String assyCompanyName;//封装厂名称
	private String assyLot;//封装厂编号
	private String customerLot;
	private String acetecLot;
	private String dataCode;
	private String packageSize;
	private String customerPPO;
	private String receiveData;//进货日期
	private String receiveQty;//进货数量
	private String taxType;
	private String packingType;
	private String shippingType;
		
	public String getProductVersion() {
		return productVersion;
	}
	public void setProductVersion(String productVersion) {
		this.productVersion = productVersion;
	}
	@Override
	public String[] businessKeys() {
		return null;
	}
	/*
	@OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "FTLot_Id")
	public FTLot getFtLot() {
		return ftLot;
	}

	public void setFtLot(FTLot ftLot) {
		this.ftLot = ftLot;
	}
	*/
	public String getWorker() {
		return Worker;
	}


	public void setWorker(String worker) {
		Worker = worker;
	}


	public String getIQCMaterialWeight() {
		return IQCMaterialWeight;
	}


	public void setIQCMaterialWeight(String iQCMaterialWeight) {
		IQCMaterialWeight = iQCMaterialWeight;
	}


	public String getActualWeight() {
		return ActualWeight;
	}


	public void setActualWeight(String actualWeight) {
		ActualWeight = actualWeight;
	}


	public String getDifferenceValue() {
		return DifferenceValue;
	}


	public void setDifferenceValue(String differenceValue) {
		DifferenceValue = differenceValue;
	}


	public String getOvenNumber() {
		return OvenNumber;
	}


	public void setOvenNumber(String ovenNumber) {
		OvenNumber = ovenNumber;
	}


	public String getBakingTemp() {
		return BakingTemp;
	}


	public void setBakingTemp(String bakingTemp) {
		BakingTemp = bakingTemp;
	}


	public String getBakingTime() {
		return BakingTime;
	}


	public void setBakingTime(String bakingTime) {
		BakingTime = bakingTime;
	}


	public String getCheckInTime() {
		return CheckInTime;
	}


	public void setCheckInTime(String checkInTime) {
		CheckInTime = checkInTime;
	}


	public String getCheckOutTime() {
		return CheckOutTime;
	}


	public void setCheckOutTime(String checkOutTime) {
		CheckOutTime = checkOutTime;
	}


	public String getEnterBakingOperator() {
		return EnterBakingOperator;
	}

	public void setEnterBakingOperator(String enterBakingOperator) {
		EnterBakingOperator = enterBakingOperator;
	}


	public String getOutBakingOperator() {
		return OutBakingOperator;
	}


	public void setOutBakingOperator(String outBakingOperator) {
		OutBakingOperator = outBakingOperator;
	}


	public String getEQCLowYieldReport() {
		return EQCLowYieldReport;
	}


	public void setEQCLowYieldReport(String eQCLowYieldReport) {
		EQCLowYieldReport = eQCLowYieldReport;
	}


	public String getTesterType() {
		return TesterType;
	}


	public void setTesterType(String testerType) {
		TesterType = testerType;
	}


	public String getNormalTestProgram() {
		return NormalTestProgram;
	}


	public void setNormalTestProgram(String normalTestProgram) {
		NormalTestProgram = normalTestProgram;
	}


	public String getProgramVersion() {
		return ProgramVersion;
	}


	public void setProgramVersion(String programVersion) {
		ProgramVersion = programVersion;
	}


	public String getRejectReport() {
		return RejectReport;
	}


	public void setRejectReport(String rejectReport) {
		RejectReport = rejectReport;
	}


	public String getFVILowYieldReport() {
		return FVILowYieldReport;
	}


	public void setFVILowYieldReport(String fVILowYieldReport) {
		FVILowYieldReport = fVILowYieldReport;
	}


	public String getLATOprator() {
		return LATOprator;
	}


	public void setLATOprator(String lATOprator) {
		LATOprator = lATOprator;
	}


	public String getFVIOperator() {
		return FVIOperator;
	}


	public void setFVIOperator(String fVIOperator) {
		FVIOperator = fVIOperator;
	}


	public String getPackingRejectReport() {
		return PackingRejectReport;
	}


	public void setPackingRejectReport(String packingRejectReport) {
		PackingRejectReport = packingRejectReport;
	}


	public String getFQCRejectReport() {
		return FQCRejectReport;
	}


	public void setFQCRejectReport(String fQCRejectReport) {
		FQCRejectReport = fQCRejectReport;
	}


	public String getFQCOperator() {
		return FQCOperator;
	}


	public void setFQCOperator(String fQCOperator) {
		FQCOperator = fQCOperator;
	}


	public String getOQCRejectReport() {
		return OQCRejectReport;
	}


	public void setOQCRejectReport(String oQCRejectReport) {
		OQCRejectReport = oQCRejectReport;
	}


	public String getOQCOperator() {
		return OQCOperator;
	}


	public void setOQCOperator(String oQCOperator) {
		OQCOperator = oQCOperator;
	}
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getAssyCompanyName() {
		return assyCompanyName;
	}
	public void setAssyCompanyName(String assyCompanyName) {
		this.assyCompanyName = assyCompanyName;
	}
	public String getAssyLot() {
		return assyLot;
	}
	public void setAssyLot(String assyLot) {
		this.assyLot = assyLot;
	}
	public String getCustomerLot() {
		return customerLot;
	}
	public void setCustomerLot(String customerLot) {
		this.customerLot = customerLot;
	}
	public String getAcetecLot() {
		return acetecLot;
	}
	public void setAcetecLot(String acetecLot) {
		this.acetecLot = acetecLot;
	}
	public String getDataCode() {
		return dataCode;
	}
	public void setDataCode(String dataCode) {
		this.dataCode = dataCode;
	}
	public String getPackageSize() {
		return packageSize;
	}
	public void setPackageSize(String packageSize) {
		this.packageSize = packageSize;
	}
	public String getCustomerPPO() {
		return customerPPO;
	}
	public void setCustomerPPO(String customerPPO) {
		this.customerPPO = customerPPO;
	}
	public String getReceiveData() {
		return receiveData;
	}
	public void setReceiveData(String receiveData) {
		this.receiveData = receiveData;
	}
	public String getReceiveQty() {
		return receiveQty;
	}
	public void setReceiveQty(String receiveQty) {
		this.receiveQty = receiveQty;
	}
	public String getTaxType() {
		return taxType;
	}
	public void setTaxType(String setTaxType) {
		this.taxType = setTaxType;
	}
	public String getPackingType() {
		return packingType;
	}
	public void setPackingType(String packingType) {
		this.packingType = packingType;
	}
	public String getShippingType() {
		return shippingType;
	}
	public void setShippingType(String shippingType) {
		this.shippingType = shippingType;
	}
	
}
