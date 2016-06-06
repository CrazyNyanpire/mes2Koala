package org.seu.acetec.mes2Koala.facade.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.seu.acetec.mes2Koala.core.domain.RuncardNote;

public class ProcessTemplateDTO implements Serializable {

	private Long id;

	private int version;

			
		private String content;

		private String testType;
		
		private String createEmployNo;
				
		private String Runcard;
				
		private String name;
		
		private String handlerType;
		
		private String allowState;
		
		private List<TestingTemplateDTO> testingTemplateDTOs = new ArrayList<TestingTemplateDTO>();
		
		private List<Long> testIds;
		
		private int testVersion;
		
		private List<AcetecAuthorizationDTO> acetecAuthorizationDTOs = new ArrayList<AcetecAuthorizationDTO>();
		
		private List<Long> acetecAuthorizationIds = new ArrayList<Long>();
		
		private SpecialFormDTO specialFormDTO;
		
		private Map<String, RuncardNoteDTO> noteDTO;
	
	public Map<String, RuncardNoteDTO> getNoteDTO() {
			return noteDTO;
		}

		public void setNoteDTO(Map<String, RuncardNoteDTO> noteDTO) {
			this.noteDTO = noteDTO;
		}

	public void setContent(String content) { 
		this.content = content;
	}

	public String getContent() {
		return this.content;
	}
		
			
	
	public void setTestType(String testType) { 
		this.testType = testType;
	}

	public String getTestType() {
		return this.testType;
	}
		
			
	
	public void setCreateEmployNo(String createEmployNo) { 
		this.createEmployNo = createEmployNo;
	}

	public String getCreateEmployNo() {
		return this.createEmployNo;
	}
		
			
	
	public void setRuncard(String Runcard) { 
		this.Runcard = Runcard;
	}

	public String getRuncard() {
		return this.Runcard;
	}

	public void setName(String name) { 
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
		
			
	
	public void setHandlerType(String handlerType) { 
		this.handlerType = handlerType;
	}

	public String getHandlerType() {
		return this.handlerType;
	}
		
			
	
	public void setAllowState(String allowState) { 
		this.allowState = allowState;
	}

	public String getAllowState() {
		return this.allowState;
	}
		
		

	public List<Long> getTestIds() {
		return testIds;
	}

	public void setTestIds(List<Long> testIds) {
		this.testIds = testIds;
	}

	/**
	 * @return the testVersion
	 */
	public int getTestVersion() {
		return testVersion;
	}

	/**
	 * @param testVersion the testVersion to set
	 */
	public void setTestVersion(int testVersion) {
		this.testVersion = testVersion;
	}

	public List<AcetecAuthorizationDTO> getAcetecAuthorizationDTOs() {
		return acetecAuthorizationDTOs;
	}

	public void setAcetecAuthorizationDTOs(List<AcetecAuthorizationDTO> acetecAuthorizationDTOs) {
		this.acetecAuthorizationDTOs = acetecAuthorizationDTOs;
	}

	public List<Long> getAcetecAuthorizationIds() {
		return acetecAuthorizationIds;
	}

	public void setAcetecAuthorizationIds(List<Long> acetecAuthorizationIds) {
		this.acetecAuthorizationIds = acetecAuthorizationIds;
	}

	/**
	 * @return the specialFormDTO
	 */
	public SpecialFormDTO getSpecialFormDTO() {
		return specialFormDTO;
	}

	/**
	 * @param specialFormDTO the specialFormDTO to set
	 */
	public void setSpecialFormDTO(SpecialFormDTO specialFormDTO) {
		this.specialFormDTO = specialFormDTO;
	}

	public List<TestingTemplateDTO> getTestingTemplateDTOs() {
		return testingTemplateDTOs;
	}

	public void setTestingTemplateDTOs(List<TestingTemplateDTO> testingTemplateDTOs) {
		this.testingTemplateDTOs = testingTemplateDTOs;
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
		result = prime * result + ((Runcard == null) ? 0 : Runcard.hashCode());
		result = prime * result + ((acetecAuthorizationDTOs == null) ? 0 : acetecAuthorizationDTOs.hashCode());
		result = prime * result + ((acetecAuthorizationIds == null) ? 0 : acetecAuthorizationIds.hashCode());
		result = prime * result + ((allowState == null) ? 0 : allowState.hashCode());
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((createEmployNo == null) ? 0 : createEmployNo.hashCode());
		result = prime * result + ((handlerType == null) ? 0 : handlerType.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((noteDTO == null) ? 0 : noteDTO.hashCode());
		result = prime * result + ((specialFormDTO == null) ? 0 : specialFormDTO.hashCode());
		result = prime * result + ((testIds == null) ? 0 : testIds.hashCode());
		result = prime * result + ((testType == null) ? 0 : testType.hashCode());
		result = prime * result + testVersion;
		result = prime * result + ((testingTemplateDTOs == null) ? 0 : testingTemplateDTOs.hashCode());
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
		ProcessTemplateDTO other = (ProcessTemplateDTO) obj;
		if (Runcard == null) {
			if (other.Runcard != null)
				return false;
		} else if (!Runcard.equals(other.Runcard))
			return false;
		if (acetecAuthorizationDTOs == null) {
			if (other.acetecAuthorizationDTOs != null)
				return false;
		} else if (!acetecAuthorizationDTOs.equals(other.acetecAuthorizationDTOs))
			return false;
		if (acetecAuthorizationIds == null) {
			if (other.acetecAuthorizationIds != null)
				return false;
		} else if (!acetecAuthorizationIds.equals(other.acetecAuthorizationIds))
			return false;
		if (allowState == null) {
			if (other.allowState != null)
				return false;
		} else if (!allowState.equals(other.allowState))
			return false;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (createEmployNo == null) {
			if (other.createEmployNo != null)
				return false;
		} else if (!createEmployNo.equals(other.createEmployNo))
			return false;
		if (handlerType == null) {
			if (other.handlerType != null)
				return false;
		} else if (!handlerType.equals(other.handlerType))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (noteDTO == null) {
			if (other.noteDTO != null)
				return false;
		} else if (!noteDTO.equals(other.noteDTO))
			return false;
		if (specialFormDTO == null) {
			if (other.specialFormDTO != null)
				return false;
		} else if (!specialFormDTO.equals(other.specialFormDTO))
			return false;
		if (testIds == null) {
			if (other.testIds != null)
				return false;
		} else if (!testIds.equals(other.testIds))
			return false;
		if (testType == null) {
			if (other.testType != null)
				return false;
		} else if (!testType.equals(other.testType))
			return false;
		if (testVersion != other.testVersion)
			return false;
		if (testingTemplateDTOs == null) {
			if (other.testingTemplateDTOs != null)
				return false;
		} else if (!testingTemplateDTOs.equals(other.testingTemplateDTOs))
			return false;
		if (version != other.version)
			return false;
		return true;
	}
}