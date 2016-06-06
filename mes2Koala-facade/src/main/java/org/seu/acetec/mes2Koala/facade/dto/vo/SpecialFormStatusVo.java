package org.seu.acetec.mes2Koala.facade.dto.vo;

/**
 * Created by LCN on 2016/4/4.
 */
public class SpecialFormStatusVo {

    private Boolean firstSheetStatus = false;

    private Boolean recordSheetStatus = false;

    private Boolean summarySheetStatus = false;

    private Boolean reelcodeSheetStatus = false;

    private Boolean machineMaterialRecordSheetStatus = false;

    private Boolean checkSheetStatus = false;

    public Boolean getFirstSheetStatus() {
        return firstSheetStatus;
    }

    public void setFirstSheetStatus(Boolean firstSheetStatus) {
        this.firstSheetStatus = firstSheetStatus;
    }

    public Boolean getRecordSheetStatus() {
        return recordSheetStatus;
    }

    public void setRecordSheetStatus(Boolean recordSheetStatus) {
        this.recordSheetStatus = recordSheetStatus;
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

    public Boolean getMachineMaterialRecordSheetStatus() {
        return machineMaterialRecordSheetStatus;
    }

    public void setMachineMaterialRecordSheetStatus(Boolean machineMaterialRecordSheetStatus) {
        this.machineMaterialRecordSheetStatus = machineMaterialRecordSheetStatus;
    }

    public Boolean getCheckSheetStatus() {
        return checkSheetStatus;
    }

    public void setCheckSheetStatus(Boolean checkSheetStatus) {
        this.checkSheetStatus = checkSheetStatus;
    }
}
