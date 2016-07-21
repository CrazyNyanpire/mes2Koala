package org.seu.acetec.mes2Koala.facade.dto;

import java.util.Date;
import java.util.List;
import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.seu.acetec.mes2Koala.facade.common.JsonDateTimeSerializer;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonAutoDetect
public class CPReworkDTO implements Serializable {

	private Long id;

	private int version;

	private String summary;

	private String reasonOther;

	private Date reworkDate;

	private Date reworkDateEnd;

	private Integer reworkReworkQty;

	private CPLotDTO reworkLot;

	private Date lastModifyTimestamp;

	private Date lastModifyTimestampEnd;

	private Integer logic;

	private String lastModifyEmployNo;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTimestamp;

	private Date createTimestampEnd;

	private String reasonExplanation;

	private String reworkRCNo;

	private String createEmployNo;

	private String reworkNo;

	private String reasonReasons;

	private String reworkCustomer;

	private Integer reworkTotalQty;

	private String reworkEquipment;

	private String reworkDepartment;

	private Long lotId;

	private List<CPReworkItemDTO> reworkItems;

	private String product;

	private String lotNo;

	private String approvePerson;

	private Long internalLotId;

	private String reworkType;

	private String approve;

	private Date approveDate;

	private String approveRemark;
	
	private String reworkFailDut;
	
	private String reworkPC;
	
	private String reworkWaferId;
	
	private String gist;
	
	private String testerNo;

	public String getSummary() {
		return this.summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getReasonOther() {
		return this.reasonOther;
	}

	public void setReasonOther(String reasonOther) {
		this.reasonOther = reasonOther;
	}

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	public Date getReworkDate() {
		return this.reworkDate;
	}

	public void setReworkDate(Date reworkDate) {
		this.reworkDate = reworkDate;
	}

	public Date getReworkDateEnd() {
		return this.reworkDateEnd;
	}

	public void setReworkDateEnd(Date reworkDateEnd) {
		this.reworkDateEnd = reworkDateEnd;
	}

	public Integer getReworkReworkQty() {
		return this.reworkReworkQty;
	}

	public void setReworkReworkQty(Integer reworkReworkQty) {
		this.reworkReworkQty = reworkReworkQty;
	}

	public CPLotDTO getReworkLot() {
		return this.reworkLot;
	}

	public void setReworkLot(CPLotDTO reworkLot) {
		this.reworkLot = reworkLot;
	}

	public Date getLastModifyTimestamp() {
		return this.lastModifyTimestamp;
	}

	public void setLastModifyTimestamp(Date lastModifyTimestamp) {
		this.lastModifyTimestamp = lastModifyTimestamp;
	}

	public Date getLastModifyTimestampEnd() {
		return this.lastModifyTimestampEnd;
	}

	public void setLastModifyTimestampEnd(Date lastModifyTimestampEnd) {
		this.lastModifyTimestampEnd = lastModifyTimestampEnd;
	}

	public Integer getLogic() {
		return this.logic;
	}

	public void setLogic(Integer logic) {
		this.logic = logic;
	}

	public String getLastModifyEmployNo() {
		return this.lastModifyEmployNo;
	}

	public void setLastModifyEmployNo(String lastModifyEmployNo) {
		this.lastModifyEmployNo = lastModifyEmployNo;
	}

	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getCreateTimestamp() {
		return this.createTimestamp;
	}

	public void setCreateTimestamp(Date createTimestamp) {
		this.createTimestamp = createTimestamp;
	}

	public Date getCreateTimestampEnd() {
		return this.createTimestampEnd;
	}

	public void setCreateTimestampEnd(Date createTimestampEnd) {
		this.createTimestampEnd = createTimestampEnd;
	}

	public String getReasonExplanation() {
		return this.reasonExplanation;
	}

	public void setReasonExplanation(String reasonExplanation) {
		this.reasonExplanation = reasonExplanation;
	}

	public String getReworkRCNo() {
		return this.reworkRCNo;
	}

	public void setReworkRCNo(String reworkRCNo) {
		this.reworkRCNo = reworkRCNo;
	}

	public String getCreateEmployNo() {
		return this.createEmployNo;
	}

	public void setCreateEmployNo(String createEmployNo) {
		this.createEmployNo = createEmployNo;
	}

	public String getReworkNo() {
		return this.reworkNo;
	}

	public void setReworkNo(String reworkNo) {
		this.reworkNo = reworkNo;
	}

	public String getReasonReasons() {
		return this.reasonReasons;
	}

	public void setReasonReasons(String reasonReasons) {
		this.reasonReasons = reasonReasons;
	}

	public String getReworkCustomer() {
		return this.reworkCustomer;
	}

	public void setReworkCustomer(String reworkCustomer) {
		this.reworkCustomer = reworkCustomer;
	}

	public Integer getReworkTotalQty() {
		return this.reworkTotalQty;
	}

	public void setReworkTotalQty(Integer reworkTotalQty) {
		this.reworkTotalQty = reworkTotalQty;
	}

	public String getReworkEquipment() {
		return this.reworkEquipment;
	}

	public void setReworkEquipment(String reworkEquipment) {
		this.reworkEquipment = reworkEquipment;
	}

	public String getReworkDepartment() {
		return this.reworkDepartment;
	}

	public void setReworkDepartment(String reworkDepartment) {
		this.reworkDepartment = reworkDepartment;
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

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getApprovePerson() {
		return approvePerson;
	}

	public void setApprovePerson(String approvePerson) {
		this.approvePerson = approvePerson;
	}

	public Long getInternalLotId() {
		return internalLotId;
	}

	public void setInternalLotId(Long internalLotId) {
		this.internalLotId = internalLotId;
	}

	public String getReworkType() {
		return reworkType;
	}

	public void setReworkType(String reworkType) {
		this.reworkType = reworkType;
	}

	public String getApprove() {
		return approve;
	}

	public void setApprove(String approve) {
		this.approve = approve;
	}

	public Date getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}

	public String getApproveRemark() {
		return approveRemark;
	}

	public void setApproveRemark(String approveRemark) {
		this.approveRemark = approveRemark;
	}

	public List<CPReworkItemDTO> getReworkItems() {
		return reworkItems;
	}

	public void setReworkItems(List<CPReworkItemDTO> reworkItems) {
		this.reworkItems = reworkItems;
	}

	public String getReworkFailDut() {
		return reworkFailDut;
	}

	public void setReworkFailDut(String reworkFailDut) {
		this.reworkFailDut = reworkFailDut;
	}

	public String getReworkPC() {
		return reworkPC;
	}

	public void setReworkPC(String reworkPC) {
		this.reworkPC = reworkPC;
	}

	public String getReworkWaferId() {
		return reworkWaferId;
	}

	public void setReworkWaferId(String reworkWaferId) {
		this.reworkWaferId = reworkWaferId;
	}


	public Long getLotId() {
		return lotId;
	}

	public void setLotId(Long lotId) {
		this.lotId = lotId;
	}

	public String getLotNo() {
		return lotNo;
	}

	public void setLotNo(String lotNo) {
		this.lotNo = lotNo;
	}

	public String getGist() {
		return gist;
	}

	public void setGist(String gist) {
		this.gist = gist;
	}

	public String getTesterNo() {
		return testerNo;
	}

	public void setTesterNo(String testerNo) {
		this.testerNo = testerNo;
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
		CPReworkDTO other = (CPReworkDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}