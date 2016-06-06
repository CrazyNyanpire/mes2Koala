package org.seu.acetec.mes2Koala.facade.dto;

import java.io.Serializable;

public class SpecialFormDTO implements Serializable {

	private Long id;

	private int version;

								
		private Boolean summaryForm;
		
									
		private Boolean flowForm;
		
									
		private Boolean reelcodeForm;
		
									
		private Boolean indexForm;
		
									
		private Boolean sizeForm;
		
									
		private Boolean lossForm;
		
									
		private Boolean checkBoxForm;
		
									
		private Boolean checkSelfForm;
		
								
	
	public void setSummaryForm(Boolean summaryForm) { 
		this.summaryForm = summaryForm;
	}

	public Boolean getSummaryForm() {
		return this.summaryForm;
	}
		
								
	
	public void setFlowForm(Boolean flowForm) { 
		this.flowForm = flowForm;
	}

	public Boolean getFlowForm() {
		return this.flowForm;
	}
		
								
	
	public void setReelcodeForm(Boolean reelcodeForm) { 
		this.reelcodeForm = reelcodeForm;
	}

	public Boolean getReelcodeForm() {
		return this.reelcodeForm;
	}
		
								
	
	public void setIndexForm(Boolean indexForm) { 
		this.indexForm = indexForm;
	}

	public Boolean getIndexForm() {
		return this.indexForm;
	}
		
								
	
	public void setSizeForm(Boolean sizeForm) { 
		this.sizeForm = sizeForm;
	}

	public Boolean getSizeForm() {
		return this.sizeForm;
	}
		
								
	
	public void setLossForm(Boolean lossForm) { 
		this.lossForm = lossForm;
	}

	public Boolean getLossForm() {
		return this.lossForm;
	}
		
								
	
	public void setCheckBoxForm(Boolean checkBoxForm) { 
		this.checkBoxForm = checkBoxForm;
	}

	public Boolean getCheckBoxForm() {
		return this.checkBoxForm;
	}
		
								
	
	public void setCheckSelfForm(Boolean checkSelfForm) { 
		this.checkSelfForm = checkSelfForm;
	}

	public Boolean getCheckSelfForm() {
		return this.checkSelfForm;
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
		SpecialFormDTO other = (SpecialFormDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}