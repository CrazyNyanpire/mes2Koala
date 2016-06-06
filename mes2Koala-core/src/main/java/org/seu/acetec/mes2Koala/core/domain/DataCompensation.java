package org.seu.acetec.mes2Koala.core.domain;

import javax.persistence.*;
@Entity
@Table(name = "E_DATA_Compensation")
@Access(AccessType.PROPERTY)
public class DataCompensation extends MES2AbstractEntity {

	private static final long serialVersionUID = 5692637834911466869L;
	
	private String lotID;
	private String waferID;
	private String smicProdID;
	private String testProgram;
	private String testerID;
	private String operatorID;
	private String startTime;
	private String endTime;
	private String notchSide;
	private String sortID;
	private String binDefinitionFile;
	private String gridXmax;
	private String gridYmax;
	private String fabSite;
	private String testSite;
	private String probeCardID;
	private String loadBoardID;
	private String romCode;
    private String xDieSize;
    private String yDieSize;
    private String xStreet;
    private String yStreet;
    private String customerCode;
    private String customerPartID;
    private String rawData;

	public String getNotchSide() {
		return notchSide;
	}

	public void setNotchSide(String notchSide) {
		this.notchSide = notchSide;
	}

	public String getGridXmax() {
		return gridXmax;
	}

	public void setGridXmax(String gridXmax) {
		this.gridXmax = gridXmax;
	}

	public String getxDieSize() {
		return xDieSize;
	}

	public void setxDieSize(String xDieSize) {
		this.xDieSize = xDieSize;
	}

	public String getFabSite() {
		return fabSite;
	}

	public void setFabSite(String fabSite) {
		this.fabSite = fabSite;
	}

	public String getyDieSize() {
		return yDieSize;
	}

	public void setyDieSize(String yDieSize) {
		this.yDieSize = yDieSize;
	}

    @Override
    public String[] businessKeys() {
        return new String[0];
    }

	public String getLotID() {
		return lotID;
	}

	public void setLotID(String lotID) {
		this.lotID = lotID;
	}

	public String getWaferID() {
		return waferID;
	}

	public void setWaferID(String waferID) {
		this.waferID = waferID;
	}

	public String getSmicProdID() {
		return smicProdID;
	}

	public void setSmicProdID(String smicProdID) {
		this.smicProdID = smicProdID;
	}

	public String getTesterID() {
		return testerID;
	}

	public void setTesterID(String testerID) {
		this.testerID = testerID;
	}

	public String getOperatorID() {
		return operatorID;
	}

	public void setOperatorID(String operatorID) {
		this.operatorID = operatorID;
	}

	public String getTestProgram() {
		return testProgram;
	}

	public void setTestProgram(String testProgram) {
		this.testProgram = testProgram;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getBinDefinitionFile() {
		return binDefinitionFile;
	}

	public void setBinDefinitionFile(String binDefinitionFile) {
		this.binDefinitionFile = binDefinitionFile;
	}

	public String getSortID() {
		return sortID;
	}

	public void setSortID(String sortID) {
		this.sortID = sortID;
	}

	public String getGridYmax() {
		return gridYmax;
	}

	public void setGridYmax(String gridYmax) {
		this.gridYmax = gridYmax;
	}

	public String getTestSite() {
		return testSite;
	}

	public void setTestSite(String testSite) {
		this.testSite = testSite;
	}

	public String getProbeCardID() {
		return probeCardID;
	}

	public void setProbeCardID(String probeCardID) {
		this.probeCardID = probeCardID;
	}

	public String getRomCode() {
		return romCode;
	}

	public void setRomCode(String romCode) {
		this.romCode = romCode;
	}

	public String getLoadBoardID() {
		return loadBoardID;
	}

	public void setLoadBoardID(String loadBoardID) {
		this.loadBoardID = loadBoardID;
	}

	public String getxStreet() {
		return xStreet;
	}

	public void setxStreet(String xStreet) {
		this.xStreet = xStreet;
	}

	public String getyStreet() {
		return yStreet;
	}

	public void setyStreet(String yStreet) {
		this.yStreet = yStreet;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getCustomerPartID() {
		return customerPartID;
	}

	public void setCustomerPartID(String customerPartID) {
		this.customerPartID = customerPartID;
	}

	public String getRawData() {
		return rawData;
	}

	public void setRawData(String rawData) {
		this.rawData = rawData;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        DataCompensation dataCompensation = (DataCompensation) o;

        if (lotID != null ? !lotID.equals(dataCompensation.lotID) : dataCompensation.lotID != null) return false;
        if (waferID != null ? !waferID.equals(dataCompensation.waferID) : dataCompensation.waferID != null) return false;
        if (smicProdID != null ? !smicProdID.equals(dataCompensation.smicProdID) : dataCompensation.smicProdID != null) return false;
        if (testProgram != null ? !testProgram.equals(dataCompensation.testProgram) : dataCompensation.testProgram != null) return false;
        if (testerID != null ? !testerID.equals(dataCompensation.testerID) : dataCompensation.testerID != null) return false;
        if (operatorID != null ? !operatorID.equals(dataCompensation.operatorID) : dataCompensation.operatorID != null) return false;
        if (startTime != null ? !startTime.equals(dataCompensation.startTime) : dataCompensation.startTime != null) return false;
        if (endTime != null ? !endTime.equals(dataCompensation.endTime) : dataCompensation.endTime != null) return false;
        if (notchSide != null ? !notchSide.equals(dataCompensation.notchSide) : dataCompensation.notchSide != null) return false;
        if (sortID != null ? !sortID.equals(dataCompensation.sortID) : dataCompensation.sortID != null) return false;
        if (binDefinitionFile != null ? !binDefinitionFile.equals(dataCompensation.binDefinitionFile) : dataCompensation.binDefinitionFile != null) 
        	return false;
        if (gridXmax != null ? !gridXmax.equals(dataCompensation.gridXmax) : dataCompensation.gridXmax != null) return false;
        if (fabSite != null ? !fabSite.equals(dataCompensation.fabSite) : dataCompensation.fabSite != null) return false;
        if (testSite != null ? !testSite.equals(dataCompensation.testSite) : dataCompensation.testSite != null) return false;
        if (probeCardID != null ? !probeCardID.equals(dataCompensation.probeCardID) : dataCompensation.probeCardID != null) return false;
        if (loadBoardID != null ? !loadBoardID.equals(dataCompensation.loadBoardID) : dataCompensation.loadBoardID != null) return false;
        if (romCode != null ? !romCode.equals(dataCompensation.romCode) : dataCompensation.romCode != null) return false;
        if (xDieSize != null ? !xDieSize.equals(dataCompensation.xDieSize) : dataCompensation.xDieSize != null) return false;
        if (yDieSize != null ? !yDieSize.equals(dataCompensation.yDieSize) : dataCompensation.yDieSize != null) return false;
        if (xStreet != null ? !xStreet.equals(dataCompensation.xStreet) : dataCompensation.xStreet != null) return false;
        if (yStreet != null ? !yStreet.equals(dataCompensation.yStreet) : dataCompensation.yStreet != null) return false;
        if (customerCode != null ? !customerCode.equals(dataCompensation.customerCode) : dataCompensation.customerCode != null) return false;
        if (rawData != null ? !rawData.equals(dataCompensation.rawData) : dataCompensation.rawData != null) return false;
        return customerPartID != null ? customerPartID.equals(dataCompensation.customerPartID) : dataCompensation.customerPartID == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (lotID != null ? lotID.hashCode() : 0);
        result = 31 * result + (waferID != null ? waferID.hashCode() : 0);
        result = 31 * result + (smicProdID != null ? smicProdID.hashCode() : 0);
        result = 31 * result + (testProgram != null ? testProgram.hashCode() : 0);
        result = 31 * result + (testerID != null ? testerID.hashCode() : 0);
        result = 31 * result + (operatorID != null ? operatorID.hashCode() : 0);
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        result = 31 * result + (notchSide != null ? notchSide.hashCode() : 0);
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        result = 31 * result + (sortID != null ? sortID.hashCode() : 0);
        result = 31 * result + (binDefinitionFile != null ? binDefinitionFile.hashCode() : 0);
        result = 31 * result + (gridXmax != null ? gridXmax.hashCode() : 0);
        result = 31 * result + (gridYmax != null ? gridYmax.hashCode() : 0);
        result = 31 * result + (fabSite != null ? fabSite.hashCode() : 0);
        result = 31 * result + (testSite != null ? testSite.hashCode() : 0);
        result = 31 * result + (probeCardID != null ? probeCardID.hashCode() : 0);
        result = 31 * result + (loadBoardID != null ? loadBoardID.hashCode() : 0);
        result = 31 * result + (romCode != null ? romCode.hashCode() : 0);
        result = 31 * result + (xDieSize != null ? xDieSize.hashCode() : 0);
        result = 31 * result + (yDieSize != null ? yDieSize.hashCode() : 0);
        result = 31 * result + (xStreet != null ? xStreet.hashCode() : 0);
        result = 31 * result + (yStreet != null ? yStreet.hashCode() : 0);
        result = 31 * result + (customerCode != null ? customerCode.hashCode() : 0);
        result = 31 * result + (customerPartID != null ? customerPartID.hashCode() : 0);
        result = 31 * result + (rawData != null ? rawData.hashCode() : 0);
        return result;
    }
}
