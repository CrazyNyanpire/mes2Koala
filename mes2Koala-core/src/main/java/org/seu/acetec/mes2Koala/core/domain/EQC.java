package org.seu.acetec.mes2Koala.core.domain;

import javax.persistence.*;

@Entity
@Table(name = "E_EQC")
@Access(AccessType.PROPERTY)
public class EQC extends MES2AbstractEntity {

    private static final long serialVersionUID = -9213808911061616878L;

    // EQC数量
    private int Qty;

    // 来料数量下限
    private int lowerLimit;

    // 来料数量上限
    private int upperLimit;
    
    // 绑定的测试节点
    private FTNode ftNode;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "FT_NODE_ID")
    public FTNode getFtNode() {
        return ftNode;
    }

    public void setFtNode(FTNode ftNode) {
        this.ftNode = ftNode;
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

	@Override
    public String[] businessKeys() {
        return new String[0];
    }


    @Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Qty;
		result = prime * result + ((ftNode == null) ? 0 : ftNode.hashCode());
		result = prime * result + lowerLimit;
		result = prime * result + upperLimit;
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
		EQC other = (EQC) obj;
		if (Qty != other.Qty)
			return false;
		if (ftNode == null) {
			if (other.ftNode != null)
				return false;
		} else if (!ftNode.equals(other.ftNode))
			return false;
		if (lowerLimit != other.lowerLimit)
			return false;
		if (upperLimit != other.upperLimit)
			return false;
		return true;
	}

}
