package org.seu.acetec.mes2Koala.facade.dto;

import java.util.Date;
import java.io.Serializable;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ReworkDTO implements Serializable {

	private Long id;

	private int version;

	private String summary;

	private String approveRemark;

	private String reasonOther;

	private String approve;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date reworkDate;

	private Date reworkDateEnd;

	private Integer reworkReworkQty;

	private String reworkType;

	private Date lastModifyTimestamp;

	private Date lastModifyTimestampEnd;

	private Integer logic;

	private String product;

	private String lastModifyEmployNo;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTimestamp;

	private Date createTimestampEnd;

	private String category;

	private String reasonExplanation;

	private String reworkRCNo;

	private String createEmployNo;

	private String reworkNo;

	private String reasonReasons;

	private String reworkCustomer;

	private Integer reworkTotalQty;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date approveDate;

	private Date approveDateEnd;

	private String approvePerson;

	private String reworkEquipment;

	private String reworkDepartment;
	
	private String lotNo;
	
	private String status;

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getSummary() {
		return this.summary;
	}

	public void setApproveRemark(String approveRemark) {
		this.approveRemark = approveRemark;
	}

	public String getApproveRemark() {
		return this.approveRemark;
	}

	public void setReasonOther(String reasonOther) {
		this.reasonOther = reasonOther;
	}

	public String getReasonOther() {
		return this.reasonOther;
	}

	public void setApprove(String approve) {
		this.approve = approve;
	}

	public String getApprove() {
		return this.approve;
	}

	public void setReworkDate(Date reworkDate) {
		this.reworkDate = reworkDate;
	}

	public Date getReworkDate() {
		return this.reworkDate;
	}

	public void setReworkDateEnd(Date reworkDateEnd) {
		this.reworkDateEnd = reworkDateEnd;
	}

	public Date getReworkDateEnd() {
		return this.reworkDateEnd;
	}

	public void setReworkReworkQty(Integer reworkReworkQty) {
		this.reworkReworkQty = reworkReworkQty;
	}

	public Integer getReworkReworkQty() {
		return this.reworkReworkQty;
	}

	public void setReworkType(String reworkType) {
		this.reworkType = reworkType;
	}

	public String getReworkType() {
		return this.reworkType;
	}

	public void setLastModifyTimestamp(Date lastModifyTimestamp) {
		this.lastModifyTimestamp = lastModifyTimestamp;
	}

	public Date getLastModifyTimestamp() {
		return this.lastModifyTimestamp;
	}

	public void setLastModifyTimestampEnd(Date lastModifyTimestampEnd) {
		this.lastModifyTimestampEnd = lastModifyTimestampEnd;
	}

	public Date getLastModifyTimestampEnd() {
		return this.lastModifyTimestampEnd;
	}

	public void setLogic(Integer logic) {
		this.logic = logic;
	}

	public Integer getLogic() {
		return this.logic;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getProduct() {
		return this.product;
	}

	public void setLastModifyEmployNo(String lastModifyEmployNo) {
		this.lastModifyEmployNo = lastModifyEmployNo;
	}

	public String getLastModifyEmployNo() {
		return this.lastModifyEmployNo;
	}

	public void setCreateTimestamp(Date createTimestamp) {
		this.createTimestamp = createTimestamp;
	}

	public Date getCreateTimestamp() {
		return this.createTimestamp;
	}

	public void setCreateTimestampEnd(Date createTimestampEnd) {
		this.createTimestampEnd = createTimestampEnd;
	}

	public Date getCreateTimestampEnd() {
		return this.createTimestampEnd;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCategory() {
		return this.category;
	}

	public void setReasonExplanation(String reasonExplanation) {
		this.reasonExplanation = reasonExplanation;
	}

	public String getReasonExplanation() {
		return this.reasonExplanation;
	}

	public void setReworkRCNo(String reworkRCNo) {
		this.reworkRCNo = reworkRCNo;
	}

	public String getReworkRCNo() {
		return this.reworkRCNo;
	}

	public void setCreateEmployNo(String createEmployNo) {
		this.createEmployNo = createEmployNo;
	}

	public String getCreateEmployNo() {
		return this.createEmployNo;
	}

	public void setReworkNo(String reworkNo) {
		this.reworkNo = reworkNo;
	}

	public String getReworkNo() {
		return this.reworkNo;
	}

	public void setReasonReasons(String reasonReasons) {
		this.reasonReasons = reasonReasons;
	}

	public String getReasonReasons() {
		return this.reasonReasons;
	}

	public void setReworkCustomer(String reworkCustomer) {
		this.reworkCustomer = reworkCustomer;
	}

	public String getReworkCustomer() {
		return this.reworkCustomer;
	}

	public void setReworkTotalQty(Integer reworkTotalQty) {
		this.reworkTotalQty = reworkTotalQty;
	}

	public Integer getReworkTotalQty() {
		return this.reworkTotalQty;
	}

	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}

	public Date getApproveDate() {
		return this.approveDate;
	}

	public void setApproveDateEnd(Date approveDateEnd) {
		this.approveDateEnd = approveDateEnd;
	}

	public Date getApproveDateEnd() {
		return this.approveDateEnd;
	}

	public void setApprovePerson(String approvePerson) {
		this.approvePerson = approvePerson;
	}

	public String getApprovePerson() {
		return this.approvePerson;
	}

	public void setReworkEquipment(String reworkEquipment) {
		this.reworkEquipment = reworkEquipment;
	}

	public String getReworkEquipment() {
		return this.reworkEquipment;
	}

	public void setReworkDepartment(String reworkDepartment) {
		this.reworkDepartment = reworkDepartment;
	}

	public String getReworkDepartment() {
		return this.reworkDepartment;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getLotNo() {
		return lotNo;
	}

	public void setLotNo(String lotNo) {
		this.lotNo = lotNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReworkDTO other = (ReworkDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}