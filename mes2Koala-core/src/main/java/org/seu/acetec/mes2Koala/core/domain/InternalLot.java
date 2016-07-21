package org.seu.acetec.mes2Koala.core.domain;

import javax.persistence.*;

import java.util.List;

/**
 * 内部批次，分别与FTLot与CPLot建立一对一关系，与Bom、Label、TestProgram、Process等产品信息建立关系
 *
 * @author Howard
 * @version v1.0
 * @lastModifyDate 2016.01.05
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)    //使用joined继承类型
@Table(name = "E_INTERNAL_LOT")
@Access(AccessType.PROPERTY)
public class InternalLot extends MES2AbstractEntity {

    public static final String HOLD_STATE_UNHOLD = "";
    public static final String HOLD_STATE_HOLD = "Hold";
    public static final String HOLD_STATE_FUTURE_HOLD = "Future Hold";

    private static final long serialVersionUID = 1962007795683648166L;
    private String internalLotNumber;
    private String shipmentProductNumber;
    private String rcNumber;    //RC Number

    // Hold状态
    private String holdState = HOLD_STATE_UNHOLD;

    // Process状态，待Baking等
    private String currentState;

//	private InternalProduct internalProduct;
//	private LabelUse labelUse;

    //用于记录合批中的母批
    private String parentIntegrationIds;

    //用于与分批中的母批产生关联
    private Long parentSeparationId;
    
    //原始母批id
    private Long sourceParentSeparationId;
    
    //原始母批no
    private String sourceParentSeparationNo;

    private String wmsTestId;
    
	private Boolean isFuture;//是否是预hold
	
	private String futureFlow;//预hold
    
    public String getWmsTestId() {
		return wmsTestId;
	}

	public void setWmsTestId(String wmsTestId) {
		this.wmsTestId = wmsTestId;
	}

	public String getRcNumber() {
        return rcNumber;
    }

    public void setRcNumber(String rcNumber) {
        this.rcNumber = rcNumber;
    }

    public String getInternalLotNumber() {
        return internalLotNumber;
    }

    public void setInternalLotNumber(String internalLotNumber) {
        this.internalLotNumber = internalLotNumber;
    }

    public String getShipmentProductNumber() {
        return shipmentProductNumber;
    }

    public void setShipmentProductNumber(String shipmentProductNumber) {
        this.shipmentProductNumber = shipmentProductNumber;
    }

    public String getHoldState() {
        return holdState;
    }

    public void setHoldState(String holdState) {
        this.holdState = holdState;
    }

    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
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


    public Boolean getIsFuture() {
		return isFuture;
	}

	public void setIsFuture(Boolean isFuture) {
		this.isFuture = isFuture;
	}

	public String getFutureFlow() {
		return futureFlow;
	}

	public void setFutureFlow(String futureFlow) {
		this.futureFlow = futureFlow;
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

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		InternalLot other = (InternalLot) obj;
		if (currentState == null) {
			if (other.currentState != null)
				return false;
		} else if (!currentState.equals(other.currentState))
			return false;
		if (holdState == null) {
			if (other.holdState != null)
				return false;
		} else if (!holdState.equals(other.holdState))
			return false;
		if (internalLotNumber == null) {
			if (other.internalLotNumber != null)
				return false;
		} else if (!internalLotNumber.equals(other.internalLotNumber))
			return false;
		if (parentIntegrationIds == null) {
			if (other.parentIntegrationIds != null)
				return false;
		} else if (!parentIntegrationIds.equals(other.parentIntegrationIds))
			return false;
		if (parentSeparationId == null) {
			if (other.parentSeparationId != null)
				return false;
		} else if (!parentSeparationId.equals(other.parentSeparationId))
			return false;
		if (rcNumber == null) {
			if (other.rcNumber != null)
				return false;
		} else if (!rcNumber.equals(other.rcNumber))
			return false;
		if (shipmentProductNumber == null) {
			if (other.shipmentProductNumber != null)
				return false;
		} else if (!shipmentProductNumber.equals(other.shipmentProductNumber))
			return false;
		return true;
	}

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((currentState == null) ? 0 : currentState.hashCode());
		result = prime * result + ((holdState == null) ? 0 : holdState.hashCode());
		result = prime * result + ((internalLotNumber == null) ? 0 : internalLotNumber.hashCode());
		result = prime * result + ((parentIntegrationIds == null) ? 0 : parentIntegrationIds.hashCode());
		result = prime * result + ((parentSeparationId == null) ? 0 : parentSeparationId.hashCode());
		result = prime * result + ((rcNumber == null) ? 0 : rcNumber.hashCode());
		result = prime * result + ((shipmentProductNumber == null) ? 0 : shipmentProductNumber.hashCode());
		return result;
	}

    @Override
    public String[] businessKeys() {
        return new String[0];
    }

}
