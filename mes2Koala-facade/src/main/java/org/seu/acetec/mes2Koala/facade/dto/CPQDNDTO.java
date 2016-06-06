package org.seu.acetec.mes2Koala.facade.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;

public class CPQDNDTO implements Serializable {

	private static final long serialVersionUID = -1574077471000947097L;

	private Long id;

	private int version;
			
	private String reason;
		
	private String flowNow;
		
	private String flow;
		
	private String customerDeal;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date date;
		
	private Date dateEnd;
				
	private String type;
		
	private String qaDeal;
		
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTimestamp;
		
	private Date createTimestampEnd;
				
	private String customerDealNote;
		
	private String toPerson;
		
	private Boolean isFuture;
		
	private String failQty;
		
	private String toDepartment;
		
	private String note;
		
	private String internalDealNote;
		
	private String customerName;
		
	private String dealPerson;
		
	private Integer status;
		
	private String qASuggestion;
		
	private String internalDeal;
		
	private String customerSuggestion;
		
	private String qdnNo;
		
	private String attachment;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date lastModifyTimestamp;
		
	private Date lastModifyTimestampEnd;
				
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date dealDate;
		
	private Date dealDateEnd;
				
	private String testerSys;
		
	private Integer logic;
		
	private String lastModifyEmployNo;
		
	private String workPerson;
		
	private String customerNote;
		
	private String createEmployNo;
		
	private CPLotDTO cpLotDTO;
		
	private Long lotId;
				
	private String method;
		
	private String suggestion;
	
	private String riskLot;
	
	private String qdnInfo;
	
	private String qdnChk;
	
	private String lotNo;
	
	public void setReason(String reason) { 
		this.reason = reason;
	}

	public String getReason() {
		return this.reason;
	}
		
	public void setFlowNow(String flowNow) { 
		this.flowNow = flowNow;
	}

	public String getFlowNow() {
		return this.flowNow;
	}
		
	public void setFlow(String flow) { 
		this.flow = flow;
	}

	public String getFlow() {
		return this.flow;
	}

	public void setCustomerDeal(String customerDeal) { 
		this.customerDeal = customerDeal;
	}

	public String getCustomerDeal() {
		return this.customerDeal;
	}
			
	public void setDate(Date date) { 
		this.date = date;
	}

	public Date getDate() {
		return this.date;
	}
		
	public void setDateEnd(Date dateEnd) { 
		this.dateEnd = dateEnd;
	}

	public Date getDateEnd() {
		return this.dateEnd;
	}
			
	public void setType(String type) { 
		this.type = type;
	}

	public String getType() {
		return this.type;
	}
	
	public void setQaDeal(String qaDeal) { 
		this.qaDeal = qaDeal;
	}

	public String getQaDeal() {
		return this.qaDeal;
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
			
	
	public void setCustomerDealNote(String customerDealNote) { 
		this.customerDealNote = customerDealNote;
	}

	public String getCustomerDealNote() {
		return this.customerDealNote;
	}

	public void setIsFuture(Boolean isFuture) { 
		this.isFuture = isFuture;
	}

	public Boolean getIsFuture() {
		return this.isFuture;
	}

	public void setToDepartment(String toDepartment) { 
		this.toDepartment = toDepartment;
	}

	public String getToDepartment() {
		return this.toDepartment;
	}

	public void setNote(String note) { 
		this.note = note;
	}

	public String getNote() {
		return this.note;
	}

	public void setInternalDealNote(String internalDealNote) { 
		this.internalDealNote = internalDealNote;
	}

	public String getInternalDealNote() {
		return this.internalDealNote;
	}

	public void setCustomerName(String customerName) { 
		this.customerName = customerName;
	}

	public String getCustomerName() {
		return this.customerName;
	}

	public void setDealPerson(String dealPerson) { 
		this.dealPerson = dealPerson;
	}

	public String getDealPerson() {
		return this.dealPerson;
	}

	public void setStatus(Integer status) { 
		this.status = status;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setQASuggestion(String qASuggestion) { 
		this.qASuggestion = qASuggestion;
	}

	public String getQASuggestion() {
		return this.qASuggestion;
	}

	public void setInternalDeal(String internalDeal) { 
		this.internalDeal = internalDeal;
	}

	public String getInternalDeal() {
		return this.internalDeal;
	}

	public void setCustomerSuggestion(String customerSuggestion) { 
		this.customerSuggestion = customerSuggestion;
	}

	public String getCustomerSuggestion() {
		return this.customerSuggestion;
	}

	public void setQdnNo(String qdnNo) { 
		this.qdnNo = qdnNo;
	}

	public String getQdnNo() {
		return this.qdnNo;
	}

	public void setAttachment(String attachment) { 
		this.attachment = attachment;
	}

	public String getAttachment() {
		return this.attachment;
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
			
	public void setDealDate(Date dealDate) { 
		this.dealDate = dealDate;
	}

	public Date getDealDate() {
		return this.dealDate;
	}
		
	public void setDealDateEnd(Date dealDateEnd) { 
		this.dealDateEnd = dealDateEnd;
	}

	public Date getDealDateEnd() {
		return this.dealDateEnd;
	}
			
	public void setTesterSys(String testerSys) { 
		this.testerSys = testerSys;
	}

	public String getTesterSys() {
		return this.testerSys;
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

	public void setWorkPerson(String workPerson) { 
		this.workPerson = workPerson;
	}

	public String getWorkPerson() {
		return this.workPerson;
	}

	public void setCustomerNote(String customerNote) { 
		this.customerNote = customerNote;
	}

	public String getCustomerNote() {
		return this.customerNote;
	}

	public void setCreateEmployNo(String createEmployNo) { 
		this.createEmployNo = createEmployNo;
	}

	public String getCreateEmployNo() {
		return this.createEmployNo;
	}

	public void setMethod(String method) { 
		this.method = method;
	}

	public String getMethod() {
		return this.method;
	}

	public void setSuggestion(String suggestion) { 
		this.suggestion = suggestion;
	}

	public String getSuggestion() {
		return this.suggestion;
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

	public Long getLotId() {
		return lotId;
	}

	public void setLotId(Long lotId) {
		this.lotId = lotId;
	}

	public String getRiskLot() {
		return riskLot;
	}

	public void setRiskLot(String riskLot) {
		this.riskLot = riskLot;
	}

	public String getQdnInfo() {
		return qdnInfo;
	}

	public void setQdnInfo(String qdnInfo) {
		this.qdnInfo = qdnInfo;
	}

	public String getQdnChk() {
		return qdnChk;
	}

	public void setQdnChk(String qdnChk) {
		this.qdnChk = qdnChk;
	}

	public String getFailQty() {
		return failQty;
	}

	public void setFailQty(String failQty) {
		this.failQty = failQty;
	}
	

	public CPLotDTO getCpLotDTO() {
		return cpLotDTO;
	}

	public void setCpLotDTO(CPLotDTO cpLotDTO) {
		this.cpLotDTO = cpLotDTO;
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
		CPQDNDTO other = (CPQDNDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getLotNo() {
		return lotNo;
	}

	public void setLotNo(String lotNo) {
		this.lotNo = lotNo;
	}

	public String getToPerson() {
		return toPerson;
	}

	public void setToPerson(String toPerson) {
		this.toPerson = toPerson;
	}

}