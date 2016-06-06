package org.seu.acetec.mes2Koala.core.domain;

import javax.persistence.*;

import java.util.List;

/**
 * @author 阙宇翔
 * @version 2016/2/13
 */
@Entity
@Table(name = "E_CP_LOT")
@Access(AccessType.PROPERTY)
public class CPLot extends InternalLot {

    private static final long serialVersionUID = 4996141239079965390L;

    private String diskContent;

    private Long quantity;

    private List<CPWafer> cpWafers;

    private CPProcess cpProcess;

    private CustomerCPLot customerCPLot;
    
    private Boolean showFlag;

    private CPRuncard cpRuncard;


    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "CUSTOMER_CP_LOT_ID", referencedColumnName = "ID")
    public CustomerCPLot getCustomerCPLot() {
        return customerCPLot;
    }

    public void setCustomerCPLot(CustomerCPLot customerCPLot) {
        this.customerCPLot = customerCPLot;
    }

    @OneToOne(mappedBy = "cpLot",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    public CPProcess getCpProcess() {
        return cpProcess;
    }

    public void setCpProcess(CPProcess cpProcess) {
        this.cpProcess = cpProcess;
    }

    @OneToMany(mappedBy = "cpLot", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    //@OneToMany(mappedBy = "cpLot", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    public List<CPWafer> getCpWafers() {
        return cpWafers;
    }

    public void setCpWafers(List<CPWafer> cpWafers) {
        this.cpWafers = cpWafers;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getDiskContent() {
        return diskContent;
    }

    public void setDiskContent(String diskContent) {
        this.diskContent = diskContent;
    }

    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "cpLot")
    public CPRuncard getCpRuncard() {
        return cpRuncard;
    }

    public void setCpRuncard(CPRuncard cpRuncard) {
        this.cpRuncard = cpRuncard;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        CPLot cpLot = (CPLot) o;

        if (diskContent != null ? !diskContent.equals(cpLot.diskContent) : cpLot.diskContent != null) return false;
        return quantity != null ? quantity.equals(cpLot.quantity) : cpLot.quantity == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (diskContent != null ? diskContent.hashCode() : 0);
        result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
        return result;
    }

    @Override
    public String[] businessKeys() {
        return new String[0];
    }

	public Boolean getShowFlag() {
		return showFlag;
	}

	public void setShowFlag(Boolean showFlag) {
		this.showFlag = showFlag;
	}

}
