package org.seu.acetec.mes2Koala.facade.dto;

import java.io.Serializable;

import org.openkoala.organisation.facade.dto.EmployeeDTO;

public class AcetecAuthorizationDTO implements Serializable {

	private Long id;

	private int version;

			
		private String opinion;
		
				
		private String note;
		
		private EmployeeDTO employeeDTO;
		
		private Long employeeId;
		
		private String lastModifyTime;
	
	public void setOpinion(String opinion) { 
		this.opinion = opinion;
	}

	public String getOpinion() {
		return this.opinion;
	}
		
			
	
	public void setNote(String note) { 
		this.note = note;
	}

	public String getNote() {
		return this.note;
	}
		
		
	public EmployeeDTO getEmployeeDTO() {
		return employeeDTO;
	}

	public void setEmployeeDTO(EmployeeDTO employeeDTO) {
		this.employeeDTO = employeeDTO;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	/**
	 * @return the lastModifyTime
	 */
	public String getLastModifyTime() {
		return lastModifyTime;
	}

	/**
	 * @param lastModifyTime the lastModifyTime to set
	 */
	public void setLastModifyTime(String lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
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
		result = prime * result + ((employeeDTO == null) ? 0 : employeeDTO.hashCode());
		result = prime * result + ((employeeId == null) ? 0 : employeeId.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((note == null) ? 0 : note.hashCode());
		result = prime * result + ((opinion == null) ? 0 : opinion.hashCode());
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
		AcetecAuthorizationDTO other = (AcetecAuthorizationDTO) obj;
		if (employeeDTO == null) {
			if (other.employeeDTO != null)
				return false;
		} else if (!employeeDTO.equals(other.employeeDTO))
			return false;
		if (employeeId == null) {
			if (other.employeeId != null)
				return false;
		} else if (!employeeId.equals(other.employeeId))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (note == null) {
			if (other.note != null)
				return false;
		} else if (!note.equals(other.note))
			return false;
		if (opinion == null) {
			if (other.opinion != null)
				return false;
		} else if (!opinion.equals(other.opinion))
			return false;
		if (version != other.version)
			return false;
		return true;
	}
}