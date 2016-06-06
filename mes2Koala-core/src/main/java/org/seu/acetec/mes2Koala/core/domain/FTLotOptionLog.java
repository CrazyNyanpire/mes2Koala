package org.seu.acetec.mes2Koala.core.domain;

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
 * @created 11-01-2016 15:31:09
 */
@Entity
@Table(name = "e_ft_option_log")
@Access(AccessType.PROPERTY)
public class FTLotOptionLog extends MES2AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1213344157646668404L;
	private String optType;
	private String remark;
	private String flownow;
	private FTLot ftLot;

	public String getOptType() {
		return optType;
	}

	public void setOptType(String optType) {
		this.optType = optType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getFlownow() {
		return flownow;
	}

	public void setFlownow(String flownow) {
		this.flownow = flownow;
	}

	@ManyToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.EAGER)
	@JoinColumn(name = "ft_lot_ID", referencedColumnName = "ID")
	public FTLot getFtLot() {
		return ftLot;
	}

	public void setFtLot(FTLot ftLot) {
		this.ftLot = ftLot;
	}

	public void finalize() throws Throwable {

	}

	@Override
	public String[] businessKeys() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((flownow == null) ? 0 : flownow.hashCode());
		result = prime * result + ((ftLot == null) ? 0 : ftLot.hashCode());
		result = prime * result + ((optType == null) ? 0 : optType.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
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
		FTLotOptionLog other = (FTLotOptionLog) obj;
		if (flownow == null) {
			if (other.flownow != null)
				return false;
		} else if (!flownow.equals(other.flownow))
			return false;
		if (ftLot == null) {
			if (other.ftLot != null)
				return false;
		} else if (!ftLot.equals(other.ftLot))
			return false;
		if (optType == null) {
			if (other.optType != null)
				return false;
		} else if (!optType.equals(other.optType))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		return true;
	}

}