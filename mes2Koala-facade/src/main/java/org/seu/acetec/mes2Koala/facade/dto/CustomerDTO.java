package org.seu.acetec.mes2Koala.facade.dto;

import java.util.Date;

import java.io.Serializable;

public class CustomerDTO implements Serializable {

	private Long id;

	private int version;


	private String englishName;


	private String code;


	private String address2;


	private String address1;


	private String createEmployNo;

	private String createTime;

	private Date createTimestamp;

	private Date createTimestampEnd;

	private String number;


	private Date lastModifyTimestamp;

	private Date lastModifyTimestampEnd;

	private String lastModifyEmployNo;


	private String phone;


	private String chineseName;


	private String logo;


	private Integer logic;


	private String status;

	private String reelFixCode;

	private String reelQty;
	
	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}

	public String getEnglishName() {
		return this.englishName;
	}


	public void setCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}


	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getAddress2() {
		return this.address2;
	}


	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress1() {
		return this.address1;
	}


	public void setCreateEmployNo(String createEmployNo) {
		this.createEmployNo = createEmployNo;
	}

	public String getCreateEmployNo() {
		return this.createEmployNo;
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


	public void setNumber(String number) {
		this.number = number;
	}

	public String getNumber() {
		return this.number;
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


	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}

	public String getChineseName() {
		return this.chineseName;
	}


	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getLogo() {
		return this.logo;
	}


	public void setLogic(Integer logic) {
		this.logic = logic;
	}

	public Integer getLogic() {
		return this.logic;
	}


	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return this.status;
	}


	public String getReelFixCode() {
		return reelFixCode;
	}

	public void setReelFixCode(String reelFixCode) {
		this.reelFixCode = reelFixCode;
	}

	public String getReelQty() {
		return reelQty;
	}

	public void setReelQty(String reelQty) {
		this.reelQty = reelQty;
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
		result = prime * result + ((address1 == null) ? 0 : address1.hashCode());
		result = prime * result + ((address2 == null) ? 0 : address2.hashCode());
		result = prime * result + ((chineseName == null) ? 0 : chineseName.hashCode());
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((createEmployNo == null) ? 0 : createEmployNo.hashCode());
		result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result + ((createTimestamp == null) ? 0 : createTimestamp.hashCode());
		result = prime * result + ((createTimestampEnd == null) ? 0 : createTimestampEnd.hashCode());
		result = prime * result + ((englishName == null) ? 0 : englishName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastModifyEmployNo == null) ? 0 : lastModifyEmployNo.hashCode());
		result = prime * result + ((lastModifyTimestamp == null) ? 0 : lastModifyTimestamp.hashCode());
		result = prime * result + ((lastModifyTimestampEnd == null) ? 0 : lastModifyTimestampEnd.hashCode());
		result = prime * result + ((logic == null) ? 0 : logic.hashCode());
		result = prime * result + ((logo == null) ? 0 : logo.hashCode());
		result = prime * result + ((number == null) ? 0 : number.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		CustomerDTO other = (CustomerDTO) obj;
		if (address1 == null) {
			if (other.address1 != null)
				return false;
		} else if (!address1.equals(other.address1))
			return false;
		if (address2 == null) {
			if (other.address2 != null)
				return false;
		} else if (!address2.equals(other.address2))
			return false;
		if (chineseName == null) {
			if (other.chineseName != null)
				return false;
		} else if (!chineseName.equals(other.chineseName))
			return false;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (createEmployNo == null) {
			if (other.createEmployNo != null)
				return false;
		} else if (!createEmployNo.equals(other.createEmployNo))
			return false;
		if (createTime == null) {
			if (other.createTime != null)
				return false;
		} else if (!createTime.equals(other.createTime))
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
		if (englishName == null) {
			if (other.englishName != null)
				return false;
		} else if (!englishName.equals(other.englishName))
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
		if (logo == null) {
			if (other.logo != null)
				return false;
		} else if (!logo.equals(other.logo))
			return false;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (version != other.version)
			return false;
		return true;
	}
}