package org.seu.acetec.mes2Koala.facade.dto.vo;

import java.io.Serializable;
import java.util.Date;

public class FTLotPageVo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -955437920035835767L;
	
	private Long id;	//用于标识实体
	private String state;	//过站状态（暂不包括hold状态)
	private String internalPPO;	//内部PPO暂时不知道从哪里来
	private String customerPPO;	//客户PPO（外ppo）
	private String packageNumber;	//封装批号
	private String productVersion;	//版本型号
	private String taxType;	//保税类型
	private String lotNumber;	//艾克批号
	private String quantity;	//数量
	private String customerLotNumber;	//客户批号
	private String customerNumber;	//客户编号
	private String shipmentProductNumber;	//出货型号
	private String customerProductNumber;	//来料型号
	private String waferLotNumber;	//wafer lot number
	private String incomingDate;	//进料日期
	//用于查询
	private String packageType;	//封装类型
	private String lotState;	//完结、重工、hold等
	private Date createTimestamp;
	private Date createTimestampEnd;
	
	private String internalProductNumber;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPackageType() {
		return packageType;
	}
	public void setPackageType(String packageType) {
		this.packageType = packageType;
	}
	public String getLotState() {
		return lotState;
	}
	public void setLotState(String lotState) {
		this.lotState = lotState;
	}
	public Date getCreateTimestamp() {
		return createTimestamp;
	}
	public void setCreateTimestamp(Date createTimestamp) {
		this.createTimestamp = createTimestamp;
	}
	public Date getCreateTimestampEnd() {
		return createTimestampEnd;
	}
	public void setCreateTimestampEnd(Date createTimestampEnd) {
		this.createTimestampEnd = createTimestampEnd;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getInternalPPO() {
		return internalPPO;
	}
	public void setInternalPPO(String internalPPO) {
		this.internalPPO = internalPPO;
	}
	public String getCustomerPPO() {
		return customerPPO;
	}
	public void setCustomerPPO(String customerPPO) {
		this.customerPPO = customerPPO;
	}
	public String getPackageNumber() {
		return packageNumber;
	}
	public void setPackageNumber(String packageNumber) {
		this.packageNumber = packageNumber;
	}
	public String getProductVersion() {
		return productVersion;
	}
	public void setProductVersion(String productVersion) {
		this.productVersion = productVersion;
	}
	public String getTaxType() {
		return taxType;
	}
	public void setTaxType(String taxType) {
		this.taxType = taxType;
	}
	public String getLotNumber() {
		return lotNumber;
	}
	public void setLotNumber(String lotNumber) {
		this.lotNumber = lotNumber;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
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
	public String getShipmentProductNumber() {
		return shipmentProductNumber;
	}
	public void setShipmentProductNumber(String shipmentProductNumber) {
		this.shipmentProductNumber = shipmentProductNumber;
	}
	public String getCustomerProductNumber() {
		return customerProductNumber;
	}
	public void setCustomerProductNumber(String customerProductNumber) {
		this.customerProductNumber = customerProductNumber;
	}
	public String getWaferLotNumber() {
		return waferLotNumber;
	}
	public void setWaferLotNumber(String waferLotNumber) {
		this.waferLotNumber = waferLotNumber;
	}
	public String getIncomingDate() {
		return incomingDate;
	}
	public void setIncomingDate(String incomingDate) {
		this.incomingDate = incomingDate;
	}
	public String getInternalProductNumber() {
		return internalProductNumber;
	}
	public void setInternalProductNumber(String internalProductNumber) {
		this.internalProductNumber = internalProductNumber;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((customerLotNumber == null) ? 0 : customerLotNumber.hashCode());
		result = prime * result + ((customerNumber == null) ? 0 : customerNumber.hashCode());
		result = prime * result + ((customerPPO == null) ? 0 : customerPPO.hashCode());
		result = prime * result + ((customerProductNumber == null) ? 0 : customerProductNumber.hashCode());
		result = prime * result + ((incomingDate == null) ? 0 : incomingDate.hashCode());
		result = prime * result + ((internalPPO == null) ? 0 : internalPPO.hashCode());
		result = prime * result + ((lotNumber == null) ? 0 : lotNumber.hashCode());
		result = prime * result + ((packageNumber == null) ? 0 : packageNumber.hashCode());
		result = prime * result + ((productVersion == null) ? 0 : productVersion.hashCode());
		result = prime * result + ((quantity == null) ? 0 : quantity.hashCode());
		result = prime * result + ((shipmentProductNumber == null) ? 0 : shipmentProductNumber.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((taxType == null) ? 0 : taxType.hashCode());
		result = prime * result + ((waferLotNumber == null) ? 0 : waferLotNumber.hashCode());
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
		FTLotPageVo other = (FTLotPageVo) obj;
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
		if (incomingDate == null) {
			if (other.incomingDate != null)
				return false;
		} else if (!incomingDate.equals(other.incomingDate))
			return false;
		if (internalPPO == null) {
			if (other.internalPPO != null)
				return false;
		} else if (!internalPPO.equals(other.internalPPO))
			return false;
		if (lotNumber == null) {
			if (other.lotNumber != null)
				return false;
		} else if (!lotNumber.equals(other.lotNumber))
			return false;
		if (packageNumber == null) {
			if (other.packageNumber != null)
				return false;
		} else if (!packageNumber.equals(other.packageNumber))
			return false;
		if (productVersion == null) {
			if (other.productVersion != null)
				return false;
		} else if (!productVersion.equals(other.productVersion))
			return false;
		if (quantity == null) {
			if (other.quantity != null)
				return false;
		} else if (!quantity.equals(other.quantity))
			return false;
		if (shipmentProductNumber == null) {
			if (other.shipmentProductNumber != null)
				return false;
		} else if (!shipmentProductNumber.equals(other.shipmentProductNumber))
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
		if (waferLotNumber == null) {
			if (other.waferLotNumber != null)
				return false;
		} else if (!waferLotNumber.equals(other.waferLotNumber))
			return false;
		return true;
	}

}
