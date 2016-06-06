
package org.seu.acetec.mes2Koala.facade.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TestProgramTemplateDTO implements Serializable {

    private Long id;

    private int version;

    private String productVersion;

    private String site;

    private String revision;

    private Integer UPHTheory;

    private String testSys;

    private Boolean isYellow;

    private Integer UPHReality;

    private String name;

    private String testType;

    private String note;

    private String allowState;
    
    private List<AcetecAuthorizationDTO> acetecAuthorizationDTOs = new ArrayList<AcetecAuthorizationDTO>();

    private List<Long> acetecAuthorizationIds = new ArrayList<Long>();

    private InternalProductDTO internalProductDTO;
    
    private String customerDirectNumber;
    

    public String getCustomerDirectNumber() {
		return customerDirectNumber;
	}

	public void setCustomerDirectNumber(String customerDirectNumber) {
		this.customerDirectNumber = customerDirectNumber;
	}

	public String getTestType() {
        return testType;
    }

    public void setTestType(String testType) {
        this.testType = testType;
    }

    public String getProductVersion() {
        return productVersion;
    }

    public void setProductVersion(String productVersion) {
        this.productVersion = productVersion;
    }

    public String getSite() {
        return this.site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getRevision() {
        return this.revision;
    }

    public void setRevision(String revision) {
        this.revision = revision;
    }

    public Integer getUPHTheory() {
        return this.UPHTheory;
    }

    public void setUPHTheory(Integer UPHTheory) {
        this.UPHTheory = UPHTheory;
    }

    public String getTestSys() {
        return this.testSys;
    }

    public void setTestSys(String testSys) {
        this.testSys = testSys;
    }

    public Boolean getIsYellow() {
        return this.isYellow;
    }

    public void setIsYellow(Boolean isYellow) {
        this.isYellow = isYellow;
    }

    public Integer getUPHReality() {
        return this.UPHReality;
    }

    public void setUPHReality(Integer UPHReality) {
        this.UPHReality = UPHReality;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return this.note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getAllowState() {
		return allowState;
	}

	public void setAllowState(String allowState) {
		this.allowState = allowState;
	}

	public List<AcetecAuthorizationDTO> getAcetecAuthorizationDTOs() {
        return acetecAuthorizationDTOs;
    }

    public void setAcetecAuthorizationDTOs(List<AcetecAuthorizationDTO> acetecAuthorizationDTOs) {
        this.acetecAuthorizationDTOs = acetecAuthorizationDTOs;
    }


    public List<Long> getAcetecAuthorizationIds() {
        return acetecAuthorizationIds;
    }

    public void setAcetecAuthorizationIds(List<Long> acetecAuthorizationIds) {
        this.acetecAuthorizationIds = acetecAuthorizationIds;
    }

    public InternalProductDTO getInternalProductDTO() {
        return internalProductDTO;
    }

    public void setInternalProductDTO(InternalProductDTO internalProductDTO) {
        this.internalProductDTO = internalProductDTO;
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
        result = prime * result + ((UPHReality == null) ? 0 : UPHReality.hashCode());
        result = prime * result + ((UPHTheory == null) ? 0 : UPHTheory.hashCode());
        result = prime * result + ((acetecAuthorizationDTOs == null) ? 0 : acetecAuthorizationDTOs.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((isYellow == null) ? 0 : isYellow.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((note == null) ? 0 : note.hashCode());
        result = prime * result + ((revision == null) ? 0 : revision.hashCode());
        result = prime * result + ((site == null) ? 0 : site.hashCode());
        result = prime * result + ((testSys == null) ? 0 : testSys.hashCode());
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
        TestProgramTemplateDTO other = (TestProgramTemplateDTO) obj;
        if (UPHReality == null) {
            if (other.UPHReality != null)
                return false;
        } else if (!UPHReality.equals(other.UPHReality))
            return false;
        if (UPHTheory == null) {
            if (other.UPHTheory != null)
                return false;
        } else if (!UPHTheory.equals(other.UPHTheory))
            return false;
        if (acetecAuthorizationDTOs == null) {
            if (other.acetecAuthorizationDTOs != null)
                return false;
        } else if (!acetecAuthorizationDTOs.equals(other.acetecAuthorizationDTOs))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (isYellow == null) {
            if (other.isYellow != null)
                return false;
        } else if (!isYellow.equals(other.isYellow))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (note == null) {
            if (other.note != null)
                return false;
        } else if (!note.equals(other.note))
            return false;
        if (revision == null) {
            if (other.revision != null)
                return false;
        } else if (!revision.equals(other.revision))
            return false;
        if (site == null) {
            if (other.site != null)
                return false;
        } else if (!site.equals(other.site))
            return false;
        if (testSys == null) {
            if (other.testSys != null)
                return false;
        } else if (!testSys.equals(other.testSys))
            return false;
        if (version != other.version)
            return false;
        return true;
    }

	@Override
	public String toString() {
		return "测试程序 [版本型号:" + productVersion + 
				", 站点:" + site + 
				", 测试程序名称:" + name +
				", 测试程序版本:" + revision + "]";
	}
}