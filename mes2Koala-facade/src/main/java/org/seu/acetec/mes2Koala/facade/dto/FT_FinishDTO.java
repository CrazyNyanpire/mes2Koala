package org.seu.acetec.mes2Koala.facade.dto;

import java.io.Serializable;

public class FT_FinishDTO implements Serializable {

	private Long id;

	private int version;

	public FT_ResultDTO ftResultDTO;
	
	public FT_StatisticsDTO ftStatisticsDTO;

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

	public FT_ResultDTO getFtResultDTO(){
		return ftResultDTO;
	}
	
	public void setFtResultDTO(FT_ResultDTO ftResultDTO){
		this.ftResultDTO = ftResultDTO;
	}
	
	public FT_StatisticsDTO getFtStatisticsDTO(){
		return ftStatisticsDTO;
	}
	
	public void setFtStatisticsDTO(FT_StatisticsDTO ftStatisticsDTO){
		this.ftStatisticsDTO = ftStatisticsDTO;
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
		FT_FinishDTO other = (FT_FinishDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}