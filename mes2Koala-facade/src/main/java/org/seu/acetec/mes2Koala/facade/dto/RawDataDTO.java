package org.seu.acetec.mes2Koala.facade.dto;

import java.util.Date;

import java.io.Serializable;

public class RawDataDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8721585162275821309L;

	private Long id;

	private int version;

	private String lastModifyEmployNo;

	private Date createTimestamp;

	private Date createTimestampEnd;

	private String xDieSize;

	private String createEmployNo;

	private String bindefinitionFile;

	private String fabSite;

	private Date lastModifyTimestamp;

	private Date lastModifyTimestampEnd;

	private String gridXmax;

	private String productID;

	private String notchSide;

	private Integer logic;

	private String customerCodeID;

	private String yDieSize;
	
	private InternalProductDTO internalProductDTO;

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

	public void setXDieSize(String xDieSize) {
		this.xDieSize = xDieSize;
	}

	public String getXDieSize() {
		return this.xDieSize;
	}

	public void setCreateEmployNo(String createEmployNo) {
		this.createEmployNo = createEmployNo;
	}

	public String getCreateEmployNo() {
		return this.createEmployNo;
	}

	public void setBindefinitionFile(String bindefinitionFile) {
		this.bindefinitionFile = bindefinitionFile;
	}

	public String getBindefinitionFile() {
		return this.bindefinitionFile;
	}

	public void setFabSite(String fabSite) {
		this.fabSite = fabSite;
	}

	public String getFabSite() {
		return this.fabSite;
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

	public void setGridXmax(String gridXmax) {
		this.gridXmax = gridXmax;
	}

	public String getGridXmax() {
		return this.gridXmax;
	}

	public void setProductID(String productID) {
		this.productID = productID;
	}

	public String getProductID() {
		return this.productID;
	}

	public void setNotchSide(String notchSide) {
		this.notchSide = notchSide;
	}

	public String getNotchSide() {
		return this.notchSide;
	}

	public void setLogic(Integer logic) {
		this.logic = logic;
	}

	public Integer getLogic() {
		return this.logic;
	}

	public void setCustomerCodeID(String customerCodeID) {
		this.customerCodeID = customerCodeID;
	}

	public String getCustomerCodeID() {
		return this.customerCodeID;
	}

	public void setYDieSize(String yDieSize) {
		this.yDieSize = yDieSize;
	}

	public String getYDieSize() {
		return this.yDieSize;
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

	public InternalProductDTO getInternalProductDTO() {
		return internalProductDTO;
	}

	public void setInternalProductDTO(InternalProductDTO internalProductDTO) {
		this.internalProductDTO = internalProductDTO;
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
		RawDataDTO other = (RawDataDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}