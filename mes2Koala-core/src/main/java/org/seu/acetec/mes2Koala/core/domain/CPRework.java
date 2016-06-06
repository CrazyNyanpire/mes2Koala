package org.seu.acetec.mes2Koala.core.domain;

import java.util.List;
import java.util.Set;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * @author harlow
 * @version 1.0
 * @created 21-四月-2016 14:06:15
 */
@Entity
@DiscriminatorValue("CP")
@Access(AccessType.PROPERTY)
public class CPRework extends Rework {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2502702963810997L;
	private CPLot reworkLot;

	private Set<CPReworkItem> reworkItems;

	private String reworkFailDut;

	private String reworkPC;

	private String reworkWaferId;
	
	private String gist;

	@ManyToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.EAGER)
	@JoinColumn(name = "cp_lot_ID", referencedColumnName = "ID")
	public CPLot getReworkLot() {
		return reworkLot;
	}

	public void setReworkLot(CPLot reworkLot) {
		this.reworkLot = reworkLot;
	}

	@OneToMany(mappedBy = "cpRework", cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	public Set<CPReworkItem> getReworkItems() {
		return reworkItems;
	}

	public void setReworkItems(Set<CPReworkItem> reworkItems) {
		this.reworkItems = reworkItems;
	}

	public String getReworkFailDut() {
		return reworkFailDut;
	}

	public void setReworkFailDut(String reworkFailDut) {
		this.reworkFailDut = reworkFailDut;
	}

	public String getReworkPC() {
		return reworkPC;
	}

	public void setReworkPC(String reworkPC) {
		this.reworkPC = reworkPC;
	}

	public String getReworkWaferId() {
		return reworkWaferId;
	}

	public void setReworkWaferId(String reworkWaferId) {
		this.reworkWaferId = reworkWaferId;
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
				+ ((reworkFailDut == null) ? 0 : reworkFailDut.hashCode());
		result = prime * result
				+ ((reworkItems == null) ? 0 : reworkItems.hashCode());
		result = prime * result
				+ ((reworkLot == null) ? 0 : reworkLot.hashCode());
		result = prime * result
				+ ((reworkPC == null) ? 0 : reworkPC.hashCode());
		result = prime * result
				+ ((reworkWaferId == null) ? 0 : reworkWaferId.hashCode());
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
		CPRework other = (CPRework) obj;
		if (reworkFailDut == null) {
			if (other.reworkFailDut != null)
				return false;
		} else if (!reworkFailDut.equals(other.reworkFailDut))
			return false;
		if (reworkItems == null) {
			if (other.reworkItems != null)
				return false;
		} else if (!reworkItems.equals(other.reworkItems))
			return false;
		if (reworkLot == null) {
			if (other.reworkLot != null)
				return false;
		} else if (!reworkLot.equals(other.reworkLot))
			return false;
		if (reworkPC == null) {
			if (other.reworkPC != null)
				return false;
		} else if (!reworkPC.equals(other.reworkPC))
			return false;
		if (reworkWaferId == null) {
			if (other.reworkWaferId != null)
				return false;
		} else if (!reworkWaferId.equals(other.reworkWaferId))
			return false;
		return true;
	}
	
}