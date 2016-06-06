package org.seu.acetec.mes2Koala.core.domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * CP异常单
 *
 * @author yuxiangque
 * @version 2016/3/28
 */
@Entity
@Table(name = "E_CP_QDN")
@Access(AccessType.PROPERTY)
public class CPQDN extends QDN {
	
	private static final long serialVersionUID = 6731396327369344427L;
	private String customerDeal;
	private String customerDealNote;
	private Date date;
	private Date dealDate;
	private String dealPerson;
	private String failQty;
	private String internalDeal;
	private String internalDealNote;
	private String qaDeal;
	private Integer status;
	private String testerSys;
	private String toPerson;
	private String type;
	private String workPerson;
	private CPLot cpLot;
	private Boolean isFuture;//是否是预hold
	private String customerName;
	private String toDepartment;
	private String flow;
	private String riskLot;
	private String qdnInfo;
	private String qdnChk;
	

	public String getCustomerDeal() {
		return customerDeal;
	}

	public void setCustomerDeal(String customerDeal) {
		this.customerDeal = customerDeal;
	}

	public String getCustomerDealNote() {
		return customerDealNote;
	}

	public void setCustomerDealNote(String customerDealNote) {
		this.customerDealNote = customerDealNote;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getDealDate() {
		return dealDate;
	}

	public void setDealDate(Date dealDate) {
		this.dealDate = dealDate;
	}

	public String getDealPerson() {
		return dealPerson;
	}

	public void setDealPerson(String dealPerson) {
		this.dealPerson = dealPerson;
	}

	public String getInternalDeal() {
		return internalDeal;
	}

	public void setInternalDeal(String internalDeal) {
		this.internalDeal = internalDeal;
	}

	public String getInternalDealNote() {
		return internalDealNote;
	}

	public void setInternalDealNote(String internalDealNote) {
		this.internalDealNote = internalDealNote;
	}

	public String getQaDeal() {
		return qaDeal;
	}

	public void setQaDeal(String qaDeal) {
		this.qaDeal = qaDeal;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getTesterSys() {
		return testerSys;
	}

	public void setTesterSys(String testerSys) {
		this.testerSys = testerSys;
	}

	public String getToPerson() {
		return toPerson;
	}

	public void setToPerson(String toPerson) {
		this.toPerson = toPerson;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getWorkPerson() {
		return workPerson;
	}

	public void setWorkPerson(String workPerson) {
		this.workPerson = workPerson;
	}

	public Boolean getIsFuture() {
		return isFuture;
	}

	public void setIsFuture(Boolean isFuture) {
		this.isFuture = isFuture;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getToDepartment() {
		return toDepartment;
	}

	public void setToDepartment(String toDepartment) {
		this.toDepartment = toDepartment;
	}

	public String getFlow() {
		return flow;
	}

	public void setFlow(String flow) {
		this.flow = flow;
	}

	@ManyToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.EAGER)
	@JoinColumn(name = "cp_lot_ID", referencedColumnName = "ID")
	public CPLot getCpLot() {
		return cpLot;
	}

	public void setCpLot(CPLot cpLot) {
		this.cpLot = cpLot;
	}

	public String getRiskLot() {
		return riskLot;
	}

	public void setRiskLot(String riskLot) {
		this.riskLot = riskLot;
	}

	public String getQdnInfo() {
		return qdnInfo;
	}

	public void setQdnInfo(String qdnInfo) {
		this.qdnInfo = qdnInfo;
	}

	public String getQdnChk() {
		return qdnChk;
	}

	public void setQdnChk(String qdnChk) {
		this.qdnChk = qdnChk;
	}

	public String getFailQty() {
		return failQty;
	}

	public void setFailQty(String failQty) {
		this.failQty = failQty;
	}
}
