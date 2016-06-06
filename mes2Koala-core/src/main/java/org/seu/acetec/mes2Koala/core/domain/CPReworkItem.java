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
 * @created 21-四月-2016 14:05:08
 */
@Entity
@Table(name = "E_CPReWorkItem")
@Access(AccessType.PROPERTY)
public class CPReworkItem extends MES2AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6690150893122337099L;
	private Date accomplishDate;
	private String attentionItem;
	private String operator;
	private String reworkFlow;
	public CPRework cpRework;
	private String gist;

	public Date getAccomplishDate() {
		return accomplishDate;
	}

	public void setAccomplishDate(Date accomplishDate) {
		this.accomplishDate = accomplishDate;
	}

	public String getAttentionItem() {
		return attentionItem;
	}

	public void setAttentionItem(String attentionItem) {
		this.attentionItem = attentionItem;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getReworkFlow() {
		return reworkFlow;
	}

	public void setReworkFlow(String reworkFlow) {
		this.reworkFlow = reworkFlow;
	}

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "cpReWork_id", referencedColumnName = "id")
	public CPRework getCpRework() {
		return cpRework;
	}

	public void setCpRework(CPRework cpRework) {
		this.cpRework = cpRework;
	}

	public String getGist() {
		return gist;
	}

	public void setGist(String gist) {
		this.gist = gist;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((accomplishDate == null) ? 0 : accomplishDate.hashCode());
		result = prime * result
				+ ((attentionItem == null) ? 0 : attentionItem.hashCode());
		result = prime * result
				+ ((operator == null) ? 0 : operator.hashCode());
		result = prime * result
				+ ((reworkFlow == null) ? 0 : reworkFlow.hashCode());
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
		CPReworkItem other = (CPReworkItem) obj;
		if (accomplishDate == null) {
			if (other.accomplishDate != null)
				return false;
		} else if (!accomplishDate.equals(other.accomplishDate))
			return false;
		if (attentionItem == null) {
			if (other.attentionItem != null)
				return false;
		} else if (!attentionItem.equals(other.attentionItem))
			return false;
		if (operator == null) {
			if (other.operator != null)
				return false;
		} else if (!operator.equals(other.operator))
			return false;
		if (reworkFlow == null) {
			if (other.reworkFlow != null)
				return false;
		} else if (!reworkFlow.equals(other.reworkFlow))
			return false;
		return true;
	}

	@Override
	public String[] businessKeys() {
		// TODO Auto-generated method stub
		return null;
	}

}