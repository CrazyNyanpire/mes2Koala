package org.seu.acetec.mes2Koala.core.domain;

import javax.persistence.*;

/**
 * Created by LCN on 2016/4/27.
 */
@Entity
@Table(name = "e_cp_flow_form")
@Access(AccessType.PROPERTY)
public class CPFlowForm extends MES2AbstractEntity{

    private String IQC;

    private String CP1_Before_Bake;

    private String CP1;

    private String CP1_DT;

    private String CP1_After_Bake;

    private String CP2;

    private String FQC;

    private String Paking;

    private String OQC;

    @Override
    public String[] businessKeys() {
        return new String[0];
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
    public String getPaking() {
        return Paking;
    }

    public void setPaking(String paking) {
        Paking = paking;
    }


    @Column(columnDefinition = "text")
    @Basic(fetch = FetchType.LAZY)
    public String getOQC() {
        return OQC;
    }

    public void setOQC(String OQC) {
        this.OQC = OQC;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        CPFlowForm that = (CPFlowForm) o;

        if (IQC != null ? !IQC.equals(that.IQC) : that.IQC != null) return false;
        if (CP1_Before_Bake != null ? !CP1_Before_Bake.equals(that.CP1_Before_Bake) : that.CP1_Before_Bake != null)
            return false;
        if (CP1 != null ? !CP1.equals(that.CP1) : that.CP1 != null) return false;
        if (CP1_DT != null ? !CP1_DT.equals(that.CP1_DT) : that.CP1_DT != null) return false;
        if (CP1_After_Bake != null ? !CP1_After_Bake.equals(that.CP1_After_Bake) : that.CP1_After_Bake != null)
            return false;
        if (CP2 != null ? !CP2.equals(that.CP2) : that.CP2 != null) return false;
        if (FQC != null ? !FQC.equals(that.FQC) : that.FQC != null) return false;
        if (Paking != null ? !Paking.equals(that.Paking) : that.Paking != null) return false;
        return OQC != null ? OQC.equals(that.OQC) : that.OQC == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (IQC != null ? IQC.hashCode() : 0);
        result = 31 * result + (CP1_Before_Bake != null ? CP1_Before_Bake.hashCode() : 0);
        result = 31 * result + (CP1 != null ? CP1.hashCode() : 0);
        result = 31 * result + (CP1_DT != null ? CP1_DT.hashCode() : 0);
        result = 31 * result + (CP1_After_Bake != null ? CP1_After_Bake.hashCode() : 0);
        result = 31 * result + (CP2 != null ? CP2.hashCode() : 0);
        result = 31 * result + (FQC != null ? FQC.hashCode() : 0);
        result = 31 * result + (Paking != null ? Paking.hashCode() : 0);
        result = 31 * result + (OQC != null ? OQC.hashCode() : 0);
        return result;
    }
}
