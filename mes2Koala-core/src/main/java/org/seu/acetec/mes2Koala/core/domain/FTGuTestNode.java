package org.seu.acetec.mes2Koala.core.domain;

import javax.persistence.*;

@Entity
@Table(name = "E_FT_GU_TEST_NODE")
@Access(AccessType.PROPERTY)
@PrimaryKeyJoinColumn(name = "FT_NODE_ID")
public class FTGuTestNode extends FTNode {

    private static final long serialVersionUID = -1263548492896007564L;
    private String customerName;
    private String goldenUnitsType;
    private String goldenUnitsNo;
    private String useTimes;
    private String productNumber;
    private String pe;

    private String standardResult;
    private String record;
    private String nox;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getGoldenUnitsType() {
        return goldenUnitsType;
    }

    public void setGoldenUnitsType(String goldenUnitsType) {
        this.goldenUnitsType = goldenUnitsType;
    }

    public String getGoldenUnitsNo() {
        return goldenUnitsNo;
    }

    public void setGoldenUnitsNo(String goldenUnitsNo) {
        this.goldenUnitsNo = goldenUnitsNo;
    }

    public String getUseTimes() {
        return useTimes;
    }

    public void setUseTimes(String useTimes) {
        this.useTimes = useTimes;
    }

    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }

    public String getPe() {
        return pe;
    }

    public void setPe(String pe) {
        this.pe = pe;
    }

    public String getStandardResult() {
        return standardResult;
    }

    public void setStandardResult(String standardResult) {
        this.standardResult = standardResult;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public String getNox() {
        return nox;
    }

    public void setNox(String nox) {
        this.nox = nox;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        FTGuTestNode ft_guTestNode = (FTGuTestNode) o;

        if (customerName != null ? !customerName.equals(ft_guTestNode.customerName) : ft_guTestNode.customerName != null)
            return false;
        if (goldenUnitsType != null ? !goldenUnitsType.equals(ft_guTestNode.goldenUnitsType) : ft_guTestNode.goldenUnitsType != null)
            return false;
        if (goldenUnitsNo != null ? !goldenUnitsNo.equals(ft_guTestNode.goldenUnitsNo) : ft_guTestNode.goldenUnitsNo != null)
            return false;
        if (useTimes != null ? !useTimes.equals(ft_guTestNode.useTimes) : ft_guTestNode.useTimes != null) return false;
        if (productNumber != null ? !productNumber.equals(ft_guTestNode.productNumber) : ft_guTestNode.productNumber != null)
            return false;
        if (pe != null ? !pe.equals(ft_guTestNode.pe) : ft_guTestNode.pe != null) return false;
        if (standardResult != null ? !standardResult.equals(ft_guTestNode.standardResult) : ft_guTestNode.standardResult != null)
            return false;
        if (record != null ? !record.equals(ft_guTestNode.record) : ft_guTestNode.record != null) return false;
        return nox != null ? nox.equals(ft_guTestNode.nox) : ft_guTestNode.nox == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (customerName != null ? customerName.hashCode() : 0);
        result = 31 * result + (goldenUnitsType != null ? goldenUnitsType.hashCode() : 0);
        result = 31 * result + (goldenUnitsNo != null ? goldenUnitsNo.hashCode() : 0);
        result = 31 * result + (useTimes != null ? useTimes.hashCode() : 0);
        result = 31 * result + (productNumber != null ? productNumber.hashCode() : 0);
        result = 31 * result + (pe != null ? pe.hashCode() : 0);
        result = 31 * result + (standardResult != null ? standardResult.hashCode() : 0);
        result = 31 * result + (record != null ? record.hashCode() : 0);
        result = 31 * result + (nox != null ? nox.hashCode() : 0);
        return result;
    }
}
