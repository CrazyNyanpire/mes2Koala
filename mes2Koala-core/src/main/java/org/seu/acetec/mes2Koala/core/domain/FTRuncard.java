package org.seu.acetec.mes2Koala.core.domain;

import javax.persistence.*;

/**
 * Created by LCN on 2016/5/16.
 */
@Entity
@Table(name = "e_ft_runcard")
@Access(AccessType.PROPERTY)
public class FTRuncard extends MES2AbstractEntity{

    private FTLot ftLot;

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


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "SPECIALFORM_ID")
    public SpecialFormTemplate getSpecialFormTemplate() {
        return specialFormTemplate;
    }

    public void setSpecialFormTemplate(SpecialFormTemplate specialFormTemplate) {
        this.specialFormTemplate = specialFormTemplate;
    }

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "FTLot_ID")
    public FTLot getFtLot() {
        return ftLot;
    }

    public void setFtLot(FTLot ftLot) {
        this.ftLot = ftLot;
    }

    @Override
    public String[] businessKeys() {
        return new String[0];
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        FTRuncard ftRuncard = (FTRuncard) o;
        if (IQC != null ? !IQC.equals(ftRuncard.IQC) : ftRuncard.IQC != null) return false;
        if (Baking != null ? !Baking.equals(ftRuncard.Baking) : ftRuncard.Baking != null) return false;
        if (siteTest != null ? !siteTest.equals(ftRuncard.siteTest) : ftRuncard.siteTest != null) return false;
        if (FVI != null ? !FVI.equals(ftRuncard.FVI) : ftRuncard.FVI != null) return false;
        if (FQC != null ? !FQC.equals(ftRuncard.FQC) : ftRuncard.FQC != null) return false;
        if (Packing != null ? !Packing.equals(ftRuncard.Packing) : ftRuncard.Packing != null) return false;
        if (OQC != null ? !OQC.equals(ftRuncard.OQC) : ftRuncard.OQC != null) return false;
        if (specialFormTemplate != null ? !specialFormTemplate.equals(ftRuncard.specialFormTemplate) : ftRuncard.specialFormTemplate != null)
            return false;
        if (keyQuantityAuthorization != null ? !keyQuantityAuthorization.equals(ftRuncard.keyQuantityAuthorization) : ftRuncard.keyQuantityAuthorization != null)
            return false;
        if (assistQuantityAuthorization != null ? !assistQuantityAuthorization.equals(ftRuncard.assistQuantityAuthorization) : ftRuncard.assistQuantityAuthorization != null)
            return false;
        if (keyProductionAuthorization != null ? !keyProductionAuthorization.equals(ftRuncard.keyProductionAuthorization) : ftRuncard.keyProductionAuthorization != null)
            return false;
        if (assistProductionAuthorization != null ? !assistProductionAuthorization.equals(ftRuncard.assistProductionAuthorization) : ftRuncard.assistProductionAuthorization != null)
            return false;
        if (keyTDEAuthorization != null ? !keyTDEAuthorization.equals(ftRuncard.keyTDEAuthorization) : ftRuncard.keyTDEAuthorization != null)
            return false;
        return assistTDEAuthorization != null ? assistTDEAuthorization.equals(ftRuncard.assistTDEAuthorization) : ftRuncard.assistTDEAuthorization == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (IQC != null ? IQC.hashCode() : 0);
        result = 31 * result + (Baking != null ? Baking.hashCode() : 0);
        result = 31 * result + (siteTest != null ? siteTest.hashCode() : 0);
        result = 31 * result + (FVI != null ? FVI.hashCode() : 0);
        result = 31 * result + (FQC != null ? FQC.hashCode() : 0);
        result = 31 * result + (Packing != null ? Packing.hashCode() : 0);
        result = 31 * result + (OQC != null ? OQC.hashCode() : 0);
        result = 31 * result + (specialFormTemplate != null ? specialFormTemplate.hashCode() : 0);
        result = 31 * result + (keyQuantityAuthorization != null ? keyQuantityAuthorization.hashCode() : 0);
        result = 31 * result + (assistQuantityAuthorization != null ? assistQuantityAuthorization.hashCode() : 0);
        result = 31 * result + (keyProductionAuthorization != null ? keyProductionAuthorization.hashCode() : 0);
        result = 31 * result + (assistProductionAuthorization != null ? assistProductionAuthorization.hashCode() : 0);
        result = 31 * result + (keyTDEAuthorization != null ? keyTDEAuthorization.hashCode() : 0);
        result = 31 * result + (assistTDEAuthorization != null ? assistTDEAuthorization.hashCode() : 0);
        return result;
    }
}
