package org.seu.acetec.mes2Koala.core.domain;

import javax.persistence.*;

/**
 * Created by LCN on 2016/4/27.
 */

@Entity
@Table(name = "e_cp_special_form")
@Access(AccessType.PROPERTY)
public class CPSpecialFormTemplate extends MES2AbstractEntity {

    private String MCP_Cover1Sheet;

    private Boolean MCP_Cover1SheetStatus = false;

    private String Sheet1;
    private Boolean Sheet1Status = false;

    private String Sheet2;
    private Boolean Sheet2Status = false;

    private String CP1Sheet;
    private Boolean CP1SheetStatus = false;

    private String CP2Sheet;
    private Boolean CP2SheetStatus = false;

    private String Map_Shift1Sheet;
    private Boolean Map_Shift1SheetStatus = false;


    @Column(columnDefinition = "text")
    @Basic(fetch = FetchType.LAZY)
    public String getMCP_Cover1Sheet() {
        return MCP_Cover1Sheet;
    }

    public void setMCP_Cover1Sheet(String MCP_Cover1Sheet) {
        this.MCP_Cover1Sheet = MCP_Cover1Sheet;
    }

    @Column(columnDefinition = "text")
    @Basic(fetch = FetchType.LAZY)
    public String getSheet1() {
        return Sheet1;
    }


    public void setSheet1(String sheet1) {
        Sheet1 = sheet1;
    }

    @Column(columnDefinition = "text")
    @Basic(fetch = FetchType.LAZY)
    public String getSheet2() {
        return Sheet2;
    }


    public void setSheet2(String sheet2) {
        Sheet2 = sheet2;
    }


    @Column(columnDefinition = "text")
    @Basic(fetch = FetchType.LAZY)
    public String getCP1Sheet() {
        return CP1Sheet;
    }

    public void setCP1Sheet(String CP1Sheet) {
        this.CP1Sheet = CP1Sheet;
    }


    @Column(columnDefinition = "text")
    @Basic(fetch = FetchType.LAZY)
    public String getCP2Sheet() {
        return CP2Sheet;
    }

    public void setCP2Sheet(String CP2Sheet) {
        this.CP2Sheet = CP2Sheet;
    }


    @Column(columnDefinition = "text")
    @Basic(fetch = FetchType.LAZY)
    public String getMap_Shift1Sheet() {
        return Map_Shift1Sheet;
    }

    public void setMap_Shift1Sheet(String map_Shift1Sheet) {
        Map_Shift1Sheet = map_Shift1Sheet;
    }


    public Boolean getMCP_Cover1SheetStatus() {
        return MCP_Cover1SheetStatus;
    }

    public void setMCP_Cover1SheetStatus(Boolean MCP_Cover1SheetStatus) {
        this.MCP_Cover1SheetStatus = MCP_Cover1SheetStatus;
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

    public Boolean getMap_Shift1SheetStatus() {
        return Map_Shift1SheetStatus;
    }

    public void setMap_Shift1SheetStatus(Boolean map_Shift1SheetStatus) {
        Map_Shift1SheetStatus = map_Shift1SheetStatus;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        CPSpecialFormTemplate that = (CPSpecialFormTemplate) o;

        if (MCP_Cover1Sheet != null ? !MCP_Cover1Sheet.equals(that.MCP_Cover1Sheet) : that.MCP_Cover1Sheet != null)
            return false;
        if (MCP_Cover1SheetStatus != null ? !MCP_Cover1SheetStatus.equals(that.MCP_Cover1SheetStatus) : that.MCP_Cover1SheetStatus != null)
            return false;
        if (Sheet1 != null ? !Sheet1.equals(that.Sheet1) : that.Sheet1 != null) return false;
        if (Sheet1Status != null ? !Sheet1Status.equals(that.Sheet1Status) : that.Sheet1Status != null) return false;
        if (Sheet2 != null ? !Sheet2.equals(that.Sheet2) : that.Sheet2 != null) return false;
        if (Sheet2Status != null ? !Sheet2Status.equals(that.Sheet2Status) : that.Sheet2Status != null) return false;
        if (CP1Sheet != null ? !CP1Sheet.equals(that.CP1Sheet) : that.CP1Sheet != null) return false;
        if (CP1SheetStatus != null ? !CP1SheetStatus.equals(that.CP1SheetStatus) : that.CP1SheetStatus != null)
            return false;
        if (CP2Sheet != null ? !CP2Sheet.equals(that.CP2Sheet) : that.CP2Sheet != null) return false;
        if (CP2SheetStatus != null ? !CP2SheetStatus.equals(that.CP2SheetStatus) : that.CP2SheetStatus != null)
            return false;
        if (Map_Shift1Sheet != null ? !Map_Shift1Sheet.equals(that.Map_Shift1Sheet) : that.Map_Shift1Sheet != null)
            return false;
        return Map_Shift1SheetStatus != null ? Map_Shift1SheetStatus.equals(that.Map_Shift1SheetStatus) : that.Map_Shift1SheetStatus == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (MCP_Cover1Sheet != null ? MCP_Cover1Sheet.hashCode() : 0);
        result = 31 * result + (MCP_Cover1SheetStatus != null ? MCP_Cover1SheetStatus.hashCode() : 0);
        result = 31 * result + (Sheet1 != null ? Sheet1.hashCode() : 0);
        result = 31 * result + (Sheet1Status != null ? Sheet1Status.hashCode() : 0);
        result = 31 * result + (Sheet2 != null ? Sheet2.hashCode() : 0);
        result = 31 * result + (Sheet2Status != null ? Sheet2Status.hashCode() : 0);
        result = 31 * result + (CP1Sheet != null ? CP1Sheet.hashCode() : 0);
        result = 31 * result + (CP1SheetStatus != null ? CP1SheetStatus.hashCode() : 0);
        result = 31 * result + (CP2Sheet != null ? CP2Sheet.hashCode() : 0);
        result = 31 * result + (CP2SheetStatus != null ? CP2SheetStatus.hashCode() : 0);
        result = 31 * result + (Map_Shift1Sheet != null ? Map_Shift1Sheet.hashCode() : 0);
        result = 31 * result + (Map_Shift1SheetStatus != null ? Map_Shift1SheetStatus.hashCode() : 0);
        return result;
    }

    @Override
    public String[] businessKeys() {
        return new String[0];
    }
}
