package org.seu.acetec.mes2Koala.core.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "E_TEST_PROGRAM_TEMPLATE")
@Access(AccessType.PROPERTY)
public class TestProgramTemplate extends MES2AbstractEntity {

    /**
     *
     */
    private static final long serialVersionUID = -7024936859790694729L;

    private String name;
    private String productVersion;
    private String testSys;
    private String site;
    private boolean isYellow;
    private Float UPHReality;
    private Float UPHTheory;
    private String revision;
    private String note;
    private String testType;
    private String allowState;
    private List<AcetecAuthorization> acetecAuthorizations = new ArrayList<AcetecAuthorization>();
    private InternalProduct internalProduct;
    private String temperature;
    private String productName;
    private String testSoftwareVersion;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductVersion() {
        return productVersion;
    }

    public void setProductVersion(String productVersion) {
        this.productVersion = productVersion;
    }

    public String getTestSys() {
        return testSys;
    }

    public void setTestSys(String testSys) {
        this.testSys = testSys;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public boolean isYellow() {
        return isYellow;
    }

    public void setYellow(boolean isYellow) {
        this.isYellow = isYellow;
    }

    public Float getUPHReality() {
        return UPHReality;
    }

    public void setUPHReality(Float uPHReality) {
        UPHReality = uPHReality;
    }

    public Float getUPHTheory() {
        return UPHTheory;
    }

    public void setUPHTheory(Float uPHTheory) {
        UPHTheory = uPHTheory;
    }

    public String getRevision() {
        return revision;
    }

    public void setRevision(String revision) {
        this.revision = revision;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @OneToMany(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "TEST_PROGRAM_TEMPLATE_ID")
    public List<AcetecAuthorization> getAcetecAuthorizations() {
        return acetecAuthorizations;
    }

    public void setAcetecAuthorizations(List<AcetecAuthorization> acetecAuthorizations) {
        this.acetecAuthorizations = acetecAuthorizations;
    }

    @ManyToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "Product_ID", referencedColumnName = "ID")
    public InternalProduct getInternalProduct() {
        return internalProduct;
    }

    public void setInternalProduct(InternalProduct internalProduct) {
        this.internalProduct = internalProduct;
    }

    @Override
    public String[] businessKeys() {
        // TODO Auto-generated method stub
        return null;
    }

	public String getTestType() {
		return testType;
	}

	public void setTestType(String testType) {
		this.testType = testType;
	}

	public String getAllowState() {
		return allowState;
	}

	public void setAllowState(String allowState) {
		this.allowState = allowState;
	}

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getTestSoftwareVersion() {
		return testSoftwareVersion;
	}

	public void setTestSoftwareVersion(String testSoftwareVersion) {
		this.testSoftwareVersion = testSoftwareVersion;
	}

}
