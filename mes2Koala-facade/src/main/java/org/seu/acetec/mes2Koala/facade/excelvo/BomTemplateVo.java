package org.seu.acetec.mes2Koala.facade.excelvo;

/**
 * @author yuxia
 * @version 2015/12/11
 */
public class BomTemplateVo {
//	private String level;
	private String bomId;

    private String modelNumber;

    private String number;

    //private String itemSubclass;

    private String revision;

    private String description;

    //private String lifecyclePhase;

    private String customerCode;

    private String um;

    private Double quantity;

    private Double theoryQuantity;
    
    //private String itemDescription;

    private String manufacturerName;

    //private String manufacturerPartNumber;

    //private String manufacturerPartLifecyclePhase;
    
    //以下字段用于工单
    private String minRequire;

/*	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}*/

	public String getMinRequire() {
		return minRequire;
	}

	public void setMinRequire(String minRequire) {
		this.minRequire = minRequire;
	}

	public String getBomId() {
		return bomId;
	}

	public void setBomId(String bomId) {
		this.bomId = bomId;
	}

	public String getModelNumber() {
		return modelNumber;
	}

	public void setModelNumber(String modelNumber) {
		this.modelNumber = modelNumber;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getRevision() {
		return revision;
	}

	public void setRevision(String revision) {
		this.revision = revision;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getUm() {
		return um;
	}

	public void setUm(String um) {
		this.um = um;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Double getTheoryQuantity() {
		return theoryQuantity;
	}

	public void setTheoryQuantity(Double theoryQuantity) {
		this.theoryQuantity = theoryQuantity;
	}

/*	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}*/

	public String getManufacturerName() {
		return manufacturerName;
	}

	public void setManufacturerName(String manufacturerName) {
		this.manufacturerName = manufacturerName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bomId == null) ? 0 : bomId.hashCode());
		result = prime * result + ((customerCode == null) ? 0 : customerCode.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((manufacturerName == null) ? 0 : manufacturerName.hashCode());
		result = prime * result + ((minRequire == null) ? 0 : minRequire.hashCode());
		result = prime * result + ((modelNumber == null) ? 0 : modelNumber.hashCode());
		result = prime * result + ((number == null) ? 0 : number.hashCode());
		result = prime * result + ((quantity == null) ? 0 : quantity.hashCode());
		result = prime * result + ((revision == null) ? 0 : revision.hashCode());
		result = prime * result + ((theoryQuantity == null) ? 0 : theoryQuantity.hashCode());
		result = prime * result + ((um == null) ? 0 : um.hashCode());
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
		BomTemplateVo other = (BomTemplateVo) obj;
		if (bomId == null) {
			if (other.bomId != null)
				return false;
		} else if (!bomId.equals(other.bomId))
			return false;
		if (customerCode == null) {
			if (other.customerCode != null)
				return false;
		} else if (!customerCode.equals(other.customerCode))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (manufacturerName == null) {
			if (other.manufacturerName != null)
				return false;
		} else if (!manufacturerName.equals(other.manufacturerName))
			return false;
		if (minRequire == null) {
			if (other.minRequire != null)
				return false;
		} else if (!minRequire.equals(other.minRequire))
			return false;
		if (modelNumber == null) {
			if (other.modelNumber != null)
				return false;
		} else if (!modelNumber.equals(other.modelNumber))
			return false;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		if (quantity == null) {
			if (other.quantity != null)
				return false;
		} else if (!quantity.equals(other.quantity))
			return false;
		if (revision == null) {
			if (other.revision != null)
				return false;
		} else if (!revision.equals(other.revision))
			return false;
		if (theoryQuantity == null) {
			if (other.theoryQuantity != null)
				return false;
		} else if (!theoryQuantity.equals(other.theoryQuantity))
			return false;
		if (um == null) {
			if (other.um != null)
				return false;
		} else if (!um.equals(other.um))
			return false;
		return true;
	}


}
