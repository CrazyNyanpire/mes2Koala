package org.seu.acetec.mes2Koala.core.domain;

import java.util.Date;
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
import javax.persistence.Table;

/**
 * @author harlow
 * @version 1.0
 * @created 21-一月-2016 14:06:15
 * @modify 21-四月-2016 14:06:15
 */
@Entity
@DiscriminatorValue("FT")
@Access(AccessType.PROPERTY)
public class FTRework extends Rework {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2502702963810997L;
	private FTLot reworkLot;
	private Set<FTReworkItem> reworkItems;

	@ManyToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.EAGER)
	@JoinColumn(name = "ft_lot_ID", referencedColumnName = "ID")
	public FTLot getReworkLot() {
		return reworkLot;
	}

	public void setReworkLot(FTLot reworkLot) {
		this.reworkLot = reworkLot;
	}

	@OneToMany(mappedBy = "ftRework", cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	public Set<FTReworkItem> getReworkItems() {
		return reworkItems;
	}

	public void setReworkItems(Set<FTReworkItem> reworkItems) {
		this.reworkItems = reworkItems;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((reworkItems == null) ? 0 : reworkItems.hashCode());
		result = prime * result
				+ ((reworkLot == null) ? 0 : reworkLot.hashCode());
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
		FTRework other = (FTRework) obj;
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
		return true;
	}

}