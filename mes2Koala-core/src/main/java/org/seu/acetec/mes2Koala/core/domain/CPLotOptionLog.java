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
 * @author HongYu
 * @version 1.0
 * @created 08-04-2016 15:31:09
 */
@Entity
@Table(name = "e_cp_option_log")
@Access(AccessType.PROPERTY)
public class CPLotOptionLog extends MES2AbstractEntity {

	private static final long serialVersionUID = 936811931238277808L;
	private String optType;
	private String remark;
	private String flownow;
	private String slectInfo;
	private String reworkInfo;
	private CPLot cpLot;

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
	@JoinColumn(name = "cp_lot_ID", referencedColumnName = "ID")
	public CPLot getCpLot() {
		return cpLot;
	}

	public void setCpLot(CPLot cpLot) {
		this.cpLot = cpLot;
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
		result = prime * result + ((cpLot == null) ? 0 : cpLot.hashCode());
		result = prime * result + ((optType == null) ? 0 : optType.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((slectInfo == null) ? 0 : slectInfo.hashCode());
		result = prime * result + ((reworkInfo == null) ? 0 : reworkInfo.hashCode());
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
		CPLotOptionLog other = (CPLotOptionLog) obj;
		if (flownow == null) {
			if (other.flownow != null)
				return false;
		} else if (!flownow.equals(other.flownow))
			return false;
		if (cpLot == null) {
			if (other.cpLot != null)
				return false;
		} else if (!cpLot.equals(other.cpLot))
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
		if (slectInfo == null) {
			if (other.slectInfo != null)
				return false;
		} else if (!slectInfo.equals(other.slectInfo))
			return false;
		if (reworkInfo == null) {
			if (other.reworkInfo != null)
				return false;
		} else if (!reworkInfo.equals(other.reworkInfo))
			return false;
		return true;
	}

	public String getSlectInfo() {
		return slectInfo;
	}

	public void setSlectInfo(String slectInfo) {
		this.slectInfo = slectInfo;
	}

	public String getReworkInfo() {
		return reworkInfo;
	}

	public void setReworkInfo(String reworkInfo) {
		this.reworkInfo = reworkInfo;
	}

}