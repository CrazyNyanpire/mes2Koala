package org.seu.acetec.mes2Koala.core.domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.Table;
import javax.persistence.InheritanceType;
import javax.persistence.DiscriminatorType;

/**
 * @author harlow
 * @version 1.0
 * @created 21-四月-2016 14:06:15
 */

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "E_Rework")
@DiscriminatorColumn(name = "CATEGORY", discriminatorType = DiscriminatorType.STRING)
public class Rework extends MES2AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2502702963810997L;
	/**
	 * 类别
	 */
	@Column(name = "CATEGORY", insertable = false, updatable = false)
	private String category;// 配件类别
	private String reworkRCNo;
	private String reworkCustomer;
	private Date reworkDate;
	/**
	 * 重工部门
	 */
	private String reworkDepartment;
	/**
	 * 重工设备
	 */
	private String reworkEquipment;
	/**
	 * 重工编号
	 */
	private String reworkNo;
	private Integer reworkReworkQty;
	/**
	 * 总数量
	 */
	private Integer reworkTotalQty;
	private String reasonExplanation;
	private String reasonOther;
	private String reasonReasons;
	private String summary;

	private String approvePerson;

	private String reworkType;

	private String product;

	private String approve;

	private Date approveDate;

	private String approveRemark;
	
	private String lotNo;
	
	private String status;
	
	private String testerNo;

	public String getReworkRCNo() {
		return reworkRCNo;
	}

	public void setReworkRCNo(String reworkRCNo) {
		this.reworkRCNo = reworkRCNo;
	}

	public String getReworkCustomer() {
		return reworkCustomer;
	}

	public void setReworkCustomer(String reworkCustomer) {
		this.reworkCustomer = reworkCustomer;
	}

	public Date getReworkDate() {
		return reworkDate;
	}

	public void setReworkDate(Date reworkDate) {
		this.reworkDate = reworkDate;
	}

	public String getReworkDepartment() {
		return reworkDepartment;
	}

	public void setReworkDepartment(String reworkDepartment) {
		this.reworkDepartment = reworkDepartment;
	}

	public String getReworkEquipment() {
		return reworkEquipment;
	}

	public void setReworkEquipment(String reworkEquipment) {
		this.reworkEquipment = reworkEquipment;
	}

	public String getReworkNo() {
		return reworkNo;
	}

	public void setReworkNo(String reworkNo) {
		this.reworkNo = reworkNo;
	}

	public Integer getReworkReworkQty() {
		return reworkReworkQty;
	}

	public void setReworkReworkQty(Integer reworkReworkQty) {
		this.reworkReworkQty = reworkReworkQty;
	}

	public Integer getReworkTotalQty() {
		return reworkTotalQty;
	}

	public void setReworkTotalQty(Integer reworkTotalQty) {
		this.reworkTotalQty = reworkTotalQty;
	}

	public String getReasonExplanation() {
		return reasonExplanation;
	}

	public void setReasonExplanation(String reasonExplanation) {
		this.reasonExplanation = reasonExplanation;
	}

	public String getReasonOther() {
		return reasonOther;
	}

	public void setReasonOther(String reasonOther) {
		this.reasonOther = reasonOther;
	}

	public String getReasonReasons() {
		return reasonReasons;
	}

	public void setReasonReasons(String reasonReasons) {
		this.reasonReasons = reasonReasons;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getApprovePerson() {
		return approvePerson;
	}

	public void setApprovePerson(String approvePerson) {
		this.approvePerson = approvePerson;
	}

	public String getReworkType() {
		return reworkType;
	}

	public void setReworkType(String reworkType) {
		this.reworkType = reworkType;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getApprove() {
		return approve;
	}

	public void setApprove(String approve) {
		this.approve = approve;
	}

	public Date getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}

	public String getApproveRemark() {
		return approveRemark;
	}

	public void setApproveRemark(String approveRemark) {
		this.approveRemark = approveRemark;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
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

	public String getTesterNo() {
		return testerNo;
	}

	public void setTesterNo(String testerNo) {
		this.testerNo = testerNo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime
				* result
				+ ((reasonExplanation == null) ? 0 : reasonExplanation
						.hashCode());
		result = prime * result
				+ ((reasonOther == null) ? 0 : reasonOther.hashCode());
		result = prime * result
				+ ((reasonReasons == null) ? 0 : reasonReasons.hashCode());
		result = prime * result
				+ ((reworkCustomer == null) ? 0 : reworkCustomer.hashCode());
		result = prime * result
				+ ((reworkDate == null) ? 0 : reworkDate.hashCode());
		result = prime
				* result
				+ ((reworkDepartment == null) ? 0 : reworkDepartment.hashCode());
		result = prime * result
				+ ((reworkEquipment == null) ? 0 : reworkEquipment.hashCode());
		result = prime * result
				+ ((reworkNo == null) ? 0 : reworkNo.hashCode());
		result = prime * result
				+ ((reworkRCNo == null) ? 0 : reworkRCNo.hashCode());
		result = prime * result
				+ ((reworkReworkQty == null) ? 0 : reworkReworkQty.hashCode());
		result = prime * result
				+ ((reworkTotalQty == null) ? 0 : reworkTotalQty.hashCode());
		result = prime * result + ((summary == null) ? 0 : summary.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Rework other = (Rework) obj;
		if (reasonExplanation == null) {
			if (other.reasonExplanation != null)
				return false;
		} else if (!reasonExplanation.equals(other.reasonExplanation))
			return false;
		if (reasonOther == null) {
			if (other.reasonOther != null)
				return false;
		} else if (!reasonOther.equals(other.reasonOther))
			return false;
		if (reasonReasons == null) {
			if (other.reasonReasons != null)
				return false;
		} else if (!reasonReasons.equals(other.reasonReasons))
			return false;
		if (reworkCustomer == null) {
			if (other.reworkCustomer != null)
				return false;
		} else if (!reworkCustomer.equals(other.reworkCustomer))
			return false;
		if (reworkDate == null) {
			if (other.reworkDate != null)
				return false;
		} else if (!reworkDate.equals(other.reworkDate))
			return false;
		if (reworkDepartment == null) {
			if (other.reworkDepartment != null)
				return false;
		} else if (!reworkDepartment.equals(other.reworkDepartment))
			return false;
		if (reworkEquipment == null) {
			if (other.reworkEquipment != null)
				return false;
		} else if (!reworkEquipment.equals(other.reworkEquipment))
			return false;
		if (reworkNo == null) {
			if (other.reworkNo != null)
				return false;
		} else if (!reworkNo.equals(other.reworkNo))
			return false;
		if (reworkRCNo == null) {
			if (other.reworkRCNo != null)
				return false;
		} else if (!reworkRCNo.equals(other.reworkRCNo))
			return false;
		if (reworkReworkQty == null) {
			if (other.reworkReworkQty != null)
				return false;
		} else if (!reworkReworkQty.equals(other.reworkReworkQty))
			return false;
		if (reworkTotalQty == null) {
			if (other.reworkTotalQty != null)
				return false;
		} else if (!reworkTotalQty.equals(other.reworkTotalQty))
			return false;
		if (summary == null) {
			if (other.summary != null)
				return false;
		} else if (!summary.equals(other.summary))
			return false;
		return true;
	}

	@Override
	public String[] businessKeys() {
		// TODO Auto-generated method stub
		return null;
	}

}