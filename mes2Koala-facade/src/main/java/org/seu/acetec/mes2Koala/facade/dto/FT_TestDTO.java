package org.seu.acetec.mes2Koala.facade.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FT_TestDTO implements Serializable {

	private Long id;

	private int version;

							
		private Integer ftState;
		
		private Integer rtState;
		
		private Integer eqcState;
		
		private Integer latState;
				
		private String ftNote;
		
		private String orderInfo;
				
		private String rtNote;
		
				
		private String latNote;
		
				
		private String eqcNote;
		
		private List<FT_ResultDTO> ftList = new ArrayList<FT_ResultDTO>();	
		
		private List<FT_ResultDTO> rtList = new ArrayList<FT_ResultDTO>();	
		
		private List<FT_ResultDTO> eqcList = new ArrayList<FT_ResultDTO>();	
		
		private List<FT_ResultDTO> latList = new ArrayList<FT_ResultDTO>();	
	
		private FT_ResultDTO finalYield = new FT_ResultDTO();
			
	public Integer getFtState() {
			return ftState;
		}

		public void setFtState(Integer ftState) {
			this.ftState = ftState;
		}

	public Integer getRtState() {
			return rtState;
		}

		public void setRtState(Integer rtState) {
			this.rtState = rtState;
		}

	public Integer getEqcState() {
			return eqcState;
		}

		public void setEqcState(Integer eqcState) {
			this.eqcState = eqcState;
		}

	public Integer getLatState() {
			return latState;
		}

		public void setLatState(Integer latState) {
			this.latState = latState;
		}

	public void setFtNote(String ftNote) { 
		this.ftNote = ftNote;
	}

	public String getFtNote() {
		return this.ftNote;
	}
		
			
	
	public void setRtNote(String rtNote) { 
		this.rtNote = rtNote;
	}

	public String getRtNote() {
		return this.rtNote;
	}
		
			
	
	public void setLatNote(String latNote) { 
		this.latNote = latNote;
	}

	public String getLatNote() {
		return this.latNote;
	}
		
			
	
	public void setEqcNote(String eqcNote) { 
		this.eqcNote = eqcNote;
	}

	public String getEqcNote() {
		return this.eqcNote;
	}
		
		
	/**
	 * @return the ftList
	 */
	public List<FT_ResultDTO> getFtList() {
		return ftList;
	}

	/**
	 * @param ftList the ftList to set
	 */
	public void setFtList(List<FT_ResultDTO> ftList) {
		this.ftList = ftList;
	}

	/**
	 * @return the rtList
	 */
	public List<FT_ResultDTO> getRtList() {
		return rtList;
	}

	/**
	 * @param rtList the rtList to set
	 */
	public void setRtList(List<FT_ResultDTO> rtList) {
		this.rtList = rtList;
	}

	/**
	 * @return the eqcList
	 */
	public List<FT_ResultDTO> getEqcList() {
		return eqcList;
	}

	/**
	 * @param eqcList the eqcList to set
	 */
	public void setEqcList(List<FT_ResultDTO> eqcList) {
		this.eqcList = eqcList;
	}

	/**
	 * @return the latList
	 */
	public List<FT_ResultDTO> getLatList() {
		return latList;
	}

	/**
	 * @param latList the latList to set
	 */
	public void setLatList(List<FT_ResultDTO> latList) {
		this.latList = latList;
	}

	public FT_ResultDTO getFinalYield() {
		return finalYield;
	}

	public void setFinalYield(FT_ResultDTO finalYield) {
		this.finalYield = finalYield;
	}

	public String getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(String orderInfo) {
		this.orderInfo = orderInfo;
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
		FT_TestDTO other = (FT_TestDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}