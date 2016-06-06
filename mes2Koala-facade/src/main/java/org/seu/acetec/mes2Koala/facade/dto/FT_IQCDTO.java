package org.seu.acetec.mes2Koala.facade.dto;

import java.io.Serializable;

public class FT_IQCDTO implements Serializable {

	private Long id;

	private int version;

			
		private String reelCode;
		
				
		private String mark;
		
														
		private String grossWeight;
		
								
		private String netWeight;
		
		private FT_ResultDTO ftResultDTO;
		
		

	
	public FT_ResultDTO getFtResultDTO() {
			return ftResultDTO;
		}

		public void setFtResultDTO(FT_ResultDTO ftResultDTO) {
			this.ftResultDTO = ftResultDTO;
		}

	public void setReelCode(String reelCode) { 
		this.reelCode = reelCode;
	}

	public String getReelCode() {
		return this.reelCode;
	}
		
							
	
	public void setMark(String mark) { 
		this.mark = mark;
	}

	public String getMark() {
		return this.mark;
	}
								
	
	public void setGrossWeight(String grossWeight) { 
		this.grossWeight = grossWeight;
	}

	public String getGrossWeight() {
		return this.grossWeight;
	}
		
							
	
	public void setNetWeight(String netWeight) { 
		this.netWeight = netWeight;
	}

	public String getNetWeight() {
		return this.netWeight;
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
		FT_IQCDTO other = (FT_IQCDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}