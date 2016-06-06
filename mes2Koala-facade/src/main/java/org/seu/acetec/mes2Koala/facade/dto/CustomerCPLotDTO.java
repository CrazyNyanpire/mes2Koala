package org.seu.acetec.mes2Koala.facade.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author 阙宇翔
 * @version 2016/2/27
 */
public class CustomerCPLotDTO {

    /**
     * {@link org.seu.acetec.mes2Koala.core.domain.MES2AbstractEntity}
     */
    private Long id;
    private int version;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTimestamp;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date lastModifyTimestamp;
    private String createEmployNo;
    private String lastModifyEmployNo;
    private Integer logic;
    private Object createTimestampEnd;
    private Object lastModifyTimestampEnd;

    /**
     * {@link org.seu.acetec.mes2Koala.core.domain.CustomerLot}
     */
    private String productVersion;
    private Long parentSeparationId;
    private String parentIntegrationIds;
    private Integer state;
    private String customerPPO;
    private String customerNumber;
    private String customerLotNumber;
    private CPInfoDTO internalProductDTO;
    private String wmsId;

    /**
     * {@link org.seu.acetec.mes2Koala.core.domain.CustomerCPLot}
     */
    private String maskName;
    private String packingLot;
    private String size;
    private String waferLot;
    private String materialType;

    public String getCreateEmployNo() {
        return createEmployNo;
    }

    public void setCreateEmployNo(String createEmployNo) {
        this.createEmployNo = createEmployNo;
    }

    public Date getCreateTimestamp() {
        return createTimestamp;
    }

    public void setCreateTimestamp(Date createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

    public Object getCreateTimestampEnd() {
        return createTimestampEnd;
    }

    public void setCreateTimestampEnd(Object createTimestampEnd) {
        this.createTimestampEnd = createTimestampEnd;
    }

    public String getCustomerLotNumber() {
        return customerLotNumber;
    }

    public void setCustomerLotNumber(String customerLotNumber) {
        this.customerLotNumber = customerLotNumber;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getCustomerPPO() {
        return customerPPO;
    }

    public void setCustomerPPO(String customerPPO) {
        this.customerPPO = customerPPO;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CPInfoDTO getInternalProductDTO() {
        return internalProductDTO;
    }

    public void setInternalProductDTO(CPInfoDTO internalProductDTO) {
        this.internalProductDTO = internalProductDTO;
    }

    public String getLastModifyEmployNo() {
        return lastModifyEmployNo;
    }

    public void setLastModifyEmployNo(String lastModifyEmployNo) {
        this.lastModifyEmployNo = lastModifyEmployNo;
    }

    public Date getLastModifyTimestamp() {
        return lastModifyTimestamp;
    }

    public void setLastModifyTimestamp(Date lastModifyTimestamp) {
        this.lastModifyTimestamp = lastModifyTimestamp;
    }

    public Object getLastModifyTimestampEnd() {
        return lastModifyTimestampEnd;
    }

    public void setLastModifyTimestampEnd(Object lastModifyTimestampEnd) {
        this.lastModifyTimestampEnd = lastModifyTimestampEnd;
    }

    public Integer getLogic() {
        return logic;
    }

    public void setLogic(Integer logic) {
        this.logic = logic;
    }

    public String getMaskName() {
        return maskName;
    }

    public void setMaskName(String maskName) {
        this.maskName = maskName;
    }

    public String getParentIntegrationIds() {
        return parentIntegrationIds;
    }

    public void setParentIntegrationIds(String parentIntegrationIds) {
        this.parentIntegrationIds = parentIntegrationIds;
    }

    public Long getParentSeparationId() {
        return parentSeparationId;
    }

    public void setParentSeparationId(Long parentSeparationId) {
        this.parentSeparationId = parentSeparationId;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getWmsId() {
        return wmsId;
    }

    public void setWmsId(String wmsId) {
        this.wmsId = wmsId;
    }

    public String getProductVersion() {
		return productVersion;
	}

	public void setProductVersion(String productVersion) {
		this.productVersion = productVersion;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CustomerCPLotDTO that = (CustomerCPLotDTO) o;

        if (version != that.version) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (createTimestamp != null ? !createTimestamp.equals(that.createTimestamp) : that.createTimestamp != null)
            return false;
        if (lastModifyTimestamp != null ? !lastModifyTimestamp.equals(that.lastModifyTimestamp) : that.lastModifyTimestamp != null)
            return false;
        if (createEmployNo != null ? !createEmployNo.equals(that.createEmployNo) : that.createEmployNo != null)
            return false;
        if (lastModifyEmployNo != null ? !lastModifyEmployNo.equals(that.lastModifyEmployNo) : that.lastModifyEmployNo != null)
            return false;
        if (logic != null ? !logic.equals(that.logic) : that.logic != null) return false;
        if (createTimestampEnd != null ? !createTimestampEnd.equals(that.createTimestampEnd) : that.createTimestampEnd != null)
            return false;
        if (lastModifyTimestampEnd != null ? !lastModifyTimestampEnd.equals(that.lastModifyTimestampEnd) : that.lastModifyTimestampEnd != null)
            return false;
        if (parentSeparationId != null ? !parentSeparationId.equals(that.parentSeparationId) : that.parentSeparationId != null)
            return false;
        if (parentIntegrationIds != null ? !parentIntegrationIds.equals(that.parentIntegrationIds) : that.parentIntegrationIds != null)
            return false;
        if (state != null ? !state.equals(that.state) : that.state != null) return false;
        if (customerPPO != null ? !customerPPO.equals(that.customerPPO) : that.customerPPO != null) return false;
        if (customerNumber != null ? !customerNumber.equals(that.customerNumber) : that.customerNumber != null)
            return false;
        if (customerLotNumber != null ? !customerLotNumber.equals(that.customerLotNumber) : that.customerLotNumber != null)
            return false;
        if (internalProductDTO != null ? !internalProductDTO.equals(that.internalProductDTO) : that.internalProductDTO != null)
            return false;
        if (maskName != null ? !maskName.equals(that.maskName) : that.maskName != null) return false;
        if (size != null ? !size.equals(that.size) : that.size != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + version;
        result = 31 * result + (createTimestamp != null ? createTimestamp.hashCode() : 0);
        result = 31 * result + (lastModifyTimestamp != null ? lastModifyTimestamp.hashCode() : 0);
        result = 31 * result + (createEmployNo != null ? createEmployNo.hashCode() : 0);
        result = 31 * result + (lastModifyEmployNo != null ? lastModifyEmployNo.hashCode() : 0);
        result = 31 * result + (logic != null ? logic.hashCode() : 0);
        result = 31 * result + (createTimestampEnd != null ? createTimestampEnd.hashCode() : 0);
        result = 31 * result + (lastModifyTimestampEnd != null ? lastModifyTimestampEnd.hashCode() : 0);
        result = 31 * result + (parentSeparationId != null ? parentSeparationId.hashCode() : 0);
        result = 31 * result + (parentIntegrationIds != null ? parentIntegrationIds.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (customerPPO != null ? customerPPO.hashCode() : 0);
        result = 31 * result + (customerNumber != null ? customerNumber.hashCode() : 0);
        result = 31 * result + (customerLotNumber != null ? customerLotNumber.hashCode() : 0);
        result = 31 * result + (internalProductDTO != null ? internalProductDTO.hashCode() : 0);
        result = 31 * result + (maskName != null ? maskName.hashCode() : 0);
        result = 31 * result + (size != null ? size.hashCode() : 0);
        return result;
    }

    public String getPackingLot() {
        return packingLot;
    }

    public void setPackingLot(String packingLot) {
        this.packingLot = packingLot;
    }

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public String getWaferLot() {
        return waferLot;
    }

    public void setWaferLot(String waferLot) {
        this.waferLot = waferLot;
    }
}