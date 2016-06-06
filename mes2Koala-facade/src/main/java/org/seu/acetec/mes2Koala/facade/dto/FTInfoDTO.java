package org.seu.acetec.mes2Koala.facade.dto;

import java.io.Serializable;

import org.openkoala.organisation.facade.dto.EmployeeDTO;

public class FTInfoDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 8361372703872686526L;

    private EmployeeDTO keyProductionManagerDTO;
    private EmployeeDTO assistProductionManagerDTO;

    private EmployeeDTO keyQuantityManagerDTO;
    private EmployeeDTO assistQuantityManagerDTO;

    private EmployeeDTO keyTDEManagerDTO;
    private EmployeeDTO assistTDEManagerDTO;


    private Long id;

    private int version;

    private String note;

    private CustomerDTO customerIndirectDTO;

    private String TestSys3;

    private String H_P_1;

    private String TestSys2;

    private String TestSys1;

    private String waferFactory;

    private String packageType;

    private String packingType;

    private String H_P_3;

    private String H_P_2;

    private CustomerDTO customerDirectDTO;

    private String size;

    private String customerProductNumber;

    private String internalProductNumber;

    private String customerProductRevision;

    private String shipmentProductNumber;

    private String packingFactory;

    private String internalProductRevision;

    private String reelFixCode;

    private String reelQty;

    private Long processId;

    public String getNote() {
        return this.note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public CustomerDTO getCustomerIndirectDTO() {
        return customerIndirectDTO;
    }

    public void setCustomerIndirectDTO(CustomerDTO customerIndirectDTO) {
        this.customerIndirectDTO = customerIndirectDTO;
    }

    public CustomerDTO getCustomerDirectDTO() {
        return customerDirectDTO;
    }

    public void setCustomerDirectDTO(CustomerDTO customerDirectDTO) {
        this.customerDirectDTO = customerDirectDTO;
    }

    public String getTestSys3() {
        return this.TestSys3;
    }

    public void setTestSys3(String TestSys3) {
        this.TestSys3 = TestSys3;
    }

    public String getH_P_1() {
        return this.H_P_1;
    }

    public void setH_P_1(String H_P_1) {
        this.H_P_1 = H_P_1;
    }

    public String getTestSys2() {
        return this.TestSys2;
    }

    public void setTestSys2(String TestSys2) {
        this.TestSys2 = TestSys2;
    }

    public String getTestSys1() {
        return this.TestSys1;
    }

    public void setTestSys1(String TestSys1) {
        this.TestSys1 = TestSys1;
    }

    public String getWaferFactory() {
        return this.waferFactory;
    }

    public void setWaferFactory(String waferFactory) {
        this.waferFactory = waferFactory;
    }

    public String getPackageType() {
        return this.packageType;
    }

    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }

    public String getPackingType() {
        return this.packingType;
    }

    public void setPackingType(String packingType) {
        this.packingType = packingType;
    }

    public String getH_P_3() {
        return this.H_P_3;
    }

    public void setH_P_3(String H_P_3) {
        this.H_P_3 = H_P_3;
    }

    public String getH_P_2() {
        return this.H_P_2;
    }

    public void setH_P_2(String H_P_2) {
        this.H_P_2 = H_P_2;
    }

    public String getSize() {
        return this.size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getCustomerProductNumber() {
        return this.customerProductNumber;
    }

    public void setCustomerProductNumber(String customerProductNumber) {
        this.customerProductNumber = customerProductNumber;
    }

    public String getInternalProductNumber() {
        return this.internalProductNumber;
    }

    public void setInternalProductNumber(String internalProductNumber) {
        this.internalProductNumber = internalProductNumber;
    }

    public String getCustomerProductRevision() {
        return this.customerProductRevision;
    }

    public void setCustomerProductRevision(String customerProductRevision) {
        this.customerProductRevision = customerProductRevision;
    }

    public String getShipmentProductNumber() {
        return this.shipmentProductNumber;
    }

    public void setShipmentProductNumber(String shipmentProductNumber) {
        this.shipmentProductNumber = shipmentProductNumber;
    }

    public String getPackingFactory() {
        return this.packingFactory;
    }

    public void setPackingFactory(String packingFactory) {
        this.packingFactory = packingFactory;
    }

    public String getInternalProductRevision() {
        return this.internalProductRevision;
    }

    public void setInternalProductRevision(String internalProductRevision) {
        this.internalProductRevision = internalProductRevision;
    }

    public String getReelFixCode() {
        return reelFixCode;
    }

    public void setReelFixCode(String reelFixCode) {
        this.reelFixCode = reelFixCode;
    }

    public String getReelQty() {
        return reelQty;
    }

    public void setReelQty(String reelQty) {
        this.reelQty = reelQty;
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


    public EmployeeDTO getKeyProductionManagerDTO() {
        return keyProductionManagerDTO;
    }

    public void setKeyProductionManagerDTO(EmployeeDTO keyProductionManagerDTO) {
        this.keyProductionManagerDTO = keyProductionManagerDTO;
    }

    public EmployeeDTO getAssistProductionManagerDTO() {
        return assistProductionManagerDTO;
    }

    public void setAssistProductionManagerDTO(EmployeeDTO assistProductionManagerDTO) {
        this.assistProductionManagerDTO = assistProductionManagerDTO;
    }

    public EmployeeDTO getKeyQuantityManagerDTO() {
        return keyQuantityManagerDTO;
    }

    public void setKeyQuantityManagerDTO(EmployeeDTO keyQuantityManagerDTO) {
        this.keyQuantityManagerDTO = keyQuantityManagerDTO;
    }

    public EmployeeDTO getAssistQuantityManagerDTO() {
        return assistQuantityManagerDTO;
    }

    public void setAssistQuantityManagerDTO(EmployeeDTO assistQuantityManagerDTO) {
        this.assistQuantityManagerDTO = assistQuantityManagerDTO;
    }

    public EmployeeDTO getKeyTDEManagerDTO() {
        return keyTDEManagerDTO;
    }

    public void setKeyTDEManagerDTO(EmployeeDTO keyTDEManagerDTO) {
        this.keyTDEManagerDTO = keyTDEManagerDTO;
    }

    public EmployeeDTO getAssistTDEManagerDTO() {
        return assistTDEManagerDTO;
    }

    public void setAssistTDEManagerDTO(EmployeeDTO assistTDEManagerDTO) {
        this.assistTDEManagerDTO = assistTDEManagerDTO;
    }

}