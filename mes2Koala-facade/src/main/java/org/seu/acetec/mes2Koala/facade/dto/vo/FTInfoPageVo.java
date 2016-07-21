package org.seu.acetec.mes2Koala.facade.dto.vo;

/**
 * Created by LCN on 2016/3/29.
 */
public class FTInfoPageVo {

    private Long id;

    private String keyProductionManagerName;

    private String assistProductionManagerName;

    private String keyQuantityManagerName;

    private String assistQuantityManagerName;

    private String keyTDEManagerName;

    private String assistTDEManagerName;

    //客户产品型号
    private String customerProductNumber;

    //客户产品版本
    private String customerProductRevision;

    //所属直接客户
    private String customerDirectName;

    //所属间接客户
    private String customerIndirectName;

    //包装形式
    private String packingType;

    //出货产品型号
    private String shipmentProductNumber;

    //产品说明
    private String note;

    //包装厂
    private String packingFactory;

    //封装形式
    private String packageType;

    //晶圆厂
    private String waferFactory;

    //ReelCode固定码
    private String reelFixCode;

    //Reel盘数量
    private String reelQty;

    //产品尺寸
    private String size;
    
    //PID
    private String internalProductNumber;
    
    private String runcardApproval;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKeyProductionManagerName() {
        return keyProductionManagerName;
    }

    public void setKeyProductionManagerName(String keyProductionManagerName) {
        this.keyProductionManagerName = keyProductionManagerName;
    }

    public String getAssistProductionManagerName() {
        return assistProductionManagerName;
    }

    public void setAssistProductionManagerName(String assistProductionManagerName) {
        this.assistProductionManagerName = assistProductionManagerName;
    }

    public String getKeyQuantityManagerName() {
        return keyQuantityManagerName;
    }

    public void setKeyQuantityManagerName(String keyQuantityManagerName) {
        this.keyQuantityManagerName = keyQuantityManagerName;
    }

    public String getAssistQuantityManagerName() {
        return assistQuantityManagerName;
    }

    public void setAssistQuantityManagerName(String assistQuantityManagerName) {
        this.assistQuantityManagerName = assistQuantityManagerName;
    }

    public String getKeyTDEManagerName() {
        return keyTDEManagerName;
    }

    public void setKeyTDEManagerName(String keyTDEManagerName) {
        this.keyTDEManagerName = keyTDEManagerName;
    }

    public String getAssistTDEManagerName() {
        return assistTDEManagerName;
    }

    public void setAssistTDEManagerName(String assistTDEManagerName) {
        this.assistTDEManagerName = assistTDEManagerName;
    }

    public String getCustomerProductNumber() {
        return customerProductNumber;
    }

    public void setCustomerProductNumber(String customerProductNumber) {
        this.customerProductNumber = customerProductNumber;
    }

    public String getCustomerProductRevision() {
        return customerProductRevision;
    }

    public void setCustomerProductRevision(String customerProductRevision) {
        this.customerProductRevision = customerProductRevision;
    }

    public String getCustomerDirectName() {
        return customerDirectName;
    }

    public void setCustomerDirectName(String customerDirectName) {
        this.customerDirectName = customerDirectName;
    }

    public String getCustomerIndirectName() {
        return customerIndirectName;
    }

    public void setCustomerIndirectName(String customerIndirectName) {
        this.customerIndirectName = customerIndirectName;
    }

    public String getPackageType() {
        return packageType;
    }

    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }

    public String getShipmentProductNumber() {
        return shipmentProductNumber;
    }

    public void setShipmentProductNumber(String shipmentProductNumber) {
        this.shipmentProductNumber = shipmentProductNumber;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPackingFactory() {
        return packingFactory;
    }

    public void setPackingFactory(String packingFactory) {
        this.packingFactory = packingFactory;
    }

    public String getPackingType() {
        return packingType;
    }

    public void setPackingType(String packingType) {
        this.packingType = packingType;
    }

    public String getWaferFactory() {
        return waferFactory;
    }

    public void setWaferFactory(String waferFactory) {
        this.waferFactory = waferFactory;
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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

	public String getInternalProductNumber() {
		return internalProductNumber;
	}

	public void setInternalProductNumber(String internalProductNumber) {
		this.internalProductNumber = internalProductNumber;
	}

	public String getRuncardApproval() {
		return runcardApproval;
	}

	public void setRuncardApproval(String runcardApproval) {
		this.runcardApproval = runcardApproval;
	}
}
