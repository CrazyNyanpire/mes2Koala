package org.seu.acetec.mes2Koala.facade.dto;

import java.io.Serializable;
import java.util.List;

import org.openkoala.organisation.facade.dto.EmployeeDTO;

/**
 * @author 阙宇翔
 * @version 2016/2/13
 */
public class CPInfoDTO implements Serializable {

    private static final long serialVersionUID = 1611229041130929781L;

    private Long id;

    private int version;
    
    private EmployeeDTO keyProductionManagerDTO;

    private EmployeeDTO assistProductionManagerDTO;

    private EmployeeDTO keyQuantityManagerDTO;

    private EmployeeDTO assistQuantityManagerDTO;

    private EmployeeDTO keyTDEManagerDTO;

    private EmployeeDTO assistTDEManagerDTO;

    // 所属直接客户
    private CustomerDTO customerDirectDTO;

    // 所属间接客户
    private CustomerDTO customerIndirectDTO;

    // 客户产品型号
    private String customerProductNumber;

    // 客户产品版本
    private String customerProductRevision;

    // 内部产品型号
    private String internalProductNumber;

    // 内部产品版本
    private String internalProductRevision;

    // 出货产品型号
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

    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    public void setCustomerIndirectDTO(CustomerDTO customerIndirectDTO) {
        this.customerIndirectDTO = customerIndirectDTO;
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

    public Integer getGrossDie() {
        return grossDie;
    }

    public void setGrossDie(Integer grossDie) {
        this.grossDie = grossDie;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInternalProductNumber() {
        return internalProductNumber;
    }

    public void setInternalProductNumber(String internalProductNumber) {
        this.internalProductNumber = internalProductNumber;
    }

    public String getInternalProductRevision() {
        return internalProductRevision;
    }

    public void setInternalProductRevision(String internalProductRevision) {
        this.internalProductRevision = internalProductRevision;
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

    public Integer getTouchQty() {
        return touchQty;
    }

    public void setTouchQty(Integer touchQty) {
        this.touchQty = touchQty;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
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
}
