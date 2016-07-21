package org.seu.acetec.mes2Koala.core.domain;

import java.util.List;
import java.util.Map;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;

/*@Entity
@Table(name = "E_Place_Holder_CP")
@Access(AccessType.PROPERTY)*/
public class PlaceHolderCP extends MES2AbstractEntity {

	// #ADD
	private String customerCode;
	private String productName;
	
	private String customerLot;
	private String acetecLot;
	
	private String customerPPO;
	private String receiveQty;// 进货数量
	private String receiveData;// 进货日期
		
	private String packageSize;
	private String packingType;
	private String shippingType;

	private String keyQuantityAuthorization;
	private String keyProductionAuthorization;
	private String keyTDEAuthorization;

	private String signedTime;
	
	/**
	 * modified by Howard 2016.06.19
	 */
	private String printDate;
	private String waferSize;
	private String maskName;
	private String grossDie;
	
	private List<CPWafer> wafers;
	private Map<String, String> testPrograms;	//<站点名称，测试程序名称>
	private String processContent;	//流程明细
	
	private String internalProductNumber;


	public String getProcessContent() {
		return processContent;
	}

	public void setProcessContent(String processContent) {
		this.processContent = processContent;
	}

	public Map<String, String> getTestPrograms() {
		return testPrograms;
	}

	public void setTestPrograms(Map<String, String> testPrograms) {
		this.testPrograms = testPrograms;
	}

	public List<CPWafer> getWafers() {
		return wafers;
	}

	public void setWafers(List<CPWafer> wafers) {
		this.wafers = wafers;
	}

	public String getGrossDie() {
		return grossDie;
	}

	public void setGrossDie(String grossDie) {
		this.grossDie = grossDie;
	}

	public String getMaskName() {
		return maskName;
	}

	public void setMaskName(String maskName) {
		this.maskName = maskName;
	}

	public String getWaferSize() {
		return waferSize;
	}

	public void setWaferSize(String waferSize) {
		this.waferSize = waferSize;
	}

	public String getPrintDate() {
		return printDate;
	}

	public void setPrintDate(String printDate) {
		this.printDate = printDate;
	}

	@Override
	public String[] businessKeys() {
		return null;
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

	public String getKeyQuantityAuthorization() {
		return keyQuantityAuthorization;
	}

	public void setKeyQuantityAuthorization(String keyQuantityAuthorization) {
		this.keyQuantityAuthorization = keyQuantityAuthorization;
	}

	public String getKeyProductionAuthorization() {
		return keyProductionAuthorization;
	}

	public void setKeyProductionAuthorization(String keyProductionAuthorization) {
		this.keyProductionAuthorization = keyProductionAuthorization;
	}

	public String getKeyTDEAuthorization() {
		return keyTDEAuthorization;
	}

	public void setKeyTDEAuthorization(String keyTDEAuthorization) {
		this.keyTDEAuthorization = keyTDEAuthorization;
	}

	public String getSignedTime() {
		return signedTime;
	}

	public void setSignedTime(String signedTime) {
		this.signedTime = signedTime;
	}

	public String getInternalProductNumber() {
		return internalProductNumber;
	}

	public void setInternalProductNumber(String internalProductNumber) {
		this.internalProductNumber = internalProductNumber;
	}
}
