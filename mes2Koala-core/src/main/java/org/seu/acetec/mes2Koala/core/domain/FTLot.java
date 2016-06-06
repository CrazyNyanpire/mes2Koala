package org.seu.acetec.mes2Koala.core.domain;

import javax.persistence.*;

/**
 * FT批次信息实体，用于存储借料、丢料、数量、类型、状态等区别于CP的特有信息
 *
 * @author Howard
 * @version v1.0
 * @lastModifyDate 2016.01.05
 */
@Entity
@Table(name = "E_FT_LOT")
@Access(AccessType.PROPERTY)
public class FTLot extends InternalLot {

    private int borrow;
    //	private boolean Is_Enable;	//?
    private int loss;
    //	private String packageType;
    private Long qty;
    private String type;

//	private Sample sample;
//	private FTHold ftHold;
//	private FTLot parentIntegration;
//	private FTLot parentSeparation;

    // 小样出货信息
    private int pass;
    private int fail;
    private int lat;
    private int other;

    private FTProcess ftProcess;
    private CustomerFTLot customerFTLot;

    private FTRuncard ftRuncard;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "CUSTOMER_FT_LOT_ID", referencedColumnName = "ID")
    public CustomerFTLot getCustomerFTLot() {
        return customerFTLot;
    }

    public void setCustomerFTLot(CustomerFTLot customerFTLot) {
        this.customerFTLot = customerFTLot;
    }

    @OneToOne(mappedBy = "ftLot", fetch = FetchType.EAGER)
    public FTProcess getFtProcess() {
        return ftProcess;
    }

    public void setFtProcess(FTProcess ftProcess) {
        this.ftProcess = ftProcess;
    }

    public int getBorrow() {
        return borrow;
    }

    public void setBorrow(int borrow) {
        this.borrow = borrow;
    }

    public int getLoss() {
        return loss;
    }

    public void setLoss(int loss) {
        this.loss = loss;
    }

    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPass() {
        return pass;
    }

    public void setPass(int pass) {
        this.pass = pass;
    }

    public int getLat() {
        return lat;
    }

    public void setLat(int lat) {
        this.lat = lat;
    }

    public int getFail() {
        return fail;
    }

    public void setFail(int fail) {
        this.fail = fail;
    }

    public int getOther() {
        return other;
    }

    public void setOther(int other) {
        this.other = other;
    }
    
    @Transient
    public FTInfo getFtInfo() {
    	try {
    		return this.customerFTLot.getFtInfo();
		} catch (NullPointerException e) {
			e.printStackTrace();
			return null;
		}
    }
    
    @Transient
    public Customer getCustomerDirect() {
    	FTInfo ftInfo = getFtInfo();
    	if ( ftInfo != null ) {
    		return ftInfo.getCustomerDirect();
    	} else {
    		return null;
    	}
    }
    
    @Transient
    public String getCustomerProductNumber() {
    	FTInfo ftInfo = getFtInfo();
    	if ( ftInfo != null ) {
    		return ftInfo.getCustomerProductNumber();
    	}else {
    		return null;
    	}
    }
    
    @Transient
    public String getCustomerProductRevision() {
    	FTInfo ftInfo = getFtInfo();
    	if ( ftInfo != null ) {
    		return ftInfo.getCustomerProductRevision();
    	} else { 
    		return null;
    	}
    }
    
    @Transient
    public String getPackageSize() {
    	FTInfo ftInfo = getFtInfo();
    	if ( ftInfo != null ) {
    		return ftInfo.getSize();
    	} else { 
    		return null;
    	}
    }


    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "ftLot")
    public FTRuncard getFtRuncard() {
        return ftRuncard;
    }

    public void setFtRuncard(FTRuncard ftRuncard) {
        this.ftRuncard = ftRuncard;
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

        FTLot ftLot = (FTLot) o;

        if (borrow != ftLot.borrow) return false;
        if (loss != ftLot.loss) return false;
        if (pass != ftLot.pass) return false;
        if (fail != ftLot.fail) return false;
        if (lat != ftLot.lat) return false;
        if (other != ftLot.other) return false;
        if (qty != null ? !qty.equals(ftLot.qty) : ftLot.qty != null) return false;
        return type != null ? type.equals(ftLot.type) : ftLot.type == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + borrow;
        result = 31 * result + loss;
        result = 31 * result + (qty != null ? qty.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + pass;
        result = 31 * result + fail;
        result = 31 * result + lat;
        result = 31 * result + other;
        return result;
    }
}
