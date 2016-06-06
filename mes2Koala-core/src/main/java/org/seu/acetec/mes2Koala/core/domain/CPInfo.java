package org.seu.acetec.mes2Koala.core.domain;

import javax.persistence.*;

@Entity
@Table(name = "E_CP_INFO")
@Access(AccessType.PROPERTY)
public class CPInfo extends InternalProduct {

    private static final long serialVersionUID = 5442525438603003639L;

    // GROSS DIE
    private int grossDie;

    // 晶圆尺寸
    private String waferSize;

    // 测试时间/片
    private Float testTime;

    // 每次接触次数
    private int touchQty;

    // 最低Pass 报警数
    private int warningQty;

    // 报警指标分类
    private String warningType;
    
    // 产品制程要求
    private String productRequire;



    public int getGrossDie() {
        return grossDie;
    }

    public void setGrossDie(int grossDie) {
        this.grossDie = grossDie;
    }

    public String getWaferSize() {
        return waferSize;
    }

    public void setWaferSize(String waferSize) {
        this.waferSize = waferSize;
    }

    public int getTouchQty() {
        return touchQty;
    }

    public void setTouchQty(int touchQty) {
        this.touchQty = touchQty;
    }

    public int getWarningQty() {
        return warningQty;
    }

    public void setWarningQty(int warningQty) {
        this.warningQty = warningQty;
    }

    public String getWarningType() {
        return warningType;
    }

    public void setWarningType(String warningType) {
        this.warningType = warningType;
    }

    @Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		CPInfo other = (CPInfo) obj;
		if (grossDie != other.grossDie)
			return false;
		if (productRequire == null) {
			if (other.productRequire != null)
				return false;
		} else if (!productRequire.equals(other.productRequire))
			return false;
		if (testTime == null) {
			if (other.testTime != null)
				return false;
		} else if (!testTime.equals(other.testTime))
			return false;
		if (touchQty != other.touchQty)
			return false;
		if (waferSize == null) {
			if (other.waferSize != null)
				return false;
		} else if (!waferSize.equals(other.waferSize))
			return false;
		if (warningQty != other.warningQty)
			return false;
		if (warningType == null) {
			if (other.warningType != null)
				return false;
		} else if (!warningType.equals(other.warningType))
			return false;
		return true;
	}

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + grossDie;
		result = prime * result + ((productRequire == null) ? 0 : productRequire.hashCode());
		result = prime * result + ((testTime == null) ? 0 : testTime.hashCode());
		result = prime * result + touchQty;
		result = prime * result + ((waferSize == null) ? 0 : waferSize.hashCode());
		result = prime * result + warningQty;
		result = prime * result + ((warningType == null) ? 0 : warningType.hashCode());
		return result;
	}

	public String getProductRequire() {
		return productRequire;
	}

	public void setProductRequire(String productRequire) {
		this.productRequire = productRequire;
	}

	public Float getTestTime() {
		return testTime;
	}

	public void setTestTime(Float testTime) {
		this.testTime = testTime;
	}

}

