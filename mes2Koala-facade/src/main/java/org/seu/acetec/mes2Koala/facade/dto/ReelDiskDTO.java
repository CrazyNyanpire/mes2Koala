package org.seu.acetec.mes2Koala.facade.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;

public class ReelDiskDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2256412132938989632L;

	private Long id;

	private int version;

	private String reelCode;

	private String isFull;

	private String reelTime;

	private String poNumber;

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date lastModifyTimestamp;

	private Date lastModifyTimestampEnd;

	private Integer logic;

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date packagingTime;

	private Date packagingTimeEnd;

	private String packagingTimeStr;

	private String lastModifyEmployNo;

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date createTimestamp;

	private Date createTimestampEnd;

	private String time;

	private String createEmployNo;

	private String wflot;

	private String poNumberBox;

	private Integer quantity;

	private String dateCode;

	private String partNumber;

	private FTLotDTO combinedLotDTO;

	// private List<ReelDiskDTO> parentsIntegrationDTOs = new
	// ArrayList<ReelDiskDTO>();

	private ReelDiskDTO parentSeparationDTO;

	private FTLotDTO ftLotDTO;

	private String parentIntegrationIds;

	private String fromReelCode;

	private String combinedLotNumber;

	private String quality;

	private String failInfo;

	private FT_ResultDTO ftResultDTO;
	
	private String importedLotNumber;
	
	private String remark;

	public String getImportedLotNumber() {
		return importedLotNumber;
	}

	public void setImportedLotNumber(String importedLotNumber) {
		this.importedLotNumber = importedLotNumber;
	}

	public String getReelCode() {
		return this.reelCode;
	}

	public void setReelCode(String reelCode) {
		this.reelCode = reelCode;
	}

	public String getIsFull() {
		return this.isFull;
	}

	public void setIsFull(String isFull) {
		this.isFull = isFull;
	}

	public String getReelTime() {
		return this.reelTime;
	}

	public void setReelTime(String reelTime) {
		this.reelTime = reelTime;
	}

	public String getPoNumber() {
		return this.poNumber;
	}

	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
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

	public Date getPackagingTime() {
		return this.packagingTime;
	}

	public void setPackagingTime(Date packagingTime) {
		this.packagingTime = packagingTime;
	}

	public Date getPackagingTimeEnd() {
		return this.packagingTimeEnd;
	}

	public void setPackagingTimeEnd(Date packagingTimeEnd) {
		this.packagingTimeEnd = packagingTimeEnd;
	}

	public String getLastModifyEmployNo() {
		return this.lastModifyEmployNo;
	}

	public void setLastModifyEmployNo(String lastModifyEmployNo) {
		this.lastModifyEmployNo = lastModifyEmployNo;
	}

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

	public String getTime() {
		return this.time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getCreateEmployNo() {
		return this.createEmployNo;
	}

	public void setCreateEmployNo(String createEmployNo) {
		this.createEmployNo = createEmployNo;
	}

	public String getWflot() {
		return this.wflot;
	}

	public void setWflot(String wflot) {
		this.wflot = wflot;
	}

	public String getPoNumberBox() {
		return this.poNumberBox;
	}

	public void setPoNumberBox(String poNumberBox) {
		this.poNumberBox = poNumberBox;
	}

	public Integer getQuantity() {
		return this.quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getDateCode() {
		return this.dateCode;
	}

	public void setDateCode(String dateCode) {
		this.dateCode = dateCode;
	}

	public String getPartNumber() {
		return this.partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	public FTLotDTO getCombinedLotDTO() {
		return combinedLotDTO;
	}

	public void setCombinedLotDTO(FTLotDTO combinedLotDTO) {
		this.combinedLotDTO = combinedLotDTO;
	}

	/*
	 * public List<ReelDiskDTO> getParentsIntegrationDTOs() { return
	 * parentsIntegrationDTOs; }
	 * 
	 * public void setParentsIntegrationDTOs(List<ReelDiskDTO>
	 * parentsIntegrationDTOs) { this.parentsIntegrationDTOs =
	 * parentsIntegrationDTOs; }
	 */
	public ReelDiskDTO getParentSeparationDTO() {
		return parentSeparationDTO;
	}

	public void setParentSeparationDTO(ReelDiskDTO parentSeparationDTO) {
		this.parentSeparationDTO = parentSeparationDTO;
	}

	public String getParentIntegrationIds() {
		return parentIntegrationIds;
	}

	public void setParentIntegrationIds(String parentIntegrationIds) {
		this.parentIntegrationIds = parentIntegrationIds;
	}

	/*
	 * public String getParentIntegrationIds() { return parentIntegrationIds; }
	 * 
	 * public void setParentIntegrationIds(String parentIntegrationIds) {
	 * this.parentIntegrationIds = parentIntegrationIds; }
	 */
	public String getPackagingTimeStr() {
		return packagingTimeStr;
	}

	public void setPackagingTimeStr(String packagingTimeStr) {
		this.packagingTimeStr = packagingTimeStr;
	}

	public FTLotDTO getFtLotDTO() {
		return ftLotDTO;
	}

	public void setFtLotDTO(FTLotDTO ftLotDTO) {
		this.ftLotDTO = ftLotDTO;
	}

	public String getFromReelCode() {
		return fromReelCode;
	}

	public void setFromReelCode(String fromReelCode) {
		this.fromReelCode = fromReelCode;
	}

	public String getCombinedLotNumber() {
		return combinedLotNumber;
	}

	public void setCombinedLotNumber(String combinedLotNumber) {
		this.combinedLotNumber = combinedLotNumber;
	}

	public String getQuality() {
		return quality;
	}

	public void setQuality(String quality) {
		this.quality = quality;
	}

	public String getFailInfo() {
		return failInfo;
	}

	public void setFailInfo(String failInfo) {
		this.failInfo = failInfo;
	}

	public FT_ResultDTO getFtResultDTO() {
		return ftResultDTO;
	}

	public void setFtResultDTO(FT_ResultDTO ftResultDTO) {
		this.ftResultDTO = ftResultDTO;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
		ReelDiskDTO other = (ReelDiskDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}