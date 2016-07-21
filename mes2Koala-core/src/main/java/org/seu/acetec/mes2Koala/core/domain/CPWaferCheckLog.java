package org.seu.acetec.mes2Koala.core.domain;

import javax.persistence.*;

import org.seu.acetec.mes2Koala.core.enums.CPWaferCheck;
import org.seu.acetec.mes2Koala.core.enums.CPWaferState;

/**
 * @author harlow
 * @version 2016/6/2
 */
@Entity
@Table(name = "E_CP_WAFER_CHECK_LOG")
@Access(AccessType.PROPERTY)
public class CPWaferCheckLog extends MES2AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5127246892370712297L;

	private CPLot cpLot;

	private CPWafer cpWafer;
	
	private String node;
	
	private CPNode cpNode;

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinColumn(name = "CP_WAFER_ID")
	public CPWafer getCpWafer() {
		return cpWafer;
	}

	public void setCpWafer(CPWafer cpWafer) {
		this.cpWafer = cpWafer;
	}

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "CP_LOT_ID")
	public CPLot getCpLot() {
		return cpLot;
	}

	public void setCpLot(CPLot cpLot) {
		this.cpLot = cpLot;
	}

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "CP_NODE_ID")
	public CPNode getCpNode() {
		return cpNode;
	}

	public void setCpNode(CPNode cpNode) {
		this.cpNode = cpNode;
	}

	@Override
	public String[] businessKeys() {
		return new String[0];
	}
}
