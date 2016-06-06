package org.seu.acetec.mes2Koala.facade.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.seu.acetec.mes2Koala.core.domain.ProcessTemplate;

public class InternalProductDTO implements Serializable {

    private Long id;

    private int version;

    private Date createTimestamp;

    private Date lastModifyTimestamp;

    private String createEmployNo;

    private String lastModifyEmployNo;

    private Integer logic;

    private String customerProductNumber;

    private String internalProductNumber;

    private String customerProductRevision;

    private String shipmentProductNumber;

    private String packageType;

    private String internalProductRevision;
    
    private String testType;

    private CustomerDTO customerDirectDTO;

    private CustomerDTO customerIndirectDTO;
    
    private ProcessTemplateDTO processTemplateDTO;
    
    private Long processId;
    
	public String getTestType() {
		return testType;
	}

	public void setTestType(String testType) {
		this.testType = testType;
	}

	public Date getCreateTimestamp() {
        return createTimestamp;
    }

    public void setCreateTimestamp(Date createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

    public Date getLastModifyTimestamp() {
        return lastModifyTimestamp;
    }

    public void setLastModifyTimestamp(Date lastModifyTimestamp) {
        this.lastModifyTimestamp = lastModifyTimestamp;
    }

    public String getCreateEmployNo() {
        return createEmployNo;
    }

    public void setCreateEmployNo(String createEmployNo) {
        this.createEmployNo = createEmployNo;
    }

    public String getLastModifyEmployNo() {
        return lastModifyEmployNo;
    }

    public void setLastModifyEmployNo(String lastModifyEmployNo) {
        this.lastModifyEmployNo = lastModifyEmployNo;
    }

    public Integer getLogic() {
        return logic;
    }

    public void setLogic(Integer logic) {
        this.logic = logic;
    }


    public void setCustomerProductNumber(String customerProductNumber) {
        this.customerProductNumber = customerProductNumber;
    }

    public String getCustomerProductNumber() {
        return this.customerProductNumber;
    }


    public void setInternalProductNumber(String internalProductNumber) {
        this.internalProductNumber = internalProductNumber;
    }

    public String getInternalProductNumber() {
        return this.internalProductNumber;
    }


    public void setCustomerProductRevision(String customerProductRevision) {
        this.customerProductRevision = customerProductRevision;
    }

    public String getCustomerProductRevision() {
        return this.customerProductRevision;
    }


    public void setShipmentProductNumber(String shipmentProductNumber) {
        this.shipmentProductNumber = shipmentProductNumber;
    }

    public String getShipmentProductNumber() {
        return this.shipmentProductNumber;
    }


    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }

    public String getPackageType() {
        return this.packageType;
    }


    public void setInternalProductRevision(String internalProductRevision) {
        this.internalProductRevision = internalProductRevision;
    }

    public String getInternalProductRevision() {
        return this.internalProductRevision;
    }

    public CustomerDTO getCustomerDirectDTO() {
        return customerDirectDTO;
    }

    public void setCustomerDirectDTO(CustomerDTO customerDirectDTO) {
        this.customerDirectDTO = customerDirectDTO;
    }

    public CustomerDTO getCustomerIndirectDTO() {
        return customerIndirectDTO;
    }

    public void setCustomerIndirectDTO(CustomerDTO getCustomerIndirectDTO) {
        this.customerIndirectDTO = getCustomerIndirectDTO;
    }

	public ProcessTemplateDTO getProcessTemplateDTO() {
		return processTemplateDTO;
	}

	public void setProcessTemplateDTO(ProcessTemplateDTO processTemplateDTO) {
		this.processTemplateDTO = processTemplateDTO;
	}

	public Long getProcessId() {
		return processId;
	}

	public void setProcessId(Long processId) {
		this.processId = processId;
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
        InternalProductDTO other = (InternalProductDTO) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}