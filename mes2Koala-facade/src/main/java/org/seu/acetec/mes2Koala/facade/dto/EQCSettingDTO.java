package org.seu.acetec.mes2Koala.facade.dto;

import java.io.Serializable;

public class EQCSettingDTO implements Serializable {

	private Long id;

	private int version;

							
		private Integer Qty;
		
								
		private Integer upperLimit;
		
								
		private Integer lowerLimit;
		
		private String nodeName;
		
		private InternalProductDTO internalProductDTO;
							
	
	public void setQty(Integer Qty) { 
		this.Qty = Qty;
	}

	public Integer getQty() {
		return this.Qty;
	}
		
							
	
	public void setUpperLimit(Integer upperLimit) { 
		this.upperLimit = upperLimit;
	}

	public Integer getUpperLimit() {
		return this.upperLimit;
	}
		
							
	
	public void setLowerLimit(Integer lowerLimit) { 
		this.lowerLimit = lowerLimit;
	}

	public Integer getLowerLimit() {
		return this.lowerLimit;
	}
		
		
	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	/**
	 * @return the internalProductDTO
	 */
	public InternalProductDTO getInternalProductDTO() {
		return internalProductDTO;
	}

	/**
	 * @param internalProductDTO the internalProductDTO to set
	 */
	public void setInternalProductDTO(InternalProductDTO internalProductDTO) {
		this.internalProductDTO = internalProductDTO;
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
		EQCSettingDTO other = (EQCSettingDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}