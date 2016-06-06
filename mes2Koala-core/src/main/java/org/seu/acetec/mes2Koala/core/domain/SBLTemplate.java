package org.seu.acetec.mes2Koala.core.domain;

import org.seu.acetec.mes2Koala.core.enums.SBLQuality;

import javax.persistence.*;

@Entity
@Table(name = "E_SBL_TEMPLATE")
@Access(AccessType.PROPERTY)
public class SBLTemplate extends MES2AbstractEntity {

    private static final long serialVersionUID = -8957814408148326290L;


    // Bin别  Bin1 Bin2 ...
    private String binType;

    // 良率下限（%）
    private double lowerLimit;

    // 良率上线（%
    private double upperLimit;

    // 品质 PASS/FAIL
    private SBLQuality binQuality;

    // Site 1-5
    private String site;

    // SBLTemplate与测试站点绑定，保存名称
    private String nodeName;

    private InternalProduct internalProduct;

    @Enumerated(EnumType.STRING)
    public SBLQuality getBinQuality() {
        return binQuality;
    }

    public void setBinQuality(SBLQuality binQuality) {
        this.binQuality = binQuality;
    }

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "Product_ID")
    public InternalProduct getInternalProduct() {
        return internalProduct;
    }

    public void setInternalProduct(InternalProduct internalProduct) {
        this.internalProduct = internalProduct;
    }

    public String getBinType() {
        return binType;
    }

    public void setBinType(String binType) {
        this.binType = binType;
    }

    public double getLowerLimit() {
        return lowerLimit;
    }

    public void setLowerLimit(double lowerLimit) {
        this.lowerLimit = lowerLimit;
    }

    public double getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(double upperLimit) {
        this.upperLimit = upperLimit;
    }

    public String getSite() {
        return site;
    }


    public void setSite(String site) {
        this.site = site;
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

        SBLTemplate that = (SBLTemplate) o;

        if (Double.compare(that.lowerLimit, lowerLimit) != 0) return false;
        if (Double.compare(that.upperLimit, upperLimit) != 0) return false;
        if (binType != null ? !binType.equals(that.binType) : that.binType != null) return false;
        if (binQuality != null ? !binQuality.equals(that.binQuality) : that.binQuality != null) return false;
        if (site != null ? !site.equals(that.site) : that.site != null) return false;
        if (nodeName != null ? !nodeName.equals(that.nodeName) : that.nodeName != null) return false;
        return internalProduct != null ? internalProduct.equals(that.internalProduct) : that.internalProduct == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        long temp;
        result = 31 * result + (binType != null ? binType.hashCode() : 0);
        temp = Double.doubleToLongBits(lowerLimit);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(upperLimit);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (binQuality != null ? binQuality.hashCode() : 0);
        result = 31 * result + (site != null ? site.hashCode() : 0);
        result = 31 * result + (nodeName != null ? nodeName.hashCode() : 0);
        result = 31 * result + (internalProduct != null ? internalProduct.hashCode() : 0);
        return result;
    }
}
