package org.seu.acetec.mes2Koala.facade.dto;

import java.io.Serializable;

public class LabelPlanDTO implements Serializable {

	private Long id;

	private int version;
	
	private LabelDTO labelInsideDTO;
	
	private LabelDTO labelOutsideDTO;
	
	private LabelDTO labelReelDTO;
	
	private InternalProductDTO internalProductDTO;
	
	private Long labelInsideId;
	
	private Long labelOutsideId;
	
	private Long labelReelId;
	
	private Long internalProductId;
	
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

	public LabelDTO getLabelInsideDTO() {
		return labelInsideDTO;
	}

	public void setLabelInsideDTO(LabelDTO labelInsideDTO) {
		this.labelInsideDTO = labelInsideDTO;
	}

	public LabelDTO getLabelOutsideDTO() {
		return labelOutsideDTO;
	}

	public void setLabelOutsideDTO(LabelDTO labelOutsideDTO) {
		this.labelOutsideDTO = labelOutsideDTO;
	}

	public LabelDTO getLabelReelDTO() {
		return labelReelDTO;
	}

	public void setLabelReelDTO(LabelDTO labelReelDTO) {
		this.labelReelDTO = labelReelDTO;
	}

	public InternalProductDTO getInternalProductDTO() {
		return internalProductDTO;
	}

	public void setInternalProductDTO(InternalProductDTO internalProductDTO) {
		this.internalProductDTO = internalProductDTO;
	}


	public Long getLabelInsideId() {
		return labelInsideId;
	}

	public void setLabelInsideId(Long labelInsideId) {
		this.labelInsideId = labelInsideId;
	}

	public Long getLabelOutsideId() {
		return labelOutsideId;
	}

	public void setLabelOutsideId(Long labelOutsideId) {
		this.labelOutsideId = labelOutsideId;
	}

	public Long getLabelReelId() {
		return labelReelId;
	}

	public void setLabelReelId(Long labelReelId) {
		this.labelReelId = labelReelId;
	}

	public Long getInternalProductId() {
		return internalProductId;
	}

	public void setInternalProductId(Long internalProductId) {
		this.internalProductId = internalProductId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((internalProductDTO == null) ? 0 : internalProductDTO.hashCode());
		result = prime * result + ((internalProductId == null) ? 0 : internalProductId.hashCode());
		result = prime * result + ((labelInsideDTO == null) ? 0 : labelInsideDTO.hashCode());
		result = prime * result + ((labelInsideId == null) ? 0 : labelInsideId.hashCode());
		result = prime * result + ((labelOutsideDTO == null) ? 0 : labelOutsideDTO.hashCode());
		result = prime * result + ((labelOutsideId == null) ? 0 : labelOutsideId.hashCode());
		result = prime * result + ((labelReelDTO == null) ? 0 : labelReelDTO.hashCode());
		result = prime * result + ((labelReelId == null) ? 0 : labelReelId.hashCode());
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
		LabelPlanDTO other = (LabelPlanDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (internalProductDTO == null) {
			if (other.internalProductDTO != null)
				return false;
		} else if (!internalProductDTO.equals(other.internalProductDTO))
			return false;
		if (internalProductId == null) {
			if (other.internalProductId != null)
				return false;
		} else if (!internalProductId.equals(other.internalProductId))
			return false;
		if (labelInsideDTO == null) {
			if (other.labelInsideDTO != null)
				return false;
		} else if (!labelInsideDTO.equals(other.labelInsideDTO))
			return false;
		if (labelInsideId == null) {
			if (other.labelInsideId != null)
				return false;
		} else if (!labelInsideId.equals(other.labelInsideId))
			return false;
		if (labelOutsideDTO == null) {
			if (other.labelOutsideDTO != null)
				return false;
		} else if (!labelOutsideDTO.equals(other.labelOutsideDTO))
			return false;
		if (labelOutsideId == null) {
			if (other.labelOutsideId != null)
				return false;
		} else if (!labelOutsideId.equals(other.labelOutsideId))
			return false;
		if (labelReelDTO == null) {
			if (other.labelReelDTO != null)
				return false;
		} else if (!labelReelDTO.equals(other.labelReelDTO))
			return false;
		if (labelReelId == null) {
			if (other.labelReelId != null)
				return false;
		} else if (!labelReelId.equals(other.labelReelId))
			return false;
		if (version != other.version)
			return false;
		return true;
	}

	
}