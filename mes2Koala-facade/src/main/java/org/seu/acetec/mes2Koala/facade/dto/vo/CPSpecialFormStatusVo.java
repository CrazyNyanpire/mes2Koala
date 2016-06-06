package org.seu.acetec.mes2Koala.facade.dto.vo;

/**
 * Created by LCN on 2016/4/28.
 */
public class CPSpecialFormStatusVo {
    private Boolean MCP_COVER1SheetStatus = false;
    private Boolean CP1SheetStatus = false;
    private Boolean CP2SheetStatus = false;
    private Boolean MAP_SHIFT1Status = false;
    private Boolean Sheet1Status = false;
    private Boolean Sheet2Status = false;

    public Boolean getMCP_COVER1SheetStatus() {
        return MCP_COVER1SheetStatus;
    }

    public void setMCP_COVER1SheetStatus(Boolean MCP_COVER1SheetStatus) {
        this.MCP_COVER1SheetStatus = MCP_COVER1SheetStatus;
    }

    public Boolean getCP1SheetStatus() {
        return CP1SheetStatus;
    }

    public void setCP1SheetStatus(Boolean CP1SheetStatus) {
        this.CP1SheetStatus = CP1SheetStatus;
    }

    public Boolean getCP2SheetStatus() {
        return CP2SheetStatus;
    }

    public void setCP2SheetStatus(Boolean CP2SheetStatus) {
        this.CP2SheetStatus = CP2SheetStatus;
    }

    public Boolean getMAP_SHIFT1Status() {
        return MAP_SHIFT1Status;
    }

    public void setMAP_SHIFT1Status(Boolean MAP_SHIFT1Status) {
        this.MAP_SHIFT1Status = MAP_SHIFT1Status;
    }

    public Boolean getSheet1Status() {
        return Sheet1Status;
    }

    public void setSheet1Status(Boolean sheet1Status) {
        Sheet1Status = sheet1Status;
    }

    public Boolean getSheet2Status() {
        return Sheet2Status;
    }

    public void setSheet2Status(Boolean sheet2Status) {
        Sheet2Status = sheet2Status;
    }
}
