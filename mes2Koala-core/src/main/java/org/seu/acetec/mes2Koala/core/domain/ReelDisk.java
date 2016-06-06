package org.seu.acetec.mes2Koala.core.domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "E_ReelDisk")
@Access(AccessType.PROPERTY)
public class ReelDisk extends MES2AbstractEntity {

    public static final String REEL_DISK_QUALITY_PASS = "pass";
    public static final String REEL_DISK_QUALITY_FAIL = "fail";
    public static final String REEL_DISK_QUALITY_LAT = "lat";
    public static final String REEL_DISK_UNAPPROVED = "Unapproved";
    public static final String REEL_DISK_APPROVED = "approved";
    public static final String REEL_DISK_STATUS_IN_HOUSE = "在库";
    public static final String REEL_DISK_STATUS_OUT_HOUSE = "出库";
    public static final String REEL_DISK_STATUS_REWORK = "重工";
    public static final String REEL_DISK_YES = "是";
    public static final String REEL_DISK_NO= "否";
    public static final String REEL_REMARK_LITTLE_SAMPLE= "小样品出货";

    
    private String reelCode;
    private int quantity;
    private String partNumber;
    private Date packagingTime;
    private String dateCode;

    private FTLot ftLot;
    private FTLot combinedLot;
    // private List<ReelDisk> parentIntegrationIds = new ArrayList<ReelDisk>();
    private ReelDisk parentSeparation;
    private String parentIntegrationIds;
    private String fromReelCode;

    private String quality;
    private String failInfo;
    private FTResult ftResult;

    private String reelTime;
    private String wflot;
    private String time;
    private String poNumber;
    private String poNumberBox;
    private String isFull;

    private String status; // 在库、出库、借料、丢料、重工
    private Date storageTime;// 进入中转库时间
    private String specialSign;// 特殊标示 取消，降级，制定出货，报废
    private String specialSignRemark;
    private String approve;

    private String holdSign;// 是，否
    private String holdSignRemark;
    private String unHoldSignRemark;
    private Date holdDate;
    private Date unHoldDate;
    private String holdPerson;
    private String unHoldPerson;
    private String latSign;
    private Date latPackageTime;
    private String latPackageNo;
    private String wmsStorageId;
    private String remark;
    
    @Override
    public String[] businessKeys() {
        // TODO Auto-generated method stub
        return null;
    }

    public String getReelCode() {
        return reelCode;
    }

    public void setReelCode(String reelCode) {
        this.reelCode = reelCode;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    @Temporal(value = TemporalType.TIMESTAMP)
    public Date getPackagingTime() {
        return packagingTime;
    }

    public void setPackagingTime(Date packagingTime) {
        this.packagingTime = packagingTime;
    }

    public String getDateCode() {
        return dateCode;
    }

    public void setDateCode(String dateCode) {
        this.dateCode = dateCode;
    }

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "Lot_ID", referencedColumnName = "ID")
    public FTLot getFtLot() {
        return ftLot;
    }

    public void setFtLot(FTLot ftLot) {
        this.ftLot = ftLot;
    }

    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "combinedLot_ID", referencedColumnName = "ID")
    public FTLot getCombinedLot() {
        return combinedLot;
    }

    public void setCombinedLot(FTLot combinedLot) {
        this.combinedLot = combinedLot;
    }

    /*
     * @OneToMany(cascade={CascadeType.REFRESH})
     *
     * @JoinColumn(name="sonIntegration") public List<ReelDisk>
     * getParentIntegrationIds() { return parentIntegrationIds; }
     *
     *
     * public void setParentIntegrationIds(List<ReelDisk> parentIntegrationIds) {
     * this.parentIntegrationIds = parentIntegrationIds; }
     */
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "parentSeparation", referencedColumnName = "ID")
    public ReelDisk getParentSeparation() {
        return parentSeparation;
    }

    public void setParentSeparation(ReelDisk parentSeparation) {
        this.parentSeparation = parentSeparation;
    }

	/*
     * public String getParentIntegrationIds() { return parentIntegrationIds; }
	 * 
	 * 
	 * public void setParentIntegrationIds(String parentIntegrationIds) {
	 * this.parentIntegrationIds = parentIntegrationIds; }
	 */

    public String getReelTime() {
        return reelTime;
    }

    public void setReelTime(String reelTime) {
        this.reelTime = reelTime;
    }

    public String getWflot() {
        return wflot;
    }

    public void setWflot(String wflot) {
        this.wflot = wflot;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPoNumber() {
        return poNumber;
    }

    public void setPoNumber(String poNumber) {
        this.poNumber = poNumber;
    }

    public String getPoNumberBox() {
        return poNumberBox;
    }

    public void setPoNumberBox(String poNumberBox) {
        this.poNumberBox = poNumberBox;
    }

    public String getIsFull() {
        return isFull;
    }

    public void setIsFull(String isFull) {
        this.isFull = isFull;
    }

    public String getFromReelCode() {
        return fromReelCode;
    }

    public void setFromReelCode(String fromReelCode) {
        this.fromReelCode = fromReelCode;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getFailInfo() {
        return failInfo;
    }

    public void setFailInfo(String failInfo) {
        this.failInfo = failInfo;
    }

    @OneToOne(cascade = {CascadeType.REFRESH, CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "ftResult_ID", referencedColumnName = "ID")
    public FTResult getFtResult() {
        return ftResult;
    }

    public void setFtResult(FTResult ftResult) {
        this.ftResult = ftResult;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getStorageTime() {
        return storageTime;
    }

    public void setStorageTime(Date storageTime) {
        this.storageTime = storageTime;
    }

    public String getSpecialSign() {
        return specialSign;
    }

    public void setSpecialSign(String specialSign) {
        this.specialSign = specialSign;
    }

    public String getSpecialSignRemark() {
        return specialSignRemark;
    }

    public void setSpecialSignRemark(String specialSignRemark) {
        this.specialSignRemark = specialSignRemark;
    }

    public String getApprove() {
        return approve;
    }

    public void setApprove(String approve) {
        this.approve = approve;
    }

    public String getHoldSign() {
        return holdSign;
    }

    public void setHoldSign(String holdSign) {
        this.holdSign = holdSign;
    }

    public String getHoldSignRemark() {
        return holdSignRemark;
    }

    public void setHoldSignRemark(String holdSignRemark) {
        this.holdSignRemark = holdSignRemark;
    }

    public Date getHoldDate() {
        return holdDate;
    }

    public void setHoldDate(Date holdDate) {
        this.holdDate = holdDate;
    }

    public String getUnHoldSignRemark() {
        return unHoldSignRemark;
    }

    public void setUnHoldSignRemark(String unHoldSignRemark) {
        this.unHoldSignRemark = unHoldSignRemark;
    }

    public Date getUnHoldDate() {
        return unHoldDate;
    }

    public void setUnHoldDate(Date unHoldDate) {
        this.unHoldDate = unHoldDate;
    }

    public String getHoldPerson() {
        return holdPerson;
    }

    public void setHoldPerson(String holdPerson) {
        this.holdPerson = holdPerson;
    }

    public String getUnHoldPerson() {
        return unHoldPerson;
    }

    public void setUnHoldPerson(String unHoldPerson) {
        this.unHoldPerson = unHoldPerson;
    }

    public String getLatSign() {
        return latSign;
    }

    public void setLatSign(String latSign) {
        this.latSign = latSign;
    }

    public Date getLatPackageTime() {
        return latPackageTime;
    }

    public void setLatPackageTime(Date latPackageTime) {
        this.latPackageTime = latPackageTime;
    }

    public String getLatPackageNo() {
        return latPackageNo;
    }

    public void setLatPackageNo(String latPackageNo) {
        this.latPackageNo = latPackageNo;
    }

	public String getWmsStorageId() {
		return wmsStorageId;
	}

	public void setWmsStorageId(String wmsStorageId) {
		this.wmsStorageId = wmsStorageId;
	}

    public String getParentIntegrationIds() {
        return parentIntegrationIds;
    }

    public void setParentIntegrationIds(String parentIntegrationIds) {
        this.parentIntegrationIds = parentIntegrationIds;
    }

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
