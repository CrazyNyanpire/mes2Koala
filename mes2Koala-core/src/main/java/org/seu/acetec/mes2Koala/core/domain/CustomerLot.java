package org.seu.acetec.mes2Koala.core.domain;

import org.seu.acetec.mes2Koala.core.enums.CustomerLotState;

import javax.persistence.*;

import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)    //使用joined继承类型
@Table(name = "E_CUSTOMER_LOT")
@Access(AccessType.PROPERTY)
public class CustomerLot extends MES2AbstractEntity {

    private static final long serialVersionUID = 71053742844094631L;

    private String parentIntegrationIds;    // 用于记录合批中的母批（较小批次）

    private Long parentSeparationId;        // 用于与分批中的母批产生关联

    private CustomerLotState state;         // 状态标识：0：未下单 1：已下单

    private String productVersion;          // 版本型号 ref:TestProgramm

    private String customerPPO;             // 客户PPO

    private String customerLotNumber;       // 客户批号

    private String customerNumber;          // 可与Customer建立联系

    private String wmsId;                   // 用于和对方系统的id绑定

    private String incomingStyle;           // 到料形式 以“01” （静电装散装）“02”（Tray盘箱装）

    private Date incomingDate;              // 来料日期

    private Long incomingQuantity;          // 来料数量

    private String customerProductNumber;   // 来料型号（应该是客户产品型号？可与InternalProduct建立联系） CustomertProductNumber
    
    private String shipmentNumber;	// 出货型号
    
    private String orderUser;
    
    private Date orderDate;
    
    private String packingLot; //封装批号
    
    private String packageNumber; //封装型号

    public String getShipmentNumber() {
		return shipmentNumber;
	}

	public void setShipmentNumber(String shipmentNumber) {
		this.shipmentNumber = shipmentNumber;
	}

	@Enumerated(EnumType.ORDINAL)
    public CustomerLotState getState() {
        return state;
    }

    public void setState(CustomerLotState state) {
        this.state = state;
    }

    public Long getIncomingQuantity() {
        return incomingQuantity;
    }

    public void setIncomingQuantity(Long incomingQuantity) {
        this.incomingQuantity = incomingQuantity;
    }

    public String getWmsId() {
        return wmsId;
    }

    public void setWmsId(String wmsId) {
        this.wmsId = wmsId;
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

    public String getCustomerProductNumber() {
        return customerProductNumber;
    }

    public void setCustomerProductNumber(String customerProductNumber) {
        this.customerProductNumber = customerProductNumber;
    }

    public String getProductVersion() {
        return productVersion;
    }

    public void setProductVersion(String productVersion) {
        this.productVersion = productVersion;
    }


    public String getOrderUser() {
		return orderUser;
	}

	public void setOrderUser(String orderUser) {
		this.orderUser = orderUser;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getPackingLot() {
		return packingLot;
	}

	public void setPackingLot(String packingLot) {
		this.packingLot = packingLot;
	}

	public String getPackageNumber() {
		return packageNumber;
	}

	public void setPackageNumber(String packageNumber) {
		this.packageNumber = packageNumber;
	}

	@Override
    public String[] businessKeys() {
        return new String[0];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        CustomerLot that = (CustomerLot) o;

        if (parentIntegrationIds != null ? !parentIntegrationIds.equals(that.parentIntegrationIds) : that.parentIntegrationIds != null)
            return false;
        if (parentSeparationId != null ? !parentSeparationId.equals(that.parentSeparationId) : that.parentSeparationId != null)
            return false;
        if (state != that.state) return false;
        if (productVersion != null ? !productVersion.equals(that.productVersion) : that.productVersion != null)
            return false;
        if (customerPPO != null ? !customerPPO.equals(that.customerPPO) : that.customerPPO != null) return false;
        if (customerLotNumber != null ? !customerLotNumber.equals(that.customerLotNumber) : that.customerLotNumber != null)
            return false;
        if (customerNumber != null ? !customerNumber.equals(that.customerNumber) : that.customerNumber != null)
            return false;
        if (wmsId != null ? !wmsId.equals(that.wmsId) : that.wmsId != null) return false;
        if (incomingStyle != null ? !incomingStyle.equals(that.incomingStyle) : that.incomingStyle != null)
            return false;
        if (incomingDate != null ? !incomingDate.equals(that.incomingDate) : that.incomingDate != null) return false;
        if (incomingQuantity != null ? !incomingQuantity.equals(that.incomingQuantity) : that.incomingQuantity != null)
            return false;
        return customerProductNumber != null ? customerProductNumber.equals(that.customerProductNumber) : that.customerProductNumber == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (parentIntegrationIds != null ? parentIntegrationIds.hashCode() : 0);
        result = 31 * result + (parentSeparationId != null ? parentSeparationId.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (productVersion != null ? productVersion.hashCode() : 0);
        result = 31 * result + (customerPPO != null ? customerPPO.hashCode() : 0);
        result = 31 * result + (customerLotNumber != null ? customerLotNumber.hashCode() : 0);
        result = 31 * result + (customerNumber != null ? customerNumber.hashCode() : 0);
        result = 31 * result + (wmsId != null ? wmsId.hashCode() : 0);
        result = 31 * result + (incomingStyle != null ? incomingStyle.hashCode() : 0);
        result = 31 * result + (incomingDate != null ? incomingDate.hashCode() : 0);
        result = 31 * result + (incomingQuantity != null ? incomingQuantity.hashCode() : 0);
        result = 31 * result + (customerProductNumber != null ? customerProductNumber.hashCode() : 0);
        return result;
    }
}
