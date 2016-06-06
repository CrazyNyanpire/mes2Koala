package org.seu.acetec.mes2Koala.core.domain;

import javax.persistence.*;

/**
 * Created by LCN on 2016/3/29.
 */
@Entity
@Table(name = "e_special_form_template")
@Access(AccessType.PROPERTY)
public class SpecialFormTemplate extends MES2AbstractEntity {

    //特殊表单
    //首页
    private String firstSheet;
    //表单状态  根据状态决定是否打印
    private Boolean firstSheetStatus = true;

    //summary表
    private String summarySheet;

    private Boolean summarySheetStatus = false;
    //reelcode表
    private String reelcodeSheet;

    private Boolean reelcodeSheetStatus = false;

    //record表
    private String recordSheet;

    private Boolean recordSheetStatus = false;


    //机台落料记录表
    private String machineMaterialRecordSheet;

    private Boolean machineMaterialRecordSheetStatus = false;

    //自主检查表
    private String checkSheet;

    private Boolean checkSheetStatus = false;

    @Column(columnDefinition = "text")
    @Basic(fetch = FetchType.LAZY)
    public String getFirstSheet() {
        return firstSheet;
    }

    public void setFirstSheet(String firstSheet) {
        this.firstSheet = firstSheet;
    }

    @Column(columnDefinition = "text")
    @Basic(fetch = FetchType.LAZY)
    public String getSummarySheet() {
        return summarySheet;
    }

    public void setSummarySheet(String summarySheet) {
        this.summarySheet = summarySheet;
    }

    @Column(columnDefinition = "text")
    @Basic(fetch = FetchType.LAZY)
    public String getReelcodeSheet() {
        return reelcodeSheet;
    }

    public void setReelcodeSheet(String reelcodeSheet) {
        this.reelcodeSheet = reelcodeSheet;
    }

    @Column(columnDefinition = "text")
    @Basic(fetch = FetchType.LAZY)
    public String getRecordSheet() {
        return recordSheet;
    }

    @Column(columnDefinition = "text")
    @Basic(fetch = FetchType.LAZY)
    public void setRecordSheet(String recordSheet) {
        this.recordSheet = recordSheet;
    }


    public Boolean getFirstSheetStatus() {
        return firstSheetStatus;
    }

    public void setFirstSheetStatus(Boolean firstSheetStatus) {
        this.firstSheetStatus = firstSheetStatus;
    }

    public Boolean getSummarySheetStatus() {
        return summarySheetStatus;
    }

    public void setSummarySheetStatus(Boolean summarySheetStatus) {
        this.summarySheetStatus = summarySheetStatus;
    }

    public Boolean getReelcodeSheetStatus() {
        return reelcodeSheetStatus;
    }

    public void setReelcodeSheetStatus(Boolean reelcodeSheetStatus) {
        this.reelcodeSheetStatus = reelcodeSheetStatus;
    }

    public Boolean getRecordSheetStatus() {
        return recordSheetStatus;
    }

    public void setRecordSheetStatus(Boolean recordSheetStatus) {
        this.recordSheetStatus = recordSheetStatus;
    }

    @Column(columnDefinition = "text")
    @Basic(fetch = FetchType.LAZY)
    public String getMachineMaterialRecordSheet() {
        return machineMaterialRecordSheet;
    }

    public void setMachineMaterialRecordSheet(String machineMaterialRecordSheet) {
        this.machineMaterialRecordSheet = machineMaterialRecordSheet;
    }

    public Boolean getMachineMaterialRecordSheetStatus() {
        return machineMaterialRecordSheetStatus;
    }

    public void setMachineMaterialRecordSheetStatus(Boolean machineMaterialRecordSheetStatus) {
        this.machineMaterialRecordSheetStatus = machineMaterialRecordSheetStatus;
    }
    @Column(columnDefinition = "text")
    @Basic(fetch = FetchType.LAZY)
    public String getCheckSheet() {
        return checkSheet;
    }

    public void setCheckSheet(String checkSheet) {
        this.checkSheet = checkSheet;
    }

    public Boolean getCheckSheetStatus() {
        return checkSheetStatus;
    }

    public void setCheckSheetStatus(Boolean checkSheetStatus) {
        this.checkSheetStatus = checkSheetStatus;
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

        SpecialFormTemplate that = (SpecialFormTemplate) o;

        if (firstSheet != null ? !firstSheet.equals(that.firstSheet) : that.firstSheet != null) return false;
        if (firstSheetStatus != null ? !firstSheetStatus.equals(that.firstSheetStatus) : that.firstSheetStatus != null)
            return false;
        if (summarySheet != null ? !summarySheet.equals(that.summarySheet) : that.summarySheet != null) return false;
        if (summarySheetStatus != null ? !summarySheetStatus.equals(that.summarySheetStatus) : that.summarySheetStatus != null)
            return false;
        if (reelcodeSheet != null ? !reelcodeSheet.equals(that.reelcodeSheet) : that.reelcodeSheet != null)
            return false;
        if (reelcodeSheetStatus != null ? !reelcodeSheetStatus.equals(that.reelcodeSheetStatus) : that.reelcodeSheetStatus != null)
            return false;
        if (recordSheet != null ? !recordSheet.equals(that.recordSheet) : that.recordSheet != null) return false;
        if (recordSheetStatus != null ? !recordSheetStatus.equals(that.recordSheetStatus) : that.recordSheetStatus != null)
            return false;
        if (machineMaterialRecordSheet != null ? !machineMaterialRecordSheet.equals(that.machineMaterialRecordSheet) : that.machineMaterialRecordSheet != null)
            return false;
        if (machineMaterialRecordSheetStatus != null ? !machineMaterialRecordSheetStatus.equals(that.machineMaterialRecordSheetStatus) : that.machineMaterialRecordSheetStatus != null)
            return false;
        if (checkSheet != null ? !checkSheet.equals(that.checkSheet) : that.checkSheet != null) return false;
        return checkSheetStatus != null ? checkSheetStatus.equals(that.checkSheetStatus) : that.checkSheetStatus == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (firstSheet != null ? firstSheet.hashCode() : 0);
        result = 31 * result + (firstSheetStatus != null ? firstSheetStatus.hashCode() : 0);
        result = 31 * result + (summarySheet != null ? summarySheet.hashCode() : 0);
        result = 31 * result + (summarySheetStatus != null ? summarySheetStatus.hashCode() : 0);
        result = 31 * result + (reelcodeSheet != null ? reelcodeSheet.hashCode() : 0);
        result = 31 * result + (reelcodeSheetStatus != null ? reelcodeSheetStatus.hashCode() : 0);
        result = 31 * result + (recordSheet != null ? recordSheet.hashCode() : 0);
        result = 31 * result + (recordSheetStatus != null ? recordSheetStatus.hashCode() : 0);
        result = 31 * result + (machineMaterialRecordSheet != null ? machineMaterialRecordSheet.hashCode() : 0);
        result = 31 * result + (machineMaterialRecordSheetStatus != null ? machineMaterialRecordSheetStatus.hashCode() : 0);
        result = 31 * result + (checkSheet != null ? checkSheet.hashCode() : 0);
        result = 31 * result + (checkSheetStatus != null ? checkSheetStatus.hashCode() : 0);
        return result;
    }
}
