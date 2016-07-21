package org.seu.acetec.mes2Koala.facade.dto.vo;

/**
 * @author yuxiangque
 * @version 2016/3/30
 */
public class CPInfoPageVo {

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

    //出货产品型号
    private String shipmentProductNumber;

    // 晶圆尺寸
    private String waferSize;

    // GROSS DIE
    private Integer grossDie;

    // 最低PASS报警
    private Integer warningQty;

    // 报警指标分类
    private String warningType;

    // 测试时间/片
    private Float testTime;

    // 产品制程要求
    private String productRequire;

    // 每片接触次数
    private Integer touchQty;
    
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

    public String getShipmentProductNumber() {
        return shipmentProductNumber;
    }

    public void setShipmentProductNumber(String shipmentProductNumber) {
        this.shipmentProductNumber = shipmentProductNumber;
    }

    public String getWaferSize() {
        return waferSize;
    }

    public void setWaferSize(String waferSize) {
        this.waferSize = waferSize;
    }

    public Integer getGrossDie() {
        return grossDie;
    }

    public void setGrossDie(Integer grossDie) {
        this.grossDie = grossDie;
    }

    public Integer getWarningQty() {
        return warningQty;
    }

    public void setWarningQty(Integer warningQty) {
        this.warningQty = warningQty;
    }

    public String getWarningType() {
        return warningType;
    }

    public void setWarningType(String warningType) {
        this.warningType = warningType;
    }

    public Integer getTouchQty() {
        return touchQty;
    }

    public void setTouchQty(Integer touchQty) {
        this.touchQty = touchQty;
    }

	public String getProductRequire() {
		return productRequire;
	}

	public void setProductRequire(String productRequire) {
		this.productRequire = productRequire;
	}

	public Float getTestTime() {
		return testTime;
	}

	public void setTestTime(Float testTime) {
		this.testTime = testTime;
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
