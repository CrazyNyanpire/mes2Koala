package org.seu.acetec.mes2Koala.core.domain;

import javax.persistence.*;

import org.seu.acetec.mes2Koala.core.enums.CPWaferCheck;
import org.seu.acetec.mes2Koala.core.enums.CPWaferState;

/**
 * @author yuxiangque
 * @version 2016/3/30
 */
@Entity
@Table(name = "E_CP_WAFER")
@Access(AccessType.PROPERTY)
public class CPWafer extends MES2AbstractEntity {

    /**
	 * 
	 */
	private static final long serialVersionUID = -5127246892370712297L;

	private CPLot cpLot;

    private CPCustomerWafer cpCustomerWafer;

    private String map;

    private CPWaferState state;
    
    private String internalWaferCode;
    
    private String pass;
    
    private String fail;
    
    private String customerOffset;
    
    private String internalOffset;
    
    private CPWaferCheck cpWaferCheck;
    

    //@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "CP_CUSTOMER_WAFER_ID")
    public CPCustomerWafer getCpCustomerWafer() {
        return cpCustomerWafer;
    }

    public void setCpCustomerWafer(CPCustomerWafer cpCustomerWafer) {
        this.cpCustomerWafer = cpCustomerWafer;
    }

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "CP_LOT_ID")
    public CPLot getCpLot() {
        return cpLot;
    }

    public void setCpLot(CPLot cpLot) {
        this.cpLot = cpLot;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }


    public String getInternalWaferCode() {
		return internalWaferCode;
	}

	public void setInternalWaferCode(String internalWaferCode) {
		this.internalWaferCode = internalWaferCode;
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
	
	@Enumerated(EnumType.ORDINAL)
	public CPWaferState getState() {
		return state;
	}

	public void setState(CPWaferState state) {
		this.state = state;
	}
	
	@Enumerated(EnumType.ORDINAL)
	public CPWaferCheck getCpWaferCheck() {
		return cpWaferCheck;
	}

	public void setCpWaferCheck(CPWaferCheck cpWaferCheck) {
		this.cpWaferCheck = cpWaferCheck;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((cpCustomerWafer == null) ? 0 : cpCustomerWafer.hashCode());
		result = prime * result + ((cpLot == null) ? 0 : cpLot.hashCode());
		result = prime
				* result
				+ ((internalWaferCode == null) ? 0 : internalWaferCode
						.hashCode());
		result = prime * result + ((map == null) ? 0 : map.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		CPWafer other = (CPWafer) obj;
		if (cpCustomerWafer == null) {
			if (other.cpCustomerWafer != null)
				return false;
		} else if (!cpCustomerWafer.equals(other.cpCustomerWafer))
			return false;
		if (cpLot == null) {
			if (other.cpLot != null)
				return false;
		} else if (!cpLot.equals(other.cpLot))
			return false;
		if (internalWaferCode == null) {
			if (other.internalWaferCode != null)
				return false;
		} else if (!internalWaferCode.equals(other.internalWaferCode))
			return false;
		if (map == null) {
			if (other.map != null)
				return false;
		} else if (!map.equals(other.map))
			return false;
		return true;
	}

	@Override
    public String[] businessKeys() {
        return new String[0];
    }
}
