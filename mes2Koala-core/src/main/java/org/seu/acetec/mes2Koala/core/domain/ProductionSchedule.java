package org.seu.acetec.mes2Koala.core.domain;

import javax.persistence.*;

import org.seu.acetec.mes2Koala.infra.MyDateUtils;

import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)    //使用joined继承类型
@Table(name = "e_production_schedule")
@Access(AccessType.PROPERTY)
public class ProductionSchedule extends MES2AbstractEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 2767790359942741541L;
	protected String state;
	protected String subState;
	protected Date planedStartTimestamp;
	protected Date planedEndTimestamp;
	protected Date actualStartTimestamp;
	protected Date actualEndTimestamp;
	protected Long amount; // 总量
	protected Long doneQty; // 已测数量
	protected String note;
	protected Double plannedTimeTakes; // 预计需要时间:数量/实际UPH
	protected Double actualTimeTakes; // 实际需要时间:Testing站点进站出站的时间差
	@Deprecated
	protected Long scheduleOrder;	//在当前机台的测试任务中所处的排序位置

	protected TestSys testSys; // 需要与机台建立联系

	// 以下为冗余字段，在下单创建排产任务时复制进来，之后不应再做修改
	protected String lotNumber; // 由于存在中途分合批的情况，因此需要该字段
	protected String pPO;// PPO
	protected String customerProductNumber;// 客户（产品）型号
	protected String packageNumber;// 封装型号
	protected String customerLotNumber;// 客户批号
	
	public String getSubState() {
		return subState;
	}

	public void setSubState(String subState) {
		this.subState = subState;
	}

	public Double getActualTimeTakes() {
		return actualTimeTakes;
	}

	public void setActualTimeTakes(Double actualTimeTakes) {
		this.actualTimeTakes = actualTimeTakes;
	}

	public void setPlannedTimeTakes(Double plannedTimeTakes) {
		this.plannedTimeTakes = plannedTimeTakes;
	}

	public Long getScheduleOrder() {
		return scheduleOrder;
	}

	public void setScheduleOrder(Long scheduleOrder) {
		this.scheduleOrder = scheduleOrder;
	}

	public String getpPO() {
		return pPO;
	}

	public void setpPO(String pPO) {
		this.pPO = pPO;
	}

	public String getCustomerProductNumber() {
		return customerProductNumber;
	}

	public void setCustomerProductNumber(String customerProductNumber) {
		this.customerProductNumber = customerProductNumber;
	}

	public String getPackageNumber() {
		return packageNumber;
	}

	public void setPackageNumber(String packageNumber) {
		this.packageNumber = packageNumber;
	}

	public String getCustomerLotNumber() {
		return customerLotNumber;
	}

	public void setCustomerLotNumber(String customerLotNumber) {
		this.customerLotNumber = customerLotNumber;
	}

	public Double getPlannedTimeTakes() {
		return plannedTimeTakes;
	}

	@ManyToOne
	@JoinColumn(name = "test_sys_id", referencedColumnName = "id", nullable = true )
	public TestSys getTestSys() {
		return testSys;
	}

	public void setTestSys(TestSys testSys) {
		this.testSys = testSys;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getPlanedStartTimestamp() {
		return planedStartTimestamp;
	}

	public void setPlanedStartTimestamp(Date planedStartTimestamp) {
		this.planedStartTimestamp = planedStartTimestamp;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getPlanedEndTimestamp() {
		return planedEndTimestamp;
	}

	public void setPlanedEndTimestamp(Date planedEndTimestamp) {
		this.planedEndTimestamp = planedEndTimestamp;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getActualStartTimestamp() {
		return actualStartTimestamp;
	}

	public void setActualStartTimestamp(Date actualStartTimestamp) {
		this.actualStartTimestamp = actualStartTimestamp;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getActualEndTimestamp() {
		return actualEndTimestamp;
	}

	public void setActualEndTimestamp(Date actualEndTimestamp) {
		this.actualEndTimestamp = actualEndTimestamp;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
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

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getLotNumber() {
		return lotNumber;
	}

	public void setLotNumber(String lotNumber) {
		this.lotNumber = lotNumber;
	}
	
	/**
	 * 用于获取所属节点名称，一般会被子类覆盖
	 * @return
	 */
	@Transient
	public String getNodeName() {
		return "无法获取";
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((actualEndTimestamp == null) ? 0 : actualEndTimestamp.hashCode());
		result = prime * result + ((actualStartTimestamp == null) ? 0 : actualStartTimestamp.hashCode());
		result = prime * result + ((actualTimeTakes == null) ? 0 : actualTimeTakes.hashCode());
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + ((customerLotNumber == null) ? 0 : customerLotNumber.hashCode());
		result = prime * result + ((customerProductNumber == null) ? 0 : customerProductNumber.hashCode());
		result = prime * result + ((doneQty == null) ? 0 : doneQty.hashCode());
		result = prime * result + ((lotNumber == null) ? 0 : lotNumber.hashCode());
		result = prime * result + ((note == null) ? 0 : note.hashCode());
		result = prime * result + ((pPO == null) ? 0 : pPO.hashCode());
		result = prime * result + ((packageNumber == null) ? 0 : packageNumber.hashCode());
		result = prime * result + ((planedEndTimestamp == null) ? 0 : planedEndTimestamp.hashCode());
		result = prime * result + ((planedStartTimestamp == null) ? 0 : planedStartTimestamp.hashCode());
		result = prime * result + ((plannedTimeTakes == null) ? 0 : plannedTimeTakes.hashCode());
		result = prime * result + ((scheduleOrder == null) ? 0 : scheduleOrder.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((testSys == null) ? 0 : testSys.hashCode());
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
		ProductionSchedule other = (ProductionSchedule) obj;
		if (actualEndTimestamp == null) {
			if (other.actualEndTimestamp != null)
				return false;
		} else if (!actualEndTimestamp.equals(other.actualEndTimestamp))
			return false;
		if (actualStartTimestamp == null) {
			if (other.actualStartTimestamp != null)
				return false;
		} else if (!actualStartTimestamp.equals(other.actualStartTimestamp))
			return false;
		if (actualTimeTakes == null) {
			if (other.actualTimeTakes != null)
				return false;
		} else if (!actualTimeTakes.equals(other.actualTimeTakes))
			return false;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (customerLotNumber == null) {
			if (other.customerLotNumber != null)
				return false;
		} else if (!customerLotNumber.equals(other.customerLotNumber))
			return false;
		if (customerProductNumber == null) {
			if (other.customerProductNumber != null)
				return false;
		} else if (!customerProductNumber.equals(other.customerProductNumber))
			return false;
		if (doneQty == null) {
			if (other.doneQty != null)
				return false;
		} else if (!doneQty.equals(other.doneQty))
			return false;
		if (lotNumber == null) {
			if (other.lotNumber != null)
				return false;
		} else if (!lotNumber.equals(other.lotNumber))
			return false;
		if (note == null) {
			if (other.note != null)
				return false;
		} else if (!note.equals(other.note))
			return false;
		if (pPO == null) {
			if (other.pPO != null)
				return false;
		} else if (!pPO.equals(other.pPO))
			return false;
		if (packageNumber == null) {
			if (other.packageNumber != null)
				return false;
		} else if (!packageNumber.equals(other.packageNumber))
			return false;
		if (planedEndTimestamp == null) {
			if (other.planedEndTimestamp != null)
				return false;
		} else if (!planedEndTimestamp.equals(other.planedEndTimestamp))
			return false;
		if (planedStartTimestamp == null) {
			if (other.planedStartTimestamp != null)
				return false;
		} else if (!planedStartTimestamp.equals(other.planedStartTimestamp))
			return false;
		if (plannedTimeTakes == null) {
			if (other.plannedTimeTakes != null)
				return false;
		} else if (!plannedTimeTakes.equals(other.plannedTimeTakes))
			return false;
		if (scheduleOrder == null) {
			if (other.scheduleOrder != null)
				return false;
		} else if (!scheduleOrder.equals(other.scheduleOrder))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (testSys == null) {
			if (other.testSys != null)
				return false;
		} else if (!testSys.equals(other.testSys))
			return false;
		return true;
	}

	@Override
	public String[] businessKeys() {
		// TODO Auto-generated method stub
		return null;
	}

}
