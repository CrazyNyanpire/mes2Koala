package org.seu.acetec.mes2Koala.core.domain;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

/**
 * @author harlow
 * @version 1.0
 * @created 11-01-2016 15:31:00
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "e_qdn")
public class QDN extends MES2AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1450705017553582852L;
	private String customerNote;
	private String customerSuggestion;
	private String method;
	private String note;
	private String qaSuggestion;
	private String reason;
	private String suggestion;
	private String attachment;
	private String qdnNo;
	private String flowNow;

	public QDN() {

	}

	public String getCustomerNote() {
		return customerNote;
	}

	public void setCustomerNote(String customerNote) {
		this.customerNote = customerNote;
	}

	public String getCustomerSuggestion() {
		return customerSuggestion;
	}

	public void setCustomerSuggestion(String customerSuggestion) {
		this.customerSuggestion = customerSuggestion;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getQASuggestion() {
		return qaSuggestion;
	}

	public void setQASuggestion(String qaSuggestion) {
		this.qaSuggestion = qaSuggestion;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getSuggestion() {
		return suggestion;
	}

	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public String getQdnNo() {
		return qdnNo;
	}

	public void setQdnNo(String qdnNo) {
		this.qdnNo = qdnNo;
	}

	public String getFlowNow() {
		return flowNow;
	}

	public void setFlowNow(String flowNow) {
		this.flowNow = flowNow;
	}

	public void finalize() throws Throwable {

	}

	@Override
	public String[] businessKeys() {
		return new String[] { String.valueOf(getId()),
				this.getCreateTimestamp().toString() };
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((qaSuggestion == null) ? 0 : qaSuggestion.hashCode());
		result = prime * result
				+ ((attachment == null) ? 0 : attachment.hashCode());
		result = prime * result
				+ ((customerNote == null) ? 0 : customerNote.hashCode());
		result = prime
				* result
				+ ((customerSuggestion == null) ? 0 : customerSuggestion
						.hashCode());
		result = prime * result + ((flowNow == null) ? 0 : flowNow.hashCode());
		result = prime * result + ((method == null) ? 0 : method.hashCode());
		result = prime * result + ((note == null) ? 0 : note.hashCode());
		result = prime * result + ((qdnNo == null) ? 0 : qdnNo.hashCode());
		result = prime * result + ((reason == null) ? 0 : reason.hashCode());
		result = prime * result
				+ ((suggestion == null) ? 0 : suggestion.hashCode());
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
		QDN other = (QDN) obj;
		if (qaSuggestion == null) {
			if (other.qaSuggestion != null)
				return false;
		} else if (!qaSuggestion.equals(other.qaSuggestion))
			return false;
		if (attachment == null) {
			if (other.attachment != null)
				return false;
		} else if (!attachment.equals(other.attachment))
			return false;
		if (customerNote == null) {
			if (other.customerNote != null)
				return false;
		} else if (!customerNote.equals(other.customerNote))
			return false;
		if (customerSuggestion == null) {
			if (other.customerSuggestion != null)
				return false;
		} else if (!customerSuggestion.equals(other.customerSuggestion))
			return false;
		if (flowNow == null) {
			if (other.flowNow != null)
				return false;
		} else if (!flowNow.equals(other.flowNow))
			return false;
		if (method == null) {
			if (other.method != null)
				return false;
		} else if (!method.equals(other.method))
			return false;
		if (note == null) {
			if (other.note != null)
				return false;
		} else if (!note.equals(other.note))
			return false;
		if (qdnNo == null) {
			if (other.qdnNo != null)
				return false;
		} else if (!qdnNo.equals(other.qdnNo))
			return false;
		if (reason == null) {
			if (other.reason != null)
				return false;
		} else if (!reason.equals(other.reason))
			return false;
		if (suggestion == null) {
			if (other.suggestion != null)
				return false;
		} else if (!suggestion.equals(other.suggestion))
			return false;
		return true;
	}

}