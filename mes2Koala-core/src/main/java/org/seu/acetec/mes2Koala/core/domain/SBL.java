package org.seu.acetec.mes2Koala.core.domain;

import org.seu.acetec.mes2Koala.core.enums.SBLQuality;

import javax.persistence.*;

/**
 * @author 阙宇翔
 * @version 2016/3/4
 */
@Entity
@Table(name = "E_SBL")
@Access(AccessType.PROPERTY)
public class SBL extends MES2AbstractEntity {

    // bin别
    private String type;

    // 良率下限（%）
    private double lowerLimit;

    // 良率上线（%）
    private double upperLimit;

    // 品质 PASS/FAIL
    private SBLQuality quality;

    // Site 1-5
    private String site;

    private String nodeName;
    
    // 绑定的测试节点
    private FTNode ftNode;

    @Enumerated(EnumType.STRING)
    public SBLQuality getQuality() {
        return quality;
    }

    public void setQuality(SBLQuality quality) {
        this.quality = quality;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "FT_NODE_ID")
    public FTNode getFtNode() {
        return ftNode;
    }

    public void setFtNode(FTNode ftNode) {
        this.ftNode = ftNode;
    }

    public double getLowerLimit() {
        return lowerLimit;
    }

    public void setLowerLimit(double lowerLimit) {
        this.lowerLimit = lowerLimit;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(double upperLimit) {
        this.upperLimit = upperLimit;
    }

    public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        SBL sbl = (SBL) o;

        if (Double.compare(sbl.lowerLimit, lowerLimit) != 0) return false;
        if (Double.compare(sbl.upperLimit, upperLimit) != 0) return false;
        if (type != null ? !type.equals(sbl.type) : sbl.type != null) return false;
        if (quality != null ? !quality.equals(sbl.quality) : sbl.quality != null) return false;
        if (site != null ? !site.equals(sbl.site) : sbl.site != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        long temp;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        temp = Double.doubleToLongBits(lowerLimit);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(upperLimit);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (quality != null ? quality.hashCode() : 0);
        result = 31 * result + (site != null ? site.hashCode() : 0);
        return result;
    }

    @Override
    public String[] businessKeys() {
        return new String[0];
    }
}
