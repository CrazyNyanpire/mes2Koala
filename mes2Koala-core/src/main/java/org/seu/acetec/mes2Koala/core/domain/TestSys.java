package org.seu.acetec.mes2Koala.core.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name = "e_test_sys")
@Access(AccessType.PROPERTY)
public class TestSys extends MES2AbstractEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7399074701309893753L;

	private String state;	//状态，待用
	private String platformNumber;	//平台编号
	private String testerNumber;	//tester编号，CP根据此字段绑定
	private String proberOrHandlerNumber;	//proberOrHandler编号
	private String testType;	//测试类型，FT或CP
	private Long emsPlatformId;	//EMS平台ID
	
	private List<ProductionSchedule> productions = new ArrayList<>();

	public Long getEmsPlatformId() {
		return emsPlatformId;
	}

	public void setEmsPlatformId(Long emsPlatformId) {
		this.emsPlatformId = emsPlatformId;
	}

	public String getPlatformNumber() {
		return platformNumber;
	}

	public void setPlatformNumber(String platformNumber) {
		if ( platformNumber == null )
			this.platformNumber = testerNumber + '&' + proberOrHandlerNumber;
		else
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

	@OneToMany( mappedBy = "testSys", fetch = FetchType.EAGER )
	@OrderBy("planedStartTimestamp ASC")
	public List<ProductionSchedule> getProductions() {
		return productions;
	}

	public void setProductions(List<ProductionSchedule> productions) {
		this.productions = productions;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((platformNumber == null) ? 0 : platformNumber.hashCode());
		result = prime * result + ((proberOrHandlerNumber == null) ? 0 : proberOrHandlerNumber.hashCode());
		result = prime * result + ((productions == null) ? 0 : productions.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((testType == null) ? 0 : testType.hashCode());
		result = prime * result + ((testerNumber == null) ? 0 : testerNumber.hashCode());
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
		TestSys other = (TestSys) obj;
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
		if (productions == null) {
			if (other.productions != null)
				return false;
		} else if (!productions.equals(other.productions))
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
		return true;
	}
	@Override
	public String[] businessKeys() {
		// TODO Auto-generated method stub
		return null;
	}

}
