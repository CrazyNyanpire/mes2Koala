package org.seu.acetec.mes2Koala.core.domain;

import javax.persistence.*;

/**
 * Bom单实体
 * 字段与Bom导入的excel文件的列名相对应；注释掉的代码是为了预防未来的需求变更，可以删除。
 *
 * @author yuxia Howard
 * @version v1.5
 * @lastModifyDate 2015.12.26
 */
@Entity
@Table(name = "E_BOM")
@Access(value = AccessType.PROPERTY)    //注解均置于属性名之上
public class BomTemplate extends MES2AbstractEntity {

    private static final long serialVersionUID = -4754627779545741860L;

    private InternalProduct internalProduct;
    //    private String level;
    private String bomId;
    private String modelNumber;
    private String number;
    //private String itemSubclass;
    private String revision;
    private String description;
    //private String lifecyclePhase;
    private String um;
    private String quantity;
    private String theoryQuantity;
    //    private String itemDescription;
    private String manufacturerName;
    //private String manufacturerPartNumber;
    //private String manufacturerPartLifecyclePhase;
    private String customerCode; // 冗余

/*    public String getLevel() {
        return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}*/

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    /**
     * 序号，用于工单导出时的条目排序。
     *
     * @return
     */
    public String getBomId() {
        return bomId;
    }

    public void setBomId(String bomId) {
        this.bomId = bomId;
    }

    /**
     * 对应于productType或客户产品型号
     *
     * @return
     */
    public String getModelNumber() {
        return modelNumber;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    public String getDescription() {
        return description;
    }

/*	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}*/

    public void setDescription(String description) {
        this.description = description;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    @Override
    public String[] businessKeys() {
        return new String[0];
    }

    /**
     * Qty
     */
    @Column(name = "QUANTITY")
    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    /**
     * TheoryQty
     */
    @Column(name = "THEORY_QUANTITY")
    public String getTheoryQuantity() {
        return theoryQuantity;
    }

    public void setTheoryQuantity(String theoryQuantity) {
        this.theoryQuantity = theoryQuantity;
    }

    /**
     * Revision
     */
    @Column(name = "REVISION")
    public String getRevision() {
        return revision;
    }

    public void setRevision(String revision) {
        this.revision = revision;
    }

    /**
     * Um
     */
    @Column(name = "UM")
    public String getUm() {
        return um;
    }

    public void setUm(String um) {
        this.um = um;
    }

    /**
     * name
     */
    @Column(name = "NAME")
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * BomTemplate:InternalProduct 多对一
     */
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "INTERNAL_PRODUCT_ID", referencedColumnName = "ID")
    public InternalProduct getInternalProduct() {
        return internalProduct;
    }

    public void setInternalProduct(InternalProduct internalProduct) {
        this.internalProduct = internalProduct;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }
}
