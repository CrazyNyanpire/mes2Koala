package org.seu.acetec.mes2Koala.facade.dto;

import java.util.Date;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;

public class FTQDNDTO implements Serializable {

	private Long id;

	private int version;

	private String reason;

	private Integer status;

	private String flowNow;

	private String qaSuggestion;

	private Integer failTotal;

	private String customerDeal;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date date;

	private Date dateEnd;

	private String QADeal;

	private String type;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTimestamp;

	private Date createTimestampEnd;

	private String customerDealNote;

	private String internalDealNote;

	private String toPerson;

	private String note;

	private String dealPerson;

	private String testerSys;

	private String customerSuggestion;

	private Integer failSample;

	private String qdnNo;

	private String attachment;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date lastModifyTimestamp;

	private Date lastModifyTimestampEnd;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date dealDate;

	private Date dealDateEnd;

	private Integer logic;

	private String lastModifyEmployNo;

	private String workPerson;

	private String customerNote;

	private String createEmployNo;

	private String method;

	private String suggestion;

	private String internalDeal;

	private Boolean isFuture;

	private String statusName;

	private String lotNo;

	private Long lotId;

	private Integer sampleQty;
	private Integer totalQty;
	private String qdnBin;

	private FTLotDTO ftLotDTO;

	private String customerName;

	private String toDepartment;

	private String qdnBinName;

	public String getLotNo() {
		return lotNo;
	}

	public void setLotNo(String lotNo) {
		this.lotNo = lotNo;
	}

	public String getFlow() {
		return flow;
	}

	public void setFlow(String flow) {
		this.flow = flow;
	}

	private String flow;

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

	public void setFailTotal(Integer failTotal) {
		this.failTotal = failTotal;
	}

	public Integer getFailTotal() {
		return this.failTotal;
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

	public void setQADeal(String QADeal) {
		this.QADeal = QADeal;
	}

	public String getQADeal() {
		return this.QADeal;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return this.type;
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

	public void setToPerson(String toPerson) {
		this.toPerson = toPerson;
	}

	public String getToPerson() {
		return this.toPerson;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getNote() {
		return this.note;
	}

	public void setDealPerson(String dealPerson) {
		this.dealPerson = dealPerson;
	}

	public String getDealPerson() {
		return this.dealPerson;
	}

	public void setTesterSys(String testerSys) {
		this.testerSys = testerSys;
	}

	public String getTesterSys() {
		return this.testerSys;
	}

	public void setCustomerSuggestion(String customerSuggestion) {
		this.customerSuggestion = customerSuggestion;
	}

	public String getCustomerSuggestion() {
		return this.customerSuggestion;
	}

	public void setFailSample(Integer failSample) {
		this.failSample = failSample;
	}

	public Integer getFailSample() {
		return this.failSample;
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

	public Boolean getIsFuture() {
		return isFuture;
	}

	public void setIsFuture(Boolean isFuture) {
		this.isFuture = isFuture;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getQaSuggestion() {
		return qaSuggestion;
	}

	public void setQASuggestion(String qaSuggestion) {
		this.qaSuggestion = qaSuggestion;
	}

	public Long getLotId() {
		return lotId;
	}

	public void setLotId(Long lotId) {
		this.lotId = lotId;
	}

	public Integer getSampleQty() {
		return sampleQty;
	}

	public void setSampleQty(Integer sampleQty) {
		this.sampleQty = sampleQty;
	}

	public Integer getTotalQty() {
		return totalQty;
	}

	public void setTotalQty(Integer totalQty) {
		this.totalQty = totalQty;
	}

	public String getQdnBin() {
		return qdnBin;
	}

	public void setQdnBin(String qdnBin) {
		this.qdnBin = qdnBin;
	}

	public FTLotDTO getFtLotDTO() {
		return ftLotDTO;
	}

	public void setFtLotDTO(FTLotDTO ftLotDTO) {
		this.ftLotDTO = ftLotDTO;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getToDepartment() {
		return toDepartment;
	}

	public void setToDepartment(String toDepartment) {
		this.toDepartment = toDepartment;
	}

	public String getInternalDealNote() {
		return internalDealNote;
	}

	public void setInternalDealNote(String internalDealNote) {
		this.internalDealNote = internalDealNote;
	}

	public String getInternalDeal() {
		return internalDeal;
	}

	public void setInternalDeal(String internalDeal) {
		this.internalDeal = internalDeal;
	}

	public String getQdnBinName() {
		return qdnBinName;
	}

	public void setQdnBinName(String qdnBinName) {
		this.qdnBinName = qdnBinName;
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
		FTQDNDTO other = (FTQDNDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}