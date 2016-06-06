package org.seu.acetec.mes2Koala.facade.dto.vo;

/**
 * @author yuxiangque
 * @version 2016/3/30
 */
public class CPCustomerLotPageVo {
    private Long id;

    private int version;

    private int state;                   // 状态

    private String customerLotNumber;    // 客户批号

    private String customerPPO;          // 客户PPO

    private String customerNumber;       // 客户编号

    private String productVersion;       // 版本型号

    private String maskName;             // MASK_NAME

    private String size;                 // 晶圆尺寸

    private Long incomingQuantity;       // 数量

    private String incomingStyle;        // 到料形式

    private String customerProductNumber;// 来料型号

    private String incomingDate;         // 进料日期

    private String processName;			  //process名称
    
    private String processContent;		  //process内容

    private String packingLot;         // 封装厂批号

    private String waferLot;              //晶圆批号

    private String materialType;          // 内部物料类型


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

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getCustomerLotNumber() {
        return customerLotNumber;
    }

    public void setCustomerLotNumber(String customerLotNumber) {
        this.customerLotNumber = customerLotNumber;
    }

    public String getCustomerPPO() {
        return customerPPO;
    }

    public void setCustomerPPO(String customerPPO) {
        this.customerPPO = customerPPO;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getProductVersion() {
        return productVersion;
    }

    public void setProductVersion(String productVersion) {
        this.productVersion = productVersion;
    }

    public String getMaskName() {
        return maskName;
    }

    public void setMaskName(String maskName) {
        this.maskName = maskName;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Long getIncomingQuantity() {
        return incomingQuantity;
    }

    public void setIncomingQuantity(Long incomingQuantity) {
        this.incomingQuantity = incomingQuantity;
    }

    public String getIncomingStyle() {
        return incomingStyle;
    }

    public void setIncomingStyle(String incomingStyle) {
        this.incomingStyle = incomingStyle;
    }

    public String getCustomerProductNumber() {
        return customerProductNumber;
    }

    public void setCustomerProductNumber(String customerProductNumber) {
        this.customerProductNumber = customerProductNumber;
    }

    public String getIncomingDate() {
        return incomingDate;
    }

    public void setIncomingDate(String incomingDate) {
        this.incomingDate = incomingDate;
    }

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public String getProcessContent() {
		return processContent;
	}

	public void setProcessContent(String processContent) {
		this.processContent = processContent;
	}

    public String getPackingLot() {
        return packingLot;
    }

    public void setPackingLot(String packingLot) {
        this.packingLot = packingLot;
    }

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public String getWaferLot() {
        return waferLot;
    }

    public void setWaferLot(String waferLot) {
        this.waferLot = waferLot;
    }
}
