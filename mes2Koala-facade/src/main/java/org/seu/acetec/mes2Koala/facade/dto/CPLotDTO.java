package org.seu.acetec.mes2Koala.facade.dto;

import java.util.Date;
import java.util.List;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;

public class CPLotDTO extends BaseRightInfoDTO implements Serializable {

	private Long id;

	private int version;

	private String shipmentProductNumber;

	private String diskContent;

	private String currentState;

	private String internalLotNumber;

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date lastModifyTimestamp;

	private Date lastModifyTimestampEnd;

	private Integer logic;

	private String lastModifyEmployNo;

	private String holdState;

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date createTimestamp;

	private Date createTimestampEnd;

	private String rcNumber;

	private String createEmployNo;

	private String parentIntegrationIds;

	private Long quantity;

	private Long parentSeparationId;
	
	private CustomerCPLotDTO customerCPLotDTO;
	
	private List<CPWaferDTO> cPWaferDTOs ;
	
	private CPProcessDTO cpProcessDTO;
	
	private String selectInfo;
	
	private String reworkInfo;

	private String wmsTestId; //用于保存WMS领料ID
	
	private boolean showFlag; //分批后母批不显示用Flag
	
	private Long cpInfoId;
	
	private Long sourceParentSeparationId;//原始母批no
	
    private String sourceParentSeparationNo;
	 
	public void setShipmentProductNumber(String shipmentProductNumber) {
		this.shipmentProductNumber = shipmentProductNumber;
	}

	public String getShipmentProductNumber() {
		return this.shipmentProductNumber;
	}

	public void setDiskContent(String diskContent) {
		this.diskContent = diskContent;
	}

	public String getDiskContent() {
		return this.diskContent;
	}

	public void setCurrentState(String currentState) {
		this.currentState = currentState;
	}

	public String getCurrentState() {
		return this.currentState;
	}

	public void setInternalLotNumber(String internalLotNumber) {
		this.internalLotNumber = internalLotNumber;
	}

	public String getInternalLotNumber() {
		return this.internalLotNumber;
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

	public void setHoldState(String holdState) {
		this.holdState = holdState;
	}

	public String getHoldState() {
		return this.holdState;
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

	public void setRcNumber(String rcNumber) {
		this.rcNumber = rcNumber;
	}

	public String getRcNumber() {
		return this.rcNumber;
	}

	public void setCreateEmployNo(String createEmployNo) {
		this.createEmployNo = createEmployNo;
	}

	public String getCreateEmployNo() {
		return this.createEmployNo;
	}

	public void setParentIntegrationIds(String parentIntegrationIds) {
		this.parentIntegrationIds = parentIntegrationIds;
	}

	public String getParentIntegrationIds() {
		return this.parentIntegrationIds;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public Long getQuantity() {
		return this.quantity;
	}

	public void setParentSeparationId(Long parentSeparationId) {
		this.parentSeparationId = parentSeparationId;
	}

	public Long getParentSeparationId() {
		return this.parentSeparationId;
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

	public CustomerCPLotDTO getCustomerCPLotDTO() {
		return customerCPLotDTO;
	}

	public void setCustomerCPLotDTO(CustomerCPLotDTO customerCPLotDTO) {
		this.customerCPLotDTO = customerCPLotDTO;
	}

	public List<CPWaferDTO> getcPWaferDTOs() {
		return cPWaferDTOs;
	}

	public void setcPWaferDTOs(List<CPWaferDTO> cPWaferDTOs) {
		this.cPWaferDTOs = cPWaferDTOs;
	}

	public CPProcessDTO getCpProcessDTO() {
		return cpProcessDTO;
	}

	public void setCpProcessDTO(CPProcessDTO cpProcessDTO) {
		this.cpProcessDTO = cpProcessDTO;
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
		CPLotDTO other = (CPLotDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getReworkInfo() {
		return reworkInfo;
	}

	public void setReworkInfo(String reworkInfo) {
		this.reworkInfo = reworkInfo;
	}

	public String getSelectInfo() {
		return selectInfo;
	}

	public void setSelectInfo(String selectInfo) {
		this.selectInfo = selectInfo;
	}

	public String getWmsTestId() {
		return wmsTestId;
	}

	public void setWmsTestId(String wmsTestId) {
		this.wmsTestId = wmsTestId;
	}

	public boolean isShowFlag() {
		return showFlag;
	}

	public void setShowFlag(boolean showFlag) {
		this.showFlag = showFlag;
	}

	public Long getSourceParentSeparationId() {
		return sourceParentSeparationId;
	}

	public void setSourceParentSeparationId(Long sourceParentSeparationId) {
		this.sourceParentSeparationId = sourceParentSeparationId;
	}

	public String getSourceParentSeparationNo() {
		return sourceParentSeparationNo;
	}

	public void setSourceParentSeparationNo(String sourceParentSeparationNo) {
		this.sourceParentSeparationNo = sourceParentSeparationNo;
	}

	public Long getCpInfoId() {
		return cpInfoId;
	}

	public void setCpInfoId(Long cpInfoId) {
		this.cpInfoId = cpInfoId;
	}
	
}