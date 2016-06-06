package org.seu.acetec.mes2Koala.core.domain;

import javax.persistence.*;

/**
 * Created by LCN on 2016/6/1.
 */
@Entity
@Table(name = "e_cp_runcard")
@Access(AccessType.PROPERTY)
public class CPRuncard extends MES2AbstractEntity {

    private CPLot cpLot;
    //flow
    private String IQC;
    private String FQC;

    private String CP1;
    private String CP1_Before_Bake;
    private String CP1_After_Bake;
    private String CP1_DT;

    private String CP2;
    private String CP2_Before_Bake;
    private String CP2_After_Bake;
    private String CP2_DT;


    private String CP3;
    private String CP3_Before_Bake;
    private String CP3_After_Bake;
    private String CP3_DT;


    private String CP4;
    private String CP4_Before_Bake;
    private String CP4_After_Bake;
    private String CP4_DT;


    private String Packing;

    private String OQC;

    //记录总的站点数
    private String totalSite;

    private CPSpecialFormTemplate cpSpecialFormTemplate;

    //对应6个产品负责人的签核记录
    private AcetecAuthorization keyQuantityAuthorization;
    private AcetecAuthorization assistQuantityAuthorization;


    private AcetecAuthorization keyProductionAuthorization;
    private AcetecAuthorization assistProductionAuthorization;

    private AcetecAuthorization keyTDEAuthorization;
    private AcetecAuthorization assistTDEAuthorization;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "CP_SPECIALFORM_ID")
    public CPSpecialFormTemplate getCpSpecialFormTemplate() {
        return cpSpecialFormTemplate;
    }

    public void setCpSpecialFormTemplate(CPSpecialFormTemplate cpSpecialFormTemplate) {
        this.cpSpecialFormTemplate = cpSpecialFormTemplate;
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
    public String getIQC() {
        return IQC;
    }

    public void setIQC(String IQC) {
        this.IQC = IQC;
    }

    @Column(columnDefinition = "text")
    @Basic(fetch = FetchType.LAZY)
    public String getCP1_Before_Bake() {
        return CP1_Before_Bake;
    }

    public void setCP1_Before_Bake(String CP1_Before_Bake) {
        this.CP1_Before_Bake = CP1_Before_Bake;
    }


    @Column(columnDefinition = "text")
    @Basic(fetch = FetchType.LAZY)
    public String getCP1() {
        return CP1;
    }

    public void setCP1(String CP1) {
        this.CP1 = CP1;
    }


    @Column(columnDefinition = "text")
    @Basic(fetch = FetchType.LAZY)
    public String getCP1_DT() {
        return CP1_DT;
    }

    public void setCP1_DT(String CP1_DT) {
        this.CP1_DT = CP1_DT;
    }


    @Column(columnDefinition = "text")
    @Basic(fetch = FetchType.LAZY)
    public String getCP1_After_Bake() {
        return CP1_After_Bake;
    }

    public void setCP1_After_Bake(String CP1_After_Bake) {
        this.CP1_After_Bake = CP1_After_Bake;
    }


    @Column(columnDefinition = "text")
    @Basic(fetch = FetchType.LAZY)
    public String getCP2() {
        return CP2;
    }

    public void setCP2(String CP2) {
        this.CP2 = CP2;
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

    @Column(columnDefinition = "text")
    @Basic(fetch = FetchType.LAZY)
    public String getCP2_Before_Bake() {
        return CP2_Before_Bake;
    }

    public void setCP2_Before_Bake(String CP2_Before_Bake) {
        this.CP2_Before_Bake = CP2_Before_Bake;
    }

    @Column(columnDefinition = "text")
    @Basic(fetch = FetchType.LAZY)
    public String getCP2_After_Bake() {
        return CP2_After_Bake;
    }

    public void setCP2_After_Bake(String CP2_After_Bake) {
        this.CP2_After_Bake = CP2_After_Bake;
    }

    @Column(columnDefinition = "text")
    @Basic(fetch = FetchType.LAZY)
    public String getCP3() {
        return CP3;
    }

    public void setCP3(String CP3) {
        this.CP3 = CP3;
    }

    @Column(columnDefinition = "text")
    @Basic(fetch = FetchType.LAZY)
    public String getCP3_Before_Bake() {
        return CP3_Before_Bake;
    }

    public void setCP3_Before_Bake(String CP3_Before_Bake) {
        this.CP3_Before_Bake = CP3_Before_Bake;
    }


    @Column(columnDefinition = "text")
    @Basic(fetch = FetchType.LAZY)
    public String getCP3_After_Bake() {
        return CP3_After_Bake;
    }

    public void setCP3_After_Bake(String CP3_After_Bake) {
        this.CP3_After_Bake = CP3_After_Bake;
    }

    @Column(columnDefinition = "text")
    @Basic(fetch = FetchType.LAZY)
    public String getCP4() {
        return CP4;
    }

    public void setCP4(String CP4) {
        this.CP4 = CP4;
    }


    @Column(columnDefinition = "text")
    @Basic(fetch = FetchType.LAZY)
    public String getCP4_Before_Bake() {
        return CP4_Before_Bake;
    }

    public void setCP4_Before_Bake(String CP4_Before_Bake) {
        this.CP4_Before_Bake = CP4_Before_Bake;
    }

    @Column(columnDefinition = "text")
    @Basic(fetch = FetchType.LAZY)
    public String getCP4_After_Bake() {
        return CP4_After_Bake;
    }

    public void setCP4_After_Bake(String CP4_After_Bake) {
        this.CP4_After_Bake = CP4_After_Bake;
    }

    @Column(columnDefinition = "text")
    @Basic(fetch = FetchType.LAZY)
    public String getTotalSite() {
        return totalSite;
    }

    public void setTotalSite(String totalSite) {
        this.totalSite = totalSite;
    }

    @Column(columnDefinition = "text")
    @Basic(fetch = FetchType.LAZY)
    public String getCP2_DT() {
        return CP2_DT;
    }

    public void setCP2_DT(String CP2_DT) {
        this.CP2_DT = CP2_DT;
    }

    @Column(columnDefinition = "text")
    @Basic(fetch = FetchType.LAZY)
    public String getCP3_DT() {
        return CP3_DT;
    }

    public void setCP3_DT(String CP3_DT) {
        this.CP3_DT = CP3_DT;
    }

    @Column(columnDefinition = "text")
    @Basic(fetch = FetchType.LAZY)
    public String getCP4_DT() {
        return CP4_DT;
    }

    public void setCP4_DT(String CP4_DT) {
        this.CP4_DT = CP4_DT;
    }


    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "CPLot_ID")
    public CPLot getCpLot() {
        return cpLot;
    }

    public void setCpLot(CPLot cpLot) {
        this.cpLot = cpLot;
    }

    @Override
    public String[] businessKeys() {
        return new String[0];
    }
}
