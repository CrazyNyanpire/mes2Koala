package org.seu.acetec.mes2Koala.core.domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "T_TSK_INFO")
public class TskInfo extends AbstractQueryEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2919704732393915403L;
	@Column(name = "TOTAL_DICE")
	private Integer totalDice;
	@Column(name = "PASS_DICE")
	private Integer passDice;
	@Column(name = "FAIL_DICE")
	private Integer failDice;
	@Column(name = "YIELD")
	private String yield;
	@Column(name = "PASS_BIN2")
	private Integer passBin2;
	@Column(name = "LOT_NUM")
	private String lotNum;
	@Column(name = "DEVICE_NAME")
	private String deviceName;
	@Column(name = "OPERATOR_NAME")
	private String operatorName;
	@Column(name = "WAFER_ID")
	private String waferId;
	@Column(name = "TEST_SITE")
	private String testSite;
	@Column(name = "START_TIME")
	private Date startTime;
	@Column(name = "END_TIME")
	private Date endTime;
	@Column(name = "LOAD_TIME")
	private Date loadTime;
	@Column(name = "UNLOAD_TIME")
	private Date unloadTime;
	@Column(name = "WAFERINDEX")
	private String waferIndex;
	@Column(name = "CREATE_TIME")
	private Date createTime;
	@Column(name = "FILE_NAME")
	private String failName;

	public Integer getTotalDice() {
		return totalDice;
	}

	public void setTotalDice(Integer totalDice) {
		this.totalDice = totalDice;
	}

	public Integer getPassDice() {
		return passDice;
	}

	public void setPassDice(Integer passDice) {
		this.passDice = passDice;
	}

	public Integer getFailDice() {
		return failDice;
	}

	public void setFailDice(Integer failDice) {
		this.failDice = failDice;
	}

	public String getYield() {
		return yield;
	}

	public void setYield(String yield) {
		this.yield = yield;
	}

	public Integer getPassBin2() {
		return passBin2;
	}

	public void setPassBin2(Integer passBin2) {
		this.passBin2 = passBin2;
	}

	public String getLotNum() {
		return lotNum;
	}

	public void setLotNum(String lotNum) {
		this.lotNum = lotNum;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getWaferId() {
		return waferId;
	}

	public void setWaferId(String waferId) {
		this.waferId = waferId;
	}

	public String getTestSite() {
		return testSite;
	}

	public void setTestSite(String testSite) {
		this.testSite = testSite;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getLoadTime() {
		return loadTime;
	}

	public void setLoadTime(Date loadTime) {
		this.loadTime = loadTime;
	}

	public Date getUnloadTime() {
		return unloadTime;
	}

	public void setUnloadTime(Date unloadTime) {
		this.unloadTime = unloadTime;
	}

	public String getWaferIndex() {
		return waferIndex;
	}

	public void setWaferIndex(String waferIndex) {
		this.waferIndex = waferIndex;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getFailName() {
		return failName;
	}

	public void setFailName(String failName) {
		this.failName = failName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result
				+ ((deviceName == null) ? 0 : deviceName.hashCode());
		result = prime * result + ((endTime == null) ? 0 : endTime.hashCode());
		result = prime * result
				+ ((failDice == null) ? 0 : failDice.hashCode());
		result = prime * result
				+ ((failName == null) ? 0 : failName.hashCode());
		result = prime * result
				+ ((loadTime == null) ? 0 : loadTime.hashCode());
		result = prime * result + ((lotNum == null) ? 0 : lotNum.hashCode());
		result = prime * result
				+ ((operatorName == null) ? 0 : operatorName.hashCode());
		result = prime * result
				+ ((passBin2 == null) ? 0 : passBin2.hashCode());
		result = prime * result
				+ ((passDice == null) ? 0 : passDice.hashCode());
		result = prime * result
				+ ((startTime == null) ? 0 : startTime.hashCode());
		result = prime * result
				+ ((testSite == null) ? 0 : testSite.hashCode());
		result = prime * result
				+ ((totalDice == null) ? 0 : totalDice.hashCode());
		result = prime * result
				+ ((unloadTime == null) ? 0 : unloadTime.hashCode());
		result = prime * result + ((waferId == null) ? 0 : waferId.hashCode());
		result = prime * result
				+ ((waferIndex == null) ? 0 : waferIndex.hashCode());
		result = prime * result + ((yield == null) ? 0 : yield.hashCode());
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
		TskInfo other = (TskInfo) obj;
		if (createTime == null) {
			if (other.createTime != null)
				return false;
		} else if (!createTime.equals(other.createTime))
			return false;
		if (deviceName == null) {
			if (other.deviceName != null)
				return false;
		} else if (!deviceName.equals(other.deviceName))
			return false;
		if (endTime == null) {
			if (other.endTime != null)
				return false;
		} else if (!endTime.equals(other.endTime))
			return false;
		if (failDice == null) {
			if (other.failDice != null)
				return false;
		} else if (!failDice.equals(other.failDice))
			return false;
		if (failName == null) {
			if (other.failName != null)
				return false;
		} else if (!failName.equals(other.failName))
			return false;
		if (loadTime == null) {
			if (other.loadTime != null)
				return false;
		} else if (!loadTime.equals(other.loadTime))
			return false;
		if (lotNum == null) {
			if (other.lotNum != null)
				return false;
		} else if (!lotNum.equals(other.lotNum))
			return false;
		if (operatorName == null) {
			if (other.operatorName != null)
				return false;
		} else if (!operatorName.equals(other.operatorName))
			return false;
		if (passBin2 == null) {
			if (other.passBin2 != null)
				return false;
		} else if (!passBin2.equals(other.passBin2))
			return false;
		if (passDice == null) {
			if (other.passDice != null)
				return false;
		} else if (!passDice.equals(other.passDice))
			return false;
		if (startTime == null) {
			if (other.startTime != null)
				return false;
		} else if (!startTime.equals(other.startTime))
			return false;
		if (testSite == null) {
			if (other.testSite != null)
				return false;
		} else if (!testSite.equals(other.testSite))
			return false;
		if (totalDice == null) {
			if (other.totalDice != null)
				return false;
		} else if (!totalDice.equals(other.totalDice))
			return false;
		if (unloadTime == null) {
			if (other.unloadTime != null)
				return false;
		} else if (!unloadTime.equals(other.unloadTime))
			return false;
		if (waferId == null) {
			if (other.waferId != null)
				return false;
		} else if (!waferId.equals(other.waferId))
			return false;
		if (waferIndex == null) {
			if (other.waferIndex != null)
				return false;
		} else if (!waferIndex.equals(other.waferIndex))
			return false;
		if (yield == null) {
			if (other.yield != null)
				return false;
		} else if (!yield.equals(other.yield))
			return false;
		return true;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}
