package org.seu.acetec.mes2Koala.facade.dto.vo;

import org.seu.acetec.mes2Koala.facade.dto.AcetecAuthorizationDTO;
import org.seu.acetec.mes2Koala.facade.dto.InternalProductDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yuxiangque
 * @version 2016/3/30
 */
public class TestProgramTemplatePageVo {

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

    private String customerNumber; // internalProductDTO.customerDirectDTO.number;

    private String customerProductNumber; // internalProductDTO.customerProductNumber;

    private String authorizationEmployeeNames; // acetecAuthorizationDTOs.employeeDTO.name

    private String authorizationDatetime; // acetecAuthorizationDTOs.lastModifyTime

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

    public String getProductVersion() {
        return productVersion;
    }

    public void setProductVersion(String productVersion) {
        this.productVersion = productVersion;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getRevision() {
        return revision;
    }

    public void setRevision(String revision) {
        this.revision = revision;
    }

    public Integer getUPHTheory() {
        return UPHTheory;
    }

    public void setUPHTheory(Integer UPHTheory) {
        this.UPHTheory = UPHTheory;
    }

    public String getTestSys() {
        return testSys;
    }

    public void setTestSys(String testSys) {
        this.testSys = testSys;
    }

    public Boolean getYellow() {
        return isYellow;
    }

    public void setYellow(Boolean yellow) {
        isYellow = yellow;
    }

    public Integer getUPHReality() {
        return UPHReality;
    }

    public void setUPHReality(Integer UPHReality) {
        this.UPHReality = UPHReality;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTestType() {
        return testType;
    }

    public void setTestType(String testType) {
        this.testType = testType;
    }

    public String getNote() {
        return note;
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

	public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getCustomerProductNumber() {
        return customerProductNumber;
    }

    public void setCustomerProductNumber(String customerProductNumber) {
        this.customerProductNumber = customerProductNumber;
    }

    public String getAuthorizationEmployeeNames() {
        return authorizationEmployeeNames;
    }

    public void setAuthorizationEmployeeNames(String authorizationEmployeeNames) {
        this.authorizationEmployeeNames = authorizationEmployeeNames;
    }

    public String getAuthorizationDatetime() {
        return authorizationDatetime;
    }

    public void setAuthorizationDatetime(String authorizationDatetime) {
        this.authorizationDatetime = authorizationDatetime;
    }
}
