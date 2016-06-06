package org.seu.acetec.mes2Koala.facade.dto;

import java.io.Serializable;

public class FT_GuTestDTO implements Serializable {

	private Long id;
	private int version;
	
	private String customerName;
	private String goldenUnitsType;
	private String goldenUnitsNo;
	private String useTimes;
	private String productNumber;
	private String pe;
	
	private String standardResult;
	private String record;
	private String nox;		
	
	public Long getId(){
		return id;
	}
	public void setId(Long id){
		this.id = id;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		FT_GuTestDTO other = (FT_GuTestDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}