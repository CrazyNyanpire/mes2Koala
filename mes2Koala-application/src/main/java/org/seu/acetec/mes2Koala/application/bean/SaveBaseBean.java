package org.seu.acetec.mes2Koala.application.bean;

import java.util.Date;

public class SaveBaseBean {
	private int version;

	private Date createTimestamp;

	private Date lastModifyTimestamp;

	private String createEmployNo;

	private String lastModifyEmployNo;

	private Integer logic;

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public Date getCreateTimestamp() {
		return createTimestamp;
	}

	public void setCreateTimestamp(Date createTimestamp) {
		this.createTimestamp = createTimestamp;
	}

	public Date getLastModifyTimestamp() {
		return lastModifyTimestamp;
	}

	public void setLastModifyTimestamp(Date lastModifyTimestamp) {
		this.lastModifyTimestamp = lastModifyTimestamp;
	}

	public String getCreateEmployNo() {
		return createEmployNo;
	}

	public void setCreateEmployNo(String createEmployNo) {
		this.createEmployNo = createEmployNo;
	}

	public String getLastModifyEmployNo() {
		return lastModifyEmployNo;
	}

	public void setLastModifyEmployNo(String lastModifyEmployNo) {
		this.lastModifyEmployNo = lastModifyEmployNo;
	}

	public Integer getLogic() {
		return logic;
	}

	public void setLogic(Integer logic) {
		this.logic = logic;
	}

}
