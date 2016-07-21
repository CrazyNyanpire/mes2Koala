package org.seu.acetec.mes2Koala.core.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by LCN on 2016/3/9.
 */
@Entity
@Table(name = "e_ft_runcardTemplate")
@Access(AccessType.PROPERTY)
public class FTRuncardTemplate extends MES2AbstractEntity {

//    private InternalProduct internalProduct;
	private InternalProduct internalProduct;
    //将flow表单拆分成需要编辑的子表单
    private String IQC;

    private String Baking;

    //存放第一个Test-*的信息
    private String siteTest;

    private String FVI;

    private String FQC;

    private String Packing;

    private String OQC;

    private SpecialFormTemplate specialFormTemplate;
    //对应6个产品负责人的签核记录
    private AcetecAuthorization keyQuantityAuthorization;
    private AcetecAuthorization assistQuantityAuthorization;


    private AcetecAuthorization keyProductionAuthorization;
    private AcetecAuthorization assistProductionAuthorization;

    private AcetecAuthorization keyTDEAuthorization;
    private AcetecAuthorization assistTDEAuthorization;


    @Column(name = "SIGNEDTIME")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date signedTime;


    @Column(columnDefinition = "text")
    @Basic(fetch = FetchType.LAZY)
    public String getIQC() {
        return IQC;
    }

    public void setIQC(String IQC) {
        this.IQC = IQC;
    }

    @Column(columnDefinition = "text")
    @Basic(fetch = FetchType.LAZY)
    public String getBaking() {
        return Baking;
    }

    public void setBaking(String baking) {
        Baking = baking;
    }


    @Column(columnDefinition = "text")
    @Basic(fetch = FetchType.LAZY)
    public String getFVI() {
        return FVI;
    }

    public void setFVI(String FVI) {
        this.FVI = FVI;
    }


    @Column(columnDefinition = "text")
    @Basic(fetch = FetchType.LAZY)
    public String getFQC() {
        return FQC;
    }

    public void setFQC(String FQC) {
        this.FQC = FQC;
    }


    @Column(columnDefinition = "text")
    @Basic(fetch = FetchType.LAZY)
    public String getPacking() {
        return Packing;
    }

    public void setPacking(String packing) {
        Packing = packing;
    }


    @Column(columnDefinition = "text")
    @Basic(fetch = FetchType.LAZY)
    public String getOQC() {
        return OQC;
    }

    public void setOQC(String OQC) {
        this.OQC = OQC;
    }


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = AcetecAuthorization.class)
    @JoinColumn(name = "KeyQuantityAuthorization_id")
    public AcetecAuthorization getKeyQuantityAuthorization() {
        return keyQuantityAuthorization;
    }

    public void setKeyQuantityAuthorization(AcetecAuthorization keyQuantityAuthorization) {
        this.keyQuantityAuthorization = keyQuantityAuthorization;
    }

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = AcetecAuthorization.class)
    @JoinColumn(name = "AssistQuantityAuthorization_id")
    public AcetecAuthorization getAssistQuantityAuthorization() {
        return assistQuantityAuthorization;
    }

    public void setAssistQuantityAuthorization(AcetecAuthorization assistQuantityAuthorization) {
        this.assistQuantityAuthorization = assistQuantityAuthorization;
    }

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = AcetecAuthorization.class)
    @JoinColumn(name = "KeyProductionAuthorization_id")
    public AcetecAuthorization getKeyProductionAuthorization() {
        return keyProductionAuthorization;
    }

    public void setKeyProductionAuthorization(AcetecAuthorization keyProductionAuthorization) {
        this.keyProductionAuthorization = keyProductionAuthorization;
    }

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = AcetecAuthorization.class)
    @JoinColumn(name = "AssistProductionAuthorization_id")
    public AcetecAuthorization getAssistProductionAuthorization() {
        return assistProductionAuthorization;
    }

    public void setAssistProductionAuthorization(AcetecAuthorization assistProductionAuthorization) {
        this.assistProductionAuthorization = assistProductionAuthorization;
    }


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = AcetecAuthorization.class)
    @JoinColumn(name = "KeyTDEAuthorization_id")
    public AcetecAuthorization getKeyTDEAuthorization() {
        return keyTDEAuthorization;
    }

    public void setKeyTDEAuthorization(AcetecAuthorization keyTDEAuthorization) {
        this.keyTDEAuthorization = keyTDEAuthorization;
    }

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = AcetecAuthorization.class)
    @JoinColumn(name = "AssistTDEAuthorization_id")
    public AcetecAuthorization getAssistTDEAuthorization() {
        return assistTDEAuthorization;
    }

    public void setAssistTDEAuthorization(AcetecAuthorization assistTDEAuthorization) {
        this.assistTDEAuthorization = assistTDEAuthorization;
    }

    @Column(columnDefinition = "text")
    @Basic(fetch = FetchType.LAZY)
    public String getSiteTest() {
        return siteTest;
    }

    public void setSiteTest(String siteTest) {
        this.siteTest = siteTest;
    }

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "INTERNAL_PRODUCT_ID")
    public InternalProduct getInternalProduct() {
        return internalProduct;
    }

    public void setInternalProduct(InternalProduct internalProduct) {
        this.internalProduct = internalProduct;
    }

    @Override
    public String[] businessKeys() {
        return new String[0];
    }

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "SPECIALFORM_ID")
    public SpecialFormTemplate getSpecialFormTemplate() {
        return specialFormTemplate;
    }

    public void setSpecialFormTemplate(SpecialFormTemplate specialFormTemplate) {
        this.specialFormTemplate = specialFormTemplate;
    }


    public Date getSignedTime() {
        return signedTime;
    }

    public void setSignedTime(Date signedTime) {
        this.signedTime = signedTime;
    }

    @Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		FTRuncardTemplate other = (FTRuncardTemplate) obj;
		if (Baking == null) {
			if (other.Baking != null)
				return false;
		} else if (!Baking.equals(other.Baking))
			return false;
		if (FQC == null) {
			if (other.FQC != null)
				return false;
		} else if (!FQC.equals(other.FQC))
			return false;
		if (FVI == null) {
			if (other.FVI != null)
				return false;
		} else if (!FVI.equals(other.FVI))
			return false;
		if (IQC == null) {
			if (other.IQC != null)
				return false;
		} else if (!IQC.equals(other.IQC))
			return false;
		if (OQC == null) {
			if (other.OQC != null)
				return false;
		} else if (!OQC.equals(other.OQC))
			return false;
		if (Packing == null) {
			if (other.Packing != null)
				return false;
		} else if (!Packing.equals(other.Packing))
			return false;
		if (assistProductionAuthorization == null) {
			if (other.assistProductionAuthorization != null)
				return false;
		} else if (!assistProductionAuthorization.equals(other.assistProductionAuthorization))
			return false;
		if (assistQuantityAuthorization == null) {
			if (other.assistQuantityAuthorization != null)
				return false;
		} else if (!assistQuantityAuthorization.equals(other.assistQuantityAuthorization))
			return false;
		if (assistTDEAuthorization == null) {
			if (other.assistTDEAuthorization != null)
				return false;
		} else if (!assistTDEAuthorization.equals(other.assistTDEAuthorization))
			return false;
		if (internalProduct == null) {
			if (other.internalProduct != null)
				return false;
		} else if (!internalProduct.equals(other.internalProduct))
			return false;
		if (keyProductionAuthorization == null) {
			if (other.keyProductionAuthorization != null)
				return false;
		} else if (!keyProductionAuthorization.equals(other.keyProductionAuthorization))
			return false;
		if (keyQuantityAuthorization == null) {
			if (other.keyQuantityAuthorization != null)
				return false;
		} else if (!keyQuantityAuthorization.equals(other.keyQuantityAuthorization))
			return false;
		if (keyTDEAuthorization == null) {
			if (other.keyTDEAuthorization != null)
				return false;
		} else if (!keyTDEAuthorization.equals(other.keyTDEAuthorization))
			return false;
		if (siteTest == null) {
			if (other.siteTest != null)
				return false;
		} else if (!siteTest.equals(other.siteTest))
			return false;
		if (specialFormTemplate == null) {
			if (other.specialFormTemplate != null)
				return false;
		} else if (!specialFormTemplate.equals(other.specialFormTemplate))
			return false;
		return true;
	}

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((Baking == null) ? 0 : Baking.hashCode());
		result = prime * result + ((FQC == null) ? 0 : FQC.hashCode());
		result = prime * result + ((FVI == null) ? 0 : FVI.hashCode());
		result = prime * result + ((IQC == null) ? 0 : IQC.hashCode());
		result = prime * result + ((OQC == null) ? 0 : OQC.hashCode());
		result = prime * result + ((Packing == null) ? 0 : Packing.hashCode());
		result = prime * result
				+ ((assistProductionAuthorization == null) ? 0 : assistProductionAuthorization.hashCode());
		result = prime * result + ((assistQuantityAuthorization == null) ? 0 : assistQuantityAuthorization.hashCode());
		result = prime * result + ((assistTDEAuthorization == null) ? 0 : assistTDEAuthorization.hashCode());
		result = prime * result + ((internalProduct == null) ? 0 : internalProduct.hashCode());
		result = prime * result + ((keyProductionAuthorization == null) ? 0 : keyProductionAuthorization.hashCode());
		result = prime * result + ((keyQuantityAuthorization == null) ? 0 : keyQuantityAuthorization.hashCode());
		result = prime * result + ((keyTDEAuthorization == null) ? 0 : keyTDEAuthorization.hashCode());
		result = prime * result + ((siteTest == null) ? 0 : siteTest.hashCode());
		result = prime * result + ((specialFormTemplate == null) ? 0 : specialFormTemplate.hashCode());
		return result;
	}
}
