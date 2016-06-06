package org.seu.acetec.mes2Koala.facade.dto;

import java.util.Date;

import java.io.Serializable;

public class TestSysDTO implements Serializable {

	private Long id;

	private int version;

	private Date lastModifyTimestamp;

	private Date lastModifyTimestampEnd;

	private String lastModifyEmployNo;

	private String createEmployNo;

	private Date createTimestamp;

	private Date createTimestampEnd;

	private Integer logic;

	private String state;

	private String platformNumber; // 平台编号

	private String testerNumber; // tester编号，CP根据此字段绑定

	private String proberOrHandlerNumber; // proberOrHandler编号

	private String testType; // 测试类型，FT或CP

	public String getPlatformNumber() {
		return platformNumber;
	}

	public void setPlatformNumber(String platformNumber) {
		this.platformNumber = platformNumber;
	}

	public String getTesterNumber() {
		return testerNumber;
	}

	public void setTesterNumber(String testerNumber) {
		this.testerNumber = testerNumber;
	}

	public String getProberOrHandlerNumber() {
		return proberOrHandlerNumber;
	}

	public void setProberOrHandlerNumber(String proberOrHandlerNumber) {
		this.proberOrHandlerNumber = proberOrHandlerNumber;
	}

	public String getTestType() {
		return testType;
	}

	public void setTestType(String testType) {
		this.testType = testType;
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

	public void setLastModifyEmployNo(String lastModifyEmployNo) {
		this.lastModifyEmployNo = lastModifyEmployNo;
	}

	public String getLastModifyEmployNo() {
		return this.lastModifyEmployNo;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getState() {
		return this.state;
	}

	public void setCreateEmployNo(String createEmployNo) {
		this.createEmployNo = createEmployNo;
	}

	public String getCreateEmployNo() {
		return this.createEmployNo;
	}

	public void setLogic(Integer logic) {
		this.logic = logic;
	}

	public Integer getLogic() {
		return this.logic;
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
		result = prime * result + ((createEmployNo == null) ? 0 : createEmployNo.hashCode());
		result = prime * result + ((createTimestamp == null) ? 0 : createTimestamp.hashCode());
		result = prime * result + ((createTimestampEnd == null) ? 0 : createTimestampEnd.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastModifyEmployNo == null) ? 0 : lastModifyEmployNo.hashCode());
		result = prime * result + ((lastModifyTimestamp == null) ? 0 : lastModifyTimestamp.hashCode());
		result = prime * result + ((lastModifyTimestampEnd == null) ? 0 : lastModifyTimestampEnd.hashCode());
		result = prime * result + ((logic == null) ? 0 : logic.hashCode());
		result = prime * result + ((platformNumber == null) ? 0 : platformNumber.hashCode());
		result = prime * result + ((proberOrHandlerNumber == null) ? 0 : proberOrHandlerNumber.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((testType == null) ? 0 : testType.hashCode());
		result = prime * result + ((testerNumber == null) ? 0 : testerNumber.hashCode());
		result = prime * result + version;
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
		TestSysDTO other = (TestSysDTO) obj;
		if (createEmployNo == null) {
			if (other.createEmployNo != null)
				return false;
		} else if (!createEmployNo.equals(other.createEmployNo))
			return false;
		if (createTimestamp == null) {
			if (other.createTimestamp != null)
				return false;
		} else if (!createTimestamp.equals(other.createTimestamp))
			return false;
		if (createTimestampEnd == null) {
			if (other.createTimestampEnd != null)
				return false;
		} else if (!createTimestampEnd.equals(other.createTimestampEnd))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastModifyEmployNo == null) {
			if (other.lastModifyEmployNo != null)
				return false;
		} else if (!lastModifyEmployNo.equals(other.lastModifyEmployNo))
			return false;
		if (lastModifyTimestamp == null) {
			if (other.lastModifyTimestamp != null)
				return false;
		} else if (!lastModifyTimestamp.equals(other.lastModifyTimestamp))
			return false;
		if (lastModifyTimestampEnd == null) {
			if (other.lastModifyTimestampEnd != null)
				return false;
		} else if (!lastModifyTimestampEnd.equals(other.lastModifyTimestampEnd))
			return false;
		if (logic == null) {
			if (other.logic != null)
				return false;
		} else if (!logic.equals(other.logic))
			return false;
		if (platformNumber == null) {
			if (other.platformNumber != null)
				return false;
		} else if (!platformNumber.equals(other.platformNumber))
			return false;
		if (proberOrHandlerNumber == null) {
			if (other.proberOrHandlerNumber != null)
				return false;
		} else if (!proberOrHandlerNumber.equals(other.proberOrHandlerNumber))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (testType == null) {
			if (other.testType != null)
				return false;
		} else if (!testType.equals(other.testType))
			return false;
		if (testerNumber == null) {
			if (other.testerNumber != null)
				return false;
		} else if (!testerNumber.equals(other.testerNumber))
			return false;
		if (version != other.version)
			return false;
		return true;
	}
}