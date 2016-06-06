package org.seu.acetec.mes2Koala.facade.dto;


import java.io.Serializable;
import java.util.Date;


public class BomTemplateDTO implements Serializable {

    private Long id;

    private int version;

    private Date createTimestamp;
    
    private Date createTimestampEnd;

    private Date lastModifyTimestamp;

    private Date lastModifyTimestampEnd;

    private String createEmployNo;

    private String lastModifyEmployNo;

    private Integer logic;


//    private String level;
    private String bomId;
    
    private String modelNumber;

    private String number;

    //private String itemSubclass;

    private String revision;

    private String description;

    //private String lifecyclePhase;

    private String customerCode;

    private String um;

    private String quantity;

    private String theoryQuantity;
    
//    private String itemDescription;

    private String manufacturerName;

    //private String manufacturerPartNumber;

    //private String manufacturerPartLifecyclePhase;

    private InternalProductDTO internalProductDTO;
    
    private String testType;

/*    public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}*/

/*	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}*/

	public String getTestType() {
		return testType;
	}

	public void setTestType(String testType) {
		this.testType = testType;
	}

	public String getBomId() {
		return bomId;
	}

	public void setBomId(String bomId) {
		this.bomId = bomId;
	}

	public InternalProductDTO getInternalProductDTO() {
        return internalProductDTO;
    }

    public void setInternalProductDTO(InternalProductDTO internalProductDTO) {
        this.internalProductDTO = internalProductDTO;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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

    public Date getCreateTimestampEnd() {
		return createTimestampEnd;
	}

	public void setCreateTimestampEnd(Date createTimestampEnd) {
		this.createTimestampEnd = createTimestampEnd;
	}

	public Date getLastModifyTimestampEnd() {
		return lastModifyTimestampEnd;
	}

	public void setLastModifyTimestampEnd(Date lastModifyTimestampEnd) {
		this.lastModifyTimestampEnd = lastModifyTimestampEnd;
	}

	public Date getLastModifyTimestamp() {
		return this.lastModifyTimestamp;
	}

    public void setLastModifyTimestamp(Date lastModifyTimestamp) {
        this.lastModifyTimestamp = lastModifyTimestamp;
    }

	public String getLastModifyEmployNo() {
		return this.lastModifyEmployNo;
	}

    public void setLastModifyEmployNo(String lastModifyEmployNo) {
        this.lastModifyEmployNo = lastModifyEmployNo;
    }

	public String getQuantity() {
		return this.quantity;
	}

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

	public String getUm() {
		return this.um;
	}

    public void setUm(String um) {
        this.um = um;
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

	public String getTheoryQuantity() {
		return this.theoryQuantity;
	}

    public void setTheoryQuantity(String theoryQuantity) {
        this.theoryQuantity = theoryQuantity;
    }

	public Date getCreateTimestamp() {
		return this.createTimestamp;
	}

    public void setCreateTimestamp(Date createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

	public String getRevision() {
		return this.revision;
	}

    public void setRevision(String revision) {
        this.revision = revision;
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
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

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bomId == null) ? 0 : bomId.hashCode());
		result = prime * result + ((createEmployNo == null) ? 0 : createEmployNo.hashCode());
		result = prime * result + ((createTimestamp == null) ? 0 : createTimestamp.hashCode());
		result = prime * result + ((createTimestampEnd == null) ? 0 : createTimestampEnd.hashCode());
		result = prime * result + ((customerCode == null) ? 0 : customerCode.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((internalProductDTO == null) ? 0 : internalProductDTO.hashCode());
		result = prime * result + ((lastModifyEmployNo == null) ? 0 : lastModifyEmployNo.hashCode());
		result = prime * result + ((lastModifyTimestamp == null) ? 0 : lastModifyTimestamp.hashCode());
		result = prime * result + ((lastModifyTimestampEnd == null) ? 0 : lastModifyTimestampEnd.hashCode());
		result = prime * result + ((logic == null) ? 0 : logic.hashCode());
		result = prime * result + ((manufacturerName == null) ? 0 : manufacturerName.hashCode());
		result = prime * result + ((modelNumber == null) ? 0 : modelNumber.hashCode());
		result = prime * result + ((number == null) ? 0 : number.hashCode());
		result = prime * result + ((quantity == null) ? 0 : quantity.hashCode());
		result = prime * result + ((revision == null) ? 0 : revision.hashCode());
		result = prime * result + ((testType == null) ? 0 : testType.hashCode());
		result = prime * result + ((theoryQuantity == null) ? 0 : theoryQuantity.hashCode());
		result = prime * result + ((um == null) ? 0 : um.hashCode());
		result = prime * result + version;
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
		BomTemplateDTO other = (BomTemplateDTO) obj;
		if (bomId == null) {
			if (other.bomId != null)
				return false;
		} else if (!bomId.equals(other.bomId))
			return false;
		if (createEmployNo == null) {
			if (other.createEmployNo != null)
				return false;
		} else if (!createEmployNo.equals(other.createEmployNo))
			return false;
		if (createTimestamp == null) {
			if (other.createTimestamp != null)
				return false;
		} else if (!createTimestamp.equals(other.createTimestamp))
			return false;
		if (createTimestampEnd == null) {
			if (other.createTimestampEnd != null)
				return false;
		} else if (!createTimestampEnd.equals(other.createTimestampEnd))
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (internalProductDTO == null) {
			if (other.internalProductDTO != null)
				return false;
		} else if (!internalProductDTO.equals(other.internalProductDTO))
			return false;
		if (lastModifyEmployNo == null) {
			if (other.lastModifyEmployNo != null)
				return false;
		} else if (!lastModifyEmployNo.equals(other.lastModifyEmployNo))
			return false;
		if (lastModifyTimestamp == null) {
			if (other.lastModifyTimestamp != null)
				return false;
		} else if (!lastModifyTimestamp.equals(other.lastModifyTimestamp))
			return false;
		if (lastModifyTimestampEnd == null) {
			if (other.lastModifyTimestampEnd != null)
				return false;
		} else if (!lastModifyTimestampEnd.equals(other.lastModifyTimestampEnd))
			return false;
		if (logic == null) {
			if (other.logic != null)
				return false;
		} else if (!logic.equals(other.logic))
			return false;
		if (manufacturerName == null) {
			if (other.manufacturerName != null)
				return false;
		} else if (!manufacturerName.equals(other.manufacturerName))
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
		if (testType == null) {
			if (other.testType != null)
				return false;
		} else if (!testType.equals(other.testType))
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
		if (version != other.version)
			return false;
		return true;
	}
}