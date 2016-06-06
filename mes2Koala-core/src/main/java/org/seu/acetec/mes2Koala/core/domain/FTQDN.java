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
 * @author harlow
 * @version 1.0
 * @created 11-01-2016 15:31:00
 */

@Entity
@Table(name = "e_ft_qdn")
@Access(AccessType.PROPERTY)
public class FTQDN extends QDN {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3257569837467030977L;
	private String customerDeal;
	private String customerDealNote;
	private Date date;
	private Date dealDate;
	private String dealPerson;
	private Integer failSample;
	private Integer failTotal;
	private String internalDeal;
	private String internalDealNote;
	// private Node node;
	private String qaDeal;
	private Integer status;
	private String testerSys;
	private String toPerson;
	private String type;
	private String workPerson;
	private FTLot ftLot;
	private Boolean isFuture;//是否是预hold
	
	private Integer sampleQty;
	private Integer totalQty;
	private String qdnBin;
	private String qdnBinName;
	private String customerName;
	
	private String toDepartment;
	
	private String flow;

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

	public Integer getFailSample() {
		return failSample;
	}

	public void setFailSample(Integer failSample) {
		this.failSample = failSample;
	}

	public Integer getFailTotal() {
		return failTotal;
	}

	public void setFailTotal(Integer failTotal) {
		this.failTotal = failTotal;
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

	public String getQADeal() {
		return qaDeal;
	}

	public void setQADeal(String qaDeal) {
		this.qaDeal = qaDeal;
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

	@ManyToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.EAGER)
	@JoinColumn(name = "ft_lot_ID", referencedColumnName = "ID")
	public FTLot getFtLot() {
		return ftLot;
	}

	public void setFtLot(FTLot ftLot) {
		this.ftLot = ftLot;
	}

	public Boolean getIsFuture() {
		return isFuture;
	}

	public void setIsFuture(Boolean isFuture) {
		this.isFuture = isFuture;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getSampleQty() {
		return sampleQty;
	}

	public void setSampleQty(Integer sampleQty) {
		this.sampleQty = sampleQty;
	}

	public Integer getTotalQty() {
		return totalQty;
	}

	public void setTotalQty(Integer totalQty) {
		this.totalQty = totalQty;
	}

	public String getQdnBin() {
		return qdnBin;
	}

	public void setQdnBin(String qdnBin) {
		this.qdnBin = qdnBin;
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

	public String getQdnBinName() {
		return qdnBinName;
	}

	public void setQdnBinName(String qdnBinName) {
		this.qdnBinName = qdnBinName;
	}

	public String getFlow() {
		return flow;
	}

	public void setFlow(String flow) {
		this.flow = flow;
	}

	public void finalize() throws Throwable {
		super.finalize();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((qaDeal == null) ? 0 : qaDeal.hashCode());
		result = prime * result
				+ ((customerDeal == null) ? 0 : customerDeal.hashCode());
		result = prime
				* result
				+ ((customerDealNote == null) ? 0 : customerDealNote.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result
				+ ((dealDate == null) ? 0 : dealDate.hashCode());
		result = prime * result
				+ ((dealPerson == null) ? 0 : dealPerson.hashCode());
		result = prime * result
				+ ((failSample == null) ? 0 : failSample.hashCode());
		result = prime * result
				+ ((failTotal == null) ? 0 : failTotal.hashCode());
		result = prime * result + ((ftLot == null) ? 0 : ftLot.hashCode());
		result = prime * result + ((testerSys == null) ? 0 : testerSys.hashCode());
		result = prime * result
				+ ((toPerson == null) ? 0 : toPerson.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result
				+ ((workPerson == null) ? 0 : workPerson.hashCode());
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
		FTQDN other = (FTQDN) obj;
		if (qaDeal == null) {
			if (other.qaDeal != null)
				return false;
		} else if (!qaDeal.equals(other.qaDeal))
			return false;
		if (customerDeal == null) {
			if (other.customerDeal != null)
				return false;
		} else if (!customerDeal.equals(other.customerDeal))
			return false;
		if (customerDealNote == null) {
			if (other.customerDealNote != null)
				return false;
		} else if (!customerDealNote.equals(other.customerDealNote))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (dealDate == null) {
			if (other.dealDate != null)
				return false;
		} else if (!dealDate.equals(other.dealDate))
			return false;
		if (dealPerson == null) {
			if (other.dealPerson != null)
				return false;
		} else if (!dealPerson.equals(other.dealPerson))
			return false;
		if (failSample == null) {
			if (other.failSample != null)
				return false;
		} else if (!failSample.equals(other.failSample))
			return false;
		if (failTotal == null) {
			if (other.failTotal != null)
				return false;
		} else if (!failTotal.equals(other.failTotal))
			return false;
		if (ftLot == null) {
			if (other.ftLot != null)
				return false;
		} else if (!ftLot.equals(other.ftLot))
			return false;
		if (testerSys == null) {
			if (other.testerSys != null)
				return false;
		} else if (!testerSys.equals(other.testerSys))
			return false;
		if (toPerson == null) {
			if (other.toPerson != null)
				return false;
		} else if (!toPerson.equals(other.toPerson))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (workPerson == null) {
			if (other.workPerson != null)
				return false;
		} else if (!workPerson.equals(other.workPerson))
			return false;
		return true;
	}

}