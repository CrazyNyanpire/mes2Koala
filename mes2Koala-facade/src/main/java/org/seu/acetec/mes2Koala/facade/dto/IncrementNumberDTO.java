package org.seu.acetec.mes2Koala.facade.dto;

import java.util.Date;

import java.io.Serializable;

public class IncrementNumberDTO implements Serializable {

	private Long id;

	private int version;

	private Integer intervalNum;

	private Date lastModifyTimestamp;

	private Date lastModifyTimestampEnd;

	private String type;

	private String startStr;

	private Integer logic;

	private String lastModifyEmployNo;

	private Date createTimestamp;

	private Date createTimestampEnd;

	private String createEmployNo;

	private String endStr;

	private Boolean isDayClean;

	private Integer maxNum;

	private Integer serialNum;

	private Boolean isZero;

	public void setIntervalNum(Integer intervalNum) {
		this.intervalNum = intervalNum;
	}

	public Integer getIntervalNum() {
		return this.intervalNum;
	}

	public void setLastModifyTimestamp(Date lastModifyTimestamp) {
		this.lastModifyTimestamp = lastModifyTimestamp;
	}

	public Date getLastModifyTimestamp() {
		return this.lastModifyTimestamp;
	}

	public void setLastModifyTimestampEnd(Date lastModifyTimestampEnd) {
		this.lastModifyTimestampEnd = lastModifyTimestampEnd;
	}

	public Date getLastModifyTimestampEnd() {
		return this.lastModifyTimestampEnd;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return this.type;
	}

	public void setStartStr(String startStr) {
		this.startStr = startStr;
	}

	public String getStartStr() {
		return this.startStr;
	}

	public void setLogic(Integer logic) {
		this.logic = logic;
	}

	public Integer getLogic() {
		return this.logic;
	}

	public void setLastModifyEmployNo(String lastModifyEmployNo) {
		this.lastModifyEmployNo = lastModifyEmployNo;
	}

	public String getLastModifyEmployNo() {
		return this.lastModifyEmployNo;
	}

	public void setCreateTimestamp(Date createTimestamp) {
		this.createTimestamp = createTimestamp;
	}

	public Date getCreateTimestamp() {
		return this.createTimestamp;
	}

	public void setCreateTimestampEnd(Date createTimestampEnd) {
		this.createTimestampEnd = createTimestampEnd;
	}

	public Date getCreateTimestampEnd() {
		return this.createTimestampEnd;
	}

	public void setCreateEmployNo(String createEmployNo) {
		this.createEmployNo = createEmployNo;
	}

	public String getCreateEmployNo() {
		return this.createEmployNo;
	}

	public void setEndStr(String endStr) {
		this.endStr = endStr;
	}

	public String getEndStr() {
		return this.endStr;
	}

	public void setIsDayClean(Boolean isDayClean) {
		this.isDayClean = isDayClean;
	}

	public Boolean getIsDayClean() {
		return this.isDayClean;
	}

	public void setMaxNum(Integer maxNum) {
		this.maxNum = maxNum;
	}

	public Integer getMaxNum() {
		return this.maxNum;
	}

	public void setSerialNum(Integer serialNum) {
		this.serialNum = serialNum;
	}

	public Integer getSerialNum() {
		return this.serialNum;
	}

	public void setIsZero(Boolean isZero) {
		this.isZero = isZero;
	}

	public Boolean getIsZero() {
		return this.isZero;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IncrementNumberDTO other = (IncrementNumberDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}