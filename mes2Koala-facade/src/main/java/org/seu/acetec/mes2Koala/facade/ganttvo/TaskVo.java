package org.seu.acetec.mes2Koala.facade.ganttvo;

import java.io.Serializable;

public class TaskVo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 367411473361816773L;
	private String name;
	private String from;
	private String to;
	private String id;
	private String actualFrom;
	private String actualTo;
	private Long amount;
	private Long doneQty;
	private String site;
	private String note;
	private String state;
	private String testSysId;
	private String testSysName;
	private String ftLotId;
	private Integer version;



	public String getTestSysName() {
		return testSysName;
	}


	public void setTestSysName(String testSysName) {
		this.testSysName = testSysName;
	}


	public String getFtLotId() {
		return ftLotId;
	}


	public void setFtLotId(String ftLotId) {
		this.ftLotId = ftLotId;
	}


	public Integer getVersion() {
		return version;
	}


	public void setVersion(Integer version) {
		this.version = version;
	}


	public String getTestSysId() {
		return testSysId;
	}


	public void setTestSysId(String testSysId) {
		this.testSysId = testSysId;
	}


	public String getActualFrom() {
		return actualFrom;
	}


	public void setActualFrom(String actualFrom) {
		this.actualFrom = actualFrom;
	}


	public String getActualTo() {
		return actualTo;
	}


	public void setActualTo(String actualTo) {
		this.actualTo = actualTo;
	}


	public Long getAmount() {
		return amount;
	}


	public void setAmount(Long amount) {
		this.amount = amount;
	}


	public Long getDoneQty() {
		return doneQty;
	}


	public void setDoneQty(Long doneQty) {
		this.doneQty = doneQty;
	}


	public String getSite() {
		return site;
	}


	public void setSite(String site) {
		this.site = site;
	}


	public String getNote() {
		return note;
	}


	public void setNote(String note) {
		this.note = note;
	}

	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}

	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	public String getFrom() {
		return from;
	}


	public void setFrom(String from) {
		this.from = from;
	}


	public String getTo() {
		return to;
	}


	public void setTo(String to) {
		this.to = to;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actualFrom == null) ? 0 : actualFrom.hashCode());
		result = prime * result + ((actualTo == null) ? 0 : actualTo.hashCode());
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + ((doneQty == null) ? 0 : doneQty.hashCode());
		result = prime * result + ((from == null) ? 0 : from.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((note == null) ? 0 : note.hashCode());
		result = prime * result + ((site == null) ? 0 : site.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((testSysId == null) ? 0 : testSysId.hashCode());
		result = prime * result + ((to == null) ? 0 : to.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
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
		TaskVo other = (TaskVo) obj;
		if (actualFrom == null) {
			if (other.actualFrom != null)
				return false;
		} else if (!actualFrom.equals(other.actualFrom))
			return false;
		if (actualTo == null) {
			if (other.actualTo != null)
				return false;
		} else if (!actualTo.equals(other.actualTo))
			return false;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (doneQty == null) {
			if (other.doneQty != null)
				return false;
		} else if (!doneQty.equals(other.doneQty))
			return false;
		if (from == null) {
			if (other.from != null)
				return false;
		} else if (!from.equals(other.from))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (note == null) {
			if (other.note != null)
				return false;
		} else if (!note.equals(other.note))
			return false;
		if (site == null) {
			if (other.site != null)
				return false;
		} else if (!site.equals(other.site))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (testSysId == null) {
			if (other.testSysId != null)
				return false;
		} else if (!testSysId.equals(other.testSysId))
			return false;
		if (to == null) {
			if (other.to != null)
				return false;
		} else if (!to.equals(other.to))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}

}
