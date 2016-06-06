package org.seu.acetec.mes2Koala.facade.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ReelDiskTransferStorageDTO extends ReelDiskDTO implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 789953640992436761L;

	private String status; // 在库、出库、借料、丢料、重工
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date storageTime;// 进入中转库时间
	private String specialSign;// 特殊标示 取消，降级，制定出货，报废
	private String specialSignRemark;

	private String approve;

	private String customerProductNumber;// 来料型号

	private String shipmentProductNumber;// 出货型号

	private String customerPPO;

	private String customerLotNumber;

	private String customerProductRevision;

	private String holdSign;// 是，否
	private String holdSignRemark;
	private String unHoldSignRemark;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date holdDate;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date unHoldDate;

	private Long internalLotId;

	private String ids;

	private String latSign;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date latPackageTime;
	private String latPackageNo;
	
	private String packageType;
	
	private String markNo;
	
	private Boolean isCombined;
	
	private Long qty;
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getStorageTime() {
		return storageTime;
	}

	public void setStorageTime(Date storageTime) {
		this.storageTime = storageTime;
	}

	public String getSpecialSign() {
		return specialSign;
	}

	public void setSpecialSign(String specialSign) {
		this.specialSign = specialSign;
	}

	public String getSpecialSignRemark() {
		return specialSignRemark;
	}

	public void setSpecialSignRemark(String specialSignRemark) {
		this.specialSignRemark = specialSignRemark;
	}

	public String getApprove() {
		return approve;
	}

	public void setApprove(String approve) {
		this.approve = approve;
	}

	public String getCustomerProductNumber() {
		return customerProductNumber;
	}

	public void setCustomerProductNumber(String customerProductNumber) {
		this.customerProductNumber = customerProductNumber;
	}

	public String getShipmentProductNumber() {
		return shipmentProductNumber;
	}

	public void setShipmentProductNumber(String shipmentProductNumber) {
		this.shipmentProductNumber = shipmentProductNumber;
	}

	public String getCustomerPPO() {
		return customerPPO;
	}

	public void setCustomerPPO(String customerPPO) {
		this.customerPPO = customerPPO;
	}

	public String getCustomerLotNumber() {
		return customerLotNumber;
	}

	public void setCustomerLotNumber(String customerLotNumber) {
		this.customerLotNumber = customerLotNumber;
	}

	public String getCustomerProductRevision() {
		return customerProductRevision;
	}

	public void setCustomerProductRevision(String customerProductRevision) {
		this.customerProductRevision = customerProductRevision;
	}

	public String getHoldSign() {
		return holdSign;
	}

	public void setHoldSign(String holdSign) {
		this.holdSign = holdSign;
	}

	public String getHoldSignRemark() {
		return holdSignRemark;
	}

	public void setHoldSignRemark(String holdSignRemark) {
		this.holdSignRemark = holdSignRemark;
	}

	public String getUnHoldSignRemark() {
		return unHoldSignRemark;
	}

	public void setUnHoldSignRemark(String unHoldSignRemark) {
		this.unHoldSignRemark = unHoldSignRemark;
	}

	public Date getHoldDate() {
		return holdDate;
	}

	public void setHoldDate(Date holdDate) {
		this.holdDate = holdDate;
	}

	public Date getUnHoldDate() {
		return unHoldDate;
	}

	public void setUnHoldDate(Date unHoldDate) {
		this.unHoldDate = unHoldDate;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public Long getInternalLotId() {
		return internalLotId;
	}

	public void setInternalLotId(Long internalLotId) {
		this.internalLotId = internalLotId;
	}

	public String getLatSign() {
		return latSign;
	}

	public void setLatSign(String latSign) {
		this.latSign = latSign;
	}

	public Date getLatPackageTime() {
		return latPackageTime;
	}

	public void setLatPackageTime(Date latPackageTime) {
		this.latPackageTime = latPackageTime;
	}

	public String getLatPackageNo() {
		return latPackageNo;
	}

	public void setLatPackageNo(String latPackageNo) {
		this.latPackageNo = latPackageNo;
	}

	public String getPackageType() {
		return packageType;
	}

	public void setPackageType(String packageType) {
		this.packageType = packageType;
	}

	public String getMarkNo() {
		return markNo;
	}

	public void setMarkNo(String markNo) {
		this.markNo = markNo;
	}

	public Boolean getIsCombined() {
		return isCombined;
	}

	public void setIsCombined(Boolean isCombined) {
		this.isCombined = isCombined;
	}

	public Long getQty() {
		return qty;
	}

	public void setQty(Long qty) {
		this.qty = qty;
	}

}