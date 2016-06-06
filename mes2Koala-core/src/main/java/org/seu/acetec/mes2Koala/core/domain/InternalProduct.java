package org.seu.acetec.mes2Koala.core.domain;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.openkoala.organisation.core.domain.Employee;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 内部产品实体，是FT产品以及CP产品的父类，包括两类产品的共同特性。
 *
 * @author Howard
 * @version 1.0
 * @lastModifyDate 2015.12.15
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)    //使用joined继承类型
@Table(name = "E_INTERNALPRODUCT")
@Access(AccessType.PROPERTY)    //所有注解置于属性上方
public class InternalProduct extends MES2AbstractEntity {

    /**
     *
     */
    private static final long serialVersionUID = 3049125298494154735L;

    private Customer customerDirect;
    private Customer customerIndirect;
    private String customerProductNumber;
    private String customerProductRevision;
    private String internalProductNumber; //PID
    private String internalProductRevision;
    private String packageType;
    private String shipmentProductNumber;
    private String testType;
    // private SpecialForm specialForm;
    // private TestProgramPlan testProgramPlan;
    // private ProcessTemplate processTemplate;
    // private LabelPlan labelPlan;
    // private FTInfo ftInfo;
    // private CPInfo cpInfo;

    private ProcessTemplate processTemplate;
    private List<Label> labels = new ArrayList<Label>();
    private List<SBLTemplate> sblTemplates;
    private List<EQCSetting> eqcSettings;


    //6个产品责任人
    //质量
    private Employee keyQuantityManager;
    private Employee assistQuantityManager;
    //生产
    private Employee keyProductionManager;
    private Employee assistProductionManager;

    //TDE
    private Employee keyTDEManager;
    private Employee assistTDEManager;


    //FTRuncardTemplate
    private FTRuncardTemplate FTRuncardTemplate;

    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "internalProduct")
    public FTRuncardTemplate getFTRuncardTemplate() {
        return FTRuncardTemplate;
    }

    public void setFTRuncardTemplate(FTRuncardTemplate FTRuncardTemplate) {
        this.FTRuncardTemplate = FTRuncardTemplate;
    }

    //CPRuncardTemplate
    private CPRuncardTemplate CPRuncardTemplate;

    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "internalProduct")
    public CPRuncardTemplate getCPRuncardTemplate() {
        return CPRuncardTemplate;
    }

    public void setCPRuncardTemplate(CPRuncardTemplate CPRuncardTemplate) {
        this.CPRuncardTemplate = CPRuncardTemplate;
    }

    @OneToOne(cascade = CascadeType.REFRESH, targetEntity = Employee.class)
    @JoinColumn(name = "keyQuantityManager_id")
    public Employee getKeyQuantityManager() {
        return keyQuantityManager;
    }

    public void setKeyQuantityManager(Employee keyQuantityManager) {
        this.keyQuantityManager = keyQuantityManager;
    }

    @OneToOne(cascade = CascadeType.REFRESH, targetEntity = Employee.class)
    @JoinColumn(name = "assistQuantityManager_id")
    public Employee getAssistQuantityManager() {
        return assistQuantityManager;
    }

    public void setAssistQuantityManager(Employee assistQuantityManager) {
        this.assistQuantityManager = assistQuantityManager;
    }

    @OneToOne(cascade = CascadeType.REFRESH, targetEntity = Employee.class)
    @JoinColumn(name = "keyProductionManager_id")
    public Employee getKeyProductionManager() {
        return keyProductionManager;
    }

    public void setKeyProductionManager(Employee keyProductionManager) {
        this.keyProductionManager = keyProductionManager;
    }

    @OneToOne(cascade = CascadeType.REFRESH, targetEntity = Employee.class)
    @JoinColumn(name = "assistProductionManager_id")
    public Employee getAssistProductionManager() {
        return assistProductionManager;
    }

    public void setAssistProductionManager(Employee assistProductionManager) {
        this.assistProductionManager = assistProductionManager;
    }


    @OneToOne(cascade = CascadeType.REFRESH, targetEntity = Employee.class)
    @JoinColumn(name = "keyTDEManager_id")
    public Employee getKeyTDEManager() {
        return keyTDEManager;
    }

    public void setKeyTDEManager(Employee keyTDEManager) {
        this.keyTDEManager = keyTDEManager;
    }

    @OneToOne(cascade = CascadeType.REFRESH, targetEntity = Employee.class)
    @JoinColumn(name = "assistTDEManager_id")
    public Employee getAssistTDEManager() {
        return assistTDEManager;
    }

    public void setAssistTDEManager(Employee assistTDEManager) {
        this.assistTDEManager = assistTDEManager;
    }

    /**
     * 测试类型：FT、CP
     *
     * @return
     */
    public String getTestType() {
        return testType;
    }

    public void setTestType(String testType) {
        this.testType = testType;
    }

    /**
     * 直接客户，多对一，以ID作为外键。
     *
     * @return
     */
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "CUSTOMERDIRECT_ID", referencedColumnName = "ID")
    public Customer getCustomerDirect() {
        return customerDirect;
    }

    public void setCustomerDirect(Customer customerDirect) {
        this.customerDirect = customerDirect;
    }

    /**
     * 间接客户，多对一，以ID作为外键。
     *
     * @return
     */
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "CUSTOMERINDIRECT_ID", referencedColumnName = "ID")
    public Customer getCustomerIndirect() {
        return customerIndirect;
    }

    public void setCustomerIndirect(Customer customerIndirect) {
        this.customerIndirect = customerIndirect;
    }

    /**
     * process模板，多对一，以ID作为外键。
     * process模板以字符串的形式存储详细的流程信息，在下单时解析以其他的形式存储详细流程信息。
     *
     * @return
     */
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "Process_ID", referencedColumnName = "ID")
    public ProcessTemplate getProcessTemplate() {
        return processTemplate;
    }

    public void setProcessTemplate(ProcessTemplate processTemplate) {
        this.processTemplate = processTemplate;
    }

    /**
     * 客户产品型号，用于对外标识某项产品；在程序内部以内部产品型号internalProduct作为产品的唯一标识
     *
     * @return
     */
    public String getCustomerProductNumber() {
        return customerProductNumber;
    }

    public void setCustomerProductNumber(String customerProductNumber) {
        this.customerProductNumber = customerProductNumber;
    }

    /**
     * 客户产品版本，暂时弃之不用；真正意义的产品版本在测试程序管理中维护
     *
     * @return
     */
    public String getCustomerProductRevision() {
        return customerProductRevision;
    }

    public void setCustomerProductRevision(String customerProductRevision) {
        this.customerProductRevision = customerProductRevision;
    }

    /**
     * 内部产品型号，以一定规则生成，在系统内部唯一标识某项产品
     *
     * @return
     */
    public String getInternalProductNumber() {
        return internalProductNumber;
    }

    public void setInternalProductNumber(String internalProductNumber) {
        this.internalProductNumber = internalProductNumber;
    }

    /**
     * 内部产品版本，暂时无用
     *
     * @return
     */
    public String getInternalProductRevision() {
        return internalProductRevision;
    }

    public void setInternalProductRevision(String internalProductRevision) {
        this.internalProductRevision = internalProductRevision;
    }

    /**
     * 封装类型。仅有FT产品才有封装类型，但在实际应用中经常通过封装类型查询信息，故将封装类型放在了父类当中。
     *
     * @return
     */
    public String getPackageType() {
        return packageType;
    }

    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }

    /**
     * 出货产品型号，暂时未用到
     *
     * @return
     */
    public String getShipmentProductNumber() {
        return shipmentProductNumber;
    }

    public void setShipmentProductNumber(String shipmentProductNumber) {
        this.shipmentProductNumber = shipmentProductNumber;
    }


    /**
     * 标签，多对多。立即加载。
     *
     * @return
     */
    @ManyToMany(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    public List<Label> getLabels() {
        return labels;
    }

    public void setLabels(List<Label> labels) {
        this.labels = labels;
    }

    @OneToMany(mappedBy = "internalProduct", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    public List<EQCSetting> getEqcSettings() {
        return eqcSettings;
    }

    public void setEqcSettings(List<EQCSetting> eqcSettings) {
        this.eqcSettings = eqcSettings;
    }

    @OneToMany(mappedBy = "internalProduct", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    public List<SBLTemplate> getSblTemplates() {
        return sblTemplates;
    }

    public void setSblTemplates(List<SBLTemplate> sblTemplates) {
        this.sblTemplates = sblTemplates;
    }

    @Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		InternalProduct other = (InternalProduct) obj;
		if (assistProductionManager == null) {
			if (other.assistProductionManager != null)
				return false;
		} else if (!assistProductionManager.equals(other.assistProductionManager))
			return false;
		if (assistQuantityManager == null) {
			if (other.assistQuantityManager != null)
				return false;
		} else if (!assistQuantityManager.equals(other.assistQuantityManager))
			return false;
		if (assistTDEManager == null) {
			if (other.assistTDEManager != null)
				return false;
		} else if (!assistTDEManager.equals(other.assistTDEManager))
			return false;
		if (customerDirect == null) {
			if (other.customerDirect != null)
				return false;
		} else if (!customerDirect.equals(other.customerDirect))
			return false;
		if (customerIndirect == null) {
			if (other.customerIndirect != null)
				return false;
		} else if (!customerIndirect.equals(other.customerIndirect))
			return false;
		if (customerProductNumber == null) {
			if (other.customerProductNumber != null)
				return false;
		} else if (!customerProductNumber.equals(other.customerProductNumber))
			return false;
		if (customerProductRevision == null) {
			if (other.customerProductRevision != null)
				return false;
		} else if (!customerProductRevision.equals(other.customerProductRevision))
			return false;
		if (eqcSettings == null) {
			if (other.eqcSettings != null)
				return false;
		} else if (!eqcSettings.equals(other.eqcSettings))
			return false;
		if (internalProductNumber == null) {
			if (other.internalProductNumber != null)
				return false;
		} else if (!internalProductNumber.equals(other.internalProductNumber))
			return false;
		if (internalProductRevision == null) {
			if (other.internalProductRevision != null)
				return false;
		} else if (!internalProductRevision.equals(other.internalProductRevision))
			return false;
		if (keyProductionManager == null) {
			if (other.keyProductionManager != null)
				return false;
		} else if (!keyProductionManager.equals(other.keyProductionManager))
			return false;
		if (keyQuantityManager == null) {
			if (other.keyQuantityManager != null)
				return false;
		} else if (!keyQuantityManager.equals(other.keyQuantityManager))
			return false;
		if (keyTDEManager == null) {
			if (other.keyTDEManager != null)
				return false;
		} else if (!keyTDEManager.equals(other.keyTDEManager))
			return false;
		if (labels == null) {
			if (other.labels != null)
				return false;
		} else if (!labels.equals(other.labels))
			return false;
		if (packageType == null) {
			if (other.packageType != null)
				return false;
		} else if (!packageType.equals(other.packageType))
			return false;
		if (processTemplate == null) {
			if (other.processTemplate != null)
				return false;
		} else if (!processTemplate.equals(other.processTemplate))
			return false;
		if (sblTemplates == null) {
			if (other.sblTemplates != null)
				return false;
		} else if (!sblTemplates.equals(other.sblTemplates))
			return false;
		if (shipmentProductNumber == null) {
			if (other.shipmentProductNumber != null)
				return false;
		} else if (!shipmentProductNumber.equals(other.shipmentProductNumber))
			return false;
		if (testType == null) {
			if (other.testType != null)
				return false;
		} else if (!testType.equals(other.testType))
			return false;
		return true;
	}

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((assistProductionManager == null) ? 0 : assistProductionManager.hashCode());
		result = prime * result + ((assistQuantityManager == null) ? 0 : assistQuantityManager.hashCode());
		result = prime * result + ((assistTDEManager == null) ? 0 : assistTDEManager.hashCode());
		result = prime * result + ((customerDirect == null) ? 0 : customerDirect.hashCode());
		result = prime * result + ((customerIndirect == null) ? 0 : customerIndirect.hashCode());
		result = prime * result + ((customerProductNumber == null) ? 0 : customerProductNumber.hashCode());
		result = prime * result + ((customerProductRevision == null) ? 0 : customerProductRevision.hashCode());
		result = prime * result + ((eqcSettings == null) ? 0 : eqcSettings.hashCode());
		result = prime * result + ((internalProductNumber == null) ? 0 : internalProductNumber.hashCode());
		result = prime * result + ((internalProductRevision == null) ? 0 : internalProductRevision.hashCode());
		result = prime * result + ((keyProductionManager == null) ? 0 : keyProductionManager.hashCode());
		result = prime * result + ((keyQuantityManager == null) ? 0 : keyQuantityManager.hashCode());
		result = prime * result + ((keyTDEManager == null) ? 0 : keyTDEManager.hashCode());
		result = prime * result + ((labels == null) ? 0 : labels.hashCode());
		result = prime * result + ((packageType == null) ? 0 : packageType.hashCode());
		result = prime * result + ((processTemplate == null) ? 0 : processTemplate.hashCode());
		result = prime * result + ((sblTemplates == null) ? 0 : sblTemplates.hashCode());
		result = prime * result + ((shipmentProductNumber == null) ? 0 : shipmentProductNumber.hashCode());
		result = prime * result + ((testType == null) ? 0 : testType.hashCode());
		return result;
	}

    @Override
    public String[] businessKeys() {
        // TODO Auto-generated method stub
        return null;
    }

}
