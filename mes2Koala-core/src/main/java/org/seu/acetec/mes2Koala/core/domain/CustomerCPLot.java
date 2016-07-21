package org.seu.acetec.mes2Koala.core.domain;

import javax.persistence.*;

import java.util.List;

/**
 * @author 阙宇翔
 * @version 2016/2/14
 */
@Entity
@Table(name = "E_CP_CUSTOMER_LOT")
@Access(AccessType.PROPERTY)
public class CustomerCPLot extends CustomerLot {

    private static final long serialVersionUID = -7842802453042830808L;



    private String packageLot; //封装厂批号

    private String maskName;  // MASK_NAME

    private String size;      // SIZE 晶圆尺寸

    private CPInfo cpInfo;

    private String materialType; // 内部物料类型

    private String waferLot; // 晶圆批号

    private List<CPLot> cpLots;

    private List<CPCustomerWafer> cpCustomerWafers;   // inn

    @OneToMany(mappedBy = "customerCPLot", cascade = CascadeType.ALL)
    public List<CPCustomerWafer> getCpCustomerWafers() {
        return cpCustomerWafers;
    }

    public void setCpCustomerWafers(List<CPCustomerWafer> cpCustomerWafers) {
        this.cpCustomerWafers = cpCustomerWafers;
    }

    @OneToMany(mappedBy = "customerCPLot")
    public List<CPLot> getCpLots() {
        return cpLots;
    }

    public void setCpLots(List<CPLot> cpLots) {
        this.cpLots = cpLots;
    }

    @ManyToOne
    @JoinColumn(name = "CP_INFO_ID", referencedColumnName = "ID")
    public CPInfo getCpInfo() {
        return cpInfo;
    }

    public void setCpInfo(CPInfo cpInfo) {
        this.cpInfo = cpInfo;
    }

    public String getMaskName() {
        return maskName;
    }

    public void setMaskName(String maskName) {
        this.maskName = maskName;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getWaferLot() {
        return waferLot;
    }

    public void setWaferLot(String waferLot) {
        this.waferLot = waferLot;
    }

    @Override
    public String[] businessKeys() {
        return new String[0];
    }

    public String getPackingLot() {
        return packageLot;
    }

    public void setPackingLot(String packageLot) {
        this.packageLot = packageLot;
    }

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }


    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;

        CustomerCPLot that = (CustomerCPLot) object;

        if (packageLot != null ? !packageLot.equals(that.packageLot) : that.packageLot != null) return false;
        if (maskName != null ? !maskName.equals(that.maskName) : that.maskName != null) return false;
        if (size != null ? !size.equals(that.size) : that.size != null) return false;
        if (cpInfo != null ? !cpInfo.equals(that.cpInfo) : that.cpInfo != null) return false;
        if (materialType != null ? !materialType.equals(that.materialType) : that.materialType != null) return false;
        if (waferLot != null ? !waferLot.equals(that.waferLot) : that.waferLot != null) return false;
        if (cpLots != null ? !cpLots.equals(that.cpLots) : that.cpLots != null) return false;
        return cpCustomerWafers != null ? cpCustomerWafers.equals(that.cpCustomerWafers) : that.cpCustomerWafers == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (packageLot != null ? packageLot.hashCode() : 0);
        result = 31 * result + (maskName != null ? maskName.hashCode() : 0);
        result = 31 * result + (size != null ? size.hashCode() : 0);
        result = 31 * result + (cpInfo != null ? cpInfo.hashCode() : 0);
        result = 31 * result + (materialType != null ? materialType.hashCode() : 0);
        result = 31 * result + (waferLot != null ? waferLot.hashCode() : 0);
        result = 31 * result + (cpLots != null ? cpLots.hashCode() : 0);
        result = 31 * result + (cpCustomerWafers != null ? cpCustomerWafers.hashCode() : 0);
        return result;
    }
}