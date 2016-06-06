package org.seu.acetec.mes2Koala.core.domain;

import javax.persistence.*;

@Entity
@Table(name = "E_FT_IQC_NODE")
@Access(AccessType.PROPERTY)
@PrimaryKeyJoinColumn(name = "FT_NODE_ID")
public class FTIQCNode extends FTNode {

    private static final long serialVersionUID = -5697412347862473677L;

    private String mark;
    private String grossWeight;
    private String netWeight;
    private String reelCode;

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getGrossWeight() {
        return grossWeight;
    }

    public void setGrossWeight(String grossWeight) {
        this.grossWeight = grossWeight;
    }

    public String getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(String netWeight) {
        this.netWeight = netWeight;
    }

    public String getReelCode() {
        return reelCode;
    }

    public void setReelCode(String reelCode) {
        this.reelCode = reelCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        FTIQCNode FTIQCNode = (FTIQCNode) o;

        if (mark != null ? !mark.equals(FTIQCNode.mark) : FTIQCNode.mark != null) return false;
        if (grossWeight != null ? !grossWeight.equals(FTIQCNode.grossWeight) : FTIQCNode.grossWeight != null) return false;
        if (netWeight != null ? !netWeight.equals(FTIQCNode.netWeight) : FTIQCNode.netWeight != null) return false;
        return reelCode != null ? reelCode.equals(FTIQCNode.reelCode) : FTIQCNode.reelCode == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (mark != null ? mark.hashCode() : 0);
        result = 31 * result + (grossWeight != null ? grossWeight.hashCode() : 0);
        result = 31 * result + (netWeight != null ? netWeight.hashCode() : 0);
        result = 31 * result + (reelCode != null ? reelCode.hashCode() : 0);
        return result;
    }
}
