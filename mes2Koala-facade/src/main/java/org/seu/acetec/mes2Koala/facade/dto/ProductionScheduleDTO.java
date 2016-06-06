package org.seu.acetec.mes2Koala.facade.dto;

import java.util.Date;

import java.io.Serializable;

public class ProductionScheduleDTO implements Serializable {

	private Long id;

	private int version;

			
		private String note;
		
				
		private Long amount;
		
				
		private Long doneQty;
		
				
		private String planedEndTimestamp;
		
				
		private String createEmployNo;
		
				
		private String lotNumber;
		
		private String nodeName;
		
				
		private Date createTimestamp;
		
		private Date createTimestampEnd;
				
		private String actualEndTimestamp;
		
				
		private Date lastModifyTimestamp;
		
		private Date lastModifyTimestampEnd;
				
		private String lastModifyEmployNo;
		
				
		private String planedStartTimestamp;
		
				
		private String actualStartTimestamp;
		
				
		private Integer logic;
		
				
		private String state;
		
		private String subState;
		

		private Long testSysId;//机台ID
		
		private String testSysName;//机台
		
		private String pPO;//PPO
		
		private String customerProductNumber;//客户（产品）型号
		
		private String packageNumber;//封装型号
		
		private String customerLotNumber;//客户批号
		
		private String plannedTimeTakes;//预计需时
		
		private String actualTimeTakes;//实际需时
		
		private String testType;//测试类型

		private String lotCurrentState;//批次当前状态
		
		private String lotHoldState;//批次hold状态
		
	

	public String getLotCurrentState() {
			return lotCurrentState;
		}

		public void setLotCurrentState(String lotCurrentState) {
			this.lotCurrentState = lotCurrentState;
		}

		public String getLotHoldState() {
			return lotHoldState;
		}

		public void setLotHoldState(String lotHoldState) {
			this.lotHoldState = lotHoldState;
		}

	public String getSubState() {
			return subState;
		}

		public void setSubState(String subState) {
			this.subState = subState;
		}

	public String getNodeName() {
			return nodeName;
		}

		public void setNodeName(String nodeName) {
			this.nodeName = nodeName;
		}

	public String getTestType() {
			return testType;
		}

		public void setTestType(String testType) {
			this.testType = testType;
		}

	public Long getTestSysId() {
			return testSysId;
		}

		public void setTestSysId(Long testSysId) {
			this.testSysId = testSysId;
		}

	public String getTestSysName() {
			return testSysName;
		}

		public void setTestSysName(String testSysName) {
			this.testSysName = testSysName;
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

		public String getPlannedTimeTakes() {
			return plannedTimeTakes;
		}

		public void setPlannedTimeTakes(String plannedTimeTakes) {
			this.plannedTimeTakes = plannedTimeTakes;
		}

		public String getActualTimeTakes() {
			return actualTimeTakes;
		}

		public void setActualTimeTakes(String actualTimeTakes) {
			this.actualTimeTakes = actualTimeTakes;
		}

	public void setNote(String note) { 
		this.note = note;
	}

	public String getNote() {
		return this.note;
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

	public void setPlanedEndTimestamp(String planedEndTimestamp) { 
		this.planedEndTimestamp = planedEndTimestamp;
	}

	public String getPlanedEndTimestamp() {
		return this.planedEndTimestamp;
	}
		
			
	
	public void setCreateEmployNo(String createEmployNo) { 
		this.createEmployNo = createEmployNo;
	}

	public String getCreateEmployNo() {
		return this.createEmployNo;
	}
		
			
	
	public void setLotNumber(String lotNumber) { 
		this.lotNumber = lotNumber;
	}

	public String getLotNumber() {
		return this.lotNumber;
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
			
	
	public void setActualEndTimestamp(String actualEndTimestamp) { 
		this.actualEndTimestamp = actualEndTimestamp;
	}

	public String getActualEndTimestamp() {
		return this.actualEndTimestamp;
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
		
			
	
	public void setPlanedStartTimestamp(String planedStartTimestamp) { 
		this.planedStartTimestamp = planedStartTimestamp;
	}

	public String getPlanedStartTimestamp() {
		return this.planedStartTimestamp;
	}
		
			
	
	public void setActualStartTimestamp(String actualStartTimestamp) { 
		this.actualStartTimestamp = actualStartTimestamp;
	}

	public String getActualStartTimestamp() {
		return this.actualStartTimestamp;
	}
		
			
	
	public void setLogic(Integer logic) { 
		this.logic = logic;
	}

	public Integer getLogic() {
		return this.logic;
	}
		
			
	
	public void setState(String state) { 
		this.state = state;
	}

	public String getState() {
		return this.state;
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
		result = prime * result + ((actualEndTimestamp == null) ? 0 : actualEndTimestamp.hashCode());
		result = prime * result + ((actualStartTimestamp == null) ? 0 : actualStartTimestamp.hashCode());
		result = prime * result + ((actualTimeTakes == null) ? 0 : actualTimeTakes.hashCode());
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + ((createEmployNo == null) ? 0 : createEmployNo.hashCode());
		result = prime * result + ((createTimestamp == null) ? 0 : createTimestamp.hashCode());
		result = prime * result + ((createTimestampEnd == null) ? 0 : createTimestampEnd.hashCode());
		result = prime * result + ((customerLotNumber == null) ? 0 : customerLotNumber.hashCode());
		result = prime * result + ((customerProductNumber == null) ? 0 : customerProductNumber.hashCode());
		result = prime * result + ((doneQty == null) ? 0 : doneQty.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastModifyEmployNo == null) ? 0 : lastModifyEmployNo.hashCode());
		result = prime * result + ((lastModifyTimestamp == null) ? 0 : lastModifyTimestamp.hashCode());
		result = prime * result + ((lastModifyTimestampEnd == null) ? 0 : lastModifyTimestampEnd.hashCode());
		result = prime * result + ((logic == null) ? 0 : logic.hashCode());
		result = prime * result + ((lotNumber == null) ? 0 : lotNumber.hashCode());
		result = prime * result + ((note == null) ? 0 : note.hashCode());
		result = prime * result + ((pPO == null) ? 0 : pPO.hashCode());
		result = prime * result + ((packageNumber == null) ? 0 : packageNumber.hashCode());
		result = prime * result + ((planedEndTimestamp == null) ? 0 : planedEndTimestamp.hashCode());
		result = prime * result + ((planedStartTimestamp == null) ? 0 : planedStartTimestamp.hashCode());
		result = prime * result + ((plannedTimeTakes == null) ? 0 : plannedTimeTakes.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((testSysId == null) ? 0 : testSysId.hashCode());
		result = prime * result + ((testSysName == null) ? 0 : testSysName.hashCode());
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
		ProductionScheduleDTO other = (ProductionScheduleDTO) obj;
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
		if (testSysName == null) {
			if (other.testSysName != null)
				return false;
		} else if (!testSysName.equals(other.testSysName))
			return false;
		if (version != other.version)
			return false;
		return true;
	}
}