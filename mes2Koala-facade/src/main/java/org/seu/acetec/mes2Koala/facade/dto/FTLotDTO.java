package org.seu.acetec.mes2Koala.facade.dto;

import java.io.Serializable;
import java.util.Date;

public class FTLotDTO implements Serializable {

    /**
     * {@link org.seu.acetec.mes2Koala.core.domain.MES2AbstractEntity}
     */
    private Long id;
    private int version;
    private Date lastModifyTimestamp;
    private Date lastModifyTimestampEnd;
    private String lastModifyEmployNo;
    private String createEmployNo;
    private Date createTimestamp;
    private Date createTimestampEnd;
    private Integer logic;

    /**
     * {@link org.seu.acetec.mes2Koala.core.domain.InternalLot}
     */
    private String internalLotNumber;
    private String shipmentProductNumber;
    private String rcNumber; // RC Number
    private String holdState;
    private String currentState;// 待Baking等
    private String parentIntegrationIds; // 用于记录合批中的母批
    private Long parentSeparationId; // 用于与分批中的母批产生关联
    private String wmsTestId; //用于保存WMS领料ID
    /**
     * {@link org.seu.acetec.mes2Koala.core.domain.FTLot}
     */
    private Integer borrow;
    private Integer loss;
    private Long Qty;
    private String type;

    /**
     * Extra
     * 创建FTLot时使用回传
     */
    private String splitQty;
    private String mergeIds;
    private String materialType; // 用于下单时的批号生成
    private Boolean checkLotNo;
    private CustomerFTLotDTO customerLotDTO;
    
    private Long ftInfoId;

    public Integer getLoss() {
        return this.loss;
    }

    public void setLoss(Integer loss) {
        this.loss = loss;
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

    public String getLastModifyEmployNo() {
        return this.lastModifyEmployNo;
    }

    public void setLastModifyEmployNo(String lastModifyEmployNo) {
        this.lastModifyEmployNo = lastModifyEmployNo;
    }

    public Long getQty() {
        return this.Qty;
    }

    public void setQty(Long Qty) {
        this.Qty = Qty;
    }

    public Integer getBorrow() {
        return this.borrow;
    }

    public void setBorrow(Integer borrow) {
        this.borrow = borrow;
    }

    public String getCreateEmployNo() {
        return this.createEmployNo;
    }

    public void setCreateEmployNo(String createEmployNo) {
        this.createEmployNo = createEmployNo;
    }

    public Integer getLogic() {
        return this.logic;
    }

    public void setLogic(Integer logic) {
        this.logic = logic;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getSplitQty() {
        return splitQty;
    }

    public void setSplitQty(String splitQty) {
        this.splitQty = splitQty;
    }

    public String getMergeIds() {
        return mergeIds;
    }

    public void setMergeIds(String mergeIds) {
        this.mergeIds = mergeIds;
    }

    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }

    public String getHoldState() {
        return holdState;
    }

    public void setHoldState(String holdState) {
        this.holdState = holdState;
    }

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
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

    public String getRcNumber() {
        return rcNumber;
    }

    public void setRcNumber(String rcNumber) {
        this.rcNumber = rcNumber;
    }

    public String getShipmentProductNumber() {
        return shipmentProductNumber;
    }

    public void setShipmentProductNumber(String shipmentProductNumber) {
        this.shipmentProductNumber = shipmentProductNumber;
    }

    public String getInternalLotNumber() {
        return internalLotNumber;
    }

    public void setInternalLotNumber(String internalLotNumber) {
        this.internalLotNumber = internalLotNumber;
    }

    public Boolean getCheckLotNo() {
        return checkLotNo;
    }

    public void setCheckLotNo(Boolean checkLotNo) {
        this.checkLotNo = checkLotNo;
    }

    public CustomerFTLotDTO getCustomerLotDTO() {
        return customerLotDTO;
    }

    public void setCustomerLotDTO(CustomerFTLotDTO customerLotDTO) {
        this.customerLotDTO = customerLotDTO;
    }

    public String getWmsTestId() {
		return wmsTestId;
	}

	public void setWmsTestId(String wmsTestId) {
		this.wmsTestId = wmsTestId;
	}

	public Long getFtInfoId() {
		return ftInfoId;
	}

	public void setFtInfoId(Long ftInfoId) {
		this.ftInfoId = ftInfoId;
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
        FTLotDTO other = (FTLotDTO) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}