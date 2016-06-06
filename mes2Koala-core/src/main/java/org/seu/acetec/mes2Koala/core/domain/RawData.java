package org.seu.acetec.mes2Koala.core.domain;

import javax.persistence.*;
@Entity
@Table(name = "E_RAW_DATA")
@Access(AccessType.PROPERTY)
public class RawData extends MES2AbstractEntity {

	private static final long serialVersionUID = 5692637834911466869L;
	
	private String productID;
    private String notchSide;
    private String bindefinitionFile;
    private String gridXmax;
    private String fabSite;
    private String xDieSize;
    private String yDieSize;
    private String customerCodeID;
    //private String internalProductID;
    private InternalProduct internalProduct;
    
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "INTERNAL_PRODUCT_ID", referencedColumnName = "ID")
    public InternalProduct getInternalProduct() {
        return internalProduct;
    }

    public void setInternalProduct(InternalProduct internalProduct) {
        this.internalProduct = internalProduct;
    }

	public String getProductID() {
		return productID;
	}

	public void setProductID(String productID) {
		this.productID = productID;
	}

	public String getNotchSide() {
		return notchSide;
	}

	public void setNotchSide(String notchSide) {
		this.notchSide = notchSide;
	}

	public String getBindefinitionFile() {
		return bindefinitionFile;
	}

	public void setBindefinitionFile(String bindefinitionFile) {
		this.bindefinitionFile = bindefinitionFile;
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

	public String getCustomerCodeID() {
		return customerCodeID;
	}

	public void setCustomerCodeID(String customerCodeID) {
		this.customerCodeID = customerCodeID;
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

        RawData rawData = (RawData) o;

        if (productID != null ? !productID.equals(rawData.productID) : rawData.productID != null) return false;
        if (notchSide != null ? !notchSide.equals(rawData.notchSide) : rawData.notchSide != null) return false;
        if (bindefinitionFile != null ? !bindefinitionFile.equals(rawData.bindefinitionFile) : rawData.bindefinitionFile != null) 
        	return false;
        if (gridXmax != null ? !gridXmax.equals(rawData.gridXmax) : rawData.gridXmax != null)
            return false;
        if (fabSite != null ? !fabSite.equals(rawData.fabSite) : rawData.fabSite != null) return false;
        if (xDieSize != null ? !xDieSize.equals(rawData.xDieSize) : rawData.xDieSize != null) return false;
        if (yDieSize != null ? !yDieSize.equals(rawData.yDieSize) : rawData.yDieSize != null) return false;
        return customerCodeID != null ? customerCodeID.equals(rawData.customerCodeID) : rawData.customerCodeID == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (productID != null ? productID.hashCode() : 0);
        result = 31 * result + (notchSide != null ? notchSide.hashCode() : 0);
        result = 31 * result + (bindefinitionFile != null ? bindefinitionFile.hashCode() : 0);
        result = 31 * result + (gridXmax != null ? gridXmax.hashCode() : 0);
        result = 31 * result + (fabSite != null ? fabSite.hashCode() : 0);
        result = 31 * result + (xDieSize != null ? xDieSize.hashCode() : 0);
        result = 31 * result + (yDieSize != null ? yDieSize.hashCode() : 0);
        result = 31 * result + (customerCodeID != null ? customerCodeID.hashCode() : 0);
        return result;
    }

}
