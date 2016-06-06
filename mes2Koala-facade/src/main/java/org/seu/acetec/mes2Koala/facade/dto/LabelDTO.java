package org.seu.acetec.mes2Koala.facade.dto;

import java.io.Serializable;

public class LabelDTO implements Serializable {

	private Long id;

	private int version;

			
		private String testType;
		
				
		private String packageType;
		
				
		private String labelType;
		
				
		private String labelFullName;
		

		private String labelName;
		
		private String taxType;
		
			
	
	public String getTaxType() {
			return taxType;
		}

		public void setTaxType(String taxType) {
			this.taxType = taxType;
		}

	public String getLabelName() {
			return labelName;
		}

		public void setLabelName(String labelName) {
			this.labelName = labelName;
		}

	public void setTestType(String testType) { 
		this.testType = testType;
	}

	public String getTestType() {
		return this.testType;
	}
		
			
	
	public void setPackageType(String packageType) { 
		this.packageType = packageType;
	}

	public String getPackageType() {
		return this.packageType;
	}
		
			
	
	public void setLabelType(String labelType) { 
		this.labelType = labelType;
	}

	public String getLabelType() {
		return this.labelType;
	}
		
			
	
	public void setLabelFullName(String labelFullName) { 
		this.labelFullName = labelFullName;
	}

	public String getLabelFullName() {
		return this.labelFullName;
	}
		
		
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((labelFullName == null) ? 0 : labelFullName.hashCode());
		result = prime * result + ((labelName == null) ? 0 : labelName.hashCode());
		result = prime * result + ((labelType == null) ? 0 : labelType.hashCode());
		result = prime * result + ((packageType == null) ? 0 : packageType.hashCode());
		result = prime * result + ((testType == null) ? 0 : testType.hashCode());
		result = prime * result + version;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LabelDTO other = (LabelDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (labelFullName == null) {
			if (other.labelFullName != null)
				return false;
		} else if (!labelFullName.equals(other.labelFullName))
			return false;
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
		if (testType == null) {
			if (other.testType != null)
				return false;
		} else if (!testType.equals(other.testType))
			return false;
		if (version != other.version)
			return false;
		return true;
	}
}