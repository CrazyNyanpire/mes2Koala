package org.seu.acetec.mes2Koala.core.domain;


import javax.persistence.*;

/**
 * 标签管理实体。
 * @author LCN LHB Howard
 * @version v1.1
 * @lastModifyDate 2015.12.20
 *
 */
@Entity
@Table(name = "E_Label")
@Access(AccessType.PROPERTY)
public class Label extends MES2AbstractEntity {

    private String labelName;
    private String labelType;
    private String testType;
    private String packageType;
    private String storagePath;
    private String taxType;

    public String getTaxType() {
		return taxType;
	}

	public void setTaxType(String taxType) {
		this.taxType = taxType;
	}

	/**
     * 存储路径，不包括标签名称
     * @return
     */
	public String getStoragePath() {
		return storagePath;
	}

	public void setStoragePath(String storagePath) {
		this.storagePath = storagePath;
	}

	/**
	 * 标签名称，不包括存储路径
	 * @return
	 */
	@Column(name="name", unique=true)
	public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }


    /**
     * 标签类型
     * @return
     */
    public String getLabelType() {
        return labelType;
    }

    public void setLabelType(String labelType) {
        this.labelType = labelType;
    }

    /**
     * 测试类型
     * @return
     */
    public String getTestType() {
        return testType;
    }

    public void setTestType(String testType) {
        this.testType = testType;
    }

    /**
     * 封装类型
     * @return
     */
    public String getPackageType() {
        return packageType;
    }

    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((labelName == null) ? 0 : labelName.hashCode());
		result = prime * result + ((labelType == null) ? 0 : labelType.hashCode());
		result = prime * result + ((packageType == null) ? 0 : packageType.hashCode());
		result = prime * result + ((storagePath == null) ? 0 : storagePath.hashCode());
		result = prime * result + ((testType == null) ? 0 : testType.hashCode());
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
		Label other = (Label) obj;
		if (labelName == null) {
			if (other.labelName != null)
				return false;
		} else if (!labelName.equals(other.labelName))
			return false;
		if (labelType == null) {
			if (other.labelType != null)
				return false;
		} else if (!labelType.equals(other.labelType))
			return false;
		if (packageType == null) {
			if (other.packageType != null)
				return false;
		} else if (!packageType.equals(other.packageType))
			return false;
		if (storagePath == null) {
			if (other.storagePath != null)
				return false;
		} else if (!storagePath.equals(other.storagePath))
			return false;
		if (testType == null) {
			if (other.testType != null)
				return false;
		} else if (!testType.equals(other.testType))
			return false;
		return true;
	}

	@Override
    public String[] businessKeys() {
		// TODO Auto-generated method stub
		return null;
    }
}
