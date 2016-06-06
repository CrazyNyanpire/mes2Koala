package org.seu.acetec.mes2Koala.core.domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "e_increment_number")
@Access(AccessType.PROPERTY)
public class IncrementNumber extends MES2AbstractEntity {
    /**
	 * 
	 */
	private static final long serialVersionUID = -5091106139516231378L;

	private Integer intervalNum; //每次增加量
      
    private Integer maxNum; //预定的最大值 
       
    private Integer serialNum; //当前值
    
    private String startStr;
    
    private String endStr;
    
    private String type;
    
    private Boolean isZero;//是否补0
    
    private Boolean isDayClean;//是否每天重置

	public Integer getIntervalNum() {
		return intervalNum;
	}

	public void setIntervalNum(Integer intervalNum) {
		this.intervalNum = intervalNum;
	}

	public Integer getMaxNum() {
		return maxNum;
	}

	public void setMaxNum(Integer maxNum) {
		this.maxNum = maxNum;
	}

	public Integer getSerialNum() {
		return serialNum;
	}

	public void setSerialNum(Integer serialNum) {
		this.serialNum = serialNum;
	}

	public String getStartStr() {
		return startStr;
	}

	public void setStartStr(String startStr) {
		this.startStr = startStr;
	}

	public String getEndStr() {
		return endStr;
	}

	public void setEndStr(String endStr) {
		this.endStr = endStr;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Boolean getIsZero() {
		return isZero;
	}

	public void setIsZero(Boolean isZero) {
		this.isZero = isZero;
	}

	public Boolean getIsDayClean() {
		return isDayClean;
	}

	public void setIsDayClean(Boolean isDayClean) {
		this.isDayClean = isDayClean;
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
		result = prime * result + ((endStr == null) ? 0 : endStr.hashCode());
		result = prime * result
				+ ((intervalNum == null) ? 0 : intervalNum.hashCode());
		result = prime * result
				+ ((isDayClean == null) ? 0 : isDayClean.hashCode());
		result = prime * result + ((isZero == null) ? 0 : isZero.hashCode());
		result = prime * result + ((maxNum == null) ? 0 : maxNum.hashCode());
		result = prime * result
				+ ((serialNum == null) ? 0 : serialNum.hashCode());
		result = prime * result
				+ ((startStr == null) ? 0 : startStr.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		IncrementNumber other = (IncrementNumber) obj;
		if (endStr == null) {
			if (other.endStr != null)
				return false;
		} else if (!endStr.equals(other.endStr))
			return false;
		if (intervalNum == null) {
			if (other.intervalNum != null)
				return false;
		} else if (!intervalNum.equals(other.intervalNum))
			return false;
		if (isDayClean == null) {
			if (other.isDayClean != null)
				return false;
		} else if (!isDayClean.equals(other.isDayClean))
			return false;
		if (isZero == null) {
			if (other.isZero != null)
				return false;
		} else if (!isZero.equals(other.isZero))
			return false;
		if (maxNum == null) {
			if (other.maxNum != null)
				return false;
		} else if (!maxNum.equals(other.maxNum))
			return false;
		if (serialNum == null) {
			if (other.serialNum != null)
				return false;
		} else if (!serialNum.equals(other.serialNum))
			return false;
		if (startStr == null) {
			if (other.startStr != null)
				return false;
		} else if (!startStr.equals(other.startStr))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}


}
