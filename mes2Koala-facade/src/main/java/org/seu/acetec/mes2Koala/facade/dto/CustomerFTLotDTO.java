package org.seu.acetec.mes2Koala.facade.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class CustomerFTLotDTO implements Serializable {

    /**
     * {@link org.seu.acetec.mes2Koala.core.domain.MES2AbstractEntity}
     */
    private Long id;
    private int version;

    /**
     * {@link org.seu.acetec.mes2Koala.core.domain.CustomerLot}
     */
    private Long parentSeparationId;
    // private String parentIntegrationIds;
    private Integer state;
    private String customerPPO;
    private String customerLotNumber;
    private String customerNumber;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date incomingDate;
    private String shipmentProductionNumber;

    /**
     * {@link org.seu.acetec.mes2Koala.core.domain.CustomerFTLot}
     */
    private String packageNumber;
    private String productVersion;
    private String customerProductNumber;  // internalProductDTO.customerProductNumber
    private Long incomingQuantity;
    private String incomingStyle;
    private String dateCode;
    private String materialType;
    private String taxType;
    private String wireBond;
    private String waferLot;
    private String MFGPN;
    private String waferManufacturer;

    /**
     * 从InternalProduct中提出除的ProcessTemplate
     * {@link org.seu.acetec.mes2Koala.facade.impl.assembler.CustomerFTLotAssembler#toDTO(org.seu.acetec.mes2Koala.core.domain.CustomerCPLot)}
     */
    private String processTemplateName;
    private String processTemplateContent;
    
    /**
     * 已下单的批次会绑定FTLot实体，从中获取艾科批号以及出货型号
     * {@link org.seu.acetec.mes2Koala.facade.impl.assembler.CustomerFTLotAssembler.toDTO(CustomerFTLot)}
     */
    private String lotNumber;

    //用于和对方系统的id绑定
    private String wms_id;

    //封装形式
    private String packageType;
    
    public String getShipmentProductionNumber() {
		return shipmentProductionNumber;
	}

	public void setShipmentProductionNumber(String shipmentProductionNumber) {
		this.shipmentProductionNumber = shipmentProductionNumber;
	}

	public Date getIncomingDate() {
		return incomingDate;
	}

	public void setIncomingDate(Date incomingDate) {
		this.incomingDate = incomingDate;
	}

	public String getIncomingStyle() {
		return incomingStyle;
	}

	public void setIncomingStyle(String incomingStyle) {
		this.incomingStyle = incomingStyle;
	}

	public String getLotNumber() {
		return lotNumber;
	}

	public void setLotNumber(String lotNumber) {
		this.lotNumber = lotNumber;
	}

	public String getWms_id() {
        return wms_id;
    }

    public void setWms_id(String wms_id) {
        this.wms_id = wms_id;
    }

    public String getProcessTemplateName() {
        return processTemplateName;
    }

    public void setProcessTemplateName(String processTemplateName) {
        this.processTemplateName = processTemplateName;
    }

    public String getProcessTemplateContent() {
        return processTemplateContent;
    }

    public void setProcessTemplateContent(String processTemplateContent) {
        this.processTemplateContent = processTemplateContent;
    }

    public String getMaterialType() {
        return this.materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public String getDeliveryType() {
        return this.incomingStyle;
    }

    public void setDeliveryType(String incomingStyle) {
        this.incomingStyle = incomingStyle;
    }

    public Long getIncomingQuantity() {
        return this.incomingQuantity;
    }

    public void setIncomingQuantity(Long incomingQuantity) {
        this.incomingQuantity = incomingQuantity;
    }

    public String getDateCode() {
        return this.dateCode;
    }

    public void setDateCode(String dateCode) {
        this.dateCode = dateCode;
    }


    public String getCustomerProductNumber() {
        return this.customerProductNumber;
    }

    public void setCustomerProductNumber(String customerProductNumber) {
        this.customerProductNumber = customerProductNumber;
    }

    public String getPackageNumber() {
        return this.packageNumber;
    }

    public void setPackageNumber(String packageNumber) {
        this.packageNumber = packageNumber;
    }

    public String getWaferManufacturer() {
        return this.waferManufacturer;
    }

    public void setWaferManufacturer(String waferManufacturer) {
        this.waferManufacturer = waferManufacturer;
    }

    public String getMFGPN() {
        return this.MFGPN;
    }

    public void setMFGPN(String MFGPN) {
        this.MFGPN = MFGPN;
    }

    public String getWaferLot() {
        return this.waferLot;
    }

    public void setWaferLot(String waferLot) {
        this.waferLot = waferLot;
    }

    public String getProductVersion() {
        return this.productVersion;
    }

    public void setProductVersion(String productVersion) {
        this.productVersion = productVersion;
    }

    public String getWireBond() {
        return this.wireBond;
    }

    public void setWireBond(String wireBond) {
        this.wireBond = wireBond;
    }

    public String getTaxType() {
        return this.taxType;
    }

    public void setTaxType(String taxType) {
        this.taxType = taxType;
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

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Long getParentSeparationId() {
        return parentSeparationId;
    }

    public void setParentSeparationId(Long parentSeparationId) {
        this.parentSeparationId = parentSeparationId;
    }

    public String getCustomerPPO() {
        return customerPPO;
    }

    public void setCustomerPPO(String customerPPO) {
        this.customerPPO = customerPPO;
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


    public String getPackageType() {
		return packageType;
	}

	public void setPackageType(String packageType) {
		this.packageType = packageType;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomerFTLotDTO other = (CustomerFTLotDTO) obj;
		if (MFGPN == null) {
			if (other.MFGPN != null)
				return false;
		} else if (!MFGPN.equals(other.MFGPN))
			return false;
		if (customerLotNumber == null) {
			if (other.customerLotNumber != null)
				return false;
		} else if (!customerLotNumber.equals(other.customerLotNumber))
			return false;
		if (customerNumber == null) {
			if (other.customerNumber != null)
				return false;
		} else if (!customerNumber.equals(other.customerNumber))
			return false;
		if (customerPPO == null) {
			if (other.customerPPO != null)
				return false;
		} else if (!customerPPO.equals(other.customerPPO))
			return false;
		if (customerProductNumber == null) {
			if (other.customerProductNumber != null)
				return false;
		} else if (!customerProductNumber.equals(other.customerProductNumber))
			return false;
		if (dateCode == null) {
			if (other.dateCode != null)
				return false;
		} else if (!dateCode.equals(other.dateCode))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (incomingDate == null) {
			if (other.incomingDate != null)
				return false;
		} else if (!incomingDate.equals(other.incomingDate))
			return false;
		if (incomingQuantity == null) {
			if (other.incomingQuantity != null)
				return false;
		} else if (!incomingQuantity.equals(other.incomingQuantity))
			return false;
		if (incomingStyle == null) {
			if (other.incomingStyle != null)
				return false;
		} else if (!incomingStyle.equals(other.incomingStyle))
			return false;
		if (lotNumber == null) {
			if (other.lotNumber != null)
				return false;
		} else if (!lotNumber.equals(other.lotNumber))
			return false;
		if (materialType == null) {
			if (other.materialType != null)
				return false;
		} else if (!materialType.equals(other.materialType))
			return false;
		if (packageNumber == null) {
			if (other.packageNumber != null)
				return false;
		} else if (!packageNumber.equals(other.packageNumber))
			return false;
		if (parentSeparationId == null) {
			if (other.parentSeparationId != null)
				return false;
		} else if (!parentSeparationId.equals(other.parentSeparationId))
			return false;
		if (processTemplateContent == null) {
			if (other.processTemplateContent != null)
				return false;
		} else if (!processTemplateContent.equals(other.processTemplateContent))
			return false;
		if (processTemplateName == null) {
			if (other.processTemplateName != null)
				return false;
		} else if (!processTemplateName.equals(other.processTemplateName))
			return false;
		if (productVersion == null) {
			if (other.productVersion != null)
				return false;
		} else if (!productVersion.equals(other.productVersion))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (taxType == null) {
			if (other.taxType != null)
				return false;
		} else if (!taxType.equals(other.taxType))
			return false;
		if (version != other.version)
			return false;
		if (waferLot == null) {
			if (other.waferLot != null)
				return false;
		} else if (!waferLot.equals(other.waferLot))
			return false;
		if (waferManufacturer == null) {
			if (other.waferManufacturer != null)
				return false;
		} else if (!waferManufacturer.equals(other.waferManufacturer))
			return false;
		if (wireBond == null) {
			if (other.wireBond != null)
				return false;
		} else if (!wireBond.equals(other.wireBond))
			return false;
		if (wms_id == null) {
			if (other.wms_id != null)
				return false;
		} else if (!wms_id.equals(other.wms_id))
			return false;
		return true;
	}

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((MFGPN == null) ? 0 : MFGPN.hashCode());
		result = prime * result + ((customerLotNumber == null) ? 0 : customerLotNumber.hashCode());
		result = prime * result + ((customerNumber == null) ? 0 : customerNumber.hashCode());
		result = prime * result + ((customerPPO == null) ? 0 : customerPPO.hashCode());
		result = prime * result + ((customerProductNumber == null) ? 0 : customerProductNumber.hashCode());
		result = prime * result + ((dateCode == null) ? 0 : dateCode.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((incomingDate == null) ? 0 : incomingDate.hashCode());
		result = prime * result + ((incomingQuantity == null) ? 0 : incomingQuantity.hashCode());
		result = prime * result + ((incomingStyle == null) ? 0 : incomingStyle.hashCode());
		result = prime * result + ((lotNumber == null) ? 0 : lotNumber.hashCode());
		result = prime * result + ((materialType == null) ? 0 : materialType.hashCode());
		result = prime * result + ((packageNumber == null) ? 0 : packageNumber.hashCode());
		result = prime * result + ((parentSeparationId == null) ? 0 : parentSeparationId.hashCode());
		result = prime * result + ((processTemplateContent == null) ? 0 : processTemplateContent.hashCode());
		result = prime * result + ((processTemplateName == null) ? 0 : processTemplateName.hashCode());
		result = prime * result + ((productVersion == null) ? 0 : productVersion.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((taxType == null) ? 0 : taxType.hashCode());
		result = prime * result + version;
		result = prime * result + ((waferLot == null) ? 0 : waferLot.hashCode());
		result = prime * result + ((waferManufacturer == null) ? 0 : waferManufacturer.hashCode());
		result = prime * result + ((wireBond == null) ? 0 : wireBond.hashCode());
		result = prime * result + ((wms_id == null) ? 0 : wms_id.hashCode());
		return result;
	}
}