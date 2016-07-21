package org.seu.acetec.mes2Koala.core.domain;

import javax.persistence.*;

import org.seu.acetec.mes2Koala.core.enums.CPWaferCheck;
import org.seu.acetec.mes2Koala.core.enums.CPWaferState;

/**
 * @author harlow
 * @version 2016/6/8
 */
@Entity
@Table(name = "E_CP_WAFER_LOG")
@Access(AccessType.PROPERTY)
public class CPWaferLog extends MES2AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5127246892370712297L;

	private CPLot cpLot;

	private CPWafer cpWafer;

	private CPNode node;
	
    private String pass;
    
    private String fail;
    
    private String customerOffset;
    
    private String internalOffset;
    
    private CPWaferState state;
    
    private String remark;
    
    private String total;
    
    private String sblResult;

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinColumn(name = "CP_WAFER_ID")
	public CPWafer getCpWafer() {
		return cpWafer;
	}

	public void setCpWafer(CPWafer cpWafer) {
		this.cpWafer = cpWafer;
	}

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "CP_LOT_ID")
	public CPLot getCpLot() {
		return cpLot;
	}

	public void setCpLot(CPLot cpLot) {
		this.cpLot = cpLot;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getFail() {
		return fail;
	}

	public void setFail(String fail) {
		this.fail = fail;
	}

	public String getCustomerOffset() {
		return customerOffset;
	}

	public void setCustomerOffset(String customerOffset) {
		this.customerOffset = customerOffset;
	}

	public String getInternalOffset() {
		return internalOffset;
	}

	public void setInternalOffset(String internalOffset) {
		this.internalOffset = internalOffset;
	}

	public void setNode(CPNode node) {
		this.node = node;
	}

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinColumn(name = "CP_NODE_ID")
	public CPNode getNode() {
		return node;
	}

	@Enumerated(EnumType.ORDINAL)
	public CPWaferState getState() {
		return state;
	}

	public void setState(CPWaferState state) {
		this.state = state;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getSblResult() {
		return sblResult;
	}

	public void setSblResult(String sblResult) {
		this.sblResult = sblResult;
	}

	@Override
	public String[] businessKeys() {
		return new String[0];
	}
}
