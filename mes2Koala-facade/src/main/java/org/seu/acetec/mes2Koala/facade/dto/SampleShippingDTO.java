package org.seu.acetec.mes2Koala.facade.dto;

import java.util.Date;

import java.io.Serializable;

public class SampleShippingDTO implements Serializable {

	private Long id;

	private int version;

	private String lastModifyEmployNo;

	private Date createTimestamp;

	private Date createTimestampEnd;

	private String createEmployNo;

	private String quality;

	private Integer qtyTotal;

	private Integer qty;

	private Date lastModifyTimestamp;

	private Date lastModifyTimestampEnd;

	private Integer logic;

	private String note;
	
	private FTLotDTO fTLotDTO;
	
	private ReelDiskDTO reelDiskDTO;
	
	private Long ftLotId;
	
	private Long reelCodeID;
	

	public void setLastModifyEmployNo(String lastModifyEmployNo) {
		this.lastModifyEmployNo = lastModifyEmployNo;
	}

	public String getLastModifyEmployNo() {
		return this.lastModifyEmployNo;
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

	public void setCreateEmployNo(String createEmployNo) {
		this.createEmployNo = createEmployNo;
	}

	public String getCreateEmployNo() {
		return this.createEmployNo;
	}

	public void setQuality(String quality) {
		this.quality = quality;
	}

	public String getQuality() {
		return this.quality;
	}

	public void setQtyTotal(Integer qtyTotal) {
		this.qtyTotal = qtyTotal;
	}

	public Integer getQtyTotal() {
		return this.qtyTotal;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public Integer getQty() {
		return this.qty;
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

	public void setNote(String note) {
		this.note = note;
	}

	public String getNote() {
		return this.note;
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

	public FTLotDTO getFTLotDTO() {
		return fTLotDTO;
	}

	public void setFTLotDTO(FTLotDTO fTLotDTO) {
		this.fTLotDTO = fTLotDTO;
	}

	public Long getFtLotId() {
		return ftLotId;
	}

	public void setFtLotId(Long ftLotId) {
		this.ftLotId = ftLotId;
	}

	public Long getReelCodeID() {
		return reelCodeID;
	}

	public void setReelCodeID(Long reelCodeID) {
		this.reelCodeID = reelCodeID;
	}

	public ReelDiskDTO getReelDiskDTO() {
		return reelDiskDTO;
	}

	public void setReelDiskDTO(ReelDiskDTO reelDiskDTO) {
		this.reelDiskDTO = reelDiskDTO;
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
		SampleShippingDTO other = (SampleShippingDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}