package org.seu.acetec.mes2Koala.core.domain;

import javax.persistence.*;

@Entity
@Table(name = "E_EQC_Setting")
@Access(AccessType.PROPERTY)
public class EQCSetting extends MES2AbstractEntity {

    private static final long serialVersionUID = -9213808911061616878L;

    // EQC数量
    private int Qty;

    // 来料数量下限
    private int lowerLimit;

    // 来料数量上限
    private int upperLimit;
    
    //站点
    private String nodeName;
    
    private InternalProduct internalProduct;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "Product_ID")
    public InternalProduct getInternalProduct() {
        return internalProduct;
    }

    public void setInternalProduct(InternalProduct internalProduct) {
        this.internalProduct = internalProduct;
    }

    public int getQty() {
        return Qty;
    }

    public void setQty(int qty) {
        Qty = qty;
    }

    public int getLowerLimit() {
        return lowerLimit;
    }

    public void setLowerLimit(int lowerLimit) {
        this.lowerLimit = lowerLimit;
    }

    public int getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(int upperLimit) {
        this.upperLimit = upperLimit;
    }

    public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
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

        EQCSetting that = (EQCSetting) o;

        if (Qty != that.Qty) return false;
        if (lowerLimit != that.lowerLimit) return false;
        if (upperLimit != that.upperLimit) return false;
        return internalProduct != null ? internalProduct.equals(that.internalProduct) : that.internalProduct == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + Qty;
        result = 31 * result + lowerLimit;
        result = 31 * result + upperLimit;
        result = 31 * result + (internalProduct != null ? internalProduct.hashCode() : 0);
        return result;
    }
}
