package org.seu.acetec.mes2Koala.facade.dto;

import java.util.Date;

import java.io.Serializable;

public class CPWaferDTO implements Serializable {

	private Long id;

	private int version;

	private String lastModifyEmployNo;

	private Date createTimestamp;

	private Date createTimestampEnd;

	private String fail;

	private String internalWaferCode;

	private String createEmployNo;

	private String customerOffset;

	private String map;

	private Integer state;

	private String internalOffset;

	private Date lastModifyTimestamp;

	private Date lastModifyTimestampEnd;

	private Integer logic;

	private String pass;
	
	private String customerWaferCode;
	
	private String customerWaferIndex;
	
	private Integer isCheck;

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

	public void setFail(String fail) {
		this.fail = fail;
	}

	public String getFail() {
		return this.fail;
	}

	public void setInternalWaferCode(String internalWaferCode) {
		this.internalWaferCode = internalWaferCode;
	}

	public String getInternalWaferCode() {
		return this.internalWaferCode;
	}

	public void setCreateEmployNo(String createEmployNo) {
		this.createEmployNo = createEmployNo;
	}

	public String getCreateEmployNo() {
		return this.createEmployNo;
	}

	public void setCustomerOffset(String customerOffset) {
		this.customerOffset = customerOffset;
	}

	public String getCustomerOffset() {
		return this.customerOffset;
	}

	public void setMap(String map) {
		this.map = map;
	}

	public String getMap() {
		return this.map;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getState() {
		return this.state;
	}

	public void setInternalOffset(String internalOffset) {
		this.internalOffset = internalOffset;
	}

	public String getInternalOffset() {
		return this.internalOffset;
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

	public void setLogic(Integer logic) {
		this.logic = logic;
	}

	public Integer getLogic() {
		return this.logic;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getPass() {
		return this.pass;
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

	public String getCustomerWaferCode() {
		return customerWaferCode;
	}

	public void setCustomerWaferCode(String customerWaferCode) {
		this.customerWaferCode = customerWaferCode;
	}

	public String getCustomerWaferIndex() {
		return customerWaferIndex;
	}

	public void setCustomerWaferIndex(String customerWaferIndex) {
		this.customerWaferIndex = customerWaferIndex;
	}

	public Integer getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(Integer isCheck) {
		this.isCheck = isCheck;
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
		CPWaferDTO other = (CPWaferDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}